package com.metrofico.pulsar.api.hud;

import java.util.Locale;
import java.util.function.Supplier;

public final class HudLang {
   private static volatile Supplier<String> language = () -> "en_us";

   private HudLang() {
   }

   public static void source(Supplier<String> gameLanguage) {
      language = gameLanguage;
   }

   public static boolean spanish() {
      String code = language.get();
      return code != null && code.toLowerCase(Locale.ROOT).startsWith("es");
   }

   public static String direction(String cardinal) {
      boolean es = spanish();

      return switch (cardinal) {
         case "north" -> (es ? "NORTE" : "NORTH") + " (-Z)";
         case "south" -> (es ? "SUR" : "SOUTH") + " (+Z)";
         case "west" -> (es ? "OESTE" : "WEST") + " (-X)";
         case "east" -> (es ? "ESTE" : "EAST") + " (+X)";
         default -> "";
      };
   }

   public static String left() {
      return spanish() ? "IZQ" : "LEFT";
   }

   public static String right() {
      return spanish() ? "DER" : "RIGHT";
   }

   public static String toggled() {
      return spanish() ? "ACTIVADO" : "TOGGLED";
   }

   public static String noData() {
      return spanish() ? "SIN DATOS" : "NO DATA";
   }
}
