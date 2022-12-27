package com.cydeo.day10;

import com.cydeo.utilities.ExcelUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class P08_BookItLoginDDTTest {


    //create a method to read bookitqa3 excel file information (QA3 Sheet)
    //use those information as an email and password to send a get request to /sign endpoint
    //verify you got 200 for each request
    //print accessToken for each request

    @ParameterizedTest
    @MethodSource("getBookItData")
    @Test
    public void test1(Map<String, String> userInfo) {
        given()
                .pathParam("email", userInfo.get("email"))
                .pathParam("password", userInfo.get("password"))
                .when().get("sign")
                .then()
                .statusCode(200);
    }


        public static List<Map<String, String>> getBookItData () {
            ExcelUtil bookIt = new ExcelUtil("src/test/resources/BookItQa3.xlsx", "QA3");
            return bookIt.getDataList();

        }
    }
