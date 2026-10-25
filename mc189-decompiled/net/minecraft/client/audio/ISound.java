package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public interface ISound {
   ResourceLocation a();

   boolean b();

   int d();

   float e();

   float f();

   float g();

   float h();

   float i();

   ISound.AttenuationType j();

   public static enum AttenuationType {
      a(0),
      b(2);

      private final int c;

      private AttenuationType(int var3) {
         this.c = var3;
      }

      public int a() {
         return this.c;
      }
   }
}
