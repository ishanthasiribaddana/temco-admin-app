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
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
@Table(name = "general_organization_profile")
@NamedQueries({
    @NamedQuery(name = "GeneralOrganizationProfile.findAll", query = "SELECT g FROM GeneralOrganizationProfile g"),
    @NamedQuery(name = "GeneralOrganizationProfile.findById", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.id = :id"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByCode", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.code = :code"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByName", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.name = :name"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByPhoneNo", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.phoneNo = :phoneNo"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByEmail", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.email = :email"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByRegistrationNo", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.registrationNo = :registrationNo"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByWebsite", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.website = :website"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByFax", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.fax = :fax"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByObjectives", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.objectives = :objectives"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByMoto", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.moto = :moto"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByLogo", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.logo = :logo"),
    @NamedQuery(name = "GeneralOrganizationProfile.findByHeader", query = "SELECT g FROM GeneralOrganizationProfile g WHERE g.header = :header")})
public class GeneralOrganizationProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "address1")
    private String address1;
    @Lob
    @Column(name = "address2")
    private String address2;
    @Lob
    @Column(name = "address3")
    private String address3;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "email")
    private String email;
    @Lob
    @Column(name = "vision")
    private String vision;
    @Lob
    @Column(name = "mission")
    private String mission;
    @Column(name = "registration_no")
    private String registrationNo;
    @Column(name = "website")
    private String website;
    @Column(name = "fax")
    private String fax;
    @Column(name = "objectives")
    private String objectives;
    @Column(name = "moto")
    private String moto;
    @Column(name = "logo")
    private String logo;
    @Column(name = "header")
    private String header;
    @JoinTable(name = "interface_menu_gop_manager", joinColumns = {
        @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id"),
        @JoinColumn(name = "general_organization_profile_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "interface_menu_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<InterfaceMenu> interfaceMenuCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<Supplier> supplierCollection;
    @OneToMany(mappedBy = "generalOrganizationProfile")
    private Collection<UserLogin> userLoginCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<UniversalOrgDocumentsManager> universalOrgDocumentsManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<OrganizationBranches> organizationBranchesCollection;
    @OneToMany(mappedBy = "generalOrganizationProfileId")
    private Collection<BusinessInformation> businessInformationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<GopHasMember> gopHasMemberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<OrgDepartments> orgDepartmentsCollection;
    @OneToMany(mappedBy = "ownerGeneralOrganizationProfileId")
    private Collection<ShareCertificate> shareCertificateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<MemberOrganizationsHistory> memberOrganizationsHistoryCollection;
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne
    private City cityId;
    @JoinColumn(name = "organization_type_id", referencedColumnName = "id")
    @ManyToOne
    private OrganizationType organizationTypeId;
    @JoinColumn(name = "registration_type_id", referencedColumnName = "id")
    @ManyToOne
    private RegistrationType registrationTypeId;
    @OneToMany(mappedBy = "generalOrganizationProfile")
    private Collection<LoginSession> loginSessionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<BranchOfTheBank> branchOfTheBankCollection;
    @OneToMany(mappedBy = "generalOrganizationProfileId")
    private Collection<UserLoginGroup> userLoginGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<OrgBranchers> orgBranchersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<OrgCategoryManager> orgCategoryManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalOrganizationProfileId")
    private Collection<SecurityActivityLogs> securityActivityLogsCollection;

    public GeneralOrganizationProfile() {
    }

    public GeneralOrganizationProfile(Integer id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getMoto() {
        return moto;
    }

    public void setMoto(String moto) {
        this.moto = moto;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Collection<InterfaceMenu> getInterfaceMenuCollection() {
        return interfaceMenuCollection;
    }

    public void setInterfaceMenuCollection(Collection<InterfaceMenu> interfaceMenuCollection) {
        this.interfaceMenuCollection = interfaceMenuCollection;
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

    public Collection<UniversalOrgDocumentsManager> getUniversalOrgDocumentsManagerCollection() {
        return universalOrgDocumentsManagerCollection;
    }

    public void setUniversalOrgDocumentsManagerCollection(Collection<UniversalOrgDocumentsManager> universalOrgDocumentsManagerCollection) {
        this.universalOrgDocumentsManagerCollection = universalOrgDocumentsManagerCollection;
    }

    public Collection<OrganizationBranches> getOrganizationBranchesCollection() {
        return organizationBranchesCollection;
    }

    public void setOrganizationBranchesCollection(Collection<OrganizationBranches> organizationBranchesCollection) {
        this.organizationBranchesCollection = organizationBranchesCollection;
    }

    public Collection<BusinessInformation> getBusinessInformationCollection() {
        return businessInformationCollection;
    }

    public void setBusinessInformationCollection(Collection<BusinessInformation> businessInformationCollection) {
        this.businessInformationCollection = businessInformationCollection;
    }

    public Collection<GopHasMember> getGopHasMemberCollection() {
        return gopHasMemberCollection;
    }

    public void setGopHasMemberCollection(Collection<GopHasMember> gopHasMemberCollection) {
        this.gopHasMemberCollection = gopHasMemberCollection;
    }

    public Collection<OrgDepartments> getOrgDepartmentsCollection() {
        return orgDepartmentsCollection;
    }

    public void setOrgDepartmentsCollection(Collection<OrgDepartments> orgDepartmentsCollection) {
        this.orgDepartmentsCollection = orgDepartmentsCollection;
    }

    public Collection<ShareCertificate> getShareCertificateCollection() {
        return shareCertificateCollection;
    }

    public void setShareCertificateCollection(Collection<ShareCertificate> shareCertificateCollection) {
        this.shareCertificateCollection = shareCertificateCollection;
    }

    public Collection<MemberOrganizationsHistory> getMemberOrganizationsHistoryCollection() {
        return memberOrganizationsHistoryCollection;
    }

    public void setMemberOrganizationsHistoryCollection(Collection<MemberOrganizationsHistory> memberOrganizationsHistoryCollection) {
        this.memberOrganizationsHistoryCollection = memberOrganizationsHistoryCollection;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public OrganizationType getOrganizationTypeId() {
        return organizationTypeId;
    }

    public void setOrganizationTypeId(OrganizationType organizationTypeId) {
        this.organizationTypeId = organizationTypeId;
    }

    public RegistrationType getRegistrationTypeId() {
        return registrationTypeId;
    }

    public void setRegistrationTypeId(RegistrationType registrationTypeId) {
        this.registrationTypeId = registrationTypeId;
    }

    public Collection<LoginSession> getLoginSessionCollection() {
        return loginSessionCollection;
    }

    public void setLoginSessionCollection(Collection<LoginSession> loginSessionCollection) {
        this.loginSessionCollection = loginSessionCollection;
    }

    public Collection<BranchOfTheBank> getBranchOfTheBankCollection() {
        return branchOfTheBankCollection;
    }

    public void setBranchOfTheBankCollection(Collection<BranchOfTheBank> branchOfTheBankCollection) {
        this.branchOfTheBankCollection = branchOfTheBankCollection;
    }

    public Collection<UserLoginGroup> getUserLoginGroupCollection() {
        return userLoginGroupCollection;
    }

    public void setUserLoginGroupCollection(Collection<UserLoginGroup> userLoginGroupCollection) {
        this.userLoginGroupCollection = userLoginGroupCollection;
    }

    public Collection<OrgBranchers> getOrgBranchersCollection() {
        return orgBranchersCollection;
    }

    public void setOrgBranchersCollection(Collection<OrgBranchers> orgBranchersCollection) {
        this.orgBranchersCollection = orgBranchersCollection;
    }

    public Collection<OrgCategoryManager> getOrgCategoryManagerCollection() {
        return orgCategoryManagerCollection;
    }

    public void setOrgCategoryManagerCollection(Collection<OrgCategoryManager> orgCategoryManagerCollection) {
        this.orgCategoryManagerCollection = orgCategoryManagerCollection;
    }

    public Collection<SecurityActivityLogs> getSecurityActivityLogsCollection() {
        return securityActivityLogsCollection;
    }

    public void setSecurityActivityLogsCollection(Collection<SecurityActivityLogs> securityActivityLogsCollection) {
        this.securityActivityLogsCollection = securityActivityLogsCollection;
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
        if (!(object instanceof GeneralOrganizationProfile)) {
            return false;
        }
        GeneralOrganizationProfile other = (GeneralOrganizationProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.exon.temco_loan_system.entity.GeneralOrganizationProfile[ id=" + id + " ]";
    }
    
}
