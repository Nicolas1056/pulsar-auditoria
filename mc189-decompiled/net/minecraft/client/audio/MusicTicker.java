package net.minecraft.client.audio;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MusicTicker implements ITickable {
   private final Random a = new Random();
   private final Minecraft b;
   private ISound c;
   private int d = 100;

   public MusicTicker(Minecraft var1) {
      this.b = var1;
   }

   @Override
   public void c() {
      MusicTicker.MusicType var1 = this.b.X();
      if (this.c != null) {
         if (!var1.a().equals(this.c.a())) {
            this.b.W().b(this.c);
            this.d = MathHelper.a(this.a, 0, var1.b() / 2);
         }

         if (!this.b.W().c(this.c)) {
            this.c = null;
            this.d = Math.min(MathHelper.a(this.a, var1.b(), var1.c()), this.d);
         }
      }

      if (this.c == null && this.d-- <= 0) {
         this.a(var1);
      }
   }

   public void a(MusicTicker.MusicType var1) {
      this.c = PositionedSoundRecord.a(var1.a());
      this.b.W().a(this.c);
      this.d = Integer.MAX_VALUE;
   }

   public void a() {
      if (this.c != null) {
         this.b.W().b(this.c);
         this.c = null;
         this.d = 0;
      }
   }

   public static enum MusicType {
      a(new ResourceLocation("minecraft:music.menu"), 20, 600),
      b(new ResourceLocation("minecraft:music.game"), 12000, 24000),
      c(new ResourceLocation("minecraft:music.game.creative"), 1200, 3600),
      d(new ResourceLocation("minecraft:music.game.end.credits"), Integer.MAX_VALUE, Integer.MAX_VALUE),
      e(new ResourceLocation("minecraft:music.game.nether"), 1200, 3600),
      f(new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0),
      g(new ResourceLocation("minecraft:music.game.end"), 6000, 24000);

      private final ResourceLocation h;
      private final int i;
      private final int j;

      private MusicType(ResourceLocation var3, int var4, int var5) {
         this.h = var3;
         this.i = var4;
         this.j = var5;
      }

      public ResourceLocation a() {
         return this.h;
      }

      public int b() {
         return this.i;
      }

      public int c() {
         return this.j;
      }
   }
}
