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
@Table(name = "employement_type")
@NamedQueries({
    @NamedQuery(name = "EmployementType.findAll", query = "SELECT e FROM EmployementType e"),
    @NamedQuery(name = "EmployementType.findById", query = "SELECT e FROM EmployementType e WHERE e.id = :id"),
    @NamedQuery(name = "EmployementType.findByType", query = "SELECT e FROM EmployementType e WHERE e.type = :type")})
public class EmployementType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employementTypeId")
    private Collection<EmployementTypeHistory> employementTypeHistoryCollection;

    public EmployementType() {
    }

    public EmployementType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<EmployementTypeHistory> getEmployementTypeHistoryCollection() {
        return employementTypeHistoryCollection;
    }

    public void setEmployementTypeHistoryCollection(Collection<EmployementTypeHistory> employementTypeHistoryCollection) {
        this.employementTypeHistoryCollection = employementTypeHistoryCollection;
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
        if (!(object instanceof EmployementType)) {
            return false;
        }
        EmployementType other = (EmployementType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.EmployementType[ id=" + id + " ]";
    }
    
}
