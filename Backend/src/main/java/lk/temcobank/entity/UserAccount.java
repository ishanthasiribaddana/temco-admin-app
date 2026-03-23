package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * UserAccount entity mapped to the existing user_login table.
 * 
 * user_login is the junction/associative table between general_user_profile
 * and user_role, resolving the many-to-many relationship.
 * One person can have multiple logins (one per role/app).
 */
@Entity
@Table(name = "user_login")
@NamedQueries({
    @NamedQuery(name = "UserAccount.findByUsername", 
                query = "SELECT u FROM UserAccount u WHERE u.username = :username AND u.isActive = true"),
    @NamedQuery(name = "UserAccount.findByProfileAndRole",
                query = "SELECT u FROM UserAccount u WHERE u.generalUserProfile.id = :profileId AND u.userRole.id = :roleId")
})
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username is required")
    @Size(max = 255)
    @Column(name = "username", length = 255)
    private String username;

    @Size(max = 255)
    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "is_active")
    private Short isActive = 1;

    @Column(name = "max_login_attempt")
    private Integer maxLoginAttempt;

    @Column(name = "count_attempt")
    private Integer countAttempt = 0;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "general_user_profile_id")
    private GeneralUserProfile generalUserProfile;

    // ==================== Business Logic ====================

    public void recordSuccessfulLogin() {
        this.lastLoginAt = LocalDateTime.now();
        this.countAttempt = 0;
        this.updatedAt = LocalDateTime.now();
    }

    public void recordFailedLogin() {
        this.countAttempt = (this.countAttempt != null ? this.countAttempt : 0) + 1;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isLocked() {
        if (this.maxLoginAttempt == null || this.maxLoginAttempt == 0) {
            return false;
        }
        return this.countAttempt != null && this.countAttempt >= this.maxLoginAttempt;
    }

    public String getFullName() {
        if (generalUserProfile != null) {
            return generalUserProfile.getFullName();
        }
        return username;
    }

    public String getEmail() {
        if (generalUserProfile != null) {
            return generalUserProfile.getEmail();
        }
        return null;
    }

    public String getRoleName() {
        if (userRole != null) {
            return userRole.getName();
        }
        return null;
    }

    // ==================== Getters and Setters ====================

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Short getIsActive() { return isActive; }
    public void setIsActive(Short isActive) { this.isActive = isActive; }
    public Integer getMaxLoginAttempt() { return maxLoginAttempt; }
    public void setMaxLoginAttempt(Integer maxLoginAttempt) { this.maxLoginAttempt = maxLoginAttempt; }
    public Integer getCountAttempt() { return countAttempt; }
    public void setCountAttempt(Integer countAttempt) { this.countAttempt = countAttempt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    public UserRole getUserRole() { return userRole; }
    public void setUserRole(UserRole userRole) { this.userRole = userRole; }
    public GeneralUserProfile getGeneralUserProfile() { return generalUserProfile; }
    public void setGeneralUserProfile(GeneralUserProfile generalUserProfile) { this.generalUserProfile = generalUserProfile; }

    @Override
    public String toString() {
        return "UserAccount{id=" + id + ", username='" + username + "', role=" + getRoleName() + "}";
    }
}
