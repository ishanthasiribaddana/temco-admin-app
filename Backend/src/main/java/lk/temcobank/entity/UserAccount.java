package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * UserAccount entity for authentication and authorization.
 */
@Entity
@Table(name = "user_account", indexes = {
    @Index(name = "idx_user_username", columnList = "username"),
    @Index(name = "idx_user_email", columnList = "email"),
    @Index(name = "idx_user_status", columnList = "account_status"),
    @Index(name = "idx_user_active", columnList = "is_active, is_deleted")
})
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", 
                query = "SELECT u FROM UserAccount u WHERE u.isDeleted = false ORDER BY u.username"),
    @NamedQuery(name = "UserAccount.findByUsername", 
                query = "SELECT u FROM UserAccount u WHERE u.username = :username AND u.isDeleted = false"),
    @NamedQuery(name = "UserAccount.findByEmail", 
                query = "SELECT u FROM UserAccount u WHERE u.email = :email AND u.isDeleted = false"),
    @NamedQuery(name = "UserAccount.findActive", 
                query = "SELECT u FROM UserAccount u WHERE u.accountStatus = 'ACTIVE' AND u.isDeleted = false")
})
public class UserAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(max = 100)
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @NotBlank(message = "Password is required")
    @Column(name = "password_hash", nullable = false, length = 500)
    private byte[] passwordHash;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255)
    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @NotBlank(message = "First name is required")
    @Size(max = 255)
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 255)
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Size(max = 45)
    @Column(name = "mobile_no", length = 45)
    private String mobileNo;

    @Size(max = 50)
    @Column(name = "account_status", length = 50)
    private String accountStatus = "ACTIVE"; // ACTIVE, INACTIVE, LOCKED, SUSPENDED

    @Column(name = "must_change_password")
    private Boolean mustChangePassword = true;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts = 0;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_user_profile_id")
    private GeneralUserProfile generalUserProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id")
    private Cashier cashier;

    // ==================== Relationships ====================

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAccountRole> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "userAccount")
    private List<UserSession> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "userAccount")
    private List<UserLoginHistory> loginHistory = new ArrayList<>();

    // ==================== Constructors ====================

    public UserAccount() {}

    public UserAccount(String username, byte[] passwordHash, String email, String firstName, String lastName) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountStatus = "ACTIVE";
        this.mustChangePassword = true;
    }

    // ==================== Business Logic ====================

    public void recordSuccessfulLogin() {
        this.lastLoginAt = LocalDateTime.now();
        this.failedLoginAttempts = 0;
        this.lockedUntil = null;
    }

    public void recordFailedLogin() {
        this.failedLoginAttempts = (this.failedLoginAttempts != null ? this.failedLoginAttempts : 0) + 1;
        if (this.failedLoginAttempts >= 5) {
            this.accountStatus = "LOCKED";
            this.lockedUntil = LocalDateTime.now().plusMinutes(30);
        }
    }

    public boolean isLocked() {
        if (!"LOCKED".equals(this.accountStatus)) {
            return false;
        }
        if (this.lockedUntil != null && LocalDateTime.now().isAfter(this.lockedUntil)) {
            this.accountStatus = "ACTIVE";
            this.failedLoginAttempts = 0;
            this.lockedUntil = null;
            return false;
        }
        return true;
    }

    public void changePassword(byte[] newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.passwordChangedAt = LocalDateTime.now();
        this.mustChangePassword = false;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Boolean getMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(Boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    public LocalDateTime getPasswordChangedAt() {
        return passwordChangedAt;
    }

    public void setPasswordChangedAt(LocalDateTime passwordChangedAt) {
        this.passwordChangedAt = passwordChangedAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public LocalDateTime getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public GeneralUserProfile getGeneralUserProfile() {
        return generalUserProfile;
    }

    public void setGeneralUserProfile(GeneralUserProfile generalUserProfile) {
        this.generalUserProfile = generalUserProfile;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public List<UserAccountRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserAccountRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<UserSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<UserSession> sessions) {
        this.sessions = sessions;
    }

    public List<UserLoginHistory> getLoginHistory() {
        return loginHistory;
    }

    public void setLoginHistory(List<UserLoginHistory> loginHistory) {
        this.loginHistory = loginHistory;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                '}';
    }
}
