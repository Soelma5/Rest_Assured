package com.cydeo.day08;

import com.cydeo.utilities.BookItTestBase;
import com.cydeo.utilities.BookItUtils;
import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

public class P02_BookITTest extends BookItTestBase {


    String email = "lfinnisz@yolasite.com";
    String password = "lissiefinnis";
    String accessToken = BookItUtils.getToken(email, password);
    //"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTMxMiIsImF1ZCI6InN0dWRlbnQtdGVhbS1sZWFkZXIifQ.ZIHJuDh19eLga3bLP7udnvNtEA0DM_W1H67ah2Zu3Lc";

    @DisplayName("GET /api/campuses")
    @Test
    public void task1() {

        given()
                .accept(ContentType.JSON)
                .header("Authorization", accessToken)
                .when().get("/api/campuses").prettyPeek()
                .then().statusCode(200);


        assertThat(accessToken, not(emptyOrNullString()));


    }

    @DisplayName("GET /api/users/me")
    @Test
    public void task2() {

        given()
                .accept(ContentType.JSON)
                .header("Authorization", accessToken)
                .when().get("/api/users/me").prettyPeek()
                .then().statusCode(200);

        assertThat(accessToken, not(emptyOrNullString()));
    }
}
// 12e81ae8a9a840948da3a0115135e2c6