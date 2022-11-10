package scripts.main_package.a_skill_data.util;

import com.allatori.annotations.DoNotRename;
import javafx.scene.control.MenuItem;

public enum SkillingLocationChoiceSelection {

    NEAREST("Use Nearest Location"),
    RANDOM("Use Random Location"),
//    SEEDED("Use Seeded Location")
    ;

    String locationType;

    SkillingLocationChoiceSelection(String locationType) {
        this.locationType = locationType;
    }

    @DoNotRename
    public MenuItem getMenuItem() {
        return new MenuItem(locationType);
    }

    @Override
    public String toString() {
        return locationType;
    }

}
