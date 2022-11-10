package scripts.main_package.a_skill_data.util;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import scripts.main_package.api.other.Utils;

@Data
@Accessors(chain = true)
public class SkillingItemSelection {

    Skill skill;
    int maxLevel = 99;
    boolean buyFromGe = true;
    boolean bankItems = true;
    boolean enabled = true;
    int productId;

    public SkillingItemSelection(int productId) {
        this.setProductId(productId);
    }

    public boolean isMembers() {
        return Utils.isMembers(productId);
    }

    // These getter functions below are for GUI purposes.
    @DoNotRename
    public String getSkillItemName() {
        return Utils.getItemName(productId);
    }

    @DoNotRename
    public TextField getMaxLevelTextfield() {
        TextField textField = new TextField();
        textField.setText(String.valueOf(maxLevel));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String numberOnly = newValue.replaceAll("[^0-9]", "");
            Log.info(getSkillItemName() + " Max Level From " + oldValue + " to " + numberOnly);
            if (numberOnly.length() > 0)
                setMaxLevel(Integer.parseInt(numberOnly));
        });
        return textField;
    }

    @DoNotRename
    public CheckBox getBuyFromGeCheckBox() {
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(buyFromGeEvent);
        checkBox.setSelected(buyFromGe);
        return checkBox;
    }

    @DoNotRename
    public CheckBox getBankItemsCheckBox() {
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(bankEvent);
        checkBox.setSelected(enabled);
        return checkBox;
    }

    @DoNotRename
    public CheckBox getEnabledCheckBox() {
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(enableEvent);
        checkBox.setSelected(enabled);
        return checkBox;
    }

    @DoNotRename
    EventHandler buyFromGeEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox chk = (CheckBox) event.getSource();
                val selected = chk.isSelected();
                buyFromGe = selected;
                Log.info("{" + getSkillItemName() + "} Buy From Ge Setting: " + buyFromGe);
            }
        }
    };

    @DoNotRename
    EventHandler bankEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox chk = (CheckBox) event.getSource();
                val selected = chk.isSelected();
                bankItems = selected;
                Log.info("{" + getSkillItemName() + "} Bank Items Setting: " + bankItems);
            }
        }
    };

    @DoNotRename
    EventHandler enableEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox chk = (CheckBox) event.getSource();
                val selected = chk.isSelected();
                enabled = selected;
                Log.info("{" + getSkillItemName() + "} Enabled Setting: " + enabled);
            }
        }
    };

}
