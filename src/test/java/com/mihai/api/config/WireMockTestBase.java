package com.mihai.api.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public abstract class WireMockTestBase {

    protected static WireMockServer wireMock;

    @BeforeAll
    static void startWireMock() {
        wireMock = new WireMockServer(
                wireMockConfig()
                        .dynamicPort()
                        .usingFilesUnderDirectory("src/test/resources/wiremock")
        );
        wireMock.start();
    }

    @AfterAll
    static void stopWireMock() {
        if (wireMock != null) {
            wireMock.stop();
        }
    }

    protected String baseUrl() {
        return wireMock.baseUrl() + "/api";
    }
}
