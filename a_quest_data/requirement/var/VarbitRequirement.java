package scripts.main_package.a_quest_data.requirement.var;

import lombok.Data;
import org.tribot.script.sdk.GameState;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.util.Operation;

import java.math.BigInteger;
import java.util.Locale;

@Data
public class VarbitRequirement extends Requirement {

    private final int varbitID;
    private final int requiredValue;
    private final Operation operation;
    private final String displayText;

    // bit positions
    private boolean bitIsSet = false;
    private int bitPosition = -1;

    public VarbitRequirement(int varbitID, Operation operation, int requiredValue, String displayText) {
        this.varbitID = varbitID;
        this.operation = operation;
        this.requiredValue = requiredValue;
        this.displayText = displayText;
//        shouldCountForFilter = true;
    }

//    public VarbitRequirement(Varbits varbit, Operation operation, int requiredValue, String displayText) {
//        this(varbit.getId(), operation, requiredValue, displayText);
//    }

    public VarbitRequirement(int varbitID, int value) {
        this(varbitID, Operation.EQUAL, value, null);
    }

    public VarbitRequirement(int varbitID, int value, Operation operation) {
        this(varbitID, operation, value, null);
    }

    public VarbitRequirement(int varbitID, boolean bitIsSet, int bitPosition) {
        this.varbitID = varbitID;
        this.requiredValue = -1;
        this.operation = Operation.EQUAL;

        this.bitPosition = bitPosition;
        this.bitIsSet = bitIsSet;
        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        String text = String.valueOf(bitPosition);
        switch (bitPosition % 100) {
            case 11:
            case 12:
            case 13:
                text += "th";
            default:
                text = bitPosition + suffixes[bitPosition % 10];
        }
        this.displayText = varbitID + " must have the " + text + " bit set.";

//        shouldCountForFilter = true;
    }

    @Override
    public boolean check() {
        int varbitValue = GameState.getVarbit(varbitID);
        if (bitPosition >= 0) {
            return bitIsSet == BigInteger.valueOf(varbitValue).testBit(bitPosition);
        }

        return operation.check(varbitValue, requiredValue);
    }

    @Override
    public String getDisplayText() {
        if (displayText != null) {
            return displayText;
        }
        if (bitPosition >= 0) {
            return varbitID + " must have the " + bitPosition + " bit set.";
        }
        return varbitID + " must be + " + operation.name().toLowerCase(Locale.ROOT) + " " + requiredValue;
    }

}
