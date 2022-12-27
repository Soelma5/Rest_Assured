package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class P04_HrWithResponsePath extends HrTestBase {

    @DisplayName("GET request to countries wiht using Response Path")
    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":2}").
                when().get("/countries");

        System.out.println("response.path(\"limit\") = " + response.path("limit"));
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));
        System.out.println("response.path(\"items[1].country_id\") = " + response.path("items[1].country_id"));
        System.out.println("response.path(\"items[3].country_name\") = " + response.path("items[3].country_name"));

        System.out.println("response.path(\"items[3].links[0].href\") = " + response.path("items[3].links[0].href"));

        List<String> allCountryNames = response.path("items.country_name");
        System.out.println(allCountryNames);

        List<Integer> allRegionsId = response.path("items.region_id");
        for(Integer eachRegionId : allRegionsId){
            System.out.println("each region ID = "  +eachRegionId);
          assertEquals(2, eachRegionId);
        }

        assertTrue(allRegionsId.stream().allMatch(each->each==2));

    }

    /*
        Send a GET request to /employees endpoint to see only job_id = IT_PROG
        Query Param
            q = {"job_id":"IT_PROG"}
        Then assert all job_ids are IT_PROG
     */

    @DisplayName("GET all job_ids are IT_PROG")
    @Test
    public void test2(){
    Response response = given()
        .accept(ContentType.JSON)
        .and()
        .queryParam("q","{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

         response.prettyPrint();
        List<String> allJobIds = response.path("items.job_id");
        assertTrue(allJobIds.stream().allMatch(each->each.equals("IT_PROG")));
    }

    }

