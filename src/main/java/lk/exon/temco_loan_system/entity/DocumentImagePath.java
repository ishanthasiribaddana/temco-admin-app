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
import jakarta.persistence.Lob;
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
@Table(name = "document_image_path")
@NamedQueries({
    @NamedQuery(name = "DocumentImagePath.findAll", query = "SELECT d FROM DocumentImagePath d"),
    @NamedQuery(name = "DocumentImagePath.findById", query = "SELECT d FROM DocumentImagePath d WHERE d.id = :id")})
public class DocumentImagePath implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "img_path")
    private String imgPath;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentImagePathId")
    private Collection<ImgPathManager> imgPathManagerCollection;

    public DocumentImagePath() {
    }

    public DocumentImagePath(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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
        if (!(object instanceof DocumentImagePath)) {
            return false;
        }
        DocumentImagePath other = (DocumentImagePath) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.DocumentImagePath[ id=" + id + " ]";
    }
    
}
