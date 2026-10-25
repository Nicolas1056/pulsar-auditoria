package com.metrofico.pulsar.launcher.rules;

import java.util.Locale;

public record Platform(String os, String arch, String version) {
   public static final String WINDOWS = "windows";
   public static final String OSX = "osx";
   public static final String LINUX = "linux";

   public static Platform current() {
      return new Platform(detectOs(System.getProperty("os.name")), normalizeArch(System.getProperty("os.arch")), System.getProperty("os.version"));
   }

   public boolean is64Bit() {
      return !"x86".equals(this.arch);
   }

   static String detectOs(String osName) {
      String n = osName.toLowerCase(Locale.ROOT);
      if (n.contains("win")) {
         return "windows";
      } else {
         return !n.contains("mac") && !n.contains("darwin") ? "linux" : "osx";
      }
   }

   static String normalizeArch(String osArch) {
      String a = osArch.toLowerCase(Locale.ROOT);
      if (a.equals("amd64") || a.equals("x86_64")) {
         return "x86_64";
      } else if (a.equals("aarch64") || a.equals("arm64")) {
         return "arm64";
      } else {
         return !a.equals("x86") && !a.equals("i386") && !a.equals("i686") ? a : "x86";
      }
   }
}
