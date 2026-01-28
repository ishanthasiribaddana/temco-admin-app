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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "document_inactive")
@NamedQueries({
    @NamedQuery(name = "DocumentInactive.findAll", query = "SELECT d FROM DocumentInactive d"),
    @NamedQuery(name = "DocumentInactive.findById", query = "SELECT d FROM DocumentInactive d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentInactive.findByDateInactive", query = "SELECT d FROM DocumentInactive d WHERE d.dateInactive = :dateInactive")})
public class DocumentInactive implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_inactive")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInactive;
    @JoinColumn(name = "document_creator_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentCreator documentCreatorId;

    public DocumentInactive() {
    }

    public DocumentInactive(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateInactive() {
        return dateInactive;
    }

    public void setDateInactive(Date dateInactive) {
        this.dateInactive = dateInactive;
    }

    public DocumentCreator getDocumentCreatorId() {
        return documentCreatorId;
    }

    public void setDocumentCreatorId(DocumentCreator documentCreatorId) {
        this.documentCreatorId = documentCreatorId;
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
        if (!(object instanceof DocumentInactive)) {
            return false;
        }
        DocumentInactive other = (DocumentInactive) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.DocumentInactive[ id=" + id + " ]";
    }
    
}
