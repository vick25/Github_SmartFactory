package smartfactoryV2;

/**
 *
 * @author Victor Kadiata
 */
public class Queries {

    public static final String DATALOG_PRODUCTION = "SELECT d.LogTime AS 'Time', d.LogData "
            + "FROM configuration c, datalog d \n"
            + "WHERE c.ConfigNo = d.ConfigNo \n"
            + "AND d.ConfigNo =? \n"
            + "AND d.LogTime >=? AND d.LogTime <=? \n"
            + "ORDER BY 'Time' ASC";

    public static final String DATALOG_PRODUCTION_HR = "SELECT d.LogTime AS 'Time', (d.LogData * 60) \n"
            + "FROM datalog d \n"
            + "WHERE d.ConfigNo =? \n"
            + "AND d.LogTime >=? AND d.LogTime <=? \n"
            + "ORDER BY 'Time' ASC";

    public static final String CREATE_TABLE_BREAKS = "USE smartfactory; \n"
            + "  CREATE TABLE IF NOT EXISTS `breaks` ( \n"
            + "  `BreaksNo` smallint(5) NOT NULL AUTO_INCREMENT, \n"
            + "  `BreaksName` varchar(50) NOT NULL, \n"
            + "  PRIMARY KEY (`BreaksNo`) \n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

    public static final String CREATE_TABLE_TIMEBREAKS = "USE smartfactory; \n"
            + "CREATE TABLE IF NOT EXISTS `timebreaks` ( \n"
            + "`TimeBreaksNo` SMALLINT( 5 ) NOT NULL AUTO_INCREMENT , \n"
            + "`HwNo` SMALLINT( 5 ) UNSIGNED NOT NULL , \n"
            + "`BreaksNo` SMALLINT( 5 ) NOT NULL , \n"
            + "`StartTime` TIME NOT NULL , \n"
            + "`EndTime` TIME NOT NULL , \n"
            + "PRIMARY KEY (`TimeBreaksNo`) \n"
            + ") ENGINE = InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

    public static final String CREATE_TABLE_STARTENDTIME = "USE smartfactory; \n"
            + "CREATE TABLE IF NOT EXISTS `startendtime` ( \n"
            + "  `StartEndTimeNo` smallint(5) unsigned NOT NULL AUTO_INCREMENT, \n"
            + "  `HwNo` int(11) NOT NULL, \n"
            + "  `StartTime` time DEFAULT NULL, \n"
            + "  `EndTime` time DEFAULT NULL, \n"
            + "  PRIMARY KEY (`StartEndTimeNo`) \n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

    public static final String CREATE_TABLE_TARGET = "USE smartfactory; \n"
            + "CREATE TABLE IF NOT EXISTS `target` ( \n"
            + " `TargetNo` smallint(5) NOT NULL AUTO_INCREMENT, \n"
            + " `Machine` varchar(50) NOT NULL, \n"
            + " `ConfigNo` smallint(6) NOT NULL, \n"
            + " `TargetValue` double NOT NULL, \n"
            + " `TargetUnit` varchar(10) NOT NULL, \n"
            + " PRIMARY KEY (`TargetNo`) \n"
            + ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";

    public static final String HISTORY = "SELECT c.ConfigNo \n"
            + "FROM configuration c, hardware h \n"
            + "WHERE h.HwNo = c.HwNo \n"
            + "AND h.Machine =? \n"
            + "ORDER BY c.HwNo ASC, c.AvMinMax DESC";

    public static final String HISTORY_LOGDATA = "SELECT d.LogTime, d.LogData \n"
            + "FROM configuration c, datalog d \n"
            + "WHERE c.ConfigNo = d.ConfigNo \n"
            + "AND d.ConfigNo =? \n"
            + "ORDER BY d.LogTime ASC";

    public static final String HISTORY_MACHINE_CHANNELID = "SELECT c.ChannelID \n"
            + "FROM configuration c, hardware h \n"
            + "WHERE h.HwNo = c.HwNo \n"
            + "AND h.Machine =? \n"
            + "ORDER BY c.HwNo ASC, c.AvMinMax DESC";

    public static final String GET_CONFIGNO = "SELECT DISTINCT c.ConfigNo \n"
            + "FROM configuration c, hardware h \n"
            + "WHERE h.HwNo = c.HwNo \n"
            + "AND c.AvMinMax =? \n"
            + "AND h.Machine =? \n"
            + "AND c.Active = 1 ORDER BY h.HwNo ASC";

    public static final String GET_HARDWARE = "SELECT HwNo, Machine FROM hardware \n"
            + "WHERE HwNo > ? "
            + "ORDER BY Machine ASC";
    
    public static final String GET_LAST_DATA_TIME = "SELECT LogTime FROM datalog \n"
                + "ORDER BY LogTime DESC LIMIT 1;";
}
