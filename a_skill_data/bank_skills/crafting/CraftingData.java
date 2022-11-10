package scripts.main_package.a_skill_data.bank_skills.crafting;

import lombok.Setter;
import org.tribot.script.sdk.Skill;
import scripts.main_package.a_skill_data.bank_skills.CombineTask;
import scripts.main_package.a_skill_data.ItemCreationMainTask;
import scripts.main_package.a_skill_data.bank_skills.MakescreenTask;
import scripts.main_package.a_skill_data.util.SkillingItemSelection;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.*;
import scripts.main_package.api.task.general_action.ActionGameObject;
import scripts.main_package.api.task.general_action.ActionWidget;
import scripts.main_package.api.task.general_condition.ConditionGameObject;
import scripts.main_package.api.task.general_condition.ConditionInventoryItem;
import scripts.main_package.api.task.general_condition.ConditionWidget;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CraftingData {

    private final static Skill CRAFTING = Skill.CRAFTING;
    private final static int CHISEL = ItemID.CHISEL;
    private final static int BATTLESTAFF = ItemID.BATTLESTAFF;
    private final static int THREAD = ItemID.THREAD, NEEDLE = ItemID.NEEDLE;
    private final static int LEATHER = ItemID.LEATHER, HARD_LEATHER = ItemID.HARD_LEATHER;
    private final static int BLACK_LEATHER = ItemID.BLACK_DRAGON_LEATHER, RED_LEATHER = ItemID.RED_DRAGON_LEATHER,
            BLUE_LEATHER = ItemID.BLUE_DRAGON_LEATHER, GREEN_LEATHER = ItemID.GREEN_DRAGON_LEATHER;

    public enum Gems {

        DRAGONSTONE("Dragonstone", 55, ItemID.UNCUT_DRAGONSTONE, ItemID.DRAGONSTONE, 137.5),
        DIAMOND("Diamond", 43, ItemID.UNCUT_DIAMOND, ItemID.DIAMOND, 107.5),
        RUBY("Ruby", 34, ItemID.UNCUT_RUBY, ItemID.RUBY, 85),
        EMERALD("Emerald", 27, ItemID.UNCUT_EMERALD, ItemID.EMERALD, 67.5),
        SAPPHIRE("Sapphire", 20, ItemID.UNCUT_SAPPHIRE, ItemID.SAPPHIRE, 50),
        RED_TOPAZ("Red Topaz", 16, ItemID.UNCUT_RED_TOPAZ, ItemID.RED_TOPAZ, 25),
        JADE("Jade", 13, ItemID.UNCUT_JADE, ItemID.JADE, 20),
        OPAL("Opal", 1, ItemID.UNCUT_OPAL, ItemID.OPAL, 15);

        @Setter
        String name;
        @Setter
        int levelReq;
        @Setter
        int materialId;
        @Setter
        int productId;
        @Setter
        double exp;

        Gems(String name, int levelReq, int materialId, int productId, double exp) {
            this.setName(name);
            this.setLevelReq(levelReq);
            this.setMaterialId(materialId);
            this.setProductId(productId);
            this.setExp(exp);
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(productId);
            skillingItemSelection.setSkill(CRAFTING);
            return skillingItemSelection;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Crafting} Cutting Gem: " + name);
            itemCreationMainTask.ticksPerActivity = 2;
            itemCreationMainTask.maxItemSets = 10000; // 10000 buy limit
            itemCreationMainTask.addSkillRequirement(CRAFTING, levelReq);

            itemCreationMainTask.addExperienceReward(CRAFTING, exp);
            itemCreationMainTask.addProduct(productId);

            itemCreationMainTask.addMaterial(materialId);
            itemCreationMainTask.addTool(CHISEL);

            Task makeScreenTask = new MakescreenTask(productId, materialId).getTask();
            Task combineTask = new CombineTask(CHISEL, materialId).getTask();
            itemCreationMainTask.addTasks(makeScreenTask, combineTask);

            return itemCreationMainTask;
        }

    }

    public enum Battlestaves {
        AIR_BATTLESTAFF("Air battlestaff", 66, ItemID.AIR_ORB, ItemID.AIR_BATTLESTAFF, 137.5),
        FIRE_BATTLESTAFF("Fire battlestaff", 62, ItemID.FIRE_ORB, ItemID.FIRE_BATTLESTAFF, 125),
        EARTH_BATTLESTAFF("Earth battlestaff", 58, ItemID.EARTH_ORB, ItemID.EARTH_BATTLESTAFF, 112.5),
        WATER_BATTLESTAFF("Water battlestaff", 54, ItemID.WATER_ORB, ItemID.WATER_BATTLESTAFF, 100);

        @Setter
        String name;
        @Setter
        int levelReq;
        @Setter
        int materialId;
        @Setter
        int productId;
        @Setter
        double exp;

        Battlestaves(String name, int levelReq, int materialId, int productId, double exp) {
            this.setName(name);
            this.setLevelReq(levelReq);
            this.setMaterialId(materialId);
            this.setProductId(productId);
            this.setExp(exp);
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(productId);
            skillingItemSelection.setSkill(CRAFTING);
            return skillingItemSelection;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Crafting} Creating BattleStaff: " + name);
            itemCreationMainTask.ticksPerActivity = 4;
            itemCreationMainTask.maxItemSets = 11000; // 11000 buy limit
            itemCreationMainTask.addSkillRequirement(CRAFTING, levelReq);

            itemCreationMainTask.addExperienceReward(CRAFTING, exp);
            itemCreationMainTask.addProduct(productId);

            itemCreationMainTask.addMaterial(materialId);
            itemCreationMainTask.addMaterial(BATTLESTAFF);

            Task makeScreenTask = new MakescreenTask(productId, materialId).getTask();
            Task combineTask = new CombineTask(BATTLESTAFF, materialId).getTask();
            itemCreationMainTask.addTasks(makeScreenTask, combineTask);

            return itemCreationMainTask;
        }

    }

    public enum Leathers {

        COIF("Coif", 38, LEATHER, ItemID.COIF, 37),
        HARD_BODY("Hardleather body", 28, HARD_LEATHER, ItemID.HARDLEATHER_BODY, 35),
        CHAPS("Leather chaps", 18, LEATHER, ItemID.LEATHER_CHAPS, 27),
        BODY("Leather body", 14, LEATHER, ItemID.LEATHER_BODY, 25),
        VAMBRACES("Leather vambraces", 11, LEATHER, ItemID.LEATHER_VAMBRACES, 22),
        COWL("Leather cowl", 9, LEATHER, ItemID.LEATHER_COWL, 18.5),
        BOOTS("Leather boots", 7, LEATHER, ItemID.LEATHER_BOOTS, 16.2),
        GLOVES("Leather gloves", 1, LEATHER, ItemID.LEATHER_GLOVES, 13.8);

        @Setter
        String name;
        @Setter
        int levelReq;
        @Setter
        int materialId;
        @Setter
        int productId;
        @Setter
        double exp;

        Leathers(String name, int levelReq, int materialId, int productId, double exp) {
            this.setName(name);
            this.setLevelReq(levelReq);
            this.setMaterialId(materialId);
            this.setProductId(productId);
            this.setExp(exp);
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(productId);
            skillingItemSelection.setSkill(CRAFTING);
            return skillingItemSelection;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Crafting} Creating Leather Item: " + name);
            itemCreationMainTask.ticksPerActivity = 3;
            itemCreationMainTask.maxItemSets = 13000; // 13000 buy limit
            itemCreationMainTask.addSkillRequirement(CRAFTING, levelReq);

            itemCreationMainTask.addExperienceReward(CRAFTING, exp);
            itemCreationMainTask.addProduct(productId);

            itemCreationMainTask.addMaterial(materialId);
            itemCreationMainTask.addMaterial(THREAD); //TODO figure out how to make this 20% of leather quantity
            itemCreationMainTask.addTool(NEEDLE);

            Task makeScreenTask = new MakescreenTask(productId, materialId).getTask();
            Task combineTask = new CombineTask(NEEDLE, materialId).getTask();
            itemCreationMainTask.addTasks(makeScreenTask, combineTask);

            return itemCreationMainTask;
        }

    }

    public enum DragonLeathers {

        BLACK_BODY("Black d'hide body", 84, BLACK_LEATHER, 3, ItemID.BLACK_DHIDE_BODY, 258),
        BLACK_CHAPS("Black d'hide chaps", 82, BLACK_LEATHER, 2, ItemID.BLACK_DHIDE_CHAPS, 172),
        BLACK_VAMBRACES("Black d'hide vambraces", 79, BLACK_LEATHER, 1, ItemID.BLACK_DHIDE_VAMBRACES, 86),
        RED_BODY("Red d'hide body", 77, RED_LEATHER, 3, ItemID.RED_DHIDE_BODY, 234),
        RED_CHAPS("Red d'hide chaps", 75, RED_LEATHER, 2, ItemID.RED_DHIDE_CHAPS, 156),
        RED_VAMBRACES("Red d'hide vambraces", 73, RED_LEATHER, 1, ItemID.RED_DHIDE_VAMBRACES, 78),
        BLUE_BODY("Blue d'hide body", 71, BLUE_LEATHER, 3, ItemID.BLUE_DHIDE_BODY, 210),
        BLUE_CHAPS("Blue d'hide chaps", 68, BLUE_LEATHER, 2, ItemID.BLUE_DHIDE_CHAPS, 140),
        BLUE_VAMBRACES("Blue d'hide vambraces", 66, BLUE_LEATHER, 1, ItemID.BLUE_DHIDE_VAMBRACES, 70),
        GREEN_BODY("Green d'hide body", 63, GREEN_LEATHER, 3, ItemID.GREEN_DHIDE_BODY, 186),
        GREEN_CHAPS("Green d'hide chaps", 60, GREEN_LEATHER, 2, ItemID.GREEN_DHIDE_CHAPS, 124),
        GREEN_VAMBRACES("Green d'hide vambraces", 57, GREEN_LEATHER, 1, ItemID.GREEN_DHIDE_VAMBRACES, 62);

        @Setter
        String name;
        @Setter
        int levelReq;
        @Setter
        int materialId;
        @Setter
        int materialQuantity;
        @Setter
        int productId;
        @Setter
        double exp;

        DragonLeathers(String name, int levelReq, int materialId, int materialQuantity, int productId, double exp) {
            this.setName(name);
            this.setLevelReq(levelReq);
            this.setMaterialId(materialId);
            this.setMaterialQuantity(materialQuantity);
            this.setProductId(productId);
            this.setExp(exp);
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Crafting} Creating Dragon Leather Item: " + name);
            itemCreationMainTask.ticksPerActivity = 3;
            itemCreationMainTask.maxItemSets = 13000 / materialQuantity; // 13000 buy limit for dragon leather
            itemCreationMainTask.addSkillRequirement(CRAFTING, levelReq);

            itemCreationMainTask.addExperienceReward(CRAFTING, exp);
            itemCreationMainTask.addProduct(productId);

            itemCreationMainTask.addMaterial(materialId, materialQuantity);
            itemCreationMainTask.addMaterial(THREAD); //TODO figure out how to make this 20% of leather quantity
            itemCreationMainTask.addTool(NEEDLE);

            Task makeScreenTask = new MakescreenTask(productId, materialId).getTask();
            CombineTask combineTask = new CombineTask(NEEDLE);
            combineTask.addItem(materialId, materialQuantity);
            itemCreationMainTask.addTasks(makeScreenTask, combineTask.getTask());

            return itemCreationMainTask;
        }

    }

    //TODO
    public enum Jewelry {

        DRAGONSTONE_AMULET_U(80, ItemID.AMULET_MOULD, ItemID.DRAGONSTONE, ItemID.DRAGONSTONE_AMULET_U, 150),
        DRAGONSTONE_BRACELET(74, ItemID.BRACELET_MOULD, ItemID.DRAGONSTONE, ItemID.DRAGONSTONE_BRACELET, 110),
        DRAGON_NECKLACE(72, ItemID.NECKLACE_MOULD, ItemID.DRAGONSTONE, ItemID.DRAGON_NECKLACE, 105),
        DIAMOND_AMULET_U(70, ItemID.AMULET_MOULD, ItemID.DIAMOND, ItemID.DIAMOND_AMULET_U, 100),
        DIAMOND_BRACELET(58, ItemID.BRACELET_MOULD, ItemID.DIAMOND, ItemID.DIAMOND_BRACELET, 95),
        DIAMOND_NECKLACE(56, ItemID.NECKLACE_MOULD, ItemID.DIAMOND, ItemID.DIAMOND_NECKLACE, 90),
        DRAGONSTONE_RING(55, ItemID.RING_MOULD, ItemID.DRAGONSTONE, ItemID.DRAGONSTONE_RING, 100),
        RUBY_AMULET_U(50, ItemID.AMULET_MOULD, ItemID.RUBY, ItemID.RUBY_AMULET_U, 85),
        DIAMOND_RING(43, ItemID.RING_MOULD, ItemID.DIAMOND, ItemID.DIAMOND_RING, 85),
        RUBY_BRACELET(42, ItemID.BRACELET_MOULD, ItemID.RUBY, ItemID.RUBY_BRACELET, 80),
        RUBY_NECKLACE(40, ItemID.NECKLACE_MOULD, ItemID.RUBY, ItemID.RUBY_NECKLACE, 75),
        RUBY_RING(34, ItemID.RING_MOULD, ItemID.RUBY, ItemID.RUBY_RING, 70),
        EMERALD_AMULET_U(31, ItemID.AMULET_MOULD, ItemID.EMERALD, ItemID.EMERALD_AMULET_U, 70),
        EMERALD_BRACELET(30, ItemID.BRACELET_MOULD, ItemID.EMERALD, ItemID.EMERALD_BRACELET, 65),
        EMERALD_NECKLACE(29, ItemID.NECKLACE_MOULD, ItemID.EMERALD, ItemID.EMERALD_NECKLACE, 60),
        EMERALD_RING(27, ItemID.RING_MOULD, ItemID.EMERALD, ItemID.EMERALD_RING, 55),
        SAPPHIRE_AMULET_U(24, ItemID.AMULET_MOULD, ItemID.SAPPHIRE, ItemID.SAPPHIRE_AMULET_U, 65),
        SAPPHIRE_BRACELET(23, ItemID.BRACELET_MOULD, ItemID.SAPPHIRE, ItemID.SAPPHIRE_BRACELET_11072, 60),
        SAPPHIRE_NECKLACE(22, ItemID.NECKLACE_MOULD, ItemID.SAPPHIRE, ItemID.SAPPHIRE_NECKLACE, 55),
        SAPPHIRE_RING(20, ItemID.RING_MOULD, ItemID.SAPPHIRE, ItemID.SAPPHIRE_RING, 40),
        GOLD_AMULET_U(8, ItemID.AMULET_MOULD, ItemID.GOLD_AMULET_U, 30),
        GOLD_BRACELET(7, ItemID.BRACELET_MOULD, ItemID.GOLD_BRACELET, 25),
        GOLD_NECKLACE(6, ItemID.NECKLACE_MOULD, ItemID.GOLD_NECKLACE, 20),
        GOLD_RING(5, ItemID.RING_MOULD, ItemID.GOLD_RING, 15);

        private final int level;
        private final int toolId;
        private int gemId = -1;
        private final int productId;
        private final double exp;

        Jewelry(int level, int toolId, int gemId, int productId, double exp) {
            this(level, toolId, productId, exp);
            this.gemId = gemId;
        }

        Jewelry(int level, int toolId, int productId, double exp) {
            this.level = level;
            this.toolId = toolId;
            this.productId = productId;
            this.exp = exp;
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(productId);
            skillingItemSelection.setSkill(CRAFTING);
            return skillingItemSelection;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Crafting} Creating Jewelry: " + Utils.getItemName(productId));
            itemCreationMainTask.ticksPerActivity = 3; // Need to account for the walking somehow
            itemCreationMainTask.maxItemSets = 10000; // 10000 buy limit on gold bars
            itemCreationMainTask.addSkillRequirement(CRAFTING, level);

            itemCreationMainTask.addExperienceReward(CRAFTING, exp);
            itemCreationMainTask.addProduct(productId);

            // Materials
            if (gemId != -1)
                itemCreationMainTask.addMaterial(gemId);
            itemCreationMainTask.addMaterial(ItemID.GOLD_BAR);
            itemCreationMainTask.addTool(toolId);

            //TODO a field to add in the teleport items, these items only valid if members world?

            // Actions
            ConditionInventoryItem haveItems = new ConditionInventoryItem(ItemID.GOLD_BAR, toolId);
            if (gemId != -1)
                haveItems.addItem(gemId);

            // Widget handling
            String itemName = Utils.getItemName(productId);
            ConditionWidget conditionWidget = new ConditionWidget().setAction(itemName);

            ActionWidget actionWidget = new ActionWidget().setAction(itemName);
            TaskWaiting taskWaiting = new TaskWaiting(180000, 240000, "Wait after selecting item");
            taskWaiting.addCondition("Out of items", () -> !haveItems.isValid());
            actionWidget.setTaskWaiting(taskWaiting);

            itemCreationMainTask.addTask(conditionWidget, actionWidget);

            // Furnace interaction
            TaskWaiting objClickWaiting = new TaskWaiting(20000, 30000, "Wait After Clicking Furnace");
            objClickWaiting.addConditions(new ConditionWidget().setAction(itemName));
            TaskCondition objCond = new ConditionGameObject("Furnace");
            TaskAction objAction = new ActionGameObject("Furnace").setTaskWaiting(objClickWaiting);
            Task objTask = new Task(objCond, objAction).addConditions(haveItems);; // .addConditions(haveItems);
            itemCreationMainTask.addTasks(objTask);

            //TODO Cond: have items, travel to valid location

            return itemCreationMainTask;
        }

    }

    public static ArrayList<MainTask> getAllCraftingMainTasks() {
        ArrayList<MainTask> list = new ArrayList<>();

        // Gems
        list.addAll(Arrays.stream(Gems.values()).map(Gems::getMainTask).collect(Collectors.toList()));

        // BattleStaff
        list.addAll(Arrays.stream(Battlestaves.values()).map(Battlestaves::getMainTask).collect(Collectors.toList()));

        // Leather
        list.addAll(Arrays.stream(Leathers.values()).map(Leathers::getMainTask).collect(Collectors.toList()));

        // DragonLeather
        list.addAll(Arrays.stream(DragonLeathers.values()).map(DragonLeathers::getMainTask).collect(Collectors.toList()));

        // Jewelry
        list.addAll(Arrays.stream(Jewelry.values()).map(Jewelry::getMainTask).collect(Collectors.toList()));

        return list;
    }

}
