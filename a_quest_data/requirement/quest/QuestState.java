package scripts.main_package.a_quest_data.requirement.quest;

import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.Quest;

public enum QuestState {

    FINISHED(Quest.State.COMPLETE);

    @Setter
    @Getter
    Quest.State questState;

    QuestState(Quest.State questState) {
        this.setQuestState(questState);
    }

}
