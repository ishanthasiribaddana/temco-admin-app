package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.temcobank.dto.AuthRequest;
import lk.temcobank.dto.AuthResponse;
import lk.temcobank.entity.UserAccount;
import lk.temcobank.entity.UserAccountRole;
import lk.temcobank.entity.UserLoginHistory;
import lk.temcobank.entity.UserSession;
import lk.temcobank.exception.AuthenticationException;
import lk.temcobank.security.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Authentication service for login/logout operations.
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

        // Find user by username
        UserAccount user = findUserByUsername(request.getUsername());
        if (user == null) {
            recordLoginHistory(null, ipAddress, userAgent, "FAILED", "User not found");
            throw new AuthenticationException("Invalid username or password");
        }

        // Check if account is locked
        if (user.isLocked()) {
            recordLoginHistory(user, ipAddress, userAgent, "BLOCKED", "Account locked");
            throw new AuthenticationException("Account is locked. Please try again later.");
        }

        // Check if account is active
        if (!"ACTIVE".equals(user.getAccountStatus())) {
            recordLoginHistory(user, ipAddress, userAgent, "FAILED", "Account not active");
            throw new AuthenticationException("Account is not active. Please contact administrator.");
        }

        // Verify password
        if (!verifyPassword(request.getPassword(), user.getPasswordHash())) {
            user.recordFailedLogin();
            entityManager.merge(user);
            recordLoginHistory(user, ipAddress, userAgent, "FAILED", "Invalid password");
            throw new AuthenticationException("Invalid username or password");
        }

        // Successful login
        user.recordSuccessfulLogin();
        entityManager.merge(user);

        // Get user roles
        List<String> roles = user.getUserRoles().stream()
                .map(uar -> uar.getUserRole().getRoleCode())
                .collect(Collectors.toList());

        // Generate tokens
        String accessToken = jwtUtil.generateToken(user.getId(), user.getUsername(), roles);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // Create session
        createUserSession(user, accessToken, ipAddress, userAgent);

        // Record successful login
        recordLoginHistory(user, ipAddress, userAgent, "SUCCESS", null);

        logger.info("Login successful for user: {}", request.getUsername());

        return new AuthResponse(
                accessToken,
                refreshToken,
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                roles,
                user.getMustChangePassword()
        );
    }

    /**
     * Logout user and invalidate session.
     */
    public void logout(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            
            // Invalidate all sessions for user
            entityManager.createQuery(
                    "UPDATE UserSession s SET s.sessionStatus = 'LOGGED_OUT' " +
                    "WHERE s.userAccount.username = :username AND s.sessionStatus = 'ACTIVE'")
                    .setParameter("username", username)
                    .executeUpdate();

            logger.info("Logout successful for user: {}", username);
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
        
        if (user == null || !"ACTIVE".equals(user.getAccountStatus())) {
            throw new AuthenticationException("User not found or inactive");
        }

        List<String> roles = user.getUserRoles().stream()
                .map(uar -> uar.getUserRole().getRoleCode())
                .collect(Collectors.toList());

        String newAccessToken = jwtUtil.generateToken(user.getId(), user.getUsername(), roles);
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                roles,
                user.getMustChangePassword()
        );
    }

    /**
     * Change user password.
     */
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        UserAccount user = entityManager.find(UserAccount.class, userId);
        if (user == null) {
            throw new AuthenticationException("User not found");
        }

        if (!verifyPassword(oldPassword, user.getPasswordHash())) {
            throw new AuthenticationException("Current password is incorrect");
        }

        user.changePassword(hashPassword(newPassword).getBytes());
        entityManager.merge(user);
        
        logger.info("Password changed for user: {}", user.getUsername());
    }

    /**
     * Get current user details.
     */
    public AuthResponse getCurrentUser(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new AuthenticationException("Invalid token");
        }

        Long userId = jwtUtil.extractUserId(token);
        UserAccount user = entityManager.find(UserAccount.class, userId);
        
        if (user == null) {
            throw new AuthenticationException("User not found");
        }

        List<String> roles = user.getUserRoles().stream()
                .map(uar -> uar.getUserRole().getRoleCode())
                .collect(Collectors.toList());

        return new AuthResponse(
                null, null,
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                roles,
                user.getMustChangePassword()
        );
    }

    // ==================== Helper Methods ====================

    private UserAccount findUserByUsername(String username) {
        try {
            return entityManager.createQuery(
                    "SELECT u FROM UserAccount u WHERE u.username = :username AND u.isDeleted = false",
                    UserAccount.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean verifyPassword(String plainPassword, byte[] hashedPassword) {
        try {
            String hash = new String(hashedPassword);
            return BCrypt.checkpw(plainPassword, hash);
        } catch (Exception e) {
            logger.error("Password verification error: {}", e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    private void createUserSession(UserAccount user, String token, String ipAddress, String userAgent) {
        UserSession session = new UserSession();
        session.setUserAccount(user);
        session.setSessionToken(token);
        session.setLoginTime(LocalDateTime.now());
        session.setLastActivityTime(LocalDateTime.now());
        session.setExpiryTime(LocalDateTime.now().plusHours(24));
        session.setIpAddress(ipAddress);
        session.setUserAgent(userAgent);
        session.setSessionStatus("ACTIVE");
        entityManager.persist(session);
    }

    private void recordLoginHistory(UserAccount user, String ipAddress, String userAgent, 
                                    String status, String failureReason) {
        UserLoginHistory history = new UserLoginHistory();
        history.setUserAccount(user);
        history.setLoginTime(LocalDateTime.now());
        history.setIpAddress(ipAddress);
        history.setUserAgent(userAgent);
        history.setLoginStatus(status);
        history.setFailureReason(failureReason);
        
        if (user != null) {
            entityManager.persist(history);
        }
    }
}
