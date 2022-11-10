package scripts.main_package.api.tracker.data;

import com.allatori.annotations.DoNotRename;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@DoNotRename
@ToString
@AllArgsConstructor
public class JwtContainer {
    private final String token;
    private final String tokenExpiration;
    private final String refreshToken;
    private final String refreshTokenExpiration;
}
