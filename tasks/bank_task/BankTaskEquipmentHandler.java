package scripts.main_package.tasks.bank_task;

import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.main_package.api.action.UtilBank;
import scripts.main_package.api.action.UtilWait;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class BankTaskEquipmentHandler {

    private final String[] equipActions = {"Wear", "Wield"};
    ArrayList<DetailedItem> equippedDetailedItems; // Details on items after loading.
    List<Integer> wearIds;

    // The detailed items should support filled, min, max.
    public BankTaskEquipmentHandler(ArrayList<DetailedItem> bankDetailedItems) {
        equippedDetailedItems = bankDetailedItems
                .stream()
                .filter(DetailedItem::isEquipped)
                .collect(Collectors.toCollection(ArrayList::new));
        wearIds = equippedDetailedItems
                .stream()
                .map(DetailedItem::getItemId)
                .collect(Collectors.toList());
    }


    public boolean handle() {
        for (int i = 0; i < 5; i++) {
            if (isComplete()) {
                Log.info("[BankTaskEquipmentHandler] All equipped items equipped");
                return true;
            }

            if (!handleUnwantedEquipped()) {
                Log.info("[BankTaskEquipmentHandler] Failed to remove unwanted equipment");
                continue;
            }

            if (!handleExtraInInventory()) {
                Log.info("[BankTaskEquipmentHandler] Failed to remove extra equipment");
                continue;
            }

            // Withdraws equip items.
            if (!handleWithdraw()) {
                Log.info("[BankTaskEquipmentHandler] Failed to withdraw equipment");
                continue;
            }

            handleEquipping();
        }

        return false;
    }

    private boolean handleUnwantedEquipped() {
        if (!isUnwantedEquipped())
            return true;

        if (!UtilBank.open())
            return false;

        Bank.depositEquipment();

        return UtilWait.until(1500, () -> !isUnwantedEquipped());
    }

    private boolean handleExtraInInventory() {
        val extraItem = getExtraInInventory();
        if (extraItem.isEmpty())
            return true;

        Log.info("[BankTaskEquipmentHandler] Attempting to deposit extra equipment items");
        return UtilBank.open() && Bank.depositInventory();
    }

    private boolean handleWithdraw() {
        if (!UtilBank.open() || !UtilBank.setNoted(false))
            return false;

        // Failsafe
        if (Inventory.isFull())
            Bank.depositInventory();

        int invCount = Inventory.getEmptySlots();
        boolean itemsWithdrawn = false;
        for (DetailedItem next : equippedDetailedItems) {
            if (next.isHaveInEquipment())
                continue;

            int id = next.getBestItemToUseId();
            int quantity = next.getQuantity();
            if (Bank.withdraw(id, quantity))
                itemsWithdrawn = true;
        }

        return !itemsWithdrawn || UtilWait.until(1200, () -> Inventory.getEmptySlots() != invCount);
    }

    private boolean handleEquipping() {
        Waiting.waitUniform(600, 800); // Wait a tick, so items can load after withdrawing.
        List<InventoryItem> items = Query.inventory()
                .filter(item -> wearIds.contains(item.getId()))
                .stream()
                .collect(Collectors.toList());
        if (items.size() > 0 && !Bank.close()) {
            return false;
        }
        items.forEach(InventoryItem::click);
        return UtilWait.until(1200, this::isComplete);
    }

    public boolean isComplete() {
        if (isUnwantedEquipped() || getExtraInInventory().isPresent()) {
            return false;
        }

        return equippedDetailedItems.stream().allMatch(DetailedItem::isHaveInEquipment);
    }

    // Returns if items not included in bank task to be equipped is equipped
    private boolean isUnwantedEquipped() {
        return Query.equipment()
                .filter(equip -> !wearIds.contains(equip.getId()))
                .isAny();
    }

    private Optional<DetailedItem> getExtraInInventory() {
        return this.equippedDetailedItems.stream().filter(this::isExtraInInventory).findAny();
    }

    //TODO might cause some errors for
    // items we want equippped and in the inventory
    private boolean isExtraInInventory(DetailedItem detailedItem) {
        return detailedItem.isHaveInEquipment() && detailedItem.isHaveInInventory();
    }



}
