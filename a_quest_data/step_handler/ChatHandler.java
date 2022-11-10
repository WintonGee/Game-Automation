package scripts.main_package.a_quest_data.step_handler;

import lombok.Data;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import scripts.main_package.a_quest_data.step.QuestStep;

import java.util.ArrayList;

@Data
public class ChatHandler {

    private final int CUTSCENE_VARBIT = 542;

    QuestStep questStep;

    public ChatHandler() {
    }

    public ChatHandler(QuestStep questStep) {
        this.setQuestStep(questStep);
    }

    public boolean isComplete() {
        return !ChatScreen.isOpen();
    }

    public boolean handle() {
        int attempts = questStep.getChatAttempts();
        ArrayList<String> choices = questStep.getChoices();
        ChatScreen.setConfig(ChatScreen.Config.builder().timeout(() -> 240000).holdSpaceForContinue(true).build());
        String[] array = choices.toArray(new String[0]);
        Log.info("[ChatHandler] Choices: " + choices);
        for (int i = 0; i < attempts; i++) {
            ChatScreen.handle(array);
        }
        return isComplete();
    }

}
