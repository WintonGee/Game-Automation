package scripts.main_package.a_quest_data.step;

import org.tribot.script.sdk.interfaces.Clickable;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;

public class InventoryStep extends QuestStep {

    int invId;

    public InventoryStep(BasicQuestHelper basicQuestHelper, WorldPoint worldPoint, String description, ItemRequirement invReq) {
        super(basicQuestHelper, worldPoint, description);
        this.invId = invReq.getId();
        this.setInteractionType(InteractionType.CLICKABLE);
    }

    public InventoryStep(BasicQuestHelper basicQuestHelper, String description, ItemRequirement invReq) {
        super(basicQuestHelper, description);
        this.invId = invReq.getId();
        this.setInteractionType(InteractionType.CLICKABLE);
    }

    @Override
    public Clickable getClickable() {
        return Query.inventory().idEquals(invId).findFirst().orElse(null);
    }

}
