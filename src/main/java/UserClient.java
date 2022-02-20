import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredParameters {

    public static final String REGISTER_URL = "/auth/register";
    public static final String LOGIN_URL = "/auth/login";
    public static final String CHANGES_URL = "/auth/user";

    @Override
    public String toString() {
        return "UserClient{}";
    }

    @Step("Sending POST request to " + REGISTER_URL)
    public Response createUser(User user) {
        return given()
                .spec(getBaseParameters())
                .body(user)
                .post(REGISTER_URL);
    }

    @Step("Sending POST request to " + LOGIN_URL)
    public Response loginUser(UserCredentials userCredentials) {
        return given()
                .spec(getBaseParameters())
                .body(userCredentials)
                .post(LOGIN_URL);
    }

    @Step("Sending PATCH request to " + CHANGES_URL)
    public Response userDataChanges(User user, String accessToken) {
        return given()
                .spec(getBaseParameters())
                .auth().oauth2(accessToken)
                .body(user)
                .patch(CHANGES_URL);
    }

    @Step("Sending PATCH request to " + CHANGES_URL)
    public Response userDataChangesWithoutToken(User user) {
        return given()
                .spec(getBaseParameters())
                .body(user)
                .patch(CHANGES_URL);
    }

    @Step("Sending DELETE request to " + CHANGES_URL)
    public Response deleteUser(String accessToken) {
        return given()
                .spec(getBaseParameters())
                .auth().oauth2(accessToken)
                .delete(CHANGES_URL);
    }
}
