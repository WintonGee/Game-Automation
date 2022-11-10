package scripts.main_package.a_quest_data.quests.romeo_and_juliet;

import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.*;
import scripts.main_package.a_quest_data.objects.unused.PanelDetails;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.ZoneRequirement;
import scripts.main_package.a_quest_data.requirement.conditional.Conditions;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestState;
import scripts.main_package.a_quest_data.step.*;
import scripts.main_package.item_data.TeleportItemData;
import scripts.raw_data.ItemID;
import scripts.raw_data.ObjectID;

import java.util.*;

public class RomeoAndJuliet extends BasicQuestHelper {
	private static final String JULIET = "Juliet";


	// Items Required
	ItemRequirement cadavaBerry, letter, potion, varrockTele_5;

	Requirement inJulietRoom;

	QuestStep talkToRomeo, goUpToJuliet, talkToJuliet, giveLetterToRomeo, talkToLawrence, talkToApothecary,
			goUpToJuliet2, givePotionToJuliet, finishQuest;

	// Zones
	Zone julietRoom;

	@Override
	public LinkedHashMap<Integer, QuestStep> loadSteps() {
		setupItemRequirements();
		setupZones();
		setupConditions();
		setupSteps();
		LinkedHashMap<Integer, QuestStep> steps = new LinkedHashMap<>();

		steps.put(0, talkToRomeo);

		ConditionalStep tellJulietAboutRomeo = new ConditionalStep(this, goUpToJuliet);
		tellJulietAboutRomeo.addStep(inJulietRoom, talkToJuliet);

		steps.put(10, tellJulietAboutRomeo);
		steps.put(20, giveLetterToRomeo);
		steps.put(30, talkToLawrence);
		steps.put(40, talkToApothecary);

		ConditionalStep bringPotionToJuliet = new ConditionalStep(this, talkToApothecary);
		bringPotionToJuliet.addStep(new Conditions(potion, inJulietRoom), givePotionToJuliet);
		bringPotionToJuliet.addStep(potion, goUpToJuliet2);

		steps.put(50, bringPotionToJuliet);
		steps.put(60, finishQuest);

		return steps;
	}

	public void setupItemRequirements() {
		cadavaBerry = new ItemRequirement("Cadava berries", ItemID.CADAVA_BERRIES);
		cadavaBerry.setTooltip("You can pick some from bushes south east of Varrock");
		letter = new ItemRequirement("Message", ItemID.MESSAGE);
		letter.setTooltip("You can get another from Juliet");
		potion = new ItemRequirement("Cadava potion", ItemID.CADAVA_POTION);
		
		varrockTele_5 = new ItemRequirement("Varrock teleport", ItemID.VARROCK_TELEPORT, 5);
	}

	public void setupConditions() {
		inJulietRoom = new ZoneRequirement(julietRoom);
	}

	public void setupZones() {
		julietRoom = new Zone(new WorldPoint(3147, 3425, 1), new WorldPoint(3166, 3443, 1));
	}

	public void setupSteps() {
		talkToRomeo = new NpcStep(this, "Romeo", new WorldPoint(3211, 3422, 0), "Talk to Romeo in Varrock Square.");
		talkToRomeo.addDialogStep("Yes, I have seen her actually!");
		talkToRomeo.addDialogStep("Yes, ok, I'll let her know.");
		talkToRomeo.addDialogStep("Ok, thanks.");
		goUpToJuliet = new ObjectStep(this, ObjectID.STAIRCASE_11797, new WorldPoint(3161, 3435, 0),
				"Talk to Juliet in the house west of Varrock.");
		goUpToJuliet.addDialogStep("Ok, thanks.");
		talkToJuliet = new NpcStep(this, JULIET, new WorldPoint(3158, 3426, 1),
				"Talk to Juliet in the house west of Varrock.");
		talkToJuliet.addSubSteps(goUpToJuliet);

		giveLetterToRomeo = new NpcStep(this, "Romeo", new WorldPoint(3211, 3422, 0),
				"Bring the letter to Romeo in Varrock Square.", letter);
		talkToLawrence = new NpcStep(this, "Father Lawrence", new WorldPoint(3254, 3483, 0),
				"Talk to Father Lawrence in north east Varrock.").setChatAttempts(2);
		talkToLawrence.addDialogStep("Ok, thanks.");
		talkToApothecary = new NpcStep(this, "Apothecary", new WorldPoint(3195, 3405, 0),
				"Bring the Apothecary cadava berries in south west Varrock.", cadavaBerry);
		talkToApothecary.addDialogStep("Talk about something else.");
		talkToApothecary.addDialogStep("Talk about Romeo & Juliet.");
		goUpToJuliet2 = new ObjectStep(this, ObjectID.STAIRCASE_11797, new WorldPoint(3161, 3435, 0),
				"Bring the potion to Juliet in the house west of Varrock.", potion);
		givePotionToJuliet = new NpcStep(this, JULIET, new WorldPoint(3158, 3426, 1),
				"Bring the potion to Juliet in the house west of Varrock.", potion);
		givePotionToJuliet.addSubSteps(goUpToJuliet2);

		finishQuest = new NpcStep(this, "Romeo", new WorldPoint(3211, 3422, 0),
				"Talk to Romeo in Varrock Square to finish the quest.");

	}

	@Override
	public List<ItemRequirement> getItemRequirements() {
		ArrayList<ItemRequirement> reqs = new ArrayList<>();
		reqs.add(cadavaBerry);
		reqs.add(varrockTele_5);
		return reqs;
	}

	@Override
	public QuestPointReward getQuestPointReward() {
		return new QuestPointReward(5);
	}

	@Override
	public List<PanelDetails> getPanels() {
		List<PanelDetails> allSteps = new ArrayList<>();

		allSteps.add(new PanelDetails("Helping Romeo", Arrays.asList(talkToRomeo, talkToJuliet, giveLetterToRomeo)));
		allSteps.add(new PanelDetails("Hatching a plan", Arrays.asList(talkToLawrence, talkToApothecary), cadavaBerry));
		allSteps.add(new PanelDetails("Enact the plan", Arrays.asList(givePotionToJuliet, finishQuest)));
		return allSteps;
	}
}
