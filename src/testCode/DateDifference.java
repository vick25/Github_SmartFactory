package testCode;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Victor Kadiata
 */
public class DateDifference {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date1 = "2014/08/20 07:30:59";
        String date2 = "2014/08/22 09:31:59";

        printDiffs(getTimeDifference(dateFormat.parse(date2), dateFormat.parse(date1)));
    }

    public static double[] getTimeDifference(Date d1, Date d2) {
//        System.out.println(d1 + "   " + d2);
        double[] result = new double[4];
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTime(d1);

        long t1 = cal.getTimeInMillis();
        cal.setTime(d2);

        long diff = Math.abs(cal.getTimeInMillis() - t1);
        long s = diff / 1000;
        double m = Double.parseDouble(String.format("%.6f", s / 60d));
        double h = new BigDecimal(String.format("%.5f", s / (60d * 60))).doubleValue();
        long d = diff / (24 * 60 * 60 * 1000);
        result[0] = d;
        result[1] = h;
        result[2] = m;
        result[3] = s;
//        result[4] = ms;
        return result;
    }

    public static void printDiffs(double[] diffs) {
//        System.out.printf("Days:         %3d\n", diffs[0]);
        System.out.printf("Hours:        %3s\n", diffs[1]);
        System.out.printf("Minutes:      %3s\n", diffs[2]);
        System.out.printf("Seconds:      %3s\n", diffs[3]);
//        System.out.printf("Milliseconds: %3d\n", diffs[4]);
    }
}
