package com.restful.booker.crudetest;

import com.restful.booker.model.AuthorisationPoJo;
import com.restful.booker.model.BookingPojo;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostTest {

    @Test
    public void createToken() {

        AuthorisationPoJo authorisationPojo = new AuthorisationPoJo();
        authorisationPojo.setUsername("admin");
        authorisationPojo.setPassword("password123");
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(authorisationPojo)
                .post("https://restful-booker.herokuapp.com/auth");
        response.then().statusCode(200);
    }

    @Test
    public void createBooking() {

        BookingPojo.BookingDates date = new BookingPojo.BookingDates();
        date.setCheckin("2023-07-10");
        date.setCheckout("2023-08-24");
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname("Dev");
        bookingPojo.setLastname("Patel");
        bookingPojo.setTotalprice(1000);
        bookingPojo.setDepositpaid(true);
        bookingPojo.setBookingdates(date);
        bookingPojo.setAdditionalneeds("Breakfast");
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post("https://restful-booker.herokuapp.com/booking");
        response.then().statusCode(200);
    }

}