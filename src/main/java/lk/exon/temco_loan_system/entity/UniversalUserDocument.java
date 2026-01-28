/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "universal_user_document")
@NamedQueries({
    @NamedQuery(name = "UniversalUserDocument.findAll", query = "SELECT u FROM UniversalUserDocument u"),
    @NamedQuery(name = "UniversalUserDocument.findById", query = "SELECT u FROM UniversalUserDocument u WHERE u.id = :id"),
    @NamedQuery(name = "UniversalUserDocument.findByCreatedTimestamp", query = "SELECT u FROM UniversalUserDocument u WHERE u.createdTimestamp = :createdTimestamp"),
    @NamedQuery(name = "UniversalUserDocument.findByIsVerified", query = "SELECT u FROM UniversalUserDocument u WHERE u.isVerified = :isVerified"),
    @NamedQuery(name = "UniversalUserDocument.findByIsActive", query = "SELECT u FROM UniversalUserDocument u WHERE u.isActive = :isActive")})
public class UniversalUserDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTimestamp;
    @Column(name = "is_verified")
    private Short isVerified;
    @Lob
    @Column(name = "file_url")
    private String fileUrl;
    @Column(name = "is_active")
    private Short isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universalUserDocumentId")
    private Collection<LoanDocumentStatusManager> loanDocumentStatusManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universalUserDocumentId")
    private Collection<SubmittedUserDocument> submittedUserDocumentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDocumentId")
    private Collection<UploadedDocumentFilePath> uploadedDocumentFilePathCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDocumentId")
    private Collection<TemporaryDataForReconsilation> temporaryDataForReconsilationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universalUserDocumentId")
    private Collection<DocumentFieldsManager> documentFieldsManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universalUserDocumentId")
    private Collection<Table1> table1Collection;
    @JoinColumn(name = "general_user_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeneralUserProfile generalUserProfileId;
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MemberDocuments documentTypeId;
    @JoinColumn(name = "user_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserType userTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universalUserDocumentId")
    private Collection<JournalEntryProof> journalEntryProofCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universalUserDocumentId")
    private Collection<VoucherDocumentMap> voucherDocumentMapCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universalUserDocumentId")
    private Collection<DocumentVerification> documentVerificationCollection;

    public UniversalUserDocument() {
    }

    public UniversalUserDocument(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Short getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Short isVerified) {
        this.isVerified = isVerified;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Collection<LoanDocumentStatusManager> getLoanDocumentStatusManagerCollection() {
        return loanDocumentStatusManagerCollection;
    }

    public void setLoanDocumentStatusManagerCollection(Collection<LoanDocumentStatusManager> loanDocumentStatusManagerCollection) {
        this.loanDocumentStatusManagerCollection = loanDocumentStatusManagerCollection;
    }

    public Collection<SubmittedUserDocument> getSubmittedUserDocumentCollection() {
        return submittedUserDocumentCollection;
    }

    public void setSubmittedUserDocumentCollection(Collection<SubmittedUserDocument> submittedUserDocumentCollection) {
        this.submittedUserDocumentCollection = submittedUserDocumentCollection;
    }

    public Collection<UploadedDocumentFilePath> getUploadedDocumentFilePathCollection() {
        return uploadedDocumentFilePathCollection;
    }

    public void setUploadedDocumentFilePathCollection(Collection<UploadedDocumentFilePath> uploadedDocumentFilePathCollection) {
        this.uploadedDocumentFilePathCollection = uploadedDocumentFilePathCollection;
    }

    public Collection<TemporaryDataForReconsilation> getTemporaryDataForReconsilationCollection() {
        return temporaryDataForReconsilationCollection;
    }

    public void setTemporaryDataForReconsilationCollection(Collection<TemporaryDataForReconsilation> temporaryDataForReconsilationCollection) {
        this.temporaryDataForReconsilationCollection = temporaryDataForReconsilationCollection;
    }

    public Collection<DocumentFieldsManager> getDocumentFieldsManagerCollection() {
        return documentFieldsManagerCollection;
    }

    public void setDocumentFieldsManagerCollection(Collection<DocumentFieldsManager> documentFieldsManagerCollection) {
        this.documentFieldsManagerCollection = documentFieldsManagerCollection;
    }

    public Collection<Table1> getTable1Collection() {
        return table1Collection;
    }

    public void setTable1Collection(Collection<Table1> table1Collection) {
        this.table1Collection = table1Collection;
    }

    public GeneralUserProfile getGeneralUserProfileId() {
        return generalUserProfileId;
    }

    public void setGeneralUserProfileId(GeneralUserProfile generalUserProfileId) {
        this.generalUserProfileId = generalUserProfileId;
    }

    public MemberDocuments getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(MemberDocuments documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public UserType getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(UserType userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Collection<JournalEntryProof> getJournalEntryProofCollection() {
        return journalEntryProofCollection;
    }

    public void setJournalEntryProofCollection(Collection<JournalEntryProof> journalEntryProofCollection) {
        this.journalEntryProofCollection = journalEntryProofCollection;
    }

    public Collection<VoucherDocumentMap> getVoucherDocumentMapCollection() {
        return voucherDocumentMapCollection;
    }

    public void setVoucherDocumentMapCollection(Collection<VoucherDocumentMap> voucherDocumentMapCollection) {
        this.voucherDocumentMapCollection = voucherDocumentMapCollection;
    }

    public Collection<DocumentVerification> getDocumentVerificationCollection() {
        return documentVerificationCollection;
    }

    public void setDocumentVerificationCollection(Collection<DocumentVerification> documentVerificationCollection) {
        this.documentVerificationCollection = documentVerificationCollection;
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
        if (!(object instanceof UniversalUserDocument)) {
            return false;
        }
        UniversalUserDocument other = (UniversalUserDocument) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.UniversalUserDocument[ id=" + id + " ]";
    }
    
}
