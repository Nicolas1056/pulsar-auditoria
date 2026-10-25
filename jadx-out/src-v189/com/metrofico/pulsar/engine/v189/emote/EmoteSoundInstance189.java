package com.metrofico.pulsar.engine.v189.emote;

import com.metrofico.pulsar.api.emote.EmoteHost.SoundHandle;

public final class EmoteSoundInstance189 extends bpb implements SoundHandle {
   private final int entityId;

   EmoteSoundInstance189(jy id, int entityId, float volume, float pitch) {
      super(id);
      this.entityId = entityId;
      this.b = volume;
      this.c = pitch;
      this.move();
   }

   private void move() {
      ave mc = ave.A();
      pk e = mc.f == null ? null : mc.f.a(this.entityId);
      if (e == null) {
         this.j = true;
      } else {
         this.d = (float)e.s;
         this.e = (float)e.t + 1.0F;
         this.f = (float)e.u;
      }
   }

   public void c() {
      this.move();
   }

   public void end() {
      this.j = true;
   }
}
