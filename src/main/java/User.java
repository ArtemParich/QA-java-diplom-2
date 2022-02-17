import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class User {
    public String email;
    public String password;
    public String name;
    public static final String EMAIL_POSTFIX = "@yandex.ru";

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Create User")
    public static User getAllVariables() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User (email, password, name);
    }

    @Step("Create User without Email")
    public static User getPasswordAndName() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User (null, password, name);
    }

    @Step("Create User without Password")
    public static User getEmailAndName() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User (email, null, name);
    }

    @Step("Create User without Name")
    public static User getEmailAndPassword() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new User (email, password, null);
    }

    @Step("Getting new Email")
    public static String getNewEmail() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        return email;
    }

    @Step("Getting new Password")
    public static String getNewPassword() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        return password;
    }

    @Step("Getting new Name")
    public static String getNewName() {
        final String name = RandomStringUtils.randomAlphabetic(10);
        return name;
    }


}
