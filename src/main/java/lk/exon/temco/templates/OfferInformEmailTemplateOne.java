/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco.templates;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

/**
 *
 * @author USER
 */
public class OfferInformEmailTemplateOne {

    String template = "";

    String url = "";

    public String emailTemplate(String studentName, String securityToken) {

        try {
//            String path = "https://lending.temcobank.com/tasks/loan-request-form.xhtml?en=" + securityToken;
            String path = "https://lending.temcobank.com/view/loan-selection.xhtml?en=" + securityToken;
            String emailServiceUnsubscriptionService = "https://lending.temcobank.com/view/email-service-unsubscription.xhtml?en=" + securityToken;
            template = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    <title>Samma Upakara Higher Education Investment Plan</title>\n"
                    + "    <style>\n"
                    + "        body {\n"
                    + "            font-family: Arial, sans-serif;\n"
                    + "            margin: 0;\n"
                    + "            padding: 0;\n"
                    + "            background-color: #f4f4f4;\n"
                    + "        }\n"
                    + "        .container {\n"
                    + "            max-width: 600px;\n"
                    + "            margin: 20px auto;\n"
                    + "            background-color: #ffffff;\n"
                    + "            border-radius: 8px;\n"
                    + "            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\n"
                    + "            overflow: hidden;\n"
                    + "        }\n"
                    + "        .header {\n"
                    + "            background-color: #0336FE;\n"
                    + "            color: white;\n"
                    + "            padding: 20px;\n"
                    + "            text-align: center;\n"
                    + "        }\n"
                    + "        .content {\n"
                    + "            padding: 20px;\n"
                    + "            line-height: 1.6;\n"
                    + "        }\n"
                    + "        .cta-button {\n"
                    + "            display: inline-block;\n"
                    + "            margin: 20px 0;\n"
                    + "            padding: 10px 20px;\n"
                    + "            border-radius: 5px;\n"
                    + "        }\n"
                    + "        .footer {\n"
                    + "            background-color: #f1f1f1;\n"
                    + "            padding: 10px;\n"
                    + "            text-align: center;\n"
                    + "            font-size: 12px;\n"
                    + "            color: #666;\n"
                    + "        }\n"
                    + "    </style>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "    <div class=\"container\">\n"
                    + "        <div class=\"header\">\n"
                    + "            <h1>TEMCO Samma Upakara Higher Education Investment Plan</h1>\n"
                    + "        </div>\n"
                    + "        <div class=\"content\">\n"
                    + "            <p>Dear Student,</p>\n"
                    + "            <p>You're studying hard to build a brighter future! However, did you know that you are at risk? If your course fees are not paid on time, your Graduate Program may be discontinued. How will you reach your dreams then?</p>\n"
                    + "            <p>We are here to assist you!</p>\n"
                    + "            <h2>Samma Upakara Higher Education Investment Plan</h2>\n"
                    + "            <p>Our program helps you overcome financial barriers to education and paves the way for a brighter future.</p>\n"
                    + "            <ul>\n"
                    + "                <li>Investments up to one million rupees without the need for collateral.</li>\n"
                    + "                <li>Low interest rate of 6% per annum.</li>\n"
                    + "                <li>Flexible repayment plans tailored to your individual circumstances.</li>\n"
                    + "                <li>Easy online application process.</li>\n"
                    + "            </ul>\n"
                    + "            <p>Upon submission of the guarantor's details along with the completed investment application, an interim facility can be provided to pursue your degree course, subject to fulfilling all requirements necessary for investment approval.</p>\n"
                    + "            <p>Maintain your credit history for more financial freedom tomorrow! Beyond student investment, TEMCO Bank provides extended financial support for customers with a history of timely payments, including loans for laptops, entrepreneurship, vehicles, housing, and more in the future.</p>\n"
                    + "            <p>Don't let financial constraints hold you back!</p>\n"
                    + "            <a class=\"cta-button\" style=\"text-decoration: none;background-color: #28a745;color:#ffffff;font-size:1.2em\" href=\""
                    + path
                    + "\">Click Here to Apply for the Investment</a>\n"
                    + "        </div>\n"
                    + "        <div class=\"footer\">\n"
                    + "            <p>Need more information or assistance?</p>\n"
                    + "            <p>Tele: 077 757 0403</p>\n"
                    + "            <p>Email: <a href=\"mailto:support@temcobank.com\">support@temcobank.com</a></p>\n"
                    + "            <p>Terms and Conditions apply.</p>\n"
                    + "<p>If you no longer wish to receive these emails, click here to <a href=\""
                    + emailServiceUnsubscriptionService
                    + "\">unsubscribe</a>.</p>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "</body>\n"
                    + "</html>";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;

    }

}
