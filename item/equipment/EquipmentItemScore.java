package scripts.main_package.item.equipment;

import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.pricing.Pricing;
import scripts.main_package.api.other.UtilSeed;
import scripts.main_package.api.other.Utils;

//TODO test if multipliers are needed for ammo
@Data
public class EquipmentItemScore {

    private final int COST_SCORE_MULTIPLIER = 100;
    private final int ARROW_SCORE_MULTIPLIER = 2;
    private final int SPELL_SCORE_MULTIPLIER = 5;

    EquipmentItem equipmentItem;

    public EquipmentItemScore(EquipmentItem equipmentItem) {
        this.setEquipmentItem(equipmentItem);
    }

    // Used for comparing with other items and determining
    public int getScore(int liquidWealth) {
        return equipmentItem.statScore + getSeededScore() + getCostScore(liquidWealth) + getSpellScore();
    }

    private int getSeededScore() {
        String key = Utils.getItemName(equipmentItem.getMainId());
        return UtilSeed.getValue(key, 1, 5);
    }

    // This score is based off of the worth compared to total player wealth
    private int getCostScore(double liquidWealth) {
        int itemCost = Pricing.lookupPrice(equipmentItem.mainId).orElse(1);
        // Prevent infinity error in case 0.
        if (liquidWealth == 0)
            liquidWealth++;

        int wealthScore = (int) Math.ceil((itemCost / liquidWealth) * COST_SCORE_MULTIPLIER);
        boolean haveItem = equipmentItem.getDetailedItem().isHave(true);
        return haveItem ? wealthScore : wealthScore * -1;
    }

    // Returns the score of the item based on the best spell castable.
    //TODO the cost of the spell cast?
    public int getSpellScore() {
        val type = equipmentItem.spellCastType;
        if (type == null) {
            return 0;
        }

        val bestCast = type.getBestSpellCast();
//        Log.debug("[EquipmentItemScore] Type: " + type + " Best Cast: " + bestCast + " Score: " + bestCast.getMaxHit());
        return bestCast.getMaxHit();
    }

}
