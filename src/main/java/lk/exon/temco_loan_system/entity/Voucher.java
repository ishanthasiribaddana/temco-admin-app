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
import jakarta.persistence.Lob;
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
@Table(name = "voucher")
@NamedQueries({
    @NamedQuery(name = "Voucher.findAll", query = "SELECT v FROM Voucher v"),
    @NamedQuery(name = "Voucher.findById", query = "SELECT v FROM Voucher v WHERE v.id = :id"),
    @NamedQuery(name = "Voucher.findByAmount", query = "SELECT v FROM Voucher v WHERE v.amount = :amount"),
    @NamedQuery(name = "Voucher.findByDate", query = "SELECT v FROM Voucher v WHERE v.date = :date"),
    @NamedQuery(name = "Voucher.findByUpdatedAt", query = "SELECT v FROM Voucher v WHERE v.updatedAt = :updatedAt")})
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Lob
    @Column(name = "is_active")
    private byte[] isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucherId")
    private Collection<PayOrderSettlmentVoucherManager> payOrderSettlmentVoucherManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucher")
    private Collection<VoucherItem> voucherItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucherId")
    private Collection<VoucherDocumentMap> voucherDocumentMapCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucherId")
    private Collection<VoucherAttachment> voucherAttachmentCollection;
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    @ManyToOne
    private BankAccount bankAccountId;
    @JoinColumns({
        @JoinColumn(name = "general_user_profile_id", referencedColumnName = "id"),
        @JoinColumn(name = "general_user_profile_id", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private GeneralUserProfile generalUserProfile;
    @JoinColumns({
        @JoinColumn(name = "login_session_id", referencedColumnName = "id"),
        @JoinColumn(name = "login_session_id", referencedColumnName = "id")})
    @ManyToOne
    private LoginSession loginSession;
    @JoinColumn(name = "org_departments_id", referencedColumnName = "id")
    @ManyToOne
    private OrgDepartments orgDepartmentsId;
    @JoinColumn(name = "spot_member_id", referencedColumnName = "id")
    @ManyToOne
    private SpotMember spotMemberId;
    @JoinColumn(name = "voucher_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VoucherStatus voucherStatusId;
    @JoinColumn(name = "voucher_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VoucherType voucherTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucherId")
    private Collection<VoucherApprovalManager> voucherApprovalManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucherId")
    private Collection<LoanPaymentHistory> loanPaymentHistoryCollection;

    public Voucher() {
    }

    public Voucher(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public byte[] getIsActive() {
        return isActive;
    }

    public void setIsActive(byte[] isActive) {
        this.isActive = isActive;
    }

    public Collection<PayOrderSettlmentVoucherManager> getPayOrderSettlmentVoucherManagerCollection() {
        return payOrderSettlmentVoucherManagerCollection;
    }

    public void setPayOrderSettlmentVoucherManagerCollection(Collection<PayOrderSettlmentVoucherManager> payOrderSettlmentVoucherManagerCollection) {
        this.payOrderSettlmentVoucherManagerCollection = payOrderSettlmentVoucherManagerCollection;
    }

    public Collection<VoucherItem> getVoucherItemCollection() {
        return voucherItemCollection;
    }

    public void setVoucherItemCollection(Collection<VoucherItem> voucherItemCollection) {
        this.voucherItemCollection = voucherItemCollection;
    }

    public Collection<VoucherDocumentMap> getVoucherDocumentMapCollection() {
        return voucherDocumentMapCollection;
    }

    public void setVoucherDocumentMapCollection(Collection<VoucherDocumentMap> voucherDocumentMapCollection) {
        this.voucherDocumentMapCollection = voucherDocumentMapCollection;
    }

    public Collection<VoucherAttachment> getVoucherAttachmentCollection() {
        return voucherAttachmentCollection;
    }

    public void setVoucherAttachmentCollection(Collection<VoucherAttachment> voucherAttachmentCollection) {
        this.voucherAttachmentCollection = voucherAttachmentCollection;
    }

    public BankAccount getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(BankAccount bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public GeneralUserProfile getGeneralUserProfile() {
        return generalUserProfile;
    }

    public void setGeneralUserProfile(GeneralUserProfile generalUserProfile) {
        this.generalUserProfile = generalUserProfile;
    }

    public LoginSession getLoginSession() {
        return loginSession;
    }

    public void setLoginSession(LoginSession loginSession) {
        this.loginSession = loginSession;
    }

    public OrgDepartments getOrgDepartmentsId() {
        return orgDepartmentsId;
    }

    public void setOrgDepartmentsId(OrgDepartments orgDepartmentsId) {
        this.orgDepartmentsId = orgDepartmentsId;
    }

    public SpotMember getSpotMemberId() {
        return spotMemberId;
    }

    public void setSpotMemberId(SpotMember spotMemberId) {
        this.spotMemberId = spotMemberId;
    }

    public VoucherStatus getVoucherStatusId() {
        return voucherStatusId;
    }

    public void setVoucherStatusId(VoucherStatus voucherStatusId) {
        this.voucherStatusId = voucherStatusId;
    }

    public VoucherType getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(VoucherType voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    public Collection<VoucherApprovalManager> getVoucherApprovalManagerCollection() {
        return voucherApprovalManagerCollection;
    }

    public void setVoucherApprovalManagerCollection(Collection<VoucherApprovalManager> voucherApprovalManagerCollection) {
        this.voucherApprovalManagerCollection = voucherApprovalManagerCollection;
    }

    public Collection<LoanPaymentHistory> getLoanPaymentHistoryCollection() {
        return loanPaymentHistoryCollection;
    }

    public void setLoanPaymentHistoryCollection(Collection<LoanPaymentHistory> loanPaymentHistoryCollection) {
        this.loanPaymentHistoryCollection = loanPaymentHistoryCollection;
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
        if (!(object instanceof Voucher)) {
            return false;
        }
        Voucher other = (Voucher) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.Voucher[ id=" + id + " ]";
    }
    
}
