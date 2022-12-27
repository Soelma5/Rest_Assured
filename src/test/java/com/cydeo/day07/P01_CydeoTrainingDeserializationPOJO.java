package com.cydeo.day07;

import com.cydeo.pojo.Address;
import com.cydeo.pojo.Company;
import com.cydeo.pojo.Contact;
import com.cydeo.pojo.Student;
import com.cydeo.utilities.CydeoTrainingTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P01_CydeoTrainingDeserializationPOJO extends CydeoTrainingTestBase {

    /*
    Given accept type is application/json
    And path param is 2
    When user send request /student/{id}
    Then status code should be 200
    And content type is application/json;charset=UTF-8

    And verify following
                firstName Mark
                batch 13
                major math
                emailAddress mark@email.com
                companyName Cydeo
                street 777 5th Ave
                zipCode 33222

     */

    @DisplayName("GET info student id = 2")
    @Test
    public void task1() {

            JsonPath jsonPath = given().log().all()
                    .accept(ContentType.JSON)
                    .and()
                    .pathParam("id", 2)
                    .when().get("/student/{id}")
                            .then()
                                    .statusCode(200)
                                            .contentType("application/json")
                                                    .extract().jsonPath();


        Student student = jsonPath.getObject("students[0]", Student.class);
        System.out.println("student = " + student);
        assertEquals("Mark", student.getFirstName());
        assertEquals(13, student.getBatch());
        assertEquals("math", student.getMajor());

        Contact contact = jsonPath.getObject("students[0].contact", Contact.class);
        System.out.println("contact = " + contact);
        assertEquals("mark@email.com", contact.getEmailAddress());

        Company company = jsonPath.getObject("students[0].company", Company.class);
        System.out.println("company = " + company);
        assertEquals("Cydeo", company.getCompanyName());

        Address address = jsonPath.getObject("students[0].company.address", Address.class);
        System.out.println("address = " + address);
        assertEquals("777 5th Ave", address.getStreet());
        assertEquals(33222, address.getZipCode());


    }
    }

