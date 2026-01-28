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
@Table(name = "share_certificate")
@NamedQueries({
    @NamedQuery(name = "ShareCertificate.findAll", query = "SELECT s FROM ShareCertificate s"),
    @NamedQuery(name = "ShareCertificate.findById", query = "SELECT s FROM ShareCertificate s WHERE s.id = :id"),
    @NamedQuery(name = "ShareCertificate.findByDate", query = "SELECT s FROM ShareCertificate s WHERE s.date = :date"),
    @NamedQuery(name = "ShareCertificate.findByShareTranferDate", query = "SELECT s FROM ShareCertificate s WHERE s.shareTranferDate = :shareTranferDate"),
    @NamedQuery(name = "ShareCertificate.findByPurchesDate", query = "SELECT s FROM ShareCertificate s WHERE s.purchesDate = :purchesDate"),
    @NamedQuery(name = "ShareCertificate.findByShareValue", query = "SELECT s FROM ShareCertificate s WHERE s.shareValue = :shareValue"),
    @NamedQuery(name = "ShareCertificate.findByNoOfShares", query = "SELECT s FROM ShareCertificate s WHERE s.noOfShares = :noOfShares")})
public class ShareCertificate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "share_tranfer_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shareTranferDate;
    @Column(name = "purches_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchesDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "share_value")
    private Double shareValue;
    @Column(name = "no_of_shares")
    private Integer noOfShares;
    @OneToMany(mappedBy = "shareCertificateId")
    private Collection<MembershipLevelHasShareCertificate> membershipLevelHasShareCertificateCollection;
    @JoinColumn(name = "owner_general_organization_profile_id", referencedColumnName = "id")
    @ManyToOne
    private GeneralOrganizationProfile ownerGeneralOrganizationProfileId;
    @JoinColumn(name = "onwer_member_id", referencedColumnName = "id")
    @ManyToOne
    private Member1 onwerMemberId;
    @JoinColumn(name = "share_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ShareType shareTypeId;

    public ShareCertificate() {
    }

    public ShareCertificate(Integer id) {
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

    public Date getShareTranferDate() {
        return shareTranferDate;
    }

    public void setShareTranferDate(Date shareTranferDate) {
        this.shareTranferDate = shareTranferDate;
    }

    public Date getPurchesDate() {
        return purchesDate;
    }

    public void setPurchesDate(Date purchesDate) {
        this.purchesDate = purchesDate;
    }

    public Double getShareValue() {
        return shareValue;
    }

    public void setShareValue(Double shareValue) {
        this.shareValue = shareValue;
    }

    public Integer getNoOfShares() {
        return noOfShares;
    }

    public void setNoOfShares(Integer noOfShares) {
        this.noOfShares = noOfShares;
    }

    public Collection<MembershipLevelHasShareCertificate> getMembershipLevelHasShareCertificateCollection() {
        return membershipLevelHasShareCertificateCollection;
    }

    public void setMembershipLevelHasShareCertificateCollection(Collection<MembershipLevelHasShareCertificate> membershipLevelHasShareCertificateCollection) {
        this.membershipLevelHasShareCertificateCollection = membershipLevelHasShareCertificateCollection;
    }

    public GeneralOrganizationProfile getOwnerGeneralOrganizationProfileId() {
        return ownerGeneralOrganizationProfileId;
    }

    public void setOwnerGeneralOrganizationProfileId(GeneralOrganizationProfile ownerGeneralOrganizationProfileId) {
        this.ownerGeneralOrganizationProfileId = ownerGeneralOrganizationProfileId;
    }

    public Member1 getOnwerMemberId() {
        return onwerMemberId;
    }

    public void setOnwerMemberId(Member1 onwerMemberId) {
        this.onwerMemberId = onwerMemberId;
    }

    public ShareType getShareTypeId() {
        return shareTypeId;
    }

    public void setShareTypeId(ShareType shareTypeId) {
        this.shareTypeId = shareTypeId;
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
        if (!(object instanceof ShareCertificate)) {
            return false;
        }
        ShareCertificate other = (ShareCertificate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.ShareCertificate[ id=" + id + " ]";
    }
    
}
