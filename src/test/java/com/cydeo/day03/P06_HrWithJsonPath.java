package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.reflect.Array.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class P06_HrWithJsonPath extends HrTestBase {

    @DisplayName("GET all countries")
    @Test
    public void test1() {

        Response response = given().get("/countries");
        response.prettyPrint();
        assertEquals(200, response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        System.out.println("jsonPath.getString(\"items\") = " + jsonPath.getString("items"));
        System.out.println("jsonPath.getString(\"items[2].country_name\") = " + jsonPath.getString("items[2].country_name"));
        System.out.println("jsonPath.getString(\"items[2,3].country_name\") = " + jsonPath.getString("items[2,3].country_name"));

        List<String> list = jsonPath.getList("items.findAll{it.region_id==2}.country_name");
        System.out.println(list);



    }
}

