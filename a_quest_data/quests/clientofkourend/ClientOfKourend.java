package scripts.main_package.a_quest_data.quests.clientofkourend;

import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.ItemReward;
import scripts.main_package.a_quest_data.objects.QuestPointReward;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.objects.unused.PanelDetails;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.conditional.Conditions;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestState;
import scripts.main_package.a_quest_data.requirement.var.VarbitRequirement;
import scripts.main_package.a_quest_data.step.*;
import scripts.main_package.item_data.TeleportItemData;
import scripts.raw_data.ItemID;
import scripts.raw_data.NpcID;

import java.util.*;

public class ClientOfKourend extends BasicQuestHelper {
    //Items Required
    ItemRequirement feather;

    ItemRequirement glory, stamina;

    //Other items used
    ItemRequirement enchantedScroll, enchantedQuill, mysteriousOrb;

    Requirement talkedToLeenz, talkedToHorace, talkedToJennifer, talkedToMunty, talkedToRegath;

    QuestStep talkToVeos, useFeatherOnScroll, talkToLeenz, talkToHorace, talkToJennifer, talkToMunty, talkToRegath, returnToVeos, goToAltar, finishQuest;

    @Override
    public Map<Integer, QuestStep> loadSteps() {
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToVeos);

        ConditionalStep makeEnchantedQuill = new ConditionalStep(this, talkToVeos, "Make Quill Conditional Step");
        makeEnchantedQuill.addStep(new Conditions(enchantedQuill, talkedToLeenz, talkedToRegath, talkedToMunty, talkedToJennifer), talkToHorace);
        makeEnchantedQuill.addStep(new Conditions(enchantedQuill, talkedToLeenz, talkedToRegath, talkedToMunty), talkToJennifer);
        makeEnchantedQuill.addStep(new Conditions(enchantedQuill, talkedToLeenz, talkedToRegath), talkToMunty);
        makeEnchantedQuill.addStep(new Conditions(enchantedQuill, talkedToLeenz), talkToRegath);
        makeEnchantedQuill.addStep(new Conditions(enchantedQuill), talkToLeenz);
        makeEnchantedQuill.addStep(enchantedScroll, useFeatherOnScroll);
        steps.put(1, makeEnchantedQuill);

        steps.put(2, returnToVeos);

        ConditionalStep takeOrbToAltar = new ConditionalStep(this, returnToVeos);
        takeOrbToAltar.addStep(mysteriousOrb, goToAltar);

        steps.put(3, returnToVeos);

        steps.put(4, takeOrbToAltar);

        steps.put(5, finishQuest);
        steps.put(6, finishQuest);

