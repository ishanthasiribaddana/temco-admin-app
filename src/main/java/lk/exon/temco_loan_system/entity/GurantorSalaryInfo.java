/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "gurantor_salary_info")
@NamedQueries({
    @NamedQuery(name = "GurantorSalaryInfo.findAll", query = "SELECT g FROM GurantorSalaryInfo g"),
    @NamedQuery(name = "GurantorSalaryInfo.findById", query = "SELECT g FROM GurantorSalaryInfo g WHERE g.gurantorSalaryInfoPK.id = :id"),
    @NamedQuery(name = "GurantorSalaryInfo.findBySalary", query = "SELECT g FROM GurantorSalaryInfo g WHERE g.salary = :salary"),
    @NamedQuery(name = "GurantorSalaryInfo.findByGurantorManagerId", query = "SELECT g FROM GurantorSalaryInfo g WHERE g.gurantorSalaryInfoPK.gurantorManagerId = :gurantorManagerId")})
public class GurantorSalaryInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GurantorSalaryInfoPK gurantorSalaryInfoPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salary")
    private Double salary;
    @JoinColumn(name = "gurantor_manager_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GurantorManager gurantorManager;

    public GurantorSalaryInfo() {
    }

    public GurantorSalaryInfo(GurantorSalaryInfoPK gurantorSalaryInfoPK) {
        this.gurantorSalaryInfoPK = gurantorSalaryInfoPK;
    }

    public GurantorSalaryInfo(int id, int gurantorManagerId) {
        this.gurantorSalaryInfoPK = new GurantorSalaryInfoPK(id, gurantorManagerId);
    }

    public GurantorSalaryInfoPK getGurantorSalaryInfoPK() {
        return gurantorSalaryInfoPK;
    }

    public void setGurantorSalaryInfoPK(GurantorSalaryInfoPK gurantorSalaryInfoPK) {
        this.gurantorSalaryInfoPK = gurantorSalaryInfoPK;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public GurantorManager getGurantorManager() {
        return gurantorManager;
    }

    public void setGurantorManager(GurantorManager gurantorManager) {
        this.gurantorManager = gurantorManager;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gurantorSalaryInfoPK != null ? gurantorSalaryInfoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GurantorSalaryInfo)) {
            return false;
        }
        GurantorSalaryInfo other = (GurantorSalaryInfo) object;
        if ((this.gurantorSalaryInfoPK == null && other.gurantorSalaryInfoPK != null) || (this.gurantorSalaryInfoPK != null && !this.gurantorSalaryInfoPK.equals(other.gurantorSalaryInfoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GurantorSalaryInfo[ gurantorSalaryInfoPK=" + gurantorSalaryInfoPK + " ]";
    }
    
}
