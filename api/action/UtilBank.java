package scripts.main_package.api.action;

import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.walking.GlobalWalking;

public class UtilBank {

    private final static int NOTED_SETTING = 115; // 0 = off, 1 = on
    private final static int DEFAULT_QUANTITY_VARBIT = 3960; // Value returns the x amount

    public static boolean initBank() {
        for (int i = 0; i < 5; i++) {
            if (BankCache.isInitialized()) {
                Log.trace("[UtilBank] Bank Initialized!");
                return true;
            }
            if (open())
                BankCache.update();
        }
        return false;
    }

    public static boolean open() {
        if (Bank.isOpen())
            return true;
        if (!Bank.isNearby())
            GlobalWalking.walkToBank();
        return Bank.ensureOpen();
    }

    //TODO remove method
    public static boolean close() {
        return Bank.close();
    }

    public static boolean depositAllInventory() {
        if (Inventory.isEmpty())
            return true;
        return open() && Bank.depositInventory();
    }

    public static boolean setNoted(boolean on) {
        int settingVal = GameState.getSetting(NOTED_SETTING);
        boolean alreadyComplete = on ? settingVal == 1 : settingVal == 0;
        if (alreadyComplete)
            return true;
        String action = on ? "Note" : "Item";
        boolean clicked = Query.widgets().actionEquals(action).findFirst().map(i -> i.click(action)).orElse(false);
        return clicked && UtilWait.until(1800, () -> settingVal != GameState.getSetting(NOTED_SETTING));
    }

    public static int getDefaultAmount() {
        return GameState.getVarbit(DEFAULT_QUANTITY_VARBIT);
    }


}
