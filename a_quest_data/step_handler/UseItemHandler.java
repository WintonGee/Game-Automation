package scripts.main_package.a_quest_data.step_handler;

import lombok.Data;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.main_package.a_quest_data.step.QuestStep;
import scripts.main_package.api.other.Utils;

import java.util.Optional;

@Data
public class UseItemHandler {

    QuestStep questStep;

    public UseItemHandler(QuestStep questStep) {
        this.setQuestStep(questStep);
    }

    public boolean handle() {
        int id = questStep.getUseItemId();
        String name = questStep.getUseItemName();
        if (id == questStep.getINVALID_ID() && name == null)
            return true;

        Log.info("[UseItemHandler] Selecting Item: " + Utils.getItemName(id) + " " + name);
        return getUseItem().map(InventoryItem::ensureSelected).orElse(false);
    }

    private Optional<InventoryItem> getUseItem() {
        int id = questStep.getUseItemId();
        String name = questStep.getUseItemName();

        boolean useId = id != questStep.getINVALID_ID();
        if (useId)
            return Query.inventory().idEquals(id).findFirst();
        return Query.inventory().nameEquals(name).findFirst();
    }

}
