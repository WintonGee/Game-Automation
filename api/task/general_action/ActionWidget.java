package scripts.main_package.api.task.general_action;

import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;
import scripts.main_package.api.task.TaskAction;
import scripts.main_package.api.task.TaskWaiting;

public class ActionWidget extends TaskAction {

    @Setter
    @Accessors(chain = true)
    String action, componentName;

    @Override
    public String getActionName() {
        String string = "Widget Action";
        if (action != null)
            string = string + ": " + action;
        if (componentName != null)
            string = string + ": " + componentName;
        return string;
    }

    @Override
    public boolean handleAction() {
        val widgetQuery = Query.widgets();
        if (action != null)
            widgetQuery.actionContains(action);
        if (componentName != null)
            widgetQuery.nameContains(componentName);
        return widgetQuery
                .stream()
                .findFirst()
                .map(Widget::click)
                .orElse(false);
    }

}
