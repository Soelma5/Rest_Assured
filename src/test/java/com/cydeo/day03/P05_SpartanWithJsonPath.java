package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P05_SpartanWithJsonPath extends SpartanTestBase {

    @Test
    public void test1(){

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 10)
                .when().get("/api/spartans/{id}");

        response.prettyPrint();

        assertEquals(200, response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        System.out.println("jsonPath.getInt(\"id\") = " + jsonPath.getInt("id"));

        int id = jsonPath.getInt("id");
        String name = jsonPath.getString("name");
        String gender = jsonPath.getString("gender");
        long phone = jsonPath.getLong("phone");

        assertEquals(10, id);
    }
}
