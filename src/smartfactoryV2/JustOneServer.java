package smartfactoryV2;

/**
 *
 * @author Victor Kadiata
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JustOneServer extends Thread {

    @Override
    public void run() {
        try {
            // Create the server socket
            serverSocket = new ServerSocket(ConnectDB.PORTMAINSERVER, 1);
            while (true) {
                // Wait for a connection
                clientSocket = serverSocket.accept();
                System.out.println("*** Got a connection! ***");
            }
        } catch (IOException ioe) {
            System.out.println("Error in JustOneServer: " + ioe);
        } finally {
//            try {
////                serverSocket.close();
////                clientSocket.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
        }
    }
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
}
