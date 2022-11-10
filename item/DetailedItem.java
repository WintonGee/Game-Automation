package scripts.main_package.item;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.pricing.Pricing;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.api.other.Utils;

import java.util.ArrayList;
import java.util.Optional;

@Data
@Accessors(chain = true)
public class DetailedItem {

    private final int DEFAULT_QUANTITY = 1;

    int itemId;
    int quantity = DEFAULT_QUANTITY;

    boolean equipped = false; // If item should be equipped.

    boolean depletes = true; // Tools will be set to false
    boolean noted = false; // To grab the item in noted form

    // Used for items that can stack, for example, nails.
    // For bank task, will grab all items.
    // Set this to false if an exact amount should be used instead.
    // Very rare case where an exact amount of a item that stacks is needed.
    boolean fillStackableWhenBanking = true;

    public ArrayList<DetailedItem> alternativeItems = new ArrayList<DetailedItem>();

    public DetailedItem(int itemId) {
        this.setItemId(itemId);
    }

    public DetailedItem(int itemId, int quantity) {
        this.setItemId(itemId);
        this.setQuantity(quantity);
    }

    public DetailedItem addAlt(int itemId) {
        alternativeItems.add(new DetailedItem(itemId));
        return this;
    }

    public DetailedItem addAlt(int itemId, int quantity) {
        alternativeItems.add(new DetailedItem(itemId, quantity));
        return this;
    }

    // Item Definitions
    public boolean isMembers() {
        return Utils.isMembers(this.itemId);
    }

    public boolean isStackable() {
        return Utils.isStackable(this.itemId);
    }

    public boolean isTradeable() {
        return Utils.isTradeable(this.itemId);
    }

    //TODO add checking grand exchange

    // Checks for both main and alternative items
    public boolean isHave(boolean includeNoted) {
        return isHaveMainItems(includeNoted) || isHaveAltItems(includeNoted);
    }

    public boolean isHaveOnCharacter() {
        return isHaveInInventory() || isHaveInEquipment();
    }

    public boolean isHaveInInventory() {
        return isHaveMainInInventory() || isHaveAltInInventory();
    }

    public boolean isHaveInEquipment() {
        return isHaveMainInEquipment() || isHaveAltInEquipment();
    }

    // Main Items
    public boolean isHaveMainItems() {
        return isHaveMainItems(false);
    }

    public boolean isHaveMainItems(boolean includeNoted) {
        if (includeNoted)
            return Utils.getTotalItemCountWithNoted(this.itemId) >= this.quantity;
        return Utils.getTotalItemCount(this.itemId) >= this.quantity;
    }

    public boolean isHaveMainInInventory() {
        return Query.inventory()
                .idEquals(this.itemId)
                .sumStacks() >= this.quantity;
    }

    public boolean isHaveMainInEquipment() {
        return Query.equipment()
                .idEquals(this.itemId)
                .sumStacks() >= this.quantity;
    }

    // Alternative items
    public boolean isHaveAltItems() {
        return isHaveAltItems(false);
    }

    public boolean isHaveAltItems(boolean noted) {
        if (alternativeItems.size() == 0) // Recursion stop cond.
            return false;
        return alternativeItems.stream()
                .anyMatch(alt -> alt.isHaveMainItems(noted));
    }

    public boolean isHaveAltInInventory() {
        if (alternativeItems.size() == 0) // Recursion stop cond.
            return false;
        return alternativeItems.stream()
                .anyMatch(DetailedItem::isHaveMainInInventory);
    }

    public boolean isHaveAltInEquipment() {
        if (alternativeItems.size() == 0) // Recursion stop cond.
            return false;
        return alternativeItems.stream()
                .anyMatch(DetailedItem::isHaveMainInEquipment);
    }

    // Returns item that has enough quantity, checking alternatives first
    public int getBestItemToUseId() {
        val alt = getValidAltItemId();
        return alt.orElseGet(() -> itemId);
    }

    // Returns alternative item that has enough quantity
    public Optional<Integer> getValidAltItemId() {
        return alternativeItems.stream()
                .filter(item -> item.isHaveMainItems(true))
                .findFirst()
                .map(DetailedItem::getItemId);
    }

    // Returns a copy without alt ids and alt id set to newId.
    // Generally used when a duplicate item is wanted and used for changing the quantity
    public DetailedItem getCopy() {
        DetailedItem newItem = new DetailedItem(this.itemId, quantity);
        newItem.setDepletes(this.depletes);
        newItem.setEquipped(this.equipped);
        newItem.setNoted(this.noted);
        newItem.setFillStackableWhenBanking(this.fillStackableWhenBanking);
        newItem.alternativeItems.addAll(this.alternativeItems);
        return newItem;
    }

    // Returns a copy without alt ids and alt id set to newId.
    public DetailedItem getAltCopy(int newId) {
        DetailedItem newItem = new DetailedItem(newId, quantity);
        newItem.setDepletes(this.depletes);
        newItem.setEquipped(this.equipped);
        newItem.setNoted(this.noted);
        newItem.setFillStackableWhenBanking(this.fillStackableWhenBanking);
        return newItem;
    }

    public int getCost() {
        if (this instanceof UntradeableItem) {
            return ((UntradeableItem) this).getMaterialCost();
        }

        int pricePer = Pricing.lookupPrice(itemId).orElse(1);
        return pricePer * quantity;
    }

    public ArrayList<Requirement> getRequirements() {
        return this instanceof UntradeableItem ? ((UntradeableItem) this).getAllRequirements() : new ArrayList<>();
    }

}
