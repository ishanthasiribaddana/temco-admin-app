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
@Table(name = "member")
@NamedQueries({
    @NamedQuery(name = "Member1.findAll", query = "SELECT m FROM Member1 m"),
    @NamedQuery(name = "Member1.findById", query = "SELECT m FROM Member1 m WHERE m.id = :id"),
    @NamedQuery(name = "Member1.findByMembershipNo", query = "SELECT m FROM Member1 m WHERE m.membershipNo = :membershipNo"),
    @NamedQuery(name = "Member1.findByRegisteredDate", query = "SELECT m FROM Member1 m WHERE m.registeredDate = :registeredDate"),
    @NamedQuery(name = "Member1.findByKpiValue", query = "SELECT m FROM Member1 m WHERE m.kpiValue = :kpiValue"),
    @NamedQuery(name = "Member1.findByAvailableFund", query = "SELECT m FROM Member1 m WHERE m.availableFund = :availableFund"),
    @NamedQuery(name = "Member1.findByLastPaymentDate", query = "SELECT m FROM Member1 m WHERE m.lastPaymentDate = :lastPaymentDate"),
    @NamedQuery(name = "Member1.findByUpdatedAt", query = "SELECT m FROM Member1 m WHERE m.updatedAt = :updatedAt")})
public class Member1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "membership_no")
    private String membershipNo;
    @Column(name = "registered_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kpi_value")
    private Double kpiValue;
    @Column(name = "available_fund")
    private Double availableFund;
    @Column(name = "last_payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPaymentDate;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(mappedBy = "memberId")
    private Collection<Supplier> supplierCollection;
    @OneToMany(mappedBy = "memberId")
    private Collection<BusinessInformation> businessInformationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberId")
    private Collection<GopHasMember> gopHasMemberCollection;
    @OneToMany(mappedBy = "onwerMemberId")
    private Collection<ShareCertificate> shareCertificateCollection;
    @JoinColumn(name = "general_user_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeneralUserProfile generalUserProfileId;
    @OneToMany(mappedBy = "interduceeMemberId")
    private Collection<MemberHasMember> memberHasMemberCollection;
    @OneToMany(mappedBy = "interducerMemberId")
    private Collection<MemberHasMember> memberHasMemberCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberId")
    private Collection<Nominess> nominessCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberId")
    private Collection<MemberOrganizationsHistory> memberOrganizationsHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberId")
    private Collection<EmployementTypeHistory> employementTypeHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberId")
    private Collection<LoanDocumentsScheduler> loanDocumentsSchedulerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberId")
    private Collection<MemberBankAccounts> memberBankAccountsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberId")
    private Collection<LoanApplicantGurantor> loanApplicantGurantorCollection;

    public Member1() {
    }

    public Member1(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembershipNo() {
        return membershipNo;
    }

    public void setMembershipNo(String membershipNo) {
        this.membershipNo = membershipNo;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Double getKpiValue() {
        return kpiValue;
    }

    public void setKpiValue(Double kpiValue) {
        this.kpiValue = kpiValue;
    }

    public Double getAvailableFund() {
        return availableFund;
    }

    public void setAvailableFund(Double availableFund) {
        this.availableFund = availableFund;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Collection<Supplier> getSupplierCollection() {
        return supplierCollection;
    }

    public void setSupplierCollection(Collection<Supplier> supplierCollection) {
        this.supplierCollection = supplierCollection;
    }

    public Collection<BusinessInformation> getBusinessInformationCollection() {
        return businessInformationCollection;
    }

    public void setBusinessInformationCollection(Collection<BusinessInformation> businessInformationCollection) {
        this.businessInformationCollection = businessInformationCollection;
    }

    public Collection<GopHasMember> getGopHasMemberCollection() {
        return gopHasMemberCollection;
    }

    public void setGopHasMemberCollection(Collection<GopHasMember> gopHasMemberCollection) {
        this.gopHasMemberCollection = gopHasMemberCollection;
    }

    public Collection<ShareCertificate> getShareCertificateCollection() {
        return shareCertificateCollection;
    }

    public void setShareCertificateCollection(Collection<ShareCertificate> shareCertificateCollection) {
        this.shareCertificateCollection = shareCertificateCollection;
    }

    public GeneralUserProfile getGeneralUserProfileId() {
        return generalUserProfileId;
    }

    public void setGeneralUserProfileId(GeneralUserProfile generalUserProfileId) {
        this.generalUserProfileId = generalUserProfileId;
    }

    public Collection<MemberHasMember> getMemberHasMemberCollection() {
        return memberHasMemberCollection;
    }

    public void setMemberHasMemberCollection(Collection<MemberHasMember> memberHasMemberCollection) {
        this.memberHasMemberCollection = memberHasMemberCollection;
    }

    public Collection<MemberHasMember> getMemberHasMemberCollection1() {
        return memberHasMemberCollection1;
    }

    public void setMemberHasMemberCollection1(Collection<MemberHasMember> memberHasMemberCollection1) {
        this.memberHasMemberCollection1 = memberHasMemberCollection1;
    }

    public Collection<Nominess> getNominessCollection() {
        return nominessCollection;
    }

    public void setNominessCollection(Collection<Nominess> nominessCollection) {
        this.nominessCollection = nominessCollection;
    }

    public Collection<MemberOrganizationsHistory> getMemberOrganizationsHistoryCollection() {
        return memberOrganizationsHistoryCollection;
    }

    public void setMemberOrganizationsHistoryCollection(Collection<MemberOrganizationsHistory> memberOrganizationsHistoryCollection) {
        this.memberOrganizationsHistoryCollection = memberOrganizationsHistoryCollection;
    }

    public Collection<EmployementTypeHistory> getEmployementTypeHistoryCollection() {
        return employementTypeHistoryCollection;
    }

    public void setEmployementTypeHistoryCollection(Collection<EmployementTypeHistory> employementTypeHistoryCollection) {
        this.employementTypeHistoryCollection = employementTypeHistoryCollection;
    }

    public Collection<LoanDocumentsScheduler> getLoanDocumentsSchedulerCollection() {
        return loanDocumentsSchedulerCollection;
    }

    public void setLoanDocumentsSchedulerCollection(Collection<LoanDocumentsScheduler> loanDocumentsSchedulerCollection) {
        this.loanDocumentsSchedulerCollection = loanDocumentsSchedulerCollection;
    }

    public Collection<MemberBankAccounts> getMemberBankAccountsCollection() {
        return memberBankAccountsCollection;
    }

    public void setMemberBankAccountsCollection(Collection<MemberBankAccounts> memberBankAccountsCollection) {
        this.memberBankAccountsCollection = memberBankAccountsCollection;
    }

    public Collection<LoanApplicantGurantor> getLoanApplicantGurantorCollection() {
        return loanApplicantGurantorCollection;
    }

    public void setLoanApplicantGurantorCollection(Collection<LoanApplicantGurantor> loanApplicantGurantorCollection) {
        this.loanApplicantGurantorCollection = loanApplicantGurantorCollection;
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
        if (!(object instanceof Member1)) {
            return false;
        }
        Member1 other = (Member1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.Member1[ id=" + id + " ]";
    }
    
}
