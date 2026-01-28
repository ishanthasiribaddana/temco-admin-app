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
@Table(name = "general_user_profile")
@NamedQueries({
    @NamedQuery(name = "GeneralUserProfile.findAll", query = "SELECT g FROM GeneralUserProfile g"),
    @NamedQuery(name = "GeneralUserProfile.findById", query = "SELECT g FROM GeneralUserProfile g WHERE g.id = :id"),
    @NamedQuery(name = "GeneralUserProfile.findByNic", query = "SELECT g FROM GeneralUserProfile g WHERE g.nic = :nic"),
    @NamedQuery(name = "GeneralUserProfile.findByFirstName", query = "SELECT g FROM GeneralUserProfile g WHERE g.firstName = :firstName"),
    @NamedQuery(name = "GeneralUserProfile.findByLastName", query = "SELECT g FROM GeneralUserProfile g WHERE g.lastName = :lastName"),
    @NamedQuery(name = "GeneralUserProfile.findByInitialsName", query = "SELECT g FROM GeneralUserProfile g WHERE g.initialsName = :initialsName"),
    @NamedQuery(name = "GeneralUserProfile.findByHomePhone", query = "SELECT g FROM GeneralUserProfile g WHERE g.homePhone = :homePhone"),
    @NamedQuery(name = "GeneralUserProfile.findByMobileNo", query = "SELECT g FROM GeneralUserProfile g WHERE g.mobileNo = :mobileNo"),
    @NamedQuery(name = "GeneralUserProfile.findByEmergencyContact", query = "SELECT g FROM GeneralUserProfile g WHERE g.emergencyContact = :emergencyContact"),
    @NamedQuery(name = "GeneralUserProfile.findByEmail", query = "SELECT g FROM GeneralUserProfile g WHERE g.email = :email"),
    @NamedQuery(name = "GeneralUserProfile.findByAddress1", query = "SELECT g FROM GeneralUserProfile g WHERE g.address1 = :address1"),
    @NamedQuery(name = "GeneralUserProfile.findByAddress2", query = "SELECT g FROM GeneralUserProfile g WHERE g.address2 = :address2"),
    @NamedQuery(name = "GeneralUserProfile.findByAddress3", query = "SELECT g FROM GeneralUserProfile g WHERE g.address3 = :address3"),
    @NamedQuery(name = "GeneralUserProfile.findByDate", query = "SELECT g FROM GeneralUserProfile g WHERE g.date = :date"),
    @NamedQuery(name = "GeneralUserProfile.findByProfileCreatedDate", query = "SELECT g FROM GeneralUserProfile g WHERE g.profileCreatedDate = :profileCreatedDate"),
    @NamedQuery(name = "GeneralUserProfile.findByUpdatedAt", query = "SELECT g FROM GeneralUserProfile g WHERE g.updatedAt = :updatedAt"),
    @NamedQuery(name = "GeneralUserProfile.findByDob", query = "SELECT g FROM GeneralUserProfile g WHERE g.dob = :dob"),
    @NamedQuery(name = "GeneralUserProfile.findByOfficePhoneNo", query = "SELECT g FROM GeneralUserProfile g WHERE g.officePhoneNo = :officePhoneNo"),
    @NamedQuery(name = "GeneralUserProfile.findBySignature", query = "SELECT g FROM GeneralUserProfile g WHERE g.signature = :signature"),
    @NamedQuery(name = "GeneralUserProfile.findByVerificationToken", query = "SELECT g FROM GeneralUserProfile g WHERE g.verificationToken = :verificationToken"),
    @NamedQuery(name = "GeneralUserProfile.findByIsActive", query = "SELECT g FROM GeneralUserProfile g WHERE g.isActive = :isActive")})
