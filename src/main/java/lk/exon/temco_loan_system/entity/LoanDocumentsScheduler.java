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
@Table(name = "loan_documents_scheduler")
@NamedQueries({
    @NamedQuery(name = "LoanDocumentsScheduler.findAll", query = "SELECT l FROM LoanDocumentsScheduler l"),
    @NamedQuery(name = "LoanDocumentsScheduler.findById", query = "SELECT l FROM LoanDocumentsScheduler l WHERE l.id = :id"),
    @NamedQuery(name = "LoanDocumentsScheduler.findBySubmissionDate", query = "SELECT l FROM LoanDocumentsScheduler l WHERE l.submissionDate = :submissionDate"),
    @NamedQuery(name = "LoanDocumentsScheduler.findByIsSubmitted", query = "SELECT l FROM LoanDocumentsScheduler l WHERE l.isSubmitted = :isSubmitted")})
public class LoanDocumentsScheduler implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "submission_date")
    @Temporal(TemporalType.DATE)
    private Date submissionDate;
    @Column(name = "is_submitted")
    private Short isSubmitted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanDocumentsSchedulerId")
    private Collection<LoanDocumentStatusManager> loanDocumentStatusManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanDocumentsSchedulerId")
    private Collection<WeeksScheduler> weeksSchedulerCollection;
    @JoinColumn(name = "document_creator_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CreatedDocument documentCreatorId;
    @JoinColumn(name = "loan_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanManager loanManagerId;
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Member1 memberId;

    public LoanDocumentsScheduler() {
    }

    public LoanDocumentsScheduler(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Short getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Short isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    public Collection<LoanDocumentStatusManager> getLoanDocumentStatusManagerCollection() {
        return loanDocumentStatusManagerCollection;
    }

    public void setLoanDocumentStatusManagerCollection(Collection<LoanDocumentStatusManager> loanDocumentStatusManagerCollection) {
        this.loanDocumentStatusManagerCollection = loanDocumentStatusManagerCollection;
    }

    public Collection<WeeksScheduler> getWeeksSchedulerCollection() {
        return weeksSchedulerCollection;
    }

    public void setWeeksSchedulerCollection(Collection<WeeksScheduler> weeksSchedulerCollection) {
        this.weeksSchedulerCollection = weeksSchedulerCollection;
    }

    public CreatedDocument getDocumentCreatorId() {
        return documentCreatorId;
    }

    public void setDocumentCreatorId(CreatedDocument documentCreatorId) {
        this.documentCreatorId = documentCreatorId;
    }

    public LoanManager getLoanManagerId() {
        return loanManagerId;
    }

    public void setLoanManagerId(LoanManager loanManagerId) {
        this.loanManagerId = loanManagerId;
    }

    public Member1 getMemberId() {
        return memberId;
    }

    public void setMemberId(Member1 memberId) {
        this.memberId = memberId;
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
        if (!(object instanceof LoanDocumentsScheduler)) {
            return false;
        }
        LoanDocumentsScheduler other = (LoanDocumentsScheduler) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanDocumentsScheduler[ id=" + id + " ]";
    }
    
}
