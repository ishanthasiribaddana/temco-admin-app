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
@Table(name = "login_session")
@NamedQueries({
    @NamedQuery(name = "LoginSession.findAll", query = "SELECT l FROM LoginSession l"),
    @NamedQuery(name = "LoginSession.findById", query = "SELECT l FROM LoginSession l WHERE l.id = :id"),
    @NamedQuery(name = "LoginSession.findByStartTime", query = "SELECT l FROM LoginSession l WHERE l.startTime = :startTime"),
    @NamedQuery(name = "LoginSession.findByEndTime", query = "SELECT l FROM LoginSession l WHERE l.endTime = :endTime"),
    @NamedQuery(name = "LoginSession.findByIp", query = "SELECT l FROM LoginSession l WHERE l.ip = :ip")})
public class LoginSession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "ip")
    private String ip;
    @JoinColumns({
        @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id"),
        @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id"),
        @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id")})
    @ManyToOne
    private GeneralOrganizationProfile generalOrganizationProfile;
    @JoinColumns({
        @JoinColumn(name = "user_login_id", referencedColumnName = "id"),
        @JoinColumn(name = "user_login_id", referencedColumnName = "id")})
    @ManyToOne
    private UserLogin userLogin;
    @JoinColumn(name = "user_login_group_id", referencedColumnName = "id")
    @ManyToOne
    private UserLoginGroup userLoginGroupId;
    @OneToMany(mappedBy = "loginSession")
    private Collection<Voucher> voucherCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginSessionId")
    private Collection<VoucherApprovalManager> voucherApprovalManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loginSessionId")
    private Collection<SecurityActivityLogs> securityActivityLogsCollection;

    public LoginSession() {
    }

    public LoginSession(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public GeneralOrganizationProfile getGeneralOrganizationProfile() {
        return generalOrganizationProfile;
    }

    public void setGeneralOrganizationProfile(GeneralOrganizationProfile generalOrganizationProfile) {
        this.generalOrganizationProfile = generalOrganizationProfile;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public UserLoginGroup getUserLoginGroupId() {
        return userLoginGroupId;
    }

    public void setUserLoginGroupId(UserLoginGroup userLoginGroupId) {
        this.userLoginGroupId = userLoginGroupId;
    }

    public Collection<Voucher> getVoucherCollection() {
        return voucherCollection;
    }

    public void setVoucherCollection(Collection<Voucher> voucherCollection) {
        this.voucherCollection = voucherCollection;
    }

    public Collection<VoucherApprovalManager> getVoucherApprovalManagerCollection() {
        return voucherApprovalManagerCollection;
    }

    public void setVoucherApprovalManagerCollection(Collection<VoucherApprovalManager> voucherApprovalManagerCollection) {
        this.voucherApprovalManagerCollection = voucherApprovalManagerCollection;
    }

    public Collection<SecurityActivityLogs> getSecurityActivityLogsCollection() {
        return securityActivityLogsCollection;
    }

    public void setSecurityActivityLogsCollection(Collection<SecurityActivityLogs> securityActivityLogsCollection) {
        this.securityActivityLogsCollection = securityActivityLogsCollection;
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
        if (!(object instanceof LoginSession)) {
            return false;
        }
        LoginSession other = (LoginSession) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoginSession[ id=" + id + " ]";
    }
    
}
