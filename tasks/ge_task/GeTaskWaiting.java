package scripts.main_package.tasks.ge_task;

import lombok.Data;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GrandExchangeOffer.Status;
import scripts.main_package.api.action.UtilWait;
import scripts.main_package.api.other.UtilTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// This class is used for waiting if needed.
// Conditions:
//  - When offers are full (8+ items)
//  - No offer to cancel
//TODO write this class
@Data
public class GeTaskWaiting {

    ArrayList<GeTaskItem> list;

    public GeTaskWaiting(ArrayList<GeTaskItem> list) {
        this.setList(list);
    }

    public void handle() {

        if (haveOfferToCollect()) {
            Log.debug("[GeTaskWaiting] Offer to collect detected, Skipping wait.");
            return;
        }

        if (haveOfferToCancel()) {
            Log.debug("[GeTaskWaiting] Offer to cancel detected, Skipping wait.");
            return;
        }

        int maxWaitTime = getWaitTime();
        Log.trace("[GeTaskWaiting] Waiting until a valid task, Max Time: " + maxWaitTime + "(ms)");
        UtilWait.until(maxWaitTime, () -> haveOfferToCancel() || haveOfferToCollect());
    }

    private boolean haveOfferToCollect() {
        return Query.grandExchangeOffers().statusEquals(Status.COMPLETED).isAny();
    }

    private boolean haveOfferToCancel() {
        List<Integer> cooldownList = this.list.stream()
                .filter(item -> !item.isOnCooldown())
                .map(item -> item.getItemId())
                .collect(Collectors.toList());
        return Query.grandExchangeOffers().filter(o -> {
            int id = o.getItemId();
            return cooldownList.contains(id);
        }).isAny();
    }

    private int getWaitTime() {
        long waitUntil = this.getNextCooldown();
        int maxWaitTime = (int) (waitUntil - UtilTime.getTime());
        return maxWaitTime <= 0 ? 0 : maxWaitTime;
    }

    // Returns the amount of time should wait for
    // this will depend on the cooldown of next item.
    private long getNextCooldown() {
        return list.stream().filter(item -> item.isOnCooldown()).filter(item -> {
            return GeUtil.getItemOfferSlots(item).size() > 0; // Make sure item is in ge offer
        }).findFirst().map(item -> item.getCooldownUntil()).orElse(UtilTime.getTime());
    }

}
