package scripts.main_package.item.equipment;

import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.player.SkillRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestRequirement;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.item_data.WeaponItemData;

import java.util.ArrayList;

public class EquipmentBuilder {

    EquipmentItem.GearGroup gearGroup = EquipmentItem.GearGroup.GENERAL;
    int mainId;
    Equipment.Slot slot;

    ArrayList<Integer> altIds = new ArrayList<>();
    ArrayList<Requirement> requirements = new ArrayList<>();

    int statScore = 0;

    // For weapon type items
    boolean twoHanded;
    WeaponItemData.RangedArrow bestArrow;
    WeaponItemData.SpellCastType spellCastType;

    public EquipmentBuilder(int mainId) {
        this.mainId = mainId;
    }

    public EquipmentItem build() {
        EquipmentItem item = new EquipmentItem(mainId);
        item.setSlot(slot);
        item.setGearGroup(gearGroup);
        item.setAltIds(altIds);
        item.setRequirements(requirements);
        item.setStatScore(statScore);

        item.setTwoHanded(twoHanded);
        item.setBestArrow(bestArrow);
        item.setSpellCastType(spellCastType);

        return item;
    }

    public DetailedItem getDetailedItem() {
        DetailedItem detailedItem = new DetailedItem(mainId);
        for (int id : altIds)
            detailedItem.addAlt(id);
        return detailedItem;
    }

    public boolean isMembers() {
        return Utils.isMembers(mainId);
    }

    public EquipmentBuilder addAlts(int... ids) {
        for (int id : ids)
            altIds.add(id);
        return this;
    }

    // Requirements
    public EquipmentBuilder addQuest(Quest quest) {
        statScore += 5;
        requirements.add(new QuestRequirement(quest));
        return this;
    }

    public EquipmentBuilder addHitpoints(int level) {
        addSkillRequirement(Skill.HITPOINTS, level);
        return this;
    }

    public EquipmentBuilder addDefence(int level) {
        statScore += level;
        addSkillRequirement(Skill.DEFENCE, level);
        return this;
    }

    public EquipmentBuilder addRanged(int level) {
        addSkillRequirement(Skill.RANGED, level);
        return this;
    }

    public EquipmentBuilder addMagic(int level) {
        addSkillRequirement(Skill.MAGIC, level);
        return this;
    }

    public EquipmentBuilder addAttack(int level) {
        addSkillRequirement(Skill.ATTACK, level);
        return this;
    }

    public EquipmentBuilder addStrength(int level) {
        addSkillRequirement(Skill.STRENGTH, level);
        return this;
    }

    public EquipmentBuilder addSkillRequirement(Skill skill, int level) {
        statScore += (level / 10);
        requirements.add(new SkillRequirement(skill, level));
        return this;
    }

    // Gear groups
    public EquipmentBuilder setMage() {
        this.gearGroup = EquipmentItem.GearGroup.MAGE;
        return this;
    }

    public EquipmentBuilder setMelee() {
        this.gearGroup = EquipmentItem.GearGroup.MELEE;
        return this;
    }

    public EquipmentBuilder setRanged() {
        this.gearGroup = EquipmentItem.GearGroup.RANGED;
        return this;
    }

    // Slots
    public EquipmentBuilder setNeck() {
        this.slot = Equipment.Slot.NECK;
        return this;
    }

    public EquipmentBuilder setRing() {
        this.slot = Equipment.Slot.RING;
        return this;
    }

    public EquipmentBuilder setHands() {
        this.slot = Equipment.Slot.HANDS;
        return this;
    }

    public EquipmentBuilder setHelmet() {
        this.slot = Equipment.Slot.HEAD;
        return this;
    }

    public EquipmentBuilder setBody() {
        this.slot = Equipment.Slot.BODY;
        return this;
    }

    public EquipmentBuilder setLegs() {
        this.slot = Equipment.Slot.LEGS;
        return this;
    }

    public EquipmentBuilder setFeet() {
        this.slot = Equipment.Slot.FEET;
        return this;
    }

    public EquipmentBuilder setShield() {
        this.slot = Equipment.Slot.SHIELD;
        return this;
    }

    public EquipmentBuilder setCape() {
        this.slot = Equipment.Slot.CAPE;
        return this;
    }

    public EquipmentBuilder setWeapon() {
        this.slot = Equipment.Slot.WEAPON;
        return this;
    }

    public EquipmentBuilder setScore(int score) {
        this.statScore = score;
        return this;
    }

    public EquipmentBuilder setTwoHanded() {
        this.twoHanded = true;
        return this;
    }

    public EquipmentBuilder setBestArrow(WeaponItemData.RangedArrow bestArrow) {
        this.bestArrow = bestArrow;
        return this;
    }

    public EquipmentBuilder setCastType(WeaponItemData.SpellCastType spellCastType) {
        this.spellCastType = spellCastType;
        return this;
    }

}
