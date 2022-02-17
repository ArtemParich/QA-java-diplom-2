import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateOrderTest {

    public OrderClient orderClient;

    @Before
    public void start() {
        orderClient = new OrderClient();
    }

    @Test
    public void canCreateOrderWithoutAuthorizationTest() {
        ValidatableResponse responseCreateOrder = orderClient.createOrderWithoutAuthorization(Ingredients.getIngredients())
                .then().statusCode(200);

        assertTrue(responseCreateOrder.extract().body().path("success"));
    }

    @Test
    public void canCreateOrderWitAuthorizationTest() {
        UserClient userClient = new UserClient();
        String accessToken = userClient.createUser(User.getAllVariables()).body().path("accessToken");
        accessToken = accessToken.substring(7);

        ValidatableResponse responseCreateOrder = orderClient.createOrder(Ingredients.getIngredients(), accessToken)
                .then().statusCode(200);

        assertTrue(responseCreateOrder.extract().body().path("success"));

        userClient.deleteUser(accessToken).then().statusCode(202);
    }

    @Test
    public void cannotCreateOrderWithoutIngredientsTest() {
        ValidatableResponse responseCreateOrder = orderClient.createOrderWithoutAuthorization(Ingredients.getEmptyIngredients())
                .then().statusCode(400);

        String expectedMessage = "Ingredient ids must be provided";
        assertFalse(responseCreateOrder.extract().body().path("success"));
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateOrder.extract().body().path("message"));
    }

    @Test
    public void cannotCreateOrderWithIncorrectHashIngredientsTest() {
        OrderClient orderClient = new OrderClient();
        orderClient.createOrderWithoutAuthorization(Ingredients.getIncorrectHashIngredients())
                .then().statusCode(500);
    }
}
