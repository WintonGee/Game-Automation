package scripts.main_package.a_quest_data.quests.druidic_ritual;

import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.*;
import scripts.main_package.a_quest_data.objects.unused.PanelDetails;
import scripts.main_package.a_quest_data.objects.unused.UnlockReward;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.ZoneRequirement;
import scripts.main_package.a_quest_data.requirement.conditional.Conditions;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestState;
import scripts.main_package.a_quest_data.step.*;
import scripts.main_package.item_data.TeleportItemData;
import scripts.raw_data.ItemID;
import scripts.raw_data.NpcID;
import scripts.raw_data.ObjectID;

import java.util.*;

public class DruidicRitual extends BasicQuestHelper {

	// Items Required
	ItemRequirement rawRat, rawBear, rawBeef, rawChicken, rawRatHighlighted, rawBearHighlighted, rawBeefHighlighted,
			rawChickenHighlighted, enchantedBear, enchantedBeef, enchantedChicken, enchantedRat;

	Requirement inDungeon, inSanfewRoom;

	QuestStep talkToKaqemeex, goUpToSanfew, talkToSanfew, enterDungeon, enchantMeats, useRatOnCauldron,
			useBeefOnCauldron, useBearOnCauldron, useChickenOnCauldron, goUpToSanfewWithMeat, talkToSanfewWithMeat,
			talkToKaqemeexToFinish;

	// Zones
	Zone dungeon, sanfewRoom;

	@Override
	public LinkedHashMap<Integer, QuestStep> loadSteps() {
		loadZones();
		setupItemRequirements();
		setupConditions();
		setupSteps();
		LinkedHashMap<Integer, QuestStep> steps = new LinkedHashMap<>();

		steps.put(0, talkToKaqemeex);

		ConditionalStep goTalkToSanfew = new ConditionalStep(this, goUpToSanfew);
		goTalkToSanfew.addStep(inSanfewRoom, talkToSanfew);
		steps.put(1, goTalkToSanfew);

		ConditionalStep prepareMeats = new ConditionalStep(this, enterDungeon);
		prepareMeats.addStep(new Conditions(inSanfewRoom, enchantedRat, enchantedBear, enchantedBeef, enchantedChicken),
				talkToSanfewWithMeat);
		prepareMeats.addStep(new Conditions(enchantedRat, enchantedBear, enchantedBeef, enchantedChicken),
				goUpToSanfewWithMeat);
		prepareMeats.addStep(new Conditions(inDungeon, enchantedRat, enchantedBear, enchantedBeef),
				useChickenOnCauldron);
		prepareMeats.addStep(new Conditions(inDungeon, enchantedRat, enchantedBear), useBeefOnCauldron);
		prepareMeats.addStep(new Conditions(inDungeon, enchantedRat), useBearOnCauldron);
		prepareMeats.addStep(inDungeon, useRatOnCauldron);
		steps.put(2, prepareMeats);

		steps.put(3, talkToKaqemeexToFinish);

		return steps;
	}

	public void setupItemRequirements() {
		rawRat = new ItemRequirement("Raw rat meat", ItemID.RAW_RAT_MEAT);
//		rawRat.addAlternates(ItemID.ENCHANTED_RAT);
		rawBear = new ItemRequirement("Raw bear meat", ItemID.RAW_BEAR_MEAT);
//		rawBear.addAlternates(ItemID.ENCHANTED_BEAR);
		rawBeef = new ItemRequirement("Raw beef", ItemID.RAW_BEEF);
//		rawBeef.addAlternates(ItemID.ENCHANTED_BEEF);
		rawChicken = new ItemRequirement("Raw chicken", ItemID.RAW_CHICKEN);
//		rawChicken.addAlternates(ItemID.ENCHANTED_CHICKEN);

		rawRatHighlighted = new ItemRequirement("Raw rat meat", ItemID.RAW_RAT_MEAT);
		rawRatHighlighted.setHighlightInInventory(true);
		rawBearHighlighted = new ItemRequirement("Raw bear meat", ItemID.RAW_BEAR_MEAT);
		rawBearHighlighted.setHighlightInInventory(true);
		rawBeefHighlighted = new ItemRequirement("Raw beef", ItemID.RAW_BEEF);
		rawBeefHighlighted.setHighlightInInventory(true);
		rawChickenHighlighted = new ItemRequirement("Raw chicken", ItemID.RAW_CHICKEN);
		rawChickenHighlighted.setHighlightInInventory(true);

		enchantedBear = new ItemRequirement("Enchanted bear", ItemID.ENCHANTED_BEAR);
		enchantedBeef = new ItemRequirement("Enchanted beef", ItemID.ENCHANTED_BEEF);
		enchantedChicken = new ItemRequirement("Enchanted chicken", ItemID.ENCHANTED_CHICKEN);
		enchantedRat = new ItemRequirement("Enchanted rat", ItemID.ENCHANTED_RAT);
	}

	public void loadZones() {
		sanfewRoom = new Zone(new WorldPoint(2893, 3423, 1), new WorldPoint(2903, 3433, 1));
		dungeon = new Zone(new WorldPoint(2816, 9668, 0), new WorldPoint(2973, 9855, 0));
	}

