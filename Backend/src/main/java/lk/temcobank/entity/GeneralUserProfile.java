package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * GeneralUserProfile entity — the single identity for all users in the system.
 * Maps to the existing general_user_profile table.
 */
@Entity
@Table(name = "general_user_profile")
@NamedQueries({
    @NamedQuery(name = "GeneralUserProfile.findAll", query = "SELECT g FROM GeneralUserProfile g WHERE g.isActive = 1"),
    @NamedQuery(name = "GeneralUserProfile.findByNic", query = "SELECT g FROM GeneralUserProfile g WHERE g.nic = :nic"),
    @NamedQuery(name = "GeneralUserProfile.findByEmail", query = "SELECT g FROM GeneralUserProfile g WHERE g.email = :email")
})
public class GeneralUserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nic", unique = true, nullable = false, length = 255)
    private String nic;

    @Size(max = 255)
    @Column(name = "first_name", length = 255)
    private String firstName;

    @Size(max = 255)
    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "full_name", columnDefinition = "TEXT")
    private String fullNameField;

    @Size(max = 255)
    @Column(name = "initials_name", length = 255)
    private String initialsName;

    @Size(max = 45)
    @Column(name = "home_phone", length = 45)
    private String homePhone;

    @Size(max = 255)
    @Column(name = "mobile_no", length = 255)
    private String mobileNo;

    @Size(max = 45)
    @Column(name = "emergency_contact", length = 45)
    private String emergencyContact;

    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Size(max = 1000)
    @Column(name = "address1", length = 1000)
    private String address1;

    @Size(max = 1000)
    @Column(name = "address2", length = 1000)
    private String address2;

    @Size(max = 1000)
    @Column(name = "address3", length = 1000)
    private String address3;

    @Column(name = "province_id")
    private Integer provinceId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "divisional_secretarial_id")
    private Integer divisionalSecretarialId;

    @Column(name = "gn_division_id")
    private Integer gnDivisionId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "profile_created_date")
    private LocalDateTime profileCreatedDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "education_level_id")
    private Integer educationLevelId;

    @Size(max = 255)
    @Column(name = "office_phone_no", length = 255)
    private String officePhoneNo;

    @Size(max = 255)
    @Column(name = "signature", length = 255)
    private String signature;

    @Column(name = "designation_id")
    private Integer designationId;

    @Column(name = "profeession_id")
    private Integer professionId;

    @Column(name = "is_verified")
    private byte[] isVerified;

    @Size(max = 345)
    @Column(name = "verification_token", nullable = false, length = 345)
    private String verificationToken;

    @Column(name = "is_active")
    private Short isActive = 1;

    // ==================== Convenience Methods ====================

    public String getFullName() {
        if (fullNameField != null && !fullNameField.isEmpty()) {
            return fullNameField;
        }
        String fn = firstName != null ? firstName : "";
        String ln = lastName != null ? lastName : "";
        return (fn + " " + ln).trim();
    }

    // ==================== Getters and Setters ====================

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFullNameField() { return fullNameField; }
    public void setFullNameField(String fullNameField) { this.fullNameField = fullNameField; }
    public String getInitialsName() { return initialsName; }
    public void setInitialsName(String initialsName) { this.initialsName = initialsName; }
    public String getHomePhone() { return homePhone; }
    public void setHomePhone(String homePhone) { this.homePhone = homePhone; }
    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }
    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }
    public String getAddress3() { return address3; }
    public void setAddress3(String address3) { this.address3 = address3; }
    public Integer getProvinceId() { return provinceId; }
    public void setProvinceId(Integer provinceId) { this.provinceId = provinceId; }
    public Integer getDistrictId() { return districtId; }
    public void setDistrictId(Integer districtId) { this.districtId = districtId; }
    public Integer getDivisionalSecretarialId() { return divisionalSecretarialId; }
    public void setDivisionalSecretarialId(Integer divisionalSecretarialId) { this.divisionalSecretarialId = divisionalSecretarialId; }
    public Integer getGnDivisionId() { return gnDivisionId; }
    public void setGnDivisionId(Integer gnDivisionId) { this.gnDivisionId = gnDivisionId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalDateTime getProfileCreatedDate() { return profileCreatedDate; }
    public void setProfileCreatedDate(LocalDateTime profileCreatedDate) { this.profileCreatedDate = profileCreatedDate; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public Integer getEducationLevelId() { return educationLevelId; }
    public void setEducationLevelId(Integer educationLevelId) { this.educationLevelId = educationLevelId; }
    public String getOfficePhoneNo() { return officePhoneNo; }
    public void setOfficePhoneNo(String officePhoneNo) { this.officePhoneNo = officePhoneNo; }
    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }
    public Integer getDesignationId() { return designationId; }
    public void setDesignationId(Integer designationId) { this.designationId = designationId; }
    public Integer getProfessionId() { return professionId; }
    public void setProfessionId(Integer professionId) { this.professionId = professionId; }
    public byte[] getIsVerified() { return isVerified; }
    public void setIsVerified(byte[] isVerified) { this.isVerified = isVerified; }
    public String getVerificationToken() { return verificationToken; }
    public void setVerificationToken(String verificationToken) { this.verificationToken = verificationToken; }
    public Short getIsActive() { return isActive; }
    public void setIsActive(Short isActive) { this.isActive = isActive; }
}
