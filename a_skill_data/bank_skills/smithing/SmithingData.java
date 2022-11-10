package scripts.main_package.a_skill_data.bank_skills.smithing;

import org.tribot.script.sdk.Skill;
import scripts.main_package.a_skill_data.ItemCreationMainTask;
import scripts.main_package.a_skill_data.bank_skills.MakescreenTask;
import scripts.main_package.a_skill_data.util.SkillingItemSelection;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.*;
import scripts.main_package.api.task.general_action.ActionGameObject;
import scripts.main_package.api.task.general_action.ActionWidget;
import scripts.main_package.api.task.general_condition.ConditionGameObject;
import scripts.main_package.api.task.general_condition.ConditionInventoryItem;
import scripts.main_package.api.task.general_condition.ConditionMakescreen;
import scripts.main_package.api.task.general_condition.ConditionWidget;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SmithingData {

    static Skill SMITHING = Skill.SMITHING;

    public enum Bars {

        RUNE(85, ItemID.RUNITE_ORE, ItemID.COAL, 8, ItemID.RUNITE_BAR, 50, 75),
        ADAMANT(70, ItemID.ADAMANTITE_ORE, ItemID.COAL, 6, ItemID.ADAMANTITE_BAR, 37.5, 62.5),
        MITHRIL(50, ItemID.MITHRIL_ORE, ItemID.COAL, 4, ItemID.MITHRIL_BAR, 30, 50),
        GOLD(40, ItemID.GOLD_ORE, ItemID.GOLD_BAR, 22.5, 0), // Not used on anvils?
        STEEL(30, ItemID.IRON_ORE, ItemID.COAL, 2, ItemID.STEEL_BAR, 17.5, 37.5),
        SILVER(20, ItemID.SILVER_ORE, ItemID.SILVER_BAR, 13.7, 0), // Not used on anvils?
        IRON(15, ItemID.IRON_ORE, ItemID.IRON_BAR, 12.5, 25),
        BRONZE(1, ItemID.TIN_ORE, ItemID.COPPER_ORE, 1, ItemID.BRONZE_BAR, 6.2, 12.5);

        int level;
        int oreId;
        int secondaryOreId = -1; // To check for invalid
        int secondaryOreAmount;
        int productId;
        double exp, anvilExp;

        Bars(int level, int oreId, int secondaryOreId, int secondaryOreAmount, int productId, double exp, double anvilExp) {
            this(level, oreId, productId, exp, anvilExp);
            this.secondaryOreId = secondaryOreId;
            this.secondaryOreAmount = secondaryOreAmount;
        }

        Bars(int level, int oreId, int productId, double exp, double anvilExp) {
            this.level = level;
            this.oreId = oreId;
            this.productId = productId;
            this.exp = exp;
            this.anvilExp = anvilExp;
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(productId);
            skillingItemSelection.setSkill(SMITHING);
            return skillingItemSelection;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Smithing} Smelting Bar: " + Utils.getItemName(productId));
            itemCreationMainTask.ticksPerActivity = 4;
            itemCreationMainTask.maxItemSets = 13000; // 13000 buy limit
            itemCreationMainTask.addSkillRequirement(SMITHING, level);

            itemCreationMainTask.addExperienceReward(SMITHING, exp);
            itemCreationMainTask.addProduct(productId);

            itemCreationMainTask.addMaterial(oreId);

            if (secondaryOreId != -1)
                itemCreationMainTask.addMaterial(secondaryOreId, secondaryOreAmount);

            // Actions
            ConditionInventoryItem haveItems = new ConditionInventoryItem(oreId);
            if (secondaryOreId != -1)
                haveItems.addItem(secondaryOreId, secondaryOreAmount);

            // Widget handling
            Task makeScreenTask = new MakescreenTask(productId, oreId).getTask();
            itemCreationMainTask.addTasks(makeScreenTask);

            // Furnace interaction
            String itemName = Utils.getItemName(productId);
            TaskWaiting objClickWaiting = new TaskWaiting(20000, 30000, "Wait After Clicking Furnace");
            objClickWaiting.addConditions(new ConditionMakescreen());

            TaskCondition objCond = new ConditionGameObject("Furnace");
            TaskAction objAction = new ActionGameObject("Furnace").setTaskWaiting(objClickWaiting);
            Task objTask = new Task(objCond, objAction).addConditions(haveItems);
            ; // .addConditions(haveItems);
            itemCreationMainTask.addTasks(objTask);

            //TODO Cond: have items, travel to valid location

            return itemCreationMainTask;
        }

    }

    public enum AnvilItems {

        // Bronze
        BRONZE_PLATEBODY(18, ItemID.BRONZE_PLATEBODY, Bars.BRONZE,5),
        BRONZE_PLATESKIRT(16, ItemID.BRONZE_PLATESKIRT, Bars.BRONZE,3),
        BRONZE_PLATELEGS(16, ItemID.BRONZE_PLATELEGS, Bars.BRONZE,3),
        BRONZE_2H_SWORD(14, ItemID.BRONZE_2H_SWORD, Bars.BRONZE, 3),
//        BRONZE_CLAWS(13, ItemID.BRONZE_CLAWS, Bars.BRONZE, 2),// Needs death plateau
        BRONZE_KITESHIELD(12, ItemID.BRONZE_KITESHIELD, Bars.BRONZE, 3),
        BRONZE_CHAINBODY(11, ItemID.BRONZE_CHAINBODY, Bars.BRONZE, 3),
        BRONZE_BATTLEAXE(10, ItemID.BRONZE_BATTLEAXE, Bars.BRONZE, 3),
        BRONZE_WARHAMMER(9, ItemID.BRONZE_WARHAMMER, Bars.BRONZE, 3),
        BRONZE_SQ_SHIELD(8, ItemID.BRONZE_SQ_SHIELD, Bars.BRONZE, 2),
        BRONZE_KNIFE(7, ItemID.BRONZE_KNIFE, 5, Bars.BRONZE),
        BRONZE_FULL_HELM(7, ItemID.BRONZE_FULL_HELM, Bars.BRONZE, 2),
        BRONZE_JAVELIN_HEADS(6, ItemID.BRONZE_JAVELIN_HEADS, 5, Bars.BRONZE),
        BRONZE_LONGSWORD(6, ItemID.BRONZE_LONGSWORD, Bars.BRONZE, 2),
        BRONZE_LIMBS(6, ItemID.BRONZE_LIMBS, Bars.BRONZE),
        BRONZE_ARROWTIPS(5, ItemID.BRONZE_ARROWTIPS, 15, Bars.BRONZE),
//        BRONZE_HASTA(5, ItemID.BRONZE_HASTA, Bars.BRONZE), // Requires log, barbarian smithing
//        BRONZE_SPEAR(5, ItemID.BRONZE_SPEAR, Bars.BRONZE), // Requires log, barbarian smithing
        BRONZE_SCIMITAR(5, ItemID.BRONZE_SCIMITAR, Bars.BRONZE, 2),
        BRONZE_NAILS(4, ItemID.BRONZE_NAILS, 15, Bars.BRONZE),
        BRONZE_WIRE(4, ItemID.BRONZE_WIRE, Bars.BRONZE),
        BRONZE_DART_TIP(4, ItemID.BRONZE_DART_TIP, 10, Bars.BRONZE),
        BRONZE_SWORD(4, ItemID.BRONZE_SWORD, Bars.BRONZE),
        BRONZE_BOLTS_UNF(3, ItemID.BRONZE_BOLTS_UNF, 10, Bars.BRONZE),
        BRONZE_MED_HELM(3, ItemID.BRONZE_MED_HELM, Bars.BRONZE),
        BRONZE_MACE(2, ItemID.BRONZE_MACE, Bars.BRONZE),
        BRONZE_AXE(1, ItemID.BRONZE_AXE, Bars.BRONZE),
        BRONZE_DAGGER(1, ItemID.BRONZE_DAGGER, Bars.BRONZE),

        // IRON
        IRON_PLATEBODY(33, ItemID.IRON_PLATEBODY, Bars.IRON,5),
        IRON_PLATESKIRT(31, ItemID.IRON_PLATESKIRT, Bars.IRON,3),
        IRON_PLATELEGS(31, ItemID.IRON_PLATELEGS, Bars.IRON,3),
        IRON_2H_SWORD(29, ItemID.IRON_2H_SWORD, Bars.IRON, 3),
//        IRON_CLAWS(28, ItemID.IRON_CLAWS, Bars.IRON, 2),// Needs death plateau
        IRON_KITESHIELD(27, ItemID.IRON_KITESHIELD, Bars.IRON, 3),
        OIL_LANTERN_FRAME(26, ItemID.OIL_LANTERN_FRAME, Bars.IRON),
        IRON_CHAINBODY(26, ItemID.IRON_CHAINBODY, Bars.IRON, 3),
        IRON_BATTLEAXE(25, ItemID.IRON_BATTLEAXE, Bars.IRON, 3),
        IRON_WARHAMMER(24, ItemID.IRON_WARHAMMER, Bars.IRON, 3),
        IRON_LIMBS(23, ItemID.IRON_LIMBS, Bars.IRON),
        IRON_SQ_SHIELD(23, ItemID.IRON_SQ_SHIELD, Bars.IRON, 2),
        IRON_KNIFE(22, ItemID.IRON_KNIFE, 5, Bars.IRON),
        IRON_FULL_HELM(22, ItemID.IRON_FULL_HELM, Bars.IRON, 2),
        IRON_JAVELIN_HEADS(21, ItemID.IRON_JAVELIN_HEADS, 5, Bars.IRON),
        IRON_LONGSWORD(21, ItemID.IRON_LONGSWORD, Bars.IRON, 2),
        IRON_ARROWTIPS(20, ItemID.IRON_ARROWTIPS, 15, Bars.IRON),
//      IRON_HASTA(5, ItemID.IRON_HASTA, Bars.IRON), // Requires log, barbarian smithing
//      IRON_SPEAR(5, ItemID.IRON_SPEAR, Bars.IRON), // Requires log, barbarian smithing
        IRON_SCIMITAR(20, ItemID.IRON_SCIMITAR, Bars.IRON, 2),
        IRON_NAILS(19, ItemID.IRON_NAILS, 15, Bars.IRON),
        IRON_DART_TIP(19, ItemID.IRON_DART_TIP, 10, Bars.IRON),
        IRON_SWORD(19, ItemID.IRON_SWORD, Bars.IRON),
        IRON_BOLTS_UNF(18, ItemID.IRON_BOLTS_UNF, 10, Bars.IRON),
        IRON_MED_HELM(18, ItemID.IRON_MED_HELM, Bars.IRON),
        IRON_SPIT(17, ItemID.IRON_SPIT, Bars.IRON),
        IRON_MACE(17, ItemID.IRON_MACE, Bars.IRON),
        IRON_AXE(16, ItemID.IRON_AXE, Bars.IRON),
        IRON_DAGGER(15, ItemID.IRON_DAGGER, Bars.IRON),

        // STEEL
        BULLSEYE_LANTERN_UNF(49, ItemID.BULLSEYE_LANTERN_UNF, Bars.STEEL),
        STEEL_PLATEBODY(48, ItemID.STEEL_PLATEBODY, Bars.STEEL,5),
        STEEL_PLATESKIRT(46, ItemID.STEEL_PLATESKIRT, Bars.STEEL,3),
        STEEL_PLATELEGS(46, ItemID.STEEL_PLATELEGS, Bars.STEEL,3),
        STEEL_2H_SWORD(44, ItemID.STEEL_2H_SWORD, Bars.STEEL, 3),
//        STEEL_CLAWS(43, ItemID.STEEL_CLAWS, Bars.STEEL, 2),// Needs death plateau
        STEEL_KITESHIELD(42, ItemID.STEEL_KITESHIELD, Bars.STEEL, 3),
        STEEL_CHAINBODY(41, ItemID.STEEL_CHAINBODY, Bars.STEEL, 3),
        STEEL_BATTLEAXE(40, ItemID.STEEL_BATTLEAXE, Bars.STEEL, 3),
        STEEL_WARHAMMER(39, ItemID.STEEL_WARHAMMER, Bars.STEEL, 3),
        STEEL_SQ_SHIELD(38, ItemID.STEEL_SQ_SHIELD, Bars.STEEL, 2),
        STEEL_KNIFE(37, ItemID.STEEL_KNIFE, 5, Bars.STEEL),
        STEEL_FULL_HELM(37, ItemID.STEEL_FULL_HELM, Bars.STEEL, 2),
        STEEL_STUDS(36, ItemID.STEEL_STUDS, 5, Bars.STEEL),
        STEEL_JAVELIN_HEADS(36, ItemID.STEEL_JAVELIN_HEADS, 5, Bars.STEEL),
        STEEL_LONGSWORD(36, ItemID.STEEL_LONGSWORD, Bars.STEEL, 2),
        STEEL_LIMBS(36, ItemID.STEEL_LIMBS, Bars.STEEL),
        // Cannon Balls
        STEEL_ARROWTIPS(35, ItemID.STEEL_ARROWTIPS, 15, Bars.STEEL),
        //      STEEL_HASTA(5, ItemID.STEEL_HASTA, Bars.STEEL), // Requires log, barbarian smithing
//      STEEL_SPEAR(5, ItemID.STEEL_SPEAR, Bars.STEEL), // Requires log, barbarian smithing
        STEEL_SCIMITAR(35, ItemID.STEEL_SCIMITAR, Bars.STEEL, 2),
        STEEL_DART_TIP(34, ItemID.STEEL_DART_TIP, 10, Bars.STEEL),
        STEEL_NAILS(34, ItemID.STEEL_NAILS, 15, Bars.STEEL),
        STEEL_SWORD(34, ItemID.STEEL_SWORD, Bars.STEEL),
        STEEL_BOLTS_UNF(33, ItemID.STEEL_BOLTS_UNF, 10, Bars.STEEL),
        STEEL_MED_HELM(33, ItemID.STEEL_MED_HELM, Bars.STEEL),
        STEEL_MACE(32, ItemID.STEEL_MACE, Bars.STEEL),
        STEEL_AXE(31, ItemID.STEEL_AXE, Bars.STEEL),
        STEEL_DAGGER(30, ItemID.STEEL_DAGGER, Bars.STEEL),

        // MITHRIL
        MITHRIL_PLATEBODY(68, ItemID.MITHRIL_PLATEBODY, Bars.MITHRIL,5),
        MITHRIL_PLATESKIRT(66, ItemID.MITHRIL_PLATESKIRT, Bars.MITHRIL,3),
        MITHRIL_PLATELEGS(66, ItemID.MITHRIL_PLATELEGS, Bars.MITHRIL,3),
        MITHRIL_2H_SWORD(64, ItemID.MITHRIL_2H_SWORD, Bars.MITHRIL, 3),
//        MITHRIL_CLAWS(63, ItemID.MITHRIL_CLAWS, Bars.MITHRIL, 2),// Needs death plateau
        MITHRIL_KITESHIELD(62, ItemID.MITHRIL_KITESHIELD, Bars.MITHRIL, 3),
        MITHRIL_CHAINBODY(61, ItemID.MITHRIL_CHAINBODY, Bars.MITHRIL, 3),
        MITHRIL_BATTLEAXE(60, ItemID.MITHRIL_BATTLEAXE, Bars.MITHRIL, 3),
        MITH_GRAPPLE_TIP(59, ItemID.MITH_GRAPPLE_TIP, Bars.MITHRIL),
        MITHRIL_WARHAMMER(59, ItemID.MITHRIL_WARHAMMER, Bars.MITHRIL, 3),
        MITHRIL_SQ_SHIELD(58, ItemID.MITHRIL_SQ_SHIELD, Bars.MITHRIL, 2),
        MITHRIL_KNIFE(57, ItemID.MITHRIL_KNIFE, 5, Bars.MITHRIL),
        MITHRIL_FULL_HELM(57, ItemID.MITHRIL_FULL_HELM, Bars.MITHRIL, 2),
        MITHRIL_JAVELIN_HEADS(56, ItemID.MITHRIL_JAVELIN_HEADS, 5, Bars.MITHRIL),
        MITHRIL_LONGSWORD(56, ItemID.MITHRIL_LONGSWORD, Bars.MITHRIL, 2),
        MITHRIL_LIMBS(56, ItemID.MITHRIL_LIMBS, Bars.MITHRIL),
        MITHRIL_ARROWTIPS(55, ItemID.MITHRIL_ARROWTIPS, 15, Bars.MITHRIL),
        //      MITHRIL_HASTA(5, ItemID.MITHRIL_HASTA, Bars.MITHRIL), // Requires log, barbarian smithing
//      MITHRIL_SPEAR(5, ItemID.MITHRIL_SPEAR, Bars.MITHRIL), // Requires log, barbarian smithing
        MITHRIL_SCIMITAR(55, ItemID.MITHRIL_SCIMITAR, Bars.MITHRIL, 2),
        MITHRIL_NAILS(54, ItemID.MITHRIL_NAILS, 15, Bars.MITHRIL),
        MITHRIL_DART_TIP(54, ItemID.MITHRIL_DART_TIP, 10, Bars.MITHRIL),
        MITHRIL_SWORD(54, ItemID.MITHRIL_SWORD, Bars.MITHRIL),
        MITHRIL_BOLTS_UNF(53, ItemID.MITHRIL_BOLTS_UNF, 10, Bars.MITHRIL),
        MITHRIL_MED_HELM(53, ItemID.MITHRIL_MED_HELM, Bars.MITHRIL),
        MITHRIL_MACE(52, ItemID.MITHRIL_MACE, Bars.MITHRIL),
        MITHRIL_AXE(51, ItemID.MITHRIL_AXE, Bars.MITHRIL),
        MITHRIL_DAGGER(50, ItemID.MITHRIL_DAGGER, Bars.MITHRIL),

        // ADAMANT
        ADAMANT_PLATEBODY(88, ItemID.ADAMANT_PLATEBODY, Bars.ADAMANT,5),
        ADAMANT_PLATESKIRT(86, ItemID.ADAMANT_PLATESKIRT, Bars.ADAMANT,3),
        ADAMANT_PLATELEGS(86, ItemID.ADAMANT_PLATELEGS, Bars.ADAMANT,3),
        ADAMANT_2H_SWORD(84, ItemID.ADAMANT_2H_SWORD, Bars.ADAMANT, 3),
//        ADAMANT_CLAWS(83, ItemID.ADAMANT_CLAWS, Bars.ADAMANT, 2), // Needs death plateau
        ADAMANT_KITESHIELD(82, ItemID.ADAMANT_KITESHIELD, Bars.ADAMANT, 3),
        ADAMANT_CHAINBODY(88, ItemID.ADAMANT_CHAINBODY, Bars.ADAMANT, 3),
        ADAMANT_BATTLEAXE(80, ItemID.ADAMANT_BATTLEAXE, Bars.ADAMANT, 3),
        ADAMANT_WARHAMMER(79, ItemID.ADAMANT_WARHAMMER, Bars.ADAMANT, 3),
        ADAMANT_SQ_SHIELD(78, ItemID.ADAMANT_SQ_SHIELD, Bars.ADAMANT, 2),
        ADAMANT_KNIFE(77, ItemID.ADAMANT_KNIFE, 5, Bars.ADAMANT),
        ADAMANT_FULL_HELM(77, ItemID.ADAMANT_FULL_HELM, Bars.ADAMANT, 2),
        ADAMANT_JAVELIN_HEADS(76, ItemID.ADAMANT_JAVELIN_HEADS, 5, Bars.ADAMANT),
        ADAMANT_LONGSWORD(76, ItemID.ADAMANT_LONGSWORD, Bars.ADAMANT, 2),
        ADAMANTITE_LIMBS(76, ItemID.ADAMANTITE_LIMBS, Bars.ADAMANT),
        ADAMANT_ARROWTIPS(75, ItemID.ADAMANT_ARROWTIPS, 15, Bars.ADAMANT),
        //      ADAMANT_HASTA(5, ItemID.ADAMANT_HASTA, Bars.ADAMANT), // Requires log, barbarian smithing
//      ADAMANT_SPEAR(5, ItemID.ADAMANT_SPEAR, Bars.ADAMANT), // Requires log, barbarian smithing
        ADAMANT_SCIMITAR(75, ItemID.ADAMANT_SCIMITAR, Bars.ADAMANT, 2),
        ADAMANTITE_NAILS(74, ItemID.ADAMANTITE_NAILS, 15, Bars.ADAMANT),
        ADAMANT_DART_TIP(74, ItemID.ADAMANT_DART_TIP, 10, Bars.ADAMANT),
        ADAMANT_SWORD(74, ItemID.ADAMANT_SWORD, Bars.ADAMANT),
        ADAMANT_BOLTSUNF(73, ItemID.ADAMANT_BOLTSUNF, 10, Bars.ADAMANT),
        ADAMANT_MED_HELM(73, ItemID.ADAMANT_MED_HELM, Bars.ADAMANT),
        ADAMANT_MACE(72, ItemID.ADAMANT_MACE, Bars.ADAMANT),
        ADAMANT_AXE(71, ItemID.ADAMANT_AXE, Bars.ADAMANT),
        ADAMANT_DAGGER(71, ItemID.ADAMANT_DAGGER, Bars.ADAMANT),

        // RUNE
        RUNE_PLATEBODY(99, ItemID.RUNE_PLATEBODY, Bars.RUNE,5),
        RUNE_PLATESKIRT(99, ItemID.RUNE_PLATESKIRT, Bars.RUNE,3),
        RUNE_PLATELEGS(99, ItemID.RUNE_PLATELEGS, Bars.RUNE,3),
        RUNE_2H_SWORD(99, ItemID.RUNE_2H_SWORD, Bars.RUNE, 3),
//        RUNE_CLAWS(98, ItemID.RUNE_CLAWS, Bars.RUNE, 2), // Needs death plateau
        RUNE_KITESHIELD(97, ItemID.RUNE_KITESHIELD, Bars.RUNE, 3),
        RUNE_CHAINBODY(96, ItemID.RUNE_CHAINBODY, Bars.RUNE, 3),
        RUNE_BATTLEAXE(95, ItemID.RUNE_BATTLEAXE, Bars.RUNE, 3),
        RUNE_WARHAMMER(94, ItemID.RUNE_WARHAMMER, Bars.RUNE, 3),
        RUNE_SQ_SHIELD(93, ItemID.RUNE_SQ_SHIELD, Bars.RUNE, 2),
        RUNE_KNIFE(92, ItemID.RUNE_KNIFE, 5, Bars.RUNE),
        RUNE_FULL_HELM(92, ItemID.RUNE_FULL_HELM, Bars.RUNE, 2),
        RUNE_JAVELIN_HEADS(91, ItemID.RUNE_JAVELIN_HEADS, 5, Bars.RUNE),
        RUNE_LONGSWORD(91, ItemID.RUNE_LONGSWORD, Bars.RUNE, 2),
        RUNITE_LIMBS(91, ItemID.RUNITE_LIMBS, Bars.RUNE),
        RUNE_ARROWTIPS(90, ItemID.RUNE_ARROWTIPS, 15, Bars.RUNE),
        //      RUNE_HASTA(5, ItemID.RUNE_HASTA, Bars.RUNE), // Requires log, barbarian smithing
//      RUNE_SPEAR(5, ItemID.RUNE_SPEAR, Bars.RUNE), // Requires log, barbarian smithing
        RUNE_SCIMITAR(90, ItemID.RUNE_SCIMITAR, Bars.RUNE, 2),
        RUNE_NAILS(89, ItemID.RUNE_NAILS, 15, Bars.RUNE),
        RUNE_DART_TIP(89, ItemID.RUNE_DART_TIP, 10, Bars.RUNE),
        RUNE_SWORD(89, ItemID.RUNE_SWORD, Bars.RUNE),
        RUNITE_BOLTS_UNF(88, ItemID.RUNITE_BOLTS_UNF, 10, Bars.RUNE),
        RUNE_MED_HELM(88, ItemID.RUNE_MED_HELM, Bars.RUNE),
        RUNE_MACE(87, ItemID.RUNE_MACE, Bars.RUNE),
        RUNE_AXE(86, ItemID.RUNE_AXE, Bars.RUNE),
        RUNE_DAGGER(85, ItemID.RUNE_DAGGER, Bars.RUNE),

        ;

        int level, productId, productAmount, barAmount;
        double exp;
        Bars bar;

        AnvilItems(int level, int productId, Bars bar) {
            this(level, productId, bar, 1);
        }

        AnvilItems(int level, int productId, Bars bar, int barAmount) {
            this(level, productId, 1, bar, barAmount);
        }

        AnvilItems(int level, int productId, int productAmount, Bars bar) {
            this(level, productId, productAmount, bar, 1);
        }

        AnvilItems(int level, int productId, int productAmount, Bars bar, int barAmount) {
            this.level = level;
            this.productId = productId;
            this.productAmount = productAmount;
            this.exp = bar.anvilExp * barAmount;
            this.bar = bar;
            this.barAmount = barAmount;
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(productId);
            skillingItemSelection.setSkill(SMITHING);
            return skillingItemSelection;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Smithing} Anvil Item: " + Utils.getItemName(productId));
            itemCreationMainTask.ticksPerActivity = 5; // Most are 5, so...
            itemCreationMainTask.maxItemSets = 10000 / barAmount; // 10000 buy limit on bars
            itemCreationMainTask.addSkillRequirement(SMITHING, level);

            itemCreationMainTask.addExperienceReward(SMITHING, exp);
            itemCreationMainTask.addProduct(productId);

            itemCreationMainTask.addMaterial(bar.productId, barAmount);
            itemCreationMainTask.addTool(ItemID.HAMMER);

            //TODO bank location

            // Actions
            ConditionInventoryItem haveItems = new ConditionInventoryItem(ItemID.HAMMER);
            haveItems.addItem(bar.productId, barAmount);

            // Widget handling
            String itemName = Utils.getItemName(productId);
            ConditionWidget conditionWidget = new ConditionWidget().setComponentName(itemName);

            ActionWidget actionWidget = new ActionWidget().setComponentName(itemName);
            TaskWaiting taskWaiting = new TaskWaiting(180000, 240000, "Wait after selecting item");
            taskWaiting.addCondition("Out of items", () -> !haveItems.isValid());
            actionWidget.setTaskWaiting(taskWaiting);

            itemCreationMainTask.addTask(conditionWidget, actionWidget);

            // Anvil interaction
            TaskWaiting objClickWaiting = new TaskWaiting(20000, 30000, "Wait After Clicking Anvil");
            objClickWaiting.addConditions(conditionWidget);
            TaskCondition objCond = new ConditionGameObject("Anvil");
            TaskAction objAction = new ActionGameObject("Anvil").setTaskWaiting(objClickWaiting);
            Task objTask = new Task(objCond, objAction).addConditions(haveItems);
            itemCreationMainTask.addTasks(objTask);

            return itemCreationMainTask;
        }

    }

    public static ArrayList<MainTask> getAllSmithingMainTasks() {
        ArrayList<MainTask> list = new ArrayList<>();

        // Bars
        list.addAll(Arrays.stream(SmithingData.Bars.values()).map(SmithingData.Bars::getMainTask).collect(Collectors.toList()));

        // Anvil Items
        list.addAll(Arrays.stream(SmithingData.AnvilItems.values()).map(SmithingData.AnvilItems::getMainTask).collect(Collectors.toList()));


        return list;
    }


}
