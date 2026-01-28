/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.exon.temco_loan_system.common.ComPath;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.DocumentDataVerification;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.entity.GurantorManager;
import lk.exon.temco_loan_system.entity.LoanApplicantGurantor;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.Member1;
import lk.exon.temco_loan_system.entity.MemberDocuments;
import lk.exon.temco_loan_system.entity.TemporaryDataForReconsilation;
import lk.exon.temco_loan_system.entity.UniversalUserDocument;
import lk.exon.temco_loan_system.entity.UserType;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author USER
 */
@Named
@SessionScoped
public class FirstWeekDocumentsSubmission implements Serializable {

    private String accountNo;
    private String accountName;
    private String bankName;
    private String branchName;

    private String loanApplicantMemberNo;
    private UploadedFile lastThreeMonthsInstallementsSlip;
    private UploadedFile loanApplicantSlip;

    private GurantorManager memberOne;
    private String memberOneId;
    private UploadedFile guarantorOneSlip;

    private GurantorManager memberTwo;
    private String memberTwoId;
    private UploadedFile guarantorTwoSlip;

    private String securityCode;

    private GeneralUserProfile gup;

    private String folderPath;

    private String loanId;

    private LoanManager loanManager;

    @EJB
    UniDBLocal uniDB;

    @EJB
    UniDBLocal uniDb;

    @PostConstruct
    public void init() {
        System.out.println("saveDocuments init called first week document submission");

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        securityCode = params.get("en");
        loanId = params.get("lid");

        if (securityCode != null) {
            System.out.println("in if");
            getUserDetailsFromGeneralUserProfile(securityCode);
            loanManager = getVerificationToken(loanId);
        } else {
            System.out.println("in else");
        }

        folderPath = ComPath.basePath;
        LoadLoanGuarantors(loanManager);
    }

    public void LoadLoanGuarantors(LoanManager loanManager) {
        LoanApplicantGurantor loanApplicant = loanManager.getLoanApplicantAndGurantorsId();
        List<GurantorManager> guarantorManagers = uniDB.searchByQuery("SELECT g FROM GurantorManager g WHERE g.loanApplicantId.id='" + loanApplicant.getId() + "' ");
        System.out.println("!guarantorManager.isEmpty() " + !guarantorManagers.isEmpty());
        int i = 0;
        if (!guarantorManagers.isEmpty()) {
            for (GurantorManager guarantorManager : guarantorManagers) {
                if (guarantorManager.getGurantorCountId().getValue().equals("1")) {
                    memberOne = guarantorManager;
                    System.out.println("memberOne Name : " + memberOne.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName());
                    i++;
                } else if (guarantorManager.getGurantorCountId().getValue().equals("2")) {
                    memberTwo = guarantorManager;
                    System.out.println("memberTwo Name : " + memberTwo.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName());
                }
            }
        }

    }

    public LoanManager getVerificationToken(String token) {
        List<LoanManager> l = uniDB.searchByQuery("SELECT  g FROM LoanManager g WHERE g.verificationToke='" + token + "' ");
        if (l.isEmpty()) {

            return null;
        } else {
            System.out.println("returned object");
            return l.get(0);
        }
    }

    private void getUserDetailsFromGeneralUserProfile(String securityCode) {

        List<GeneralUserProfile> generalUserProfile = uniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.verificationToken ='" + securityCode + "' ");
        if (!generalUserProfile.isEmpty()) {
            System.out.println("name " + generalUserProfile.get(0).getFirstName());
            gup = generalUserProfile.get(0);
        }
    }

