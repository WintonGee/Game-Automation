package scripts.main_package.a_quest_data.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Optional;

@Data
@Accessors(chain = true)
public class ItemReward {
    private final String name;
    private final int itemID;
    private final int quantity;

    RewardType rewardType = RewardType.ITEM;
    int minLevel = 0; // Minimum level to use this reward (exp type most likely)
    ArrayList<Skill> limitedSkills = new ArrayList<Skill>(); // Lamp reward can only be used on these skills.
    ArrayList<String> chatOptions = new ArrayList<>();

    public ItemReward(String name, int itemID, int quantity) {
        this.name = name;
        this.itemID = itemID;
        this.quantity = quantity;
    }

    public ItemReward(String name, int itemID) {
        this.name = name;
        this.itemID = itemID;
        this.quantity = 1;
    }

    public Optional<InventoryItem> getInInventory() {
        return Query.inventory().idEquals(itemID).findFirst();
    }

    public ItemReward setClaimable() {
        setRewardType(RewardType.CLAIMABLE);
        return this;
    }

    public ItemReward addChatOption(String... chatOptions) {
        for (String chatOption : chatOptions)
            this.chatOptions.add(chatOption);
        return this;
    }

    public String getName() {
        return name;
    }

    @Nonnull
    public String getDisplayText() {
        return getName();
    }

    public enum RewardType {

        ITEM("Item"),
        CLAIMABLE("Claimable");

        @Setter
        @Getter
        String name;

        RewardType(String name) {
            this.setName(name);
        }

    }

}
