
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Store {

    long storeId;

    @BeforeTest
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io";
    }

    @Test
    public void returnPetInventories() {
        Response response =
        RestAssured.    given().
                        when().
                        get("/v2/store/inventory");
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.contentType(),"application/json");
    }

    @Test
    public void placeOrderForPet() {
        String json = "{\n" +
                "  \"id\": 0,\n" +
                "  \"petId\": 0,\n" +
                "  \"quantity\": 0,\n" +
                "  \"shipDate\": \"2021-11-17T11:31:25.883Z\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}";
        Response response =
            RestAssured.given().
                        header("accept","application/json").
                        header("Content-Type", "application/json").
                        body(json).
                        when().
                        post("/v2/store/order");
               response.then().
                        body("status",equalTo("placed"));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.contentType(),"application/json");
        storeId = response.path("id");
    }

    @Test(dependsOnMethods = {"placeOrderForPet"})
    public void findOrderById() {
        Response response =
            RestAssured.given().
                        when().
                        get("/v2/store/order/"+storeId);
        response.then().body("status",equalTo("placed")).
                        body("complete",equalTo(true));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.contentType(),"application/json");
    }

    @Test(dependsOnMethods = {"placeOrderForPet"})
    public void deleteOrderById() {
        Response response =
                given().
                        header("accept","application/json").
                        when().
                        delete("/v2/store/order/"+storeId);
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.contentType(),"application/json");
    }
}
