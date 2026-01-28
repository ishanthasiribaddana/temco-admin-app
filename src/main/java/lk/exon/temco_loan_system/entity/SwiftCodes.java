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
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "swift_codes")
@NamedQueries({
    @NamedQuery(name = "SwiftCodes.findAll", query = "SELECT s FROM SwiftCodes s"),
    @NamedQuery(name = "SwiftCodes.findById", query = "SELECT s FROM SwiftCodes s WHERE s.id = :id"),
    @NamedQuery(name = "SwiftCodes.findByCode", query = "SELECT s FROM SwiftCodes s WHERE s.code = :code")})
public class SwiftCodes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private String code;
    @OneToMany(mappedBy = "swiftCodesId")
    private Collection<BankAccount> bankAccountCollection;
    @JoinColumn(name = "branch_of_the_bank_id", referencedColumnName = "id")
    @ManyToOne
    private BranchOfTheBank branchOfTheBankId;

    public SwiftCodes() {
    }

    public SwiftCodes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Collection<BankAccount> getBankAccountCollection() {
        return bankAccountCollection;
    }

    public void setBankAccountCollection(Collection<BankAccount> bankAccountCollection) {
        this.bankAccountCollection = bankAccountCollection;
    }

    public BranchOfTheBank getBranchOfTheBankId() {
        return branchOfTheBankId;
    }

    public void setBranchOfTheBankId(BranchOfTheBank branchOfTheBankId) {
        this.branchOfTheBankId = branchOfTheBankId;
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
        if (!(object instanceof SwiftCodes)) {
            return false;
        }
        SwiftCodes other = (SwiftCodes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.SwiftCodes[ id=" + id + " ]";
    }
    
}
