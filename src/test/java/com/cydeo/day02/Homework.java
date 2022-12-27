package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Homework {

    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://54.227.119.244:1000/ords/hr";
    }

    /**
     * Given accept type is Json
     *  When users sends request to /countries/US
     *  Then status code is 200
     *  And Content - Type is application/json
     *  And response contains United States of America
     */

    @DisplayName("GET USA")
    @Test
    public void getUSA(){

        Response response = RestAssured
                .given()
                .when()
                .accept("application/json")
                .when().get("/countries/US");
        response.prettyPrint();

        Assertions.assertEquals("application/json", response.contentType());

        Assertions.assertTrue(response.body().asString().contains("United States of America"));

        Assertions.assertEquals(200, response.getStatusCode());


    }

    /**
     * Given accept type is Json
     * When users sends request to /employees/1
     * Then status code is 404
     */

    @DisplayName("GET Error")
    @Test
    public void getError(){
        Response response = RestAssured
                .given()
                .accept("application/json")
                .when().get("/employees/1");
        response.prettyPrint();

        Assertions.assertEquals(404, response.getStatusCode());
    }

    /**
     * Given accept type is Json
     * When users sends request to /regions/1
     * Then status code is 200
     * And Content - Type is application/json
     * And response contains Europe
     * And header should contains Date
     * And Transfer-Encoding should be chunked
     */

    @DisplayName("GET Europe")
    @Test
    public void getEurope(){

        Response response = RestAssured
                .given()
                .accept("application/json")
                .when().get("/regions/1");
        response.prettyPrint();

        Assertions.assertEquals(200, response.getStatusCode());

        Assertions.assertEquals("application/json", response.contentType());

        Assertions.assertTrue(response.body().asString().contains("Europe"));

        System.out.println("response.headers().getValue(\"Date\") = " + response.headers().getValue("Date"));
    }
}
