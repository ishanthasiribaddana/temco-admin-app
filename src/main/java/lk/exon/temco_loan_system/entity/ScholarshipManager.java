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
@Table(name = "scholarship_manager")
@NamedQueries({
    @NamedQuery(name = "ScholarshipManager.findAll", query = "SELECT s FROM ScholarshipManager s"),
    @NamedQuery(name = "ScholarshipManager.findById", query = "SELECT s FROM ScholarshipManager s WHERE s.id = :id")})
public class ScholarshipManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "loan_customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanCustomer loanCustomerId;
    @JoinColumn(name = "scholarship_catergory_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ScholarshipCatergory scholarshipCatergoryId;

    public ScholarshipManager() {
    }

    public ScholarshipManager(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LoanCustomer getLoanCustomerId() {
        return loanCustomerId;
    }

    public void setLoanCustomerId(LoanCustomer loanCustomerId) {
        this.loanCustomerId = loanCustomerId;
    }

    public ScholarshipCatergory getScholarshipCatergoryId() {
        return scholarshipCatergoryId;
    }

    public void setScholarshipCatergoryId(ScholarshipCatergory scholarshipCatergoryId) {
        this.scholarshipCatergoryId = scholarshipCatergoryId;
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
        if (!(object instanceof ScholarshipManager)) {
            return false;
        }
        ScholarshipManager other = (ScholarshipManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.ScholarshipManager[ id=" + id + " ]";
    }
    
}
