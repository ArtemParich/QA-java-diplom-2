import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestAssuredParameters {

    @Override
    public String toString() {
        return "RestAssuredParameters{}";
    }

    public RequestSpecification getBaseParameters() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://stellarburgers.nomoreparties.site/api")
                .build();
    }
}
