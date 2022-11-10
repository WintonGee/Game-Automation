package scripts.main_package.api.task.general_action;

import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.main_package.api.task.TaskAction;
import scripts.main_package.api.task.TaskWaiting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ActionGameObject extends TaskAction {

    String action;

    ArrayList<Integer> idList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();

    int maxDistance = 30;

    public ActionGameObject(int... ids) {
        for (int id : ids) {
            idList.add(id);
        }
    }

    public ActionGameObject(String... names) {
        nameList.addAll(Arrays.asList(names));
    }

    @Override
    public String getActionName() {
        return "Game Object Action";
    }

    @Override
    public boolean handleAction() {
        return getGameObject()
                .map(g -> g.interact(getActionToUse(g)))
                .orElse(false);
    }

    public Optional<GameObject> getGameObject() {
        val objQuery = Query.gameObjects();

        objQuery.maxDistance(maxDistance);
        if (action != null) {
            objQuery.actionContains(action);
        }

        return objQuery.filter(obj -> {
            int id = obj.getId();
            String name = obj.getName();
            return idList.contains(id) || nameList.contains(name);
        }).findBestInteractable();
    }

    private String getActionToUse(GameObject gameObject) {
        if (gameObject == null || action != null)
            return action;
        val list = gameObject.getActions();
        return list.size() == 0 ? "" : list.get(0);
    }

}
