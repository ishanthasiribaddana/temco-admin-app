/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service.user.task;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lk.exon.temco_loan_system.common.ComPath;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.ApprovalStatus;
import lk.exon.temco_loan_system.entity.DocumentRole;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.entity.Member1;
import lk.exon.temco_loan_system.entity.MemberBankAccounts;
import lk.exon.temco_loan_system.entity.MemberDocuments;
import lk.exon.temco_loan_system.entity.UniversalUserDocument;
import lk.exon.temco_loan_system.entity.UserType;
import lk.exon.temco_loan_system.entity.Voucher;
import lk.exon.temco_loan_system.entity.VoucherApprovalManager;
import lk.exon.temco_loan_system.entity.VoucherDocumentMap;
import lk.exon.temco_loan_system.entity.VoucherItem;
import lk.exon.temco_loan_system.entity.VoucherStatus;
import lk.exon.temco_loan_system.entity.VoucherType;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author USER
 */
@Named
@ViewScoped
public class DepositsSubmission implements Serializable {

    private MemberBankAccounts memberLoanAccount = null;
    private String depositedDate = "";
    private Double depositedTotalAmount = 0.00;
    private UploadedFile uploadeReceipt = null;
    String folderPath = "";
    private String accountName = "";

    String securityCode = "";
    @Inject
    private UniDBLocal uni;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

        securityCode = params.get("en");

        if (securityCode != null) {

            getMemberLoanAccount(securityCode);
            setDateFormat();
        } else {
            System.out.println("in else 2");
        }
    }

    public void save() {
        System.out.println("Uploaded file: " + uploadeReceipt);
        System.out.println("File name: "
                + (uploadeReceipt != null ? uploadeReceipt.getFileName() : "NULL"));
        FacesMessage msg = null;
        if (memberLoanAccount != null) {
            if (depositedTotalAmount != 0.00) {
                if (uploadeReceipt != null) {
                    Date date = new Date();
                    List<VoucherType> voucherTypes = uni.searchByQuery("SELECT v FROM VoucherType v WHERE v.id='13'");
                    Voucher voucher = new Voucher();
                    voucher.setVoucherTypeId(voucherTypes.get(0));

                    List<VoucherStatus> VoucherStatuses = uni.searchByQuery("SELECT s FROM VoucherStatus s WHERE s.id='1'");
                    voucher.setVoucherStatusId(VoucherStatuses.get(0));

                    voucher.setDate(date);
                    voucher.setAmount(depositedTotalAmount);
                    voucher.setGeneralUserProfile(memberLoanAccount.getMemberId().getGeneralUserProfileId());
                    int statusCode = 1;
                    byte[] byteArray = new byte[]{(byte) statusCode};
                    voucher.setIsActive(byteArray);

                    uni.create(voucher);

                    VoucherItem item = new VoucherItem();
                    item.setVoucher(voucher);
                    item.setAmount(depositedTotalAmount);
                    item.setEnteredDate(date);
                    item.setIsActive(Short.valueOf("1"));

                    uni.create(item);

                    VoucherApprovalManager approval = new VoucherApprovalManager();
                    approval.setVoucherId(voucher);
                    List<ApprovalStatus> approvalStatuses = uni.searchByQuery("SELECT s FROM ApprovalStatus s WHERE s.id='3'");
                    approval.setApprovalStatusId(approvalStatuses.get(0));

                    uni.create(approval);

                    MemberDocuments memberDocuments = (MemberDocuments) uni.find(2, MemberDocuments.class);

                    String folderPath = createGurantorFolders(ComPath.depositsSlipsPath, memberLoanAccount.getMemberId().getGeneralUserProfileId().getNic());
                    System.out.println("folderPath "+folderPath);
                    UserType userType = (UserType) uni.find(1, UserType.class);

                    UniversalUserDocument uud = new UniversalUserDocument();
                    uud.setGeneralUserProfileId(memberLoanAccount.getMemberId().getGeneralUserProfileId());
                    uud.setCreatedTimestamp(date);
                    uud.setIsVerified(Short.valueOf("0"));
                    uud.setFileUrl(imageUpload(uploadeReceipt, folderPath));
                    uud.setDocumentTypeId(memberDocuments);
                    uud.setUserTypeId(userType);
                    uud.setIsActive(Short.valueOf("1"));
                    uni.create(uud);

                    List<DocumentRole> roles = uni.searchByQuery("SELECT d FROM DocumentRole d WHERE d.id='1'");

                    VoucherDocumentMap map = new VoucherDocumentMap();
                    map.setVoucherId(voucher);
                    map.setUniversalUserDocumentId(uud);
                    map.setDocumentRoleId(roles.get(0));
                    map.setCreatedDate(date);
                    uni.create(map);

                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Upload the Bank Deposited Receipt");
                    FacesContext.getCurrentInstance().addMessage("", msg);
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Enter Deposited Amount");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Account Could Not found.Please contact the admin");
            FacesContext.getCurrentInstance().addMessage("", msg);
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

    private void setDateFormat() {
        Date date = new Date();
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
        depositedDate = smp.format(date);
    }

    private void getMemberLoanAccount(String securityCode) {
        System.out.println("A");
        List<GeneralUserProfile> generalUserProfile = uni.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.verificationToken ='" + securityCode + "' ");
        System.out.println("B");
        if (!generalUserProfile.isEmpty()) {
            System.out.println("C" + generalUserProfile.get(0).getId());
            List<Member1> member = uni.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + generalUserProfile.get(0).getId() + "'");
            System.out.println("D");
            if (!member.isEmpty()) {
                System.out.println("E");
                List<MemberBankAccounts> bankaccount = uni.searchByQuery("SELECT g FROM MemberBankAccounts g WHERE g.memberId.id='" + member.get(0).getId() + "' AND g.accountTypeId.id='1' ");
                if (!bankaccount.isEmpty()) {
                    memberLoanAccount = bankaccount.get(0);
                    accountName = generalUserProfile.get(0).getFirstName() + " " + generalUserProfile.get(0).getLastName();
                    System.out.println("F");
                }
            }
        }

    }

    public MemberBankAccounts getMemberLoanAccount() {
        return memberLoanAccount;
    }

    public void setMemberLoanAccount(MemberBankAccounts memberLoanAccount) {
        this.memberLoanAccount = memberLoanAccount;
    }

    public String getDepositedDate() {
        return depositedDate;
    }

    public void setDepositedDate(String depositedDate) {
        this.depositedDate = depositedDate;
    }

    public Double getDepositedTotalAmount() {
        return depositedTotalAmount;
    }

    public void setDepositedTotalAmount(Double depositedTotalAmount) {
        this.depositedTotalAmount = depositedTotalAmount;
    }

    public UploadedFile getUploadeReceipt() {
        return uploadeReceipt;
    }

    public void setUploadeReceipt(UploadedFile uploadeReceipt) {
        this.uploadeReceipt = uploadeReceipt;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

}
