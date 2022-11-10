package scripts.main_package.data;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileData {

    private final static String fileSeparator = FileSystems.getDefault().getSeparator();
    private final static String DEFAULT_PATH = "C:" + fileSeparator + "Scripting" + fileSeparator + "Account Data"
            + fileSeparator;

    public enum FileType {

        UNUSED("unused.txt"), COOLDOWN("cooldown.txt"), BANNED("banned.txt"), CONSOLE("console");

        @Setter
        @Getter
        String filePath;

        FileType(String filePath) {
            this.setFilePath(DEFAULT_PATH + filePath);
        }

        public String getConsolePath() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date now = new Date();
            String strDate = sdf.format(now);
            String filePath = DEFAULT_PATH + "console" + fileSeparator + strDate + ".txt";
            return filePath;
        }

    }
}
