package scripts.main_package.a_quest_data.execution;

import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;

import java.util.List;

public class QuestEndInterfaceHandler {

    private final String closeAction = "Close";

    public boolean isComplete() {
        return getCloseWidget().size() == 0;
    }

    public boolean handle() {
        for (int i = 0; i < 5; i++) {
            if (isComplete()) {
                return true;
            }

            getCloseWidget().forEach(widget -> widget.click(closeAction));
            Waiting.waitUniform(600, 800);
        }
        return false;
    }

    public List<Widget> getCloseWidget() {
        return Query.widgets().actionContains(closeAction).toList();
    }

}
