package scripts.main_package.a_skill_data.combat_skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import scripts.main_package.item.DetailedItem;

// This class is used for finishing monsters off
// Example: gargoyles using rock hammer
// Example: desert lizards using Ice Cooler
@AllArgsConstructor
@Data
public class MonsterFinishTask {

    // Use this item on the monster
    // Will generally be an item that is untradeable, steps might be needed to get the item
    // Example: Slayer items that need to be bought from slayer store
    DetailedItem finishingTool;

    int minHp; // Execute this task when monster is this or lower hp

}
