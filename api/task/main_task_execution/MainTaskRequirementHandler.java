package scripts.main_package.api.task.main_task_execution;

import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.item.UntradeableItem;
import scripts.main_package.item.equipment.EquipmentItem;
import scripts.main_package.tasks.requirement_task.RequirementTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Makes sure the task is performable
// - Have the requirements needed.
public class MainTaskRequirementHandler {

    MainTask mainTask;

    public MainTaskRequirementHandler(MainTask mainTask) {
        this.mainTask = mainTask;
    }

    //TODO requirements from untradeable items?
    public boolean handle() {
        String activityName = mainTask.getActivityName();
        RequirementTask requirementTask = new RequirementTask(activityName, getAllRequirements());
        return requirementTask.initRequirementTask() &&  requirementTask.loadInfo() && requirementTask.loadTasksNeeded() && requirementTask.executeTasks();
    }

    private ArrayList<Requirement> getAllRequirements() {
        ArrayList<Requirement> list = mainTask.requirementsList;
        list.addAll(getRequirementsForItems());
        return list;
    }

    private ArrayList<Requirement> getRequirementsForItems() {
        ArrayList<Requirement> list = new ArrayList<>();

        //TODO get reqs - only untradeable items

        // Notice: recursion will likely be needed in case the materials for untradeable items have requiremements

        return list;
    }

    // Returns all the items that can potentially have requirements
    private ArrayList<DetailedItem> getTaskItems() {

        ArrayList<DetailedItem> list = new ArrayList<>(mainTask.inventoryItems);

        // Adding the equipment items if any
        if (mainTask.combatSetupTask != null) {
            List<UntradeableItem> equipUntradeableItems = mainTask.combatSetupTask.getCurrentItems()
                    .stream()
                    .filter(o -> o.getUntradeableItemTask() != null)
                    .map(EquipmentItem::getUntradeableItemTask)
                    .map(UntradeableItem.UntradeableItemTask::getUntradeableItem)
                    .collect(Collectors.toList());

            list.addAll(equipUntradeableItems);
        }

        return list;
    }

}
