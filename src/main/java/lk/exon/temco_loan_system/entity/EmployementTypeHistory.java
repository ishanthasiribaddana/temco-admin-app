/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "employement_type_history")
@NamedQueries({
    @NamedQuery(name = "EmployementTypeHistory.findAll", query = "SELECT e FROM EmployementTypeHistory e"),
    @NamedQuery(name = "EmployementTypeHistory.findById", query = "SELECT e FROM EmployementTypeHistory e WHERE e.id = :id"),
    @NamedQuery(name = "EmployementTypeHistory.findByDate", query = "SELECT e FROM EmployementTypeHistory e WHERE e.date = :date")})
public class EmployementTypeHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "employement_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EmployementType employementTypeId;
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Member1 memberId;

    public EmployementTypeHistory() {
    }

    public EmployementTypeHistory(Integer id) {
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

    public EmployementType getEmployementTypeId() {
        return employementTypeId;
    }

    public void setEmployementTypeId(EmployementType employementTypeId) {
        this.employementTypeId = employementTypeId;
    }

    public Member1 getMemberId() {
        return memberId;
    }

    public void setMemberId(Member1 memberId) {
        this.memberId = memberId;
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
        if (!(object instanceof EmployementTypeHistory)) {
            return false;
        }
        EmployementTypeHistory other = (EmployementTypeHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.EmployementTypeHistory[ id=" + id + " ]";
    }
    
}
