/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.CustomerResponseHistory;
import lk.exon.temco_loan_system.entity.LoanCustomer;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.OfferManager;
import lk.exon.temco_loan_system.entity.ResponseStatus;

/**
 *
 * @author USER
 */
@Named
@ViewScoped
public class loanDetails implements Serializable {

    private List<TableDetails> tableDetailses = new ArrayList<>();
    private String loanIdPara;
    private String loanId;

    private boolean checkbox = false;

    private String calculateDownPaymentDateAndMemberShipfeesUploadingDeadlineDate;

    private double loanInterest = 6;

    private LoanManager loanManager;

    @EJB
    private UniDBLocal UniDB;

    @Inject
    LoanRequestForm LoanRequestForm;

    @PostConstruct
    public void iniit() {
        initializedMethod();
        loadLoanDetailsTable();
    }

    public void initializedMethod() {
        System.out.println("initialize method");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

        loanIdPara = params.get("l");

        System.out.println("loand id para " + loanIdPara);
        try {
            if (loanIdPara != null) {
                loanManager = getVerificationToken(loanIdPara);
                if (loanManager != null) {
                    loanId = loanManager.getReferenceId();
                    System.out.println("loan id " + loanId);
                    updateOfferManager();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOfferManager() {
        Date date = new Date();
        System.out.println("updateOfferManager");
        List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + LoanRequestForm.getNic() + "'");
        System.out.println("nic " + LoanRequestForm.getNic());
        if (!loanCustomer.isEmpty()) {
            System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
            List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
            System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
            if (!offerManager.isEmpty()) {
                List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='13'");
                if (crhList.isEmpty()) {
                    CustomerResponseHistory crh = new CustomerResponseHistory();
                    crh.setDate(date);
                    crh.setOfferManagerId(offerManager.get(0));
                    crh.setResponseStatusId((ResponseStatus) UniDB.find(13, ResponseStatus.class));
                    UniDB.create(crh);
                }
            }
        }
    }

    public String calculateDeadlineDates(Date loanRequestedDate, int dateCount) {

        LocalDate localLoanRequestedDate = convertToLocalDate(loanRequestedDate);

        LocalDate seventhDay = calculateDates(localLoanRequestedDate, dateCount);

        return new SimpleDateFormat("yyyy-MM-dd").format(Date.from(seventhDay.atStartOfDay(ZoneId.systemDefault()).toInstant()));

    }

    public static LocalDate calculateDates(LocalDate startDate, int dateCount) {
        LocalDate resultDate = startDate;
        int daysAdded = 0;

        while (daysAdded < dateCount) {
            resultDate = resultDate.plusDays(1);
            // Check if it's not a Sunday
            if (resultDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                daysAdded++;
            }
        }

        return resultDate;
    }

    private static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LoanManager getVerificationToken(String token) {
        List<LoanManager> l = UniDB.searchByQuery("SELECT  g FROM LoanManager g WHERE g.verificationToke='" + token + "' ");
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    public void nextPage() {

        try {

            Date dateTwo = new Date();
            System.out.println("updateOfferManager");
            List<LoanCustomer> loanCustomer = UniDB.searchByQuery("SELECT g FROM LoanCustomer g WHERE g.nic='" + LoanRequestForm.getNic() + "'");
            System.out.println("nic " + LoanRequestForm.getNic());
            if (!loanCustomer.isEmpty()) {
                System.out.println("loanCustomer.isEmpty() " + loanCustomer.isEmpty());
                List<OfferManager> offerManager = UniDB.searchByQuery("SELECT g FROM OfferManager g WHERE g.loanCustomerId.id='" + loanCustomer.get(0).getId() + "' AND g.loanOfferId.id='1'");
                System.out.println("offerManager.isEmpty() " + offerManager.isEmpty());
                if (!offerManager.isEmpty()) {
                    List<CustomerResponseHistory> crhList = UniDB.searchByQuery("SELECT g FROM CustomerResponseHistory g WHERE g.offerManagerId.id='" + offerManager.get(0).getId() + "' AND g.responseStatusId.id='14'");
                    if (crhList.isEmpty()) {
                        CustomerResponseHistory crh = new CustomerResponseHistory();
                        crh.setDate(dateTwo);
                        crh.setOfferManagerId(offerManager.get(0));
                        crh.setResponseStatusId((ResponseStatus) UniDB.find(14, ResponseStatus.class));
                        UniDB.create(crh);

                        CustomerResponseHistory crhCompleted = new CustomerResponseHistory();
                        crhCompleted.setDate(dateTwo);
                        crhCompleted.setOfferManagerId(offerManager.get(0));
                        crhCompleted.setResponseStatusId((ResponseStatus) UniDB.find(15, ResponseStatus.class));
                        UniDB.create(crhCompleted);

                    }
                }
            }

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/view/success.xhtml");
            facesContext.responseComplete();
        } catch (Exception e) {
        }

//        System.out.println("loan id para");
//        try {
//            if (loanIdPara != null) {
//                System.out.println("loan id para 2");
//                FacesMessage msg;
//                if (checkTermAndConditions()) {
//                    if (checkbox) {
//                        FacesContext facesContext = FacesContext.getCurrentInstance();
//                        ExternalContext externalContext = facesContext.getExternalContext();
//                        externalContext.redirect(externalContext.getRequestContextPath() + "/tasks/gurantor-details.xhtml?l=" + loanIdPara);
//                        facesContext.responseComplete();
//                    } else {
//                        System.out.println("loan id para4");
//                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "To move next steps you need to agree with term and conditions");
//                        FacesContext.getCurrentInstance().addMessage("growl", msg);
//                    }
//                } else {
//                    System.out.println("loan id para3");
//                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "To move next steps you need to agree with Acknowledgment and Assurances");
//                    FacesContext.getCurrentInstance().addMessage("growl", msg);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public boolean checkTermAndConditions() {
        boolean b = false;
        for (TableDetails detail : tableDetailses) {
            int i = 0;
            i++;
            System.out.println("i " + i);
            System.out.println("isChecked " + detail.isChecked());
            // If any checkbox is not checked, return false
            if (!detail.isChecked()) {
                return false;
            } else {
                b = true;
            }
        }
        return b;
    }

    public void loadLoanDetailsTable() {

        tableDetailses.clear();

//        Date date = loanManager.getDate();
        Date date = new Date();

        double value = (loanManager.getMonthlyInstallement() * 3);
        double roundedValue = Math.round(value / 100) * 100;

        tableDetailses.add(new TableDetails("I hereby unconditionally agree to abide by all terms and conditions set forth by TEMCO Bank time to time pertaining to my loan application and any subsequent agreements or transactions.", false));
        tableDetailses.add(new TableDetails("I agree to pay the sum o Rs. " + (roundedValue) + " equivalent to the last three months' loan installments, on or before " + calculateDeadlineDates(date, 14), false));
        tableDetailses.add(new TableDetails("I agree to upload images of the required documents as specified before " + calculateDeadlineDates(date, 60), false));
    }

    public class TableDetails implements Serializable {

        private String text;
        private boolean checkBoxes;

        public TableDetails(String text, boolean checked) {
            this.text = text;
            this.checkBoxes = checked;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isChecked() {
            return checkBoxes;
        }

        public void setChecked(boolean checked) {
            this.checkBoxes = checked;
        }

    }

    public List<TableDetails> getTableDetailses() {
        return tableDetailses;
    }

    public void setTableDetailses(List<TableDetails> tableDetailses) {
        this.tableDetailses = tableDetailses;
    }

    public String getCalculateDownPaymentDateAndMemberShipfeesUploadingDeadlineDate() {
        return calculateDownPaymentDateAndMemberShipfeesUploadingDeadlineDate;
    }

    public void setCalculateDownPaymentDateAndMemberShipfeesUploadingDeadlineDate(String calculateDownPaymentDateAndMemberShipfeesUploadingDeadlineDate) {
        this.calculateDownPaymentDateAndMemberShipfeesUploadingDeadlineDate = calculateDownPaymentDateAndMemberShipfeesUploadingDeadlineDate;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

}
