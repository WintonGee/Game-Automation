package scripts.main_package.tasks.requirement_task;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_quest_data.objects.ExperienceReward;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.player.SkillRequirement;
import scripts.main_package.api.other.UtilStat;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.api.task.MainTaskData;
import scripts.main_package.api.task.general_condition.ConditionBS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

// This class will be used for selecting a task that yields experience for a certain skill.
public class RequirementTaskSkillSelection {

    RequirementTaskSelection requirementTaskSelection;

    public RequirementTaskSkillSelection(RequirementTaskSelection requirementTaskSelection) {
        this.requirementTaskSelection = requirementTaskSelection;
    }

    //TODO make this into arraylist of tasks instead?
    //TODO optimize this whole task?
    public Optional<MainTask> getSkillingTask() {
        Skill skill = this.requirementTaskSelection.requirementTasks.getSkillWithIncompletePrediction();
        if (skill == null) {
            Log.error("[RequirementTaskSkillSelection] Failed to determine an incomplete skill prediction");
            return Optional.empty();
        }

        ArrayList<MainTask> potentialTasks = getAllPotentialTasks(skill);
        ArrayList<MainTask> potentialQuests = getAllPotentialQuests(skill);

        ArrayList<MainTask> potentialList = new ArrayList<>();
        potentialList.addAll(potentialTasks);
        potentialList.addAll(potentialQuests);
        sortListByScore(potentialList, skill);

        int currentExp = this.requirementTaskSelection.requirementTasks.predictedSkillsInfo.get(skill);
        int requiredExp = this.requirementTaskSelection.requirementTasks.requirementTaskInfo.requiredSkillsInfo.get(skill);
        int currentLevel = UtilStat.getLevelAtExperience(currentExp);
        int requiredLevel = UtilStat.getLevelAtExperience(requiredExp);

        Log.info("[RequirementTaskSkillSelection] " + skill
                + ", Predicted Level: " + currentLevel + ", Required: " + requiredLevel
                + ", Potential Tasks: " + potentialTasks.size() + ", Quests: " + potentialQuests.size()
        );

        //TODO might need to rewrite how the stop level is determined
        int stopLevel = requiredLevel;
        for (MainTask mainTaskTemp : potentialList) { //TODO need to get copy of maintask somehow
            MainTask mainTask = mainTaskTemp.getCopy();
            if (isSkillRequirementInTaskValid(mainTask, skill) && setTasksToFulfillTaskRequirements(mainTask)) {
                int expAtStopLevel = UtilStat.getExperienceForLevel(stopLevel);
                double xpNeeded = expAtStopLevel - currentExp;
                double xpPerActivity = getExpYield(mainTask, skill);

                int activitiesNeeded = (int) Math.ceil(xpNeeded / xpPerActivity);
                int maxActivities = Math.min(activitiesNeeded, mainTask.maxItemSets);
                int predictedExpAfterTask = currentExp + (int) Math.floor((maxActivities * xpPerActivity));

                mainTask.addTaskEndCondition(new ConditionBS(() -> skill.getXp() >= predictedExpAfterTask));

                mainTask.maxItemSets = maxActivities;
                mainTask.minItemSets = maxActivities;

                return Optional.of(mainTask);
            }

            stopLevel = Math.min(stopLevel, getTaskRequirementLevel(mainTask, skill));
        }

        return Optional.empty();
    }

