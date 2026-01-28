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
@Table(name = "master_documents")
@NamedQueries({
    @NamedQuery(name = "MasterDocuments.findAll", query = "SELECT m FROM MasterDocuments m"),
    @NamedQuery(name = "MasterDocuments.findById", query = "SELECT m FROM MasterDocuments m WHERE m.id = :id"),
    @NamedQuery(name = "MasterDocuments.findByDocumentName", query = "SELECT m FROM MasterDocuments m WHERE m.documentName = :documentName")})
public class MasterDocuments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "document_name")
    private String documentName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterDocumentsId")
    private Collection<DocumentCreator> documentCreatorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterDocumentsId")
    private Collection<CreatedDocument> createdDocumentCollection;

    public MasterDocuments() {
    }

    public MasterDocuments(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Collection<DocumentCreator> getDocumentCreatorCollection() {
        return documentCreatorCollection;
    }

    public void setDocumentCreatorCollection(Collection<DocumentCreator> documentCreatorCollection) {
        this.documentCreatorCollection = documentCreatorCollection;
    }

    public Collection<CreatedDocument> getCreatedDocumentCollection() {
        return createdDocumentCollection;
    }

    public void setCreatedDocumentCollection(Collection<CreatedDocument> createdDocumentCollection) {
        this.createdDocumentCollection = createdDocumentCollection;
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
        if (!(object instanceof MasterDocuments)) {
            return false;
        }
        MasterDocuments other = (MasterDocuments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.MasterDocuments[ id=" + id + " ]";
    }
    
}
