package scripts.main_package.a_skill_data.positionable_skills.woodcutting;

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

public class WoodcuttingData {

    static Skill WOODCUTTING = Skill.WOODCUTTING;

    public enum Trees {
        MAGIC("Magic tree", 75, ItemID.MAGIC_LOGS, 250),
        YEW("Yew", 60, ItemID.YEW_LOGS, 175),
        MAPLE("Maple tree", 45, ItemID.MAPLE_LOGS, 100),
        WILLOW("Willow", 30, ItemID.WILLOW_LOGS, 67.5),
        OAK("Oak", 15, ItemID.OAK_LOGS, 37.5),
        TREE("Tree", 1, ItemID.LOGS, 25);

        @Getter
        private final String name;
        @Getter
        private final int level;
        @Getter
        private final int productId;
        @Getter
        private final double exp;

        Trees(String name, int level, int productId, double exp) {
            this.name = name;
            this.level = level;
            this.productId = productId;
            this.exp = exp;
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(productId);
            skillingItemSelection.setSkill(WOODCUTTING);
            return skillingItemSelection;
        }

        public SkillingInteractable getSkillingInteractable() {
            SkillingInteractable skillingInteractable = new SkillingInteractable(this.productId);
            skillingInteractable.namesList.add(name);
            return skillingInteractable;
        }

        public MainTask getMainTask() {
            ItemCreationMainTask itemCreationMainTask = new ItemCreationMainTask("{Woodcutting} Tree: " + Utils.getItemName(productId));
            itemCreationMainTask.ticksPerActivity = 10;
            itemCreationMainTask.addSkillRequirement(Skill.WOODCUTTING, level);

            itemCreationMainTask.addExperienceReward(Skill.WOODCUTTING, exp);
            itemCreationMainTask.addProduct(productId);

            itemCreationMainTask.maxItemSets = 100000; // No Limit

            //TODO add the best axe

            //TODO bank location
            //TODO steps to create

            return itemCreationMainTask;
        }

    }

    public enum Axe {

        RUNE_AXE(ItemID.RUNE_AXE, 41, 40),
        ADAMANT_AXE(ItemID.ADAMANT_AXE, 31, 30),
        MITHRIL_AXE(ItemID.MITHRIL_AXE, 21, 20),
        STEEL_AXE(ItemID.STEEL_AXE, 6, 5),
        IRON_AXE(ItemID.IRON_AXE, 1, 1),
        BRONZE_AXE(ItemID.BRONZE_AXE, 1, 1);

        private final int id;
        private final int woodcuttinglevel;
        private final int attackLevel;

        Axe(int id, int woodcuttinglevel, int attackLevel) {
            this.id = id;
            this.woodcuttinglevel = woodcuttinglevel;
            this.attackLevel = attackLevel;
        }

    }

    public enum WoodcuttingLocation {
        // Tree
        LUMBRIDGE_TREES(new SkillingLocation("Lumbridge Trees",
                Area.fromRectangle(new WorldTile(3192, 3239,  0), new WorldTile(3202, 3249, 0)))
                .addInteractables(Trees.TREE.getSkillingInteractable()).setF2P(true)
        ),

        NORTH_SEERS_TREES(new SkillingLocation("North Seers' Village Trees",
                Area.fromRectangle(new WorldTile(2706, 3499,  0), new WorldTile(2717, 3504, 0)))
                .addInteractables(Trees.TREE.getSkillingInteractable())
        ),

        DRAYNOR_TREES(new SkillingLocation("Draynor Village Trees",
                Area.fromRectangle(new WorldTile(3103, 3226,  0), new WorldTile(3108, 3232, 0)))
                .addInteractables(Trees.TREE.getSkillingInteractable()).setF2P(true)
        ),


        // Oak
        LUMBRIDGE_OAKS(new SkillingLocation("Lumbridge Oaks",
                Area.fromRectangle(new WorldTile(3202, 3237,  0), new WorldTile(3206, 3247, 0)))
                .addInteractables(Trees.OAK.getSkillingInteractable()).setF2P(true)
        ),

        WEST_VARROCK_OAKS(new SkillingLocation("West Varrock Oaks",
                Area.fromRectangle(new WorldTile(3160, 3410,  0), new WorldTile(3171, 3423, 0)))
                .addInteractables(Trees.OAK.getSkillingInteractable()).setF2P(true)
        ),

