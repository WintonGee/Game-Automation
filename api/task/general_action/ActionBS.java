package scripts.main_package.api.task.general_action;

import lombok.Setter;
import lombok.experimental.Accessors;
import scripts.main_package.api.task.TaskAction;
import scripts.main_package.api.task.TaskCondition;
import scripts.main_package.api.task.TaskWaiting;

import java.util.function.BooleanSupplier;

// For fast coding, use Boolean Supplier
public class ActionBS extends TaskAction {


    @Setter @Accessors(chain = true)
    BooleanSupplier bs;

    public ActionBS(BooleanSupplier bs) {
        this.setBs(bs);
    }


    @Override
    public boolean handleAction() {
        return bs.getAsBoolean();
    }

}
