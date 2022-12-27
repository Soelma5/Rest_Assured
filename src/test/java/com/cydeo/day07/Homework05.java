package com.cydeo.day07;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.*;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Homework05 extends HrTestBase {

    /*
    Given accept is json
   and content type is json
   When I send post request to "/regions/"
   With json:
   {
       "region_id":100,
       "region_name":"Test Region"
   }
   Then status code is 201
   content type is json
   region_id is 100
   region_name is Test Region
     */

    @DisplayName("POST new region")
    @Test
    public void task1() {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("region_id", 100);
        requestBody.put("region_name", "Test Region");

        JsonPath jsonPath = given().log().body()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/regions/")
                .then()
                .statusCode(201)
                .extract().jsonPath();



        assertEquals(100, jsonPath.getInt("body.region_id"));
        assertEquals("Test Region", jsonPath.getString("body.region_name"));

        int id = jsonPath.getInt("region_id");
        JsonPath jsonPath1 = given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when().get("/regions/{region_id}")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        jsonPath1.prettyPeek();
    }

    /*
    Given accept type is Json
  And content type is json
  When i send PUT request to /regions/100
  With json body:
   {
      "region_id": 100,
       "region_name": "Wooden Region"
      }
  Then status code is 200
  And content type is json
 region_id is 100
 region_name is Wooden Region

     */
    @DisplayName("PUT new region")
    @Test
    public void task2() {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("region_id", 100);
        requestBody.put("region_name", "Wooden Region");

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .pathParam("id", 100)
                .body(requestBody)
                .when().put("/regions/{region_id}")
                .then()
                .statusCode(204);

    }
}
