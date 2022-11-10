package scripts.main_package.tasks.combat_setup_task;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.pricing.Pricing;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.equipment.EquipmentItem;
import scripts.main_package.item_data.WeaponItemData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

// This task is used for handling the gear.
// Mainly for the gear that is needed for equipping
// This will hold data on the attack style and spell selections
@Data
@Accessors(chain = true)
public class CombatSetupTask {

    //TODO max cost per piece?
    LinkedHashMap<Equipment.Slot, EquipmentItem.GearGroup> gearInfo = new LinkedHashMap<>();
    boolean f2p;

    // Used to skip loading slots that have a finalized item set.
    ArrayList<Equipment.Slot> disabledSlots = new ArrayList<>();
    //TODO slots that can be skipped if the player does not have enough gp

    int liquidWealth;
    public WeaponSetup weaponSetup;
    public AmmoSetup ammoSetup;
    public GearSetup gearSetup;

    ArrayList<EquipmentItem> currentItems = new ArrayList<>();

    int minAmmoSets = 500, maxAmmoSets = 5000; //TODO maybe an ammo buy amount?
    WeaponItemData.RangedArrow selectedArrow;
    WeaponItemData.SpellCast selectedSpellCast;

    // Used for selecting an attack style based on the skill experience.
    Skill attackStyleExperience;

    public CombatSetupTask(EquipmentItem.GearGroup gearGroup) {
        for (Equipment.Slot slot : Equipment.Slot.values())
            gearInfo.put(slot, gearGroup);

        this.setWeaponSetup(new WeaponSetup(this));
        this.setAmmoSetup(new AmmoSetup(this));
        this.setGearSetup(new GearSetup(this));

        this.liquidWealth = Utils.getTotalItemCount(995);
        //TODO, include sellable items and ge items?
    }

    public void loadRemainingItems() {
        List<String> list = currentItems.stream().map(EquipmentItem::toString).collect(Collectors.toList());
        String listString = String.join(", ", list);
        Log.info("[CombatSetupTask] Items Loaded: " + listString);
        Log.info("[CombatSetupTask] Loading remaining items");

        this.weaponSetup.load();
        this.ammoSetup.load();
        this.gearSetup.load();
    }


    public void loadItem(Equipment.Slot slot, EquipmentItem.GearGroup gearGroup, List<EquipmentItem> equipsToCheck) {
        if (disabledSlots.contains(slot)) {
            Log.warn("[CombatSetupTask] Skipping, Slot is disabled: " + slot);
            return;
        }

        EquipmentItem currentItem = null;

        for (EquipmentItem equipmentItem : equipsToCheck) {
            EquipmentItem.GearGroup checkingGearGroup = equipmentItem.getGearGroup();
            if (gearGroup != null && checkingGearGroup != EquipmentItem.GearGroup.GENERAL && checkingGearGroup != gearGroup) {
                continue;
            }

            // Failsafe for using default master list
            if (slot != null && slot != equipmentItem.getSlot())
                continue;

            // Requirements
            if (!equipmentItem.isRequirementsValid()) {
                continue;
            }

            // F2P
            if (f2p && equipmentItem.isMembers())
                continue;

            if (currentItem == null) {
                currentItem = equipmentItem;
                continue;
            }

            // Comparing scores between current and new item
            int currentItemScore = currentItem.getScore(liquidWealth);
            int newItemScore = equipmentItem.getScore(liquidWealth);
            boolean shouldReplace = newItemScore >= currentItemScore;
            if (shouldReplace) {
                Log.debug("[CombatSetupTask] Replacing " + currentItem.getName() + "(Score: " + currentItemScore + ")" + " to " + equipmentItem.getName() + "(Score: " + newItemScore + ")");
                currentItem = equipmentItem;
            }
        }

        if (currentItem == null) {
            Log.warn("[CombatSetupTask] No item selected for slot: " + slot);
            return;
        }

        // TODO untradeables support -> include material cost to get untradeable
        int newItemCost = currentItem.getDetailedItem().isHave(true) ? 0
                : Pricing.lookupPrice(currentItem.getMainId()).orElse(0);
        liquidWealth -= newItemCost;
        Log.info("[CombatSetupTask] Final Item Selected: " + currentItem + ", Cost: " + newItemCost + ", Remaining Liquidity: " + liquidWealth);
        this.setGear(currentItem);
    }


    public void addDisableSlots(Equipment.Slot... slots) {
        disabledSlots.addAll(Arrays.asList(slots));
    }

    // Example usage: default is melee type, but magic is wanted in slot legs
    // This will make the task use mage legs, and remaining slots melee.
    public void setGearSlotInfo(Equipment.Slot slot, EquipmentItem.GearGroup gearGroup) {
        gearInfo.put(slot, gearGroup);
    }

    //TODO have function to set a gear info for inventory slot

    public void setGear(EquipmentItem equipmentItem) {
        currentItems.add(equipmentItem);

        Equipment.Slot slot = equipmentItem.getSlot();
        disabledSlots.add(slot);

        // For weapons
        if (equipmentItem.isTwoHanded())
            disabledSlots.add(Equipment.Slot.SHIELD);

        if (equipmentItem.getBestArrow() != null)
            disabledSlots.add(Equipment.Slot.AMMO);
    }

}
