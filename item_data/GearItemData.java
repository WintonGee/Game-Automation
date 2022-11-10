package scripts.main_package.item_data;

import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.Quest;
import scripts.main_package.item.equipment.EquipmentBuilder;
import scripts.main_package.item.equipment.EquipmentItem;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GearItemData {

    public enum GearRing {

        RING_OF_DUELING(new EquipmentBuilder(ItemID.RING_OF_DUELING8)
                .addAlts(ItemID.RING_OF_DUELING7, ItemID.RING_OF_DUELING6, ItemID.RING_OF_DUELING5, ItemID.RING_OF_DUELING4,
                        ItemID.RING_OF_DUELING3, ItemID.RING_OF_DUELING2, ItemID.RING_OF_DUELING1)),

        RING_OF_WEALTH(new EquipmentBuilder(ItemID.RING_OF_WEALTH_5)
                .addAlts(ItemID.RING_OF_WEALTH_4, ItemID.RING_OF_WEALTH_3, ItemID.RING_OF_WEALTH_2, ItemID.RING_OF_WEALTH_1)),

        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        GearRing(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setRing()
                    .build()
            );
        }

    }

    public enum GearNeck {

        // General
        AMULET_OF_ACCURACY(new EquipmentBuilder(ItemID.AMULET_OF_ACCURACY).addDefence(1)),
        AMULET_OF_GLORY(new EquipmentBuilder(ItemID.AMULET_OF_GLORY6)
                .addAlts(ItemID.AMULET_OF_GLORY5, ItemID.AMULET_OF_GLORY4, ItemID.AMULET_OF_GLORY3, ItemID.AMULET_OF_GLORY2, ItemID.AMULET_OF_GLORY1).setScore(100)),

        // Melee
        AMULET_OF_STRENGTH(new EquipmentBuilder(ItemID.AMULET_OF_STRENGTH).setMelee().addDefence(1)),

        // Ranged
        AMULET_OF_POWER(new EquipmentBuilder(ItemID.AMULET_OF_POWER).setRanged().addDefence(1)),

        // Mage
        AMULET_OF_MAGIC(new EquipmentBuilder(ItemID.AMULET_OF_MAGIC).setMage().addDefence(1)),
        LUNAR_AMULET(new EquipmentBuilder(ItemID.LUNAR_AMULET).setMage().addDefence(40).addMagic(65).addQuest(Quest.LUNAR_DIPLOMACY)),

        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        GearNeck(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setNeck()
                    .build()
            );
        }

    }

    public enum GearHand {

        // General
        LEATHER_VAMBRACES(new EquipmentBuilder(ItemID.LEATHER_VAMBRACES).addDefence(1)),

        COMBAT_BRACELET(new EquipmentBuilder(ItemID.COMBAT_BRACELET6)
                .addAlts(ItemID.COMBAT_BRACELET5, ItemID.COMBAT_BRACELET4, ItemID.COMBAT_BRACELET3,
                        ItemID.COMBAT_BRACELET2, ItemID.COMBAT_BRACELET1, ItemID.COMBAT_BRACELET).setScore(100)),

        // Ranged
        SNAKESKIN_VAMBRACES(new EquipmentBuilder(ItemID.SNAKESKIN_VAMBRACES).setRanged().addDefence(30).addRanged(30)),
        GREEN_DHIDE_VAMBRACES(new EquipmentBuilder(ItemID.GREEN_DHIDE_VAMBRACES).setRanged().addRanged(40).setScore(40)),
        BLUE_DHIDE_VAMBRACES(new EquipmentBuilder(ItemID.BLUE_DHIDE_VAMBRACES).setRanged().addRanged(50).setScore(50)),
        RED_DHIDE_VAMBRACES(new EquipmentBuilder(ItemID.RED_DHIDE_VAMBRACES).setRanged().addRanged(60).setScore(60)),
        BLACK_DHIDE_VAMBRACES(new EquipmentBuilder(ItemID.BLACK_DHIDE_VAMBRACES).setRanged().addRanged(70).setScore(70)),

        GUTHIX_BRACERS(new EquipmentBuilder(ItemID.GUTHIX_BRACERS).setRanged().addRanged(70).setScore(75)),
        ANCIENT_BRACERS(new EquipmentBuilder(ItemID.ANCIENT_BRACERS).setRanged().addRanged(70).setScore(75)),
        ARMADYL_BRACERS(new EquipmentBuilder(ItemID.ARMADYL_BRACERS).setRanged().addRanged(70).setScore(75)),
        BANDOS_BRACERS(new EquipmentBuilder(ItemID.BANDOS_BRACERS).setRanged().addRanged(70).setScore(75)),
        SARADOMIN_BRACERS(new EquipmentBuilder(ItemID.SARADOMIN_BRACERS).setRanged().addRanged(70).setScore(75)),
        ZAMORAK_BRACERS(new EquipmentBuilder(ItemID.ZAMORAK_BRACERS).setRanged().addRanged(70).setScore(75)),

        // Magic
        MYSTIC_GLOVES(new EquipmentBuilder(ItemID.MYSTIC_GLOVES).setMage().addDefence(20).addMagic(40)),
        MYSTIC_GLOVES_DARK(new EquipmentBuilder(ItemID.MYSTIC_GLOVES_DARK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_GLOVES_DUSK(new EquipmentBuilder(ItemID.MYSTIC_GLOVES_DUSK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_GLOVES_LIGHT(new EquipmentBuilder(ItemID.MYSTIC_GLOVES_LIGHT).setMage().addDefence(20).addMagic(40)),

        GLOVES_OF_DARKNESS(new EquipmentBuilder(ItemID.GLOVES_OF_DARKNESS).setMage().addDefence(20).addMagic(40)),

        SKELETAL_GLOVES(new EquipmentBuilder(ItemID.SKELETAL_GLOVES).setMage().addDefence(40).addMagic(40).addQuest(Quest.THE_FREMENNIK_TRIALS)),

        LUNAR_GLOVES(new EquipmentBuilder(ItemID.LUNAR_GLOVES).setMage().addDefence(40).addMagic(65).addQuest(Quest.LUNAR_DIPLOMACY)),
        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        GearHand(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setHands()
                    .build()
            );
        }

    }

    public enum GearHelmet {

        // General
        LEATHER_COWL(new EquipmentBuilder(ItemID.LEATHER_COWL).addDefence(1)),

        // Melee
        BRONZE_FULL_HELM(new EquipmentBuilder(ItemID.BRONZE_FULL_HELM).setMelee().addDefence(1)),
        BRONZE_MED_HELM(new EquipmentBuilder(ItemID.BRONZE_MED_HELM).setMelee().addDefence(1)),

        IRON_FULL_HELM(new EquipmentBuilder(ItemID.IRON_FULL_HELM).setMelee().addDefence(1)),
        IRON_MED_HELM(new EquipmentBuilder(ItemID.IRON_MED_HELM).setMelee().addDefence(1)),

        STEEL_FULL_HELM(new EquipmentBuilder(ItemID.STEEL_FULL_HELM).setMelee().addDefence(5)),
        STEEL_MED_HELM(new EquipmentBuilder(ItemID.STEEL_MED_HELM).setMelee().addDefence(5)),

        MITHRIL_FULL_HELM(new EquipmentBuilder(ItemID.MITHRIL_FULL_HELM).setMelee().addDefence(20)),
        MITHRIL_MED_HELM(new EquipmentBuilder(ItemID.MITHRIL_MED_HELM).setMelee().addDefence(20)),

        ADAMANT_FULL_HELM(new EquipmentBuilder(ItemID.ADAMANT_FULL_HELM).setMelee().addDefence(30)),
        ADAMANT_MED_HELM(new EquipmentBuilder(ItemID.ADAMANT_MED_HELM).setMelee().addDefence(30)),

        RUNE_FULL_HELM(new EquipmentBuilder(ItemID.RUNE_FULL_HELM).setMelee().addDefence(40)),
        RUNE_MED_HELM(new EquipmentBuilder(ItemID.RUNE_MED_HELM).setMelee().addDefence(40)),

        GRANITE_HELM(new EquipmentBuilder(ItemID.GRANITE_HELM).setMelee().addDefence(50).addStrength(50)),

        DRAGON_MED_HELM(new EquipmentBuilder(ItemID.DRAGON_MED_HELM).setMelee().addDefence(60)),

        // Ranged
        COIF(new EquipmentBuilder(ItemID.COIF).setRanged().addRanged(20).setScore(20)),
        SNAKESKIN_BANDANA(new EquipmentBuilder(ItemID.SNAKESKIN_BANDANA).setRanged().addDefence(30).addRanged(30)),

        GUTHIX_COIF(new EquipmentBuilder(ItemID.GUTHIX_COIF).setRanged().addDefence(40).addRanged(70)),
        ANCIENT_COIF(new EquipmentBuilder(ItemID.ANCIENT_COIF).setRanged().addDefence(40).addRanged(70)),
        ARMADYL_COIF(new EquipmentBuilder(ItemID.ARMADYL_COIF).setRanged().addDefence(40).addRanged(70)),
        BANDOS_COIF(new EquipmentBuilder(ItemID.BANDOS_COIF).setRanged().addDefence(40).addRanged(70)),
        SARADOMIN_COIF(new EquipmentBuilder(ItemID.SARADOMIN_COIF).setRanged().addDefence(40).addRanged(70)),
        ZAMORAK_COIF(new EquipmentBuilder(ItemID.ZAMORAK_COIF).setRanged().addDefence(40).addRanged(70)),

        KARILS_COIF(new EquipmentBuilder(ItemID.KARILS_COIF).setRanged().addDefence(70).addRanged(70)
                .addAlts(ItemID.KARILS_COIF_100, ItemID.KARILS_COIF_75, ItemID.KARILS_COIF_50, ItemID.KARILS_COIF_25)),

        // Mage
        BLUE_WIZARD_HAT(new EquipmentBuilder(ItemID.BLUE_WIZARD_HAT).setMage().addDefence(1)),
        WIZARD_HAT(new EquipmentBuilder(ItemID.WIZARD_HAT).setMage().addDefence(1)),
        ELDER_CHAOS_HOOD(new EquipmentBuilder(ItemID.ELDER_CHAOS_HOOD).setMage().addDefence(1).addMagic(40)),
        XERICIAN_HAT(new EquipmentBuilder(ItemID.XERICIAN_HAT).setMage().addDefence(10).addMagic(20)),

        MYSTIC_HAT(new EquipmentBuilder(ItemID.MYSTIC_HAT).setMage().addDefence(20).addMagic(40)),
        MYSTIC_HAT_DARK(new EquipmentBuilder(ItemID.MYSTIC_HAT_DARK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_HAT_DUSK(new EquipmentBuilder(ItemID.MYSTIC_HAT_DUSK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_HAT_LIGHT(new EquipmentBuilder(ItemID.MYSTIC_HAT_LIGHT).setMage().addDefence(20).addMagic(40)),

        ENCHANTED_HAT(new EquipmentBuilder(ItemID.ENCHANTED_HAT).setMage().addDefence(20).addMagic(40)),
        HOOD_OF_DARKNESS(new EquipmentBuilder(ItemID.HOOD_OF_DARKNESS).setMage().addDefence(20).addMagic(40)),

        SKELETAL_HELM(new EquipmentBuilder(ItemID.SKELETAL_HELM).setMage().addDefence(40).addMagic(40).addQuest(Quest.THE_FREMENNIK_TRIALS)),

        LUNAR_HELM(new EquipmentBuilder(ItemID.LUNAR_HELM).setMage().addDefence(40).addMagic(65).addQuest(Quest.LUNAR_DIPLOMACY)),

        AHRIMS_HOOD(new EquipmentBuilder(ItemID.AHRIMS_HOOD).setMage().addDefence(70).addMagic(70)
                .addAlts(ItemID.AHRIMS_HOOD_100, ItemID.AHRIMS_HOOD_75, ItemID.AHRIMS_HOOD_50, ItemID.AHRIMS_HOOD_25)),
        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        GearHelmet(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setHelmet()
                    .build()
            );
        }

    }

    public enum GearBody {

        // General
        LEATHER_BODY(new EquipmentBuilder(ItemID.LEATHER_BODY).addDefence(1)),

        // Melee
        BRONZE_PLATEBODY(new EquipmentBuilder(ItemID.BRONZE_PLATEBODY).setMelee().addDefence(1)),
        BRONZE_CHAINBODY(new EquipmentBuilder(ItemID.BRONZE_CHAINBODY).setMelee().addDefence(1)),

        IRON_PLATEBODY(new EquipmentBuilder(ItemID.IRON_PLATEBODY).setMelee().addDefence(1)),
        IRON_CHAINBODY(new EquipmentBuilder(ItemID.IRON_CHAINBODY).setMelee().addDefence(1)),

        STEEL_PLATEBODY(new EquipmentBuilder(ItemID.STEEL_PLATEBODY).setMelee().addDefence(5)),
        STEEL_CHAINBODY(new EquipmentBuilder(ItemID.STEEL_CHAINBODY).setMelee().addDefence(5)),

        MITHRIL_PLATEBODY(new EquipmentBuilder(ItemID.MITHRIL_PLATEBODY).setMelee().addDefence(20)),
        MITHRIL_CHAINBODY(new EquipmentBuilder(ItemID.MITHRIL_CHAINBODY).setMelee().addDefence(20)),

        ADAMANT_PLATEBODY(new EquipmentBuilder(ItemID.ADAMANT_PLATEBODY).setMelee().addDefence(30)),
        ADAMANT_CHAINBODY(new EquipmentBuilder(ItemID.ADAMANT_CHAINBODY).setMelee().addDefence(30)),

        RUNE_PLATEBODY(new EquipmentBuilder(ItemID.RUNE_PLATEBODY).setMelee().addDefence(40).addQuest(Quest.DRAGON_SLAYER_I)),
        RUNE_CHAINBODY(new EquipmentBuilder(ItemID.RUNE_CHAINBODY).setMelee().addDefence(40)),

        GRANITE_BODY(new EquipmentBuilder(ItemID.GRANITE_BODY).setMelee().addDefence(50).addStrength(50)),

        DRAGON_CHAINBODY(new EquipmentBuilder(ItemID.DRAGON_CHAINBODY_3140).setMelee().addDefence(60)),
        DRAGON_PLATEBODY(new EquipmentBuilder(ItemID.DRAGON_PLATEBODY).setMelee().addDefence(60).addQuest(Quest.DRAGON_SLAYER_I)),

        // Ranged
        HARD_LEATHER_BODY(new EquipmentBuilder(ItemID.HARDLEATHER_BODY).setRanged().addDefence(10)),
        STUDDED_BODY(new EquipmentBuilder(ItemID.STUDDED_BODY).setRanged().addDefence(20).addRanged(20)),
        SNAKESKIN_BODY(new EquipmentBuilder(ItemID.SNAKESKIN_BODY).setRanged().addDefence(30).addRanged(30)),
        GREEN_DHIDE_BODY(new EquipmentBuilder(ItemID.GREEN_DHIDE_BODY).setRanged().addDefence(40).addRanged(40).addQuest(Quest.DRAGON_SLAYER_I)),
        BLUE_DHIDE_BODY(new EquipmentBuilder(ItemID.BLUE_DHIDE_BODY).setRanged().addDefence(40).addRanged(50)),
        RED_DHIDE_BODY(new EquipmentBuilder(ItemID.RED_DHIDE_BODY).setRanged().addDefence(40).addRanged(60)),
        BLACK_DHIDE_BODY(new EquipmentBuilder(ItemID.BLACK_DHIDE_BODY).setRanged().addDefence(40).addRanged(70)),

        GUTHIX_DHIDE_BODY(new EquipmentBuilder(ItemID.GUTHIX_DHIDE_BODY).setRanged().addDefence(40).addRanged(70).setScore(75)),
        ANCIENT_DHIDE_BODY(new EquipmentBuilder(ItemID.ANCIENT_DHIDE_BODY).setRanged().addDefence(40).addRanged(70).setScore(75)),
        ARMADYL_DHIDE_BODY(new EquipmentBuilder(ItemID.ARMADYL_DHIDE_BODY).setRanged().addDefence(40).addRanged(70).setScore(75)),
        BANDOS_DHIDE_BODY(new EquipmentBuilder(ItemID.BANDOS_DHIDE_BODY).setRanged().addDefence(40).addRanged(70).setScore(75)),
        SARADOMIN_DHIDE_BODY(new EquipmentBuilder(ItemID.SARADOMIN_DHIDE_BODY).setRanged().addDefence(40).addRanged(70).setScore(75)),
        ZAMORAK_DHIDE_BODY(new EquipmentBuilder(ItemID.ZAMORAK_DHIDE_BODY).setRanged().addDefence(40).addRanged(70).setScore(75)),

        KARILS_LEATHERTOP(new EquipmentBuilder(ItemID.KARILS_LEATHERTOP).setRanged().addDefence(70).addRanged(70)
                .addAlts(ItemID.KARILS_LEATHERTOP_100, ItemID.KARILS_LEATHERTOP_75, ItemID.KARILS_LEATHERTOP_50, ItemID.KARILS_LEATHERTOP_25)),

        // Mage
        ZAMORAK_MONK_TOP(new EquipmentBuilder(ItemID.ZAMORAK_MONK_TOP).setMage().addDefence(1)),
        BLUE_WIZARD_ROBE(new EquipmentBuilder(ItemID.BLUE_WIZARD_ROBE).setMage().addDefence(1)),
        BLACK_ROBE(new EquipmentBuilder(ItemID.BLACK_ROBE).setMage().addDefence(1)),
        ELDER_CHAOS_TOP(new EquipmentBuilder(ItemID.ELDER_CHAOS_TOP).setMage().addDefence(1).addMagic(40)),
        XERICIAN_TOP(new EquipmentBuilder(ItemID.XERICIAN_TOP).setMage().addDefence(10).addMagic(20)),

        MYSTIC_ROBE_TOP(new EquipmentBuilder(ItemID.MYSTIC_ROBE_TOP).setMage().addDefence(20).addMagic(40)),
        MYSTIC_ROBE_TOP_DARK(new EquipmentBuilder(ItemID.MYSTIC_ROBE_TOP_DARK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_ROBE_TOP_DUSK(new EquipmentBuilder(ItemID.MYSTIC_ROBE_TOP_DUSK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_ROBE_TOP_LIGHT(new EquipmentBuilder(ItemID.MYSTIC_ROBE_TOP_LIGHT).setMage().addDefence(20).addMagic(40)),

        ENCHANTED_TOP(new EquipmentBuilder(ItemID.ENCHANTED_TOP).setMage().addDefence(20).addMagic(40)),
        ROBE_TOP_OF_DARKNESS(new EquipmentBuilder(ItemID.ROBE_TOP_OF_DARKNESS).setMage().addDefence(20).addMagic(40)),

        SKELETAL_TOP(new EquipmentBuilder(ItemID.SKELETAL_TOP).setMage().addDefence(40).addMagic(40).addQuest(Quest.THE_FREMENNIK_TRIALS)),

        LUNAR_TORSO(new EquipmentBuilder(ItemID.LUNAR_TORSO).setMage().addDefence(40).addMagic(65).addQuest(Quest.LUNAR_DIPLOMACY)),

        AHRIMS_ROBETOP(new EquipmentBuilder(ItemID.AHRIMS_ROBETOP).setMage().addDefence(70).addMagic(70)
                .addAlts(ItemID.AHRIMS_ROBETOP_100, ItemID.AHRIMS_ROBETOP_75, ItemID.AHRIMS_ROBETOP_50, ItemID.AHRIMS_ROBETOP_25)),
        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        GearBody(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setBody()
                    .build()
            );
        }

    }

    public enum GearLeg {

        // General
        LEATHER_CHAPS(new EquipmentBuilder(ItemID.LEATHER_CHAPS).addDefence(1)),

        // Melee
        BRONZE_LEGS(new EquipmentBuilder(ItemID.BRONZE_PLATELEGS).setMelee().addDefence(1).addAlts(ItemID.BRONZE_PLATESKIRT)),
        BRONZE_PLATESKIRT(new EquipmentBuilder(ItemID.BRONZE_PLATESKIRT).setMelee().addDefence(1).addAlts(ItemID.BRONZE_PLATESKIRT)),

        IRON_LEGS(new EquipmentBuilder(ItemID.IRON_PLATELEGS).setMelee().addDefence(1).addAlts(ItemID.IRON_PLATESKIRT)),
        IRON_PLATESKIRT(new EquipmentBuilder(ItemID.IRON_PLATESKIRT).setMelee().addDefence(1).addAlts(ItemID.IRON_PLATESKIRT)),

        STEEL_LEGS(new EquipmentBuilder(ItemID.STEEL_PLATELEGS).setMelee().addDefence(5).addAlts(ItemID.STEEL_PLATESKIRT)),
        STEEL_PLATESKIRT(new EquipmentBuilder(ItemID.STEEL_PLATESKIRT).setMelee().addDefence(5).addAlts(ItemID.STEEL_PLATESKIRT)),

        MITHRIL_LEGS(new EquipmentBuilder(ItemID.MITHRIL_PLATELEGS).setMelee().addDefence(20).addAlts(ItemID.MITHRIL_PLATESKIRT)),
        MITHRIL_PLATESKIRT(new EquipmentBuilder(ItemID.MITHRIL_PLATESKIRT).setMelee().addDefence(20).addAlts(ItemID.MITHRIL_PLATESKIRT)),

        ADAMANT_LEGS(new EquipmentBuilder(ItemID.ADAMANT_PLATELEGS).setMelee().addDefence(30).addAlts(ItemID.ADAMANT_PLATESKIRT)),
        ADAMANT_PLATESKIRT(new EquipmentBuilder(ItemID.ADAMANT_PLATESKIRT).setMelee().addDefence(30).addAlts(ItemID.ADAMANT_PLATESKIRT)),

        RUNE_LEGS(new EquipmentBuilder(ItemID.RUNE_PLATELEGS).setMelee().addDefence(40).addAlts(ItemID.RUNE_PLATESKIRT)),
        RUNE_PLATESKIRT(new EquipmentBuilder(ItemID.RUNE_PLATESKIRT).setMelee().addDefence(40).addAlts(ItemID.RUNE_PLATESKIRT)),

        GRANITE_LEGS(new EquipmentBuilder(ItemID.GRANITE_LEGS).setMelee().addDefence(50).addStrength(50)),

        DRAGON_LEGS(new EquipmentBuilder(ItemID.DRAGON_PLATELEGS).setMelee().addDefence(60)),
        DRAGON_PLATESKIRT(new EquipmentBuilder(ItemID.DRAGON_PLATESKIRT).setMelee().addDefence(60)),

        // Ranged
        STUDDED_CHAPS(new EquipmentBuilder(ItemID.STUDDED_CHAPS).setRanged().addRanged(20).setScore(20)),
        SNAKESKIN_CHAPS(new EquipmentBuilder(ItemID.SNAKESKIN_CHAPS).setRanged().addDefence(30).addRanged(30)),
        GREEN_DHIDE_CHAPS(new EquipmentBuilder(ItemID.GREEN_DHIDE_CHAPS).setRanged().addRanged(40).setScore(40)),
        BLUE_DHIDE_CHAPS(new EquipmentBuilder(ItemID.BLUE_DHIDE_CHAPS).setRanged().addRanged(50).setScore(50)),
        RED_DHIDE_CHAPS(new EquipmentBuilder(ItemID.RED_DHIDE_CHAPS).setRanged().addRanged(60).setScore(60)),
        BLACK_DHIDE_CHAPS(new EquipmentBuilder(ItemID.BLACK_DHIDE_CHAPS).setRanged().addRanged(70).setScore(70)),

        GUTHIX_CHAPS(new EquipmentBuilder(ItemID.GUTHIX_CHAPS).setRanged().addRanged(70).setScore(75)),
        ANCIENT_CHAPS(new EquipmentBuilder(ItemID.ANCIENT_CHAPS).setRanged().addRanged(70).setScore(75)),
        ARMADYL_CHAPS(new EquipmentBuilder(ItemID.ARMADYL_CHAPS).setRanged().addRanged(70).setScore(75)),
        BANDOS_CHAPS(new EquipmentBuilder(ItemID.BANDOS_CHAPS).setRanged().addRanged(70).setScore(75)),
        SARADOMIN_CHAPS(new EquipmentBuilder(ItemID.SARADOMIN_CHAPS).setRanged().addRanged(70).setScore(75)),
        ZAMORAK_CHAPS(new EquipmentBuilder(ItemID.ZAMORAK_CHAPS).setRanged().addRanged(70).setScore(75)),

        KARILS_LEATHERSKIRT(new EquipmentBuilder(ItemID.KARILS_LEATHERSKIRT).setRanged().addDefence(70).addRanged(70)
                .addAlts(ItemID.KARILS_LEATHERSKIRT_100, ItemID.KARILS_LEATHERSKIRT_75, ItemID.KARILS_LEATHERSKIRT_50, ItemID.KARILS_LEATHERSKIRT_25)),

        // Mage
        ZAMORAK_MONK_BOTTOM(new EquipmentBuilder(ItemID.ZAMORAK_MONK_BOTTOM).setMage().addDefence(1)),
        BLUE_SKIRT(new EquipmentBuilder(ItemID.BLUE_SKIRT).setMage().addDefence(1)),
        BLACK_SKIRT(new EquipmentBuilder(ItemID.BLACK_SKIRT).setMage().addDefence(1)),
        ELDER_CHAOS_ROBE(new EquipmentBuilder(ItemID.ELDER_CHAOS_ROBE).setMage().addDefence(1).addMagic(40)),
        XERICIAN_ROBE(new EquipmentBuilder(ItemID.XERICIAN_ROBE).setMage().addDefence(10).addMagic(20)),

        MYSTIC_ROBE_BOTTOM(new EquipmentBuilder(ItemID.MYSTIC_ROBE_BOTTOM).setMage().addDefence(20).addMagic(40)),
        MYSTIC_ROBE_BOTTOM_DARK(new EquipmentBuilder(ItemID.MYSTIC_ROBE_BOTTOM_DARK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_ROBE_BOTTOM_DUSK(new EquipmentBuilder(ItemID.MYSTIC_ROBE_BOTTOM_DUSK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_ROBE_BOTTOM_LIGHT(new EquipmentBuilder(ItemID.MYSTIC_ROBE_BOTTOM_LIGHT).setMage().addDefence(20).addMagic(40)),

        ENCHANTED_ROBE(new EquipmentBuilder(ItemID.ENCHANTED_ROBE).setMage().addDefence(20).addMagic(40)),
        ROBE_BOTTOM_OF_DARKNESS(new EquipmentBuilder(ItemID.ROBE_BOTTOM_OF_DARKNESS).setMage().addDefence(20).addMagic(40)),

        SKELETAL_BOTTOMS(new EquipmentBuilder(ItemID.SKELETAL_BOTTOMS).setMage().addDefence(40).addMagic(40).addQuest(Quest.THE_FREMENNIK_TRIALS)),

        LUNAR_LEGS(new EquipmentBuilder(ItemID.LUNAR_LEGS).setMage().addDefence(40).addMagic(65).addQuest(Quest.LUNAR_DIPLOMACY)),

        AHRIMS_ROBESKIRT(new EquipmentBuilder(ItemID.AHRIMS_ROBESKIRT).setMage().addDefence(70).addMagic(70)
                .addAlts(ItemID.AHRIMS_ROBESKIRT_100, ItemID.AHRIMS_ROBESKIRT_75, ItemID.AHRIMS_ROBESKIRT_50, ItemID.AHRIMS_ROBESKIRT_25)),

        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        GearLeg(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setLegs()
                    .build()
            );
        }

    }

    public enum GearFeet {

        // General
        LEATHER_BOOTS(new EquipmentBuilder(ItemID.LEATHER_BOOTS).addDefence(1)),

        // Melee
        BRONZE_BOOTS(new EquipmentBuilder(ItemID.BRONZE_BOOTS).setMelee().addDefence(1)),
        IRON_BOOTS(new EquipmentBuilder(ItemID.IRON_BOOTS).setMelee().addDefence(1)),
        STEEL_BOOTS(new EquipmentBuilder(ItemID.STEEL_BOOTS).setMelee().addDefence(5)),
        MITHRIL_BOOTS(new EquipmentBuilder(ItemID.MITHRIL_BOOTS).setMelee().addDefence(20)),
        ADAMANT_BOOTS(new EquipmentBuilder(ItemID.ADAMANT_BOOTS).setMelee().addDefence(30)),
        RUNE_BOOTS(new EquipmentBuilder(ItemID.RUNE_BOOTS).setMelee().addDefence(40)),
        GRANITE_BOOTS(new EquipmentBuilder(ItemID.GRANITE_BOOTS).setMelee().addDefence(50).addStrength(50)),
        DRAGON_BOOTS(new EquipmentBuilder(ItemID.DRAGON_BOOTS).setMelee().addDefence(60)),

        // Ranged
        SNAKESKIN_BOOTS(new EquipmentBuilder(ItemID.SNAKESKIN_BOOTS).setRanged().addDefence(30).addRanged(30)),

        GUTHIX_DHIDE_BOOTS(new EquipmentBuilder(ItemID.GUTHIX_DHIDE_BOOTS).setRanged().addDefence(40).addRanged(70).setScore(75)),
        ANCIENT_DHIDE_BOOTS(new EquipmentBuilder(ItemID.ANCIENT_DHIDE_BOOTS).setRanged().addDefence(40).addRanged(70).setScore(75)),
        ARMADYL_DHIDE_BOOTS(new EquipmentBuilder(ItemID.ARMADYL_DHIDE_BOOTS).setRanged().addDefence(40).addRanged(70).setScore(75)),
        BANDOS_DHIDE_BOOTS(new EquipmentBuilder(ItemID.BANDOS_DHIDE_BOOTS).setRanged().addDefence(40).addRanged(70).setScore(75)),
        SARADOMIN_DHIDE_BOOTS(new EquipmentBuilder(ItemID.SARADOMIN_DHIDE_BOOTS).setRanged().addDefence(40).addRanged(70).setScore(75)),
        ZAMORAK_DHIDE_BOOTS(new EquipmentBuilder(ItemID.ZAMORAK_DHIDE_BOOTS).setRanged().addDefence(40).addRanged(70).setScore(75)),

        MYSTIC_BOOTS(new EquipmentBuilder(ItemID.MYSTIC_BOOTS).setMage().addDefence(20).addMagic(40)),
        MYSTIC_BOOTS_DARK(new EquipmentBuilder(ItemID.MYSTIC_BOOTS_DARK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_BOOTS_DUSK(new EquipmentBuilder(ItemID.MYSTIC_BOOTS_DUSK).setMage().addDefence(20).addMagic(40)),
        MYSTIC_BOOTS_LIGHT(new EquipmentBuilder(ItemID.MYSTIC_BOOTS_LIGHT).setMage().addDefence(20).addMagic(40)),

        BOOTS_OF_DARKNESS(new EquipmentBuilder(ItemID.BOOTS_OF_DARKNESS).setMage().addDefence(20).addMagic(40)),

        SKELETAL_BOOTS(new EquipmentBuilder(ItemID.SKELETAL_BOOTS).setMage().addDefence(40).addMagic(40).addQuest(Quest.THE_FREMENNIK_TRIALS)),

        LUNAR_BOOTS(new EquipmentBuilder(ItemID.LUNAR_BOOTS).setMage().addDefence(40).addMagic(65).addQuest(Quest.LUNAR_DIPLOMACY)),

        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        GearFeet(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setFeet()
                    .build()
            );
        }

    }

    public enum GearShield {

        BRONZE_SHIELD(new EquipmentBuilder(ItemID.BRONZE_KITESHIELD).setMelee().addDefence(1)),
        BRONZE_SQ_SHIELD(new EquipmentBuilder(ItemID.BRONZE_SQ_SHIELD).setMelee().addDefence(1)),

        IRON_SHIELD(new EquipmentBuilder(ItemID.IRON_KITESHIELD).setMelee().addDefence(1)),
        IRON_SQ_SHIELD(new EquipmentBuilder(ItemID.IRON_SQ_SHIELD).setMelee().addDefence(1)),

        STEEL_SHIELD(new EquipmentBuilder(ItemID.STEEL_KITESHIELD).setMelee().addDefence(5)),
        STEEL_SQ_SHIELD(new EquipmentBuilder(ItemID.STEEL_SQ_SHIELD).setMelee().addDefence(5)),

        MITHRIL_SHIELD(new EquipmentBuilder(ItemID.MITHRIL_KITESHIELD).setMelee().addDefence(20)),
        MITHRIL_SQ_SHIELD(new EquipmentBuilder(ItemID.MITHRIL_SQ_SHIELD).setMelee().addDefence(20)),

        ADAMANT_SHIELD(new EquipmentBuilder(ItemID.ADAMANT_KITESHIELD).setMelee().addDefence(30)),
        ADAMANT_SQ_SHIELD(new EquipmentBuilder(ItemID.ADAMANT_SQ_SHIELD).setMelee().addDefence(30)),

        RUNE_SHIELD(new EquipmentBuilder(ItemID.RUNE_KITESHIELD).setMelee().addDefence(40)),
        RUNE_SQ_SHIELD(new EquipmentBuilder(ItemID.RUNE_SQ_SHIELD).setMelee().addDefence(40)),

        GRANITE_SHIELD(new EquipmentBuilder(ItemID.GRANITE_SHIELD).setMelee().addDefence(50).addStrength(50)),

        DRAGON_SHIELD(new EquipmentBuilder(ItemID.DRAGON_KITESHIELD).setMelee().addDefence(60)),
        DRAGON_SQ_SHIELD(new EquipmentBuilder(ItemID.DRAGON_SQ_SHIELD).setMelee().addDefence(60).addQuest(Quest.LEGENDS_QUEST)),

        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        GearShield(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setShield()
                    .build()
            );
        }

    }

    public enum GearCape {

        LUNAR_CAPE(new EquipmentBuilder(ItemID.LUNAR_CAPE).setMage().addDefence(40).addMagic(65).addQuest(Quest.LUNAR_DIPLOMACY)),

        ;

        @Setter
        @Getter
        EquipmentItem equipmentItem;

        GearCape(EquipmentBuilder equipmentBuilder) {
            this.setEquipmentItem(equipmentBuilder
                    .setCape()
                    .build()
            );
        }

    }


    public static ArrayList<EquipmentItem> getEquipmentItems() {
        ArrayList<EquipmentItem> list = new ArrayList<>();

        list.addAll(Arrays.stream(GearRing.values()).map(GearRing::getEquipmentItem).collect(Collectors.toList()));
        list.addAll(Arrays.stream(GearHand.values()).map(GearHand::getEquipmentItem).collect(Collectors.toList()));
        list.addAll(Arrays.stream(GearNeck.values()).map(GearNeck::getEquipmentItem).collect(Collectors.toList()));

        list.addAll(Arrays.stream(GearHelmet.values()).map(GearHelmet::getEquipmentItem).collect(Collectors.toList()));
        list.addAll(Arrays.stream(GearBody.values()).map(GearBody::getEquipmentItem).collect(Collectors.toList()));
        list.addAll(Arrays.stream(GearLeg.values()).map(GearLeg::getEquipmentItem).collect(Collectors.toList()));
        list.addAll(Arrays.stream(GearFeet.values()).map(GearFeet::getEquipmentItem).collect(Collectors.toList()));

        list.addAll(Arrays.stream(GearShield.values()).map(GearShield::getEquipmentItem).collect(Collectors.toList()));
        list.addAll(Arrays.stream(GearCape.values()).map(GearCape::getEquipmentItem).collect(Collectors.toList()));

        return list;
    }


}


