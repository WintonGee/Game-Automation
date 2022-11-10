package scripts.main_package.api.tracker.data;

import com.allatori.annotations.DoNotRename;
import lombok.AllArgsConstructor;
import lombok.Getter;

@DoNotRename
@Getter
@AllArgsConstructor
public class LoginRequest {
    private final String userId;
    private final String secretKey;
    private final String refreshToken;
}
