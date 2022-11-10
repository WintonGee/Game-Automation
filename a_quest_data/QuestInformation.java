package scripts.main_package.a_quest_data;

import lombok.Data;
import org.tribot.script.sdk.Quest;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.requirement.Requirement;

import java.util.List;

@Data
public class QuestInformation {

    Quest quest;
    BasicQuestHelper basicQuestHelper;

    public QuestInformation(Quest quest, BasicQuestHelper basicQuestHelper) {
        this.setQuest(quest);
        this.setBasicQuestHelper(basicQuestHelper);
    }

    public boolean isComplete() {
        return quest.getState() == Quest.State.COMPLETE;
    }

    public boolean isCharacterRequirementsValid() {
        List<Requirement> generalRequirements = this.basicQuestHelper.getGeneralRequirements();
        if (generalRequirements == null)
            return true;
        return generalRequirements.stream().allMatch(Requirement::check);
    }

}
