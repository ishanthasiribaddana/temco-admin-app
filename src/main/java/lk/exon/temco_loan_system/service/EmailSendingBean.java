/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lk.exon.temco.templates.OfferInformEmailTemplateOne;
import lk.exon.temco.templates.UniversityLoanOfferEmail;
import lk.exon.temco.tools.NewMailSender;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.BranchManager;
import lk.exon.temco_loan_system.entity.CustomerResponseHistory;
import lk.exon.temco_loan_system.entity.Gender;
import lk.exon.temco_loan_system.entity.Intake;
import lk.exon.temco_loan_system.entity.IntakeManager;
import lk.exon.temco_loan_system.entity.LoanCustomer;
import lk.exon.temco_loan_system.entity.LoanOffer;
import lk.exon.temco_loan_system.entity.MaterializedStudentLoanEligibleStudentTable;
import lk.exon.temco_loan_system.entity.MobileNo;
import lk.exon.temco_loan_system.entity.OfferManager;
import lk.exon.temco_loan_system.entity.OrganizationBranches;
import lk.exon.temco_loan_system.entity.Priority;
import lk.exon.temco_loan_system.entity.ResponseStatus;
import lk.exon.temco_loan_system.entity.ScholarshipCatergory;
import lk.exon.temco_loan_system.entity.ScholarshipManager;
import lk.exon.temco_loan_system.entity.Status;

/**
 *
 * @author USER
 */
@Named
@ViewScoped
public class EmailSendingBean implements Serializable {

    private int count;

    private String selectedIntake;

    private List<LoanExpectingStudents> studentLoanExpecitngStudentList;
    private List<SelectItem> studentsIntakeList;

    private boolean selected = true;

    String email;
    String firstName;
    String lastName;
    String verificationToken;

    String firstHalf;
    String secondHalf;

    @EJB
    private UniDBLocal uniDB;

    @PostConstruct
    public void init() {
        selectedIntake = "0";
        count = 0;
        loadLoanExpectingStudentsToTheTable();
        loadIntakes();
    }

    public void searchStudentsByNameNicAndEmail() {

    }

    public void loadSelectedIntakeStudents() {
        System.out.println("a");
        if (!selectedIntake.equals("0")) {
            System.out.println("b");
            List<MaterializedStudentLoanEligibleStudentTable> msle = uniDB.searchByQuery("Select g FROM MaterializedStudentLoanEligibleStudentTable g WHERE g.intakeId='" + selectedIntake + "'");
            System.out.println("c");
            if (!msle.isEmpty()) {
                System.out.println("d");
                studentLoanExpecitngStudentList = new ArrayList<>();
                System.out.println("msle " + msle.size());
                System.out.println("e");

                SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                for (MaterializedStudentLoanEligibleStudentTable msle_Object : msle) {
                    String formattedDateAndTime = "N/A";
                    if (msle_Object.getTransferDate() != null) {
                        formattedDateAndTime = smp.format(msle_Object.getTransferDate());
                    }
                    System.out.println("f");
                    studentLoanExpecitngStudentList.add(new LoanExpectingStudents(msle_Object.getNic(), msle_Object.getFirstName() + " " + msle_Object.getLastName(), msle_Object.getEmail(), msle_Object.getMobileNo(), msle_Object.getTotalDue(), (msle_Object.getInternationalUniversityDue() == null
                            || msle_Object.getInternationalUniversityDue().toString().isEmpty())
                            ? "N/A"
                            : msle_Object.getInternationalUniversityDue().toString(), false, msle_Object.getVerificationToken(), msle_Object.getIntakeName(), formattedDateAndTime));

//                    List<LoanCustomer> loanCustomerList = uniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + msle_Object.getNic() + "' ");
//                    if (loanCustomerList.isEmpty()) {
//                        System.out.println("g");
//                        studentLoanExpecitngStudentList.add(new LoanExpectingStudents(msle_Object.getNic(), msle_Object.getFirstName() + " " + msle_Object.getLastName(), msle_Object.getEmail(), msle_Object.getMobileNo(), msle_Object.getTotalDue(), false, msle_Object.getVerificationToken(), msle_Object.getIntakeName()));
//                    }
                }
            }
        } else {
            loadLoanExpectingStudentsToTheTable();
        }
    }

