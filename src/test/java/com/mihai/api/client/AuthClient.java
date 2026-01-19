package com.mihai.api.client;

import com.mihai.api.dto.AuthRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class AuthClient extends ApiClient {

    public AuthClient(String baseUri) {
        super(baseUri);
    }

    public ValidatableResponse token(AuthRequest request) {
        return req()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/auth/token")
                .then();
    }
}
