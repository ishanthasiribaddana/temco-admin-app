/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author USER
 */
public class RegexVerifications {

    private static final String NIC_REGEX = "^(?:\\d{9}[VX]|\\d{12})$";
    private static final String PHONE_REGEX = "^(0[1-9][0-9]-[0-9]{7}|07[0-9]{8,9})$";

    public static boolean verifyNIC(String nic) {
        if (nic == null) {
            return false;
        }
        Pattern nicPattern = Pattern.compile(NIC_REGEX);
        Matcher matcher = nicPattern.matcher(nic);
        System.out.println("nic verification " + matcher.matches());
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }

}
