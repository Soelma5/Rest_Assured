package com.cydeo.day06;

import com.cydeo.pojo.Formula;
import com.cydeo.utilities.FormulaTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Driver;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeworkFormulaAPI extends FormulaTestBase {

    /**
     * - Given accept type is json
     * - And path param driverId is alonso
     * - When user send request /drivers/{driverId}.json
     * - Then verify status code is 200
     * - And content type is application/json; charset=utf-8
     * - And total is 1
     * - And givenName is David
     * - And familyName is Coulthard
     * - And nationality is British
     */

    @DisplayName("GET driver using JSONPATH")
    @Test
    public void task1() {
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json");


        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());

        JsonPath jsonPath = response.jsonPath();
        assertEquals("1", jsonPath.getString("MRData.total"));
        assertEquals("Fernando", jsonPath.getString("MRData.DriverTable.Drivers[0].givenName"));
        assertEquals("Alonso", jsonPath.getString("MRData.DriverTable.Drivers[0].familyName"));
        assertEquals("Spanish", jsonPath.getString("MRData.DriverTable.Drivers[0].nationality"));


    }

    @DisplayName("GET driver using Hamcrest Matchers")
    @Test
    public void task2() {
        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json").prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("MRData.total", is("1"))
                .body("MRData.DriverTable.Drivers[0].givenName", is("Fernando"))
                .body("MRData.DriverTable.Drivers[0].familyName", is("Alonso"))
                .body("MRData.DriverTable.Drivers[0].nationality", is("Spanish"))
                .extract().response().jsonPath();
        System.out.println("jsonPath = " + jsonPath);

    }

    @DisplayName("GET driver converting Constructor Information to Java Structure")
    @Test
    public void task3() {
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json");


        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());

        JsonPath jsonPath = response.jsonPath();

        Map<String, Object> firstDriver = jsonPath.getMap("MRData.DriverTable.Drivers[0]");

        System.out.println("jsonPath.getString(\"MRData.total\") = " + jsonPath.getString("MRData.total"));
        System.out.println("firstDriver = " + firstDriver);
        System.out.println("firstDriver.get(\"givenName\") = " + firstDriver.get("givenName"));
        System.out.println("firstDriver.get(\"familyName\") = " + firstDriver.get("familyName"));
        System.out.println("firstDriver.get(\"nationality\") = " + firstDriver.get("nationality"));

    }
    @DisplayName("GET driver converting Constructor Information to POJO class")
    @Test
    public void task4() {
        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json")
                        .then()
                                .statusCode(200)
                                        .extract().jsonPath();

        Formula formula = jsonPath.getObject("MRData.DriverTable.Drivers[0]", Formula.class);
        System.out.println("formula = " + formula);

        System.out.println("formula.getGivenName() = " + formula.getGivenName());
        System.out.println("formula.getFamilyName() = " + formula.getFamilyName());
        System.out.println("formula.getNationality() = " + formula.getNationality());


    }
}
