/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.exon.temco_loan_system.common.ComLib;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.LoanInstallementPlan;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.LoanStatusManager;

/**
 *
 * @author USER
 */
@Named
@LocalBean
@RequestScoped
public class SubmissionSuccessPage {

    private String loanId;
    private Double monthlyInstallement;

    private LoanManager loanManager;

    private String loanIdPara;

    private String documnetsSubmmittingDate;

    private String serviceChargePayableDate;

    private double payAmount;

    private String startMonth;
    private String endMonth;

    private ComLib ComLib;

    private List<TableView> tableList = new ArrayList<>();

    @EJB
    private UniDBLocal UniDB;

    @PostConstruct
    public void init() {
        initialize();
    }

    public void initialize() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

        loanIdPara = params.get("en");

        System.out.println("loand id para " + loanIdPara);
        try {
            if (loanIdPara != null) {
                loanManager = getVerificationToken(loanIdPara);
                if (loanManager != null) {
                    loanId = loanManager.getReferenceId();
                    System.out.println("loan id " + loanId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("loanManager != null " + (loanManager != null));
        if (loanManager != null) {
            payAmount = loanManager.getMonthlyInstallement() * 3;
            setDates();
            getLoanLastThreePaymentsDates();
        } else {
            System.out.println("loan manager null");
        }
        LoadTable();
    }

    public void LoadTable() {
        tableList.clear();
        tableList.add(new TableView("Loan application ", documnetsSubmmittingDate));
        tableList.add(new TableView("Guarantor statements", documnetsSubmmittingDate));
        tableList.add(new TableView("Loan Agreement Form", documnetsSubmmittingDate));
        tableList.add(new TableView("Loan voucher", documnetsSubmmittingDate));
    }

    public void getLoanLastThreePaymentsDates() {
        List<LoanInstallementPlan> loanInstallementPlansList = UniDB.searchByQuery("SELECT g FROM LoanInstallementPlan g WHERE g.loanManagerId.id='" + loanManager.getId() + "' ORDER BY g.repaymentDate DESC");
        System.out.println("loanInstallementPlansList.isEmpty() " + loanInstallementPlansList.size());
        if (!loanInstallementPlansList.isEmpty()) {
            int x = 0;
            for (LoanInstallementPlan loanInstallementPlan : loanInstallementPlansList) {
                if (x <= 3) {
                    double totalAmount = loanInstallementPlan.getMonthlyInterest() + loanInstallementPlan.getPrincipalAmount();
                    BigDecimal formattedAmount = new BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP);
                    formattedAmount = formattedAmount.setScale(2, RoundingMode.FLOOR);
                    if (formattedAmount.doubleValue() == loanManager.getMonthlyInstallement()) {
                        if (endMonth == null) {
                            endMonth = new SimpleDateFormat("yyyy-MM-dd").format(loanInstallementPlan.getRepaymentDate());
                            System.out.println(endMonth);
                            x = x + 1;
                        } else if (startMonth == null && x == 2) {
                            startMonth = new SimpleDateFormat("yyyy-MM-dd").format(loanInstallementPlan.getRepaymentDate());
                            x = x + 1;
                        }
                        x = x + 1;
                    }
                }
            }
        }
    }

    public void setDates() {
        System.out.println("set dates");
        List<LoanStatusManager> loanStatusManagerList = UniDB.searchByQuery("SELECT g FROM LoanStatusManager g WHERE g.loanManagerId.id='" + loanManager.getId() + "' AND g.loanStatusId.id='1' ");
        Date payOrderReleasedDate = null;
        if (!loanStatusManagerList.isEmpty() || loanStatusManagerList != null) {
            payOrderReleasedDate = loanStatusManagerList.get(0).getDate();
        }
        if (payOrderReleasedDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(payOrderReleasedDate);
            calendar.add(Calendar.DAY_OF_YEAR, 90);
            Date newDate = calendar.getTime();

            documnetsSubmmittingDate = new SimpleDateFormat("yyyy-MM-dd").format(newDate);
            serviceChargePayableDate = new SimpleDateFormat("yyyy-MM-dd").format(newDate);
            System.out.println("documnetsSubmmittingDate");
            System.out.println("serviceChargePayableDate");

        }
    }

    public LoanManager getVerificationToken(String token) {
        List<LoanManager> l = UniDB.searchByQuery("SELECT  g FROM LoanManager g WHERE g.verificationToke='" + token + "' ");
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getDocumnetsSubmmittingDate() {
        return documnetsSubmmittingDate;
    }

    public void setDocumnetsSubmmittingDate(String documnetsSubmmittingDate) {
        this.documnetsSubmmittingDate = documnetsSubmmittingDate;
    }

    public String getServiceChargePayableDate() {
        return serviceChargePayableDate;
    }

    public void setServiceChargePayableDate(String serviceChargePayableDate) {
        this.serviceChargePayableDate = serviceChargePayableDate;
    }

    public List<TableView> getTableList() {
        return tableList;
    }

    public void setTableList(List<TableView> tableList) {
        this.tableList = tableList;
    }

    public class TableView implements Serializable {

        private String name;
        private String date;

        public TableView(String name, String date) {
            this.name = name;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }

}
