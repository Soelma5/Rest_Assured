package com.cydeo.day08;

import com.cydeo.utilities.NewsTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Homework extends NewsTestBase {

    String apiKey = "12e81ae8a9a840948da3a0115135e2c6";

    @DisplayName("GET bitcoin using X-API-KEY in HEADER")
    @Test
    public void task1() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .queryParam("q", "bitcoin")
                .and()
                .queryParam("apiKey", apiKey)
                .when().get("everything")
                .then().log().ifValidationFails()
                .statusCode(200)
                .extract().jsonPath();

        List<Map<String, Object>> list = jsonPath.getList("articles");
        System.out.println(list);

        for (Map<String, Object> each : list)
            System.out.println("each.toString().contains(\"bitcoin\") = " + each.toString().contains("bitcoin"));
    }

    @DisplayName("GET bitcoin using QUERY PARAM")
    @Test
    public void task2() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .headers("X-Api-Key", apiKey)
                .queryParam("q", "bitcoin")
                .when().get("everything")
                .then().log().ifValidationFails()
                .statusCode(200)
                .extract().jsonPath();

        List<Map<String, Object>> list = jsonPath.getList("articles");
        System.out.println(list);

        for (Map<String, Object> each : list)
            System.out.println("each.toString().contains(\"bitcoin\") = " + each.toString().contains("bitcoin"));

    }

    @DisplayName("GET bitcoin using Authorization in HEADER")
    @Test
    public void task3() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .queryParam("q", "bitcoin")
                .and()
                .header("Authorization", apiKey)
                .when().get("everything")
                .then().log().ifValidationFails()
                .statusCode(200)
                .extract().jsonPath();

        List<Map<String, Object>> list = jsonPath.getList("articles");
        System.out.println(list);

        for (Map<String, Object> each : list)
            System.out.println("each.toString().contains(\"bitcoin\") = " + each.toString().contains("bitcoin"));
    }

    @DisplayName("GET us using Authorization in HEADER")
    @Test
    public void task4() {

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .queryParam("country", "us")
                .and()
                .header("Authorization", "Bearer " + apiKey)
                .when().get("top-headlines")
                .then().log().ifValidationFails()
                .statusCode(200)
                .extract().jsonPath();
        jsonPath.prettyPrint();
        List<Map<String, Object>> list = jsonPath.getList("articles.source.name");
        System.out.println("list = " + list);
    }
}
