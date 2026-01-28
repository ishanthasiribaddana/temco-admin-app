/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco.templates;

import java.text.DecimalFormat;
import java.util.List;
import lk.exon.temco_loan_system.entity.GurantorManager;
import lk.exon.temco_loan_system.entity.LoanManager;

/**
 *
 * @author USER
 */
public class AcceptanceEmail {

    String template = "";

    public String acceptanceEmailTemplate(String date, LoanManager loan_manager_obj, List<GurantorManager> gurantorManagerList, double downPayementAmount, String securityToken) {

        try {
            System.out.println("A");
            String loanApplicantFullName = loan_manager_obj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName() + " " + loan_manager_obj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
            String loanApplicantAddress = loan_manager_obj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getAddress1() + "," + loan_manager_obj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getAddress2() + "," + loan_manager_obj.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getAddress3();
            System.out.println("B");
            GurantorManager firstGuarantor = null;
            GurantorManager secondGuarantor = null;
            System.out.println("C");
            for (GurantorManager gurantorManager : gurantorManagerList) {
                if (gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() == 1 && firstGuarantor == null) {
                    firstGuarantor = gurantorManager;
                    System.out.println("C");
                } else if (gurantorManager.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getIsActive() == 1 && secondGuarantor == null) {
                    secondGuarantor = gurantorManager;
                    System.out.println("C");
                }
            }
            System.out.println("D");

            String firstGurantor = null;
            String secondGurantor = null;

            if (firstGurantor == null) {
                firstGurantor = "";
            } else {
                firstGurantor = firstGuarantor.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName() + " " + firstGuarantor.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();
            }

            if (secondGurantor == null) {
                secondGurantor = "";
            } else {
                secondGurantor = secondGuarantor.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getFirstName() + " " + secondGuarantor.getLoanApplicantAndGurantorsId().getMemberId().getGeneralUserProfileId().getLastName();

            }

            System.out.println("D");
            double value = (downPayementAmount);
            double roundedValue = Math.round(value / 100) * 100;

            double totalPayment = (3000 + 3000) + downPayementAmount;

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String formattedNumber = decimalFormat.format(totalPayment);

            String url2 = "https://lending.temcobank.com/user/login.xhtml";
            String url = "https://lending.temcobank.com/tasks/submit-pay-order.xhtml?l=" + securityToken;
            template = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    <title>Promissory Note Email Template</title>\n"
                    + "    <style>\n"
                    + "        body {\n"
                    + "            font-family: Arial, sans-serif;\n"
                    + "            margin: 0;\n"
                    + "            padding: 0;\n"
                    + "            background-color: #f8f8f8;\n"
                    + "            color: #333;\n"
                    + "        }\n"
                    + "        .email-container {\n"
                    + "            max-width: 800px;\n"
                    + "            margin: 20px auto;\n"
                    + "            background: #fff;\n"
                    + "            padding: 20px;\n"
                    + "            border-radius: 5px;\n"
                    + "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n"
                    + "        }\n"
                    + "        .header {\n"
                    + "            text-align: center;\n"
                    + "            padding-bottom: 10px;\n"
                    + "            border-bottom: 2px solid #ddd;\n"
                    + "            margin-bottom: 20px;\n"
                    + "        }\n"
                    + "        .header h1 {\n"
                    + "            font-size: 1.5rem;\n"
                    + "            color: #007bff;\n"
                    + "        }\n"
                    + "        .content {\n"
                    + "            padding: 20px;\n"
                    + "        }\n"
                    + "        .content h3 {\n"
                    + "            margin-top: 20px;\n"
                    + "            margin-bottom: 10px;\n"
                    + "        }\n"
                    + "        ul {\n"
                    + "            margin: 10px 0;\n"
                    + "            padding-left: 20px;\n"
                    + "        }\n"
                    + "        .footer {\n"
                    + "            text-align: center;\n"
                    + "            margin-top: 20px;\n"
                    + "            font-size: 0.9em;\n"
                    + "            color: #555;\n"
                    + "        }\n"
                    + "        a {\n"
                    + "            color: #007bff;\n"
                    + "            text-decoration: none;\n"
                    + "        }\n"
                    + "        a:hover {\n"
                    + "            text-decoration: underline;\n"
                    + "        }\n"
                    + "    </style>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "    <div class=\"email-container\">\n"
                    + "        <div class=\"header\">\n"
                    + "            <h1>Promissory Note (PN) for Conditional Enrollment in the International University Payment Program</h1>\n"
                    + "        </div>\n"
                    + "        <div class=\"content\">\n"
                    + "            <p>\n"
                    + "                I hereby accept the Promissory Note (PN), allowing me to attend the International University Payment program at the Java Institute until I fulfill the remaining prerequisites of my loan application.\n"
                    + "            </p>\n"
                    + "            <p>\n"
                    + "                <strong>Date:</strong> "
                    + date
                    + "<br>\n"
                    + "                <strong>Place:</strong> Online\n"
                    + "            </p>\n"
                    + "            <p>\n"
                    + "                I, <strong>"
                    + loanApplicantFullName
                    + "</strong>, residing at <strong>"
                    + loanApplicantAddress
                    + "</strong>, hereby promise to pay Java Institute for Advanced Technology Pvt Ltd the total sum of <strong><Insert Amount></strong> as payment for the full course fee for my graduate program.\n"
                    + "            </p>\n"
                    + "            <h3>Payment of Full Course Fee</h3>\n"
                    + "            <p>\n"
                    + "                I commit to arranging the necessary loan facility through TEMCO Bank Ltd and to pay the entire course fee to the Java Institute for Advanced Technology within 90 days from the date of this Promissory Note.\n"
                    + "            </p>\n"
                    + "            <h3>Payment of Loan Installments</h3>\n"
                    + "            <p>\n"
                    + "                I shall make the agreed monthly loan installment payments to TEMCO Bank Ltd on or before the due date as stipulated in the loan agreement.\n"
                    + "            </p>\n"
                    + "            <h3>Final Three Installments</h3>\n"
                    + "            <p>\n"
                    + "                I explicitly promise to pay the final three months' installments of the loan within 14 days to TEMCO Bank Ltd to demonstrate my repayment capacity, ensuring the loan facility is fully settled without default.\n"
                    + "            </p>\n"
                    + "            <p>\n"
                    + "                Failure to fulfill the terms stated herein shall constitute a breach of this Promissory Note, and I acknowledge that the Java Institute for Advanced Technology and/or TEMCO Bank may take any legal action permitted under applicable laws to recover the due amount.\n"
                    + "            </p>\n"
                    + "            <p><strong>Signed:</strong> "
                    + loanApplicantFullName
                    + "<br><strong>Date:</strong> "
                    + date
                    + "</p>\n"
                    + "            <h3>Witnesses</h3>\n"
                    + "            <p>\n"
                    + "                1. <strong>Name:</strong> "
                    + firstGurantor
                    + "<br>\n"
                    + "                <strong>Date:</strong> "
                    + date
                    + "<br>\n"
                    + "                2. <strong>Name:</strong> "
                    + secondGurantor
                    + "<br>\n"
                    + "                <strong>Date:</strong> "
                    + date
                    + "\n"
                    + "            </p>\n"
                    + "            <h3>Acknowledgment</h3>\n"
                    + "            <p>\n"
                    + "                By signing below, I affirm that I have read and understood the terms of this Promissory Note and agree to abide by them without reservation.\n"
                    + "            </p>\n"
                    + "            <p>\n"
                    + "                [<a href=\""
                    + url
                    + "\">PN Submission Link</a>]\n"
                    + "            </p>\n"
                    + "            <h3>Additional Requirements</h3>\n"
                    + "            <ul>\n"
                    + "                <li>You and your two guarantors must become members of TEMCO Bank by submitting the TEMCO Bank member registration forms (which have already been delivered to you).</li>\n"
                    + "                <li>Pay the following fees:\n"
                    + "                    <ul>\n"
                    + "                        <li>TEMCO Bank registration fees (for all three): Rs.1000 x 3 = Rs.3000</li>\n"
                    + "                        <li>Share purchase fees (all three): Rs.1000.00 x 3 = Rs.3000.00</li>\n"
                    + "                        <li>First Month loan installments: Rs. "
                    + roundedValue
                    + "</li>\n"
                    + "<li>(If you Already paid the registration fees,Share purchase fees you does not need to pay again )</li>\n"
                    + "                    </ul>\n"
                    + "                </li>\n"
                    + "            </ul>\n"
                    + "            <p>\n"
                    + "                <strong>Total amount to pay:</strong> Rs. "
                    + formattedNumber
                    + "\n"
                    + "            </p>\n"
                    + "            <p>\n"
                    + "                Please deposit this amount to the following bank account:\n"
                    + "            </p>\n"
                    + "            <p>\n"
                    + "                <strong>Account Name:</strong> TEMCO Development Banking Society Ltd<br>\n"
                    + "                <strong>Account Number:</strong> 0086-823 497<br>\n"
                    + "                <strong>Bank Name:</strong> Bank Of Ceylon<br>\n"
                    + "                <strong>Branch Name:</strong> Pelawatta\n"
                    + "            </p>\n"
                    + "            <p>\n"
                    + "                Within one day of paying the total amount, please submit/upload a clear image of the deposit slip through the link below in your TEMCO Bank portal:\n"
                    + "            </p>\n"
                    + "            <p>\n"
                    + "                [<a href=\""
                    + url2
                    + "\">Document Submission Link</a>]\n"
                    + "            </p>\n"
                    + "            <p>\n"
                    + "                Need more information or assistance? Call Thilan on 0762528351.\n"
                    + "            </p>\n"
                    + "        </div>\n"
                    + "        <div class=\"footer\">\n"
                    + "            <p>Thank you,<br>TEMCO Bank Team</p>\n"
                    + "            <p><em>*Terms and Conditions apply</em></p>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "</body>\n"
                    + "</html>";
            return template;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("print template " + template);
        return template;
    }

}
