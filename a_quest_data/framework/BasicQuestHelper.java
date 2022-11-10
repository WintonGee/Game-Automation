package scripts.main_package.a_quest_data.framework;

import scripts.main_package.a_quest_data.objects.ExperienceReward;
import scripts.main_package.a_quest_data.objects.ItemReward;
import scripts.main_package.a_quest_data.objects.QuestPointReward;
import scripts.main_package.a_quest_data.objects.unused.PanelDetails;
import scripts.main_package.a_quest_data.objects.unused.UnlockReward;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.a_quest_data.step.QuestStep;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class BasicQuestHelper {

    //TODO a field for the time it takes in ticks

    // For quests like barcrawl where all values are based off one varbit
    public QuestStep questStep() {
        return null;
    }

    public Map<Integer, QuestStep> loadSteps() {
        return null;
    }

    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    //TODO create a field for quest items
    public List<ItemRequirement> getItemRequirements() {
        return null;
    }

    public List<ItemRequirement> getItemRecommended() {
        return null;
    } //TODO

    // Used to hold information for ending details
    // - Chat options
    public QuestStep endingQuestStep = new QuestStep(null) {
    };

    // This will hold the data on all items needed for the quest.
    // Usually used on quests that require many items (28+)
    public List<ItemRequirement> getStartingItemRequirements() {
        return null;
    }
    //TODO food needed?

    public List<ExperienceReward> getExperienceRewards() {
        return null;
    }

    public QuestPointReward getQuestPointReward() {
        return null;
    }

    public List<ItemReward> getItemRewards() {
        return null;
    }

//    public MainTaskInfo getMainTaskInfo() {
//        MainTaskInfo mainTaskInfo = new MainTaskInfo();
//        return mainTaskInfo;
//    }

    // Unused Below Here
    public List<PanelDetails> getPanels() {
        return null;
    }

    public List<UnlockReward> getUnlockRewards() {
        return null;
    }

    public List<String> getCombatRequirements()
    {
        return null;
    }



}
