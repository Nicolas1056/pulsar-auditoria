package com.metrofico.pulsar.api.ui;

import com.metrofico.pulsar.api.hud.Zone;
import java.util.Locale;
import java.util.Map;

public record ScreenDefinition(
   String id, String title, String key, Node root, boolean dim, boolean closeOnEsc, boolean overlay, Zone zone, long ttlMs, String anim, long createdAt
) {
   public static final long MAX_TTL = 60000L;

   public static ScreenDefinition from(String id, Map<String, Object> f, ScreenDefinition previous, Node root) {
      return from(id, f, previous, root, 0L);
   }

   public static ScreenDefinition from(String id, Map<String, Object> f, ScreenDefinition previous, Node root, long now) {
      String title = f.get("title") instanceof String s ? s : (previous == null ? "" : previous.title());
      String key = f.containsKey("key") ? normalizeKey(f.get("key")) : (previous == null ? null : previous.key());
      boolean dim = f.get("dim") instanceof Boolean b ? b : previous == null || previous.dim();
      boolean esc = f.get("closeOnEsc") instanceof Boolean bx ? bx : previous == null || previous.closeOnEsc();
      boolean overlay = f.get("overlay") instanceof Boolean bxx ? bxx : previous != null && previous.overlay();
      Zone zone = f.get("zone") instanceof String sx ? Zone.byId(sx).orElse(Zone.TOP_RIGHT) : (previous == null ? Zone.TOP_RIGHT : previous.zone());
      long ttl = f.get("ttl") instanceof Number n ? Math.max(0L, Math.min(60000L, n.longValue())) : (previous == null ? 0L : previous.ttlMs());
      String anim = f.get("anim") instanceof String sxx ? sxx : (previous == null ? "slide" : previous.anim());
      long created = previous != null && !f.containsKey("root") ? previous.createdAt() : now;
      return new ScreenDefinition(id, title, key, root, dim, esc, overlay, zone, ttl, anim, created);
   }

   public boolean expired(long now) {
      return this.overlay && this.ttlMs > 0L && now >= this.createdAt + this.ttlMs;
   }

   public double progress(long now) {
      if (this.overlay && !this.anim.equals("none")) {
         long slide = 300L;
         long age = now - this.createdAt;
         if (age < slide) {
            return Math.max(0.0, (double)age / slide);
         } else {
            if (this.ttlMs > 0L) {
               long left = this.createdAt + this.ttlMs - now;
               if (left < slide) {
                  return Math.max(0.0, (double)left / slide);
               }
            }

            return 1.0;
         }
      } else {
         return 1.0;
      }
   }

   public static String normalizeKey(Object v) {
      if (v instanceof String s && !s.isEmpty()) {
         String k = s.toUpperCase(Locale.ROOT);
         if (k.length() == 1 && k.charAt(0) >= 'A' && k.charAt(0) <= 'Z' && "WASDEQFT".indexOf(k.charAt(0)) < 0) {
            return k;
         } else {
            return k.matches("F(5|6|7|8|9|10|11|12)") ? k : null;
         }
      } else {
         return null;
      }
   }
}
