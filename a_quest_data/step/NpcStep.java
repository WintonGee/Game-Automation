package scripts.main_package.a_quest_data.step;

import lombok.Setter;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.interfaces.Interactable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.requirement.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NpcStep extends QuestStep {

    private String npcName;
    private int npcID;
    private final ArrayList<Integer> alternateNpcIDs = new ArrayList<>();

    private boolean allowMultipleHighlights;

    @Setter
    private int maxRoamRange = 48;

    public NpcStep(BasicQuestHelper questHelper, int npcID, String text, Requirement... requirements) {
        super(questHelper, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.npcID = npcID;
    }

    public NpcStep(BasicQuestHelper questHelper, int npcID, WorldPoint worldPoint, String text, Requirement... requirements) {
        super(questHelper, worldPoint, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.npcID = npcID;

    }

    public NpcStep(BasicQuestHelper questHelper, int npcID, WorldPoint worldPoint, String text, boolean allowMultipleHighlights, Requirement... requirements) {
        this(questHelper, npcID, worldPoint, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.allowMultipleHighlights = allowMultipleHighlights;
    }

    public NpcStep(BasicQuestHelper questHelper, int npcID, String text, boolean allowMultipleHighlights, Requirement... requirements) {
        this(questHelper, npcID, null, text, allowMultipleHighlights, requirements);
    }

    public NpcStep(BasicQuestHelper questHelper, String npcName, String text, Requirement... requirements) {
        super(questHelper, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.npcName = npcName;
    }

    public NpcStep(BasicQuestHelper questHelper, String npcName, WorldPoint worldPoint, String text, Requirement... requirements) {
        super(questHelper, worldPoint, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.npcName = npcName;

    }

    public NpcStep(BasicQuestHelper questHelper, String npcName, WorldPoint worldPoint, String text, boolean allowMultipleHighlights, Requirement... requirements) {
        this(questHelper, npcName, worldPoint, text, requirements);
        this.setInteractionType(InteractionType.INTERACTABLE);
        this.allowMultipleHighlights = allowMultipleHighlights;
    }

    public NpcStep(BasicQuestHelper questHelper, String npcName, String text, boolean allowMultipleHighlights, Requirement... requirements) {
        this(questHelper, npcName, null, text, allowMultipleHighlights, requirements);
    }

    public void addAlternateNpcs(Integer... alternateNpcIDs) {
        this.alternateNpcIDs.addAll(Arrays.asList(alternateNpcIDs));
    }

    public void addAlternateNpcs(List<Integer> alternateNpcIDs) {
        this.alternateNpcIDs.addAll(alternateNpcIDs);
    }

    public List<Integer> allIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(npcID);
        ids.addAll(alternateNpcIDs);
        return ids;
    }

    @Override
    public Interactable getInteractable() {
        Log.info("[NpcStep] Attempting to get interactable, Name: " + npcName + " Id: " + npcID);
        if (npcName != null) {
            Optional<Npc> main = Query.npcs().nameEquals(npcName).findClosest();
            return main.isPresent() ? main.get() : null;
        }

        Optional<Npc> main = Query.npcs().idEquals(npcID).findClosest();
        if (main.isPresent())
            return main.get();

        if (alternateNpcIDs.size() == 0)
            return null;

        return Query.npcs().filter(n -> {
            int npcId = n.getId();
            return alternateNpcIDs.contains(npcId);
        }).findClosest().orElse(null);
    }

}
