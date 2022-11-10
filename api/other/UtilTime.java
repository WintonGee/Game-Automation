package scripts.main_package.api.other;

import java.util.concurrent.TimeUnit;

public class UtilTime {

    public static long getTime() {
        return System.currentTimeMillis();
    }

    public static long getMillisHour(int i) {
        return getMillisMinute(i) * 60;
    }

    public static long getMillisMinute(int i) {
        return getMillisSecond(i) * 60;
    }

    public static long getMillisSecond(int i) {
        return i * 1000;
    }

    public static long getMillisTick(int i) {
        return i * 600;
    }

    public static String getTimeString(long millis) {
        long HH = TimeUnit.MILLISECONDS.toHours(millis);
        long MM = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long SS = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }

}
