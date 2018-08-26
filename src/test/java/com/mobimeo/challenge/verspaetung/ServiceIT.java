package com.mobimeo.challenge.verspaetung;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ServiceIT {

    private int testClientPort = 8081;

    @Before
    public void setup() {
        RestAssured.port = testClientPort;
        RestAssured.baseURI = "http://localhost";
    }

    /**
     * Smoke test to check if the application is exposing any data on given endpoint
     */
    @Test
    public void shouldReturnHttpOKForProperRequest() {
        given().accept("application/json")
                .queryParam("timestamp", "10:10:00")
                .queryParam("x", 2)
                .queryParam("y", 9)
        .when()
                .get("/lines")
        .then()
                .statusCode(200)
        .assertThat()
                .body("size()", is(1))
                .body("[0].id",is(1))
                .body("[0].name",is("200"));
    }

    /**
     * Just example test - we can write test based on DataProvider and params
     * to see if the 400 is returned for combinations of wrong params :
     * - wrong timestamp, missing timestamp, timestamp out of range, wrong x ... etc
     */
    @Test
    public void shouldReturnBadRequestWhenParamsAreMissing() {
        given()
                .accept("application/json")
                .queryParam("timestamp", "10:00:00")
                .queryParam("x", 10)
        .when()
                .get("/lines")
        .then()
                .statusCode(400);
    }

    @Test
    public void shouldReturnHttpOkForIsDelayedEndpoint() {
        given()
                .accept("application/json")
        .when()
                .get("/lines/lineA")
        .then()
                .statusCode(200)
                .assertThat()
                .body("isDelayed", equalTo(false));
    }
}
