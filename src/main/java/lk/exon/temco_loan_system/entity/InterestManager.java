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
@Table(name = "interest_manager")
@NamedQueries({
    @NamedQuery(name = "InterestManager.findAll", query = "SELECT i FROM InterestManager i"),
    @NamedQuery(name = "InterestManager.findById", query = "SELECT i FROM InterestManager i WHERE i.id = :id")})
public class InterestManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "Loan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Loan loanid;
    @JoinColumn(name = "loan_interest_rate_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanInterestRate loanInterestRateId;
    @JoinColumn(name = "loan_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanManager loanManagerId;

    public InterestManager() {
    }

    public InterestManager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Loan getLoanid() {
        return loanid;
    }

    public void setLoanid(Loan loanid) {
        this.loanid = loanid;
    }

    public LoanInterestRate getLoanInterestRateId() {
        return loanInterestRateId;
    }

    public void setLoanInterestRateId(LoanInterestRate loanInterestRateId) {
        this.loanInterestRateId = loanInterestRateId;
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
        if (!(object instanceof InterestManager)) {
            return false;
        }
        InterestManager other = (InterestManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.InterestManager[ id=" + id + " ]";
    }
    
}
