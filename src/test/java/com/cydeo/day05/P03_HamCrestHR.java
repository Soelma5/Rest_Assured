package com.cydeo.day05;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class P03_HamCrestHR  extends HrTestBase {
    /*
                   Given accept type is Json
                   And parameters: q = {"job_id":"IT_PROG"}
                   When users sends a GET request to "/employees"
                   Then status code is 200
                   And Content type is application/json
                   Verify
                       - each employees has manager
                       - each employees working as IT_PROG
                       - each of them getting salary greater than 3000
                       - first names are .... (find proper method to check list against list)
                       - emails without checking order (provide emails in different order,just make sure it has same emails)
              */
    @Test
    public void test1() {

        /*  EMAILS
            AHUNOLD
            BERNST
            DAUSTIN
            VPATABAL
            DLORENTZ
         */

        List<String> names = Arrays.asList("Alexander","Bruce","David","Valli","Diana");


        given().accept(ContentType.JSON)
                .queryParam("q","{\"job_id\": \"IT_PROG\"}").
                when().get("/employees").prettyPeek().
                then()
                .statusCode(200)
                .contentType("application/json")
                .body("items.manager_id",everyItem(notNullValue()))
                .body("items.job_id",everyItem(equalTo("IT_PROG")))
                .body("items.salary",everyItem(greaterThan(3000)))
                .body("items.first_name",equalTo(names))
                .body("items.email",containsInAnyOrder("DAUSTIN","AHUNOLD","BERNST","VPATABAL","DLORENTZ")) ;
        
    }

    /*
      Given
               accept type is application/json
       When
               user sends get request to /regions
       Then
               response status code must be 200
               verify Date has values
               first region name is Europe
               first region id is 1
               four regions we have
               region names are not null
               Regions name should be same order as "Europe","Americas","Asia","Middle East and Africa"
               region ids needs to be 1,2,3,4

               print all the regions names
               ...
               ..
               .
    */
    @Test
    public void test2() {
        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .when().get("/regions").prettyPeek()
                .then()
                .statusCode(is(200))
                .contentType(is("application/json"))
                .header("Date", notNullValue())
                .body("items[0].region_name", is("Europe"))
                .body("items[0].region_id", is(1))
                .body("items", hasSize(4))
                .body("items.region_name", everyItem(notNullValue()))
                .body("items.region_id", containsInRelativeOrder(1, 2, 3, 4))
        .body("items.region_name", containsInRelativeOrder("Europe","Americas","Asia","Middle East and Africa"))
                .extract().jsonPath();
        
        // print all the regions names
        // assume that we are going to verify region names API vs Database

        // get all region names from API response
        List<String> allRegionNames = jsonPath.getList("items.region_name");
        System.out.println("allRegionNames = " + allRegionNames);

        // get all region names from Database

        // compare


    }
}