public class GeneralUserProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nic")
    private String nic;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Lob
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "initials_name")
    private String initialsName;
    @Column(name = "home_phone")
    private String homePhone;
    @Column(name = "mobile_no")
    private String mobileNo;
    @Column(name = "emergency_contact")
    private String emergencyContact;
    @Column(name = "email")
    private String email;
    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "address3")
    private String address3;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "profile_created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date profileCreatedDate;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "office_phone_no")
    private String officePhoneNo;
    @Column(name = "signature")
    private String signature;
    @Lob
    @Column(name = "is_verified")
    private byte[] isVerified;
    @Basic(optional = false)
    @Column(name = "verification_token")
    private String verificationToken;
    @Basic(optional = false)
    @Column(name = "is_active")
    private short isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalUserProfileId")
    private Collection<Supplier> supplierCollection;
    @OneToMany(mappedBy = "generalUserProfile")
    private Collection<UserLogin> userLoginCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalUserProfileId")
    private Collection<LoanCustomer> loanCustomerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalUserProfileId")
    private Collection<Member1> member1Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalUserProfileId")
    private Collection<UniversalUserDocument> universalUserDocumentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalUserProfile")
    private Collection<Voucher> voucherCollection;
    @OneToMany(mappedBy = "generalUserProfileId")
    private Collection<Employee> employeeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalUserProfileId")
    private Collection<UserRoleManagement> userRoleManagementCollection;
    @OneToMany(mappedBy = "generalUserProfileCreatedBy")
    private Collection<Settings> settingsCollection;
    @OneToMany(mappedBy = "generalUserProfileUpdatedBy")
    private Collection<Settings> settingsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalUserProfileId")
    private Collection<SpotMember> spotMemberCollection;
    @JoinColumn(name = "designation_id", referencedColumnName = "id")
    @ManyToOne
    private Designation designationId;
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    @ManyToOne
    private District districtId;
    @JoinColumn(name = "divisional_secretarial_id", referencedColumnName = "id")
    @ManyToOne
    private DivisionalSecretarial divisionalSecretarialId;
    @JoinColumn(name = "education_level_id", referencedColumnName = "id")
    @ManyToOne
    private EducationLevel educationLevelId;
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    @ManyToOne
    private Gender genderId;
    @JoinColumn(name = "gn_division_id", referencedColumnName = "id")
    @ManyToOne
    private GnDivision gnDivisionId;
    @JoinColumn(name = "profeession_id", referencedColumnName = "id")
    @ManyToOne
    private Profeession profeessionId;
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    @ManyToOne
    private Province provinceId;

    public GeneralUserProfile() {
    }

    public GeneralUserProfile(Integer id) {
        this.id = id;
    }

    public GeneralUserProfile(Integer id, String nic, String verificationToken, short isActive) {
        this.id = id;
        this.nic = nic;
        this.verificationToken = verificationToken;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInitialsName() {
        return initialsName;
    }

    public void setInitialsName(String initialsName) {
        this.initialsName = initialsName;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getProfileCreatedDate() {
        return profileCreatedDate;
    }

    public void setProfileCreatedDate(Date profileCreatedDate) {
        this.profileCreatedDate = profileCreatedDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getOfficePhoneNo() {
        return officePhoneNo;
    }

    public void setOfficePhoneNo(String officePhoneNo) {
        this.officePhoneNo = officePhoneNo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public byte[] getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(byte[] isVerified) {
        this.isVerified = isVerified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public short getIsActive() {
        return isActive;
    }

    public void setIsActive(short isActive) {
        this.isActive = isActive;
    }

    public Collection<Supplier> getSupplierCollection() {
        return supplierCollection;
    }

    public void setSupplierCollection(Collection<Supplier> supplierCollection) {
        this.supplierCollection = supplierCollection;
    }

    public Collection<UserLogin> getUserLoginCollection() {
        return userLoginCollection;
    }

    public void setUserLoginCollection(Collection<UserLogin> userLoginCollection) {
        this.userLoginCollection = userLoginCollection;
    }

    public Collection<LoanCustomer> getLoanCustomerCollection() {
        return loanCustomerCollection;
    }

    public void setLoanCustomerCollection(Collection<LoanCustomer> loanCustomerCollection) {
        this.loanCustomerCollection = loanCustomerCollection;
    }

    public Collection<Member1> getMember1Collection() {
        return member1Collection;
    }

    public void setMember1Collection(Collection<Member1> member1Collection) {
        this.member1Collection = member1Collection;
    }

    public Collection<UniversalUserDocument> getUniversalUserDocumentCollection() {
        return universalUserDocumentCollection;
    }

    public void setUniversalUserDocumentCollection(Collection<UniversalUserDocument> universalUserDocumentCollection) {
        this.universalUserDocumentCollection = universalUserDocumentCollection;
    }

    public Collection<Voucher> getVoucherCollection() {
        return voucherCollection;
    }

    public void setVoucherCollection(Collection<Voucher> voucherCollection) {
        this.voucherCollection = voucherCollection;
    }

    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    public Collection<UserRoleManagement> getUserRoleManagementCollection() {
        return userRoleManagementCollection;
    }

    public void setUserRoleManagementCollection(Collection<UserRoleManagement> userRoleManagementCollection) {
        this.userRoleManagementCollection = userRoleManagementCollection;
    }

    public Collection<Settings> getSettingsCollection() {
        return settingsCollection;
    }

    public void setSettingsCollection(Collection<Settings> settingsCollection) {
        this.settingsCollection = settingsCollection;
    }

    public Collection<Settings> getSettingsCollection1() {
        return settingsCollection1;
    }

    public void setSettingsCollection1(Collection<Settings> settingsCollection1) {
        this.settingsCollection1 = settingsCollection1;
    }

    public Collection<SpotMember> getSpotMemberCollection() {
        return spotMemberCollection;
    }

    public void setSpotMemberCollection(Collection<SpotMember> spotMemberCollection) {
        this.spotMemberCollection = spotMemberCollection;
    }

    public Designation getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Designation designationId) {
        this.designationId = designationId;
    }

    public District getDistrictId() {
        return districtId;
    }

    public void setDistrictId(District districtId) {
        this.districtId = districtId;
    }

    public DivisionalSecretarial getDivisionalSecretarialId() {
        return divisionalSecretarialId;
    }

    public void setDivisionalSecretarialId(DivisionalSecretarial divisionalSecretarialId) {
        this.divisionalSecretarialId = divisionalSecretarialId;
    }

    public EducationLevel getEducationLevelId() {
        return educationLevelId;
    }

    public void setEducationLevelId(EducationLevel educationLevelId) {
        this.educationLevelId = educationLevelId;
    }

    public Gender getGenderId() {
        return genderId;
    }

    public void setGenderId(Gender genderId) {
        this.genderId = genderId;
    }

    public GnDivision getGnDivisionId() {
        return gnDivisionId;
    }

    public void setGnDivisionId(GnDivision gnDivisionId) {
        this.gnDivisionId = gnDivisionId;
    }

    public Profeession getProfeessionId() {
        return profeessionId;
    }

    public void setProfeessionId(Profeession profeessionId) {
        this.profeessionId = profeessionId;
    }

    public Province getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Province provinceId) {
        this.provinceId = provinceId;
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
        if (!(object instanceof GeneralUserProfile)) {
            return false;
        }
        GeneralUserProfile other = (GeneralUserProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GeneralUserProfile[ id=" + id + " ]";
    }
    
}
