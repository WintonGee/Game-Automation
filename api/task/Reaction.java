package scripts.main_package.api.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import net.sourceforge.jdistlib.Normal;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.input.Mouse;
import scripts.main_package.api.other.UtilSeed;

import java.util.Random;

@Data
@Accessors(chain = true)
public class Reaction {

    /**
     * Prepared reaction time formula
     * a + b log (1/p)
     * a,b -> Constants
     * p   -> probability of a stimulus appearing at any given time
     *
     * Reaction time is slower w/ more choices
     *
     * Reaction time std range 0.3-0.4
     *
     * Skewed bell curve to the right for reaction times: https://humanbenchmark.com/tests/reactiontime
     */

    private final static int DEFAULT_ACTIVE_MIN = 50, DEFAULT_ACTIVE_MAX = 400;

    private final static int DEFAULT_AFK_MIN = 2000, DEFAULT_AFK_MAX = 5000;

    boolean validWait;
    long timeWaited;

    public Reaction(boolean validWait, long timeWaited) {
        this.setValidWait(validWait);
        this.setTimeWaited(timeWaited);
    }

    public void react() {
        int wait = getMean();
        int std = (int) Math.ceil(wait * UtilSeed.getValue("Reaction.waitStd", 0.3, 0.4));
        int sleep = (int) Math.ceil(new Normal(wait, std).random());
        Log.info("[Reaction] Time waited: " + timeWaited + ", Valid Wait: " + validWait
                + ", Mean: " + wait + ", Std: " + std + ", Sleep: " + sleep);
        Waiting.wait(sleep);
    }


    public int getMean() {
        return isActive() ? getActiveReactionTime() : getAfkReactionTime();
    }

    private boolean isActive() {
        return Mouse.isOnScreen();
    }

    // Generates a reaction time as if the player is active
    private int getActiveReactionTime() {
        return UtilSeed.getValue("Reaction.getActiveReactionTime", DEFAULT_ACTIVE_MIN, DEFAULT_ACTIVE_MAX);
    }

    // Generates a reaction time as if the player is idle
    private int getAfkReactionTime() {
        return UtilSeed.getValue("Reaction.getActiveReactionTime", DEFAULT_AFK_MIN, DEFAULT_AFK_MAX);
    }

    private double defaultMultiplier() {
        return 1.0;
    }

    // Returns a multiplier based on the player's fatigue
    private double fatigueMultiplier() {
        return 1.0;
    }

}
