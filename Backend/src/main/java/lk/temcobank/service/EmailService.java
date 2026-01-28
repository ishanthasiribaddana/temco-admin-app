package lk.temcobank.service;

import jakarta.ejb.Stateless;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.temcobank.dto.EmailConfigDTO;
import lk.temcobank.dto.MemberDTO;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    @PersistenceContext(unitName = "temcoPU")
    private EntityManager em;

    // Default Mailtrap config (from existing system)
    private static final String DEFAULT_SMTP_HOST = "live.smtp.mailtrap.io";
    private static final int DEFAULT_SMTP_PORT = 587;
    private static final String DEFAULT_USERNAME = "smtp@mailtrap.io";
    private static final String DEFAULT_PASSWORD = "8cdf5c7fda61c9a415b38823e98cdc53";
    private static final String DEFAULT_SENDER = "noreply@temcobanklanka.com";
    private static final String DEFAULT_REPLY_TO = "secretary@temcobanklanka.com";

    public EmailConfigDTO getDefaultConfig() {
        EmailConfigDTO config = new EmailConfigDTO();
        config.setSmtpHost(DEFAULT_SMTP_HOST);
        config.setSmtpPort(DEFAULT_SMTP_PORT);
        config.setUsername(DEFAULT_USERNAME);
        config.setPassword("********"); // Don't expose real password
        config.setSenderEmail(DEFAULT_SENDER);
        config.setSenderName("TEMCO Bank");
        config.setReplyTo(DEFAULT_REPLY_TO);
        config.setUseTls(true);
        config.setUseAuth(true);
        return config;
    }

    public boolean testConnection(EmailConfigDTO config) {
        try {
            Properties props = buildMailProperties(config);
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        config.getUsername() != null ? config.getUsername() : DEFAULT_USERNAME,
                        config.getPassword() != null && !config.getPassword().equals("********") 
                            ? config.getPassword() : DEFAULT_PASSWORD
                    );
                }
            });
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.close();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "SMTP connection test failed", e);
            return false;
        }
    }

    public Map<String, Object> sendEmail(String toEmail, String toName, String subject, String htmlBody) {
        Map<String, Object> result = new HashMap<>();
        try {
            Properties props = buildMailProperties(null);
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(DEFAULT_USERNAME, DEFAULT_PASSWORD);
                }
            });

            MimeMessage msg = new MimeMessage(session);
            msg.setHeader("Content-Type", "text/html; charset=UTF-8");
            msg.setFrom(new InternetAddress(DEFAULT_SENDER, "TEMCO Bank"));
            msg.setReplyTo(InternetAddress.parse(DEFAULT_REPLY_TO));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toName));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(htmlBody, "text/html; charset=UTF-8");

            Transport.send(msg);
            
            result.put("success", true);
            result.put("message", "Email sent successfully to " + toEmail);
            LOGGER.info("Email sent to: " + toEmail);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to send email to " + toEmail, e);
            result.put("success", false);
            result.put("message", "Failed: " + e.getMessage());
        }
        return result;
    }

    public Map<String, Object> sendBulkEmail(List<MemberDTO> members, String subject, String templateBody) {
        Map<String, Object> result = new HashMap<>();
        int successCount = 0;
        int failCount = 0;
        List<String> failures = new ArrayList<>();

        for (MemberDTO member : members) {
            if (member.getEmail() == null || member.getEmail().isEmpty()) {
                failCount++;
                failures.add(member.getFullName() + " (no email)");
                continue;
            }

            String personalizedBody = personalizeTemplate(templateBody, member);
            Map<String, Object> sendResult = sendEmail(
                member.getEmail(),
                member.getFullName(),
                subject,
                personalizedBody
            );

            if ((boolean) sendResult.get("success")) {
                successCount++;
            } else {
                failCount++;
                failures.add(member.getEmail());
            }

            // Rate limiting - wait 100ms between emails
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }

        result.put("success", failCount == 0);
        result.put("successCount", successCount);
        result.put("failCount", failCount);
        result.put("failures", failures);
        result.put("message", String.format("Sent %d emails, %d failed", successCount, failCount));
        return result;
    }

    public List<MemberDTO> getMembersWithEmail(List<Integer> memberIds) {
        List<MemberDTO> members = new ArrayList<>();
        try {
            String sql;
            if (memberIds == null || memberIds.isEmpty()) {
                sql = "SELECT m.id, m.membership_no, g.first_name, g.last_name, g.email, g.nic, m.is_active " +
                      "FROM member m JOIN general_user_profile g ON m.general_user_profile_id = g.id " +
                      "WHERE g.email IS NOT NULL AND g.email != ''";
            } else {
                sql = "SELECT m.id, m.membership_no, g.first_name, g.last_name, g.email, g.nic, m.is_active " +
                      "FROM member m JOIN general_user_profile g ON m.general_user_profile_id = g.id " +
                      "WHERE m.id IN (" + memberIds.toString().replace("[", "").replace("]", "") + ") " +
                      "AND g.email IS NOT NULL AND g.email != ''";
            }

            @SuppressWarnings("unchecked")
            List<Object[]> results = em.createNativeQuery(sql).getResultList();
            
            for (Object[] row : results) {
                MemberDTO dto = new MemberDTO();
                dto.setId(((Number) row[0]).intValue());
                dto.setMembershipNo((String) row[1]);
                dto.setFirstName((String) row[2]);
                dto.setLastName((String) row[3]);
                dto.setEmail((String) row[4]);
                dto.setNic((String) row[5]);
                dto.setIsActive(row[6] != null && ((Number) row[6]).intValue() == 1);
                
                String fullName = "";
                if (dto.getFirstName() != null) fullName += dto.getFirstName();
                if (dto.getLastName() != null) fullName += " " + dto.getLastName();
                dto.setFullName(fullName.trim());
                
                members.add(dto);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching members with email", e);
        }
        return members;
    }

    private Properties buildMailProperties(EmailConfigDTO config) {
        Properties props = new Properties();
        props.put("mail.smtp.host", config != null && config.getSmtpHost() != null ? config.getSmtpHost() : DEFAULT_SMTP_HOST);
        props.put("mail.smtp.port", config != null ? String.valueOf(config.getSmtpPort()) : String.valueOf(DEFAULT_SMTP_PORT));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.from", config != null && config.getSenderEmail() != null ? config.getSenderEmail() : DEFAULT_SENDER);
        return props;
    }

    private String personalizeTemplate(String template, MemberDTO member) {
        return template
            .replace("{{firstName}}", member.getFirstName() != null ? member.getFirstName() : "")
            .replace("{{lastName}}", member.getLastName() != null ? member.getLastName() : "")
            .replace("{{fullName}}", member.getFullName() != null ? member.getFullName() : "Member")
            .replace("{{email}}", member.getEmail() != null ? member.getEmail() : "")
            .replace("{{membershipNo}}", member.getMembershipNo() != null ? member.getMembershipNo() : "")
            .replace("{{nic}}", member.getNic() != null ? member.getNic() : "");
    }
}
