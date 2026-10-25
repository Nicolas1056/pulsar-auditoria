package com.metrofico.pulsar.launcher.auth;

public record DeviceCode(String userCode, String verificationUri, String deviceCode, int intervalSeconds, long expiresAtEpochMs) {
}
