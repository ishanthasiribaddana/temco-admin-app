/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.user.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lk.exon.temco_loan_system.common.ComPath;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.entity.GuarantorDocuments;
import lk.exon.temco_loan_system.entity.GurantorManager;
import lk.exon.temco_loan_system.entity.LoanApplicantGurantor;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.LoanStatusManager;
import lk.exon.temco_loan_system.entity.Member1;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author USER
 */
@Named
@SessionScoped
public class UserLoans implements Serializable {

    private String securityCode;
    private LoanApplicantGurantor gurantorManager;

    private List<UsereLoansDetails> userLoansArrayList;

    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNo;
    private String dateOfBirth;
    private String email;
    private String addressOne;
    private String addressTwo;
    private String addressThree;

    private String ffirstName;
    private String flastName;
    private String fnic;
    private String ffrontNic;
    private String firstBackNic;

    private String sfirstName;
    private String slastName;
    private String snic;
    private String sfrontNic;
    private String secondBackNic;

    private UploadedFile firstGuarantorUploadFIleOne;
    private UploadedFile firstGuarantorUploadFIleTwo;

    private UploadedFile secondGuarantorUploadFIleOne;
    private UploadedFile secondGuarantorUploadFIleTwo;

    private GurantorManager firstGuarantorObj;
    private GurantorManager secondGuarantorObj;

    private String loanSecurityCode;

    private UsereLoansDetails selectedLoanObj;

    private boolean firstGuarantorAvailable = false;
    private boolean secondGuaranotrAvailable = false;

    private String loanToken;

    @EJB
    UniDBLocal uniDB;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

        securityCode = params.get("en");
        loanSecurityCode = params.get("lid");

        if (securityCode != null) {
            System.out.println("in if");
            LoadUserLoans();
            System.out.println("userLoansArrayList size " + userLoansArrayList.size());
//            getUserDetailsFromGeneralUserProfile(securityCode);
        } else {
            System.out.println("in else 2");
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true"; // Redirect to the login page after logout
    }

