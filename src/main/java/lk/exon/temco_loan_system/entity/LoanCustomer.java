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
@Table(name = "loan_customer")
@NamedQueries({
    @NamedQuery(name = "LoanCustomer.findAll", query = "SELECT l FROM LoanCustomer l"),
    @NamedQuery(name = "LoanCustomer.findById", query = "SELECT l FROM LoanCustomer l WHERE l.id = :id"),
    @NamedQuery(name = "LoanCustomer.findByGupId", query = "SELECT l FROM LoanCustomer l WHERE l.gupId = :gupId"),
    @NamedQuery(name = "LoanCustomer.findByNic", query = "SELECT l FROM LoanCustomer l WHERE l.nic = :nic"),
    @NamedQuery(name = "LoanCustomer.findByFirstName", query = "SELECT l FROM LoanCustomer l WHERE l.firstName = :firstName"),
    @NamedQuery(name = "LoanCustomer.findByLastName", query = "SELECT l FROM LoanCustomer l WHERE l.lastName = :lastName"),
    @NamedQuery(name = "LoanCustomer.findByNameWithInitials", query = "SELECT l FROM LoanCustomer l WHERE l.nameWithInitials = :nameWithInitials"),
    @NamedQuery(name = "LoanCustomer.findByDob", query = "SELECT l FROM LoanCustomer l WHERE l.dob = :dob"),
    @NamedQuery(name = "LoanCustomer.findByEmail", query = "SELECT l FROM LoanCustomer l WHERE l.email = :email"),
    @NamedQuery(name = "LoanCustomer.findByAddressLine1", query = "SELECT l FROM LoanCustomer l WHERE l.addressLine1 = :addressLine1"),
    @NamedQuery(name = "LoanCustomer.findByAddressLine2", query = "SELECT l FROM LoanCustomer l WHERE l.addressLine2 = :addressLine2"),
    @NamedQuery(name = "LoanCustomer.findByAddressLine3", query = "SELECT l FROM LoanCustomer l WHERE l.addressLine3 = :addressLine3"),
    @NamedQuery(name = "LoanCustomer.findByVerificationToken", query = "SELECT l FROM LoanCustomer l WHERE l.verificationToken = :verificationToken"),
    @NamedQuery(name = "LoanCustomer.findByIsSubscribe", query = "SELECT l FROM LoanCustomer l WHERE l.isSubscribe = :isSubscribe")})
public class LoanCustomer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "gup_id")
    private Integer gupId;
    @Column(name = "nic")
    private String nic;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "name_with_initials")
    private String nameWithInitials;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "email")
    private String email;
    @Column(name = "address_line1")
    private String addressLine1;
    @Column(name = "address_line2")
    private String addressLine2;
    @Column(name = "address_line3")
    private String addressLine3;
    @Column(name = "verification_token")
    private String verificationToken;
    @Column(name = "is_subscribe")
    private Short isSubscribe;
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    @ManyToOne
    private Gender genderId;
    @JoinColumn(name = "general_user_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeneralUserProfile generalUserProfileId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanCustomerId")
    private Collection<MobileNo> mobileNoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanCustomerId")
    private Collection<OfferManager> offerManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanCustomerId")
    private Collection<BranchManager> branchManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanCustomerId")
    private Collection<StudentDue> studentDueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanCustomerId")
    private Collection<ScholarshipManager> scholarshipManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanCustomerId")
    private Collection<IntakeManager> intakeManagerCollection;

    public LoanCustomer() {
    }

    public LoanCustomer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGupId() {
        return gupId;
    }

    public void setGupId(Integer gupId) {
        this.gupId = gupId;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNameWithInitials() {
        return nameWithInitials;
    }

    public void setNameWithInitials(String nameWithInitials) {
        this.nameWithInitials = nameWithInitials;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Short getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(Short isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public Gender getGenderId() {
        return genderId;
    }

    public void setGenderId(Gender genderId) {
        this.genderId = genderId;
    }

    public GeneralUserProfile getGeneralUserProfileId() {
        return generalUserProfileId;
    }

    public void setGeneralUserProfileId(GeneralUserProfile generalUserProfileId) {
        this.generalUserProfileId = generalUserProfileId;
    }

    public Collection<MobileNo> getMobileNoCollection() {
        return mobileNoCollection;
    }

    public void setMobileNoCollection(Collection<MobileNo> mobileNoCollection) {
        this.mobileNoCollection = mobileNoCollection;
    }

    public Collection<OfferManager> getOfferManagerCollection() {
        return offerManagerCollection;
    }

    public void setOfferManagerCollection(Collection<OfferManager> offerManagerCollection) {
        this.offerManagerCollection = offerManagerCollection;
    }

    public Collection<BranchManager> getBranchManagerCollection() {
        return branchManagerCollection;
    }

    public void setBranchManagerCollection(Collection<BranchManager> branchManagerCollection) {
        this.branchManagerCollection = branchManagerCollection;
    }

    public Collection<StudentDue> getStudentDueCollection() {
        return studentDueCollection;
    }

    public void setStudentDueCollection(Collection<StudentDue> studentDueCollection) {
        this.studentDueCollection = studentDueCollection;
    }

    public Collection<ScholarshipManager> getScholarshipManagerCollection() {
        return scholarshipManagerCollection;
    }

    public void setScholarshipManagerCollection(Collection<ScholarshipManager> scholarshipManagerCollection) {
        this.scholarshipManagerCollection = scholarshipManagerCollection;
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
        if (!(object instanceof LoanCustomer)) {
            return false;
        }
        LoanCustomer other = (LoanCustomer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.LoanCustomer[ id=" + id + " ]";
    }
    
}
