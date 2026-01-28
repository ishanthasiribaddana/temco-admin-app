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
@Table(name = "user_role_has_system_interface")
@NamedQueries({
    @NamedQuery(name = "UserRoleHasSystemInterface.findAll", query = "SELECT u FROM UserRoleHasSystemInterface u"),
    @NamedQuery(name = "UserRoleHasSystemInterface.findById", query = "SELECT u FROM UserRoleHasSystemInterface u WHERE u.id = :id")})
public class UserRoleHasSystemInterface implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "system_interface_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SystemInterface systemInterfaceId;
    @JoinColumn(name = "user_role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserRole userRoleId;

    public UserRoleHasSystemInterface() {
    }

    public UserRoleHasSystemInterface(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SystemInterface getSystemInterfaceId() {
        return systemInterfaceId;
    }

    public void setSystemInterfaceId(SystemInterface systemInterfaceId) {
        this.systemInterfaceId = systemInterfaceId;
    }

    public UserRole getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UserRole userRoleId) {
        this.userRoleId = userRoleId;
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
        if (!(object instanceof UserRoleHasSystemInterface)) {
            return false;
        }
        UserRoleHasSystemInterface other = (UserRoleHasSystemInterface) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.UserRoleHasSystemInterface[ id=" + id + " ]";
    }
    
}
