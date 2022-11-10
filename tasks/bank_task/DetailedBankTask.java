package scripts.main_package.tasks.bank_task;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.GrandExchange;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;
import java.util.Optional;

@Data
@Accessors(chain = true)
public class DetailedBankTask {

    private final int INVENTORY_SLOTS = 28;

    int emptySlots = 0;
    boolean allowExtraSets = true; // Set false when using exact amounts

    @NonNull
    ArrayList<DetailedItem> defaultList;
    BankTaskHandler bankTaskHandler;

    public DetailedBankTask(ArrayList<DetailedItem> defaultList) {
        this.setDefaultList(defaultList);
    }

    public boolean isPossible() {
        return defaultList.stream().allMatch(i -> i.isHave(true));
    }

    public boolean isSatisfied() {
        if (Inventory.getEmptySlots() < emptySlots)
            return false;
        return defaultList.stream().allMatch(BankTaskHandler::isItemSatisfied);
    }

    public boolean handle() {
        update();
        return GrandExchange.close() && bankTaskHandler.handle();
    }

    public void update() {
        ArrayList<DetailedItem> updatedList = getUpdatedList();
        bankTaskHandler = new BankTaskHandler(updatedList);
    }

    private ArrayList<DetailedItem> getUpdatedList() {
        if (!allowExtraSets)
            return defaultList;

        int undepletableSlots = getUndepletableSlots(), deletableSlots = getDeletableSlots();
        int maxSets = getMaxSets(undepletableSlots, deletableSlots);

        Log.info("[DetailedBankTask] Max sets: " + maxSets + ", Tools: " + undepletableSlots + " Materials: " + deletableSlots);

        ArrayList<DetailedItem> list = new ArrayList<DetailedItem>();

        defaultList.forEach(item -> {
            int id = item.getItemId();
            Optional<Integer> altId = item.getValidAltItemId();
            int quantityToUse = getQuantityToUse(item, maxSets);
            int idToUse = altId.orElse(id);

            DetailedItem copy = item.getAltCopy(idToUse);
            copy.setQuantity(quantityToUse);

            list.add(copy);
        });

        return list;
    }

    private int getQuantityToUse(DetailedItem item, int maxSets) {
        int min = item.getQuantity();
        if (!item.isDepletes())
            return min;

        int id = item.getItemId();
        int max = min * maxSets;
        int haveAmount = Utils.getTotalItemCountWithNoted(id, true);

        // Case noted items
        if (item.isNoted())
            return min;

        // Case stackable
        if (item.isStackable()) {
            if (item.isFillStackableWhenBanking())
                return Math.max(haveAmount, min);

            return min;
        }

        // Case equipped item, not stackable
        if (item.isEquipped())
            return min;

        // Case have more than max amount
        if (max < haveAmount)
            return max;

        return Math.max(haveAmount, min);
    }

    private int getUndepletableSlots() {
        int slotsFromUndepletable = defaultList.stream()
                .filter(item -> !item.isEquipped())
                .filter(item -> !item.isDepletes())
                .map(DetailedItem::getQuantity)
                .reduce(0, Integer::sum);

        int slotsFromStackable = (int) defaultList.stream()
                .filter(item -> !item.isEquipped())
                .filter(DetailedItem::isDepletes)
                .filter(item -> item.isNoted() || item.isStackable())
                .map(DetailedItem::getQuantity)
                .count();

        return slotsFromStackable + slotsFromUndepletable;
    }

    private int getDeletableSlots() {
        return defaultList.stream()
                .filter(item -> !item.isEquipped())
                .filter(item -> !item.isNoted())
                .filter(item -> !item.isStackable())
                .filter(DetailedItem::isDepletes)
                .mapToInt(DetailedItem::getQuantity)
                .sum();
    }

    private int getMaxSets(int undepletableSlots, int deletableSlots) {
        if (!allowExtraSets)
            return 1;

        int sets = INVENTORY_SLOTS;
        sets -= undepletableSlots;
        sets -= this.emptySlots;

        if (deletableSlots == 0)
            deletableSlots = 1;

        sets /= deletableSlots; // Note: rounds down, which is wanted

        return sets;
    }

}
