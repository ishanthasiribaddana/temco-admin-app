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
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "org_category")
@NamedQueries({
    @NamedQuery(name = "OrgCategory.findAll", query = "SELECT o FROM OrgCategory o"),
    @NamedQuery(name = "OrgCategory.findById", query = "SELECT o FROM OrgCategory o WHERE o.id = :id"),
    @NamedQuery(name = "OrgCategory.findByName", query = "SELECT o FROM OrgCategory o WHERE o.name = :name")})
public class OrgCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "orgCategoryId")
    private Collection<OrgCategory> orgCategoryCollection;
    @JoinColumn(name = "org_category_id", referencedColumnName = "id")
    @ManyToOne
    private OrgCategory orgCategoryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orgCategoryId")
    private Collection<OrgCategoryManager> orgCategoryManagerCollection;

    public OrgCategory() {
    }

    public OrgCategory(Integer id) {
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

    public Collection<OrgCategory> getOrgCategoryCollection() {
        return orgCategoryCollection;
    }

    public void setOrgCategoryCollection(Collection<OrgCategory> orgCategoryCollection) {
        this.orgCategoryCollection = orgCategoryCollection;
    }

    public OrgCategory getOrgCategoryId() {
        return orgCategoryId;
    }

    public void setOrgCategoryId(OrgCategory orgCategoryId) {
        this.orgCategoryId = orgCategoryId;
    }

    public Collection<OrgCategoryManager> getOrgCategoryManagerCollection() {
        return orgCategoryManagerCollection;
    }

    public void setOrgCategoryManagerCollection(Collection<OrgCategoryManager> orgCategoryManagerCollection) {
        this.orgCategoryManagerCollection = orgCategoryManagerCollection;
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
        if (!(object instanceof OrgCategory)) {
            return false;
        }
        OrgCategory other = (OrgCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.OrgCategory[ id=" + id + " ]";
    }
    
}
