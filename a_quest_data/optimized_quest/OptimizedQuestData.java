package scripts.main_package.a_quest_data.optimized_quest;

import lombok.Getter;
import scripts.main_package.a_quest_data.requirement.item.ItemRequirement;
import scripts.main_package.item.DetailedItem;
import scripts.raw_data.ItemID;

import java.util.ArrayList;

public class OptimizedQuestData {

    public enum OptimizedQuestItems {

        // Items to get before the quest start, all items obtained through GE
        CABBAGE(ItemID.CABBAGE),
        IRON_CHAINBODY(ItemID.IRON_CHAINBODY),
        BRONZE_MED_HELM(ItemID.BRONZE_MED_HELM),
        POT_OF_FLOUR(ItemID.POT_OF_FLOUR),
        POT_OF_FLOUR_2(ItemID.POT_OF_FLOUR, 2),
        EGG(ItemID.EGG),
        BUCKET_OF_MILK(ItemID.BUCKET_OF_MILK),
        COINS_100(ItemID.COINS_995, 100),
        COINS_116(ItemID.COINS_995, 116),
        BUCKET_OF_WATER(ItemID.BUCKET_OF_WATER),
        BONES_25(ItemID.BONES, 25),
        CLAY_6(ItemID.CLAY, 6),
        COPPER_ORE_4(ItemID.COPPER_ORE, 4),
        IRON_ORE_2(ItemID.IRON_ORE, 2),
        SPADE(ItemID.SPADE),
        BLUE_DYE(ItemID.BLUE_DYE),
        ORANGE_DYE(ItemID.ORANGE_DYE),
        GOBLIN_MAIL_3(ItemID.GOBLIN_MAIL, 3),
        BLACK_BEAD(ItemID.BLACK_BEAD),
        RED_BEAD(ItemID.RED_BEAD),
        YELLOW_BEAD(ItemID.YELLOW_BEAD),
        WHITE_BEAD(ItemID.WHITE_BEAD),
        REDBERRY_PIE(ItemID.REDBERRY_PIE),
        IRON_PICKAXE(ItemID.IRON_PICKAXE),
        IRON_BAR_2(ItemID.IRON_BAR, 2),
        WHITE_APRON(ItemID.WHITE_APRON),
        REDBERRIES(ItemID.REDBERRIES),
        SOFT_CLAY(ItemID.SOFT_CLAY),
        BALLS_OF_WOOL_3(ItemID.BALL_OF_WOOL, 3),
        BALLS_OF_WOOL_20(ItemID.BALL_OF_WOOL, 20),
        BALLS_OF_WOOL_23(ItemID.BALL_OF_WOOL, 23),
        YELLOW_DYE(ItemID.YELLOW_DYE),
        ASHES(ItemID.ASHES),
        BRONZE_BAR(ItemID.BRONZE_BAR),
        PINK_SKIRT(ItemID.PINK_SKIRT),
        BEER(ItemID.BEER),
        BEER_4(ItemID.BEER, 4),
        ROPE(ItemID.ROPE),
        CADAVA_BERRIES(ItemID.CADAVA_BERRIES),
        HAMMER(ItemID.HAMMER),
        GARLIC(ItemID.GARLIC),
        //        BURNT_MEAT(ItemID.BURNT_MEAT), //TODO
        COOKED_MEAT(ItemID.COOKED_MEAT),
        EYE_OF_NEWT(ItemID.EYE_OF_NEWT),
        ONION(ItemID.ONION),

        // Below here are items obtained through the quests.
        AIR_TALISMAN(ItemID.AIR_TALISMAN),
        SKULL(ItemID.SKULL), //TODO check
        CHEST_KEY(ItemID.CHEST_KEY), //TODO check
        RESEARCH_PACKAGE(ItemID.RESEARCH_PACKAGE), //TODO check
        KEY_PRINT(ItemID.KEY_PRINT) //TODO check

        ;

        @Getter
        int id;
        @Getter
        int quantity;

        OptimizedQuestItems(int id) {
            this(id, 1);
        }

