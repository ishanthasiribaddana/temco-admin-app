package lk.temcobank.dto;

import java.time.LocalDateTime;

public class ContactMessageDTO {

    private Long id;
    private Long userProfileId;
    private String senderName;
    private String senderEmail;
    private String senderPhone;
    private String senderNic;
    private String subject;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;

    // ==================== Constructors ====================

    public ContactMessageDTO() {}

    public ContactMessageDTO(Long id, Long userProfileId, String senderName, String senderEmail,
                             String senderPhone, String senderNic, String subject, String message,
                             Boolean isRead, LocalDateTime createdAt) {
        this.id = id;
        this.userProfileId = userProfileId;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.senderPhone = senderPhone;
        this.senderNic = senderNic;
        this.subject = subject;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    // ==================== Getters and Setters ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserProfileId() { return userProfileId; }
    public void setUserProfileId(Long userProfileId) { this.userProfileId = userProfileId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public String getSenderPhone() { return senderPhone; }
    public void setSenderPhone(String senderPhone) { this.senderPhone = senderPhone; }

    public String getSenderNic() { return senderNic; }
    public void setSenderNic(String senderNic) { this.senderNic = senderNic; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ==================== Request DTO (for POST) ====================

    public static class CreateRequest {
        private String subject;
        private String message;

        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
