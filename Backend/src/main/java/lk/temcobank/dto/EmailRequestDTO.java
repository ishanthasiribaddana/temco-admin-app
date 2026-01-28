package lk.temcobank.dto;

import java.util.List;

public class EmailRequestDTO {
    private List<Integer> memberIds;
    private String subject;
    private String body;
    private String templateId;
    private boolean sendToAll;

    public EmailRequestDTO() {}

    public List<Integer> getMemberIds() { return memberIds; }
    public void setMemberIds(List<Integer> memberIds) { this.memberIds = memberIds; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getTemplateId() { return templateId; }
    public void setTemplateId(String templateId) { this.templateId = templateId; }

    public boolean isSendToAll() { return sendToAll; }
    public void setSendToAll(boolean sendToAll) { this.sendToAll = sendToAll; }
}
