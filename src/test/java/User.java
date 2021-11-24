import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class User {

    String userName;

    @BeforeTest
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io";
    }

    @Test
    public void createWithList() {
        String json ="[\n" +
                "  {\n" +
                "    \"id\": 0,\n" +
                "    \"username\": \"string\",\n" +
                "    \"firstName\": \"string\",\n" +
                "    \"lastName\": \"string\",\n" +
                "    \"email\": \"string\",\n" +
                "    \"password\": \"string\",\n" +
                "    \"phone\": \"string\",\n" +
                "    \"userStatus\": 0\n" +
                "  }\n" +
                "]";
        Response response =
            RestAssured.given().
                        header("accept","application/json").
                        header("Content-Type", "application/json").
                        body(json).
                        when().
                        post("/v2/user/createWithList");
               response.then().
                        body("message",equalTo("ok"));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
    }

    @Test
    public void createUser() {
        String json = "{\n" +
                "  \"id\": 0,\n" +
                "  \"username\": \"Noob\",\n" +
                "  \"firstName\": \"string\",\n" +
                "  \"lastName\": \"string\",\n" +
                "  \"email\": \"string\",\n" +
                "  \"password\": \"string\",\n" +
                "  \"phone\": \"string\",\n" +
                "  \"userStatus\": 0\n" +
                "}";
        Response response =
                RestAssured.given().
                        header("accept","application/json").
                        header("Content-Type", "application/json").
                        body(json).
                        when().
                        post("/v2/user");
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.contentType(),"application/json");
        userName = response.path("username");
    }

    @Test
    public void getUserByName() {
   Response response = given().
                       when().
                       get("/v2/user/Noob");
              response.then().
                       body("username", equalTo("Noob"));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.contentType(),"application/json");
    }

    @Test
    public void updateUser() {
        String json = "{\n" +
                "  \"id\": 4,\n" +
                "  \"username\": \"string\",\n" +
                "  \"firstName\": \"string\",\n" +
                "  \"lastName\": \"string\",\n" +
                "  \"email\": \"string\",\n" +
                "  \"password\": \"string\",\n" +
                "  \"phone\": \"string\",\n" +
                "  \"userStatus\": 0\n" +
                "}";
        Response response =
                given().
                        header("accept","application/json").
                        header("Content-Type", "application/json").
                        body(json).
                        when().
                        put("/v2/user/Petro");
               response.then().
                        body("message", equalTo("4"));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void deleteUser() {
        Response response =
                given().
                        header("accept","application/json").
                        when().
                        delete("/v2/user/Noob");
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void logUser() {
        Response response = given().
                            when().
                            get("/v2/user/login?username=Vita&password=123");
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void logOutUser() {
        Response response = given().
                            when().
                            get("/v2/user/logout");
                   response.then().
                            body("message",equalTo("ok")).
                            log().all();
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void createListWithArray() {
        String json = "[\n" +
                "  {\n" +
                "    \"id\": 0,\n" +
                "    \"username\": \"string\",\n" +
                "    \"firstName\": \"string\",\n" +
                "    \"lastName\": \"string\",\n" +
                "    \"email\": \"string\",\n" +
                "    \"password\": \"string\",\n" +
                "    \"phone\": \"string\",\n" +
                "    \"userStatus\": 0\n" +
                "  }\n" +
                "]";
        Response response =
                RestAssured.given().
                        header("accept","application/json").
                        header("Content-Type", "application/json").
                        body(json).
                        when().
                        post("/v2/user/createWithArray");
               response.then().
                        body("message",equalTo("ok")).
                        log().all();
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);

    }



}
