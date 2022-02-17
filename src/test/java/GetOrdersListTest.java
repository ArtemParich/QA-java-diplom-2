import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetOrdersListTest {

    public OrderClient orderClient;

    @Before
    public void start() {
        orderClient = new OrderClient();
    }

    @Test
    public void cannotGetOrdersListWithoutAuthorizationTest() {
        ValidatableResponse responseGetOrdersList = orderClient.getOrdersListWithoutAuthorization()
                .then().statusCode(401);

        String expectedMessage = "You should be authorised";
        assertFalse(responseGetOrdersList.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseGetOrdersList.extract().body().path("message"));
    }

    @Test
    public void canGetOrdersListWithAuthorizationTest() {
        UserClient userClient = new UserClient();

        String accessToken = userClient.createUser(User.getAllVariables())
                .then().statusCode(200).extract().body().path("accessToken");
        accessToken = accessToken.substring(7);

        orderClient.createOrder(Ingredients.getIngredients(), accessToken);
        orderClient.createOrder(Ingredients.getIngredients(), accessToken);

        ValidatableResponse responseGetOrdersList = orderClient.getOrdersList(accessToken)
                .then().statusCode(200);

        assertTrue(responseGetOrdersList.extract().body().path("success"));

        userClient.deleteUser(accessToken).then().statusCode(202);
    }
}


