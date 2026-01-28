package lk.temcobank.dto;

import java.time.LocalDateTime;

public class ActivityLogDTO {
    private Long id;
    private LocalDateTime timestamp;
    private String username;
    private String action;
    private String ipAddress;
    private String details;
    private String status;

    public ActivityLogDTO() {}

    public ActivityLogDTO(Long id, LocalDateTime timestamp, String username, String action, 
                          String ipAddress, String details, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.username = username;
        this.action = action;
        this.ipAddress = ipAddress;
        this.details = details;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
