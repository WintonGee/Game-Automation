package scripts.main_package.api.paint;

import lombok.Setter;
import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;

import java.awt.*;

public class PaintData {

    @Setter
    public static String activity = "Loading...";

    public static void loadPaint() {

        PaintTextRow template = PaintTextRow.builder().textColor(Color.black).background(Color.white).build();
        BasicPaintTemplate paint = BasicPaintTemplate.builder().row(PaintRows.scriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Activity").value(() -> activity).build())
                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT).build();
        Painting.addPaint(paint::render);
    }

}
