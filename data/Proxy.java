package scripts.main_package.data;

import lombok.Data;

@Data
public class Proxy {

    String ip;
    String port;

    int currentUses = 0;
    // Increment 1 each time an account uses this proxy
    // When setting accounts to banned status, remove account
    // Decrease by 1 every time account is sent to cooldown

    public Proxy(String ip, String port) {
        this.setIp(ip);
        this.setPort(port);
    }

}
