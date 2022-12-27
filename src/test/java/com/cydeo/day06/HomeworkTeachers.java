package com.cydeo.day06;

import com.cydeo.utilities.CydeoTrainingTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class HomeworkTeachers extends CydeoTrainingTestBase {
    /**
     * Create a test called getTeachers
     * 1. Send request to GET /teacher/all
     * 2. log uri to see
     * 3. Get all Json as a map and print out screen all the things with the help of  map
     * System.out.println("====== GET FIRST TEACHER INFO   ======");
     * System.out.println("====== GET FIRST TEACHER NAME  ======");
     * System.out.println("====== GET ALL TEACHER INFO  AS LIST OF MAP======");
     * System.out.println("====== FIRST TEACHER INFO======");
     * System.out.println("====== FIRST TEACHER NAME ======");
     * System.out.println("====== LAST TEACHER NAME  ======");
     */


    @DisplayName("GET All Teachers")
    @Test
    public void task1() {

        JsonPath jsonPath = given()
                .log().uri()
                .when().get("/teacher/all")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().response().jsonPath();
        System.out.println("jsonPath = " + jsonPath);

        Map<Object, Object> firstTeacherInfo = jsonPath.getMap("teachers[0]");
        System.out.println("firstTeacherInfo = " + firstTeacherInfo);

        System.out.println("jsonPath.getMap(\"teachers[0]\").get(\"firstName\") = " + jsonPath.getMap("teachers[0]").get("firstName"));

        List<Map<String, Object>> allTeachers = jsonPath.getList("teachers");
        System.out.println("allTeachers = " + allTeachers);

        System.out.println("allTeachers.get(0).get(\"firstName\") = " + allTeachers.get(0).get("firstName"));

        System.out.println("allTeachers.get(0).get(0) = " + allTeachers.get(0));
        System.out.println("allTeachers.get(allTeachers.size()-1).get(\"lastName\") = " + allTeachers.get(allTeachers.size() - 1).get("lastName"));
    }
}
