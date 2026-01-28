/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Row;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
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
import java.util.Map;
import java.util.stream.Collectors;
import lk.exon.temco.templates.AcceptanceEmail;
import lk.exon.temco.templates.LoanApplicationRejectionEmail;
import lk.exon.temco.tools.CustomPDFExporter;
import lk.exon.temco.tools.NewMailSender;
import lk.exon.temco_loan_system.common.ComMethods;
import lk.exon.temco_loan_system.common.ComPath;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.CommentType;
import lk.exon.temco_loan_system.entity.CreatedDocument;
import lk.exon.temco_loan_system.entity.GuarantorDocuments;
import lk.exon.temco_loan_system.entity.GurantorManager;
import lk.exon.temco_loan_system.entity.InterestManager;
import lk.exon.temco_loan_system.entity.LoanDocumentsScheduler;
import lk.exon.temco_loan_system.entity.LoanInstallementPlan;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.LoanStatus;
import lk.exon.temco_loan_system.entity.LoanStatusManager;
import lk.exon.temco_loan_system.entity.StatusCommentManager;
import lk.exon.temco_loan_system.entity.UniversalUserDocument;
import lk.exon.temco_loan_system.entity.Weeks;
import lk.exon.temco_loan_system.entity.WeeksScheduler;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author USER
 */
@Named
@ViewScoped
public class LoanDashboard implements Serializable {

    private List<Loan> loans = new ArrayList<>();
    private List<SelectItem> commentType;
    private List<Loan> filteredLoans;

    private int loanRequests = 0;
    private double totalRequestedLoanAmount;
    private long totalSubmittedPayOrders = 0;
    private int classAttenededRequestors;
    private double totalDuesToCollected = 0.00;
    private String aggregateDisbursementToJIAT = "";
    private double cumulativeDisbursementToJIAT = 0.00;
    private int totalLoanApplicants = 0;

    private Loan loanObject;

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

    private String selectedComment;
    private String comment;

    private String loanBrandName;
    private String interestRate;
    private String loanAmount;
    private String monthlyInstallement;
    private String timePeriod;

    private boolean viewButtons;

    private String globalFilter;

    private List<GurantorManager> gurantorManagersList;
    private List<UserLoanInstallementPlan> loanInstallementPlanList;

    @EJB
    private UniDBLocal uniDB;

    @PostConstruct
    public void init() {
        initialized();
    }

    public void initialized() {
        loadLoans();
    }

