/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service.university_payment_loan;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lk.exon.temco.filteration.Filteration;
import lk.exon.temco.templates.LoanRequestPortalEmail;
import lk.exon.temco.tools.NewMailSender;
import lk.exon.temco_loan_system.common.ComLib;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.CustomerResponseHistory;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.entity.InterestManager;
import lk.exon.temco_loan_system.entity.Loan;
import lk.exon.temco_loan_system.entity.LoanApplicantGurantor;
import lk.exon.temco_loan_system.entity.LoanApplicantHasBranch;
import lk.exon.temco_loan_system.entity.LoanCustomer;
import lk.exon.temco_loan_system.entity.LoanInterestRate;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.LoanStatus;
import lk.exon.temco_loan_system.entity.LoanStatusManager;
import lk.exon.temco_loan_system.entity.MaterializedStudentLoanEligibleStudentTable;
import lk.exon.temco_loan_system.entity.Member1;
import lk.exon.temco_loan_system.entity.MemberBankAccounts;
import lk.exon.temco_loan_system.entity.OfferManager;
import lk.exon.temco_loan_system.entity.OrganizationBranches;
import lk.exon.temco_loan_system.entity.Penalty;
import lk.exon.temco_loan_system.entity.RepaymentPeriod;
import lk.exon.temco_loan_system.entity.ResponseStatus;

/**
 *
 * @author USER
 */
@Named
@SessionScoped
public class LoanApplication implements Serializable {

    private List<LoanCalculatorRecords> loanStrucure = new ArrayList<>();

    private double dueCourseFee;

    double correctMonthlyInstallment = 0.00;
    boolean correctMonthlyInstallmentBoolean = false;
    private double expectedLoanAmount = 0.00;
    private double monthlyinstallement = 0.00;
    private String repayementPeriod = "5";
    private double grossIncome;
    private double interestRate = 0.06;
    private int actualLoanTenture;
    private String actualMonthlyInstallement = "0.00";
    int actualMonth = 0;
    private boolean loanCalculated = false;
    private boolean displayExpectedLoanAmount = false;
    private boolean displaymonthlyInstallment = false;
    private boolean displayLoanTenture = false;
    private boolean displayLoanAmount = false;

    private boolean checkbox;

    private List<SelectItem> repaymentPeriodList = new ArrayList<>();

    private String error_message;

    GeneralUserProfile gup = null;

    String userNic = "";

    private final Double usdToLkr = 305.00;
    private final Double gbpToLkr = 402.00;

    @Inject
    PersonalDetailsRegistration personalRegistrationForm;

    @EJB
    private UniDBLocal UniDB;

    private ComLib ComLib;

    @PostConstruct
    public void init() {
        System.out.println("in it");
        intializeMethod();

    }

    private void intializeMethod() {
        System.out.println("intializeMethod");

        if (personalRegistrationForm.getNic() != null) {
            System.out.println("LoanRequestForm.getNic() " + personalRegistrationForm.getNic());
            System.out.println("loan calculator initialize if");
            updateOfferManager(personalRegistrationForm.getNic());
            System.out.println("A1");
            getUserDetailsFromNIC(personalRegistrationForm.getNic());
            System.out.println("A2");
            System.out.println("personalRegistrationForm.getMaterializedObj() " + (personalRegistrationForm.getMaterializedObj() != null));
            calculateTotalLoanAmount(personalRegistrationForm.getMaterializedObj());
            calulateLoan();
        } else {
            System.out.println("initialize else");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

            String loanIdPara = params.get("en");

            userNic = getUserDetailsFromGeneralUserProfile(loanIdPara);
            System.out.println("userNic " + userNic);
            updateOfferManager(userNic);

            List<MaterializedStudentLoanEligibleStudentTable> mt = UniDB.searchByQuery("SELECT g FROM MaterializedStudentLoanEligibleStudentTable g WHERE g.nic='" + userNic + "' ");

            if (!mt.isEmpty()) {
                calculateTotalLoanAmount(personalRegistrationForm.getMaterializedObj());
            }

        }

        repaymentPeriod();
    }

