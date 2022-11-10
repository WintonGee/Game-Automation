package scripts.main_package.a_quest_data.quests.goblindiplomacy;

import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.*;
import scripts.main_package.a_quest_data.objects.unused.PanelDetails;
import scripts.main_package.a_quest_data.objects.unused.UnlockReward;
import scripts.main_package.a_quest_data.requirement.AnyRequirement;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.ZoneRequirement;
import scripts.main_package.a_quest_data.requirement.conditional.Conditions;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestState;
import scripts.main_package.a_quest_data.requirement.var.VarbitRequirement;
import scripts.main_package.a_quest_data.step.*;
import scripts.main_package.item_data.TeleportItemData;
import scripts.raw_data.ItemID;
import scripts.raw_data.NpcID;
import scripts.raw_data.ObjectID;

import java.util.*;

public class GoblinDiplomacy extends BasicQuestHelper {

	// Required items
	ItemRequirement goblinMailThree, orangeDye, blueDye, goblinMail, goblinMailTwo, blueArmour, orangeArmour, mailReq;

	Requirement isUpstairs, hasUpstairsArmour, hasWestArmour, hasNorthArmour;

	QuestStep talkToGeneral1, talkToGeneral2, talkToGeneral3, goUpLadder, searchUpLadder, goDownLadder, searchWestHut,
			searchBehindGenerals, dyeOrange, dyeBlue, getCrate2, getCrate3;

	// Zones
	Zone upstairs;

	@Override
	public LinkedHashMap<Integer, QuestStep> loadSteps() {
		setupItemRequirements();
		setupZones();
		setupConditions();
		setupSteps();
		LinkedHashMap<Integer, QuestStep> steps = new LinkedHashMap<>();

//		ConditionalStep lootArmour = new ConditionalStep(this, goUpLadder);
//		lootArmour.addStep(new Conditions(isUpstairs, hasUpstairsArmour), goDownLadder);
//		lootArmour.addStep(new Conditions(hasUpstairsArmour, hasWestArmour), searchBehindGenerals);
//		lootArmour.addStep(hasUpstairsArmour, searchWestHut);
//		lootArmour.addStep(isUpstairs, searchUpLadder);

		QuestStep lootArmour = new QuestStep(this, "Should not run this step, Missing armor");

		ConditionalStep prepareForQuest = new ConditionalStep(this, lootArmour);
		prepareForQuest.addStep(new Conditions(goblinMail, blueArmour), dyeOrange);
		prepareForQuest.addStep(
				new AnyRequirement(goblinMailThree, new Conditions(hasUpstairsArmour, hasWestArmour, hasNorthArmour)),
				dyeBlue);

		ConditionalStep step1 = new ConditionalStep(this, prepareForQuest);
		step1.addStep(new Conditions(goblinMail, blueArmour, orangeArmour), talkToGeneral1);

		steps.put(0, step1);
		steps.put(3, step1);

		ConditionalStep prepareBlueArmour = new ConditionalStep(this, lootArmour);
		prepareBlueArmour.addStep(
				new AnyRequirement(goblinMailTwo, new Conditions(hasUpstairsArmour, hasWestArmour, hasNorthArmour)),
				dyeBlue);

		ConditionalStep step2 = new ConditionalStep(this, prepareBlueArmour);
		step2.addStep(blueArmour, talkToGeneral2);

		steps.put(4, step2);

		ConditionalStep step3 = new ConditionalStep(this, lootArmour);
		step3.addStep(goblinMail, talkToGeneral3);

		steps.put(5, step3);

		return steps;
	}

	public void setupItemRequirements() {
		blueDye = new ItemRequirement("Blue dye", ItemID.BLUE_DYE);
		blueDye.setTooltip(
				"You can have Aggie in Draynor Village make you some for 2 woad leaves (bought from Wyson in Falador Park for 20 coins) and 5 coins.");
		blueDye.setHighlightInInventory(true);
		orangeDye = new ItemRequirement("Orange dye", ItemID.ORANGE_DYE);
		orangeDye.setTooltip(
				"This is made from red dye and yellow dye. Bring Aggie in Draynor Village 3 redberries and 5 coins for red dye, then 2 onions and 5 coins for yellow dye.");
		orangeDye.setHighlightInInventory(true);
		goblinMailThree = new ItemRequirement("Goblin mail", ItemID.GOBLIN_MAIL, 3);
		mailReq = new ItemRequirement("Goblin mail (obtainable during quest)", ItemID.GOBLIN_MAIL, 3);
		goblinMailTwo = new ItemRequirement("Goblin mail", ItemID.GOBLIN_MAIL, 2);

		goblinMail = new ItemRequirement("Goblin mail", ItemID.GOBLIN_MAIL);
		goblinMail.setTooltip("You can get goblin mail by killing goblins around goblin village.");
		goblinMail.setHighlightInInventory(true);

		blueArmour = new ItemRequirement("Blue goblin mail", ItemID.BLUE_GOBLIN_MAIL);
		orangeArmour = new ItemRequirement("Orange goblin mail", ItemID.ORANGE_GOBLIN_MAIL);
	}

