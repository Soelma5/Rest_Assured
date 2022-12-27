package com.cydeo.day04;

import com.cydeo.utilities.CydeoTrainingTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P02_CydeoTraining extends CydeoTrainingTestBase {

    @DisplayName("Get All Students")
    @Test
    public void test1() {
        Response response = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .pathParam("studentId", 2)
                .when().get("/students/{studentId}");
        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());

        JsonPath jsonPath = response.jsonPath();
        assertEquals("Mark", jsonPath.getString("firstName"));
        assertEquals("math", jsonPath.getString("major"));
        System.out.println("jsonPath.getString(\"email\") = " + jsonPath.getString("contact.emailAddress"));
        System.out.println("jsonPath.getString(\"company.address\") = " + jsonPath.getString("company.address"));
        System.out.println("jsonPath.getString(\"company.address.street\") = " + jsonPath.getString("company.address.street"));

    }
}
