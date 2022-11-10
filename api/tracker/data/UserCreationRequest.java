package scripts.main_package.api.tracker.data;

import com.allatori.annotations.DoNotRename;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@DoNotRename
public class UserCreationRequest {
    private final String name;
}
