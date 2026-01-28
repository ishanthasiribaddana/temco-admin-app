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
@Table(name = "materialized_student_loan_eligible_student_table")
@NamedQueries({
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findAll", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findById", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.id = :id"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByGupId", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.gupId = :gupId"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByNic", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.nic = :nic"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByFirstName", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.firstName = :firstName"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByLastName", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.lastName = :lastName"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByNameWithInitials", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.nameWithInitials = :nameWithInitials"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByDob", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.dob = :dob"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByEmail", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.email = :email"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByMobileNo", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.mobileNo = :mobileNo"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByAddressLine1", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.addressLine1 = :addressLine1"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByAddressLine2", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.addressLine2 = :addressLine2"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByAddressLine3", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.addressLine3 = :addressLine3"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByScholarship", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.scholarship = :scholarship"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByPaymentOption", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.paymentOption = :paymentOption"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByCourseDue", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.courseDue = :courseDue"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByInternationalAwardingBodyDiplomaDue", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.internationalAwardingBodyDiplomaDue = :internationalAwardingBodyDiplomaDue"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByInternationalAwardingBodyDiplomaCurrency", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.internationalAwardingBodyDiplomaCurrency = :internationalAwardingBodyDiplomaCurrency"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByInternationalAwardingBodyHigherDiplomaDue", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.internationalAwardingBodyHigherDiplomaDue = :internationalAwardingBodyHigherDiplomaDue"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByInternationalAwardingBodyHigherDiplomaCurrency", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.internationalAwardingBodyHigherDiplomaCurrency = :internationalAwardingBodyHigherDiplomaCurrency"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByInternationalUniversityDue", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.internationalUniversityDue = :internationalUniversityDue"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByInternationalUniversityCurrency", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.internationalUniversityCurrency = :internationalUniversityCurrency"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByServiceChargesPresentage", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.serviceChargesPresentage = :serviceChargesPresentage"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByTotalDue", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.totalDue = :totalDue"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByGenderType", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.genderType = :genderType"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByVerificationToken", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.verificationToken = :verificationToken"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByBranchName", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.branchName = :branchName"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByIntakeId", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.intakeId = :intakeId"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByIntakeName", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.intakeName = :intakeName"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByProfileCreateDate", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.profileCreateDate = :profileCreateDate"),
    @NamedQuery(name = "MaterializedStudentLoanEligibleStudentTable.findByTransferDate", query = "SELECT m FROM MaterializedStudentLoanEligibleStudentTable m WHERE m.transferDate = :transferDate")})
public class MaterializedStudentLoanEligibleStudentTable implements Serializable {

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
    @Column(name = "mobile_no")
    private String mobileNo;
    @Column(name = "address_line1")
    private String addressLine1;
    @Column(name = "address_line2")
    private String addressLine2;
    @Column(name = "address_line3")
    private String addressLine3;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "scholarship")
    private Double scholarship;
    @Column(name = "payment_option")
    private String paymentOption;
    @Column(name = "course_due")
    private Double courseDue;
    @Column(name = "international_awarding_body_diploma_due")
    private Double internationalAwardingBodyDiplomaDue;
    @Column(name = "international_awarding_body_diploma_currency")
    private String internationalAwardingBodyDiplomaCurrency;
    @Column(name = "international_awarding_body_higher_diploma_due")
    private Double internationalAwardingBodyHigherDiplomaDue;
    @Column(name = "international_awarding_body_higher_diploma_currency")
    private String internationalAwardingBodyHigherDiplomaCurrency;
    @Column(name = "international_university_due")
    private Double internationalUniversityDue;
    @Column(name = "international_university_currency")
    private String internationalUniversityCurrency;
    @Column(name = "service_charges_presentage")
    private Double serviceChargesPresentage;
    @Column(name = "total_due")
    private Double totalDue;
    @Column(name = "gender_type")
    private String genderType;
    @Column(name = "verification_token")
    private String verificationToken;
    @Column(name = "branch_name")
    private String branchName;
    @Column(name = "intake_id")
    private Integer intakeId;
    @Column(name = "intake_name")
    private String intakeName;
    @Column(name = "profile_create_date")
    @Temporal(TemporalType.DATE)
    private Date profileCreateDate;
    @Column(name = "transfer_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transferDate;

    public MaterializedStudentLoanEligibleStudentTable() {
    }

    public MaterializedStudentLoanEligibleStudentTable(Integer id) {
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public Double getScholarship() {
        return scholarship;
    }

    public void setScholarship(Double scholarship) {
        this.scholarship = scholarship;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public Double getCourseDue() {
        return courseDue;
    }

    public void setCourseDue(Double courseDue) {
        this.courseDue = courseDue;
    }

    public Double getInternationalAwardingBodyDiplomaDue() {
        return internationalAwardingBodyDiplomaDue;
    }

    public void setInternationalAwardingBodyDiplomaDue(Double internationalAwardingBodyDiplomaDue) {
        this.internationalAwardingBodyDiplomaDue = internationalAwardingBodyDiplomaDue;
    }

    public String getInternationalAwardingBodyDiplomaCurrency() {
        return internationalAwardingBodyDiplomaCurrency;
    }

    public void setInternationalAwardingBodyDiplomaCurrency(String internationalAwardingBodyDiplomaCurrency) {
        this.internationalAwardingBodyDiplomaCurrency = internationalAwardingBodyDiplomaCurrency;
    }

    public Double getInternationalAwardingBodyHigherDiplomaDue() {
        return internationalAwardingBodyHigherDiplomaDue;
    }

    public void setInternationalAwardingBodyHigherDiplomaDue(Double internationalAwardingBodyHigherDiplomaDue) {
        this.internationalAwardingBodyHigherDiplomaDue = internationalAwardingBodyHigherDiplomaDue;
    }

    public String getInternationalAwardingBodyHigherDiplomaCurrency() {
        return internationalAwardingBodyHigherDiplomaCurrency;
    }

    public void setInternationalAwardingBodyHigherDiplomaCurrency(String internationalAwardingBodyHigherDiplomaCurrency) {
        this.internationalAwardingBodyHigherDiplomaCurrency = internationalAwardingBodyHigherDiplomaCurrency;
    }

    public Double getInternationalUniversityDue() {
        return internationalUniversityDue;
    }

    public void setInternationalUniversityDue(Double internationalUniversityDue) {
        this.internationalUniversityDue = internationalUniversityDue;
    }

    public String getInternationalUniversityCurrency() {
        return internationalUniversityCurrency;
    }

    public void setInternationalUniversityCurrency(String internationalUniversityCurrency) {
        this.internationalUniversityCurrency = internationalUniversityCurrency;
    }

    public Double getServiceChargesPresentage() {
        return serviceChargesPresentage;
    }

    public void setServiceChargesPresentage(Double serviceChargesPresentage) {
        this.serviceChargesPresentage = serviceChargesPresentage;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(Integer intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public Date getProfileCreateDate() {
        return profileCreateDate;
    }

    public void setProfileCreateDate(Date profileCreateDate) {
        this.profileCreateDate = profileCreateDate;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
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
        if (!(object instanceof MaterializedStudentLoanEligibleStudentTable)) {
            return false;
        }
        MaterializedStudentLoanEligibleStudentTable other = (MaterializedStudentLoanEligibleStudentTable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.MaterializedStudentLoanEligibleStudentTable[ id=" + id + " ]";
    }
    
}
