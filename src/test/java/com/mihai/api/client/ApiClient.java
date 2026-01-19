package com.mihai.api.client;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private final String baseUri;

    public ApiClient(String baseUri) {
        this.baseUri = baseUri;
    }

    protected RequestSpecification req() {
        return given().baseUri(baseUri);
    }
}
