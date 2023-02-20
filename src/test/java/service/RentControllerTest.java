package service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static service.dataForTests.adminDto;
import static service.dataForTests.bookDto;
import static service.dataForTests.clientDto;
import static service.dataForTests.incorrectUserRentDto;
import static service.dataForTests.rentDto;
import static service.dataForTests.url;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RentControllerTest {


    @BeforeAll
    static void init() {

        given()
                .contentType("application/json")
                .body(clientDto)
                .when()
                .post(url + "user/client");

        given()
                .contentType("application/json")
                .body(adminDto)
                .when()
                .post(url + "user/admin");


        given()
                .contentType("application/json")
                .body(bookDto)
                .when()
                .post(url + "rentable-item/book");

    }

    @AfterAll
    static void cleanUp() {
        given()
                .when()
                .delete(url + "user/3");
        given()
                .when()
                .delete(url + "user/4");
        given()
                .when()
                .delete(url + "rentable-item/3");
    }


    @Test
    @Transactional
    @Order(1)
    void createRent() {

        given()
                .contentType("application/json")
                .body(rentDto)
                .when()
                .post(url + "rent")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .extract().response().prettyPrint();

        given()
                .when()
                .get(url + "rent")
                .then()
                .body("size()", is(1))
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response().prettyPrint();
    }

    @Test
    @Order(3)
    void onlyClientCanCreateRent() {

        given()
                .contentType("application/json")
                .body(incorrectUserRentDto)
                .when()
                .post(url + "rent")
                .then()
                .statusCode(400)
                .extract().response().prettyPrint();

        given()
                .when()
                .get(url + "rent")
                .then()
                .body("size()", is(1))
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response().prettyPrint();
    }

    @Test
    @Order(3)
    void cannotCreateRentWithRentedRentableItem() {

        given()
                .when()
                .get(url + "rentable-item")
                .then()
                .body("[0].available", is(false))
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response().prettyPrint();

        given()
                .contentType("application/json")
                .body(rentDto)
                .when()
                .post(url + "rent")
                .then()
                .statusCode(400)
                .extract().response().prettyPrint();

    }

    @Test
    @Order(4)
    void endRent() {

        given()
                .when()
                .get(url + "rent/1")
                .then()
                .body("ended", is(false));

        given()
                .contentType("application/json")
                .when()
                .delete(url + "rent/1")
                .then()
                .extract().response().prettyPrint();

        given()
                .when()
                .get(url + "rent/1")
                .then()
                .body("ended", is(true));
    }

    @Test
    @Order(5)
    void cannotEndAlreadyEndedRent() {

        given()
                .contentType("application/json")
                .when()
                .delete(url + "rent/1")
                .then()
                .statusCode(400);
    }
}