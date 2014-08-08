package backRestoreDB;

import java.io.IOException;
import smartfactoryV2.SplashScreen;

/**
 *
 * @author Victor Kadiata
 */
public class RestoreDatabase extends Thread {

    public RestoreDatabase(String dbUserName, String dbPassword, Object source) {
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
        this.source = source;
    }

    @Override
    public void run() {
        restore();
    }

    private void restore() {
        String[] restoreCmd = new String[]{"mysql ", "--user=" + dbUserName, "--password=" + dbPassword, "-e",
            "source " + source};
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(restoreCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                SplashScreen.lblSplash.setText("Smartnew database successful!");
//                System.out.println("Restored successfully!");
            } else {
                SplashScreen.lblSplash.setText("Could not restore the backup!");
//                System.out.println("Could not restore the backup!");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

//    public boolean restoreDB(String dbUserName, String dbPassword, String source) {
//        String[] executeCmd = new String[]{"mysql", "--user=" + dbUserName, "--password=" + dbPassword, "-e", 
//            "source " + source};
//        Process runtimeProcess;
//        try {
//            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
//            int processComplete = runtimeProcess.waitFor();
//            if (processComplete == 0) {
//                System.out.println("Backup restored successfully");
//                return true;
//            } else {
//                System.out.println("Could not restore the backup");
//            }
//        } catch (IOException | InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        return false;
//    }
    private final String dbUserName, dbPassword;
    private final Object source;
}
