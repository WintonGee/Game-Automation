package scripts.main_package.a_quest_data.step_handler;

import lombok.Data;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import scripts.main_package.a_quest_data.step.QuestStep;
import scripts.main_package.api.action.UtilWait;

// Executes activities needed for quest step
@Data
public class QuestStepHandler {

    Quest quest;
    int val;
    QuestStep questStep;

    public QuestStepHandler(Quest quest, int val, QuestStep questStep) {
        this.setQuest(quest);
        this.setVal(val);
        this.setQuestStep(questStep);
    }

    public void handle() {
        Log.info("[QuestStepHandler] " + questStep.getDescription());

        //TODO custom step

        CutsceneHandler cutsceneHandler = new CutsceneHandler(questStep);
        if (!cutsceneHandler.handle()) {
            Log.error("[QuestStepHandler] Failed to handle cutscene!");
            return;
        }

        SubStepsHandler subStepsHandler = new SubStepsHandler(questStep);
        if (!subStepsHandler.handle(quest, val)) {
            Log.error("[QuestStepHandler] Failed to handle substep!");
            return;
        }

        LocationHandler locationHandler = new LocationHandler(questStep);
        if (!locationHandler.handle()) {
            Log.error("[QuestStepHandler] Failed to handle location!");
            return;
        }

        UseItemHandler useItemHandler = new UseItemHandler(questStep);
        if (!useItemHandler.handle()) {
            Log.error("[QuestStepHandler] Failed to handle use item!");
            return;
        }

        InteractionHandler interactionHandler = new InteractionHandler(questStep);
        if (!interactionHandler.handle()) {
            Log.error("[QuestStepHandler] Failed to handle interaction!");
            return;
        }

        if (!waitForChange()) {
            Log.warn("[QuestStepHandler] No chat screen to handle!");
            return;
        }

        ChatHandler chatHandler = new ChatHandler(questStep);
        if (ChatScreen.isOpen() && !chatHandler.handle()) {
            Log.error("[QuestStepHandler] Failed to handle chat!");
            return;
        }

    }

    //TODO change to wait for change
    private boolean waitForChange() {
        return UtilWait.untilIdle(20000, 3, () -> {
            boolean chatScreen = ChatScreen.isOpen();
            boolean valChange = quest.getStep() != val;
            return chatScreen || valChange;
        });
    }

}
