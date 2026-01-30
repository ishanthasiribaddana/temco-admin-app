package com.temco.dto;

public class RoleDTO {
    private Integer id;
    private String roleCode;
    private String roleName;
    private String description;
    private Integer userCount;
    private Integer permissionCount;
    private Boolean isActive;

    public RoleDTO() {}

    public RoleDTO(Integer id, String name, String description, Boolean isActive) {
        this.id = id;
        this.roleCode = name != null ? name.toUpperCase().replace(" ", "_") : "";
        this.roleName = name;
        this.description = description;
        this.userCount = 0;
        this.permissionCount = 0;
        this.isActive = isActive;
    }

    public RoleDTO(Integer id, String name) {
        this.id = id;
        this.roleCode = name != null ? name.toUpperCase().replace(" ", "_") : "";
        this.roleName = name;
        this.description = name + " role";
        this.userCount = 0;
        this.permissionCount = 0;
        this.isActive = true;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getUserCount() { return userCount; }
    public void setUserCount(Integer userCount) { this.userCount = userCount; }
    public Integer getPermissionCount() { return permissionCount; }
    public void setPermissionCount(Integer permissionCount) { this.permissionCount = permissionCount; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