        return steps;
    }

    public void setupItemRequirements() {
        glory = new ItemRequirement("Glory", TeleportItemData.AMULET_OF_GLORY.getTeleportItem().getBestIdWithMinCharges(5));
        stamina = new ItemRequirement(ItemID.STAMINA_POTION4);

        feather = new ItemRequirement("Feather", ItemID.FEATHER);
        feather.addAlternates(ItemID.BLUE_FEATHER, ItemID.ORANGE_FEATHER, ItemID.RED_FEATHER, ItemID.YELLOW_FEATHER,
                ItemID.EAGLE_FEATHER, ItemID.STRIPY_FEATHER);
        feather.setHighlightInInventory(true);
        enchantedScroll = new ItemRequirement("Enchanted scroll", ItemID.ENCHANTED_SCROLL);
        enchantedScroll.setHighlightInInventory(true);
        mysteriousOrb = new ItemRequirement("Mysterious orb", ItemID.MYSTERIOUS_ORB);
        mysteriousOrb.setHighlightInInventory(true);

        enchantedQuill = new ItemRequirement("Enchanted quill", ItemID.ENCHANTED_QUILL);
    }

    public void setupConditions() {
        talkedToLeenz = new VarbitRequirement(5620, 1);
        talkedToRegath = new VarbitRequirement(5621, 1);
        talkedToMunty = new VarbitRequirement(5622, 1);
        talkedToJennifer = new VarbitRequirement(5623, 1);
        talkedToHorace = new VarbitRequirement(5624, 1);
    }

    public void setupSteps() {
        talkToVeos = new NpcStep(this, "Veos", new WorldPoint(1824, 3690, 0), "Talk to Veos on the Port Piscarilius docks. You can travel to him by talking to Veos in Port Sarim.");
        talkToVeos.addDialogStep("Sounds interesting! How can I help?");
        talkToVeos.addDialogStep("Can you take me to Great Kourend?");
        talkToVeos.addDialogStep("Have you got any quests for me?");
        talkToVeos.addDialogStep("Let's talk about your client...");
        talkToVeos.addDialogStep("I've lost something you've given me.");

        useFeatherOnScroll = new CombineStep(this, "Use a feather on the Enchanted Scroll", feather, enchantedScroll);

        talkToLeenz = new NpcStep(this, NpcID.LEENZ, new WorldPoint(1807, 3726, 0), "Talk to Leenz in Port Piscarilius general store.", enchantedQuill);
        talkToLeenz.addDialogStep("Can I ask you about Port Piscarilius?");
        talkToLeenz.addDialogStep("Why should I gain favour with Port Piscarilius?");
        talkToHorace = new NpcStep(this, NpcID.HORACE, new WorldPoint(1774, 3589, 0), "Talk to Horace in the Hosidius general store.", enchantedQuill);
        talkToHorace.addDialogStep("Can I ask you about Hosidius?");
        talkToHorace.addDialogStep("Why should I gain favour with Hosidius?");
        talkToJennifer = new NpcStep(this, NpcID.JENNIFER, new WorldPoint(1519, 3588, 0), "Talk to Jennifer in Shayzien general store.", enchantedQuill);
        talkToJennifer.addDialogStep("Can I ask you about Shayzien?");
        talkToJennifer.addDialogStep("Why should I gain favour with Shayzien?");
        talkToMunty = new NpcStep(this, NpcID.MUNTY, new WorldPoint(1551, 3752, 0), "Talk to Munty in Lovakengj general store.", enchantedQuill);
        talkToMunty.addDialogStep("Can I ask you about Lovakengj?");
        talkToMunty.addDialogStep("Why should I gain favour with Lovakengj?");
        talkToRegath = new NpcStep(this, NpcID.REGATH, new WorldPoint(1720, 3724, 0), "Talk to Regath in Arceuus general store.", enchantedQuill);
        talkToRegath.addDialogStep("Can I ask you about Arceuus?");
        talkToRegath.addDialogStep("Why should I gain favour with Arceuus?");

        returnToVeos = new NpcStep(this, "Veos", new WorldPoint(1824, 3690, 0), "Return to Veos on Piscarilius docks.");
        returnToVeos.addDialogStep("Let's talk about your client...");
        returnToVeos.addDialogStep("I've lost something you've given me.");
        goToAltar = new InventoryStep(this, new WorldPoint(1712, 3883, 0), "Activate the mysterious orb at the Dark Altar. You can either run there through Arceuus, teleport to Wintertodt with the Games Necklace and run south, or teleport straight there on the Arceuus spellbook.", mysteriousOrb);

        finishQuest = new NpcStep(this, "Veos", new WorldPoint(1824, 3690, 0), "Return to Veos on Piscarilius docks.");
        finishQuest.addDialogStep("Let's talk about your client...");
    }

    @Override
    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(glory);
        reqs.add(stamina);
        reqs.add(feather);

        //TODO add games necklace min 1 charge
        reqs.add(new ItemRequirement(TeleportItemData.GAMES_NECKLACE.getTeleportItem().getBestIdWithMinCharges(1)));
        return reqs;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        List<Requirement> reqs = new ArrayList<>();
        reqs.add(new QuestRequirement(QuestHelperQuest.X_MARKS_THE_SPOT, QuestState.FINISHED));
        return reqs;
    }

    @Override
    public QuestPointReward getQuestPointReward() {
        return new QuestPointReward(1);
    }

    @Override
    public List<ItemReward> getItemRewards() {
        return Arrays.asList(
                new ItemReward("2 x 500 Experience Lamps (Any Skill)", ItemID.ANTIQUE_LAMP_21262, 2).setClaimable(),
                new ItemReward("20% Kourend Favour Certificate", ItemID.KOUREND_FAVOUR_CERTIFICATE, 1).setClaimable()
                        .addChatOption("Shayzien", "Lovakengj", "Piscarilius", "Hosidius", "Arceuus"),
                new ItemReward("Kharedst's Memoirs", ItemID.KHAREDSTS_MEMOIRS, 1));
    }

    @Override
    public List<PanelDetails> getPanels() {
        List<PanelDetails> allSteps = new ArrayList<>();

        allSteps.add(new PanelDetails("Starting off", Arrays.asList(talkToVeos, useFeatherOnScroll), feather));
        allSteps.add(new PanelDetails("Learn about Kourend", Arrays.asList(talkToLeenz, talkToRegath, talkToMunty, talkToJennifer, talkToHorace, returnToVeos)));
        allSteps.add(new PanelDetails("The Dark Altar", Arrays.asList(goToAltar, finishQuest)));
        return allSteps;
    }
}
