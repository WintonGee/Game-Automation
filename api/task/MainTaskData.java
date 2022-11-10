package scripts.main_package.api.task;

import lombok.NonNull;
import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_skill_data.bank_skills.cooking.CookingData;
import scripts.main_package.a_skill_data.bank_skills.crafting.CraftingData;
import scripts.main_package.a_skill_data.bank_skills.fletching.FletchingData;
import scripts.main_package.a_skill_data.bank_skills.herblore.HerbloreData;
import scripts.main_package.a_skill_data.bank_skills.smithing.SmithingData;
import scripts.main_package.a_skill_data.positionable_skills.fishing.FishingData;
import scripts.main_package.a_skill_data.positionable_skills.mining.MiningData;
import scripts.main_package.a_skill_data.positionable_skills.woodcutting.WoodcuttingData;

import java.util.ArrayList;

// This class is used to hold data on all main tasks
// - Skills
// - Quests
// - Money making
public class MainTaskData {

    public static boolean isFreeToPlayMode;

    // This list is to be loaded on script start
    @NonNull
    public static ArrayList<MainTask> enabledSkillTaskList; // TODO add in money making methods?

    // List of tasks loaded on script start
    @NonNull
    public static ArrayList<QuestHelperQuest> enabledQuestHelperList;

    //TODO areas here?


    public static ArrayList<MainTask> getAllSkillTasks() {
        ArrayList<MainTask> list = new ArrayList<>();

        // Crafting
        list.addAll(CraftingData.getAllCraftingMainTasks());

        // Cooking
        list.addAll(CookingData.getAllCookingMainTasks());

        // Fishing
        list.addAll(FishingData.getAllFishingMainTasks());

        // Fletching
        list.addAll(FletchingData.getAllFletchingMainTasks());

        // Herblore
        list.addAll(HerbloreData.getAllHerbloreMainTasks());

        // Mining
        list.addAll(MiningData.getAllMiningMainTasks());

        // Smithing
        list.addAll(SmithingData.getAllSmithingMainTasks());

        // Woodcutting
        list.addAll(WoodcuttingData.getAllWoodcuttingMainTasks());

        return list;
    }

    //TODO money making tasks
}
