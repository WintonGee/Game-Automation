package scripts.main_package.a_skill_data.combat_skills;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.Skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Data
@Accessors(chain = true)
public class Monster {

    String name; // Mainly used for display/gui
    ArrayList<String> namesList = new ArrayList<>();

    // levels
    int hitpoints, attackLevel, strengthLevel, defenceLevel, magicLevel, rangedLevel;

    // Aggressive
    int attackBonus, meleeStrength, magicAttack, magicStrength, rangedAttack, rangedStrength;

    // Defensive
    int stabDefence, slashDefence, crushDefence, magicDefence, rangedDefence;

    int combatLevel, attackSpeed = 4, maxHit = 1;
    Combat.AttackStyle mainAttackStyle;
    String style; //TODO

    boolean aggressive, poisonous;

    //TODO implement
    MonsterFinishTask monsterFinishTask;

    // Requirements to kill the monster
    int minHpReq = 10, minSkillReq = 1;

    // Settings
    // This can be used for gui, seeded options, or monsters that need a certain attack style.
    boolean meleeEnabled = true, mageEnabled = true, rangedEnabled = true;


    //TODO specific items needed for killing?
    // inv, and equipped items.

    public Monster(String name) {
        this.setName(name);

        addNames(name);
    }

    //TODO replace the hp and skill req and determine using the stats
    public boolean isSkillsValid(Skill skill) {
        return Skill.HITPOINTS.getActualLevel() >= minHpReq && skill.getActualLevel() >= minSkillReq;
    }

    public Monster addNames(String... names) {
        this.namesList.addAll(Arrays.asList(names));
        return this;
    }

    public Monster setGeneralStats(int combatLevel, int attackSpeed, int maxHit, String style) {
        this.combatLevel = combatLevel;
        this.attackSpeed = attackSpeed;
        this.maxHit = maxHit;
        this.style = style;
        return this;
    }

    public Monster setLevels(int hitpoints, int attack, int strength, int defence, int magic, int ranged) {
        this.hitpoints = hitpoints;
        this.attackLevel = attack;
        this.strengthLevel = strength;
        this.defenceLevel = defence;
        this.magicLevel = magic;
        this.rangedLevel = ranged;
        return this;
    }

    public Monster setAggressiveStats(int attackBonus, int meleeStrength, int magicAttack, int magicStrength, int rangedAttack, int rangedStrength) {
        this.attackBonus = attackBonus;
        this.meleeStrength = meleeStrength;
        this.magicAttack = magicAttack;
        this.magicStrength = magicStrength;
        this.rangedAttack = rangedAttack;
        this.rangedStrength = rangedStrength;
        return this;
    }

    public Monster setDefensiveStats(int stabDefence, int slashDefence, int crushDefence, int magicDefence, int rangedDefence) {
        this.stabDefence = stabDefence;
        this.slashDefence = slashDefence;
        this.crushDefence = crushDefence;
        this.magicDefence = magicDefence;
        this.rangedDefence = rangedDefence;
        return this;
    }

//    public int getMaxHit() {
//        int styleBonus = 0; //TODO figure out what style bonus is
//        int effective = strengthLevel + 8 + styleBonus;
//        return UtilHits.getMaxMeleeHit(effective, meleeStrength);
//    }


}
