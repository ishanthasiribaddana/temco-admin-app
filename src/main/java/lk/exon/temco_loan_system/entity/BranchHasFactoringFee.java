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
@Table(name = "branch_has_factoring_fee")
@NamedQueries({
    @NamedQuery(name = "BranchHasFactoringFee.findAll", query = "SELECT b FROM BranchHasFactoringFee b"),
    @NamedQuery(name = "BranchHasFactoringFee.findById", query = "SELECT b FROM BranchHasFactoringFee b WHERE b.id = :id"),
    @NamedQuery(name = "BranchHasFactoringFee.findByIsActive", query = "SELECT b FROM BranchHasFactoringFee b WHERE b.isActive = :isActive"),
    @NamedQuery(name = "BranchHasFactoringFee.findByDate", query = "SELECT b FROM BranchHasFactoringFee b WHERE b.date = :date")})
public class BranchHasFactoringFee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_active")
    private Short isActive;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchHasFactoringFeeId")
    private Collection<PayOrderSettlementGuide> payOrderSettlementGuideCollection;
    @JoinColumn(name = "factoring_fee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FactoringFee factoringFeeId;
    @JoinColumn(name = "organization_branches_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrganizationBranches organizationBranchesId;

    public BranchHasFactoringFee() {
    }

    public BranchHasFactoringFee(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Collection<PayOrderSettlementGuide> getPayOrderSettlementGuideCollection() {
        return payOrderSettlementGuideCollection;
    }

    public void setPayOrderSettlementGuideCollection(Collection<PayOrderSettlementGuide> payOrderSettlementGuideCollection) {
        this.payOrderSettlementGuideCollection = payOrderSettlementGuideCollection;
    }

    public FactoringFee getFactoringFeeId() {
        return factoringFeeId;
    }

    public void setFactoringFeeId(FactoringFee factoringFeeId) {
        this.factoringFeeId = factoringFeeId;
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
        if (!(object instanceof BranchHasFactoringFee)) {
            return false;
        }
        BranchHasFactoringFee other = (BranchHasFactoringFee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.BranchHasFactoringFee[ id=" + id + " ]";
    }
    
}
