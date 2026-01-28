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
@Table(name = "document_creator")
@NamedQueries({
    @NamedQuery(name = "DocumentCreator.findAll", query = "SELECT d FROM DocumentCreator d"),
    @NamedQuery(name = "DocumentCreator.findById", query = "SELECT d FROM DocumentCreator d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentCreator.findByVersion", query = "SELECT d FROM DocumentCreator d WHERE d.version = :version"),
    @NamedQuery(name = "DocumentCreator.findByIsActive", query = "SELECT d FROM DocumentCreator d WHERE d.isActive = :isActive"),
    @NamedQuery(name = "DocumentCreator.findByDateCreated", query = "SELECT d FROM DocumentCreator d WHERE d.dateCreated = :dateCreated")})
public class DocumentCreator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "version")
    private String version;
    @Column(name = "is_active")
    private Short isActive;
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentCreatorId")
    private Collection<SubmittedUserDocument> submittedUserDocumentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentCreatorId")
    private Collection<DocumentInactive> documentInactiveCollection;
    @JoinColumn(name = "meta_data_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentItems metaDataId;
    @JoinColumn(name = "master_documents_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MasterDocuments masterDocumentsId;
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MemberDocuments documentTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentCreatorId")
    private Collection<DocumentFieldsManager> documentFieldsManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentCreatorId")
    private Collection<Table1> table1Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentCreatorId")
    private Collection<ImgPathManager> imgPathManagerCollection;

    public DocumentCreator() {
    }

    public DocumentCreator(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Collection<SubmittedUserDocument> getSubmittedUserDocumentCollection() {
        return submittedUserDocumentCollection;
    }

    public void setSubmittedUserDocumentCollection(Collection<SubmittedUserDocument> submittedUserDocumentCollection) {
        this.submittedUserDocumentCollection = submittedUserDocumentCollection;
    }

    public Collection<DocumentInactive> getDocumentInactiveCollection() {
        return documentInactiveCollection;
    }

    public void setDocumentInactiveCollection(Collection<DocumentInactive> documentInactiveCollection) {
        this.documentInactiveCollection = documentInactiveCollection;
    }

    public DocumentItems getMetaDataId() {
        return metaDataId;
    }

    public void setMetaDataId(DocumentItems metaDataId) {
        this.metaDataId = metaDataId;
    }

    public MasterDocuments getMasterDocumentsId() {
        return masterDocumentsId;
    }

    public void setMasterDocumentsId(MasterDocuments masterDocumentsId) {
        this.masterDocumentsId = masterDocumentsId;
    }

    public MemberDocuments getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(MemberDocuments documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public Collection<DocumentFieldsManager> getDocumentFieldsManagerCollection() {
        return documentFieldsManagerCollection;
    }

    public void setDocumentFieldsManagerCollection(Collection<DocumentFieldsManager> documentFieldsManagerCollection) {
        this.documentFieldsManagerCollection = documentFieldsManagerCollection;
    }

    public Collection<Table1> getTable1Collection() {
        return table1Collection;
    }

    public void setTable1Collection(Collection<Table1> table1Collection) {
        this.table1Collection = table1Collection;
    }

    public Collection<ImgPathManager> getImgPathManagerCollection() {
        return imgPathManagerCollection;
    }

    public void setImgPathManagerCollection(Collection<ImgPathManager> imgPathManagerCollection) {
        this.imgPathManagerCollection = imgPathManagerCollection;
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
        if (!(object instanceof DocumentCreator)) {
            return false;
        }
        DocumentCreator other = (DocumentCreator) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.DocumentCreator[ id=" + id + " ]";
    }
    
}
