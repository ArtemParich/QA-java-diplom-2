import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateUserTest {

    public UserClient userClient;
    private String accessToken;

    @Before
    public void start() {
        userClient = new UserClient();
        accessToken = null;
    }

    @Test
    public void canCreateUserTest() {
        User user = User.getAllVariables();

        ValidatableResponse responseCreateUser = userClient.createUser(user)
                .then().statusCode(200);

        accessToken = responseCreateUser.extract().body().path("accessToken");

        assertTrue(responseCreateUser.extract().body().path("success"));
        assertNotNull("The response body should include accessToken", responseCreateUser.extract().body().path("accessToken"));
        assertNotNull("The response body should include refreshToken", responseCreateUser.extract().body().path("refreshToken"));
    }

    @Test
    public void cannotCreateTwoIdenticalUserTest() {
        User user = User.getAllVariables();
        accessToken = userClient.createUser(user).body().path("accessToken");

        ValidatableResponse responseCreateUser = userClient.createUser(user).then().statusCode(403);

        String expectedMessage = "User already exists";
        assertFalse(responseCreateUser.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateUser.extract().body().path("message"));
    }

    @Test
    public void cannotCreateUserWithoutEmailTest() {
        User user = User.getPasswordAndName();

        ValidatableResponse responseCreateUser = userClient.createUser(user)
                .then().statusCode(403);

        String expectedMessage = "Email, password and name are required fields";
        assertFalse(responseCreateUser.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateUser.extract().body().path("message"));
    }

    @Test
    public void cannotCreateUserWithoutPasswordTest() {
        User user = User.getEmailAndName();

        ValidatableResponse responseCreateUser = userClient.createUser(user)
                .then().statusCode(403);

        String expectedMessage = "Email, password and name are required fields";
        assertFalse(responseCreateUser.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateUser.extract().body().path("message"));
    }

    @Test
    public void cannotCreateUserWithoutNameTest() {
        User user = User.getEmailAndPassword();

        ValidatableResponse responseCreateUser = userClient.createUser(user)
                .then().statusCode(403);

        String expectedMessage = "Email, password and name are required fields";
        assertFalse(responseCreateUser.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateUser.extract().body().path("message"));
    }

    @After
    public void finish() {
        if (accessToken != null) {
            accessToken = accessToken.substring(7);
            userClient.deleteUser(accessToken);
        }
    }
}
