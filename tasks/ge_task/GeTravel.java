package scripts.main_package.tasks.ge_task;

import org.tribot.script.sdk.GrandExchange;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Worlds;
import org.tribot.script.sdk.interfaces.Tile;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.GlobalWalking;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.tasks.bank_task.DetailedBankTask;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GeTravel {

    // ring of wealth, skills necklace to the cook's guild
    private final static int[] teleItemIds =
            {
                    ItemID.RING_OF_WEALTH_1, ItemID.RING_OF_WEALTH_2, ItemID.RING_OF_WEALTH_3,
                    ItemID.RING_OF_WEALTH_4, ItemID.RING_OF_WEALTH_5,
                    ItemID.AMULET_OF_GLORY1, ItemID.AMULET_OF_GLORY2, ItemID.AMULET_OF_GLORY3,
                    ItemID.AMULET_OF_GLORY4, ItemID.AMULET_OF_GLORY5, ItemID.AMULET_OF_GLORY6,
                    ItemID.SKILLS_NECKLACE1, ItemID.SKILLS_NECKLACE2, ItemID.SKILLS_NECKLACE3,
                    ItemID.SKILLS_NECKLACE4, ItemID.SKILLS_NECKLACE5, ItemID.SKILLS_NECKLACE6,
                    ItemID.VARROCK_TELEPORT
            };

    private final static int INVALID_TELE_ID = -1;

    private static final Tile GE_TILE = new WorldTile(3167, 3489, 0);

    // TODO Check for bank teleport items if too far
    public static boolean travel() {
        if (GrandExchange.isNearby())
            return true;

        if (!isShouldSkipTeleportItem() && !setTeleportItem()) {
            Log.warn("[GeTravel] Failed to grab a teleport item");
            return false;
        }

        // TODO grab items and walk there
        return GlobalWalking.walkTo(GE_TILE);
    }

    private static boolean isShouldSkipTeleportItem() {
        if (Worlds.getCurrent().stream().noneMatch(w -> w.isMembers())) {
            Log.warn("[GeTravel] Skipping teleport item, not in members world.");
            return true;
        }
        return GE_TILE.distanceTo(MyPlayer.getPosition()) <= 100;
    }

    private static boolean setTeleportItem() {
        if (isTeleportItemOnCharacter()) {
            Log.debug("[GeTravel] Teleport item is already on character, skip banking!");
            return true;
        }

        int bestTeleportId = getBestTeleportInBank();
        boolean noTeleports = bestTeleportId == INVALID_TELE_ID;
        if (noTeleports) {
            Log.warn("[GeTravel] No teleport items in bank to use.");
            return true;
        }

        Log.info("[GeTravel] Attempting to grab teleport item from bank: " + Utils.getItemName(bestTeleportId));
        ArrayList<DetailedItem> itemsList = new ArrayList<DetailedItem>();
        itemsList.add(new DetailedItem(bestTeleportId));
        DetailedBankTask bankTask = new DetailedBankTask(itemsList).setAllowExtraSets(false);
        bankTask.update();
        return bankTask.handle();
    }

    private static boolean isTeleportItemOnCharacter() {
        return Query.inventory().idEquals(teleItemIds).isAny() || Query.equipment().idEquals(teleItemIds).isAny();
    }

    private static int getBestTeleportInBank() {
        List<Integer> list = Query.bank().idEquals(teleItemIds).stream().map(item -> item.getId()).collect(Collectors.toList());
        Iterator<Integer> iterator = Arrays.stream(teleItemIds).iterator();
        while (iterator.hasNext()) {
            int teleId = iterator.next();
            boolean haveItem = list.contains(teleId);
            if (haveItem)
                return teleId;
        }

        return INVALID_TELE_ID;
    }

}
