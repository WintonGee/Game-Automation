package scripts.main_package.api.task.general_condition;

import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.api.task.TaskCondition;


public class ConditionWidget extends TaskCondition {

    @Setter @Accessors(chain = true)
    String action, componentName;

    @Override
    public String getConditionName() {
        return "Widget Condition";
    }

    @Override
    public boolean check() {
        val widgetQuery = Query.widgets();
        if (action != null)
            widgetQuery.actionContains(action);
        if (componentName != null)
            widgetQuery.nameContains(componentName);
        return widgetQuery.isAny();
    }

}
