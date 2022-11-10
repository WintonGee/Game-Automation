package scripts.main_package.api.task.general_condition;

import lombok.Setter;
import org.tribot.script.sdk.Skill;
import scripts.main_package.api.task.TaskCondition;

public class ConditionLevel extends TaskCondition {

    @Setter
    int level;

    @Setter
    Skill skill;

    public ConditionLevel(int level, Skill skill) {
        this.setLevel(level);
        this.setSkill(skill);
    }

    public ConditionLevel(Skill skill, int level) {
        this.setLevel(level);
        this.setSkill(skill);
    }

    @Override
    public String getConditionName() {
        return "Level Condition: " + skill + " " + level;
    }

    @Override
    public boolean check() {
        return this.skill.getActualLevel() >= this.level;
    }

}