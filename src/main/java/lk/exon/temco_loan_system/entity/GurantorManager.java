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
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "gurantor_manager")
@NamedQueries({
    @NamedQuery(name = "GurantorManager.findAll", query = "SELECT g FROM GurantorManager g"),
    @NamedQuery(name = "GurantorManager.findById", query = "SELECT g FROM GurantorManager g WHERE g.id = :id")})
public class GurantorManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gurantorManager")
    private Collection<GurantorSalaryInfo> gurantorSalaryInfoCollection;
    @JoinColumn(name = "gurantor_count_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GurantorCount gurantorCountId;
    @JoinColumn(name = "loan_applicant_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanApplicantGurantor loanApplicantId;
    @JoinColumn(name = "loan_applicant_and_gurantors_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanApplicantGurantor loanApplicantAndGurantorsId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gurantorManagerId")
    private Collection<GuarantorDocuments> guarantorDocumentsCollection;

    public GurantorManager() {
    }

    public GurantorManager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<GurantorSalaryInfo> getGurantorSalaryInfoCollection() {
        return gurantorSalaryInfoCollection;
    }

    public void setGurantorSalaryInfoCollection(Collection<GurantorSalaryInfo> gurantorSalaryInfoCollection) {
        this.gurantorSalaryInfoCollection = gurantorSalaryInfoCollection;
    }

    public GurantorCount getGurantorCountId() {
        return gurantorCountId;
    }

    public void setGurantorCountId(GurantorCount gurantorCountId) {
        this.gurantorCountId = gurantorCountId;
    }

    public LoanApplicantGurantor getLoanApplicantId() {
        return loanApplicantId;
    }

    public void setLoanApplicantId(LoanApplicantGurantor loanApplicantId) {
        this.loanApplicantId = loanApplicantId;
    }

    public LoanApplicantGurantor getLoanApplicantAndGurantorsId() {
        return loanApplicantAndGurantorsId;
    }

    public void setLoanApplicantAndGurantorsId(LoanApplicantGurantor loanApplicantAndGurantorsId) {
        this.loanApplicantAndGurantorsId = loanApplicantAndGurantorsId;
    }

    public Collection<GuarantorDocuments> getGuarantorDocumentsCollection() {
        return guarantorDocumentsCollection;
    }

    public void setGuarantorDocumentsCollection(Collection<GuarantorDocuments> guarantorDocumentsCollection) {
        this.guarantorDocumentsCollection = guarantorDocumentsCollection;
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
        if (!(object instanceof GurantorManager)) {
            return false;
        }
        GurantorManager other = (GurantorManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GurantorManager[ id=" + id + " ]";
    }
    
}
