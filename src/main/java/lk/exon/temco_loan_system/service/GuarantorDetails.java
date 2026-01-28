/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import com.google.cloud.storage.Storage;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.exon.temco.filteration.Filteration;
import lk.exon.temco.security.Security;
import lk.exon.temco_loan_system.common.ComLib;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.District;
import lk.exon.temco_loan_system.entity.DivisionalSecretarial;
import lk.exon.temco_loan_system.entity.EmployementType;
import lk.exon.temco_loan_system.entity.GeneralOrganizationProfile;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.entity.GnDivision;
import lk.exon.temco_loan_system.entity.GopHasMember;
import lk.exon.temco_loan_system.entity.GurantorManager;
import lk.exon.temco_loan_system.entity.LoanApplicantGurantor;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.Member1;
import lk.exon.temco_loan_system.entity.MemberOrganizationsHistory;
import lk.exon.temco_loan_system.entity.OrganizationType;
import lk.exon.temco_loan_system.entity.Province;
import static lk.exon.temco_loan_system.service.LoanRequestForm.formatID;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import lk.exon.temco.tools.NewMailSender;
import lk.exon.temco.tools.RegexVerifications;
import lk.exon.temco.tools.TemcoVerifications;
import lk.exon.temco_loan_system.common.ComPath;
import lk.exon.temco_loan_system.entity.CustomerResponseHistory;
import lk.exon.temco_loan_system.entity.GurantorCount;
import lk.exon.temco_loan_system.entity.LoanCustomer;
import lk.exon.temco_loan_system.entity.MemberDocuments;
import lk.exon.temco_loan_system.entity.OfferManager;
import lk.exon.temco_loan_system.entity.ResponseStatus;
import lk.exon.temco_loan_system.entity.UniversalUserDocument;
import lk.exon.temco_loan_system.entity.UserType;

/**
 *
 * @author USER
 */
@Named
@LocalBean
@SessionScoped
public class GuarantorDetails implements Serializable {

    private String loanId;
    private Double monthlyInstallement;

    private LoanManager loanManager;

    private String firstGuarantorFirstName;
    private String firstGuarantorlastName;
    private String firstGuarantorNic;
    private UploadedFile firstGuarantorUploadFIleOne;
    private UploadedFile firstGuarantorUploadFIleTwo;
    private String firstGuarantorMobileNo;
    private String firstGuarantorDocumentType;

    private String secondGuarantorFirstName;
    private String secondGuarantorLastName;
    private String secondGuarantorNic;
    private Double secondGuarantorGrossIncome;
    private Double secondGuarantorNetIncome;
    private UploadedFile secondGuarantorUploadFIleOne;
    private UploadedFile secondGuarantorUploadFIleTwo;
    private String secondGuarantorMobileNo;
    private String secondGuarantorDocumentType;

    private List<SelectItem> documentTypeList;

    private double interestRate = 6;

    private boolean methodCalled = true;

    private String loanIdPara;

    private ComLib ComLib;

    private String firstGuarantorSelectedEmployementType;
    private List<SelectItem> firstGuarantoremployementTypeList;
    private boolean firstGuarantorselfEmlpoyed = false;
    private boolean firstGuarantoremlpoyed = false;

    private String secondGuarantorSelectedEmployementType;
    private List<SelectItem> secondGuarantoremployementTypeList;
    private boolean secondGuarantorselfEmlpoyed = false;
    private boolean secondGuarantoremlpoyed = false;

    private String selectedProvinceId;
    private String selectedDistrict;
    private String selectedDivisionalSecretarial;
    private String selectedGnDivision;

    private String selectedProvinceIdSecond;
    private String selectedDistrictSecond;
    private String selectedDivisionalSecretarialSecond;
    private String selectedGnDivisionSecond;

    private String firstGnameOftheEmployerOrBusiness;
    private String firstGcontactNumberOftheEmployerOrBusiness;
    private String firstGemployerAddressOneOrBusiness;
    private String firstGemployerAddressTwoOrBusiness;
    private String firstGemployerAddressThreeOrBusiness;

    private String secondGnameOftheEmployerOrBusiness;
    private String secondGcontactNumberOftheEmployerOrBusiness;
    private String secondGemployerAddressOneOrBusiness;
    private String secondGemployerAddressTwoOrBusiness;
    private String secondGemployerAddressThreeOrBusiness;

    private List<SelectItem> provinceList = new ArrayList<>();
    private List<SelectItem> districtList = new ArrayList<>();
    private List<SelectItem> divisionalSecretarialList = new ArrayList<>();
    private List<SelectItem> gnDivisionList = new ArrayList<>();

    private List<SelectItem> provinceListSecond = new ArrayList<>();
    private List<SelectItem> districtListSecond = new ArrayList<>();
    private List<SelectItem> divisionalSecretarialListSecond = new ArrayList<>();
    private List<SelectItem> gnDivisionListSecond = new ArrayList<>();

    private String organizationIdFirst;
    private String organizationIdSecond;

    private String folderPath;

    private Date date;

    private Storage storage;

    private boolean x = true;
    private boolean firstGsaved;
    private boolean secondGsaved;

    private GurantorCount gurantorNo;

    String nic = "";

    @Inject
    LoanRequestForm LoanRequestForm;

    @EJB
    private UniDBLocal UniDB;

