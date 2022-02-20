import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDataChangesTest {

    public UserClient userClient;
    public User user;
    private String accessToken;

    @Before
    public void start() {
        userClient = new UserClient();
        user = User.getAllVariables();
        accessToken = userClient.createUser(user).body().path("accessToken");
        accessToken = accessToken.substring(7);
    }

    @Test
    public void canChangesUserEmailWithAuthorizationTest() {
        user.email = User.getNewEmail();

        ValidatableResponse responseChangesUserData = userClient.userDataChanges(user, accessToken)
                .then().statusCode(200);

        assertTrue(responseChangesUserData.extract().body().path("success"));
        assertEquals("The e-mail in response body should be: " + user.email.toLowerCase(), user.email.toLowerCase(),
                responseChangesUserData.extract().body().path("user.email"));
        assertEquals("The name in response body should be: " + user.name, user.name,
                responseChangesUserData.extract().body().path("user.name"));
    }

    @Test
    public void cannotChangesUserEmailThatAlreadyUseTest() {
        String firstEmail = user.email;

        user = User.getAllVariables();
        String secondAccessToken = userClient.createUser(user).then().statusCode(200).extract().body().path("accessToken");
        secondAccessToken = secondAccessToken.substring(7);

        user.email = firstEmail;

        ValidatableResponse responseChangesUserData = userClient.userDataChanges(user, secondAccessToken)
                .then().statusCode(403);

        String expectedMessage = "User with such email already exists";
        assertFalse(responseChangesUserData.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseChangesUserData.extract().body().path("message"));

        userClient.deleteUser(secondAccessToken);
    }

    @Test
    public void canChangesUserPasswordWithAuthorizationTest() {
        user.password = User.getNewPassword();

        ValidatableResponse responseChangesUserData = userClient.userDataChanges(user, accessToken)
               .then().statusCode(200);

        assertTrue(responseChangesUserData.extract().body().path("success"));
        assertEquals("The e-mail in response body should be: " + user.email.toLowerCase(), user.email.toLowerCase(),
                responseChangesUserData.extract().body().path("user.email"));
        assertEquals("The name in response body should be: " + user.name, user.name,
                responseChangesUserData.extract().body().path("user.name"));
    }

    @Test
    public void canChangesUserNameWithAuthorizationTest() {
        user.name = User.getNewName();

        ValidatableResponse responseChangesUserData = userClient.userDataChanges(user, accessToken)
                .then().statusCode(200);

        assertTrue(responseChangesUserData.extract().body().path("success"));
        assertEquals("The e-mail in response body should be: " + user.email.toLowerCase(), user.email.toLowerCase(),
                responseChangesUserData.extract().body().path("user.email"));
        assertEquals("The name in response body should be: " + user.name, user.name,
                responseChangesUserData.extract().body().path("user.name"));
    }

    @Test
    public void cannotChangesUserEmailWithoutAuthorizationTest() {
        user.email = User.getNewEmail();

        ValidatableResponse responseChangesUserData = userClient.userDataChangesWithoutToken(user)
                .then().statusCode(401);

        String expectedMessage = "You should be authorised";
        assertFalse(responseChangesUserData.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseChangesUserData.extract().body().path("message"));
    }

    @Test
    public void cannotChangesUserPasswordWithoutAuthorizationTest() {
        user.password = User.getNewPassword();

        ValidatableResponse responseChangesUserData = userClient.userDataChangesWithoutToken(user)
                .then().statusCode(401);

        String expectedMessage = "You should be authorised";
        assertFalse(responseChangesUserData.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseChangesUserData.extract().body().path("message"));
    }


    @Test
    public void cannotChangesUserNameWithoutAuthorizationTest() {
        user.name = User.getNewName();

        ValidatableResponse responseChangesUserData = userClient.userDataChangesWithoutToken(user)
                .then().statusCode(401);

        String expectedMessage = "You should be authorised";
        assertFalse(responseChangesUserData.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseChangesUserData.extract().body().path("message"));
    }

    @After
    public void finish() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}
