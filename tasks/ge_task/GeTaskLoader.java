package scripts.main_package.tasks.ge_task;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.types.GrandExchangeOffer.Type;
import scripts.main_package.item.DetailedItem;

import java.util.ArrayList;

@Accessors(chain = true)
@Data
public class GeTaskLoader {

    public ArrayList<GeTaskItem> getList(ArrayList<DetailedItem> list, Type type, int multiplier) {
        ArrayList<GeTaskItem> newList = new ArrayList<GeTaskItem>();
        for (DetailedItem item : list) {
            if (type == Type.BUY && item.isHave(true))
                continue;
            boolean isTradable = item.isTradeable();
            if (isTradable) {
                newList.add(new GeTaskItem(item, type, multiplier));
            }
        }
        return newList;
    }

}
