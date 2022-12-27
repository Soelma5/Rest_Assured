package com.cydeo.day09;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class P04_JsonSchemaValidation extends SpartanTestBase {

    @Test
    public void test1() {
        given().accept(ContentType.JSON)
                .pathParam("id", 45)
                .when().get("/api/spartans/{id}").prettyPeek()
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));

        // it gets response from execution, and we provided structure of response in following path

        // --JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json")

        // if structure that we are getting matches with SingleSpartanSchema it will pass
    }

    @Test
    public void test2() {
        given().accept(ContentType.JSON)
                .pathParam("id", 45)
                .when().get("/api/spartans/{id}").prettyPeek()
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/SingleSpartanSchema.json")));
    }
    @DisplayName("GET /api/spartans/search to validate with JsonSchemaValidator matches JsonSchema")
    @Test()
    public void test3() {
        given().accept(ContentType.JSON)
                .when().get("/api/spartans/search")
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SearchSpartansSchema.json"));
    }
}
