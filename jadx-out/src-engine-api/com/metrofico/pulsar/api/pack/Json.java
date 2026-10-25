package com.metrofico.pulsar.api.pack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public final class Json {
   public static Object parse(String text) {
      Json.Parser p = new Json.Parser(text);
      Object v = p.value();
      p.skipWs();
      if (p.i != text.length()) {
         throw new IllegalArgumentException("json: trailing data at " + p.i);
      } else {
         return v;
      }
   }

   public static Map<String, Object> object(Object v, String what) {
      if (v instanceof Map<?, ?> m) {
         return (Map<String, Object>)m;
      } else {
         throw new IllegalArgumentException(what + ": expected object");
      }
   }

   public static List<Object> array(Object v, String what) {
      if (v instanceof List<?> l) {
         return (List<Object>)l;
      } else {
         throw new IllegalArgumentException(what + ": expected array");
      }
   }

   public static String string(Map<String, Object> o, String key, String what) {
      if (o.get(key) instanceof String s) {
         return s;
      } else {
         throw new IllegalArgumentException(what + ": missing string \"" + key + "\"");
      }
   }

   public static long integer(Map<String, Object> o, String key, String what) {
      Object v = o.get(key);
      if (v instanceof Long l) {
         return l;
      } else if (v instanceof Double d && d == Math.floor(d)) {
         return d.longValue();
      } else {
         throw new IllegalArgumentException(what + ": missing integer \"" + key + "\"");
      }
   }

   public static String canonical(Object v) {
      StringBuilder sb = new StringBuilder();
      writeCanonical(sb, v);
      return sb.toString();
   }

   public static String write(Object v) {
      return canonical(v);
   }

   private static void writeCanonical(StringBuilder sb, Object v) {
      if (v == null) {
         sb.append("null");
      } else if (v instanceof Boolean b) {
         sb.append(b);
      } else if (v instanceof Long l) {
         sb.append(l);
      } else if (v instanceof Integer i) {
         sb.append(i);
      } else if (!(v instanceof Double d)) {
         if (v instanceof String s) {
            quote(sb, s);
         } else if (v instanceof List<?> l) {
            sb.append('[');

            for (int i = 0; i < l.size(); i++) {
               if (i > 0) {
                  sb.append(',');
               }

               writeCanonical(sb, l.get(i));
            }

            sb.append(']');
         } else if (v instanceof Map<?, ?> m) {
            Map<String, Object> sorted = new TreeMap<>();
            m.forEach((k, x) -> sorted.put(String.valueOf(k), x));
            sb.append('{');
            boolean first = true;

            for (Entry<String, Object> e : sorted.entrySet()) {
               if (!first) {
                  sb.append(',');
               }

               first = false;
               quote(sb, e.getKey());
               sb.append(':');
               writeCanonical(sb, e.getValue());
            }

            sb.append('}');
         } else {
            throw new IllegalArgumentException("json: unsupported " + v.getClass());
         }
      } else {
         if (d == Math.floor(d) && !Double.isInfinite(d) && Math.abs(d) < 1.0E15) {
            sb.append(d.longValue());
         } else if (Math.abs(d) < 0.001 && Math.abs(d) >= 1.0E-6) {
            sb.append(new BigDecimal(Double.toString(d)).stripTrailingZeros().toPlainString());
         } else {
            sb.append(d);
         }
      }
   }

   private static void quote(StringBuilder sb, String s) {
      sb.append('"');

      for (char c : s.toCharArray()) {
         switch (c) {
            case '\b':
               sb.append("\\b");
               break;
            case '\t':
               sb.append("\\t");
               break;
            case '\n':
               sb.append("\\n");
               break;
            case '\f':
               sb.append("\\f");
               break;
            case '\r':
               sb.append("\\r");
               break;
            case '"':
               sb.append("\\\"");
               break;
            case '\\':
               sb.append("\\\\");
               break;
            default:
               if (c < ' ') {
                  sb.append(String.format("\\u%04x", Integer.valueOf(c)));
               } else {
                  sb.append(c);
               }
         }
      }

      sb.append('"');
   }

   private Json() {
   }

   private static final class Parser {
      final String s;
      int i;

      Parser(String s) {
         this.s = s;
      }

      void skipWs() {
         while (this.i < this.s.length() && Character.isWhitespace(this.s.charAt(this.i))) {
            this.i++;
         }
      }

      Object value() {
         this.skipWs();
         if (this.i >= this.s.length()) {
            throw new IllegalArgumentException("json: unexpected end");
         } else {
            char c = this.s.charAt(this.i);
            if (c == '{') {
               return this.object();
            } else if (c == '[') {
               return this.array();
            } else if (c == '"') {
               return this.string();
            } else if (this.s.startsWith("true", this.i)) {
               this.i += 4;
               return Boolean.TRUE;
            } else if (this.s.startsWith("false", this.i)) {
               this.i += 5;
               return Boolean.FALSE;
            } else if (this.s.startsWith("null", this.i)) {
               this.i += 4;
               return null;
            } else {
               return this.number();
            }
         }
      }

      Map<String, Object> object() {
         Map<String, Object> m = new LinkedHashMap<>();
         this.i++;
         this.skipWs();
         if (this.peek('}')) {
            this.i++;
            return m;
         } else {
            while (true) {
               this.skipWs();
               String k = this.string();
               this.skipWs();
               this.expect(':');
               m.put(k, this.value());
               this.skipWs();
               if (!this.peek(',')) {
                  this.expect('}');
                  return m;
               }

               this.i++;
            }
         }
      }

      List<Object> array() {
         List<Object> l = new ArrayList<>();
         this.i++;
         this.skipWs();
         if (this.peek(']')) {
            this.i++;
            return l;
         } else {
            while (true) {
               l.add(this.value());
               this.skipWs();
               if (!this.peek(',')) {
                  this.expect(']');
                  return l;
               }

               this.i++;
            }
         }
      }

      String string() {
         this.expect('"');
         StringBuilder sb = new StringBuilder();

         while (this.i < this.s.length()) {
            char c = this.s.charAt(this.i++);
            if (c == '"') {
               return sb.toString();
            }

            if (c == '\\') {
               char e = this.s.charAt(this.i++);
               switch (e) {
                  case '"':
                     sb.append('"');
                     break;
                  case '/':
                     sb.append('/');
                     break;
                  case '\\':
                     sb.append('\\');
                     break;
                  case 'b':
                     sb.append('\b');
                     break;
                  case 'f':
                     sb.append('\f');
                     break;
                  case 'n':
                     sb.append('\n');
                     break;
                  case 'r':
                     sb.append('\r');
                     break;
                  case 't':
                     sb.append('\t');
                     break;
                  case 'u':
                     sb.append((char)Integer.parseInt(this.s.substring(this.i, this.i + 4), 16));
                     this.i += 4;
                     break;
                  default:
                     throw new IllegalArgumentException("json: bad escape \\" + e);
               }
            } else {
               sb.append(c);
            }
         }

         throw new IllegalArgumentException("json: unterminated string");
      }

      Object number() {
         int start = this.i;

         while (this.i < this.s.length() && "+-0123456789.eE".indexOf(this.s.charAt(this.i)) >= 0) {
            this.i++;
         }

         String n = this.s.substring(start, this.i);
         if (n.isEmpty()) {
            throw new IllegalArgumentException("json: unexpected '" + this.s.charAt(this.i) + "' at " + this.i);
         } else {
            if (n.indexOf(46) < 0 && n.indexOf(101) < 0 && n.indexOf(69) < 0) {
               try {
                  return Long.parseLong(n);
               } catch (NumberFormatException var4) {
               }
            }

            return Double.parseDouble(n);
         }
      }

      boolean peek(char c) {
         return this.i < this.s.length() && this.s.charAt(this.i) == c;
      }

      void expect(char c) {
         if (!this.peek(c)) {
            throw new IllegalArgumentException("json: expected '" + c + "' at " + this.i);
         } else {
            this.i++;
         }
      }
   }
}
