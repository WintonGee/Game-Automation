package scripts.main_package.a_skill_data.util;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.main_package.a_quest_data.requirement.player.CombatLevelRequirement;
import scripts.main_package.a_quest_data.requirement.player.SkillRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestRequirement;
import scripts.main_package.item.DetailedItem;
import scripts.main_package.item.TeleportItem;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Data
@Accessors(chain = true)
public class SkillingLocation {

    // Nearby = within 100 tiles of distance
    private static int DISTANCE_NEARBY = 100;

    String description;
    Area area;

    boolean isF2P; // Make locations p2p as default

    ArrayList<SkillingInteractable> interactableList = new ArrayList<>();

    // Items needed either equipped or inventory to access location
    ArrayList<DetailedItem> requiredItemList = new ArrayList<>();

    // Teleport items that can be used to traverse to location.
    ArrayList<TeleportItem> teleportItemList = new ArrayList<>();

    ArrayList<SkillRequirement> skillRequirementList = new ArrayList<>();
    ArrayList<QuestRequirement> questRequirementList = new ArrayList<>();
    CombatLevelRequirement combatLevelRequirement;

    public SkillingLocation(String description, Area area) {
        this.setDescription(description);
        this.setArea(area);
    }

    //TODO travel to method

    public boolean isValid() {
        if (!skillRequirementList.stream().allMatch(SkillRequirement::check))
            return false;
        if (!questRequirementList.stream().allMatch(QuestRequirement::check))
            return false;
        return combatLevelRequirement == null || combatLevelRequirement.check();
    }

    public boolean isNearby() {
        return getTravelTile().stream()
                .anyMatch(tile -> MyPlayer.getTile().distanceTo(tile) <= DISTANCE_NEARBY);
    }

    public boolean isHaveTeleportItem() {
        return getBestTeleportItem().isPresent();
    }

    public SkillingLocation addInteractables(SkillingInteractable... interactables) {
        interactableList.addAll(Arrays.asList(interactables));
        return this;
    }

    public SkillingLocation addSkillRequirement(Skill skill, int level) {
        this.skillRequirementList.add(new SkillRequirement(skill, level));
        return this;
    }

    public SkillingLocation addQuestRequirement(Quest quest) {
        this.questRequirementList.add(new QuestRequirement(quest));
        return this;
    }

    public SkillingLocation addRequiredItem(int id, int amount) {
        return addRequiredItem(id, amount, true, false);
    }

    public SkillingLocation addRequiredItem(int id, int amount, boolean equipped, boolean depletes) {
        this.requiredItemList.add(new DetailedItem(id, amount).setEquipped(equipped).setDepletes(depletes));
        return this;
    }

    public SkillingLocation addTeleportItems(TeleportItem... teleportItems) {
        this.teleportItemList.addAll(Arrays.asList(teleportItems));
        return this;
    }

    public SkillingLocation setCombatLevelRequirement(int level) {
        this.combatLevelRequirement = new CombatLevelRequirement(level);
        return this;
    }

    //TODO add some seeding into this
    public Optional<TeleportItem> getBestTeleportItem() {
        return teleportItemList.stream()
                .filter(item -> item.getAsDetailedItem().isHave(true))
                .findFirst();
    }

    public Optional<WorldTile> getTravelTile() {
        return Optional.of(area.getCenter());
    }

}
