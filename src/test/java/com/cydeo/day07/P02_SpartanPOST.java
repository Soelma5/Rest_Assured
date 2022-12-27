package com.cydeo.day07;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P02_SpartanPOST extends SpartanTestBase {

    /**
     * Given accept type is JSON
     * and Content type is JSON
     * And request json body is:
     * {
     * "gender":"Male",
     * "name":"John Doe",
     * "phone":8877445596
     * }
     * When user sends POST request to '/api/spartans'
     * Then status code 201
     * And content type should be application/json
     * And json payload/response/body should contain:
     * verify the success value is A Spartan is Born!
     * "name": "John Doe",
     * "gender": "Male",
     * "phone": 8877445596
     */


    @DisplayName("POST spartan with String body")
    @Test
    public void test1() {

        String requestBody = "{\n" +
                "      \"gender\":\"Male\",\n" +
                "      \"name\":\"John Doe\",\n" +
                "      \"phone\":8877445596\n" +
                "      }";
        String expectedMessage = "A Spartan is Born!";

        JsonPath jsonPath = given()
                .accept(ContentType.JSON) // API sends me response in JSON format
                .contentType(ContentType.JSON) // API I am sending body in JSON format
                .body(requestBody)
                .when().post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        jsonPath.prettyPeek();
        assertEquals(expectedMessage, jsonPath.getString("success"));
        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(8877445596L, jsonPath.getLong("data.phone"));

    }

    @DisplayName("POST spartan with String body")
    @Test
    public void test2() {

        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("name", "James Bond");
        requestBody.put("gender", "Male");
        requestBody.put("phone", 1234567890L);


        String expectedMessage = "A Spartan is Born!";


        // body(requestBody) --> is doing serialization behind the scene to send data in JSON format
        // to do serialization we need to one of the ObjectMapper(Jackson/GSON)

        JsonPath jsonPath = given()
                .accept(ContentType.JSON).log().body() // API sends me response in JSON format
                .contentType(ContentType.JSON) // API I am sending body in JSON format
                .body(requestBody)
                .when().post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        jsonPath.prettyPeek();
        assertEquals(expectedMessage, jsonPath.getString("success"));
        assertEquals("James Bond", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(1234567890L, jsonPath.getLong("data.phone"));


        Map<Object, Object> map = jsonPath.getMap("");
        System.out.println("map = " + map);

        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);
        assertEquals(map, jsonPath.getMap(""));


    }

    @DisplayName("POST spartan with Spartan POJO body")
    @Test
    public void test3() {

       Spartan requestBody = new Spartan();
        requestBody.setName("James Bond");
        requestBody.setGender( "Male");
        requestBody.setPhone(1234567890L);


        String expectedMessage = "A Spartan is Born!";
        JsonPath jsonPath = given()
                .accept(ContentType.JSON).log().body() // API sends me response in JSON format
                .contentType(ContentType.JSON) // API I am sending body in JSON format
                .body(requestBody)
                .when().post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        // get ID
        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);

        assertEquals(expectedMessage, jsonPath.getString("success"));
        assertEquals(requestBody.getName(), jsonPath.getString("data.name"));
        assertEquals(requestBody.getGender(), jsonPath.getString("data.gender"));
        assertEquals(requestBody.getPhone(), jsonPath.getLong("data.phone"));
    }
    @DisplayName("POST spartan with Spartan POJO body and GET same spartan")
    @Test
    public void test4() {

        Spartan requestBody = new Spartan();
        requestBody.setName("James Bond");
        requestBody.setGender( "Male");
        requestBody.setPhone(1234567890L);


        System.out.println("requestBody = " + requestBody);

        String expectedMessage = "A Spartan is Born!";
        JsonPath jsonPath = given()
                .accept(ContentType.JSON).log().body() // API sends me response in JSON format
                .contentType(ContentType.JSON) // API I am sending body in JSON format
                .body(requestBody)
                .when().post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        // get ID
        int idFromPOST = jsonPath.getInt("data.id");
        System.out.println("id = " + idFromPOST);

        assertEquals(expectedMessage, jsonPath.getString("success"));
        assertEquals(requestBody.getName(), jsonPath.getString("data.name"));
        assertEquals(requestBody.getGender(), jsonPath.getString("data.gender"));
        assertEquals(requestBody.getPhone(), jsonPath.getLong("data.phone"));

        Spartan spartanGET = given().accept(ContentType.JSON)
                .pathParam("id", idFromPOST)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("", Spartan.class);
        System.out.println("spartanGET = " + spartanGET);
        assertEquals(requestBody.getName(), spartanGET.getName());
        assertEquals(requestBody.getGender(), spartanGET.getGender());
        assertEquals(requestBody.getPhone(), spartanGET.getPhone());
        assertEquals(requestBody.getId(), requestBody.getId());

    }
}
