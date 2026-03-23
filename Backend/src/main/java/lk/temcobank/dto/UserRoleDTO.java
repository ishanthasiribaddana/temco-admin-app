package lk.temcobank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for user_role table (id, name).
 */
public class UserRoleDTO {
    
    private Integer id;
    
    @NotBlank(message = "Role name is required")
    @Size(max = 45, message = "Role name must not exceed 45 characters")
    private String name;
    
    private Integer userCount;

    public UserRoleDTO() {}

    public UserRoleDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getUserCount() { return userCount; }
    public void setUserCount(Integer userCount) { this.userCount = userCount; }
}
