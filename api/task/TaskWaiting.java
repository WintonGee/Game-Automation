package scripts.main_package.api.task;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.api.legacy_tribot.General;
import scripts.main_package.api.other.UtilStat;
import scripts.main_package.api.other.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BooleanSupplier;

// This class is used for waiting after a task if needed
@Data @Accessors(chain = true)
public class TaskWaiting {

    private final static int DEFAULT_IDLE_TIMEOUT = 20;

    int min, max;
    String description;

    int checkItemGainId = -1, checkingItemsGained = 0, checkItemGainedLimit = 100;

    // Usually stops tasks if level up is detected
    boolean checkForLevelUp = true;

    ArrayList<TaskCondition> waitConditionList = new ArrayList<>();

    public TaskWaiting(int min, int max, String description) {
        this.min = min;
        this.max = max;
        this.description = description;
    }

    public boolean handle() {
        long startTime = System.currentTimeMillis();
        boolean validWait = handleWaiting();
        long timeWaited = System.currentTimeMillis() - startTime;

        new Reaction(validWait, timeWaited).react();

        return validWait;
    }


    private boolean handleWaiting() {
        int maxWait = General.random(min, max);
        long endTime = System.currentTimeMillis() + maxWait;
        Log.debug("[TaskWaiting] " + description);

        int currentCheckItemAmount = Query.inventory().idEquals(checkItemGainId).sumStacks();
        int currentInventoryItemsAmount = Query.inventory().sumStacks();

        int currentExp = UtilStat.getTotalXp();
        int startingLevel = UtilStat.getTotalLevel();

        int idleCounter = 0;

        while (System.currentTimeMillis() < endTime) {
            Waiting.waitUniform(100, 200); //TODO better method

            // Checking if player is idle
            if (idleCounter >= DEFAULT_IDLE_TIMEOUT) {
                Log.warn("[TaskWaiting] Condition Detected: Idle");
                return false;
            }

            // Checking if player leveled up
            if (checkForLevelUp && startingLevel < UtilStat.getTotalLevel()) {
                Log.info("[TaskWaiting] Condition Detected: Leveled up");
                return true;
            }

            // Checking new target items gained timeout
            if (checkingItemsGained >= checkItemGainedLimit) {
                Log.info("[TaskWaiting] Condition Detected: " + checkingItemsGained + " " +
                        Utils.getItemName(checkItemGainId) + " items gained");
                return true;
            }

            // Any custom conditions
            val validWaitCondition = getValidWaitCondition();
            if (validWaitCondition.isPresent()) {
                validWaitCondition.map(TaskCondition::getConditionName)
                        .ifPresent(o -> Log.info("[TaskWaiting] Condition Detected: " + o));
                return true;
            }

            // Experience
            int newXp = UtilStat.getTotalXp();
            boolean xpGained = newXp != currentExp;
            currentExp = newXp;


            // All Items Count
            int newInventoryItemsAmount = Query.inventory().sumStacks();
            boolean itemsGained = newInventoryItemsAmount != currentInventoryItemsAmount;
            currentInventoryItemsAmount = newInventoryItemsAmount;


            // Checking Item
            int newCheckItemAmount = Query.inventory().idEquals(checkItemGainId).sumStacks();
            int checkItemAmountGained = newCheckItemAmount - currentCheckItemAmount;
            currentCheckItemAmount = newCheckItemAmount;
            checkingItemsGained += checkItemAmountGained;


            // Idle detection
            boolean notIdle = xpGained || itemsGained || MyPlayer.isAnimating() || MyPlayer.isMoving();

            if (notIdle)
                idleCounter = 0;
            else
                idleCounter++;

        }

        Log.warn("[TaskWaiting] " + description + " Max Wait: " + maxWait + ", Timed out");
        return false;
    }

    public TaskWaiting addCondition(String description, BooleanSupplier booleanSupplier) {
        this.waitConditionList.add(getTaskCondition(description, booleanSupplier));
        return this;
    }

    public TaskWaiting addConditions(TaskCondition... taskConditions) {
        this.waitConditionList.addAll(Arrays.asList(taskConditions));
        return this;
    }

    private Optional<TaskCondition> getValidWaitCondition() {
        return waitConditionList.stream()
                .filter(TaskCondition::check)
                .findFirst();
    }

    private TaskCondition getTaskCondition(String name, BooleanSupplier booleanSupplier) {
        return new TaskCondition() {
            @Override
            public String getConditionName() {
                return name;
            }

            @Override
            public boolean check() {
                return booleanSupplier.getAsBoolean();
            }
        };
    }

}
