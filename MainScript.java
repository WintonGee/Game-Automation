package scripts.main_package;

import com.google.gson.Gson;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.script.TribotScriptManifest;
import scripts.main_package.api.legacy_tribot.General;
import scripts.main_package.api.socket.SocketListener;
import scripts.main_package.data.AccountDetails;
import scripts.main_package.server_updater.ClientManagerUpdater;
import scripts.main_package.server_updater.ServerUpdater;

import java.io.IOException;

/**
 * Java Docs Link:
 * https://runeautomation.com/docs/sdk/javadocs/index.html?overview-summary.html
 */
@TribotScriptManifest(name = "MainScriptIntelliJ", author = "Winton", category = "Main")
//@ScriptManifest(authors = { "Winton" }, category = "Main", name = "MainScript4")
public class MainScript implements TribotScript {

    public static boolean shouldRun = true;
    //	public static AccountDetails account;
    public static AccountDetails account;

    @Override
    public void execute(String arg) {

        if (arg == null || arg.length() == 0) {
            Log.warn("[MainScript] No args detected");
            return;
        }
        if (!starting(arg))
            return;

        General.sleep(1000000020);

        // TODO mule or normal acc
    }

    private boolean starting(String arg) {
        Log.info("Loading Arg: " + arg);
        account = new Gson().fromJson(arg, AccountDetails.class);
        if (!account.connection.update()) {
            Log.warn("[MainScript] Failed to update connections.");
            return false;
        }

        new ClientManagerUpdater().start();
        new ServerUpdater(account).start();
        try {
            new SocketListener(account.connection.getPort()).start();
        } catch (IOException e) {
            Log.warn("Failed to start communications listener.");
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
