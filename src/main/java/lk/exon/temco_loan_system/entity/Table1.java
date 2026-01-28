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
@Table(name = "table1")
@NamedQueries({
    @NamedQuery(name = "Table1.findAll", query = "SELECT t FROM Table1 t"),
    @NamedQuery(name = "Table1.findById", query = "SELECT t FROM Table1 t WHERE t.id = :id")})
public class Table1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "document_creator_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentCreator documentCreatorId;
    @JoinColumn(name = "document_items_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentItems documentItemsId;
    @JoinColumn(name = "universal_user_document_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UniversalUserDocument universalUserDocumentId;

    public Table1() {
    }

    public Table1(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DocumentCreator getDocumentCreatorId() {
        return documentCreatorId;
    }

    public void setDocumentCreatorId(DocumentCreator documentCreatorId) {
        this.documentCreatorId = documentCreatorId;
    }

    public DocumentItems getDocumentItemsId() {
        return documentItemsId;
    }

    public void setDocumentItemsId(DocumentItems documentItemsId) {
        this.documentItemsId = documentItemsId;
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
        if (!(object instanceof Table1)) {
            return false;
        }
        Table1 other = (Table1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.Table1[ id=" + id + " ]";
    }
    
}
