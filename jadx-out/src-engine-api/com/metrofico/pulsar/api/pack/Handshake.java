package com.metrofico.pulsar.api.pack;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class Handshake {
   public static final int PROTOCOL = 1;

   public static String hello(String clientVersion, String locale) {
      return hello(clientVersion, locale, EnumSet.allOf(Capability.class));
   }

   public static String hello(String clientVersion, String locale, Set<Capability> supported) {
      return hello(clientVersion, locale, supported, false);
   }

   public static String hello(String clientVersion, String locale, Set<Capability> supported, boolean legit) {
      List<String> caps = new ArrayList<>();

      for (Capability c : Capability.values()) {
         if (supported.contains(c)) {
            caps.add(c.id());
         }
      }

      if (legit) {
         caps.add("legit");
      }

      Map<String, Object> m = new LinkedHashMap<>();
      m.put("t", "hello");
      m.put("protocol", 1L);
      m.put("client", clientVersion);
      m.put("capabilities", caps);
      m.put("locale", locale);
      return Json.write(m);
   }

   public static String ready(String packId, long packVersion, Iterable<Capability> granted) {
      List<String> caps = new ArrayList<>();
      granted.forEach(c -> caps.add(c.id()));
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("t", "ready");
      m.put("pack", packId);
      m.put("packVersion", packVersion);
      m.put("granted", caps);
      return Json.write(m);
   }

   public static String declined(String packId, String reason) {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("t", "declined");
      m.put("pack", packId);
      m.put("reason", reason);
      return Json.write(m);
   }

   public static Optional<Handshake.Offer> offer(Envelope e) {
      if (!"pack".equals(e.type())) {
         return Optional.empty();
      } else {
         Map<String, Object> f = e.fields();
         String serverId = f.get("serverId") instanceof String s ? s : null;
         String packId = f.get("id") instanceof String sx ? sx : null;
         long version = f.get("packVersion") instanceof Long l ? l : (f.get("packVersion") instanceof Double d ? d.longValue() : 0L);
         String manifestJson = f.get("manifest") != null ? Json.write(f.get("manifest")) : null;
         String manifestUrl = f.get("manifestUrl") instanceof String sxx ? sxx : null;
         if (serverId != null && packId != null && (manifestJson != null || manifestUrl != null)) {
            return manifestUrl != null && !PackVerifier.allowedUrl(manifestUrl)
               ? Optional.empty()
               : Optional.of(new Handshake.Offer(serverId, packId, version, manifestJson, manifestUrl));
         } else {
            return Optional.empty();
         }
      }
   }

   private Handshake() {
   }

   public record Offer(String serverId, String packId, long packVersion, String manifestJson, String manifestUrl) {
   }
}
