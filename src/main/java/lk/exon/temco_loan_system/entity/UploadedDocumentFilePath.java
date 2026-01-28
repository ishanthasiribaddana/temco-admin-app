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
import jakarta.persistence.Lob;
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
@Table(name = "uploaded_document_file_path")
@NamedQueries({
    @NamedQuery(name = "UploadedDocumentFilePath.findAll", query = "SELECT u FROM UploadedDocumentFilePath u"),
    @NamedQuery(name = "UploadedDocumentFilePath.findById", query = "SELECT u FROM UploadedDocumentFilePath u WHERE u.id = :id")})
public class UploadedDocumentFilePath implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "file_path")
    private String filePath;
    @JoinColumn(name = "user_document_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UniversalUserDocument userDocumentId;

    public UploadedDocumentFilePath() {
    }

    public UploadedDocumentFilePath(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public UniversalUserDocument getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(UniversalUserDocument userDocumentId) {
        this.userDocumentId = userDocumentId;
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
        if (!(object instanceof UploadedDocumentFilePath)) {
            return false;
        }
        UploadedDocumentFilePath other = (UploadedDocumentFilePath) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.UploadedDocumentFilePath[ id=" + id + " ]";
    }
    
}
