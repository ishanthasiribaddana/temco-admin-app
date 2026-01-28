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
@Table(name = "loan_manager")
@NamedQueries({
    @NamedQuery(name = "LoanManager.findAll", query = "SELECT l FROM LoanManager l"),
    @NamedQuery(name = "LoanManager.findById", query = "SELECT l FROM LoanManager l WHERE l.id = :id"),
    @NamedQuery(name = "LoanManager.findByReferenceId", query = "SELECT l FROM LoanManager l WHERE l.referenceId = :referenceId"),
    @NamedQuery(name = "LoanManager.findByLoanCapitalAmount", query = "SELECT l FROM LoanManager l WHERE l.loanCapitalAmount = :loanCapitalAmount"),
    @NamedQuery(name = "LoanManager.findByTotalInterest", query = "SELECT l FROM LoanManager l WHERE l.totalInterest = :totalInterest"),
    @NamedQuery(name = "LoanManager.findByMonthlyInstallement", query = "SELECT l FROM LoanManager l WHERE l.monthlyInstallement = :monthlyInstallement"),
    @NamedQuery(name = "LoanManager.findByDate", query = "SELECT l FROM LoanManager l WHERE l.date = :date"),
    @NamedQuery(name = "LoanManager.findByUpdatedAt", query = "SELECT l FROM LoanManager l WHERE l.updatedAt = :updatedAt"),
    @NamedQuery(name = "LoanManager.findByVerificationToke", query = "SELECT l FROM LoanManager l WHERE l.verificationToke = :verificationToke")})
public class LoanManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "reference_id")
    private String referenceId;
    @Basic(optional = false)
    @Column(name = "loan_capital_amount")
    private double loanCapitalAmount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_interest")
    private Double totalInterest;
    @Basic(optional = false)
    @Column(name = "monthly_installement")
    private double monthlyInstallement;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Basic(optional = false)
    @Column(name = "verification_toke")
    private String verificationToke;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanManagerId")
    private Collection<InterestManager> interestManagerCollection;
    @OneToMany(mappedBy = "loanManagerId")
    private Collection<StudentDue> studentDueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanManagerId")
    private Collection<LoanStatusManager> loanStatusManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanManagerId")
    private Collection<LoanDocumentsScheduler> loanDocumentsSchedulerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanManagerId")
    private Collection<LoanPaymentHistory> loanPaymentHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanManagerId")
    private Collection<LoanInstallementPlan> loanInstallementPlanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanManagerId")
    private Collection<LoanApplicantHasBranch> loanApplicantHasBranchCollection;
    @JoinColumn(name = "loan_applicant_and_gurantors_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanApplicantGurantor loanApplicantAndGurantorsId;
    @JoinColumn(name = "member_bank_accounts_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MemberBankAccounts memberBankAccountsId;
    @JoinColumn(name = "penalty_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Penalty penaltyId;
    @JoinColumn(name = "repayment_period_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RepaymentPeriod repaymentPeriodId;

    public LoanManager() {
    }

    public LoanManager(Integer id) {
        this.id = id;
    }

    public LoanManager(Integer id, double loanCapitalAmount, double monthlyInstallement, String verificationToke) {
        this.id = id;
        this.loanCapitalAmount = loanCapitalAmount;
        this.monthlyInstallement = monthlyInstallement;
        this.verificationToke = verificationToke;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public double getLoanCapitalAmount() {
        return loanCapitalAmount;
    }

    public void setLoanCapitalAmount(double loanCapitalAmount) {
        this.loanCapitalAmount = loanCapitalAmount;
    }

    public Double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(Double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public double getMonthlyInstallement() {
        return monthlyInstallement;
    }

    public void setMonthlyInstallement(double monthlyInstallement) {
        this.monthlyInstallement = monthlyInstallement;
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

    public String getVerificationToke() {
        return verificationToke;
    }

    public void setVerificationToke(String verificationToke) {
        this.verificationToke = verificationToke;
    }

    public Collection<InterestManager> getInterestManagerCollection() {
        return interestManagerCollection;
    }

    public void setInterestManagerCollection(Collection<InterestManager> interestManagerCollection) {
        this.interestManagerCollection = interestManagerCollection;
    }

    public Collection<StudentDue> getStudentDueCollection() {
        return studentDueCollection;
    }

    public void setStudentDueCollection(Collection<StudentDue> studentDueCollection) {
        this.studentDueCollection = studentDueCollection;
    }

    public Collection<LoanStatusManager> getLoanStatusManagerCollection() {
        return loanStatusManagerCollection;
    }

    public void setLoanStatusManagerCollection(Collection<LoanStatusManager> loanStatusManagerCollection) {
        this.loanStatusManagerCollection = loanStatusManagerCollection;
    }

    public Collection<LoanDocumentsScheduler> getLoanDocumentsSchedulerCollection() {
        return loanDocumentsSchedulerCollection;
    }

    public void setLoanDocumentsSchedulerCollection(Collection<LoanDocumentsScheduler> loanDocumentsSchedulerCollection) {
        this.loanDocumentsSchedulerCollection = loanDocumentsSchedulerCollection;
    }

    public Collection<LoanPaymentHistory> getLoanPaymentHistoryCollection() {
        return loanPaymentHistoryCollection;
    }

    public void setLoanPaymentHistoryCollection(Collection<LoanPaymentHistory> loanPaymentHistoryCollection) {
        this.loanPaymentHistoryCollection = loanPaymentHistoryCollection;
    }

    public Collection<LoanInstallementPlan> getLoanInstallementPlanCollection() {
        return loanInstallementPlanCollection;
    }

    public void setLoanInstallementPlanCollection(Collection<LoanInstallementPlan> loanInstallementPlanCollection) {
        this.loanInstallementPlanCollection = loanInstallementPlanCollection;
    }

    public Collection<LoanApplicantHasBranch> getLoanApplicantHasBranchCollection() {
        return loanApplicantHasBranchCollection;
    }

    public void setLoanApplicantHasBranchCollection(Collection<LoanApplicantHasBranch> loanApplicantHasBranchCollection) {
        this.loanApplicantHasBranchCollection = loanApplicantHasBranchCollection;
    }

    public LoanApplicantGurantor getLoanApplicantAndGurantorsId() {
        return loanApplicantAndGurantorsId;
    }

    public void setLoanApplicantAndGurantorsId(LoanApplicantGurantor loanApplicantAndGurantorsId) {
        this.loanApplicantAndGurantorsId = loanApplicantAndGurantorsId;
    }

    public MemberBankAccounts getMemberBankAccountsId() {
        return memberBankAccountsId;
    }

    public void setMemberBankAccountsId(MemberBankAccounts memberBankAccountsId) {
        this.memberBankAccountsId = memberBankAccountsId;
    }

    public Penalty getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(Penalty penaltyId) {
        this.penaltyId = penaltyId;
    }

    public RepaymentPeriod getRepaymentPeriodId() {
        return repaymentPeriodId;
    }

    public void setRepaymentPeriodId(RepaymentPeriod repaymentPeriodId) {
        this.repaymentPeriodId = repaymentPeriodId;
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
        if (!(object instanceof LoanManager)) {
            return false;
        }
        LoanManager other = (LoanManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanManager[ id=" + id + " ]";
    }
    
}
