package scripts.main_package.a_skill_data.util;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;

import java.util.ArrayList;

@Data
public class SkillSelection {

    boolean members;
    Skill skill;
    int maxLevel = 99;

    boolean enabled = true;
    int skillWeight = 1;

    public SkillSelection(Skill skill, boolean members) {
        this.setSkill(skill);
        this.setMembers(members);
    }

    public boolean isUnderMaxLevel() {
        return skill.getActualLevel() < maxLevel;
    }

    public void loadSeededWeight() {
        //TODO set skillWeight to something seeded.
    }

    @Override
    public String toString() {
        return "Weight: " + skillWeight + ", " + getSkillName();
    }

    // These getter functions below are for GUI purposes.
    @DoNotRename
    public String getSkillName() {
        String name = skill.name();
        String lowerCase = name.toLowerCase();
        String output = name.substring(0, 1).toUpperCase() + lowerCase.substring(1);
        return output;
    }


    @DoNotRename
    public TextField getSkillWeightTextfield() {
        TextField textField = new TextField();
        textField.setText(String.valueOf(skillWeight));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String numberOnly = newValue.replaceAll("[^0-9]", "");
            Log.info(getSkillName() + " Skill Weight From " + oldValue + " to " + numberOnly);
            if (numberOnly.length() > 0)
                setSkillWeight(Integer.parseInt(numberOnly));
        });
        return textField;
    }

    @DoNotRename
    public TextField getMaxLevelTextfield() {
        TextField textField = new TextField();
        textField.setText(String.valueOf(maxLevel));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String numberOnly = newValue.replaceAll("[^0-9]", "");
            Log.info(getSkillName() + " Max Level From " + oldValue + " to " + numberOnly);
            if (numberOnly.length() > 0)
                setMaxLevel(Integer.parseInt(numberOnly));
        });
        return textField;
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
                val selected = chk.isSelected();
                enabled = selected;
                Log.info("{" + getSkillName() + "} Enabled Setting: " + enabled);
            }
        }
    };


    public static ArrayList<SkillSelection> getAllSkillSelectionList() {
        ArrayList<SkillSelection> list = new ArrayList<>();

        // F2P skills
        list.add(new SkillSelection(Skill.ATTACK, false));
        list.add(new SkillSelection(Skill.STRENGTH, false));
        list.add(new SkillSelection(Skill.DEFENCE, false));
        list.add(new SkillSelection(Skill.RANGED, false));
        list.add(new SkillSelection(Skill.PRAYER, false));
        list.add(new SkillSelection(Skill.MAGIC, false));
        // list.add(new SkillSelectionObject(Skill.RUNECRAFT, false)); Not planning to support?
        list.add(new SkillSelection(Skill.HITPOINTS, false));
        list.add(new SkillSelection(Skill.CRAFTING, false));
        list.add(new SkillSelection(Skill.MINING, false));
        list.add(new SkillSelection(Skill.SMITHING, false));
        list.add(new SkillSelection(Skill.FISHING, false));
        list.add(new SkillSelection(Skill.COOKING, false));
        list.add(new SkillSelection(Skill.FIREMAKING, false));
        list.add(new SkillSelection(Skill.WOODCUTTING, false));

        // P2P
        list.add(new SkillSelection(Skill.AGILITY, true));
        list.add(new SkillSelection(Skill.HERBLORE, true));
        list.add(new SkillSelection(Skill.THIEVING, true));
        list.add(new SkillSelection(Skill.FLETCHING, true));
        list.add(new SkillSelection(Skill.SLAYER, true));
        list.add(new SkillSelection(Skill.FARMING, true));
        list.add(new SkillSelection(Skill.CONSTRUCTION, true));
        list.add(new SkillSelection(Skill.HUNTER, true));

        return list;
    }

}