    public void loadIntakes() {

        studentsIntakeList = new ArrayList<>();
        studentsIntakeList.add(new SelectItem("0", "Select"));
        List<MaterializedStudentLoanEligibleStudentTable> intakes = uniDB.searchByQuery("SELECT g FROM MaterializedStudentLoanEligibleStudentTable g GROUP BY g.intakeId ORDER BY g.intakeId ASC");
        System.out.println("intakes " + intakes.size());
        if (!intakes.isEmpty()) {
            for (MaterializedStudentLoanEligibleStudentTable intake : intakes) {
                if (intake.getIntakeId() != 22775) {
                    System.out.println("intake.getIntakeId() " + intake.getIntakeId() + " " + intake.getIntakeName());
                    studentsIntakeList.add(new SelectItem(String.valueOf(intake.getIntakeId()), intake.getIntakeName()));
                }
            }
        }
    }

    public void loadLoanExpectingStudentsToTheTable() {
        List<MaterializedStudentLoanEligibleStudentTable> msle = uniDB.searchByQuery("Select g FROM MaterializedStudentLoanEligibleStudentTable g");

        if (!msle.isEmpty()) {
            studentLoanExpecitngStudentList = new ArrayList<>();
            System.out.println("msle " + msle.size());
            SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (MaterializedStudentLoanEligibleStudentTable msle_Object : msle) {
                String formattedDateAndTime = "N/A";
                if (msle_Object.getTransferDate() != null) {
                    formattedDateAndTime = smp.format(msle_Object.getTransferDate());
                }

                studentLoanExpecitngStudentList.add(new LoanExpectingStudents(msle_Object.getNic(), msle_Object.getFirstName() + " " + msle_Object.getLastName(), msle_Object.getEmail(), msle_Object.getMobileNo(), msle_Object.getTotalDue(), (msle_Object.getInternationalUniversityDue() == null
                        || msle_Object.getInternationalUniversityDue().toString().isEmpty())
                        ? "N/A"
                        : msle_Object.getInternationalUniversityDue().toString(), false, msle_Object.getVerificationToken(), msle_Object.getIntakeName(), formattedDateAndTime));
            }
        }

    }

