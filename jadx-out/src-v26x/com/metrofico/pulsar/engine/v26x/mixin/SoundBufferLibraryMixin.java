package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.emote.EmoteSounds;
import com.mojang.blaze3d.audio.SoundBuffer;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.JOrbisAudioStream;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SoundBufferLibrary.class})
abstract class SoundBufferLibraryMixin {
   @Inject(
      method = {"getCompleteBuffer"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$emoteBuffer(Identifier id, CallbackInfoReturnable<CompletableFuture<SoundBuffer>> cir) {
      if ("pulsar-emote".equals(id.getNamespace())) {
         String sha = EmoteSounds.shaOf(id.getPath());
         cir.setReturnValue(CompletableFuture.supplyAsync(() -> {
            try {
               SoundBuffer x2;
               try (InputStream in = EmoteSounds.open(sha)) {
                  JOrbisAudioStream stream = new JOrbisAudioStream(in);

                  try {
                     x2 = new SoundBuffer(stream.readAll(), stream.getFormat());
                  } catch (Throwable var7) {
                     try {
                        stream.close();
                     } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                     }

                     throw var7;
                  }

                  stream.close();
               }

               return x2;
            } catch (Exception var9) {
               throw new CompletionException(var9);
            }
         }, Util.nonCriticalIoPool()));
      }
   }

   @Inject(
      method = {"getStream"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$emoteStream(Identifier id, boolean looping, CallbackInfoReturnable<CompletableFuture<AudioStream>> cir) {
      if ("pulsar-emote".equals(id.getNamespace())) {
         String sha = EmoteSounds.shaOf(id.getPath());
         cir.setReturnValue(CompletableFuture.supplyAsync(() -> {
            try {
               return new JOrbisAudioStream(EmoteSounds.open(sha));
            } catch (Exception var2x) {
               throw new CompletionException(var2x);
            }
         }, Util.nonCriticalIoPool()));
      }
   }
}
