package scripts.main_package.server_updater;

import org.tribot.script.sdk.Log;
import scripts.main_package.MainScript;
import scripts.main_package.api.legacy_tribot.General;
import scripts.main_package.api.socket.Client;
import scripts.main_package.data.SocketData;

import java.io.IOException;

public class ClientManagerUpdater extends Thread {

    @Override
    public void run() {
        while (MainScript.shouldRun) {
            General.sleep(60000); // Update the client every minute
            updateAccount();
        }
    }

    private void updateAccount() {
        long pid = ProcessHandle.current().pid();
        String updateMessage = String.valueOf(pid);
//		updateMessage = updateMessage.concat(SocketMessage.PID_UPDATE.getMessage());
        try {
            String returnedMessage = Client.sendMessage(updateMessage, SocketData.CLIENT_MANAGER_PORT);
//			Log.debug("[ServerUpdater] Returned Message: " + returnedMessage);
        } catch (IOException e) {
            Log.debug("[ServerUpdater] Failed to send update");
            e.printStackTrace();
        }
    }

}