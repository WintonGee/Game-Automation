package scripts.main_package.a_quest_data.execution;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.GrandExchangeOffer;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.tasks.bank_task.DetailedBankTask;
import scripts.main_package.tasks.ge_task.GeTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// This class is used for getting the items needed for the quest.
// - Includes quest items
// - Includes ge items.
public class QuestStartingItemsHandler {

    private final int ATTEMPTS = 10;
    private final BasicQuestHelper basicQuestHelper;

    List<ItemRequirement> itemRequirements;
    List<ItemRequirement> itemRecommendations;

    DetailedBankTask mainBankTask;

    public QuestStartingItemsHandler(BasicQuestHelper basicQuestHelper) {
        this.basicQuestHelper = basicQuestHelper;

        itemRequirements = basicQuestHelper.getItemRequirements();
        itemRecommendations = this.basicQuestHelper.getItemRecommended();

        mainBankTask = this.getBankTask().setAllowExtraSets(false);
    }

    public boolean handle() {
        for (int i = 0; i < ATTEMPTS; i++) {
            Log.info("[QuestStartingItemsHandler] Starting attempt: " + i);
            if (isHaveAllItems() && isInventorySet()) {
                Log.debug("[QuestStartingItemsHandler] All items ready for questing!");
                return true;
            }

            if (!handleQuestItems()) {
                Log.warn("[QuestStartingItemsHandler] Failed to handle quest items.");
                continue;
            }

            if (!handleTradableItems()) {
                Log.warn("[QuestStartingItemsHandler] Failed to handle buyable items.");
                continue;
            }

            if (handleStartingInventory())
                Log.info("[QuestStartingItemsHandler] Inventory setup successful!");
        }
        return false;
    }

    private boolean isHaveAllItems() {
        return isHaveQuestItems() && isHaveTradableItems();
    }

    //TODO need to implement the quest item object
    private boolean isHaveQuestItems() {
        return true;
    }

    private boolean isHaveTradableItems() {
        if (itemRequirements == null) {
            Log.info("[QuestStartingItemsHandler] No tradable items needed for quest!");
            return true;
        }

        // Supports the alternative items
        return itemRequirements.stream().map(ItemRequirement::getAsDetailedItem).allMatch(i -> i.isHave(true));
    }

    private boolean isInventorySet() {
        return mainBankTask.isSatisfied();
    }

    private boolean handleQuestItems() {
        return true;
    }

    private boolean handleTradableItems() {
        ArrayList<DetailedItem> list = itemRequirements.stream()
                .map(ItemRequirement::getAsDetailedItem)
                .collect(Collectors.toCollection(ArrayList::new));
        GeTask geTask = new GeTask();
        geTask.addItems(list, GrandExchangeOffer.Type.BUY);
        return geTask.execute();
    }

    private boolean handleStartingInventory() {
        return mainBankTask.handle();
    }

    private DetailedBankTask getBankTask() {

        // For quests that require over 28 slots
        List<ItemRequirement> overriddenList = this.basicQuestHelper.getStartingItemRequirements();
        if (overriddenList != null) {
            ArrayList<DetailedItem> overriddenItems = overriddenList.stream()
                    .map(ItemRequirement::getAsDetailedItem)
                    .collect(Collectors.toCollection(ArrayList::new));
            DetailedBankTask mainBankTask = new DetailedBankTask(overriddenItems);
            mainBankTask.update();
            return mainBankTask;
        }

        ArrayList<DetailedItem> list = new ArrayList<DetailedItem>();

        // Adding Required Items
        if (itemRequirements != null) {
            list.addAll(itemRequirements.stream().map(ItemRequirement::getAsDetailedItem).collect(Collectors.toList()));
        }

        // Adding Recommended Items, TODO check which to not add
        if (itemRecommendations != null) {
            list.addAll(itemRecommendations.stream().map(ItemRequirement::getAsDetailedItem).collect(Collectors.toList()));
        }

        //TODO add quest items


        DetailedBankTask mainBankTask = new DetailedBankTask(list);
        mainBankTask.update();

        return mainBankTask;
    }


}
