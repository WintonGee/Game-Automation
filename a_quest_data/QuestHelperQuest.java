package scripts.main_package.a_quest_data;

import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.Quest;
import scripts.main_package.a_quest_data.execution.QuestHelperQuestExecution;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.quests.clientofkourend.ClientOfKourend;
import scripts.main_package.a_quest_data.quests.cooksassistant.CooksAssistant;
import scripts.main_package.a_quest_data.quests.doricsquest.DoricsQuest;
import scripts.main_package.a_quest_data.quests.druidic_ritual.DruidicRitual;
import scripts.main_package.a_quest_data.quests.goblindiplomacy.GoblinDiplomacy;
import scripts.main_package.a_quest_data.quests.impcatcher.ImpCatcher;
import scripts.main_package.a_quest_data.quests.romeo_and_juliet.RomeoAndJuliet;
import scripts.main_package.a_quest_data.quests.runemysteries.RuneMysteries;
import scripts.main_package.a_quest_data.quests.sheepshearer.SheepShearer;
import scripts.main_package.a_quest_data.quests.the_restless_ghost.TheRestlessGhost;
import scripts.main_package.a_quest_data.quests.xmarksthespot.XMarksTheSpot;
import scripts.main_package.api.task.MainTask;

public enum QuestHelperQuest {

    CLIENT_OF_KOUREND(Quest.CLIENT_OF_KOUREND, new ClientOfKourend()),
    COOKS_ASSISTANT(Quest.COOKS_ASSISTANT, new CooksAssistant()),
    DORICS_QUEST(Quest.DORICS_QUEST, new DoricsQuest()),
    DRUIDIC_RITUAL(Quest.DRUIDIC_RITUAL, new DruidicRitual()),
    GOBLIN_DIPLOMACY(Quest.GOBLIN_DIPLOMACY, new GoblinDiplomacy()),
    IMP_CATCHER(Quest.IMP_CATCHER, new ImpCatcher()),
    ROMEO_JULIET(Quest.ROMEO_JULIET, new RomeoAndJuliet()),
    RUNE_MYSTERIES(Quest.RUNE_MYSTERIES, new RuneMysteries()),
    SHEEP_SHEARER(Quest.SHEEP_SHEARER, new SheepShearer()),
    THE_RESTLESS_GHOST(Quest.THE_RESTLESS_GHOST, new TheRestlessGhost()),
    X_MARKS_THE_SPOT(Quest.X_MARKS_THE_SPOT, new XMarksTheSpot())
    ;


    @Getter
    @Setter
    Quest quest;

    @Getter
    @Setter
    BasicQuestHelper basicQuestHelper;

    QuestHelperQuest(Quest quest, BasicQuestHelper basicQuestHelper) {
        this.setQuest(quest);
        this.setBasicQuestHelper(basicQuestHelper);
    }

    public QuestInformation getQuestInformation() {
        return new QuestInformation(quest, basicQuestHelper);
    }

    public MainTask getMainTask() {
        MainTask q = new QuestHelperQuestExecution(getQuestInformation());
        q.load();
        return q;
    }

}
