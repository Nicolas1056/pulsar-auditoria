package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.hub.ClientOptions;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public final class EmoteSounds {
   private static final Map<EmotePlayer.Active, EmoteSounds.State> STATES = new IdentityHashMap<>();

   private EmoteSounds() {
   }

   public static boolean enabled() {
      return ClientOptions.get().bool("emotes.sound", true);
   }

   public static void tick(EmotePlayer.Active a) {
      List<Emote.Sound> sounds = a.emote.sounds();
      if (!sounds.isEmpty() && enabled()) {
         EmoteSounds.State st = STATES.computeIfAbsent(a, k -> new EmoteSounds.State());
         double elapsed = a.elapsed();
         if (elapsed >= a.endAt()) {
            stop(a);
         } else {
            double duration = a.emote.duration();
            double prev = st.last;
            st.last = elapsed;

            for (Emote.Sound s : sounds) {
               boolean fire;
               if (a.emote.loop() && duration > 0.0) {
                  fire = Math.floor((elapsed - s.at()) / duration) > Math.floor((prev - s.at()) / duration);
               } else {
                  fire = s.at() > prev && s.at() <= elapsed;
               }

               if (fire) {
                  play(a, s);
               }
            }
         }
      }
   }

   private static void play(EmotePlayer.Active a, Emote.Sound s) {
      if (available(s.sha256())) {
         EmoteHost.SoundHandle handle = Emotes.host().playSound(s.sha256(), a.entityId, s.volume(), s.pitch());
         if (handle != null) {
            STATES.computeIfAbsent(a, k -> new EmoteSounds.State()).playing.add(handle);
         }
      }
   }

   public static void stop(EmotePlayer.Active a) {
      EmoteSounds.State st = STATES.remove(a);
      if (st != null) {
         for (EmoteHost.SoundHandle i : st.playing) {
            i.end();
         }
      }
   }

   public static boolean available(String sha256) {
      return Files.isRegularFile(Emotes.pack().cache().pathFor(sha256));
   }

   public static InputStream open(String sha256) throws IOException {
      if (!sha256.matches("[0-9a-f]{64}")) {
         throw new IOException("hash inválido");
      } else {
         Path cached = Emotes.pack().cache().pathFor(sha256);
         if (Files.isRegularFile(cached)) {
            return Files.newInputStream(cached);
         } else {
            throw new IOException("sonido no descargado: " + sha256.substring(0, 12));
         }
      }
   }

   public static String shaOf(String path) {
      String p = path;
      if (path.startsWith("sounds/")) {
         p = path.substring("sounds/".length());
      }

      if (p.endsWith(".ogg")) {
         p = p.substring(0, p.length() - 4);
      }

      return p;
   }

   public static void clearAll() {
      for (EmoteSounds.State st : STATES.values()) {
         for (EmoteHost.SoundHandle i : st.playing) {
            i.end();
         }
      }

      STATES.clear();
   }

   static void log(String msg) {
      Emotes.log("emote sound: " + msg);
   }

   private static final class State {
      double last;
      final List<EmoteHost.SoundHandle> playing = new ArrayList<>();
   }
}
