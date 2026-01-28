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
@Table(name = "intake")
@NamedQueries({
    @NamedQuery(name = "Intake.findAll", query = "SELECT i FROM Intake i"),
    @NamedQuery(name = "Intake.findById", query = "SELECT i FROM Intake i WHERE i.id = :id"),
    @NamedQuery(name = "Intake.findByIntakeId", query = "SELECT i FROM Intake i WHERE i.intakeId = :intakeId"),
    @NamedQuery(name = "Intake.findByName", query = "SELECT i FROM Intake i WHERE i.name = :name")})
public class Intake implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "intake_id")
    private Integer intakeId;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intakeId")
    private Collection<IntakeManager> intakeManagerCollection;

    public Intake() {
    }

    public Intake(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(Integer intakeId) {
        this.intakeId = intakeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<IntakeManager> getIntakeManagerCollection() {
        return intakeManagerCollection;
    }

    public void setIntakeManagerCollection(Collection<IntakeManager> intakeManagerCollection) {
        this.intakeManagerCollection = intakeManagerCollection;
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
        if (!(object instanceof Intake)) {
            return false;
        }
        Intake other = (Intake) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.Intake[ id=" + id + " ]";
    }
    
}