    private String getUserDetailsFromNIC(String nic) {
        System.out.println("getUserDetailsFromGeneralUserProfile(String securityCode) " + nic);
        List<GeneralUserProfile> generalUserProfile = UniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic ='" + nic + "' ");
        System.out.println("getUserDetailsFromGeneralUserProfile(String securityCode) " + !generalUserProfile.isEmpty());
        if (!generalUserProfile.isEmpty()) {
            gup = generalUserProfile.get(0);
            return generalUserProfile.get(0).getNic();
        }
        return "";
    }

    private String getUserDetailsFromGeneralUserProfile(String securityCode) {
        System.out.println("getUserDetailsFromGeneralUserProfile(String securityCode) " + securityCode);
        List<GeneralUserProfile> generalUserProfile = UniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.verificationToken ='" + securityCode + "' ");
        System.out.println("getUserDetailsFromGeneralUserProfile(String securityCode) " + !generalUserProfile.isEmpty());
        if (!generalUserProfile.isEmpty()) {
            gup = generalUserProfile.get(0);
            return generalUserProfile.get(0).getNic();
        }
        return "";
    }

    public void updateOfferManager(String nic) {
        Date date = new Date();
        System.out.println("updateOfferManager" + nic);
        List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + nic + "'");
        if (!loanCustomer.isEmpty()) {
            System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
            List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
            System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
            if (!offerManager.isEmpty()) {
                List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='7'");
                if (crhList.isEmpty()) {
                    CustomerResponseHistory crh = new CustomerResponseHistory();
                    crh.setDate(date);
                    crh.setOfferManagerId(offerManager.get(0));
                    crh.setResponseStatusId((ResponseStatus) UniDB.find(7, ResponseStatus.class));
                    UniDB.create(crh);
                }
            }
        }
    }

    public void saveLoanDetails() {
        FacesMessage msg;

        if ((grossIncome != 0) && (monthlyinstallement < grossIncome)) {
            System.out.println("employement type selected");
            if (checkbox) {
                try {
                    Date date = new Date();

                    Member1 member = null;
                    if (personalRegistrationForm.getMember() != null) {
                        System.out.println("A");
                        member = personalRegistrationForm.getMember();
                    } else {
                        System.out.println("B else");
                        System.out.println("gup.getId " + gup.getId());
                        List<Member1> results = UniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gup.getId() + "' ");
                        if (results != null && !results.isEmpty()) {
                            member = (Member1) results.get(0);
                        }
                    }

                    int branchId = 0;

                    MemberBankAccounts memberBankAccounts = null;

                    if (personalRegistrationForm.getMemberBankAccounts() != null) {
                        memberBankAccounts = personalRegistrationForm.getMemberBankAccounts();
                    } else {
                        System.out.println("gup.getId " + gup.getId());
                        List<MemberBankAccounts> results = UniDB.searchByQuery("SELECT g FROM MemberBankAccounts g WHERE g.memberId.id='" + member.getId() + "' ");
                        if (results != null && !results.isEmpty()) {
                            memberBankAccounts = (MemberBankAccounts) results.get(0);
                        }
                    }

                    LoanApplicantGurantor loanApplicantGurantor = new LoanApplicantGurantor();
                    loanApplicantGurantor.setDate(date);
                    loanApplicantGurantor.setMemberId(member);
                    UniDB.create(loanApplicantGurantor);
//                                                                            String loanid = generateRefernceId(member.getMembershipNo());

                    LoanManager newLoan = new LoanManager();
                    newLoan.setReferenceId(generateReferenceId());
                    newLoan.setLoanCapitalAmount(expectedLoanAmount);
                    newLoan.setMonthlyInstallement(Double.parseDouble(actualMonthlyInstallement));
                    newLoan.setDate(date);
                    newLoan.setRepaymentPeriodId((RepaymentPeriod) UniDB.find(Integer.parseInt(repayementPeriod), RepaymentPeriod.class));
                    newLoan.setLoanApplicantAndGurantorsId(loanApplicantGurantor);
                    newLoan.setPenaltyId((Penalty) UniDB.find(1, Penalty.class));
                    newLoan.setMemberBankAccountsId(memberBankAccounts);

//                                    String verification_token = Security.encrypt(loanid);
                    String verification_token = Filteration.getFilteredSHA256HashedPassword(System.currentTimeMillis() + "" + member.getId());
                    newLoan.setVerificationToke(verification_token);
                    UniDB.create(newLoan);

                    System.out.println("branch id a" + branchId);

                    if (branchId == 0) {
                        if (personalRegistrationForm.getBranchId() != 0) {
                            branchId = personalRegistrationForm.getBranchId();
                        } else {
                            System.out.println("gup.getId " + gup.getId());
                            System.out.println("gup.getNic() " + gup.getNic());
                            List<MaterializedStudentLoanEligibleStudentTable> mt = UniDB.searchByQuery("SELECT g FROM MaterializedStudentLoanEligibleStudentTable g WHERE g.nic='" + gup.getNic() + "' ");
                            System.out.println("mt size " + mt.size());
                            List<OrganizationBranches> orgList = UniDB.searchByQuery("SELECT g FROM OrganizationBranches g WHERE g.name LIKE '%" + mt.get(0).getBranchName() + "%'");
                            if (orgList.size() > 0) {
                                branchId = orgList.get(0).getId();
                                System.out.println("branch id " + branchId);
                            }
                        }
                        System.out.println("branch id b" + branchId);
                    }

                    LoanApplicantHasBranch loanApplicantHasBranch = new LoanApplicantHasBranch();
                    loanApplicantHasBranch.setLoanApplicantGurantorId(loanApplicantGurantor);
                    loanApplicantHasBranch.setLoanManagerId(newLoan);
                    System.out.println("branch id c" + branchId);
                    loanApplicantHasBranch.setOrganizationBranchesId((OrganizationBranches) UniDB.find(branchId, OrganizationBranches.class));
                    UniDB.create(loanApplicantHasBranch);

                    LoanStatusManager loanStatusManager = new LoanStatusManager();
                    loanStatusManager.setDate(date);
                    loanStatusManager.setLoanStatusId((LoanStatus) UniDB.find(1, LoanStatus.class));
                    loanStatusManager.setLoanManagerId(newLoan);
                    UniDB.create(loanStatusManager);

                    InterestManager im = new InterestManager();
                    im.setLoanid((Loan) UniDB.find(2, Loan.class));
                    im.setLoanInterestRateId((LoanInterestRate) UniDB.find(14, LoanInterestRate.class));
                    im.setLoanManagerId(newLoan);
                    UniDB.create(im);

                    Date dateTwo = new Date();
                    System.out.println("updateOfferManager");
                    List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + gup.getNic() + "'");
                    if (!loanCustomer.isEmpty()) {
                        System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
                        List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
                        System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
                        if (!offerManager.isEmpty()) {
                            List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='8'");
                            if (crhList.isEmpty()) {
                                CustomerResponseHistory crh = new CustomerResponseHistory();
                                crh.setDate(dateTwo);
                                crh.setOfferManagerId(offerManager.get(0));
                                crh.setResponseStatusId((ResponseStatus) UniDB.find(8, ResponseStatus.class));
                                UniDB.create(crh);
                            }
                        }
                    }

                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success ! ! !", "Details Saved Successful.Please check your email");
                    FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                    FacesContext.getCurrentInstance().addMessage("", msg);

                    sendPortalEmail(member.getGeneralUserProfileId().getFirstName() + " " + member.getGeneralUserProfileId().getLastName(), member.getGeneralUserProfileId().getEmail());
                    System.out.println("saved successfull");
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    ExternalContext externalContext = facesContext.getExternalContext();
                    externalContext.redirect(externalContext.getRequestContextPath() + "/view/details-submission-success.xhtml");
                    facesContext.responseComplete();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please check the checkbox that Agreeing terms and conditions");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Guarantors Gross Income", "Guarantors income can not be 0.00 and need to be larger than entered monthly installment");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public String generateReferenceId() {
        String datePart = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String randomPart = String.format("%04d", new Random().nextInt(10000));

        return "TB-" + datePart + "-" + randomPart;
    }

    public void calulateLoan() {

        FacesMessage msg;
        loanStrucure.clear();

        List<RepaymentPeriod> repaymentPeriods = UniDB.searchByQuery(
                "SELECT r FROM RepaymentPeriod r WHERE r.id = '" + repayementPeriod + "'");

        if (repaymentPeriods.isEmpty()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Repayment Period!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        int tenureMonths = Integer.parseInt(repaymentPeriods.get(0).getPeriod());

        /* -----------------------------
     * FLAT INTEREST CALCULATION
     * ----------------------------- */
        double totalInterest = expectedLoanAmount * interestRate * tenureMonths / 12;
        double monthlyInterest = totalInterest / tenureMonths;
        double monthlyPrincipal = expectedLoanAmount / tenureMonths;
        double monthlyInstallment = monthlyPrincipal + monthlyInterest;

        actualMonthlyInstallement = new DecimalFormat("0.00").format(monthlyInstallment);

        /* -----------------------------
     * FIRST INSTALLMENT DATE
     * ----------------------------- */
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date firstDueDate = cal.getTime();

        double openingBalance = expectedLoanAmount;
        double totalPaid = 0;
        double totalInterestPaid = 0;

        for (int month = 1; month <= tenureMonths; month++) {

            Calendar installmentCal = Calendar.getInstance();
            installmentCal.setTime(firstDueDate);
            installmentCal.add(Calendar.MONTH, month - 1);

            Date installmentDate = installmentCal.getTime();

            totalPaid += monthlyInstallment;
            totalInterestPaid += monthlyInterest;

            loanStrucure.add(new LoanCalculatorRecords(
                    month,
                    ComLib.getDate(installmentDate),
                    openingBalance,
                    monthlyPrincipal,
                    monthlyInterest,
                    totalInterestPaid,
                    monthlyInstallment,
                    monthlyPrincipal * month,
                    totalPaid
            ));

            openingBalance -= monthlyPrincipal;
        }
    }

    public double pmt(double rate_per_period, double number_of_payments, double present_value, double future_value, int type) {

        double val = 0.0;
        if (rate_per_period != 0.0) {
            // Interest rate exists
            double q = Math.pow(1 + rate_per_period, number_of_payments);
            val = -(rate_per_period * (future_value + (q * present_value))) / ((-1 + q) * (1 + rate_per_period * (type)));

        } else if (number_of_payments != 0.0) {
            // No interest rate, but number of payments exists
            val = -(future_value + present_value) / number_of_payments;
        }
        return val;

    }

    public void sendPortalEmail(String name, String email) {

        String email_content = new LoanRequestPortalEmail().PortalCreatedEmail(name);

        boolean b = new NewMailSender().sendM(email, "Welcome to Your Loan Request Portal", email_content);
//        boolean b = new NewMailSender().sendM("tryabeywardane@gmail.com", "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new OfferInformEmailTemplateOne().emailTemplate(studentLoanExpecitngStudentList.get(i).studentName, studentLoanExpecitngStudentList.get(i).verificationToken));

        if (b) {
            System.out.println("Email send successfull");
        } else {
            System.out.println("Email send Failed");
        }

    }

    public void setActualLoanDetails() {
        actualLoanTenture = actualMonth;
        correctMonthlyInstallmentBoolean = false;
        loanCalculated = true;

    }

    public boolean calculateMonthlyInstallementIsLessThanGuarantorsIncome() {
        System.out.println("grossIncome " + grossIncome);
        System.out.println("monthlyinstallement " + monthlyinstallement);
        double guarantorInstallemt = (grossIncome / 100) * 60;
        System.out.println("guarantorInstallemt " + guarantorInstallemt);
        return monthlyinstallement <= guarantorInstallemt;

    }

    public void repaymentPeriod() {
        getRepaymentPeriodList().clear();
        List<RepaymentPeriod> repaymentPeriods = UniDB.searchByQuery("SELECT g FROM RepaymentPeriod g WHERE g.loanid.id='2' AND g.isActive='1'");
        getRepaymentPeriodList().add(new SelectItem("", "Select"));
        for (RepaymentPeriod repaymentPeriodObj : repaymentPeriods) {
            getRepaymentPeriodList().add(new SelectItem(repaymentPeriodObj.getId().toString(), repaymentPeriodObj.getPeriod()));
        }
    }

//    this method calculate total Loan amount by converting international course dues to LKE and adding Service charge
    private void calculateTotalLoanAmount(List<MaterializedStudentLoanEligibleStudentTable> materializedObj) {
        try {
            System.out.println("A");
            String internationalAwardingBodyDiplomaCurrency = materializedObj.get(0).getInternationalAwardingBodyDiplomaCurrency();
            Double internationalAwardingBodyDiplomaDue = materializedObj.get(0).getInternationalAwardingBodyDiplomaDue();
            System.out.println("internationalAwardingBodyDiplomaDue " + internationalAwardingBodyDiplomaDue);

            String internationalAwardingBodyHigherDiplomaCurrency = materializedObj.get(0).getInternationalAwardingBodyHigherDiplomaCurrency();
            Double internationalAwardingBodyHigherDiplomaDue = materializedObj.get(0).getInternationalAwardingBodyHigherDiplomaDue();
            System.out.println("internationalAwardingBodyHigherDiplomaDue " + internationalAwardingBodyHigherDiplomaDue);
            String internationalUniversityCurrency = materializedObj.get(0).getInternationalUniversityCurrency();

            Double internationalUniversityDue = materializedObj.get(0).getInternationalUniversityDue();
            System.out.println("internationalUniversityDue " + internationalUniversityDue);

//            Double serviceChargesPercentage = materializedObj.get(0).getServiceChargesPresentage();
            Double serviceChargesPercentage = 0.0;
            System.out.println("serviceChargesPercentage " + serviceChargesPercentage);
            Double diplomaValue = 0.00;
            Double higherDiplomaValue = 0.00;
            Double universityPayment = 0.00;

            if (internationalAwardingBodyDiplomaCurrency != null && internationalAwardingBodyDiplomaCurrency.equals("GBP")) {

                diplomaValue = internationalAwardingBodyDiplomaDue * gbpToLkr;
                System.out.println("diplomaValue gbpToLkr " + diplomaValue);
            } else if (internationalAwardingBodyDiplomaCurrency != null && internationalAwardingBodyDiplomaCurrency.equals("USD")) {

                diplomaValue = internationalAwardingBodyDiplomaDue * usdToLkr;
                System.out.println("diplomaValue usdToLkr " + diplomaValue);

            } else if (internationalAwardingBodyDiplomaCurrency != null && internationalAwardingBodyDiplomaCurrency.equals("LKR")) {

                diplomaValue = internationalAwardingBodyDiplomaDue;
                System.out.println("diplomaValue Lkr " + diplomaValue);
            }

            if (internationalAwardingBodyHigherDiplomaCurrency != null && internationalAwardingBodyHigherDiplomaCurrency.equals("GBP")) {

                higherDiplomaValue = internationalAwardingBodyHigherDiplomaDue * gbpToLkr;
                System.out.println("higherDiplomaValue Lkr " + higherDiplomaValue);
            } else if (internationalAwardingBodyHigherDiplomaCurrency != null && internationalAwardingBodyHigherDiplomaCurrency.equals("USD")) {

                higherDiplomaValue = internationalAwardingBodyHigherDiplomaDue * usdToLkr;
                System.out.println("higherDiplomaValue Lkr " + higherDiplomaValue);
            } else if (internationalAwardingBodyHigherDiplomaCurrency != null && internationalAwardingBodyHigherDiplomaCurrency.equals("LKR")) {

                higherDiplomaValue = internationalAwardingBodyHigherDiplomaDue;
                System.out.println("higherDiplomaValue Lkr " + higherDiplomaValue);
            }

            if (internationalUniversityCurrency != null && internationalUniversityCurrency.equals("GBP")) {

                universityPayment = internationalUniversityDue * gbpToLkr;
                System.out.println("universityPayment Lkr " + universityPayment);
            } else if (internationalUniversityCurrency != null && internationalUniversityCurrency.equals("USD")) {

                universityPayment = internationalUniversityDue * usdToLkr;
                System.out.println("universityPayment Lkr " + universityPayment);
            } else if (internationalUniversityCurrency != null && internationalUniversityCurrency.equals("LKR")) {

                universityPayment = internationalUniversityDue;
                System.out.println("universityPayment Lkr " + universityPayment);
            }

            if (internationalUniversityCurrency != null && internationalUniversityCurrency.equals("LKR")) {

                universityPayment = internationalUniversityDue;
                System.out.println("universityPayment Lkr " + universityPayment);
            }

            System.out.println("diplomaValue " + diplomaValue);
            System.out.println("higherDiplomaValue " + higherDiplomaValue);
            System.out.println("universityPayment " + universityPayment);

            Double totalLoanAmount = internationalUniversityDue;

            System.out.println("totalLoanAmount Lkr " + totalLoanAmount);

            Double serviceFee = (totalLoanAmount / 100) * serviceChargesPercentage;

            System.out.println("serviceFee Lkr " + serviceFee);

            dueCourseFee = totalLoanAmount + serviceFee;

            System.out.println("dueCourseFee Lkr " + dueCourseFee);

            expectedLoanAmount = dueCourseFee;

        } catch (Exception e) {
            e.printStackTrace();
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

    public List<LoanCalculatorRecords> getLoanStrucure() {
        return loanStrucure;
    }

    public void setLoanStrucure(List<LoanCalculatorRecords> loanStrucure) {
        this.loanStrucure = loanStrucure;
    }

    public double getCorrectMonthlyInstallment() {
        return correctMonthlyInstallment;
    }

    public void setCorrectMonthlyInstallment(double correctMonthlyInstallment) {
        this.correctMonthlyInstallment = correctMonthlyInstallment;
    }

    public boolean isCorrectMonthlyInstallmentBoolean() {
        return correctMonthlyInstallmentBoolean;
    }

    public void setCorrectMonthlyInstallmentBoolean(boolean correctMonthlyInstallmentBoolean) {
        this.correctMonthlyInstallmentBoolean = correctMonthlyInstallmentBoolean;
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

    public int getActualMonth() {
        return actualMonth;
    }

    public void setActualMonth(int actualMonth) {
        this.actualMonth = actualMonth;
    }

    public boolean isLoanCalculated() {
        return loanCalculated;
    }

    public void setLoanCalculated(boolean loanCalculated) {
        this.loanCalculated = loanCalculated;
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

    public boolean isDisplayLoanAmount() {
        return displayLoanAmount;
    }

    public void setDisplayLoanAmount(boolean displayLoanAmount) {
        this.displayLoanAmount = displayLoanAmount;
    }

    public List<SelectItem> getRepaymentPeriodList() {
        return repaymentPeriodList;
    }

    public void setRepaymentPeriodList(List<SelectItem> repaymentPeriodList) {
        this.repaymentPeriodList = repaymentPeriodList;
    }

    public double getDueCourseFee() {
        return dueCourseFee;
    }

    public void setDueCourseFee(double dueCourseFee) {
        this.dueCourseFee = dueCourseFee;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

}
