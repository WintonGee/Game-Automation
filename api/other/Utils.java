package scripts.main_package.api.other;

import lombok.val;
import org.tribot.script.sdk.Worlds;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GrandExchangeOffer;
import org.tribot.script.sdk.types.World;
import org.tribot.script.sdk.types.definitions.ItemDefinition;

import java.text.DecimalFormat;
import java.util.Optional;

public class Utils {

    //TODO check grand exchange items?
    public static int getTotalItemCount(int id) {
        int total = 0;

        total += Query.equipment().idEquals(id).sumStacks();
        total += Query.inventory().idEquals(id).sumStacks();

        total += BankCache.getStack(id);

        return total;
    }

    public static int getTotalItemCountWithNoted(int id) {
        return getTotalItemCountWithNoted(id, true);
    }

    public static int getTotalItemCountWithNoted(int id, boolean checkBank) {
        int notedId = getNotedId(id);
        int total = 0;

        total += Query.equipment().idEquals(id).sumStacks();
        total += Query.inventory().idEquals(id, notedId).sumStacks();

        if (checkBank)
            total += BankCache.getStack(id);

        return total;
    }

    //TODO implemenet
    public static int getCountInGrandExchange(int id) {
        int sellOffersAmount = Query.grandExchangeOffers()
                .itemIdEquals(id)
                .filter(grandExchangeOffer -> grandExchangeOffer.getType() == GrandExchangeOffer.Type.SELL)
                .stream().mapToInt(o -> {
                    int initialAmount = o.getTotalQuantity();
                    int soldAmount = o.getTransferredItemQuantity();
                    return initialAmount - soldAmount;

                }).sum();

        int boughtAmount = Query.grandExchangeOffers()
                .itemIdEquals(id)
                .filter(grandExchangeOffer -> grandExchangeOffer.getType() == GrandExchangeOffer.Type.BUY)
                .stream().mapToInt(GrandExchangeOffer::getTransferredItemQuantity).sum();

        return sellOffersAmount + boughtAmount;
    }

    // Returns: id of item, which takes up one space. Either noted or stackable.
    public static int getMonoSpaceId(int id) {
        Optional<ItemDefinition> def = ItemDefinition.get(id);
        if (def.isEmpty())
            return id;
        ItemDefinition itemDef = def.get();
        if (itemDef.isStackable())
            return id;
        return itemDef.getNotedItemId();
    }

    public static int getNotedId(int id) {
        Optional<ItemDefinition> def = ItemDefinition.get(id);
        if (def.isEmpty())
            return id;

        val definition = def.get();
        if (definition.isStackable() || definition.isNoted())
            return id;

        return def.map(ItemDefinition::getNotedItemId).orElse(id);
    }

    public static String getItemName(int id) {
        return ItemDefinition.get(id).map(ItemDefinition::getName).orElse("Name Error: " + id);
    }

    public static int getIntFromString(String string) {
        String numberOnly = string.replaceAll("[^0-9]", "");
        return numberOnly.length() == 0 ? 0 : Integer.parseInt(numberOnly);
    }

    public static String getStringAddedCommaToNumber(int num) {
        val format = new DecimalFormat("#,###.00");
        return format.format(num);
    }

    public boolean isInP2PWorld() {
        return Worlds.getCurrent().filter(World::isMembers).isPresent();
    }

    public static boolean isMembers(int id) {
        return ItemDefinition.get(id).filter(ItemDefinition::isMembersOnly).isPresent();
    }

    public static boolean isStackable(int id) {
        return ItemDefinition.get(id).filter(ItemDefinition::isStackable).isPresent();
    }

    public static boolean isTradeable(int id) {
        return ItemDefinition.get(id).filter(ItemDefinition::isGrandExchangeTradeable).isPresent();
    }

}
