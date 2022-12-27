package com.cydeo.day11;

import com.cydeo.utilities.SpartanNewTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import javax.swing.text.AbstractDocument;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class P02_SpartanSpecTest extends SpartanNewTestBase {

    @Test
    public void getAllSpartans() {

        given().log().all().accept(ContentType.JSON)
                .auth().basic("admin", "admin").
                when().get("/spartans").prettyPeek()
                .then().
                statusCode(200)
                .contentType(ContentType.JSON);

    }

    @Test
    public void getAllSpartansWithReqResSpec() {

        given().spec(reqSpec).
                when().get("/spartans").
                then().spec(resSpec);

    }


    @Test
    public void getSingleSpartansWithReqResSpec() {


        given().spec(reqSpec).
                pathParam("id", 112).
                when().get("/spartans/{id}").prettyPeek().
                then().spec(resSpec)
                .body("id", is(112));

    }

    @Test
    public void getSingleSpartanAsUser() {

        given().spec(dynamicReqSpec("user", "user"))
                .pathParam("id", 112).
                when().get("/spartans/{id}").prettyPeek().
                then().spec(dynamicResSpec(404));

    }


    /**
     * Create GET_RBAC.csv
     * username,password,id,statuscode
     * admin,admin,3,200
     * editor,editor,3,200
     * user,user,3,200
     * <p>
     * Create a parameterized test to check RBAC for GET method
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/GET_RBAS.csv", numLinesToSkip = 1)
    public void test1(String username, String password, int id, int statusCode) {
        given().spec(dynamicReqSpec(username, password))
                .pathParam("id", id)
                .when().get("/spartans/{id}")
                .then().spec(dynamicResSpec(statusCode));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/DELETE_RBAS.csv", numLinesToSkip = 1)
    public void test2(String username, String password, int id, int statusCode) {
        given().spec(dynamicReqSpec(username, password))
                .pathParam("id", id)
                .when().delete("/spartans/{id}")
                .then().spec(dynamicResSpec(statusCode));
    }
}
