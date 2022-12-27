package com.cydeo.day04;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P01_HRWithJsonPath extends HrTestBase {

    @DisplayName("Get All /employee?limit=200 --> JSONPATH")
    @Test
    public void test1(){
        Response response = given()
                .accept(ContentType.JSON)
                .and()
                .queryParam("limit", 200)
                .when().get("/employees");

       // response.prettyPrint();

        assertEquals(200, response.statusCode());
        JsonPath jsonPath = response.jsonPath();
       List<String> list1 =  jsonPath.getList("items.email");
        System.out.println(list1);

        List<String> emailsIT = jsonPath.getList("items.findAll{it.job_id == 'IT_PROG'}.email");
        System.out.println("emailsIT = " + emailsIT);
        System.out.println("emailsIT.size() = " + emailsIT.size());

        List<String> allEmployeesSalaryMoreThan10 = jsonPath.getList("items.findAll{it.salary >= 10000}.first_name");
        System.out.println("allEmployeesSalaryMoreThan10 = " + allEmployeesSalaryMoreThan10);


        System.out.println("jsonPath.getString(\"items.max{it.salary}\") = " + jsonPath.getString("items.max{it.salary}"));

        System.out.println("jsonPath.getString(\"items.max{it.salary}.first_name\") = " + jsonPath.getString("items.max{it.salary}.first_name"));
    }
    }



