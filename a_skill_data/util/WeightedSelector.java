package scripts.main_package.a_skill_data.util;

import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.util.RandomSelectors;
import scripts.main_package.a_quest_data.QuestSelectionObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.ToDoubleFunction;

public class WeightedSelector {

    // By defaulting to 0, skill will not be selectable unless all other is 0.
    private final static ToDoubleFunction<SkillSelection> skillDoubleFunction = SkillSelection::getSkillWeight;

    private final static ToDoubleFunction<QuestSelectionObject> questDoubleFunction = QuestSelectionObject::getQuestWeight;

    /**
     * @param seeded - If should use seeded weighting
     * @return Skill selection object
     */
    public static Optional<SkillSelection> selectRandomSkill(boolean seeded) {
        return selectSkill(getAllSkillSelectionObjectList(seeded));
    }

    /**
     * @param list - Contains skill selection objects with loaded weights.
     * @return Skill Selection object
     */
    public static Optional<SkillSelection> selectSkill(ArrayList<SkillSelection> list) {
        return RandomSelectors.weighted(skillDoubleFunction).select(list);
    }

    /**
     * @param list - Contains quest selection objects with loaded weights.
     * @return Quest Selection object
     */
    public static Optional<QuestSelectionObject> selectQuest(ArrayList<QuestSelectionObject> list) {
        return RandomSelectors.weighted(questDoubleFunction).select(list);
    }

    /**
     * @param seeded - If should use seeded weighting
     * @return List of Skill Selection Objects
     */
    private static ArrayList<SkillSelection> getAllSkillSelectionObjectList(boolean seeded) {
        ArrayList<SkillSelection> list = new ArrayList<>();
        Arrays.stream(Skill.values())
                .forEach(skill -> {
                    // Shouldnt all be false, but doesnt matter for what this func. is used for.
                    SkillSelection obj = new SkillSelection(skill, false);
                    if (seeded) obj.loadSeededWeight();
                });
        return list;
    }

}
