package scripts.main_package.a_skill_data.bank_skills.firemaking;

import org.tribot.script.sdk.Skill;
import scripts.main_package.a_skill_data.util.SkillingItemSelection;
import scripts.raw_data.ItemID;

public class FiremakingData {

    static Skill FIREMAKING = Skill.FIREMAKING;

    public enum Log {
        YEW("Yew", 60, 99, ItemID.YEW_LOGS, 202.5),
        MAPLE("Maple", 45, 60, ItemID.MAPLE_LOGS, 135),
        WILLOW("Willow", 30, 45, ItemID.WILLOW_LOGS, 90),
        OAK("Oak", 15, 30, ItemID.OAK_LOGS, 60),
        NORMAL("Normal", 1, 15, ItemID.LOGS, 40);

        private final String name;
        private final int levelReq;
        private final int stopLevel;
        private final int logId;
        private final double exp;

        Log(String name, int levelReq, int stopLevel, int logId, double exp) {
            this.name = name;
            this.levelReq = levelReq;
            this.stopLevel = stopLevel;
            this.logId = logId;
            this.exp = exp;
        }

        public SkillingItemSelection getSkillingItemObject() {
            SkillingItemSelection skillingItemSelection = new SkillingItemSelection(logId);
            skillingItemSelection.setSkill(FIREMAKING);
            skillingItemSelection.setMaxLevel(stopLevel);
            return skillingItemSelection;
        }

    }

}
