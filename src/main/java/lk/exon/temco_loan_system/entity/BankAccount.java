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
@Table(name = "bank_account")
@NamedQueries({
    @NamedQuery(name = "BankAccount.findAll", query = "SELECT b FROM BankAccount b"),
    @NamedQuery(name = "BankAccount.findById", query = "SELECT b FROM BankAccount b WHERE b.id = :id"),
    @NamedQuery(name = "BankAccount.findByAccountNo", query = "SELECT b FROM BankAccount b WHERE b.accountNo = :accountNo"),
    @NamedQuery(name = "BankAccount.findByNo", query = "SELECT b FROM BankAccount b WHERE b.no = :no"),
    @NamedQuery(name = "BankAccount.findByStreet", query = "SELECT b FROM BankAccount b WHERE b.street = :street"),
    @NamedQuery(name = "BankAccount.findByAccountHolder", query = "SELECT b FROM BankAccount b WHERE b.accountHolder = :accountHolder"),
    @NamedQuery(name = "BankAccount.findByCreatedDate", query = "SELECT b FROM BankAccount b WHERE b.createdDate = :createdDate"),
    @NamedQuery(name = "BankAccount.findByIsActive", query = "SELECT b FROM BankAccount b WHERE b.isActive = :isActive"),
    @NamedQuery(name = "BankAccount.findByLastUpdateDate", query = "SELECT b FROM BankAccount b WHERE b.lastUpdateDate = :lastUpdateDate"),
    @NamedQuery(name = "BankAccount.findByUpdatedAt", query = "SELECT b FROM BankAccount b WHERE b.updatedAt = :updatedAt"),
    @NamedQuery(name = "BankAccount.findByIsDefault", query = "SELECT b FROM BankAccount b WHERE b.isDefault = :isDefault")})
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "account_no")
    private String accountNo;
    @Column(name = "no")
    private String no;
    @Column(name = "street")
    private String street;
    @Column(name = "account_holder")
    private String accountHolder;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "is_active")
    private Short isActive;
    @Column(name = "last_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "is_default")
    private Short isDefault;
    @JoinColumn(name = "branch_of_the_bank_id", referencedColumnName = "id")
    @ManyToOne
    private BranchOfTheBank branchOfTheBankId;
    @JoinColumn(name = "spot_member_id", referencedColumnName = "id")
    @ManyToOne
    private SpotMember spotMemberId;
    @JoinColumn(name = "swift_codes_id", referencedColumnName = "id")
    @ManyToOne
    private SwiftCodes swiftCodesId;
    @OneToMany(mappedBy = "bankAccountId")
    private Collection<Voucher> voucherCollection;

    public BankAccount() {
    }

    public BankAccount(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Short getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Short isDefault) {
        this.isDefault = isDefault;
    }

    public BranchOfTheBank getBranchOfTheBankId() {
        return branchOfTheBankId;
    }

    public void setBranchOfTheBankId(BranchOfTheBank branchOfTheBankId) {
        this.branchOfTheBankId = branchOfTheBankId;
    }

    public SpotMember getSpotMemberId() {
        return spotMemberId;
    }

    public void setSpotMemberId(SpotMember spotMemberId) {
        this.spotMemberId = spotMemberId;
    }

    public SwiftCodes getSwiftCodesId() {
        return swiftCodesId;
    }

    public void setSwiftCodesId(SwiftCodes swiftCodesId) {
        this.swiftCodesId = swiftCodesId;
    }

    public Collection<Voucher> getVoucherCollection() {
        return voucherCollection;
    }

    public void setVoucherCollection(Collection<Voucher> voucherCollection) {
        this.voucherCollection = voucherCollection;
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
        if (!(object instanceof BankAccount)) {
            return false;
        }
        BankAccount other = (BankAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.BankAccount[ id=" + id + " ]";
    }
    
}
