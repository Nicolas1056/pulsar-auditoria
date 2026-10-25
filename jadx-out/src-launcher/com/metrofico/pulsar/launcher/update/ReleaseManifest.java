package com.metrofico.pulsar.launcher.update;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public record ReleaseManifest(
   String version, String channel, String publishedAt, String notes, String minVersion, List<ReleaseManifest.Package> packages, boolean signed
) {
   public static final String PUBLIC_KEY_RESOURCE = "/pulsar-release.pub";

   public static ReleaseManifest parse(String json, PublicKey publicKey) {
      JsonObject root = JsonParser.parseString(json).getAsJsonObject();
      if (root.has("version") && root.get("version").getAsInt() == 1 && root.has("release")) {
         JsonObject release = root.getAsJsonObject("release");
         boolean signed = root.has("signature") && publicKey != null && verify(canonical(release), root.get("signature").getAsString(), publicKey);
         List<ReleaseManifest.Package> packages = new ArrayList<>();

         for (JsonElement e : release.getAsJsonArray("packages")) {
            JsonObject p = e.getAsJsonObject();
            packages.add(
               new ReleaseManifest.Package(
                  p.get("os").getAsString(),
                  p.get("arch").getAsString(),
                  p.get("format").getAsString(),
                  p.get("url").getAsString(),
                  p.get("sha256").getAsString(),
                  p.get("size").getAsLong()
               )
            );
         }

         String v = release.get("version").getAsString();
         Semver.parse(v);
         return new ReleaseManifest(
            v,
            release.get("channel").getAsString(),
            release.get("publishedAt").getAsString(),
            release.has("notes") ? release.get("notes").getAsString() : "",
            release.has("minVersion") ? release.get("minVersion").getAsString() : null,
            List.copyOf(packages),
            signed
         );
      } else {
         throw new IllegalArgumentException("manifest: version must be 1 and release present");
      }
   }

   public static PublicKey loadPublicKey(String pem) throws GeneralSecurityException {
      String b64 = pem.replaceAll("-----[A-Z ]+-----", "").replaceAll("\\s", "");
      return KeyFactory.getInstance("Ed25519").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(b64)));
   }

   public static PublicKey embeddedPublicKey() throws GeneralSecurityException, IOException {
      PublicKey var1;
      try (InputStream in = ReleaseManifest.class.getResourceAsStream("/pulsar-release.pub")) {
         if (in == null) {
            throw new IOException("missing /pulsar-release.pub");
         }

         var1 = loadPublicKey(new String(in.readAllBytes(), StandardCharsets.UTF_8));
      }

      return var1;
   }

   static boolean verify(String canonical, String signatureB64, PublicKey key) {
      try {
         Signature s = Signature.getInstance("Ed25519");
         s.initVerify(key);
         s.update(canonical.getBytes(StandardCharsets.UTF_8));
         return s.verify(Base64.getDecoder().decode(signatureB64));
      } catch (IllegalArgumentException | GeneralSecurityException var4) {
         return false;
      }
   }

   static String canonical(JsonElement e) {
      if (e.isJsonArray()) {
         StringBuilder sb = new StringBuilder("[");
         JsonArray a = e.getAsJsonArray();

         for (int i = 0; i < a.size(); i++) {
            if (i > 0) {
               sb.append(',');
            }

            sb.append(canonical(a.get(i)));
         }

         return sb.append(']').toString();
      } else if (e.isJsonObject()) {
         Map<String, JsonElement> sorted = new TreeMap<>();
         e.getAsJsonObject().entrySet().forEach(xx -> sorted.put((String)xx.getKey(), (JsonElement)xx.getValue()));
         StringBuilder sb = new StringBuilder("{");
         boolean first = true;

         for (Entry<String, JsonElement> x : sorted.entrySet()) {
            if (!first) {
               sb.append(',');
            }

            first = false;
            sb.append(jsString(x.getKey())).append(':').append(canonical(x.getValue()));
         }

         return sb.append('}').toString();
      } else if (e.isJsonNull()) {
         return "null";
      } else if (e.getAsJsonPrimitive().isString()) {
         return jsString(e.getAsString());
      } else if (e.getAsJsonPrimitive().isBoolean()) {
         return String.valueOf(e.getAsBoolean());
      } else {
         String n = e.getAsJsonPrimitive().getAsNumber().toString();
         return n.endsWith(".0") ? n.substring(0, n.length() - 2) : n;
      }
   }

   static String jsString(String s) {
      StringBuilder sb = new StringBuilder("\"");

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

      return sb.append('"').toString();
   }

   public record Package(String os, String arch, String format, String url, String sha256, long size) {
   }
}
