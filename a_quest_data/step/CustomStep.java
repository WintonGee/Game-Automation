package scripts.main_package.a_quest_data.step;

import scripts.main_package.a_quest_data.framework.BasicQuestHelper;


public abstract class CustomStep extends QuestStep {

    public CustomStep(BasicQuestHelper questHelper) {
        super(questHelper);
    }

    public abstract void handle();

}
