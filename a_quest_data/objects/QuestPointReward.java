package scripts.main_package.a_quest_data.objects;

import lombok.Data;

@Data
public class QuestPointReward {

    int amount;

    public QuestPointReward(int amount) {
        this.setAmount(amount);
    }
}
