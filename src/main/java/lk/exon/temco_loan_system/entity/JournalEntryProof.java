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
@Table(name = "journal_entry_proof")
@NamedQueries({
    @NamedQuery(name = "JournalEntryProof.findAll", query = "SELECT j FROM JournalEntryProof j"),
    @NamedQuery(name = "JournalEntryProof.findById", query = "SELECT j FROM JournalEntryProof j WHERE j.id = :id")})
public class JournalEntryProof implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "general_journal_entry_general_journal_entry_id", referencedColumnName = "general_journal_entry_id")
    @ManyToOne(optional = false)
    private GeneralJournalEntry generalJournalEntryGeneralJournalEntryId;
    @JoinColumn(name = "universal_user_document_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UniversalUserDocument universalUserDocumentId;

    public JournalEntryProof() {
    }

    public JournalEntryProof(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GeneralJournalEntry getGeneralJournalEntryGeneralJournalEntryId() {
        return generalJournalEntryGeneralJournalEntryId;
    }

    public void setGeneralJournalEntryGeneralJournalEntryId(GeneralJournalEntry generalJournalEntryGeneralJournalEntryId) {
        this.generalJournalEntryGeneralJournalEntryId = generalJournalEntryGeneralJournalEntryId;
    }

    public UniversalUserDocument getUniversalUserDocumentId() {
        return universalUserDocumentId;
    }

    public void setUniversalUserDocumentId(UniversalUserDocument universalUserDocumentId) {
        this.universalUserDocumentId = universalUserDocumentId;
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
        if (!(object instanceof JournalEntryProof)) {
            return false;
        }
        JournalEntryProof other = (JournalEntryProof) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.JournalEntryProof[ id=" + id + " ]";
    }
    
}
