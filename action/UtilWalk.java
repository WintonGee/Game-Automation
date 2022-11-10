package scripts.main_package.api.action;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.GlobalWalking;

public class UtilWalk {

    public static boolean global(WorldTile tile) {
        Log.info("[UtilWalk] Walking to: " + tile.toString());
        return GlobalWalking.walkTo(tile);
    }

}
