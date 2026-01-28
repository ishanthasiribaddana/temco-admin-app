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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "documents_submission_status")
@NamedQueries({
    @NamedQuery(name = "DocumentsSubmissionStatus.findAll", query = "SELECT d FROM DocumentsSubmissionStatus d"),
    @NamedQuery(name = "DocumentsSubmissionStatus.findById", query = "SELECT d FROM DocumentsSubmissionStatus d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentsSubmissionStatus.findByStatus", query = "SELECT d FROM DocumentsSubmissionStatus d WHERE d.status = :status")})
public class DocumentsSubmissionStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentsSubmissionStatusId")
    private Collection<LoanDocumentStatusManager> loanDocumentStatusManagerCollection;

    public DocumentsSubmissionStatus() {
    }

    public DocumentsSubmissionStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Collection<LoanDocumentStatusManager> getLoanDocumentStatusManagerCollection() {
        return loanDocumentStatusManagerCollection;
    }

    public void setLoanDocumentStatusManagerCollection(Collection<LoanDocumentStatusManager> loanDocumentStatusManagerCollection) {
        this.loanDocumentStatusManagerCollection = loanDocumentStatusManagerCollection;
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
        if (!(object instanceof DocumentsSubmissionStatus)) {
            return false;
        }
        DocumentsSubmissionStatus other = (DocumentsSubmissionStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.DocumentsSubmissionStatus[ id=" + id + " ]";
    }
    
}
