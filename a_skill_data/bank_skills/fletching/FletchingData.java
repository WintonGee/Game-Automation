package scripts.main_package.a_skill_data.bank_skills.fletching;

import lombok.Setter;
import org.tribot.script.sdk.Skill;
import scripts.main_package.a_skill_data.bank_skills.CombineTask;
import scripts.main_package.a_skill_data.ItemCreationMainTask;
import scripts.main_package.a_skill_data.bank_skills.MakescreenTask;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.api.task.Task;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FletchingData {

    private final static Skill FLETCHING = Skill.FLETCHING;
    private final static int KNIFE = ItemID.KNIFE, BOW_STRING = ItemID.BOW_STRING;

    public enum ArrowShaft {

        YEW(60,  ItemID.YEW_LOGS, 75, 12000),
        MAPLE(45,  ItemID.MAPLE_LOGS, 60),
        WILLOW(30,  ItemID.WILLOW_LOGS, 45),
        OAK(15,  ItemID.OAK_LOGS, 30),
        NORMAL(1,  ItemID.LOGS, 15);

        int level, logId, quantity, buyLimit;

        ArrowShaft(int level, int logId, int quantity) {
            this(level, logId, quantity, 15000);
        }

        ArrowShaft(int level, int logId, int quantity, int buyLimit) {
            this.level = level;
            this.logId = logId;
            this.quantity = quantity; // Num of arrow shafts per log
            this.buyLimit = buyLimit;
        }

        private int getExp() {
            return quantity / 3;
        }

        public MainTask getMainTask() {
            final int ARROW_SHAFT = ItemID.ARROW_SHAFT;
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Fletching} Arrow Shafts, Log: " + Utils.getItemName(logId));
            itemCreationMainTask.ticksPerActivity = 2;
            itemCreationMainTask.maxItemSets = this.buyLimit; // Buy Limit
            itemCreationMainTask.addSkillRequirement(FLETCHING, level);

            itemCreationMainTask.addExperienceReward(FLETCHING, getExp());
            itemCreationMainTask.addProduct(ARROW_SHAFT, quantity);

            itemCreationMainTask.addMaterial(logId);
            itemCreationMainTask.addTool(KNIFE);

            Task makeScreenTask = new MakescreenTask(ARROW_SHAFT).getTask();
            Task combineTask = new CombineTask(KNIFE, logId).getTask();
            itemCreationMainTask.addTasks(makeScreenTask, combineTask);

            return itemCreationMainTask;
        }

    }

    public enum Bows {

        MAGIC_LONG("Magic longbow", 85, ItemID.MAGIC_LOGS, ItemID.MAGIC_LONGBOW_U, ItemID.MAGIC_LONGBOW, 91.5),
        MAGIC_SHORT("Magic shortbow", 80, ItemID.MAGIC_LOGS, ItemID.MAGIC_SHORTBOW, ItemID.MAGIC_LONGBOW, 83.3),
        YEW_LONG("Yew longbow", 70, ItemID.YEW_LOGS, ItemID.YEW_LONGBOW_U, ItemID.YEW_LONGBOW, 75),
        YEW_SHORT("Yew shortbow", 65, ItemID.YEW_LOGS, ItemID.YEW_SHORTBOW, ItemID.YEW_LONGBOW, 67.5),
        MAPLE_LONG("Maple longbow", 55, ItemID.MAPLE_LOGS, ItemID.MAPLE_LONGBOW_U, ItemID.MAPLE_LONGBOW, 58.3),
        MAPLE_SHORT("Maple shortbow", 50, ItemID.MAPLE_LOGS, ItemID.MAPLE_SHORTBOW, ItemID.MAPLE_LONGBOW, 50),
        WILLOW_LONG("Willow longbow", 40, ItemID.WILLOW_LOGS, ItemID.WILLOW_LONGBOW_U, ItemID.WILLOW_LONGBOW, 41.5),
        WILLOW_SHORT("Willow shortbow", 35, ItemID.WILLOW_LOGS, ItemID.WILLOW_SHORTBOW, ItemID.WILLOW_LONGBOW, 33.3),
        OAK_LONG("Oak longbow", 25, ItemID.OAK_LOGS, ItemID.OAK_LONGBOW_U, ItemID.OAK_LONGBOW, 25),
        OAK_SHORT("Oak shortbow", 20, ItemID.OAK_LOGS, ItemID.OAK_SHORTBOW, ItemID.OAK_LONGBOW, 16.5),
        LONG("Longbow", 10, ItemID.LOGS, ItemID.LONGBOW_U, ItemID.LONGBOW, 10),
        SHORT("Shortbow", 5, ItemID.LOGS, ItemID.SHORTBOW_U, ItemID.SHORTBOW, 5);


        @Setter
        String name;
        @Setter
        int levelReq;
        @Setter
        int logId;
        @Setter
        int unfId;
        @Setter
        int bowId;
        @Setter
        double exp;

        Bows(String name, int levelReq, int logId, int unfId, int bowId, double exp) {
            this.setName(name);
            this.setLevelReq(levelReq);
            this.setLogId(logId);
            this.setUnfId(unfId);
            this.setBowId(bowId);
            this.setExp(exp);
        }

        public MainTask getUnfBowMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Fletching} Unfinished Bow: " + name);
            itemCreationMainTask.ticksPerActivity = 3;
            itemCreationMainTask.maxItemSets = 12000; // 12000-15000 buy limit
            itemCreationMainTask.addSkillRequirement(FLETCHING, levelReq);

            itemCreationMainTask.addExperienceReward(FLETCHING, exp);
            itemCreationMainTask.addProduct(unfId);

            itemCreationMainTask.addMaterial(logId);
            itemCreationMainTask.addTool(KNIFE);

            Task makeScreenTask = new MakescreenTask(unfId).getTask();
            Task combineTask = new CombineTask(KNIFE, logId).getTask();
            itemCreationMainTask.addTasks(makeScreenTask, combineTask);

            return itemCreationMainTask;
        }

        public MainTask getFinishedBowMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Fletching} Bow: " + name);
            itemCreationMainTask.ticksPerActivity = 2;
            itemCreationMainTask.maxItemSets = 10000; // 10000 buy limit
            itemCreationMainTask.addSkillRequirement(FLETCHING, levelReq);

            itemCreationMainTask.addExperienceReward(FLETCHING, exp);
            itemCreationMainTask.addProduct(bowId);

            itemCreationMainTask.addMaterial(unfId);
            itemCreationMainTask.addMaterial(BOW_STRING);

            Task makeScreenTask = new MakescreenTask(bowId, unfId).getTask();
            Task combineTask = new CombineTask(unfId, BOW_STRING).getTask();
            itemCreationMainTask.addTasks(makeScreenTask, combineTask);

            return itemCreationMainTask;
        }

    }

    public static ArrayList<MainTask> getAllFletchingMainTasks() {
        ArrayList<MainTask> list = new ArrayList<>();

        // Arrow shafts
        list.addAll(Arrays.stream(FletchingData.ArrowShaft.values()).map(FletchingData.ArrowShaft::getMainTask).collect(Collectors.toList()));

        // Unfinished Bow
        list.addAll(Arrays.stream(FletchingData.Bows.values()).map(FletchingData.Bows::getUnfBowMainTask).collect(Collectors.toList()));

        // Finished Bow
        list.addAll(Arrays.stream(FletchingData.Bows.values()).map(FletchingData.Bows::getFinishedBowMainTask).collect(Collectors.toList()));


        return list;
    }


}
