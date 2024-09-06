import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class HttpRequest {
    private final String BASE_URL = "https://reqres.in/api";

    @Test(priority = 1)
    public void getListOfUsers() {

        Response responses =

                given()
                        .pathParam("myUser", "users")
                        .queryParam("page", 2)
                        .when().get(BASE_URL + "/{myUser}")
                        .then()
                        .statusCode(200)
                        //To print the exact logs of the response along with headers
                        //.log().all();
                        .extract().response();

        System.out.println(responses.getBody().asString());
    }

    @Test(priority = 2)
    public void deleteUser() {
        Response response =
                given()
                        .pathParam("myUser", "users")
                        .queryParam("page", 2)
                        .queryParam("id", 8)
                        .when()
                        .delete(BASE_URL + "/{myUser}");

        assertEquals(204, response.getStatusCode());
    }

    @Test(priority = 3)
    public void updateUser() {
        JSONObject data = new JSONObject();
        data.put("name", "morpheus");
        data.put("job", "zion resident");

        Response response =
                given()
                        .contentType("application/json")
                        .pathParam("myUser", "users")
                        .pathParam("data", "2")
                        .body(data.toString())
                        .when()
                        .put(BASE_URL + "/{myUser}/{data}");
        //.then().extract().response();
        //.then().log().all();

        String body = response.getBody().asString();
        System.out.println(body);

        assertEquals(200, response.getStatusCode());
        assertTrue(body.contains("morpheus"));
    }
}