        SEERS_VILLAGE_OAKS(new SkillingLocation("Seers' Village Bank Oaks",
                Area.fromRectangle(new WorldTile(2731, 3490,  0), new WorldTile(2734, 3494, 0)))
                .addInteractables(Trees.OAK.getSkillingInteractable())
        ),


        // Willow
        DRAYNOR_VILLAGE_WILLOW(new SkillingLocation("Draynor Village Willows",
                Area.fromRectangle(new WorldTile(3080, 3238,  0), new WorldTile(3091, 3224, 0)))
                .addInteractables(Trees.WILLOW.getSkillingInteractable()).setF2P(true)
                .setCombatLevelRequirement(15) // Wizards are lv7
        ),

        NORTH_SEERS_WILLOW(new SkillingLocation("North Seers' Village Willows",
                Area.fromRectangle(new WorldTile(2707, 3506,  0), new WorldTile(2714, 3514, 0)))
                .addInteractables(Trees.WILLOW.getSkillingInteractable())
        ),


        // Maple
        SOUTH_SEERS_BANK_MAPLE(new SkillingLocation("South Seers' Village Bank Maples",
                Area.fromRectangle(new WorldTile(2728, 3480,  0), new WorldTile(2731, 3482, 0)))
                .addInteractables(Trees.MAPLE.getSkillingInteractable())
        ),

        NORTH_SEERS_BANK_MAPLE(new SkillingLocation("North Seers' Village Bank Maples",
                Area.fromRectangle(new WorldTile(2720, 3498, 0), new WorldTile(2734, 3502, 0)))
                .addInteractables(Trees.MAPLE.getSkillingInteractable())
        ),


        // Yews
        EDGEVILLE_NORTH_YEW(new SkillingLocation("Edgeville North Yew",
                Area.fromRectangle(new WorldTile(3085, 3482, 0), new WorldTile(3089, 3477, 0)))
                .addInteractables(Trees.YEW.getSkillingInteractable()).setF2P(true)
        ),

        EDGEVILLE_SOUTH_YEW(new SkillingLocation("Edgeville South Yew",
                Area.fromRectangle(new WorldTile(3085, 3472, 0), new WorldTile(3091, 3468, 0)))
                .addInteractables(Trees.YEW.getSkillingInteractable()).setF2P(true)
        ),

       FALADOR_LEFT_YEW(new SkillingLocation("Falador Left Yew",
                Area.fromRectangle(new WorldTile(2994, 3313, 0), new WorldTile(2999, 3308, 0)))
                .addInteractables(Trees.YEW.getSkillingInteractable()).setF2P(true)
        ),

        FALADOR_MIDDLE_YEW(new SkillingLocation("Falador Middle Yew",
                Area.fromRectangle(new WorldTile(3017, 3317, 0), new WorldTile(3021, 3313, 0)))
                .addInteractables(Trees.YEW.getSkillingInteractable()).setF2P(true)
        ),

        FALADOR_RIGHT_YEW(new SkillingLocation("Falador Right Yew",
                Area.fromRectangle(new WorldTile(3040, 3321, 0), new WorldTile(3045, 3317, 0)))
                .addInteractables(Trees.YEW.getSkillingInteractable()).setF2P(true)
        ),

        LUMBRIDGE_GRAVEYARD_YEW(new SkillingLocation("Lumbridge Graveyard Yew",
                Area.fromRectangle(new WorldTile(3246, 3203, 0), new WorldTile(3251, 3197, 0)))
                .addInteractables(Trees.YEW.getSkillingInteractable()).setF2P(true)
        ),


        // Magic Trees
        SORCERERS_TOWER_MAGIC(new SkillingLocation("Sorcerer's Tower",
                Area.fromRectangle(new WorldTile(2698, 3395, 0), new WorldTile(2707, 3400, 0)))
                .addInteractables(Trees.MAGIC.getSkillingInteractable())
        ),

        ;

        @Getter
        SkillingLocation skillingLocation;

        WoodcuttingLocation(SkillingLocation skillingLocation) {
            this.skillingLocation = skillingLocation;
        }

    }


    public static ArrayList<MainTask> getAllWoodcuttingMainTasks() {
        ArrayList<MainTask> list = new ArrayList<>();

        // Raw fish
        list.addAll(Arrays.stream(WoodcuttingData.Trees.values()).map(WoodcuttingData.Trees::getMainTask).collect(Collectors.toList()));

        return list;
    }

}
