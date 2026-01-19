package com.mihai.api.tests;

import com.mihai.api.client.AuthClient;
import com.mihai.api.client.OrdersClient;
import com.mihai.api.config.WireMockTestBase;
import com.mihai.api.dto.AuthRequest;
import com.mihai.api.dto.CreateOrderRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class OrdersNegativeTests extends WireMockTestBase {

    @Test
    void shouldReturn401WhenCreatingOrderWithoutToken() {
        new OrdersClient(baseUrl())
                .createWithoutAuth(new CreateOrderRequest("SKU-123", 2, "EUR"))
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("schemas/error.schema.json"))
                .body("code", equalTo("AUTH_REQUIRED"));
    }

    @Test
    void shouldReturn400WhenQuantityIsInvalid() {
        String token = new AuthClient(baseUrl())
                .token(new AuthRequest("admin", "secret"))
                .statusCode(200)
                .extract()
                .path("token");

        new OrdersClient(baseUrl())
                .create(token, new CreateOrderRequest("SKU-123", 0, "EUR"))
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("schemas/error.schema.json"))
                .body("code", equalTo("VALIDATION_ERROR"));
    }

    @Test
    void shouldReturn404WhenOrderDoesNotExist() {
        String token = new AuthClient(baseUrl())
                .token(new AuthRequest("admin", "secret"))
                .statusCode(200)
                .extract()
                .path("token");

        new OrdersClient(baseUrl())
                .getById(token, "ord-9999")
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("schemas/error.schema.json"))
                .body("code", equalTo("ORDER_NOT_FOUND"));
    }
}
