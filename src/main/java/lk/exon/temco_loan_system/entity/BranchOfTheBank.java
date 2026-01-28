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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "branch_of_the_bank")
@NamedQueries({
    @NamedQuery(name = "BranchOfTheBank.findAll", query = "SELECT b FROM BranchOfTheBank b"),
    @NamedQuery(name = "BranchOfTheBank.findById", query = "SELECT b FROM BranchOfTheBank b WHERE b.id = :id"),
    @NamedQuery(name = "BranchOfTheBank.findByBranchCode", query = "SELECT b FROM BranchOfTheBank b WHERE b.branchCode = :branchCode")})
public class BranchOfTheBank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "branch_code")
    private String branchCode;
    @OneToMany(mappedBy = "branchOfTheBankId")
    private Collection<BankAccount> bankAccountCollection;
    @OneToMany(mappedBy = "branchOfTheBankId")
    private Collection<SwiftCodes> swiftCodesCollection;
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bank bankId;
    @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeneralOrganizationProfile generalOrganizationProfileId;

    public BranchOfTheBank() {
    }

    public BranchOfTheBank(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Collection<BankAccount> getBankAccountCollection() {
        return bankAccountCollection;
    }

    public void setBankAccountCollection(Collection<BankAccount> bankAccountCollection) {
        this.bankAccountCollection = bankAccountCollection;
    }

    public Collection<SwiftCodes> getSwiftCodesCollection() {
        return swiftCodesCollection;
    }

    public void setSwiftCodesCollection(Collection<SwiftCodes> swiftCodesCollection) {
        this.swiftCodesCollection = swiftCodesCollection;
    }

    public Bank getBankId() {
        return bankId;
    }

    public void setBankId(Bank bankId) {
        this.bankId = bankId;
    }

    public GeneralOrganizationProfile getGeneralOrganizationProfileId() {
        return generalOrganizationProfileId;
    }

    public void setGeneralOrganizationProfileId(GeneralOrganizationProfile generalOrganizationProfileId) {
        this.generalOrganizationProfileId = generalOrganizationProfileId;
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
        if (!(object instanceof BranchOfTheBank)) {
            return false;
        }
        BranchOfTheBank other = (BranchOfTheBank) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.BranchOfTheBank[ id=" + id + " ]";
    }
    
}
