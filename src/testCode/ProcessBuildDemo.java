package testCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class ProcessBuildDemo {

    public static void main(String[] args) throws IOException {
        String filePath = ConnectDB.findFileOnClassPath(new StringBuilder(pathSeparator).append("build").append(pathSeparator).
                append("classes").append(pathSeparator).append("bin").append(pathSeparator).
                append("myService.exe").toString()).getAbsolutePath();
        String[] command = {"CMD", "/C", "dir"};
//        System.out.println(filePath.substring(0, filePath.length() - 13));
//        filePath = "/" + filePath.substring(0, filePath.length() - 13);
//        String[] command = {"cmd.exe", filePath, "dir"};
        ProcessBuilder probuilder = new ProcessBuilder(command);

        //You can set up your work directory
//        probuilder.directory(new File(filePath));
        Process process = probuilder.start();

        //Read out dir output
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.printf("Output of running %s is:\n", Arrays.toString(command));
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        //Wait to get exit value
        try {
            int exitValue = process.waitFor();
            System.out.println("\n\nExit Value is " + exitValue);
            System.exit(exitValue);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static String pathSeparator = File.separator;
}
