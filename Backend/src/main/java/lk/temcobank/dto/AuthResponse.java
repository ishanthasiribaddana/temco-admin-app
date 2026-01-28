package lk.temcobank.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Authentication response DTO.
 */
public class AuthResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String username;
    private String fullName;
    private String email;
    private List<String> roles;
    private Boolean mustChangePassword;

    public AuthResponse() {}

    public AuthResponse(String accessToken, String refreshToken, Long userId, String username,
                        String fullName, String email, List<String> roles, Boolean mustChangePassword) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.roles = roles;
        this.mustChangePassword = mustChangePassword;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    public Boolean getMustChangePassword() { return mustChangePassword; }
    public void setMustChangePassword(Boolean mustChangePassword) { this.mustChangePassword = mustChangePassword; }
}
