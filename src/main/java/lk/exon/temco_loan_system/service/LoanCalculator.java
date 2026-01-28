/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.exon.temco.filteration.Filteration;
import lk.exon.temco.security.Security;
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
public class LoanCalculator implements Serializable {

    private List<LoanCalculatorRecords> loanStrucure = new ArrayList<>();

    private double dueCourseFee;

    double correctMonthlyInstallment = 0.00;
    boolean correctMonthlyInstallmentBoolean = false;
    private double expectedLoanAmount = 0.00;
    private double monthlyinstallement = 24000.00;
    private String repayementPeriod;
    private double grossIncome;
    private double interestRate = 6;
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

    @Inject
    LoanRequestForm LoanRequestForm;

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

        if (LoanRequestForm.getNic() != null) {
            System.out.println("LoanRequestForm.getNic() " + LoanRequestForm.getNic());
            System.out.println("loan calculator initialize if");
            updateOfferManager(LoanRequestForm.getNic());
            getUserDetailsFromNIC(LoanRequestForm.getNic());
            dueCourseFee = LoanRequestForm.getDueCourseFee();
            expectedLoanAmount = LoanRequestForm.getDueCourseFee();
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
                dueCourseFee = mt.get(0).getTotalDue();
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
        RepaymentPeriod r = (RepaymentPeriod) UniDB.find(Integer.parseInt(repayementPeriod), RepaymentPeriod.class);

        Double loanAmount = (monthlyinstallement * Double.valueOf(r.getPeriod()));

        if (expectedLoanAmount != 0) {
            if (monthlyinstallement >= 20000) {
                if (repayementPeriod != null && !repayementPeriod.equals("0")) {
                    if ((grossIncome != 0) && (monthlyinstallement < grossIncome)) {
                        System.out.println("employement type selected");
                        if (loanCalculated) {
                            if (checkbox) {
                                try {
                                    Date date = new Date();

                                    Member1 member = null;
                                    if (LoanRequestForm.getMember() != null) {
                                        System.out.println("A");
                                        member = LoanRequestForm.getMember();
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

                                    if (LoanRequestForm.getMemberBankAccounts() != null) {
                                        memberBankAccounts = LoanRequestForm.getMemberBankAccounts();
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
                                    String loanid = memberBankAccounts.getAccountNo();

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

                                    System.out.println("branch id a" + branchId);
                                    List<MaterializedStudentLoanEligibleStudentTable> mt = null;
                                    if (branchId == 0) {
                                        if (LoanRequestForm.getBranchId() != 0) {
                                            branchId = LoanRequestForm.getBranchId();
                                        } else {
                                            System.out.println("gup.getId " + gup.getId());
                                            System.out.println("gup.getNic() " + gup.getNic());
                                            mt = UniDB.searchByQuery("SELECT g FROM MaterializedStudentLoanEligibleStudentTable g WHERE g.nic='" + gup.getNic() + "' ");
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
                                    im.setLoanid((Loan) UniDB.find(1, Loan.class));
                                    im.setLoanInterestRateId((LoanInterestRate) UniDB.find(13, LoanInterestRate.class));
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

                                    if (mt != null
                                            && !mt.isEmpty()
                                            && mt.get(0).getInternationalUniversityDue() != null
                                            && mt.get(0).getInternationalUniversityDue() != 0.00) {

                                        System.out.println("saved successfull");
                                        FacesContext facesContext = FacesContext.getCurrentInstance();
                                        ExternalContext externalContext = facesContext.getExternalContext();
                                        externalContext.redirect(externalContext.getRequestContextPath() + "/view/international-university-payment-info.xhtml?en=" + mt.get(0).getVerificationToken());
                                        facesContext.responseComplete();
                                    } else {
                                        System.out.println("saved successfull");
                                        FacesContext facesContext = FacesContext.getCurrentInstance();
                                        ExternalContext externalContext = facesContext.getExternalContext();
                                        externalContext.redirect(externalContext.getRequestContextPath() + "/view/details-submission-success.xhtml");
                                        facesContext.responseComplete();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please check the checkbox that Agreeing terms and conditions");
                                FacesContext.getCurrentInstance().addMessage("", msg);
                            }
                        } else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Before Submit the Loan application please calculate the requested loan with expected monthly installment");
                            FacesContext.getCurrentInstance().addMessage("", msg);
                        }
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Guarantors Gross Income", "Guarantors income can not be 0.00 and need to be larger than entered monthly installment");
                        FacesContext.getCurrentInstance().addMessage("", msg);
                    }
                } else {
                    displayLoanTenture = true;
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Select the Loan Tenture of the loan");
                    FacesContext.getCurrentInstance().addMessage("", msg);
                }
            } else {
                displaymonthlyInstallment = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Minimum Monthly Installement you can pay is Rs.20,000");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }
        } else {
            displayLoanAmount = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please Enter the Expected Loan Amount Correctly");
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
                System.out.println("loan amount " + loanAmount);
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
        List<RepaymentPeriod> repaymentPeriods = UniDB.searchByQuery("SELECT g FROM RepaymentPeriod g WHERE g.loanid.id='1' AND g.isActive='1'");
        getRepaymentPeriodList().add(new SelectItem("", "Select"));
        for (RepaymentPeriod repaymentPeriodObj : repaymentPeriods) {
            getRepaymentPeriodList().add(new SelectItem(repaymentPeriodObj.getId().toString(), repaymentPeriodObj.getPeriod()));
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
