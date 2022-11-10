package scripts.main_package.tasks.combat_setup_task;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.Log;
import scripts.main_package.a_skill_data.combat_skills.CombatUtil;

import java.util.Collections;

// Used to equip and handle whatever is needed to be done for task setup
// - Spells
// - Combat style //TODO
// - Equipping items
@Setter
public class CombatTaskSetupHandler {

    CombatSetupTask combatSetupTask;

    public CombatTaskSetupHandler(CombatSetupTask combatSetupTask) {
        this.setCombatSetupTask(combatSetupTask);
    }

    public SetupResult handle() {


        if (!handleAttackStyle())
            return SetupResult.ATTACK_STYLE;

        return SetupResult.SUCCESS;
    }

    private boolean handleItems() {
        return false;
    }

    private boolean handleArrows() {
        return false;
    }

    private boolean handleSpell() {
        return false;
    }

    private boolean handleAttackStyle() {
        if (combatSetupTask.attackStyleExperience == null)
            return true;

        val validAttackStyles = CombatUtil.getAttackStyleWithExperienceList(combatSetupTask.attackStyleExperience, true);
        if (validAttackStyles.size() == 0) {
            Log.error("[CombatTaskSetupHandler] No valid Attack Style For Experience: " + combatSetupTask.attackStyleExperience);
            return false;
        }

        boolean isAnyAlreadySelected = validAttackStyles.stream().anyMatch(Combat::isAttackStyleSet);
        if (isAnyAlreadySelected) //TODO have a chance to skip return true, this will give a chance to change attack styles
            return true;

        Collections.shuffle(validAttackStyles);
        return Combat.setAttackStyle(validAttackStyles.get(0));
    }

    public enum SetupResult {

        SUCCESS("Success"),
        ATTACK_STYLE("Error: Setting attack style"),
        NO_ARROWS("Error: Out of arrows"),
        NO_RUNES("Error: Out of runes"),
        SETTING_SPELL("Error: Setting Spell"),
        MISSING_ITEM("Error: Missing Item"),
        UNKNOWN("Error: Unknown");

        @Getter
        @Setter
        String message;

        SetupResult(String m) {
            this.setMessage(m);
        }

        public void print() {
            Log.info("[CombatTaskSetupHandler][SetupResult] " + message);
        }

        @Override
        public String toString() {
            return message;
        }

    }

}
