package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_permission")
@NamedQueries({
    @NamedQuery(name = "UserPermission.findAll", query = "SELECT p FROM UserPermission p WHERE p.isDeleted = false ORDER BY p.module, p.permissionName")
})
public class UserPermission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "permission_code", unique = true, nullable = false, length = 100)
    private String permissionCode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "permission_name", nullable = false, length = 255)
    private String permissionName;

    @Size(max = 100)
    @Column(name = "module", length = 100)
    private String module;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPermissionCode() { return permissionCode; }
    public void setPermissionCode(String permissionCode) { this.permissionCode = permissionCode; }
    public String getPermissionName() { return permissionName; }
    public void setPermissionName(String permissionName) { this.permissionName = permissionName; }
    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
