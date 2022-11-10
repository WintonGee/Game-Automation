package scripts.main_package.tasks.combat_setup_task;

import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import scripts.main_package.item.equipment.EquipmentItem;
import scripts.main_package.item_data.WeaponItemData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Used for weapon type items,
// also used to store information on ammo types
// - Arrows
// - Runes needed for spell
@Data
public class WeaponSetup {
    //TODO something to account for the expereince type and attack style?

    CombatSetupTask combatSetupTask;

    List<EquipmentItem> usableGears;

    public WeaponSetup(CombatSetupTask combatSetupTask) {
        this.setCombatSetupTask(combatSetupTask);
    }

    public void load() {
        if (combatSetupTask.disabledSlots.contains(Equipment.Slot.WEAPON)) {
            Log.warn("[WeaponSetup] Weapon slot is disabled, Skipping");
            return;
        }

        val group = combatSetupTask.getGearInfo().get(Equipment.Slot.WEAPON);
        if (group == null) {
            Log.warn("[WeaponSetup] Group is null, Skipping");
            return;
        }

        val selectableItems = getItems(group);
        if (selectableItems == null) {
            Log.warn("[WeaponSetup] No weapons to choose from, Group: " + group);
            return;
        }

        this.combatSetupTask.loadItem(Equipment.Slot.WEAPON, group, selectableItems);
    }


    private List<EquipmentItem> getItems(EquipmentItem.GearGroup gearGroup) {
        if (gearGroup == EquipmentItem.GearGroup.MELEE) {
            return Arrays.stream(WeaponItemData.WeaponMelee.values()).map(WeaponItemData.WeaponMelee::getEquipmentItem).collect(Collectors.toList());
        }
        if (gearGroup == EquipmentItem.GearGroup.RANGED) {
            return Arrays.stream(WeaponItemData.WeaponRanged.values()).map(WeaponItemData.WeaponRanged::getEquipmentItem).collect(Collectors.toList());
        }
        if (gearGroup == EquipmentItem.GearGroup.MAGE) {
            return Arrays.stream(WeaponItemData.WeaponMage.values()).map(WeaponItemData.WeaponMage::getEquipmentItem).collect(Collectors.toList());
        }

        return null;
    }

}
