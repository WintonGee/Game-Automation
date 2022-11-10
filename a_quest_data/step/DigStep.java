package scripts.main_package.a_quest_data.step;

import lombok.Setter;
import org.tribot.script.sdk.interfaces.Clickable;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.raw_data.ItemID;

public class DigStep extends QuestStep {

    private final ItemRequirement SPADE = new ItemRequirement("Spade", ItemID.SPADE);

    @Setter
    int expectedId = -1;

    public DigStep(BasicQuestHelper questHelper, WorldPoint worldPoint, String text, Requirement... requirements) {
        super(questHelper, worldPoint, text, requirements);
        this.getRequirements().add(SPADE);

        this.setInteractionType(InteractionType.CLICKABLE);
        this.setUseExactTile(true);
    }

    public void setExpectedItem(int itemID) {
        this.setExpectedId(itemID);
    }

    @Override
    public Clickable getClickable() {
        return Query.inventory().idEquals(ItemID.SPADE).findFirst().orElse(null);
    }


}
