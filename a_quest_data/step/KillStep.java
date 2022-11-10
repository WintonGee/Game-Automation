package scripts.main_package.a_quest_data.step;

import lombok.Getter;
import lombok.Setter;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.api.task.TaskCondition;

// Will always be used as a sub-step for conditional step.
@Setter
@Getter
public class KillStep extends CustomStep {

    int attempts;

    //TODO bank task needed to set up for fight?

    //TODO condition if no prayer
    // prayer
    // equipment

    //TODO safespot stuff

    // boolean or task condition for food

    ItemRequirement loot;

    //TODO have loot item, npc killed
    TaskCondition completeCondition;


    public KillStep(BasicQuestHelper questHelper) {
        super(questHelper);
    }

    @Override
    public void handle() {

    }


    /** TODO determine what the default step will be
     - First condition, check for loot
     */

    /** Steps to determine
     *  Talking to start the fight?
     *  Attacking the npcs - Handles prayer eating potting
     *
     */

}
