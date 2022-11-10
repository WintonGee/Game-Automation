package scripts.main_package.item.equipment;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Equipment;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.item.UntradeableItem;
import scripts.main_package.item_data.WeaponItemData;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class EquipmentItem {

    GearGroup gearGroup = GearGroup.GENERAL;

    int mainId;
    ArrayList<Integer> altIds = new ArrayList<>();
    Equipment.Slot slot;
    ArrayList<Requirement> requirements = new ArrayList<>();

    int statScore = 0;

    // For weapon type items
    boolean twoHanded;
    WeaponItemData.RangedArrow bestArrow;
    WeaponItemData.SpellCastType spellCastType;

    // Task to get the item if it is not tradeable
    UntradeableItem.UntradeableItemTask untradeableItemTask;

    public EquipmentItem(int mainId) {
        this.setMainId(mainId);
    }

    public boolean isMembers() {
        return Utils.isMembers(mainId);
    }

    public boolean isRequirementsValid() {
        return requirements.stream().allMatch(Requirement::check);
    }

    public DetailedItem getDetailedItem() {
        DetailedItem detailedItem = new DetailedItem(mainId);
        for (int altId : altIds)
            detailedItem.addAlt(altId);
        return detailedItem;
    }

    public String getName() {
        return Utils.getItemName(mainId);
    }

    public int getScore(int liquidWealth) {
        return new EquipmentItemScore(this).getScore(liquidWealth);
    }

    //TODO get item cost
    // account for ammo and sets?
    // figure how to determine the arrow type?


    public enum GearGroup {

        GENERAL("General"), // Can be any type, Melee, Ranged, Mage
        MELEE("Melee"),
        RANGED("Ranged"),
        MAGE("Mage");

        @Getter
        private final String type;

        GearGroup(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

    }

    @Override
    public String toString() {
        return Utils.getItemName(mainId);
    }

}
