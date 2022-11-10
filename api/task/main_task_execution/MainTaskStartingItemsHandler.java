package scripts.main_package.api.task.main_task_execution;

import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.GrandExchangeOffer;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.item.equipment.EquipmentItem;
import scripts.main_package.tasks.ge_task.GeTask;

import java.util.ArrayList;
import java.util.stream.Collectors;

// Make sure to have the items needed for the task
// This will get all untradeable items and grand exchange buyable items
public class MainTaskStartingItemsHandler {

    MainTask mainTask;

    public MainTaskStartingItemsHandler(MainTask mainTask) {
        this.mainTask = mainTask;
    }

    // handle getting buying the starting items
    // loop
    // - Banking?
    // - Execution?

    // or just copy the questing one

    public boolean handle() {
        if (mainTask.skipGeBuying && !isHaveItems()) {
            Log.error("[MainTaskStartingItemsHandler] Do not have enough items for task: " + mainTask.getActivityName() + ", and GE buying is disabled.");
            return false;
        }

        //TEMP TODO (add sellables?)
        int liquidWealth = Utils.getTotalItemCount(995);

        val multiSetItems = getMultiSetItems();
        val monoSetItems = getMonoSetItems();

        // Increase 10% to ensure have enough gold
        int setCostMultiItems = (int) Math.floor(getSetCost(multiSetItems) * 1.1);
        int setCostMonoItems = (int) Math.floor(getSetCost(monoSetItems));

        int maxSetsBuyable = getMaxSetsBuyableWithCurrentWealth(liquidWealth, setCostMonoItems, setCostMultiItems);
        int setsToBuy = Math.min(maxSetsBuyable, mainTask.maxItemSets); //TODO do some randomization and seeding? swap maxItemSets
        boolean canBuyEnoughSets = setsToBuy >= mainTask.minItemSets;

        if (!canBuyEnoughSets) {
            Log.error("[MainTaskStartingItemsHandler] Not enough wealth to buy items, Max Sets Buyable: "
                    + maxSetsBuyable + ", Min Sets Needed: " + mainTask.minItemSets);
            return false;
        }

        Log.info("[MainTaskStartingItemsHandler] Number of item sets to buy: " + setsToBuy);
        ArrayList<DetailedItem> minItems = new ArrayList<DetailedItem>(monoSetItems);
        ArrayList<DetailedItem> buyItems = new ArrayList<DetailedItem>(monoSetItems);

        multiSetItems.forEach(o -> {
            val minCopy = o.getCopy();
            val maxCopy = o.getCopy();

            int quantity = o.getQuantity();
            int minQuantity = quantity * mainTask.minItemSets;
            int maxQuantity = quantity * setsToBuy;

            minCopy.setQuantity(minQuantity);
            maxCopy.setQuantity(maxQuantity);

            minItems.add(minCopy);
            buyItems.add(maxCopy);
        });


        GeTask geTask = new GeTask();
        geTask.addItems(buyItems, GrandExchangeOffer.Type.BUY);
//		geTask.addItems(sellItems, Type.SELL); //TODO
        geTask.execute();

        return minItems.stream().allMatch(o -> o.isHave(true));
    }

    private boolean isHaveItems() {
        return false;
    }

    // Items to buy multiple sets of
    // Example: Materials - Uncut gems for crafting, etc...
    private ArrayList<DetailedItem> getMultiSetItems() {
        return mainTask.inventoryItems
                .stream()
                .filter(DetailedItem::isDepletes)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Items to buy single sets of
    // Example: Armor, Tools
    private ArrayList<DetailedItem> getMonoSetItems() {

        // Adding tools
        ArrayList<DetailedItem> list = mainTask.inventoryItems
                .stream()
                .filter(o -> !o.isDepletes())
                .collect(Collectors.toCollection(ArrayList::new));

        // Adding equipment gear
        if (mainTask.combatSetupTask != null) {
            list.addAll(mainTask.combatSetupTask.getCurrentItems()
                    .stream()
                    .map(EquipmentItem::getDetailedItem)
                    .collect(Collectors.toCollection(ArrayList::new))
            );

            // TODO add equipment ammo and runes

        }

        return list;
    }

    private int getSetCost(ArrayList<DetailedItem> list) {
        return list.stream()
                .mapToInt(DetailedItem::getCost)
                .sum();
    }

    private int getMaxSetsBuyableWithCurrentWealth(int liquidWealth, int setCostMono, int setCostMulti) {
        int currentMoney = liquidWealth - setCostMono;

        // Infinity failsafe
        if (setCostMulti == 0)
            setCostMulti++;

        return (int) Math.floor(currentMoney / setCostMulti);
    }

}
