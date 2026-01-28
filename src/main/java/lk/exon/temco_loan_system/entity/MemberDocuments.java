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
@Table(name = "member_documents")
@NamedQueries({
    @NamedQuery(name = "MemberDocuments.findAll", query = "SELECT m FROM MemberDocuments m"),
    @NamedQuery(name = "MemberDocuments.findById", query = "SELECT m FROM MemberDocuments m WHERE m.id = :id"),
    @NamedQuery(name = "MemberDocuments.findByName", query = "SELECT m FROM MemberDocuments m WHERE m.name = :name")})
public class MemberDocuments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentTypeId")
    private Collection<DocumentCreator> documentCreatorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentTypeId")
    private Collection<UniversalUserDocument> universalUserDocumentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentTypeId")
    private Collection<GuarantorDocuments> guarantorDocumentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberDocumentsId")
    private Collection<CreatedDocument> createdDocumentCollection;

    public MemberDocuments() {
    }

    public MemberDocuments(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<DocumentCreator> getDocumentCreatorCollection() {
        return documentCreatorCollection;
    }

    public void setDocumentCreatorCollection(Collection<DocumentCreator> documentCreatorCollection) {
        this.documentCreatorCollection = documentCreatorCollection;
    }

    public Collection<UniversalUserDocument> getUniversalUserDocumentCollection() {
        return universalUserDocumentCollection;
    }

    public void setUniversalUserDocumentCollection(Collection<UniversalUserDocument> universalUserDocumentCollection) {
        this.universalUserDocumentCollection = universalUserDocumentCollection;
    }

    public Collection<GuarantorDocuments> getGuarantorDocumentsCollection() {
        return guarantorDocumentsCollection;
    }

    public void setGuarantorDocumentsCollection(Collection<GuarantorDocuments> guarantorDocumentsCollection) {
        this.guarantorDocumentsCollection = guarantorDocumentsCollection;
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
        if (!(object instanceof MemberDocuments)) {
            return false;
        }
        MemberDocuments other = (MemberDocuments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.MemberDocuments[ id=" + id + " ]";
    }
    
}
