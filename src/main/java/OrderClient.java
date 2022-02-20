import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredParameters {

    public static final String ORDER_URL = "/orders";

    @Override
    public String toString() {
        return "OrderClient{}";
    }

    @Step("Sending POST request to " + ORDER_URL)
    public Response createOrderWithoutAuthorization(Ingredients ingredients) {
        return given()
                .spec(getBaseParameters())
                .body(ingredients)
                .post(ORDER_URL);
    }

    @Step("Sending POST request to " + ORDER_URL)
    public Response createOrder(Ingredients ingredients, String accessToken) {
        return given()
                .spec(getBaseParameters())
                .auth().oauth2(accessToken)
                .body(ingredients)
                .post(ORDER_URL);
    }

    @Step("Sending GET request to " + ORDER_URL)
    public Response getOrdersListWithoutAuthorization() {
        return given()
                .spec(getBaseParameters())
                .get(ORDER_URL);
    }

    @Step("Sending GET request to " + ORDER_URL)
    public Response getOrdersList(String accessToken) {
        return given()
                .spec(getBaseParameters())
                .auth().oauth2(accessToken)
                .get(ORDER_URL);
    }
}
