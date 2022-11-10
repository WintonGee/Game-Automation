package scripts.main_package.a_skill_data;

import lombok.NonNull;
import scripts.main_package.api.task.MainTask;

// Used for tasks that create items
public class ItemCreationMainTask extends MainTask {

    //TODO widget details

    // object location?
    // need to use item on object?
    // object to click

    String activityName;

    //TODO some type of stop condtions?

    // no wait condition for making darts
    // note that conditon needed for fletching where it makes 100 items then stops

    public ItemCreationMainTask(String activityName) {
        super(activityName);
    }

    @Override
    public void load() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }
}
