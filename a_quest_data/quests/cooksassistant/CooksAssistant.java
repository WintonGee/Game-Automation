package scripts.main_package.a_quest_data.quests.cooksassistant;

import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.ExperienceReward;
import scripts.main_package.a_quest_data.objects.QuestPointReward;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.objects.unused.PanelDetails;
import scripts.main_package.a_quest_data.objects.unused.UnlockReward;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.a_quest_data.step.NpcStep;
import scripts.main_package.a_quest_data.step.QuestStep;
import scripts.raw_data.ItemID;
import scripts.raw_data.NpcID;

import java.util.*;

public class CooksAssistant extends BasicQuestHelper {
    //Items Required
    ItemRequirement egg, milk, flour;

    QuestStep doQuest;

    @Override
    public Map<Integer, QuestStep> loadSteps() {
        setupItemRequirements();
        Map<Integer, QuestStep> steps = new HashMap<>();

        doQuest = new NpcStep(this, "Cook", new WorldPoint(3206, 3214, 0),
                "Give the Cook in Lumbridge Castle's kitchen the required items to finish the quest.",
                egg, milk, flour);
        doQuest.addDialogStep("You're a cook, why don't you bake me a cake?", "I'll get right on it.");

        steps.put(0, doQuest);
        steps.put(1, doQuest);

        return steps;
    }

    public void setupItemRequirements() {
        egg = new ItemRequirement("Egg", ItemID.EGG);
        egg.setTooltip("You can find an egg in the farm north of Lumbridge.");
        milk = new ItemRequirement("Bucket of milk", ItemID.BUCKET_OF_MILK);
        milk.setTooltip("You can get a bucket from the Lumbridge General Store, then milk a Dairy Cow north-east of Lumbridge.");
        flour = new ItemRequirement("Pot of flour", ItemID.POT_OF_FLOUR);
        flour.setTooltip("You can buy a pot from the Lumbridge General Store, collect some wheat from a field north of Lumbridge, then grind it in the Lumbridge Mill north of Lumbridge");
    }

    @Override
    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(egg);
        reqs.add(flour);
        reqs.add(milk);
        return reqs;
    }

    @Override
    public QuestPointReward getQuestPointReward() {
        return new QuestPointReward(1);
    }

    @Override
    public List<ExperienceReward> getExperienceRewards() {
        return Collections.singletonList(new ExperienceReward(Skill.COOKING, 300));
    }

    @Override
    public List<UnlockReward> getUnlockRewards() {
        return Collections.singletonList(new UnlockReward("Permission to use The Cook's range."));
    }

    @Override
    public List<PanelDetails> getPanels() {
        List<PanelDetails> allSteps = new ArrayList<>();
        allSteps.add(new PanelDetails("Starting off", Collections.singletonList(doQuest), flour, egg, milk));
        return allSteps;
    }
}
