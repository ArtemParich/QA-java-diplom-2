import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginUserTest {

    public UserClient userClient;
    public User user;
    private String accessToken;

    @Before
    public void start() {
        userClient = new UserClient();
        user = User.getAllVariables();
        accessToken = userClient.createUser(user).body().path("accessToken");
    }

    @Test
    public void canLoginUserTest() {
        ValidatableResponse responseLoginUser = userClient.loginUser(UserCredentials.getVariablesForAuthorization(user))
                .then().statusCode(200);

        assertTrue(responseLoginUser.extract().body().path("success"));
        assertNotNull("The response body should include accessToken", responseLoginUser.extract().body().path("accessToken"));
        assertNotNull("The response body should include refreshToken", responseLoginUser.extract().body().path("refreshToken"));
    }

    @Test
    public void cannotLoginUserWithIncorrectEmailAndPasswordTest() {
        ValidatableResponse responseLoginUser = userClient.loginUser(UserCredentials.getIncorrectLoginAndPassword(user))
                .then().statusCode(401);

        String expectedMessage = "email or password are incorrect";
        assertFalse(responseLoginUser.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginUser.extract().body().path("message"));
    }

    @After
    public void finish() {
        if (accessToken != null) {
            accessToken = accessToken.substring(7);
            userClient.deleteUser(accessToken);
        }
    }
}
