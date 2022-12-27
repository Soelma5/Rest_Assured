package com.cydeo.day06;

import com.cydeo.utilities.HrTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class P01_HRDeserialization extends HrTestBase {


    /**
     * Create a test called getLocation
     * 1. Send request to GET /locations
     * 2. log uri to see
     * 3. Get all Json as a map and print out screen all the things with the help of  map
     * System.out.println("====== GET FIRST LOCATION  ======");
     * System.out.println("====== GET FIRST LOCATION LINKS  ======");
     * System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
     * System.out.println("====== FIRST LOCATION ======");
     * System.out.println("====== FIRST LOCATION ID ======");
     * System.out.println("====== FIRST LOCATION COUNTRY_ID ======");
     * System.out.println("====== GET FIRST LOCATION FIRST LINK  ====== ");
     * System.out.println("====== LAST LOCATION ID ======");
     */

    @DisplayName("GET /locations to deserialization onto Java Collections")
    @Test
    public void test1() {
        JsonPath jsonPath = given()
                .log().uri()
                .when().get("/locations")
                .then()
                .statusCode(200)
                .extract().response().jsonPath();

        Map<String, Object> firstMap = jsonPath.getMap("items[0]");
        System.out.println("firstMap = " + firstMap);

        Map<Object, Object> secondMap = jsonPath.getMap("items[0].links[0]");
        System.out.println("secondMap = " + secondMap);

        List<Map<String, Object>> locationsAsListOfMap = jsonPath.getList("items");
        System.out.println("locationsAsListOfMap = " + locationsAsListOfMap);

        Map<String, Object> firstLocation = locationsAsListOfMap.get(0);
        System.out.println("firstLocation = " + firstLocation);

        System.out.println("locationsAsListOfMap.get(0).get(\"location_id\") = " + locationsAsListOfMap.get(0).get("location_id"));

        System.out.println("locationsAsListOfMap.get(0).get(\"country_id\") = " + locationsAsListOfMap.get(0).get("country_id"));

        List<Map<String, Object>> links = (List<Map<String, Object>>) locationsAsListOfMap.get(0).get("links");
        System.out.println("links.get(0).get(\"href\") = " + links.get(0).get("href"));

        System.out.println("locationsAsListOfMap.get(locationsAsListOfMap.size()).get(\"location_id\") = " + locationsAsListOfMap.get(locationsAsListOfMap.size() - 1).get("location_id"));
    }

    /**
     * Create a test called getTeachers
     * 1. Send request to GET /teacher/all
     * 2. log uri to see
     * 3. Get all Json as a map and print out screen all the things with the help of  map
     * System.out.println("====== GET FIRST TEACHER INFO   ======");
     * System.out.println("====== GET FIRST TEACHER NAME  ======");
     * System.out.println("====== GET ALL TEACHER INFO  AS LIST OF MAP======");
     * System.out.println("====== FIRST TEACHER INFO======");
     * System.out.println("====== FIRST TEACHER NAME ======");
     * System.out.println("====== LAST TEACHER NAME  ======");
     */
    @DisplayName("GET /locations to deserialization onto Java Collections")
    @Test
    public void test2() {
    }
}
