package scripts.main_package.a_quest_data.requirement.player;

import org.tribot.script.sdk.MyPlayer;
import scripts.main_package.a_quest_data.requirement.Requirement;

public class CombatLevelRequirement extends Requirement {

    int level;

    public CombatLevelRequirement(int level) {
        this.level = level;
    }

    @Override
    public boolean check() {
        return MyPlayer.getCombatLevel() >= level;
    }

    @Override
    public String toString() {
        return "CombatLevelRequirement: " + level;
    }

}
