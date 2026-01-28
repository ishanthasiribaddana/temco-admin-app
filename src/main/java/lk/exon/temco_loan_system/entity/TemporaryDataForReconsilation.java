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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "temporary_data_for_reconsilation")
@NamedQueries({
    @NamedQuery(name = "TemporaryDataForReconsilation.findAll", query = "SELECT t FROM TemporaryDataForReconsilation t"),
    @NamedQuery(name = "TemporaryDataForReconsilation.findById", query = "SELECT t FROM TemporaryDataForReconsilation t WHERE t.id = :id")})
public class TemporaryDataForReconsilation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "field_value")
    private String fieldValue;
    @JoinColumn(name = "document_data_verification_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentDataVerification documentDataVerificationId;
    @JoinColumn(name = "user_document_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UniversalUserDocument userDocumentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temporaryDataForReconsilationId")
    private Collection<ReconsilationHistory> reconsilationHistoryCollection;

    public TemporaryDataForReconsilation() {
    }

    public TemporaryDataForReconsilation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public DocumentDataVerification getDocumentDataVerificationId() {
        return documentDataVerificationId;
    }

    public void setDocumentDataVerificationId(DocumentDataVerification documentDataVerificationId) {
        this.documentDataVerificationId = documentDataVerificationId;
    }

    public UniversalUserDocument getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(UniversalUserDocument userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public Collection<ReconsilationHistory> getReconsilationHistoryCollection() {
        return reconsilationHistoryCollection;
    }

    public void setReconsilationHistoryCollection(Collection<ReconsilationHistory> reconsilationHistoryCollection) {
        this.reconsilationHistoryCollection = reconsilationHistoryCollection;
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
        if (!(object instanceof TemporaryDataForReconsilation)) {
            return false;
        }
        TemporaryDataForReconsilation other = (TemporaryDataForReconsilation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.TemporaryDataForReconsilation[ id=" + id + " ]";
    }
    
}
