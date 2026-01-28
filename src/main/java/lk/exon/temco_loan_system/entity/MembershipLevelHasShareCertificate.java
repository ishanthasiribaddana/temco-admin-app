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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "membership_level_has_share_certificate")
@NamedQueries({
    @NamedQuery(name = "MembershipLevelHasShareCertificate.findAll", query = "SELECT m FROM MembershipLevelHasShareCertificate m"),
    @NamedQuery(name = "MembershipLevelHasShareCertificate.findById", query = "SELECT m FROM MembershipLevelHasShareCertificate m WHERE m.id = :id"),
    @NamedQuery(name = "MembershipLevelHasShareCertificate.findByDate", query = "SELECT m FROM MembershipLevelHasShareCertificate m WHERE m.date = :date")})
public class MembershipLevelHasShareCertificate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "membership_level_id", referencedColumnName = "id")
    @ManyToOne
    private MembershipLevel membershipLevelId;
    @JoinColumn(name = "share_certificate_id", referencedColumnName = "id")
    @ManyToOne
    private ShareCertificate shareCertificateId;

    public MembershipLevelHasShareCertificate() {
    }

    public MembershipLevelHasShareCertificate(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MembershipLevel getMembershipLevelId() {
        return membershipLevelId;
    }

    public void setMembershipLevelId(MembershipLevel membershipLevelId) {
        this.membershipLevelId = membershipLevelId;
    }

    public ShareCertificate getShareCertificateId() {
        return shareCertificateId;
    }

    public void setShareCertificateId(ShareCertificate shareCertificateId) {
        this.shareCertificateId = shareCertificateId;
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
        if (!(object instanceof MembershipLevelHasShareCertificate)) {
            return false;
        }
        MembershipLevelHasShareCertificate other = (MembershipLevelHasShareCertificate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.MembershipLevelHasShareCertificate[ id=" + id + " ]";
    }
    
}