	public void setupConditions() {
		isUpstairs = new ZoneRequirement(upstairs);
		hasUpstairsArmour = new VarbitRequirement(2381, 1);
		hasWestArmour = new VarbitRequirement(2380, 1);
		hasNorthArmour = new VarbitRequirement(2379, 1);
	}

	public void setupZones() {
		upstairs = new Zone(new WorldPoint(2952, 3495, 2), new WorldPoint(2959, 3498, 2));
	}

	public void setupSteps() {
		goUpLadder = new ObjectStep(this, ObjectID.LADDER_16450, new WorldPoint(2954, 3497, 0),
				"You need three goblin mails, which you can find around the Goblin "
						+ "Village. The first is up the ladder in a crate in the south of the village.");
		searchUpLadder = new ObjectStep(this, ObjectID.CRATE_16561, new WorldPoint(2955, 3498, 2),
				"Search the crate up the ladder.");
		goUpLadder.addSubSteps(searchUpLadder);
		goDownLadder = new ObjectStep(this, ObjectID.LADDER_16556, new WorldPoint(2954, 3497, 2),
				"Go back down the ladder.");
		searchWestHut = new ObjectStep(this, ObjectID.CRATE_16560, new WorldPoint(2951, 3508, 0),
				"Search the crate in the west of Goblin Village for Goblin Mail.");
//		getCrate2 = new DetailedQuestStep(this, "The second goblin mail can be found in the west hut in a crate.");
//		getCrate2.addSubSteps(goDownLadder, searchWestHut);

		searchBehindGenerals = new ObjectStep(this, ObjectID.CRATE_16559, new WorldPoint(2959, 3514, 0),
				"Search the crate north of the General's hut in Goblin Village.");
//		getCrate3 = new DetailedQuestStep(this, "The last goblin mail is north of the generals' hut in a crate.");
//		getCrate3.addSubSteps(searchBehindGenerals);

//		dyeBlue = new DetailedQuestStep(this, "Use the blue dye on one of the goblin mail.", blueDye, goblinMail);
//		dyeOrange = new DetailedQuestStep(this, "Use the orange dye on one of the goblin mail.", orangeDye, goblinMail);

		dyeBlue = new InventoryStep(this, "Use the blue dye on one of the goblin mail.", goblinMail).setUseItem(blueDye);
		dyeOrange = new InventoryStep(this, "Use the orange dye on one of the goblin mail.", goblinMail).setUseItem(orangeDye);

		talkToGeneral1 = new NpcStep(this, NpcID.GENERAL_BENTNOZE, new WorldPoint(2958, 3512, 0),
				"Talk to one of the Goblin Generals in Goblin Village.", orangeArmour);
		talkToGeneral1.addDialogStep("So how is life for the goblins?");
		talkToGeneral1.addDialogStep("Yes, Wartface looks fat");
		talkToGeneral1.addDialogStep("Do you want me to pick an armour colour for you?");
		talkToGeneral1.addDialogStep("What about a different colour?");
		talkToGeneral1.addDialogStep("I have some orange armour here");

		talkToGeneral2 = new NpcStep(this, NpcID.GENERAL_BENTNOZE, new WorldPoint(2958, 3512, 0),
				"Talk to one of the Goblin Generals in Goblin Village again.", blueArmour);
		talkToGeneral2.addDialogStep("So how is life for the goblins?");
		talkToGeneral2.addDialogStep("I have some blue armour here");

		talkToGeneral3 = new NpcStep(this, NpcID.GENERAL_BENTNOZE, new WorldPoint(2958, 3512, 0),
				"Talk to one of the Goblin Generals in Goblin Village once more.", goblinMail);
		talkToGeneral3.addDialogStep("So how is life for the goblins?");
		talkToGeneral3.addDialogStep("Yes, Wartface looks fat");
		talkToGeneral3.addDialogStep("I have some brown armour here");
	}

	@Override
	public List<ItemRequirement> getItemRequirements() {
		ArrayList<ItemRequirement> reqs = new ArrayList<>();
		reqs.add(new ItemRequirement("Faldor teleport", ItemID.FALADOR_TELEPORT));
		reqs.add(blueDye);
		reqs.add(orangeDye);
		reqs.add(mailReq);
		return reqs;
	}

	@Override
	public QuestPointReward getQuestPointReward() {
		return new QuestPointReward(5);
	}

	@Override
	public List<ExperienceReward> getExperienceRewards() {
		return Collections.singletonList(new ExperienceReward(Skill.CRAFTING, 200));
	}

	@Override
	public List<ItemReward> getItemRewards() {
		return Collections.singletonList(new ItemReward("A Gold Bar", ItemID.GOLD_BAR, 1));
	}

	@Override
	public List<PanelDetails> getPanels() {
		List<PanelDetails> allSteps = new ArrayList<>();
		PanelDetails getArmours = new PanelDetails("Prepare goblin mail",
				Arrays.asList(goUpLadder, getCrate2, getCrate3, dyeBlue, dyeOrange), blueDye, orangeDye);
		allSteps.add(getArmours);

		allSteps.add(
				new PanelDetails("Present the armours", Arrays.asList(talkToGeneral1, talkToGeneral2, talkToGeneral3)));
		return allSteps;
	}
}