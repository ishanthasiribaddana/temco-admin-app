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
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
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
@Table(name = "user_login")
@NamedQueries({
    @NamedQuery(name = "UserLogin.findAll", query = "SELECT u FROM UserLogin u"),
    @NamedQuery(name = "UserLogin.findById", query = "SELECT u FROM UserLogin u WHERE u.id = :id"),
    @NamedQuery(name = "UserLogin.findByUsername", query = "SELECT u FROM UserLogin u WHERE u.username = :username"),
    @NamedQuery(name = "UserLogin.findByPassword", query = "SELECT u FROM UserLogin u WHERE u.password = :password"),
    @NamedQuery(name = "UserLogin.findByIsActive", query = "SELECT u FROM UserLogin u WHERE u.isActive = :isActive"),
    @NamedQuery(name = "UserLogin.findByMaxLoginAttempt", query = "SELECT u FROM UserLogin u WHERE u.maxLoginAttempt = :maxLoginAttempt"),
    @NamedQuery(name = "UserLogin.findByCountAttempt", query = "SELECT u FROM UserLogin u WHERE u.countAttempt = :countAttempt"),
    @NamedQuery(name = "UserLogin.findByUpdatedAt", query = "SELECT u FROM UserLogin u WHERE u.updatedAt = :updatedAt"),
    @NamedQuery(name = "UserLogin.findByLastLoginAt", query = "SELECT u FROM UserLogin u WHERE u.lastLoginAt = :lastLoginAt")})
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "is_active")
    private Short isActive;
    @Column(name = "max_login_attempt")
    private Integer maxLoginAttempt;
    @Column(name = "count_attempt")
    private Integer countAttempt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "last_login_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userLoginId")
    private Collection<UserLoginHasSystemInterface> userLoginHasSystemInterfaceCollection;
    @JoinColumns({
        @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id"),
        @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id"),
        @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id")})
    @ManyToOne
    private GeneralOrganizationProfile generalOrganizationProfile;
    @JoinColumns({
        @JoinColumn(name = "general_user_profile_id", referencedColumnName = "id"),
        @JoinColumn(name = "general_user_profile_id", referencedColumnName = "id")})
    @ManyToOne
    private GeneralUserProfile generalUserProfile;
    @JoinColumn(name = "user_role_id", referencedColumnName = "id")
    @ManyToOne
    private UserRole userRoleId;
    @OneToMany(mappedBy = "userLogin")
    private Collection<LoginSession> loginSessionCollection;
    @OneToMany(mappedBy = "userLoginId")
    private Collection<UserLoginGroup> userLoginGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userLoginId")
    private Collection<DataChangedLogManager> dataChangedLogManagerCollection;

    public UserLogin() {
    }

    public UserLogin(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Integer getMaxLoginAttempt() {
        return maxLoginAttempt;
    }

    public void setMaxLoginAttempt(Integer maxLoginAttempt) {
        this.maxLoginAttempt = maxLoginAttempt;
    }

    public Integer getCountAttempt() {
        return countAttempt;
    }

    public void setCountAttempt(Integer countAttempt) {
        this.countAttempt = countAttempt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Collection<UserLoginHasSystemInterface> getUserLoginHasSystemInterfaceCollection() {
        return userLoginHasSystemInterfaceCollection;
    }

    public void setUserLoginHasSystemInterfaceCollection(Collection<UserLoginHasSystemInterface> userLoginHasSystemInterfaceCollection) {
        this.userLoginHasSystemInterfaceCollection = userLoginHasSystemInterfaceCollection;
    }

    public GeneralOrganizationProfile getGeneralOrganizationProfile() {
        return generalOrganizationProfile;
    }

    public void setGeneralOrganizationProfile(GeneralOrganizationProfile generalOrganizationProfile) {
        this.generalOrganizationProfile = generalOrganizationProfile;
    }

    public GeneralUserProfile getGeneralUserProfile() {
        return generalUserProfile;
    }

    public void setGeneralUserProfile(GeneralUserProfile generalUserProfile) {
        this.generalUserProfile = generalUserProfile;
    }

    public UserRole getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UserRole userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Collection<LoginSession> getLoginSessionCollection() {
        return loginSessionCollection;
    }

    public void setLoginSessionCollection(Collection<LoginSession> loginSessionCollection) {
        this.loginSessionCollection = loginSessionCollection;
    }

    public Collection<UserLoginGroup> getUserLoginGroupCollection() {
        return userLoginGroupCollection;
    }

    public void setUserLoginGroupCollection(Collection<UserLoginGroup> userLoginGroupCollection) {
        this.userLoginGroupCollection = userLoginGroupCollection;
    }

    public Collection<DataChangedLogManager> getDataChangedLogManagerCollection() {
        return dataChangedLogManagerCollection;
    }

    public void setDataChangedLogManagerCollection(Collection<DataChangedLogManager> dataChangedLogManagerCollection) {
        this.dataChangedLogManagerCollection = dataChangedLogManagerCollection;
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
        if (!(object instanceof UserLogin)) {
            return false;
        }
        UserLogin other = (UserLogin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.UserLogin[ id=" + id + " ]";
    }
    
}
