package scripts.main_package.a_skill_data.bank_skills;

import lombok.val;
import org.tribot.script.sdk.MakeScreen;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.Task;
import scripts.main_package.api.task.TaskAction;
import scripts.main_package.api.task.TaskCondition;
import scripts.main_package.api.task.TaskWaiting;
import scripts.main_package.api.task.general_action.ActionBS;
import scripts.main_package.api.task.general_condition.ConditionBS;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MakescreenTask {

    int productId;
    ArrayList<DetailedItem> items = new ArrayList<>();

    public MakescreenTask(int productId) {
        this.productId = productId;
    }

    public MakescreenTask(int productId, int... ids) {
        this.productId = productId;
        for (int id : ids)
            items.add(new DetailedItem(id));
    }

    public MakescreenTask(int productId, DetailedItem... detailedItems) {
        this.productId = productId;
        items.addAll(Arrays.asList(detailedItems));
    }

    public Task getTask() {
        return new Task(getTaskCondition(), getTaskAction());
    }

    private TaskCondition getTaskCondition() {
        return new ConditionBS(MakeScreen::isOpen);
    }

    private TaskAction getTaskAction() {
        val list = items.stream()
                .map(DetailedItem::getItemId)
                .collect(Collectors.toList());


        String activityName = "Makescreen: " + Utils.getItemName(productId);
        return new ActionBS(() -> MakeScreen.makeAll(i -> {
            int id = i.getId();
            return id == productId || list.contains(id);
        }))
                .setActionName(activityName)
                .setTaskWaiting(getTaskWait());
    }

    public TaskWaiting getTaskWait() {
        TaskWaiting taskWaiting = new TaskWaiting(180000, 240000, "Wait after makescreen");

        taskWaiting.addCondition("Out of items", () -> items.stream().anyMatch(i -> !i.isHaveOnCharacter()));

        return taskWaiting;
    }

}
