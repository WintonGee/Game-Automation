package scripts.main_package.a_quest_data;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;

//TODO make an interface for this and SkillSelectionObject?
@Data
public class QuestSelectionObject {

    Quest quest;

    boolean enabled = true;
    int questWeight = 1;

    public QuestSelectionObject(Quest quest) {
        this.setQuest(quest);
    }

    @Override
    public String toString() {
        return "Weight: " + questWeight + ", " + getQuestName();
    }

    public boolean isQuestComplete() {
        return quest.getState() == Quest.State.COMPLETE;
    }


    @DoNotRename
    public String getQuestName() { // Enum name -> normal looking name
        String name = quest.name();
        name = name.replaceAll("_", " ");
        String lowerCase = name.toLowerCase();
        String output = name.substring(0, 1).toUpperCase() + lowerCase.substring(1);
        return output;
    }

    @DoNotRename
    public TextField getQuestWeightTextfield() {
        TextField textField = new TextField();
        textField.setText(String.valueOf(questWeight));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String numberOnly = newValue.replaceAll("[^0-9]", "");
            Log.info(getQuestName() + " Quest Weight From " + oldValue + " to " + numberOnly);
            if (numberOnly.length() > 0)
                setQuestWeight(Integer.parseInt(numberOnly));
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
                Log.info("{" + getQuestName() + "} Enabled Setting: " + enabled);
            }
        }
    };

}
