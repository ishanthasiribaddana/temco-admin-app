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
@Table(name = "scholarship_catergory")
@NamedQueries({
    @NamedQuery(name = "ScholarshipCatergory.findAll", query = "SELECT s FROM ScholarshipCatergory s"),
    @NamedQuery(name = "ScholarshipCatergory.findById", query = "SELECT s FROM ScholarshipCatergory s WHERE s.id = :id"),
    @NamedQuery(name = "ScholarshipCatergory.findByCatergory", query = "SELECT s FROM ScholarshipCatergory s WHERE s.catergory = :catergory")})
public class ScholarshipCatergory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "catergory")
    private Double catergory;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scholarshipCatergoryId")
    private Collection<ScholarshipManager> scholarshipManagerCollection;

    public ScholarshipCatergory() {
    }

    public ScholarshipCatergory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCatergory() {
        return catergory;
    }

    public void setCatergory(Double catergory) {
        this.catergory = catergory;
    }

    public Collection<ScholarshipManager> getScholarshipManagerCollection() {
        return scholarshipManagerCollection;
    }

    public void setScholarshipManagerCollection(Collection<ScholarshipManager> scholarshipManagerCollection) {
        this.scholarshipManagerCollection = scholarshipManagerCollection;
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
        if (!(object instanceof ScholarshipCatergory)) {
            return false;
        }
        ScholarshipCatergory other = (ScholarshipCatergory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.ScholarshipCatergory[ id=" + id + " ]";
    }
    
}
