package scripts.main_package.a_skill_data.util;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.interfaces.Clickable;

public class SkillingWait {

    // Will return true when the object Id changes
    // Or false if player is detected as idle,
    // any other reasons that stops action.
    // Used for npc, or game objects.
    // False Conditions:
    // - Under attack
    // - Inventory full
    // - Not animating for too long
    public static boolean waitForFixedClickableChange(long maxWait, Clickable clickable) {
        if (clickable == null) {
            Log.error("[SkillingUtil] Skipping wait, Error detecting object");
            return false;
        }
        long endTimeLimit = System.currentTimeMillis() + maxWait;
        while (endTimeLimit > System.currentTimeMillis()) {

            Waiting.waitUniform(100, 200);

        }

        return false;
    }

    private static boolean isUnderAttack() {
        return MyPlayer.isHealthBarVisible();
    }

    //TODO method for wait for attack npc change.

}
