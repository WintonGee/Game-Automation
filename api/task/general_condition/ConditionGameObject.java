package scripts.main_package.api.task.general_condition;

import lombok.val;
import org.tribot.script.sdk.query.Query;
import scripts.main_package.api.task.TaskCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;

public class ConditionGameObject extends TaskCondition {

    String action;

    ArrayList<Integer> idList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();

    int maxDistance = 30;

    public ConditionGameObject(int... ids) {
        for (int id : ids) {
            idList.add(id);
        }
    }

    public ConditionGameObject(String... names) {
        nameList.addAll(Arrays.asList(names));
    }

    @Override
    public String getConditionName() {
        return "Game Object Condition";
    }

    @Override
    public boolean check() {
        val objQuery = Query.gameObjects();

        objQuery.maxDistance(maxDistance);
        if (action != null) {
            objQuery.actionContains(action);
        }

        return objQuery.filter(obj -> {
            int id = obj.getId();
            String name = obj.getName();
            return idList.contains(id) || nameList.contains(name);
        }).isAny();
    }
}
