package scripts.main_package.tasks.ge_task;

import lombok.Data;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GrandExchangeOffer.Status;
import org.tribot.script.sdk.types.GrandExchangeOffer.Type;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.tasks.bank_task.DetailedBankTask;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

// If buying items, make sure to have enough gold
// If selling items, make sure to have items to sell
@Data
public class GeInventorySetup {

    ArrayList<GeTaskItem> list;

    public GeInventorySetup(ArrayList<GeTaskItem> list) {
        this.setList(list);
    }

    public boolean execute() {
        ArrayList<DetailedItem> list = getBankTaskItems();
        DetailedBankTask bankTask = new DetailedBankTask(list);
        bankTask.update();
        return bankTask.handle();
    }

    public boolean canSkip() {
        if (isNeedToMakeSpace()) {
            Log.trace("[GeInventorySetup] Need to make Inventory space");
            return false;
        }

        if (isHaveItemsToSell()) {
            Log.trace("[GeInventorySetup] Able to skip, Have items to sell");
            return true;
        }

        if (isHaveGoldToBuy() && isAnyPendingBuyItems() && !Inventory.isFull()) {
            Log.trace("[GeInventorySetup] Able to skip, Have gold to buy");
            return true;
        }

        if (isEverythingInOffered()) {
            Log.trace("[GeInventorySetup] Able to skip, Everything is offered");
            return true;
        }

        return false;
    }

    // Checks if have enough gold, accounts for items in offer
    public boolean isSetupPossible() {
        int goldNeeded = this.getGoldNeeded();
        int accessibleGold = Utils.getTotalItemCount(ItemID.COINS_995);
        boolean haveGoldToBuy = goldNeeded <= accessibleGold;
        if (haveGoldToBuy)
            return true;

        Log.warn("[GeInventorySetup] Gold Needed: " + goldNeeded + ", Accessible: " + accessibleGold);
        return isHaveItemsToSell();
    }

    // Have sell items in inventory, ge offers not full.
    private boolean isHaveItemsToSell() {
        List<Integer> sellIds = this.list.stream()
                .filter(i -> i.getType() == Type.SELL)
                .map(i -> i.getItemId())
                .collect(Collectors.toList());
        List<Integer> notedSellIds = this.list.stream()
                .filter(i -> i.getType() == Type.SELL)
                .map(i -> {
                    return Utils.getMonoSpaceId(i.getItemId());
                })
                .collect(Collectors.toList());

        return Query.inventory().filter(item -> {
            int id = item.getId();
            return sellIds.contains(id) || notedSellIds.contains(id);
        }).isAny();
    }

    public boolean isHaveGoldToBuy() {
        int goldNeeded = this.getGoldNeeded();
        int invGold = Inventory.getCount(ItemID.COINS_995);
        boolean haveEnoughGold = goldNeeded <= invGold;
        return haveEnoughGold;
    }

    private boolean isNeedToMakeSpace() {
        boolean noEmptyOffers = !GeUtil.isAnyFreeSlots();
        boolean invFull = Inventory.isFull();
        return noEmptyOffers && invFull;
    }

    private boolean isAnyPendingBuyItems() {
        return this.list.stream().anyMatch(i -> i.type == Type.BUY);
    }

    private boolean isEverythingInOffered() {
        return list.stream().allMatch(item -> GeUtil.isItemInOffered(item));
    }

    private int getMaxInventoryItems() {
        int slots = 28;
        int itemsInOffers = Query.grandExchangeOffers().statusNotEquals(Status.EMPTY).count();

        slots -= itemsInOffers;
        return slots;
    }

    private int getGoldNeeded() {
        return list.stream()
                .filter(item -> item.type == Type.BUY)
                .filter(item -> !GeUtil.isItemInOffered(item))
                .map(GeTaskItem::getRemainingBuyValue)
                .reduce(0, Integer::sum);
    }


    // Only need to grab items for sell items.
    // Grab gold for buy items
    private ArrayList<DetailedItem> getBankTaskItems() {
        ArrayList<DetailedItem> newList = new ArrayList<DetailedItem>();
        int count = 0;

        // Buy items, need gold
        if (isAnyPendingBuyItems()) {
            int totalGp = Utils.getTotalItemCount(ItemID.COINS_995);
            Log.trace("[GeInventorySetup] Adding Gold Slot, Amount: " + totalGp);
            newList.add(new DetailedItem(ItemID.COINS_995, totalGp));
            count++;
        }

        Iterator<GeTaskItem> iter = list.iterator();
        int maxSlots = getMaxInventoryItems();
        while (iter.hasNext()) {
            GeTaskItem next = iter.next();

            // Skip buy items.
            if (next.type == Type.BUY)
                continue;

            // Inventory Slot limit
            if (count >= maxSlots) {
                return newList;
            }

            int id = next.getItemId();
            int goalQuantity = next.getQuantity();
            int haveQuantity = Utils.getTotalItemCountWithNoted(id);
            int useQuantity = haveQuantity - goalQuantity;

            DetailedItem detailedItem = new DetailedItem(id, useQuantity);
            if (!detailedItem.isStackable()) {
                detailedItem.setNoted(true);
            }

            newList.add(detailedItem);
            count++;
        }
        return newList;
    }

}
