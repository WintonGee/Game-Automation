package scripts.main_package.a_skill_data.combat_skills;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import org.tribot.script.sdk.Log;
import scripts.main_package.a_skill_data.util.MonsterLocationSelection;
import scripts.main_package.a_skill_data.util.SkillingLocation;

public class MonsterLocation {

    SkillingLocation skillingLocation;
    Monster monster;

    public MonsterLocation(SkillingLocation skillingLocation, Monster monster) {
        this.skillingLocation = skillingLocation;
        this.monster = monster;
    }

    public boolean isMembers() {
        return !skillingLocation.isF2P();
    }

    public boolean isMatchSelection(MonsterLocationSelection selection) {
        return monster.getName().equals(selection.getMonsterName());
    }

    public void updateSettings(MonsterLocationSelection selection) {
        monster.setMeleeEnabled(selection.isMeleeEnabled());
        monster.setMageEnabled(selection.isMageEnabled());
        monster.setRangedEnabled(selection.isRangedEnabled());
    }

}
