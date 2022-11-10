package scripts.main_package.a_skill_data.util;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import lombok.Data;
import org.tribot.script.sdk.Log;
import scripts.main_package.api.other.Utils;

import java.util.stream.Collectors;

@Data
public class SkillingLocationSelection {

    SkillingLocation location;
    boolean enabled = true;

    public SkillingLocationSelection(SkillingLocation location) {
        this.setLocation(location);
    }

    @DoNotRename
    public String getSkillAreaName() {
        return location.getDescription();
    }

    @DoNotRename
    public CheckBox getEnabledCheckBox() {
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(enableEvent);
        checkBox.setSelected(enabled);
        return checkBox;
    }


    @DoNotRename
    EventHandler enableEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox chk = (CheckBox) event.getSource();
                enabled = chk.isSelected();
                Log.info("{" + getSkillAreaName() + "} Enabled Setting: " + enabled);
            }
        }
    };

    @DoNotRename
    public String getLocationName() {
        return location.getDescription();
    }

    @DoNotRename
    public String getSkillAreaItemNames() {
        return location.interactableList.stream()
                .map(SkillingInteractable::getProductId)
                .map(Utils::getItemName)
                .distinct()
                .collect(Collectors.joining(", "));
    }

}
