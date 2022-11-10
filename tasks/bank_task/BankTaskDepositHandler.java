package scripts.main_package.tasks.bank_task;

import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.main_package.api.action.UtilBank;
import scripts.main_package.api.action.UtilWait;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BankTaskDepositHandler {

    ArrayList<DetailedItem> bankDetailedItems;
    List<Integer> taskItemIds;

    public BankTaskDepositHandler(ArrayList<DetailedItem> bankDetailedItems) {
        this.setBankDetailedItems(bankDetailedItems);

        taskItemIds = bankDetailedItems.stream().map(BankTaskHandler::getIdToUse).collect(Collectors.toList());
    }

    public boolean handle() {
        for (int i = 0; i < 5; i++) {

            if (isAllDeposited()) {
                Log.info("[BankTaskDepositHandler] Items deposited successfully!");
                return true;
            }

            if (!handleDeposit()) {
                Log.info("[BankTaskDepositHandler] Failed to deposit items!");
            }
        }

        return false;
    }

    //TODO rewrite this method to collect the items data to deposit into a list
    public boolean handleDeposit() {
        if (!UtilBank.open())
            return false;

        ArrayList<Integer> itemsDepositedList = new ArrayList<>();
        for (val itemToDeposit : getItemsToDeposit()) {
            int itemToDepositId = itemToDeposit.getId();
            if (itemsDepositedList.contains(itemToDepositId)) // Skip duplicates
                continue;

            if (Bank.depositAll(itemToDeposit)) {
                itemsDepositedList.add(itemToDepositId);
            }
        }

        return UtilWait.until(1500, this::isAllDeposited);
    }

    public boolean isAllDeposited() {
        return getItemsToDeposit().size() == 0;
    }

    private List<InventoryItem> getItemsToDeposit() {
        return Query.inventory()
                .filter(item -> !taskItemIds.contains(item.getId()))
                .toList();
    }

    //TODO implement
    private List<Integer> getDepositIds() {
        return Query.inventory()
                .filter(item -> !taskItemIds.contains(item.getId()))
                .stream().map(InventoryItem::getId)
                .distinct()
                .collect(Collectors.toList());
    }

    //TODO something to deposit extra items?

}
