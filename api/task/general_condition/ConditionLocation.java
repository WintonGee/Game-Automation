package scripts.main_package.api.task.general_condition;

import lombok.Setter;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.main_package.api.task.TaskCondition;

// In location
public class ConditionLocation extends TaskCondition {

    @Setter
    Area area;

    @Setter
    WorldTile worldTile;
    @Setter
    int withinDistance;

    public ConditionLocation(WorldTile worldTile, int withinDistance) {
        this.setWorldTile(worldTile);
        this.setWithinDistance(withinDistance);
    }

    public ConditionLocation(Area area) {
        this.setArea(area);
    }

    @Override
    public String getConditionName() {
        return "Location";
    }

    @Override
    public boolean check() {
        if (this.area != null)
            return area.containsMyPlayer();

        return worldTile.distanceTo(MyPlayer.getPosition()) <= this.withinDistance;
    }

}
