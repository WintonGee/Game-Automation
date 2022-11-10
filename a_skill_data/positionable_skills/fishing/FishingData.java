package scripts.main_package.a_skill_data.positionable_skills.fishing;

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

public class FishingData {

    public enum FishingTool {

        SMALL_FISHING_NET("Net", ItemID.SMALL_FISHING_NET),
        BIG_FISHING_NET("Net", ItemID.BIG_FISHING_NET),

        BAIT_FISHING_ROD("Bait", ItemID.FISHING_ROD, FishingBait.FISHING_BAIT),
        LURE_FLY_FISHING_ROD("Lure", ItemID.FLY_FISHING_ROD, FishingBait.FEATHER),

        HARPOON("Harpoon", ItemID.HARPOON), //TODO alternate, dragon harpoon
        LOBSTER_POT("Cage", ItemID.LOBSTER_POT);


        String action;
        int toolId;
        FishingBait fishingBait;

        FishingTool(String action, int toolId, FishingBait fishingBait) {
            this(action, toolId);
            this.fishingBait = fishingBait;
        }

        FishingTool(String action, int toolId) {
            this.action = action;
            this.toolId = toolId;
        }


    }

    public enum FishingBait {

        FEATHER(ItemID.FEATHER, 30000),
        FISHING_BAIT(ItemID.FISHING_BAIT, 8000);

        int id, buyLimit;

        FishingBait(int id, int buyLimit) {
            this.id = id;
            this.buyLimit = buyLimit;
        }

    }

    public enum Fish {

        // Net-bait
        RAW_SHRIMPS(1, ItemID.RAW_SHRIMPS, FishingTool.SMALL_FISHING_NET, 10),
        RAW_SARDINE(5, ItemID.RAW_SARDINE, FishingTool.BAIT_FISHING_ROD, 20),
        RAW_HERRING(10, ItemID.RAW_HERRING, FishingTool.BAIT_FISHING_ROD, 30),
        RAW_ANCHOVIES(15, ItemID.RAW_ANCHOVIES, FishingTool.SMALL_FISHING_NET, 40),

        // Lure-bait
        RAW_TROUT(20, ItemID.RAW_TROUT, FishingTool.LURE_FLY_FISHING_ROD, 50),
        RAW_PIKE(25, ItemID.RAW_PIKE, FishingTool.BAIT_FISHING_ROD, 60),
        RAW_SALMON(30, ItemID.RAW_SALMON, FishingTool.LURE_FLY_FISHING_ROD, 70),

        // Cage-harpoon
        RAW_TUNA(35, ItemID.RAW_TUNA, FishingTool.HARPOON, 80),
        RAW_LOBSTER(40, ItemID.RAW_LOBSTER, FishingTool.LOBSTER_POT, 90),
        RAW_SWORDFISH(50, ItemID.RAW_SWORDFISH, FishingTool.HARPOON, 100),

        ;

        int level;
        int fishId;
        double exp;
        FishingTool fishingTool;

        Fish(int level, int fishId, FishingTool fishingTool, int exp) {
            this.level = level;
            this.fishId = fishId;
            this.fishingTool = fishingTool;
            this.exp = exp;

        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(fishId);
            skillingItemSelection.setSkill(Skill.FISHING);
            return skillingItemSelection;
        }

        public SkillingInteractable getSkillingInteractable() {
            SkillingInteractable skillingInteractable = new SkillingInteractable(this.fishId)
                    .setInteractionType(SkillingInteractable.InteractionType.NPC)
                    .setAction(fishingTool.action);
            return skillingInteractable;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Fishing} Fish: " + Utils.getItemName(fishId));
            itemCreationMainTask.ticksPerActivity = 10;
//            itemCreationMainTask.maxItemSets = 13000; // 13000 buy limit //TODO figure this out
            itemCreationMainTask.addSkillRequirement(Skill.FISHING, level);

            itemCreationMainTask.addExperienceReward(Skill.FISHING, exp);
            itemCreationMainTask.addProduct(fishId);

            itemCreationMainTask.addTool(this.fishingTool.toolId);

            itemCreationMainTask.maxItemSets = 100000; // No limit

            FishingBait fishingBait = this.fishingTool.fishingBait;
            if (fishingBait != null) {
                itemCreationMainTask.maxItemSets = fishingBait.buyLimit;
                itemCreationMainTask.addMaterial(fishingBait.id);
            }


            //TODO bank location
            //TODO steps to create

