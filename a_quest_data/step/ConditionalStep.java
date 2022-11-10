package scripts.main_package.a_quest_data.step;

import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.RequirementQuestStep;
import scripts.main_package.a_quest_data.requirement.Requirement;

public class ConditionalStep extends QuestStep {

    protected boolean started = false;

    protected QuestStep currentStep;
    protected Requirement[] requirements;

    public ConditionalStep(BasicQuestHelper questHelper, QuestStep step, Requirement... requirements) {
        super(questHelper);
        this.requirements = requirements;
        this.defaultQuestStep = step;
    }

    public ConditionalStep(BasicQuestHelper questHelper, QuestStep step, String text, Requirement... requirements) {
        super(questHelper, text);
        this.requirements = requirements;
        this.defaultQuestStep = step;
    }

    public void addStep(Requirement requirement, QuestStep step) {
        addStep(requirement, step, false);
    }

    public void addStep(Requirement requirement, QuestStep step, boolean isLockable) {
        step.setLockable(isLockable);
        this.requirementQuestSteps.add(new RequirementQuestStep(requirement, step));
//        checkForConditions(requirement);
    }

}
