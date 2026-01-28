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
@Table(name = "loan_type")
@NamedQueries({
    @NamedQuery(name = "LoanType.findAll", query = "SELECT l FROM LoanType l"),
    @NamedQuery(name = "LoanType.findById", query = "SELECT l FROM LoanType l WHERE l.id = :id"),
    @NamedQuery(name = "LoanType.findByName", query = "SELECT l FROM LoanType l WHERE l.name = :name"),
    @NamedQuery(name = "LoanType.findByCreatedDate", query = "SELECT l FROM LoanType l WHERE l.createdDate = :createdDate")})
public class LoanType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToMany(mappedBy = "loanTypeId")
    private Collection<LoanInterestRate> loanInterestRateCollection;

    public LoanType() {
    }

    public LoanType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Collection<LoanInterestRate> getLoanInterestRateCollection() {
        return loanInterestRateCollection;
    }

    public void setLoanInterestRateCollection(Collection<LoanInterestRate> loanInterestRateCollection) {
        this.loanInterestRateCollection = loanInterestRateCollection;
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
        if (!(object instanceof LoanType)) {
            return false;
        }
        LoanType other = (LoanType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanType[ id=" + id + " ]";
    }
    
}
