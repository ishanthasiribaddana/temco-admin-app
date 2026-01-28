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
@Table(name = "currency_rate_type")
@NamedQueries({
    @NamedQuery(name = "CurrencyRateType.findAll", query = "SELECT c FROM CurrencyRateType c"),
    @NamedQuery(name = "CurrencyRateType.findById", query = "SELECT c FROM CurrencyRateType c WHERE c.id = :id"),
    @NamedQuery(name = "CurrencyRateType.findByName", query = "SELECT c FROM CurrencyRateType c WHERE c.name = :name")})
public class CurrencyRateType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencyRateTypeId")
    private Collection<CurrencyRates> currencyRatesCollection;

    public CurrencyRateType() {
    }

    public CurrencyRateType(Integer id) {
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

    public Collection<CurrencyRates> getCurrencyRatesCollection() {
        return currencyRatesCollection;
    }

    public void setCurrencyRatesCollection(Collection<CurrencyRates> currencyRatesCollection) {
        this.currencyRatesCollection = currencyRatesCollection;
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
        if (!(object instanceof CurrencyRateType)) {
            return false;
        }
        CurrencyRateType other = (CurrencyRateType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.CurrencyRateType[ id=" + id + " ]";
    }
    
}
