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
@Table(name = "credit_or_debit")
@NamedQueries({
    @NamedQuery(name = "CreditOrDebit.findAll", query = "SELECT c FROM CreditOrDebit c"),
    @NamedQuery(name = "CreditOrDebit.findByCreditOrDebitId", query = "SELECT c FROM CreditOrDebit c WHERE c.creditOrDebitId = :creditOrDebitId"),
    @NamedQuery(name = "CreditOrDebit.findByType", query = "SELECT c FROM CreditOrDebit c WHERE c.type = :type")})
public class CreditOrDebit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "credit_or_debit_id")
    private Integer creditOrDebitId;
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionTypeId")
    private Collection<BankStatement> bankStatementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creditOrDebitId")
    private Collection<GeneralJournalEntry> generalJournalEntryCollection;

    public CreditOrDebit() {
    }

    public CreditOrDebit(Integer creditOrDebitId) {
        this.creditOrDebitId = creditOrDebitId;
    }

    public Integer getCreditOrDebitId() {
        return creditOrDebitId;
    }

    public void setCreditOrDebitId(Integer creditOrDebitId) {
        this.creditOrDebitId = creditOrDebitId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<BankStatement> getBankStatementCollection() {
        return bankStatementCollection;
    }

    public void setBankStatementCollection(Collection<BankStatement> bankStatementCollection) {
        this.bankStatementCollection = bankStatementCollection;
    }

    public Collection<GeneralJournalEntry> getGeneralJournalEntryCollection() {
        return generalJournalEntryCollection;
    }

    public void setGeneralJournalEntryCollection(Collection<GeneralJournalEntry> generalJournalEntryCollection) {
        this.generalJournalEntryCollection = generalJournalEntryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditOrDebitId != null ? creditOrDebitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditOrDebit)) {
            return false;
        }
        CreditOrDebit other = (CreditOrDebit) object;
        if ((this.creditOrDebitId == null && other.creditOrDebitId != null) || (this.creditOrDebitId != null && !this.creditOrDebitId.equals(other.creditOrDebitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.CreditOrDebit[ creditOrDebitId=" + creditOrDebitId + " ]";
    }
    
}
