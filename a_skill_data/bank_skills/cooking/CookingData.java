package scripts.main_package.a_skill_data.bank_skills.cooking;

import org.tribot.script.sdk.Skill;
import scripts.main_package.a_skill_data.ItemCreationMainTask;
import scripts.main_package.a_skill_data.bank_skills.CombineTask;
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

public class CookingData {

    static Skill COOKING = Skill.COOKING;

    public enum RawFood {


        MANTA_RAY(91, ItemID.RAW_MANTA_RAY, ItemID.MANTA_RAY, 216.3),
        RAW_DARK_CRAB(90, ItemID.RAW_DARK_CRAB, ItemID.DARK_CRAB, 215),
        RAW_ANGLERFISH(84, ItemID.RAW_ANGLERFISH, ItemID.ANGLERFISH, 230),
        RAW_SEA_TURTLE(82, ItemID.RAW_SEA_TURTLE, ItemID.SEA_TURTLE, 211.3),
        RAW_SHARK(80, ItemID.RAW_SHARK, ItemID.SHARK, 210),
        RAW_MONKFISH(62, ItemID.RAW_MONKFISH, ItemID.MONKFISH, 150),
        RAW_SWORDFISH(50, ItemID.RAW_SWORDFISH, ItemID.SWORDFISH, 140),
        RAW_BASS(43, ItemID.RAW_BASS, ItemID.BASS, 130),
        RAW_LOBSTER(40, ItemID.RAW_LOBSTER, ItemID.LOBSTER, 120),
        RAW_RAINBOW_FISH(35, ItemID.RAW_RAINBOW_FISH, ItemID.RAINBOW_FISH, 110),
        RAW_TUNA(30, ItemID.RAW_TUNA, ItemID.TUNA, 100),
        RAW_SALMON(25, ItemID.RAW_SALMON, ItemID.SALMON, 90),
        RAW_PIKE(20, ItemID.RAW_PIKE, ItemID.PIKE, 80),
        RAW_COD(18, ItemID.RAW_COD, ItemID.COD, 75),
        RAW_TROUT(15, ItemID.RAW_TROUT, ItemID.TROUT, 70),
        RAW_HERRING(5, ItemID.RAW_HERRING, ItemID.HERRING, 50),
        RAW_SARDINE(1, ItemID.RAW_SARDINE, ItemID.SARDINE, 40),
        RAW_ANCHOVIES(1, ItemID.RAW_ANCHOVIES, ItemID.ANCHOVIES, 30),
        RAW_SHRIMPS(1, ItemID.RAW_SHRIMPS, ItemID.SHRIMPS, 30);

        //TODO add in some meats

        private final int level;
        private final int rawId;
        private final int productId;
        private final double exp;

        RawFood(int level, int rawId, int productId, double exp) {
            this.level = level;
            this.rawId = rawId;
            this.productId = productId;
            this.exp = exp;
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(productId);
            skillingItemSelection.setSkill(COOKING);
            return skillingItemSelection;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Cooking} Cooking: " + Utils.getItemName(productId));
            itemCreationMainTask.ticksPerActivity = 4;
            itemCreationMainTask.maxItemSets = 13000; // 13000 buy limit
            itemCreationMainTask.addSkillRequirement(COOKING, level);

            // Materials
            itemCreationMainTask.addMaterial(rawId);

            // Actions
            ConditionInventoryItem haveItems = new ConditionInventoryItem(rawId);

            // Makescreen
            Task makeScreenTask = new MakescreenTask(productId, rawId).getTask();
            itemCreationMainTask.addTasks(makeScreenTask);

            // Range interaction
            TaskWaiting objClickWaiting = new TaskWaiting(20000, 30000, "Wait After Clicking Range");
            objClickWaiting.addConditions(new ConditionMakescreen());

            TaskCondition objCond = new ConditionGameObject("Range");
            TaskAction objAction = new ActionGameObject("Range").setTaskWaiting(objClickWaiting);
            Task objTask = new Task(objCond, objAction).addConditions(haveItems);; // .addConditions(haveItems);
            itemCreationMainTask.addTasks(objTask);

            //TODO Cond: have items, travel to valid location

            return itemCreationMainTask;
        }

    }



    public static ArrayList<MainTask> getAllCookingMainTasks() {
        ArrayList<MainTask> list = new ArrayList<>();

        // Raw fish
        list.addAll(Arrays.stream(RawFood.values()).map(RawFood::getMainTask).collect(Collectors.toList()));

        return list;
    }

}
