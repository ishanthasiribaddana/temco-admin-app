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
import jakarta.persistence.ManyToMany;
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
@Table(name = "interface_menu")
@NamedQueries({
    @NamedQuery(name = "InterfaceMenu.findAll", query = "SELECT i FROM InterfaceMenu i"),
    @NamedQuery(name = "InterfaceMenu.findById", query = "SELECT i FROM InterfaceMenu i WHERE i.id = :id"),
    @NamedQuery(name = "InterfaceMenu.findByName", query = "SELECT i FROM InterfaceMenu i WHERE i.name = :name")})
public class InterfaceMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "interfaceMenuCollection")
    private Collection<GeneralOrganizationProfile> generalOrganizationProfileCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interfaceMenuId")
    private Collection<SystemInterface> systemInterfaceCollection;

    public InterfaceMenu() {
    }

    public InterfaceMenu(Integer id) {
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

    public Collection<SystemInterface> getSystemInterfaceCollection() {
        return systemInterfaceCollection;
    }

    public void setSystemInterfaceCollection(Collection<SystemInterface> systemInterfaceCollection) {
        this.systemInterfaceCollection = systemInterfaceCollection;
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
        if (!(object instanceof InterfaceMenu)) {
            return false;
        }
        InterfaceMenu other = (InterfaceMenu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.InterfaceMenu[ id=" + id + " ]";
    }
    
}
