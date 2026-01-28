/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.common;

import java.text.DecimalFormat;

/**
 *
 * @author USER
 */
public class ComMethods {

    public static String convertToTwoDecimalPoints(float value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedNumber = decimalFormat.format(value);
        return formattedNumber;
    }

}
