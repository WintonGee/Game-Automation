package scripts.main_package.api.other;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class UtilSocket {

    public static void sendMessage(String message, Socket socket) throws IOException {
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.println("Message has been received!");
        pw.flush();
    }

}
