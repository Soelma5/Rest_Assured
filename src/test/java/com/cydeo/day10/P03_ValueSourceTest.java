package com.cydeo.day10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

public class P03_ValueSourceTest {
    @ParameterizedTest
    @ValueSource(ints = {10,20,30,40,50} )
    public void test1(int number) {

        System.out.println(number);
        Assertions.assertTrue(number<40);

    }


    @ParameterizedTest
    @ValueSource(strings = {"Mike","Rose","Caberly","Kimberly","TJ","King"})
    public void test2(String name) {

        System.out.println("name = " + name);


    }
    // SEND GET REQUEST TO https://api.zippopotam.us/us/{zipcode}
    // with these zipcodes 22030,22031, 22032, 22033 , 22034, 22035, 22036
    // check status code 200

    @ParameterizedTest
    @ValueSource(ints = {22030,22031, 22032, 22033 , 22034, 22035, 22036})
    public void test3(int zipCode){
        System.out.println(zipCode);

        given()
                .pathParam("zipcode", zipCode)
                .when().get("https://api.zippopotam.us/us/{zipcode}").prettyPeek()
                .then()
                .statusCode(200);
    }
}


