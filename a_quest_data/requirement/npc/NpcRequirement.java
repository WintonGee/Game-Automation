package scripts.main_package.a_quest_data.requirement.npc;

public class NpcRequirement {
//    private final int npcID;
//    private final Zone zone;
//    private final String displayText;
//    private final boolean checkNotInZone;
//
//    /**
//     * Check for the existence of an NPC within your canvas.
//     *
//     * @param displayText the display text
//     * @param npcID       the NPC to check for
//     */
//    public NpcRequirement(String displayText, int npcID) {
//        this(displayText, npcID, false, null);
//    }
//
//    /**
//     * Check if a given NPC is in a specified {@link Zone}.
//     *
//     * @param displayText the display text
//     * @param npcID       the {@link NPC} to check for
//     * @param worldPoint  the location to check for the NPC
//     */
//    public NpcRequirement(String displayText, int npcID, WorldPoint worldPoint) {
//        this(displayText, npcID, false, new Zone(worldPoint));
//    }
//
//    /**
//     * Check if a given NPC is in a specified {@link Zone}.
//     *
//     * @param displayText the display text
//     * @param npcID       the {@link NPC} to check for
//     * @param zone        the zone to check.
//     */
//    public NpcRequirement(String displayText, int npcID, Zone zone) {
//        this(displayText, npcID, false, zone);
//    }
//
//    /**
//     * Check if a given NPC is in a specified {@link Zone}.
//     * <br>
//     * If {@param checkNotInZone} is true, this will check if the NPC is NOT in the zone.
//     *
//     * @param displayText    the display text
//     * @param npcID          the {@link NPC} to check for
//     * @param checkNotInZone determines whether to check if the NPC is in the zone or not
//     * @param zone           the zone to check.
//     */
//    public NpcRequirement(String displayText, int npcID, boolean checkNotInZone, Zone zone) {
//        this.displayText = displayText;
//        this.npcID = npcID;
//        this.zone = zone;
//        this.checkNotInZone = checkNotInZone;
//    }
//
//    @Override
//    public boolean check(Client client) {
//        List<NPC> found = client.getNpcs().stream()
//                .filter(npc -> npc.getId() == npcID)
//                .collect(Collectors.toList());
//
//        if (!found.isEmpty()) {
//            if (zone != null) {
//                for (NPC npc : found) {
//                    WorldPoint npcLocation = WorldPoint.fromLocalInstance(client, npc.getLocalLocation(), 2);
//                    if (npcLocation != null) {
//                        boolean inZone = zone.contains(npcLocation);
//                        return inZone && !checkNotInZone || (!inZone && checkNotInZone);
//                    }
//                }
//            }
//            return true; // the NPC exists, and we aren't checking for its location
//        }
//        return false; // npc not in scene
//    }
//
//    @Override
//    public String getDisplayText() {
//        return displayText;
//    }
}
