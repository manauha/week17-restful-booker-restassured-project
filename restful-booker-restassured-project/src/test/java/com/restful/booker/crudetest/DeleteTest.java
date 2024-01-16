package com.restful.booker.crudetest;

import com.restful.booker.model.AuthorisationPoJo;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteTest {

    static ValidatableResponse response;
    static String token;
    static int id = TestUtils.getRandomValue();

    @BeforeClass
    public static void inIt() {
        AuthorisationPoJo authorisationPojo = new AuthorisationPoJo();
        authorisationPojo.setUsername("admin");
        authorisationPojo.setPassword("password123");
        token = given()
                .header("Content-Type", "application/json")
                .when()
                .body(authorisationPojo)
                .post("https://restful-booker.herokuapp.com/auth")
                .then().statusCode(200).extract().path("token");
    }

    @Test
    public void deleteSingleBookingID() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .when().delete("https://restful-booker.herokuapp.com/booking/"+id);
        response.then().statusCode(201);
    }
}
