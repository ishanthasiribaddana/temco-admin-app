/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatefulEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.user.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import lk.exon.temco_loan_system.entity.UniversalUserDocument;

/**
 *
 * @author USER
 */
@Named
@SessionScoped
public class DashboardBean implements Serializable {

    private String securityCode;

    private String url;
    private String urlTwo;
    private String urlThree;

    private String userVerificationToken;
    private String verificationToken;
    private String firstName;
    private String loanSecurityCode;

    private String memberId;

    private String loanName;
    private double requestedAmount;
    private String loanProgression;
    private boolean urlView = true;
    private boolean urlViewForApplyLoan = true;
    private boolean loggedIn;

    private String loanApplicantFirstName;
    private String loanApplicantLastName;
    private String loanApplicantNic;
    private String loanApplicantMobileNo;
    private String loanApplicantDob;
    private String loanApplicantEmail;
    private String loanApplicantAddress01;
    private String loanApplicantAddress02;
    private String loanApplicantAddress03;

    private String firstGuarantorMemberId;
    private String firstGuarantorFirstName;
    private String firstGuarantorLastName;
    private String firstGuarantorNic;
    private String firstGuarantorMobileNo;
    private String firstGuarantorAddress01;
    private String firstGuarantorAddress02;
    private String firstGuarantorAddress03;
    private String ffrontNic;
    private String firstBackNic;

    private String secondGuarantorMemberId;
    private String secondGuarantorFirstName;
    private String secondGuarantorLastName;
    private String secondGuarantorNic;
    private String secondGuarantorMobileNo;
    private String secondGuarantorAddress01;
    private String secondGuarantorAddress02;
    private String secondGuarantorAddress03;
    private String sfrontNic;
    private String secondBackNic;

    private static final long INACTIVITY_LIMIT = 3600000;

    @EJB
    UniDBLocal uniDB;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        securityCode = params.get("en");

        if (securityCode != null) {
            System.out.println("in if");
            getUserDetailsFromGeneralUserProfile(securityCode);
        } else {
            System.out.println("in else");
        }

