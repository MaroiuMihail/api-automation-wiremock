package com.mihai.api.dto;

public record AuthResponse(String token, String tokenType, int expiresIn) {}
