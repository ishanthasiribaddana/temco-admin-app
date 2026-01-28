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
@Table(name = "factoring_fee")
@NamedQueries({
    @NamedQuery(name = "FactoringFee.findAll", query = "SELECT f FROM FactoringFee f"),
    @NamedQuery(name = "FactoringFee.findById", query = "SELECT f FROM FactoringFee f WHERE f.id = :id"),
    @NamedQuery(name = "FactoringFee.findByValue", query = "SELECT f FROM FactoringFee f WHERE f.value = :value")})
public class FactoringFee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factoringFeeId")
    private Collection<BranchHasFactoringFee> branchHasFactoringFeeCollection;

    public FactoringFee() {
    }

    public FactoringFee(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Collection<BranchHasFactoringFee> getBranchHasFactoringFeeCollection() {
        return branchHasFactoringFeeCollection;
    }

    public void setBranchHasFactoringFeeCollection(Collection<BranchHasFactoringFee> branchHasFactoringFeeCollection) {
        this.branchHasFactoringFeeCollection = branchHasFactoringFeeCollection;
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
        if (!(object instanceof FactoringFee)) {
            return false;
        }
        FactoringFee other = (FactoringFee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.FactoringFee[ id=" + id + " ]";
    }
    
}
