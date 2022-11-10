package scripts.main_package.tasks.ge_task;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.GrandExchangeOffer.Slot;
import scripts.main_package.api.other.Utils;

import java.util.ArrayList;

// Handles activities when the item is in grand exchange offers.
@Data
@Accessors(chain = true)
public class GeItemInOfferHandler {

    GeTaskItem geItem;

    public GeItemInOfferHandler(GeTaskItem geItem) {
        this.setGeItem(geItem);
    }

    public boolean handle() {
        ArrayList<Slot> slots = GeUtil.getItemOfferSlots(geItem);
        if (slots.size() == 0)
            return true;

        Log.info("[" + geItem.type + "][GeItemInOfferHandler] Clearing slots for "
                + Utils.getItemName(geItem.getItemId()));
        return GeUtil.abortSlots(slots) && GeUtil.getItemOfferSlots(geItem).size() == 0;
    }

}
