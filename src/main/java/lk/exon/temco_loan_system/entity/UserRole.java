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
@Table(name = "user_role")
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u"),
    @NamedQuery(name = "UserRole.findById", query = "SELECT u FROM UserRole u WHERE u.id = :id"),
    @NamedQuery(name = "UserRole.findByName", query = "SELECT u FROM UserRole u WHERE u.name = :name")})
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "userRoleId")
    private Collection<UserLogin> userLoginCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userRoleId")
    private Collection<UseCaseHasUserRole> useCaseHasUserRoleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userRoleId")
    private Collection<UserRoleHasSystemInterface> userRoleHasSystemInterfaceCollection;
    @OneToMany(mappedBy = "userRoleId")
    private Collection<UserLoginGroup> userLoginGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userRoleId")
    private Collection<UserRoleManagement> userRoleManagementCollection;

    public UserRole() {
    }

    public UserRole(Integer id) {
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

    public Collection<UserLogin> getUserLoginCollection() {
        return userLoginCollection;
    }

    public void setUserLoginCollection(Collection<UserLogin> userLoginCollection) {
        this.userLoginCollection = userLoginCollection;
    }

    public Collection<UseCaseHasUserRole> getUseCaseHasUserRoleCollection() {
        return useCaseHasUserRoleCollection;
    }

    public void setUseCaseHasUserRoleCollection(Collection<UseCaseHasUserRole> useCaseHasUserRoleCollection) {
        this.useCaseHasUserRoleCollection = useCaseHasUserRoleCollection;
    }

    public Collection<UserRoleHasSystemInterface> getUserRoleHasSystemInterfaceCollection() {
        return userRoleHasSystemInterfaceCollection;
    }

    public void setUserRoleHasSystemInterfaceCollection(Collection<UserRoleHasSystemInterface> userRoleHasSystemInterfaceCollection) {
        this.userRoleHasSystemInterfaceCollection = userRoleHasSystemInterfaceCollection;
    }

    public Collection<UserLoginGroup> getUserLoginGroupCollection() {
        return userLoginGroupCollection;
    }

    public void setUserLoginGroupCollection(Collection<UserLoginGroup> userLoginGroupCollection) {
        this.userLoginGroupCollection = userLoginGroupCollection;
    }

    public Collection<UserRoleManagement> getUserRoleManagementCollection() {
        return userRoleManagementCollection;
    }

    public void setUserRoleManagementCollection(Collection<UserRoleManagement> userRoleManagementCollection) {
        this.userRoleManagementCollection = userRoleManagementCollection;
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
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.UserRole[ id=" + id + " ]";
    }
    
}
