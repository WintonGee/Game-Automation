package scripts.main_package.data;

import lombok.Data;
import lombok.experimental.Accessors;
import org.tribot.script.sdk.Log;
import scripts.main_package.api.other.UtilTime;
import scripts.main_package.data.AccountData.AccountStatus;
import scripts.main_package.data.AccountData.AccountType;

@Data
@Accessors(chain = true)
public class AccountDetails {

    private LoginDetails loginDetails;
    private AccountType type; // Set when account is added

    private long nextSelectableTime = 0L; // For determining if account can be selected by server
    private int banCount = 0; // When ban is detected, increment

    Proxy proxy; //TODO should be loaded if null during account selection process.

    public ConnectionDetails connection = new ConnectionDetails(); // Used for communicating with character

    public AccountStatus accountStatus = AccountStatus.AVAILABLE;
    public InGameDetails inGameDetails = new InGameDetails(); // Non null

    public AccountDetails(LoginDetails loginDetails) {
        this.setLoginDetails(loginDetails);
    }

    public AccountDetails(String username, String password, String totp) {
        this.setLoginDetails(new LoginDetails(username, password, totp));
    }

    // For server to determine if account can be used
    // when account is requested.
    public boolean isAccountAvailable() {
        return UtilTime.getTime() >= this.nextSelectableTime;
    }

    // Used to update settings after this account
    // has been selected by the server to use for launch.
    public void updateAccountSelected() {
        // 30 Minutes before account can potentially be selected again.
        this.setNextSelectableTime(UtilTime.getTime() + UtilTime.getMillisMinute(30));
    }

    public boolean isAccountMatch(AccountDetails checkingAccount) {
        return checkingAccount.loginDetails.getUsername().equals(this.loginDetails.getUsername());
    }

    // Settings if account is on CoolDown - TriBot Client
    public void updateCooldown() {

    }

    // Adjust the settings if account is banned - TriBot Client
    public void updateBanned() {
        Log.info("Adding Ban Adjustments");
        banCount++;

        // 2 day ban
        long newTime = UtilTime.getTime() + UtilTime.getMillisHour(48);
        this.setNextSelectableTime(newTime);
    }

    // For GUI related stuff
    public String getName() {
        return inGameDetails == null ? null : inGameDetails.getCharacterName();
    }

    public String getLoginUsername() {
        return loginDetails == null ? null : loginDetails.getUsername();
    }

    public String getPassword() {
        return loginDetails == null ? null : loginDetails.getPassword();
    }

    public String getTotp() {
        return loginDetails == null ? null : loginDetails.getTotp();
    }

    public String getConnectionIp() {
        return connection.isUpdated() ? this.connection.getIp() : null;
    }

    public int getConnectionPort() {
        return connection.isUpdated() ? this.connection.getPort() : SocketData.INVALID_REQUEST_PORT;
    }

    public int getCooldownTime() {
        long timeLeft = nextSelectableTime - UtilTime.getTime();
        timeLeft = timeLeft <= 0 ? 0 : timeLeft; // If less than 0, set to 0
        return (int) (timeLeft / 1000);
    }

}
