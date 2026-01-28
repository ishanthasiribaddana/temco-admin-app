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
@Table(name = "general_journal_entry")
@NamedQueries({
    @NamedQuery(name = "GeneralJournalEntry.findAll", query = "SELECT g FROM GeneralJournalEntry g"),
    @NamedQuery(name = "GeneralJournalEntry.findByGeneralJournalEntryId", query = "SELECT g FROM GeneralJournalEntry g WHERE g.generalJournalEntryId = :generalJournalEntryId"),
    @NamedQuery(name = "GeneralJournalEntry.findByDescription", query = "SELECT g FROM GeneralJournalEntry g WHERE g.description = :description"),
    @NamedQuery(name = "GeneralJournalEntry.findByAmount", query = "SELECT g FROM GeneralJournalEntry g WHERE g.amount = :amount"),
    @NamedQuery(name = "GeneralJournalEntry.findByDate", query = "SELECT g FROM GeneralJournalEntry g WHERE g.date = :date"),
    @NamedQuery(name = "GeneralJournalEntry.findByIsActive", query = "SELECT g FROM GeneralJournalEntry g WHERE g.isActive = :isActive"),
    @NamedQuery(name = "GeneralJournalEntry.findByChequeNo", query = "SELECT g FROM GeneralJournalEntry g WHERE g.chequeNo = :chequeNo")})
public class GeneralJournalEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "general_journal_entry_id")
    private Integer generalJournalEntryId;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "is_active")
    private Integer isActive;
    @Column(name = "cheque_no")
    private String chequeNo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalJournalEntryGeneralJournalEntryId")
    private Collection<JournalEntryProof> journalEntryProofCollection;
    @JoinColumn(name = "credit_or_debit_id", referencedColumnName = "credit_or_debit_id")
    @ManyToOne(optional = false)
    private CreditOrDebit creditOrDebitId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalJournalEntryIdMain")
    private Collection<GeneralJournalEntryManager> generalJournalEntryManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalJournalEntrySub")
    private Collection<GeneralJournalEntryManager> generalJournalEntryManagerCollection1;

    public GeneralJournalEntry() {
    }

    public GeneralJournalEntry(Integer generalJournalEntryId) {
        this.generalJournalEntryId = generalJournalEntryId;
    }

    public GeneralJournalEntry(Integer generalJournalEntryId, double amount) {
        this.generalJournalEntryId = generalJournalEntryId;
        this.amount = amount;
    }

    public Integer getGeneralJournalEntryId() {
        return generalJournalEntryId;
    }

    public void setGeneralJournalEntryId(Integer generalJournalEntryId) {
        this.generalJournalEntryId = generalJournalEntryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public Collection<JournalEntryProof> getJournalEntryProofCollection() {
        return journalEntryProofCollection;
    }

    public void setJournalEntryProofCollection(Collection<JournalEntryProof> journalEntryProofCollection) {
        this.journalEntryProofCollection = journalEntryProofCollection;
    }

    public CreditOrDebit getCreditOrDebitId() {
        return creditOrDebitId;
    }

    public void setCreditOrDebitId(CreditOrDebit creditOrDebitId) {
        this.creditOrDebitId = creditOrDebitId;
    }

    public Collection<GeneralJournalEntryManager> getGeneralJournalEntryManagerCollection() {
        return generalJournalEntryManagerCollection;
    }

    public void setGeneralJournalEntryManagerCollection(Collection<GeneralJournalEntryManager> generalJournalEntryManagerCollection) {
        this.generalJournalEntryManagerCollection = generalJournalEntryManagerCollection;
    }

    public Collection<GeneralJournalEntryManager> getGeneralJournalEntryManagerCollection1() {
        return generalJournalEntryManagerCollection1;
    }

    public void setGeneralJournalEntryManagerCollection1(Collection<GeneralJournalEntryManager> generalJournalEntryManagerCollection1) {
        this.generalJournalEntryManagerCollection1 = generalJournalEntryManagerCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (generalJournalEntryId != null ? generalJournalEntryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneralJournalEntry)) {
            return false;
        }
        GeneralJournalEntry other = (GeneralJournalEntry) object;
        if ((this.generalJournalEntryId == null && other.generalJournalEntryId != null) || (this.generalJournalEntryId != null && !this.generalJournalEntryId.equals(other.generalJournalEntryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GeneralJournalEntry[ generalJournalEntryId=" + generalJournalEntryId + " ]";
    }
    
}
