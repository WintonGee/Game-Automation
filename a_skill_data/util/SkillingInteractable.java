package scripts.main_package.a_skill_data.util;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.interfaces.Clickable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.WorldTile;
import scripts.main_package.api.other.Utils;

import java.util.ArrayList;

// This class is not to be exposed for GUI
@Data
@Accessors(chain = true)
public class SkillingInteractable {

    int productId;
    public String action;

    public ArrayList<Integer> idsList = new ArrayList<>();
    public ArrayList<String> namesList = new ArrayList<>();
    InteractionType interactionType;

    // Example Usage: Al Kharid Mining, since the ores are so spread out
    WorldTile specificTile;

    public SkillingInteractable() {

    }

    public SkillingInteractable(int productId) {
        this.setProductId(productId);
    }

    public Clickable getInteractable() {
        if (interactionType == null) {
            Log.error("[SkillingInteractable] No interaction type is set for: " + Utils.getItemName(productId) + ", Ids: " + idsList + ", Names: " + namesList);
            return null;
        }

        if (interactionType == InteractionType.GAME_OBJECT) {
            return Query.gameObjects()
                    .filter(o -> action == null || o.getActions().contains(action))
                    .filter(o -> idsList.size() > 0 ? idsList.contains(o.getId()) : namesList.contains(o.getName()))
                    .isReachable()
                    .findClosestByPathDistance().orElse(null);
        }

        if (interactionType == InteractionType.NPC) {
            return Query.npcs()
                    .filter(o -> action == null || o.getActions().contains(action))
                    .filter(o -> idsList.size() > 0 ? idsList.contains(o.getId()) : namesList.contains(o.getName()))
                    .isReachable()
                    .findClosestByPathDistance().orElse(null);
        }

        if (interactionType == InteractionType.GROUND_ITEM) {
            return Query.groundItems()
                    .filter(o -> action == null || o.getActions().contains(action))
                    .filter(o -> idsList.size() > 0 ? idsList.contains(o.getId()) : namesList.contains(o.getName()))
                    .isReachable()
                    .findClosestByPathDistance().orElse(null);
        }

        Log.error("[SkillingInteractable] Could not determine interactable type for: " + Utils.getItemName(productId) + ", Ids: " + idsList + ", Names: " + namesList);
        return null;
    }

    public SkillingInteractable getCopy() {
        SkillingInteractable copy = new SkillingInteractable(productId);

        copy.setAction(action);

        copy.idsList.addAll(idsList);
        copy.namesList.addAll(namesList);
        copy.setInteractionType(interactionType);

        copy.setSpecificTile(specificTile);

        return copy;
    }

    public enum InteractionType {

        NPC("Npc"),
        GAME_OBJECT("Game Object"),
        GROUND_ITEM("Ground Object");

        String name;

        InteractionType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

}
