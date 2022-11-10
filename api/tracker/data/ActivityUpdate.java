package scripts.main_package.api.tracker.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ActivityUpdate {
    private final String scriptId;
    private final Map<String, Long> resources;
}
