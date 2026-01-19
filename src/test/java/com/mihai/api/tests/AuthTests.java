package com.mihai.api.tests;

import com.mihai.api.client.AuthClient;
import com.mihai.api.config.WireMockTestBase;
import com.mihai.api.dto.AuthRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class AuthTests extends WireMockTestBase {

    @Test
    void shouldReturnTokenForValidCredentials() {
        AuthClient auth = new AuthClient(baseUrl());

        auth.token(new AuthRequest("admin", "secret"))
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("schemas/auth-response.schema.json"))
                .body("tokenType", equalTo("Bearer"))
                .body("token", not(blankOrNullString()))
                .body("expiresIn", greaterThanOrEqualTo(60));
    }

    @Test
    void shouldReturn401ForInvalidCredentials() {
        AuthClient auth = new AuthClient(baseUrl());

        auth.token(new AuthRequest("admin", "wrong"))
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("code", equalTo("AUTH_INVALID"))
                .body("message", not(blankOrNullString()));
    }
}
