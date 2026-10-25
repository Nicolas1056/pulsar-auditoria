package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.gfx.Canvas;
import java.io.InputStream;
import java.util.UUID;

public interface EmoteHost {
   boolean paused();

   boolean inWorld();

   int ownEntityId();

   int entityIdOf(UUID var1);

   boolean entityExists(int var1);

   float bodyYaw(int var1);

   EmoteHost.CancelState cancelState(int var1, boolean var2);

   String accountUuid();

   String currentServer();

   void runOnMain(Runnable var1);

   String cameraType();

   void cameraType(String var1);

   default float[] ownLook() {
      return null;
   }

   EmoteHost.SoundHandle playSound(String var1, int var2, float var3, float var4);

   void openWheel(boolean var1);

   void openLibrary(int var1);

   Canvas measure();

   boolean drawOwnPlayer(Canvas var1, int var2, int var3, int var4, int var5, int var6, float var7, float var8);

   default InputStream resource(String absolutePath) {
      return this.getClass().getResourceAsStream(absolutePath);
   }

   public record CancelState(
      boolean hurt,
      boolean airborneOrSwimming,
      boolean passenger,
      boolean sleeping,
      boolean swinging,
      boolean usingItem,
      boolean moving,
      boolean screenBlocks,
      double movedSq
   ) {
   }

   public interface SoundHandle {
      void end();
   }
}
