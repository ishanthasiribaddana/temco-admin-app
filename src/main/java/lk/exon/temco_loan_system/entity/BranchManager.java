/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "branch_manager")
@NamedQueries({
    @NamedQuery(name = "BranchManager.findAll", query = "SELECT b FROM BranchManager b"),
    @NamedQuery(name = "BranchManager.findById", query = "SELECT b FROM BranchManager b WHERE b.id = :id")})
public class BranchManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "loan_customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanCustomer loanCustomerId;
    @JoinColumn(name = "organization_branches_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrganizationBranches organizationBranchesId;

    public BranchManager() {
    }

    public BranchManager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LoanCustomer getLoanCustomerId() {
        return loanCustomerId;
    }

    public void setLoanCustomerId(LoanCustomer loanCustomerId) {
        this.loanCustomerId = loanCustomerId;
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
        if (!(object instanceof BranchManager)) {
            return false;
        }
        BranchManager other = (BranchManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.BranchManager[ id=" + id + " ]";
    }
    
}
