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
@Table(name = "pay_order_settlement_guide")
@NamedQueries({
    @NamedQuery(name = "PayOrderSettlementGuide.findAll", query = "SELECT p FROM PayOrderSettlementGuide p"),
    @NamedQuery(name = "PayOrderSettlementGuide.findById", query = "SELECT p FROM PayOrderSettlementGuide p WHERE p.id = :id"),
    @NamedQuery(name = "PayOrderSettlementGuide.findByDate", query = "SELECT p FROM PayOrderSettlementGuide p WHERE p.date = :date"),
    @NamedQuery(name = "PayOrderSettlementGuide.findByPrincipalAmount", query = "SELECT p FROM PayOrderSettlementGuide p WHERE p.principalAmount = :principalAmount"),
    @NamedQuery(name = "PayOrderSettlementGuide.findByPaybleDiscountedAmount", query = "SELECT p FROM PayOrderSettlementGuide p WHERE p.paybleDiscountedAmount = :paybleDiscountedAmount"),
    @NamedQuery(name = "PayOrderSettlementGuide.findBySettlementAmount", query = "SELECT p FROM PayOrderSettlementGuide p WHERE p.settlementAmount = :settlementAmount")})
public class PayOrderSettlementGuide implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "principal_amount")
    private Double principalAmount;
    @Column(name = "payble_discounted_amount")
    private Double paybleDiscountedAmount;
    @Column(name = "settlement_amount")
    private Double settlementAmount;
    @JoinColumn(name = "branch_has_factoring_fee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BranchHasFactoringFee branchHasFactoringFeeId;
    @JoinColumn(name = "loan_applicant_has_branch_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanApplicantHasBranch loanApplicantHasBranchId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "payOrderSettlementGuideId")
    private Collection<PayOrderSettlmentVoucherManager> payOrderSettlmentVoucherManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "payOrderSettlementStatementId")
    private Collection<PayOrderSettlementStatement> payOrderSettlementStatementCollection;

    public PayOrderSettlementGuide() {
    }

    public PayOrderSettlementGuide(Integer id) {
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

    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public Double getPaybleDiscountedAmount() {
        return paybleDiscountedAmount;
    }

    public void setPaybleDiscountedAmount(Double paybleDiscountedAmount) {
        this.paybleDiscountedAmount = paybleDiscountedAmount;
    }

    public Double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public BranchHasFactoringFee getBranchHasFactoringFeeId() {
        return branchHasFactoringFeeId;
    }

    public void setBranchHasFactoringFeeId(BranchHasFactoringFee branchHasFactoringFeeId) {
        this.branchHasFactoringFeeId = branchHasFactoringFeeId;
    }

    public LoanApplicantHasBranch getLoanApplicantHasBranchId() {
        return loanApplicantHasBranchId;
    }

    public void setLoanApplicantHasBranchId(LoanApplicantHasBranch loanApplicantHasBranchId) {
        this.loanApplicantHasBranchId = loanApplicantHasBranchId;
    }

    public Collection<PayOrderSettlmentVoucherManager> getPayOrderSettlmentVoucherManagerCollection() {
        return payOrderSettlmentVoucherManagerCollection;
    }

    public void setPayOrderSettlmentVoucherManagerCollection(Collection<PayOrderSettlmentVoucherManager> payOrderSettlmentVoucherManagerCollection) {
        this.payOrderSettlmentVoucherManagerCollection = payOrderSettlmentVoucherManagerCollection;
    }

    public Collection<PayOrderSettlementStatement> getPayOrderSettlementStatementCollection() {
        return payOrderSettlementStatementCollection;
    }

    public void setPayOrderSettlementStatementCollection(Collection<PayOrderSettlementStatement> payOrderSettlementStatementCollection) {
        this.payOrderSettlementStatementCollection = payOrderSettlementStatementCollection;
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
        if (!(object instanceof PayOrderSettlementGuide)) {
            return false;
        }
        PayOrderSettlementGuide other = (PayOrderSettlementGuide) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.PayOrderSettlementGuide[ id=" + id + " ]";
    }
    
}
