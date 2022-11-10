package scripts.main_package.a_quest_data.execution;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import scripts.main_package.a_quest_data.QuestInformation;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.ExperienceReward;
import scripts.main_package.a_quest_data.step.QuestStep;
import scripts.main_package.a_quest_data.step_handler.QuestStepHandler;
import scripts.main_package.api.paint.PaintData;
import scripts.main_package.api.task.MainTask;
import scripts.main_package.api.task.TaskAction;
import scripts.main_package.api.task.TaskCondition;
import scripts.main_package.api.task.general_action.ActionBS;
import scripts.main_package.api.task.general_condition.ConditionBS;

import java.util.List;
import java.util.Map;

// Maybe rewrite this class to use the general main task item setup?
// The questing one works fine, but it's better to not use two.
public class QuestHelperQuestExecution extends MainTask {

    @Setter
    @Getter
    boolean isTesting = false;

    @Setter
    @Getter
    private int LOOPS = 100; // Should set to higher number on longer quests.

    @Setter
    @Getter
    private QuestInformation questInformation;

    @Setter
    @Getter
    private BasicQuestHelper basicQuestHelper;
    @Setter
    @Getter
    private Quest quest;
    @Setter
    @Getter
    private String name;

    public QuestHelperQuestExecution(QuestInformation questInformation) {
        super(questInformation.getQuest().name());
        this.questInformation = questInformation;

        basicQuestHelper = questInformation.getBasicQuestHelper();
        quest = questInformation.getQuest();
        name = quest.name();
    }

    @Override
    public @NonNull String getActivityName() {
        return "Questing: " + name;
    }

    @Override
    public void load() {

        // Stop condition
        this.addTaskEndCondition(new ConditionBS(() -> questInformation.isComplete()));

        // Load Requirements
        val reqList = basicQuestHelper.getGeneralRequirements();
        if (reqList != null)
            this.requirementsList.addAll(reqList);

        // Execution
        TaskCondition condition = new ConditionBS(() -> true); //TODO something better
        TaskAction action = new ActionBS(this::handle);
        this.addTask(condition, action);

        // Rewards
        List<ExperienceReward> expRewardList = basicQuestHelper.getExperienceRewards();
        if (expRewardList != null)
            this.experienceRewardsList.addAll(expRewardList);
    }

    public boolean handle() {
        PaintData.setActivity("Questing " + name);
        Map<Integer, QuestStep> stepMap = basicQuestHelper.loadSteps();
        if (!isTesting && !handleStartingItems()) {
            Log.error("[QuestHelperQuestExecution] Failed to handle starting items for quest; " + name);
            return false;
        }

        for (int i = 0; i < LOOPS; i++) {
            if (questInformation.isComplete()) {
                Log.info("[QuestHelperQuestExecution] Quest Complete: " + name);
                handleEnding();
                return true;
            }
            handleSteps(stepMap);
            Waiting.waitUniform(50, 100);
        }

        return false;
    }

    private void handleSteps(Map<Integer, QuestStep> stepMap) {
        for (val entry : stepMap.entrySet()) {
            int stepVal = entry.getKey();
            int currentStep = quest.getStep();

            if (stepVal == currentStep) {
                QuestStep questStep = entry.getValue();
                Log.info("[" + name + "] Doing Step: " + stepVal + " " + questStep.getDescription());
                new QuestStepHandler(quest, stepVal, questStep).handle();
            }
        }
    }

    private void handleEnding() {
        QuestEndingHandler endingHandler = new QuestEndingHandler(basicQuestHelper);
        endingHandler.handle();
    }

    // Gets all the items needed for this quest
    public boolean handleStartingItems() {
        return new QuestStartingItemsHandler(basicQuestHelper).handle();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }
}
