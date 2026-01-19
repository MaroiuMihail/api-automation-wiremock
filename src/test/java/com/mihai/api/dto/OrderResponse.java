package com.mihai.api.dto;

public record OrderResponse(
        String id,
        String status,
        String productId,
        int quantity,
        String currency
) {}
