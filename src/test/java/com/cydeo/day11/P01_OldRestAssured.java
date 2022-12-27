package com.cydeo.day11;

import com.cydeo.utilities.SpartanNewTestBase;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class P01_OldRestAssured extends SpartanNewTestBase {
    @Test
    public void getAllSpartan() {
        given()
                .accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .when().get("/spartans").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]", is(105))// if this fails, the second one will not run   HARD ASSERT
                .body("id[1]", is(106));
    }

    @Test
    public void getAllSpartanOldWay() {

        given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .expect().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]", is(105)) //if this fails, the second one will be running  SOFT ASSERT
                .body("id[1]", is(106))
                .when().get("/spartans");
    }
}
