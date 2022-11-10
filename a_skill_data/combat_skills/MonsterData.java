package scripts.main_package.a_skill_data.combat_skills;

import lombok.Getter;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.main_package.a_skill_data.util.MonsterLocationSelection;
import scripts.main_package.a_skill_data.util.SkillingInteractable;
import scripts.main_package.a_skill_data.util.SkillingLocation;

public class MonsterData {

    public enum Monsters {

        CHICKEN(new Monster("Chicken")
                .setGeneralStats(1, 4, 0, "Stab")
                .setLevels(3, 1, 1, 1, 1, 1)
                .setAggressiveStats(-47, -42, 0, 0, 0, 0)
                .setDefensiveStats(-42, -42, -42, -42, -42)
        ),

        COW(new Monster("Cow").addNames("Cow calf")
                .setGeneralStats(2, 4, 1, "Crush")
                .setLevels(8, 1, 1, 1, 1, 1)
                .setAggressiveStats(-15, -15, +0, +0, +0, +0)
                .setDefensiveStats(-21, -21, -21, -21, -21)
        ),

        GIANT_FROG_13(new Monster("Giant frog")
                .setGeneralStats(13, 4, 2, "Crush")
                .setLevels(23, 10, 8, 6, 1, 1)
                .setAggressiveStats(+0, +0, +0, +0, +0, +0)
                .setDefensiveStats(+0, +0, +0, +0, +0)
        ),

        GIANT_RAT(new Monster("Giant rat")
                .setGeneralStats(3, 4, 1, "Stab")
                .setLevels(5, 2, 3, 2, 1, 1)
                .setAggressiveStats(+0, +0, +0, +0, +0, +0)
                .setDefensiveStats(+0, +0, +0, +0, +0)
        ),

        GOBLIN(new Monster("Goblin")
                .setGeneralStats(2, 4, 1, "Crush")
                .setLevels(5, 1, 1, 1, 1, 1)
                .setAggressiveStats(-21, -15, +0, +0, +0, +0)
                .setDefensiveStats(-15, -15, -15, -15, -15)
        ),

        MAN(new Monster("Man").addNames("Woman")
                .setGeneralStats(2, 4, 1, "Crush")
                .setLevels(7, 1, 1, 1, 1, 1)
                .setAggressiveStats(+0, +0, +0, +0, +0, +0)
                .setDefensiveStats(-21, -21, -21, -21, -21)
        ),

        ;

        @Getter
        Monster monster;

        Monsters(Monster monster) {
            this.monster = monster;
        }

        public SkillingInteractable getSkillingInteractable() {
            return null; //TODO
        }

    }

    public enum MonsterLocationData {

        CHICKEN_FARM_SOUTH_OF_FALADOR("Farm south of Falador", Area.fromRectangle(
                new WorldTile(3014, 3298, 0), new WorldTile(3020, 3282, 0)),
                true,
                Monsters.CHICKEN
        ),

        CHICKEN_LUMBRIDGE_WEST_FARM("Lumbridge West Farm", Area.fromPolygon(
                new WorldTile(3173, 3308, 0),
                new WorldTile(3180, 3308, 0),
                new WorldTile(3180, 3304, 0),
                new WorldTile(3182, 3304, 0),
                new WorldTile(3183, 3303, 0),
                new WorldTile(3184, 3303, 0),
                new WorldTile(3186, 3301, 0),
                new WorldTile(3186, 3299, 0),
                new WorldTile(3187, 3298, 0),
                new WorldTile(3187, 3296, 0),
                new WorldTile(3186, 3295, 0),
                new WorldTile(3186, 3291, 0),
                new WorldTile(3184, 3289, 0),
                new WorldTile(3178, 3289, 0),
                new WorldTile(3177, 3288, 0),
                new WorldTile(3175, 3288, 0),
                new WorldTile(3174, 3289, 0),
                new WorldTile(3171, 3289, 0),
                new WorldTile(3169, 3291, 0),
                new WorldTile(3169, 3295, 0),
                new WorldTile(3170, 3296, 0),
                new WorldTile(3170, 3298, 0),
                new WorldTile(3169, 3299, 0),
                new WorldTile(3169, 3300, 0),
                new WorldTile(3173, 3304, 0)),
                true,
                Monsters.CHICKEN
        ),

        CHICKEN_LUMBRIDGE_EAST_FARM("Lumbridge East Farm", Area.fromPolygon(
                new WorldTile(3195, 3303, 0),
                new WorldTile(3200, 3303, 0),
                new WorldTile(3201, 3302, 0),
                new WorldTile(3205, 3302, 0),
                new WorldTile(3206, 3303, 0),
                new WorldTile(3210, 3303, 0),
                new WorldTile(3211, 3302, 0),
                new WorldTile(3211, 3297, 0),
                new WorldTile(3212, 3296, 0),
                new WorldTile(3212, 3295, 0),
                new WorldTile(3214, 3293, 0),
                new WorldTile(3214, 3290, 0),
                new WorldTile(3213, 3289, 0),
                new WorldTile(3213, 3285, 0),
                new WorldTile(3212, 3284, 0),
                new WorldTile(3207, 3284, 0),
                new WorldTile(3206, 3283, 0),
                new WorldTile(3201, 3283, 0),
                new WorldTile(3200, 3282, 0),
                new WorldTile(3196, 3282, 0),
                new WorldTile(3195, 3283, 0),
                new WorldTile(3195, 3284, 0),
                new WorldTile(3193, 3286, 0),
                new WorldTile(3193, 3301, 0)),
                true,
                Monsters.CHICKEN
        ),

