/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package lk.exon.temco_loan_system.service.university_payment_loan;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.exon.temco_loan_system.common.ComLib;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.AccountType;
import lk.exon.temco_loan_system.entity.CustomerResponseHistory;
import lk.exon.temco_loan_system.entity.District;
import lk.exon.temco_loan_system.entity.DivisionalSecretarial;
import lk.exon.temco_loan_system.entity.EmployementType;
import lk.exon.temco_loan_system.entity.Gender;
import lk.exon.temco_loan_system.entity.GeneralOrganizationProfile;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.entity.GnDivision;
import lk.exon.temco_loan_system.entity.GopHasMember;
import lk.exon.temco_loan_system.entity.InterestManager;
import lk.exon.temco_loan_system.entity.LoanCustomer;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.LoanOffer;
import lk.exon.temco_loan_system.entity.LoanStatusManager;
import lk.exon.temco_loan_system.entity.MaterializedStudentLoanEligibleStudentTable;
import lk.exon.temco_loan_system.entity.Member1;
import lk.exon.temco_loan_system.entity.MemberBankAccounts;
import lk.exon.temco_loan_system.entity.MemberOrganizationsHistory;
import lk.exon.temco_loan_system.entity.OfferManager;
import lk.exon.temco_loan_system.entity.OrganizationBranches;
import lk.exon.temco_loan_system.entity.OrganizationType;
import lk.exon.temco_loan_system.entity.Province;
import lk.exon.temco_loan_system.entity.RepaymentPeriod;
import lk.exon.temco_loan_system.entity.ResponseStatus;

/**
 *
 * @author USER
 */
@Named
@LocalBean
@SessionScoped
public class PersonalDetailsRegistration implements Serializable {

    private List<LoanCalculatorRecords> loanStrucure = new ArrayList<>();
    private List<SelectItem> genderList = new ArrayList<>();
    private List<SelectItem> provinceList = new ArrayList<>();
    private List<SelectItem> districtList = new ArrayList<>();
    private List<SelectItem> divisionalSecretarialList = new ArrayList<>();
    private List<SelectItem> gnDivisionList = new ArrayList<>();

    private List<SelectItem> relationList = new ArrayList<>();
    private List<SelectItem> employementSectorsList = new ArrayList<>();
    private List<SelectItem> professionsList = new ArrayList<>();

    private List<SelectItem> repaymentPeriodList = new ArrayList<>();

    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNo;
    private Date dob;
    private String email;
    private String addressOne;
    private String addressTwo;
    private String addressThree;
    private double dueCourseFee;
    private boolean checkbox;
    private int genderId;
    private int branchId;
    private int selectedGender;

    private String userGender;
    private String selectedProvinceId;
    private String selectedDistrict;
    private String selectedDivisionalSecretarial;
    private String selectedGnDivision;
    private String selectedEmployementSector;
    double correctMonthlyInstallment = 0.00;
    boolean correctMonthlyInstallmentBoolean = false;

    private String selectedprofession;
    private String selectedBranch;

    private double expectedLoanAmount = 0.00;
    private double monthlyinstallement = 0.00;
    private String repayementPeriod;
    private String relationType = "0";
    private String existingMember;
    private double grossIncome;

    private boolean displayBoolean = true;
    private boolean displayBooleanTwo = true;
    private boolean displayAddress = true;
    private boolean telephone = true;
    private boolean genderBool = true;
    private boolean orgBoolean = true;

    private boolean displayGuarantorSection;
    private String inputTextFieldOneValue;

    private double interestRate = 6;
    private int actualLoanTenture;
    private String actualMonthlyInstallement = "0.00";
    int actualMonth = 0;
    private boolean loanCalculated = false;

    private boolean displayFullName = false;
    private boolean displayNameWithIntials = false;
    private boolean displayNic = false;
    private boolean displayMobileNo = false;
    private boolean displayGender = false;
    private boolean displayDob = false;
    private boolean displayEmail = false;
    private boolean displayAddress01 = false;
    private boolean displayAddress02 = false;
    private boolean displayAddress03 = false;
    private boolean displayProvince = false;
    private boolean displayDistrict = false;
    private boolean displayDivisiona = false;
    private boolean displayGN = false;
    private boolean displayExpectedLoanAmount = false;
    private boolean displaymonthlyInstallment = false;
    private boolean displayLoanTenture = false;
    private boolean displayLoanAmount = false;

    private String selectedEmployementType;
    private List<SelectItem> employementTypeList;
    private boolean selfEmployed;
    private boolean employed;

    private String nameOftheEmployerOrBusiness;
    private String contactNumberOftheEmployerOrBusiness;
    private String employerAddressOneOrBusiness;
    private String employerAddressTwoOrBusiness;
    private String employerAddressThreeOrBusiness;

    private List<GenderList> gender_list;

    private String organizationId;
    private List<OrganizationBranchesList> org_branch_list;

    private final String PREFIX = "TEMCO-SL";
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final AtomicInteger COUNTER = new AtomicInteger(0);

    private List<MaterializedStudentLoanEligibleStudentTable> materializedObj;

    private String error_message;

    private String securityCode;

    private Member1 member;

    private MemberBankAccounts memberBankAccounts;

    @EJB
    private UniDBLocal UniDB;

    private ComLib ComLib;

    @PostConstruct
    public void init() {
        System.out.println("in it");
        intializeMethod();
    }

