/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco.templates;

/**
 *
 * @author USER
 */
public class LoanRequestPortalEmail {

    String template = "";

    String portalUrl = "https://lending.temcobank.com/user/login.xhtml";

    public String PortalCreatedEmail(String name) {

        try {
            template = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    <title>Your TEMCO Bank Portal is Created!</title>\n"
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
                    + "            padding: 20px;\n"
                    + "            text-align: center;\n"
                    + "            border-bottom: 1px solid #ddd;\n"
                    + "        }\n"
                    + "        .header h1 {\n"
                    + "            margin: 0;\n"
                    + "            font-size: 24px;\n"
                    + "            color: #333;\n"
                    + "        }\n"
                    + "        .content {\n"
                    + "            padding: 20px;\n"
                    + "            line-height: 1.6;\n"
                    + "            font-size: 16px;\n"
                    + "            color: #333;\n"
                    + "        }\n"
                    + "        .cta-button {\n"
                    + "            display: inline-block;\n"
                    + "            margin: 20px 0;\n"
                    + "            padding: 10px 20px;\n"
                    + "            text-decoration: none;\n"
                    + "            border-radius: 5px;\n"
                    + "            text-align: center;\n"
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
                    + "            <h1>Your TEMCO Bank Portal is Created!</h1>\n"
                    + "        </div>\n"
                    + "        <div class=\"content\">\n"
                    + "            <p>Dear "
                    + name
                    + ",</p>\n"
                    + "            <p>We are pleased to inform you that your loan Application details have been saved, and a personalised TEMCO Bank portal has been created for you. This portal will allow you to edit and update your personal information and track the progress of your loan status.</p>\n"
                    + "            <h2>Portal Access</h2>\n"
                    + "            <p>Sign in using your Java Institute Student Portal credentials (Username and Password). The portal will help you with:</p>\n"
                    + "            <ul>\n"
                    + "                <li>Edit or revise your loan* application information within the next 7 days</li>\n"
                    + "                <li>Make amendments as requested by TEMCO Bank, if necessary</li>\n"
                    + "                <li>Follow your loan* application approval process</li>\n"
                    + "                <li>Receive notifications</li>\n"
                    + "            </ul>\n"
                    + "            <p><strong>To access your portal, please click on the link below:</strong></p>\n"
                    + "            <a class=\"cta-button\" style=\"text-decoration: none;background-color: #007bff;color:#ffffff;font-size:1.2em\" href=\""
                    + portalUrl
                    + "\">Access Your TEMCO Bank Portal</a>\n"
                    + "            <p>Need more information or Assistance?</p>\n"
                    + "            <p>If you have any issues logging in or need assistance, please feel free to contact our support team at <a href=\"mailto:support@temcobank.com\">support@temcobank.com</a> or call Ravindu on 0762528351.</p>\n"
                    + "            <p>We are here to assist you throughout the process. Thank you for choosing our services!</p>\n"
                    + "        </div>\n"
                    + "        <div class=\"footer\">\n"
                    + "            <p>Warm regards,</p>\n"
                    + "            <p>TEMCO Bank</p>\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "</body>\n"
                    + "</html>";
        } catch (Exception e) {

        }
        return template;
    }

}
