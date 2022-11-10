package scripts.main_package.api.task.general_condition;

import org.tribot.script.sdk.MakeScreen;
import scripts.main_package.api.task.TaskCondition;

public class ConditionMakescreen extends TaskCondition {
    @Override
    public boolean check() {
        return MakeScreen.isOpen();
    }
}
