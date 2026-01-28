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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "spot_member")
@NamedQueries({
    @NamedQuery(name = "SpotMember.findAll", query = "SELECT s FROM SpotMember s"),
    @NamedQuery(name = "SpotMember.findById", query = "SELECT s FROM SpotMember s WHERE s.id = :id"),
    @NamedQuery(name = "SpotMember.findByRegisteredDate", query = "SELECT s FROM SpotMember s WHERE s.registeredDate = :registeredDate")})
public class SpotMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "registered_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredDate;
    @OneToMany(mappedBy = "spotMemberId")
    private Collection<BankAccount> bankAccountCollection;
    @OneToMany(mappedBy = "spotMemberId")
    private Collection<Voucher> voucherCollection;
    @JoinColumn(name = "general_user_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeneralUserProfile generalUserProfileId;

    public SpotMember() {
    }

    public SpotMember(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Collection<BankAccount> getBankAccountCollection() {
        return bankAccountCollection;
    }

    public void setBankAccountCollection(Collection<BankAccount> bankAccountCollection) {
        this.bankAccountCollection = bankAccountCollection;
    }

    public Collection<Voucher> getVoucherCollection() {
        return voucherCollection;
    }

    public void setVoucherCollection(Collection<Voucher> voucherCollection) {
        this.voucherCollection = voucherCollection;
    }

    public GeneralUserProfile getGeneralUserProfileId() {
        return generalUserProfileId;
    }

    public void setGeneralUserProfileId(GeneralUserProfile generalUserProfileId) {
        this.generalUserProfileId = generalUserProfileId;
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
        if (!(object instanceof SpotMember)) {
            return false;
        }
        SpotMember other = (SpotMember) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.SpotMember[ id=" + id + " ]";
    }
    
}
