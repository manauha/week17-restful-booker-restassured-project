package com.restful.booker.crudetest;

import com.restful.booker.model.AuthorisationPoJo;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.model.PatchBookingPojo;
import com.restful.booker.model.UpdateBookingPojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CRUDTest {

    static int id;
    static String token; //= "Basic YWRtaW46cGFzc3dvcmQxMjM=";

    @Test
    public void test01(){
        AuthorisationPoJo authPojo = new AuthorisationPoJo();
        authPojo.setUsername("admin");
        authPojo.setPassword("password123");

        token = given()
                .contentType(ContentType.JSON)
                .when()
                .body(authPojo)
                .post("https://restful-booker.herokuapp.com/auth")
        .then().statusCode(200).extract().path("token");

        //response.prettyPrint();
        //response.then().statusCode(200);

    }

    @Test
    public void test02() {
        BookingPojo.BookingDates date = new BookingPojo.BookingDates();
        date.setCheckin("2024-07-10");
        date.setCheckout("2024-08-27");
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname("Neil");
        bookingPojo.setLastname("Cooper");
        bookingPojo.setTotalprice(900);
        bookingPojo.setDepositpaid(true);
        bookingPojo.setBookingdates(date);
        bookingPojo.setAdditionalneeds("Wheelchair Access");
        id = given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post("https://restful-booker.herokuapp.com/booking")
                .then().statusCode(200).extract().path("bookingid");
    }

    @Test
    public void test03() {
        PatchBookingPojo patchBookingPojo = new PatchBookingPojo();
        patchBookingPojo.setAdditionalneeds("Lunch");
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(patchBookingPojo)
                .when().patch("https://restful-booker.herokuapp.com/booking/" + id);
        response.then().statusCode(200);
    }

    @Test
    public void test04() {
        UpdateBookingPojo.BookingDates date = new UpdateBookingPojo.BookingDates();
        date.setCheckin("2024-06-02");
        date.setCheckout("2024-07-08");
        UpdateBookingPojo updateBookingPojo = new UpdateBookingPojo();
        updateBookingPojo.setFirstname("Neil");
        updateBookingPojo.setLastname("Cooper");
        updateBookingPojo.setTotalprice(990);
        updateBookingPojo.setDepositpaid(true);
        updateBookingPojo.setBookingdates(date);
        updateBookingPojo.setAdditionalneeds("Breakfast and Lunch");
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(updateBookingPojo)
                .when().put("https://restful-booker.herokuapp.com/booking/" + id);
        response.then().statusCode(200);
    }

    @Test
    public void test05() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .when().delete("https://restful-booker.herokuapp.com/booking/" + id);
        response.then().statusCode(201);
    }
}
