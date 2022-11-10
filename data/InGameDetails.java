package scripts.main_package.data;

import lombok.Data;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;

import java.util.Arrays;
import java.util.LinkedHashMap;

@Data
public class InGameDetails {

    boolean playerUpdated = false;
    boolean bankUpdated = false;

    // Use id and quantity to save memory
    LinkedHashMap<Integer, Integer> bankMap = new LinkedHashMap<Integer, Integer>();
    LinkedHashMap<Integer, Integer> invMap = new LinkedHashMap<Integer, Integer>();
    LinkedHashMap<Integer, Integer> equipMap = new LinkedHashMap<Integer, Integer>();

    LinkedHashMap<Skill, Integer> skillMap = new LinkedHashMap<Skill, Integer>();

    String characterName;

    public boolean update() {
        if (!Login.isLoggedIn() || GameState.isLoading()) {
            Log.debug("[InGameDetails] Skipping Update");
            return false;
        }

        Log.debug("[InGameDetails] Updating Details");
        playerUpdated = true;
        updateCharacterName();
        updateItems();
        updateSkills();
        return true;
    }

    public void updateCharacterName() {
        characterName = MyPlayer.getUsername();
    }

    public void updateItems() {
        if (BankCache.isInitialized()) {
            bankUpdated = true;
            BankCache.entries().forEach(item -> {
                int id = item.getKey();
                int amount = item.getValue();
                bankMap.put(id, amount);
            });
        }

        Query.inventory().forEach(item -> {
            int id = item.getId();
            int amount = item.getStack();
            invMap.put(id, amount);
        });

        Query.equipment().forEach(item -> {
            int id = item.getId();
            int amount = item.getStack();
            equipMap.put(id, amount);
        });
    }

    public void updateSkills() {
        Arrays.stream(Skill.values()).forEach(skill -> {
            int skillLevel = skill.getActualLevel();
            skillMap.put(skill, skillLevel);
        });
    }


}
