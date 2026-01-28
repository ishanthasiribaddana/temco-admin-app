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
@Table(name = "loan_applicant_has_branch")
@NamedQueries({
    @NamedQuery(name = "LoanApplicantHasBranch.findAll", query = "SELECT l FROM LoanApplicantHasBranch l"),
    @NamedQuery(name = "LoanApplicantHasBranch.findById", query = "SELECT l FROM LoanApplicantHasBranch l WHERE l.id = :id")})
public class LoanApplicantHasBranch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanApplicantHasBranchId")
    private Collection<PayOrderSettlementGuide> payOrderSettlementGuideCollection;
    @JoinColumn(name = "loan_applicant_gurantor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanApplicantGurantor loanApplicantGurantorId;
    @JoinColumn(name = "loan_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanManager loanManagerId;
    @JoinColumn(name = "organization_branches_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrganizationBranches organizationBranchesId;

    public LoanApplicantHasBranch() {
    }

    public LoanApplicantHasBranch(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<PayOrderSettlementGuide> getPayOrderSettlementGuideCollection() {
        return payOrderSettlementGuideCollection;
    }

    public void setPayOrderSettlementGuideCollection(Collection<PayOrderSettlementGuide> payOrderSettlementGuideCollection) {
        this.payOrderSettlementGuideCollection = payOrderSettlementGuideCollection;
    }

    public LoanApplicantGurantor getLoanApplicantGurantorId() {
        return loanApplicantGurantorId;
    }

    public void setLoanApplicantGurantorId(LoanApplicantGurantor loanApplicantGurantorId) {
        this.loanApplicantGurantorId = loanApplicantGurantorId;
    }

    public LoanManager getLoanManagerId() {
        return loanManagerId;
    }

    public void setLoanManagerId(LoanManager loanManagerId) {
        this.loanManagerId = loanManagerId;
    }

    public OrganizationBranches getOrganizationBranchesId() {
        return organizationBranchesId;
    }

    public void setOrganizationBranchesId(OrganizationBranches organizationBranchesId) {
        this.organizationBranchesId = organizationBranchesId;
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
        if (!(object instanceof LoanApplicantHasBranch)) {
            return false;
        }
        LoanApplicantHasBranch other = (LoanApplicantHasBranch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanApplicantHasBranch[ id=" + id + " ]";
    }
    
}
