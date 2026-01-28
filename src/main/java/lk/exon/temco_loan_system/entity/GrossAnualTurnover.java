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
@Table(name = "gross_anual_turnover")
@NamedQueries({
    @NamedQuery(name = "GrossAnualTurnover.findAll", query = "SELECT g FROM GrossAnualTurnover g"),
    @NamedQuery(name = "GrossAnualTurnover.findById", query = "SELECT g FROM GrossAnualTurnover g WHERE g.id = :id"),
    @NamedQuery(name = "GrossAnualTurnover.findByTurnover", query = "SELECT g FROM GrossAnualTurnover g WHERE g.turnover = :turnover"),
    @NamedQuery(name = "GrossAnualTurnover.findByYear", query = "SELECT g FROM GrossAnualTurnover g WHERE g.year = :year")})
public class GrossAnualTurnover implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "turnover")
    private String turnover;
    @Column(name = "year")
    @Temporal(TemporalType.DATE)
    private Date year;
    @JoinColumn(name = "business_information_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BusinessInformation businessInformationId;

    public GrossAnualTurnover() {
    }

    public GrossAnualTurnover(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public BusinessInformation getBusinessInformationId() {
        return businessInformationId;
    }

    public void setBusinessInformationId(BusinessInformation businessInformationId) {
        this.businessInformationId = businessInformationId;
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
        if (!(object instanceof GrossAnualTurnover)) {
            return false;
        }
        GrossAnualTurnover other = (GrossAnualTurnover) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GrossAnualTurnover[ id=" + id + " ]";
    }
    
}
