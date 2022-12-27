package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class P01_SpartanGetRequest {

    String url = "http://54.227.119.244:8000";

    /**
     *
     * Given accept content type  as application/json
     * When user sends GET request api/spartans endpoint
     * Then status code should be 200
     * And Content type should be application/json
     */

    @DisplayName("GET All Spartans")
    @Test
    public void getAllSpartans(){

        Response response = RestAssured
                .given()
                .accept(ContentType.JSON)
                .when().get(url + "/api/spartans");
        response.prettyPrint();


        // how to get status code
        int actualStatusCode = response.statusCode();
        Assertions.assertEquals(200, actualStatusCode);

        //how can we get ContentType
        String actualContentType = response.contentType();
        Assertions.assertEquals("application/json", actualContentType);

        // get header info
        String connection = response.header("Connection");
        System.out.println("connection = " + connection);

        // get content type with header
        System.out.println("response.header(\"Content_Type\") = " + response.header("Content-Type"));

        // can we get connection() same as contentType() insteading of using header?
        // A --> Rest Assured created couple of method for common usage.
        // statusCode() contentType() methods are specificly created by them.So there is connection method

        // get date header
        System.out.println("response.header(\"Date\") = " + response.header("Date"));

        //how can we verify date is exist ?
        boolean isDateExist = response.headers().hasHeaderWithName("Date");
        Assertions.assertTrue((isDateExist));


    }

    /**
     *
     * Given accept content type  as application/json
     * When user sends GET request api/spartans/3 endpoint
     * Then status code should be 200
     * And Content type should be application/json
     * And response body needs to contain Fidole
     */

    @DisplayName("GET Single Spartan")
    @Test
    public void getSpartan(){

        Response response = RestAssured
                .given()
                .accept("application/json")
                .when().get(url + "/api/spartans/3");
        response.prettyPrint();

        // how to get status code
        int actualStatusCode = response.statusCode();
        Assertions.assertEquals(200, actualStatusCode);

        // how to get content type
        String actualContentType = response.header("Content-type");
        Assertions.assertEquals("application/json", actualContentType);
        Assertions.assertEquals(ContentType.JSON.toString(), actualContentType);
        //ContentType.JSON.toString() --> it makes enum to String to be able to use in assertions

        // verify body contains Fidole
        Assertions.assertTrue(response.body().asString().contains("Fidole"));

        // what if we have typo while we are getting header
        // if we don't have related header or if we have typo it will return NULL
        System.out.println("response.header(\"KeepAlive\") = " + response.header("KeepAlive"));


        System.out.println("response.headers().getValue(\"Date\") = " + response.headers().getValue("Date"));
    }

    @DisplayName("GET Hello Sparta")
    @Test
    public void helloSpartan(){
        Response response = RestAssured
                .given()
                .accept("text/plain")
                .when().get(url + "/api/hello");
        response.prettyPrint();

        int actualStatusCode = response.statusCode();
        Assertions.assertEquals(200, actualStatusCode);

        String actualContentType = response.header("Content-type");
        Assertions.assertEquals("text/plain;charset=UTF-8", actualContentType);

        System.out.println("response.headers().getValue(\"Date\") = " + response.headers().getValue("Date"));

        String length = response.headers().getValue("Content-length");
        Assertions.assertEquals("17", length);

        Assertions.assertEquals("Hello from Sparta", response.body().asString());

    }
}
