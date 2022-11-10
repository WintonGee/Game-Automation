package scripts.main_package.data;

import java.util.ArrayList;

public class Vars {

    public static ArrayList<Proxy> proxyList = new ArrayList<Proxy>();

    public static ArrayList<AccountDetails>

            muleAccounts = new ArrayList<AccountDetails>(),
            unusedAccounts = new ArrayList<AccountDetails>(), // Accounts that were never loaded
            runningAccounts = new ArrayList<AccountDetails>(), // Includes currently running and cooldown
            bannedAccounts = new ArrayList<AccountDetails>() // Unusable accounts that were banned

                    ;

}
