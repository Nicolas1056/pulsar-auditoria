package com.metrofico.pulsar.api.emote;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public enum Easing {
   LINEAR,
   STEP,
   CATMULLROM,
   IN_SINE,
   OUT_SINE,
   IN_OUT_SINE,
   IN_QUAD,
   OUT_QUAD,
   IN_OUT_QUAD,
   IN_CUBIC,
   OUT_CUBIC,
   IN_OUT_CUBIC,
   IN_QUART,
   OUT_QUART,
   IN_OUT_QUART,
   IN_QUINT,
   OUT_QUINT,
   IN_OUT_QUINT,
   IN_EXPO,
   OUT_EXPO,
   IN_OUT_EXPO,
   IN_CIRC,
   OUT_CIRC,
   IN_OUT_CIRC,
   IN_BACK,
   OUT_BACK,
   IN_OUT_BACK,
   IN_ELASTIC,
   OUT_ELASTIC,
   IN_OUT_ELASTIC,
   IN_BOUNCE,
   OUT_BOUNCE,
   IN_OUT_BOUNCE;

   private static final Map<String, Easing> BY_NAME = new TreeMap<>();
   private static final double C1 = 1.70158;
   private static final double C2 = 2.5949095;
   private static final double C3 = 2.70158;
   private static final double C4 = Math.PI * 2.0 / 3.0;
   private static final double C5 = Math.PI * 4.0 / 9.0;

   public String jsonName() {
      return switch (this) {
         case LINEAR -> "linear";
         case STEP -> "step";
         case CATMULLROM -> "catmullrom";
         default -> {
            String[] parts = this.name().toLowerCase(Locale.ROOT).split("_");
            StringBuilder sb = new StringBuilder("ease");

            for (String p : parts) {
               sb.append(Character.toUpperCase(p.charAt(0))).append(p, 1, p.length());
            }

            yield sb.toString();
         }
      };
   }

   public static Easing byName(String name) {
      return name == null ? null : BY_NAME.get(normalize(name));
   }

   private static String normalize(String s) {
      StringBuilder sb = new StringBuilder(s.length());

      for (int i = 0; i < s.length(); i++) {
         char c = s.charAt(i);
         if (Character.isLetterOrDigit(c)) {
            sb.append(Character.toLowerCase(c));
         }
      }

      return sb.toString();
   }

   public double apply(double x) {
      if (x <= 0.0) {
         return this == STEP ? 0.0 : 0.0;
      } else if (x >= 1.0) {
         return this == STEP ? 0.0 : 1.0;
      } else {
         return switch (this) {
            case LINEAR, CATMULLROM -> x;
            case STEP -> 0.0;
            case IN_SINE -> 1.0 - Math.cos(x * Math.PI / 2.0);
            case OUT_SINE -> Math.sin(x * Math.PI / 2.0);
            case IN_OUT_SINE -> -(Math.cos(Math.PI * x) - 1.0) / 2.0;
            case IN_QUAD -> x * x;
            case OUT_QUAD -> 1.0 - (1.0 - x) * (1.0 - x);
            case IN_OUT_QUAD -> x < 0.5 ? 2.0 * x * x : 1.0 - Math.pow(-2.0 * x + 2.0, 2.0) / 2.0;
            case IN_CUBIC -> x * x * x;
            case OUT_CUBIC -> 1.0 - Math.pow(1.0 - x, 3.0);
            case IN_OUT_CUBIC -> x < 0.5 ? 4.0 * x * x * x : 1.0 - Math.pow(-2.0 * x + 2.0, 3.0) / 2.0;
            case IN_QUART -> x * x * x * x;
            case OUT_QUART -> 1.0 - Math.pow(1.0 - x, 4.0);
            case IN_OUT_QUART -> x < 0.5 ? 8.0 * x * x * x * x : 1.0 - Math.pow(-2.0 * x + 2.0, 4.0) / 2.0;
            case IN_QUINT -> Math.pow(x, 5.0);
            case OUT_QUINT -> 1.0 - Math.pow(1.0 - x, 5.0);
            case IN_OUT_QUINT -> x < 0.5 ? 16.0 * Math.pow(x, 5.0) : 1.0 - Math.pow(-2.0 * x + 2.0, 5.0) / 2.0;
            case IN_EXPO -> Math.pow(2.0, 10.0 * x - 10.0);
            case OUT_EXPO -> 1.0 - Math.pow(2.0, -10.0 * x);
            case IN_OUT_EXPO -> x < 0.5 ? Math.pow(2.0, 20.0 * x - 10.0) / 2.0 : (2.0 - Math.pow(2.0, -20.0 * x + 10.0)) / 2.0;
            case IN_CIRC -> 1.0 - Math.sqrt(1.0 - x * x);
            case OUT_CIRC -> Math.sqrt(1.0 - (x - 1.0) * (x - 1.0));
            case IN_OUT_CIRC -> x < 0.5 ? (1.0 - Math.sqrt(1.0 - 4.0 * x * x)) / 2.0 : (Math.sqrt(1.0 - Math.pow(-2.0 * x + 2.0, 2.0)) + 1.0) / 2.0;
            case IN_BACK -> 2.70158 * x * x * x - 1.70158 * x * x;
            case OUT_BACK -> 1.0 + 2.70158 * Math.pow(x - 1.0, 3.0) + 1.70158 * (x - 1.0) * (x - 1.0);
            case IN_OUT_BACK -> x < 0.5
               ? 4.0 * x * x * (7.189819 * x - 2.5949095) / 2.0
               : (Math.pow(2.0 * x - 2.0, 2.0) * (3.5949095 * (2.0 * x - 2.0) + 2.5949095) + 2.0) / 2.0;
            case IN_ELASTIC -> -Math.pow(2.0, 10.0 * x - 10.0) * Math.sin((10.0 * x - 10.75) * (Math.PI * 2.0 / 3.0));
            case OUT_ELASTIC -> Math.pow(2.0, -10.0 * x) * Math.sin((10.0 * x - 0.75) * (Math.PI * 2.0 / 3.0)) + 1.0;
            case IN_OUT_ELASTIC -> x < 0.5
               ? -(Math.pow(2.0, 20.0 * x - 10.0) * Math.sin((20.0 * x - 11.125) * (Math.PI * 4.0 / 9.0))) / 2.0
               : Math.pow(2.0, -20.0 * x + 10.0) * Math.sin((20.0 * x - 11.125) * (Math.PI * 4.0 / 9.0)) / 2.0 + 1.0;
            case IN_BOUNCE -> 1.0 - bounceOut(1.0 - x);
            case OUT_BOUNCE -> bounceOut(x);
            case IN_OUT_BOUNCE -> x < 0.5 ? (1.0 - bounceOut(1.0 - 2.0 * x)) / 2.0 : (1.0 + bounceOut(2.0 * x - 1.0)) / 2.0;
         };
      }
   }

   private static double bounceOut(double x) {
      double n1 = 7.5625;
      double d1 = 2.75;
      if (x < 0.36363636363636365) {
         return 7.5625 * x * x;
      } else if (x < 0.7272727272727273) {
         x -= 0.5454545454545454;
         return 7.5625 * x * x + 0.75;
      } else if (x < 0.9090909090909091) {
         x -= 0.8181818181818182;
         return 7.5625 * x * x + 0.9375;
      } else {
         x -= 0.9545454545454546;
         return 7.5625 * x * x + 0.984375;
      }
   }

   static {
      BY_NAME.put("linear", LINEAR);
      BY_NAME.put("step", STEP);
      BY_NAME.put("constant", STEP);
      BY_NAME.put("catmullrom", CATMULLROM);

      for (Easing e : values()) {
         if (e.ordinal() >= IN_SINE.ordinal()) {
            BY_NAME.put(normalize("ease" + e.name()), e);
         }
      }
   }
}
