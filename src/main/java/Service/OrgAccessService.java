/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import jakarta.ejb.Stateless;
import jakarta.ejb.EJB;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lk.exon.temco.templates.OfferInformEmailTemplateOne;
import lk.exon.temco.templates.UniversityLoanOfferEmail;
import lk.exon.temco.tools.NewMailSender;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.BranchManager;
import lk.exon.temco_loan_system.entity.CustomerResponseHistory;
import lk.exon.temco_loan_system.entity.GeneralUserProfile;
import lk.exon.temco_loan_system.entity.Intake;
import lk.exon.temco_loan_system.entity.IntakeManager;
import lk.exon.temco_loan_system.entity.LoanApplicantGurantor;
import lk.exon.temco_loan_system.entity.LoanCustomer;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.LoanOffer;
import lk.exon.temco_loan_system.entity.LoanStatusManager;
import lk.exon.temco_loan_system.entity.MaterializedStudentLoanEligibleStudentTable;
import lk.exon.temco_loan_system.entity.Member1;
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
@Stateless
public class OrgAccessService {

    String firstHalf;
    String secondHalf;

    @EJB
    private UniDBLocal uniDB;

    public jakarta.ws.rs.core.Response sendLoanOfferEmailTo(
            String nic,
            String gup_id,
            String firstName,
            String lastName,
            String email,
            String mobile_no,
            String scholarship,
            String totalDue,
            String verificationToken,
            String branchName,
            String intakeId,
            String intakeName,
            String address1,
            String address2,
            String address3,
            String profileCreatedDate,
            String totalIntDue) {
        try {

            Date date = new Date();

            List<MaterializedStudentLoanEligibleStudentTable> mslet = uniDB.searchByQuery("SELECT g FROM MaterializedStudentLoanEligibleStudentTable g WHERE g.nic='" + nic + "' ");
            System.out.println("mslet " + mslet.size());
            if (mslet.isEmpty()) {
                System.out.println("A1");
                MaterializedStudentLoanEligibleStudentTable newMslet = new MaterializedStudentLoanEligibleStudentTable();

                newMslet.setNic(nic);
                newMslet.setGupId(Integer.valueOf(gup_id));
                newMslet.setFirstName(firstName);
                newMslet.setLastName(lastName);
                newMslet.setEmail(email);
                newMslet.setMobileNo(mobile_no);
                System.out.println("A1.1");
                if (address1 != null && !address1.isBlank()) {
                    newMslet.setAddressLine1(address1);
                }
                if (address2 != null && !address2.isBlank()) {
                    newMslet.setAddressLine2(address2);
                }
                if (address3 != null && !address3.isBlank()) {
                    newMslet.setAddressLine3(address3);
                }
                if (scholarship != null && !scholarship.isBlank()) {
                    newMslet.setScholarship(Double.valueOf(scholarship));
                }
                if (intakeId != null && !intakeId.isBlank()) {
                    newMslet.setIntakeId(Integer.valueOf(intakeId));
                }
                if (totalDue != null && !totalDue.isBlank()) {
                    newMslet.setTotalDue(Double.valueOf(totalDue));
                }
                if (totalDue != null && !totalDue.isBlank()) {
                    newMslet.setInternationalUniversityDue(Double.valueOf(totalIntDue));
                }
                newMslet.setInternationalUniversityCurrency("LKR");
                newMslet.setVerificationToken(verificationToken);
                newMslet.setBranchName(branchName);
                newMslet.setIntakeName(intakeName);
                System.out.println("A1.2");
                if (profileCreatedDate != null && !profileCreatedDate.isBlank()) {

                    try {
                        newMslet.setProfileCreateDate(new SimpleDateFormat("yyyy-MM-dd").parse(profileCreatedDate));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                newMslet.setTransferDate(new Date());
                System.out.println("A2");
                uniDB.create(newMslet);
            } else {
                System.out.println("A3");
                mslet.get(0).setNic(nic);
                mslet.get(0).setGupId(Integer.valueOf(gup_id));
                mslet.get(0).setFirstName(firstName);
                mslet.get(0).setLastName(lastName);
                mslet.get(0).setEmail(email);
                mslet.get(0).setMobileNo(mobile_no);

                if (address1 != null && !address1.isBlank()) {
                    mslet.get(0).setAddressLine1(address1);
                }
                if (address2 != null && !address2.isBlank()) {
                    mslet.get(0).setAddressLine2(address2);
                }
                if (address3 != null && !address3.isBlank()) {
                    mslet.get(0).setAddressLine3(address3);
                }
                if (scholarship != null && !scholarship.isBlank()) {
                    mslet.get(0).setScholarship(Double.valueOf(scholarship));
                }
                if (intakeId != null && !intakeId.isBlank()) {
                    mslet.get(0).setIntakeId(Integer.valueOf(intakeId));
                }
                if (totalDue != null && !totalDue.isBlank()) {
                    mslet.get(0).setTotalDue(Double.valueOf(totalDue));
                }

                mslet.get(0).setVerificationToken(verificationToken);
                mslet.get(0).setBranchName(branchName);
                mslet.get(0).setIntakeName(intakeName);
                mslet.get(0).setInternationalUniversityDue(Double.valueOf(totalIntDue));

                if (profileCreatedDate != null && !profileCreatedDate.trim().isEmpty()) {
                    try {
                        mslet.get(0).setProfileCreateDate(new SimpleDateFormat("yyyy-MM-dd").parse(profileCreatedDate));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mslet.get(0).setGupId(Integer.parseInt(gup_id));
                mslet.get(0).setTransferDate(new Date());
                uniDB.update(mslet.get(0));
            }

            List<LoanCustomer> msle = uniDB.searchByQuery("Select g FROM LoanCustomer g WHERE g.nic='" + nic + "'");
            LoanCustomer loanCustomer = null;
            if (msle.isEmpty()) {
                loanCustomer = new LoanCustomer();
                loanCustomer.setGupId(Integer.valueOf(gup_id));
                loanCustomer.setNic(nic);
                loanCustomer.setFirstName(firstName);
                loanCustomer.setLastName(lastName);
                loanCustomer.setNameWithInitials("");
                loanCustomer.setEmail(email);
                loanCustomer.setAddressLine1(address1);
                loanCustomer.setAddressLine2(address2);
                loanCustomer.setAddressLine3(address3);
                loanCustomer.setVerificationToken(verificationToken);
                loanCustomer.setIsSubscribe(Short.decode("1"));
                uniDB.create(loanCustomer);
            } else {
                loanCustomer = msle.get(0);
            }

            MobileNo mobileNo = new MobileNo();
            mobileNo.setMobileNo(mobile_no);
            mobileNo.setPriorityId((Priority) uniDB.find(1, Priority.class));
            mobileNo.setLoanCustomerId(loanCustomer);
            uniDB.update(mobileNo);

            double category = Double.parseDouble(scholarship);
            List<ScholarshipCatergory> scholarshipCatergory = uniDB.searchByQuery("SELECT g FROM ScholarshipCatergory g WHERE g.catergory=" + category);

            ScholarshipManager manager = new ScholarshipManager();
            manager.setLoanCustomerId(loanCustomer);
            manager.setScholarshipCatergoryId(scholarshipCatergory.get(0));

            uniDB.create(manager);

            List<OrganizationBranches> orgList = uniDB.searchByQuery("SELECT g FROM OrganizationBranches g WHERE g.name LIKE '%" + branchName + "%'");
            System.out.println("a");
            if (!orgList.isEmpty()) {
                System.out.println("b");
                BranchManager branchManager = new BranchManager();
                branchManager.setLoanCustomerId(loanCustomer);
                branchManager.setOrganizationBranchesId(orgList.get(0));
                uniDB.create(branchManager);
            }
            System.out.println("c");
            System.out.println("msle.get(0).getIntakeName() " + intakeName);

            List<Intake> intakes = uniDB.searchByQuery("SELECT g FROM Intake g WHERE g.intakeId= '" + intakeId + "'");
            Intake intake = null;
            if (intakes.isEmpty()) {
                intake = new Intake();
                intake.setIntakeId(Integer.parseInt(intakeId));
                intake.setName(intakeName);
                uniDB.create(intake);
            } else {
                intake = intakes.get(0);
            }

            IntakeManager intakeManager = new IntakeManager();
            intakeManager.setIntakeId(intake);
            intakeManager.setLoanCustomerId(loanCustomer);
            intakeManager.setStatusid((Status) uniDB.find(3, Status.class));
//            if (secondHalf != null && secondHalf.equals("Registered")) {
//                intakeManager.setStatusid((Status) uniDB.find(1, Status.class));
//            } else if (secondHalf != null && secondHalf.equals("NP")) {
//                intakeManager.setStatusid((Status) uniDB.find(2, Status.class));
//            } else if (secondHalf == null) {
//                intakeManager.setStatusid((Status) uniDB.find(3, Status.class));
//            }

            uniDB.create(intakeManager);

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
//            boolean b = new NewMailSender().sendM(email, "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new UniversityLoanOfferEmail().emailTemplate(firstName + " " + lastName, verificationToken));
            boolean b = new NewMailSender().sendM(email, "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new OfferInformEmailTemplateOne().emailTemplate(firstName + " " + lastName, verificationToken));
//                    boolean b = new NewMailSender().sendM("tryabeywardane@gmail.com", "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new OfferInformEmailTemplateOne().emailTemplate(studentLoanExpecitngStudentList.get(i).studentName, studentLoanExpecitngStudentList.get(i).verificationToken));

            if (b) {
                String successResponse = "{"
                        + "\"status\": 200,"
                        + "\"message\": \"email sent successfully\""
                        + "}";

                return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.OK)
                        .entity(successResponse)
                        .build();
            } else {
                String errorResponse = "{"
                        + "\"status\": 500,"
                        + "\"message\": \"Email Send Failed\","
                        + "\"error\": \"Internal Server Error\""
                        + "}";
                return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(errorResponse)
                        .build();
            }

        } catch (NumberFormatException e) {
            String errorResponse = "{"
                    + "\"status\": 500,"
                    + "\"message\": \"internal server error\","
                    + "\"error\": \"" + e.getMessage() + "\""
                    + "}";
            return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }

    public boolean checkUserAlreadyInTheSystem(String nic) {
        List<GeneralUserProfile> gup = uniDB.searchByQuery("SELECT g FROM GeneralUserProfile g WHERE g.nic='" + nic + "'");
        if (!gup.isEmpty()) {
            List<Member1> member = uniDB.searchByQuery("SELECT g FROM Member1 g WHERE g.generalUserProfileId.id='" + gup.get(0).getId() + "'");
            if (!member.isEmpty()) {
                List<LoanApplicantGurantor> lag = uniDB.searchByQuery("SELECT g FROM LoanApplicantGurantor g WHERE g.memberId.id='" + member.get(0).getId() + "'");
                if (!lag.isEmpty()) {
                    for (LoanApplicantGurantor l : lag) {
                        List<LoanManager> lm = uniDB.searchByQuery("SELECT g from LoanManager g WHERE g.loanApplicantAndGurantorsId.id='" + l.getId() + "'");
                        if (lm != null && !lm.isEmpty()) {
                            List<LoanStatusManager> lsm = uniDB.searchByQuery("SELECT g FROM LoanStatusManager g WHERE g.loanManagerId.id='" + lm + "' AND g.loanStatusId.id='6'");
                            return lsm.isEmpty();
                        } else {
                            return true;
                        }
                    }
                }
            }

        }
        return true;
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

    public jakarta.ws.rs.core.Response sendInternationalUniversityLoanEmailTo(
            String nic,
            String gup_id,
            String firstName,
            String lastName,
            String email,
            String mobile_no,
            String scholarship,
            String totalDue,
            String verificationToken,
            String branchName,
            String intakeId,
            String intakeName,
            String address1,
            String address2,
            String address3,
            String profileCreatedDate,
            String paymentOption,
            String courseDue,
            String internationalAwardingBodyDiplomaDue,
            String internationalAwardingBodyDiplomaCurrency,
            String internationalAwardingBodyHigherDiplomaDue,
            String internationalAwardingBodyHigherDiplomaCurrency,
            String internationalUniversityDue,
            String internationalUniversityCurrency,
            String serviceChargesPresentage) {
        try {

            Date date = new Date();

            List<MaterializedStudentLoanEligibleStudentTable> mslet = uniDB.searchByQuery("SELECT g FROM MaterializedStudentLoanEligibleStudentTable g WHERE g.nic='" + nic + "' ");
            System.out.println("mslet " + mslet.size());
            if (mslet.isEmpty()) {
                System.out.println("A1");
                MaterializedStudentLoanEligibleStudentTable newMslet = new MaterializedStudentLoanEligibleStudentTable();

                newMslet.setNic(nic);
                newMslet.setGupId(Integer.valueOf(gup_id));
                newMslet.setFirstName(firstName);
                newMslet.setLastName(lastName);
                newMslet.setEmail(email);
                newMslet.setMobileNo(mobile_no);
                System.out.println("A1.1");
                if (address1 != null && !address1.isBlank()) {
                    newMslet.setAddressLine1(address1);
                }
                if (address2 != null && !address2.isBlank()) {
                    newMslet.setAddressLine2(address2);
                }
                if (address3 != null && !address3.isBlank()) {
                    newMslet.setAddressLine3(address3);
                }
                if (scholarship != null && !scholarship.isBlank()) {
                    newMslet.setScholarship(Double.valueOf(scholarship));
                }
                if (intakeId != null && !intakeId.isBlank()) {
                    newMslet.setIntakeId(Integer.valueOf(intakeId));
                }
                if (totalDue != null && !totalDue.isBlank()) {
                    newMslet.setTotalDue(Double.valueOf(totalDue));
                }
                newMslet.setVerificationToken(verificationToken);
                newMslet.setBranchName(branchName);
                newMslet.setIntakeName(intakeName);
                newMslet.setPaymentOption(paymentOption);
                System.out.println("A1.2");
                if (profileCreatedDate != null && !profileCreatedDate.isBlank()) {

                    try {
                        newMslet.setProfileCreateDate(new SimpleDateFormat("yyyy-MM-dd").parse(profileCreatedDate));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("A2");
                uniDB.create(newMslet);
            } else {
                System.out.println("A3");
                boolean b = checkUserAlreadyInTheSystem(nic);
                System.out.println("A4 " + b);
                if (b) {
                    mslet.get(0).setNic(nic);
                    mslet.get(0).setGupId(Integer.valueOf(gup_id));
                    mslet.get(0).setFirstName(firstName);
                    mslet.get(0).setLastName(lastName);
                    mslet.get(0).setEmail(email);
                    mslet.get(0).setMobileNo(mobile_no);

                    if (address1 != null && !address1.isBlank()) {
                        mslet.get(0).setAddressLine1(address1);
                    }
                    if (address2 != null && !address2.isBlank()) {
                        mslet.get(0).setAddressLine2(address2);
                    }
                    if (address3 != null && !address3.isBlank()) {
                        mslet.get(0).setAddressLine3(address3);
                    }
                    if (scholarship != null && !scholarship.isBlank()) {
                        mslet.get(0).setScholarship(Double.valueOf(scholarship));
                    }
                    if (intakeId != null && !intakeId.isBlank()) {
                        mslet.get(0).setIntakeId(Integer.valueOf(intakeId));
                    }
                    if (totalDue != null && !totalDue.isBlank()) {
                        mslet.get(0).setTotalDue(Double.valueOf(totalDue));
                    }

                    mslet.get(0).setVerificationToken(verificationToken);
                    mslet.get(0).setBranchName(branchName);
                    mslet.get(0).setIntakeName(intakeName);
                    mslet.get(0).setPaymentOption(paymentOption);
                    mslet.get(0).setCourseDue(Double.valueOf(courseDue));
                    mslet.get(0).setInternationalAwardingBodyDiplomaDue(Double.valueOf(internationalAwardingBodyDiplomaDue));
                    mslet.get(0).setInternationalAwardingBodyDiplomaCurrency(internationalAwardingBodyDiplomaCurrency);
                    mslet.get(0).setInternationalAwardingBodyHigherDiplomaDue(Double.valueOf(internationalAwardingBodyHigherDiplomaDue));
                    mslet.get(0).setInternationalAwardingBodyHigherDiplomaCurrency(internationalAwardingBodyHigherDiplomaCurrency);
                    mslet.get(0).setInternationalUniversityDue(Double.valueOf(internationalUniversityDue));
                    mslet.get(0).setInternationalUniversityCurrency(internationalUniversityCurrency);
                    mslet.get(0).setServiceChargesPresentage(Double.valueOf(serviceChargesPresentage));

                    if (profileCreatedDate != null && !profileCreatedDate.trim().isEmpty()) {
                        try {
                            mslet.get(0).setProfileCreateDate(new SimpleDateFormat("yyyy-MM-dd").parse(profileCreatedDate));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    uniDB.update(mslet.get(0));
                } else {
                    String errorResponse = "{"
                            + "\"status\": 500,"
                            + "\"message\": \"Already Applied for Loan\","
                            + "\"error\": \"Internal Server Error\""
                            + "}";
                    return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(errorResponse)
                            .build();
                }
            }

            List<LoanCustomer> msle = uniDB.searchByQuery("Select g FROM LoanCustomer g WHERE g.nic='" + nic + "'");
            LoanCustomer loanCustomer = null;
            if (msle.isEmpty()) {
                loanCustomer = new LoanCustomer();
                loanCustomer.setGupId(Integer.valueOf(gup_id));
                loanCustomer.setNic(nic);
                loanCustomer.setFirstName(firstName);
                loanCustomer.setLastName(lastName);
                loanCustomer.setNameWithInitials("");
                loanCustomer.setEmail(email);
                loanCustomer.setAddressLine1(address1);
                loanCustomer.setAddressLine2(address2);
                loanCustomer.setAddressLine3(address3);
                loanCustomer.setVerificationToken(verificationToken);
                loanCustomer.setIsSubscribe(Short.decode("1"));
                uniDB.create(loanCustomer);
            } else {
                loanCustomer = msle.get(0);
            }

            MobileNo mobileNo = new MobileNo();
            mobileNo.setMobileNo(mobile_no);
            mobileNo.setPriorityId((Priority) uniDB.find(1, Priority.class));
            mobileNo.setLoanCustomerId(loanCustomer);
            uniDB.update(mobileNo);

            double category = Double.parseDouble(scholarship);
            List<ScholarshipCatergory> scholarshipCatergory = uniDB.searchByQuery("SELECT g FROM ScholarshipCatergory g WHERE g.catergory=" + category);

            ScholarshipManager manager = new ScholarshipManager();
            manager.setLoanCustomerId(loanCustomer);
            manager.setScholarshipCatergoryId(scholarshipCatergory.get(0));

            uniDB.create(manager);

            List<OrganizationBranches> orgList = uniDB.searchByQuery("SELECT g FROM OrganizationBranches g WHERE g.name LIKE '%" + branchName + "%'");
            System.out.println("a");
            if (!orgList.isEmpty()) {
                System.out.println("b");
                BranchManager branchManager = new BranchManager();
                branchManager.setLoanCustomerId(loanCustomer);
                branchManager.setOrganizationBranchesId(orgList.get(0));
                uniDB.create(branchManager);
            }
            System.out.println("c");
            System.out.println("msle.get(0).getIntakeName() " + intakeName);

            List<Intake> intakes = uniDB.searchByQuery("SELECT g FROM Intake g WHERE g.intakeId= '" + intakeId + "'");
            Intake intake = null;
            if (intakes.isEmpty()) {
                intake = new Intake();
                intake.setIntakeId(Integer.parseInt(intakeId));
                intake.setName(intakeName);
                uniDB.create(intake);
            } else {
                intake = intakes.get(0);
            }

            IntakeManager intakeManager = new IntakeManager();
            intakeManager.setIntakeId(intake);
            intakeManager.setLoanCustomerId(loanCustomer);
            intakeManager.setStatusid((Status) uniDB.find(3, Status.class));
//            if (secondHalf != null && secondHalf.equals("Registered")) {
//                intakeManager.setStatusid((Status) uniDB.find(1, Status.class));
//            } else if (secondHalf != null && secondHalf.equals("NP")) {
//                intakeManager.setStatusid((Status) uniDB.find(2, Status.class));
//            } else if (secondHalf == null) {
//                intakeManager.setStatusid((Status) uniDB.find(3, Status.class));
//            }

            uniDB.create(intakeManager);

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
//                    boolean b = new NewMailSender().sendM("tryabeywardane@gmail.com", "Secure Your Future with Low-Interest Student Loans from TEMCO Bank and Java Institute", new OfferInformEmailTemplateOne().emailTemplate(studentLoanExpecitngStudentList.get(i).studentName, studentLoanExpecitngStudentList.get(i).verificationToken));

            if (b) {
                String successResponse = "{"
                        + "\"status\": 200,"
                        + "\"message\": \"email sent successfully\""
                        + "}";

                return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.OK)
                        .entity(successResponse)
                        .build();
            } else {
                String errorResponse = "{"
                        + "\"status\": 500,"
                        + "\"message\": \"Email Send Failed\","
                        + "\"error\": \"Internal Server Error\""
                        + "}";
                return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(errorResponse)
                        .build();
            }

        } catch (NumberFormatException e) {
            String errorResponse = "{"
                    + "\"status\": 500,"
                    + "\"message\": \"internal server error\","
                    + "\"error\": \"" + e.getMessage() + "\""
                    + "}";
            return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }

}
