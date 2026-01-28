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
@Table(name = "bank_statement")
@NamedQueries({
    @NamedQuery(name = "BankStatement.findAll", query = "SELECT b FROM BankStatement b"),
    @NamedQuery(name = "BankStatement.findById", query = "SELECT b FROM BankStatement b WHERE b.id = :id")})
public class BankStatement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "credit_or_debit_id")
    @ManyToOne(optional = false)
    private CreditOrDebit transactionTypeId;
    @JoinColumn(name = "member_bank_accounts_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MemberBankAccounts memberBankAccountsId;

    public BankStatement() {
    }

    public BankStatement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CreditOrDebit getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(CreditOrDebit transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public MemberBankAccounts getMemberBankAccountsId() {
        return memberBankAccountsId;
    }

    public void setMemberBankAccountsId(MemberBankAccounts memberBankAccountsId) {
        this.memberBankAccountsId = memberBankAccountsId;
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
        if (!(object instanceof BankStatement)) {
            return false;
        }
        BankStatement other = (BankStatement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.BankStatement[ id=" + id + " ]";
    }
    
}
