package scripts.main_package.server_updater;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.Log;
import scripts.main_package.MainScript;
import scripts.main_package.api.legacy_tribot.General;
import scripts.main_package.api.other.UtilTime;
import scripts.main_package.api.socket.Client;
import scripts.main_package.data.AccountDetails;
import scripts.main_package.data.SocketData;
import scripts.main_package.data.SocketData.SocketMessage;

import java.io.IOException;

public class ServerUpdater extends Thread {

    @Getter
    @Setter
    AccountDetails accountDetails;

    public ServerUpdater(AccountDetails accountDetails) {
        this.setAccountDetails(accountDetails);
    }

    @Override
    public void run() {
        while (MainScript.shouldRun) {
            General.sleep(3000);
            updateAccount();
        }
    }

    private void updateAccount() {
        accountDetails.setNextSelectableTime(UtilTime.getTime() + UtilTime.getMillisMinute(30));
        accountDetails.inGameDetails.update();

        String updateMessage = new Gson().toJson(accountDetails, AccountDetails.class);
        updateMessage = updateMessage.concat(SocketMessage.ACCOUNT_UPDATE.getMessage());
        try {
            String returnedMessage = Client.sendMessage(updateMessage, SocketData.SERVER_PORT);
//			Log.debug("[ServerUpdater] Returned Message: " + returnedMessage);
        } catch (IOException e) {
            Log.debug("[ServerUpdater] Failed to send update");
            e.printStackTrace();
        }
    }

}
