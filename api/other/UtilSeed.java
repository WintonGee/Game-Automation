package scripts.main_package.api.other;

import org.tribot.script.sdk.antiban.PlayerPreferences;

public class UtilSeed {

    public static boolean getBool(String key, int chance) {
        return getValue(key, 1, 100) < chance;
    }

    public static int getValue(String key, int min, int max) {
        return PlayerPreferences.preference(key, generator -> generator.uniform(min, max));
    }

    public static double getValue(String key, double min, double max) {
        return PlayerPreferences.preference(key, generator -> generator.uniform(min, max));
    }

}
