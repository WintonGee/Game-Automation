package scripts.main_package.a_quest_data.requirement.player;

import lombok.Data;
import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.requirement.Requirement;

@Data
public class SkillRequirement extends Requirement {

    Skill skill;
    int level;

    boolean checkCurrent = false;

    public SkillRequirement(int level, Skill skill) {
        this(skill, level);
    }

    public SkillRequirement(Skill skill, int level) {
        this.setSkill(skill);
        this.setLevel(level);
    }

    @Override
    public boolean check() {
        if (checkCurrent)
            return skill.getCurrentLevel() >= level;

        return skill.getActualLevel() >= level;
    }

    @Override
    public String toString() {
        return "Skill Requirement: " + skill.name() + " " + level;
    }

}
