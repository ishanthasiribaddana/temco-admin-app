package lk.temcobank.scheduler;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.List;
import java.util.Properties;

/**
 * Scheduled job for processing email queue.
 * Runs every 5 minutes.
 */
@Singleton
@Startup
public class EmailQueueProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EmailQueueProcessor.class);

    @PersistenceContext
    private EntityManager entityManager;

    // Email configuration from environment
    private static final String SMTP_HOST = System.getenv("SMTP_HOST") != null ? System.getenv("SMTP_HOST") : "smtp.gmail.com";
    private static final String SMTP_PORT = System.getenv("SMTP_PORT") != null ? System.getenv("SMTP_PORT") : "587";
    private static final String SMTP_USER = System.getenv("SMTP_USER");
    private static final String SMTP_PASSWORD = System.getenv("SMTP_PASSWORD");

    /**
     * Process pending emails in queue.
     * Runs every 5 minutes.
     */
    @Schedule(hour = "*", minute = "*/5", second = "0", persistent = false)
    public void processEmailQueue() {
        if (SMTP_USER == null || SMTP_PASSWORD == null) {
            logger.debug("Email configuration not set, skipping email queue processing");
            return;
        }

        logger.info("Starting email queue processing...");

        try {
            // Find pending emails
            List<Object[]> pendingEmails = entityManager.createNativeQuery(
                    "SELECT id, recipient_email, subject, body_html, retry_count " +
                    "FROM email_queue " +
                    "WHERE queue_status = 'PENDING' AND retry_count < 3 " +
                    "ORDER BY created_at ASC LIMIT 50")
                    .getResultList();

            int successCount = 0;
            int failCount = 0;

            for (Object[] email : pendingEmails) {
                Long id = ((Number) email[0]).longValue();
                String recipient = (String) email[1];
                String subject = (String) email[2];
                String body = (String) email[3];
                int retryCount = ((Number) email[4]).intValue();

                try {
                    sendEmail(recipient, subject, body);
                    markEmailSent(id);
                    successCount++;
                } catch (Exception e) {
                    logger.error("Failed to send email ID {}: {}", id, e.getMessage());
                    markEmailFailed(id, retryCount + 1, e.getMessage());
                    failCount++;
                }
            }

            if (successCount > 0 || failCount > 0) {
                logger.info("Email queue processing completed. Success: {}, Failed: {}", successCount, failCount);
            }

        } catch (Exception e) {
            logger.error("Email queue processing failed: {}", e.getMessage(), e);
        }
    }

    /**
     * Send email via SMTP.
     */
    private void sendEmail(String recipient, String subject, String bodyHtml) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USER, "TEMCO Bank"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setContent(bodyHtml, "text/html; charset=utf-8");

        Transport.send(message);
        logger.debug("Email sent to: {}", recipient);
    }

    private void markEmailSent(Long id) {
        entityManager.createNativeQuery(
                "UPDATE email_queue SET queue_status = 'SENT', sent_at = NOW() WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    private void markEmailFailed(Long id, int retryCount, String errorMessage) {
        String status = retryCount >= 3 ? "FAILED" : "PENDING";
        entityManager.createNativeQuery(
                "UPDATE email_queue SET queue_status = :status, retry_count = :retryCount, " +
                "error_message = :error WHERE id = :id")
                .setParameter("status", status)
                .setParameter("retryCount", retryCount)
                .setParameter("error", errorMessage)
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Queue an email for sending.
     */
    public void queueEmail(String recipient, String subject, String bodyHtml) {
        entityManager.createNativeQuery(
                "INSERT INTO email_queue (recipient_email, subject, body_html, queue_status, retry_count, created_at) " +
                "VALUES (:recipient, :subject, :body, 'PENDING', 0, NOW())")
                .setParameter("recipient", recipient)
                .setParameter("subject", subject)
                .setParameter("body", bodyHtml)
                .executeUpdate();
    }
}
