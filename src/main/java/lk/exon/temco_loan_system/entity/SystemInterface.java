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
@Table(name = "system_interface")
@NamedQueries({
    @NamedQuery(name = "SystemInterface.findAll", query = "SELECT s FROM SystemInterface s"),
    @NamedQuery(name = "SystemInterface.findById", query = "SELECT s FROM SystemInterface s WHERE s.id = :id"),
    @NamedQuery(name = "SystemInterface.findByInterfaceName", query = "SELECT s FROM SystemInterface s WHERE s.interfaceName = :interfaceName"),
    @NamedQuery(name = "SystemInterface.findByDisplayName", query = "SELECT s FROM SystemInterface s WHERE s.displayName = :displayName"),
    @NamedQuery(name = "SystemInterface.findByUrl", query = "SELECT s FROM SystemInterface s WHERE s.url = :url")})
public class SystemInterface implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "interface_name")
    private String interfaceName;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "url")
    private String url;
    @Lob
    @Column(name = "status")
    private byte[] status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "systemInterfaceId")
    private Collection<UserLoginHasSystemInterface> userLoginHasSystemInterfaceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "systemInterfaceId")
    private Collection<UserRoleHasSystemInterface> userRoleHasSystemInterfaceCollection;
    @JoinColumn(name = "interface_menu_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private InterfaceMenu interfaceMenuId;
    @JoinColumn(name = "interface_sub_menu_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private InterfaceSubMenu interfaceSubMenuId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "systemInterfaceId")
    private Collection<UseCaseHasSystemInterface> useCaseHasSystemInterfaceCollection;

    public SystemInterface() {
    }

    public SystemInterface(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getStatus() {
        return status;
    }

    public void setStatus(byte[] status) {
        this.status = status;
    }

    public Collection<UserLoginHasSystemInterface> getUserLoginHasSystemInterfaceCollection() {
        return userLoginHasSystemInterfaceCollection;
    }

    public void setUserLoginHasSystemInterfaceCollection(Collection<UserLoginHasSystemInterface> userLoginHasSystemInterfaceCollection) {
        this.userLoginHasSystemInterfaceCollection = userLoginHasSystemInterfaceCollection;
    }

    public Collection<UserRoleHasSystemInterface> getUserRoleHasSystemInterfaceCollection() {
        return userRoleHasSystemInterfaceCollection;
    }

    public void setUserRoleHasSystemInterfaceCollection(Collection<UserRoleHasSystemInterface> userRoleHasSystemInterfaceCollection) {
        this.userRoleHasSystemInterfaceCollection = userRoleHasSystemInterfaceCollection;
    }

    public InterfaceMenu getInterfaceMenuId() {
        return interfaceMenuId;
    }

    public void setInterfaceMenuId(InterfaceMenu interfaceMenuId) {
        this.interfaceMenuId = interfaceMenuId;
    }

    public InterfaceSubMenu getInterfaceSubMenuId() {
        return interfaceSubMenuId;
    }

    public void setInterfaceSubMenuId(InterfaceSubMenu interfaceSubMenuId) {
        this.interfaceSubMenuId = interfaceSubMenuId;
    }

    public Collection<UseCaseHasSystemInterface> getUseCaseHasSystemInterfaceCollection() {
        return useCaseHasSystemInterfaceCollection;
    }

    public void setUseCaseHasSystemInterfaceCollection(Collection<UseCaseHasSystemInterface> useCaseHasSystemInterfaceCollection) {
        this.useCaseHasSystemInterfaceCollection = useCaseHasSystemInterfaceCollection;
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
        if (!(object instanceof SystemInterface)) {
            return false;
        }
        SystemInterface other = (SystemInterface) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.SystemInterface[ id=" + id + " ]";
    }
    
}
