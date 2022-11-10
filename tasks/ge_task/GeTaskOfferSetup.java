package scripts.main_package.tasks.ge_task;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GrandExchangeOffer;
import org.tribot.script.sdk.types.GrandExchangeOffer.Slot;
import org.tribot.script.sdk.types.GrandExchangeOffer.Status;
import scripts.main_package.api.action.UtilBank;
import scripts.main_package.api.other.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

// This class is used to organize.
// - Current grand exchange offers
public class GeTaskOfferSetup {

    public static boolean handle(ArrayList<GeTaskItem> list) {
        removeCompleted(list);
        // TODO maybe check if list is empty, return false

        organizeOffers(list);
        return handleUnrelatedOfferItems(list);
    }

    private static void removeCompleted(ArrayList<GeTaskItem> list) {
        Iterator<GeTaskItem> iter = list.iterator();
        while (iter.hasNext()) {
            GeTaskItem next = iter.next();
            String name = Utils.getItemName(next.getItemId());

            // Checking if the item is complete
            if (next.isComplete()) {
                Log.debug("[" + next.type + "][GeTaskOfferSetup] Item Complete: " + next.getQuantity() + " " + name);
                iter.remove();
            }
        }
    }

    // This cancels and collects the items that are past their cooldown
    private static void organizeOffers(ArrayList<GeTaskItem> list) {
        ArrayList<Slot> slotsToAbort = new ArrayList<Slot>();
        list.stream().filter(item -> !item.isOnCooldown()).forEach(item -> {
            ArrayList<Slot> slots = GeUtil.getItemOfferSlots(item);
            slotsToAbort.addAll(slots);
        });

        GeUtil.abortSlots(slotsToAbort);
    }

    // Removes items not in task list from offers if needed
    // Conditions for using this task
    // - Offers are full, need to make room
    // TODO
    private static boolean handleUnrelatedOfferItems(ArrayList<GeTaskItem> list) {
        boolean haveEmptySlots = GeUtil.isAnyFreeSlots();
        if (haveEmptySlots)
            return true;


        Log.warn("[GeTaskOfferSetup] Grand Exchange Offers Full, Clearing Unrelated Items!");
        if (Inventory.isFull() && !UtilBank.depositAllInventory()) {
            Log.error("[GeTaskOfferSetup] Failed to handle full inventory");
            return false;
        }

        List<Integer> idsList = list.stream()
                .map(GeTaskItem::getItemId)
                .collect(Collectors.toList());

        ArrayList<Slot> slotsToAbort = Query
                .grandExchangeOffers()
                .statusNotEquals(Status.EMPTY)
                .filter(offer -> !idsList.contains(offer.getItemId()))
                .stream()
                .map(GrandExchangeOffer::getSlot)
                .collect(Collectors.toCollection(ArrayList::new));

        return GeUtil.abortSlots(slotsToAbort);
    }

}
