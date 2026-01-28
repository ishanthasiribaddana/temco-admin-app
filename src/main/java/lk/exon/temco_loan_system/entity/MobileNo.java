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
@Table(name = "mobile_no")
@NamedQueries({
    @NamedQuery(name = "MobileNo.findAll", query = "SELECT m FROM MobileNo m"),
    @NamedQuery(name = "MobileNo.findById", query = "SELECT m FROM MobileNo m WHERE m.id = :id"),
    @NamedQuery(name = "MobileNo.findByMobileNo", query = "SELECT m FROM MobileNo m WHERE m.mobileNo = :mobileNo")})
public class MobileNo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "mobile_no")
    private String mobileNo;
    @JoinColumn(name = "loan_customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanCustomer loanCustomerId;
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Priority priorityId;

    public MobileNo() {
    }

    public MobileNo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LoanCustomer getLoanCustomerId() {
        return loanCustomerId;
    }

    public void setLoanCustomerId(LoanCustomer loanCustomerId) {
        this.loanCustomerId = loanCustomerId;
    }

    public Priority getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Priority priorityId) {
        this.priorityId = priorityId;
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
        if (!(object instanceof MobileNo)) {
            return false;
        }
        MobileNo other = (MobileNo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.MobileNo[ id=" + id + " ]";
    }
    
}
