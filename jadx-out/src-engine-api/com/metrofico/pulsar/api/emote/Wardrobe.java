package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.pack.Json;
import com.metrofico.pulsar.api.pack.PackVerifier;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record Wardrobe(String player, long exp, List<Wardrobe.Item> emotes) {
   public static final int VERSION = 1;

   public static Wardrobe parseSigned(String json, PublicKey pulsarKey) {
      Map<String, Object> root = Json.object(Json.parse(json), "wardrobe");
      if (Json.integer(root, "version", "wardrobe") != 1L) {
         throw new IllegalArgumentException("wardrobe: unsupported version");
      } else {
         Object body = root.get("wardrobe");
         String signature = root.get("signature") instanceof String s ? s : null;
         if (body != null && signature != null && PackVerifier.verify(Json.canonical(body), signature, pulsarKey)) {
            return from(Json.object(body, "wardrobe"));
         } else {
            throw new IllegalArgumentException("wardrobe: bad signature");
         }
      }
   }

   public static Wardrobe from(Map<String, Object> w) {
      String player = Json.string(w, "player", "wardrobe");
      long exp = Json.integer(w, "exp", "wardrobe");
      List<Wardrobe.Item> items = new ArrayList<>();

      for (Object o : Json.array(w.get("emotes"), "wardrobe.emotes")) {
         Map<String, Object> e = Json.object(o, "wardrobe.emotes[]");
         String sha = Json.string(e, "sha256", "wardrobe.emotes[]");
         if (!sha.matches("[0-9a-f]{64}")) {
            throw new IllegalArgumentException("wardrobe: bad sha256");
         }

         String vis = e.get("visibility") instanceof String v && !v.isEmpty() ? v : "public";
         if (!vis.equals("public") && !vis.matches("network:[a-z0-9-]{3,64}")) {
            throw new IllegalArgumentException("wardrobe: bad visibility " + vis);
         }

         items.add(new Wardrobe.Item(Json.string(e, "id", "wardrobe.emotes[]"), sha, e.get("name") instanceof String n ? n : "", vis));
      }

      return new Wardrobe(player, exp, List.copyOf(items));
   }

   public boolean expired(long nowMs) {
      return nowMs > this.exp;
   }

   public Wardrobe.Item byId(String id) {
      for (Wardrobe.Item i : this.emotes) {
         if (i.id().equals(id)) {
            return i;
         }
      }

      return null;
   }

   public record Item(String id, String sha256, String name, String visibility) {
      public boolean exclusive() {
         return this.visibility.startsWith("network:");
      }

      public String network() {
         return this.exclusive() ? this.visibility.substring("network:".length()) : null;
      }
   }
}
