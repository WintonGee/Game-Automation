package scripts.main_package.item;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.types.WorldTile;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.a_quest_data.requirement.player.SkillRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestRequirement;
import scripts.main_package.api.other.Utils;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class TeleportItem {

    int mainId;
    ArrayList<Integer> altIds = new ArrayList<Integer>();
    Equipment.Slot equippedSlot;

    ArrayList<QuestRequirement> questRequirements = new ArrayList<>();
    ArrayList<SkillRequirement> skillRequirements = new ArrayList<>();

    public TeleportItem(int mainId) {
        this.setMainId(mainId);
    }

    // Should start from charges 1 -> max. Will later be using for getting min charges
    public TeleportItem addAltIds(int... ids) {
        for (int id : ids)
            altIds.add(id);
        return this;
    }

    public TeleportItem addQuestRequirements(Quest... quests) {
        for (Quest quest : quests)
            questRequirements.add(new QuestRequirement(quest));
        return this;
    }

    public TeleportItem addSkillRequirements(Skill skill, int level) {
        skillRequirements.add(new SkillRequirement(skill, level));
        return this;
    }

    public DetailedItem getAsDetailedItem() {
        DetailedItem detailedItem = new DetailedItem(mainId);
        for (int id : altIds)
            detailedItem.addAlt(id);
        return detailedItem;
    }

    public ItemRequirement getAsItemRequirement() {
        String name = Utils.getItemName(mainId);
        ItemRequirement itemRequirement = new ItemRequirement(name, mainId);
        for (int id : altIds)
            itemRequirement.addAlternates(id);
        return itemRequirement;
    }

    // Very niche method, used for quests.
    // Will try to use up the teleport items without full charges.
    // Has a min charge requirement
    // Returns the id of the item that the character has
    // Will default to returning main tradable item if no valid alternatives.
    public int getBestIdWithMinCharges(int minCharges) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        int altIdsSize = this.altIds.size();
        // Subtract 1 needed since index starts at 0 instead of 1
        for (int i = minCharges - 1; i < altIdsSize; i++) {
            int id = altIds.get(i);
            if (Utils.getTotalItemCountWithNoted(id) > 0)
                return id;
        }
        return this.mainId;
    }

}
