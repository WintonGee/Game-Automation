package scripts.main_package.tasks.bank_task;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class BankTaskHandler {

    ArrayList<DetailedItem> bankDetailedItems; // Details on items after loading.

    BankTaskEquipmentHandler equipmentHandler;
    BankTaskInventoryHandler inventoryHandler;
    BankTaskDepositHandler depositHandler;

    // The detailed items should support filled, min, max.
    public BankTaskHandler(ArrayList<DetailedItem> bankDetailedItems) {
        this.setBankDetailedItems(bankDetailedItems);

        this.setEquipmentHandler(new BankTaskEquipmentHandler(bankDetailedItems));
        this.setInventoryHandler(new BankTaskInventoryHandler(bankDetailedItems));
        this.setDepositHandler(new BankTaskDepositHandler(bankDetailedItems));
    }

    // For checking if the bank task is possible.
    // If not enough items, return false.
    private boolean isPossible() {
        return this.bankDetailedItems.stream().allMatch(item -> item.isHaveMainItems(true));
    }

    //TODO add free slots support
    public boolean isSatisfied() {
        return this.bankDetailedItems.stream().allMatch(BankTaskHandler::isItemSatisfied);
    }

    public boolean handle() {
        for (int i = 0; i < 5; i++) {
            if (depositHandler.isAllDeposited() && equipmentHandler.isComplete() && inventoryHandler.isAllInInventory()) {
                Log.info("[BankTaskHandler] Bank task complete!");
                BankCache.update();
                return true;
            }

            if (!depositHandler.handle()) {
                Log.error("[BankTaskHandler] Failed to handle deposit!");
                continue;
            }

            if (!equipmentHandler.handle()) {
                Log.error("[BankTaskHandler] Failed to handle equipment!");
                continue;
            }

            if (!inventoryHandler.handle()) {
                Log.error("[BankTaskHandler] Failed to handle inventory!");
                continue;
            }

        }

        BankCache.update();
        return false;
    }

    public static boolean isItemSatisfied(DetailedItem detailedItem) {
        if (detailedItem.isEquipped() ) {
            return detailedItem.isHaveInEquipment() &&  !detailedItem.isHaveInInventory();
        }

        int amount = detailedItem.getQuantity();
        int checkId = getIdToUse(detailedItem);
        return Query.inventory().idEquals(checkId).sumStacks() == amount;
    }

    public static int getIdToUse(DetailedItem detailedItem) {
        int id = detailedItem.getItemId();
        return detailedItem.isNoted() ? Utils.getNotedId(id) : id;
    }

}
