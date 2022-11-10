package scripts.main_package.data;

import lombok.Getter;
import lombok.Setter;

public class SocketData {

    public final static int SERVER_PORT = 5000;
    public final static int CLIENT_MANAGER_PORT = 4000;

    // When a request can not be fulfilled.
    // Example: Not enough gp to send
    public final static int INVALID_REQUEST_PORT = -1;

    public enum SocketMessage {

        DEFAULT("Default Message"), // Usually for when client needs to do nothing

        REQUEST_GOLD_MULE("Client Needs Gold From Mule"), // Get items from mule
        SEND_GOLD_MULE("Client Needs Gold To Mule"), // Gives items to mule

        REQUEST_ACCOUNT("Client Needs Account From Server"),
        NO_AVAILABLE_ACCOUNTS("Server does not have any valid accounts"),

        ACCOUNT_UPDATE("Account Update Request"), // For updating server account details
        PID_UPDATE("Client is sending pid");

        @Getter
        @Setter
        String message;

        SocketMessage(String m) {
            this.setMessage(m);
        }

        public boolean isMessage(String string) {
            return string != null && string.equals(message);
        }

        public boolean containsMessage(String string) {
            return string != null && string.contains(message);
        }
    }

}
