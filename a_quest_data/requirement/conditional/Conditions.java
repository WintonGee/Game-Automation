package scripts.main_package.a_quest_data.requirement.conditional;

import lombok.Setter;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.util.LogicType;
import scripts.main_package.a_quest_data.requirement.util.Operation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conditions extends Requirement {

    List<Requirement> conditions = new ArrayList<>();

    protected boolean hasPassed;
    protected boolean onlyNeedToPassOnce;
    protected LogicType logicType;
    protected Operation operation;
    protected int quantity;

    @Setter
    protected String text;

    public Conditions(Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        logicType = LogicType.AND;
    }

    public Conditions(List<Requirement> conditions) {
        this.conditions = new ArrayList<>(conditions);
        logicType = LogicType.AND;
    }

    public Conditions(LogicType logicType, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.logicType = logicType;
    }

    public Conditions(Operation operation, int quantity, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.logicType = LogicType.AND;
        this.operation = operation;
        this.quantity = quantity;
    }

    public Conditions(LogicType logicType, List<Requirement> conditions) {
        this.conditions = new ArrayList<>(conditions);
        this.logicType = logicType;
    }

    public Conditions(boolean onlyNeedToPassOnce, Operation operation, int quantity, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.onlyNeedToPassOnce = onlyNeedToPassOnce;
        this.logicType = LogicType.AND;
        this.operation = operation;
        this.quantity = quantity;
    }

    public Conditions(boolean onlyNeedToPassOnce, LogicType logicType, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.onlyNeedToPassOnce = onlyNeedToPassOnce;
        this.logicType = logicType;
    }

    public Conditions(boolean onlyNeedToPassOnce, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.onlyNeedToPassOnce = onlyNeedToPassOnce;
        this.logicType = LogicType.AND;
    }

    @Override
    public boolean check() {
        if (onlyNeedToPassOnce && hasPassed) {
            return true;
        }

        int conditionsPassed = (int) conditions.stream().filter(c -> {
            if (c == null) {
                return true;
            }
            return c.check();
//            return c.check(client);
        }).count();

        if (operation != null) {
            return operation.check(conditionsPassed, quantity);
        }

        //TODO: Replace with LogicType check, however more testing to be done to make sure nothing breaks
        if ((conditionsPassed > 0 && logicType == LogicType.OR)
                || (conditionsPassed == 0 && logicType == LogicType.NOR)
                || (conditionsPassed == conditions.size() && logicType == LogicType.AND)
                || (conditionsPassed < conditions.size() && logicType == LogicType.NAND)) {

            hasPassed = true;
            return true;
        }

        return false;
    }

    @Override
    @Nonnull
    public String getDisplayText() {
        return text;
    }


}
