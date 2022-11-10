package scripts.main_package.a_quest_data.requirement.item;

import lombok.Data;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ItemRequirement extends Requirement {

    private final int id;

    private final String name;

    private Integer displayItemId; // Unused

    protected int quantity;

    private boolean equip;

    protected boolean highlightInInventory; // Unused

    protected final List<Integer> alternateItems = new ArrayList<>();

    protected boolean exclusiveToOneItemType; //TODO determine what this is

    private boolean displayMatchedItemName; // Unused

    private Requirement conditionToHide; // Unused?

    public ItemRequirement(int id) {
        this(id, 1);
    }

    public ItemRequirement(int id, int quantity) {
        this(Utils.getItemName(id), id, quantity);
    }

    public ItemRequirement(String name, int id) {
        this(name, id, 1);
    }

    public ItemRequirement(String name, int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        equip = false;
    }

    public ItemRequirement(String name, int id, int quantity, boolean equip) {
        this(name, id, quantity);
        this.equip = equip;
    }

    public ItemRequirement(boolean highlightInInventory, String name, int id) {
        this(name, id);
        this.highlightInInventory = highlightInInventory;
    }

    public ItemRequirement(String name, List<Integer> items) {
        this(name, items.get(0), 1);
        this.addAlternates(items.subList(1, items.size()));
    }

    public ItemRequirement(String name, List<Integer> items, int quantity) {
        this(name, items.get(0), quantity);
        this.addAlternates(items.subList(1, items.size()));
    }

    public ItemRequirement(String name, List<Integer> items, int quantity, boolean equip) {
        this(name, items.get(0), quantity);
        this.equip = equip;
        this.addAlternates(items.subList(1, items.size()));
    }

    public DetailedItem getAsDetailedItem() {
        DetailedItem detailedItem = new DetailedItem(id, quantity);

        for (int id : alternateItems)
            detailedItem.addAlt(id, quantity);

        //TODO equipment data ?

        return detailedItem;
    }


    public void addAlternates(List<Integer> alternates) {
        this.alternateItems.addAll(alternates);
    }

    public void addAlternates(Integer... alternates) {
        this.alternateItems.addAll(Arrays.asList(alternates));
    }

    @Override
    public boolean check() {
        DetailedItem detailedItem = this.getAsDetailedItem();
        if (this.equip) {
            return detailedItem.isHaveMainInEquipment() || detailedItem.isHaveAltInEquipment();
        }
        return detailedItem.isHaveMainInInventory() || detailedItem.isHaveAltInInventory();
    }
}
