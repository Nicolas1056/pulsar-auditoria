package com.metrofico.pulsar.engine.v189.emote;

import com.metrofico.pulsar.api.emote.EmoteHost.SoundHandle;
import com.metrofico.pulsar.engine.v189.mixin.SoundManagerAccessor;

public final class EmoteSounds189 {
   public static final String NAMESPACE = "pulsar-emote";

   private EmoteSounds189() {
   }

   static SoundHandle play(String sha256, int entityId, float volume, float pitch) {
      bpz manager = ave.A().W();
      jy event = new jy("pulsar-emote", sha256);
      if (manager.a(event) == null) {
         bpy set = new bpy(event, 1.0, 1.0, bpg.h);
         final bpw sound = new bpw(new jy("pulsar-emote", "sounds/" + sha256 + ".ogg"), 1.0, 1.0, false);
         set.a(new bqb<bpw>() {
            public int a() {
               return 1;
            }

            public bpw getSound() {
               return sound;
            }
         });
         ((SoundManagerAccessor)manager).pulsar$loadedSounds().a(set);
      }

      EmoteSoundInstance189 instance = new EmoteSoundInstance189(event, entityId, volume, pitch);
      manager.a(instance);
      return instance;
   }
}
