package service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static service.dataForTests.bookDto;
import static service.dataForTests.incorrectBook;
import static service.dataForTests.updatedBookDto;
import static service.dataForTests.url;

import javax.ws.rs.core.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pl.lodz.p.pas.model.resource.Book;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RentableItemServiceTest {


    @Test
    @Order(1)
    void createBook() {

        given()
                .contentType("application/json")
                .body(bookDto)
                .when()
                .post(url + "rentable-item/book")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        given()
                .when()
                .get(url + "rentable-item")
                .then()
                .body("size()", is(1))
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response().as(Book[].class);
    }

    @Order(2)
    @Test
    void cannotCreateRentableItemWithDuplicatedSerialNumber() {

        given()
                .contentType("application/json")
                .body(bookDto)
                .when()
                .post(url + "rentable-item/book")
                .then()
                .statusCode(Response.Status.CONFLICT.getStatusCode());

        given()
                .when()
                .get(url + "rentable-item")
                .then()
                .body("size()", is(1))
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response().as(Book[].class);
    }

    @Test
    @Order(3)
    void cannotCreateRentableItemWithIncorrectTitle() {

        given()
                .contentType("application/json")
                .body(incorrectBook)
                .when()
                .post(url + "rentable-item/book")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @Order(3)
    void updateBook() {

        given()
                .when()
                .get(url + "rentable-item/1")
                .then()
                .body("publishingHouse", not(equalTo(updatedBookDto.getPublishingHouse())))
                .statusCode(Response.Status.OK.getStatusCode());

        given()
                .contentType("application/json")
                .body(updatedBookDto)
                .when()
                .put(url + "rentable-item/book/1")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());


        given()
                .when()
                .get(url + "rentable-item/1")
                .then()
                .body("publishingHouse", is(updatedBookDto.getPublishingHouse()));


    }


    @Test
    @Order(4)
    void deleteBook() {

        given()
                .contentType("application/json")
                .when()
                .delete(url + "rentable-item/1")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given()
                .when()
                .get(url + "rentable-item")
                .then()
                .body("size()", is(0))
                .statusCode(Response.Status.OK.getStatusCode());

    }


    @Test
    @Order(5)
    void return404StatusCodeWhenRentableItemIsNotPresent() {
        given()
                .when()
                .get(url + "rentable-item/100")
                .then()
                .statusCode(404);
    }

}