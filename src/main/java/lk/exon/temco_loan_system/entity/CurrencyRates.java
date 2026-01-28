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
@Table(name = "currency_rates")
@NamedQueries({
    @NamedQuery(name = "CurrencyRates.findAll", query = "SELECT c FROM CurrencyRates c"),
    @NamedQuery(name = "CurrencyRates.findById", query = "SELECT c FROM CurrencyRates c WHERE c.id = :id"),
    @NamedQuery(name = "CurrencyRates.findByEnterValue", query = "SELECT c FROM CurrencyRates c WHERE c.enterValue = :enterValue"),
    @NamedQuery(name = "CurrencyRates.findByDefaultValue", query = "SELECT c FROM CurrencyRates c WHERE c.defaultValue = :defaultValue"),
    @NamedQuery(name = "CurrencyRates.findByLastUpdateTime", query = "SELECT c FROM CurrencyRates c WHERE c.lastUpdateTime = :lastUpdateTime")})
public class CurrencyRates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "enter_value")
    private Double enterValue;
    @Column(name = "default_value")
    private Double defaultValue;
    @Column(name = "last_update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;
    @JoinColumn(name = "currency_rate_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CurrencyRateType currencyRateTypeId;

    public CurrencyRates() {
    }

    public CurrencyRates(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getEnterValue() {
        return enterValue;
    }

    public void setEnterValue(Double enterValue) {
        this.enterValue = enterValue;
    }

    public Double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public CurrencyRateType getCurrencyRateTypeId() {
        return currencyRateTypeId;
    }

    public void setCurrencyRateTypeId(CurrencyRateType currencyRateTypeId) {
        this.currencyRateTypeId = currencyRateTypeId;
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
        if (!(object instanceof CurrencyRates)) {
            return false;
        }
        CurrencyRates other = (CurrencyRates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.CurrencyRates[ id=" + id + " ]";
    }
    
}
