package scripts.main_package.a_skill_data.bank_skills.herblore;

import lombok.Setter;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import scripts.main_package.a_skill_data.bank_skills.CombineTask;
import scripts.main_package.a_skill_data.ItemCreationMainTask;
import scripts.main_package.a_skill_data.bank_skills.MakescreenTask;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.api.task.Task;
import scripts.main_package.item.DetailedItem;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

//TODO add quest condition
public class HerbloreData {

    private final static Skill HERBLORE = Skill.HERBLORE;
    private final static int WATER = ItemID.VIAL_OF_WATER;

    public enum Herbs {

        TORSTOL("Torstol", 75, ItemID.GRIMY_TORSTOL, ItemID.TORSTOL, 15),
        DWARF_WEED("Dwarf weed", 70, ItemID.GRIMY_DWARF_WEED, ItemID.DWARF_WEED, 13.8),
        LANTADYME("Lantadyme", 67, ItemID.GRIMY_LANTADYME, ItemID.LANTADYME, 13.1),
        CADANTINE("Cadantine", 65, ItemID.GRIMY_CADANTINE, ItemID.CADANTINE, 12.5),
        SNAPDRAGON("Snapdragon", 59, ItemID.GRIMY_SNAPDRAGON, ItemID.SNAPDRAGON, 11.8),
        KWUARM("Kwuarm", 54, ItemID.GRIMY_KWUARM, ItemID.KWUARM, 11.3),
        AVANTOE("Avantoe", 48, ItemID.GRIMY_AVANTOE, ItemID.AVANTOE, 10),
        IRIT_LEAF("Irit leaf", 40, ItemID.GRIMY_IRIT_LEAF, ItemID.IRIT_LEAF, 8.8),
        TOADFLAX("Toadflax", 30, ItemID.GRIMY_TOADFLAX, ItemID.TOADFLAX, 8),
        RANARR_WEED("Ranarr weed", 25, ItemID.GRIMY_RANARR_WEED, ItemID.RANARR_WEED, 7.5),
        HARRALANDER("Harralander", 20, ItemID.GRIMY_HARRALANDER, ItemID.HARRALANDER, 6.3),
        TARROMIN("Tarromin", 11, ItemID.GRIMY_TARROMIN, ItemID.TARROMIN, 5),
        MARRENTILL("Marrentill", 5, ItemID.GRIMY_MARRENTILL, ItemID.MARRENTILL, 3.8),
        GUAM_LEAF("Guam leaf", 3, ItemID.GRIMY_GUAM_LEAF, ItemID.GUAM_LEAF, 2.5);

        @Setter
        String name;
        @Setter
        int levelReq;
        @Setter
        int grimyId;
        @Setter
        int cleanId;
        @Setter
        double exp;

        Herbs(String name, int levelReq, int grimyId, int cleanId, double exp) {
            this.setName(name);
            this.setLevelReq(levelReq);
            this.setGrimyId(grimyId);
            this.setCleanId(cleanId);
            this.setExp(exp);
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Herblore} Cleaning: " + name);
            itemCreationMainTask.ticksPerActivity = 1;
            itemCreationMainTask.maxItemSets = 13000; // 13000 buy limit
            itemCreationMainTask.addQuestRequirement(Quest.DRUIDIC_RITUAL);
            itemCreationMainTask.addSkillRequirement(HERBLORE, levelReq);

            itemCreationMainTask.addExperienceReward(HERBLORE, exp);
            itemCreationMainTask.addProduct(cleanId);

            itemCreationMainTask.addMaterial(grimyId);

            //TODO steps to create

