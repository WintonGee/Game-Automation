package scripts.main_package.tasks.ge_task;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.GrandExchangeOffer.Type;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;
import java.util.Collections;

// Something interesting that can be done using this framework
// Can place items for buy and sell types.
@Data
@Accessors(chain = true)
public class GeTask {

    int offerAttempts = 20;

    ArrayList<GeTaskItem> geItemsList = new ArrayList<GeTaskItem>();

    public void addItems(ArrayList<DetailedItem> itemsList, Type type) {
        addItems(itemsList, type, 1);
    }

    public void addItems(ArrayList<DetailedItem> itemsList, Type type, int multiplier) {
        ArrayList<GeTaskItem> newList = new GeTaskLoader().getList(itemsList, type, multiplier);
        this.geItemsList.addAll(newList);
    }

    // Check if have all items
    public boolean taskComplete() {
        return this.geItemsList.size() == 0;
    }

    public boolean execute() {
        Collections.shuffle(geItemsList);
        int i = 0;
        while (i < this.offerAttempts) {
            if (taskComplete()) {
                Log.info("[GeTask] No Items Left To Offer, Task Complete!");
                return true;
            }

            // Basic stuff...
            i++;
            Waiting.waitUniform(10, 300);
            Log.trace("[GeTask] Starting Attempt: " + i + "/" + this.offerAttempts + ", Items Left: "
                    + geItemsList.size());

            // Removes completed items from list.
            if (!GeTaskOfferSetup.handle(geItemsList)) {
                Log.error("[GeTask] Failed to handle offer setup.");
                continue;
            }

            // Sets up the inventory if needed
            GeInventorySetup invSetup = new GeInventorySetup(geItemsList);
            if (!invSetup.isSetupPossible()) {
                Log.error("[GeTask] Inventory Setup is not possible.");
                return false;
            }

            if (!invSetup.canSkip() && !invSetup.execute()) {
                Log.warn("[GeTask] Failed to handle inventory setup.");
                continue;
            }

            // Places offers for items
            GeItemsOfferHandler.handle(geItemsList);

            // Waiting if needed
            //TODO it was waiting after a weird delay, and the offers stuff was already done
            new GeTaskWaiting(geItemsList).handle();

        }
        Log.warn("[GeTask] Failed, Number of items failed to handle: " + this.geItemsList.size());
        return false;
    }

}