        loadCurrentLoan();
        userVerificationToken = securityCode;
        ExternalContext externalContext = facesContext.getExternalContext();
        url = externalContext.getRequestContextPath() + "/user/main/tasks/loan.xhtml?en=" + securityCode + "&" + "lid=" + loanSecurityCode;
        urlTwo = externalContext.getRequestContextPath() + "/user/main/tasks/document-submission.xhtml?en=" + securityCode + "&" + "lid=" + loanSecurityCode;
        urlThree = externalContext.getRequestContextPath() + "/tasks/loan-calculator.xhtml?en=" + verificationToken;
    }

    public void loadCurrentLoan() {
        System.out.println("LoadUserLoans");
        String basePath = ComPath.basePath;
        SimpleDateFormat smp = new SimpleDateFormat("yyyy/MM/dd");

        List<GeneralUserProfile> gup_list = uniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.verificationToken='" + securityCode + "' ");
        System.out.println("gup_list " + gup_list.size());
        if (!gup_list.isEmpty()) {
            List<Member1> member_list = uniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gup_list.get(0).getId() + "' ");
            System.out.println("member_list " + member_list.size());
            if (!member_list.isEmpty()) {
                List<LoanApplicantGurantor> loan_applicant_gurantor = uniDB.searchByQuery("SELECT g FROM LoanApplicantGurantor g WHERE g.memberId.id='" + member_list.get(0).getId() + "' ");
                System.out.println("loan_applicant_gurantor " + loan_applicant_gurantor.size());
                if (!loan_applicant_gurantor.isEmpty()) {
                    List<LoanManager> loan_manager = uniDB.searchByQueryLimit("SELECT g FROM LoanManager g WHERE g.loanApplicantAndGurantorsId.id='" + loan_applicant_gurantor.get(0).getId() + "' ORDER BY g.date DESC ", 1);

                    if (!loan_manager.isEmpty()) {
                        urlViewForApplyLoan = false;
                        System.out.println("loan_manager " + loan_manager.size());
                        System.out.println("Loan id " + loan_manager.get(0).getId());
                        List<LoanStatusManager> loan_status_managers = uniDB.searchByQueryLimit("SELECT g FROM LoanStatusManager g WHERE g.loanManagerId.id='" + loan_manager.get(0).getId() + "' ORDER BY g.date DESC ", 1);
                        System.out.println("loan_status_managers " + loan_status_managers.size());
                        if (!loan_status_managers.isEmpty()) {

                            if (loan_status_managers.get(0).getLoanStatusId().getId() > 1) {
                                urlView = false;
                            }

                            System.out.println("loan_status_managers " + loan_status_managers.size());
                            LoanManager loanObj = loan_manager.get(0);

                            loanName = loanObj.getRepaymentPeriodId().getLoanid().getLoanBrand();
                            requestedAmount = loanObj.getLoanCapitalAmount();
                            loanProgression = loan_status_managers.get(0).getLoanStatusId().getName();

                            memberId = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo();
                            loanApplicantFirstName = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName();
                            loanApplicantLastName = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
                            loanApplicantNic = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();
                            loanApplicantMobileNo = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getMobileNo();
                            loanApplicantDob = smp.format(loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getDob());
                            loanApplicantEmail = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getEmail();
                            loanApplicantAddress01 = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getAddress1();
                            loanApplicantAddress02 = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getAddress2();
                            loanApplicantAddress03 = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getAddress3();

                            int gup_id = loanObj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getId();

                            List<GurantorManager> gurantorManagersList = uniDB.searchByQuery("SELECT g FROM GurantorManager g WHERE g.loanApplicantId.id='" + loanObj.getLoanApplicantAndGurantorsId().getId() + "'");
                            int i = 0;

                            if (!gurantorManagersList.isEmpty()) {
                                System.out.println("i 1 " + i);
                                String loanManagetToken = loanObj.getVerificationToke();
                                for (GurantorManager gurantorManager : gurantorManagersList) {
                                    System.out.println("gurantorManager gup id " + gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getId());
                                    System.out.println("gurantorManager name " + gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName());
                                    System.out.println("gurantorManager is active " + gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() + " & " + (gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() == 1));
                                    if (i == 0 && gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() == 1) {

                                        firstGuarantorMemberId = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo();
                                        firstGuarantorFirstName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName();
                                        firstGuarantorLastName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
                                        firstGuarantorNic = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();
                                        firstGuarantorMobileNo = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getMobileNo();

                                        List<GuarantorDocuments> gurantorDocuments = uniDB.searchByQuery("SELECT g FROM GuarantorDocuments g WHERE g.gurantorManagerId.id='" + gurantorManager.getId() + "'");

                                        if (!gurantorDocuments.isEmpty()) {
                                            int x = 0;
                                            for (GuarantorDocuments gurantorDocument : gurantorDocuments) {
                                                if (x == 0) {
                                                    ffrontNic = basePath + "/" + loanManagetToken + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
//                            ffrontNic = "F:/temco-bank-application-2024-07-08/nilupul copy/temco_loan_system-1.0/target/temco_loan_system-1.0/documents/loan_documents/student_loan/WKXvsjUCrFanU849qbJIbSdlyBQ3mZu8/2011320000000100/temcoln909d9035-41a9-4ee5-ad97-0c41010fa02520240924091318.png";
                                                    System.out.println("url 1 " + ffrontNic);
                                                    x++;
                                                } else if (x == 1) {
                                                    firstBackNic = basePath + "/" + loanManagetToken + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
                                                    System.out.println("url 2 " + firstBackNic);
                                                    i++;
                                                    System.out.println("i " + i);
                                                }

                                            }
                                        } else {
                                            System.out.println("first guarantor documents are empty.checking in universal documents");
                                            String gurantorMemberNo = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo();
                                            System.out.println("loan applicant gup id " + gup_id);
                                            List<UniversalUserDocument> uud = uniDB.searchByQuery("SELECT g FROM UniversalUserDocument g WHERE g.generalUserProfileId.id='" + gup_id + "' AND g.isActive='1'");
                                            System.out.println("uud is empty " + uud.isEmpty());
                                            System.out.println("uud documents size " + uud.size());
                                            if (!uud.isEmpty()) {

                                                ffrontNic = basePath + "/" + loanManagetToken + "/" + gurantorMemberNo + "/" + uud.get(0).getFileUrl();
//                            ffrontNic = "F:/temco-bank-application-2024-07-08/nilupul copy/temco_loan_system-1.0/target/temco_loan_system-1.0/documents/loan_documents/student_loan/WKXvsjUCrFanU849qbJIbSdlyBQ3mZu8/2011320000000100/temcoln909d9035-41a9-4ee5-ad97-0c41010fa02520240924091318.png";
                                                System.out.println("UniversalUserDocument url 1 " + ffrontNic);
                                                firstBackNic = basePath + "/" + loanManagetToken + "/" + gurantorMemberNo + "/" + uud.get(1).getFileUrl();
                                                System.out.println("UniversalUserDocument url 2 " + firstBackNic);
                                                i++;
                                                System.out.println("i " + i);
                                            }
                                        }
                                    } else {
                                        if (gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() == 1) {
                                            System.out.println("second guarantor documents are empty.checking in universal documents");

                                            secondGuarantorMemberId = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo();
                                            secondGuarantorFirstName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName();
                                            secondGuarantorLastName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
                                            secondGuarantorNic = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();
                                            secondGuarantorMobileNo = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getMobileNo();

                                            List<GuarantorDocuments> gurantorDocuments = uniDB.searchByQuery("SELECT g FROM GuarantorDocuments g WHERE g.gurantorManagerId.id='" + gurantorManager.getId() + "'");
                                            System.out.println("gurantorDocuments " + gurantorDocuments.isEmpty());
                                            if (!gurantorDocuments.isEmpty()) {
                                                int x = 0;
                                                for (GuarantorDocuments gurantorDocument : gurantorDocuments) {
                                                    if (x == 0) {
                                                        sfrontNic = basePath + "/" + loanManagetToken + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
                                                        System.out.println("UniversalUserDocument url 1 s" + sfrontNic);
                                                        x++;
                                                        System.out.println("x " + x);
                                                    } else if (x == 1) {
                                                        secondBackNic = basePath + "/" + loanManagetToken + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
                                                        System.out.println("UniversalUserDocument url 2 s" + secondBackNic);
                                                        i++;
                                                        System.out.println("i " + i);
                                                    }
                                                }
                                            } else {
                                                String gurantorMemberNo = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo();
                                                List<UniversalUserDocument> uud = uniDB.searchByQuery("SELECT g FROM UniversalUserDocument g WHERE g.generalUserProfileId.id='" + gup_id + "'AND g.isActive='1'");
                                                System.out.println("uud documents size " + uud.size());
                                                if (!uud.isEmpty()) {
//                                                    sfrontNic = basePath + "/" + loanManagetToken + "/" + gurantorMemberNo + "/" + uud.get(2).getFileUrl();
//                            ffrontNic = "F:/temco-bank-application-2024-07-08/nilupul copy/temco_loan_system-1.0/target/temco_loan_system-1.0/documents/loan_documents/student_loan/WKXvsjUCrFanU849qbJIbSdlyBQ3mZu8/2011320000000100/temcoln909d9035-41a9-4ee5-ad97-0c41010fa02520240924091318.png";
                                                    System.out.println("url 1 " + ffrontNic);

//                                                    secondBackNic = basePath + "/" + loanManagetToken + "/" + gurantorMemberNo + "/" + uud.get(3).getFileUrl();
                                                    System.out.println("url 2 " + firstBackNic);
                                                    i++;
                                                    System.out.println("i " + i);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            loanSecurityCode = loan_manager.get(0).getVerificationToke();
                        }
                    } else {
                        urlViewForApplyLoan = true;
                    }
                }
            }
        }
    }

    private void getUserDetailsFromGeneralUserProfile(String securityCode) {

        List<GeneralUserProfile> generalUserProfile = uniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.verificationToken ='" + securityCode + "' ");
        if (!generalUserProfile.isEmpty()) {
            verificationToken = generalUserProfile.get(0).getVerificationToken();
            firstName = generalUserProfile.get(0).getFirstName();
            System.out.println("name " + generalUserProfile.get(0).getFirstName());
        }
    }

    public String getUserVerificationToken() {
        return userVerificationToken;
    }

    public void setUserVerificationToken(String userVerificationToken) {
        this.userVerificationToken = userVerificationToken;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getLoanProgression() {
        return loanProgression;
    }

    public void setLoanProgression(String loanProgression) {
        this.loanProgression = loanProgression;
    }

    public String getUrlTwo() {
        return urlTwo;
    }

    public void setUrlTwo(String urlTwo) {
        this.urlTwo = urlTwo;
    }

    public boolean isUrlView() {
        return urlView;
    }

    public void setUrlView(boolean urlView) {
        this.urlView = urlView;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getLoanSecurityCode() {
        return loanSecurityCode;
    }

    public void setLoanSecurityCode(String loanSecurityCode) {
        this.loanSecurityCode = loanSecurityCode;
    }

    public String getLoanApplicantFirstName() {
        return loanApplicantFirstName;
    }

    public void setLoanApplicantFirstName(String loanApplicantFirstName) {
        this.loanApplicantFirstName = loanApplicantFirstName;
    }

    public String getLoanApplicantLastName() {
        return loanApplicantLastName;
    }

    public void setLoanApplicantLastName(String loanApplicantLastName) {
        this.loanApplicantLastName = loanApplicantLastName;
    }

    public String getLoanApplicantNic() {
        return loanApplicantNic;
    }

    public void setLoanApplicantNic(String loanApplicantNic) {
        this.loanApplicantNic = loanApplicantNic;
    }

    public String getLoanApplicantMobileNo() {
        return loanApplicantMobileNo;
    }

    public void setLoanApplicantMobileNo(String loanApplicantMobileNo) {
        this.loanApplicantMobileNo = loanApplicantMobileNo;
    }

    public String getLoanApplicantDob() {
        return loanApplicantDob;
    }

    public void setLoanApplicantDob(String loanApplicantDob) {
        this.loanApplicantDob = loanApplicantDob;
    }

    public String getLoanApplicantEmail() {
        return loanApplicantEmail;
    }

    public void setLoanApplicantEmail(String loanApplicantEmail) {
        this.loanApplicantEmail = loanApplicantEmail;
    }

    public String getLoanApplicantAddress01() {
        return loanApplicantAddress01;
    }

    public void setLoanApplicantAddress01(String loanApplicantAddress01) {
        this.loanApplicantAddress01 = loanApplicantAddress01;
    }

    public String getLoanApplicantAddress02() {
        return loanApplicantAddress02;
    }

    public void setLoanApplicantAddress02(String loanApplicantAddress02) {
        this.loanApplicantAddress02 = loanApplicantAddress02;
    }

    public String getLoanApplicantAddress03() {
        return loanApplicantAddress03;
    }

    public void setLoanApplicantAddress03(String loanApplicantAddress03) {
        this.loanApplicantAddress03 = loanApplicantAddress03;
    }

    public String getFirstGuarantorFirstName() {
        return firstGuarantorFirstName;
    }

    public void setFirstGuarantorFirstName(String firstGuarantorFirstName) {
        this.firstGuarantorFirstName = firstGuarantorFirstName;
    }

    public String getFirstGuarantorLastName() {
        return firstGuarantorLastName;
    }

    public void setFirstGuarantorLastName(String firstGuarantorLastName) {
        this.firstGuarantorLastName = firstGuarantorLastName;
    }

    public String getFirstGuarantorNic() {
        return firstGuarantorNic;
    }

    public void setFirstGuarantorNic(String firstGuarantorNic) {
        this.firstGuarantorNic = firstGuarantorNic;
    }

    public String getFirstGuarantorMobileNo() {
        return firstGuarantorMobileNo;
    }

    public void setFirstGuarantorMobileNo(String firstGuarantorMobileNo) {
        this.firstGuarantorMobileNo = firstGuarantorMobileNo;
    }

    public String getFirstGuarantorAddress01() {
        return firstGuarantorAddress01;
    }

    public void setFirstGuarantorAddress01(String firstGuarantorAddress01) {
        this.firstGuarantorAddress01 = firstGuarantorAddress01;
    }

    public String getFirstGuarantorAddress02() {
        return firstGuarantorAddress02;
    }

    public void setFirstGuarantorAddress02(String firstGuarantorAddress02) {
        this.firstGuarantorAddress02 = firstGuarantorAddress02;
    }

    public String getFirstGuarantorAddress03() {
        return firstGuarantorAddress03;
    }

    public void setFirstGuarantorAddress03(String firstGuarantorAddress03) {
        this.firstGuarantorAddress03 = firstGuarantorAddress03;
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

    public String getSecondGuarantorMobileNo() {
        return secondGuarantorMobileNo;
    }

    public void setSecondGuarantorMobileNo(String secondGuarantorMobileNo) {
        this.secondGuarantorMobileNo = secondGuarantorMobileNo;
    }

    public String getSecondGuarantorAddress01() {
        return secondGuarantorAddress01;
    }

    public void setSecondGuarantorAddress01(String secondGuarantorAddress01) {
        this.secondGuarantorAddress01 = secondGuarantorAddress01;
    }

    public String getSecondGuarantorAddress02() {
        return secondGuarantorAddress02;
    }

    public void setSecondGuarantorAddress02(String secondGuarantorAddress02) {
        this.secondGuarantorAddress02 = secondGuarantorAddress02;
    }

    public String getSecondGuarantorAddress03() {
        return secondGuarantorAddress03;
    }

    public void setSecondGuarantorAddress03(String secondGuarantorAddress03) {
        this.secondGuarantorAddress03 = secondGuarantorAddress03;
    }

    public UniDBLocal getUniDB() {
        return uniDB;
    }

    public void setUniDB(UniDBLocal uniDB) {
        this.uniDB = uniDB;
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

    public String getFirstGuarantorMemberId() {
        return firstGuarantorMemberId;
    }

    public void setFirstGuarantorMemberId(String firstGuarantorMemberId) {
        this.firstGuarantorMemberId = firstGuarantorMemberId;
    }

    public String getSecondGuarantorMemberId() {
        return secondGuarantorMemberId;
    }

    public void setSecondGuarantorMemberId(String secondGuarantorMemberId) {
        this.secondGuarantorMemberId = secondGuarantorMemberId;
    }

    public boolean isUrlViewForApplyLoan() {
        return urlViewForApplyLoan;
    }

    public void setUrlViewForApplyLoan(boolean urlViewForApplyLoan) {
        this.urlViewForApplyLoan = urlViewForApplyLoan;
    }

    public String getUrlThree() {
        return urlThree;
    }

    public void setUrlThree(String urlThree) {
        this.urlThree = urlThree;
    }

}
