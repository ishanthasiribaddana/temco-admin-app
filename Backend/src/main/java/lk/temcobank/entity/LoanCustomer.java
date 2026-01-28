package lk.temcobank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LoanCustomer entity representing students who can apply for loans.
 */
@Entity
@Table(name = "loan_customer", indexes = {
    @Index(name = "idx_customer_profile", columnList = "general_user_profile_id"),
    @Index(name = "idx_customer_student_id", columnList = "student_id"),
    @Index(name = "idx_customer_nic", columnList = "nic"),
    @Index(name = "idx_customer_email", columnList = "email"),
    @Index(name = "idx_customer_status", columnList = "customer_status"),
    @Index(name = "idx_customer_active", columnList = "is_active, is_deleted")
})
@NamedQueries({
    @NamedQuery(name = "LoanCustomer.findAll", 
                query = "SELECT lc FROM LoanCustomer lc WHERE lc.isDeleted = false ORDER BY lc.firstName, lc.lastName"),
    @NamedQuery(name = "LoanCustomer.findByStudentId", 
                query = "SELECT lc FROM LoanCustomer lc WHERE lc.studentId = :studentId AND lc.isDeleted = false"),
    @NamedQuery(name = "LoanCustomer.findByNic", 
                query = "SELECT lc FROM LoanCustomer lc WHERE lc.nic = :nic AND lc.isDeleted = false"),
    @NamedQuery(name = "LoanCustomer.findByEmail", 
                query = "SELECT lc FROM LoanCustomer lc WHERE lc.email = :email AND lc.isDeleted = false"),
    @NamedQuery(name = "LoanCustomer.findActive", 
                query = "SELECT lc FROM LoanCustomer lc WHERE lc.customerStatus = 'ACTIVE' AND lc.isDeleted = false"),
    @NamedQuery(name = "LoanCustomer.search", 
                query = "SELECT lc FROM LoanCustomer lc WHERE lc.isDeleted = false AND " +
                        "(LOWER(lc.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                        "LOWER(lc.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                        "LOWER(lc.studentId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                        "LOWER(lc.nic) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                        "LOWER(lc.email) LIKE LOWER(CONCAT('%', :search, '%')))")
})
public class LoanCustomer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_user_profile_id", unique = true, nullable = false)
    private GeneralUserProfile generalUserProfile;

    @Size(max = 50)
    @Column(name = "student_id", unique = true, length = 50)
    private String studentId;

    @NotBlank(message = "NIC is required")
    @Size(max = 20)
    @Column(name = "nic", nullable = false, length = 20)
    private String nic;

    @NotBlank(message = "First name is required")
    @Size(max = 255)
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 255)
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Email(message = "Invalid email format")
    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 45)
    @Column(name = "mobile_no", length = 45)
    private String mobileNo;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Size(max = 1000)
    @Column(name = "address", length = 1000)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;

    @Column(name = "account_no", length = 500)
    private byte[] accountNo; // Encrypted

    @Size(max = 255)
    @Column(name = "branch_name", length = 255)
    private String branchName;

    @Size(max = 50)
    @Column(name = "customer_status", length = 50)
    private String customerStatus = "ACTIVE";

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    // ==================== Relationships ====================

    @OneToMany(mappedBy = "loanCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "loanCustomer")
    private List<StudentDue> studentDues = new ArrayList<>();

    @OneToMany(mappedBy = "loanCustomer")
    private List<PaymentHistory> paymentHistories = new ArrayList<>();

    @OneToMany(mappedBy = "loanCustomer")
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "loanCustomer")
    private List<StudentDocument> documents = new ArrayList<>();

    // ==================== Constructors ====================

    public LoanCustomer() {}

    public LoanCustomer(String nic, String firstName, String lastName, String email) {
        this.nic = nic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.customerStatus = "ACTIVE";
        this.registrationDate = LocalDate.now();
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GeneralUserProfile getGeneralUserProfile() {
        return generalUserProfile;
    }

    public void setGeneralUserProfile(GeneralUserProfile generalUserProfile) {
        this.generalUserProfile = generalUserProfile;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public byte[] getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(byte[] accountNo) {
        this.accountNo = accountNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<StudentDue> getStudentDues() {
        return studentDues;
    }

    public void setStudentDues(List<StudentDue> studentDues) {
        this.studentDues = studentDues;
    }

    public List<PaymentHistory> getPaymentHistories() {
        return paymentHistories;
    }

    public void setPaymentHistories(List<PaymentHistory> paymentHistories) {
        this.paymentHistories = paymentHistories;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<StudentDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<StudentDocument> documents) {
        this.documents = documents;
    }

    // ==================== Helper Methods ====================

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setLoanCustomer(this);
    }

    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
        enrollment.setLoanCustomer(null);
    }

    @Override
    public String toString() {
        return "LoanCustomer{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", nic='" + nic + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", customerStatus='" + customerStatus + '\'' +
                '}';
    }
}
