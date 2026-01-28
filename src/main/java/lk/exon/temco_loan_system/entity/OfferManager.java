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
@Table(name = "offer_manager")
@NamedQueries({
    @NamedQuery(name = "OfferManager.findAll", query = "SELECT o FROM OfferManager o"),
    @NamedQuery(name = "OfferManager.findById", query = "SELECT o FROM OfferManager o WHERE o.id = :id"),
    @NamedQuery(name = "OfferManager.findByDate", query = "SELECT o FROM OfferManager o WHERE o.date = :date"),
    @NamedQuery(name = "OfferManager.findByIsAccepted", query = "SELECT o FROM OfferManager o WHERE o.isAccepted = :isAccepted")})
public class OfferManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "is_accepted")
    private Short isAccepted;
    @JoinColumn(name = "loan_customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanCustomer loanCustomerId;
    @JoinColumn(name = "loan_offer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LoanOffer loanOfferId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offerManagerId")
    private Collection<CustomerResponseHistory> customerResponseHistoryCollection;

    public OfferManager() {
    }

    public OfferManager(Integer id) {
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

    public Short getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Short isAccepted) {
        this.isAccepted = isAccepted;
    }

    public LoanCustomer getLoanCustomerId() {
        return loanCustomerId;
    }

    public void setLoanCustomerId(LoanCustomer loanCustomerId) {
        this.loanCustomerId = loanCustomerId;
    }

    public LoanOffer getLoanOfferId() {
        return loanOfferId;
    }

    public void setLoanOfferId(LoanOffer loanOfferId) {
        this.loanOfferId = loanOfferId;
    }

    public Collection<CustomerResponseHistory> getCustomerResponseHistoryCollection() {
        return customerResponseHistoryCollection;
    }

    public void setCustomerResponseHistoryCollection(Collection<CustomerResponseHistory> customerResponseHistoryCollection) {
        this.customerResponseHistoryCollection = customerResponseHistoryCollection;
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
        if (!(object instanceof OfferManager)) {
            return false;
        }
        OfferManager other = (OfferManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.OfferManager[ id=" + id + " ]";
    }
    
}
