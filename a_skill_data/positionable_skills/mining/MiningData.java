package scripts.main_package.a_skill_data.positionable_skills.mining;

import lombok.Getter;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.main_package.a_skill_data.ItemCreationMainTask;
import scripts.main_package.a_skill_data.util.SkillingInteractable;
import scripts.main_package.a_skill_data.util.SkillingItemSelection;
import scripts.main_package.a_skill_data.util.SkillingLocation;
import scripts.main_package.api.other.Utils;
import scripts.main_package.api.task.MainTask;
import scripts.raw_data.ItemID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MiningData {

    static Skill MINING = Skill.MINING;

    public enum Pickaxe {

        RUNE_PICKAXE(ItemID.RUNE_PICKAXE, 41, 40),
        ADAMANT_PICKAXE(ItemID.ADAMANT_PICKAXE, 31, 30),
        MITHRIL_PICKAXE(ItemID.MITHRIL_PICKAXE, 21, 20),
        STEEL_PICKAXE(ItemID.STEEL_PICKAXE, 6, 5),
        IRON_PICKAXE(ItemID.IRON_PICKAXE, 1, 1),
        BRONZE_PICKAXE(ItemID.BRONZE_PICKAXE, 1, 1);

        private final int id;
        private final int mininglevel;
        private final int attackLevel;

        Pickaxe(int id, int mininglevel, int attackLevel) {
            this.id = id;
            this.mininglevel = mininglevel;
            this.attackLevel = attackLevel;
        }

    }

    public enum RockIds {
        AMETHYST(30371, 30372),
        RUNITE(11376, 11377),
        ADAMANTITE(11374, 11375),
        MITHRIL(11372, 11373),
        GRANITE(11387),
        VOLCANIC_SULPHUR(28496, 28497, 28498),
        GEM(11380),
        GOLD(11370, 11371),
        SANDSTONE(11386),
        COAL(11366, 11367),
        VOLCANIC_ASH(30985),
        SILVER(11368, 11369),
        IRON(11364, 11365),
        TIN(11360, 11361),
        COPPER(10943, 11161),
        CLAY(11362, 11363);
//        ESSENCE(34773); // Don't mine this.

        int[] ids;

        RockIds(int... ids) {
            this.ids = ids;
        }
    }

    public enum Rock {

        AMETHYST(92, ItemID.AMETHYST, 240, RockIds.AMETHYST),
        RUNITE(85, ItemID.RUNITE_ORE, 125, RockIds.RUNITE),
        ADAMANTITE(70, ItemID.ADAMANTITE_ORE, 95, RockIds.ADAMANTITE),
        MITHRIL(55, ItemID.MITHRIL_ORE, 80, RockIds.MITHRIL),
        //        GRANITE(45, ItemID.GRANITE_2KG, 50, RockIds.GRANITE),//TODO fix the product ids
        VOLCANIC_SULPHUR(42, ItemID.VOLCANIC_SULPHUR, 25, RockIds.VOLCANIC_SULPHUR),
        //        GEM(40, ItemID.UNCUT_EMERALD, 65, RockIds.GEM), //TODO fix the product ids
        GOLD(40, ItemID.GOLD_ORE, 65, RockIds.GOLD),
        //        SANDSTONE(35, ItemID.SANDSTONE_1KG, 30, RockIds.SANDSTONE), //TODO fix the product ids
        COAL(30, ItemID.COAL, 50, RockIds.COAL),
        VOLCANIC_ASH(22, ItemID.VOLCANIC_ASH, 10, RockIds.VOLCANIC_ASH),
        SILVER(20, ItemID.SILVER_ORE, 40, RockIds.SILVER),
        IRON(15, ItemID.IRON_ORE, 35, RockIds.IRON),
        TIN(1, ItemID.TIN_ORE, 17.5, RockIds.TIN),
        COPPER(1, ItemID.COPPER_ORE, 17.5, RockIds.COPPER),
        CLAY(1, ItemID.CLAY, 5, RockIds.CLAY);

        private final int level;
        private final int productId;
        private final double exp;

        int[] ids;

        Rock(int level, int productId, double exp, RockIds rockIds) {
            this.level = level;
            this.productId = productId;
            this.exp = exp;
            this.ids = rockIds.ids;
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(productId);
            skillingItemSelection.setSkill(MINING);
            return skillingItemSelection;
        }

        public SkillingInteractable getSkillingInteractable() {
            SkillingInteractable skillingInteractable = new SkillingInteractable(this.productId);
            for (int id : ids)
                skillingInteractable.idsList.add(id);
            return skillingInteractable;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Mining} Mining: " + Utils.getItemName(productId));
            itemCreationMainTask.ticksPerActivity = 10;
            itemCreationMainTask.addSkillRequirement(Skill.MINING, level);

            itemCreationMainTask.addExperienceReward(Skill.MINING, exp);
            itemCreationMainTask.addProduct(productId);

            itemCreationMainTask.maxItemSets = 100000; // No Limit

            //TODO add the best pickaxe

            //TODO bank location
            //TODO steps to create

            return itemCreationMainTask;
        }

    }

    public enum MiningArea {


        AL_KHARID(new SkillingLocation("Al Kharid",
                Area.fromRectangle(new WorldTile(3289, 3319, 0), new WorldTile(3310, 3275, 0)))
                .addInteractables(Rock.ADAMANTITE.getSkillingInteractable().getCopy().setSpecificTile(new WorldTile(3300, 3318, 0)))
                .addInteractables(Rock.MITHRIL.getSkillingInteractable().getCopy().setSpecificTile(new WorldTile(3304, 3305, 0)))
                .addInteractables(Rock.GOLD.getSkillingInteractable().getCopy().setSpecificTile(new WorldTile(3294, 3287, 0)))
                .addInteractables(Rock.COAL.getSkillingInteractable().getCopy().setSpecificTile(new WorldTile(3304, 3300, 0)))
                .addInteractables(Rock.SILVER.getSkillingInteractable().getCopy().setSpecificTile(new WorldTile(3295, 3304, 0)))
                .addInteractables(Rock.IRON.getSkillingInteractable().getCopy().setSpecificTile(new WorldTile(3295, 3284, 0)))
                .addInteractables(Rock.COPPER.getSkillingInteractable().getCopy().setSpecificTile(new WorldTile(3297, 3315, 0)))
                .addInteractables(Rock.TIN.getSkillingInteractable().getCopy().setSpecificTile(new WorldTile(3302, 3316, 0)))
        ),

        BARBARIAN_VILLAGE(new SkillingLocation("Barbarian Village",
                Area.fromRectangle(new WorldTile(3078, 3423, 0), new WorldTile(3085, 3416, 0)))
                .addInteractables(Rock.TIN.getSkillingInteractable(), Rock.COAL.getSkillingInteractable())
        ),

        NORTH_BRIMHAVEN(new SkillingLocation("North Brimhaven Mine",
                Area.fromRectangle(new WorldTile(2732, 3224, 0), new WorldTile(2735, 3227, 0)))
                .addInteractables(Rock.GOLD.getSkillingInteractable())
                .setCombatLevelRequirement(41) // Lv 20 Scorpions
        ),

        CRAFTING_GUILD(new SkillingLocation("Crafting Guild",
                Area.fromPolygon(
                        new WorldTile(2939, 3292, 0),
                        new WorldTile(2943, 3292, 0),
                        new WorldTile(2944, 3291, 0),
                        new WorldTile(2944, 3277, 0),
                        new WorldTile(2943, 3276, 0),
                        new WorldTile(2939, 3276, 0),
                        new WorldTile(2937, 3278, 0),
                        new WorldTile(2937, 3285, 0),
                        new WorldTile(2939, 3287, 0)))
                .addInteractables(Rock.CLAY.getSkillingInteractable(), Rock.SILVER.getSkillingInteractable(), Rock.GOLD.getSkillingInteractable())
                .addRequiredItem(ItemID.BROWN_APRON, 1)
                .addSkillRequirement(Skill.CRAFTING, 40)
        ),

        EAST_LUMBRIDGE_SWAMP(new SkillingLocation("East Lumbridge Swamp",
                Area.fromRectangle(new WorldTile(3221, 3151, 0), new WorldTile(3232, 3142, 0)))
                .addInteractables(Rock.COPPER.getSkillingInteractable(), Rock.TIN.getSkillingInteractable())
        ),

        WEST_LUMBRIDGE_SWAMP(new SkillingLocation("West Lumbridge Swamp",
                Area.fromRectangle(new WorldTile(3142, 3154, 0), new WorldTile(3150, 3142, 0)))
                .addInteractables(Rock.ADAMANTITE.getSkillingInteractable())
                .addInteractables(Rock.COAL.getSkillingInteractable())
                .addInteractables(Rock.MITHRIL.getSkillingInteractable())
        ),

        WEST_FALADOR(new SkillingLocation("West Falador",
                Area.fromRectangle(new WorldTile(2902, 3351, 0), new WorldTile(2913, 3370, 0)))
                .addInteractables(Rock.COPPER.getSkillingInteractable())
                .addInteractables(Rock.TIN.getSkillingInteractable())
                .addInteractables(Rock.COAL.getSkillingInteractable())
                .addInteractables(Rock.IRON.getSkillingInteractable())
        ),

        RIMMINGTON(new SkillingLocation("Rimmington",
                Area.fromRadius(new WorldTile(2977, 3239, 0), 15))
                .addInteractables(Rock.COPPER.getSkillingInteractable())
                .addInteractables(Rock.TIN.getSkillingInteractable())
                .addInteractables(Rock.IRON.getSkillingInteractable())
                .addInteractables(Rock.CLAY.getSkillingInteractable())
                .addInteractables(Rock.GOLD.getSkillingInteractable())
        ),

        SE_VARROCK(new SkillingLocation("South-east Varrock",
                Area.fromRectangle(new WorldTile(3280, 3372, 0), new WorldTile(3291, 3359, 0)))
                .addInteractables(Rock.COPPER.getSkillingInteractable())
                .addInteractables(Rock.TIN.getSkillingInteractable())
                .addInteractables(Rock.IRON.getSkillingInteractable())
        ),

        SW_VARROCK(new SkillingLocation("South-west Varrock",
                Area.fromPolygon(
                        new WorldTile(3171, 3365, 0),
                        new WorldTile(3178, 3372, 0),
                        new WorldTile(3181, 3380, 0),
                        new WorldTile(3186, 3380, 0),
                        new WorldTile(3184, 3369, 0),
                        new WorldTile(3179, 3364, 0),
                        new WorldTile(3173, 3364, 0)))
                .addInteractables(Rock.CLAY.getSkillingInteractable(), Rock.SILVER.getSkillingInteractable(), Rock.GOLD.getSkillingInteractable())
                .setCombatLevelRequirement(13) // Mugger is lv 6
        ),

        ;

        @Getter
        SkillingLocation skillingLocation;

        MiningArea(SkillingLocation skillingLocation) {
            this.skillingLocation = skillingLocation;
        }


    }

    public static ArrayList<MainTask> getAllMiningMainTasks() {
        ArrayList<MainTask> list = new ArrayList<>();

        // Raw fish
        list.addAll(Arrays.stream(MiningData.Rock.values()).map(MiningData.Rock::getMainTask).collect(Collectors.toList()));

        return list;
    }

}
