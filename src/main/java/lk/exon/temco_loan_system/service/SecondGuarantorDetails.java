/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import com.google.cloud.storage.Storage;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
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
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.CustomerResponseHistory;
import lk.exon.temco_loan_system.entity.District;
import lk.exon.temco_loan_system.entity.DivisionalSecretarial;
import lk.exon.temco_loan_system.entity.EmployementType;
import lk.exon.temco_loan_system.entity.GeneralOrganizationProfile;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.entity.GnDivision;
import lk.exon.temco_loan_system.entity.GopHasMember;
import lk.exon.temco_loan_system.entity.GurantorCount;
import lk.exon.temco_loan_system.entity.GurantorManager;
import lk.exon.temco_loan_system.entity.LoanApplicantGurantor;
import lk.exon.temco_loan_system.entity.LoanCustomer;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.LoanStatus;
import lk.exon.temco_loan_system.entity.LoanStatusManager;
import lk.exon.temco_loan_system.entity.Member1;
import lk.exon.temco_loan_system.entity.MemberDocuments;
import lk.exon.temco_loan_system.entity.MemberOrganizationsHistory;
import lk.exon.temco_loan_system.entity.OfferManager;
import lk.exon.temco_loan_system.entity.OrganizationType;
import lk.exon.temco_loan_system.entity.Province;
import lk.exon.temco_loan_system.entity.ResponseStatus;
import lk.exon.temco_loan_system.entity.UniversalUserDocument;
import lk.exon.temco_loan_system.entity.UserType;
import static lk.exon.temco_loan_system.service.LoanRequestForm.formatID;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author USER
 */
@Named
@LocalBean
@ViewScoped
public class SecondGuarantorDetails implements Serializable {

    private String loanId;
    private Double monthlyInstallement;
    private LoanManager loanManager;

    private String secondGuarantorFirstName;
    private String secondGuarantorLastName;
    private String secondGuarantorNic;
    private Double secondGuarantorGrossIncome;
    private Double secondGuarantorNetIncome;
    private UploadedFile secondGuarantorUploadFIleOne;
    private UploadedFile secondGuarantorUploadFIleTwo;
    private String secondGuarantorMobileNo;
    private String secondGuarantorDocumentType;

    private double interestRate = 6;
    private String loanIdPara;

    private String secondGuarantorSelectedEmployementType;
    private List<SelectItem> secondGuarantoremployementTypeList;
    private boolean secondGuarantorselfEmlpoyed = false;
    private boolean secondGuarantoremlpoyed = false;

    private String selectedProvinceIdSecond;
    private String selectedDistrictSecond;
    private String selectedDivisionalSecretarialSecond;
    private String selectedGnDivisionSecond;

    private String secondGnameOftheEmployerOrBusiness;
    private String secondGcontactNumberOftheEmployerOrBusiness;
    private String secondGemployerAddressOneOrBusiness;
    private String secondGemployerAddressTwoOrBusiness;
    private String secondGemployerAddressThreeOrBusiness;

    private List<SelectItem> provinceListSecond = new ArrayList<>();
    private List<SelectItem> districtListSecond = new ArrayList<>();
    private List<SelectItem> divisionalSecretarialListSecond = new ArrayList<>();
    private List<SelectItem> gnDivisionListSecond = new ArrayList<>();

    private String organizationIdSecond;

    private String folderPath;

    private Date date;

    private Storage storage;

    private String organizationIdFirst;

    private boolean x = true;
    private boolean secondGsaved;

    private GurantorCount gurantorNo;

    String nic = "";

    @Inject
    LoanRequestForm LoanRequestForm;

    @EJB
    private UniDBLocal UniDB;

    @Inject
    GuarantorDetails guarantorDetails;

