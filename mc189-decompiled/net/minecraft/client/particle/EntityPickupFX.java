package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPickupFX extends EntityFX {
   private Entity a;
   private Entity az;
   private int aA;
   private int aB;
   private float aC;
   private RenderManager aD = Minecraft.A().af();

   public EntityPickupFX(World var1, Entity var2, Entity var3, float var4) {
      super(var1, var2.s, var2.t, var2.u, var2.v, var2.w, var2.x);
      this.a = var2;
      this.az = var3;
      this.aB = 3;
      this.aC = var4;
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (this.aA + var3) / this.aB;
      var9 *= var9;
      double var10 = this.a.s;
      double var12 = this.a.t;
      double var14 = this.a.u;
      double var16 = this.az.P + (this.az.s - this.az.P) * var3;
      double var18 = this.az.Q + (this.az.t - this.az.Q) * var3 + this.aC;
      double var20 = this.az.R + (this.az.u - this.az.R) * var3;
      double var22 = var10 + (var16 - var10) * var9;
      double var24 = var12 + (var18 - var12) * var9;
      double var26 = var14 + (var20 - var14) * var9;
      int var28 = this.b(var3);
      int var29 = var28 % 65536;
      int var30 = var28 / 65536;
      OpenGlHelper.a(OpenGlHelper.r, var29 / 1.0F, var30 / 1.0F);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      var22 -= aw;
      var24 -= ax;
      var26 -= ay;
      this.aD.a(this.a, (float)var22, (float)var24, (float)var26, this.a.y, var3);
   }

   @Override
   public void t_() {
      this.aA++;
      if (this.aA == this.aB) {
         this.J();
      }
   }

   @Override
   public int a() {
      return 3;
   }
}
