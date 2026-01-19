package com.mihai.api.tests;

import com.mihai.api.client.AuthClient;
import com.mihai.api.client.OrdersClient;
import com.mihai.api.config.WireMockTestBase;
import com.mihai.api.dto.AuthRequest;
import com.mihai.api.dto.UpdateOrderStatusRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class OrdersPatchTests extends WireMockTestBase {

    @Test
    void shouldUpdateOrderStatus() {
        String token = new AuthClient(baseUrl())
                .token(new AuthRequest("admin", "secret"))
                .statusCode(200)
                .extract()
                .path("token");

        new OrdersClient(baseUrl())
                .updateStatus(token, "ord-1001", new UpdateOrderStatusRequest("SHIPPED"))
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("SHIPPED"));
    }

    @Test
    void shouldReturn409ForInvalidStatusTransition() {
        String token = new AuthClient(baseUrl())
                .token(new AuthRequest("admin", "secret"))
                .statusCode(200)
                .extract()
                .path("token");

        new OrdersClient(baseUrl())
                .updateStatus(token, "ord-1001", new UpdateOrderStatusRequest("CREATED"))
                .statusCode(409)
                .body("code", equalTo("INVALID_STATUS_TRANSITION"));
    }
}
