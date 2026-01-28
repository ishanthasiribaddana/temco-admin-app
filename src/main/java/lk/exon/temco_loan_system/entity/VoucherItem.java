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
import jakarta.persistence.JoinColumns;
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
@Table(name = "voucher_item")
@NamedQueries({
    @NamedQuery(name = "VoucherItem.findAll", query = "SELECT v FROM VoucherItem v"),
    @NamedQuery(name = "VoucherItem.findById", query = "SELECT v FROM VoucherItem v WHERE v.id = :id"),
    @NamedQuery(name = "VoucherItem.findByAmount", query = "SELECT v FROM VoucherItem v WHERE v.amount = :amount"),
    @NamedQuery(name = "VoucherItem.findByEnteredDate", query = "SELECT v FROM VoucherItem v WHERE v.enteredDate = :enteredDate"),
    @NamedQuery(name = "VoucherItem.findByIsActive", query = "SELECT v FROM VoucherItem v WHERE v.isActive = :isActive")})
public class VoucherItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "entered_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredDate;
    @Column(name = "is_active")
    private Short isActive;
    @JoinColumns({
        @JoinColumn(name = "voucher_id", referencedColumnName = "id"),
        @JoinColumn(name = "voucher_id", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private Voucher voucher;
    @OneToMany(mappedBy = "voucherItemId")
    private Collection<VoucherItem> voucherItemCollection;
    @JoinColumn(name = "voucher_item_id", referencedColumnName = "id")
    @ManyToOne
    private VoucherItem voucherItemId;

    public VoucherItem() {
    }

    public VoucherItem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(Date enteredDate) {
        this.enteredDate = enteredDate;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Collection<VoucherItem> getVoucherItemCollection() {
        return voucherItemCollection;
    }

    public void setVoucherItemCollection(Collection<VoucherItem> voucherItemCollection) {
        this.voucherItemCollection = voucherItemCollection;
    }

    public VoucherItem getVoucherItemId() {
        return voucherItemId;
    }

    public void setVoucherItemId(VoucherItem voucherItemId) {
        this.voucherItemId = voucherItemId;
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
        if (!(object instanceof VoucherItem)) {
            return false;
        }
        VoucherItem other = (VoucherItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.VoucherItem[ id=" + id + " ]";
    }
    
}
