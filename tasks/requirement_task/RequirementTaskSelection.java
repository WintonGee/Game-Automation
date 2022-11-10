package scripts.main_package.tasks.requirement_task;

import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_quest_data.execution.QuestHelperQuestExecution;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.player.SkillRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestRequirement;
import scripts.main_package.api.other.UtilStat;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.api.task.MainTaskData;
import scripts.main_package.api.task.MainTaskUtil;

import java.util.Optional;
import java.util.stream.Collectors;

// This class is used for selecting the best task to progress a skill
// with some missing requirements
public class RequirementTaskSelection {

    RequirementTasks requirementTasks;

    public RequirementTaskSelection(RequirementTasks requirementTasks) {
        this.requirementTasks = requirementTasks;
    }

    //TODO things to set
    // determine stop level based on the previous best level/exp
    // set the min and max sets to the same thing
    // will need to worry about time limits

    public boolean loadMainTask() {
        Optional<MainTask> questingMainTask = getQuestingTask();
        if (questingMainTask.isPresent()) {
            updatePredictions(questingMainTask.get());
            return true;
        }

        Optional<MainTask> skillingMainTask = getSkillingTask();
        if (skillingMainTask.isPresent()) {
            updatePredictions(skillingMainTask.get());
            return true;
        }

        int remainingSkillsSize = requirementTasks.requirementTaskInfo.requiredSkillsInfo.size();
        String remainingSkills = requirementTasks.requirementTaskInfo.requiredSkillsInfo
                .keySet()
                .stream().map(Enum::toString)
                .collect(Collectors.joining(", "));

        int remainingQuestsSize = requirementTasks.requirementTaskInfo.requiredQuestsInfo.size();
        String remainingQuests = requirementTasks.requirementTaskInfo.requiredQuestsInfo
                .keySet()
                .stream().map(Enum::toString)
                .collect(Collectors.joining(", "));

        Log.warn("[RequirementTaskSelection] Missing data for " + remainingSkillsSize + " skills: " + remainingSkills);
        Log.warn("[RequirementTaskSelection] Missing data for " + remainingQuestsSize + " quests: " + remainingQuests);
        return false;
    }

    // Returns a main task for progressing a skill requirement
    private Optional<MainTask> getSkillingTask() {
        return new RequirementTaskSkillSelection(this).getSkillingTask();
    }

    // Returns a main task for completing a quest requirement
    private Optional<MainTask> getQuestingTask() {
        return MainTaskData.enabledQuestHelperList.stream()
                .filter(q -> this.requirementTasks.requirementTaskInfo.requiredQuestsInfo.containsKey(q.getQuest()))
                .map(QuestHelperQuest::getMainTask)
                .filter(this::isMainTaskRequirementsValidWithPredictedInfo)
                .findFirst();
    }

    // Returns if the main task is runnable with the predicted info
    public boolean isMainTaskRequirementsValidWithPredictedInfo(MainTask mainTask) {
        val requirementsList = mainTask.requirementsList;
        return requirementsList == null || requirementsList.stream().allMatch(this::isRequirementValidWithPredictedInfo);
    }

    // Returns if the requirement is valid using the predicted info.
    private boolean isRequirementValidWithPredictedInfo(Requirement requirement) {
        if (requirement instanceof QuestRequirement) {
            QuestRequirement questRequirement = (QuestRequirement) requirement;
            Quest quest = questRequirement.getQuest();
            Quest.State state = questRequirement.getQuestState();
            return this.requirementTasks.predictedQuestInfo.containsKey(quest) && this.requirementTasks.predictedQuestInfo.get(quest) == state;
        }

        if (requirement instanceof SkillRequirement) {
            SkillRequirement skillRequirement = (SkillRequirement) requirement;
            Skill skill = skillRequirement.getSkill();
            int level = skillRequirement.getLevel();
            int xpForLevel = UtilStat.getExperienceForLevel(level);
            return this.requirementTasks.predictedSkillsInfo.containsKey(skill) && this.requirementTasks.predictedSkillsInfo.get(skill) >= xpForLevel;
        }

        Log.warn("[RequirementTaskSelection] Could not determine Requirement: " + requirement);
        return false;
    }

    // Returns if a main task will yield a certain skill experience
    public boolean isMainTaskContainsSkillReward(Skill skill, MainTask mainTask) {
        return mainTask.experienceRewardsList
                .stream()
                .anyMatch(e -> e.getSkill() == skill);
    }

    public boolean isQuestPredictedAsComplete(Quest quest) {
        return this.requirementTasks.predictedQuestInfo.get(quest) == Quest.State.COMPLETE;
    }

    public void updatePredictions(MainTask mainTask) {
        addMainTaskToQueue(mainTask);
        int multiplier = mainTask.minItemSets;

        mainTask.experienceRewardsList.forEach(reward -> {
            Skill skill = reward.getSkill();
            double amount = reward.getAmount();
            int totalAmount = (int) Math.floor(amount * multiplier);
            updateSkillPredictions(skill, totalAmount);
        });

        if (mainTask instanceof QuestHelperQuestExecution) {
            updateQuestPredictions(((QuestHelperQuestExecution) mainTask).getQuest());
        }
    }

    private void updateQuestPredictions(Quest quest) {
        this.requirementTasks.predictedQuestInfo.put(quest, Quest.State.COMPLETE);
    }

    private void updateSkillPredictions(Skill skill, int addExp) {
        int currentExp = this.requirementTasks.predictedSkillsInfo.get(skill);
        int newExp = currentExp + addExp;
        int newLevel = UtilStat.getLevelAtExperience(newExp);
        this.requirementTasks.predictedSkillsInfo.put(skill, newExp);
        Log.debug("[RequirementTaskSelection] Updating skill predictions, Skill: " + skill + ", Xp Gain: " + addExp + ", New Level: " + newLevel);

    }

    private void addMainTaskToQueue(MainTask mainTask) {
        Log.info("[RequirementTaskSelection] Adding task to queue: " + mainTask.getActivityName()
                + ", Item Sets: " + mainTask.minItemSets + ", Profit: " + MainTaskUtil.getProfit(mainTask));
        this.requirementTasks.taskQueue.add(mainTask);
    }

}
