package lk.temcobank.dto;

public class MemberDTO {
    private Long id;
    private String membershipNo;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String nic;
    private Boolean isActive;

    public MemberDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMembershipNo() { return membershipNo; }
    public void setMembershipNo(String membershipNo) { this.membershipNo = membershipNo; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
