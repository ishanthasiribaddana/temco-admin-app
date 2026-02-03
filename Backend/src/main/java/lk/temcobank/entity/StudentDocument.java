package lk.temcobank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_document")
public class StudentDocument extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_customer_id")
    private LoanCustomer loanCustomer;

    @Column(name = "document_type", length = 50)
    private String documentType;

    @Column(name = "document_name", length = 255)
    private String documentName;

    @Column(name = "file_path", length = 500)
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "verified_by")
    private Long verifiedBy;

    @Column(name = "verified_date")
    private LocalDateTime verifiedDate;

    @Column(name = "remarks", length = 500)
    private String remarks;

    // Constructors
    public StudentDocument() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LoanCustomer getLoanCustomer() { return loanCustomer; }
    public void setLoanCustomer(LoanCustomer loanCustomer) { this.loanCustomer = loanCustomer; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getDocumentName() { return documentName; }
    public void setDocumentName(String documentName) { this.documentName = documentName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }

    public Boolean getIsVerified() { return isVerified; }
    public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }

    public Long getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(Long verifiedBy) { this.verifiedBy = verifiedBy; }

    public LocalDateTime getVerifiedDate() { return verifiedDate; }
    public void setVerifiedDate(LocalDateTime verifiedDate) { this.verifiedDate = verifiedDate; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
