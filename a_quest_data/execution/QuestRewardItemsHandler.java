package scripts.main_package.a_quest_data.execution;

import lombok.Data;
import lombok.val;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Widget;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.objects.ItemReward;
import scripts.main_package.a_quest_data.step.QuestStep;
import scripts.main_package.a_quest_data.step_handler.ChatHandler;
import scripts.main_package.api.action.UtilWait;
import scripts.main_package.api.other.UtilStat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Handles the reward items at the end of the quest.
@Data
public class QuestRewardItemsHandler {

    private final List<String> skillNames = Arrays.stream(Skill.values())
            .map(skill -> skill.name())
            .collect(Collectors.toList());

    BasicQuestHelper basicQuestHelper;

    List<ItemReward> itemRewards;

    public QuestRewardItemsHandler(BasicQuestHelper basicQuestHelper) {
        this.basicQuestHelper = basicQuestHelper;

        itemRewards = basicQuestHelper.getItemRewards();
    }

    public boolean isComplete() {
        if (itemRewards == null) {
            Log.info("[QuestRewardItemsHandler] No reward items to handle!");
            return true;
        }
        return getClaimableRewards().size() == 0;
    }

    public boolean handle() {
        for (int i = 0; i < 30; i++) {
            if (isComplete()) {
                Log.info("[QuestRewardItemsHandler] Rewards handler complete!");
                return true;
            }
            List<ItemReward> claimableRewards = getClaimableRewards();
            Log.info("[QuestRewardItemsHandler] Number of rewards to handle: " + claimableRewards.size());

            claimableRewards.forEach(itemReward -> handleReward(itemReward));
            Waiting.waitUniform(100, 200);
        }

        Log.error("[QuestRewardItemsHandler] Failed to handle reward items!");
        return false;
    }

    private boolean handleReward(ItemReward reward) {
        String itemName = reward.getName();
        Log.info("[QuestRewardItemsHandler] Handling item: " + itemName);

        // Click
        Optional<InventoryItem> inInventory = reward.getInInventory();
        if (inInventory.isPresent()) {
            Log.info("[QuestRewardItemsHandler] Clicking item: " + itemName);
            int currentXp = UtilStat.getTotalXp();
            InventoryItem inventoryItem = inInventory.get();
            if (inventoryItem.click())
                UtilWait.until(2000, () -> isChatOpen() || isSelectSkillOpen() || UtilStat.getTotalXp() != currentXp);
        }

        // Handle reward selection
        if (isSelectSkillOpen()) {
            Log.info("[QuestRewardItemsHandler] Attempting to select a skill");
            return handleSkillSelection();
        }

        // Handle chat screen
        if (isChatOpen()) {
            Log.info("[QuestRewardItemsHandler] Attempting to handle chat");
            return handleChatSelection(reward);
        }

        return false;
    }

    private boolean handleSkillSelection() {
        Widget confirmWidget = getConfirmSkillWidget();
        if (!confirmWidget.isVisible()) {
            Log.error("[QuestRewardItemsHandler] Confirm widget was not visible!");
            return false;
        }

        int masterIndex = confirmWidget.getIndexPath()[0];
        List<Widget> skillWidgets = getSkillWidgets(masterIndex);
        Collections.shuffle(skillWidgets);
        int currentXp = UtilStat.getTotalXp();
        for (Widget widget : skillWidgets) {
            if (widget.isVisible() && widget.click() && isValidSkillSelected() && confirmWidget.click()) {
                return UtilWait.until(2000, () -> UtilStat.getTotalXp() != currentXp);
            }
        }
        return false;
    }

    private boolean handleChatSelection(ItemReward reward) {
        val options = reward.getChatOptions();
        QuestStep questStep = new QuestStep(null) {
        };
        questStep.addDialogSteps(reward.getChatOptions());
        return new ChatHandler(questStep).handle();
    }

    private boolean isChatOpen() {
        return ChatScreen.isOpen();
    }

    private boolean isSelectSkillOpen() {
        if (getConfirmSkillWidget() == null)
            return false;
        return Query.widgets()
                .textContains("Choose the stat you wish to be advanced!")
                .isAny();
    }

    private boolean isValidSkillSelected() {
        return Query.widgets()
                .textContains("Confirm:")
                .isAny();
    }

    private Widget getConfirmSkillWidget() {
        return Query.widgets()
                .textContains("Confirm")
                .findFirst().orElse(null);
    }

    private List<Widget> getSkillWidgets(int rootIndex) {
        return Query.widgets().inRoots(rootIndex)
                .actionContains(skillNames.toArray(new String[0]))
                .toList();
    }

    private List<ItemReward> getClaimableRewards() {
        return itemRewards.stream()
                .filter(itemReward -> itemReward.getRewardType() != ItemReward.RewardType.ITEM)
                .filter(itemReward -> itemReward.getInInventory().isPresent())
                .collect(Collectors.toList());
    }

}
