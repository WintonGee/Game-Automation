package scripts.main_package.a_quest_data.objects;

import lombok.Data;
import org.tribot.script.sdk.Skill;

@Data
public class ExperienceReward {

    Skill skill;
    double amount;

    public ExperienceReward(Skill skill, double amount) {
        this.setSkill(skill);
        this.setAmount(amount);
    }

    @Override
    public String toString() {
        return "Experience Reward: " + amount + " " + skill;
    }

}
