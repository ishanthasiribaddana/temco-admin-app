/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "api_transaction_log")
@NamedQueries({
    @NamedQuery(name = "ApiTransactionLog.findAll", query = "SELECT a FROM ApiTransactionLog a"),
    @NamedQuery(name = "ApiTransactionLog.findById", query = "SELECT a FROM ApiTransactionLog a WHERE a.id = :id"),
    @NamedQuery(name = "ApiTransactionLog.findByApiSource", query = "SELECT a FROM ApiTransactionLog a WHERE a.apiSource = :apiSource"),
    @NamedQuery(name = "ApiTransactionLog.findByTransactionType", query = "SELECT a FROM ApiTransactionLog a WHERE a.transactionType = :transactionType"),
    @NamedQuery(name = "ApiTransactionLog.findByTransactionReference", query = "SELECT a FROM ApiTransactionLog a WHERE a.transactionReference = :transactionReference"),
    @NamedQuery(name = "ApiTransactionLog.findByStudentNic", query = "SELECT a FROM ApiTransactionLog a WHERE a.studentNic = :studentNic"),
    @NamedQuery(name = "ApiTransactionLog.findByStudentEmail", query = "SELECT a FROM ApiTransactionLog a WHERE a.studentEmail = :studentEmail"),
    @NamedQuery(name = "ApiTransactionLog.findByVerificationToken", query = "SELECT a FROM ApiTransactionLog a WHERE a.verificationToken = :verificationToken"),
    @NamedQuery(name = "ApiTransactionLog.findByStatus", query = "SELECT a FROM ApiTransactionLog a WHERE a.status = :status"),
    @NamedQuery(name = "ApiTransactionLog.findByErrorCode", query = "SELECT a FROM ApiTransactionLog a WHERE a.errorCode = :errorCode"),
    @NamedQuery(name = "ApiTransactionLog.findByMaterializedTableId", query = "SELECT a FROM ApiTransactionLog a WHERE a.materializedTableId = :materializedTableId"),
    @NamedQuery(name = "ApiTransactionLog.findByLoanCustomerId", query = "SELECT a FROM ApiTransactionLog a WHERE a.loanCustomerId = :loanCustomerId"),
    @NamedQuery(name = "ApiTransactionLog.findByGeneralUserProfileId", query = "SELECT a FROM ApiTransactionLog a WHERE a.generalUserProfileId = :generalUserProfileId"),
    @NamedQuery(name = "ApiTransactionLog.findByMemberId", query = "SELECT a FROM ApiTransactionLog a WHERE a.memberId = :memberId"),
    @NamedQuery(name = "ApiTransactionLog.findByReceivedAt", query = "SELECT a FROM ApiTransactionLog a WHERE a.receivedAt = :receivedAt"),
    @NamedQuery(name = "ApiTransactionLog.findByProcessingStartedAt", query = "SELECT a FROM ApiTransactionLog a WHERE a.processingStartedAt = :processingStartedAt"),
    @NamedQuery(name = "ApiTransactionLog.findByProcessingCompletedAt", query = "SELECT a FROM ApiTransactionLog a WHERE a.processingCompletedAt = :processingCompletedAt"),
    @NamedQuery(name = "ApiTransactionLog.findByProcessingDurationMs", query = "SELECT a FROM ApiTransactionLog a WHERE a.processingDurationMs = :processingDurationMs"),
    @NamedQuery(name = "ApiTransactionLog.findByRetryCount", query = "SELECT a FROM ApiTransactionLog a WHERE a.retryCount = :retryCount"),
    @NamedQuery(name = "ApiTransactionLog.findByLastRetryAt", query = "SELECT a FROM ApiTransactionLog a WHERE a.lastRetryAt = :lastRetryAt"),
    @NamedQuery(name = "ApiTransactionLog.findByMaxRetries", query = "SELECT a FROM ApiTransactionLog a WHERE a.maxRetries = :maxRetries"),
    @NamedQuery(name = "ApiTransactionLog.findByServerName", query = "SELECT a FROM ApiTransactionLog a WHERE a.serverName = :serverName"),
    @NamedQuery(name = "ApiTransactionLog.findByIpAddress", query = "SELECT a FROM ApiTransactionLog a WHERE a.ipAddress = :ipAddress")})
