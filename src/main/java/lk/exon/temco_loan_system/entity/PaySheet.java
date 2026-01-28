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
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "pay_sheet")
@NamedQueries({
    @NamedQuery(name = "PaySheet.findAll", query = "SELECT p FROM PaySheet p"),
    @NamedQuery(name = "PaySheet.findById", query = "SELECT p FROM PaySheet p WHERE p.id = :id"),
    @NamedQuery(name = "PaySheet.findByEmploymentTypeId", query = "SELECT p FROM PaySheet p WHERE p.employmentTypeId = :employmentTypeId"),
    @NamedQuery(name = "PaySheet.findByGrossIncome", query = "SELECT p FROM PaySheet p WHERE p.grossIncome = :grossIncome"),
    @NamedQuery(name = "PaySheet.findByNetIncome", query = "SELECT p FROM PaySheet p WHERE p.netIncome = :netIncome"),
    @NamedQuery(name = "PaySheet.findByDeductions", query = "SELECT p FROM PaySheet p WHERE p.deductions = :deductions"),
    @NamedQuery(name = "PaySheet.findByOtherIncome", query = "SELECT p FROM PaySheet p WHERE p.otherIncome = :otherIncome")})
public class PaySheet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "employment_type_id")
    private int employmentTypeId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "gross_income")
    private Double grossIncome;
    @Column(name = "net_income")
    private Double netIncome;
    @Column(name = "deductions")
    private Double deductions;
    @Column(name = "other_income")
    private Double otherIncome;
    @JoinColumn(name = "loan_applicant_gurantor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanApplicantGurantor loanApplicantGurantorId;

    public PaySheet() {
    }

    public PaySheet(Integer id) {
        this.id = id;
    }

    public PaySheet(Integer id, int employmentTypeId) {
        this.id = id;
        this.employmentTypeId = employmentTypeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEmploymentTypeId() {
        return employmentTypeId;
    }

    public void setEmploymentTypeId(int employmentTypeId) {
        this.employmentTypeId = employmentTypeId;
    }

    public Double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(Double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public Double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(Double netIncome) {
        this.netIncome = netIncome;
    }

    public Double getDeductions() {
        return deductions;
    }

    public void setDeductions(Double deductions) {
        this.deductions = deductions;
    }

    public Double getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Double otherIncome) {
        this.otherIncome = otherIncome;
    }

    public LoanApplicantGurantor getLoanApplicantGurantorId() {
        return loanApplicantGurantorId;
    }

    public void setLoanApplicantGurantorId(LoanApplicantGurantor loanApplicantGurantorId) {
        this.loanApplicantGurantorId = loanApplicantGurantorId;
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
        if (!(object instanceof PaySheet)) {
            return false;
        }
        PaySheet other = (PaySheet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.PaySheet[ id=" + id + " ]";
    }
    
}
