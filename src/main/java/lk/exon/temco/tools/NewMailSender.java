/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco.tools;

import jakarta.ejb.Stateless;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Thilan Ravindu
 */
@Stateless
public class NewMailSender {

//    @Resource(mappedName = "mail/wwole")
//    private Session session;
    public NewMailSender() {
    }

    public boolean sendMailtrapEmail(String toEmail, String subject, String body) throws Exception {
        try {
            String bcc[] = {"ishantha@gmail.com", "tryabeywardane@gmail.com", "secretary@temcobanklanka.com", "gevindurodrigo2@gmail.com"};

            // Sender's email ID needs to be mentioned
            String senderEmail = "noreply@temcobanklanka.com";
            String replyEmail = "secretary@temcobanklanka.com";

            // Set the sender's and recipient's account information
            final String username = "smtp@mailtrap.io";
            final String password = "8cdf5c7fda61c9a415b38823e98cdc53";

            Properties props = new Properties();
            props.put("mail.smtp.from", senderEmail);
            props.put("mail.smtp.host", "live.smtp.mailtrap.io");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "*");

            // Get the Session object.// and pass username and password
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Used to debug SMTP issues
            session.setDebug(true);

            // Create a default MimeMessage object.
            MimeMessage msg = new MimeMessage(session);

            msg.setHeader("Content-Type", "text/html; charset=UTF-8");
            msg.setReplyTo(InternetAddress.parse(replyEmail));
            msg.setContent(body, "text/html");
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            InternetAddress[] bccAddress = new InternetAddress[bcc.length];

            // To get the array of ccaddresses
            for (int i = 0; i < bcc.length; i++) {
                bccAddress[i] = new InternetAddress(bcc[i]);
            }
            for (InternetAddress bccAddres : bccAddress) {
                msg.addRecipient(Message.RecipientType.BCC, bccAddres);
            }

            msg.setSubject(subject, "UTF-8");
            System.out.println("sending...");
            Transport.send(msg);

            // Send message
            System.out.println("Sent email successfully....");

            return true;
        } catch (Exception mex) {
            mex.printStackTrace();
            return false;
        }
    }

    public boolean sendM(String mailto, String subject, String content) {
//        
        String bcc[] = {"ishantha@gmail.com", "tryabeywardane@gmail.com", "admin@exonsoftware.lk", "secretary@temcobanklanka.com", "gevindurodrigo2@gmail.com"};

        // Sender's email ID needs to be mentioned
        String senderEmail = "noreply@temcobanklanka.com";
        String replyEmail = "secretary@temcobanklanka.com";

        // Set the sender's and recipient's account information
        final String username = "smtp@mailtrap.io";
        final String password = "8cdf5c7fda61c9a415b38823e98cdc53";

        Properties props = new Properties();
        props.put("mail.smtp.from", senderEmail);
        props.put("mail.smtp.host", "live.smtp.mailtrap.io");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "*");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {

            // Create a default MimeMessage object.
            MimeMessage msg = new MimeMessage(session);

            msg.setHeader("Content-Type", "text/html; charset=UTF-8");
            msg.setReplyTo(InternetAddress.parse(replyEmail));
            msg.setContent(content, "text/html");
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailto));

            InternetAddress[] bccAddress = new InternetAddress[bcc.length];

            // To get the array of ccaddresses
            for (int i = 0; i < bcc.length; i++) {
                bccAddress[i] = new InternetAddress(bcc[i]);
            }
            for (int i = 0; i < bccAddress.length; i++) {
                msg.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
            }

            msg.setSubject(subject, "UTF-8");
            System.out.println("sending...");
            Transport.send(msg);

            // Send message
            System.out.println("Sent email successfully....");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disableSslVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
            };

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
