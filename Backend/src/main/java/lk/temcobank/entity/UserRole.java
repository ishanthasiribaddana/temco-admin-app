package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_role")
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT r FROM UserRole r WHERE r.isDeleted = false ORDER BY r.roleName"),
    @NamedQuery(name = "UserRole.findByCode", query = "SELECT r FROM UserRole r WHERE r.roleCode = :code AND r.isDeleted = false")
})
public class UserRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "role_code", unique = true, nullable = false, length = 50)
    private String roleCode;

    @NotBlank
    @Size(max = 100)
    @Column(name = "role_name", nullable = false, length = 100)
    private String roleName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL)
    private List<UserRolePermission> rolePermissions = new ArrayList<>();

    @OneToMany(mappedBy = "userRole")
    private List<UserAccountRole> userAccountRoles = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<UserRolePermission> getRolePermissions() { return rolePermissions; }
    public void setRolePermissions(List<UserRolePermission> rolePermissions) { this.rolePermissions = rolePermissions; }
    public List<UserAccountRole> getUserAccountRoles() { return userAccountRoles; }
    public void setUserAccountRoles(List<UserAccountRole> userAccountRoles) { this.userAccountRoles = userAccountRoles; }
}
