package com.cydeo.day09;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Homework extends SpartanAuthTestBase {
    /**
     *     Do schema validation for ALLSPARTANS and POST SINGLE SPARTAN
     *
     *     ALL SPARTANS
     *      1- Get all spartans by using /api/spartans
     *      2- Validate schema by using  JsonSchemaValidator
     *
     *
     *    POST SINGLE SPARTANS
     *       1- Post single spartan
     *       2- Validate schema by using  JsonSchemaValidator
     *
     */

    @DisplayName("GET all spartans, validate schema by using JsonSchemaValidator")
    @Test
    public void task1(){
        given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("AllSpartansSchema.json"));
    }
    @DisplayName("POST single spartan, validate schema by using JsonSchemaValidator")
    @Test
    public void task2(){
        Map<String, Object> newSpartan = new LinkedHashMap<>();
        newSpartan.put("name", "Leonardo Davinci");
        newSpartan.put("gender", "Male");
        newSpartan.put("phone", 3345046558l);
        System.out.println("newSpartan = " + newSpartan);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(newSpartan)
                .when().post("/api/spartans")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SpartanPostSchema.json"));
    }
}
