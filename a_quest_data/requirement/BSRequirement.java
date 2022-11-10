package scripts.main_package.a_quest_data.requirement;

import java.util.function.BooleanSupplier;

public class BSRequirement extends Requirement {

    BooleanSupplier booleanSupplier;

    public BSRequirement(BooleanSupplier booleanSupplier) {
        this.booleanSupplier = booleanSupplier;
    }

    @Override
    public boolean check() {
        return booleanSupplier.getAsBoolean();
    }
}
