package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "general_user_profile", indexes = {
    @Index(name = "idx_profile_nic", columnList = "nic"),
    @Index(name = "idx_profile_email", columnList = "email")
})
@NamedQueries({
    @NamedQuery(name = "GeneralUserProfile.findAll", query = "SELECT g FROM GeneralUserProfile g WHERE g.isDeleted = false"),
    @NamedQuery(name = "GeneralUserProfile.findByNic", query = "SELECT g FROM GeneralUserProfile g WHERE g.nic = :nic AND g.isDeleted = false"),
    @NamedQuery(name = "GeneralUserProfile.findByEmail", query = "SELECT g FROM GeneralUserProfile g WHERE g.email = :email AND g.isDeleted = false")
})
public class GeneralUserProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "nic", unique = true, nullable = false, length = 20)
    private String nic;

    @NotBlank
    @Size(max = 255)
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @NotBlank
    @Size(max = 255)
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Email
    @Size(max = 255)
    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Size(max = 45)
    @Column(name = "mobile_no", length = 45)
    private String mobileNo;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Column(name = "address", length = 1000)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "profile_image_path", length = 500)
    private String profileImagePath;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
    public String getProfileImagePath() { return profileImagePath; }
    public void setProfileImagePath(String profileImagePath) { this.profileImagePath = profileImagePath; }
}
