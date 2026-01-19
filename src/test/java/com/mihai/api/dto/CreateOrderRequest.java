package com.mihai.api.dto;

public record CreateOrderRequest(
        String productId,
        int quantity,
        String currency
) {}
