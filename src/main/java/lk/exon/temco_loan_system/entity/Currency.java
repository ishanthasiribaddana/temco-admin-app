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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "currency")
@NamedQueries({
    @NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c"),
    @NamedQuery(name = "Currency.findById", query = "SELECT c FROM Currency c WHERE c.id = :id"),
    @NamedQuery(name = "Currency.findByCurrencyCode", query = "SELECT c FROM Currency c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "Currency.findByCurrencyName", query = "SELECT c FROM Currency c WHERE c.currencyName = :currencyName"),
    @NamedQuery(name = "Currency.findByCurrencySymbol", query = "SELECT c FROM Currency c WHERE c.currencySymbol = :currencySymbol"),
    @NamedQuery(name = "Currency.findByExchangeRateToLkr", query = "SELECT c FROM Currency c WHERE c.exchangeRateToLkr = :exchangeRateToLkr"),
    @NamedQuery(name = "Currency.findByIsActive", query = "SELECT c FROM Currency c WHERE c.isActive = :isActive"),
    @NamedQuery(name = "Currency.findByCreatedDate", query = "SELECT c FROM Currency c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "Currency.findByModifiedDate", query = "SELECT c FROM Currency c WHERE c.modifiedDate = :modifiedDate")})
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "currency_code")
    private String currencyCode;
    @Basic(optional = false)
    @Column(name = "currency_name")
    private String currencyName;
    @Column(name = "currency_symbol")
    private String currencySymbol;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "exchange_rate_to_lkr")
    private BigDecimal exchangeRateToLkr;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyId")
    private Collection<StudentDue> studentDueCollection;

    public Currency() {
    }

    public Currency(Integer id) {
        this.id = id;
    }

    public Currency(Integer id, String currencyCode, String currencyName) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public BigDecimal getExchangeRateToLkr() {
        return exchangeRateToLkr;
    }

    public void setExchangeRateToLkr(BigDecimal exchangeRateToLkr) {
        this.exchangeRateToLkr = exchangeRateToLkr;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Collection<StudentDue> getStudentDueCollection() {
        return studentDueCollection;
    }

    public void setStudentDueCollection(Collection<StudentDue> studentDueCollection) {
        this.studentDueCollection = studentDueCollection;
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
        if (!(object instanceof Currency)) {
            return false;
        }
        Currency other = (Currency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.Currency[ id=" + id + " ]";
    }
    
}
