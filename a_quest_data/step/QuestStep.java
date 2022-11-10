package scripts.main_package.a_quest_data.step;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.interfaces.Clickable;
import org.tribot.script.sdk.interfaces.Interactable;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.RequirementQuestStep;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
public class QuestStep {

    private final int INVALID_ID = -1;

    String description;
    ArrayList<Requirement> requirements = new ArrayList<Requirement>();
    boolean lockable = false;

    boolean useExactTile = false; // For traveling.
    WorldPoint worldPoint;

    ArrayList<String> choices = new ArrayList<String>(Arrays.asList("Yes.", "Yes"));
    int chatAttempts = 1; // Sometimes there is a delay for chatting, causing chat method to end unintended.

    int useItemId = INVALID_ID;
    String useItemName;

    public String actionName;
    public InteractionType interactionType = InteractionType.NONE;
    // Use item -> Using on items, will have another click action afterwards.

    BasicQuestHelper questHelper;
    protected List<String> text;

    protected QuestStep defaultQuestStep; // For conditional step, when no others valid steps
    protected ArrayList<RequirementQuestStep> requirementQuestSteps = new ArrayList<>();

    public QuestStep(BasicQuestHelper questHelper) {
        this.questHelper = questHelper;
    }

    public QuestStep(BasicQuestHelper questHelper, String description, Requirement... reqs) {
        this(questHelper, null, description, reqs);
    }

    public QuestStep(BasicQuestHelper questHelper, WorldPoint worldPoint, String description, Requirement... reqs) {
        this.questHelper = questHelper;
        this.setWorldPoint(worldPoint);
        // use explicit ArrayList because we need the 'text' list to be mutable
        this.description = description;
        this.addRequirements(reqs);
    }

    public QuestStep(BasicQuestHelper questHelper, List<String> text) {
        this.description = text.toString();
        this.questHelper = questHelper;
    }

    public Interactable getInteractable() {
        return null;
    }

    public Clickable getClickable() {
        return null;
    }

    public void addRequirements(Requirement... reqs) {
        requirements.addAll(Arrays.asList(reqs));
    }

    public void addDialogStep(String... newChoices) {
        addDialogSteps(newChoices);
    }

    public void addDialogSteps(String... newChoices) {
        choices.addAll(Arrays.asList(newChoices));
    }

    public void addDialogSteps(List<String> newChoices) {
        choices.addAll(newChoices);
    }

    //TODO figure out what this is used for
    public void addSubSteps(QuestStep... questStep) {

    }

    public QuestStep setUseItem(ItemRequirement itemRequirement) {
        this.useItemId = itemRequirement.getId();
        return this;
    }

    public boolean isRequirementsValid() {
        return requirements.stream().allMatch(Requirement::check);
    }




    public enum InteractionType {

        NONE("None"),
        CLICKABLE("Clickable"),
        INTERACTABLE("Interactable");

        @Getter
        @Setter
        String name;

        InteractionType(String name) {
            this.setName(name);
        }

    }

    // Useless
    public void addIcon(int id) {

    }

}
