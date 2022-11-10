package scripts.main_package.api.tracker.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActivitiesRequest {
    private final String scriptID;
    private final String period;
}
