package scripts.main_package.a_quest_data.step_handler;

import lombok.Setter;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import scripts.main_package.a_quest_data.objects.RequirementQuestStep;
import scripts.main_package.a_quest_data.step.QuestStep;

import java.util.ArrayList;

public class SubStepsHandler {

    @Setter
    QuestStep questStep;

    ArrayList<RequirementQuestStep> steps;

    public SubStepsHandler(QuestStep questStep) {
        this.setQuestStep(questStep);
        steps = questStep.getRequirementQuestSteps();
    }

    public boolean handle(Quest quest, int val) {
        if (steps.size() == 0) { // Stop Condition for recursion
            return true;
        }

        Log.info("[SubStepsHandler] Handling Sub Step");
        new QuestStepHandler(quest, val, getStepToHandle()).handle();
        // Need to return false because a substep is being handled.
        return false;
    }

    private QuestStep getStepToHandle() {
        Log.info("[SubStepsHandler] Number of sub steps: " + steps.size());
        return steps.stream()
                .filter(step -> step.getRequirement().check())
                .findFirst()
                .map(map -> map.getQuestStep())
                .orElse(questStep.getDefaultQuestStep());
    }

}
