package scripts.main_package.a_quest_data.step;

import org.tribot.script.sdk.interfaces.Clickable;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;

public class CombineStep extends QuestStep {

    int invId;

    public CombineStep(BasicQuestHelper basicQuestHelper, String description, ItemRequirement firstItem, ItemRequirement secondItem) {
        super(basicQuestHelper, description);
        this.setUseItemId(firstItem.getId());
        this.invId = secondItem.getId();
        this.setInteractionType(InteractionType.CLICKABLE);
    }

    @Override
    public Clickable getClickable() {
        return Query
                .inventory()
                .idEquals(invId)
                .findFirst()
                .orElse(null);
    }

}
