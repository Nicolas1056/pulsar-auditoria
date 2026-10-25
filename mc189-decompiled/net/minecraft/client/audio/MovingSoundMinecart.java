package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MovingSoundMinecart extends MovingSound {
   private final EntityMinecart k;
   private float l = 0.0F;

   public MovingSoundMinecart(EntityMinecart var1) {
      super(new ResourceLocation("minecraft:minecart.base"));
      this.k = var1;
      this.g = true;
      this.h = 0;
   }

   @Override
   public void c() {
      if (this.k.I) {
         this.j = true;
      } else {
         this.d = (float)this.k.s;
         this.e = (float)this.k.t;
         this.f = (float)this.k.u;
         float var1 = MathHelper.a(this.k.v * this.k.v + this.k.x * this.k.x);
         if (var1 >= 0.01) {
            this.l = MathHelper.a(this.l + 0.0025F, 0.0F, 1.0F);
            this.b = 0.0F + MathHelper.a(var1, 0.0F, 0.5F) * 0.7F;
         } else {
            this.l = 0.0F;
            this.b = 0.0F;
         }
      }
   }
}
