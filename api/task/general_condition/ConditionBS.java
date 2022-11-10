package scripts.main_package.api.task.general_condition;

import lombok.Setter;
import lombok.experimental.Accessors;
import scripts.main_package.api.task.TaskCondition;

import java.util.function.BooleanSupplier;

// For fast coding, use boolean supplier
public class ConditionBS extends TaskCondition {

    @Setter
    BooleanSupplier bs;

    @Setter
    @Accessors(chain = true)
    String name;

    public ConditionBS(BooleanSupplier bs) {
        this.setBs(bs);
    }

    @Override
    public String getConditionName() {
        return name;
    }

    @Override
    public boolean check() {
        return this.bs.getAsBoolean();
    }

}