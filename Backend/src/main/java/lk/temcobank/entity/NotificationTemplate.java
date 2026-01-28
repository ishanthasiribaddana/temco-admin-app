package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "notification_template")
@NamedQueries({
    @NamedQuery(name = "NotificationTemplate.findAll", query = "SELECT n FROM NotificationTemplate n WHERE n.isDeleted = false"),
    @NamedQuery(name = "NotificationTemplate.findByCode", query = "SELECT n FROM NotificationTemplate n WHERE n.templateCode = :code AND n.isDeleted = false")
})
public class NotificationTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "template_code", unique = true, nullable = false, length = 100)
    private String templateCode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "template_name", nullable = false, length = 255)
    private String templateName;

    @Size(max = 50)
    @Column(name = "notification_type", length = 50)
    private String notificationType; // EMAIL, SMS, BOTH

    @Size(max = 500)
    @Column(name = "subject", length = 500)
    private String subject;

    @Column(name = "body_template", columnDefinition = "TEXT")
    private String bodyTemplate;

    @Column(name = "sms_template", length = 500)
    private String smsTemplate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBodyTemplate() { return bodyTemplate; }
    public void setBodyTemplate(String bodyTemplate) { this.bodyTemplate = bodyTemplate; }
    public String getSmsTemplate() { return smsTemplate; }
    public void setSmsTemplate(String smsTemplate) { this.smsTemplate = smsTemplate; }
}
