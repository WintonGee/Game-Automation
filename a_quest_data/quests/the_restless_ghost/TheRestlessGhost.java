package scripts.main_package.a_quest_data.quests.the_restless_ghost;

import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.*;
import scripts.main_package.a_quest_data.objects.unused.PanelDetails;
import scripts.main_package.a_quest_data.requirement.BSRequirement;
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
import scripts.raw_data.NullObjectID;
import scripts.raw_data.ObjectID;

import java.util.*;

//Winton -> Started and completed 11/10/2021
public class TheRestlessGhost extends BasicQuestHelper {

	// Items Required
	private ItemRequirement ghostspeakAmulet, skull;

	// Items Recommended
	private ItemRequirement lumbridgeTeleports, passage;

	private Requirement ghostSpawned, coffinOpened, inBasement, hasSkull;

	private QuestStep talkToAereck, talkToUrhney, speakToGhost, openCoffin, searchCoffin, enterWizardsTowerBasement,
			searchAltarAndRun, exitWizardsTowerBasement, openCoffinToPutSkullIn, putSkullInCoffin;

	// Zones
	private Zone basement;

	private static final String RESTLESS_GHOST = "Restless ghost";
	private static final String FATHER_AERECK = "Father Aereck";
	private static final String FATHER_URHNEY = "Father Urhney";

	@Override
	public LinkedHashMap<Integer, QuestStep> loadSteps() {
		setupItemRequirements();
		setupZones();
		setupConditions();
		setupSteps();
		LinkedHashMap<Integer, QuestStep> steps = new LinkedHashMap<>();

		steps.put(0, talkToAereck);
		steps.put(1, talkToUrhney);

		ConditionalStep talkToGhost = new ConditionalStep(this, openCoffin);
		talkToGhost.addStep(ghostSpawned, speakToGhost);
		talkToGhost.addStep(coffinOpened, searchCoffin);
		talkToGhost.setDescription("Talk to ghost cond step.");
		steps.put(2, talkToGhost);

		ConditionalStep getSkullForGhost = new ConditionalStep(this, enterWizardsTowerBasement);
		getSkullForGhost.addStep(inBasement, searchAltarAndRun);
		steps.put(3, getSkullForGhost);

		ConditionalStep returnSkullToGhost = new ConditionalStep(this, enterWizardsTowerBasement);
		returnSkullToGhost.addStep(new Conditions(inBasement, hasSkull), exitWizardsTowerBasement);
		returnSkullToGhost.addStep(new Conditions(hasSkull, coffinOpened), putSkullInCoffin);
		returnSkullToGhost.addStep(hasSkull, openCoffinToPutSkullIn);
		returnSkullToGhost.addStep(inBasement, searchAltarAndRun);
		steps.put(4, returnSkullToGhost);

		return steps;
	}

	public void setupZones() {
		basement = new Zone(new WorldPoint(3094, 9553, 0), new WorldPoint(3125, 9582, 0));
	}

	public void setupConditions() {
		ghostSpawned = new BSRequirement(() -> Query.npcs().nameEquals(RESTLESS_GHOST).isAny());

		coffinOpened = new BSRequirement(() -> Query.gameObjects().idEquals(15061).isAny());
		inBasement = new ZoneRequirement(basement);
		hasSkull = new VarbitRequirement(2130, 1);
	}

	public void setupItemRequirements() {
		lumbridgeTeleports = new ItemRequirement("Lumbridge teleports", ItemID.LUMBRIDGE_TELEPORT, 2);
		ghostspeakAmulet = new ItemRequirement("Ghostspeak amulet", ItemID.GHOSTSPEAK_AMULET, 1, true);
		ghostspeakAmulet.setTooltip(
				"If you've lost it you can get another from Father Urhney in his hut in the south east of Lumbridge Swamp");
		skull = new ItemRequirement("Ghost's skull", ItemID.GHOSTS_SKULL);
		skull.setTooltip("Check your bank if you don't have this item on you.");
		passage = new ItemRequirement("Necklace of passage", TeleportItemData.NECKLACE_OF_PASSAGE.getTeleportItem().getBestIdWithMinCharges(1));
//		passage.addAlternates(ItemID.NECKLACE_OF_PASSAGE1, ItemID.NECKLACE_OF_PASSAGE2, ItemID.NECKLACE_OF_PASSAGE3, ItemID.NECKLACE_OF_PASSAGE4);
	}

