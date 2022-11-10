package scripts.main_package.a_skill_data.combat_skills;

import lombok.Getter;
import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Used for determining the experience a combat style yields
// This should only used for the general items.
// General as in items which don't have different exp rewards
// For example, abyssal bludgeons, do not use this enum.
// TODO make a class for specifically for different experience items.
// "Special Cases"
public class CombatUtil {

    // Note: All combat styles give hitpoints.
    public enum CombatStyleExperience {

        // Melee
        ACCURATE(Combat.AttackStyle.ACCURATE, Skill.ATTACK),
        AGGRESSIVE(Combat.AttackStyle.AGGRESSIVE, Skill.STRENGTH),
        DEFENSIVE(Combat.AttackStyle.DEFENSIVE, Skill.DEFENCE),
        CONTROLLED(Combat.AttackStyle.CONTROLLED, Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE),

        // Ranged
        RANGED_ACCURATE(Combat.AttackStyle.RANGED_ACCURATE, Skill.RANGED),
        RAPID(Combat.AttackStyle.RAPID, Skill.RANGED),
        LONGRANGE(Combat.AttackStyle.LONGRANGE, Skill.RANGED, Skill.DEFENCE),

        // Magic
        MAGIC_ACCURATE(Combat.AttackStyle.ACCURATE, Skill.MAGIC),
        MAGIC_LONGRANGE(Combat.AttackStyle.LONGRANGE, Skill.MAGIC, Skill.DEFENCE);

        @Getter
        Combat.AttackStyle attackStyle;
        @Getter
        ArrayList<Skill> skillExpList = new ArrayList<>(Collections.singletonList(Skill.HITPOINTS));

        CombatStyleExperience(Combat.AttackStyle attackStyle, Skill... skills) {
            this.attackStyle = attackStyle;
            skillExpList.addAll(Arrays.asList(skills));
        }
    }

    //TODO have a handle method here to select the skill needed?
    // include support for magic through checking each one if selectable

    public static Combat.AttackStyle getAttackStyleWithExperience(Skill skillExp) {
        return getAttackStyleWithExperience(skillExp, true);
    }

    public static Combat.AttackStyle getAttackStyleWithExperience(Skill skillExp, boolean allowSharedExp) {
        List<Combat.AttackStyle> attackStyleList = getAttackStyleWithExperienceList(skillExp, allowSharedExp);
        if (attackStyleList.size() == 0) {
            Log.error("[CombatExperience] Failed to determine an attack style that gives experience: " + skillExp);
            return null;
        }

        Collections.shuffle(attackStyleList);
        return attackStyleList.get(0);
    }

    public static List<Combat.AttackStyle> getAttackStyleWithExperienceList(Skill skillExp, boolean allowSharedExp) {
        return Arrays.stream(CombatStyleExperience.values())
                .filter(o -> o.skillExpList.contains(skillExp))
                .filter(o -> Combat.isAttackStyleAvailable(o.getAttackStyle()))
                // Size of 2 = contains skillExp and HitPoint experience yields.
                .filter(o -> allowSharedExp || o.getSkillExpList().size() == 2)
                .map(o -> o.attackStyle)
                .collect(Collectors.toList());
    }

}
