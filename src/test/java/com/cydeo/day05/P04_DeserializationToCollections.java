package com.cydeo.day05;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class P04_DeserializationToCollections extends SpartanTestBase {

    /*
     Given accept type is application/json
     And Path param id = 10
     When i send GET request to /api/spartans
     Then status code is 200
     And content type is json
     And spartan data matching:
     id > 10
     name>Lorenza
     gender >Female
     phone >3312820936
     */

    @Test
    public void test1() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", 10)
                .when().get("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();

        Map<String, Object> spartanMap = response.as(Map.class);
        System.out.println(spartanMap);


        int id = (int) spartanMap.get("id");
        String name = (String) spartanMap.get("name");
        String gender = (String) spartanMap.get("gender");

        System.out.println("id = " + id);


        JsonPath jsonPath = response.jsonPath();
        Map<String, Object> jsonPathMap = jsonPath.getMap("");
        System.out.println(jsonPathMap);

        int idJson = (int) jsonPathMap.get("id");
        String nameJson = (String) jsonPathMap.get("name");
        System.out.println("nameJson = " + nameJson);

    }

    @DisplayName("GET All Spartans with Java Collections")

    @Test()
    public void test2() {
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .when().get("/api/spartans")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();

        // Approach one ---> with Response

        List<Map<String, Object>> spartanList = response.as(List.class);
        System.out.println("spartanList = " + spartanList);

        for(Map<String, Object> eachMap : spartanList)
            System.out.println(eachMap);

        // how to find first spartan info
        System.out.println("spartanList.get(0) = " + spartanList.get(0));

        // how to find first spartan name
        System.out.println("spartanList.get(0).get(\"name\") = " + spartanList.get(0).get("name"));

        // how to find first spartan id
        System.out.println("spartanList.get(0).get(\"id\") = " + spartanList.get(0).get("id"));

        // how to store first spartan info as a Map with response.as() method
        // if you want to convert only specific part of the response with response.as() method it doesn't have any parameters to get as path of json object

        Map<String, Object>  firstSpartanMap= response.path("[0]");
        System.out.println("firstSpartanMap = " + firstSpartanMap);

        // Approach 2 ---> with jsonPath

        JsonPath jsonPath = response.jsonPath();

        List<Map<String, Object>> allSpartanList = jsonPath.getList("");
        // get all spartans as a List
        System.out.println("allSpartanList = " + allSpartanList);

        // first spartan info
        System.out.println("allSpartanList.get(0) = " + allSpartanList.get(0));

        // how to find first spartan name
        System.out.println("allSpartanList.get(\"name\") = " + allSpartanList.get(0).get("name"));

        // how to find first spartan id
        System.out.println("allSpartanList.get(0).get(\"id\") = " + allSpartanList.get(0).get("id"));

        

    }
}
