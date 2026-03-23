package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contact_messages", indexes = {
    @Index(name = "idx_contact_msg_user", columnList = "user_profile_id"),
    @Index(name = "idx_contact_msg_read", columnList = "is_read")
})
@NamedQueries({
    @NamedQuery(name = "ContactMessage.findAll",
        query = "SELECT c FROM ContactMessage c WHERE c.isDeleted = false ORDER BY c.createdAt DESC"),
    @NamedQuery(name = "ContactMessage.findUnread",
        query = "SELECT c FROM ContactMessage c WHERE c.isRead = false AND c.isDeleted = false ORDER BY c.createdAt DESC"),
    @NamedQuery(name = "ContactMessage.findByUser",
        query = "SELECT c FROM ContactMessage c WHERE c.userProfile.id = :userId AND c.isDeleted = false ORDER BY c.createdAt DESC"),
    @NamedQuery(name = "ContactMessage.countUnread",
        query = "SELECT COUNT(c) FROM ContactMessage c WHERE c.isRead = false AND c.isDeleted = false")
})
public class ContactMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private GeneralUserProfile userProfile;

    @NotBlank
    @Size(max = 200)
    @Column(name = "subject", nullable = false, length = 200)
    private String subject;

    @NotBlank
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    // ==================== Getters and Setters ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public GeneralUserProfile getUserProfile() { return userProfile; }
    public void setUserProfile(GeneralUserProfile userProfile) { this.userProfile = userProfile; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
}
