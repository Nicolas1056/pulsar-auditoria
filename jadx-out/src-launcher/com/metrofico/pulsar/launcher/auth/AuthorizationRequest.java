package com.metrofico.pulsar.launcher.auth;

public record AuthorizationRequest(String authorizeUrl, String redirectUri, String state) {
}
