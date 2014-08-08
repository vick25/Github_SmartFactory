package productionPanel;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class ReadSmartServerIni {

    public static void main(String args[]) {
        try {
            Wini ini;
            /* Load the ini file. */
            ini = new Wini(new File(ConnectDB.SMARTSERVERPATH));
            /* Extract the logging operation.*/
            int autoLogging = Integer.valueOf(ini.get("Logging", "AutoLogging"));
            /* Extract the logging days. */
            int autoDays = Integer.valueOf(ini.get("Logging", "AutoDays"));
            /* Extract the from day. */
            String autoFrom = ini.get("Logging", "AutoFrom");
            /* Extract the to day. */
            String autoTo = ini.get("Logging", "AutoTo");

            /* Show user the output. */
            switch (autoLogging) {
                case 0:
                    logOperation = "None";
                    break;
                case 1:
                    logOperation = "Schedule";
                    break;
                case 2:
                    logOperation = "Full";
                    break;
            }
            System.out.println("Log Operation: " + logOperation);
            System.out.println("Days selected: " + autoDays);
            System.out.println("From: " + autoFrom);
            System.out.println("To: " + autoTo);

//            int a = autoDays;
//            int orig = a;
//            int count = 0;
//            while (a > 0) {
//                a = a >> 1 << 1;
//                if (orig - a == 1) {
//                    count++;
//                }
//                orig = a >> 1;
//                a = orig;
//            }
//            System.out.println("Number of 1s are: " + count);
            String byDay = make7bits(Integer.toBinaryString(autoDays));
            System.out.println("Byte: " + byDay);

            for (int j = 0; j < byDay.length(); j++) {
                if (byDay.charAt(j) != '1') {
                    vectWeekDays.add(getWeekDay(j).name());
                    System.out.println(getWeekDay(j).name());
                }
            }
//            System.out.println(String.format("<html>vick <br> kadiata</html>"));
        } catch (InvalidFileFormatException e) {
            System.out.println("Invalid file format.");
        } catch (IOException e) {
            System.out.println("Problem reading file.");
        }
    }

    private static String make7bits(String s) {
        if (s.length() < 7) {
            s1 = "0" + s;
            make7bits(s1);
        }
        return s1;
    }

    private static WEEKDAYS getWeekDay(int day) {
        switch (day) {
            case 0:
                return WEEKDAYS.SUNDAY;
            case 1:
                return WEEKDAYS.MONDAY;
            case 2:
                return WEEKDAYS.TUESDAY;
            case 3:
                return WEEKDAYS.WEDNESDAY;
            case 4:
                return WEEKDAYS.THURSDAY;
            case 5:
                return WEEKDAYS.FRIDAY;
            case 6:
                return WEEKDAYS.SATURDAY;
        }
        return null;
    }

    public static String logOperation = "None";
    static String s1;
    static Vector<String> vectWeekDays = new Vector<>();

    enum WEEKDAYS {

        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }
//    static BitSet bitSet;
}
