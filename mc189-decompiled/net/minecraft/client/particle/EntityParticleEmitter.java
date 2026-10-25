package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityParticleEmitter extends EntityFX {
   private Entity a;
   private int az;
   private int aA;
   private EnumParticleTypes aB;

   public EntityParticleEmitter(World var1, Entity var2, EnumParticleTypes var3) {
      super(var1, var2.s, var2.aR().b + var2.K / 2.0F, var2.u, var2.v, var2.w, var2.x);
      this.a = var2;
      this.aA = 3;
      this.aB = var3;
      this.t_();
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
   }

   @Override
   public void t_() {
      for (int var1 = 0; var1 < 16; var1++) {
         double var2 = this.V.nextFloat() * 2.0F - 1.0F;
         double var4 = this.V.nextFloat() * 2.0F - 1.0F;
         double var6 = this.V.nextFloat() * 2.0F - 1.0F;
         if (!(var2 * var2 + var4 * var4 + var6 * var6 > 1.0)) {
            double var8 = this.a.s + var2 * this.a.J / 4.0;
            double var10 = this.a.aR().b + this.a.K / 2.0F + var4 * this.a.K / 4.0;
            double var12 = this.a.u + var6 * this.a.J / 4.0;
            this.o.a(this.aB, false, var8, var10, var12, var2, var4 + 0.2, var6);
         }
      }

      this.az++;
      if (this.az >= this.aA) {
         this.J();
      }
   }

   @Override
   public int a() {
      return 3;
   }
}
