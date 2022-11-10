package scripts.main_package.a_skill_data.util;

import com.allatori.annotations.DoNotRename;
import com.google.gson.annotations.SerializedName;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import lombok.Data;
import lombok.Setter;
import org.tribot.script.sdk.Log;

// Use this because if the MonsterLocation class is used
// everything breaks because of some json stuff
@Data
public class MonsterLocationSelection {

    String monster;

    String location;

    @Setter
    boolean isF2P;

    boolean meleeEnabled = true, mageEnabled = true, rangedEnabled = true;

    public MonsterLocationSelection(String monster, String location) {
        this.monster = monster;
        this.location = location;
    }

    public boolean isMembers() {
        return !isF2P;
    }

    @DoNotRename
    public String getMonsterName() {
        return this.monster;
    }

    @DoNotRename
    public String getLocationName() {
        return this.location;
    }

    @DoNotRename
    public CheckBox getEnabledCheckBoxMelee() {
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(enableEventMelee);
        checkBox.setSelected(meleeEnabled);
        return checkBox;
    }

    @DoNotRename
    public CheckBox getEnabledCheckBoxMage() {
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(enableEventMage);
        checkBox.setSelected(mageEnabled);
        return checkBox;
    }

    @DoNotRename
    public CheckBox getEnabledCheckBoxRanged() {
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(enableEventRanged);
        checkBox.setSelected(rangedEnabled);
        return checkBox;
    }

    // The functions below are for GUI purposes

    @DoNotRename
    EventHandler enableEventMelee = new EventHandler<ActionEvent>() {
        @Override @SerializedName("enableEventMelee")
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox chk = (CheckBox) event.getSource();
                meleeEnabled = chk.isSelected();
                Log.info("{Melee} Enabled Setting: " + meleeEnabled);
            }
        }
    };

    @DoNotRename
    EventHandler enableEventMage = new EventHandler<ActionEvent>() {
        @Override @SerializedName("enableEventMage")
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox chk = (CheckBox) event.getSource();
                mageEnabled = chk.isSelected();
                Log.info("{Mage} Enabled Setting: " + mageEnabled);
            }
        }
    };

    @DoNotRename
    EventHandler enableEventRanged = new EventHandler<ActionEvent>() {
        @Override @SerializedName("enableEventRanged")
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox chk = (CheckBox) event.getSource();
                rangedEnabled = chk.isSelected();
                Log.info("{Ranged} Enabled Setting: " + rangedEnabled);
            }
        }
    };

}
