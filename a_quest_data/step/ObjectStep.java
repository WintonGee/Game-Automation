package scripts.main_package.a_quest_data.step;

import lombok.Setter;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.interfaces.Interactable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.requirement.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ObjectStep extends QuestStep {

    private String name;
    private int mainId;
    private final ArrayList<Integer> alternateIDs = new ArrayList<>();

    private boolean allowMultipleHighlights;

    @Setter
    private int maxRoamRange = 48;

    public ObjectStep(BasicQuestHelper questHelper, int mainId, String text, Requirement... requirements) {
        super(questHelper, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.mainId = mainId;
    }

    public ObjectStep(BasicQuestHelper questHelper, int mainId, WorldPoint worldPoint, String text, Requirement... requirements) {
        super(questHelper, worldPoint, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.mainId = mainId;

    }

    public ObjectStep(BasicQuestHelper questHelper, int mainId, WorldPoint worldPoint, String text, boolean allowMultipleHighlights, Requirement... requirements) {
        this(questHelper, mainId, worldPoint, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.allowMultipleHighlights = allowMultipleHighlights;
    }

    public ObjectStep(BasicQuestHelper questHelper, int mainId, String text, boolean allowMultipleHighlights, Requirement... requirements) {
        this(questHelper, mainId, null, text, allowMultipleHighlights, requirements);
    }

    public ObjectStep(BasicQuestHelper questHelper, String name, String text, Requirement... requirements) {
        super(questHelper, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.name = name;
    }

    public ObjectStep(BasicQuestHelper questHelper, String name, WorldPoint worldPoint, String text, Requirement... requirements) {
        super(questHelper, worldPoint, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.name = name;

    }

    public ObjectStep(BasicQuestHelper questHelper, String name, WorldPoint worldPoint, String text, boolean allowMultipleHighlights, Requirement... requirements) {
        this(questHelper, name, worldPoint, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.allowMultipleHighlights = allowMultipleHighlights;
    }

    public ObjectStep(BasicQuestHelper questHelper, String name, String text, boolean allowMultipleHighlights, Requirement... requirements) {
        this(questHelper, name, null, text, allowMultipleHighlights, requirements);
    }

    public void addAlternateObjects(Integer... alternateIDs) {
        this.alternateIDs.addAll(Arrays.asList(alternateIDs));
    }

    public void addAlternateObjects(List<Integer> alternateIDs) {
        this.alternateIDs.addAll(alternateIDs);
    }

    public List<Integer> allIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(mainId);
        ids.addAll(alternateIDs);
        return ids;
    }

    @Override
    public Interactable getInteractable() {
        Log.info("[ObjectStep] Attempting to get interactable, Name: " + name + " Id: " + mainId);
        if (name != null) {
            Optional<GameObject> main = Query.gameObjects().nameEquals(name).findClosest();
            return main.isPresent() ? main.get() : null;
        }

        Optional<GameObject> main = Query.gameObjects().idEquals(mainId).findClosest();
        if (main.isPresent())
            return main.get();

        if (alternateIDs.size() == 0)
            return null;

        return Query.gameObjects().filter(n -> {
            int npcId = n.getId();
            return alternateIDs.contains(npcId);
        }).findClosest().orElse(null);
    }
}
