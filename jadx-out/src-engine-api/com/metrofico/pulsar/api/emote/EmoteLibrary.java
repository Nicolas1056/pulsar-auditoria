package com.metrofico.pulsar.api.emote;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class EmoteLibrary {
   private static final Map<String, Emote> BY_SHA = new HashMap<>();
   private static Wardrobe wardrobe;
   private static long wardrobeMtime = -1L;
   private static String wardrobeError;

   private EmoteLibrary() {
   }

   public static Path wardrobeFile() {
      return Emotes.pack().dataDir().resolve("emotes").resolve("wardrobe.json");
   }

   public static synchronized Wardrobe wardrobe() {
      Path f = wardrobeFile();

      long mtime;
      try {
         mtime = Files.isRegularFile(f) ? Files.getLastModifiedTime(f).toMillis() : 0L;
      } catch (IOException var5) {
         mtime = 0L;
      }

      if (mtime == wardrobeMtime) {
         return wardrobe;
      } else {
         wardrobeMtime = mtime;
         wardrobe = null;
         wardrobeError = null;
         if (mtime == 0L) {
            wardrobeError = "sin guardarropa";
            return null;
         } else {
            try {
               Wardrobe w = Wardrobe.parseSigned(Files.readString(f, StandardCharsets.UTF_8), Emotes.pack().pulsarKey());
               String me = Emotes.host().accountUuid();
               if (me != null && !me.equalsIgnoreCase(w.player())) {
                  wardrobeError = "el guardarropa es de otra cuenta";
               } else if (w.expired(System.currentTimeMillis())) {
                  wardrobeError = "guardarropa caducado";
               } else {
                  wardrobe = w;
                  Emotes.log("emotes: wardrobe with " + w.emotes().size() + " emotes");
               }
            } catch (Exception var6) {
               wardrobeError = "guardarropa inválido: " + var6.getMessage();
               Emotes.log("emotes: " + wardrobeError);
            }

            return wardrobe;
         }
      }
   }

   public static String wardrobeError() {
      wardrobe();
      return wardrobeError;
   }

   public static synchronized List<EmoteLibrary.Entry> all() {
      Map<String, EmoteLibrary.Entry> out = new LinkedHashMap<>();
      Wardrobe w = wardrobe();
      if (w != null) {
         for (Wardrobe.Item item : w.emotes()) {
            Emote e = bySha(item.sha256());
            if (e != null) {
               out.put(item.id(), new EmoteLibrary.Entry(e, item.sha256(), false, item.network()));
            }
         }
      }

      for (Emote e : EmoteDev.list()) {
         out.putIfAbsent(e.id(), new EmoteLibrary.Entry(e, null, true, null));
      }

      return new ArrayList<>(out.values());
   }

   public static EmoteLibrary.Entry byId(String id) {
      if (id != null && !id.isEmpty()) {
         for (EmoteLibrary.Entry e : all()) {
            if (e.id().equals(id)) {
               return e;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static synchronized Emote bySha(String sha256) {
      Emote cached = BY_SHA.get(sha256);
      if (cached != null) {
         return cached;
      } else {
         Path f = Emotes.pack().resourceFile(sha256);
         if (f == null) {
            return null;
         } else {
            try {
               Emote e = PemoteJson.parse(Files.readString(f, StandardCharsets.UTF_8));
               BY_SHA.put(sha256, e);
               return e;
            } catch (IOException | RuntimeException var4) {
               Emotes.log("emotes: bad .pemote " + sha256.substring(0, 12) + ": " + var4.getMessage());
               return null;
            }
         }
      }
   }

   public static synchronized void invalidate() {
      wardrobeMtime = -1L;
      BY_SHA.clear();
   }

   public record Entry(Emote emote, String sha256, boolean dev, String network) {
      public String id() {
         return this.emote.id();
      }

      public boolean inline() {
         return this.dev;
      }
   }
}
