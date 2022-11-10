package scripts.main_package.a_quest_data.quests.xmarksthespot;

import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.ItemReward;
import scripts.main_package.a_quest_data.objects.QuestPointReward;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.objects.unused.PanelDetails;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.a_quest_data.step.DigStep;
import scripts.main_package.a_quest_data.step.NpcStep;
import scripts.main_package.a_quest_data.step.QuestStep;
import scripts.main_package.item_data.TeleportItemData;
import scripts.raw_data.ItemID;

import java.util.*;

public class XMarksTheSpot extends BasicQuestHelper {
    //Items Required
    ItemRequirement spade;

    // Items recommended
    ItemRequirement glory, stamina;

    QuestStep speakVeosLumbridge, digOutsideBob, digCastle, digDraynor, digMartin, speakVeosSarim, speakVeosSarimWithoutCasket;

    @Override
    public Map<Integer, QuestStep> loadSteps() {
        setupRequirements();
        setupSteps();

        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, speakVeosLumbridge);
        steps.put(1, steps.get(0));
        steps.put(2, digOutsideBob);
        steps.put(3, digCastle);
        steps.put(4, digDraynor);
        steps.put(5, digMartin);
        steps.put(6, speakVeosSarim);
        steps.put(7, speakVeosSarimWithoutCasket);

        return steps;
    }

    private void setupRequirements() {
        spade = new ItemRequirement("Spade", ItemID.SPADE);
        glory = new ItemRequirement("Amulet of Glory for faster teleport to Draynor Village.",
                TeleportItemData.AMULET_OF_GLORY.getTeleportItem().getBestIdWithMinCharges(3));
        stamina = new ItemRequirement("Stamina", ItemID.STAMINA_POTION4);
    }

    private void setupSteps() {
        speakVeosLumbridge = new NpcStep(this, "Veos", new WorldPoint(3228, 3242, 0),
                "Talk to Veos in The Sheared Ram pub in Lumbridge to start the quest.");
        speakVeosLumbridge.addDialogStep("I'm looking for a quest.");
        speakVeosLumbridge.addDialogStep("Sounds good, what should I do?");

        digOutsideBob = new DigStep(this, new WorldPoint(3230, 3209, 0),
                "Dig north of Bob's Brilliant Axes, on the west side of the plant against the wall of his house.");
        digOutsideBob.addDialogStep("Okay, thanks Veos.");

        digCastle = new DigStep(this, new WorldPoint(3203, 3212, 0),
                "Dig behind Lumbridge Castle, just outside the kitchen door.");

        digDraynor = new DigStep(this, new WorldPoint(3109, 3264, 0),
                "Dig north-west of the Draynor Village jail, just by the wheat farm.");

        digMartin = new DigStep(this, new WorldPoint(3078, 3259, 0),
                "Dig in the pig pen just west where Martin the Master Gardener is.",
                new ItemRequirement("Treasure scroll", ItemID.TREASURE_SCROLL_23070));

        ItemRequirement ancientCasket = new ItemRequirement("Ancient casket", ItemID.ANCIENT_CASKET);
        ancientCasket.setTooltip("If you've lost this you can get another by digging in the pig pen in Draynor Village.");

        speakVeosSarim = new NpcStep(this, "Veos", new WorldPoint(3054, 3245, 0),
                "Talk to Veos directly south of the Rusty Anchor Inn in Port Sarim to finish the quest.",
                ancientCasket);

        speakVeosSarimWithoutCasket = new NpcStep(this, "Veos", new WorldPoint(3054, 3245, 0),
                "Talk to Veos directly south of the Rusty Anchor Inn in Port Sarim to finish the quest.");

        speakVeosSarim.addSubSteps(speakVeosSarimWithoutCasket);
    }

    @Override
    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(spade);
        return reqs;
    }

    @Override
    public List<ItemRequirement> getItemRecommended() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(glory);
        reqs.add(stamina);
        return reqs;
    }

    @Override
    public QuestPointReward getQuestPointReward() {
        return new QuestPointReward(1);
    }

    @Override
    public List<ItemReward> getItemRewards() {
        return Arrays.asList(
                new ItemReward("300 Exp. Lamp (Any Skill)", ItemID.ANTIQUE_LAMP, 1).setRewardType(ItemReward.RewardType.CLAIMABLE),
                new ItemReward("200 Coins", ItemID.COINS_995, 200),
                new ItemReward("A Beginner Clue Scroll", ItemID.CLUE_SCROLL_BEGINNER, 1));
    }

    @Override
    public List<PanelDetails> getPanels() {
        List<PanelDetails> allSteps = new ArrayList<>();
        allSteps.add(new PanelDetails("Speak to Veos", Collections.singletonList(speakVeosLumbridge), spade));
        allSteps.add(new PanelDetails("Solve the clue scroll", Arrays.asList(digOutsideBob, digCastle, digDraynor, digMartin)));
        allSteps.add(new PanelDetails("Bring the casket to Veos", Collections.singletonList(speakVeosSarim)));
        return allSteps;
    }


}
