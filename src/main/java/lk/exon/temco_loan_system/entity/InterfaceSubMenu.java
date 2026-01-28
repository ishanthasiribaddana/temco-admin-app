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
@Table(name = "interface_sub_menu")
@NamedQueries({
    @NamedQuery(name = "InterfaceSubMenu.findAll", query = "SELECT i FROM InterfaceSubMenu i"),
    @NamedQuery(name = "InterfaceSubMenu.findById", query = "SELECT i FROM InterfaceSubMenu i WHERE i.id = :id"),
    @NamedQuery(name = "InterfaceSubMenu.findByName", query = "SELECT i FROM InterfaceSubMenu i WHERE i.name = :name")})
public class InterfaceSubMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interfaceSubMenuId")
    private Collection<SystemInterface> systemInterfaceCollection;

    public InterfaceSubMenu() {
    }

    public InterfaceSubMenu(Integer id) {
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
        if (!(object instanceof InterfaceSubMenu)) {
            return false;
        }
        InterfaceSubMenu other = (InterfaceSubMenu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.InterfaceSubMenu[ id=" + id + " ]";
    }
    
}
