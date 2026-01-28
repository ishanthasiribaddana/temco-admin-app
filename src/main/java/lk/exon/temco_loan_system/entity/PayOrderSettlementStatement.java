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
@Table(name = "pay_order_settlement_statement")
@NamedQueries({
    @NamedQuery(name = "PayOrderSettlementStatement.findAll", query = "SELECT p FROM PayOrderSettlementStatement p"),
    @NamedQuery(name = "PayOrderSettlementStatement.findById", query = "SELECT p FROM PayOrderSettlementStatement p WHERE p.id = :id"),
    @NamedQuery(name = "PayOrderSettlementStatement.findByDate", query = "SELECT p FROM PayOrderSettlementStatement p WHERE p.date = :date"),
    @NamedQuery(name = "PayOrderSettlementStatement.findByPaidAmount", query = "SELECT p FROM PayOrderSettlementStatement p WHERE p.paidAmount = :paidAmount"),
    @NamedQuery(name = "PayOrderSettlementStatement.findByBalanceDue", query = "SELECT p FROM PayOrderSettlementStatement p WHERE p.balanceDue = :balanceDue")})
public class PayOrderSettlementStatement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "paid_amount")
    private Double paidAmount;
    @Column(name = "balance_due")
    private Double balanceDue;
    @JoinColumn(name = "pay_order_settlement_statement_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PayOrderSettlementGuide payOrderSettlementStatementId;

    public PayOrderSettlementStatement() {
    }

    public PayOrderSettlementStatement(Integer id) {
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

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(Double balanceDue) {
        this.balanceDue = balanceDue;
    }

    public PayOrderSettlementGuide getPayOrderSettlementStatementId() {
        return payOrderSettlementStatementId;
    }

    public void setPayOrderSettlementStatementId(PayOrderSettlementGuide payOrderSettlementStatementId) {
        this.payOrderSettlementStatementId = payOrderSettlementStatementId;
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
        if (!(object instanceof PayOrderSettlementStatement)) {
            return false;
        }
        PayOrderSettlementStatement other = (PayOrderSettlementStatement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.PayOrderSettlementStatement[ id=" + id + " ]";
    }
    
}
