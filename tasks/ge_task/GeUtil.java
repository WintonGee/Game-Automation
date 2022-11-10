package scripts.main_package.tasks.ge_task;

import org.tribot.script.sdk.GrandExchange;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.interfaces.Tile;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GrandExchangeOffer;
import org.tribot.script.sdk.types.GrandExchangeOffer.Slot;
import org.tribot.script.sdk.types.GrandExchangeOffer.Status;
import org.tribot.script.sdk.types.WorldTile;
import scripts.main_package.api.action.UtilBank;
import scripts.main_package.api.action.UtilWait;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GeUtil {

    private static final Tile GE_TILE = new WorldTile(3167, 3489, 0);

    public static boolean open() {
        if (GrandExchange.isOpen())
            return true;
        return travel() && UtilBank.close() && GrandExchange.open();
    }

    public static boolean travel() {
        return GeTravel.travel();
    }

    public static boolean isAnyFreeSlots() {
        return getFreeSlotCount() > 0;
    }

    public static boolean isItemInOffered(GeTaskItem geItem) {
        int id = geItem.getItemId();
        return Query.grandExchangeOffers()
                .itemIdEquals(id)
                .isAny();
    }

    public static int getFreeSlotCount() {
        return Query.grandExchangeOffers()
                .isEnabled()
                .filter(o -> o.getStatus() == Status.EMPTY)
                .count();
    }

    public static int getGeOffersCount() {
        return getCurrentOffers().size();
    }

    public static ArrayList<GrandExchangeOffer> getCurrentOffers() {
        return Query.grandExchangeOffers()
                .statusNotEquals(Status.EMPTY)
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Slot> getItemOfferSlots(GeTaskItem geItem) {
        int id = geItem.getItemId();
        return Query
                .grandExchangeOffers()
                .itemIdEquals(id)
                .stream()
                .map(GrandExchangeOffer::getSlot)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean abortSlots(ArrayList<Slot> slots) {
        int size = slots.size();
        if (size == 0)
            return true;
        if (!GeUtil.open()) {
            Log.error("[GeUtil] Failed to open ge.");
            return false;
        }

        Log.trace("[GeUtil] Aborting " + size + " slot(s).");
        boolean abortOffers = slots.stream().allMatch(GrandExchange::abort);
        if (abortOffers) {
            UtilWait.until(2400, () ->
                    Query.grandExchangeOffers()
                    .slotEquals(slots.toArray(new Slot[size]))
                    .statusNotEquals(Status.CANCELLED)
                    .count() == 0
            );
        }
        int freeSlots = getFreeSlotCount();
        return GrandExchange.collectAll() && UtilWait.until(2400, () -> freeSlots < getFreeSlotCount());
    }


}
