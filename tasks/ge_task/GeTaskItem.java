package scripts.main_package.tasks.ge_task;

import lombok.Data;
import org.tribot.script.sdk.GrandExchange.CreateOfferConfig;
import org.tribot.script.sdk.GrandExchange.CreateOfferConfig.CreateOfferConfigBuilder;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.pricing.Pricing;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GrandExchangeOffer.Type;
import scripts.main_package.api.legacy_tribot.General;
import scripts.main_package.api.other.UtilTime;
import scripts.main_package.api.other.Utils;
import scripts.main_package.item.DetailedItem;

//TODO need a method where it also checks the alts
@Data
public class GeTaskItem {

    private final long minWait = UtilTime.getMillisSecond(15);
    private final long maxWait = UtilTime.getMillisMinute(2);

    // Detailed item with the quantity requested.
    // Only used for checking if have the items
    private DetailedItem detailedItem;
    private int itemId, quantity;
    private int price, adjustments = 0;
    Type type;
    private long cooldownUntil; // Do not adjust this item until cooldown expires.

    // When buying provide the list of total items.
    // When selling provide the list of final amounts.
    public GeTaskItem(DetailedItem detailedItem, Type type) {
        this(detailedItem, type, 1);
    }

    public GeTaskItem(DetailedItem detailedItem, Type type, int multiplier) {
        this.setType(type);
        this.setItemId(detailedItem.getItemId());

        // Only need be buy 1 set if item does not deplete.
        int defaultQuantity = detailedItem.getQuantity();
        int quantityToSet = detailedItem.isDepletes() ? multiplier * defaultQuantity : defaultQuantity;
        this.setQuantity(quantityToSet);

        this.setPrice(Pricing.lookupPrice(itemId).orElse(0));

        // TODO add alt item support
        // For this alt item support, just use it for checking, do not buy the alt items
        // Will need this support when buying bonds because id changes after purchase
        this.setDetailedItem(new DetailedItem(this.itemId, this.quantity));
        detailedItem.getAlternativeItems().forEach(alt -> {
            int id = alt.getItemId();
            this.detailedItem.addAlt(id, quantityToSet);
        });
    }

    // TODO will need to check the alternative items
    // for buying, support bonds?
    public boolean isComplete() {
        if (price < 0)
            return true;

//		int haveAmount = Utils.getTotalItemCountWithNoted(itemId);
        if (type == Type.BUY) {
            return this.detailedItem.isHaveMainItems(true) || this.detailedItem.isHaveAltItems(true);
        }

        // Ensures the items in GE offers are cleared.
        int haveAmount = Utils.getTotalItemCountWithNoted(itemId);
        int geAmount = Query.grandExchangeOffers().itemIdEquals(itemId).stream().mapToInt(i -> i.getTotalQuantity())
                .sum();
        return haveAmount + geAmount <= quantity;
    }

    public boolean isOnCooldown() {
        return cooldownUntil > UtilTime.getTime();
    }

    public int getRemainingBuyValue() {
        return price * getRemainingBuyQuantity();
    }

    // Used for buying, determining remaining gold
    public int getRemainingBuyQuantity() {
        int haveAmount = Utils.getTotalItemCountWithNoted(itemId);
        int amountNeeded = quantity - haveAmount;
        return amountNeeded < 0 ? 0 : amountNeeded;
    }

    public CreateOfferConfig getConfig() {
        int haveAmount = Utils.getTotalItemCountWithNoted(itemId);
        int buyQuantity = quantity - haveAmount;
        int sellQuantity = Utils.getTotalItemCountWithNoted(itemId, false);
        int newQuantity = this.type == Type.BUY ? buyQuantity : sellQuantity;

        if (newQuantity <= 0 || this.isComplete()) { // Return null to skip offer.
            Log.warn("[GeTaskItem] Skipping Config: " + Utils.getItemName(itemId));
            return null;
        }

        CreateOfferConfigBuilder builder = CreateOfferConfig.builder();
        int configId = getConfigId();

        builder.type(type);
        builder.itemId(configId);
        builder.quantity(newQuantity);

        // Determines how to set the price
        // Use button presses if failed to determine price
        if (price > 0)
            builder.price(price);
        else
            builder.priceAdjustment(adjustments);

        Log.info("[" + type + "][GeTaskItem] Config Loaded " + ", Id: " + configId + ", Quantity: " + newQuantity
                + ", Price: " + price);
        return builder.build();
    }

    // Sometimes when selling, might not have unnoted item.
    // In this case, the noted id must be used.
    private int getConfigId() {
        if (type == Type.BUY)
            return itemId;
        boolean useNotedId = Query.inventory().idEquals(itemId).count() == 0;
        return useNotedId ? Utils.getNotedId(itemId) : itemId;
    }

    public void adjustOffer() {
        // Buying -> Increase the price
        // Selling -> Decrease the price
        int beforePrice = price;
        double rand = General.randomDouble(0.05, 0.1);
        int randVal = (int) Math.floor(rand * price) + 1;
        if (this.type == Type.BUY) {
            price += randVal;
            // TODO big increase in case its a tool
        } else {
            price -= randVal;
        }
        adjustments += General.random(2, 4);

        // TODO adjust based on the pricing of item?
        long waitTime = General.randomLong(minWait, maxWait);
        long cooldownTime = UtilTime.getTime() + waitTime;
        this.setCooldownUntil(cooldownTime);

        Log.debug("Setting next offer pricing for " + Utils.getItemName(itemId) + ", " + beforePrice + " -> " + price
                + ", Wait: " + waitTime);
    }

}