    public void updateLoanDetails() {
        System.out.println("update loan details called");

        FacesMessage msg = null;

        try {
            GeneralUserProfile loanCustomer = selectedLoanObj.loanManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId();
            loanCustomer.setFirstName(firstName);
            loanCustomer.setFirstName(lastName);
            loanCustomer.setFirstName(mobileNo);
            loanCustomer.setFirstName(addressOne);
            loanCustomer.setFirstName(addressTwo);
            loanCustomer.setFirstName(addressThree);
            uniDB.update(loanCustomer);

            if (firstGuarantorObj != null) {
                GeneralUserProfile gup = firstGuarantorObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId();
                gup.setNic(fnic);
                gup.setFirstName(ffirstName);
                gup.setLastName(flastName);
                uniDB.update(gup);
            }

            if (secondGuarantorObj != null) {
                GeneralUserProfile gupTwo = secondGuarantorObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId();
                gupTwo.setNic(snic);
                gupTwo.setFirstName(sfirstName);
                gupTwo.setLastName(slastName);
                uniDB.update(gupTwo);
            }

            if (firstGuarantorUploadFIleOne != null) {
                try (InputStream input = firstGuarantorUploadFIleOne.getInputStream()) {

                    // Set the desired file path where the image should be saved
                    String destination = ffrontNic;

                    // Write the uploaded file to the specified location
                    try (OutputStream output = new FileOutputStream(new File(destination))) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (firstGuarantorUploadFIleTwo != null) {
                try (InputStream input = firstGuarantorUploadFIleTwo.getInputStream()) {

                    // Set the desired file path where the image should be saved
                    String destination = ffrontNic;

                    // Write the uploaded file to the specified location
                    try (OutputStream output = new FileOutputStream(new File(destination))) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (secondGuarantorUploadFIleOne != null) {
                try (InputStream input = firstGuarantorUploadFIleTwo.getInputStream()) {

                    // Set the desired file path where the image should be saved
                    String destination = ffrontNic;

                    // Write the uploaded file to the specified location
                    try (OutputStream output = new FileOutputStream(new File(destination))) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (secondGuarantorUploadFIleTwo != null) {
                try (InputStream input = firstGuarantorUploadFIleTwo.getInputStream()) {

                    // Set the desired file path where the image should be saved
                    String destination = ffrontNic;

                    // Write the uploaded file to the specified location
                    try (OutputStream output = new FileOutputStream(new File(destination))) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success ! ! !", "Loan Details Update is Successfully ");
            FacesContext.getCurrentInstance().addMessage("", msg);

        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Loan Details Update is Failed ");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public boolean checkfirstGurantorAvailable(LoanManager loanManager) {

        List<GurantorManager> gurantorManagersList = uniDB.searchByQuery("SELECT g FROM GurantorManager g WHERE g.loanApplicantId.id='" + loanManager.getLoanApplicantAndGurantorsId().getId() + "'");
        System.out.println("gurantorManagersList.size() < 2 && !gurantorManagersList.isEmpty() " + (gurantorManagersList.size() < 2 && !gurantorManagersList.isEmpty()));
        return gurantorManagersList.size() < 2 && !gurantorManagersList.isEmpty();
    }

    public boolean checkSecondGurantorAvailable(LoanManager loanManager) {

        List<GurantorManager> gurantorManagersList = uniDB.searchByQuery("SELECT g FROM GurantorManager g WHERE g.loanApplicantId.id='" + loanManager.getLoanApplicantAndGurantorsId().getId() + "'");
        System.out.println("gurantorManagersList.size() < 2 && !gurantorManagersList.isEmpty() " + (gurantorManagersList.size() == 2 && gurantorManagersList.size() > 1));
        return gurantorManagersList.size() == 2 && gurantorManagersList.size() > 1;
    }

    public void setSelectedLoan(UsereLoansDetails customer) {

        ffrontNic = "";
        firstBackNic = "";
        sfrontNic = "";
        secondBackNic = "";

        selectedLoanObj = customer;
        firstName = customer.loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getFirstName();
        lastName = customer.loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getLastName();
        nic = customer.loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getNic();
        mobileNo = customer.loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getMobileNo();
        dateOfBirth = new SimpleDateFormat("yyyy/MM/dd").format(customer.loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getDob());
        email = customer.loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getEmail();
        addressOne = customer.loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getAddress1();
        addressTwo = customer.loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getAddress2();
        addressThree = customer.loanManager.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getAddress3();
        loanToken = customer.loanManager.getVerificationToke();

        int loan_applicant_id = customer.loanManager.getLoanApplicantAndGurantorsId().getId();

        List<GurantorManager> gurantorManagersList = uniDB.searchByQuery("SELECT g FROM GurantorManager g WHERE g.loanApplicantId.id='" + loan_applicant_id + "'");
        int i = 0;

//        String basePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\documents\\loan_documents\\student_loan");
        String basePath = ComPath.basePath;
//        String basePath = "F:\\temco-bank-application-2024-07-08\\images";
        // If getRealPath returns null (like in some local dev environments), use absolute fallback for local testing

        if (!gurantorManagersList.isEmpty()) {
            System.out.println("i 1" + i);

            for (GurantorManager gurantorManager : gurantorManagersList) {
                if (i == 0) {
                    firstGuarantorObj = gurantorManager;
                    ffirstName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName();
                    flastName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
                    fnic = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();
                    List<GuarantorDocuments> gurantorDocuments = uniDB.searchByQuery("SELECT g FROM GuarantorDocuments g WHERE g.gurantorManagerId.id='" + gurantorManager.getId() + "'");
                    int x = 0;
                    for (GuarantorDocuments gurantorDocument : gurantorDocuments) {
                        if (x == 0) {
                            ffrontNic = basePath + "/" + selectedLoanObj.loanManager.getVerificationToke() + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
//                            ffrontNic = "F:/temco-bank-application-2024-07-08/nilupul copy/temco_loan_system-1.0/target/temco_loan_system-1.0/documents/loan_documents/student_loan/WKXvsjUCrFanU849qbJIbSdlyBQ3mZu8/2011320000000100/temcoln909d9035-41a9-4ee5-ad97-0c41010fa02520240924091318.png";
                            System.out.println("url 1 " + ffrontNic);
                            x++;
                            firstGuarantorAvailable = true;
                        } else if (x == 1) {
                            firstBackNic = basePath + "/" + selectedLoanObj.loanManager.getVerificationToke() + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
                            System.out.println("url 2 " + firstBackNic);
                            i++;
                            System.out.println("i " + i);
                        }

                    }
                } else {
                    secondGuarantorObj = gurantorManager;
                    sfirstName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName();
                    slastName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
                    snic = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();
                    List<GuarantorDocuments> gurantorDocuments = uniDB.searchByQuery("SELECT g FROM GuarantorDocuments g WHERE g.gurantorManagerId.id='" + gurantorManager.getId() + "'");
                    int x = 0;
                    for (GuarantorDocuments gurantorDocument : gurantorDocuments) {
                        if (x == 0) {
                            sfrontNic = basePath + "/" + selectedLoanObj.loanManager.getVerificationToke() + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
                            System.out.println("url 1 s" + sfrontNic);
                            x++;
                            secondGuaranotrAvailable = true;
                            System.out.println("x " + x);
                        } else if (x == 1) {
                            secondBackNic = basePath + "/" + selectedLoanObj.loanManager.getVerificationToke() + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
                            System.out.println("url 2 s" + secondBackNic);
                            i++;
                            System.out.println("i " + i);
                        }
                    }
                }
            }
        } else {
            System.out.println("no guarantors found for this loan");
        }

    }

    public void LoadUserLoans() {
        System.out.println("LoadUserLoans");
        userLoansArrayList = new ArrayList<>();
        List<GeneralUserProfile> gup_list = uniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.verificationToken='" + securityCode + "' ");
        System.out.println("gup_list " + gup_list.size());
        if (!gup_list.isEmpty()) {
            List<Member1> member_list = uniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gup_list.get(0).getId() + "' ");
            System.out.println("member_list " + member_list.size());
            if (!member_list.isEmpty()) {
                List<LoanApplicantGurantor> loan_applicant_gurantor = uniDB.searchByQuery("SELECT g FROM LoanApplicantGurantor g WHERE g.memberId.id='" + member_list.get(0).getId() + "' ");
                System.out.println("member_list " + loan_applicant_gurantor.size());
                if (!loan_applicant_gurantor.isEmpty()) {
                    gurantorManager = loan_applicant_gurantor.get(0);
                    List<LoanManager> loan_manager = uniDB.searchByQuery("SELECT g FROM LoanManager g WHERE g.loanApplicantAndGurantorsId.id='" + loan_applicant_gurantor.get(0).getId() + "' ");

                    if (!loan_manager.isEmpty()) {
                        System.out.println("loan_manager " + loan_manager.size());
                        List<LoanStatusManager> loan_status_managers = uniDB.searchByQueryLimit("SELECT g FROM LoanStatusManager g WHERE g.loanManagerId.id='" + loan_manager.get(0).getId() + "' ORDER BY g.date DESC ", 1);
                        if (!loan_status_managers.isEmpty()) {
                            System.out.println("loan_status_managers " + loan_status_managers.size());
                            getUserLoansArrayList().add(new UsereLoansDetails(loan_manager.get(0).getReferenceId(), loan_manager.get(0).getLoanCapitalAmount(), loan_status_managers.get(0).getLoanStatusId().getName(), null, loan_manager.get(0)));
                        }
                    }
                }
            }
        }
    }

    public class UsereLoansDetails {

        private String loanId;
        private double loanAmount;
        private String loanStatus;
        private GurantorManager gurantorManagerObj;
        private LoanManager loanManager;

        public UsereLoansDetails(String loanId, double loanAmount, String loanStatus, GurantorManager gurantorManagerObj, LoanManager loanManager) {
            this.loanId = loanId;
            this.loanAmount = loanAmount;
            this.loanStatus = loanStatus;
            this.gurantorManagerObj = gurantorManagerObj;
            this.loanManager = loanManager;
        }

        public String getLoanId() {
            return loanId;
        }

        public void setLoanId(String loanId) {
            this.loanId = loanId;
        }

        public double getLoanAmount() {
            return loanAmount;
        }

        public void setLoanAmount(double loanAmount) {
            this.loanAmount = loanAmount;
        }

        public String getLoanStatus() {
            return loanStatus;
        }

        public void setLoanStatus(String loanStatus) {
            this.loanStatus = loanStatus;
        }

        public GurantorManager getGurantorManagerObj() {
            return gurantorManagerObj;
        }

        public void setGurantorManagerObj(GurantorManager gurantorManagerObj) {
            this.gurantorManagerObj = gurantorManagerObj;
        }

        public LoanManager getLoanManager() {
            return loanManager;
        }

        public void setLoanManager(LoanManager loanManager) {
            this.loanManager = loanManager;
        }

    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public LoanApplicantGurantor getGurantorManager() {
        return gurantorManager;
    }

    public void setGurantorManager(LoanApplicantGurantor gurantorManager) {
        this.gurantorManager = gurantorManager;
    }

    public List<UsereLoansDetails> getUserLoansArrayList() {
        return userLoansArrayList;
    }

    public void setUserLoansArrayList(List<UsereLoansDetails> userLoansArrayList) {
        this.userLoansArrayList = userLoansArrayList;
    }

    public UniDBLocal getUniDB() {
        return uniDB;
    }

    public void setUniDB(UniDBLocal uniDB) {
        this.uniDB = uniDB;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getFfirstName() {
        return ffirstName;
    }

    public void setFfirstName(String ffirstName) {
        this.ffirstName = ffirstName;
    }

    public String getFlastName() {
        return flastName;
    }

    public void setFlastName(String flastName) {
        this.flastName = flastName;
    }

    public String getFnic() {
        return fnic;
    }

    public void setFnic(String fnic) {
        this.fnic = fnic;
    }

    public String getFfrontNic() {
        return ffrontNic;
    }

    public void setFfrontNic(String ffrontNic) {
        this.ffrontNic = ffrontNic;
    }

    public String getFirstBackNic() {
        return firstBackNic;
    }

    public void setFirstBackNic(String firstBackNic) {
        this.firstBackNic = firstBackNic;
    }

    public String getSfirstName() {
        return sfirstName;
    }

    public void setSfirstName(String sfirstName) {
        this.sfirstName = sfirstName;
    }

    public String getSlastName() {
        return slastName;
    }

    public void setSlastName(String slastName) {
        this.slastName = slastName;
    }

    public String getSnic() {
        return snic;
    }

    public void setSnic(String snic) {
        this.snic = snic;
    }

    public String getSfrontNic() {
        return sfrontNic;
    }

    public void setSfrontNic(String sfrontNic) {
        this.sfrontNic = sfrontNic;
    }

    public String getSecondBackNic() {
        return secondBackNic;
    }

    public void setSecondBackNic(String secondBackNic) {
        this.secondBackNic = secondBackNic;
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

    public GurantorManager getFirstGuarantorObj() {
        return firstGuarantorObj;
    }

    public void setFirstGuarantorObj(GurantorManager firstGuarantorObj) {
        this.firstGuarantorObj = firstGuarantorObj;
    }

    public GurantorManager getSecondGuarantorObj() {
        return secondGuarantorObj;
    }

    public void setSecondGuarantorObj(GurantorManager secondGuarantorObj) {
        this.secondGuarantorObj = secondGuarantorObj;
    }

    public UsereLoansDetails getSelectedLoanObj() {
        return selectedLoanObj;
    }

    public void setSelectedLoanObj(UsereLoansDetails selectedLoanObj) {
        this.selectedLoanObj = selectedLoanObj;
    }

    public boolean isFirstGuarantorAvailable() {
        return firstGuarantorAvailable;
    }

    public void setFirstGuarantorAvailable(boolean firstGuarantorAvailable) {
        this.firstGuarantorAvailable = firstGuarantorAvailable;
    }

    public boolean isSecondGuaranotrAvailable() {
        return secondGuaranotrAvailable;
    }

    public void setSecondGuaranotrAvailable(boolean secondGuaranotrAvailable) {
        this.secondGuaranotrAvailable = secondGuaranotrAvailable;
    }

    public String getLoanToken() {
        return loanToken;
    }

    public void setLoanToken(String loanToken) {
        this.loanToken = loanToken;
    }

}
