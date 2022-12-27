package com.cydeo.day07;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class P03_SpartanPUTPATCHDELETE extends SpartanTestBase {


    @DisplayName("PUT Spartan Single Spartan With Map ")
    @Test
    public void test1() {

        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("name", "James Bond PUT");
        requestBody.put("gender", "Male");
        requestBody.put("phone", 1234567890L);

        // PUT will update existing record
        int id = 126;

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(requestBody)
                .when().put("/api/spartans/{id}")
                .then()
                .statusCode(204); // no content, I did successfully, but no response


    }

    @DisplayName("PATCH Spartan Single Spartan With Map ")
    @Test
    public void test2() {

        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("name", "James PATCH");


        // PUT will update existing record
        int id = 126;

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(requestBody)
                .when().patch("/api/spartans/{id}")
                .then()
                .statusCode(204); // no content, I did successfully, but no response
    }
    @DisplayName("DELETE Spartan Single Spartan With Map ")
    @Test
    public void test3() {

        // PUT will update existing record
            int id=126;

            /* given().pathParam("id",id)
                    .when().delete("/api/spartans/{id}").
                    then().statusCode(204);

             */
            //use get to see 404
             given().accept(ContentType.JSON)
                    .pathParam("id",id).
                    when().get("/api/spartans/{id}").
                    then().statusCode(404);


        }
}
