package scripts.main_package.a_quest_data.step_handler;

import lombok.Data;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.main_package.a_quest_data.step.QuestStep;

@Data
public class CutsceneHandler {

    private final int CUTSCENE_VARBIT = 542;

    QuestStep questStep;

    public CutsceneHandler() {
    }

    public CutsceneHandler(QuestStep questStep) {
        this.setQuestStep(questStep);
    }

    public boolean handle() {
        for (int i = 0; i < 100; i++) {
            if (!isInCutscene())
                return true;
            Waiting.waitUniform(1500, 2000);
            handleCutscene();
        }
        return false;
    }

    // when handling chat, make it support null quest step

    private void handleCutscene() {
        Log.info("[CutsceneHandler] TODO ADD CHAT SCREEN HANDLING");
    }

    public boolean isInCutscene() {
        return GameState.getVarbit(CUTSCENE_VARBIT) != 0;
    }

}