            return itemCreationMainTask;
        }


    }

    public enum UnfPots {

        TORSTOL("Torstol", 78, ItemID.TORSTOL, ItemID.TORSTOL_POTION_UNF),
        DWARF_WEED("Dwarf weed", 72, ItemID.DWARF_WEED, ItemID.DWARF_WEED_POTION_UNF),
        LANTADYME("Lantadyme", 69, ItemID.LANTADYME, ItemID.LANTADYME_POTION_UNF),
        CADANTINE("Cadantine", 66, ItemID.CADANTINE, ItemID.CADANTINE_POTION_UNF),
        SNAPDRAGON("Snapdragon", 63, ItemID.SNAPDRAGON, ItemID.SNAPDRAGON_POTION_UNF),
        KWUARM("Kwuarm", 55, ItemID.KWUARM, ItemID.KWUARM_POTION_UNF),
        AVANTOE("Avantoe", 50, ItemID.AVANTOE, ItemID.AVANTOE_POTION_UNF),
        IRIT_LEAF("Irit leaf", 45, ItemID.IRIT_LEAF, ItemID.IRIT_POTION_UNF),
        TOADFLAX("Toadflax", 34, ItemID.TOADFLAX, ItemID.TOADFLAX_POTION_UNF),
        RANARR_WEED("Ranarr weed", 30, ItemID.RANARR_WEED, ItemID.RANARR_POTION_UNF),
        HARRALANDER("Harralander", 22, ItemID.HARRALANDER, ItemID.HARRALANDER_POTION_UNF),
        TARROMIN("Tarromin", 12, ItemID.TARROMIN, ItemID.TARROMIN_POTION_UNF),
        MARRENTILL("Marrentill", 5, ItemID.MARRENTILL, ItemID.MARRENTILL_POTION_UNF),
        GUAM_LEAF("Guam leaf", 3, ItemID.GUAM_LEAF, ItemID.GUAM_POTION_UNF);

        @Setter
        String name;
        @Setter
        int levelReq;
        @Setter
        int cleanId;
        @Setter
        int unfId;

        UnfPots(String name, int levelReq, int cleanId, int unfId) {
            this.setName(name);
            this.setLevelReq(levelReq);
            this.setCleanId(cleanId);
            this.setUnfId(unfId);
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Herblore} Unf Potion: " + name);
            itemCreationMainTask.ticksPerActivity = 1;
            itemCreationMainTask.maxItemSets = 13000; // 13000 buy limit
            itemCreationMainTask.addQuestRequirement(Quest.DRUIDIC_RITUAL);
            itemCreationMainTask.addSkillRequirement(HERBLORE, levelReq);

            // No exp
            itemCreationMainTask.addExperienceReward(HERBLORE, 0);
            itemCreationMainTask.addProduct(unfId);

            itemCreationMainTask.addMaterial(cleanId);
            itemCreationMainTask.addMaterial(WATER);

            Task makeScreenTask = new MakescreenTask(unfId, ItemID.VIAL_OF_WATER, cleanId).getTask();
            Task combineTask = new CombineTask(WATER, cleanId).getTask();
            itemCreationMainTask.addTasks(makeScreenTask, combineTask);

            return itemCreationMainTask;
        }


    }


    public enum Potions {

        EXTENDED_SUPER_ANTIFIRE4(98, ItemID.EXTENDED_SUPER_ANTIFIRE4, 160, ItemID.SUPER_ANTIFIRE_POTION4, ItemID.LAVA_SCALE_SHARD, 4),
        ANTIVENOM4_12913(94, ItemID.ANTIVENOM4_12913, 125, ItemID.ANTIVENOM4, ItemID.TORSTOL),
        SUPER_ANTIFIRE_POTION4(92, ItemID.SUPER_ANTIFIRE_POTION4, 130, ItemID.ANTIFIRE_POTION4, ItemID.CRUSHED_SUPERIOR_DRAGON_BONES),
        SUPER_COMBAT_POTION4(90, ItemID.SUPER_COMBAT_POTION4, 90, new DetailedItem(ItemID.TORSTOL),
                new DetailedItem(ItemID.SUPER_ATTACK4), new DetailedItem(ItemID.SUPER_STRENGTH4), new DetailedItem(ItemID.SUPER_DEFENCE4)),
        ANTIVENOM4(87, ItemID.ANTIVENOM4, 120, ItemID.ANTIDOTE4_5952, ItemID.ZULRAHS_SCALES, 20),
        EXTENDED_ANTIFIRE4(84, ItemID.EXTENDED_ANTIFIRE4, 110, ItemID.ANTIFIRE_POTION4, ItemID.LAVA_SCALE_SHARD, 4),
        SARADOMIN_BREW3(81, ItemID.SARADOMIN_BREW3, 180, ItemID.TOADFLAX_POTION_UNF, ItemID.CRUSHED_NEST),
        BATTLEMAGE_POTION3(80, ItemID.BATTLEMAGE_POTION3, 155, ItemID.CADANTINE_BLOOD_POTION_UNF, ItemID.POTATO_CACTUS),
        BASTION_POTION3(80, ItemID.BASTION_POTION3, 155, ItemID.CADANTINE_BLOOD_POTION_UNF, ItemID.WINE_OF_ZAMORAK),
        ZAMORAK_BREW3(78, ItemID.ZAMORAK_BREW3, 175, ItemID.TORSTOL_POTION_UNF, ItemID.JANGERBERRIES),
        STAMINA_POTION4(77, ItemID.STAMINA_POTION4, 102, ItemID.SUPER_ENERGY4, ItemID.AMYLASE_CRYSTAL, 4),
        MAGIC_POTION3(76, ItemID.MAGIC_POTION3, 172.5, ItemID.LANTADYME_POTION_UNF, ItemID.POTATO_CACTUS),
        RANGING_POTION3(72, ItemID.RANGING_POTION3, 162.5, ItemID.DWARF_WEED_POTION_UNF, ItemID.WINE_OF_ZAMORAK),
        ANTIFIRE_POTION3(69, ItemID.ANTIFIRE_POTION3, 157.5, ItemID.LANTADYME_POTION_UNF, ItemID.DRAGON_SCALE_DUST),
        SUPER_DEFENCE3(66, ItemID.SUPER_DEFENCE3, 150, ItemID.CADANTINE_POTION_UNF, ItemID.WHITE_BERRIES),
        SUPER_RESTORE3(63, ItemID.SUPER_RESTORE3, 142.5, ItemID.SNAPDRAGON_POTION_UNF, ItemID.RED_SPIDERS_EGGS),
        SUPER_STRENGTH3(55, ItemID.SUPER_STRENGTH3, 125, ItemID.KWUARM_POTION_UNF, ItemID.LIMPWURT_ROOT),
        HUNTER_POTION3(53, ItemID.HUNTER_POTION3, 120, ItemID.AVANTOE_POTION_UNF, ItemID.KEBBIT_TEETH_DUST),
        SUPER_ENERGY3(52, ItemID.SUPER_ENERGY3, 117.5, ItemID.AVANTOE_POTION_UNF, ItemID.MORT_MYRE_FUNGUS),
        FISHING_POTION3(50, ItemID.FISHING_POTION3, 112.5, ItemID.AVANTOE_POTION_UNF, ItemID.SNAPE_GRASS),
        SUPERANTIPOISON3(48, ItemID.SUPERANTIPOISON3, 106.3, ItemID.IRIT_POTION_UNF, ItemID.UNICORN_HORN_DUST),
        SUPER_ATTACK3(45, ItemID.SUPER_ATTACK3, 100, ItemID.IRIT_POTION_UNF, ItemID.EYE_OF_NEWT),
        PRAYER_POTION3(38, ItemID.PRAYER_POTION3, 87.5, ItemID.RANARR_POTION_UNF, ItemID.SNAPE_GRASS),
        COMBAT_POTION3(36, ItemID.COMBAT_POTION3, 84, ItemID.HARRALANDER_POTION_UNF, ItemID.GOAT_HORN_DUST),
        AGILITY_POTION3(34, ItemID.AGILITY_POTION3, 80, ItemID.TOADFLAX_POTION_UNF, ItemID.TOADS_LEGS),
        DEFENCE_POTION3(30, ItemID.DEFENCE_POTION3, 75, ItemID.RANARR_POTION_UNF, ItemID.WHITE_BERRIES),
        ENERGY_POTION3(26, ItemID.ENERGY_POTION3, 67.5, ItemID.HARRALANDER_POTION_UNF, ItemID.CHOCOLATE_DUST),
        RESTORE_POTION3(22, ItemID.RESTORE_POTION3, 62.5, ItemID.HARRALANDER_POTION_UNF, ItemID.RED_SPIDERS_EGGS),
        STRENGTH_POTION3(12, ItemID.STRENGTH_POTION3, 50, ItemID.TARROMIN_POTION_UNF, ItemID.LIMPWURT_ROOT),
        ANTIPOISON3(5, ItemID.ANTIPOISON3, 37.5, ItemID.MARRENTILL_POTION_UNF, ItemID.UNICORN_HORN_DUST),
        ATTACK_POTION3(3, ItemID.ATTACK_POTION3, 25, ItemID.GUAM_POTION_UNF, ItemID.EYE_OF_NEWT),

        ;

        int level, productId;
        double exp;

        ArrayList<DetailedItem> items = new ArrayList<>();

        Potions(int level, int productId, double exp, int potId, int secondaryId) {
            this(level, productId, exp, potId, secondaryId, 1);
        }

        Potions(int level, int productId, double exp, int potId, int secondaryId, int secondaryAmount) {
            this(level, productId, exp, new DetailedItem(secondaryId, secondaryAmount), new DetailedItem(potId));
        }

        Potions(int level, int productId, double exp, DetailedItem... detailedItems) {
            this.level = level;
            this.productId = productId;
            this.exp = exp;

            this.items.addAll(Arrays.asList(detailedItems));
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Herblore} Potion: " + Utils.getItemName(productId));
            itemCreationMainTask.ticksPerActivity = 2;
            itemCreationMainTask.maxItemSets = 10000; // TODO buy limit
            itemCreationMainTask.addQuestRequirement(Quest.DRUIDIC_RITUAL);
            itemCreationMainTask.addSkillRequirement(HERBLORE, level);

            // No exp
            itemCreationMainTask.addExperienceReward(HERBLORE, exp);
            itemCreationMainTask.addProduct(productId);

            for (DetailedItem material : items)
                itemCreationMainTask.addMaterial(material);

            int firstId = items.get(0).getItemId(), secondId = items.get(1).getItemId();
            Task makeScreenTask = new MakescreenTask(productId, firstId, secondId).getTask();
            Task combineTask = new CombineTask(firstId, secondId).getTask();
            itemCreationMainTask.addTasks(makeScreenTask, combineTask);

            return itemCreationMainTask;
        }
    }

    public static ArrayList<MainTask> getAllHerbloreMainTasks() {
        ArrayList<MainTask> list = new ArrayList<>();

        // Herbs
        list.addAll(Arrays.stream(HerbloreData.Herbs.values()).map(HerbloreData.Herbs::getMainTask).collect(Collectors.toList()));

        // Unf pots
        list.addAll(Arrays.stream(HerbloreData.UnfPots.values()).map(HerbloreData.UnfPots::getMainTask).collect(Collectors.toList()));

        // Pots
        list.addAll(Arrays.stream(HerbloreData.Potions.values()).map(HerbloreData.Potions::getMainTask).collect(Collectors.toList()));

        return list;
    }


}
