/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package lk.exon.temco_loan_system.service;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import lk.exon.temco_loan_system.common.UniDBLocal;
import lk.exon.temco_loan_system.entity.LoanManager;
import lk.exon.temco_loan_system.entity.LoanStatusManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lk.exon.temco_loan_system.common.ComLib;
import lk.exon.temco_loan_system.common.ComPath;
import lk.exon.temco_loan_system.entity.BranchHasFactoringFee;
import lk.exon.temco_loan_system.entity.LoanApplicantHasBranch;
import lk.exon.temco_loan_system.entity.LoanInstallementPlan;
import lk.exon.temco_loan_system.entity.LoanStatus;
import lk.exon.temco_loan_system.entity.PayOrderSettlementGuide;
import lk.exon.temco_loan_system.entity.RepaymentPeriod;
import static lk.exon.temco_loan_system.service.LoanCalculator.calculateMonthlyInstallment;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author USER
 */
@Named
@ViewScoped
public class PayOrderSubmittedSuccess implements Serializable {

    private String loanId;
    private String loanIdPara;
    private LoanManager loanManager;
    Date date;

    private String actualMonthlyInstallement = "0.00";
    int actualMonth = 0;

    private double interestRate = 6;

    private boolean x = true;

    @EJB
    private UniDBLocal UniDB;

    @PostConstruct
    public void init() {
        try {
            if (x) {
                x = false;
                date = new Date();
                initializeData();
                sendPayOrderRequest(loanManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getNic(), loanManager.getLoanCapitalAmount());
                updateLoanManagerStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeData() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

        loanIdPara = params.get("l");

        try {
            if (loanIdPara != null) {
                loanManager = getVerificationToken(loanIdPara);
                System.out.println("loan manager gotted");
                if (loanManager != null) {
                    loanId = loanManager.getReferenceId();
                    System.out.println("set the referrence id");
                } else {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/view/error.xhtml");
                }
            } else {
                externalContext.redirect(externalContext.getRequestContextPath() + "/view/error.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String sendPayOrderRequest(String nic, double amount) throws Exception {
        System.out.println("");
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
            newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
            newBuilder.hostnameVerifier((hostname, session) -> true);

            OkHttpClient client = newBuilder.build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "stu_nic=" + nic + "&amount=" + amount);

            Request request = new Request.Builder()
                    .url(ComPath.promissoryNoteSubmittingUrl)
                    //                    .url("https://c9c8-2402-d000-a400-dbee-b50f-a793-eaa9-f7b8.ngrok-free.app/java-institute-web-portal/PayOrder")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            String outPut = "";
            if (response.isSuccessful()) {
                outPut = response.toString();
                System.out.println(response.body().string());
                System.out.println("Exon System Data Save Successful.");
            }
            response.close();

            return outPut;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public void calulateLoan() {
        try {
            System.out.println("loan");
            double loanAmount = loanManager.getLoanCapitalAmount();
            System.out.println("loanAmount " + loanAmount);
            int installmentPeriod = Integer.parseInt(loanManager.getRepaymentPeriodId().getPeriod());
            System.out.println("installmentPeriod " + installmentPeriod);
            double monthly_installement = loanManager.getMonthlyInstallement();
            System.out.println("installmentPeriod " + installmentPeriod);

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

                LoanInstallementPlan loanInstallementPlan = new LoanInstallementPlan();
                loanInstallementPlan.setRepaymentDate(new SimpleDateFormat("yyyy-MM-dd").parse(lastDay));
                loanInstallementPlan.setOpeningBalance((float) openingBalance);
                loanInstallementPlan.setPrincipalAmount((float) principalAmount);
                loanInstallementPlan.setMonthlyInterest((float) interest);
                loanInstallementPlan.setPaidCapital((float) paidCapital);
                loanInstallementPlan.setLoanManagerId(loanManager);
                UniDB.create(loanInstallementPlan);

                openingBalance -= principalAmount;
                months++;

                if (month == 12) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }
            }
//            actualMonth = months;
//            DecimalFormat decimalFormat = new DecimalFormat("#.00");
//            String formattedNumber = decimalFormat.format(monthly_installement);
//            actualMonthlyInstallement = formattedNumber;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLoanManagerStatus() {
        System.out.println("updateLoanManagerStatus called");
        List<LoanStatusManager> getLoanStatusManagers = UniDB.searchByQuery("SELECT g FROM LoanStatusManager g WHERE g.loanManagerId.id='" + loanManager.getId() + "' AND g.loanStatusId.id='3'");
        System.out.println("getLoanStatusManagers " + getLoanStatusManagers.size());
        System.out.println("getLoanStatusManagers.isEmpty() " + getLoanStatusManagers.isEmpty());
        if (getLoanStatusManagers.isEmpty()) {
            if (loanId != null && !loanId.equals("")) {

                LoanStatusManager loanStatusManager = new LoanStatusManager();
                loanStatusManager.setDate(date);
                loanStatusManager.setLoanManagerId(loanManager);
                loanStatusManager.setLoanStatusId((LoanStatus) UniDB.find(3, LoanStatus.class));
                UniDB.create(loanStatusManager);

                calulateLoan();

                PayOrderSettlementGuide payOrderSettlementGuide = new PayOrderSettlementGuide();
                payOrderSettlementGuide.setDate(date);
                payOrderSettlementGuide.setPrincipalAmount(loanManager.getLoanCapitalAmount());

                System.out.println("g.loanManagerId.id " + loanId);
                System.out.println("g.loanApplicantGurantorId.id " + loanManager.getLoanApplicantAndGurantorsId().getId());

                List<LoanApplicantHasBranch> loanApplicantHasBranchs = UniDB.searchByQuery("SELECT g FROM LoanApplicantHasBranch g WHERE g.loanManagerId.id='" + loanManager.getId() + "' AND g.loanApplicantGurantorId.id='" + loanManager.getLoanApplicantAndGurantorsId().getId() + "' ");

                int branchId = loanApplicantHasBranchs.get(0).getOrganizationBranchesId().getId();

                List<BranchHasFactoringFee> branchHasFactoringFees = UniDB.searchByQuery("SELECT g FROM BranchHasFactoringFee g WHERE g.organizationBranchesId.id='" + branchId + "' AND g.isActive='1' ");

                double factoring_fee = branchHasFactoringFees.get(0).getFactoringFeeId().getValue();

                double paybaleDiscountedAmount = (loanManager.getLoanCapitalAmount() * ((100 - factoring_fee) / 100));

                payOrderSettlementGuide.setPaybleDiscountedAmount(paybaleDiscountedAmount);
                payOrderSettlementGuide.setSettlementAmount(0.00);
                payOrderSettlementGuide.setBranchHasFactoringFeeId(branchHasFactoringFees.get(0));
                payOrderSettlementGuide.setLoanApplicantHasBranchId(loanApplicantHasBranchs.get(0));
                UniDB.create(payOrderSettlementGuide);

                System.out.println("updateLoanManagerStatus updated");
            }
        }
    }

    public LoanManager getVerificationToken(String token) {
        List<LoanManager> l = UniDB.searchByQuery("SELECT  g FROM LoanManager g WHERE g.verificationToke='" + token + "' ");
        if (l.isEmpty()) {

            return null;
        } else {
            System.out.println("returned object");
            return l.get(0);
        }
    }

}
