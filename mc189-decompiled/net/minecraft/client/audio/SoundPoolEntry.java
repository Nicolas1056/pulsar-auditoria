package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class SoundPoolEntry {
   private final ResourceLocation a;
   private final boolean b;
   private double c;
   private double d;

   public SoundPoolEntry(ResourceLocation var1, double var2, double var4, boolean var6) {
      this.a = var1;
      this.c = var2;
      this.d = var4;
      this.b = var6;
   }

   public SoundPoolEntry(SoundPoolEntry var1) {
      this.a = var1.a;
      this.c = var1.c;
      this.d = var1.d;
      this.b = var1.b;
   }

   public ResourceLocation a() {
      return this.a;
   }

   public double b() {
      return this.c;
   }

   public void a(double var1) {
      this.c = var1;
   }

   public double c() {
      return this.d;
   }

   public void b(double var1) {
      this.d = var1;
   }

   public boolean d() {
      return this.b;
   }
}
