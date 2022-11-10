package scripts.main_package.a_quest_data.execution;

import lombok.Data;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.step.QuestStep;
import scripts.main_package.a_quest_data.step_handler.ChatHandler;
import scripts.main_package.a_quest_data.step_handler.CutsceneHandler;
import scripts.main_package.api.action.UtilWait;

@Data
public class QuestEndingHandler {
    private final int countsNeeded = 5;

    BasicQuestHelper basicQuestHelper;
    QuestStep endingQuestStep;

    QuestEndInterfaceHandler questEndInterfaceHandler;
    QuestRewardItemsHandler questRewardItemsHandler;
    CutsceneHandler cutsceneHandler;
    ChatHandler chatHandler;

    public QuestEndingHandler(BasicQuestHelper basicQuestHelper) {
        this.basicQuestHelper = basicQuestHelper;
        this.endingQuestStep = basicQuestHelper.endingQuestStep;

        this.setQuestEndInterfaceHandler(new QuestEndInterfaceHandler());
        this.setQuestRewardItemsHandler(new QuestRewardItemsHandler(basicQuestHelper));
        this.setCutsceneHandler(new CutsceneHandler(endingQuestStep));
        this.setChatHandler(new ChatHandler(endingQuestStep));
    }

    public boolean handle() {
        Log.info("[QuestEndingHandler] Starting Quest Ending Handler");

        for (int i = 0; i < 100; i++) {
            Waiting.waitUniform(500, 1000);

            Log.info("[QuestEndingHandler] Waiting for an ending task.");
            if (!UtilWait.until(5000, () -> !isComplete())) {
                Log.info("[QuestEndingHandler] Quest Ending Complete!");
                return true;
            }

            if (!questEndInterfaceHandler.handle()) {
                Log.error("[QuestEndingHandler] Failed to handle quest interface!");
                continue;
            }

            if (!cutsceneHandler.handle()) {
                Log.error("[QuestEndingHandler] Failed to handle cutscene!");
                continue;
            }

            if (!chatHandler.isComplete() && !chatHandler.handle()) {
                Log.error("[QuestEndingHandler] Failed to handle chat!");
//                continue; // Reward items might have chat
            }

            if (!questRewardItemsHandler.handle()) {
                Log.error("[QuestEndingHandler] Failed to handle rewards!");
                continue;
            }

        }

        return false;
    }

    public boolean isComplete() {
        return questEndInterfaceHandler.isComplete() && questRewardItemsHandler.isComplete()
                && !cutsceneHandler.isInCutscene() && chatHandler.isComplete();
    }

}
