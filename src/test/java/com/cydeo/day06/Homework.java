package com.cydeo.day06;

import com.cydeo.utilities.ZippopotamTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Homework {

    @Test
    public void task1() {
        given()
                .accept(ContentType.JSON)
                .log().uri()
                .pathParam("postal-code", 22031)
                .and()
                .when().get("http://api.zippopotam.us/us/{postal-code}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .header("Server", is("cloudflare"))
                .header("Report-To", notNullValue());

    }
}
