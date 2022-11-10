package scripts.main_package.data;

import lombok.Data;

@Data
public class LoginDetails {

    private String username;
    private String password;
    private String totp;

    public LoginDetails() {

    }

    public LoginDetails(String username, String password, String totp) {
        this.setUsername(username);
        this.setPassword(password);
        this.setTotp(totp);
    }

}
