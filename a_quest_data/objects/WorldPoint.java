package scripts.main_package.a_quest_data.objects;

import lombok.Data;
import org.tribot.script.sdk.types.WorldTile;

@Data
public class WorldPoint {

    int x, y, z;

    public WorldPoint(int x, int y, int z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public WorldTile getWorldTile() {
        return new WorldTile(x, y, z);
    }

    public int getPlane() {
        return z;
    }
}