	public void setupConditions() {
		inSanfewRoom = new ZoneRequirement(sanfewRoom);
		inDungeon = new ZoneRequirement(dungeon);
	}

	public void setupSteps() {
		talkToKaqemeex = new NpcStep(this, NpcID.KAQEMEEX, new WorldPoint(2925, 3486, 0),
				"Talk to Kaqemeex in the Druid Circle in Taverley.");
		talkToKaqemeex.addDialogSteps("I'm in search of a quest.", "Okay, I will try and help.");
		goUpToSanfew = new ObjectStep(this, ObjectID.STAIRCASE_16671, new WorldPoint(2899, 3429, 0),
				"Talk to Sanfew upstairs in the Taverley herblore store.");
		talkToSanfew = new NpcStep(this, NpcID.SANFEW, new WorldPoint(2899, 3429, 1),
				"Talk to Sanfew upstairs in the Taverley herblore store.");
		talkToSanfew.addDialogStep("I've been sent to help purify the Varrock stone circle.");
		talkToSanfew.addSubSteps(goUpToSanfew);

		enterDungeon = new ObjectStep(this, ObjectID.LADDER_16680, new WorldPoint(2884, 3397, 0),
				"Enter Taverley Dungeon south of Taverley.", rawBear, rawBeef, rawChicken, rawRat);
		enterDungeon.addDialogStep("Ok, I'll do that then.");
		useRatOnCauldron = new ObjectStep(this, ObjectID.CAULDRON_OF_THUNDER, new WorldPoint(2893, 9831, 0),
				"Use the rat meat on the cauldron. To enter the room, spam-click the gate to get in.",
				rawRatHighlighted).setUseItem(rawRat); //
		useRatOnCauldron.addIcon(ItemID.RAW_RAT_MEAT);
		useBeefOnCauldron = new ObjectStep(this, ObjectID.CAULDRON_OF_THUNDER, new WorldPoint(2893, 9831, 0),
				"Use the beef meat on the cauldron. To enter the room, spam-click the gate to get in.",
				rawBeefHighlighted).setUseItem(rawBeef);
		useBeefOnCauldron.addIcon(ItemID.RAW_BEEF);
		useBearOnCauldron = new ObjectStep(this, ObjectID.CAULDRON_OF_THUNDER, new WorldPoint(2893, 9831, 0),
				"Use the bear meat on the cauldron. To enter the room, spam-click the gate to get in.",
				rawBearHighlighted).setUseItem(rawBear);
		useBearOnCauldron.addIcon(ItemID.RAW_BEAR_MEAT);
		useChickenOnCauldron = new ObjectStep(this, ObjectID.CAULDRON_OF_THUNDER, new WorldPoint(2893, 9831, 0),
				"Use the chicken meat on the cauldron. To enter the room, spam-click the gate to get in.",
				rawChickenHighlighted).setUseItem(rawChicken);
		useChickenOnCauldron.addIcon(ItemID.RAW_CHICKEN);
		enchantMeats = new ObjectStep(this, ObjectID.CAULDRON_OF_THUNDER, new WorldPoint(2893, 9831, 0),
				"Use the four meats on the cauldron. To enter the room, spam-click the gate to get in.");
		enchantMeats.addSubSteps(useRatOnCauldron, useChickenOnCauldron, useBeefOnCauldron, useBearOnCauldron);

		goUpToSanfewWithMeat = new ObjectStep(this, ObjectID.STAIRCASE_16671, new WorldPoint(2899, 3429, 0),
				"Bring the enchanted meats to Sanfew upstairs in the Taverley herblore store.", enchantedBear,
				enchantedBeef, enchantedChicken, enchantedRat);
		talkToSanfewWithMeat = new NpcStep(this, NpcID.SANFEW, new WorldPoint(2899, 3429, 1),
				"Bring the enchanted meats to Sanfew upstairs in the Taverley herblore store.", enchantedBear,
				enchantedBeef, enchantedChicken, enchantedRat);
		talkToSanfewWithMeat.addSubSteps(goUpToSanfewWithMeat);
		talkToKaqemeexToFinish = new NpcStep(this, NpcID.KAQEMEEX, new WorldPoint(2925, 3486, 0),
				"Return to Kaqemeex in the Druid Circle to finish the quest.");
	}

	@Override
	public List<ItemRequirement> getItemRequirements() {
		return Arrays.asList(rawBear, rawBeef, rawChicken, rawRat);
	}

	@Override
	public QuestPointReward getQuestPointReward() {
		return new QuestPointReward(4);
	}

	@Override
	public List<ExperienceReward> getExperienceRewards() {
		return Collections.singletonList(new ExperienceReward(Skill.HERBLORE, 250));
	}

	@Override
	public List<UnlockReward> getUnlockRewards() {
		return Collections.singletonList(new UnlockReward("Access to the Herblore Skill"));
	}

	@Override
	public List<PanelDetails> getPanels() {
		List<PanelDetails> allSteps = new ArrayList<>();
		allSteps.add(new PanelDetails("Helping the druids", Arrays.asList(talkToKaqemeex, talkToSanfew, enterDungeon,
				enchantMeats, talkToSanfewWithMeat, talkToKaqemeexToFinish), rawBear, rawBeef, rawChicken, rawRat));

		return allSteps;
	}
}