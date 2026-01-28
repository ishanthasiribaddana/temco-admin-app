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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "response_status")
@NamedQueries({
    @NamedQuery(name = "ResponseStatus.findAll", query = "SELECT r FROM ResponseStatus r"),
    @NamedQuery(name = "ResponseStatus.findById", query = "SELECT r FROM ResponseStatus r WHERE r.id = :id"),
    @NamedQuery(name = "ResponseStatus.findByStatus", query = "SELECT r FROM ResponseStatus r WHERE r.status = :status")})
public class ResponseStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responseStatusId")
    private Collection<CustomerResponseHistory> customerResponseHistoryCollection;

    public ResponseStatus() {
    }

    public ResponseStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof ResponseStatus)) {
            return false;
        }
        ResponseStatus other = (ResponseStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.ResponseStatus[ id=" + id + " ]";
    }
    
}
