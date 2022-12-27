package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class P02_HrWithParams extends HrTestBase {


      /*
        Given accept type is Json
        And parameters: q = "{"region_id":2}"
        When users sends a GET request to "/countries"
        Then status code is 200
        And Content type is application/json
        And Payload should contain "United States of America"
     */

    @DisplayName("GET request /countries with regionID ")
    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":2}").
                when().get("/countries");

        // response.prettyPrint();


        //Then status code is 200
        assertEquals(200,response.statusCode());
        //And Content type is application/json
        assertEquals("application/json",response.contentType());
        //And Payload should contain "United States of America"
        assertTrue(response.body().asString().contains("United States of America"));


    }
}
