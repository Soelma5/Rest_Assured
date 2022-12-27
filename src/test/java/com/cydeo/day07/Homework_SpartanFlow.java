package com.cydeo.day07;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Homework_SpartanFlow extends SpartanTestBase {
    static int globalId;
    /*
    Create a spartan Map
    name = "API Flow POST"
    gender="Male"
    phone=1231231231l
            - verify status code 201
            - message is "A Spartan is Born!"
            - take spartanID from response and save as a global  variable

     */

@Order(value = 1)
    @DisplayName("POST new spartan")
    @Test
    public void task1() {

    Map<String, Object> map = new LinkedHashMap<>();
    map.put("name", "API Flow POST");
    map.put("gender", "Male");
    map.put("phone", 1231231231l);

    String expectedMessage = "A Spartan is Born!";
    JsonPath jsonPath = given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post("/api/spartans").prettyPeek()
            .then()
            .statusCode(201)
            .extract().jsonPath();
    jsonPath.prettyPeek();
    assertEquals(expectedMessage, jsonPath.getString("success"));
    globalId = jsonPath.getInt("data.id");
}
    @Order(value = 2)
    @DisplayName("GET new spartan")
    @Test
    public void task2() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .pathParam("id", globalId)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().jsonPath();
        assertEquals("API Flow POST", jsonPath.getString("name"));
    }

    /*
    Create a spartan Map
                name = "API PUT Flow"
                gender="Female"
                phone=3213213213
             - verify status code 204
     */
    @Order(value = 3)
    @DisplayName("PUT new spartan")
    @Test
    public void task3() {
        System.out.println("globalId = " + globalId);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "API PUT Flow");
        map.put("gender", "Female");
        map.put("phone", 3213213213l);


        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("id", globalId)
                .body(map)
                .when().put("/api/spartans/{id}")
                .then()
                .statusCode(204)
                .extract().jsonPath();
    }
/*
- verify status code 200
             - verify name is API PUT Flow
 */
        @Order(value = 4)
        @DisplayName("GET new spartan")
        @Test
        public void task4 () {
            JsonPath jsonPath = given()
                    .accept(ContentType.JSON)
                    .pathParam("id", globalId)
                    .when().get("/api/spartans/{id}")
                    .then()
                    .statusCode(200)
                    .extract().jsonPath();
            assertEquals("API PUT Flow", jsonPath.getString("name"));
        }

        @Order(value = 5)
        @DisplayName("DELETE new spartan")
        @Test
        public void task5 () {
            given().pathParam("id", globalId)
                    .when().delete("/api/spartans/{id}").
                    then().statusCode(204);
        }

            @Order(value = 6)
            @DisplayName("GET Not Found new spartan")
            @Test
            public void task6() {
        given().accept(ContentType.JSON)
                .pathParam("id",globalId).
                when().get("/api/spartans/{id}").
                then().statusCode(404);
    }
}