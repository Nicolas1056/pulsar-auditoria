package com.metrofico.pulsar.launcher.runtime;

public record RuntimeRelease(int major, String releaseName, String fileName, String url, String sha256, long size, ArchiveExtractor.Format format) {
}
