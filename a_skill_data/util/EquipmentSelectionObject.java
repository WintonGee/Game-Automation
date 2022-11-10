package scripts.main_package.a_skill_data.util;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Log;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.equipment.EquipmentItem;

@Data
@Accessors(chain = true)
public class EquipmentSelectionObject {

    boolean enabled = true;
    EquipmentItem equipmentItem;

    public EquipmentSelectionObject(EquipmentItem equipmentItem) {
        this.setEquipmentItem(equipmentItem);
    }

    @Override
    public String toString() {
        return Utils.getItemName(equipmentItem.getMainId());
    }

    @DoNotRename
    public String getEquipmentName() {
        return toString();
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
                Log.info("{" + getEquipmentName() + "} Enabled Setting: " + enabled);
            }
        }
    };

}
