package scripts.main_package.a_quest_data.requirement.quest;

import lombok.Data;
import lombok.NonNull;
import org.tribot.script.sdk.Quest;
import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_quest_data.QuestInformation;
import scripts.main_package.a_quest_data.requirement.Requirement;

@Data
public class QuestRequirement extends Requirement {

    @NonNull
    Quest quest;
    Quest.State questState;

    public QuestRequirement(Quest quest) {
        this.setQuest(quest);
        this.questState = Quest.State.COMPLETE;
    }

    public QuestRequirement(QuestInformation questInformation) {
        this(questInformation, Quest.State.COMPLETE);
    }

    public QuestRequirement(QuestHelperQuest questHelperQuest, QuestState newQuestState) {
        this(questHelperQuest.getQuestInformation(), newQuestState);
    }

    public QuestRequirement(QuestInformation questInformation, QuestState newQuestState) {
        this(questInformation, newQuestState.getQuestState());
    }

    public QuestRequirement(QuestInformation questInformation, Quest.State questState) {
//        this.setQuestInformation(questInformation);
        this.setQuest(questInformation.getQuest());
        this.setQuestState(questState);
    }

    // Should work, but need to check and make sure
    // if in progress and complete are related.
    @Override
//    public boolean check() {
//        Quest.State currentQuestState = quest.getState();
//        return currentQuestState == questState;
//    }
    public boolean check() {
        Quest.State currentQuestState = quest.getState();
        return currentQuestState == Quest.State.COMPLETE;
    }

    @Override
    public String toString() {
        return "Quest Requirement: " + quest.toString();
    }

}
