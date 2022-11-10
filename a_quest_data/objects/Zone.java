package scripts.main_package.a_quest_data.objects;

import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

public class Zone
{
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private int minPlane = 0;
    private int maxPlane = 2;

    //The first plane of the "Overworld"
    public Zone()
    {
        minX = 1152;
        maxX = 3903;
        minY = 2496;
        maxY = 4159;
        maxPlane = 0;
    }

    public Zone(WorldPoint p1, WorldPoint p2)
    {
        minX = Math.min(p1.getX(), p2.getX());
        maxX = Math.max(p1.getX(), p2.getX());
        minY = Math.min(p1.getY(), p2.getY());
        maxY = Math.max(p1.getY(), p2.getY());
        minPlane = Math.min(p1.getPlane(), p2.getPlane());
        maxPlane = Math.max(p1.getPlane(), p2.getPlane());
    }

    public Zone(WorldPoint p)
    {
        minX = p.getX();
        maxX = p.getX();
        minY = p.getY();
        maxY = p.getY();
        minPlane = p.getPlane();
        maxPlane = p.getPlane();
    }

//    public Zone(int regionID)
//    {
//        int regionX = (regionID >> 8) & 0xff;
//        int regionY = regionID & 0xff;
//        minX = regionX >> 6;
//        maxX = minX + REGION_SIZE;
//        minY = regionY >> 6;
//        maxY = minY + REGION_SIZE;
//    }

//    public Zone(int regionID, int plane)
//    {
//        this(regionID);
//        minPlane = plane;
//        maxPlane = plane;
//    }

    public boolean contains(WorldPoint worldPoint)
    {
        return minX <= worldPoint.getX()
                && worldPoint.getX() <= maxX
                && minY <= worldPoint.getY()
                && worldPoint.getY() <= maxY
                && minPlane <= worldPoint.getPlane()
                && worldPoint.getPlane() <= maxPlane;
    }

    public Area getArea() {
        return Area.fromRectangle(new WorldTile(minX, minY, minPlane), new WorldTile(maxX, maxY, maxPlane));
    }

}