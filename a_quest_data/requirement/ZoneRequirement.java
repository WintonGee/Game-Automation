package scripts.main_package.a_quest_data.requirement;

import org.tribot.script.sdk.Log;
import scripts.main_package.a_quest_data.objects.WorldPoint;
import scripts.main_package.a_quest_data.objects.Zone;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZoneRequirement extends Requirement {

    final ArrayList<Zone> zones;
    final boolean checkInZone;
    String displayText;

    public ZoneRequirement(String displayText, Zone zone) {
        this(displayText, false, zone);
    }

    public ZoneRequirement(String displayText, boolean checkNotInZone, Zone zone) {
        this.displayText = displayText;
        this.checkInZone = !checkNotInZone; // This was originally 'checkNotInZone' so we have to maintain that behavior
        this.zones = new ArrayList<>(Collections.singletonList(zone));
    }

    public ZoneRequirement(Zone... zone) {
        this(true, zone);
    }

    public ZoneRequirement(boolean checkInZone, Zone... zone) {
        this.checkInZone = checkInZone;
        this.zones = Arrays.stream(zone).collect(Collectors.toCollection(ArrayList::new));
    }

    public ZoneRequirement(WorldPoint... worldPoints) {
        this(true, worldPoints);
    }

    public ZoneRequirement(boolean checkInZone, WorldPoint... worldPoints) {
        this.zones = Stream.of(worldPoints).map(Zone::new).collect(Collectors.toCollection(ArrayList::new));
        this.checkInZone = checkInZone;
    }

    @Nonnull
    @Override
    public String getDisplayText() {
        return displayText == null ? "" : displayText;
    }


    @Override
    public boolean check() {
        if (zones == null) {
            Log.error("[Zone Requirement] No zones loaded");
            return false;
        }

        boolean inZone = zones.stream().anyMatch(z -> z.getArea().containsMyPlayer());
        return inZone == checkInZone;
    }


}
