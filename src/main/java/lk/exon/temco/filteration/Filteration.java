/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco.filteration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nilupul Nethmina
 */
public class Filteration {
    
    public static String getFilteredUsername(String uname) {
        //uname = uname.replaceAll("\\W+", "");
        uname = uname.replace("'", "");
        uname = uname.replace("<", "");
        uname = uname.replace(">", "");
        uname = uname.replace("(", "");
        uname = uname.replace(")", "");
        uname = uname.replace("\"", "");
//        uname = uname.replace(".", "");
        uname = uname.replace(",", "");
        uname = uname.replace("`", "");
        uname = uname.replace("\t", "");
        return uname;
    }

    public static String getFilteredUsernameForUpload(String uname) {
        uname = uname.replaceAll("\\W+", "");
        uname = uname.replace("'", "");
        uname = uname.replace("<", "");
        uname = uname.replace(">", "");
        uname = uname.replace("(", "");
        uname = uname.replace(")", "");
        uname = uname.replace("\"", "");
        uname = uname.replace(".", "");
        uname = uname.replace(",", "");
        uname = uname.replace("`", "");
        uname = uname.replace("\t", "");
        return uname;
    }

    public static String getFilteredFilename(String filename) {
        filename = filename.replace("\\", "");
        filename = filename.replace("/", "");
        filename = filename.replace(",", "");
        filename = filename.replace("..", "");
        filename = filename.replace("...", "");
        filename = filename.replace("$", "");
        filename = filename.replace("#", "");
        filename = filename.replace("%", "");
        filename = filename.replace("{", "");
        filename = filename.replace("}", "");
        filename = filename.replace("\"", "");
        filename = filename.replace("'", "");
        filename = filename.replace("<", "");
        filename = filename.replace(">", "");
        filename = filename.replace("</", "");
        filename = filename.replace("!", "");
        filename = filename.replace("&&", "");
        filename = filename.replace("|", "");
        filename = filename.replace("||", "");
        filename = filename.replace(";", "");
        return filename;
    }

    public static String getFilteredFilename2(String filename) {
        filename = filename.replace("\\", "");
        filename = filename.replace("/", "");
        filename = filename.replace(",", "");
        filename = filename.replace("..", "");
        filename = filename.replace("...", "");
        filename = filename.replace("$", "");
        filename = filename.replace("#", "");
        filename = filename.replace("%", "");
        filename = filename.replace("{", "");
        filename = filename.replace("}", "");
        filename = filename.replace("\"", "");
        filename = filename.replace("'", "");
        filename = filename.replace("<", "");
        filename = filename.replace(">", "");
        filename = filename.replace("</", "");
        filename = filename.replace("!", "");
        filename = filename.replace("&", "");
        filename = filename.replace("&&", "");
        filename = filename.replace("|", "");
        filename = filename.replace("||", "");
        filename = filename.replace(";", "");
        return filename;
    }

    public static String getFilteredUrl(String url) {
         url = url.replace("\\", "");
        url = url.replace("\"", "");
        url = url.replace(",", "");
        url = url.replace("..", "");
        url = url.replace("'", "");
        url = url.replace("#", "");
        url = url.replace("%", "");
        url = url.replace("<", "");
        url = url.replace(">", "");
        url = url.replace("</", "");
        return url;
    }

    public static String getFilteredNIC(String nic) {
        nic = nic.replaceAll("\\D+", "");
        if (nic.length() == 9) {
            nic = nic + "V";
        } else if (nic.length() == 12) {
            nic = nic;
        }
        return nic;
    }

    public static String getFilteredNumber(String num) {
        num = num.replaceAll("\\D+", "");
        return num;
    }

    public static int getFilteredNumberInt(String num) {
        num = num.replaceAll("\\D+", "");
        return Integer.parseInt(num);
    }

    public static String getFilteredPIN(String num) {
        num = num.replaceAll("\\D+", "");
        return num;
    }

    public static int getFilteredPINInt(String num) {
        num = num.replaceAll("\\D+", "");
        return Integer.parseInt(num);
    }

    public static String getFilteredString(String searchString) {
        searchString = searchString.replace("'", "");
        searchString = searchString.replace("<", "");
        searchString = searchString.replace(">", "");
        searchString = searchString.replace("(", "");
        searchString = searchString.replace(")", "");
        searchString = searchString.replace("\"", "");
        searchString = searchString.replace(".", "");
        searchString = searchString.replace(",", "");
        return searchString;
    }

    public static boolean validatePassword(String password) {
        int passlen = password.length();
        boolean validity;
//        System.out.println(passlen);
        int specialCharCount = password.replaceAll("\\w+", "").length();
        int DigitCount = password.replaceAll("\\D+", "").length();
        int UpperCount = password.replaceAll("[^A-Z]", "").length();
        int LowerCount = password.replaceAll("[^a-z]", "").length();

        if (passlen > 7 && specialCharCount > 0 && DigitCount > 0 && UpperCount > 0 && LowerCount > 0) {
            System.out.println("Valid Password");
            validity = true;
        } else {
            System.out.println("Invalid Password");
            validity = false;
        }

        return validity;
    }


    public static boolean validateEmail(String email) {

        String regex = "^[A-Za-z0-9+_.-]+@(.+)[.](.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static String getFilteredSHA1HashedPassword(String password) {
        String hashedPassword = null;
        String salt = password.replaceAll("\\W+", "");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return hashedPassword;
    }

    public static String getFilteredSHA256HashedPassword(String password) {
        String hashedPassword = null;
        String salt = password.replaceAll("\\W+", "");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return hashedPassword;
    }

    public static String getFilteredSHA512HashedPassword(String password) {
        String hashedPassword = null;
        String salt = password.replaceAll("\\W+", "");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return hashedPassword;
    }

    public static boolean isValidInput(String input) {
        int length = input.length();
        if (length != 9 && length != 10 && length != 12) {
            return false;
        }

        if (length==10) {
            char c = input.charAt(9);
            if (!Character.isDigit(c) && c != 'v' && c != 'V' && c != 'x' && c != 'X') {
                return false;
            }
        }
        
        return true;
    }
}
