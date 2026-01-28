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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "student_due")
@NamedQueries({
    @NamedQuery(name = "StudentDue.findAll", query = "SELECT s FROM StudentDue s"),
    @NamedQuery(name = "StudentDue.findById", query = "SELECT s FROM StudentDue s WHERE s.id = :id"),
    @NamedQuery(name = "StudentDue.findByDueAmount", query = "SELECT s FROM StudentDue s WHERE s.dueAmount = :dueAmount"),
    @NamedQuery(name = "StudentDue.findByDueAmountLkr", query = "SELECT s FROM StudentDue s WHERE s.dueAmountLkr = :dueAmountLkr"),
    @NamedQuery(name = "StudentDue.findByExchangeRateUsed", query = "SELECT s FROM StudentDue s WHERE s.exchangeRateUsed = :exchangeRateUsed"),
    @NamedQuery(name = "StudentDue.findByPaymentStatus", query = "SELECT s FROM StudentDue s WHERE s.paymentStatus = :paymentStatus"),
    @NamedQuery(name = "StudentDue.findByAcademicYear", query = "SELECT s FROM StudentDue s WHERE s.academicYear = :academicYear"),
    @NamedQuery(name = "StudentDue.findBySemester", query = "SELECT s FROM StudentDue s WHERE s.semester = :semester"),
    @NamedQuery(name = "StudentDue.findByDueDate", query = "SELECT s FROM StudentDue s WHERE s.dueDate = :dueDate"),
    @NamedQuery(name = "StudentDue.findByPayeeInstitution", query = "SELECT s FROM StudentDue s WHERE s.payeeInstitution = :payeeInstitution"),
    @NamedQuery(name = "StudentDue.findByLoanRequestedDate", query = "SELECT s FROM StudentDue s WHERE s.loanRequestedDate = :loanRequestedDate"),
    @NamedQuery(name = "StudentDue.findByLoanApprovedDate", query = "SELECT s FROM StudentDue s WHERE s.loanApprovedDate = :loanApprovedDate"),
    @NamedQuery(name = "StudentDue.findByLoanDisbursedDate", query = "SELECT s FROM StudentDue s WHERE s.loanDisbursedDate = :loanDisbursedDate"),
    @NamedQuery(name = "StudentDue.findByScholarshipAmount", query = "SELECT s FROM StudentDue s WHERE s.scholarshipAmount = :scholarshipAmount"),
    @NamedQuery(name = "StudentDue.findByDiscountAmount", query = "SELECT s FROM StudentDue s WHERE s.discountAmount = :discountAmount"),
    @NamedQuery(name = "StudentDue.findByNetPayableAmount", query = "SELECT s FROM StudentDue s WHERE s.netPayableAmount = :netPayableAmount"),
    @NamedQuery(name = "StudentDue.findByServiceChargePercentage", query = "SELECT s FROM StudentDue s WHERE s.serviceChargePercentage = :serviceChargePercentage"),
    @NamedQuery(name = "StudentDue.findByServiceChargeAmount", query = "SELECT s FROM StudentDue s WHERE s.serviceChargeAmount = :serviceChargeAmount"),
    @NamedQuery(name = "StudentDue.findByVerificationStatus", query = "SELECT s FROM StudentDue s WHERE s.verificationStatus = :verificationStatus"),
    @NamedQuery(name = "StudentDue.findByVerificationToken", query = "SELECT s FROM StudentDue s WHERE s.verificationToken = :verificationToken"),
    @NamedQuery(name = "StudentDue.findByVerifiedBy", query = "SELECT s FROM StudentDue s WHERE s.verifiedBy = :verifiedBy"),
    @NamedQuery(name = "StudentDue.findByVerifiedDate", query = "SELECT s FROM StudentDue s WHERE s.verifiedDate = :verifiedDate"),
    @NamedQuery(name = "StudentDue.findByCreatedDate", query = "SELECT s FROM StudentDue s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "StudentDue.findByModifiedDate", query = "SELECT s FROM StudentDue s WHERE s.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "StudentDue.findByCreatedBy", query = "SELECT s FROM StudentDue s WHERE s.createdBy = :createdBy"),
    @NamedQuery(name = "StudentDue.findByModifiedBy", query = "SELECT s FROM StudentDue s WHERE s.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "StudentDue.findByIsDeleted", query = "SELECT s FROM StudentDue s WHERE s.isDeleted = :isDeleted")})
public class StudentDue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "due_amount")
    private BigDecimal dueAmount;
    @Column(name = "due_amount_lkr")
    private BigDecimal dueAmountLkr;
    @Column(name = "exchange_rate_used")
    private BigDecimal exchangeRateUsed;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "academic_year")
    private String academicYear;
    @Column(name = "semester")
    private String semester;
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column(name = "payee_institution")
    private String payeeInstitution;
    @Lob
    @Column(name = "payee_bank_details")
    private String payeeBankDetails;
    @Column(name = "loan_requested_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loanRequestedDate;
    @Column(name = "loan_approved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loanApprovedDate;
    @Column(name = "loan_disbursed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loanDisbursedDate;
    @Column(name = "scholarship_amount")
    private BigDecimal scholarshipAmount;
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;
    @Column(name = "net_payable_amount")
    private BigDecimal netPayableAmount;
    @Column(name = "service_charge_percentage")
    private BigDecimal serviceChargePercentage;
    @Column(name = "service_charge_amount")
    private BigDecimal serviceChargeAmount;
    @Lob
    @Column(name = "remarks")
    private String remarks;
    @Lob
    @Column(name = "supporting_documents")
    private String supportingDocuments;
    @Column(name = "verification_status")
    private String verificationStatus;
    @Column(name = "verification_token")
    private String verificationToken;
    @Column(name = "verified_by")
    private Integer verifiedBy;
    @Column(name = "verified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verifiedDate;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "modified_by")
    private Integer modifiedBy;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Currency currencyId;
    @JoinColumn(name = "due_category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DueCategory dueCategoryId;
    @JoinColumn(name = "loan_customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanCustomer loanCustomerId;
    @JoinColumn(name = "loan_manager_id", referencedColumnName = "id")
    @ManyToOne
    private LoanManager loanManagerId;

    public StudentDue() {
    }

    public StudentDue(Integer id) {
        this.id = id;
    }

    public StudentDue(Integer id, BigDecimal dueAmount) {
        this.id = id;
        this.dueAmount = dueAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(BigDecimal dueAmount) {
        this.dueAmount = dueAmount;
    }

    public BigDecimal getDueAmountLkr() {
        return dueAmountLkr;
    }

    public void setDueAmountLkr(BigDecimal dueAmountLkr) {
        this.dueAmountLkr = dueAmountLkr;
    }

    public BigDecimal getExchangeRateUsed() {
        return exchangeRateUsed;
    }

    public void setExchangeRateUsed(BigDecimal exchangeRateUsed) {
        this.exchangeRateUsed = exchangeRateUsed;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPayeeInstitution() {
        return payeeInstitution;
    }

    public void setPayeeInstitution(String payeeInstitution) {
        this.payeeInstitution = payeeInstitution;
    }

    public String getPayeeBankDetails() {
        return payeeBankDetails;
    }

    public void setPayeeBankDetails(String payeeBankDetails) {
        this.payeeBankDetails = payeeBankDetails;
    }

    public Date getLoanRequestedDate() {
        return loanRequestedDate;
    }

    public void setLoanRequestedDate(Date loanRequestedDate) {
        this.loanRequestedDate = loanRequestedDate;
    }

    public Date getLoanApprovedDate() {
        return loanApprovedDate;
    }

    public void setLoanApprovedDate(Date loanApprovedDate) {
        this.loanApprovedDate = loanApprovedDate;
    }

    public Date getLoanDisbursedDate() {
        return loanDisbursedDate;
    }

    public void setLoanDisbursedDate(Date loanDisbursedDate) {
        this.loanDisbursedDate = loanDisbursedDate;
    }

    public BigDecimal getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(BigDecimal scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getNetPayableAmount() {
        return netPayableAmount;
    }

    public void setNetPayableAmount(BigDecimal netPayableAmount) {
        this.netPayableAmount = netPayableAmount;
    }

    public BigDecimal getServiceChargePercentage() {
        return serviceChargePercentage;
    }

    public void setServiceChargePercentage(BigDecimal serviceChargePercentage) {
        this.serviceChargePercentage = serviceChargePercentage;
    }

    public BigDecimal getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setServiceChargeAmount(BigDecimal serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSupportingDocuments() {
        return supportingDocuments;
    }

    public void setSupportingDocuments(String supportingDocuments) {
        this.supportingDocuments = supportingDocuments;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Integer getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(Integer verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Date getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Date verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    public DueCategory getDueCategoryId() {
        return dueCategoryId;
    }

    public void setDueCategoryId(DueCategory dueCategoryId) {
        this.dueCategoryId = dueCategoryId;
    }

    public LoanCustomer getLoanCustomerId() {
        return loanCustomerId;
    }

    public void setLoanCustomerId(LoanCustomer loanCustomerId) {
        this.loanCustomerId = loanCustomerId;
    }

    public LoanManager getLoanManagerId() {
        return loanManagerId;
    }

    public void setLoanManagerId(LoanManager loanManagerId) {
        this.loanManagerId = loanManagerId;
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
        if (!(object instanceof StudentDue)) {
            return false;
        }
        StudentDue other = (StudentDue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.StudentDue[ id=" + id + " ]";
    }
    
}
