package com.cydeo.day08;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class P01_SpartanAuthTest extends SpartanAuthTestBase {

    @DisplayName("GET /spartans as GUEST user")
    @Test
    public void test1() {

        // it is negative test case
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when().get("/api/spartans/")
                .then().log().ifValidationFails()
                .statusCode(401)
                .body("error", is("Unauthorized"));

    }

    @DisplayName("GET /spartans as USER -> expect -> 200")
    @Test
    public void test2() {


        given()
                .accept(ContentType.JSON).log().all()
                .auth().basic("user", "user")
                .when().get("/api/spartans/")
                .then().log().ifValidationFails()
                .statusCode(200);
    }

    @DisplayName("DELETE /spartans/{id} as EDITOR -> expect -> 403 -> Forbidden")
    @Test
    public void test3() {


        given()
                .pathParam("id", 33)
                .log().all()
                .auth().basic("editor", "editor")
                .when().delete("/api/spartans/{id}")
                .then().log().ifValidationFails()
                .statusCode(403);
    }
    @DisplayName("DELETE /spartans/{id} as ADMIN -> expect -> 403 -> Forbidden")
    @Test
    public void test4() {

        given()
                .pathParam("id", 31)
                .log().all()
                .auth().basic("admin", "admin")
                .when().delete("/api/spartans/{id}")
                .then().log().ifValidationFails()
                .statusCode(204);
    }

}