    public void loadLoans() {
        try {

            double totalRequestedAmount = 0.0;
            int nonRejectedLoanCount = 0;

            // 1. Fetch latest InterestManager rows (one per loan applicant)
            List<InterestManager> interestManagers = uniDB.searchByQueryInterestManager(
                    "SELECT im FROM InterestManager im "
                    + "WHERE im.loanid.id = :loanId "
                    + "AND im.id IN ( "
                    + "   SELECT MAX(im2.id) FROM InterestManager im2 "
                    + "   WHERE im2.loanid.id = :loanId "
                    + "   GROUP BY im2.loanManagerId.loanApplicantAndGurantorsId.memberId.id "
                    + ") ORDER BY im.id DESC",
                    Map.of("loanId", 1)
            );

            if (interestManagers.isEmpty()) {
                loanRequests = 0;
                aggregateDisbursementToJIAT = "0.00";
                totalRequestedLoanAmount = 0.00;
                return;
            }

            // 2. Extract loanManagerIds
            List<Integer> loanManagerIds = interestManagers.stream()
                    .map(im -> im.getLoanManagerId().getId())
                    .collect(Collectors.toList());

            // 3. Fetch latest status for all these loans at once
            List<LoanStatusManager> latestStatusList = uniDB.searchByQueryLoanStatusManager(
                    "SELECT lsm FROM LoanStatusManager lsm "
                    + "WHERE lsm.loanManagerId.id IN :ids "
                    + "AND lsm.date IN ( "
                    + "   SELECT MAX(lsm2.date) FROM LoanStatusManager lsm2 "
                    + "   WHERE lsm2.loanManagerId.id IN :ids "
                    + "   GROUP BY lsm2.loanManagerId.id "
                    + ")",
                    Map.of("ids", loanManagerIds)
            );

            // 4. Create a map for O(1) lookup
            Map<Integer, LoanStatusManager> latestStatusMap = latestStatusList.stream()
                    .collect(Collectors.toMap(
                            s -> s.getLoanManagerId().getId(),
                            s -> s
                    ));

            // 5. Count submitted pay orders (status = 3)
            Long count = (Long) uniDB.searchByQuery(
                    "SELECT COUNT(g) FROM LoanStatusManager g WHERE g.loanStatusId.id = 3"
            ).get(0);

            totalSubmittedPayOrders = count;

            Object value = uniDB.searchByQuery(
                    "SELECT COUNT(g) FROM LoanStatusManager g WHERE g.loanStatusId.id = 3"
            ).get(0);

            System.out.println("CLASS = " + value.getClass());

            // 6. Build final loan list and apply business rules (exclude rejected loans)
            loans.clear();

            for (InterestManager im : interestManagers) {

                LoanManager lm = im.getLoanManagerId();
                LoanStatusManager latest = latestStatusMap.get(lm.getId());

                boolean isRejected = latest != null && latest.getLoanStatusId().getId() == 7;

                // Count only applicants whose latest status is NOT rejected
                if (!isRejected) {
                    totalLoanApplicants++;
                    nonRejectedLoanCount++;
                    totalRequestedAmount += lm.getLoanCapitalAmount();
                    aggregateDisbursementToJIAT = String.format("%,.2f", totalRequestedAmount);
                }

                // Still load all loans into the table including rejected
                loans.add(new Loan(
                        lm.getReferenceId(),
                        lm.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic(),
                        lm.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getFirstName()
                        + " "
                        + lm.getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getLastName(),
                        lm.getLoanCapitalAmount(),
                        formatDate(lm.getDate()),
                        latest != null ? latest.getLoanStatusId().getName() : "Unknown",
                        latest != null ? formatDate(latest.getDate()) : "",
                        0.00,
                        "",
                        im
                ));
            }

            // 7. Final aggregated values
            loanRequests = nonRejectedLoanCount;
            totalRequestedLoanAmount = totalRequestedAmount;
            totalLoanApplicants = interestManagers.size();        // this counts ALL applicants

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }

    public void filterLoans() {
        if (globalFilter == null || globalFilter.isEmpty()) {
            filteredLoans = new ArrayList<>(loans);
        } else {
            String filterText = globalFilter.toLowerCase();
            filteredLoans = loans.stream()
                    .filter(loan
                            -> loan.getAssoNo().toLowerCase().contains(filterText)
                    )
                    .collect(Collectors.toList());
        }
    }

    public void postProcessXLSX(Object document) {
        try {
            XSSFWorkbook workbook = (XSSFWorkbook) document;
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Create text format style
            XSSFCellStyle textStyle = workbook.createCellStyle();
            XSSFDataFormat format = workbook.createDataFormat();
            textStyle.setDataFormat(format.getFormat("@")); // Text format

            // Find NIC column by header
            int nicColumnIndex = findNICColumnIndex(sheet);
            if (nicColumnIndex == -1) {
                nicColumnIndex = 1; // Fallback to column index 1
            }

            System.out.println("Processing NIC column at index: " + nicColumnIndex);

            // Process all rows (skip header row 0)
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowNum);
                if (row != null) {
                    org.apache.poi.ss.usermodel.Cell nicCell = row.getCell(nicColumnIndex);
                    if (nicCell != null) {
                        // Get the original value as string
                        String originalValue = getCellValueAsString(nicCell);
                        System.out.println("Original NIC value at row " + rowNum + ": " + originalValue);

                        if (originalValue != null && !originalValue.trim().isEmpty()) {
                            String cleanValue = originalValue.trim();

                            // Apply text style
                            nicCell.setCellStyle(textStyle);

                            // Handle different NIC formats
                            if (cleanValue.matches("\\d{12}")) {
                                // 12-digit new NIC - ensure it displays as full number
                                nicCell.setCellValue(cleanValue);
                                System.out.println("Formatted 12-digit NIC: " + cleanValue);
                            } else if (cleanValue.matches("\\d{9}[Vv]")) {
                                // 10-character old NIC with V - keep as is
                                nicCell.setCellValue(cleanValue);
                                System.out.println("Formatted 9-digit NIC with V: " + cleanValue);
                            } else if (cleanValue.contains("E+")) {
                                // Scientific notation - extract the original number
                                String fixedValue = convertScientificToFullNumber(cleanValue);
                                nicCell.setCellValue(fixedValue);
                                System.out.println("Converted scientific to: " + fixedValue);
                            } else {
                                // Any other format
                                nicCell.setCellValue(cleanValue);
                            }
                        }
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Helper method to find NIC column index by header
    private int findNICColumnIndex(XSSFSheet sheet) {
        org.apache.poi.ss.usermodel.Row headerRow = sheet.getRow(0);
        if (headerRow != null) {
            for (org.apache.poi.ss.usermodel.Cell cell : headerRow) {
                if (cell != null && "NIC".equalsIgnoreCase(cell.getStringCellValue().trim())) {
                    return cell.getColumnIndex();
                }
            }
        }
        return -1;
    }

// Helper method to convert scientific notation to full number
    private String convertScientificToFullNumber(String scientificValue) {
        try {
            if (scientificValue.contains("E+")) {
                // This is scientific notation like "2.00407E+11"
                String[] parts = scientificValue.split("E\\+");
                double base = Double.parseDouble(parts[0]);
                int exponent = Integer.parseInt(parts[1]);

                // Convert to full number
                double fullNumber = base * Math.pow(10, exponent);

                // Format as integer without decimal places
                if (fullNumber == Math.floor(fullNumber)) {
                    return String.format("%.0f", fullNumber);
                } else {
                    return String.valueOf(fullNumber);
                }
            }
            return scientificValue;
        } catch (Exception e) {
            return scientificValue;
        }
    }

    public String formatNICForExport(Object value) {
        if (value == null) {
            return "";
        }

        String nic = value.toString().trim();
        // For 12-digit NICs, ensure they're treated as text
        if (nic.length() == 12 && nic.matches("\\d+")) {
            return "'" + nic;
        }
        return nic;
    }

// Enhanced helper method to handle different cell types
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numValue = cell.getNumericCellValue();
                    // Handle NIC numbers that might be stored as numeric
                    // For 12-digit numbers, format without decimal
                    if (numValue >= 100000000000L && numValue < 1000000000000L) {
                        // It's a 12-digit number (like NIC)
                        return String.format("%.0f", numValue);
                    } else if (numValue >= 1000000000L && numValue < 10000000000L) {
                        // It's a 10-digit number (old NIC)
                        return String.format("%.0f", numValue);
                    } else {
                        return String.valueOf(numValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
            try {
                return cell.getStringCellValue();
            } catch (Exception e) {
                return String.valueOf(cell.getNumericCellValue());
            }
            default:
                return "";
        }
    }

    public void preProcessPDF(Object document) {
        try {
            Document pdf = (Document) document;
            pdf.open();
            pdf.setPageSize(PageSize.A4.rotate());

            // You can also set margins
            pdf.setMargins(20, 20, 20, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void releaseCreditNote() {
        try {
            FacesMessage msg;

            GurantorManager firstGuarantor = null;
            GurantorManager secondGuarantor = null;

            for (GurantorManager gurantorManager : gurantorManagersList) {
                switch (gurantorManager.getGurantorCountId().getValue()) {
                    case "1":
                        firstGuarantor = gurantorManager;
                        break;
                    case "2":
                        secondGuarantor = gurantorManager;
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            System.out.println("loanObj != null " + (loanObject != null));
            System.out.println("firstGuarantor != null " + (firstGuarantor != null));
            System.out.println("secondGuarantor != null " + (secondGuarantor != null));
            if (firstGuarantor != null && secondGuarantor != null) {
                List<LoanStatusManager> loanStatusManager = uniDB.searchByQuery("SELECT g FROM LoanStatusManager g WHERE g.loanStatusId.id='2' AND g.loanManagerId.id='" + loanObject.loanMangerObj.getLoanManagerId().getId() + "' ");
                Date date = new Date();
                if (loanStatusManager.isEmpty()) {

                    List<LoanDocumentsScheduler> listDocumentsScheduler = uniDB.searchByQuery("SELECT g FROM LoanDocumentsScheduler g WHERE g.loanManagerId.id=" + loanObject.loanMangerObj.getLoanManagerId().getId() + " AND g.memberId.id=" + loanObject.loanMangerObj.getLoanManagerId().getLoanApplicantAndGurantorsId().getMemberId().getId());
                    LoanDocumentsScheduler loanApplicantMemberFeeandSharePurchaseFee;
                    Weeks weekOne = (Weeks) uniDB.find(1, Weeks.class);
                    if (listDocumentsScheduler.isEmpty()) {
                        loanApplicantMemberFeeandSharePurchaseFee = new LoanDocumentsScheduler();

                        loanApplicantMemberFeeandSharePurchaseFee.setLoanManagerId(loanObject.loanMangerObj.getLoanManagerId());
                        loanApplicantMemberFeeandSharePurchaseFee.setSubmissionDate(date);
                        loanApplicantMemberFeeandSharePurchaseFee.setIsSubmitted(Short.valueOf("1"));
                        loanApplicantMemberFeeandSharePurchaseFee.setMemberId(loanObject.loanMangerObj.getLoanManagerId().getLoanApplicantAndGurantorsId().getMemberId());
                        loanApplicantMemberFeeandSharePurchaseFee.setDocumentCreatorId((CreatedDocument) uniDB.find(1, CreatedDocument.class));
                        uniDB.create(loanApplicantMemberFeeandSharePurchaseFee);

                        WeeksScheduler firstWeekLoanGurantorScheduler = new WeeksScheduler();
                        firstWeekLoanGurantorScheduler.setLoanDocumentsSchedulerId(loanApplicantMemberFeeandSharePurchaseFee);
                        firstWeekLoanGurantorScheduler.setWeeksId(weekOne);
                        uniDB.create(firstWeekLoanGurantorScheduler);

                    } else {

                        listDocumentsScheduler.get(0).setSubmissionDate(date);
                        uniDB.update(listDocumentsScheduler.get(0));
                    }

                    if (firstGuarantor != null) {

                        List<LoanDocumentsScheduler> listDocumentsSchedulerFirstGuarantor = uniDB.searchByQuery("SELECT g FROM LoanDocumentsScheduler g WHERE g.loanManagerId.id=" + firstGuarantor.getLoanApplicantAndGurantorsId().getMemberId().getId() + " AND g.memberId.id=" + loanObject.loanMangerObj.getLoanManagerId().getLoanApplicantAndGurantorsId().getMemberId().getId());

                        LoanDocumentsScheduler FirstGurantorMemberFeeandSharePurchaseFee;

                        if (listDocumentsSchedulerFirstGuarantor.isEmpty()) {
                            FirstGurantorMemberFeeandSharePurchaseFee = new LoanDocumentsScheduler();
                            FirstGurantorMemberFeeandSharePurchaseFee.setLoanManagerId(loanObject.loanMangerObj.getLoanManagerId());
                            FirstGurantorMemberFeeandSharePurchaseFee.setSubmissionDate(date);
                            FirstGurantorMemberFeeandSharePurchaseFee.setIsSubmitted(Short.valueOf("1"));
                            FirstGurantorMemberFeeandSharePurchaseFee.setMemberId(firstGuarantor.getLoanApplicantAndGurantorsId().getMemberId());
                            FirstGurantorMemberFeeandSharePurchaseFee.setDocumentCreatorId((CreatedDocument) uniDB.find(1, CreatedDocument.class));
                            uniDB.create(FirstGurantorMemberFeeandSharePurchaseFee);

                            WeeksScheduler firstWeekLoanFirstGurantorScheduler = new WeeksScheduler();
                            firstWeekLoanFirstGurantorScheduler.setLoanDocumentsSchedulerId(FirstGurantorMemberFeeandSharePurchaseFee);
                            firstWeekLoanFirstGurantorScheduler.setWeeksId(weekOne);
                            uniDB.create(firstWeekLoanFirstGurantorScheduler);
                        } else {
                            listDocumentsSchedulerFirstGuarantor.get(0).setSubmissionDate(date);
                            uniDB.update(listDocumentsSchedulerFirstGuarantor.get(0));
                        }
                    }

                    if (secondGuarantor != null) {
                        List<LoanDocumentsScheduler> listDocumentsSchedulerSecondGuarantor = uniDB.searchByQuery("SELECT g FROM LoanDocumentsScheduler g WHERE g.loanManagerId.id=" + firstGuarantor.getLoanApplicantAndGurantorsId().getMemberId().getId() + " AND g.memberId.id=" + secondGuarantor.getLoanApplicantAndGurantorsId().getMemberId().getId());

                        LoanDocumentsScheduler secondGurantorMemberFeeandSharePurchaseFee;

                        if (listDocumentsSchedulerSecondGuarantor.isEmpty()) {
                            secondGurantorMemberFeeandSharePurchaseFee = new LoanDocumentsScheduler();
                            secondGurantorMemberFeeandSharePurchaseFee.setLoanManagerId(loanObject.loanMangerObj.getLoanManagerId());
                            secondGurantorMemberFeeandSharePurchaseFee.setSubmissionDate(date);
                            secondGurantorMemberFeeandSharePurchaseFee.setIsSubmitted(Short.valueOf("1"));
                            secondGurantorMemberFeeandSharePurchaseFee.setMemberId(gurantorManagersList.get(1).getLoanApplicantAndGurantorsId().getMemberId());
                            secondGurantorMemberFeeandSharePurchaseFee.setDocumentCreatorId((CreatedDocument) uniDB.find(1, CreatedDocument.class));
                            uniDB.create(secondGurantorMemberFeeandSharePurchaseFee);

                            WeeksScheduler firstWeekLoanSecondGurantorScheduler = new WeeksScheduler();
                            firstWeekLoanSecondGurantorScheduler.setLoanDocumentsSchedulerId(secondGurantorMemberFeeandSharePurchaseFee);
                            firstWeekLoanSecondGurantorScheduler.setWeeksId(weekOne);
                            uniDB.create(firstWeekLoanSecondGurantorScheduler);
                        } else {
                            listDocumentsSchedulerSecondGuarantor.get(0).setSubmissionDate(date);
                            uniDB.update(listDocumentsSchedulerSecondGuarantor.get(0));
                        }
                    }

                    double threeMonthsDownPayment = loanObject.getLoanMangerObj().getLoanManagerId().getMonthlyInstallement() * 3;

                    String accptanceEmailContent = new AcceptanceEmail().acceptanceEmailTemplate(new SimpleDateFormat("yyyy/MM/dd").format(date), loanObject.loanMangerObj.getLoanManagerId(), gurantorManagersList, threeMonthsDownPayment, loanObject.loanMangerObj.getLoanManagerId().getVerificationToke());

                    boolean b = new NewMailSender().sendM(loanObject.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getEmail(), "Acceptance of Your Student Loan Application", accptanceEmailContent);

                    LoanStatusManager statusManager = new LoanStatusManager();
                    statusManager.setLoanManagerId(loanObject.loanMangerObj.getLoanManagerId());
                    statusManager.setLoanStatusId((LoanStatus) uniDB.find(2, LoanStatus.class));
                    statusManager.setDate(date);
                    uniDB.create(statusManager);

                    if (b) {
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucess ! ! !", "Email Send Successful");
                        FacesContext.getCurrentInstance().addMessage("", msg);
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ! ! !", "Email Send Failed");
                        FacesContext.getCurrentInstance().addMessage("", msg);
                    }

                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ! ! !", "A credit note already issued ");
                    FacesContext.getCurrentInstance().addMessage("", msg);
                }

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ! ! !", "Gurantors Data is Empty.Can not Proceed");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRejectComment() {
        FacesMessage msg;
        if (loanObject != null) {

            CommentType selectedCommentByUser = (CommentType) uniDB.find(Integer.parseInt(selectedComment), CommentType.class);

            LoanStatusManager statusManager = new LoanStatusManager();
            statusManager.setLoanManagerId(loanObject.loanMangerObj.getLoanManagerId());
            statusManager.setLoanStatusId((LoanStatus) uniDB.find(7, LoanStatus.class));
            statusManager.setDate(new Date());
            uniDB.create(statusManager);

            StatusCommentManager commentManager = new StatusCommentManager();
            commentManager.setLoanStatusManagerId(statusManager);
            commentManager.setCommentTypeId(selectedCommentByUser);
            uniDB.create(commentManager);

            String rejectioneEmail = new LoanApplicationRejectionEmail().loanApplicationRejectionEmail(loanObject.name, selectedCommentByUser.getComment());

            boolean b = new NewMailSender().sendM(loanObject.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getEmail(), "Issue/s in your Student Loan Application", rejectioneEmail);

            if (b) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucess ! ! !", "Email Send Successful");
                FacesContext.getCurrentInstance().addMessage("", msg);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ! ! !", "Email Send Failed");
                FacesContext.getCurrentInstance().addMessage("", msg);
            }

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ! ! !", "Please Select a Loan Request");
            FacesContext.getCurrentInstance().addMessage("", msg);
        }
    }

    public void setSelectedUser(Loan loanObj) {

        try {
            System.out.println("setSelectedUser");
            System.out.println("loan manager verification token " + loanObj.getLoanMangerObj().getLoanManagerId().getVerificationToke());
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
            ffrontNic = "";
            firstBackNic = "";
            sfrontNic = "";
            secondBackNic = "";
            firstName = loanObj.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getFirstName();
            lastName = loanObj.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getLastName();
            nic = loanObj.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getNic();
            mobileNo = loanObj.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getMobileNo();
            dateOfBirth = new SimpleDateFormat("yyyy/MM/dd").format(loanObj.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getDob());
            email = loanObj.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getEmail();
            addressOne = loanObj.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getAddress1();
            addressTwo = loanObj.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getAddress2();
            addressThree = loanObj.loanMangerObj.getLoanManagerId().getMemberBankAccountsId().getMemberId().getGeneralUserProfileId().getAddress3();

            int loan_applicant_id = loanObj.loanMangerObj.getLoanManagerId().getLoanApplicantAndGurantorsId().getId();
            int gup_id = loanObj.loanMangerObj.getLoanManagerId().getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getId();
            System.out.println("loan_applicant_id " + loan_applicant_id);
            gurantorManagersList = uniDB.searchByQuery("SELECT g FROM GurantorManager g WHERE g.loanApplicantId.id='" + loan_applicant_id + "'");
            int i = 0;
            System.out.println("gurantorManagersList " + gurantorManagersList.size());
            loanBrandName = loanObj.loanMangerObj.getLoanManagerId().getRepaymentPeriodId().getLoanid().getLoanBrand();
            interestRate = loanObj.loanMangerObj.getLoanInterestRateId().getRate().toString();
            loanAmount = String.valueOf(loanObj.loanMangerObj.getLoanManagerId().getLoanCapitalAmount());
            monthlyInstallement = String.valueOf(loanObj.loanMangerObj.getLoanManagerId().getMonthlyInstallement());
            timePeriod = String.valueOf(loanObj.loanMangerObj.getLoanManagerId().getRepaymentPeriodId().getPeriod());

            loanObject = loanObj;

            viewButtons = loanObj.payOrderStatus.equals("Credit Note Issued");

            System.out.println("view buttons " + viewButtons);

            System.out.println("loanObj.getLoanMangerObj().getLoanManagerId().getId() " + loanObj.getLoanMangerObj().getLoanManagerId().getId());

            List<LoanInstallementPlan> list = uniDB.searchByQuery("SELECT g FROM LoanInstallementPlan g WHERE g.loanManagerId.id='" + loanObj.getLoanMangerObj().getLoanManagerId().getId() + "'");
            System.out.println("!list.isEmpty() " + list.isEmpty());
            if (!list.isEmpty()) {
                Float paidCapital = 0.00f;
                loanInstallementPlanList = new ArrayList<>();
                for (LoanInstallementPlan loanInstallementPlan : list) {

                    String formattedDate = date.format(loanInstallementPlan.getRepaymentDate());
                    paidCapital = paidCapital + loanInstallementPlan.getPaidCapital();
                    loanInstallementPlanList.add(new UserLoanInstallementPlan(formattedDate, ComMethods.convertToTwoDecimalPoints(loanInstallementPlan.getOpeningBalance()), ComMethods.convertToTwoDecimalPoints(loanInstallementPlan.getPrincipalAmount()), ComMethods.convertToTwoDecimalPoints(loanInstallementPlan.getMonthlyInterest()), ComMethods.convertToTwoDecimalPoints(loanInstallementPlan.getMonthlyInterest() + loanInstallementPlan.getPrincipalAmount()), ComMethods.convertToTwoDecimalPoints(paidCapital)));
                }
            }

//        String basePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\documents\\loan_documents\\student_loan");
            String basePath = ComPath.basePath;
//        String basePath = "F:\\temco-bank-application-2024-07-08\\images";
            // If getRealPath returns null (like in some local dev environments), use absolute fallback for local testing

            if (!gurantorManagersList.isEmpty()) {
                System.out.println("i 1 " + i);

                for (GurantorManager gurantorManager : gurantorManagersList) {
                    System.out.println("gurantorManager gup id " + gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getId());
                    System.out.println("gurantorManager name " + gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName());
                    System.out.println("gurantorManager is active " + gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() + " & " + (gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() == 1));
                    if (gurantorManager.getGurantorCountId().getValue().equals("1") && gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() == 1) {

                        ffirstName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName();
                        flastName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
                        fnic = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();

                        List<GuarantorDocuments> gurantorDocuments = uniDB.searchByQuery("SELECT g FROM GuarantorDocuments g WHERE g.gurantorManagerId.id='" + gurantorManager.getId() + "'");

                        if (!gurantorDocuments.isEmpty()) {
                            int x = 0;
                            for (GuarantorDocuments gurantorDocument : gurantorDocuments) {
                                if (x == 0) {
                                    ffrontNic = basePath + "/" + loanObj.getLoanMangerObj().getLoanManagerId().getVerificationToke() + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
//                            ffrontNic = "F:/temco-bank-application-2024-07-08/nilupul copy/temco_loan_system-1.0/target/temco_loan_system-1.0/documents/loan_documents/student_loan/WKXvsjUCrFanU849qbJIbSdlyBQ3mZu8/2011320000000100/temcoln909d9035-41a9-4ee5-ad97-0c41010fa02520240924091318.png";
                                    System.out.println("url 1 " + ffrontNic);
                                    x++;
                                } else if (x == 1) {
                                    firstBackNic = basePath + "/" + loanObj.getLoanMangerObj().getLoanManagerId().getVerificationToke() + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
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

                                ffrontNic = basePath + "/" + loanObj.getLoanMangerObj().getLoanManagerId().getVerificationToke() + "/" + gurantorMemberNo + "/" + uud.get(0).getFileUrl();
//                            ffrontNic = "F:/temco-bank-application-2024-07-08/nilupul copy/temco_loan_system-1.0/target/temco_loan_system-1.0/documents/loan_documents/student_loan/WKXvsjUCrFanU849qbJIbSdlyBQ3mZu8/2011320000000100/temcoln909d9035-41a9-4ee5-ad97-0c41010fa02520240924091318.png";
                                System.out.println("UniversalUserDocument url 1 " + ffrontNic);
                                firstBackNic = basePath + "/" + loanObj.getLoanMangerObj().getLoanManagerId().getVerificationToke() + "/" + gurantorMemberNo + "/" + uud.get(1).getFileUrl();
                                System.out.println("UniversalUserDocument url 2 " + firstBackNic);
                                i++;
                                System.out.println("i " + i);
                            }
                        }
                    } else {
                        if (gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() == 1) {
                            System.out.println("second guarantor documents are empty.checking in universal documents");
                            sfirstName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName();
                            slastName = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
                            snic = gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic();

                            System.out.println("gurantor manager id " + gurantorManager.getId());
                            List<GuarantorDocuments> gurantorDocuments = uniDB.searchByQuery("SELECT g FROM GuarantorDocuments g WHERE g.gurantorManagerId.id='" + gurantorManager.getId() + "'");
                            System.out.println("gurantorDocuments " + gurantorDocuments.isEmpty());
                            if (!gurantorDocuments.isEmpty()) {
                                int x = 0;
                                for (GuarantorDocuments gurantorDocument : gurantorDocuments) {
                                    if (x == 0) {
                                        sfrontNic = basePath + "/" + loanObj.getLoanMangerObj().getLoanManagerId().getVerificationToke() + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
                                        System.out.println("UniversalUserDocument url 1 s" + sfrontNic);
                                        x++;
                                        System.out.println("x " + x);
                                    } else if (x == 1) {
                                        secondBackNic = basePath + "/" + loanObj.getLoanMangerObj().getLoanManagerId().getVerificationToke() + "/" + gurantorDocument.getGurantorManagerId().getLoanApplicantAndGurantorsId().getMemberId().getMembershipNo() + "/" + gurantorDocument.getUrl();
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
                                    sfrontNic = basePath + "/" + loanObj.getLoanMangerObj().getLoanManagerId().getVerificationToke() + "/" + gurantorMemberNo + "/" + uud.get(2).getFileUrl();
//                            ffrontNic = "F:/temco-bank-application-2024-07-08/nilupul copy/temco_loan_system-1.0/target/temco_loan_system-1.0/documents/loan_documents/student_loan/WKXvsjUCrFanU849qbJIbSdlyBQ3mZu8/2011320000000100/temcoln909d9035-41a9-4ee5-ad97-0c41010fa02520240924091318.png";
                                    System.out.println("url 1 " + ffrontNic);

                                    secondBackNic = basePath + "/" + loanObj.getLoanMangerObj().getLoanManagerId().getVerificationToke() + "/" + gurantorMemberNo + "/" + uud.get(3).getFileUrl();
                                    System.out.println("url 2 " + firstBackNic);
                                    i++;
                                    System.out.println("i " + i);
                                }
                            }
                        }
                    }
                }
            }
            loadComments();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadComments() {

        commentType = new ArrayList<>();

        commentType.add(new SelectItem("0", "Select"));
        List<CommentType> commentsList = uniDB.findAll(CommentType.class);

        for (CommentType comment : commentsList) {
            commentType.add(new SelectItem(comment.getId(), comment.getComment()));
        }

    }

    public class UserLoanInstallementPlan {

        private String repaymentDate;
        private String openingBalance;
        private String principleAmount;
        private String MonthlyInterest;
        private String monthlyInstallment;
        private String paidCapital;

        public UserLoanInstallementPlan(String repaymentDate, String openingBalance, String principleAmount, String MonthlyInterest, String monthlyInstallment, String paidCapital) {
            this.repaymentDate = repaymentDate;
            this.openingBalance = openingBalance;
            this.principleAmount = principleAmount;
            this.MonthlyInterest = MonthlyInterest;
            this.monthlyInstallment = monthlyInstallment;
            this.paidCapital = paidCapital;
        }

        public String getRepaymentDate() {
            return repaymentDate;
        }

        public void setRepaymentDate(String repaymentDate) {
            this.repaymentDate = repaymentDate;
        }

        public String getOpeningBalance() {
            return openingBalance;
        }

        public void setOpeningBalance(String openingBalance) {
            this.openingBalance = openingBalance;
        }

        public String getPrincipleAmount() {
            return principleAmount;
        }

        public void setPrincipleAmount(String principleAmount) {
            this.principleAmount = principleAmount;
        }

        public String getMonthlyInterest() {
            return MonthlyInterest;
        }

        public void setMonthlyInterest(String MonthlyInterest) {
            this.MonthlyInterest = MonthlyInterest;
        }

        public String getMonthlyInstallment() {
            return monthlyInstallment;
        }

        public void setMonthlyInstallment(String monthlyInstallment) {
            this.monthlyInstallment = monthlyInstallment;
        }

        public String getPaidCapital() {
            return paidCapital;
        }

        public void setPaidCapital(String paidCapital) {
            this.paidCapital = paidCapital;
        }

    }

    public class Loan {

        private String LoanId;
        private String assoNo;
        private String name;
        private double loanAmount;
        private String loanAppliedDate;
        private String payOrderStatus;
        private String PayOrderDate;
        private double downPayment;
        private String lecturesStarted;
        private InterestManager loanMangerObj;

        public Loan(String LoanId, String assoNo, String name, double loanAmount, String loanAppliedDate, String payOrderStatus, String PayOrderDate, double downPayment, String lecturesStarted, InterestManager loanMangerObj) {
            this.LoanId = LoanId;
            this.assoNo = assoNo;
            this.name = name;
            this.loanAmount = loanAmount;
            this.loanAppliedDate = loanAppliedDate;
            this.payOrderStatus = payOrderStatus;
            this.PayOrderDate = PayOrderDate;
            this.downPayment = downPayment;
            this.lecturesStarted = lecturesStarted;
            this.loanMangerObj = loanMangerObj;
        }

        public String getLoanId() {
            return LoanId;
        }

        public void setLoanId(String LoanId) {
            this.LoanId = LoanId;
        }

        public String getAssoNo() {
            return assoNo;
        }

        public void setAssoNo(String assoNo) {
            this.assoNo = assoNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getLoanAmount() {
            return loanAmount;
        }

        public void setLoanAmount(double loanAmount) {
            this.loanAmount = loanAmount;
        }

        public String getLoanAppliedDate() {
            return loanAppliedDate;
        }

        public void setLoanAppliedDate(String loanAppliedDate) {
            this.loanAppliedDate = loanAppliedDate;
        }

        public String getPayOrderStatus() {
            return payOrderStatus;
        }

        public void setPayOrderStatus(String payOrderStatus) {
            this.payOrderStatus = payOrderStatus;
        }

        public String getPayOrderDate() {
            return PayOrderDate;
        }

        public void setPayOrderDate(String PayOrderDate) {
            this.PayOrderDate = PayOrderDate;
        }

        public double getDownPayment() {
            return downPayment;
        }

        public void setDownPayment(double downPayment) {
            this.downPayment = downPayment;
        }

        public String getLecturesStarted() {
            return lecturesStarted;
        }

        public void setLecturesStarted(String lecturesStarted) {
            this.lecturesStarted = lecturesStarted;
        }

        public InterestManager getLoanMangerObj() {
            return loanMangerObj;
        }

        public void setLoanMangerObj(InterestManager loanMangerObj) {
            this.loanMangerObj = loanMangerObj;
        }

    }

    public int getLoanRequests() {
        return loanRequests;
    }

    public void setLoanRequests(int loanRequests) {
        this.loanRequests = loanRequests;
    }

    public double getTotalRequestedLoanAmount() {
        return totalRequestedLoanAmount;
    }

    public void setTotalRequestedLoanAmount(double totalRequestedLoanAmount) {
        this.totalRequestedLoanAmount = totalRequestedLoanAmount;
    }

    public long getTotalSubmittedPayOrders() {
        return totalSubmittedPayOrders;
    }

    public void setTotalSubmittedPayOrders(long totalSubmittedPayOrders) {
        this.totalSubmittedPayOrders = totalSubmittedPayOrders;
    }

    public int getClassAttenededRequestors() {
        return classAttenededRequestors;
    }

    public void setClassAttenededRequestors(int classAttenededRequestors) {
        this.classAttenededRequestors = classAttenededRequestors;
    }

    public Loan getLoanObj() {
        return loanObject;
    }

    public void setLoanObj(Loan loanObject) {
        this.loanObject = loanObject;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFirstBackNic() {
        return firstBackNic;
    }

    public void setFirstBackNic(String firstBackNic) {
        this.firstBackNic = firstBackNic;
    }

    public List<SelectItem> getCommentType() {
        return commentType;
    }

    public void setCommentType(List<SelectItem> commentType) {
        this.commentType = commentType;
    }

    public String getSelectedComment() {
        return selectedComment;
    }

    public void setSelectedComment(String selectedComment) {
        this.selectedComment = selectedComment;
    }

    public String getLoanBrandName() {
        return loanBrandName;
    }

    public void setLoanBrandName(String loanBrandName) {
        this.loanBrandName = loanBrandName;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getMonthlyInstallement() {
        return monthlyInstallement;
    }

    public void setMonthlyInstallement(String monthlyInstallement) {
        this.monthlyInstallement = monthlyInstallement;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public List<GurantorManager> getGurantorManagersList() {
        return gurantorManagersList;
    }

    public void setGurantorManagersList(List<GurantorManager> gurantorManagersList) {
        this.gurantorManagersList = gurantorManagersList;
    }

    public UniDBLocal getUniDB() {
        return uniDB;
    }

    public void setUniDB(UniDBLocal uniDB) {
        this.uniDB = uniDB;
    }

    public boolean isViewButtons() {
        return viewButtons;
    }

    public void setViewButtons(boolean viewButtons) {
        this.viewButtons = viewButtons;
    }

    public List<UserLoanInstallementPlan> getLoanInstallementPlanList() {
        return loanInstallementPlanList;
    }

    public void setLoanInstallementPlanList(List<UserLoanInstallementPlan> loanInstallementPlanList) {
        this.loanInstallementPlanList = loanInstallementPlanList;
    }

    public double getTotalDuesToCollected() {
        return totalDuesToCollected;
    }

    public void setTotalDuesToCollected(double totalDuesToCollected) {
        this.totalDuesToCollected = totalDuesToCollected;
    }

    public String getAggregateDisbursementToJIAT() {
        return aggregateDisbursementToJIAT;
    }

    public void setAggregateDisbursementToJIAT(String aggregateDisbursementToJIAT) {
        this.aggregateDisbursementToJIAT = aggregateDisbursementToJIAT;
    }

    public Loan getLoanObject() {
        return loanObject;
    }

    public void setLoanObject(Loan loanObject) {
        this.loanObject = loanObject;
    }

    public double getCumulativeDisbursementToJIAT() {
        return cumulativeDisbursementToJIAT;
    }

    public void setCumulativeDisbursementToJIAT(double cumulativeDisbursementToJIAT) {
        this.cumulativeDisbursementToJIAT = cumulativeDisbursementToJIAT;
    }

    public List<Loan> getFilteredLoans() {
        return filteredLoans;
    }

    public void setFilteredLoans(List<Loan> filteredLoans) {
        this.filteredLoans = filteredLoans;
    }

    public String getGlobalFilter() {
        return globalFilter;
    }

    public void setGlobalFilter(String globalFilter) {
        this.globalFilter = globalFilter;
    }

    public int getTotalLoanApplicants() {
        return totalLoanApplicants;
    }

    public void setTotalLoanApplicants(int totalLoanApplicants) {
        this.totalLoanApplicants = totalLoanApplicants;
    }

}
