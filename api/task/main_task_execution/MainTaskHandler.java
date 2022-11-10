package scripts.main_package.api.task.main_task_execution;

import lombok.Data;
import org.tribot.script.sdk.Log;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.tasks.requirement_task.RequirementTask;

@Data
public class MainTaskHandler {

    MainTask mainTask;

    public MainTaskHandler(MainTask mainTask) {
        this.setMainTask(mainTask);

        this.mainTask.load();
    }

    public boolean handle() {
        String activityName = mainTask.getActivityName();
        Log.info("[MainTaskHandler] Main task chosen: " + activityName);

        // Requirements handling
        RequirementTask requirementTask = new RequirementTask(activityName, mainTask.requirementsList);
        if (!requirementTask.initRequirementTask() || !requirementTask.loadInfo()
                || !requirementTask.loadTasksNeeded() || !requirementTask.executeTasks()) {
            Log.error("[MainTaskHandler] Failed to handle requirements for task: " + mainTask.getActivityName());
            return false;
        }

        //TODO need to put in teleport item?
        if (mainTask.combatSetupTask != null)
            mainTask.combatSetupTask.loadRemainingItems();

        if (!new MainTaskStartingItemsHandler(mainTask).handle()) {
            Log.error("[MainTaskHandler] Failed to handle starting items for task: " + mainTask.getActivityName());
            return false;
        }

        return new MainTaskLoopHandler(mainTask).handle();
    }


}