	public void setupSteps() {
		talkToAereck = new NpcStep(this, FATHER_AERECK, new WorldPoint(3243, 3206, 0),
				"Talk to Father Aereck in the Lumbridge Church.");
		talkToAereck.addDialogStep("I'm looking for a quest!");
		talkToAereck.addDialogStep("Ok, let me help then.");

		talkToUrhney = new NpcStep(this, FATHER_URHNEY, new WorldPoint(3147, 3175, 0),
				"Talk to Father Urhney in the south west of Lumbridge Swamp.");
		talkToUrhney.addDialogStep("Father Aereck sent me to talk to you.");
		talkToUrhney.addDialogStep("He's got a ghost haunting his graveyard.");

		openCoffin = new ObjectStep(this, ObjectID.COFFIN_2145, new WorldPoint(3250, 3193, 0),
				"Open the coffin in the Lumbridge Graveyard to spawn the ghost.", ghostspeakAmulet);
		openCoffin.addDialogStep("Yep, now tell me what the problem is.");
		openCoffin.addDialogStep("Yep, clever aren't I?.");
		openCoffin.addDialogStep("Yes, ok. Do you know WHY you're a ghost?");
		openCoffin.addDialogStep("Yes, ok. Do you know why you're a ghost?");

		searchCoffin = new ObjectStep(this, 15061, new WorldPoint(3250, 3193, 0),
				"Search the coffin in the Lumbridge Graveyard to spawn the ghost.", ghostspeakAmulet);
		searchCoffin.addDialogStep("Yep, now tell me what the problem is.");
		searchCoffin.addDialogStep("Yep, clever aren't I?.");
		searchCoffin.addDialogStep("Yes, ok. Do you know WHY you're a ghost?");
		searchCoffin.addDialogStep("Yes, ok. Do you know why you're a ghost?");

		speakToGhost = new NpcStep(this, RESTLESS_GHOST, new WorldPoint(3250, 3195, 0),
				"Speak to the Ghost that appears whilst wearing your Ghostspeak Amulet.", ghostspeakAmulet);
		speakToGhost.addDialogStep("Yep, now tell me what the problem is.");
		speakToGhost.addDialogStep("Yep, clever aren't I?.");
		speakToGhost.addDialogStep("Yes, ok. Do you know WHY you're a ghost?");
		speakToGhost.addDialogStep("Yes, ok. Do you know why you're a ghost?");

		enterWizardsTowerBasement = new ObjectStep(this, ObjectID.LADDER_2147, new WorldPoint(3104, 3162, 0),
				"Enter the Wizards' Tower basement.");
		searchAltarAndRun = new ObjectStep(this, NullObjectID.NULL_2146, new WorldPoint(3120, 9567, 0),
				"Search the Altar. A skeleton (level 13) will appear and attack you, but you can just run away.");
		exitWizardsTowerBasement = new ObjectStep(this, ObjectID.LADDER_2148, new WorldPoint(3103, 9576, 0),
				"Leave the basement.", skull);
		openCoffinToPutSkullIn = new ObjectStep(this, ObjectID.COFFIN_2145, new WorldPoint(3250, 3193, 0),
				"Open the ghost's coffin in Lumbridge graveard.", skull);
		putSkullInCoffin = new ObjectStep(this, 15061, new WorldPoint(3250, 3193, 0), "Put skull in coffin")
				.setUseItemId(skull.getId());
	}

	@Override
	public List<ItemRequirement> getItemRecommended() {
		ArrayList<ItemRequirement> recommended = new ArrayList<>();
		recommended.add(lumbridgeTeleports);
		recommended.add(passage);

		// TODO test, this should grab if have
		// skip if not since untradeable

		if (this.ghostspeakAmulet.getAsDetailedItem().isHave(true))
			recommended.add(ghostspeakAmulet);

		return recommended;
	}

	@Override
	public QuestPointReward getQuestPointReward() {
		return new QuestPointReward(1);
	}

	@Override
	public List<ExperienceReward> getExperienceRewards() {
		return Collections.singletonList(new ExperienceReward(Skill.PRAYER, 1125));
	}

	@Override
	public List<ItemReward> getItemRewards() {
		return Collections.singletonList(new ItemReward("Ghostspeak Amulet", ItemID.GHOSTSPEAK_AMULET, 1));
	}

	@Override
	public List<PanelDetails> getPanels() {
		List<PanelDetails> allSteps = new ArrayList<>();

		allSteps.add(new PanelDetails("Talk to Father Aereck", Collections.singletonList(talkToAereck)));
		allSteps.add(new PanelDetails("Get a ghostspeak amulet", Collections.singletonList(talkToUrhney)));
		allSteps.add(new PanelDetails("Talk to the ghost", Arrays.asList(openCoffin, speakToGhost)));
		allSteps.add(new PanelDetails("Return the ghost's skull", Arrays.asList(enterWizardsTowerBasement,
				searchAltarAndRun, exitWizardsTowerBasement, openCoffinToPutSkullIn, putSkullInCoffin)));
		return allSteps;
	}

	@Override
	public List<String> getCombatRequirements() {
		return Collections.singletonList("A skeleton (level 13) you can run away from");
	}

}