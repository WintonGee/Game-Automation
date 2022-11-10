package scripts.main_package.api.other;

public class UtilHits {

    public static int getMaxMeleeHit(int effective, int bonus) {
        return (int) Math.floor(0.5 + effective * (bonus + 64) / 640);
    }

    //TODO
    public static double getHitChance() {
        return 0.0;
    }

//    public static double getAffinity() {
//        return 0.0;
//    }

}
