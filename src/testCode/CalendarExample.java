package testCode;

// -----------------------------------------------------------------------------
// CalendarExample.java
// -----------------------------------------------------------------------------

/*
 * =============================================================================
 * Copyright (c) 1998-2011 Jeffrey M. Hunter. All rights reserved.
 *
 * All source code and material located at the Internet address of
 * http://www.idevelopment.info is the copyright of Jeffrey M. Hunter and is
 * protected under copyright laws of the United States. This source code may not
 * be hosted on any other site without my express, prior, written permission.
 * Application to host any of the material elsewhere can be made by contacting
 * me at jhunter@idevelopment.info.
 *
 * I have made every effort and taken great care in making sure that the source
 * code and other content included on my web site is technically accurate, but I
 * disclaim any and all responsibility for any loss, damage or destruction of
 * data or any other property which may arise from relying on it. I will in no
 * case be liable for any monetary damages arising from such loss, damage or
 * destruction.
 *
 * As with any code, ensure to test this code in a development environment
 * before attempting to run it in production.
 * =============================================================================
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * ----------------------------------------------------------------------------- Used to provide an example
 * that exercises most of the functionality of the java.util.Calendar class.
 *
 * @version 1.0
 * @author Jeffrey M. Hunter (jhunter@idevelopment.info)
 * @author http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
public class CalendarExample {

    /**
     * Helper utility used to print a String to STDOUT.
     *
     * @param s String that will be printed to STDOUT.
     */
    private static void prt(String s) {
        System.out.println(s);
    }

    private static void prt() {
        System.out.println();
    }

    /**
     * Calendar's getTime() method returns a Date object. This can then be passed to println() to print
     * today's date (and time) in the traditional (but non-localized) format.
     */
    private static void doCalendarTimeExample() {
        prt("CURRENT DATE/TIME");
        prt("=================================================================");
        Date now = Calendar.getInstance().getTime();
        prt("Calendar.getInstance().getTime() : " + now);
        prt();
    }

    /**
     * Simple Date Format from java.text package.
     */
    private static void doSimpleDateFormat() {
        prt("SIMPLE DATE FORMAT");
        prt("=================================================================");

        String dateInString = "2011/02/04 14:34:00";
        // Get today's date
        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MMMM.dd 'at' hh:mm:ss a zzz");
        SimpleDateFormat vick = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        SimpleDateFormat vick1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = vick1.parse(dateInString);

            prt("  It is now : " + formatter.format(now.getTime()));
            prt("  It is now : " + vick.format(now.getTime()));
            prt("  It is now : " + vick1.format(now.getTime()));
            prt("  Formatted date is : " + vick1.format(vick1.parse(dateInString)));
            System.out.println((int) Double.parseDouble("12.0"));
            prt();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Date Arithmetic function. Adds the specified (signed) amount of time to the given time field, based on
     * the calendar's rules. The following example: - Subtracts 2 years from the current time of the calendar
     * - Adds 5 days from the current time of the calendar
     */
    private static void doAdd() {
        prt("ADD / SUBTRACT CALENDAR / DATES");
        prt("=================================================================");

        // Get today's date
        Calendar now = Calendar.getInstance();
        Calendar working;
        SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        working = (Calendar) now.clone();
        working.add(Calendar.DAY_OF_YEAR, -(365 * 2));
        prt("  Two years ago it was: " + formatter.format(working.getTime()));

        working = (Calendar) now.clone();
        working.add(Calendar.DAY_OF_YEAR, +5);
        prt("  In five days it will be: " + formatter.format(working.getTime()));

        working = (Calendar) now.clone();
        working.add(Calendar.DAY_OF_YEAR, -1);
        prt("  The day before was: " + formatter.format(working.getTime()));

        prt();
    }

    private static void doDateDifference() {
        prt("DIFFERENCE BETWEEN TWO DATES");
        prt("=================================================================");
        Date startDate1 = new GregorianCalendar(1994, 02, 14, 14, 00).getTime();
        Date endDate1 = new Date();

        long diff = endDate1.getTime() - startDate1.getTime();

        prt("  Difference between " + endDate1);
        prt("  and " + startDate1 + " is " + (diff / (1000L * 60L * 60L * 24L)) + " days.");
        prt();

    }

    private static void doGetMethods() {
        prt("CALENDAR GET METHODS");
        prt("=================================================================");
        Calendar c = Calendar.getInstance();
//        c.setTimeZone(TimeZone.getTimeZone("UTC+2"));

        prt("  YEAR                 : " + c.get(Calendar.YEAR));
        prt("  MONTH                : " + c.get(Calendar.MONTH));
        prt("  DAY_OF_MONTH         : " + c.get(Calendar.DAY_OF_MONTH));
        prt("  DAY_OF_WEEK          : " + c.get(Calendar.DAY_OF_WEEK));
        prt("  DAY_OF_YEAR          : " + c.get(Calendar.DAY_OF_YEAR));
        prt("  WEEK_OF_YEAR         : " + c.get(Calendar.WEEK_OF_YEAR));
        prt("  WEEK_OF_MONTH        : " + c.get(Calendar.WEEK_OF_MONTH));
        prt("  DAY_OF_WEEK_IN_MONTH : " + c.get(Calendar.DAY_OF_WEEK_IN_MONTH));
        prt("  HOUR                 : " + c.get(Calendar.HOUR));
        prt("  AM_PM                : " + c.get(Calendar.AM_PM));
        prt("  HOUR_OF_DAY (24-hour): " + c.get(Calendar.HOUR_OF_DAY));
        prt("  MINUTE               : " + c.get(Calendar.MINUTE));
        prt("  SECOND               : " + c.get(Calendar.SECOND));

        prt("=================================================================");
        prt("  Day               : " + Calendar.MONDAY);
        prt("  Day               : " + Calendar.TUESDAY);
        prt();

    }

    public static void main(String[] args) {
        prt();
        doCalendarTimeExample();
        doSimpleDateFormat();
        doAdd();
        doDateDifference();
        doGetMethods();
    }
}