            return itemCreationMainTask;
        }

    }

    public enum FishingSpot {

        NET_BAIT(Fish.RAW_SHRIMPS, Fish.RAW_SARDINE, Fish.RAW_HERRING, Fish.RAW_ANCHOVIES),
        LURE_BAIT(Fish.RAW_TROUT, Fish.RAW_PIKE, Fish.RAW_SALMON),
        CAGE_HARPOON(Fish.RAW_TUNA, Fish.RAW_LOBSTER, Fish.RAW_SWORDFISH),
        HARPOON_ONLY(Fish.RAW_TUNA, Fish.RAW_SWORDFISH)

        ;

        Fish[] fish;

        FishingSpot(Fish... fish) {
            this.fish = fish;
        }

        public SkillingInteractable[] getSkillingInteractables() {
            return Arrays.stream(fish)
                    .map(Fish::getSkillingInteractable)
                    .toArray(SkillingInteractable[]::new);
        }
    }

    public enum FishingArea {

        AL_KHARID(new SkillingLocation("Al Kharid",
                Area.fromPolygon(
                        new WorldTile(3265, 3151,0),
                        new WorldTile(3269, 3151,0),
                        new WorldTile(3279, 3140,0),
                        new WorldTile(3276, 3137,0),
                        new WorldTile(3263, 3148,0)
                ))
                .addInteractables(FishingSpot.NET_BAIT.getSkillingInteractables())
                .setF2P(true)
        ),

        CATHERBY(new SkillingLocation("Catherby",
                Area.fromPolygon(
                        new WorldTile(2835, 3434, 0),
                        new WorldTile(2845, 3433, 0),
                        new WorldTile(2848, 3431, 0),
                        new WorldTile(2852, 3426, 0),
                        new WorldTile(2856, 3426, 0),
                        new WorldTile(2858, 3429, 0),
                        new WorldTile(2863, 3429, 0),
                        new WorldTile(2863, 3425, 0),
                        new WorldTile(2858, 3424, 0),
                        new WorldTile(2850, 3423, 0),
                        new WorldTile(2846, 3428, 0),
                        new WorldTile(2833, 3431, 0)
                ))
                .addInteractables(FishingSpot.NET_BAIT.getSkillingInteractables())
                .addInteractables(FishingSpot.CAGE_HARPOON.getSkillingInteractables())
//                .addInteractables(FishingSpot.BIGNET_HARPOON.getSkillingInteractables()) //TODO
                .addInteractables(FishingSpot.HARPOON_ONLY.getSkillingInteractables())
        ),

        DRAYNOR_VILLAGE(new SkillingLocation("Draynor Village",
                Area.fromRectangle(new WorldTile(3085, 3226, 0), new WorldTile(3088, 3230, 0)))
                .addInteractables(FishingSpot.NET_BAIT.getSkillingInteractables())
                .setF2P(true)
        ),

        LUMBRIDGE(new SkillingLocation("Lumbridge",
                Area.fromRectangle(new WorldTile(3238, 3239, 0), new WorldTile(3241, 3255, 0)))
                .addInteractables(FishingSpot.NET_BAIT.getSkillingInteractables())
                .setF2P(true)
        ),

        LUMBRIDGE_SWAMP(new SkillingLocation("Lumbridge Swamp",
                Area.fromRectangle(new WorldTile(3238, 3144, 0), new WorldTile(3247, 3160, 0)))
                .addInteractables(FishingSpot.NET_BAIT.getSkillingInteractables())
                .setF2P(true)
        ),

        MUSA_POINT(new SkillingLocation("Musa Point",
                Area.fromRectangle(new WorldTile(2923, 3176, 0), new WorldTile(2926, 3183, 0)))
                .addInteractables(FishingSpot.NET_BAIT.getSkillingInteractables())
                .addInteractables(FishingSpot.CAGE_HARPOON.getSkillingInteractables())
                .setF2P(true)
        ),

        PORT_SARIM(new SkillingLocation("Port Sarim",
                Area.fromRectangle(new WorldTile(2985, 3174, 0), new WorldTile(2988, 3178, 0)))
                .addInteractables(FishingSpot.NET_BAIT.getSkillingInteractables())
                .setF2P(true)
        ),

        /*
    BARBARIAN_VILLAGE(new Location("Barbarian Village", new Area(
            new int[][]{
                    {3102, 3422},
                    {3102, 3428},
                    {3106, 3432},
                    {3106, 3436},
                    {3111, 3436},
                    {3111, 3430},
                    {3106, 3425},
                    {3106, 3422}
            }
    ))),

    SHILO_VILLAGE(new Location("Shilo Village", new Area(2857, 2970, 2861, 2973))),
    FISHING_GUILD_NORTH(new Location("Fishing Guild North", new Area(2599, 3419, 2606, 3426))),
    FISHING_GUILD_SOUTH(new Location("Fishing Guild South", new Area(2603, 3410, 2614, 3417))),
    PISCATORIS_FISHING_COLONY(new Location("Piscatoris Fishing Colony", new Area(2307, 3697, 2312, 3703)));
         */;

        @Getter
        SkillingLocation skillingLocation;

        FishingArea(SkillingLocation skillingLocation) {
            this.skillingLocation = skillingLocation;
        }


    }

    public static ArrayList<MainTask> getAllFishingMainTasks() {
        ArrayList<MainTask> list = new ArrayList<>();

        // Raw fish
        list.addAll(Arrays.stream(FishingData.Fish.values()).map(FishingData.Fish::getMainTask).collect(Collectors.toList()));

        return list;
    }


}
