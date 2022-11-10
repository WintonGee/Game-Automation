package scripts.main_package.item_data;

import lombok.Setter;
import scripts.main_package.item.DetailedItem;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Arrays;

//TODO need to rewrite this class
public class RechargeableItemData {

    //TODO change to obj and extend RechargeableItem to detailed item
    public enum RechargeableItem {

        TRIDENT_OF_THE_SEAS(ItemID.UNCHARGED_TRIDENT, ItemID.TRIDENT_OF_THE_SEAS, ItemID.TRIDENT_OF_THE_SEAS_FULL, 2500
                , ItemCharge.CHAOS_RUNE_1, ItemCharge.DEATH_RUNE_1, ItemCharge.COINS_10);

        @Setter
        int emptyId, partiallyChargedId, fullyChargedId, maxCharges;
        // Items needed for a single charge
        ArrayList<ItemCharge> chargeMaterials = new ArrayList<>();

        RechargeableItem(int emptyId, int partiallyChargedId, int fullyChargedId, int maxCharges, ItemCharge... itemCharges) {
            this.setEmptyId(emptyId);
            this.setPartiallyChargedId(partiallyChargedId);
            this.setFullyChargedId(fullyChargedId);
            this.setMaxCharges(maxCharges);

            chargeMaterials.addAll(Arrays.asList(itemCharges));
        }

    }

    private enum ItemCharge {

        COINS_10(ItemID.COINS_995, 10),

        // Runes
        CHAOS_RUNE_1(ItemID.CHAOS_RUNE),
        DEATH_RUNE_1(ItemID.DEATH_RUNE),
        FIRE_RUNE_1(ItemID.FIRE_RUNE),
        FIRE_RUNE_5(ItemID.FIRE_RUNE, 5),

        ;

        int id;
        int amount;

        ItemCharge(int id) {
            this(id, 1);
        }

        ItemCharge(int id, int amount) {
            this.id = id;
            this.amount = amount;
        }

        public DetailedItem getDetailedItem() {
            return new DetailedItem(id, amount);
        }

    }

}
