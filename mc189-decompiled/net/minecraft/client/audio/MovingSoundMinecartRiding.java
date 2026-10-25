package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MovingSoundMinecartRiding extends MovingSound {
   private final EntityPlayer k;
   private final EntityMinecart l;

   public MovingSoundMinecartRiding(EntityPlayer var1, EntityMinecart var2) {
      super(new ResourceLocation("minecraft:minecart.inside"));
      this.k = var1;
      this.l = var2;
      this.i = ISound.AttenuationType.a;
      this.g = true;
      this.h = 0;
   }

   @Override
   public void c() {
      if (!this.l.I && this.k.au() && this.k.m == this.l) {
         float var1 = MathHelper.a(this.l.v * this.l.v + this.l.x * this.l.x);
         if (var1 >= 0.01) {
            this.b = 0.0F + MathHelper.a(var1, 0.0F, 1.0F) * 0.75F;
         } else {
            this.b = 0.0F;
         }
      } else {
         this.j = true;
      }
   }
}
