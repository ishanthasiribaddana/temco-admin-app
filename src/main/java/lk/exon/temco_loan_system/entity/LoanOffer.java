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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "loan_offer")
@NamedQueries({
    @NamedQuery(name = "LoanOffer.findAll", query = "SELECT l FROM LoanOffer l"),
    @NamedQuery(name = "LoanOffer.findById", query = "SELECT l FROM LoanOffer l WHERE l.id = :id")})
public class LoanOffer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Loan loanId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanOfferId")
    private Collection<OfferManager> offerManagerCollection;

    public LoanOffer() {
    }

    public LoanOffer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Loan getLoanId() {
        return loanId;
    }

    public void setLoanId(Loan loanId) {
        this.loanId = loanId;
    }

    public Collection<OfferManager> getOfferManagerCollection() {
        return offerManagerCollection;
    }

    public void setOfferManagerCollection(Collection<OfferManager> offerManagerCollection) {
        this.offerManagerCollection = offerManagerCollection;
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
        if (!(object instanceof LoanOffer)) {
            return false;
        }
        LoanOffer other = (LoanOffer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanOffer[ id=" + id + " ]";
    }
    
}
