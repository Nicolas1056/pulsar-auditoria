package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.api.emote.EmoteHost.SoundHandle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.resources.sounds.Sound.Type;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.entity.Entity;

public final class EmoteSoundInstance extends AbstractSoundInstance implements TickableSoundInstance, SoundHandle {
   private final int entityId;
   private boolean stopped;

   public EmoteSoundInstance(String sha256, int entityId, float volume, float pitch) {
      super(Identifier.fromNamespaceAndPath("pulsar-emote", sha256), SoundSource.PLAYERS, RandomSource.create());
      this.entityId = entityId;
      this.volume = volume;
      this.pitch = pitch;
      this.attenuation = Attenuation.LINEAR;
      this.move();
   }

   public WeighedSoundEvents resolve(SoundManager manager) {
      this.sound = new Sound(this.identifier, ConstantFloat.of(1.0F), ConstantFloat.of(1.0F), 1, Type.FILE, false, false, 16);
      return new WeighedSoundEvents(this.identifier, null);
   }

   private void move() {
      Minecraft mc = Minecraft.getInstance();
      Entity entity = mc.level == null ? null : mc.level.getEntity(this.entityId);
      if (entity == null) {
         this.stopped = true;
      } else {
         this.x = entity.getX();
         this.y = entity.getY() + 1.0;
         this.z = entity.getZ();
      }
   }

   public void end() {
      this.stopped = true;
   }

   public void tick() {
      this.move();
   }

   public boolean isStopped() {
      return this.stopped;
   }
}
