/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "loan_document_status_manager")
@NamedQueries({
    @NamedQuery(name = "LoanDocumentStatusManager.findAll", query = "SELECT l FROM LoanDocumentStatusManager l"),
    @NamedQuery(name = "LoanDocumentStatusManager.findById", query = "SELECT l FROM LoanDocumentStatusManager l WHERE l.id = :id"),
    @NamedQuery(name = "LoanDocumentStatusManager.findByDate", query = "SELECT l FROM LoanDocumentStatusManager l WHERE l.date = :date")})
public class LoanDocumentStatusManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "documents_submission_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentsSubmissionStatus documentsSubmissionStatusId;
    @JoinColumn(name = "loan_documents_scheduler_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanDocumentsScheduler loanDocumentsSchedulerId;
    @JoinColumn(name = "universal_user_document_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UniversalUserDocument universalUserDocumentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanDocumentReviewHistoryId")
    private Collection<LoanDocComment> loanDocCommentCollection;

    public LoanDocumentStatusManager() {
    }

    public LoanDocumentStatusManager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DocumentsSubmissionStatus getDocumentsSubmissionStatusId() {
        return documentsSubmissionStatusId;
    }

    public void setDocumentsSubmissionStatusId(DocumentsSubmissionStatus documentsSubmissionStatusId) {
        this.documentsSubmissionStatusId = documentsSubmissionStatusId;
    }

    public LoanDocumentsScheduler getLoanDocumentsSchedulerId() {
        return loanDocumentsSchedulerId;
    }

    public void setLoanDocumentsSchedulerId(LoanDocumentsScheduler loanDocumentsSchedulerId) {
        this.loanDocumentsSchedulerId = loanDocumentsSchedulerId;
    }

    public UniversalUserDocument getUniversalUserDocumentId() {
        return universalUserDocumentId;
    }

    public void setUniversalUserDocumentId(UniversalUserDocument universalUserDocumentId) {
        this.universalUserDocumentId = universalUserDocumentId;
    }

    public Collection<LoanDocComment> getLoanDocCommentCollection() {
        return loanDocCommentCollection;
    }

    public void setLoanDocCommentCollection(Collection<LoanDocComment> loanDocCommentCollection) {
        this.loanDocCommentCollection = loanDocCommentCollection;
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
        if (!(object instanceof LoanDocumentStatusManager)) {
            return false;
        }
        LoanDocumentStatusManager other = (LoanDocumentStatusManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanDocumentStatusManager[ id=" + id + " ]";
    }
    
}
