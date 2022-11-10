package scripts.main_package.tasks.bank_task;

import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.api.action.UtilBank;
import scripts.main_package.api.action.UtilWait;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;
import java.util.stream.Collectors;

// This class will deposit excess items
// and will withdraw items, accounting for items already in inventory.
public class BankTaskInventoryHandler {

    ArrayList<DetailedItem> invDetailedItems; // Details on items after loading.

    // The detailed items should support filled, min, max.
    public BankTaskInventoryHandler(ArrayList<DetailedItem> bankDetailedItems) {
        invDetailedItems = bankDetailedItems
                .stream()
                .filter(item -> !item.isEquipped())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean isAllInInventory() {
        return invDetailedItems.stream().allMatch(BankTaskHandler::isItemSatisfied);
    }

    public boolean handle() {
        if (!UtilBank.open())
            return false;

        for (DetailedItem next : invDetailedItems) {
            if (BankTaskHandler.isItemSatisfied(next))
                continue;

            int idToCheck = BankTaskHandler.getIdToUse(next); // Returns noted or unnoted id.
            int amountNeeded = next.getQuantity();
            int amountInInventory = Query.inventory().idEquals(idToCheck).sumStacks();
            int diff = amountNeeded - amountInInventory;

            boolean shouldWithdraw = diff > 0;
            if (shouldWithdraw) {
                handleWithdraw(next, diff);
                continue;
            }

            int diffAbsolute = Math.abs(diff);
            Bank.deposit(idToCheck, diffAbsolute);

        }
        return UtilWait.until(1500, this::isAllInInventory);
    }

    private void handleWithdraw(DetailedItem next, int amount) {
        int id = next.getItemId();
        if (handleNoting(next)) {
            Bank.withdraw(id, amount);
        }
    }

    // Turn noted on if the item is wanted in noted form
    private boolean handleNoting(DetailedItem detailedItem) {
        return UtilBank.setNoted(detailedItem.isNoted());
    }

}
