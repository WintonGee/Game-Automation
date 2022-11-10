package scripts.main_package.tasks.requirement_task;

import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import scripts.main_package.api.other.UtilTime;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.api.task.MainTaskUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

// Used for determining the tasks needed to get to the TaskInfo
@Data
public class RequirementTasks {

    RequirementTaskInfo requirementTaskInfo;

    LinkedHashMap<Skill, Integer> predictedSkillsInfo;
    LinkedHashMap<Quest, Quest.State> predictedQuestInfo;

    //TODO current items field
    // includes everything, ge, bank, inv, equip

    // have a field for selling items too?

    // Can later iterate through each one and choose whichever is valid.
    // Also check the stop condition first before starting anything from the task.
    // Note: There will always be a valid task in the queue, usually the best or quest
    ArrayList<MainTask> taskQueue = new ArrayList<>();

    public RequirementTasks(RequirementTaskInfo requirementTaskInfo) {
        this.requirementTaskInfo = requirementTaskInfo;
    }

    public boolean loadPredictedInfo() {
        predictedSkillsInfo = new LinkedHashMap<>();
        predictedQuestInfo = new LinkedHashMap<>();
        for (Skill skill : Skill.values())
            predictedSkillsInfo.put(skill, skill.getXp());

        for (Quest quest : Quest.values())
            predictedQuestInfo.put(quest, quest.getState());

        return true;
    }

    public boolean loadTasks() {
        RequirementTaskSelection requirementTask = new RequirementTaskSelection(this);
        while (!isAllSkillPredictionComplete() || !isAllQuestPredictionComplete()) {
            Waiting.waitUniform(100, 200); // Slow down the debug
            removeCompletePredictions();

            if (!requirementTask.loadMainTask()) {
                Log.error("[RequirementTasks] Failed to select tasks to complete all requirements");
                //TODO enum for why it failed to select a task
                // - Not enough money -> do moneymaking tasks?
                // - No valid data
                return false;
            }
        }

        // Printing out
        long estimatedRuntime = taskQueue.stream().mapToLong(MainTaskUtil::getExpectedRuntimeMillis).sum();
        String runTimeString = UtilTime.getTimeString(estimatedRuntime);
        int estimatedProfit = taskQueue.stream().mapToInt(MainTaskUtil::getProfit).sum();
        Log.info("[RequirementTasks] Loaded all tasks successfully! Number of tasks: " + taskQueue.size()
                + ", Estimated Runtime: " + runTimeString + ", Profit: " + Utils.getStringAddedCommaToNumber(estimatedProfit));
        return true;
    }

    private void removeCompletePredictions() {

        // Skills
        val skillIter = requirementTaskInfo.requiredSkillsInfo.entrySet().iterator();
        while (skillIter.hasNext()) {
            val next = skillIter.next();
            Skill skill = next.getKey();
            if (isSkillPredictionComplete(skill)) {
                Log.warn("[RequirementTasks] Predictions complete for skill: " + skill);
                skillIter.remove();
            }
        }

        // Quests
        val questIter = requirementTaskInfo.requiredQuestsInfo.entrySet().iterator();
        while (questIter.hasNext()) {
            val next = questIter.next();
            Quest quest = next.getKey();
            if (isQuestPredictionComplete(quest)) {
                Log.warn("[RequirementTasks] Predictions complete for quest: " + quest);
                questIter.remove();
            }
        }
    }

    private boolean isAllSkillPredictionComplete() {
        return getSkillWithIncompletePrediction() == null;
    }

    private boolean isAllQuestPredictionComplete() {
        return getQuestWithIncompletePrediction() == null;
    }

    private boolean isSkillPredictionComplete(Skill skill) {
        if (!requirementTaskInfo.requiredSkillsInfo.containsKey(skill))
            return true;
        int expReq = requirementTaskInfo.requiredSkillsInfo.get(skill);
        int predictedExp = predictedSkillsInfo.get(skill);
        return predictedExp >= expReq;
    }

    private boolean isQuestPredictionComplete(Quest quest) {
        if (!requirementTaskInfo.requiredQuestsInfo.containsKey(quest))
            return true;
        return predictedQuestInfo.get(quest) == Quest.State.COMPLETE;
    }

    public Skill getSkillWithIncompletePrediction() {
        val list = requirementTaskInfo.requiredSkillsInfo.keySet()
                .stream()
                .filter(s -> !isSkillPredictionComplete(s))
                .collect(Collectors.toList());
        Collections.shuffle(list);
        return list.size() == 0 ? null : list.get(0);
    }

    private Quest getQuestWithIncompletePrediction() {
        val list = requirementTaskInfo.requiredQuestsInfo.keySet()
                .stream()
                .filter(q -> !isQuestPredictionComplete(q))
                .collect(Collectors.toList());
        Collections.shuffle(list);
        return list.size() == 0 ? null : list.get(0);
    }


}
