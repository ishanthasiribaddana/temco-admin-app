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
@Table(name = "created_document")
@NamedQueries({
    @NamedQuery(name = "CreatedDocument.findAll", query = "SELECT c FROM CreatedDocument c"),
    @NamedQuery(name = "CreatedDocument.findById", query = "SELECT c FROM CreatedDocument c WHERE c.id = :id"),
    @NamedQuery(name = "CreatedDocument.findByDate", query = "SELECT c FROM CreatedDocument c WHERE c.date = :date"),
    @NamedQuery(name = "CreatedDocument.findByIsActive", query = "SELECT c FROM CreatedDocument c WHERE c.isActive = :isActive")})
public class CreatedDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "isActive")
    private Short isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentCreatorId")
    private Collection<LoanDocumentsScheduler> loanDocumentsSchedulerCollection;
    @JoinColumn(name = "master_documents_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MasterDocuments masterDocumentsId;
    @JoinColumn(name = "member_documents_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MemberDocuments memberDocumentsId;

    public CreatedDocument() {
    }

    public CreatedDocument(Integer id) {
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

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Collection<LoanDocumentsScheduler> getLoanDocumentsSchedulerCollection() {
        return loanDocumentsSchedulerCollection;
    }

    public void setLoanDocumentsSchedulerCollection(Collection<LoanDocumentsScheduler> loanDocumentsSchedulerCollection) {
        this.loanDocumentsSchedulerCollection = loanDocumentsSchedulerCollection;
    }

    public MasterDocuments getMasterDocumentsId() {
        return masterDocumentsId;
    }

    public void setMasterDocumentsId(MasterDocuments masterDocumentsId) {
        this.masterDocumentsId = masterDocumentsId;
    }

    public MemberDocuments getMemberDocumentsId() {
        return memberDocumentsId;
    }

    public void setMemberDocumentsId(MemberDocuments memberDocumentsId) {
        this.memberDocumentsId = memberDocumentsId;
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
        if (!(object instanceof CreatedDocument)) {
            return false;
        }
        CreatedDocument other = (CreatedDocument) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.CreatedDocument[ id=" + id + " ]";
    }
    
}
