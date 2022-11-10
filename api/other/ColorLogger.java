package scripts.main_package.api.other;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Log;

import java.awt.*;

@Data
public class ColorLogger {

    String description;

    @Accessors(chain = true)
    boolean enabled = true; // Used for debug logger

    public ColorLogger(String description) {
        this.setDescription(description);
    }

    public void green(Object o) {
        print(o, Color.GREEN);
    }

    public void red(Object o) {
        print(o, Color.RED);
    }

    public void print(Object o, Color color) {
        if (!enabled)
            return;

        String objectString = o.toString();
        String detailString = "[" + this.description + "] ";
        String finalString = detailString + objectString;
//        General.println(finalString, color);
        Log.trace(finalString);
    }


}
