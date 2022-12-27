package com.cydeo.day09;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class P02_ResponseTimeTest extends SpartanAuthTestBase {

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans")
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .time(both(greaterThan(500L)).and(lessThan(1000L))).extract().response();// actual response time

        System.out.println("response.getTimeIn(TimeUnit.MILLISECONDS) = " + response.getTimeIn(TimeUnit.MILLISECONDS));
    }
}