    // Returns the tasks that will give the skill experience needed.
    private ArrayList<MainTask> getAllPotentialTasks(Skill skill) {
        return MainTaskData.enabledSkillTaskList
                .stream()
                .filter(o -> requirementTaskSelection.isMainTaskContainsSkillReward(skill, o))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Returns a list of quests that are not predict as complete and contains skill reward
    private ArrayList<MainTask> getAllPotentialQuests(Skill skill) {
        return MainTaskData.enabledQuestHelperList
                .stream()
                .filter(o -> !requirementTaskSelection.isQuestPredictedAsComplete(o.getQuest())) // Quest not complete
                .map(QuestHelperQuest::getMainTask)
                .filter(o -> requirementTaskSelection.isMainTaskContainsSkillReward(skill, o)) // Contains skill exp
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private double getScore(MainTask mainTask, Skill skill) {
        double expScore = getExpScore(mainTask, skill), costScore = getCostScore(mainTask);
//        Log.info("[RequirementTaskSkilLSelection] " + mainTask.getActivityName() + ", Exp Score: " + expScore + ", Cost Score: " + costScore);
        return expScore + costScore;
    }

    // Returns a score based on the amount of exp an activity yields
    // Calculated based on exp per time unit
    private double getExpScore(MainTask mainTask, Skill skill) {
        double expYield = getExpYield(mainTask, skill);
        return expYield / mainTask.ticksPerActivity;
    }

    // Returns a score based on the cost it takes to perform an activity
    private double getCostScore(MainTask mainTask) {
        // multiplier * non deplete
        // higher multiplier * deplete?
        return 0;
    }

    // Returns the score based on the missing requirements for this task
    // The score will rely on the time it takes to complete the requirements.
    private double getRequirementScore(MainTask mainTask) {
        //TODO get all the missing requirements, make an estimate time function
        return 0;
    }

    private double getExpYield(MainTask mainTask, Skill skill) {
        return mainTask.experienceRewardsList
                .stream()
                .filter(experienceReward -> experienceReward.getSkill() == skill)
                .mapToDouble(ExperienceReward::getAmount)
                .sum();
    }

    // Returns the skill level requirement in main task
    private int getTaskRequirementLevel(MainTask mainTask, Skill skill) {
        return mainTask.requirementsList
                .stream()
                .filter(r -> r instanceof SkillRequirement)
                .map(r -> (SkillRequirement) r)
                .filter(r -> r.getSkill() == skill)
                .findFirst()
                .map(SkillRequirement::getLevel)
                .orElse(1);
    }

    // Gets the tasks needed to fulfill to requirements for mainTask
    private ArrayList<MainTask> getTasksToCompleteRequirements(MainTask mainTask) {
        String activityName = mainTask.getActivityName();
        ArrayList<Requirement> reqList = mainTask.requirementsList;
        RequirementTask requirementTask = new RequirementTask(activityName, reqList);

        if (!requirementTask.initRequirementTask()) {
            Log.error("[RequirementTaskSkillSelection] Unable to init requirement task for: " + activityName);
            return null;
        }

//        requirementTask.requirementTasks.predictedQuestInfo = new LinkedHashMap<>();
//        requirementTask.requirementTasks.predictedQuestInfo = new LinkedHashMap<>();
        requirementTask.requirementTasks.predictedQuestInfo = this.requirementTaskSelection.requirementTasks.predictedQuestInfo;
        requirementTask.requirementTasks.predictedSkillsInfo = this.requirementTaskSelection.requirementTasks.predictedSkillsInfo;

        if (!requirementTask.loadTasksNeeded()) {
            Log.error("[RequirementTaskSkillSelection] Unable to load requirement task for: " + activityName);
            return null;
        }

        return requirementTask.getRequirementTasks().getTaskQueue();
    }

    // Returns if the skill requirement for skill is valid with predicted info in main task.
    // For example: if there is a skill requirement, lv 20 herblore
    // - Predicted herblore level = 10, return false.
    // - Predicted herblore level = 20+, return true.
    private boolean isSkillRequirementInTaskValid(MainTask mainTask, Skill skill) {
        int reqLevelInMainTask = getTaskRequirementLevel(mainTask, skill);
        int currentPredictedExperience = this.requirementTaskSelection.requirementTasks.predictedSkillsInfo.get(skill);
        int currentPredictedLevel = UtilStat.getLevelAtExperience(currentPredictedExperience);
        return currentPredictedLevel >= reqLevelInMainTask;
    }

    private boolean setTasksToFulfillTaskRequirements(MainTask mainTask) {
        if (this.requirementTaskSelection.isMainTaskRequirementsValidWithPredictedInfo(mainTask))
            return true;

        ArrayList<MainTask> tasks = getTasksToCompleteRequirements(mainTask);
        if (tasks == null) {
            Log.error("[RequirementTaskSkillSelection] Error with: setTasksToFulfillTaskRequirements: " + mainTask.activityName);
            return false;
        }

        tasks.forEach(t -> this.requirementTaskSelection.updatePredictions(t));
        return true;
    }

    private void sortListByScore(ArrayList<MainTask> list, Skill skill) {
        list.sort(Comparator.comparingDouble(o -> this.getScore(o, skill)));
        Collections.reverse(list);
    }

}
