package scripts.main_package.api.task.general_action;

import lombok.Setter;
import org.tribot.script.sdk.MakeScreen;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.TaskAction;
import scripts.main_package.api.task.TaskCondition;
import scripts.main_package.api.task.TaskWaiting;
import scripts.main_package.api.task.general_condition.ConditionBS;

import java.util.Optional;

public class ActionCombine extends TaskAction {

    @Setter
    int firstId, secondId;

    public ActionCombine(int firstId, int secondId) {
        this.setFirstId(firstId);
        this.setSecondId(secondId);

        this.setTaskWaiting(getTaskWait());
    }

    @Override
    public String getActionName() {
        return "Combine " + Utils.getItemName(firstId) + " on " + Utils.getItemName(secondId);
    }

    @Override
    public boolean handleAction() {
        Optional<InventoryItem> firstItem = Query.inventory().idEquals(this.firstId).findClosestToMouse();
        Optional<InventoryItem> secondItem = Query.inventory().idEquals(this.secondId).findRandom();
        return secondItem.isPresent() && firstItem.map(m -> m.useOn(secondItem.get())).orElse(false);
    }

    public TaskWaiting getTaskWait() {
        TaskWaiting taskWaiting = new TaskWaiting(180000, 240000, "Wait after combining items");

        String firstItemName = Utils.getItemName(firstId);
        String secondItemName = Utils.getItemName(secondId);

        taskWaiting.addCondition("Out of item: " + firstItemName, () -> Query.inventory().idEquals(this.firstId).count() == 0);
        taskWaiting.addCondition("Out of item: " + secondItemName, () -> Query.inventory().idEquals(this.secondId).count() == 0);
        taskWaiting.addCondition("Make screen open", MakeScreen::isOpen);

        return taskWaiting;
    }

}
