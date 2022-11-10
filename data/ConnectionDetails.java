package scripts.main_package.data;

import lombok.Data;
import scripts.main_package.api.legacy_tribot.General;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;

@Data
public class ConnectionDetails {

    private final int portMin = 1000;
    private final int portMax = 60000;

    String ip = null;
    int port = SocketData.INVALID_REQUEST_PORT;

    public boolean isUpdated() {
        return ip != null && port != SocketData.INVALID_REQUEST_PORT;
    }

    // Sets the ip and server port
    public boolean update() {
        this.setIp(getPingIp());
        this.setPort(getValidPort());
        return isUpdated();
    }

    private String getPingIp() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    // For getting port
    public int getValidPort() {
        for (int i = 0; i < 100; i++) {
            int randomPort = General.random(portMin, portMax);
            if (!isLocalPortInUse(randomPort)) {
                General.println("[ConnectionDetails] Valid Port Found: " + randomPort);
                return randomPort;
            }
        }
        return SocketData.INVALID_REQUEST_PORT;
    }

    private boolean isLocalPortInUse(int port) {
        try {
            new ServerSocket(port).close();
            return false;
        } catch (IOException e) {
            return true;
        }
    }

}
