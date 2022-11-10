package scripts.main_package.a_quest_data.requirement;

import lombok.Data;

@Data
public abstract class Requirement {

    String tooltip;

    public abstract boolean check();

    public String getDisplayText() {
        return null;
    }

}
