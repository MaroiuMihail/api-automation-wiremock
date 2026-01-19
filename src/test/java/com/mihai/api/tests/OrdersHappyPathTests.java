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

public class OrdersHappyPathTests extends WireMockTestBase {

    @Test
    void shouldCreateOrderWhenAuthorized() {
        String token = new AuthClient(baseUrl())
                .token(new AuthRequest("admin", "secret"))
                .statusCode(200)
                .extract()
                .path("token");


        new OrdersClient(baseUrl())
                .create(token, new CreateOrderRequest("SKU-123", 2, "EUR"))
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("schemas/order-response.schema.json"))
                .body("status", equalTo("CREATED"))
                .body("id", equalTo("ord-1001"));
    }

    @Test
    void shouldGetOrderById() {
        String token = new AuthClient(baseUrl())
                .token(new AuthRequest("admin", "secret"))
                .statusCode(200)
                .extract()
                .path("token");

        new OrdersClient(baseUrl())
                .getById(token, "ord-1001")
                .statusCode(200);
    }

}
