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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "customer_response_history")
@NamedQueries({
    @NamedQuery(name = "CustomerResponseHistory.findAll", query = "SELECT c FROM CustomerResponseHistory c"),
    @NamedQuery(name = "CustomerResponseHistory.findById", query = "SELECT c FROM CustomerResponseHistory c WHERE c.id = :id"),
    @NamedQuery(name = "CustomerResponseHistory.findByDate", query = "SELECT c FROM CustomerResponseHistory c WHERE c.date = :date")})
public class CustomerResponseHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "offer_manager_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OfferManager offerManagerId;
    @JoinColumn(name = "response_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ResponseStatus responseStatusId;

    public CustomerResponseHistory() {
    }

    public CustomerResponseHistory(Integer id) {
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

    public OfferManager getOfferManagerId() {
        return offerManagerId;
    }

    public void setOfferManagerId(OfferManager offerManagerId) {
        this.offerManagerId = offerManagerId;
    }

    public ResponseStatus getResponseStatusId() {
        return responseStatusId;
    }

    public void setResponseStatusId(ResponseStatus responseStatusId) {
        this.responseStatusId = responseStatusId;
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
        if (!(object instanceof CustomerResponseHistory)) {
            return false;
        }
        CustomerResponseHistory other = (CustomerResponseHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.CustomerResponseHistory[ id=" + id + " ]";
    }
    
}
