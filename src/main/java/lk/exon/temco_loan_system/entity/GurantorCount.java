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
@Table(name = "gurantor_count")
@NamedQueries({
    @NamedQuery(name = "GurantorCount.findAll", query = "SELECT g FROM GurantorCount g"),
    @NamedQuery(name = "GurantorCount.findById", query = "SELECT g FROM GurantorCount g WHERE g.id = :id"),
    @NamedQuery(name = "GurantorCount.findByValue", query = "SELECT g FROM GurantorCount g WHERE g.value = :value")})
public class GurantorCount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "value")
    private String value;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gurantorCountId")
    private Collection<GurantorManager> gurantorManagerCollection;

    public GurantorCount() {
    }

    public GurantorCount(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Collection<GurantorManager> getGurantorManagerCollection() {
        return gurantorManagerCollection;
    }

    public void setGurantorManagerCollection(Collection<GurantorManager> gurantorManagerCollection) {
        this.gurantorManagerCollection = gurantorManagerCollection;
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
        if (!(object instanceof GurantorCount)) {
            return false;
        }
        GurantorCount other = (GurantorCount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GurantorCount[ id=" + id + " ]";
    }
    
}