    public void sendEmails() throws Exception {

        FacesMessage msg = null;
        System.out.println("student is selected a " + selected);
        if (count > 0) {
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    System.out.println("i " + studentLoanExpecitngStudentList.get(i).studentName);
                    List<LoanCustomer> loanCustomerList = uniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + studentLoanExpecitngStudentList.get(i).nic + "' ");
                    if (loanCustomerList != null && loanCustomerList.isEmpty()) {

                        Date date = new Date();

                        List<MaterializedStudentLoanEligibleStudentTable> msle = uniDB.searchByQuery("Select g FROM MaterializedStudentLoanEligibleStudentTable g WHERE g.nic='" + studentLoanExpecitngStudentList.get(i).nic + "'");

                        String email = msle.get(0).getEmail();
                        firstName = msle.get(0).getFirstName();
                        lastName = msle.get(0).getLastName();
                        verificationToken = msle.get(0).getVerificationToken();

                        LoanCustomer loanCustomer = new LoanCustomer();
                        loanCustomer.setGupId(msle.get(0).getGupId());
                        loanCustomer.setNic(msle.get(0).getNic());
                        loanCustomer.setFirstName(firstName);
                        loanCustomer.setLastName(lastName);
                        loanCustomer.setNameWithInitials(msle.get(0).getNameWithInitials());
                        loanCustomer.setDob(msle.get(0).getDob());
                        loanCustomer.setEmail(email);
                        loanCustomer.setAddressLine1(msle.get(0).getAddressLine1());
                        loanCustomer.setAddressLine2(msle.get(0).getAddressLine2());
                        loanCustomer.setAddressLine3(msle.get(0).getAddressLine3());
                        loanCustomer.setVerificationToken(verificationToken);

                        List<Gender> gender = uniDB.searchByQuery("SELECT g FROM Gender g WHERE g.name LIKE '%" + msle.get(0).getGenderType() + "%'");
                        loanCustomer.setGenderId(gender.get(0));
                        loanCustomer.setIsSubscribe(Short.decode("1"));
                        uniDB.create(loanCustomer);

                        MobileNo mobileNo = new MobileNo();
                        mobileNo.setMobileNo(msle.get(0).getMobileNo());
                        mobileNo.setPriorityId((Priority) uniDB.find(1, Priority.class));
                        mobileNo.setLoanCustomerId(loanCustomer);
                        uniDB.create(mobileNo);

                        double category = msle.get(0).getScholarship();
                        List<ScholarshipCatergory> scholarshipCatergory = uniDB.searchByQuery("SELECT g FROM ScholarshipCatergory g WHERE g.catergory=" + category);

                        ScholarshipManager manager = new ScholarshipManager();
                        manager.setLoanCustomerId(loanCustomer);
                        manager.setScholarshipCatergoryId(scholarshipCatergory.get(0));

                        uniDB.create(manager);

                        List<OrganizationBranches> orgList = uniDB.searchByQuery("SELECT g FROM OrganizationBranches g WHERE g.name LIKE '%" + msle.get(0).getBranchName() + "%'");
                        System.out.println("a");
                        if (!orgList.isEmpty()) {
                            System.out.println("b");
                            BranchManager branchManager = new BranchManager();
                            branchManager.setLoanCustomerId(loanCustomer);
                            branchManager.setOrganizationBranchesId(orgList.get(0));
                            uniDB.create(branchManager);
                        }
                        System.out.println("c");
                        System.out.println("msle.get(0).getIntakeName() " + msle.get(0).getIntakeName());

                        List<Intake> intakes;
                        if (msle.get(0).getIntakeName().contains("Registered") || msle.get(0).getIntakeName().contains("NP")) {
                            SplitText(msle.get(0).getIntakeName());
                            intakes = uniDB.searchByQuery("SELECT g FROM Intake g WHERE g.name like '%" + firstHalf + "%'");
                        } else {
                            intakes = uniDB.searchByQuery("SELECT g FROM Intake g WHERE g.name like '%" + msle.get(0).getIntakeName() + "%'");
                        }

                        if (intakes.isEmpty()) {
                            Intake intake = new Intake();
                            intake.setId(msle.get(0).getIntakeId());
                            intake.setName(firstHalf);
                            uniDB.create(intake);

                            IntakeManager intakeManager = new IntakeManager();
                            intakeManager.setIntakeId(intake);
                            intakeManager.setLoanCustomerId(loanCustomer);

                            if (secondHalf != null && secondHalf.equals("Registered")) {
                                intakeManager.setStatusid((Status) uniDB.find(1, Status.class));
                            } else if (secondHalf != null && secondHalf.equals("NP")) {
                                intakeManager.setStatusid((Status) uniDB.find(2, Status.class));
                            } else if (secondHalf == null) {
                                intakeManager.setStatusid((Status) uniDB.find(3, Status.class));
                            }

                            uniDB.create(intakeManager);
                        } else {
                            IntakeManager intakeManager = new IntakeManager();
                            intakeManager.setIntakeId(intakes.get(0));
                            intakeManager.setLoanCustomerId(loanCustomer);

                            if (secondHalf != null && secondHalf.equals("Registered")) {
                                intakeManager.setStatusid((Status) uniDB.find(1, Status.class));
                            } else if (secondHalf != null && secondHalf.equals("NP")) {
                                intakeManager.setStatusid((Status) uniDB.find(2, Status.class));
                            } else if (secondHalf == null) {
                                intakeManager.setStatusid((Status) uniDB.find(3, Status.class));
                            }

                            uniDB.create(intakeManager);
                        }

                        OfferManager offerManager = new OfferManager();
                        offerManager.setDate(date);
                        offerManager.setIsAccepted(Short.decode("0"));
                        offerManager.setLoanCustomerId(loanCustomer);
                        offerManager.setLoanOfferId((LoanOffer) uniDB.find(1, LoanOffer.class));
                        uniDB.create(offerManager);

                        CustomerResponseHistory responseHistory = new CustomerResponseHistory();
                        responseHistory.setDate(date);
                        responseHistory.setOfferManagerId(offerManager);
                        responseHistory.setResponseStatusId((ResponseStatus) uniDB.find(1, ResponseStatus.class));
                        uniDB.create(responseHistory);

                        boolean b = new NewMailSender().sendM(email, "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new UniversityLoanOfferEmail().emailTemplate(firstName + " " + lastName, verificationToken));
//                        boolean b = new NewMailSender().sendMailtrapEmail(studentLoanExpecitngStudentList.get(i).getEmail(), "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new OfferInformEmailTemplateOne().emailTemplate(studentLoanExpecitngStudentList.get(i).studentName, studentLoanExpecitngStudentList.get(i).verificationToken));
//                    boolean b = new NewMailSender().sendM("tryabeywardane@gmail.com", "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new OfferInformEmailTemplateOne().emailTemplate(studentLoanExpecitngStudentList.get(i).studentName, studentLoanExpecitngStudentList.get(i).verificationToken));

                        if (b) {
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucess", "Email Send Successfull to " + msle.get(0).getFirstName() + " " + msle.get(0).getLastName() + " ");
                            FacesContext.getCurrentInstance().addMessage("", msg);
                        } else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Email Send Failed to " + msle.get(0).getFirstName() + " " + msle.get(0).getLastName() + "");
                            FacesContext.getCurrentInstance().addMessage("", msg);
                        }

                    }
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "To send emails Email count can not be 0 ");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }
        } else {
            System.out.println("in the else");
            for (LoanExpectingStudents loanExpectingStudents : studentLoanExpecitngStudentList) {
                if (loanExpectingStudents.studentSelected == true) {
                    System.out.println("loanExpectingStudents.studentSelected " + loanExpectingStudents.studentSelected);
//                    List<LoanCustomer> loanCustomerList = uniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + loanExpectingStudents.nic + "' ");
//                    if (loanCustomerList != null && loanCustomerList.isEmpty()) {
//                        System.out.println("loanCustomerList" + loanCustomerList);
                    Date date = new Date();
                    List<MaterializedStudentLoanEligibleStudentTable> msle = uniDB.searchByQuery("Select g FROM MaterializedStudentLoanEligibleStudentTable g WHERE g.nic='" + loanExpectingStudents.nic + "'");

                    email = msle.get(0).getEmail();
                    firstName = msle.get(0).getFirstName();
                    lastName = msle.get(0).getLastName();

                    String verificationToken = msle.get(0).getVerificationToken();
                    LoanCustomer loanCustomer = new LoanCustomer();
                    loanCustomer.setGupId(msle.get(0).getGupId());
                    loanCustomer.setNic(msle.get(0).getNic());
                    loanCustomer.setFirstName(msle.get(0).getFirstName());
                    loanCustomer.setLastName(msle.get(0).getLastName());
                    loanCustomer.setNameWithInitials(msle.get(0).getNameWithInitials());
                    loanCustomer.setDob(msle.get(0).getDob());
                    loanCustomer.setEmail(msle.get(0).getEmail());
                    loanCustomer.setAddressLine1(msle.get(0).getAddressLine1());
                    loanCustomer.setAddressLine2(msle.get(0).getAddressLine2());
                    loanCustomer.setAddressLine3(msle.get(0).getAddressLine3());
                    loanCustomer.setVerificationToken(msle.get(0).getVerificationToken());
                    System.out.println("Gender" + msle.get(0).getGenderType());

                    List<Gender> gender = uniDB.searchByQuery("SELECT g FROM Gender g WHERE g.name LIKE '%" + msle.get(0).getGenderType() + "%'");
                    System.out.println("gender " + !gender.isEmpty());
                    if (!gender.isEmpty()) {
                        System.out.println("Gender table " + gender.get(0).getName());
                        loanCustomer.setGenderId(gender.get(0));
                    }
                    loanCustomer.setIsSubscribe(Short.decode("1"));
                    uniDB.create(loanCustomer);

                    MobileNo mobileNo = new MobileNo();
                    mobileNo.setMobileNo(msle.get(0).getMobileNo());
                    mobileNo.setPriorityId((Priority) uniDB.find(1, Priority.class));
                    mobileNo.setLoanCustomerId(loanCustomer);
                    uniDB.create(mobileNo);

                    double category = msle.get(0).getScholarship();
                    List<ScholarshipCatergory> scholarshipCatergory = uniDB.searchByQuery("SELECT g FROM ScholarshipCatergory g WHERE g.catergory=" + category);

                    ScholarshipManager manager = new ScholarshipManager();
                    manager.setLoanCustomerId(loanCustomer);
                    manager.setScholarshipCatergoryId(scholarshipCatergory.get(0));
                    uniDB.create(manager);

                    List<OrganizationBranches> orgList = uniDB.searchByQuery("SELECT g FROM OrganizationBranches g WHERE g.name LIKE '%" + msle.get(0).getBranchName() + "%'");
                    System.out.println("a");
                    if (!orgList.isEmpty()) {
                        System.out.println("b");
                        BranchManager branchManager = new BranchManager();
                        branchManager.setLoanCustomerId(loanCustomer);
                        branchManager.setOrganizationBranchesId(orgList.get(0));
                        uniDB.create(branchManager);
                    }
                    System.out.println("c");
                    System.out.println("msle.get(0).getIntakeName() " + msle.get(0).getIntakeId());
                    System.out.println("msle.get(0).getIntakeName() " + msle.get(0).getIntakeName());

                    List<Intake> intakes = uniDB.searchByQuery("SELECT g FROM Intake g WHERE g.intakeId= '" + msle.get(0).getIntakeId() + "'");
                    System.out.println("intake id from intake table" + intakes.get(0).getIntakeId() + " " + intakes.get(0).getName());
                    if (intakes.isEmpty()) {
                        Intake intake = new Intake();
                        intake.setIntakeId(msle.get(0).getIntakeId());
                        intake.setName(msle.get(0).getIntakeName());
                        uniDB.create(intake);

                        IntakeManager intakeManager = new IntakeManager();
                        intakeManager.setIntakeId(intake);
                        intakeManager.setLoanCustomerId(loanCustomer);

                        if (secondHalf != null && secondHalf.equals("Registered")) {
                            intakeManager.setStatusid((Status) uniDB.find(1, Status.class));
                        } else if (secondHalf != null && secondHalf.equals("NP")) {
                            intakeManager.setStatusid((Status) uniDB.find(2, Status.class));
                        } else if (secondHalf == null) {
                            intakeManager.setStatusid((Status) uniDB.find(3, Status.class));
                        }

                        uniDB.create(intakeManager);
                    } else {
                        IntakeManager intakeManager = new IntakeManager();
                        Intake i = intakes.get(0);
                        intakeManager.setIntakeId(i);
                        intakeManager.setLoanCustomerId(loanCustomer);

                        if (secondHalf != null && secondHalf.equals("Registered")) {
                            intakeManager.setStatusid((Status) uniDB.find(1, Status.class));
                        } else if (secondHalf != null && secondHalf.equals("NP")) {
                            intakeManager.setStatusid((Status) uniDB.find(2, Status.class));
                        } else if (secondHalf == null) {
                            intakeManager.setStatusid((Status) uniDB.find(3, Status.class));
                        }

                        uniDB.create(intakeManager);
                    }

                    OfferManager offerManager = new OfferManager();
                    offerManager.setDate(date);
                    offerManager.setIsAccepted(Short.decode("0"));
                    offerManager.setLoanCustomerId(loanCustomer);
                    offerManager.setLoanOfferId((LoanOffer) uniDB.find(1, LoanOffer.class));
                    uniDB.create(offerManager);

                    CustomerResponseHistory responseHistory = new CustomerResponseHistory();
                    responseHistory.setDate(date);
                    responseHistory.setOfferManagerId(offerManager);
                    responseHistory.setResponseStatusId((ResponseStatus) uniDB.find(1, ResponseStatus.class));
                    uniDB.create(responseHistory);

                    boolean b = new NewMailSender().sendM(email, "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new UniversityLoanOfferEmail().emailTemplate(firstName + " " + lastName, verificationToken));
//                    boolean b = new NewMailSender().sendMailtrapEmail(loanExpectingStudents.email, "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new OfferInformEmailTemplateOne().emailTemplate(loanExpectingStudents.studentName, loanExpectingStudents.verificationToken));
//                    boolean b = new NewMailSender().sendM("tryabeywardane@gmail.com", "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new OfferInformEmailTemplateOne().emailTemplate(studentLoanExpecitngStudentList.get(i).studentName, studentLoanExpecitngStudentList.get(i).verificationToken));

                    if (b) {
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucess", "Email Send Successfull to " + msle.get(0).getFirstName() + " " + msle.get(0).getLastName() + " ");
                        FacesContext.getCurrentInstance().addMessage("", msg);
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Email Send Failed to " + msle.get(0).getFirstName() + " " + msle.get(0).getLastName() + "");
                        FacesContext.getCurrentInstance().addMessage("", msg);
                    }
                } else {
//                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR ! ! !", "To send a email you need to select user or add a count");
//                    FacesContext.getCurrentInstance().addMessage("", msg);
                }
            }
        }
        count = 0;
    }

    public void SplitText(String text) {

        if (text.contains(" - Registered")) {
            String[] parts = text.split(" - Registered");
            firstHalf = parts[0].trim();
            secondHalf = "Registered";
        } else if (text.contains(" - NP")) {
            String[] parts = text.split(" - NP");
            firstHalf = parts[0].trim();
            secondHalf = "NP";
        } else {
            firstHalf = text;
        }
        System.out.println("firstHalf split " + firstHalf);
        System.out.println("secondHalf split " + secondHalf);
    }

    public class LoanExpectingStudents implements Serializable {

        private String nic;
        private String studentName;
        private String email;
        private String mobileNo;
        private double totalDue;
        private String totalintunipaymentdue;
        private boolean studentSelected;
        private String verificationToken;
        private String intake;
        private String transferDate;

        public LoanExpectingStudents(String nic, String studentName, String email, String mobileNo, double totalDue, String totalintunipaymentdue, boolean studentSelected, String verificationToken, String intake, String transferDate) {
            this.nic = nic;
            this.studentName = studentName;
            this.email = email;
            this.mobileNo = mobileNo;
            this.totalDue = totalDue;
            this.totalintunipaymentdue = totalintunipaymentdue;
            this.studentSelected = studentSelected;
            this.verificationToken = verificationToken;
            this.intake = intake;
            this.transferDate = transferDate;
        }

        public String getNic() {
            return nic;
        }

        public void setNic(String nic) {
            this.nic = nic;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public double getTotalDue() {
            return totalDue;
        }

        public void setTotalDue(double totalDue) {
            this.totalDue = totalDue;
        }

        public String getTotalintunipaymentdue() {
            return totalintunipaymentdue;
        }

        public void setTotalintunipaymentdue(String totalintunipaymentdue) {
            this.totalintunipaymentdue = totalintunipaymentdue;
        }

        public boolean isStudentSelected() {
            return studentSelected;
        }

        public void setStudentSelected(boolean studentSelected) {
            this.studentSelected = studentSelected;
        }

        public String getVerificationToken() {
            return verificationToken;
        }

        public void setVerificationToken(String verificationToken) {
            this.verificationToken = verificationToken;
        }

        public String getIntake() {
            return intake;
        }

        public void setIntake(String intake) {
            this.intake = intake;
        }

        public String getTransferDate() {
            return transferDate;
        }

        public void setTransferDate(String transferDate) {
            this.transferDate = transferDate;
        }

    }

    public List<LoanExpectingStudents> getStudentLoanExpecitngStudentList() {
        return studentLoanExpecitngStudentList;
    }

    public void setStudentLoanExpecitngStudentList(List<LoanExpectingStudents> studentLoanExpecitngStudentList) {
        this.studentLoanExpecitngStudentList = studentLoanExpecitngStudentList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SelectItem> getStudentsIntakeList() {
        return studentsIntakeList;
    }

    public void setStudentsIntakeList(List<SelectItem> studentsIntakeList) {
        this.studentsIntakeList = studentsIntakeList;
    }

    public String getSelectedIntake() {
        return selectedIntake;
    }

    public void setSelectedIntake(String selectedIntake) {
        this.selectedIntake = selectedIntake;
    }

}