        OptimizedQuestItems(int id, int quantity) {
            this.id = id;
            this.quantity = quantity;
        }

        public DetailedItem getDetailedItem() {
            return new DetailedItem(this.id, this.quantity);
        }

        public ItemRequirement getItemRequirement() {
            return new ItemRequirement(id, quantity);
        }

    }

    public ArrayList<OptimizedQuestItems> firstInventory() {
        ArrayList<OptimizedQuestItems> optimizedQuestItems = new ArrayList<>();
        optimizedQuestItems.add(OptimizedQuestItems.EGG);
        optimizedQuestItems.add(OptimizedQuestItems.POT_OF_FLOUR);
        optimizedQuestItems.add(OptimizedQuestItems.BUCKET_OF_MILK);
        optimizedQuestItems.add(OptimizedQuestItems.BALLS_OF_WOOL_20);
        optimizedQuestItems.add(OptimizedQuestItems.COINS_100);
        return optimizedQuestItems;
    }

    public ArrayList<OptimizedQuestItems> secondInventory() {
        ArrayList<OptimizedQuestItems> optimizedQuestItems = new ArrayList<>();
        optimizedQuestItems.add(OptimizedQuestItems.AIR_TALISMAN);
        optimizedQuestItems.add(OptimizedQuestItems.BLACK_BEAD);
        optimizedQuestItems.add(OptimizedQuestItems.RED_BEAD);
        optimizedQuestItems.add(OptimizedQuestItems.WHITE_BEAD);
        optimizedQuestItems.add(OptimizedQuestItems.YELLOW_BEAD);
        optimizedQuestItems.add(OptimizedQuestItems.ROPE);
        optimizedQuestItems.add(OptimizedQuestItems.YELLOW_DYE);
        optimizedQuestItems.add(OptimizedQuestItems.POT_OF_FLOUR);
        optimizedQuestItems.add(OptimizedQuestItems.BUCKET_OF_WATER);
        optimizedQuestItems.add(OptimizedQuestItems.ASHES);
        optimizedQuestItems.add(OptimizedQuestItems.BALLS_OF_WOOL_3);
        optimizedQuestItems.add(OptimizedQuestItems.REDBERRIES);
        optimizedQuestItems.add(OptimizedQuestItems.SOFT_CLAY);
        optimizedQuestItems.add(OptimizedQuestItems.WHITE_APRON);
        optimizedQuestItems.add(OptimizedQuestItems.COINS_100);
        optimizedQuestItems.add(OptimizedQuestItems.REDBERRY_PIE);
        return optimizedQuestItems;
    }

    public ArrayList<OptimizedQuestItems> thirdInventory() {
        ArrayList<OptimizedQuestItems> optimizedQuestItems = new ArrayList<>();
        optimizedQuestItems.add(OptimizedQuestItems.EYE_OF_NEWT);
        optimizedQuestItems.add(OptimizedQuestItems.COOKED_MEAT);
        optimizedQuestItems.add(OptimizedQuestItems.ONION);
        return optimizedQuestItems;
    }

    public ArrayList<OptimizedQuestItems> fourthInventory() {
        ArrayList<OptimizedQuestItems> optimizedQuestItems = new ArrayList<>();
        optimizedQuestItems.add(OptimizedQuestItems.CADAVA_BERRIES);
        //TODO weapon
        optimizedQuestItems.add(OptimizedQuestItems.BEER);
        optimizedQuestItems.add(OptimizedQuestItems.COINS_100);
        optimizedQuestItems.add(OptimizedQuestItems.BUCKET_OF_WATER);
        optimizedQuestItems.add(OptimizedQuestItems.BRONZE_BAR);
        optimizedQuestItems.add(OptimizedQuestItems.SKULL);
        optimizedQuestItems.add(OptimizedQuestItems.CHEST_KEY);
        optimizedQuestItems.add(OptimizedQuestItems.RESEARCH_PACKAGE);
        optimizedQuestItems.add(OptimizedQuestItems.KEY_PRINT);
        return optimizedQuestItems;
    }


}
