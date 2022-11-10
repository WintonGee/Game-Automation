package scripts.main_package.a_skill_data.location;

public class LocationData {


    public enum CookingLocation {

        ;

        CookingLocation() {

        }

    }

    public enum FurnaceLocation {

        ;

        FurnaceLocation() {

        }

    }

    public enum SmithLocation {

        ;

        SmithLocation() {

        }

    }

//    public enum CookingLocation {
//
//        FALADOR_RANGE(new Location("Falador", new Area(
//                new int[][]{
//                        {2988, 3363},
//                        {2988, 3368},
//                        {2992, 3368},
//                        {2992, 3366},
//                        {2995, 3366},
//                        {2995, 3363}
//                }
//        )), CookingObject.RANGE),
//        EDGEVILLE_STOVE(new Location("Edgeville Stove", new Area(3077, 3493, 3079, 3496)), CookingObject.STOVE),
//        EDGEVILLE_RANGE(new Location("Edgeville Range", new Area(new Position(3083, 3509, 1), new Position(3080, 3508, 1))), CookingObject.RANGE),
//        CATHERBY_RANGE(new Location("Catherby Range", new Area(2815, 3439, 2818, 3444)), CookingObject.RANGE),
//        LUMBRIDGE_RANGE(new Location("Lumbridge Range", new Area(3205, 3212, 3212, 3217)), CookingObject.RANGE);
//
//        public Location location;
//        public CookingObject cookingObject;
//
//        CookingLocation(final Location location, final CookingObject cookingObject) {
//            this.location = location;
//            this.cookingObject = cookingObject;
//        }
//
//        @Override
//        public String toString() {
//            return location.toString();
//        }
//    }
//
//    public enum FireMakingLocation {
//
//        GRAND_EXCHANGE(new Location("Grand Exchange", new Area(3146, 3473, 3185, 3487))),
//        LUMBRIDGE(new Location("Lumbridge", new Area(
//                new int[][]{
//                        {3217, 3216},
//                        {3217, 3211},
//                        {3215, 3209},
//                        {3207, 3209},
//                        {3207, 3203},
//                        {3221, 3203},
//                        {3221, 3216}
//                }
//        ))),
//        VARROCK_WEST(new Location("Varrock West", new Area(3200, 3428, 3165, 3432))),
//        FALADOR_EAST(new Location("Falador East", new Area(2996, 3359, 3033, 3366))),
//        DRAYNOR(new Location("Draynor", new Area(3097, 3247, 3074, 3250))),
//        AL_KHARID(new Location("Al Kharid", new Area(3304, 3157, 3265, 3154))),
//        EDGEVILLE(new Location("Edgeville", new Area(3073, 3501, 3104, 3506))),
//        SEERS_VILLAGE(new Location("Seers' Village", new Area(2703, 3483, 2737, 3486))),
//        VARROCK_EAST(new Location("Varrock East", new Area(3228, 3428, 3273, 3430))),
//        YANILLE(new Location("Yanille", new Area(2608, 3096, 2575, 3098)));
//
//        public Location LOCATION;
//
//        FireMakingLocation(final Location LOCATION) {
//            this.LOCATION = LOCATION;
//        }
//
//        @Override
//        public String toString() {
//            return LOCATION.toString();
//        }
//    }
//
//    public enum FishingLocation {
//
//        DRAYNOR_VILLAGE(new Location("Draynor Village", new Area(3085, 3226, 3088, 3230))),
//        LUMBRIDGE_SWAMP(new Location("Lumbridge Swamp", new Area(3238, 3144, 3247, 3160))),
//        MUSA_POINT(new Location("Musa Point", new Area(2923, 3176, 2926, 3183))),
//        PORT_SARIM(new Location("Port Sarim", new Area(2985, 3174, 2988, 3178))),
//        AL_KHARID(new Location("Al Kharid", new Area(
//                new int[][]{
//                        {3265, 3151},
//                        {3269, 3151},
//                        {3279, 3140},
//                        {3276, 3137},
//                        {3263, 3148}
//                }
//        ))),
//        CATHERBY(new Location("Catherby", new Area(
//                new int[][]{
//                        {2835, 3434},
//                        {2845, 3433},
//                        {2848, 3431},
//                        {2852, 3426},
//                        {2856, 3426},
//                        {2858, 3429},
//                        {2863, 3429},
//                        {2863, 3425},
//                        {2858, 3424},
//                        {2850, 3423},
//                        {2846, 3428},
//                        {2833, 3431}
//                }
//        ))),
//        BARBARIAN_VILLAGE(new Location("Barbarian Village", new Area(
//                new int[][]{
//                        {3102, 3422},
//                        {3102, 3428},
//                        {3106, 3432},
//                        {3106, 3436},
//                        {3111, 3436},
//                        {3111, 3430},
//                        {3106, 3425},
//                        {3106, 3422}
//                }
//        ))),
//        LUMBRIDGE(new Location("Lumbridge", new Area(3238, 3239, 3241, 3255))),
//        SHILO_VILLAGE(new Location("Shilo Village", new Area(2857, 2970, 2861, 2973))),
//        FISHING_GUILD_NORTH(new Location("Fishing Guild North", new Area(2599, 3419, 2606, 3426))),
//        FISHING_GUILD_SOUTH(new Location("Fishing Guild South", new Area(2603, 3410, 2614, 3417))),
//        PISCATORIS_FISHING_COLONY(new Location("Piscatoris Fishing Colony", new Area(2307, 3697, 2312, 3703)));
//
//        static FishingLocation[] smallNetBaitLocations = {
//                DRAYNOR_VILLAGE,
//                LUMBRIDGE_SWAMP,
//                MUSA_POINT,
//                PORT_SARIM,
//                AL_KHARID,
//                CATHERBY
//        };
//
//        static FishingLocation[] lureBaitLocations = {
//                BARBARIAN_VILLAGE,
//                LUMBRIDGE,
//                SHILO_VILLAGE
//        };
//
//        static FishingLocation[] cageHarpoonLocations = {
//                MUSA_POINT,
//                CATHERBY,
//                FISHING_GUILD_NORTH,
//                FISHING_GUILD_SOUTH
//        };
//
//        static FishingLocation[] bigNetHarpoonLocations = {
//                CATHERBY,
//                FISHING_GUILD_NORTH,
//                FISHING_GUILD_SOUTH
//        };
//
//        static FishingLocation[] tunaSwordfishLocations = {
//                MUSA_POINT,
//                CATHERBY,
//                FISHING_GUILD_SOUTH,
//                FISHING_GUILD_NORTH,
//                PISCATORIS_FISHING_COLONY
//        };
//
//        public Location location;
//
//        FishingLocation(final Location location) {
//            this.location = location;
//        }
//
//        @Override
//        public String toString() {
//            return location.toString();
//        }
//    }
//
//    public enum SmeltLocation {
//
//        AL_KHARID(new Location("Al-Kharid", new Area(3272, 3184, 3279, 3188))),
//        EDGEVILLE(new Location("Edgeville", new Area(3105, 3496, 3110, 3501))),
//        FALADOR(new Location("Falador", new Area(
//                new int[][]{
//                        {2970, 3368},
//                        {2970, 3377},
//                        {2974, 3377},
//                        {2974, 3375},
//                        {2978, 3375},
//                        {2978, 3373},
//                        {2976, 3373},
//                        {2976, 3368}
//                }
//        )));
//
//        public Location location;
//
//        SmeltLocation(final Location location) {
//            this.location = location;
//        }
//
//        @Override
//        public String toString() {
//            return location.toString();
//        }
//    }
//
//    public enum SmithLocation {
//
//        VARROCK(new Location("Varrock", new Area(3185, 3420, 3190, 3427))),
//        SEERS_VILLAGE(new Location("Seers' Village", new Area(
//                new int[][]{
//                        {2706, 3487},
//                        {2706, 3492},
//                        {2704, 3492},
//                        {2704, 3499},
//                        {2706, 3499},
//                        {2706, 3497},
//                        {2709, 3497},
//                        {2709, 3496},
//                        {2715, 3496},
//                        {2715, 3492},
//                        {2712, 3492},
//                        {2712, 3487}
//                }
//        )));
//
//        Location location;
//
//        SmithLocation(final Location location) {
//            this.location = location;
//        }
//
//        @Override
//        public String toString() {
//            return location.toString();
//        }
//    }

}
