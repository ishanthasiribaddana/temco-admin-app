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
@Table(name = "organization_type")
@NamedQueries({
    @NamedQuery(name = "OrganizationType.findAll", query = "SELECT o FROM OrganizationType o"),
    @NamedQuery(name = "OrganizationType.findById", query = "SELECT o FROM OrganizationType o WHERE o.id = :id"),
    @NamedQuery(name = "OrganizationType.findByName", query = "SELECT o FROM OrganizationType o WHERE o.name = :name")})
public class OrganizationType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "organizationTypeId")
    private Collection<GeneralOrganizationProfile> generalOrganizationProfileCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationTypeId")
    private Collection<OrganizationSubTypes> organizationSubTypesCollection;

    public OrganizationType() {
    }

    public OrganizationType(Integer id) {
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

    public Collection<GeneralOrganizationProfile> getGeneralOrganizationProfileCollection() {
        return generalOrganizationProfileCollection;
    }

    public void setGeneralOrganizationProfileCollection(Collection<GeneralOrganizationProfile> generalOrganizationProfileCollection) {
        this.generalOrganizationProfileCollection = generalOrganizationProfileCollection;
    }

    public Collection<OrganizationSubTypes> getOrganizationSubTypesCollection() {
        return organizationSubTypesCollection;
    }

    public void setOrganizationSubTypesCollection(Collection<OrganizationSubTypes> organizationSubTypesCollection) {
        this.organizationSubTypesCollection = organizationSubTypesCollection;
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
        if (!(object instanceof OrganizationType)) {
            return false;
        }
        OrganizationType other = (OrganizationType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.OrganizationType[ id=" + id + " ]";
    }
    
}
