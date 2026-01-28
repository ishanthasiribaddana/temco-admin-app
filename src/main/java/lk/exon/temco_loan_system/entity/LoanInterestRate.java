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
@Table(name = "loan_interest_rate")
@NamedQueries({
    @NamedQuery(name = "LoanInterestRate.findAll", query = "SELECT l FROM LoanInterestRate l"),
    @NamedQuery(name = "LoanInterestRate.findById", query = "SELECT l FROM LoanInterestRate l WHERE l.id = :id"),
    @NamedQuery(name = "LoanInterestRate.findByRate", query = "SELECT l FROM LoanInterestRate l WHERE l.rate = :rate"),
    @NamedQuery(name = "LoanInterestRate.findByCreatedDate", query = "SELECT l FROM LoanInterestRate l WHERE l.createdDate = :createdDate")})
public class LoanInterestRate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rate")
    private Double rate;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "loan_term_id", referencedColumnName = "id")
    @ManyToOne
    private LoanTerm loanTermId;
    @JoinColumn(name = "loan_type_id", referencedColumnName = "id")
    @ManyToOne
    private LoanType loanTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanInterestRateId")
    private Collection<InterestManager> interestManagerCollection;

    public LoanInterestRate() {
    }

    public LoanInterestRate(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public LoanTerm getLoanTermId() {
        return loanTermId;
    }

    public void setLoanTermId(LoanTerm loanTermId) {
        this.loanTermId = loanTermId;
    }

    public LoanType getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(LoanType loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public Collection<InterestManager> getInterestManagerCollection() {
        return interestManagerCollection;
    }

    public void setInterestManagerCollection(Collection<InterestManager> interestManagerCollection) {
        this.interestManagerCollection = interestManagerCollection;
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
        if (!(object instanceof LoanInterestRate)) {
            return false;
        }
        LoanInterestRate other = (LoanInterestRate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanInterestRate[ id=" + id + " ]";
    }
    
}
