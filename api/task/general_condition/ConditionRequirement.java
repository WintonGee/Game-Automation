package scripts.main_package.api.task.general_condition;

import lombok.Getter;
import lombok.Setter;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.api.task.TaskCondition;

public class ConditionRequirement extends TaskCondition {

    @Setter
    @Getter
    Requirement requirement;

    public ConditionRequirement(Requirement requirement) {
        this.setRequirement(requirement);
    }

    @Override
    public String getConditionName() {
        return requirement.getDisplayText();
    }

    @Override
    public boolean check() {
        return requirement.check();
    }

}
