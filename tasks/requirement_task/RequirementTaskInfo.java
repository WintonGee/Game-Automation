package scripts.main_package.tasks.requirement_task;

import lombok.val;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import scripts.main_package.a_quest_data.QuestHelperQuest;
import scripts.main_package.a_quest_data.framework.BasicQuestHelper;
import scripts.main_package.a_quest_data.requirement.Requirement;
import scripts.main_package.a_quest_data.requirement.player.SkillRequirement;
import scripts.main_package.a_quest_data.requirement.quest.QuestRequirement;
import scripts.main_package.api.other.UtilStat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

// Used for holding tasks needed to do
// and future skill levels.
public class RequirementTaskInfo {


    LinkedHashMap<Skill, Integer> requiredSkillsInfo;
    LinkedHashMap<Quest, Quest.State> requiredQuestsInfo;

    public void loadInfo(ArrayList<Requirement> requirementsList) {
        requiredSkillsInfo = new LinkedHashMap<>();
        requiredQuestsInfo = new LinkedHashMap<>();

        loadRequirements(requirementsList);
    }


    // Updates a list of requirements needed
    public void loadRequirements(ArrayList<Requirement> requirementsList) {
        if (requirementsList == null || requirementsList.size() == 0) // Ends recursion
            return;
        for (Requirement requirement : requirementsList) {
            if (requirement instanceof SkillRequirement) {
                updateSkillRequirement((SkillRequirement) requirement);
                continue;
            }
            if (requirement instanceof QuestRequirement) {
                updateQuestRequirement((QuestRequirement) requirement);
                continue;
            }
            Log.warn("[RequirementTaskInfo] Error, Skipped Requirement: " + requirement);
        }
    }

    // Updates the skill requirement needed
    private void updateSkillRequirement(SkillRequirement skillRequirement) {
        Log.debug("[RequirementTaskInfo] Adding " + skillRequirement);
        Skill skill = skillRequirement.getSkill();
        int level = skillRequirement.getLevel();
        int experienceForLevel = UtilStat.getExperienceForLevel(level);

        // Updating the requirement level part
        if (requiredSkillsInfo.containsKey(skill)) {
            val currentAmount = requiredSkillsInfo.get(skill);
            if (currentAmount < experienceForLevel) {
//                Log.debug("[RequirementTaskInfo] Replacing " + skill + " exp from " + currentAmount + " to " + experienceForLevel + ", level: " + level);
                requiredSkillsInfo.put(skill, experienceForLevel);
            }
            return;
        }

//        Log.debug("[RequirementTaskInfo] Setting " + skill + " exp to " + experienceForLevel + ", level: " + level);
        requiredSkillsInfo.put(skill, experienceForLevel);
    }

    // Updates the quest requirement needed
    public void updateQuestRequirement(QuestRequirement questRequirement) {
        Log.debug("[RequirementTaskInfo] Adding " + questRequirement);
        Quest quest = questRequirement.getQuest();
        requiredQuestsInfo.put(quest, Quest.State.COMPLETE);
        val questReqs = getRequirementsForQuest(quest);
        if (questReqs != null && questReqs.size() > 0) {
            Log.debug("[RequirementTaskInfo] Adding " + questReqs.size() + " Requirement(s) for quest: " + quest);
            loadRequirements(questReqs);
        }

    }

    // Returns: Requirements to do a quest
    private ArrayList<Requirement> getRequirementsForQuest(Quest quest) {
        val questHelperQuest = Arrays.stream(QuestHelperQuest.values())
                .filter(q -> q.getQuest() == quest)
                .findFirst();

        if (questHelperQuest.isEmpty()) {
            Log.error("[RequirementTaskInfo] Could not get data on quest: " + quest);
            return null;
        }

        val basicHelper = questHelperQuest.map(QuestHelperQuest::getBasicQuestHelper);
        val list = basicHelper.map(BasicQuestHelper::getGeneralRequirements).orElse(null);
        return list == null ? null : new ArrayList<>(list);
    }


}
