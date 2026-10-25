package com.metrofico.pulsar.api.pack;

import java.util.Optional;
import java.util.UUID;

public record ImageRef(ImageRef.Kind kind, String value, int[] region) {
   public static final int MAX_REMOTE_BYTES = 2097152;
   public static final String LINE_PREFIX = "[img:";

   public static Optional<ImageRef> parse(Object v) {
      if (v instanceof String full && !full.isEmpty() && full.length() <= 512) {
         String s = full;
         int[] region = null;
         int hash = full.lastIndexOf(35);
         if (hash > 0) {
            String[] parts = full.substring(hash + 1).split(",");
            if (parts.length != 4) {
               return Optional.empty();
            }

            region = new int[4];

            try {
               for (int i = 0; i < 4; i++) {
                  region[i] = Integer.parseInt(parts[i].trim());
               }
            } catch (NumberFormatException var8) {
               return Optional.empty();
            }

            if (region[0] < 0 || region[1] < 0 || region[2] <= 0 || region[3] <= 0 || region[2] > 4096 || region[3] > 4096) {
               return Optional.empty();
            }

            s = full.substring(0, hash);
         }

         if (s.startsWith("player:")) {
            try {
               return Optional.of(
                  new ImageRef(ImageRef.Kind.PLAYER, UUID.fromString(s.substring(7)).toString(), region != null ? region : new int[]{8, 8, 8, 8})
               );
            } catch (IllegalArgumentException var7) {
               return Optional.empty();
            }
         } else if (s.startsWith("https://")) {
            return Optional.of(new ImageRef(ImageRef.Kind.URL, s, region));
         } else {
            return !s.startsWith("http://") && !s.contains("..") && !s.startsWith("/") && !s.isEmpty()
               ? Optional.of(new ImageRef(ImageRef.Kind.RESOURCE, s, region))
               : Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   public boolean isFace() {
      return this.kind == ImageRef.Kind.PLAYER
         && this.region != null
         && this.region[0] == 8
         && this.region[1] == 8
         && this.region[2] == 8
         && this.region[3] == 8;
   }

   public static String sprite(String ref, int x, int y, int w, int h) {
      return ref + "#" + x + "," + y + "," + w + "," + h;
   }

   public static String player(UUID id) {
      return "player:" + id;
   }

   public static Object[] splitLine(String line) {
      if (line.startsWith("[img:")) {
         int end = line.indexOf(93);
         if (end > "[img:".length()) {
            Optional<ImageRef> ref = parse(line.substring("[img:".length(), end));
            if (ref.isPresent()) {
               return new Object[]{ref.get(), line.substring(end + 1).stripLeading()};
            }
         }
      }

      return new Object[]{null, line};
   }

   public static enum Kind {
      RESOURCE,
      PLAYER,
      URL;
   }
}
