
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetStore {

    long petId;

    @BeforeTest
    public void SetUp() {
        RestAssured.baseURI = "https://petstore.swagger.io";
    }

    @Test
    public static void getPetByStatusPending () {
        Response response = given().
                when().
                get("/v2/pet/findByStatus?status=pending");
       response.then().
                body("status[0]",equalTo("pending"));
        int statusCode = response.getStatusCode();
        System.out.println(response.prettyPrint());
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(response.contentType(),"application/json");
    }

    @Test
    public void getPetByStatusAvailable() {
        Response response =
                given().
                when().
                get("/v2/pet/findByStatus?status=available");
       response.then().
                body("status[0]",equalTo("available"));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.contentType(),"application/json");
    }

    @Test
    public void getPetByStatusSold (){
        Response response =
                given().
                when().
                get("/v2/pet/findByStatus?status=sold");
       response.then().
                body("status[0]",equalTo("sold"));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.contentType(),"application/json");
    }

//    @Test
//    public void uploadImg() {
//        Response response =
//                given().
//                header("Content-Type","multipart/form-data").
//                formParam("additionalMetadata","test1").
//                multiPart("file",new File("C:\\Users\\v.riaboshapka\\Pictures"),
//                        "image/jpeg").
//                        when().
//                        post("/v2/pet/9223372000001103000/uploadImage");
//        System.out.println(response.prettyPrint());
//        System.out.println("Status Code = "+response.getStatusCode());
//    }

    @Test(dependsOnMethods = {"addNewPet"})
    public void findPetWithId() {
        Response response = given().
                when().
                get("/v2/pet/"+petId);
       response.then().
                body("category.name",equalTo("PetCat")).
                body("name",equalTo("Cat"));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void addNewPet (){
        String json = "{\n" +
                "  \"id\": 0," +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"PetCat\"\n" +
                "  },\n" +
                "  \"name\": \"Cat\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"petStore\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";
        Response response =
                given().
                header("accept","application/json").
                header("Content-Type", "application/json").
                body(json).
                when().
                post("/v2/pet");
       response.then().
                body("category.name",equalTo("PetCat")).
                body("name",equalTo("Cat")).
                body("tags[0].id",equalTo(0)).
                body("tags[0].name",equalTo("petStore")).
                log().all();
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);

        petId = response.path("id");
        System.out.println(petId);
    }

    @Test
    public void updateExistingPet() {
        String json = "{\n" +
                "  \"id\": 3," +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"Cat\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";
        Response response =
                given().
                header("accept","application/json").
                header("Content-Type", "application/json").
                body(json).
                when().
                put("/v2/pet");
       response.then().
                body("name", equalTo("Cat")).
                body("status", equalTo("available"));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"addNewPet"})
    public void deletePet() {
       Response response =
               given().
               header("accept","application/json").
               when().
               delete("/v2/pet/"+petId);
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"updateExistingPet"})
    public void addFormData() {
      Response response =
              given().
              header("accept","application/json").
              header("Content-Type","application/x-www-form-urlencoded").
              formParam("name", "sweetheart").
              formParam("status","sold").
              when().
              post("/v2/pet/3");
     response.then().
              body("message", equalTo("3"));
        System.out.println(response.prettyPrint());
        System.out.println("Status Code = "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

}



