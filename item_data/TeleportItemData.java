package scripts.main_package.item_data;

import lombok.Getter;
import lombok.Setter;
import scripts.main_package.item.TeleportItem;
import scripts.raw_data.ItemID;

//NEEDS TO BE REWRITTEN and use Teleport item
public enum TeleportItemData {

    AMULET_OF_GLORY(new TeleportItem(ItemID.AMULET_OF_GLORY6).addAltIds(ItemID.AMULET_OF_GLORY1, ItemID.AMULET_OF_GLORY2,
            ItemID.AMULET_OF_GLORY3, ItemID.AMULET_OF_GLORY4, ItemID.AMULET_OF_GLORY5, ItemID.AMULET_OF_GLORY6)),

    GAMES_NECKLACE(new TeleportItem(ItemID.GAMES_NECKLACE8).addAltIds(ItemID.GAMES_NECKLACE1, ItemID.GAMES_NECKLACE2, ItemID.GAMES_NECKLACE3,
            ItemID.GAMES_NECKLACE4, ItemID.GAMES_NECKLACE5, ItemID.GAMES_NECKLACE6, ItemID.GAMES_NECKLACE7, ItemID.GAMES_NECKLACE8)),

    RING_OF_WEALTH(new TeleportItem(ItemID.RING_OF_WEALTH_5).addAltIds(ItemID.RING_OF_WEALTH_1, ItemID.RING_OF_WEALTH_2,
            ItemID.RING_OF_WEALTH_3, ItemID.RING_OF_WEALTH_4, ItemID.RING_OF_WEALTH_5)),

    NECKLACE_OF_PASSAGE(new TeleportItem(ItemID.NECKLACE_OF_PASSAGE5).addAltIds(ItemID.NECKLACE_OF_PASSAGE1, ItemID.NECKLACE_OF_PASSAGE2,
            ItemID.NECKLACE_OF_PASSAGE3, ItemID.NECKLACE_OF_PASSAGE4, ItemID.NECKLACE_OF_PASSAGE5)),


    ;

    @Getter
    @Setter
    TeleportItem teleportItem;


    TeleportItemData(TeleportItem teleportItem) {
        this.setTeleportItem(teleportItem);
    }


}