    public void popUpMessage() {
        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "මෙය ණය ඉල්ලුම්පතේ ආකෘතියකි. මෙම සාමාජිකයාගේ දත්ත සැබෑ දත්ත නොවේ . ඔබට අවශ්‍ය Real Time  Online TEMCO ණය ක්‍රමය තේරුම් ගැනීමට නම්, ණය ඉල්ලුම්පත්‍රය පිරවීමට Province එක ඇතුලත් කරන තැනින් ආරම්භ කරන්න. ඔබ මේ වන විට TEMCO බැංකුවේ සාමාජිකයෙකු නොවේ නම් ණය ඉල්ලුම් කිරීම පිණිස මුලින් TEMCO සමුපකාර බැංකුවේ සාමාජිකත්වය අයදුම් කල යුතුය. ඒ සඳහා 0777 570 403 අමතන්න");
        FacesContext.getCurrentInstance().addMessage("", msg);
    }

    private void intializeMethod() {
        System.out.println("intializeMethod");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

        securityCode = params.get("en");

        if (securityCode != null) {
            System.out.println("in if");

            getUserDetailsFromMaterializeTable(securityCode);

            if (userAlreadyHaveALoan(nic)) {
                try {
                    ExternalContext externalContext = facesContext.getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath());
                    facesContext.responseComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                updateOfferManager();
            }

        } else {
            System.out.println("in else");
        }

        getDistrictList().add(new SelectItem("0", "Select"));
        getDivisionalSecretarialList().add(new SelectItem("0", "Select"));
        getGnDivisionList().add(new SelectItem("0", "Select"));
        getRelationList().add(new SelectItem("0", "Select"));

        EmployeeTypeList();
        gender();
        province();
        repaymentPeriod();
        selectedEmployementType = "0";
        userEmployementType();
    }

    public void updateOfferManager() {
        Date date = new Date();
        System.out.println("updateOfferManager");
        List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + nic + "'");
        System.out.println("nic " + nic);
        if (!loanCustomer.isEmpty()) {
            System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
            List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
            System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
            if (offerManager.isEmpty()) {

                System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
                OfferManager newOfferManager = new OfferManager();
                newOfferManager.setDate(date);
                newOfferManager.setIsAccepted(Short.decode("1"));
                newOfferManager.setLoanOfferId((LoanOffer) UniDB.find(1, LoanOffer.class));
                newOfferManager.setLoanCustomerId(loanCustomer.get(0));
                UniDB.create(newOfferManager);

                CustomerResponseHistory crh = new CustomerResponseHistory();
                crh.setDate(date);
                crh.setOfferManagerId(newOfferManager);
                crh.setResponseStatusId((ResponseStatus) UniDB.find(5, ResponseStatus.class));
                UniDB.create(crh);

            } else {
                offerManager.get(0).setIsAccepted(Short.decode("1"));
                UniDB.update(offerManager.get(0));

                List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='5'");
                if (crhList.isEmpty()) {
                    CustomerResponseHistory crh = new CustomerResponseHistory();
                    crh.setDate(date);
                    crh.setOfferManagerId(offerManager.get(0));
                    crh.setResponseStatusId((ResponseStatus) UniDB.find(5, ResponseStatus.class));
                    UniDB.create(crh);
                }
            }
        }
    }

    public boolean calculateMonthlyInstallementIsLessThanGuarantorsIncome() {
        System.out.println("grossIncome " + grossIncome);
        System.out.println("monthlyinstallement " + monthlyinstallement);
        double guarantorInstallemt = (grossIncome / 100) * 60;
        System.out.println("guarantorInstallemt " + guarantorInstallemt);
        return monthlyinstallement <= guarantorInstallemt;

    }

    public void userEmployementType() {
        System.out.println("userEmployementType");
        System.out.println("selectedEmployementType " + selectedEmployementType);

        nameOftheEmployerOrBusiness = "";
        contactNumberOftheEmployerOrBusiness = "";
        employerAddressOneOrBusiness = "";
        employerAddressTwoOrBusiness = "";
        employerAddressThreeOrBusiness = "";

        switch (Integer.parseInt(selectedEmployementType)) {
            case 1:
                employed = true;
                selfEmployed = false;
                break;
            case 2:
                employed = false;
                selfEmployed = true;
                break;
            default:
                employed = false;
                selfEmployed = false;
                break;
        }
    }

    private void EmployeeTypeList() {
        employementTypeList = new ArrayList<>();
        employementTypeList.add(new SelectItem("0", "Select"));
        List<EmployementType> employementTypes = UniDB.searchByQuery("SELECT g FROM EmployementType g");
        for (EmployementType employementType : employementTypes) {
            employementTypeList.add(new SelectItem(employementType.getId(), employementType.getType()));
        }

    }

    private void getUserDetailsFromMaterializeTable(String securityCode) {
        try {
            System.out.println("getUserDetailsFromMaterializeTable");
            List<MaterializedStudentLoanEligibleStudentTable> mt = UniDB.searchByQuery("SELECT g FROM MaterializedStudentLoanEligibleStudentTable g WHERE g.verificationToken='" + securityCode + "' ");
            System.out.println("mt size " + mt.size());
            if (mt != null && !mt.isEmpty()) {
                materializedObj = mt;
                System.out.println("A1");
                firstName = mt.get(0).getFirstName();
                lastName = mt.get(0).getLastName();
                nic = mt.get(0).getNic();
                mobileNo = mt.get(0).getMobileNo();
                dob = mt.get(0).getDob();
                email = mt.get(0).getEmail();
                addressOne = mt.get(0).getAddressLine1();
                addressTwo = mt.get(0).getAddressLine2();
                addressThree = mt.get(0).getAddressLine3();
                dueCourseFee = mt.get(0).getTotalDue();
                expectedLoanAmount = dueCourseFee;
                displayAddress = true;
                telephone = true;
                System.out.println("A");
                if (firstName.equals("") || firstName.equals("null")) {
                    displayBoolean = false;
                    firstName = "";
                }
                System.out.println("B");
                if (lastName.equals("") || lastName.equals("null")) {
                    displayBooleanTwo = false;
                    lastName = "";
                }
                System.out.println("C");
                System.out.println("addressOne " + addressOne);
                if (addressOne == null || addressOne.equals("") || addressOne.equals("null")) {
                    displayAddress = false;
                    addressOne = "";
                    addressTwo = "";
                    addressThree = "";
                }
                System.out.println("D");
                System.out.println("addressTwo " + addressTwo);
                if (addressTwo == null || addressTwo.equals("") || addressTwo.equals("null")) {
                    displayAddress = false;
                    addressOne = "";
                    addressTwo = "";
                    addressThree = "";
                }
                System.out.println("E");
                System.out.println("addressThree " + addressThree);
                if (addressThree == null || addressThree.equals("") || addressThree.equals("null")) {
                    displayAddress = false;
                    addressOne = "";
                    addressTwo = "";
                    addressThree = "";
                }
                System.out.println("F");
                if (mobileNo == null || mobileNo.equals("") || mobileNo.equals("null")) {
                    telephone = false;
                    mobileNo = "";
                }

                System.out.println("orgList" + mt.get(0).getBranchName());
                List<OrganizationBranches> orgList = UniDB.searchByQuery("SELECT g FROM OrganizationBranches g WHERE g.name LIKE '%" + mt.get(0).getBranchName() + "%'");
                System.out.println("orgList " + orgList.size());

                if (!orgList.isEmpty()) {
                    System.out.println("branch name and id " + orgList.get(0).getId());
                    System.out.println("branch name and id " + orgList.get(0).getName());
                    System.out.println("branch name and id " + orgList.get(0).getId() + " " + orgList.get(0).getName());
                    branchId = orgList.get(0).getId();
                } else {
                    orgBoolean = false;
                    List<OrganizationBranches> orgList2 = UniDB.searchByQuery("SELECT g FROM OrganizationBranches g");
                    org_branch_list = new ArrayList<>();
                    for (OrganizationBranches organizationBranches : orgList2) {
                        org_branch_list.add(new OrganizationBranchesList(organizationBranches.getId(), organizationBranches.getName()));
                    }
                }

                System.out.println("mt.get(0).getGenderType() " + mt.get(0).getGenderType());
                List<Gender> genderLists = UniDB.searchByQuery("SELECT g FROM Gender g WHERE g.name='" + mt.get(0).getGenderType() + "'");
                if (genderLists != null) {
                    genderId = genderLists.get(0).getId();
                    userGender = genderLists.get(0).getName();
                } else {
                    genderBool = false;
                    List<Gender> genderList = UniDB.searchByQuery("SELECT g FROM GENDER g");
                    gender_list = new ArrayList<>();
                    for (Gender gender1 : genderList) {
                        gender_list.add(new GenderList(gender1.getId(), gender1.getName()));
                    }
                }
            } else {

            }
        } catch (Exception e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(PersonalDetailsRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean userAlreadyHaveALoan(String nic) {
        List<GeneralUserProfile> gupExist = UniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic='" + nic + "'");
        System.out.println("gup exist " + (gupExist.size() > 0));
        if (gupExist.size() > 0) {
            List<Member1> existmember = UniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gupExist.get(0).getId() + "' ");
            System.out.println("existmember " + (existmember.size() > 0));
            if (existmember.size() > 0) {
                List<MemberBankAccounts> memberBankAccountsList = UniDB.searchByQuery("SELECT g FROM MemberBankAccounts g WHERE g.memberId.id='" + existmember.get(0).getId() + "'");
                if (memberBankAccountsList.size() > 0) {
                    for (MemberBankAccounts memberBankAccountsObj : memberBankAccountsList) {
                        List<LoanManager> memberLoans = UniDB.searchByQuery("SELECT g FROM LoanManager g WHERE g.memberBankAccountsId.id='" + memberBankAccountsObj.getId() + "'");
                        for (LoanManager memberLoan : memberLoans) {
                            List<LoanStatusManager> status = UniDB.searchByQuery(
                                    "SELECT g FROM LoanStatusManager g WHERE g.loanManagerId.id = '" + memberLoan.getId()
                                    + "' AND g.loanStatusId.id IN ('1', '2', '3', '4', '5') AND g.date = ("
                                    + "SELECT MAX(gs.date) FROM LoanStatusManager gs WHERE gs.loanManagerId.id = '" + memberLoan.getId() + "')"
                            );

                            if (status.size() > 0) {
                                List<InterestManager> interestManagers = UniDB.searchByQuery("SELECT g FROM InterestManager g WHERE g.loanManagerId.id='" + status.get(0).getLoanManagerId().getId() + "' ");
                                if (interestManagers.size() > 0) {
                                    if (interestManagers.get(0).getLoanid().getId() == 2) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }

                            return (status.size() > 0);
                        }
                    }
                } else {
                    return false;
                }
            } else {

                return false;
            }
        } else {

            return false;
        }
        return false;
    }

    public void saveUserDetails() {

        FacesMessage msg;
        System.out.println("selectedGnDivision " + selectedGnDivision);
        if (!mobileNo.equals("")) {
            if (!addressOne.equals("")) {
                if (!addressTwo.equals("")) {
                    if (!addressThree.equals("")) {
                        if (selectedProvinceId != null && !selectedProvinceId.equals("0")) {
                            if (!selectedDistrict.equals("0") && !selectedDistrict.equals("")) {
                                if (!selectedDivisionalSecretarial.equals("0") && !selectedDivisionalSecretarial.equals("")) {
                                    if (selectedGnDivision != null && !selectedGnDivision.equals("0")) {
                                        if (dob != null && dob.before(new Date())) {
                                            if (isEmployementTypeSelected(selectedEmployementType)) {
                                                System.out.println("employement type selected");
                                                if (checkbox) {
                                                    try {
                                                        Date date = new Date();
                                                        List<GeneralUserProfile> gupExist = UniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic='" + nic + "'");

                                                        GeneralUserProfile gup = null;

                                                        if (gupExist == null || gupExist.isEmpty()) {
                                                            gup = new GeneralUserProfile();
                                                            gup.setFirstName(firstName);
                                                            gup.setLastName(lastName);
                                                            gup.setNic(nic);
                                                            gup.setMobileNo(mobileNo);
                                                            gup.setGenderId((Gender) UniDB.find(genderId, Gender.class));
                                                            gup.setDob(dob);
                                                            gup.setEmail(email);
                                                            gup.setAddress1(addressOne);
                                                            gup.setAddress2(addressTwo);
                                                            gup.setAddress3(addressThree);
                                                            gup.setProvinceId((Province) UniDB.find(Integer.parseInt(selectedProvinceId), Province.class));
                                                            gup.setDistrictId((District) UniDB.find(Integer.parseInt(selectedDistrict), District.class));
                                                            gup.setDivisionalSecretarialId((DivisionalSecretarial) UniDB.find(Integer.parseInt(selectedDivisionalSecretarial), DivisionalSecretarial.class));
                                                            gup.setGnDivisionId((GnDivision) UniDB.find(Integer.parseInt(selectedGnDivision), GnDivision.class));
                                                            gup.setProfileCreatedDate(date);
                                                            gup.setVerificationToken(securityCode);
                                                            gup.setIsActive(Short.valueOf("1"));
                                                            UniDB.create(gup);
                                                        } else {
                                                            gup = gupExist.get(0);
                                                        }

                                                        List<Member1> existmember = UniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gup.getId() + "' ");

                                                        member = null;

                                                        if (existmember == null || existmember.isEmpty()) {
                                                            member = new Member1();
                                                            member.setMembershipNo(generateMemberNo());
                                                            member.setRegisteredDate(date);
                                                            member.setGeneralUserProfileId(gup);
                                                            member.setKpiValue(0.00);
                                                            member.setAvailableFund(0.00);
                                                            UniDB.create(member);

                                                            GopHasMember gopHasMember = new GopHasMember();
                                                            gopHasMember.setMemberId(member);
                                                            gopHasMember.setStartDate(new Date());
                                                            gopHasMember.setGeneralOrganizationProfileId((GeneralOrganizationProfile) UniDB.find(1, GeneralOrganizationProfile.class));
                                                            UniDB.create(gopHasMember);

                                                        } else {
                                                            member = existmember.get(0);
                                                        }

                                                        List<MemberBankAccounts> memberBankAccountsList = UniDB.searchByQuery("SELECT g FROM MemberBankAccounts g WHERE g.memberId.id='" + member.getId() + "'");

                                                        boolean g = true;
                                                        if (memberBankAccountsList.isEmpty() || memberBankAccountsList == null) {
                                                            memberBankAccounts = new MemberBankAccounts();
                                                            memberBankAccounts.setAccountNo(generateBankAccountNo(member.getMembershipNo()));
                                                            memberBankAccounts.setDate(date);
                                                            memberBankAccounts.setMemberId(member);
                                                            memberBankAccounts.setAccountTypeId((AccountType) UniDB.find(1, AccountType.class));
                                                            UniDB.create(memberBankAccounts);
                                                        } else {
                                                            memberBankAccounts = memberBankAccountsList.get(0);
                                                            for (MemberBankAccounts memberBankAccountsObj : memberBankAccountsList) {
                                                                List<LoanManager> memberLoans = UniDB.searchByQuery("SELECT g FROM LoanManager g WHERE g.memberBankAccountsId.id='" + memberBankAccountsObj.getId() + "'");
                                                                for (LoanManager memberLoan : memberLoans) {
                                                                    List<LoanStatusManager> status = UniDB.searchByQuery(
                                                                            "SELECT g FROM LoanStatusManager g WHERE g.loanManagerId.id = '" + memberLoan.getId()
                                                                            + "' AND g.loanStatusId.id IN ('1', '2', '3', '4', '5') AND g.date = ("
                                                                            + "SELECT MAX(gs.date) FROM LoanStatusManager gs WHERE gs.loanManagerId.id = '" + memberLoan.getId() + "')"
                                                                    );

                                                                    if (status.size() > 0) {
                                                                        List<InterestManager> interestManagers = UniDB.searchByQuery("SELECT g FROM InterestManager g WHERE g.loanManagerId.id='" + status.get(0).getLoanManagerId().getId() + "' ");
                                                                        if (interestManagers.size() > 0) {
                                                                            if (interestManagers.get(0).getLoanid().getId() == 2) {
                                                                                g = false;
                                                                            } else {
                                                                                g = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (g) {

                                                            List<GeneralOrganizationProfile> generalOrganizationProfileList;
                                                            if (organizationId != null) {
                                                                generalOrganizationProfileList = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.id='" + organizationId + "'");
                                                            } else {
                                                                generalOrganizationProfileList = null;
                                                            }

                                                            GeneralOrganizationProfile orgProfile = null;
                                                            if (generalOrganizationProfileList == null || generalOrganizationProfileList.isEmpty()) {
                                                                orgProfile = new GeneralOrganizationProfile();
                                                                orgProfile.setName(nameOftheEmployerOrBusiness);
                                                                orgProfile.setPhoneNo(contactNumberOftheEmployerOrBusiness);
                                                                orgProfile.setAddress1(employerAddressOneOrBusiness);
                                                                orgProfile.setAddress2(employerAddressTwoOrBusiness);
                                                                orgProfile.setAddress3(employerAddressThreeOrBusiness);
                                                                orgProfile.setOrganizationTypeId((OrganizationType) UniDB.find(4, OrganizationType.class));
                                                                UniDB.create(orgProfile);

                                                            } else {
                                                                orgProfile = generalOrganizationProfileList.get(0);
                                                            }

                                                            List<MemberOrganizationsHistory> mohLists = UniDB.searchByQuery("SELECT g FROM MemberOrganizationsHistory g WHERE g.generalOrganizationProfileId.id='" + orgProfile.getId() + "' AND g.memberId.id='" + member.getId() + "'");

                                                            if (mohLists.isEmpty()) {
                                                                MemberOrganizationsHistory moh = new MemberOrganizationsHistory();
                                                                moh.setDate(new Date());
                                                                moh.setGeneralOrganizationProfileId(orgProfile);
                                                                moh.setIsActive(Short.valueOf("1"));
                                                                moh.setMemberId(member);
                                                                UniDB.create(moh);
                                                            } else {
                                                                mohLists.get(0).setDate(new Date());
                                                                mohLists.get(0).setIsActive(Short.valueOf("1"));
                                                            }

//                                                                            String loanid = generateRefernceId(member.getMembershipNo());
                                                            String loanid = memberBankAccounts.getAccountNo();

                                                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success ! ! !", "Details Saved Successful.Please check your email");
                                                            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                                                            FacesContext.getCurrentInstance().addMessage("", msg);

                                                            Date dateTwo = new Date();
                                                            System.out.println("updateOfferManager");
                                                            List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + nic + "'");
                                                            System.out.println("nic " + nic);
                                                            if (!loanCustomer.isEmpty()) {
                                                                System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
                                                                List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
                                                                System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
                                                                if (!offerManager.isEmpty()) {
                                                                    List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='6'");
                                                                    if (crhList.isEmpty()) {
                                                                        CustomerResponseHistory crh = new CustomerResponseHistory();
                                                                        crh.setDate(dateTwo);
                                                                        crh.setOfferManagerId(offerManager.get(0));
                                                                        crh.setResponseStatusId((ResponseStatus) UniDB.find(6, ResponseStatus.class));
                                                                        UniDB.create(crh);
                                                                    }
                                                                }
                                                            }

                                                            System.out.println("saved successfull");
                                                            FacesContext facesContext = FacesContext.getCurrentInstance();
                                                            ExternalContext externalContext = facesContext.getExternalContext();
                                                            externalContext.redirect(externalContext.getRequestContextPath() + "/tasks/university-due-payment/loan-application.xhtml");
                                                            facesContext.responseComplete();
                                                        } else {
                                                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User has a pending loan application");
                                                            FacesContext.getCurrentInstance().addMessage("", msg);
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please check the checkbox that Agreeing terms and conditions");
                                                    FacesContext.getCurrentInstance().addMessage("", msg);
                                                }
                                            } else {
                                                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select your employement type");
                                                FacesContext.getCurrentInstance().addMessage("", msg);

                                            }
                                        } else {
                                            displayLoanTenture = true;
                                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Select date of birth or you have entered invalid date of birth");
                                            FacesContext.getCurrentInstance().addMessage("", msg);
                                        }
                                    } else {
                                        displayGN = true;
                                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Select the GN Division");
                                        FacesContext.getCurrentInstance().addMessage("", msg);
                                    }
                                } else {
                                    displayDivisiona = true;
                                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Select the Divisional Secretarial");
                                    FacesContext.getCurrentInstance().addMessage("", msg);
                                }
                            } else {
                                displayDistrict = true;
                                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Select the District");
                                FacesContext.getCurrentInstance().addMessage("", msg);
                            }
                        } else {
                            displayProvince = true;
                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Select the province");
                            FacesContext.getCurrentInstance().addMessage("", msg);
                        }
                    } else {
                        displayAddress03 = true;
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Enter the address three");
                        FacesContext.getCurrentInstance().addMessage("", msg);
                    }
                } else {
                    displayAddress02 = true;
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Enter the address two");
                    FacesContext.getCurrentInstance().addMessage("", msg);
                }
            } else {
                displayAddress01 = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Enter the address one");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }
        } else {
            displayMobileNo = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Enter the mobile number");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public String generateMemberNo() {

        Province province = (Province) UniDB.find(Integer.parseInt(selectedProvinceId), Province.class);
        String provinceCode = province.getId().toString();
        System.out.println("provinceCode :" + provinceCode);

        District district = (District) UniDB.find(Integer.parseInt(selectedDistrict), District.class);
        String districtCode = district.getDistrictCode();
        System.out.println("districtCode :" + districtCode);

        DivisionalSecretarial divisionalSecretarial = (DivisionalSecretarial) UniDB.find(Integer.parseInt(selectedDivisionalSecretarial), DivisionalSecretarial.class);
        String divisionalSecretarialCode = divisionalSecretarial.getDsCode();
        System.out.println("divisionalSecretarialCode :" + divisionalSecretarialCode);

        GnDivision gnDivision = (GnDivision) UniDB.find(Integer.parseInt(selectedGnDivision), GnDivision.class);
        String gnDivisionCode = gnDivision.getGnDivisionCode();
        System.out.println("gnDivisionCode :" + gnDivisionCode);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(provinceCode);
        stringBuilder.append(districtCode);
        stringBuilder.append(divisionalSecretarialCode);
        stringBuilder.append(gnDivisionCode);

        List<Member1> member1 = UniDB.searchByQueryLimit("SELECT g FROM Member1 g ORDER BY g.registeredDate DESC", 1);

        int lastInsertedId;

        if (member1.isEmpty() || member1 == null) {
            lastInsertedId = 0;
        } else {
            lastInsertedId = member1.get(0).getId();
        }

        String formattedId = formatID(lastInsertedId);

        System.out.println("formattedId " + formattedId);

        stringBuilder.append(formattedId);
        stringBuilder.append("00");
        String memberId = stringBuilder.toString();
        System.out.println("member Id " + memberId);
        return memberId;
    }

    public static String formatID(int id) {

        return String.format("%07d", id);
    }

    public String generateBankAccountNo(String membershipNo) {
        AccountType accountType = (AccountType) UniDB.find(1, AccountType.class);
        int accountTypeNo = accountType.getId();

        String formattedAccountTypeId = formatAccountID(accountTypeNo);

        System.out.println("formattedAccountTypeId " + formattedAccountTypeId);

        String remvoedId = membershipNo.substring(0, membershipNo.length() - 2);

        System.out.println("remvoedId " + remvoedId);

        StringBuilder builder = new StringBuilder();
        builder.append(remvoedId);
        builder.append(formattedAccountTypeId);

        System.out.println("builder " + builder.toString());

        return builder.toString();
    }

    public static String formatAccountID(int id) {

        return String.format("%02d", id);
    }

//    public String generateRefernceId(String memberId) {
//        List<Integer> l = UniDB.searchByQuerySingle("SELECT lr.id FROM LoanManager lr ORDER BY lr.id DESC");
//        int i = 0;
//        if (!l.isEmpty()) {
//            i = l.get(0);
//        }
//
//        LocalDate currentDate = LocalDate.now();
//        String date = currentDate.format(DATE_FORMAT);
//        int uniqueNumber = COUNTER.incrementAndGet(); // Increment and get the counter value
//        String formattedNumber = String.format("%04d", uniqueNumber); // Zero pad to ensure 4 digits
//        i++;
//        return PREFIX + "-" + date + "-" + formattedNumber + i;
//    }
    public void gender() {
        getGenderList().clear();
        List<Gender> genderList = UniDB.searchByQuery("SELECT g FROM Gender g");
        getGenderList().add(new SelectItem("0", "Select"));
        if (genderList.size() > 0) {
            for (Gender genderObj : genderList) {
                getGenderList().add(new SelectItem(genderObj.getId(), genderObj.getName()));
            }
        }
    }

    public void province() {
        getProvinceList().clear();
        List<Province> province = UniDB.searchByQuery("SELECT g FROM Province g ORDER BY g.name ASC");
        getProvinceList().add(new SelectItem("0", "Select"));
        if (province.size() > 0) {
            for (Province provinceObj : province) {
                getProvinceList().add(new SelectItem(provinceObj.getId(), provinceObj.getName()));
            }
        }
    }

    public void district() {
        getDistrictList().clear();
        System.out.println("province id " + selectedProvinceId);
        List<District> districts = UniDB.searchByQuery("SELECT g FROM District g WHERE g.provinceId.id='" + selectedProvinceId + "' ORDER BY g.name ASC ");
        getDistrictList().add(new SelectItem("0", "Select"));
        System.out.println("districts.size() " + districts.size());
        if (!districts.isEmpty()) {
            for (District districtObj : districts) {
                System.out.println("districtObj.getId() " + districtObj.getId() + " " + "districtObj.getName" + districtObj.getName());
                getDistrictList().add(new SelectItem(districtObj.getId(), districtObj.getName()));
            }
        }
    }

    public void divisionalSecretarial() {
        getDivisionalSecretarialList().clear();
        System.out.println("district id " + selectedDistrict);
        getDivisionalSecretarialList().add(new SelectItem("0", "Select"));
        List<DivisionalSecretarial> divisionalSecretarials = UniDB.searchByQuery("SELECT g FROM DivisionalSecretarial g WHERE g.districtId.id='" + selectedDistrict + "' ORDER BY g.name ASC");

        System.out.println("districts.size() " + divisionalSecretarials.size());
        for (DivisionalSecretarial divisionalSecretarialObj : divisionalSecretarials) {
            getDivisionalSecretarialList().add(new SelectItem(divisionalSecretarialObj.getId(), divisionalSecretarialObj.getName()));
        }

    }

    public void gnDivision() {
        getGnDivisionList().clear();
        getGnDivisionList().add(new SelectItem("0", "Select"));
        String get_gib = "SELECT g FROM GnDivision g WHERE g.divisionalSecretarialId.id='" + selectedDivisionalSecretarial + "' ORDER BY g.name ASC";
        List<GnDivision> gnDivisions = UniDB.searchByQuery(get_gib);

        for (GnDivision gnDivisionObj : gnDivisions) {
            getGnDivisionList().add(new SelectItem(gnDivisionObj.getId().toString(), gnDivisionObj.getName()));
        }
    }

    public void repaymentPeriod() {
        getRepaymentPeriodList().clear();
        List<RepaymentPeriod> repaymentPeriods = UniDB.searchByQuery("SELECT g FROM RepaymentPeriod g WHERE g.loanid.id='1' AND g.isActive='1'");
        getRepaymentPeriodList().add(new SelectItem("", "Select"));
        for (RepaymentPeriod repaymentPeriodObj : repaymentPeriods) {
            getRepaymentPeriodList().add(new SelectItem(repaymentPeriodObj.getId().toString(), repaymentPeriodObj.getPeriod()));
        }
    }

    public void calulateLoan() {
        FacesMessage msg;
        System.out.println("calculateMonthlyInstallementIsLessThanGuarantorsIncome() " + calculateMonthlyInstallementIsLessThanGuarantorsIncome());
        if (calculateMonthlyInstallementIsLessThanGuarantorsIncome()) {
            loanStrucure.clear();
            System.out.println("loan");
            double loanAmount = 0.0;
            int installmentPeriod = 0;
            double interest_rate = 0.0;
            double monthly_installement = 0.0;

            System.out.println("expectedLoanAmount " + expectedLoanAmount);
            System.out.println("repayementPeriod " + repayementPeriod);
            if (this.expectedLoanAmount != 0 && this.expectedLoanAmount > 0) {
                loanAmount = this.expectedLoanAmount;
                System.out.println("loan amount");
            }
            if (this.repayementPeriod != null && !this.repayementPeriod.equals("0")) {
                List<RepaymentPeriod> repaymentPeriods = UniDB.searchByQuery("SELECT g FROM RepaymentPeriod g WHERE g.id='" + repayementPeriod + "'");
                installmentPeriod = Integer.parseInt(repaymentPeriods.get(0).getPeriod());
                System.out.println("period");
            }
            if (interest_rate != 0.0) {
                interest_rate = this.interestRate;
            }
            if (this.monthlyinstallement != 0 && this.monthlyinstallement > 0) {
                monthly_installement = this.monthlyinstallement;
                System.out.println("monthly");
            }

            if (correctMonthlyInstallmentBoolean) {
                monthly_installement = correctMonthlyInstallment;
            }

            if (loanAmount == 0.0) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loan Amount Empty or Invalid!", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else if (installmentPeriod == 0) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Please Select a Repayment Period  !", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else if (monthly_installement == 0) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Please Enter a Monthly Installement  !", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                System.out.println("loan cal");
                loanStrucure.clear();

                Date date = new Date();
                String dt = ComLib.getDate(date);
                String[] dateArray = dt.split("-");
                int year = Integer.parseInt(dateArray[0]);
                int month = Integer.parseInt(dateArray[1]);

                if (month == 12) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }

                double val = interestRate / 100;
                double rate = val / 12;

                double totalInterestPaid = 0.0;
                double paidCapital = 0.0;
                double totalPayment = 0.0;
                double openingBalance = loanAmount;
                int months = 0;

                while (openingBalance > 0 && months < installmentPeriod) {
                    String lastDay = ComLib.getDate(ComLib.getLastDaYOfMonthFromMonth(year, month));
                    lastDay = lastDay.substring(0, 7) + "-25";

                    double interest = openingBalance * rate;
                    totalInterestPaid += interest;

                    double monthlyPayment = monthly_installement;
                    if (openingBalance + interest < monthly_installement) {
                        monthlyPayment = openingBalance + interest;
                    }

                    totalPayment += monthlyPayment;
                    double principalAmount = monthlyPayment - interest;
                    paidCapital += principalAmount;

                    getLoanStrucure().add(new LoanCalculatorRecords(
                            months + 1, lastDay, openingBalance,
                            principalAmount, interest, totalInterestPaid,
                            monthlyPayment, paidCapital, totalPayment
                    ));

                    openingBalance -= principalAmount;
                    months++;

                    if (month == 12) {
                        month = 1;
                        year++;
                    } else {
                        month++;
                    }
                }
                actualMonth = months;
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String formattedNumber = decimalFormat.format(monthly_installement);
                actualMonthlyInstallement = formattedNumber;
                if (openingBalance > 0 && !correctMonthlyInstallmentBoolean) {
                    error_message = "Invalid installment period: The given period is not enough to repay the loan.So we have calulated the matching monthly installement for selected loan tenture";

                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", error_message);
                    FacesContext.getCurrentInstance().addMessage("", msg);
                    System.out.println("Invalid installment period: The given period is not enough to repay the loan.");
                    // Calculate the correct monthly installment
                    System.out.println("installmentPeriod " + installmentPeriod);
                    correctMonthlyInstallment = calculateMonthlyInstallment(loanAmount, interestRate, installmentPeriod);
                    correctMonthlyInstallmentBoolean = true;
                    System.out.println("Correct Monthly Installment to repay the loan in " + installmentPeriod + " months: " + correctMonthlyInstallment);
                    // Recalculate loan structure with correct monthly installment
                    calulateLoan();

                }
                setActualLoanDetails();
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "The entered monthly installement is exceeding 60%", "");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public void setSelectedEmployer() {
        System.out.println("nameOftheEmployer 1" + nameOftheEmployerOrBusiness);

        List<GeneralOrganizationProfile> generalUserProfilesList = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.name like '%" + nameOftheEmployerOrBusiness + "%' ");
        System.out.println("generalUserProfilesList " + generalUserProfilesList.isEmpty());
        if (!generalUserProfilesList.isEmpty()) {
            for (GeneralOrganizationProfile generalOrganizationProfile : generalUserProfilesList) {
                contactNumberOftheEmployerOrBusiness = generalOrganizationProfile.getPhoneNo();
                employerAddressOneOrBusiness = generalOrganizationProfile.getAddress1();
                employerAddressTwoOrBusiness = generalOrganizationProfile.getAddress2();
                employerAddressThreeOrBusiness = generalOrganizationProfile.getAddress3();
                System.out.println("contactNumberOftheEmployer  " + contactNumberOftheEmployerOrBusiness);
                System.out.println("employerAddressOne  " + employerAddressOneOrBusiness);
                System.out.println("employerAddressTwo  " + employerAddressTwoOrBusiness);
                System.out.println("employerAddressThree  " + employerAddressThreeOrBusiness);
            }
        }
    }

    public List<String> loadEmployers(String query) {
        System.out.println("loadEmployers " + query);

        List<String> loadEmployers = new ArrayList<>();

        List<GeneralOrganizationProfile> generalUserProfilesList = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.name LIKE '%" + query + "%' AND g.organizationTypeId.id='4' order by  g.id ASC");
        System.out.println("generalUserProfilesList " + generalUserProfilesList.isEmpty());
        System.out.println("generalUserProfilesList " + generalUserProfilesList.size());
        if (!generalUserProfilesList.isEmpty()) {
            for (GeneralOrganizationProfile generalOrganizationProfile : generalUserProfilesList) {
                loadEmployers.add(generalOrganizationProfile.getName());
            }
        } else {
            nameOftheEmployerOrBusiness = query;
            System.out.println("nameOftheEmployerOrBusiness loadEmployers(String query)" + nameOftheEmployerOrBusiness);
        }
        return loadEmployers;
    }

    public void setActualLoanDetails() {
        actualLoanTenture = actualMonth;
        correctMonthlyInstallmentBoolean = false;
        loanCalculated = true;

    }

    public static double calculateMonthlyInstallment(double loanAmount, double annualInterestRate, int numPayments) {
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        return (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numPayments));
    }

    public double pmt(double rate_per_period, double number_of_payments, double present_value, double future_value, int type) {

        double val = 0.0;
        if (rate_per_period != 0.0) {
            // Interest rate exists
            double q = Math.pow(1 + rate_per_period, number_of_payments);
            val = -(rate_per_period * (future_value + (q * present_value))) / ((-1 + q) * (1 + rate_per_period * (type)));

        } else if (number_of_payments != 0.0) {
            // No interest rate, but number of payments existsfixedInstallment
            val = -(future_value + present_value) / number_of_payments;
        }
        return val;

    }

    private boolean isEmployementTypeSelected(String type) {
        return !type.equals("0");
    }

    private static class OrganizationBranchesList {

        private int branchId;
        private String branchName;

        public OrganizationBranchesList(int branchId, String branchName) {
            this.branchId = branchId;
            this.branchName = branchName;
        }

        public int getBranchId() {
            return branchId;
        }

        public void setBranchId(int branchId) {
            this.branchId = branchId;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

    }

    public class LoanCalculatorRecords implements Serializable {

        private int monthNumber;
        private String repaymentDate;
        private double openingBalance;
        private double principalAmount;
        private double monthlyInterest;
        private double totalInterestPaid;
        private double monthlyPayment;
        private double paidCapital;
        private double totalPayment;

        public LoanCalculatorRecords(int monthNumber, String repaymentDate, double openingBalance, double principalAmount, double monthlyInterest, double totalInterestPaid, double monthlyPayment, double paidCapital, double totalPayment) {
            this.monthNumber = monthNumber;
            this.repaymentDate = repaymentDate;
            this.openingBalance = openingBalance;
            this.principalAmount = principalAmount;
            this.monthlyInterest = monthlyInterest;
            this.totalInterestPaid = totalInterestPaid;
            this.monthlyPayment = monthlyPayment;
            this.paidCapital = paidCapital;
            this.totalPayment = totalPayment;
        }

        public int getMonthNumber() {
            return monthNumber;
        }

        public void setMonthNumber(int monthNumber) {
            this.monthNumber = monthNumber;
        }

        public String getRepaymentDate() {
            return repaymentDate;
        }

        public void setRepaymentDate(String repaymentDate) {
            this.repaymentDate = repaymentDate;
        }

        public double getOpeningBalance() {
            return openingBalance;
        }

        public void setOpeningBalance(double openingBalance) {
            this.openingBalance = openingBalance;
        }

        public double getPrincipalAmount() {
            return principalAmount;
        }

        public void setPrincipalAmount(double principalAmount) {
            this.principalAmount = principalAmount;
        }

        public double getMonthlyInterest() {
            return monthlyInterest;
        }

        public void setMonthlyInterest(double monthlyInterest) {
            this.monthlyInterest = monthlyInterest;
        }

        public double getTotalInterestPaid() {
            return totalInterestPaid;
        }

        public void setTotalInterestPaid(double totalInterestPaid) {
            this.totalInterestPaid = totalInterestPaid;
        }

        public double getMonthlyPayment() {
            return monthlyPayment;
        }

        public void setMonthlyPayment(double monthlyPayment) {
            this.monthlyPayment = monthlyPayment;
        }

        public double getPaidCapital() {
            return paidCapital;
        }

        public void setPaidCapital(double paidCapital) {
            this.paidCapital = paidCapital;
        }

        public double getTotalPayment() {
            return totalPayment;
        }

        public void setTotalPayment(double totalPayment) {
            this.totalPayment = totalPayment;
        }

    }

    class GenderList implements Serializable {

        private int id;
        private String value;

        public GenderList(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public boolean isDisplayLoanAmount() {
        return displayLoanAmount;
    }

    public void setDisplayLoanAmount(boolean displayLoanAmount) {
        this.displayLoanAmount = displayLoanAmount;
    }

    public boolean isDisplayFullName() {
        return displayFullName;
    }

    public void setDisplayFullName(boolean displayFullName) {
        this.displayFullName = displayFullName;
    }

    public boolean isDisplayNameWithIntials() {
        return displayNameWithIntials;
    }

    public void setDisplayNameWithIntials(boolean displayNameWithIntials) {
        this.displayNameWithIntials = displayNameWithIntials;
    }

    public boolean isDisplayNic() {
        return displayNic;
    }

    public void setDisplayNic(boolean displayNic) {
        this.displayNic = displayNic;
    }

    public boolean isDisplayMobileNo() {
        return displayMobileNo;
    }

    public void setDisplayMobileNo(boolean displayMobileNo) {
        this.displayMobileNo = displayMobileNo;
    }

    public boolean isDisplayGender() {
        return displayGender;
    }

    public void setDisplayGender(boolean displayGender) {
        this.displayGender = displayGender;
    }

    public boolean isDisplayBooleanTwo() {
        return displayBooleanTwo;
    }

    public void setDisplayBooleanTwo(boolean displayBooleanTwo) {
        this.displayBooleanTwo = displayBooleanTwo;
    }

    public boolean isDisplayDob() {
        return displayDob;
    }

    public void setDisplayDob(boolean displayDob) {
        this.displayDob = displayDob;
    }

    public boolean isDisplayEmail() {
        return displayEmail;
    }

    public void setDisplayEmail(boolean displayEmail) {
        this.displayEmail = displayEmail;
    }

    public boolean isDisplayAddress01() {
        return displayAddress01;
    }

    public void setDisplayAddress01(boolean displayAddress01) {
        this.displayAddress01 = displayAddress01;
    }

    public boolean isDisplayAddress02() {
        return displayAddress02;
    }

    public void setDisplayAddress02(boolean displayAddress02) {
        this.displayAddress02 = displayAddress02;
    }

    public boolean isDisplayAddress03() {
        return displayAddress03;
    }

    public void setDisplayAddress03(boolean displayAddress03) {
        this.displayAddress03 = displayAddress03;
    }

    public boolean isDisplayProvince() {
        return displayProvince;
    }

    public void setDisplayProvince(boolean displayProvince) {
        this.displayProvince = displayProvince;
    }

    public boolean isDisplayDistrict() {
        return displayDistrict;
    }

    public void setDisplayDistrict(boolean displayDistrict) {
        this.displayDistrict = displayDistrict;
    }

    public boolean isDisplayDivisiona() {
        return displayDivisiona;
    }

    public void setDisplayDivisiona(boolean displayDivisiona) {
        this.displayDivisiona = displayDivisiona;
    }

    public boolean isDisplayGN() {
        return displayGN;
    }

    public void setDisplayGN(boolean displayGN) {
        this.displayGN = displayGN;
    }

    public boolean isDisplayExpectedLoanAmount() {
        return displayExpectedLoanAmount;
    }

    public void setDisplayExpectedLoanAmount(boolean displayExpectedLoanAmount) {
        this.displayExpectedLoanAmount = displayExpectedLoanAmount;
    }

    public boolean isDisplaymonthlyInstallment() {
        return displaymonthlyInstallment;
    }

    public void setDisplaymonthlyInstallment(boolean displaymonthlyInstallment) {
        this.displaymonthlyInstallment = displaymonthlyInstallment;
    }

    public boolean isDisplayLoanTenture() {
        return displayLoanTenture;
    }

    public void setDisplayLoanTenture(boolean displayLoanTenture) {
        this.displayLoanTenture = displayLoanTenture;
    }

    public int getActualLoanTenture() {
        return actualLoanTenture;
    }

    public void setActualLoanTenture(int actualLoanTenture) {
        this.actualLoanTenture = actualLoanTenture;
    }

    public String getActualMonthlyInstallement() {
        return actualMonthlyInstallement;
    }

    public void setActualMonthlyInstallement(String actualMonthlyInstallement) {
        this.actualMonthlyInstallement = actualMonthlyInstallement;
    }

    public double getDueCourseFee() {
        return dueCourseFee;
    }

    public void setDueCourseFee(double dueCourseFee) {
        this.dueCourseFee = dueCourseFee;
    }

    public boolean isDisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(boolean displayAddress) {
        this.displayAddress = displayAddress;
    }

    public boolean isTelephone() {
        return telephone;
    }

    public void setTelephone(boolean telephone) {
        this.telephone = telephone;
    }

    public boolean isDisplayBoolean() {
        return displayBoolean;
    }

    public void setDisplayBoolean(boolean displayBoolean) {
        this.displayBoolean = displayBoolean;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
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

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getAddressThree() {
        return addressThree;
    }

    public void setAddressThree(String addressThree) {
        this.addressThree = addressThree;
    }

    public String getInputTextFieldOneValue() {
        return inputTextFieldOneValue;
    }

    public void setInputTextFieldOneValue(String inputTextFieldOneValue) {
        this.inputTextFieldOneValue = inputTextFieldOneValue;
    }

    public boolean isDisplayGuarantorSection() {
        return displayGuarantorSection;
    }

    public void setDisplayGuarantorSection(boolean displayGuarantorSection) {
        this.displayGuarantorSection = displayGuarantorSection;
    }

    public String getExistingMember() {
        return existingMember;
    }

    public void setExistingMember(String existingMember) {
        this.existingMember = existingMember;
    }

    public List<SelectItem> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<SelectItem> relationList) {
        this.relationList = relationList;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getSelectedEmployementSector() {
        return selectedEmployementSector;
    }

    public void setSelectedEmployementSector(String selectedEmployementSector) {
        this.selectedEmployementSector = selectedEmployementSector;
    }

    public String getSelectedprofession() {
        return selectedprofession;
    }

    public void setSelectedprofession(String selectedprofession) {
        this.selectedprofession = selectedprofession;
    }

    public List<SelectItem> getEmployementSectorsList() {
        return employementSectorsList;
    }

    public void setEmployementSectorsList(List<SelectItem> employementSectorsList) {
        this.employementSectorsList = employementSectorsList;
    }

    public List<SelectItem> getProfessionsList() {
        return professionsList;
    }

    public void setProfessionsList(List<SelectItem> professionsList) {
        this.professionsList = professionsList;
    }

    public List<SelectItem> getGnDivisionList() {
        return gnDivisionList;
    }

    public void setGnDivisionList(List<SelectItem> gnDivisionList) {
        this.gnDivisionList = gnDivisionList;
    }

    public String getSelectedGnDivision() {
        return selectedGnDivision;
    }

    public void setSelectedGnDivision(String selectedGnDivision) {
        System.out.println("Selected GN Division: " + selectedGnDivision);
        this.selectedGnDivision = selectedGnDivision;
    }

    public List<LoanCalculatorRecords> getLoanStrucure() {
        return loanStrucure;
    }

    public void setLoanStrucure(List<LoanCalculatorRecords> loanStrucure) {
        this.loanStrucure = loanStrucure;
    }

    public List<SelectItem> getRepaymentPeriodList() {
        return repaymentPeriodList;
    }

    public void setRepaymentPeriodList(List<SelectItem> repaymentPeriodList) {
        this.repaymentPeriodList = repaymentPeriodList;
    }

    public double getExpectedLoanAmount() {
        return expectedLoanAmount;
    }

    public void setExpectedLoanAmount(double expectedLoanAmount) {
        this.expectedLoanAmount = expectedLoanAmount;
    }

    public double getMonthlyinstallement() {
        return monthlyinstallement;
    }

    public void setMonthlyinstallement(double monthlyinstallement) {
        this.monthlyinstallement = monthlyinstallement;
    }

    public String getRepayementPeriod() {
        return repayementPeriod;
    }

    public void setRepayementPeriod(String repayementPeriod) {
        this.repayementPeriod = repayementPeriod;
    }

    public List<SelectItem> getDivisionalSecretarialList() {
        return divisionalSecretarialList;
    }

    public void setDivisionalSecretarialList(List<SelectItem> divisionalSecretarialList) {
        this.divisionalSecretarialList = divisionalSecretarialList;
    }

    public String getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(String SelectedDistrict) {
        this.selectedDistrict = SelectedDistrict;
    }

    public String getSelectedDivisionalSecretarial() {
        return selectedDivisionalSecretarial;
    }

    public void setSelectedDivisionalSecretarial(String SelectedDivisionalSecretarial) {
        this.selectedDivisionalSecretarial = SelectedDivisionalSecretarial;
    }

    public List<SelectItem> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<SelectItem> districtList) {
        this.districtList = districtList;
    }

    public List<SelectItem> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<SelectItem> provinceList) {
        this.provinceList = provinceList;
    }

    public String getSelectedProvinceId() {
        return selectedProvinceId;
    }

    public void setSelectedProvinceId(String selectedProvinceId) {
        this.selectedProvinceId = selectedProvinceId;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public List<SelectItem> getGenderList() {
        return genderList;
    }

    public void setGenderList(List<SelectItem> genderList) {
        this.genderList = genderList;
    }

    public List<SelectItem> getEmployementTypeList() {
        return employementTypeList;
    }

    public void setEmployementTypeList(List<SelectItem> employementTypeList) {
        this.employementTypeList = employementTypeList;
    }

    public boolean isSelfEmployed() {
        return selfEmployed;
    }

    public void setSelfEmployed(boolean selfEmployed) {
        this.selfEmployed = selfEmployed;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }

    public String getSelectedEmployementType() {
        return selectedEmployementType;
    }

    public void setSelectedEmployementType(String selectedEmployementType) {
        this.selectedEmployementType = selectedEmployementType;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getNameOftheEmployerOrBusiness() {
        return nameOftheEmployerOrBusiness;
    }

    public void setNameOftheEmployerOrBusiness(String nameOftheEmployerOrBusiness) {
        System.out.println("nameOftheEmployerOrBusiness :" + nameOftheEmployerOrBusiness);
        this.nameOftheEmployerOrBusiness = nameOftheEmployerOrBusiness;
    }

    public String getContactNumberOftheEmployerOrBusiness() {
        return contactNumberOftheEmployerOrBusiness;
    }

    public void setContactNumberOftheEmployerOrBusiness(String contactNumberOftheEmployerOrBusiness) {
        this.contactNumberOftheEmployerOrBusiness = contactNumberOftheEmployerOrBusiness;
    }

    public String getEmployerAddressOneOrBusiness() {
        return employerAddressOneOrBusiness;
    }

    public void setEmployerAddressOneOrBusiness(String employerAddressOneOrBusiness) {
        this.employerAddressOneOrBusiness = employerAddressOneOrBusiness;
    }

    public String getEmployerAddressTwoOrBusiness() {
        return employerAddressTwoOrBusiness;
    }

    public void setEmployerAddressTwoOrBusiness(String employerAddressTwoOrBusiness) {
        this.employerAddressTwoOrBusiness = employerAddressTwoOrBusiness;
    }

    public String getEmployerAddressThreeOrBusiness() {
        return employerAddressThreeOrBusiness;
    }

    public void setEmployerAddressThreeOrBusiness(String employerAddressThreeOrBusiness) {
        this.employerAddressThreeOrBusiness = employerAddressThreeOrBusiness;
    }

    public Member1 getMember() {
        return member;
    }

    public void setMember(Member1 member) {
        this.member = member;
    }

    public MemberBankAccounts getMemberBankAccounts() {
        return memberBankAccounts;
    }

    public void setMemberBankAccounts(MemberBankAccounts memberBankAccounts) {
        this.memberBankAccounts = memberBankAccounts;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public boolean isGenderBool() {
        return genderBool;
    }

    public void setGenderBool(boolean genderBool) {
        this.genderBool = genderBool;
    }

    public List<GenderList> getGender_list() {
        return gender_list;
    }

    public void setGender_list(List<GenderList> gender_list) {
        this.gender_list = gender_list;
    }

    public int getSelectedGender() {
        return selectedGender;
    }

    public void setSelectedGender(int selectedGender) {
        this.selectedGender = selectedGender;
    }

    public List<OrganizationBranchesList> getOrg_branch_list() {
        return org_branch_list;
    }

    public void setOrg_branch_list(List<OrganizationBranchesList> org_branch_list) {
        this.org_branch_list = org_branch_list;
    }

    public String getSelectedBranch() {
        return selectedBranch;
    }

    public void setSelectedBranch(String selectedBranch) {
        this.selectedBranch = selectedBranch;
    }

    public boolean isOrgBoolean() {
        return orgBoolean;
    }

    public void setOrgBoolean(boolean orgBoolean) {
        this.orgBoolean = orgBoolean;
    }

    public List<MaterializedStudentLoanEligibleStudentTable> getMaterializedObj() {
        return materializedObj;
    }

    public void setMaterializedObj(List<MaterializedStudentLoanEligibleStudentTable> materializedObj) {
        this.materializedObj = materializedObj;
    }

}
