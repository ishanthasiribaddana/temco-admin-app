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
@Table(name = "general_journal_entry_manager")
@NamedQueries({
    @NamedQuery(name = "GeneralJournalEntryManager.findAll", query = "SELECT g FROM GeneralJournalEntryManager g"),
    @NamedQuery(name = "GeneralJournalEntryManager.findById", query = "SELECT g FROM GeneralJournalEntryManager g WHERE g.id = :id"),
    @NamedQuery(name = "GeneralJournalEntryManager.findByIsActive", query = "SELECT g FROM GeneralJournalEntryManager g WHERE g.isActive = :isActive")})
public class GeneralJournalEntryManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_active")
    private Integer isActive;
    @JoinColumn(name = "general_journal_entry_id_main", referencedColumnName = "general_journal_entry_id")
    @ManyToOne(optional = false)
    private GeneralJournalEntry generalJournalEntryIdMain;
    @JoinColumn(name = "general_journal_entry_sub", referencedColumnName = "general_journal_entry_id")
    @ManyToOne(optional = false)
    private GeneralJournalEntry generalJournalEntrySub;

    public GeneralJournalEntryManager() {
    }

    public GeneralJournalEntryManager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public GeneralJournalEntry getGeneralJournalEntryIdMain() {
        return generalJournalEntryIdMain;
    }

    public void setGeneralJournalEntryIdMain(GeneralJournalEntry generalJournalEntryIdMain) {
        this.generalJournalEntryIdMain = generalJournalEntryIdMain;
    }

    public GeneralJournalEntry getGeneralJournalEntrySub() {
        return generalJournalEntrySub;
    }

    public void setGeneralJournalEntrySub(GeneralJournalEntry generalJournalEntrySub) {
        this.generalJournalEntrySub = generalJournalEntrySub;
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
        if (!(object instanceof GeneralJournalEntryManager)) {
            return false;
        }
        GeneralJournalEntryManager other = (GeneralJournalEntryManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GeneralJournalEntryManager[ id=" + id + " ]";
    }
    
}
