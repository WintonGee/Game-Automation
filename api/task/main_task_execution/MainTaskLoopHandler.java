package scripts.main_package.api.task.main_task_execution;

import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.api.task.Task;
import scripts.main_package.api.task.TaskCondition;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.tasks.bank_task.DetailedBankTask;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

// Loops, most of time spent running is here.
public class MainTaskLoopHandler {

    MainTask mainTask;
    DetailedBankTask detailedBankTask;

    public MainTaskLoopHandler(MainTask mainTask) {
        this.mainTask = mainTask;

        ArrayList<DetailedItem> taskItems = new ArrayList<>(mainTask.inventoryItems);

        // Equipment Items
        if (mainTask.combatSetupTask != null) {
            taskItems.addAll(mainTask.combatSetupTask.getCurrentItems()
                    .stream()
                    .map(eq -> eq.getDetailedItem().getCopy().setEquipped(true))
                    .collect(Collectors.toCollection(ArrayList::new))
            );

            // TODO add equipment ammo and runes

        }

        detailedBankTask = new DetailedBankTask(taskItems);
        detailedBankTask.setEmptySlots(mainTask.emptySlots);



        //TODO determine location?
//        if (!mainTask.onlyTeleportOnce)
//            taskItems.add()
    }

    public boolean handle() {


        Log.info("[MainTaskLoopHandler] Starting task: " + mainTask.getActivityName());
        mainTask.onStart();

        //TODO travel on start

        // for tasks like fishing, woodcutting teleport on start, but also bring tele item?

        //TODO a boolean for must run bank task
        // for maybe wilderness tasks where the inventory must be set exactly
//        detailedBankTask.handle();


        int noTaskCount = 0;
        while (!shouldStopLooping()) {
            Waiting.waitUniform(100, 200); //TODO change this

            //TODO check for tasks such as death or muling?
            if (noTaskCount > 10) {
                Log.error("[MainTaskLoopHandler] Failed to determine the next task, stopping.");
                return false;
            }

            val nextTask = getNextTask();
            if (nextTask.isPresent()) {
                noTaskCount = 0;
                nextTask.ifPresent(Task::handleTask);
                continue;
            }

            noTaskCount++;

            // Banking
            if (!mainTask.onlyBankOnce && !detailedBankTask.isPossible()) {
                Log.warn("[MainTaskLoopHandler] Stopping task, not enough items for " + this.mainTask.activityName);
                return false;
            }

            if (!mainTask.onlyBankOnce && !detailedBankTask.isSatisfied() && !detailedBankTask.handle()) {
                Log.warn("[MainTaskLoopHandler] Stopping task, failed to handle bank task.");
                return false;
            }

        }

        mainTask.onEnd();
        return true;
    }

    private boolean shouldStopLooping() {
        return mainTask.taskEndConditions.stream().anyMatch(TaskCondition::check);
    }

    private Optional<Task> getNextTask() {
        return mainTask.taskList
                .stream()
                .filter(Task::isValid)
                .findFirst();
    }

}
