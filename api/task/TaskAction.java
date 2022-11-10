package scripts.main_package.api.task;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public abstract class TaskAction {

    String actionName;

    // Used for after a successful action
    TaskWaiting taskWaiting;

    public abstract boolean handleAction();

}
