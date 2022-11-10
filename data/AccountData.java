package scripts.main_package.data;

import lombok.Getter;
import lombok.Setter;

public class AccountData {

    // Used to determine which type of tasks to be ran
    // Farming type tasks
    // Muling type tasks
    public enum AccountType {
        MAIN_MULE("Main mule"), // Should only have 1
        MULE("Mule"), // Should only have 1 per ~20 characters
        NORMAL("Normal");

        @Setter
        @Getter
        String type;

        AccountType(String type) {
            this.setType(type);
        }
    }

    public enum AccountStatus {

        AVAILABLE("Available"),
        COOLDOWN("Cooldown"),
        BANNED("Banned");

        @Setter
        @Getter
        String status;

        AccountStatus(String status) {
            this.setStatus(status);
        }

    }

}
