package com.metrofico.pulsar.launcher.auth;

public record MsaToken(String accessToken, String refreshToken, long expiresAtEpochMs) {
}
