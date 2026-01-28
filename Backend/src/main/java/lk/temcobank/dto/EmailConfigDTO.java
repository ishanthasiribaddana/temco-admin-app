package lk.temcobank.dto;

public class EmailConfigDTO {
    private String smtpHost;
    private int smtpPort;
    private String username;
    private String password;
    private String senderEmail;
    private String senderName;
    private String replyTo;
    private boolean useTls;
    private boolean useAuth;

    public EmailConfigDTO() {}

    public String getSmtpHost() { return smtpHost; }
    public void setSmtpHost(String smtpHost) { this.smtpHost = smtpHost; }

    public int getSmtpPort() { return smtpPort; }
    public void setSmtpPort(int smtpPort) { this.smtpPort = smtpPort; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getReplyTo() { return replyTo; }
    public void setReplyTo(String replyTo) { this.replyTo = replyTo; }

    public boolean isUseTls() { return useTls; }
    public void setUseTls(boolean useTls) { this.useTls = useTls; }

    public boolean isUseAuth() { return useAuth; }
    public void setUseAuth(boolean useAuth) { this.useAuth = useAuth; }
}
