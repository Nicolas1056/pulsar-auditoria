package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityBreakingFX extends EntityFX {
   protected EntityBreakingFX(World var1, double var2, double var4, double var6, Item var8) {
      this(var1, var2, var4, var6, var8, 0);
   }

   protected EntityBreakingFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, Item var14, int var15) {
      this(var1, var2, var4, var6, var14, var15);
      this.v *= 0.1F;
      this.w *= 0.1F;
      this.x *= 0.1F;
      this.v += var8;
      this.w += var10;
      this.x += var12;
   }

   protected EntityBreakingFX(World var1, double var2, double var4, double var6, Item var8, int var9) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
      this.a(Minecraft.A().ag().a().a(var8, var9));
      this.ar = this.as = this.at = 1.0F;
      this.i = Blocks.aJ.I;
      this.h /= 2.0F;
   }

   @Override
   public int a() {
      return 1;
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (this.b + this.d / 4.0F) / 16.0F;
      float var10 = var9 + 0.015609375F;
      float var11 = (this.c + this.e / 4.0F) / 16.0F;
      float var12 = var11 + 0.015609375F;
      float var13 = 0.1F * this.h;
      if (this.av != null) {
         var9 = this.av.a((double)(this.d / 4.0F * 16.0F));
         var10 = this.av.a((double)((this.d + 1.0F) / 4.0F * 16.0F));
         var11 = this.av.b((double)(this.e / 4.0F * 16.0F));
         var12 = this.av.b((double)((this.e + 1.0F) / 4.0F * 16.0F));
      }

      float var14 = (float)(this.p + (this.s - this.p) * var3 - aw);
      float var15 = (float)(this.q + (this.t - this.q) * var3 - ax);
      float var16 = (float)(this.r + (this.u - this.r) * var3 - ay);
      int var17 = this.b(var3);
      int var18 = var17 >> 16 & 65535;
      int var19 = var17 & 65535;
      var1.b((double)(var14 - var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 - var6 * var13 - var8 * var13))
         .a((double)var9, (double)var12)
         .a(this.ar, this.as, this.at, 1.0F)
         .a(var18, var19)
         .d();
      var1.b((double)(var14 - var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 - var6 * var13 + var8 * var13))
         .a((double)var9, (double)var11)
         .a(this.ar, this.as, this.at, 1.0F)
         .a(var18, var19)
         .d();
      var1.b((double)(var14 + var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 + var6 * var13 + var8 * var13))
         .a((double)var10, (double)var11)
         .a(this.ar, this.as, this.at, 1.0F)
         .a(var18, var19)
         .d();
      var1.b((double)(var14 + var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 + var6 * var13 - var8 * var13))
         .a((double)var10, (double)var12)
         .a(this.ar, this.as, this.at, 1.0F)
         .a(var18, var19)
         .d();
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         int var16 = var15.length > 1 ? var15[1] : 0;
         return new EntityBreakingFX(var2, var3, var5, var7, var9, var11, var13, Item.b(var15[0]), var16);
      }
   }

   public static class SlimeFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityBreakingFX(var2, var3, var5, var7, Items.aM);
      }
   }

   public static class SnowballFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityBreakingFX(var2, var3, var5, var7, Items.aD);
      }
   }
}
