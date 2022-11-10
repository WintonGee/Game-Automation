package scripts.main_package.a_quest_data.step_handler;

import lombok.Data;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.types.WorldTile;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.step.QuestStep;
import scripts.main_package.api.action.UtilWait;
import scripts.main_package.api.action.UtilWalk;

// Handles the traveling needed for the quest step
@Data
public class LocationHandler {

    QuestStep questStep;

    //TODO custom method here

    public LocationHandler(QuestStep questStep) {
        this.setQuestStep(questStep);
    }

    public boolean handle() {
        //TODO custom walking methods
        return handleWorldPoint();
    }

    private boolean handleWorldPoint() {
        WorldPoint worldPoint = this.questStep.getWorldPoint();
        if (worldPoint == null)
            return true;

        WorldTile worldTile = worldPoint.getWorldTile();
        Log.info("[LocationHandler] Handling location: " + worldTile);

        if (!handleWalking(worldTile)) {
            Log.error("[LocationHandler] Failed to handle location: " + worldTile);
            return false;
        }

        boolean useExactTile = questStep.isUseExactTile();
        return !useExactTile || handleExactTile(worldTile);
    }

    // TODO support different methods
    private boolean handleWalking(WorldTile worldTile) {
        return UtilWalk.global(worldTile);
    }

    private boolean handleExactTile(WorldTile worldTile) {
        if (isOnExactTile(worldTile)) {
            Log.trace("[LocationHandler] On Exact Tile!");
            return true;
        }
        Log.trace("[LocationHandler] Attempting to go on exact tile.");
        return worldTile.interact("Walk here") &&
                UtilWait.untilIdle(15000, 10, () -> isOnExactTile(worldTile));
    }

    private boolean isOnExactTile(WorldTile worldTile) {
        return MyPlayer.getPosition().equals(worldTile);
    }

}