    @PostConstruct
    public void init() {
        try {
            initializedMethod();
            System.out.println("in it");

            getDistrictListSecond().add(new SelectItem("0", "Select"));
            getDivisionalSecretarialListSecond().add(new SelectItem("0", "Select"));
            getGnDivisionListSecond().add(new SelectItem("0", "Select"));

            EmployeeTypeList();
            provinceSecond();

            date = new Date();

            folderPath = guarantorDetails.getFolderPath();
            gurantorNo = (GurantorCount) UniDB.find(2, GurantorCount.class);
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

        System.out.println("loanIdPara" + loanIdPara);
        try {
            if (loanIdPara != null) {
                loanManager = getVerificationToken(loanIdPara);
                System.out.println("loan manager gotted");
                if (loanManager != null) {
                    loanId = loanManager.getReferenceId();
                    System.out.println("set the referrence id");
                    updateOfferManager(loanManager);
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
        System.out.println("updateOfferManager");
        List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + nic + "'");
        System.out.println("nic " + nic);
        if (!loanCustomer.isEmpty()) {
            System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
            List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
            System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
            if (!offerManager.isEmpty()) {
                List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='11'");
                if (crhList.isEmpty()) {
                    CustomerResponseHistory crh = new CustomerResponseHistory();
                    crh.setDate(date);
                    crh.setOfferManagerId(offerManager.get(0));
                    crh.setResponseStatusId((ResponseStatus) UniDB.find(11, ResponseStatus.class));
                    UniDB.create(crh);
                }
            }
        }
    }

    public void saveSecondGuarantorDetails() {
        System.out.println("saveSecondGuarantorDetails ");

        System.out.println("secondGuarantorFirstName " + secondGuarantorFirstName);
        System.out.println("secondGuarantorLastName " + secondGuarantorLastName);
        FacesMessage msg = null;
        System.out.println("saveSecondGuarantorDetails out");
        if (x) {
            x = false;
            System.out.println("saveSecondGuarantorDetails in");
            System.out.println("secondGuarantorFirstName " + secondGuarantorFirstName);
            try {
                if (secondGuarantorFirstName != null && !secondGuarantorFirstName.isEmpty()) {
                    if (secondGuarantorLastName != null && !secondGuarantorLastName.isEmpty()) {
                        if (secondGuarantorGrossIncome != null && secondGuarantorGrossIncome != 0.00) {
                            if (secondGuarantorUploadFIleOne != null) {
                                if (secondGuarantorUploadFIleTwo != null) {

                                    System.out.println("all got it");
                                    Double monthlinstallement = loanManager.getMonthlyInstallement();

                                    double guarantorInstallemt = (secondGuarantorGrossIncome / 100) * 60;

                                    System.out.println("guarantorInstallemt " + guarantorInstallemt);

                                    if (monthlinstallement <= guarantorInstallemt) {
                                        System.out.println("A ");

                                        List<GeneralUserProfile> gupExistSecondGuarantor = UniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic='" + secondGuarantorNic + "'");

                                        GeneralUserProfile gupSecondGuarantor;

                                        if (gupExistSecondGuarantor == null || gupExistSecondGuarantor.isEmpty()) {
                                            gupSecondGuarantor = new GeneralUserProfile();
                                            gupSecondGuarantor.setFirstName(secondGuarantorFirstName);
                                            gupSecondGuarantor.setLastName(secondGuarantorLastName);
                                            gupSecondGuarantor.setNic(secondGuarantorNic);
                                            gupSecondGuarantor.setMobileNo(secondGuarantorMobileNo);
                                            gupSecondGuarantor.setProfileCreatedDate(date);
                                            gupSecondGuarantor.setVerificationToken(Security.encrypt(Filteration.getFilteredSHA256HashedPassword(secondGuarantorMobileNo)));
                                            gupSecondGuarantor.setProvinceId((Province) UniDB.find(Integer.parseInt(selectedProvinceIdSecond), Province.class));
                                            gupSecondGuarantor.setDistrictId((District) UniDB.find(Integer.parseInt(selectedDistrictSecond), District.class));
                                            gupSecondGuarantor.setDivisionalSecretarialId((DivisionalSecretarial) UniDB.find(Integer.parseInt(selectedDivisionalSecretarialSecond), DivisionalSecretarial.class));
                                            gupSecondGuarantor.setGnDivisionId((GnDivision) UniDB.find(Integer.parseInt(selectedGnDivisionSecond), GnDivision.class));
                                            gupSecondGuarantor.setIsActive(Short.valueOf("1"));
                                            UniDB.create(gupSecondGuarantor);
                                            System.out.println("J ");
                                        } else {
                                            gupSecondGuarantor = gupExistSecondGuarantor.get(0);
                                        }

                                        List<Member1> existmemberSecondGuarantor = UniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gupSecondGuarantor.getId() + "' ");

                                        Member1 memberSecondGuarantor = null;
                                        String memberTwoId;
                                        if (existmemberSecondGuarantor == null || existmemberSecondGuarantor.isEmpty()) {
                                            memberSecondGuarantor = new Member1();
                                            memberTwoId = generateMemberNo(selectedProvinceIdSecond, selectedDistrictSecond, selectedDivisionalSecretarialSecond, selectedGnDivisionSecond);
                                            memberSecondGuarantor.setMembershipNo(memberTwoId);
                                            memberSecondGuarantor.setRegisteredDate(date);
                                            memberSecondGuarantor.setGeneralUserProfileId(gupSecondGuarantor);
                                            memberSecondGuarantor.setKpiValue(0.00);
                                            memberSecondGuarantor.setAvailableFund(0.00);
                                            UniDB.create(memberSecondGuarantor);
                                            GopHasMember gopHasMember = new GopHasMember();
                                            gopHasMember.setMemberId(memberSecondGuarantor);
                                            gopHasMember.setStartDate(new Date());
                                            gopHasMember
                                                    .setGeneralOrganizationProfileId((GeneralOrganizationProfile) UniDB.find(1, GeneralOrganizationProfile.class
                                                    ));
                                            UniDB.create(gopHasMember);
                                            System.out.println("K ");

                                        } else {
                                            memberSecondGuarantor = existmemberSecondGuarantor.get(0);
                                            memberTwoId = existmemberSecondGuarantor.get(0).getMembershipNo();
                                            System.out.println("L ");
                                        }

                                        List<GeneralOrganizationProfile> generalOrganizationProfileListSecond;
                                        if (organizationIdFirst != null) {
                                            generalOrganizationProfileListSecond = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.id='" + organizationIdSecond + "'");
                                        } else {
                                            generalOrganizationProfileListSecond = null;
                                        }

                                        GeneralOrganizationProfile orgProfileSecond = null;
                                        if (generalOrganizationProfileListSecond == null || generalOrganizationProfileListSecond.isEmpty()) {
                                            orgProfileSecond = new GeneralOrganizationProfile();
                                            orgProfileSecond.setName(secondGnameOftheEmployerOrBusiness);
                                            orgProfileSecond.setPhoneNo(secondGcontactNumberOftheEmployerOrBusiness);
                                            orgProfileSecond.setAddress1(secondGemployerAddressOneOrBusiness);
                                            orgProfileSecond.setAddress2(secondGemployerAddressTwoOrBusiness);
                                            orgProfileSecond.setAddress3(secondGemployerAddressThreeOrBusiness);
                                            orgProfileSecond
                                                    .setOrganizationTypeId((OrganizationType) UniDB.find(4, OrganizationType.class
                                                    ));
                                            UniDB.create(orgProfileSecond);

                                        } else {
                                            orgProfileSecond = generalOrganizationProfileListSecond.get(0);
                                        }

                                        List<MemberOrganizationsHistory> mohListsSecond = UniDB.searchByQuery("SELECT g FROM MemberOrganizationsHistory g WHERE g.generalOrganizationProfileId.id='" + orgProfileSecond.getId() + "' AND g.memberId.id='" + memberSecondGuarantor.getId() + "'");

                                        if (mohListsSecond.isEmpty()) {
                                            MemberOrganizationsHistory moh = new MemberOrganizationsHistory();
                                            moh.setDate(new Date());
                                            moh.setGeneralOrganizationProfileId(orgProfileSecond);
                                            moh.setIsActive(Short.valueOf("1"));
                                            moh.setMemberId(memberSecondGuarantor);
                                            UniDB.create(moh);
                                        } else {
                                            mohListsSecond.get(0).setDate(new Date());
                                            mohListsSecond.get(0).setIsActive(Short.valueOf("1"));
                                        }

                                        LoanApplicantGurantor secondGuarantor = new LoanApplicantGurantor();
                                        secondGuarantor.setMemberId(memberSecondGuarantor);
                                        secondGuarantor.setDate(date);
                                        UniDB.create(secondGuarantor);

                                        GurantorManager secondGurantorManager = new GurantorManager();
                                        secondGurantorManager.setLoanApplicantId(loanManager.getLoanApplicantAndGurantorsId());
                                        secondGurantorManager.setLoanApplicantAndGurantorsId(secondGuarantor);

                                        System.out.println("Member two id " + memberTwoId);

                                        String secondGuarantorFolderPath = createGurantorFolders(folderPath, memberTwoId);
                                        secondGurantorManager.setGurantorCountId(gurantorNo);
                                        System.out.println("String secondGuarantorFolderPath - " + secondGuarantorFolderPath);
                                        UniDB.create(secondGurantorManager);

                                        MemberDocuments memberDocuments = (MemberDocuments) UniDB.find(1, MemberDocuments.class);
                                        UserType userType = (UserType) UniDB.find(1, UserType.class);

                                        UniversalUserDocument uud = new UniversalUserDocument();
                                        uud.setGeneralUserProfileId(gupSecondGuarantor);
                                        uud.setCreatedTimestamp(date);
                                        uud.setIsVerified(Short.valueOf("0"));
                                        uud.setFileUrl(imageUpload(secondGuarantorUploadFIleOne, secondGuarantorFolderPath));
                                        uud.setDocumentTypeId(memberDocuments);
                                        uud.setUserTypeId(userType);
                                        uud.setIsActive(Short.parseShort("1"));
                                        UniDB.create(uud);

                                        UniversalUserDocument uud2 = new UniversalUserDocument();
                                        uud2.setGeneralUserProfileId(gupSecondGuarantor);
                                        uud2.setCreatedTimestamp(date);
                                        uud2.setIsVerified(Short.valueOf("0"));
                                        uud2.setFileUrl(imageUpload(secondGuarantorUploadFIleTwo, secondGuarantorFolderPath));
                                        uud2.setDocumentTypeId(memberDocuments);
                                        uud2.setUserTypeId(userType);
                                        uud2.setIsActive(Short.parseShort("1"));
                                        UniDB.create(uud2);

                                        LoanStatusManager loanStatusManager = new LoanStatusManager();
                                        loanStatusManager.setDate(date);
                                        loanStatusManager.setLoanStatusId((LoanStatus) UniDB.find(1, LoanStatus.class));
                                        loanStatusManager.setLoanManagerId(loanManager);
                                        UniDB.create(loanStatusManager);

                                        secondGsaved = true;

                                        Date dateTwo = new Date();
                                        System.out.println("updateOfferManager");
                                        List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + nic + "'");
                                        System.out.println("nic " + nic);
                                        if (!loanCustomer.isEmpty()) {
                                            System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
                                            List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
                                            System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
                                            if (!offerManager.isEmpty()) {
                                                List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='12'");
                                                if (crhList.isEmpty()) {
                                                    CustomerResponseHistory crh = new CustomerResponseHistory();
                                                    crh.setDate(dateTwo);
                                                    crh.setOfferManagerId(offerManager.get(0));
                                                    crh.setResponseStatusId((ResponseStatus) UniDB.find(12, ResponseStatus.class));
                                                    UniDB.create(crh);
                                                }
                                            }
                                        }

                                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success ! ! !", "Second Guarantor Details Saved Successful");
                                        FacesContext.getCurrentInstance().addMessage("", msg);

                                        FacesContext facesContext = FacesContext.getCurrentInstance();
                                        ExternalContext externalContext = facesContext.getExternalContext();

                                        String encodedloanIdPara = URLEncoder.encode(loanIdPara, StandardCharsets.UTF_8);

                                        externalContext.redirect(externalContext.getRequestContextPath() + "/tasks/loan-details.xhtml?l=" + encodedloanIdPara);
                                        facesContext.responseComplete();

                                    } else {
                                        secondGsaved = false;
                                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ensure the loan monthly installment is 60% or below the guarantor's gross income.");
                                        FacesContext.getCurrentInstance().addMessage("", msg);
                                    }
                                } else {
                                    secondGsaved = false;
                                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please upload the front image of second guarantor's nic or driving licence");
                                    FacesContext.getCurrentInstance().addMessage("", msg);
                                }
                            } else {
                                secondGsaved = false;
                                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please upload the front image of second guarantor's nic or driving licence");
                                FacesContext.getCurrentInstance().addMessage("", msg);
                            }
                        } else {
                            secondGsaved = false;
                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please enter the second guarantor Gross income");
                            FacesContext.getCurrentInstance().addMessage("", msg);
                        }
                    } else {
                        secondGsaved = false;
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please enter the second guarantor last name");
                        FacesContext.getCurrentInstance().addMessage("", msg);
                    }
                } else {
                    secondGsaved = false;
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please enter the second guarantor first name");
                    FacesContext.getCurrentInstance().addMessage("", msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Guarantor already registered as your loan guarantor");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    private void EmployeeTypeList() {

        secondGuarantoremployementTypeList = new ArrayList<>();

        secondGuarantoremployementTypeList.add(new SelectItem("0", "Select"));
        List<EmployementType> employementTypes = UniDB.searchByQuery("SELECT g FROM EmployementType g");
        for (EmployementType employementType : employementTypes) {
            if (employementType.getId() != 3) {
                secondGuarantoremployementTypeList.add(new SelectItem(employementType.getId(), employementType.getType()));
            }
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

    public LoanManager getVerificationToken(String token) {
        List<LoanManager> l = UniDB.searchByQuery("SELECT  g FROM LoanManager g WHERE g.verificationToke='" + token + "' ");
        if (l.isEmpty()) {

            return null;
        } else {
            System.out.println("returned object");
            return l.get(0);
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

    public void setSecondGuarantorSelectedEmployer() {
        System.out.println("nameOftheEmployer 1" + secondGnameOftheEmployerOrBusiness);

//        int min = 0;
//        int max = secondGnameOftheEmployerOrBusiness.indexOf(")");
//        String id = secondGnameOftheEmployerOrBusiness.substring(min, max);
//        String companyName = secondGnameOftheEmployerOrBusiness.substring(max + 1);
//        System.out.println("id " + id);
//        System.out.println("companyName " + companyName);
//        secondGnameOftheEmployerOrBusiness = companyName;
        List<GeneralOrganizationProfile> generalUserProfilesList = UniDB.searchByQuery("SELECT g FROM GeneralOrganizationProfile g WHERE g.name LIKE '%" + secondGnameOftheEmployerOrBusiness + "%' AND g.organizationTypeId.id='4' order by  g.id ASC");
        System.out.println("generalUserProfilesList " + generalUserProfilesList.isEmpty());
        if (!generalUserProfilesList.isEmpty()) {
            for (GeneralOrganizationProfile generalOrganizationProfile : generalUserProfilesList) {
                organizationIdSecond = String.valueOf(generalOrganizationProfile.getId());
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

    public String getSecondGuarantorFirstName() {
        return secondGuarantorFirstName;
    }

    public void setSecondGuarantorFirstName(String secondGuarantorFirstName) {
        this.secondGuarantorFirstName = secondGuarantorFirstName;
    }

    public String getSecondGuarantorLastName() {
        return secondGuarantorLastName;
    }

    public void setSecondGuarantorLastName(String secondGuarantorLastName) {
        this.secondGuarantorLastName = secondGuarantorLastName;
    }

    public String getSecondGuarantorNic() {
        return secondGuarantorNic;
    }

    public void setSecondGuarantorNic(String secondGuarantorNic) {
        this.secondGuarantorNic = secondGuarantorNic;
    }

    public Double getSecondGuarantorGrossIncome() {
        return secondGuarantorGrossIncome;
    }

    public void setSecondGuarantorGrossIncome(Double secondGuarantorGrossIncome) {
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

    public String getOrganizationIdSecond() {
        return organizationIdSecond;
    }

    public void setOrganizationIdSecond(String organizationIdSecond) {
        this.organizationIdSecond = organizationIdSecond;
    }

    public boolean isSecondGsaved() {
        return secondGsaved;
    }

    public void setSecondGsaved(boolean secondGsaved) {
        this.secondGsaved = secondGsaved;
    }

}
