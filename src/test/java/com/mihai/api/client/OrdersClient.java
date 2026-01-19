package com.mihai.api.client;

import com.mihai.api.dto.CreateOrderRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import com.mihai.api.dto.UpdateOrderStatusRequest;


public class OrdersClient extends ApiClient {

    public OrdersClient(String baseUri) {
        super(baseUri);
    }

    public ValidatableResponse create(String bearerToken, CreateOrderRequest request) {
        return req()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .when()
                .post("/orders")
                .then();
    }

    public ValidatableResponse createWithoutAuth(CreateOrderRequest request) {
        return req()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/orders")
                .then();
    }

    public ValidatableResponse getById(String bearerToken, String orderId) {
        return req()
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .get("/orders/{id}", orderId)
                .then();
    }

    public ValidatableResponse updateStatus(String token, String orderId, UpdateOrderStatusRequest request) {
        return req()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .patch("/orders/{id}", orderId)
                .then();
    }



}
