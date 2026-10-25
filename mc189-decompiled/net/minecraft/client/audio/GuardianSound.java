package net.minecraft.client.audio;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;

public class GuardianSound extends MovingSound {
   private final EntityGuardian k;

   public GuardianSound(EntityGuardian var1) {
      super(new ResourceLocation("minecraft:mob.guardian.attack"));
      this.k = var1;
      this.i = ISound.AttenuationType.a;
      this.g = true;
      this.h = 0;
   }

   @Override
   public void c() {
      if (!this.k.I && this.k.cp()) {
         this.d = (float)this.k.s;
         this.e = (float)this.k.t;
         this.f = (float)this.k.u;
         float var1 = this.k.q(0.0F);
         this.b = 0.0F + 1.0F * var1 * var1;
         this.c = 0.7F + 0.5F * var1;
      } else {
         this.j = true;
      }
   }
}
