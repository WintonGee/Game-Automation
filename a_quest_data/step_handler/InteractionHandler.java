package scripts.main_package.a_quest_data.step_handler;

import lombok.Data;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.interfaces.Actionable;
import org.tribot.script.sdk.interfaces.Clickable;
import org.tribot.script.sdk.interfaces.Interactable;
import scripts.main_package.a_quest_data.step.QuestStep;

import java.util.List;

@Data
public class InteractionHandler {

    QuestStep questStep;

    public InteractionHandler(QuestStep questStep) {
        this.setQuestStep(questStep);
    }

    public boolean handle() {
        QuestStep.InteractionType interactionType = questStep.getInteractionType();
        if (interactionType == QuestStep.InteractionType.NONE)
            return true;

        if (interactionType == QuestStep.InteractionType.INTERACTABLE && !handleInteractable()) {
            Log.warn("[InteractionHandler] Failed to handle interactable");
            return false;
        }

        if (interactionType == QuestStep.InteractionType.CLICKABLE && !handleClickable()) {
            Log.warn("[InteractionHandler] Failed to handle clickable");
            return false;
        }

//        Log.error("[InteractionHandler] Failed to determine the interaction type.");
        return true;
    }

    private boolean handleInteractable() {
        Interactable interactable = questStep.getInteractable();
        if (interactable == null) {
            Log.warn("[InteractionHandler] Failed to handle interactable, None detected");
            return false;
        }
        String actionName = questStep.getActionName();
//        return actionName == null ? interactable.interact(getAction(interactable)) : interactable.interact(actionName);
        return actionName == null ? interactable.interact(null) : interactable.interact(actionName);
    }

    private boolean handleClickable() {
        Clickable clickable = questStep.getClickable();
        if (clickable == null) {
            Log.warn("[InteractionHandler] Failed to handle clickable, None detected");
            return false;
        }
        String actionName = questStep.getActionName();
//        return actionName == null ? clickable.click(getAction(clickable)) : clickable.click(actionName);
        return actionName == null ? clickable.click() : clickable.click(actionName);
    }

    public String getAction(Interactable interactable) {
        if (interactable instanceof Clickable) {
            return getAction((Clickable) interactable);
        }
        Log.error("[InteractionHandler] Failed to determine click action for interactable.");
        return null;
    }

    public String getAction(Clickable clickable) {
        if (clickable instanceof Actionable) {
            Actionable actionable = (Actionable) clickable;
            List<String> actions = actionable.getActions();
            boolean isValid = actions != null && actions.size() > 0;
            return isValid ? actions.get(0) : "";
        }
        Log.error("[InteractionHandler] Failed to determine click action for clickable.");
        return null;
    }

}
