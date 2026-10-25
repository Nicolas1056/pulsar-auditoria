package com.metrofico.pulsar.launcher.daemon;

public record Account(String name, String uuid, String xuid, String skinUrl, long signedInAtEpochMs, String clientId) {
}
