package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user_role_permission", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_role_id", "user_permission_id"})
})
public class UserRolePermission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole userRole;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_permission_id", nullable = false)
    private UserPermission userPermission;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserRole getUserRole() { return userRole; }
    public void setUserRole(UserRole userRole) { this.userRole = userRole; }
    public UserPermission getUserPermission() { return userPermission; }
    public void setUserPermission(UserPermission userPermission) { this.userPermission = userPermission; }
}
