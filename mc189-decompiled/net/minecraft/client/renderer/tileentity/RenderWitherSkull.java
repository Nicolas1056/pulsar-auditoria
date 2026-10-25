package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.ResourceLocation;

public class RenderWitherSkull extends Render<EntityWitherSkull> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/wither/wither.png");
   private final ModelSkeletonHead f = new ModelSkeletonHead();

   public RenderWitherSkull(RenderManager var1) {
      super(var1);
   }

   private float a(float var1, float var2, float var3) {
      float var4 = var2 - var1;

      while (var4 < -180.0F) {
         var4 += 360.0F;
      }

      while (var4 >= 180.0F) {
         var4 -= 360.0F;
      }

      return var1 + var3 * var4;
   }

   public void a(EntityWitherSkull var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      GlStateManager.p();
      float var10 = this.a(var1.A, var1.y, var9);
      float var11 = var1.B + (var1.z - var1.B) * var9;
      GlStateManager.b((float)var2, (float)var4, (float)var6);
      float var12 = 0.0625F;
      GlStateManager.B();
      GlStateManager.a(-1.0F, -1.0F, 1.0F);
      GlStateManager.d();
      this.c(var1);
      this.f.a(var1, 0.0F, 0.0F, 0.0F, var10, var11, var12);
      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityWitherSkull var1) {
      return var1.l() ? a : e;
   }
}
