package scripts.main_package.item_data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.requirement.player.SkillRequirement;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.item.equipment.EquipmentBuilder;
import scripts.main_package.item.equipment.EquipmentItem;
import scripts.raw_data.ItemID;

import java.util.ArrayList;

public class WeaponItemData {

    public enum WeaponMelee {

        BRONZE_SCIMITAR(new EquipmentBuilder(ItemID.BRONZE_SCIMITAR).addAttack(1)),
        IRON_SCIMITAR(new EquipmentBuilder(ItemID.IRON_SCIMITAR).addAttack(1).setScore(3)),
        STEEL_SCIMITAR(new EquipmentBuilder(ItemID.STEEL_SCIMITAR).addAttack(5).setScore(5)),
        MITHRIL_SCIMITAR(new EquipmentBuilder(ItemID.MITHRIL_SCIMITAR).addAttack(20).setScore(20)),
        ADAMANT_SCIMITAR(new EquipmentBuilder(ItemID.ADAMANT_SCIMITAR).addAttack(30).setScore(30)),
        RUNE_SCIMITAR(new EquipmentBuilder(ItemID.RUNE_SCIMITAR).addAttack(40).setScore(40)),
        DRAGON_SCIMITAR(new EquipmentBuilder(ItemID.DRAGON_SCIMITAR).addAttack(60).setScore(60).addQuest(Quest.MONKEY_MADNESS_I)) // TODO need to check id

        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        WeaponMelee(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setMelee()
                    .setWeapon()
                    .build()
            );
        }
    }

    //TODO need to somehow set the score based on the best spell that can be cast
    public enum WeaponMage {

        // AIR
        STAFF_0F_AIR(new EquipmentBuilder(ItemID.STAFF_OF_AIR).addMagic(1).addAttack(1).setCastType(SpellCastType.AIR)),
        AIR_BATTLESTAFF(new EquipmentBuilder(ItemID.STAFF_OF_AIR).addMagic(30).addAttack(30).setCastType(SpellCastType.AIR)),
        MYSTIC_AIR_STAFF(new EquipmentBuilder(ItemID.MYSTIC_AIR_STAFF).addMagic(40).addAttack(40).setCastType(SpellCastType.AIR)),

        // WATER
        STAFF_0F_WATER(new EquipmentBuilder(ItemID.STAFF_OF_WATER).addMagic(1).addAttack(1).setCastType(SpellCastType.WATER)),
        WATER_BATTLESTAFF(new EquipmentBuilder(ItemID.STAFF_OF_WATER).addMagic(30).addAttack(30).setCastType(SpellCastType.WATER)),
        MYSTIC_WATER_STAFF(new EquipmentBuilder(ItemID.MYSTIC_WATER_STAFF).addMagic(40).addAttack(40).setCastType(SpellCastType.WATER)),

        // EARTH
        STAFF_0F_EARTH(new EquipmentBuilder(ItemID.STAFF_OF_EARTH).addMagic(1).addAttack(1).setCastType(SpellCastType.EARTH)),
        EARTH_BATTLESTAFF(new EquipmentBuilder(ItemID.STAFF_OF_EARTH).addMagic(30).addAttack(30).setCastType(SpellCastType.EARTH)),
        MYSTIC_EARTH_STAFF(new EquipmentBuilder(ItemID.MYSTIC_EARTH_STAFF).addMagic(40).addAttack(40).setCastType(SpellCastType.EARTH)),

        // Fire
        STAFF_0F_FIRE(new EquipmentBuilder(ItemID.STAFF_OF_FIRE).addMagic(1).addAttack(1).setCastType(SpellCastType.FIRE)),
        FIRE_BATTLESTAFF(new EquipmentBuilder(ItemID.STAFF_OF_FIRE).addMagic(30).addAttack(30).setCastType(SpellCastType.FIRE)),
        MYSTIC_FIRE_STAFF(new EquipmentBuilder(ItemID.MYSTIC_FIRE_STAFF).addMagic(40).addAttack(40).setCastType(SpellCastType.FIRE)),

        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        WeaponMage(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setMage()
                    .setWeapon()
                    .build()
            );
        }
    }

    public enum WeaponRanged {

        SHORTBOW(new EquipmentBuilder(ItemID.SHORTBOW).setTwoHanded().addRanged(1).setScore(8).setBestArrow(RangedArrow.IRON_ARROW)),
        OAK_SHORTBOW(new EquipmentBuilder(ItemID.OAK_SHORTBOW).setTwoHanded().addRanged(5).setScore(14).setBestArrow(RangedArrow.STEEL_ARROW)),
        WILLOW_SHORTBOW(new EquipmentBuilder(ItemID.WILLOW_SHORTBOW).setTwoHanded().addRanged(20).setScore(20).setBestArrow(RangedArrow.MITHRIL_ARROW)),
        MAPLE_SHORTBOW(new EquipmentBuilder(ItemID.MAPLE_SHORTBOW).setTwoHanded().addRanged(30).setScore(29).setBestArrow(RangedArrow.ADAMANT_ARROW)),
        YEW_SHORTBOW(new EquipmentBuilder(ItemID.YEW_SHORTBOW).setTwoHanded().addRanged(40).setScore(47).setBestArrow(RangedArrow.RUNE_ARROW)),
        MAGIC_SHORTBOW(new EquipmentBuilder(ItemID.MAGIC_SHORTBOW).setTwoHanded().addRanged(50).setScore(69).setBestArrow(RangedArrow.AMETHYST_ARROW))
        //TODO magic shortbow I
        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        WeaponRanged(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setRanged()
                    .setWeapon()
                    .build()
            );
        }
    }

    public enum RangedArrow {

        BRONZE_ARROW(ItemID.BRONZE_ARROW, 1, 7),
        IRON_ARROW(ItemID.IRON_ARROW, 1, 10),
        STEEL_ARROW(ItemID.STEEL_ARROW, 5, 16),
        MITHRIL_ARROW(ItemID.MITHRIL_ARROW, 20, 22),
        ADAMANT_ARROW(ItemID.ADAMANT_ARROW, 30, 31),
        RUNE_ARROW(ItemID.RUNE_ARROW, 40, 49),
        AMETHYST_ARROW(ItemID.AMETHYST_ARROW, 50, 55);

        // Stat score is based off accuracy
        @Getter
        int id, level, statScore;

        RangedArrow(int id, int level, int statScore) {
            this.id = id;
            this.level = level;
            this.statScore = statScore;
        }

        public DetailedItem getDetailedItem() {
            return new DetailedItem(id);
        }

        public SkillRequirement getSkillRequirement() {
            return new SkillRequirement(level, Skill.RANGED);
        }

        @Override
        public String toString() {
            return Utils.getItemName(id);
        }

    }

    public enum ElementalStaff {

        // Fire
        MYSTIC_FIRE_STAFF(ItemID.MYSTIC_FIRE_STAFF, 40, ItemID.FIRE_RUNE),
        FIRE_BATTLESTAFF(ItemID.FIRE_BATTLESTAFF, 30, ItemID.FIRE_RUNE),
        STAFF_0F_FIRE(ItemID.STAFF_OF_FIRE, 1, ItemID.FIRE_RUNE),

        // EARTH
        MYSTIC_EARTH_STAFF(ItemID.MYSTIC_EARTH_STAFF, 40, ItemID.EARTH_RUNE),
        EARTH_BATTLESTAFF(ItemID.EARTH_BATTLESTAFF, 30, ItemID.EARTH_RUNE),
        STAFF_0F_EARTH(ItemID.STAFF_OF_EARTH, 1, ItemID.EARTH_RUNE),

        // WATER
        MYSTIC_WATER_STAFF(ItemID.MYSTIC_WATER_STAFF, 40, ItemID.WATER_RUNE),
        WATER_BATTLESTAFF(ItemID.WATER_BATTLESTAFF, 30, ItemID.WATER_RUNE),
        STAFF_0F_WATER(ItemID.STAFF_OF_WATER, 1, ItemID.WATER_RUNE),

        // AIR
        MYSTIC_AIR_STAFF(ItemID.MYSTIC_AIR_STAFF, 40, ItemID.AIR_RUNE),
        AIR_BATTLESTAFF(ItemID.AIR_BATTLESTAFF, 30, ItemID.AIR_RUNE),
        STAFF_0F_AIR(ItemID.STAFF_OF_AIR, 1, ItemID.AIR_RUNE),

        ;

        int id, level;
        @Getter
        ArrayList<Integer> unlimitedRunesList = new ArrayList<>();

        ElementalStaff(int id, int level, int... unlimitedRunes) {
            this.level = level;
            for (int unlimitedRune : unlimitedRunes)
                unlimitedRunesList.add(unlimitedRune);
        }

        public EquipmentBuilder getEquipmentBuilder() {
            return new EquipmentBuilder(id)
                    .addAttack(level)
                    .addMagic(level);
        }

        public boolean isMembers() {
            return Utils.isMembers(id);
        }

        public boolean canEquip() {
            return Skill.MAGIC.getActualLevel() >= level && Skill.ATTACK.getActualLevel() >= level;
        }

    }

    public enum SpellCastType {

        AIR(SpellCastData.WIND_STRIKE, SpellCastData.WIND_BOLT, SpellCastData.WIND_BLAST, SpellCastData.WIND_WAVE, SpellCastData.WIND_SURGE),
        WATER(SpellCastData.WATER_STRIKE, SpellCastData.WATER_BOLT, SpellCastData.WATER_BLAST, SpellCastData.WATER_WAVE, SpellCastData.WATER_SURGE),
        EARTH(SpellCastData.EARTH_STRIKE, SpellCastData.EARTH_BOLT, SpellCastData.EARTH_BLAST, SpellCastData.EARTH_WAVE, SpellCastData.EARTH_SURGE),
        FIRE(SpellCastData.FIRE_STRIKE, SpellCastData.FIRE_BOLT, SpellCastData.FIRE_BLAST, SpellCastData.FIRE_WAVE, SpellCastData.FIRE_SURGE),

        ;

        @Getter
        ArrayList<SpellCast> spellCastList = new ArrayList<>();

        SpellCastType(SpellCastData... spellCastDatas) {
            for (SpellCastData spellCastData : spellCastDatas) {
                spellCastList.add(spellCastData.getSpellCast());
            }
        }

        public SpellCast getBestSpellCast() {
            SpellCast bestCast = null;
            for (SpellCast spellCast : spellCastList) {
                if (spellCast.requirementsValid())
                    bestCast = spellCast;
            }
            return bestCast;
        }

    }

    public enum SpellCastData {

        // Strike spells
        WIND_STRIKE(new SpellCast(Combat.AutocastableSpell.WIND_STRIKE, 1).setMaxHit(2).setExp(5.5)
                .addRune(ItemID.AIR_RUNE, 1).addRune(ItemID.MIND_RUNE, 1)),

        WATER_STRIKE(new SpellCast(Combat.AutocastableSpell.WATER_STRIKE, 5).setMaxHit(4).setExp(7.5)
                .addRune(ItemID.AIR_RUNE, 1).addRune(ItemID.MIND_RUNE, 1).addRune(ItemID.WATER_RUNE, 1)),

        EARTH_STRIKE(new SpellCast(Combat.AutocastableSpell.EARTH_STRIKE, 9).setMaxHit(6).setExp(9.5)
                .addRune(ItemID.AIR_RUNE, 1).addRune(ItemID.MIND_RUNE, 1).addRune(ItemID.EARTH_RUNE, 2)),

        FIRE_STRIKE(new SpellCast(Combat.AutocastableSpell.FIRE_STRIKE, 13).setMaxHit(8).setExp(11.5)
                .addRune(ItemID.AIR_RUNE, 2).addRune(ItemID.MIND_RUNE, 1).addRune(ItemID.FIRE_RUNE, 3)),

        // Bolt spells
        WIND_BOLT(new SpellCast(Combat.AutocastableSpell.WIND_BOLT, 17).setMaxHit(9).setExp(13.5)
                .addRune(ItemID.AIR_RUNE, 2).addRune(ItemID.CHAOS_RUNE, 1)),

        WATER_BOLT(new SpellCast(Combat.AutocastableSpell.WATER_BOLT, 23).setMaxHit(10).setExp(16.5)
                .addRune(ItemID.AIR_RUNE, 2).addRune(ItemID.CHAOS_RUNE, 1).addRune(ItemID.WATER_RUNE, 2)),

        EARTH_BOLT(new SpellCast(Combat.AutocastableSpell.EARTH_BOLT, 29).setMaxHit(11).setExp(19.5)
                .addRune(ItemID.AIR_RUNE, 2).addRune(ItemID.CHAOS_RUNE, 1).addRune(ItemID.EARTH_RUNE, 3)),

        FIRE_BOLT(new SpellCast(Combat.AutocastableSpell.FIRE_BOLT, 35).setMaxHit(12).setExp(22.5)
                .addRune(ItemID.AIR_RUNE, 3).addRune(ItemID.CHAOS_RUNE, 1).addRune(ItemID.FIRE_RUNE, 4)),

        // Blast spells
        WIND_BLAST(new SpellCast(Combat.AutocastableSpell.WIND_BLAST, 41).setMaxHit(13).setExp(25.5)
                .addRune(ItemID.AIR_RUNE, 3).addRune(ItemID.DEATH_RUNE, 1)),

        WATER_BLAST(new SpellCast(Combat.AutocastableSpell.WATER_BLAST, 47).setMaxHit(14).setExp(28.5)
                .addRune(ItemID.AIR_RUNE, 3).addRune(ItemID.DEATH_RUNE, 1).addRune(ItemID.WATER_RUNE, 3)),

        EARTH_BLAST(new SpellCast(Combat.AutocastableSpell.EARTH_BLAST, 53).setMaxHit(15).setExp(31.5)
                .addRune(ItemID.AIR_RUNE, 3).addRune(ItemID.DEATH_RUNE, 1).addRune(ItemID.EARTH_RUNE, 4)),

        FIRE_BLAST(new SpellCast(Combat.AutocastableSpell.FIRE_BLAST, 59).setMaxHit(16).setExp(34.5)
                .addRune(ItemID.AIR_RUNE, 4).addRune(ItemID.DEATH_RUNE, 1).addRune(ItemID.FIRE_RUNE, 5)),

        // Wave spells
        WIND_WAVE(new SpellCast(Combat.AutocastableSpell.WIND_WAVE, 62).setMaxHit(17).setExp(36)
                .addRune(ItemID.AIR_RUNE, 5).addRune(ItemID.BLOOD_RUNE, 1)),

        WATER_WAVE(new SpellCast(Combat.AutocastableSpell.WATER_WAVE, 65).setMaxHit(18).setExp(37.5)
                .addRune(ItemID.AIR_RUNE, 5).addRune(ItemID.BLOOD_RUNE, 1).addRune(ItemID.WATER_RUNE, 7)),

        EARTH_WAVE(new SpellCast(Combat.AutocastableSpell.EARTH_WAVE, 70).setMaxHit(19).setExp(40)
                .addRune(ItemID.AIR_RUNE, 5).addRune(ItemID.BLOOD_RUNE, 1).addRune(ItemID.EARTH_RUNE, 7)),

        FIRE_WAVE(new SpellCast(Combat.AutocastableSpell.FIRE_WAVE, 75).setMaxHit(20).setExp(42.5)
                .addRune(ItemID.AIR_RUNE, 5).addRune(ItemID.BLOOD_RUNE, 1).addRune(ItemID.FIRE_RUNE, 7)),

        // Surge Spells
        WIND_SURGE(new SpellCast(Combat.AutocastableSpell.WIND_SURGE, 81).setMaxHit(21).setExp(44.5)
                .addRune(ItemID.AIR_RUNE, 7).addRune(ItemID.WRATH_RUNE, 1)),

        WATER_SURGE(new SpellCast(Combat.AutocastableSpell.WATER_SURGE, 85).setMaxHit(22).setExp(46.5)
                .addRune(ItemID.AIR_RUNE, 7).addRune(ItemID.WRATH_RUNE, 1).addRune(ItemID.WATER_RUNE, 10)),

        EARTH_SURGE(new SpellCast(Combat.AutocastableSpell.EARTH_SURGE, 90).setMaxHit(23).setExp(48.5)
                .addRune(ItemID.AIR_RUNE, 7).addRune(ItemID.WRATH_RUNE, 1).addRune(ItemID.EARTH_RUNE, 10)),

        FIRE_SURGE(new SpellCast(Combat.AutocastableSpell.FIRE_SURGE, 95).setMaxHit(24).setExp(50.5)
                .addRune(ItemID.AIR_RUNE, 7).addRune(ItemID.WRATH_RUNE, 1).addRune(ItemID.FIRE_RUNE, 10)),

        ;

        @Getter
        SpellCast spellCast;

        SpellCastData(SpellCast spellCast) {
            this.spellCast = spellCast;
        }


    }

    @Accessors(chain = true)
    public static class SpellCast {

        Combat.AutocastableSpell autocastableSpell;

        @Setter
        @Getter
        int level, maxHit;

        @Setter
        double exp;

        @Getter
        ArrayList<DetailedItem> runes = new ArrayList<>();

        public SpellCast(Combat.AutocastableSpell autocastableSpell, int level) {
            this.autocastableSpell = autocastableSpell;
            this.level = level;
        }

        public SpellCast addRune(int runeId, int amount) {
            runes.add(new DetailedItem(runeId, amount));
            return this;
        }

        public boolean handleAutocast(boolean defensive) {
            return Combat.setAutocastSpell(autocastableSpell, defensive);
        }

        public boolean isMembers() {
            return runes.stream().anyMatch(item -> {
                int runeId = item.getItemId();
                return Utils.isMembers(runeId);
            });
        }

        public boolean requirementsValid() {
            return Skill.MAGIC.getActualLevel() >= level;
        }

        // Used during the execution part
        public boolean isCastable() {
            if (Skill.MAGIC.getActualLevel() < level)
                return false;
            //TODO check if have the runes
            return false;
        }

        //TODO check, test, etc...
        public int getStatScore() {
            return level;
        }

        @Override
        public String toString() {
            return this.autocastableSpell.name();
        }

    }

}
