package testCode;

/**
 *
 * @author Victor Kadiata
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class VBSUtils {

    private VBSUtils() {
    }

    public static boolean isRunning(String process) {
        boolean found = false;
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            try (FileWriter fw = new java.io.FileWriter(file)) {
                String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                        + "Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n"
                        + "Set service = locator.ConnectServer()\n"
                        + "Set processes = service.ExecQuery _\n"
                        + " (\"select * from Win32_Process where name='" + process + "'\")\n"
                        + "For Each process in processes\n"
                        + "wscript.echo process.Name \n"
                        + "Next\n"
                        + "Set WSHShell = Nothing\n";

                fw.write(vbs);
            }
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                line = input.readLine();
                if (line != null) {
                    if (line.equals(process)) {
                        found = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return found;
    }

    public static void main(String[] args) {
        String fileName = "SmartServer.exe";
        boolean result = VBSUtils.isRunning(fileName);

        msgBox("Is " + fileName + " running ?  " + (result ? " Yes" : "No"));
    }

    public static void msgBox(String msg) {
        javax.swing.JOptionPane.showConfirmDialog(null, msg, "VBSUtils", javax.swing.JOptionPane.DEFAULT_OPTION);
    }
}
