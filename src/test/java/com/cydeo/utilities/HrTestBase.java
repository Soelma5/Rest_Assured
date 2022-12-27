package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class HrTestBase {
    @BeforeAll
    public static void init(){

        RestAssured.baseURI="http://3.80.111.193:1000/ords/hr";

        //RestAssured.baseURI= "YOURIP:1000/ords/hr";

    }

}

