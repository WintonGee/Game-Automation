package scripts.main_package.tasks.combat_setup_task;

import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.Equipment;
import scripts.main_package.item.equipment.EquipmentItem;
import scripts.main_package.item_data.GearItemData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Used to hold the information on the gear needed for the
// combat details
@Data
public class GearSetup {

    CombatSetupTask combatSetupTask;

    List<EquipmentItem> usableGears;

    public GearSetup(CombatSetupTask combatSetupTask) {
        this.setCombatSetupTask(combatSetupTask);
    }

    public void load() {
        for (val entry : combatSetupTask.gearInfo.entrySet()) {
            Equipment.Slot slot = entry.getKey();
            val gearGroup = entry.getValue();
            List<EquipmentItem> equipsToCheck = usableGears != null ? usableGears : getSlotItems(slot);
            combatSetupTask.loadItem(slot, gearGroup, equipsToCheck);
        }

    }

    // TODO add more stuff
    private List<EquipmentItem> getSlotItems(Equipment.Slot slot) {
        switch (slot) {
            case HEAD:
                return Arrays.stream(GearItemData.GearHelmet.values()).map(GearItemData.GearHelmet::getEquipmentItem).collect(Collectors.toList());
            default:
                return GearItemData.getEquipmentItems();
        }
    }

}

