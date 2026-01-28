/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.exon.temco_loan_system.common;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Nilupul Nethmina
 */
@Stateless
@LocalBean
public class ComLib {
    
    public static Date getDateObject(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date FormattedDate = null;
        try {
            FormattedDate = formatter.parse(date);
        } catch (Exception e) {
        }
        return FormattedDate;
    }

    public static Date getDateAferYear(Date date, int count) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, count); // to get previous year add -1
        Date nextYear = cal.getTime();
        return nextYear;
    }

    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public static String getDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String getDouble(double value) {
        DecimalFormat myFormatter = new DecimalFormat("###,###,###.##");
        String output = myFormatter.format(value);
        return output;
    }

    public static String getDateFromSelectedDate(Date date, int days) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return formatter.format(calendar.getTime());
    }

    public static String getDoubleWithCents(double value) {
        DecimalFormat myFormatter = new DecimalFormat("###,###,###.##");
        myFormatter.setMinimumFractionDigits(2);
        String output = myFormatter.format(value);
        return output;
    }

    public static String getInt(double value) {
        DecimalFormat myFormatter = new DecimalFormat("###,###,###");
        String output = myFormatter.format(value);
        return output;
    }
    
    public static Date getDate(String date, String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date + " " + time);
    }

    public static int GetCurrentYear() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        return Integer.valueOf(cal1.getTime().toString().split(" ")[5]);
    }

    public static int GetCurrentFinanceYear() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        int curYear = Integer.valueOf(cal1.getTime().toString().split(" ")[5]);
        int curmonth = GetCurrentMonth();
        if (curmonth >= 1 && curmonth < 4) {
            curYear = curYear - 1;
        }
        return curYear;
    }
    
    public static int GetCurrentMonth() {
        SimpleDateFormat mm = new SimpleDateFormat("MM");
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        return Integer.valueOf(mm.format(cal1.getTime()));
    }

    public static String getFullDate(Date time) {
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.US);
        return formatter.format(time);
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
                || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static long getDayCount(String start, String end) {
        long diff = -1;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateStart = simpleDateFormat.parse(start);
            Date dateEnd = simpleDateFormat.parse(end);

            //time is always 00:00:00 so rounding should help to ignore the missing hour when going from winter to summer time as well as the extra hour in the other direction
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return diff;
    }

    public static double getRounded(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public static void setHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
    }

    public static Date getLastDaYOfMonthFromMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal
        
        Date lastDateOfMonth = cal.getTime();
        return lastDateOfMonth;
    }

    public static Date getFirstDaYOfMonthFromMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);

        Date lastDateOfMonth = cal.getTime();
        return lastDateOfMonth;
    }

    public static Date getFirstDaYOfMonthFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        Date firstDateOfMonth = cal.getTime();
        return firstDateOfMonth;
    }

    public static Date getYesterdayFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        //  System.out.println("Yesterday's date = " + cal.getTime());
        return cal.getTime();
    }

    public static Date getLastDaYOfMonthFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal

        Date lastDateOfMonth = cal.getTime();
        return lastDateOfMonth;
    }
    
    public static String txtToHtml(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
                case '\n':
                    builder.append("<br/>");
                    break;
                // We need Tab support here, because we print StackTraces as HTML
                default:
                    builder.append(c);
            }
        }
        return builder.toString();
    }

    public static Date getFirstDayofWeekFromSelectedDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return c.getTime();
    }
    
    public static long getDayCountDate(Date dateStart, Date dateEnd) {
        long diff = -1;
        try {
            //time is always 00:00:00 so rounding should help to ignore the missing hour when going from winter to summer time as well as the extra hour in the other direction
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return diff;
    }
}
