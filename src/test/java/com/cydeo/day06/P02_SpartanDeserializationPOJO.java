package com.cydeo.day06;

import com.cydeo.pojo.Search;
import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class P02_SpartanDeserializationPOJO extends SpartanTestBase {

    @DisplayName("GET Single Spartan for deserialization to POJO")
    @Test
    public void test1() {

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("/api/spartans/{id}").prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();



        /*
        {
            "id": 15,
            "name": "Meta";
            "gender": "Female",
            "phone": 1938695106
        }
         */

        // RESPONSE
        Spartan spartan = response.as(Spartan.class);
        System.out.println(spartan);
        System.out.println(spartan.getId());
        System.out.println(spartan.getName());
        System.out.println(spartan.getGender());
        System.out.println(spartan.getPhone());


        // JSONPATH

        JsonPath jsonPath = response.jsonPath();
        Spartan sp = jsonPath.getObject("", Spartan.class);
        System.out.println("sp.getId() = " + sp.getId());
        System.out.println("sp.getName() = " + sp.getName());
        System.out.println("sp.getGender() = " + sp.getGender());
        System.out.println("sp.getPhone() = " + sp.getPhone());
    }

    @DisplayName("GET Spartans from search endpoint for deserialization to POJO")
    @Test
    public void test2() {

        Response response = given()
                .accept(ContentType.JSON)
                .when().get("/api/spartans/search").prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();

        // RESPONSE


        //JSONPATH
        JsonPath jsonPath = response.jsonPath();
        System.out.println("jsonPath.getObject(\"content[0]\", Spartan.class) = " + jsonPath.getObject("content[0]", Spartan.class));

    }

    @DisplayName("GET Spartans from search endpoint for deserialization to SEARCH CLASS")
    @Test
    public void test3() {

        Response response = given()
                .accept(ContentType.JSON)
                .when().get("/api/spartans/search").prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();

        // RESPONSE
        Search search1 = response.as(Search.class);
        System.out.println("search1.getTotalElement() = " + search1.getTotalElement());
        System.out.println("search1.getContent().get(0) = " + search1.getContent().get(0));
        System.out.println("search1.getContent().get(0).getName() = " + search1.getContent().get(0).getName());


        //JSONPATH
        JsonPath jp = response.jsonPath();
        Search search = jp.getObject("", Search.class);
        System.out.println("search = " + search.getTotalElement());
        System.out.println("search.getContent().get(0) = " + search.getContent().get(0));
        System.out.println("search.getContent().get(0).getId() = " + search.getContent().get(0).getId());

    }

    @DisplayName("GET Spartans from search endpoint for deserialization to List of Spartan class")
    @Test
    public void test4() {

        Response response = given()
                .accept(ContentType.JSON)
                .when().get("/api/spartans/search")
                .then()
                .statusCode(200)
                .extract().response();

        // RESPONSE
        JsonPath jsonPath = response.jsonPath();
        List<Spartan> allSpartans = jsonPath.getList("content", Spartan.class);
        System.out.println("allSpartans = " + allSpartans);

        for(Spartan each : allSpartans){
            System.out.println("each = " + each);
            System.out.println("allSpartans.get(0) = " + allSpartans.get(0));
            System.out.println("allSpartans.get(0).getName() = " + allSpartans.get(0).getName());
        }


    }
}
