package com.metrofico.pulsar.api.hud;

import java.util.ArrayList;
import java.util.List;

public final class RichText {
   private RichText() {
   }

   public static List<RichText.Span> parse(String s, int baseColor) {
      List<RichText.Span> out = new ArrayList<>();
      StringBuilder cur = new StringBuilder();
      Integer color = null;
      int i = 0;

      while (i < s.length()) {
         char c = s.charAt(i);
         if (c == '&' && i + 1 < s.length()) {
            char n = s.charAt(i + 1);
            if (n == '&') {
               cur.append('&');
               i += 2;
               continue;
            }

            if (n == 'r') {
               flush(out, cur, color);
               color = null;
               i += 2;
               continue;
            }

            if (n == '#' && i + 8 <= s.length()) {
               String hex = s.substring(i + 2, i + 8);
               if (hex.matches("[0-9A-Fa-f]{6}")) {
                  flush(out, cur, color);
                  color = 0xFF000000 | Integer.parseInt(hex, 16);
                  i += 8;
                  continue;
               }
            }
         }

         cur.append(c);
         i++;
      }

      flush(out, cur, color);
      if (out.isEmpty()) {
         out.add(new RichText.Span("", null));
      }

      return out;
   }

   private static void flush(List<RichText.Span> out, StringBuilder cur, Integer color) {
      if (cur.length() > 0) {
         out.add(new RichText.Span(cur.toString(), color));
         cur.setLength(0);
      }
   }

   public static String plain(String s) {
      StringBuilder sb = new StringBuilder();

      for (RichText.Span span : parse(s, 0)) {
         sb.append(span.text());
      }

      return sb.toString();
   }

   public static int colorOf(RichText.Span span, int base) {
      return span.color() != null ? span.color() : base;
   }

   public record Span(String text, Integer color) {
   }
}
