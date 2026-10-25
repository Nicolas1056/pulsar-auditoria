package com.metrofico.pulsar.api.pack;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Fragmenter {
   public static final int CHUNK = 30000;
   public static final int MAX_IN_FLIGHT = 8;
   private final Map<Long, Fragmenter.Partial> partials = new HashMap<>();
   private final int maxPerSecond;
   private long windowStart;
   private int inWindow;
   private long nextId = 1L;

   public Fragmenter(int maxPerSecond) {
      this.maxPerSecond = maxPerSecond;
   }

   public List<String> split(String json) {
      byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
      if (bytes.length > 1048576) {
         throw new Fragmenter.LimitExceeded("message too large: " + bytes.length);
      } else if (bytes.length <= 30000) {
         return List.of(json);
      } else {
         long id = this.nextId++;
         List<String> out = new ArrayList<>();
         int total = (bytes.length + 30000 - 1) / 30000;

         for (int i = 0; i < total; i++) {
            int from = i * 30000;
            int to = Math.min(bytes.length, from + 30000);
            String part = Base64.getEncoder().encodeToString(Arrays.copyOfRange(bytes, from, to));
            out.add(Json.write(Map.of("t", "frag", "id", id, "i", (long)i, "n", (long)total, "d", part)));
         }

         return out;
      }
   }

   public Optional<String> receive(String json, long nowMillis) {
      if (nowMillis - this.windowStart >= 1000L) {
         this.windowStart = nowMillis;
         this.inWindow = 0;
      }

      if (++this.inWindow > this.maxPerSecond) {
         throw new Fragmenter.LimitExceeded("rate exceeded: " + this.maxPerSecond + "/s");
      } else if (json.length() > 1048576) {
         throw new Fragmenter.LimitExceeded("message too large");
      } else if (!json.contains("\"t\":\"frag\"")) {
         return Optional.of(json);
      } else {
         Map<String, Object> o = Json.object(Json.parse(json), "frag");
         if (!"frag".equals(o.get("t"))) {
            return Optional.of(json);
         } else {
            long id = Json.integer(o, "id", "frag");
            int i = (int)Json.integer(o, "i", "frag");
            int n = (int)Json.integer(o, "n", "frag");
            String d = Json.string(o, "d", "frag");
            if (n > 0 && n <= 64 && i >= 0 && i < n) {
               Fragmenter.Partial p = this.partials.get(id);
               if (p == null) {
                  if (this.partials.size() >= 8) {
                     throw new Fragmenter.LimitExceeded("too many partial messages");
                  }

                  p = new Fragmenter.Partial(n, new String[n], 0L);
                  this.partials.put(id, p);
               }

               if (p.total != n) {
                  throw new Fragmenter.LimitExceeded("fragment count mismatch");
               } else {
                  p.parts[i] = d;
                  long bytes = p.bytes + d.length();
                  this.partials.put(id, new Fragmenter.Partial(n, p.parts, bytes));
                  if (bytes > 1399125L) {
                     this.partials.remove(id);
                     throw new Fragmenter.LimitExceeded("message too large");
                  } else {
                     for (String part : p.parts) {
                        if (part == null) {
                           return Optional.empty();
                        }
                     }

                     this.partials.remove(id);
                     StringBuilder sb = new StringBuilder();
                     ByteArrayOutputStream out = new ByteArrayOutputStream();

                     for (String partx : p.parts) {
                        out.writeBytes(Base64.getDecoder().decode(partx));
                     }

                     sb.append(out.toString(StandardCharsets.UTF_8));
                     return Optional.of(sb.toString());
                  }
               }
            } else {
               throw new Fragmenter.LimitExceeded("bad fragment");
            }
         }
      }
   }

   public int pending() {
      return this.partials.size();
   }

   public static final class LimitExceeded extends RuntimeException {
      public LimitExceeded(String m) {
         super(m);
      }
   }

   private record Partial(int total, String[] parts, long bytes) {
   }
}
