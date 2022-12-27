package com.cydeo.day06;

import com.cydeo.utilities.FormulaTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class HomeworkFormulaAPI2 extends FormulaTestBase {

    /**
     * - Given accept type is json
     * - When user send request /constructorStandings/1/constructors.json -
     * Then verify status code is 200
     * - And content type is application/json; charset=utf-8
     * - And total is 17
     * - And limit is 30
     * - And each constructor has constructorId
     * - And constructor has name
     * - And size of constructor is 17 (using with hasSize)
     * - And constructor IDs has “benetton”, “mercedes”,”team_lotus”
     * - And names are in same order by following list
     */

    @DisplayName("GET constructor using JSONPATH")
    @Test
    public void task1() {
        Response response = given()
                .accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json");
        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());
        JsonPath jsonPath = response.jsonPath();
        assertEquals(17, jsonPath.getInt("MRData.total"));
        assertEquals(30, jsonPath.getInt("MRData.limit"));
        List<Map<String, Object>> list = jsonPath.getList("MRData.ConstructorTable.Constructors");
        System.out.println("string = " + list);

        for (Map<String, Object> each : list) {
            System.out.println("each.get(\"constructorId\") = " + each.get("constructorId"));
            System.out.println("each.get(\"name\") = " + each.get("name"));
        }
        assertThat(jsonPath.getList("MRData.ConstructorTable.Constructors"), hasSize(17));

        assertThat(jsonPath.getList("MRData.ConstructorTable.Constructors.constructorId"), hasItems("benetton", "mercedes", "team_lotus"));

        assertThat(jsonPath.getList("MRData.ConstructorTable.Constructors.name"), containsInRelativeOrder("Benetton", "Brabham-Repco", "Brawn", "BRM", "Cooper-Climax", "Ferrari", "Lotus-Climax", "Lotus-Ford", "Matra-Ford", "McLaren", "Mercedes", "Red Bull", "Renault", "Team Lotus", "Tyrrell", "Vanwall", "Williams"));

    }

    @DisplayName("GET constructor using HAMCREST MATCHERS")
    @Test
    public void task2() {
        List<String> names = Arrays.asList("Benetton", "Brabham-Repco", "Brawn", "BRM", "Cooper-Climax", "Ferrari", "Lotus-Climax", "Lotus-Ford", "Matra-Ford", "McLaren", "Mercedes", "Red Bull", "Renault", "Team Lotus", "Tyrrell", "Vanwall", "Williams");

        given()
                .accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json").prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .contentType("application/json; charset=utf-8")
                .assertThat()
                .body("MRData.total", is("17"),
                        "MRData.limit", is("30")).assertThat()
                .body("MRData.ConstructorTable.Constructors.constructorId", everyItem(notNullValue()))
                .body("MRData.ConstructorTable.Constructors.name", everyItem(notNullValue()))
                .body("MRData.ConstructorTable.Constructors", hasSize(17))
                .body(("MRData.ConstructorTable.Constructors.constructorId"), hasItems("benetton", "mercedes", "team_lotus"))
                .body("MRData.ConstructorTable.Constructors.name", containsInRelativeOrder(names));

    }

    @DisplayName("GET constructor converting Constructor Information to Java Structure")
    @Test
    public void task3() {
       JsonPath jsonPath = given()
                .accept(ContentType.JSON)
               .log().uri()
               .when().get("/constructorStandings/1/constructors.json")
               .prettyPeek()
               .then()
               .statusCode(200)
               .extract().response().jsonPath();
        System.out.println("jsonPath = " + jsonPath);

        System.out.println("jsonPath.getString(\"MRData.total\") = " + jsonPath.getString("MRData.total"));
        System.out.println("jsonPath.getString(\"MRData.limit\") = " + jsonPath.getString("MRData.limit"));


    }
}
