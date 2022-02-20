import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserCredentials {

    public String email;
    public String password;
    public static final String EMAIL_POSTFIX = "@yandex.ru";

    @Override
    public String toString() {
        return "UserCredentials{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Step("Getting user variables for authorization")
    public static UserCredentials getVariablesForAuthorization(User user) {
        return new UserCredentials(user.email, user.password);
    }

    @Step("Getting incorrect user variables for authorization")
    public static UserCredentials getIncorrectLoginAndPassword(User user) {
        user.email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        user.password = RandomStringUtils.randomAlphabetic(10);
        return new UserCredentials(user.email, user.password);
    }
}
