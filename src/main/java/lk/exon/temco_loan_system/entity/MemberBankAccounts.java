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
@Table(name = "member_bank_accounts")
@NamedQueries({
    @NamedQuery(name = "MemberBankAccounts.findAll", query = "SELECT m FROM MemberBankAccounts m"),
    @NamedQuery(name = "MemberBankAccounts.findById", query = "SELECT m FROM MemberBankAccounts m WHERE m.id = :id"),
    @NamedQuery(name = "MemberBankAccounts.findByAccountNo", query = "SELECT m FROM MemberBankAccounts m WHERE m.accountNo = :accountNo"),
    @NamedQuery(name = "MemberBankAccounts.findByDate", query = "SELECT m FROM MemberBankAccounts m WHERE m.date = :date")})
public class MemberBankAccounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "account_no")
    private String accountNo;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberBankAccountsId")
    private Collection<BankStatement> bankStatementCollection;
    @JoinColumn(name = "account_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AccountType accountTypeId;
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Member1 memberId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberBankAccountsId")
    private Collection<LoanManager> loanManagerCollection;

    public MemberBankAccounts() {
    }

    public MemberBankAccounts(Integer id) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Collection<BankStatement> getBankStatementCollection() {
        return bankStatementCollection;
    }

    public void setBankStatementCollection(Collection<BankStatement> bankStatementCollection) {
        this.bankStatementCollection = bankStatementCollection;
    }

    public AccountType getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(AccountType accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public Member1 getMemberId() {
        return memberId;
    }

    public void setMemberId(Member1 memberId) {
        this.memberId = memberId;
    }

    public Collection<LoanManager> getLoanManagerCollection() {
        return loanManagerCollection;
    }

    public void setLoanManagerCollection(Collection<LoanManager> loanManagerCollection) {
        this.loanManagerCollection = loanManagerCollection;
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
        if (!(object instanceof MemberBankAccounts)) {
            return false;
        }
        MemberBankAccounts other = (MemberBankAccounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.MemberBankAccounts[ id=" + id + " ]";
    }
    
}
