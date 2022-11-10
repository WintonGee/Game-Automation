package scripts.main_package.tasks.requirement_task;

import lombok.Data;
import org.tribot.script.sdk.Log;
import scripts.main_package.a_quest_data.requirement.Requirement;

import java.util.ArrayList;

// This task will generate the data of tasks needed to fulfill
// the provided list of requirements
@Data
public class RequirementTask {

    String description;

    ArrayList<Requirement> requirementList;
    RequirementTaskInfo requirementTaskInfo;
    RequirementTasks requirementTasks;

    public RequirementTask(String description, ArrayList<Requirement> requirementList) {
        this.description = description;
        this.setRequirementList(requirementList);
    }

    // Attempts to run all the tasks in the queue.
    public boolean executeTasks() {
        return true;
    }

    public boolean isAllRequirementsComplete() {
        return requirementList.stream().allMatch(Requirement::check);
    }

    //TODO make this boolean and return false if the player is not logged in
    public boolean initRequirementTask() {
        Log.info("[RequirementTask] Loading Requirement Task Info for main task: " + description);
        requirementTaskInfo = new RequirementTaskInfo();
        requirementTaskInfo.loadInfo(requirementList);
        requirementTasks = new RequirementTasks(requirementTaskInfo);
        return true; //TODO set to logged in
    }

    // Loads the tasks required to fulfill all the requirements provided
    public boolean loadInfo() {
        return requirementTasks.loadPredictedInfo();
    }

    public boolean loadTasksNeeded() {
        return requirementTasks.loadTasks();
    }

    //TODO multiply by like 1.3? since not tick perfect
    public int getApproximatedRunTimeTicks() {
        return requirementTasks.taskQueue.stream()
                .mapToInt(o -> o.minItemSets * o.ticksPerActivity)
                .sum();
    }

}
