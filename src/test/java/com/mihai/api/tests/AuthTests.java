package com.mihai.api.tests;

import com.mihai.api.config.WireMockTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class AuthTests extends WireMockTestBase {

    @Test
    void shouldReturnTokenForValidCredentials() {
        given()
                .baseUri(baseUrl())
                .contentType(ContentType.JSON)
                .body("""
                      { "username": "admin", "password": "secret" }
                      """)
                .when()
                .post("/auth/token")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("schemas/auth-response.schema.json"))
                .body("tokenType", equalTo("Bearer"))
                .body("token", not(blankOrNullString()))
                .body("expiresIn", greaterThanOrEqualTo(60));
    }

    @Test
    void shouldReturn401ForInvalidCredentials() {
        given()
                .baseUri(baseUrl())
                .contentType(ContentType.JSON)
                .body("""
                      { "username": "admin", "password": "wrong" }
                      """)
                .when()
                .post("/auth/token")
                .then()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("code", equalTo("AUTH_INVALID"))
                .body("message", not(blankOrNullString()));
    }
}
