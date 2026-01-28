/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Embeddable
public class GurantorSalaryInfoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "gurantor_manager_id")
    private int gurantorManagerId;

    public GurantorSalaryInfoPK() {
    }

    public GurantorSalaryInfoPK(int id, int gurantorManagerId) {
        this.id = id;
        this.gurantorManagerId = gurantorManagerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGurantorManagerId() {
        return gurantorManagerId;
    }

    public void setGurantorManagerId(int gurantorManagerId) {
        this.gurantorManagerId = gurantorManagerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) gurantorManagerId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GurantorSalaryInfoPK)) {
            return false;
        }
        GurantorSalaryInfoPK other = (GurantorSalaryInfoPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.gurantorManagerId != other.gurantorManagerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GurantorSalaryInfoPK[ id=" + id + ", gurantorManagerId=" + gurantorManagerId + " ]";
    }
    
}
