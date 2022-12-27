package com.cydeo.day11;

import com.cydeo.utilities.BookItTestBase;
import com.cydeo.utilities.BookItUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class P03_BookitSpecTest extends BookItTestBase {

    @Test
    public void test1() {
        given().spec(BookItUtils.getReqSpec("teacher"))
                .when().get("/api/users/me").prettyPeek()
                .then().spec(BookItUtils.getResSpec(200))
                .body("firstName", is("Barbabas"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/userInfo.csv", numLinesToSkip = 1)
    public void test1(String role, String firstName) {
        given().spec(BookItUtils.getReqSpec(role))
                .when().get("/api/users/me").prettyPeek()
                .then().spec(BookItUtils.getResSpec(200))
                .body("firstName", is(firstName));
    }
}
