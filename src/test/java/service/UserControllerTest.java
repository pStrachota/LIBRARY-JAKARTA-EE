package service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static service.dataForTests.clientDto;
import static service.dataForTests.incorrectClient;
import static service.dataForTests.updatedClientDto;
import static service.dataForTests.url;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Stateless
class UserControllerTest {

    @Test
    @Order(1)
    void createClient() {

        given()
                .contentType("application/json")
                .body(clientDto)
                .when()
                .post(url + "user/client")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        given()
                .when()
                .get(url + "user")
                .then()
                .body("size()", is(1))
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response().prettyPrint();
    }

    @Test
    @Order(2)
    void cannotCreateUserWithDuplicatedLogin() {

        given()
                .contentType("application/json")
                .body(clientDto)
                .when()
                .post(url + "user/client")
                .then()
                .statusCode(Response.Status.CONFLICT.getStatusCode())
                .extract().response().prettyPrint();

        given()
                .when()
                .get(url + "user")
                .then()
                .body("size()", is(1))
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response().prettyPrint();
    }

    @Test
    @Order(3)
    void cannotCreateUserWithIncorrectData() {

        given()
                .contentType("application/json")
                .body(incorrectClient)
                .when()
                .post(url + "user/client")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());

    }

    //wiem dlaczego to przechodzi, bo
    //test jest nie popraweny
    //nie 'docieram' do bazy danych...
    @Test
    @Order(4)
    void updateClient() {

        given()
                .when()
                .get(url + "user/1")
                .then()
                .body("name", is(not(equalTo(updatedClientDto.getName()))))
                .statusCode(Response.Status.OK.getStatusCode());


        given()
                .contentType("application/json")
                .body(updatedClientDto)
                .when()
                .put(url + "user/client/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

        given()
                .when()
                .get(url + "user/1")
                .then()
                .body("name", not("Adam"))
                .statusCode(Response.Status.OK.getStatusCode());

    }

    @Test
    @Order(6)
    void deactivateClient() {

        given()
                .when()
                .get(url + "user/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("active", is(true));

        given()
                .contentType("application/json")
                .when()
                .patch(url + "user/deactivate/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

        given()
                .when()
                .get(url + "user/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("active", is(false));
    }

    @Test
    @Order(7)
    void findByLoginTest() {

        given()
                .contentType("application/json")
                .when()
                .get(url + "user/login-search?login=" + updatedClientDto.getLogin())
                .then()
                .body("[0].login", is(updatedClientDto.getLogin()))
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response().prettyPrint();
    }

    @Test
    @Order(8)
    void deleteClient() {

        given()
                .when()
                .delete(url + "user/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

        given()
                .when()
                .get(url + "user")
                .then()
                .body("size()", is(0))
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().response().prettyPrint();
    }

    @Test
    @Order(9)
    void return404StatusCodeWhenUserIsNotPresent() {
        given()
                .when()
                .get(url + "user/100")
                .then()
                .statusCode(404);
    }


}