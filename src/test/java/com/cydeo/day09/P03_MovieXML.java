package com.cydeo.day09;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class P03_MovieXML {

    @Test
    public void test1(){
       Response response = given()
                .queryParam("t", "Superman")
                .queryParam("r", "xml")
                .queryParam("apikey", "81815fe6")
                .when().get("http://www.omdbapi.com").prettyPeek();
        XmlPath xmlPath = response.xmlPath();

        // get attribute
        System.out.println("xmlPath.getString(\"root.movie.@year\") = " + xmlPath.getString("root.movie.@year"));
        System.out.println("xmlPath.getString(\"root.movie.@title\") = " + xmlPath.getString("root.movie.@title"));

        System.out.println("xmlPath.getString(\"root.movie.@genre\") = " + xmlPath.getString("root.movie.@genre"));

        System.out.println("xmlPath.getString(\"root.movie.@writer\") = " + xmlPath.getString("root.movie.@writer"));
    }
}
