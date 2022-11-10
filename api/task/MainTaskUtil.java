package scripts.main_package.api.task;

import scripts.main_package.item.DetailedItem;

public class MainTaskUtil {

    private final static int TICKS_PER_HOUR = 6000, MILLIS_PER_TICK = 600;

    // int activitiesPerHour = TICKS_PER_HOUR / mainTask.ticksPerActivity;
    // Returns the profit per hour
    public static int getProfitPerHour(MainTask mainTask) {
        return (getProfitPerActivity(mainTask) * TICKS_PER_HOUR) / mainTask.ticksPerActivity;
    }

    // Returns the profit from this task
    public static int getProfit(MainTask mainTask) {
        return getProfitPerActivity(mainTask) * mainTask.minItemSets;
    }

    // Returns the profit per activity
    public static int getProfitPerActivity(MainTask mainTask) {
        //TODO rune and arrow costs from combat setup
        int cost = mainTask.inventoryItems.stream()
                .filter(DetailedItem::isDepletes)
                .mapToInt(DetailedItem::getCost)
                .sum();
        int yield = mainTask.productItems.stream()
                .mapToInt(DetailedItem::getCost)
                .sum();
        return yield - cost;
    }

    // Returns the expected runtime in ticks in total for this main task
    public static int getExpectedRuntimeMillis(MainTask mainTask) {
        return mainTask.ticksPerActivity * MILLIS_PER_TICK * mainTask.minItemSets;
    }
}
