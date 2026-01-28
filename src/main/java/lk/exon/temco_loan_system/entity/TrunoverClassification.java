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
@Table(name = "trunover_classification")
@NamedQueries({
    @NamedQuery(name = "TrunoverClassification.findAll", query = "SELECT t FROM TrunoverClassification t"),
    @NamedQuery(name = "TrunoverClassification.findById", query = "SELECT t FROM TrunoverClassification t WHERE t.id = :id"),
    @NamedQuery(name = "TrunoverClassification.findByTrunover", query = "SELECT t FROM TrunoverClassification t WHERE t.trunover = :trunover"),
    @NamedQuery(name = "TrunoverClassification.findByCreatedAt", query = "SELECT t FROM TrunoverClassification t WHERE t.createdAt = :createdAt")})
public class TrunoverClassification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "trunover")
    private String trunover;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToMany(mappedBy = "trunoverClassificationId")
    private Collection<BusinessInformation> businessInformationCollection;

    public TrunoverClassification() {
    }

    public TrunoverClassification(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrunover() {
        return trunover;
    }

    public void setTrunover(String trunover) {
        this.trunover = trunover;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Collection<BusinessInformation> getBusinessInformationCollection() {
        return businessInformationCollection;
    }

    public void setBusinessInformationCollection(Collection<BusinessInformation> businessInformationCollection) {
        this.businessInformationCollection = businessInformationCollection;
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
        if (!(object instanceof TrunoverClassification)) {
            return false;
        }
        TrunoverClassification other = (TrunoverClassification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.TrunoverClassification[ id=" + id + " ]";
    }
    
}
