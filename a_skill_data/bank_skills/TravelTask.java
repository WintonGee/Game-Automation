package scripts.main_package.a_skill_data.bank_skills;

import org.tribot.script.sdk.Log;
import scripts.main_package.a_skill_data.util.SkillingLocation;
import scripts.main_package.api.task.MainTaskData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Task used for traveling to the skilling locations
public class TravelTask {

    ArrayList<SkillingLocation> skillingLocationList = new ArrayList<>();

    public TravelTask(ArrayList<SkillingLocation> locations) {
        this.skillingLocationList.addAll(locations);
    }

    private Optional<SkillingLocation> getBestLocation() {
        List<SkillingLocation> validList = getValidLocations();
        if (validList.size() == 0) {
            Log.error("[TravelTask] No valid locations");
            return Optional.empty();
        }

        // Apply f2p filter if needed
        if (MainTaskData.isFreeToPlayMode)
            validList = validList.stream()
                    .filter(SkillingLocation::isF2P)
                    .collect(Collectors.toList());

        Optional<SkillingLocation> nearbyLocation = getNearbyLocations(validList);
        if (nearbyLocation.isPresent())
            return nearbyLocation;

        Optional<SkillingLocation> locationWithValidTeleport = getLocationWithValidTeleport(validList);
        if (MainTaskData.isFreeToPlayMode && locationWithValidTeleport.isPresent())
            return locationWithValidTeleport;

        return getClosest(validList);
    }

    private List<SkillingLocation> getValidLocations() {
        return skillingLocationList.stream()
                .filter(SkillingLocation::isValid)
                .collect(Collectors.toList());
    }

    // Nearby locations, No teleportation needed
    private Optional<SkillingLocation> getNearbyLocations(List<SkillingLocation> validList) {
        return validList.stream()
                .filter(SkillingLocation::isNearby)
                .findFirst();
    }

    // Locations the player has a teleport to
    private Optional<SkillingLocation> getLocationWithValidTeleport(List<SkillingLocation> validList) {
        return validList.stream()
                .filter(SkillingLocation::isHaveTeleportItem)
                .findFirst();
    }

    private Optional<SkillingLocation> getClosest(List<SkillingLocation> validList) {
        return Optional.empty();
    }

}
