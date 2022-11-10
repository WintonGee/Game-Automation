package scripts.main_package.tasks.combat_setup_task;

import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.item.equipment.EquipmentItem;
import scripts.main_package.item_data.WeaponItemData;

import java.util.*;

//TODO need to set the ammo to use
@Data
public class AmmoSetup {

    private final int COST_SCORE_MULTIPLIER = 200;

    CombatSetupTask combatSetupTask;

    List<EquipmentItem> usableGears;

    public AmmoSetup(CombatSetupTask combatSetupTask) {
        this.setCombatSetupTask(combatSetupTask);
    }

    public void load() {
        Optional<EquipmentItem> weaponOptional = combatSetupTask.currentItems
                .stream()
                .filter(equipmentItem -> equipmentItem.getSlot() == Equipment.Slot.WEAPON)
                .findFirst();

        if (weaponOptional.isEmpty()) {
            Log.warn("[AmmoSetup] No weapon selected, skipping ammo setup.");
            return;
        }

        EquipmentItem weapon = weaponOptional.get();
        loadArrow(weapon);
        loadSpell(weapon);
    }

    private void loadArrow(EquipmentItem weapon) {
        val bestArrow = weapon.getBestArrow();
        if (bestArrow == null) {
            Log.info("[AmmoSetup] No arrows needed for this combat setup");
            return;
        }

        val usableArrows = getUsableArrows(bestArrow);
        WeaponItemData.RangedArrow currentArrow = null;
        for (WeaponItemData.RangedArrow arrow : usableArrows) {
            if (currentArrow == null) {
                currentArrow = arrow;
                continue;
            }
            int currentArrowScore = getArrowScore(currentArrow);
            int newArrowScore = getArrowScore(arrow);
            if (newArrowScore >= currentArrowScore) {
                Log.debug("[AmmoSetup] Replacing " + currentArrow + "(" + currentArrowScore + ") to " + arrow + "(" + newArrowScore + ")");
                currentArrow = arrow;
            }
        }

        //TODO cost and diff the liquid wealth
        Log.info("[AmmoSetup] Final Arrow Selected: " + currentArrow);
        this.combatSetupTask.selectedArrow = currentArrow;
    }


    private void loadSpell(EquipmentItem weapon) {
        val spellType = weapon.getSpellCastType();
        if (spellType == null) {
            Log.info("[AmmoSetup] No Spell needed for this combat setup");
            return;
        }

        val usableSpells = getUsableSpells(spellType);
        WeaponItemData.SpellCast currentSpell = null;
        for (WeaponItemData.SpellCast spellCast : usableSpells) {
            if (currentSpell == null) {
                currentSpell = spellCast;
                continue;
            }

            if (!spellCast.requirementsValid()) {
                break;
            }

            int currentSpellScore = getSpellScore(currentSpell);
            int newSpellScore = getSpellScore(spellCast);
            if (newSpellScore >= currentSpellScore) {
                Log.debug("[AmmoSetup] Replacing " + currentSpell + "(" + currentSpellScore + ") to " + spellCast + "(" + newSpellScore + ")");
                currentSpell = spellCast;
            }
        }

        Log.info("[AmmoSetup] Final Spell Selected: " + currentSpell);
        this.combatSetupTask.selectedSpellCast = currentSpell;
    }

    private ArrayList<WeaponItemData.RangedArrow> getUsableArrows(WeaponItemData.RangedArrow bestArrow) {
        int ordinal = bestArrow.ordinal();
        return new ArrayList<>(Arrays.asList(WeaponItemData.RangedArrow.values()).subList(0, ordinal + 1));
    }

    private ArrayList<WeaponItemData.SpellCast> getUsableSpells(WeaponItemData.SpellCastType spellCastType) {
        return spellCastType.getSpellCastList();
    }

    private int getArrowScore(WeaponItemData.RangedArrow arrow) {
        int sets = 2000; //TEMP
        ArrayList<DetailedItem> list = new ArrayList<>(Collections.singletonList(arrow.getDetailedItem()));
        int cost = getCost(sets, list);
        int costScore = getCostScore(cost);
        return costScore + arrow.getStatScore();
    }

    private int getSpellScore(WeaponItemData.SpellCast spellCast) {
        int sets = 1000; //TEMP
        int cost = getCost(sets, spellCast.getRunes());
        int costScore = getCostScore(cost);
        Log.debug("[AmmoSetup] Cost Score " + spellCast + ", " + costScore);
        return costScore + spellCast.getStatScore();
    }

    private int getCostScore(int cost) {

        int liquidWealth = this.combatSetupTask.liquidWealth;
        // Prevent infinity error in case 0.
        if (liquidWealth == 0)
            liquidWealth++;

        int wealthScore = (int) Math.ceil((COST_SCORE_MULTIPLIER * cost / liquidWealth));
        return wealthScore * -1;
    }


    // items -> runes needed per cast, or a single arrow
    private int getCost(int sets, ArrayList<DetailedItem> items) {
        ArrayList<DetailedItem> totalItemsList = new ArrayList<>();
        for (DetailedItem item : items) {
            int id = item.getItemId();
            int amountNeeded = item.getQuantity() * sets;
            int haveAmount = Utils.getTotalItemCount(id);
            int purchaseAmount = amountNeeded - haveAmount;

            if (purchaseAmount > 0)
                totalItemsList.add(new DetailedItem(id, purchaseAmount));
        }

        return totalItemsList
                .stream()
                .mapToInt(DetailedItem::getCost)
                .sum();
    }

}