        COW_LUMBRIDGE_EAST_FARM("Lumbridge East Farm", Area.fromPolygon(
                new WorldTile(3241, 3299, 0),
                new WorldTile(3256, 3299, 0),
                new WorldTile(3257, 3300, 0),
                new WorldTile(3261, 3300, 0),
                new WorldTile(3262, 3299, 0),
                new WorldTile(3264, 3299, 0),
                new WorldTile(3266, 3297, 0),
                new WorldTile(3266, 3255, 0),
                new WorldTile(3253, 3255, 0),
                new WorldTile(3253, 3272, 0),
                new WorldTile(3251, 3274, 0),
                new WorldTile(3251, 3276, 0),
                new WorldTile(3249, 3278, 0),
                new WorldTile(3246, 3278, 0),
                new WorldTile(3244, 3280, 0),
                new WorldTile(3244, 3281, 0),
                new WorldTile(3240, 3285, 0),
                new WorldTile(3240, 3287, 0),
                new WorldTile(3241, 3288, 0),
                new WorldTile(3241, 3289, 0),
                new WorldTile(3242, 3290, 0),
                new WorldTile(3242, 3293, 0),
                new WorldTile(3241, 3294, 0),
                new WorldTile(3241, 3295, 0),
                new WorldTile(3240, 3296, 0),
                new WorldTile(3240, 3298, 0)),
                true,
                Monsters.COW
        ),

        COW_SOUTH_OF_CHAMPIONS_GUILD("Field south of the Champions' Guild", Area.fromPolygon(
                new WorldTile(3156, 3349, 0),
                new WorldTile(3162, 3348, 0),
                new WorldTile(3168, 3344, 0),
                new WorldTile(3174, 3344, 0),
                new WorldTile(3177, 3346, 0),
                new WorldTile(3182, 3346, 0),
                new WorldTile(3200, 3337, 0),
                new WorldTile(3214, 3315, 0),
                new WorldTile(3214, 3308, 0),
                new WorldTile(3209, 3308, 0),
                new WorldTile(3208, 3309, 0),
                new WorldTile(3203, 3309, 0),
                new WorldTile(3202, 3308, 0),
                new WorldTile(3200, 3308, 0),
                new WorldTile(3199, 3307, 0),
                new WorldTile(3196, 3307, 0),
                new WorldTile(3195, 3308, 0),
                new WorldTile(3192, 3308, 0),
                new WorldTile(3190, 3310, 0),
                new WorldTile(3189, 3310, 0),
                new WorldTile(3185, 3314, 0),
                new WorldTile(3180, 3314, 0),
                new WorldTile(3178, 3316, 0),
                new WorldTile(3171, 3316, 0),
                new WorldTile(3169, 3318, 0),
                new WorldTile(3165, 3318, 0),
                new WorldTile(3161, 3314, 0),
                new WorldTile(3156, 3314, 0),
                new WorldTile(3154, 3316, 0),
                new WorldTile(3154, 3318, 0),
                new WorldTile(3153, 3319, 0),
                new WorldTile(3153, 3322, 0),
                new WorldTile(3152, 3323, 0),
                new WorldTile(3152, 3326, 0),
                new WorldTile(3153, 3327, 0),
                new WorldTile(3153, 3330, 0),
                new WorldTile(3154, 3331, 0),
                new WorldTile(3154, 3334, 0),
                new WorldTile(3153, 3335, 0),
                new WorldTile(3153, 3338, 0),
                new WorldTile(3152, 3339, 0),
                new WorldTile(3152, 3343, 0),
                new WorldTile(3153, 3344, 0),
                new WorldTile(3153, 3346, 0)),
                true,
                Monsters.COW
        );

        String locationName;
        Area area;
        boolean f2p;
        Monsters monsters;

        // Constructor to default the location as p2p
        MonsterLocationData(String locationName, Area area, Monsters monsters) {
            this(locationName, area, false, monsters);
        }

        MonsterLocationData(String locationName, Area area, boolean f2p, Monsters monsters) {
            this.locationName = locationName;
            this.area = area;
            this.f2p = f2p;
            this.monsters = monsters;
        }

        public MonsterLocation getMonsterLocation() {
            SkillingLocation skillingLocation = new SkillingLocation(this.locationName, area)
                    .setF2P(f2p);
            Monster monster = monsters.getMonster();
            return new MonsterLocation(skillingLocation, monster);
        }

        public MonsterLocationSelection getMonsterLocationSelection() {
            String monsterName = monsters.monster.getName();
            MonsterLocationSelection selection = new MonsterLocationSelection(monsterName, this.locationName);
            selection.setF2P(f2p);
            return selection;
        }

        //TODO maintask

    }


}
