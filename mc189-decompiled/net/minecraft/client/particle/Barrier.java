package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Barrier extends EntityFX {
   protected Barrier(World var1, double var2, double var4, double var6, Item var8) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
      this.a(Minecraft.A().ag().a().a(var8));
      this.ar = this.as = this.at = 1.0F;
      this.v = this.w = this.x = 0.0;
      this.i = 0.0F;
      this.g = 80;
   }

   @Override
   public int a() {
      return 1;
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = this.av.e();
      float var10 = this.av.f();
      float var11 = this.av.g();
      float var12 = this.av.h();
      float var13 = 0.5F;
      float var14 = (float)(this.p + (this.s - this.p) * var3 - aw);
      float var15 = (float)(this.q + (this.t - this.q) * var3 - ax);
      float var16 = (float)(this.r + (this.u - this.r) * var3 - ay);
      int var17 = this.b(var3);
      int var18 = var17 >> 16 & 65535;
      int var19 = var17 & 65535;
      var1.b((double)(var14 - var4 * 0.5F - var7 * 0.5F), (double)(var15 - var5 * 0.5F), (double)(var16 - var6 * 0.5F - var8 * 0.5F))
         .a((double)var10, (double)var12)
         .a(this.ar, this.as, this.at, 1.0F)
         .a(var18, var19)
         .d();
      var1.b((double)(var14 - var4 * 0.5F + var7 * 0.5F), (double)(var15 + var5 * 0.5F), (double)(var16 - var6 * 0.5F + var8 * 0.5F))
         .a((double)var10, (double)var11)
         .a(this.ar, this.as, this.at, 1.0F)
         .a(var18, var19)
         .d();
      var1.b((double)(var14 + var4 * 0.5F + var7 * 0.5F), (double)(var15 + var5 * 0.5F), (double)(var16 + var6 * 0.5F + var8 * 0.5F))
         .a((double)var9, (double)var11)
         .a(this.ar, this.as, this.at, 1.0F)
         .a(var18, var19)
         .d();
      var1.b((double)(var14 + var4 * 0.5F - var7 * 0.5F), (double)(var15 - var5 * 0.5F), (double)(var16 + var6 * 0.5F - var8 * 0.5F))
         .a((double)var9, (double)var12)
         .a(this.ar, this.as, this.at, 1.0F)
         .a(var18, var19)
         .d();
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new Barrier(var2, var3, var5, var7, Item.a(Blocks.cv));
      }
   }
}
