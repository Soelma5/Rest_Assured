package com.cydeo.day09;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class P01_SpartanXMLTest extends SpartanAuthTestBase {

    /**
     * Given accept type is application/xml
     * When send the request /api/spartans
     * Then status code is 200
     * And content type is application/xml
     * print firstname
     * .....
     * ...
     */

    @DisplayName("GET /api/spartans with using XMLPath")
    @Test
    public void test1() {
        given().accept(ContentType.XML)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.XML)
                .body("List.item[0].name", is("John Dan"))
                .body("List.item[0].gender", is("Male"));

    }
    @DisplayName("GET /api/spartans with using XMLPath")
    @Test
    public void test2() {
        Response response = given().accept(ContentType.XML)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans").prettyPeek();

        // GET response as XML format and save into XMLPath
        XmlPath xmlPath = response.xmlPath();

        // get first spartan name
        String name = xmlPath.get("List.item[0].name");
        System.out.println("name = " + name);

        String name1 = xmlPath.getString("List.item[0].name");
        System.out.println("name1 = " + name1);

        // get third spartan name
        String name3 = xmlPath.getString("List.item[2].name");
        System.out.println("name3 = " + name3);

        // get last spartans name
        String lastName = xmlPath.getString("List.item[-1].name");
        System.out.println("lastName = " + lastName);

        // get all spartans name
        List<String> list = xmlPath.getList("List.item.name");
        System.out.println("list = " + list);

        // get how many spartans we have
        System.out.println("list.size() = " + list.size());


    }
}