    public void saveDocuments() {
        System.out.println("called");
        FacesMessage msg;
        if (!checkBankDetails()) {
            if (!verifyUploadedImages()) {

                try {
                    Date date = new Date();

//                    loan applicant member and share fee uploading
                    String loanApplicantFolderPath = createGurantorFolders(folderPath, loanManager);

                    String imageName = imageUpload(loanApplicantSlip, loanApplicantFolderPath);

                    UniversalUserDocument u1 = new UniversalUserDocument();
                    u1.setGeneralUserProfileId(gup);
                    u1.setCreatedTimestamp(date);
                    u1.setIsVerified(Short.valueOf("0"));
                    u1.setDocumentTypeId((MemberDocuments) uniDB.find(2, MemberDocuments.class));
                    u1.setUserTypeId((UserType) uniDB.find(1, UserType.class));
                    u1.setFileUrl(imageName);
                    uniDB.create(u1);

                    TemporaryDataForReconsilation tdfpAccountNo = new TemporaryDataForReconsilation();
                    tdfpAccountNo.setUserDocumentId(u1);
                    tdfpAccountNo.setFieldValue(accountNo);
                    tdfpAccountNo.setDocumentDataVerificationId((DocumentDataVerification) uniDB.find(1, DocumentDataVerification.class));
                    uniDB.create(tdfpAccountNo);

                    TemporaryDataForReconsilation tdfpAccountName = new TemporaryDataForReconsilation();
                    tdfpAccountName.setUserDocumentId(u1);
                    tdfpAccountName.setFieldValue(accountName);
                    tdfpAccountName.setDocumentDataVerificationId((DocumentDataVerification) uniDB.find(2, DocumentDataVerification.class));
                    uniDB.create(tdfpAccountName);

                    TemporaryDataForReconsilation tdfpBankBranch = new TemporaryDataForReconsilation();
                    tdfpBankBranch.setUserDocumentId(u1);
                    tdfpBankBranch.setFieldValue(branchName);
                    tdfpBankBranch.setDocumentDataVerificationId((DocumentDataVerification) uniDB.find(3, DocumentDataVerification.class));
                    uniDB.create(tdfpBankBranch);

                    TemporaryDataForReconsilation tdfpBankName = new TemporaryDataForReconsilation();
                    tdfpBankName.setUserDocumentId(u1);
                    tdfpBankName.setFieldValue(accountNo);
                    tdfpBankName.setDocumentDataVerificationId((DocumentDataVerification) uniDB.find(4, DocumentDataVerification.class));
                    uniDB.create(tdfpBankName);

//                    loan applicant last three months downpayment slips uploading
                    String imageName2 = imageUpload(lastThreeMonthsInstallementsSlip, loanApplicantFolderPath);

                    UniversalUserDocument u2 = new UniversalUserDocument();
                    u2.setGeneralUserProfileId(gup);
                    u2.setCreatedTimestamp(date);
                    u2.setIsVerified(Short.valueOf("0"));
                    u2.setDocumentTypeId((MemberDocuments) uniDB.find(2, MemberDocuments.class));
                    u2.setUserTypeId((UserType) uniDB.find(1, UserType.class));
                    u2.setFileUrl(imageName2);
                    uniDB.create(u2);

//                    first guarantor membership fee and share purchase fee slip uploading
                    String imageName3 = imageUpload(lastThreeMonthsInstallementsSlip, folderPath + "/" + memberOneId);
                    UniversalUserDocument u3 = new UniversalUserDocument();
                    u3.setGeneralUserProfileId(gup);
                    u3.setCreatedTimestamp(date);
                    u3.setIsVerified(Short.valueOf("0"));
                    u3.setDocumentTypeId((MemberDocuments) uniDB.find(2, MemberDocuments.class));
                    u3.setUserTypeId((UserType) uniDB.find(1, UserType.class));
                    u3.setFileUrl(imageName3);
                    uniDB.create(u3);

                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    ExternalContext externalContext = facesContext.getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + "/user/main/dashboard.xhtml?en=" + securityCode);
                    facesContext.responseComplete();

                } catch (Exception e) {
                    e.printStackTrace();
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ! ! !", "Something went wrong please contact the admin");
                    FacesContext.getCurrentInstance().addMessage("", msg);
                }

            } else {

            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ! ! !", "Please verify that all data inserted correctly");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

//    upload images
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

//    create a new folder for store loan applicant documents
    public String createGurantorFolders(String mainFolderPath, LoanManager loanManager) {
        System.out.println("createGurantorFolders");

        // Create the main directory
        String basePath = mainFolderPath;

        // Create 'user1' and 'user2' folders inside the main directory
        String uniqueFolderName = loanManager.getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo();

        // Construct the full folder path
        Path folderPath = Paths.get(basePath + "/" + loanManager.getVerificationToke() + "/" + uniqueFolderName);
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

    public boolean verifyUploadedImages() {
        if (lastThreeMonthsInstallementsSlip == null) {
            return false;
        }

        if (loanApplicantSlip == null) {
            return false;
        }

        if (guarantorOneSlip == null) {
            return false;
        }

        if (guarantorTwoSlip == null) {
            return false;
        }

        return true;
    }

    public boolean checkBankDetails() {
        return accountNo.isEmpty() && accountName.isEmpty() && bankName.isEmpty() && branchName.isEmpty();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public UploadedFile getLastThreeMonthsInstallementsSlip() {
        return lastThreeMonthsInstallementsSlip;
    }

    public void setLastThreeMonthsInstallementsSlip(UploadedFile lastThreeMonthsInstallementsSlip) {
        this.lastThreeMonthsInstallementsSlip = lastThreeMonthsInstallementsSlip;
    }

    public UploadedFile getLoanApplicantSlip() {
        return loanApplicantSlip;
    }

    public void setLoanApplicantSlip(UploadedFile loanApplicantSlip) {
        this.loanApplicantSlip = loanApplicantSlip;
    }

    public UploadedFile getGuarantorOneSlip() {
        return guarantorOneSlip;
    }

    public void setGuarantorOneSlip(UploadedFile guarantorOneSlip) {
        this.guarantorOneSlip = guarantorOneSlip;
    }

    public UploadedFile getGuarantorTwoSlip() {
        return guarantorTwoSlip;
    }

    public void setGuarantorTwoSlip(UploadedFile guarantorTwoSlip) {
        this.guarantorTwoSlip = guarantorTwoSlip;
    }

    public UniDBLocal getUniDb() {
        return uniDb;
    }

    public void setUniDb(UniDBLocal uniDb) {
        this.uniDb = uniDb;
    }

    public String getMemberOneId() {
        return memberOneId;
    }

    public void setMemberOneId(String memberOneId) {
        this.memberOneId = memberOneId;
    }

    public String getMemberTwoId() {
        return memberTwoId;
    }

    public void setMemberTwoId(String memberTwoId) {
        this.memberTwoId = memberTwoId;
    }

}
