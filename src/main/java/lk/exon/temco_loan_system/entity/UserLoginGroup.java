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
@Table(name = "user_login_group")
@NamedQueries({
    @NamedQuery(name = "UserLoginGroup.findAll", query = "SELECT u FROM UserLoginGroup u"),
    @NamedQuery(name = "UserLoginGroup.findById", query = "SELECT u FROM UserLoginGroup u WHERE u.id = :id"),
    @NamedQuery(name = "UserLoginGroup.findByMaxLoginAttempt", query = "SELECT u FROM UserLoginGroup u WHERE u.maxLoginAttempt = :maxLoginAttempt"),
    @NamedQuery(name = "UserLoginGroup.findByCountAttempt", query = "SELECT u FROM UserLoginGroup u WHERE u.countAttempt = :countAttempt")})
public class UserLoginGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "is_active")
    private byte[] isActive;
    @Column(name = "max_login_attempt")
    private Integer maxLoginAttempt;
    @Lob
    @Column(name = "is_first_time")
    private byte[] isFirstTime;
    @Column(name = "count_attempt")
    private Integer countAttempt;
    @OneToMany(mappedBy = "userLoginGroupId")
    private Collection<LoginSession> loginSessionCollection;
    @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id")
    @ManyToOne
    private GeneralOrganizationProfile generalOrganizationProfileId;
    @JoinColumn(name = "user_login_id", referencedColumnName = "id")
    @ManyToOne
    private UserLogin userLoginId;
    @JoinColumn(name = "user_role_id", referencedColumnName = "id")
    @ManyToOne
    private UserRole userRoleId;

    public UserLoginGroup() {
    }

    public UserLoginGroup(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getIsActive() {
        return isActive;
    }

    public void setIsActive(byte[] isActive) {
        this.isActive = isActive;
    }

    public Integer getMaxLoginAttempt() {
        return maxLoginAttempt;
    }

    public void setMaxLoginAttempt(Integer maxLoginAttempt) {
        this.maxLoginAttempt = maxLoginAttempt;
    }

    public byte[] getIsFirstTime() {
        return isFirstTime;
    }

    public void setIsFirstTime(byte[] isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    public Integer getCountAttempt() {
        return countAttempt;
    }

    public void setCountAttempt(Integer countAttempt) {
        this.countAttempt = countAttempt;
    }

    public Collection<LoginSession> getLoginSessionCollection() {
        return loginSessionCollection;
    }

    public void setLoginSessionCollection(Collection<LoginSession> loginSessionCollection) {
        this.loginSessionCollection = loginSessionCollection;
    }

    public GeneralOrganizationProfile getGeneralOrganizationProfileId() {
        return generalOrganizationProfileId;
    }

    public void setGeneralOrganizationProfileId(GeneralOrganizationProfile generalOrganizationProfileId) {
        this.generalOrganizationProfileId = generalOrganizationProfileId;
    }

    public UserLogin getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(UserLogin userLoginId) {
        this.userLoginId = userLoginId;
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
        if (!(object instanceof UserLoginGroup)) {
            return false;
        }
        UserLoginGroup other = (UserLoginGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.UserLoginGroup[ id=" + id + " ]";
    }
    
}