    @PostConstruct
    public void init() {
        folderPath = ComPath.basePath;
        try {
            initializedMethod();
            System.out.println("in it");
            EmployeeTypeList();
            getDistrictList().add(new SelectItem("0", "Select"));
            getDivisionalSecretarialList().add(new SelectItem("0", "Select"));
            getGnDivisionList().add(new SelectItem("0", "Select"));

            getDistrictListSecond().add(new SelectItem("0", "Select"));
            getDivisionalSecretarialListSecond().add(new SelectItem("0", "Select"));
            getGnDivisionListSecond().add(new SelectItem("0", "Select"));

            EmployeeTypeList();
            province();
            provinceSecond();

            date = new Date();
            gurantorNo = (GurantorCount) UniDB.find(1, GurantorCount.class);
//            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
//            storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializedMethod() {
        System.out.println("initialize method");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

        loanIdPara = params.get("l");
        System.out.println("loanIdPara " + loanIdPara);
        try {
            if (loanIdPara != null) {
                loanManager = getVerificationToken(loanIdPara);
                System.out.println("loan manager gotted");
                if (loanManager != null) {
                    loanId = loanManager.getReferenceId();
                    System.out.println("set the referrence id");
                    updateOfferManager(loanManager);
                    folderPath = "/root/temodocs/studentloansdocs/gurantordetails";
                } else {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/view/error.xhtml");
                }
            } else {
                externalContext.redirect(externalContext.getRequestContextPath() + "/view/error.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOfferManager(LoanManager loanManagerObj) {

        nic = loanManagerObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();

        Date date = new Date();
        System.out.println("updateOfferManager " + nic);
        List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + nic + "'");
        System.out.println("nic " + nic);
        if (!loanCustomer.isEmpty()) {
            System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
            List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
            System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
            if (!offerManager.isEmpty()) {
                List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='9'");
                if (crhList.isEmpty()) {
                    CustomerResponseHistory crh = new CustomerResponseHistory();
                    crh.setDate(date);
                    crh.setOfferManagerId(offerManager.get(0));
                    crh.setResponseStatusId((ResponseStatus) UniDB.find(9, ResponseStatus.class));
                    UniDB.create(crh);
                }
            }
        }
    }

    public void searchGuarantorAlreadyEntered(String y) {
        System.out.println("searchGuarantorAlreadyEntered ");
        System.out.println("y " + y);
        if (y.equals("1")) {
            List<GeneralUserProfile> gupExistFirstGuarantor = UniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic LIKE '%" + firstGuarantorNic + "%'");
            if (!gupExistFirstGuarantor.isEmpty()) {
                System.out.println("gupExistFirstGuarantor " + firstGuarantorNic + " " + !gupExistFirstGuarantor.isEmpty());
                List<Member1> existmemberFirstGuarantor = UniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gupExistFirstGuarantor.get(0).getId() + "' ");
                if (!existmemberFirstGuarantor.isEmpty()) {

                    System.out.println("existmemberFirstGuarantor " + firstGuarantorNic + " " + !existmemberFirstGuarantor.isEmpty());

                    LoanApplicantGurantor loanApplicantGurantor = loanManager.getLoanApplicantAndGurantorsId();

                    List<GurantorManager> guarantorManagers = UniDB.searchByQuery("SELECT g FROM GurantorManager g WHERE g.loanApplicantId.id='" + loanApplicantGurantor.getId() + "' ");
                    System.out.println("!guarantorManager.isEmpty() " + !guarantorManagers.isEmpty());
                    if (!guarantorManagers.isEmpty()) {

                        for (GurantorManager gurantorManager : guarantorManagers) {
                            if (gurantorManager.getGurantorCountId().equals("1")) {
                                if (gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().equals(gupExistFirstGuarantor.get(0))) {
                                    System.out.println("guarantorManager " + firstGuarantorNic + " " + !guarantorManagers.isEmpty());
                                    System.out.println("nic " + gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic());
                                    firstGuarantorFirstName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName();
                                    firstGuarantorlastName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
                                    firstGuarantorNic = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();
                                    firstGuarantorMobileNo = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getMobileNo();
                                    folderPath = folderPath + "/" + loanIdPara;
//                                folderPath = "F:\\temco-bank-application-2024-07-08\\images" + "\\" + loanIdPara;
                                    System.out.println("folder path " + folderPath);
                                    firstGsaved = true;
                                    x = false;
                                }
                            }
                        }
                    } else {
                        x = true;
                    }
                }
            }
        } else if (y.equals("2")) {
            System.out.println("A");
            List<GeneralUserProfile> gupGuarantor = UniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic LIKE '%" + secondGuarantorNic + "%'");
            if (!gupGuarantor.isEmpty()) {
                System.out.println("B");
                List<Member1> existmemberFirstGuarantor = UniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gupGuarantor.get(0).getId() + "' ");
                if (!existmemberFirstGuarantor.isEmpty()) {
                    System.out.println("C");
                    LoanApplicantGurantor loanApplicantGurantor = loanManager.getLoanApplicantAndGurantorsId();
                    List<GurantorManager> guarantorManagers = UniDB.searchByQuery("SELECT g FROM GurantorManager g WHERE g.loanApplicantId.id='" + loanApplicantGurantor.getId() + "' ");
                    if (!guarantorManagers.isEmpty()) {
                        System.out.println("D");
                        for (GurantorManager guarantorManager : guarantorManagers) {
                            if (guarantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().equals(gupGuarantor.get(0))) {
                                System.out.println("E");
                                System.out.println("nic " + guarantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic());
                                secondGuarantorFirstName = guarantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName();
                                secondGuarantorLastName = guarantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
                                secondGuarantorNic = guarantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();
                                secondGuarantorMobileNo = guarantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getMobileNo();
                                x = false;
//                                folderPath = folderPath + "/" + loanIdPara;
//                                folderPath = "F:\\temco-bank-application-2024-07-08\\images" + "\\" + loanIdPara;
                                secondGsaved = true;

                                System.out.println("folder path " + folderPath);
                                System.out.println("F");
                            }
                        }
                    } else {
                        x = true;
                    }
                } else {
                    x = true;
                }
            } else {
                x = true;
            }
        }

    }

    public void saveFirstGuarantorDetails() {
        try {
            System.out.println("saveFirstGuarantorDetails method");
            System.out.println("firstGuarantorFirstName " + firstGuarantorFirstName);
            System.out.println("firstGuarantorFirstName " + firstGuarantorlastName);
            System.out.println("firstGuarantorFirstName " + firstGuarantorNic);
            System.out.println("firstGuarantorFirstName " + firstGuarantorMobileNo);
//          
            FacesMessage msg = null;
//            sendEmail();
            System.out.println("outside the x");
            if (x) {
                x = false;
                System.out.println("in side x");
                try {
                    if (RegexVerifications.verifyNIC(firstGuarantorNic)) {
                        if (firstGuarantorFirstName != null && !firstGuarantorFirstName.isEmpty()) {
                            System.out.println("A");
                            if (firstGuarantorlastName != null && !firstGuarantorlastName.isEmpty()) {
                                System.out.println("B");
                                if (firstGuarantorNic != null && !firstGuarantorNic.isEmpty()) {
                                    System.out.println("C");
                                    if (firstGuarantorUploadFIleOne != null) {
                                        System.out.println("D");
                                        if (firstGuarantorUploadFIleTwo != null) {
                                            System.out.println("E");
                                            if (!firstGnameOftheEmployerOrBusiness.equals("") && !firstGcontactNumberOftheEmployerOrBusiness.equals("") && !firstGemployerAddressOneOrBusiness.equals("") && !firstGemployerAddressTwoOrBusiness.equals("") && !firstGemployerAddressThreeOrBusiness.equals("")) {
                                                System.out.println("save first guarantor called");

                                                List<GeneralUserProfile> gupExistFirstGuarantor = UniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic='" + firstGuarantorNic + "'");

                                                GeneralUserProfile gupFirstGuarantor = null;

                                                if (gupExistFirstGuarantor == null || gupExistFirstGuarantor.isEmpty()) {
                                                    System.out.println("B ");
                                                    gupFirstGuarantor = new GeneralUserProfile();
                                                    gupFirstGuarantor.setFirstName(firstGuarantorFirstName);
                                                    gupFirstGuarantor.setLastName(firstGuarantorlastName);
                                                    gupFirstGuarantor.setNic(firstGuarantorNic);
                                                    gupFirstGuarantor.setMobileNo(firstGuarantorMobileNo);
                                                    gupFirstGuarantor.setProfileCreatedDate(date);
                                                    gupFirstGuarantor.setVerificationToken(Filteration.getFilteredSHA256HashedPassword(firstGuarantorMobileNo));
                                                    gupFirstGuarantor.setIsActive(Short.valueOf("1"));
                                                    gupFirstGuarantor
                                                            .setProvinceId((Province) UniDB.find(Integer.parseInt(selectedProvinceId), Province.class
                                                            ));
                                                    gupFirstGuarantor
                                                            .setDistrictId((District) UniDB.find(Integer.parseInt(selectedDistrict), District.class
                                                            ));
                                                    gupFirstGuarantor
                                                            .setDivisionalSecretarialId((DivisionalSecretarial) UniDB.find(Integer.parseInt(selectedDivisionalSecretarial), DivisionalSecretarial.class
                                                            ));
                                                    gupFirstGuarantor
                                                            .setGnDivisionId((GnDivision) UniDB.find(Integer.parseInt(selectedGnDivision), GnDivision.class
                                                            ));
                                                    UniDB.create(gupFirstGuarantor);
                                                } else {
                                                    gupFirstGuarantor = gupExistFirstGuarantor.get(0);
                                                    System.out.println("C");
                                                }

                                                List<Member1> existmemberFirstGuarantor = UniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gupFirstGuarantor.getId() + "' ");

                                                Member1 memberFirstGuarantor;
                                                String memberOneId;
                                                if (existmemberFirstGuarantor == null || existmemberFirstGuarantor.isEmpty()) {
                                                    memberFirstGuarantor = new Member1();
                                                    memberOneId = generateMemberNo(selectedProvinceId, selectedDistrict, selectedDivisionalSecretarial, selectedGnDivision);
                                                    memberFirstGuarantor.setMembershipNo(memberOneId);
                                                    memberFirstGuarantor.setRegisteredDate(date);
                                                    memberFirstGuarantor.setGeneralUserProfileId(gupFirstGuarantor);
                                                    memberFirstGuarantor.setKpiValue(0.00);
                                                    memberFirstGuarantor.setAvailableFund(0.00);
                                                    UniDB.create(memberFirstGuarantor);

                                                    GopHasMember gopHasMember = new GopHasMember();
                                                    gopHasMember.setMemberId(memberFirstGuarantor);
                                                    gopHasMember.setStartDate(new Date());
                                                    gopHasMember
                                                            .setGeneralOrganizationProfileId((GeneralOrganizationProfile) UniDB.find(1, GeneralOrganizationProfile.class
                                                            ));
                                                    UniDB.create(gopHasMember);
                                                    System.out.println("D ");

                                                } else {
                                                    memberFirstGuarantor = existmemberFirstGuarantor.get(0);
                                                    memberOneId = existmemberFirstGuarantor.get(0).getMembershipNo();
                                                    System.out.println("E ");
                                                }

                                                List<GeneralOrganizationProfile> generalOrganizationProfileListFirst;
                                                if (organizationIdFirst != null) {
                                                    generalOrganizationProfileListFirst = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.id='" + organizationIdFirst + "'");
                                                } else {
                                                    generalOrganizationProfileListFirst = null;
                                                }

                                                GeneralOrganizationProfile orgProfile = null;
                                                if (generalOrganizationProfileListFirst == null || generalOrganizationProfileListFirst.isEmpty()) {
                                                    orgProfile = new GeneralOrganizationProfile();
                                                    orgProfile.setName(firstGnameOftheEmployerOrBusiness);
                                                    orgProfile.setPhoneNo(firstGcontactNumberOftheEmployerOrBusiness);
                                                    orgProfile.setAddress1(firstGemployerAddressOneOrBusiness);
                                                    orgProfile.setAddress2(firstGemployerAddressTwoOrBusiness);
                                                    orgProfile.setAddress3(firstGemployerAddressThreeOrBusiness);
                                                    orgProfile
                                                            .setOrganizationTypeId((OrganizationType) UniDB.find(4, OrganizationType.class
                                                            ));
                                                    UniDB.create(orgProfile);

                                                } else {
                                                    orgProfile = generalOrganizationProfileListFirst.get(0);
                                                }

                                                List<MemberOrganizationsHistory> mohLists = UniDB.searchByQuery("SELECT g FROM MemberOrganizationsHistory g WHERE g.generalOrganizationProfileId.id='" + orgProfile.getId() + "' AND g.memberId.id='" + memberFirstGuarantor.getId() + "'");

                                                if (mohLists.isEmpty()) {
                                                    MemberOrganizationsHistory moh = new MemberOrganizationsHistory();
                                                    moh.setDate(new Date());
                                                    moh.setGeneralOrganizationProfileId(orgProfile);
                                                    moh.setIsActive(Short.valueOf("1"));
                                                    moh.setMemberId(memberFirstGuarantor);
                                                    UniDB.create(moh);
                                                } else {
                                                    mohLists.get(0).setDate(new Date());
                                                    mohLists.get(0).setIsActive(Short.valueOf("1"));
                                                }

                                                folderPath = createStudentLoanFolder(loanIdPara);

                                                LoanApplicantGurantor firstGuarantor = new LoanApplicantGurantor();
                                                firstGuarantor.setMemberId(memberFirstGuarantor);
                                                firstGuarantor.setDate(date);
                                                UniDB.create(firstGuarantor);

                                                GurantorManager firstGurantorManager = new GurantorManager();
                                                firstGurantorManager.setLoanApplicantId(loanManager.getLoanApplicantAndGurantorsId());
                                                firstGurantorManager.setLoanApplicantAndGurantorsId(firstGuarantor);
                                                firstGurantorManager.setGurantorCountId(gurantorNo);
                                                String firstGuarantorFolderPath = createGurantorFolders(folderPath, memberOneId);
                                                firstGurantorManager.setGurantorCountId(gurantorNo);
                                                UniDB.create(firstGurantorManager);
                                                System.out.println("G ");

                                                MemberDocuments memberDocuments = (MemberDocuments) UniDB.find(1, MemberDocuments.class);
                                                UserType userType = (UserType) UniDB.find(1, UserType.class);

                                                UniversalUserDocument uud = new UniversalUserDocument();
                                                uud.setGeneralUserProfileId(gupFirstGuarantor);
                                                uud.setCreatedTimestamp(date);
                                                uud.setIsVerified(Short.valueOf("0"));
                                                uud.setFileUrl(imageUpload(firstGuarantorUploadFIleOne, firstGuarantorFolderPath));
                                                uud.setDocumentTypeId(memberDocuments);
                                                uud.setUserTypeId(userType);
                                                uud.setIsActive(Short.parseShort("1"));
                                                UniDB.create(uud);

                                                System.out.println("H");

                                                UniversalUserDocument uud2 = new UniversalUserDocument();
                                                uud2.setGeneralUserProfileId(gupFirstGuarantor);
                                                uud2.setCreatedTimestamp(date);
                                                uud2.setIsVerified(Short.valueOf("0"));
                                                uud2.setFileUrl(imageUpload(firstGuarantorUploadFIleTwo, firstGuarantorFolderPath));
                                                uud2.setDocumentTypeId(memberDocuments);
                                                uud2.setIsActive(Short.parseShort("1"));
                                                uud2.setUserTypeId(userType);
                                                UniDB.create(uud2);

                                                System.out.println("I ");

                                                firstGsaved = true;

                                                Date dateTwo = new Date();
                                                System.out.println("updateOfferManager");
                                                List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + nic + "'");
                                                System.out.println("nic " + nic);
                                                if (!loanCustomer.isEmpty()) {
                                                    System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
                                                    List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
                                                    System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
                                                    if (!offerManager.isEmpty()) {
                                                        List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='10'");
                                                        if (crhList.isEmpty()) {
                                                            CustomerResponseHistory crh = new CustomerResponseHistory();
                                                            crh.setDate(dateTwo);
                                                            crh.setOfferManagerId(offerManager.get(0));
                                                            crh.setResponseStatusId((ResponseStatus) UniDB.find(10, ResponseStatus.class));
                                                            UniDB.create(crh);
                                                        }
                                                    }
                                                }

                                                FacesContext facesContext = FacesContext.getCurrentInstance();
                                                ExternalContext externalContext = facesContext.getExternalContext();
                                                externalContext.redirect(externalContext.getRequestContextPath() + "/tasks/second-guarantor-details.xhtml?l=" + loanIdPara);

                                            } else {
                                                firstGsaved = false;
                                                x = true;
                                                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please input Guarantor's employeement related field correctly");
                                                FacesContext.getCurrentInstance().addMessage("", msg);
                                            }
                                        } else {
                                            firstGsaved = false;
                                            x = true;
                                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please upload the back image of first guarantor's nic or driving licence");
                                            FacesContext.getCurrentInstance().addMessage("", msg);
                                        }
                                    } else {
                                        firstGsaved = false;
                                        x = true;
                                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please upload the front image of first guarantor's nic or driving licence");
                                        FacesContext.getCurrentInstance().addMessage("", msg);
                                    }
                                } else {
                                    firstGsaved = false;
                                    x = true;
                                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Enter the First Guarantor NICe ");
                                    FacesContext.getCurrentInstance().addMessage("", msg);
                                }
                            } else {
                                firstGsaved = false;
                                x = true;
                                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Enter the First Guarantor Last Name ");
                                FacesContext.getCurrentInstance().addMessage("", msg);
                            }
                        } else {
                            firstGsaved = false;
                            x = true;
                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Enter the First Guarantor First Name ");
                            FacesContext.getCurrentInstance().addMessage("", msg);
                        }
                    } else {
                        firstGsaved = false;
                        x = true;
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid NIC No", "Please check the given nic no is correct");
                        FacesContext.getCurrentInstance().addMessage("", msg);
                    }
                } catch (Exception e) {
                    firstGsaved = false;
                    e.printStackTrace();
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Guarantor already registered as your loan guarantor");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
                externalContext.redirect(externalContext.getRequestContextPath() + "/view/error.xhtml");
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
    }

//    public void saveSecondGuarantorDetails() {
//        System.out.println("saveSecondGuarantorDetails ");
//
//        System.out.println("secondGuarantorFirstName " + secondGuarantorFirstName);
//        System.out.println("secondGuarantorLastName " + secondGuarantorLastName);
//        System.out.println("firstGuarantorFirstName " + firstGuarantorNic);
//        FacesMessage msg = null;
//        System.out.println("saveSecondGuarantorDetails out");
//        if (x) {
//            System.out.println("saveSecondGuarantorDetails in");
//            System.out.println("secondGuarantorFirstName " + secondGuarantorFirstName);
//            try {
//                if (secondGuarantorFirstName != null && !secondGuarantorFirstName.isEmpty()) {
//                    if (secondGuarantorLastName != null && !secondGuarantorLastName.isEmpty()) {
//                        if (secondGuarantorGrossIncome != null && secondGuarantorGrossIncome != 0.00) {
//                            if (secondGuarantorUploadFIleOne != null) {
//                                if (secondGuarantorUploadFIleTwo != null) {
//
//                                    System.out.println("all got it");
//                                    Double monthlinstallement = loanManager.getMonthlyInstallement();
//
//                                    double guarantorInstallemt = (secondGuarantorGrossIncome / 100) * 60;
//
//                                    System.out.println("guarantorInstallemt " + guarantorInstallemt);
//
//                                    if (monthlinstallement <= guarantorInstallemt) {
//                                        System.out.println("A ");
//
//                                        List<GeneralUserProfile> gupExistSecondGuarantor = UniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic='" + secondGuarantorNic + "'");
//
//                                        GeneralUserProfile gupSecondGuarantor;
//
//                                        if (gupExistSecondGuarantor == null || gupExistSecondGuarantor.isEmpty()) {
//                                            gupSecondGuarantor = new GeneralUserProfile();
//                                            gupSecondGuarantor.setFirstName(secondGuarantorFirstName);
//                                            gupSecondGuarantor.setLastName(secondGuarantorLastName);
//                                            gupSecondGuarantor.setNic(secondGuarantorNic);
//                                            gupSecondGuarantor.setMobileNo(secondGuarantorMobileNo);
//                                            gupSecondGuarantor.setProfileCreatedDate(date);
//                                            gupSecondGuarantor.setVerificationToken(Security.encrypt(secondGuarantorMobileNo));
//                                            gupSecondGuarantor.setProvinceId((Province) UniDB.find(Integer.parseInt(selectedProvinceIdSecond), Province.class));
//                                            gupSecondGuarantor.setDistrictId((District) UniDB.find(Integer.parseInt(selectedDistrictSecond), District.class));
//                                            gupSecondGuarantor.setDivisionalSecretarialId((DivisionalSecretarial) UniDB.find(Integer.parseInt(selectedDivisionalSecretarialSecond), DivisionalSecretarial.class));
//                                            gupSecondGuarantor.setGnDivisionId((GnDivision) UniDB.find(Integer.parseInt(selectedGnDivisionSecond), GnDivision.class));
//                                            UniDB.create(gupSecondGuarantor);
//                                            System.out.println("J ");
//                                        } else {
//                                            gupSecondGuarantor = gupExistSecondGuarantor.get(0);
//                                        }
//
//                                        List<Member1> existmemberSecondGuarantor = UniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gupSecondGuarantor.getId() + "' ");
//
//                                        Member1 memberSecondGuarantor = null;
//                                        String memberTwoId;
//                                        if (existmemberSecondGuarantor == null || existmemberSecondGuarantor.isEmpty()) {
//                                            memberSecondGuarantor = new Member1();
//                                            memberTwoId = generateMemberNo(selectedProvinceIdSecond, selectedDistrictSecond, selectedDivisionalSecretarialSecond, selectedGnDivisionSecond);
//                                            memberSecondGuarantor.setMembershipNo(memberTwoId);
//                                            memberSecondGuarantor.setRegisteredDate(date);
//                                            memberSecondGuarantor.setGeneralUserProfileId(gupSecondGuarantor);
//                                            memberSecondGuarantor.setKpiValue(0.00);
//                                            memberSecondGuarantor.setAvailableFund(0.00);
//                                            UniDB.create(memberSecondGuarantor);
//                                            GopHasMember gopHasMember = new GopHasMember();
//                                            gopHasMember.setMemberId(memberSecondGuarantor);
//                                            gopHasMember.setStartDate(new Date());
//                                            gopHasMember
//                                                    .setGeneralOrganizationProfileId((GeneralOrganizationProfile) UniDB.find(1, GeneralOrganizationProfile.class
//                                                    ));
//                                            UniDB.create(gopHasMember);
//                                            System.out.println("K ");
//
//                                        } else {
//                                            memberSecondGuarantor = existmemberSecondGuarantor.get(0);
//                                            memberTwoId = existmemberSecondGuarantor.get(0).getMembershipNo();
//                                            System.out.println("L ");
//                                        }
//
//                                        List<GeneralOrganizationProfile> generalOrganizationProfileListSecond;
//                                        if (organizationIdFirst != null) {
//                                            generalOrganizationProfileListSecond = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.id='" + organizationIdSecond + "'");
//                                        } else {
//                                            generalOrganizationProfileListSecond = null;
//                                        }
//
//                                        GeneralOrganizationProfile orgProfileSecond = null;
//                                        if (generalOrganizationProfileListSecond == null || generalOrganizationProfileListSecond.isEmpty()) {
//                                            orgProfileSecond = new GeneralOrganizationProfile();
//                                            orgProfileSecond.setName(secondGnameOftheEmployerOrBusiness);
//                                            orgProfileSecond.setPhoneNo(secondGcontactNumberOftheEmployerOrBusiness);
//                                            orgProfileSecond.setAddress1(secondGemployerAddressOneOrBusiness);
//                                            orgProfileSecond.setAddress2(secondGemployerAddressTwoOrBusiness);
//                                            orgProfileSecond.setAddress3(secondGemployerAddressThreeOrBusiness);
//                                            orgProfileSecond
//                                                    .setOrganizationTypeId((OrganizationType) UniDB.find(4, OrganizationType.class
//                                                    ));
//                                            UniDB.create(orgProfileSecond);
//
//                                        } else {
//                                            orgProfileSecond = generalOrganizationProfileListSecond.get(0);
//                                        }
//
//                                        List<MemberOrganizationsHistory> mohListsSecond = UniDB.searchByQuery("SELECT g FROM MemberOrganizationsHistory g WHERE g.generalOrganizationProfileId.id='" + orgProfileSecond.getId() + "' AND g.memberId.id='" + memberSecondGuarantor.getId() + "'");
//
//                                        if (mohListsSecond.isEmpty()) {
//                                            MemberOrganizationsHistory moh = new MemberOrganizationsHistory();
//                                            moh.setDate(new Date());
//                                            moh.setGeneralOrganizationProfileId(orgProfileSecond);
//                                            moh.setIsActive(Short.valueOf("1"));
//                                            moh.setMemberId(memberSecondGuarantor);
//                                            UniDB.create(moh);
//                                        } else {
//                                            mohListsSecond.get(0).setDate(new Date());
//                                            mohListsSecond.get(0).setIsActive(Short.valueOf("1"));
//                                        }
//
//                                        LoanApplicantGurantor secondGuarantor = new LoanApplicantGurantor();
//                                        secondGuarantor.setMemberId(memberSecondGuarantor);
//                                        secondGuarantor.setDate(date);
//                                        UniDB.create(secondGuarantor);
//
//                                        GurantorManager secondGurantorManager = new GurantorManager();
//                                        secondGurantorManager.setLoanApplicantId(loanManager.getLoanApplicantAndGurantorsId());
//                                        secondGurantorManager.setLoanApplicantAndGurantorsId(secondGuarantor);
//
//                                        System.out.println("Member two id " + memberTwoId);
//
//                                        String secondGuarantorFolderPath = createGurantorFolders(folderPath, memberTwoId);
//
//                                        System.out.println("String secondGuarantorFolderPath - " + secondGuarantorFolderPath);
//                                        UniDB.create(secondGurantorManager);
//
//                                        GuarantorDocuments gd3 = new GuarantorDocuments();
//                                        gd3.setGurantorManagerId(secondGurantorManager);
//                                        gd3.setUrl(imageUpload(secondGuarantorUploadFIleOne, secondGuarantorFolderPath));
//                                        gd3.setDocumentTypeId((DocumentType) UniDB.find(1, DocumentType.class));
//                                        UniDB.create(gd3);
//                                        System.out.println("M ");
//                                        GuarantorDocuments gd4 = new GuarantorDocuments();
//                                        gd4.setGurantorManagerId(secondGurantorManager);
//                                        gd4.setUrl(imageUpload(secondGuarantorUploadFIleTwo, secondGuarantorFolderPath));
//                                        gd4.setDocumentTypeId((DocumentType) UniDB.find(1, DocumentType.class));
//                                        UniDB.create(gd4);
//
//                                        secondGsaved = true;
//                                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success ! ! !", "Second Guarantor Details Saved Successful");
//                                        FacesContext.getCurrentInstance().addMessage("", msg);
//
//                                    } else {
//                                        secondGsaved = false;
//                                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ensure the loan monthly installment is 60% or below the guarantor's gross income.");
//                                        FacesContext.getCurrentInstance().addMessage("", msg);
//                                    }
//                                } else {
//                                    secondGsaved = false;
//                                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please upload the front image of second guarantor's nic or driving licence");
//                                    FacesContext.getCurrentInstance().addMessage("", msg);
//                                }
//                            } else {
//                                secondGsaved = false;
//                                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please upload the front image of second guarantor's nic or driving licence");
//                                FacesContext.getCurrentInstance().addMessage("", msg);
//                            }
//                        } else {
//                            secondGsaved = false;
//                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please enter the second guarantor Gross income");
//                            FacesContext.getCurrentInstance().addMessage("", msg);
//                        }
//                    } else {
//                        secondGsaved = false;
//                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please enter the second guarantor last name");
//                        FacesContext.getCurrentInstance().addMessage("", msg);
//                    }
//                } else {
//                    secondGsaved = false;
//                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please enter the second guarantor first name");
//                    FacesContext.getCurrentInstance().addMessage("", msg);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Guarantor already registered as your loan guarantor");
//            FacesContext.getCurrentInstance().addMessage("", msg);
//        }
//    }
    public List<String> firstGuarantorLoadEmployers(String query) {

        System.out.println("loadEmployers " + query);

        List<String> loadEmployers = new ArrayList<>();

        List<GeneralOrganizationProfile> generalUserProfilesList = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.name LIKE '%" + query + "%' AND g.organizationTypeId.id='4' order by  g.id ASC");
        System.out.println("generalUserProfilesList " + generalUserProfilesList.isEmpty());
        System.out.println("generalUserProfilesList " + generalUserProfilesList.size());
        if (!generalUserProfilesList.isEmpty()) {
            for (GeneralOrganizationProfile generalOrganizationProfile : generalUserProfilesList) {
                loadEmployers.add(generalOrganizationProfile.getName());
            }
        }
        return loadEmployers;
    }

    public void firstGEmployementType() {
        System.out.println("userEmployementType");
        System.out.println("selectedEmployementType " + firstGuarantorSelectedEmployementType);

        firstGnameOftheEmployerOrBusiness = "";
        firstGcontactNumberOftheEmployerOrBusiness = "";
        firstGemployerAddressOneOrBusiness = "";
        firstGemployerAddressTwoOrBusiness = "";
        firstGemployerAddressThreeOrBusiness = "";

        switch (Integer.parseInt(firstGuarantorSelectedEmployementType)) {
            case 1:
                firstGuarantorselfEmlpoyed = true;
                firstGuarantoremlpoyed = false;
                break;
            case 2:
                firstGuarantorselfEmlpoyed = false;
                firstGuarantoremlpoyed = true;
                break;
            default:
                firstGuarantorselfEmlpoyed = false;
                firstGuarantoremlpoyed = false;
                break;
        }
    }

    public void secondGEmployementType() {
        System.out.println("userEmployementType");
        System.out.println("selectedEmployementType " + secondGuarantorSelectedEmployementType);

        secondGnameOftheEmployerOrBusiness = "";
        secondGcontactNumberOftheEmployerOrBusiness = "";
        secondGemployerAddressOneOrBusiness = "";
        secondGemployerAddressTwoOrBusiness = "";
        secondGemployerAddressThreeOrBusiness = "";

        switch (Integer.parseInt(secondGuarantorSelectedEmployementType)) {
            case 1:
                secondGuarantorselfEmlpoyed = true;
                secondGuarantoremlpoyed = false;
                break;
            case 2:
                secondGuarantorselfEmlpoyed = false;
                secondGuarantoremlpoyed = true;
                break;
            default:
                secondGuarantorselfEmlpoyed = false;
                secondGuarantoremlpoyed = false;
                break;
        }
    }

    public void setSecondGuarantorSelectedEmployer() {
        System.out.println("nameOftheEmployer 1" + secondGnameOftheEmployerOrBusiness);

        int min = 0;
        int max = secondGnameOftheEmployerOrBusiness.indexOf(")");
        String id = secondGnameOftheEmployerOrBusiness.substring(min, max);
        String companyName = secondGnameOftheEmployerOrBusiness.substring(max + 1);
        System.out.println("id " + id);
        System.out.println("companyName " + companyName);
        secondGnameOftheEmployerOrBusiness = companyName;
        organizationIdSecond = id;
        System.out.println("id " + id);

        List<GeneralOrganizationProfile> generalUserProfilesList = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.id='" + id + "' ");
        System.out.println("generalUserProfilesList " + generalUserProfilesList.isEmpty());
        if (!generalUserProfilesList.isEmpty()) {
            for (GeneralOrganizationProfile generalOrganizationProfile : generalUserProfilesList) {
                secondGcontactNumberOftheEmployerOrBusiness = generalOrganizationProfile.getPhoneNo();
                secondGemployerAddressOneOrBusiness = generalOrganizationProfile.getAddress1();
                secondGemployerAddressTwoOrBusiness = generalOrganizationProfile.getAddress2();
                secondGemployerAddressThreeOrBusiness = generalOrganizationProfile.getAddress3();
                System.out.println("contactNumberOftheEmployer  " + secondGcontactNumberOftheEmployerOrBusiness);
                System.out.println("employerAddressOne  " + secondGemployerAddressOneOrBusiness);
                System.out.println("employerAddressTwo  " + secondGemployerAddressTwoOrBusiness);
                System.out.println("employerAddressThree  " + secondGemployerAddressThreeOrBusiness);
            }
        }
    }

    public void setFirstGuarantorSelectedEmployer() {
        System.out.println("nameOftheEmployer 1" + firstGnameOftheEmployerOrBusiness);

//        int min = 0;
//        int max = firstGnameOftheEmployerOrBusiness.indexOf(")");
//        String id = firstGnameOftheEmployerOrBusiness.substring(min, max);
//        String companyName = firstGnameOftheEmployerOrBusiness.substring(max + 1);
//        System.out.println("id " + id);
//        System.out.println("companyName " + companyName);
//        firstGnameOftheEmployerOrBusiness = companyName;
//        organizationIdFirst = id;
//        
//        System.out.println("id " + id);
        List<GeneralOrganizationProfile> generalUserProfilesList = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.name LIKE '%" + firstGnameOftheEmployerOrBusiness + "%' AND g.organizationTypeId.id='4' order by  g.id ASC");
        System.out.println("generalUserProfilesList " + generalUserProfilesList.isEmpty());
        if (!generalUserProfilesList.isEmpty()) {
            for (GeneralOrganizationProfile generalOrganizationProfile : generalUserProfilesList) {
                organizationIdFirst = String.valueOf(generalOrganizationProfile.getId());
                firstGcontactNumberOftheEmployerOrBusiness = generalOrganizationProfile.getPhoneNo();
                firstGemployerAddressOneOrBusiness = generalOrganizationProfile.getAddress1();
                firstGemployerAddressTwoOrBusiness = generalOrganizationProfile.getAddress2();
                firstGemployerAddressThreeOrBusiness = generalOrganizationProfile.getAddress3();
                System.out.println("contactNumberOftheEmployer  " + firstGcontactNumberOftheEmployerOrBusiness);
                System.out.println("employerAddressOne  " + firstGemployerAddressOneOrBusiness);
                System.out.println("employerAddressTwo  " + firstGemployerAddressTwoOrBusiness);
                System.out.println("employerAddressThree  " + firstGemployerAddressThreeOrBusiness);
            }
        }
    }

    public List<String> secondGuarantorLoadEmployers(String query) {

        System.out.println("loadEmployers " + query);

        List<String> loadEmployers = new ArrayList<>();

        List<GeneralOrganizationProfile> generalUserProfilesList = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.name LIKE '%" + query + "%' AND g.organizationTypeId.id='4' order by  g.id ASC");
        System.out.println("generalUserProfilesList " + generalUserProfilesList.isEmpty());
        System.out.println("generalUserProfilesList " + generalUserProfilesList.size());
        if (!generalUserProfilesList.isEmpty()) {
            for (GeneralOrganizationProfile generalOrganizationProfile : generalUserProfilesList) {
                loadEmployers.add(generalOrganizationProfile.getName());
            }
        }
        return loadEmployers;
    }

    public void firstUserEmployementType() {
        System.out.println("userEmployementType");
        System.out.println("firstGuarantorDelectedEmployementType " + firstGuarantorSelectedEmployementType);
        switch (firstGuarantorSelectedEmployementType) {
            case "1":
                firstGuarantoremlpoyed = true;
                firstGuarantorselfEmlpoyed = false;
                break;
            case "2":
                firstGuarantoremlpoyed = false;
                firstGuarantorselfEmlpoyed = true;
                break;
            default:
                secondGuarantoremlpoyed = false;
                firstGuarantorselfEmlpoyed = false;
                break;
        }
    }

    public void secondUserEmployementType() {
        System.out.println("userEmployementType");
        System.out.println("firstGuarantorDelectedEmployementType " + secondGuarantorSelectedEmployementType);
        switch (secondGuarantorSelectedEmployementType) {
            case "1":
                secondGuarantoremlpoyed = true;
                secondGuarantorselfEmlpoyed = false;
                break;
            case "2":
                secondGuarantoremlpoyed = false;
                secondGuarantorselfEmlpoyed = true;
                break;
            default:
                secondGuarantoremlpoyed = false;
                secondGuarantorselfEmlpoyed = false;
                break;
        }
    }

    private void EmployeeTypeList() {

        firstGuarantoremployementTypeList = new ArrayList<>();
        secondGuarantoremployementTypeList = new ArrayList<>();

        firstGuarantoremployementTypeList.add(new SelectItem("0", "Select"));
        secondGuarantoremployementTypeList.add(new SelectItem("0", "Select"));
        List<EmployementType> employementTypes = UniDB.searchByQuery("SELECT g FROM EmployementType g");
        for (EmployementType employementType : employementTypes) {
            if (employementType.getId() != 3) {
                firstGuarantoremployementTypeList.add(new SelectItem(employementType.getId(), employementType.getType()));
                secondGuarantoremployementTypeList.add(new SelectItem(employementType.getId(), employementType.getType()));
            }
        }

    }

    public LoanManager getVerificationToken(String token) {
        List<LoanManager> l = UniDB.searchByQuery("SELECT  g FROM LoanManager g WHERE g.verificationToke='" + token + "' ");
        if (l.isEmpty()) {

            return null;
        } else {
            System.out.println("returned object");
            return l.get(0);
        }
    }

    public void saveGuarantorDetails() {
//        sendEmail();
        if (methodCalled) {
            methodCalled = false;
//          
            FacesMessage msg;

            if (firstGsaved) {
                if (secondGsaved) {

//            sendEmail();
                    try {
//                sendEmail();
                        FacesContext facesContext = FacesContext.getCurrentInstance();
                        ExternalContext externalContext = facesContext.getExternalContext();
                        //                externalContext.redirect(externalContext.getRequestContextPath() + "/view/guarantor-details-submission-successful.xhtml?en=" + loanIdPara);

                        externalContext.redirect(externalContext.getRequestContextPath() + "/tasks/loan-details.xhtml?l=" + loanIdPara);
//                        externalContext.redirect(externalContext.getRequestContextPath() + "/view/success.xhtml");
                        facesContext.responseComplete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Second Guarantor data not saved correctly.Try Again to upload data");
                    FacesContext.getCurrentInstance().addMessage("", msg);
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "First Guarantor data not saved correctly.Try Again to upload data");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }
        }
    }

    public void sendEmail() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String url = "https://lending.temcobank.com/view/loan-submitting-instruction.xhtml?en=" + loanIdPara;
            System.out.println("url " + url);
            String content = "<!DOCTYPE html>\n"
                    + "                <html lang=\"en\">\n"
                    + "                <head>\n"
                    + "                    <meta charset=\"UTF-8\">\n"
                    + "                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "                    <style>\n"
                    + "                        body {\n"
                    + "                            font-family: Arial, sans-serif;\n"
                    + "                            background-color: #f4f4f4;\n"
                    + "                            margin: 0;\n"
                    + "                            padding: 0;\n"
                    + "                        }\n"
                    + "                        .container {\n"
                    + "                            width: 100%;\n"
                    + "                            max-width: 600px;\n"
                    + "                            margin: 0 auto;\n"
                    + "                            background-color: #ffffff;\n"
                    + "                            padding: 20px;\n"
                    + "                            border-radius: 8px;\n"
                    + "                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                    + "                        }\n"
                    + "                        .header {\n"
                    + "                            text-align: center;\n"
                    + "                            padding: 20px;\n"
                    + "                            background-color: #4CAF50;\n"
                    + "                            color: #ffffff;\n"
                    + "                            border-top-left-radius: 8px;\n"
                    + "                            border-top-right-radius: 8px;\n"
                    + "                        }\n"
                    + "                        .header h1 {\n"
                    + "                            margin: 0;\n"
                    + "                            font-size: 24px;\n"
                    + "                        }\n"
                    + "                        .content {\n"
                    + "                            padding: 20px;\n"
                    + "                            color: #333333;\n"
                    + "                        }\n"
                    + "                        .content p {\n"
                    + "                            line-height: 1.6;\n"
                    + "                        }\n"
                    + "                        .button-container {\n"
                    + "                            text-align: center;\n"
                    + "                            margin-top: 20px;\n"
                    + "                        }\n"
                    + "                        .button {\n"
                    + "                            background-color: #4CAF50;\n"
                    + "                            color: #ffffff;\n"
                    + "                            padding: 10px 20px;\n"
                    + "                            text-decoration: none;\n"
                    + "                            border-radius: 5px;\n"
                    + "                            font-size: 16px;\n"
                    + "                        }\n"
                    + "                        .footer {\n"
                    + "                            text-align: center;\n"
                    + "                            padding: 20px;\n"
                    + "                            background-color: #f4f4f4;\n"
                    + "                            color: #777777;\n"
                    + "                            border-bottom-left-radius: 8px;\n"
                    + "                            border-bottom-right-radius: 8px;\n"
                    + "                        }\n"
                    + "                    </style>\n"
                    + "                </head>\n"
                    + "                <body>\n"
                    + "                    <div class=\"container\">\n"
                    + "                        <div class=\"header\">\n"
                    + "                            <h1>Loan Request Submitted Successfully!</h1>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"content\">\n"
                    + "                            <p>Dear ";
//        content += loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getEmail();
            content += loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getFirstName();
            content += ",</p>\n"
                    + "                            <p>Thank you for submitting your loan request. We are pleased to inform you that your request has been successfully submitted and is currently under review.</p>\n"
                    + "                            <p>To proceed with your loan request, please click the button below to send the pay order.</p>\n"
                    + "                            <div class=\"button-container\">\n"
                    + "                                <a href=\"";
            content += url;
            content += "\" class=\"button\">Send Pay Order</a>\n"
                    + "                            </div>\n"
                    + "                            <p>If you have any questions or need further assistance, please do not hesitate to contact our support team.</p>\n"
                    + "                            <p>Thank you for choosing our services!</p>\n"
                    + "                            <p>Best regards,<br>Temco Bank</p>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"footer\">\n"
                    + "                            <p>&copy; 2024 Temco Bank. All rights reserved.</p>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </body>\n"
                    + "                </html>";

//            new SendgridEmailManager().sendTemplatedEmail("Loan Request Submitted Successful - Submit the Pay Order", "tryabeywardane@gmail.com", content);
            new NewMailSender().sendM(loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getEmail(), "Loan Request Submitted Successful - Submit the Pay Order", content);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String createGurantorFolders(String mainFolderPath, String memberId) {
        System.out.println("createGurantorFolders");

        // Create the main directory
        String basePath = mainFolderPath;

        // Create 'user1' and 'user2' folders inside the main directory
        String uniqueFolderName = memberId;

        // Construct the full folder path
        Path folderPath = Paths.get(basePath + "/" + uniqueFolderName);
//        Path folderPath = Paths.get(basePath + "\\" + uniqueFolderName);
        System.out.println("Folder path: " + folderPath);

        try {
            // Check if the folder already exists
            if (Files.exists(folderPath)) {
                System.out.println("Folder already exists at: " + folderPath);
                return folderPath.toString();
            } else {
                // Create the directories if they don't already exist
                Files.createDirectories(folderPath);
                System.out.println("Folder created successfully at: " + folderPath);
                return folderPath.toString();
            }

            // Return the folder path as a String
        } catch (Exception e) {
            // Handle exception if folder creation fails
            e.printStackTrace();
            return null;
        }
    }

    public String createStudentLoanFolder(String loanId) {
        System.out.println("createStudentLoanFolder");

        String basePath = ComPath.basePath;
        // Define the base path where the folder should be created
//        String basePath = "F:\\temco-bank-application-2024-07-08\\images";

        // Generate a unique folder name using the loanId
        String uniqueFolderName = loanId;

        System.out.println("Base Path: " + basePath);

        // Combine the base path and unique folder name to create the full path
//        Path folderPath = Paths.get(basePath + "\\" + uniqueFolderName);
        Path folderPath = Paths.get(basePath + "/" + uniqueFolderName);

        try {
            // Check if the folder already exists
            if (Files.exists(folderPath)) {
                System.out.println("Folder already exists at: " + folderPath);
                return folderPath.toString();
            } else {
                // Create the directories if they don't already exist
                Files.createDirectories(folderPath);
                System.out.println("Folder created successfully at: " + folderPath);
                return folderPath.toString();
            }

            // Return the folder path as a String
        } catch (Exception e) {
            // Handle exception if folder creation fails
            e.printStackTrace();
            return null;
        }
    }

    public String imageUpload(UploadedFile file, String path) {
        System.out.println("imageUpload");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");

        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileExtension;
        if (file != null && file.getFileName() != null) {
            String originalFileName = file.getFileName();
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        } else {
            System.out.println("File or file name is null.");
            return null;
        }
        // Proceed with your logic
        String newFileName = "temcoln" + UUID.randomUUID().toString() + sdf.format(d) + fileExtension;
        File outputFile = new File(directory + "/" + newFileName);
//        File outputFile = new File(directory + "\\" + newFileName);
        try (InputStream input = file.getInputStream(); OutputStream output = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            return newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    public void provinceSecond() {
        getProvinceListSecond().clear();
        List<Province> province = UniDB.searchByQuery("SELECT g FROM Province g ORDER BY g.name ASC");
        getProvinceListSecond().add(new SelectItem("0", "Select"));
        if (province.size() > 0) {
            for (Province provinceObj : province) {
                getProvinceListSecond().add(new SelectItem(provinceObj.getId(), provinceObj.getName()));
            }
        }
    }

    public void districtSecond() {
        getDistrictListSecond().clear();
        System.out.println("province id 2" + selectedProvinceIdSecond);
        List<District> districts = UniDB.searchByQuery("SELECT g FROM District g WHERE g.provinceId.id='" + selectedProvinceIdSecond + "' ORDER BY g.name ASC ");
        getDistrictListSecond().add(new SelectItem("0", "Select"));
        System.out.println("districts.size() 2" + districts.size());
        if (!districts.isEmpty()) {
            for (District districtObj : districts) {
                System.out.println("districtObj.getId() " + districtObj.getId() + " " + "districtObj.getName" + districtObj.getName());
                getDistrictListSecond().add(new SelectItem(districtObj.getId(), districtObj.getName()));
            }
        }
    }

    public void divisionalSecretarialSecond() {
        getDivisionalSecretarialListSecond().clear();
        System.out.println("district id 2" + selectedDistrictSecond);
        getDivisionalSecretarialListSecond().add(new SelectItem("0", "Select"));
        List<DivisionalSecretarial> divisionalSecretarials = UniDB.searchByQuery("SELECT g FROM DivisionalSecretarial g WHERE g.districtId.id='" + selectedDistrictSecond + "' ORDER BY g.name ASC");

        System.out.println("districts.size() 2" + divisionalSecretarials.size());
        for (DivisionalSecretarial divisionalSecretarialObj : divisionalSecretarials) {
            getDivisionalSecretarialListSecond().add(new SelectItem(divisionalSecretarialObj.getId(), divisionalSecretarialObj.getName()));
        }

    }

    public void gnDivisionSecond() {
        getGnDivisionListSecond().clear();
        getGnDivisionListSecond().add(new SelectItem("0", "Select"));
        String get_gib = "SELECT g FROM GnDivision g WHERE g.divisionalSecretarialId.id='" + selectedDivisionalSecretarialSecond + "' ORDER BY g.name ASC";
        List<GnDivision> gnDivisions = UniDB.searchByQuery(get_gib);

        for (GnDivision gnDivisionObj : gnDivisions) {
            getGnDivisionListSecond().add(new SelectItem(gnDivisionObj.getId().toString(), gnDivisionObj.getName()));
        }
    }

    public String generateMemberNo(String provincId, String districtId, String dsId, String gnId) {

        Province province = (Province) UniDB.find(Integer.parseInt(provincId), Province.class);
        String provinceCode = province.getId().toString();
        System.out.println("provinceCode :" + provinceCode);

        District district = (District) UniDB.find(Integer.parseInt(districtId), District.class
        );
        String districtCode = district.getDistrictCode();
        System.out.println("districtCode :" + districtCode);

        DivisionalSecretarial divisionalSecretarial = (DivisionalSecretarial) UniDB.find(Integer.parseInt(dsId), DivisionalSecretarial.class
        );
        String divisionalSecretarialCode = divisionalSecretarial.getDsCode();
        System.out.println("divisionalSecretarialCode :" + divisionalSecretarialCode);

        GnDivision gnDivision = (GnDivision) UniDB.find(Integer.parseInt(gnId), GnDivision.class
        );
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

    public void firstGurantorHandleFileUpload(FileUploadEvent event) {
        System.out.println("firstGurantorHandleFileUpload event");
        firstGuarantorUploadFIleOne = event.getFile();
        if (firstGuarantorUploadFIleOne != null) {
            byte[] imageBytes = firstGuarantorUploadFIleOne.getContent(); // Assuming file.getContent() gets byte[]
            if (imageBytes != null && imageBytes.length > 0) {
                new TemcoVerifications().nicVerification(firstGuarantorUploadFIleOne, "962500269V");
            } else {
                System.out.println("Uploaded file is empty or you haven entered first gurantor's NIC");
            }
        } else {
            System.out.println("No file uploaded.");
        }

    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Double getMonthlyInstallement() {
        return monthlyInstallement;
    }

    public void setMonthlyInstallement(Double monthlyInstallement) {
        this.monthlyInstallement = monthlyInstallement;
    }

    public LoanManager getLoanManager() {
        return loanManager;
    }

    public void setLoanManager(LoanManager loanManager) {
        this.loanManager = loanManager;
    }

    public String getFirstGuarantorFirstName() {
        return firstGuarantorFirstName;
    }

    public void setFirstGuarantorFirstName(String firstGuarantorFirstName) {
        System.out.println("Setting first name: " + firstGuarantorFirstName);
        this.firstGuarantorFirstName = firstGuarantorFirstName;
    }

    public String getFirstGuarantorlastName() {
        return firstGuarantorlastName;
    }

    public void setFirstGuarantorlastName(String firstGuarantorlastName) {
        System.out.println("Setting firstGuarantorlastName: " + firstGuarantorlastName);
        this.firstGuarantorlastName = firstGuarantorlastName;
    }

    public String getFirstGuarantorNic() {
        return firstGuarantorNic;
    }

    public void setFirstGuarantorNic(String firstGuarantorNic) {
        System.out.println("Setting firstGuarantorNic: " + firstGuarantorNic);
        this.firstGuarantorNic = firstGuarantorNic;
    }

    public UploadedFile getFirstGuarantorUploadFIleOne() {
        return firstGuarantorUploadFIleOne;
    }

    public void setFirstGuarantorUploadFIleOne(UploadedFile firstGuarantorUploadFIleOne) {
        this.firstGuarantorUploadFIleOne = firstGuarantorUploadFIleOne;
    }

    public UploadedFile getFirstGuarantorUploadFIleTwo() {
        return firstGuarantorUploadFIleTwo;
    }

    public void setFirstGuarantorUploadFIleTwo(UploadedFile firstGuarantorUploadFIleTwo) {
        this.firstGuarantorUploadFIleTwo = firstGuarantorUploadFIleTwo;
    }

    public String getFirstGuarantorMobileNo() {
        return firstGuarantorMobileNo;
    }

    public void setFirstGuarantorMobileNo(String firstGuarantorMobileNo) {
        this.firstGuarantorMobileNo = firstGuarantorMobileNo;
    }

    public String getFirstGuarantorDocumentType() {
        return firstGuarantorDocumentType;
    }

    public void setFirstGuarantorDocumentType(String firstGuarantorDocumentType) {
        this.firstGuarantorDocumentType = firstGuarantorDocumentType;
    }

    public String getSecondGuarantorFirstName() {
        return secondGuarantorFirstName;
    }

    public void setSecondGuarantorFirstName(String secondGuarantorFirstName) {
        System.out.println("Setting secondGuarantorFirstName: " + secondGuarantorFirstName);
        this.secondGuarantorFirstName = secondGuarantorFirstName;
    }

    public String getSecondGuarantorLastName() {
        return secondGuarantorLastName;
    }

    public void setSecondGuarantorLastName(String secondGuarantorLastName) {
        System.out.println("Setting secondGuarantorLastName: " + secondGuarantorLastName);
        this.secondGuarantorLastName = secondGuarantorLastName;
    }

    public String getSecondGuarantorNic() {
        return secondGuarantorNic;
    }

    public void setSecondGuarantorNic(String secondGuarantorNic) {
        System.out.println("Setting secondGuarantorNic: " + secondGuarantorNic);
        this.secondGuarantorNic = secondGuarantorNic;
    }

    public Double getSecondGuarantorGrossIncome() {
        return secondGuarantorGrossIncome;
    }

    public void setSecondGuarantorGrossIncome(Double secondGuarantorGrossIncome) {
        System.out.println("Setting secondGuarantorGrossIncome: " + secondGuarantorGrossIncome);
        this.secondGuarantorGrossIncome = secondGuarantorGrossIncome;
    }

    public Double getSecondGuarantorNetIncome() {
        return secondGuarantorNetIncome;
    }

    public void setSecondGuarantorNetIncome(Double secondGuarantorNetIncome) {
        this.secondGuarantorNetIncome = secondGuarantorNetIncome;
    }

    public UploadedFile getSecondGuarantorUploadFIleOne() {
        return secondGuarantorUploadFIleOne;
    }

    public void setSecondGuarantorUploadFIleOne(UploadedFile secondGuarantorUploadFIleOne) {
        this.secondGuarantorUploadFIleOne = secondGuarantorUploadFIleOne;
    }

    public UploadedFile getSecondGuarantorUploadFIleTwo() {
        return secondGuarantorUploadFIleTwo;
    }

    public void setSecondGuarantorUploadFIleTwo(UploadedFile secondGuarantorUploadFIleTwo) {
        this.secondGuarantorUploadFIleTwo = secondGuarantorUploadFIleTwo;
    }

    public String getSecondGuarantorMobileNo() {
        return secondGuarantorMobileNo;
    }

    public void setSecondGuarantorMobileNo(String secondGuarantorMobileNo) {
        this.secondGuarantorMobileNo = secondGuarantorMobileNo;
    }

    public String getSecondGuarantorDocumentType() {
        return secondGuarantorDocumentType;
    }

    public void setSecondGuarantorDocumentType(String secondGuarantorDocumentType) {
        this.secondGuarantorDocumentType = secondGuarantorDocumentType;
    }

    public List<SelectItem> getDocumentTypeList() {
        return documentTypeList;
    }

    public void setDocumentTypeList(List<SelectItem> documentTypeList) {
        this.documentTypeList = documentTypeList;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public boolean isMethodCalled() {
        return methodCalled;
    }

    public void setMethodCalled(boolean methodCalled) {
        this.methodCalled = methodCalled;
    }

    public String getLoanIdPara() {
        return loanIdPara;
    }

    public void setLoanIdPara(String loanIdPara) {
        this.loanIdPara = loanIdPara;
    }

    public ComLib getComLib() {
        return ComLib;
    }

    public void setComLib(ComLib ComLib) {
        this.ComLib = ComLib;
    }

    public String getFirstGuarantorSelectedEmployementType() {
        return firstGuarantorSelectedEmployementType;
    }

    public void setFirstGuarantorSelectedEmployementType(String firstGuarantorSelectedEmployementType) {
        this.firstGuarantorSelectedEmployementType = firstGuarantorSelectedEmployementType;
    }

    public List<SelectItem> getFirstGuarantoremployementTypeList() {
        return firstGuarantoremployementTypeList;
    }

    public void setFirstGuarantoremployementTypeList(List<SelectItem> firstGuarantoremployementTypeList) {
        this.firstGuarantoremployementTypeList = firstGuarantoremployementTypeList;
    }

    public boolean isFirstGuarantorselfEmlpoyed() {
        return firstGuarantorselfEmlpoyed;
    }

    public void setFirstGuarantorselfEmlpoyed(boolean firstGuarantorselfEmlpoyed) {
        this.firstGuarantorselfEmlpoyed = firstGuarantorselfEmlpoyed;
    }

    public boolean isFirstGuarantoremlpoyed() {
        return firstGuarantoremlpoyed;
    }

    public void setFirstGuarantoremlpoyed(boolean firstGuarantoremlpoyed) {
        this.firstGuarantoremlpoyed = firstGuarantoremlpoyed;
    }

    public String getSecondGuarantorSelectedEmployementType() {
        return secondGuarantorSelectedEmployementType;
    }

    public void setSecondGuarantorSelectedEmployementType(String secondGuarantorSelectedEmployementType) {
        this.secondGuarantorSelectedEmployementType = secondGuarantorSelectedEmployementType;
    }

    public List<SelectItem> getSecondGuarantoremployementTypeList() {
        return secondGuarantoremployementTypeList;
    }

    public void setSecondGuarantoremployementTypeList(List<SelectItem> secondGuarantoremployementTypeList) {
        this.secondGuarantoremployementTypeList = secondGuarantoremployementTypeList;
    }

    public boolean isSecondGuarantorselfEmlpoyed() {
        return secondGuarantorselfEmlpoyed;
    }

    public void setSecondGuarantorselfEmlpoyed(boolean secondGuarantorselfEmlpoyed) {
        this.secondGuarantorselfEmlpoyed = secondGuarantorselfEmlpoyed;
    }

    public boolean isSecondGuarantoremlpoyed() {
        return secondGuarantoremlpoyed;
    }

    public void setSecondGuarantoremlpoyed(boolean secondGuarantoremlpoyed) {
        this.secondGuarantoremlpoyed = secondGuarantoremlpoyed;
    }

    public String getSelectedProvinceId() {
        return selectedProvinceId;
    }

    public void setSelectedProvinceId(String selectedProvinceId) {
        this.selectedProvinceId = selectedProvinceId;
    }

    public String getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(String selectedDistrict) {
        this.selectedDistrict = selectedDistrict;
    }

    public String getSelectedDivisionalSecretarial() {
        return selectedDivisionalSecretarial;
    }

    public void setSelectedDivisionalSecretarial(String selectedDivisionalSecretarial) {
        this.selectedDivisionalSecretarial = selectedDivisionalSecretarial;
    }

    public String getSelectedGnDivision() {
        return selectedGnDivision;
    }

    public void setSelectedGnDivision(String selectedGnDivision) {
        this.selectedGnDivision = selectedGnDivision;
    }

    public String getSelectedProvinceIdSecond() {
        return selectedProvinceIdSecond;
    }

    public void setSelectedProvinceIdSecond(String selectedProvinceIdSecond) {
        this.selectedProvinceIdSecond = selectedProvinceIdSecond;
    }

    public String getSelectedDistrictSecond() {
        return selectedDistrictSecond;
    }

    public void setSelectedDistrictSecond(String selectedDistrictSecond) {
        this.selectedDistrictSecond = selectedDistrictSecond;
    }

    public String getSelectedDivisionalSecretarialSecond() {
        return selectedDivisionalSecretarialSecond;
    }

    public void setSelectedDivisionalSecretarialSecond(String selectedDivisionalSecretarialSecond) {
        this.selectedDivisionalSecretarialSecond = selectedDivisionalSecretarialSecond;
    }

    public String getSelectedGnDivisionSecond() {
        return selectedGnDivisionSecond;
    }

    public void setSelectedGnDivisionSecond(String selectedGnDivisionSecond) {
        this.selectedGnDivisionSecond = selectedGnDivisionSecond;
    }

    public String getFirstGnameOftheEmployerOrBusiness() {
        return firstGnameOftheEmployerOrBusiness;
    }

    public void setFirstGnameOftheEmployerOrBusiness(String firstGnameOftheEmployerOrBusiness) {
        this.firstGnameOftheEmployerOrBusiness = firstGnameOftheEmployerOrBusiness;
    }

    public String getFirstGcontactNumberOftheEmployerOrBusiness() {
        return firstGcontactNumberOftheEmployerOrBusiness;
    }

    public void setFirstGcontactNumberOftheEmployerOrBusiness(String firstGcontactNumberOftheEmployerOrBusiness) {
        this.firstGcontactNumberOftheEmployerOrBusiness = firstGcontactNumberOftheEmployerOrBusiness;
    }

    public String getFirstGemployerAddressOneOrBusiness() {
        return firstGemployerAddressOneOrBusiness;
    }

    public void setFirstGemployerAddressOneOrBusiness(String firstGemployerAddressOneOrBusiness) {
        this.firstGemployerAddressOneOrBusiness = firstGemployerAddressOneOrBusiness;
    }

    public String getFirstGemployerAddressTwoOrBusiness() {
        return firstGemployerAddressTwoOrBusiness;
    }

    public void setFirstGemployerAddressTwoOrBusiness(String firstGemployerAddressTwoOrBusiness) {
        this.firstGemployerAddressTwoOrBusiness = firstGemployerAddressTwoOrBusiness;
    }

    public String getFirstGemployerAddressThreeOrBusiness() {
        return firstGemployerAddressThreeOrBusiness;
    }

    public void setFirstGemployerAddressThreeOrBusiness(String firstGemployerAddressThreeOrBusiness) {
        this.firstGemployerAddressThreeOrBusiness = firstGemployerAddressThreeOrBusiness;
    }

    public String getSecondGnameOftheEmployerOrBusiness() {
        return secondGnameOftheEmployerOrBusiness;
    }

    public void setSecondGnameOftheEmployerOrBusiness(String secondGnameOftheEmployerOrBusiness) {
        this.secondGnameOftheEmployerOrBusiness = secondGnameOftheEmployerOrBusiness;
    }

    public String getSecondGcontactNumberOftheEmployerOrBusiness() {
        return secondGcontactNumberOftheEmployerOrBusiness;
    }

    public void setSecondGcontactNumberOftheEmployerOrBusiness(String secondGcontactNumberOftheEmployerOrBusiness) {
        this.secondGcontactNumberOftheEmployerOrBusiness = secondGcontactNumberOftheEmployerOrBusiness;
    }

    public String getSecondGemployerAddressOneOrBusiness() {
        return secondGemployerAddressOneOrBusiness;
    }

    public void setSecondGemployerAddressOneOrBusiness(String secondGemployerAddressOneOrBusiness) {
        this.secondGemployerAddressOneOrBusiness = secondGemployerAddressOneOrBusiness;
    }

    public String getSecondGemployerAddressTwoOrBusiness() {
        return secondGemployerAddressTwoOrBusiness;
    }

    public void setSecondGemployerAddressTwoOrBusiness(String secondGemployerAddressTwoOrBusiness) {
        this.secondGemployerAddressTwoOrBusiness = secondGemployerAddressTwoOrBusiness;
    }

    public String getSecondGemployerAddressThreeOrBusiness() {
        return secondGemployerAddressThreeOrBusiness;
    }

    public void setSecondGemployerAddressThreeOrBusiness(String secondGemployerAddressThreeOrBusiness) {
        this.secondGemployerAddressThreeOrBusiness = secondGemployerAddressThreeOrBusiness;
    }

    public List<SelectItem> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<SelectItem> provinceList) {
        this.provinceList = provinceList;
    }

    public List<SelectItem> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<SelectItem> districtList) {
        this.districtList = districtList;
    }

    public List<SelectItem> getDivisionalSecretarialList() {
        return divisionalSecretarialList;
    }

    public void setDivisionalSecretarialList(List<SelectItem> divisionalSecretarialList) {
        this.divisionalSecretarialList = divisionalSecretarialList;
    }

    public List<SelectItem> getGnDivisionList() {
        return gnDivisionList;
    }

    public void setGnDivisionList(List<SelectItem> gnDivisionList) {
        this.gnDivisionList = gnDivisionList;
    }

    public UniDBLocal getUniDB() {
        return UniDB;
    }

    public void setUniDB(UniDBLocal UniDB) {
        this.UniDB = UniDB;
    }

    public List<SelectItem> getProvinceListSecond() {
        return provinceListSecond;
    }

    public void setProvinceListSecond(List<SelectItem> provinceListSecond) {
        this.provinceListSecond = provinceListSecond;
    }

    public List<SelectItem> getDistrictListSecond() {
        return districtListSecond;
    }

    public void setDistrictListSecond(List<SelectItem> districtListSecond) {
        this.districtListSecond = districtListSecond;
    }

    public List<SelectItem> getDivisionalSecretarialListSecond() {
        return divisionalSecretarialListSecond;
    }

    public void setDivisionalSecretarialListSecond(List<SelectItem> divisionalSecretarialListSecond) {
        this.divisionalSecretarialListSecond = divisionalSecretarialListSecond;
    }

    public List<SelectItem> getGnDivisionListSecond() {
        return gnDivisionListSecond;
    }

    public void setGnDivisionListSecond(List<SelectItem> gnDivisionListSecond) {
        this.gnDivisionListSecond = gnDivisionListSecond;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

}
