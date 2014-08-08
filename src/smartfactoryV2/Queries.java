package smartfactoryV2;

/**
 *
 * @author Victor Kadiata
 */
public class Queries {

    public static final String TOTAL_PRODUCTION = "SELECT d.LogTime AS 'Time', d.LogData FROM datalog d\n"
            + "WHERE d.ConfigNo =?\n"
            + "AND d.LogTime >=? AND d.LogTime <=?\n"
            + "ORDER BY 'Time' ASC";

    public static final String RATE_PRODUCTION_HR = "SELECT d.LogTime AS 'Time', (d.LogData * 60)\n"
            + "FROM datalog d\n"
            + "WHERE d.ConfigNo =?\n"
            + "AND d.LogTime >=? AND d.LogTime <=?\n"
            + "ORDER BY 'Time' ASC";

    public static final String RATE_PRODUCTION_MIN = "SELECT d.LogTime AS 'Time', d.LogData FROM datalog d\n"
            + "WHERE d.ConfigNo =?\n"
            + "AND d.LogTime >=? AND d.LogTime <=?\n"
            + "ORDER BY 'Time' ASC";
}
