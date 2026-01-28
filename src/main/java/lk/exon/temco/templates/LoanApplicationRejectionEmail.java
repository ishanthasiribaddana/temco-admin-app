/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco.templates;

import java.util.List;
import lk.exon.temco_loan_system.entity.CommentType;

/**
 *
 * @author USER
 */
public class LoanApplicationRejectionEmail {

    String template = "";

    public String loanApplicationRejectionEmail(String name, String reason) {

        try {
            template = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    <title>Notice of Inability to Process Loan Application</title>\n"
                    + "    <style>\n"
                    + "        body {\n"
                    + "            font-family: Arial, sans-serif;\n"
                    + "            color: #333333;\n"
                    + "            background-color: #f9f9f9;\n"
                    + "            margin: 0;\n"
                    + "            padding: 0;\n"
                    + "        }\n"
                    + "        .email-container {\n"
                    + "            max-width: 600px;\n"
                    + "            margin: 20px auto;\n"
                    + "            padding: 20px;\n"
                    + "            background-color: #ffffff;\n"
                    + "            border: 1px solid #dddddd;\n"
                    + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);\n"
                    + "        }\n"
                    + "        .email-header {\n"
                    + "            text-align: center;\n"
                    + "            padding-bottom: 20px;\n"
                    + "            border-bottom: 1px solid #dddddd;\n"
                    + "        }\n"
                    + "        .email-header h1 {\n"
                    + "            font-size: 24px;\n"
                    + "            color: #333333;\n"
                    + "            margin: 0;\n"
                    + "        }\n"
                    + "        .email-body {\n"
                    + "            padding: 20px 0;\n"
                    + "            line-height: 1.6;\n"
                    + "        }\n"
                    + "        .email-body p {\n"
                    + "            font-size: 16px;\n"
                    + "            color: #555555;\n"
                    + "            margin: 10px 0;\n"
                    + "        }\n"
                    + "        .email-body a {\n"
                    + "            color: #0073e6;\n"
                    + "            text-decoration: none;\n"
                    + "        }\n"
                    + "        .email-footer {\n"
                    + "            padding-top: 20px;\n"
                    + "            border-top: 1px solid #dddddd;\n"
                    + "            font-size: 14px;\n"
                    + "            color: #888888;\n"
                    + "            text-align: center;\n"
                    + "        }\n"
                    + "    </style>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "    <div class=\"email-container\">\n"
                    + "        <div class=\"email-header\">\n"
                    + "            <h1>Notice of Inability to Process Loan Application</h1>\n"
                    + "        </div>\n"
                    + "        <div class=\"email-body\">\n"
                    + "            <p>Dear "
                    + name
                    + ",</p>\n"
                    + "            <p>We are unable to process your loan* application due to the following issue/s:</p>\n"
                    + "            <p><strong>Issue/s:</strong> "
                    + reason
                    + "</p>\n"
                    + "            <p>To rectify the issue/s, please contact the following person within 7 days of this email:</p>\n"
                    + "            <p><strong>Contact:</strong><br>\n"
                    + "               Call Prasanna on 077 757 0403</p>\n"
                    + "            <p>Your application will be considered canceled if we do not hear from you within 7 days of this email.</p>\n"
                    + "            <p>Thank you,<br>TEMCO Bank Team</p>\n"
                    + "            <p><em>*Terms and Conditions apply</em></p>\n"
                    + "        </div>\n"
                    + "        <div class=\"email-footer\">\n"
                    + "            <p>&copy; TEMCO Bank. All rights reserv";
        } catch (Exception e) {

        }
        return template;
    }

}
