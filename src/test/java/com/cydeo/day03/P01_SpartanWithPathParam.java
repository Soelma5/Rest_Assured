package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class P01_SpartanWithPathParam extends SpartanTestBase {


    @DisplayName("GET Spartan with path param")
    @Test
    public void test1() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParams("id", 24)
                .when().get("/api/spartans/{id}");
        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Julio"));
    }

    /*
        TASK
        Given accept type is Json
        And Id parameter value is 500
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 404
        And response content-type: application/json
        And "Not Found" message should be in response payload
     */

    @DisplayName("GET 404 Error")
    @Test
    public void test2() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParams("id", 500)
                .when().get("/api/spartans/{id}");
        response.prettyPrint();

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Not Found"));


    }

    @DisplayName("GET Spartan with query param")
    @Test
    public void test3() {
        Response response =
                given()
                        .accept(ContentType.JSON)
                        .and()
                        .queryParam("gender", "Female")
                        .and()
                        .queryParam("nameContains", "e")
                        .when()
                        .get("/api/spartans/search");

        response.prettyPrint();


        assertEquals(200, response.getStatusCode());

        assertEquals("application/json", response.contentType());

        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Janette"));
    }
}
