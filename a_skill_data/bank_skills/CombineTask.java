package scripts.main_package.a_skill_data.bank_skills;

import org.tribot.script.sdk.MakeScreen;
import scripts.main_package.api.task.Task;
import scripts.main_package.api.task.TaskAction;
import scripts.main_package.api.task.TaskCondition;
import scripts.main_package.api.task.general_action.ActionCombine;
import scripts.main_package.api.task.general_condition.ConditionBS;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;
import java.util.Arrays;

public class CombineTask {

    ArrayList<DetailedItem> itemsList = new ArrayList<>();

    public CombineTask(DetailedItem... detailedItems) {
        itemsList.addAll(Arrays.asList(detailedItems));
    }

    public CombineTask(int... ids) {
        for (int id : ids) {
            itemsList.add(new DetailedItem(id));
        }
    }

    public void addItem(int id) {
        addItem(id, 1);
    }

    public void addItem(int id, int amount) {
        this.itemsList.add(new DetailedItem(id, amount));
    }

    public Task getTask() {
        return new Task(getTaskCondition(), getTaskAction());
    }

    private TaskCondition getTaskCondition() {
        return new TaskCondition() {
            @Override
            public String getConditionName() {
                return "Have all items needed";
            }

            @Override
            public boolean check() {
                return itemsList.stream().allMatch(DetailedItem::isHaveOnCharacter);
            }
        };
    }

    private TaskAction getTaskAction() {
        int firstId = itemsList.get(0).getItemId();
        int secondId = itemsList.get(1).getItemId();
        return new ActionCombine(firstId, secondId);
    }

}
