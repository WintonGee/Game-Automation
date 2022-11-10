package scripts.main_package.a_quest_data.objects;

import lombok.Getter;
import lombok.Setter;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.step.QuestStep;

// Used to store a requirement - Quest Step
public class RequirementQuestStep {

    @Setter
    @Getter
    Requirement requirement;

    @Setter
    @Getter
    QuestStep questStep;

    public RequirementQuestStep(Requirement requirement, QuestStep questStep) {
        this.setRequirement(requirement);
        this.setQuestStep(questStep);
    }


}
