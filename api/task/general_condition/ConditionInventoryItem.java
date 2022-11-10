package scripts.main_package.api.task.general_condition;

import lombok.Setter;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.TaskCondition;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;
import java.util.Arrays;

public class ConditionInventoryItem extends TaskCondition {

    ArrayList<DetailedItem> itemList = new ArrayList<>();

    public ConditionInventoryItem(int... ids) {
       for (int id : ids)
           itemList.add(new DetailedItem(id));
    }

    public ConditionInventoryItem(DetailedItem... detailedItem) {
        itemList.addAll(Arrays.asList(detailedItem));
    }

    public ConditionInventoryItem addItem(int id) {
        return addItem(id, 1);
    }

    public ConditionInventoryItem addItem(int id, int amount) {
        itemList.add(new DetailedItem(id, amount));
        return this;
    }

    @Override
    public String getConditionName() {
        return "Inventory Item";
    }

    @Override
    public boolean check() {
        return itemList.stream()
                .allMatch(DetailedItem::isHaveInInventory);
    }

}
