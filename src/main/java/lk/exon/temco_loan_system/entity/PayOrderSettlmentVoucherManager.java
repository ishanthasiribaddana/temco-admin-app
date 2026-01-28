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
@Table(name = "pay_order_settlment_voucher_manager")
@NamedQueries({
    @NamedQuery(name = "PayOrderSettlmentVoucherManager.findAll", query = "SELECT p FROM PayOrderSettlmentVoucherManager p"),
    @NamedQuery(name = "PayOrderSettlmentVoucherManager.findById", query = "SELECT p FROM PayOrderSettlmentVoucherManager p WHERE p.id = :id"),
    @NamedQuery(name = "PayOrderSettlmentVoucherManager.findByDate", query = "SELECT p FROM PayOrderSettlmentVoucherManager p WHERE p.date = :date")})
public class PayOrderSettlmentVoucherManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "pay_order_settlement_guide_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PayOrderSettlementGuide payOrderSettlementGuideId;
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Voucher voucherId;

    public PayOrderSettlmentVoucherManager() {
    }

    public PayOrderSettlmentVoucherManager(Integer id) {
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

    public PayOrderSettlementGuide getPayOrderSettlementGuideId() {
        return payOrderSettlementGuideId;
    }

    public void setPayOrderSettlementGuideId(PayOrderSettlementGuide payOrderSettlementGuideId) {
        this.payOrderSettlementGuideId = payOrderSettlementGuideId;
    }

    public Voucher getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Voucher voucherId) {
        this.voucherId = voucherId;
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
        if (!(object instanceof PayOrderSettlmentVoucherManager)) {
            return false;
        }
        PayOrderSettlmentVoucherManager other = (PayOrderSettlmentVoucherManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.PayOrderSettlmentVoucherManager[ id=" + id + " ]";
    }
    
}
