package scripts.main_package.api.task.general_condition;

import lombok.Setter;
import org.tribot.script.sdk.Skill;
import scripts.main_package.api.task.TaskCondition;

public class ConditionMaxLevel extends TaskCondition {

    @Setter
    int level;

    @Setter
    Skill skill;

    public ConditionMaxLevel(int level, Skill skill) {
        this.setLevel(level);
        this.setSkill(skill);
    }

    @Override
    public String getConditionName() {
        return skill + " Max Level: " + level;
    }

    @Override
    public boolean check() {
        return this.skill.getActualLevel() <= this.level;
    }

}