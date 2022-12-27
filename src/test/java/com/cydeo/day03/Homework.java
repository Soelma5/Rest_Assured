package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Homework extends HrTestBase {

    /*
    TASK 1 :
- Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is 2
     */

    @DisplayName("GET USA from countries")
    @Test
    public void task1() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("country_id", "US")
                .when().get("/countries/{country_id}");
        response.prettyPrint();
        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("United States of America"));
        assertTrue(response.body().asString().contains("2"));
    }

    /*
    TASK 2 :
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
     */

    @DisplayName("GET department_id")
    @Test
    public void task2() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .queryParam("q", "{\"department_id\":80}")
                .when().get("/employees");
        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("application/json", response.contentType());
        List<String> jobIds = response.path("items.job_id");
        System.out.println(jobIds);

        System.out.println("jobIds.stream().allMatch(each -> each.startsWith(\"SA\")) = " + jobIds.stream().allMatch(each -> each.startsWith("SA")));

        List<Integer> allDepartmentIds = response.path("items.department_id");

        System.out.println("allDepartmentIds.stream().allMatch(each -> each == 80) = " + allDepartmentIds.stream().allMatch(each -> each == 80));

        JsonPath jsonPath = response.jsonPath();
        assertEquals(25, jsonPath.getInt("count"));

    }

    /*
    TASK 3 :
- Given accept type is Json
-Query param value q= region_id 3
- When users sends request to /countries
- Then status code is 200
- And all regions_id is 3
- And count is 6
- And hasMore is false
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore
     */

    @DisplayName("GET country_name")
    @Test
    public void task3() {

        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .queryParam("q", "{\"region_id\":3}")
                .when().get("/countries");
        response.prettyPrint();
        assertEquals(200, response.statusCode());
        List<Integer> allRegionIds = response.path("items.region_id");
        System.out.println("allRegionIds.stream().allMatch(each -> each == 3) = " + allRegionIds.stream().allMatch(each -> each == 3));

        JsonPath jsonPath = response.jsonPath();
        assertEquals(6, jsonPath.getInt("count"));
        List<String> countries = jsonPath.getList("items.country_name");
        System.out.println(countries);
        assertEquals(false, jsonPath.getBoolean("hasMore"));

    }
    /*
    TASK 1 :
- Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is 2
     */

    @DisplayName("GET USA from countries")
    @Test
    public void task4() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("country_id", "US")
                .when().get("/countries/{country_id}").prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("country_id", is("US"))
                .body("country_name", is("United States of America"))
                .body("region_id", is(2))
                .extract().response();
        System.out.println(response);

        Map<String, Object> countries = response.as(Map.class);
        System.out.println("countries = " + countries);

        String country_name = (String) countries.get("country_name");
        System.out.println("country_name = " + country_name);

    }
}


