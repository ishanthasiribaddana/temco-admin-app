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
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "organization_branches")
@NamedQueries({
    @NamedQuery(name = "OrganizationBranches.findAll", query = "SELECT o FROM OrganizationBranches o"),
    @NamedQuery(name = "OrganizationBranches.findById", query = "SELECT o FROM OrganizationBranches o WHERE o.id = :id"),
    @NamedQuery(name = "OrganizationBranches.findByName", query = "SELECT o FROM OrganizationBranches o WHERE o.name = :name")})
public class OrganizationBranches implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeneralOrganizationProfile generalOrganizationProfileId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationBranchesId")
    private Collection<BranchHasFactoringFee> branchHasFactoringFeeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationBranchesId")
    private Collection<BranchManager> branchManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationBranchesId")
    private Collection<LoanApplicantHasBranch> loanApplicantHasBranchCollection;

    public OrganizationBranches() {
    }

    public OrganizationBranches(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneralOrganizationProfile getGeneralOrganizationProfileId() {
        return generalOrganizationProfileId;
    }

    public void setGeneralOrganizationProfileId(GeneralOrganizationProfile generalOrganizationProfileId) {
        this.generalOrganizationProfileId = generalOrganizationProfileId;
    }

    public Collection<BranchHasFactoringFee> getBranchHasFactoringFeeCollection() {
        return branchHasFactoringFeeCollection;
    }

    public void setBranchHasFactoringFeeCollection(Collection<BranchHasFactoringFee> branchHasFactoringFeeCollection) {
        this.branchHasFactoringFeeCollection = branchHasFactoringFeeCollection;
    }

    public Collection<BranchManager> getBranchManagerCollection() {
        return branchManagerCollection;
    }

    public void setBranchManagerCollection(Collection<BranchManager> branchManagerCollection) {
        this.branchManagerCollection = branchManagerCollection;
    }

    public Collection<LoanApplicantHasBranch> getLoanApplicantHasBranchCollection() {
        return loanApplicantHasBranchCollection;
    }

    public void setLoanApplicantHasBranchCollection(Collection<LoanApplicantHasBranch> loanApplicantHasBranchCollection) {
        this.loanApplicantHasBranchCollection = loanApplicantHasBranchCollection;
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
        if (!(object instanceof OrganizationBranches)) {
            return false;
        }
        OrganizationBranches other = (OrganizationBranches) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.OrganizationBranches[ id=" + id + " ]";
    }
    
}
