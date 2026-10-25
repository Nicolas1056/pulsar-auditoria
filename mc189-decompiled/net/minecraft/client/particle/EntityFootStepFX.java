package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFootStepFX extends EntityFX {
   private static final ResourceLocation a = new ResourceLocation("textures/particle/footprint.png");
   private int az;
   private int aA;
   private TextureManager aB;

   protected EntityFootStepFX(TextureManager var1, World var2, double var3, double var5, double var7) {
      super(var2, var3, var5, var7, 0.0, 0.0, 0.0);
      this.aB = var1;
      this.v = this.w = this.x = 0.0;
      this.aA = 200;
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (this.az + var3) / this.aA;
      var9 *= var9;
      float var10 = 2.0F - var9 * 2.0F;
      if (var10 > 1.0F) {
         var10 = 1.0F;
      }

      var10 *= 0.2F;
      GlStateManager.f();
      float var11 = 0.125F;
      float var12 = (float)(this.s - aw);
      float var13 = (float)(this.t - ax);
      float var14 = (float)(this.u - ay);
      float var15 = this.o.o(new BlockPos(this));
      this.aB.a(a);
      GlStateManager.l();
      GlStateManager.b(770, 771);
      var1.a(7, DefaultVertexFormats.i);
      var1.b((double)(var12 - 0.125F), (double)var13, (double)(var14 + 0.125F)).a(0.0, 1.0).a(var15, var15, var15, var10).d();
      var1.b((double)(var12 + 0.125F), (double)var13, (double)(var14 + 0.125F)).a(1.0, 1.0).a(var15, var15, var15, var10).d();
      var1.b((double)(var12 + 0.125F), (double)var13, (double)(var14 - 0.125F)).a(1.0, 0.0).a(var15, var15, var15, var10).d();
      var1.b((double)(var12 - 0.125F), (double)var13, (double)(var14 - 0.125F)).a(0.0, 0.0).a(var15, var15, var15, var10).d();
      Tessellator.a().b();
      GlStateManager.k();
      GlStateManager.e();
   }

   @Override
   public void t_() {
      this.az++;
      if (this.az == this.aA) {
         this.J();
      }
   }

   @Override
   public int a() {
      return 3;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityFootStepFX(Minecraft.A().P(), var2, var3, var5, var7);
      }
   }
}
