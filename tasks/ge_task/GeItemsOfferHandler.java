package scripts.main_package.tasks.ge_task;

import org.tribot.script.sdk.GrandExchange;
import org.tribot.script.sdk.GrandExchange.CreateOfferConfig;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GrandExchangeOffer.Status;
import org.tribot.script.sdk.types.GrandExchangeOffer.Type;
import scripts.main_package.api.action.UtilWait;
import scripts.main_package.api.other.Utils;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Iterator;

public class GeItemsOfferHandler {

    public static void handle(ArrayList<GeTaskItem> list) {
        Iterator<GeTaskItem> iter = list.iterator();
        while (iter.hasNext()) {
            GeTaskItem next = iter.next();
            String name = Utils.getItemName(next.getItemId());
            int quantity = next.getQuantity();

            // TODO this is preventing from grabbing completed
            if (next.isOnCooldown() && !isStatusComplete(next)) {
                Log.trace("[GeItemsOfferHandler] " + quantity + " " + name + " is on cooldown, skipping");
                continue;
            }

            if (!new GeItemInOfferHandler(next).handle()) {
                Log.warn("[GeItemsOfferHandler] Failed to handle item in GE: " + name);
                continue;
            }

            if (!isCanOffer(next)) { // Too much spam, comment this out
//                Log.error("[GeItemsOfferHandler] Unable to make offer: " + quantity + " " + name);
                continue;
            }

            if (offerItem(next)) {
                next.adjustOffer();
            }
        }
    }

    private static boolean isStatusComplete(GeTaskItem next) {
        return Query.grandExchangeOffers().statusEquals(Status.COMPLETED).itemIdEquals(next.getItemId()).isAny();
    }

    private static boolean isCanOffer(GeTaskItem next) {
        if (!GeUtil.isAnyFreeSlots()) {
//            Log.warn("No Free Slots!");
            return false;
        }

        if (next.type == Type.BUY) { // Have enough gold for next item set
            return Query.inventory().idEquals(ItemID.COINS_995).sumStacks() >= next.getRemainingBuyValue();
        }

        // Any items to sell
        int id = next.getItemId(), notedId = Utils.getNotedId(id);
        return Query.inventory().idEquals(id, notedId).isAny();
    }

    private static boolean offerItem(GeTaskItem next) {
        String name = Utils.getItemName(next.getItemId());
        int quantity = next.getQuantity();
        Log.info("[" + next.type + "][GeItemsOfferHandler] Offer Item: " + name + " -> " + quantity);
        if (GeUtil.open()) {
            CreateOfferConfig config = next.getConfig();
            int freeOfferSlots = GeUtil.getFreeSlotCount();
            if (config != null && GrandExchange.placeOffer(config)) {
                return waitForOfferScreen(freeOfferSlots);
            }
        }
        return false;
    }

    //TODO test or write something better
    private static boolean waitForOfferScreen(int freeOfferSlots) {
        return UtilWait.until(2400, () -> {
            int newFreeSlots = GeUtil.getFreeSlotCount();
            return newFreeSlots != freeOfferSlots;
        });
    }

}