public class ApiTransactionLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "api_source")
    private String apiSource;
    @Basic(optional = false)
    @Column(name = "transaction_type")
    private String transactionType;
    @Column(name = "transaction_reference")
    private String transactionReference;
    @Lob
    @Column(name = "request_payload")
    private String requestPayload;
    @Column(name = "student_nic")
    private String studentNic;
    @Column(name = "student_email")
    private String studentEmail;
    @Column(name = "verification_token")
    private String verificationToken;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Lob
    @Column(name = "result_message")
    private String resultMessage;
    @Lob
    @Column(name = "error_details")
    private String errorDetails;
    @Column(name = "error_code")
    private String errorCode;
    @Lob
    @Column(name = "validation_errors")
    private String validationErrors;
    @Column(name = "materialized_table_id")
    private Integer materializedTableId;
    @Column(name = "loan_customer_id")
    private Integer loanCustomerId;
    @Column(name = "general_user_profile_id")
    private Integer generalUserProfileId;
    @Column(name = "member_id")
    private Integer memberId;
    @Lob
    @Column(name = "student_due_ids")
    private String studentDueIds;
    @Column(name = "received_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedAt;
    @Column(name = "processing_started_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processingStartedAt;
    @Column(name = "processing_completed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processingCompletedAt;
    @Column(name = "processing_duration_ms")
    private Integer processingDurationMs;
    @Column(name = "retry_count")
    private Integer retryCount;
    @Column(name = "last_retry_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRetryAt;
    @Column(name = "max_retries")
    private Integer maxRetries;
    @Column(name = "server_name")
    private String serverName;
    @Column(name = "ip_address")
    private String ipAddress;
    @Lob
    @Column(name = "user_agent")
    private String userAgent;

    public ApiTransactionLog() {
    }

    public ApiTransactionLog(Long id) {
        this.id = id;
    }

    public ApiTransactionLog(Long id, String apiSource, String transactionType, String status) {
        this.id = id;
        this.apiSource = apiSource;
        this.transactionType = transactionType;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiSource() {
        return apiSource;
    }

    public void setApiSource(String apiSource) {
        this.apiSource = apiSource;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public String getStudentNic() {
        return studentNic;
    }

    public void setStudentNic(String studentNic) {
        this.studentNic = studentNic;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(String validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Integer getMaterializedTableId() {
        return materializedTableId;
    }

    public void setMaterializedTableId(Integer materializedTableId) {
        this.materializedTableId = materializedTableId;
    }

    public Integer getLoanCustomerId() {
        return loanCustomerId;
    }

    public void setLoanCustomerId(Integer loanCustomerId) {
        this.loanCustomerId = loanCustomerId;
    }

    public Integer getGeneralUserProfileId() {
        return generalUserProfileId;
    }

    public void setGeneralUserProfileId(Integer generalUserProfileId) {
        this.generalUserProfileId = generalUserProfileId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getStudentDueIds() {
        return studentDueIds;
    }

    public void setStudentDueIds(String studentDueIds) {
        this.studentDueIds = studentDueIds;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Date getProcessingStartedAt() {
        return processingStartedAt;
    }

    public void setProcessingStartedAt(Date processingStartedAt) {
        this.processingStartedAt = processingStartedAt;
    }

    public Date getProcessingCompletedAt() {
        return processingCompletedAt;
    }

    public void setProcessingCompletedAt(Date processingCompletedAt) {
        this.processingCompletedAt = processingCompletedAt;
    }

    public Integer getProcessingDurationMs() {
        return processingDurationMs;
    }

    public void setProcessingDurationMs(Integer processingDurationMs) {
        this.processingDurationMs = processingDurationMs;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Date getLastRetryAt() {
        return lastRetryAt;
    }

    public void setLastRetryAt(Date lastRetryAt) {
        this.lastRetryAt = lastRetryAt;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApiTransactionLog)) {
            return false;
        }
        ApiTransactionLog other = (ApiTransactionLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.ApiTransactionLog[ id=" + id + " ]";
    }
    
}
