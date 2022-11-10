package scripts.main_package.api.action;

import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import scripts.main_package.api.legacy_tribot.General;
import scripts.main_package.api.other.UtilStat;
import scripts.main_package.api.other.UtilTime;

import java.util.function.BooleanSupplier;

public class UtilWait {

    // Only returns true when bs boolean is true, does not break otherwise
    public static boolean until(int time, BooleanSupplier bs) {
        return until(time, 0.5, bs);
    }

    public static boolean until(int time, double randomization, BooleanSupplier bs) {
        long extraTime = (long) (General.randomDouble(0, randomization) * time);
        long maxWait = time + extraTime;
        long endTime = UtilTime.getTime() + maxWait;
        while (UtilTime.getTime() < endTime) {
            if (bs.getAsBoolean())
                return true;
            Waiting.waitUniform(100, 300);
        }
        return false;
    }

    // Same as until function, except will return false if idle
    public static boolean untilIdle(int time, int maxMarks, BooleanSupplier bs) {
        long endTime = time + UtilTime.getTime();
        int xp = UtilStat.getTotalXp();
        int currentMarks = 0;
        while (UtilTime.getTime() < endTime) {
            if (bs.getAsBoolean())
                return true;

            if (currentMarks > maxMarks) {
                Log.warn("[UtilWait] Max marks exceeded {" + maxMarks + "}, detected as idle.");
                return false;
            }

            Waiting.waitUniform(100, 300);

            // Experience Activity
            int newXp = UtilStat.getTotalXp();
            boolean xpChange = newXp != xp;
            if (xpChange) {
                xp = newXp;
                currentMarks = 0;
                continue;
            }

            // Animation not in combat
            boolean animating = MyPlayer.isAnimating();
            if (animating) {
                currentMarks = 0;
                continue;
            }

            // Loading
            if (GameState.isLoading()) {
                currentMarks = 0;
                continue;
            }

            // Moving
            if (MyPlayer.isMoving()) {
                currentMarks = 0;
                continue;
            }

            currentMarks++;
        }
        return false;
    }

}
