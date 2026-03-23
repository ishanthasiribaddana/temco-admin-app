package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.temcobank.dto.AuthRequest;
import lk.temcobank.dto.AuthResponse;
import lk.temcobank.entity.UserAccount;
import lk.temcobank.exception.AuthenticationException;
import lk.temcobank.security.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Authentication service for login/logout operations.
 * Authenticates against the user_login table (mapped as UserAccount entity).
 * User identity comes from general_user_profile via the FK relationship.
 * JWT is stateless — no server-side session table needed.
 */
@Stateless
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JwtUtil jwtUtil;

    /**
     * Authenticate user and generate JWT token.
     */
    public AuthResponse login(AuthRequest request, String ipAddress, String userAgent) {
        logger.info("Login attempt for user: {}", request.getUsername());

        // Find user_login by username
        UserAccount user = findUserByUsername(request.getUsername());
        if (user == null) {
            throw new AuthenticationException("Invalid username or password");
        }

        // Check if account is locked (exceeded max login attempts)
        if (user.isLocked()) {
            throw new AuthenticationException("Account is locked. Please contact administrator.");
        }

        // Check if account is active
        if (user.getIsActive() == null || user.getIsActive() != 1) {
            throw new AuthenticationException("Account is not active. Please contact administrator.");
        }

        // Verify password (supports both BCrypt and legacy Base64 formats)
        if (!verifyPassword(request.getPassword(), user.getPassword())) {
            user.recordFailedLogin();
            entityManager.merge(user);
            throw new AuthenticationException("Invalid username or password");
        }

        // Successful login
        user.recordSuccessfulLogin();
        entityManager.merge(user);

        // Role from user_login → user_role
        List<String> roles = user.getRoleName() != null 
                ? Collections.singletonList(user.getRoleName()) 
                : Collections.emptyList();

        // Generate tokens
        String accessToken = jwtUtil.generateToken(
                user.getId().longValue(), user.getUsername(), roles);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        logger.info("Login successful for user: {}", request.getUsername());

        return new AuthResponse(
                accessToken,
                refreshToken,
                user.getId().longValue(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                roles,
                false
        );
    }

    /**
     * Logout user — JWT is stateless, so just log it.
     */
    public void logout(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            logger.info("Logout for user: {}", username);
        } catch (Exception e) {
            logger.error("Logout error: {}", e.getMessage());
        }
    }

    /**
     * Refresh access token using refresh token.
     */
    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new AuthenticationException("Invalid refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        UserAccount user = findUserByUsername(username);

        if (user == null || user.getIsActive() == null || user.getIsActive() != 1) {
            throw new AuthenticationException("User not found or inactive");
        }

        List<String> roles = user.getRoleName() != null 
                ? Collections.singletonList(user.getRoleName()) 
                : Collections.emptyList();

        String newAccessToken = jwtUtil.generateToken(
                user.getId().longValue(), user.getUsername(), roles);
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                user.getId().longValue(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                roles,
                false
        );
    }

    /**
     * Change user password (stores as BCrypt).
     */
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        UserAccount user = entityManager.find(UserAccount.class, userId.intValue());
        if (user == null) {
            throw new AuthenticationException("User not found");
        }

        if (!verifyPassword(oldPassword, user.getPassword())) {
            throw new AuthenticationException("Current password is incorrect");
        }

        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
        entityManager.merge(user);

        logger.info("Password changed for user: {}", user.getUsername());
    }

    /**
     * Get current user details from token.
     */
    public AuthResponse getCurrentUser(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new AuthenticationException("Invalid token");
        }

        Long userId = jwtUtil.extractUserId(token);
        UserAccount user = entityManager.find(UserAccount.class, userId.intValue());

        if (user == null) {
            throw new AuthenticationException("User not found");
        }

        List<String> roles = user.getRoleName() != null 
                ? Collections.singletonList(user.getRoleName()) 
                : Collections.emptyList();

        return new AuthResponse(
                null, null,
                user.getId().longValue(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                roles,
                false
        );
    }

    // ==================== Helper Methods ====================

    private UserAccount findUserByUsername(String username) {
        try {
            return entityManager.createQuery(
                    "SELECT u FROM UserAccount u WHERE u.username = :username AND u.isActive = 1",
                    UserAccount.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Verify password using BCrypt.
     */
    private boolean verifyPassword(String plainPassword, String storedPassword) {
        if (storedPassword == null || storedPassword.isEmpty()) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, storedPassword);
        } catch (Exception e) {
            logger.error("Password verification error: {}", e.getMessage());
            return false;
        }
    }
}
