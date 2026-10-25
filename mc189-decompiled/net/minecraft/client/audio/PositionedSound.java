package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public abstract class PositionedSound implements ISound {
   protected final ResourceLocation a;
   protected float b = 1.0F;
   protected float c = 1.0F;
   protected float d;
   protected float e;
   protected float f;
   protected boolean g = false;
   protected int h = 0;
   protected ISound.AttenuationType i = ISound.AttenuationType.b;

   protected PositionedSound(ResourceLocation var1) {
      this.a = var1;
   }

   @Override
   public ResourceLocation a() {
      return this.a;
   }

   @Override
   public boolean b() {
      return this.g;
   }

   @Override
   public int d() {
      return this.h;
   }

   @Override
   public float e() {
      return this.b;
   }

   @Override
   public float f() {
      return this.c;
   }

   @Override
   public float g() {
      return this.d;
   }

   @Override
   public float h() {
      return this.e;
   }

   @Override
   public float i() {
      return this.f;
   }

   @Override
   public ISound.AttenuationType j() {
      return this.i;
   }
}
