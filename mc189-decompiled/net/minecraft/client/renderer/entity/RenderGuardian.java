package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelGuardian;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderGuardian extends RenderLiving<EntityGuardian> {
   private static final ResourceLocation e = new ResourceLocation("textures/entity/guardian.png");
   private static final ResourceLocation j = new ResourceLocation("textures/entity/guardian_elder.png");
   private static final ResourceLocation k = new ResourceLocation("textures/entity/guardian_beam.png");
   int a = ((ModelGuardian)this.f).a();

   public RenderGuardian(RenderManager var1) {
      super(var1, new ModelGuardian(), 0.5F);
   }

   public boolean a(EntityGuardian var1, ICamera var2, double var3, double var5, double var7) {
      if (super.a(var1, var2, var3, var5, var7)) {
         return true;
      } else {
         if (var1.cp()) {
            EntityLivingBase var9 = var1.cq();
            if (var9 != null) {
               Vec3 var10 = this.a(var9, var9.K * 0.5, 1.0F);
               Vec3 var11 = this.a(var1, var1.aS(), 1.0F);
               if (var2.a(AxisAlignedBB.a(var11.a, var11.b, var11.c, var10.a, var10.b, var10.c))) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private Vec3 a(EntityLivingBase var1, double var2, float var4) {
      double var5 = var1.P + (var1.s - var1.P) * var4;
      double var7 = var2 + var1.Q + (var1.t - var1.Q) * var4;
      double var9 = var1.R + (var1.u - var1.R) * var4;
      return new Vec3(var5, var7, var9);
   }

   public void a(EntityGuardian var1, double var2, double var4, double var6, float var8, float var9) {
      if (this.a != ((ModelGuardian)this.f).a()) {
         this.f = new ModelGuardian();
         this.a = ((ModelGuardian)this.f).a();
      }

      super.a(var1, var2, var4, var6, var8, var9);
      EntityLivingBase var10 = var1.cq();
      if (var10 != null) {
         float var11 = var1.q(var9);
         Tessellator var12 = Tessellator.a();
         WorldRenderer var13 = var12.c();
         this.a(k);
         GL11.glTexParameterf(3553, 10242, 10497.0F);
         GL11.glTexParameterf(3553, 10243, 10497.0F);
         GlStateManager.f();
         GlStateManager.p();
         GlStateManager.k();
         GlStateManager.a(true);
         float var14 = 240.0F;
         OpenGlHelper.a(OpenGlHelper.r, var14, var14);
         GlStateManager.a(770, 1, 1, 0);
         float var15 = (float)var1.o.K() + var9;
         float var16 = var15 * 0.5F % 1.0F;
         float var17 = var1.aS();
         GlStateManager.E();
         GlStateManager.b((float)var2, (float)var4 + var17, (float)var6);
         Vec3 var18 = this.a(var10, var10.K * 0.5, var9);
         Vec3 var19 = this.a(var1, var17, var9);
         Vec3 var20 = var18.d(var19);
         double var21 = var20.b() + 1.0;
         var20 = var20.a();
         float var23 = (float)Math.acos(var20.b);
         float var24 = (float)Math.atan2(var20.c, var20.a);
         GlStateManager.b(((float) (Math.PI / 2) + -var24) * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
         GlStateManager.b(var23 * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
         byte var25 = 1;
         double var26 = var15 * 0.05 * (1.0 - (var25 & 1) * 2.5);
         var13.a(7, DefaultVertexFormats.i);
         float var28 = var11 * var11;
         int var29 = 64 + (int)(var28 * 240.0F);
         int var30 = 32 + (int)(var28 * 192.0F);
         int var31 = 128 - (int)(var28 * 64.0F);
         double var32 = var25 * 0.2;
         double var34 = var32 * 1.41;
         double var36 = 0.0 + Math.cos(var26 + (Math.PI * 3.0 / 4.0)) * var34;
         double var38 = 0.0 + Math.sin(var26 + (Math.PI * 3.0 / 4.0)) * var34;
         double var40 = 0.0 + Math.cos(var26 + (Math.PI / 4)) * var34;
         double var42 = 0.0 + Math.sin(var26 + (Math.PI / 4)) * var34;
         double var44 = 0.0 + Math.cos(var26 + (Math.PI * 5.0 / 4.0)) * var34;
         double var46 = 0.0 + Math.sin(var26 + (Math.PI * 5.0 / 4.0)) * var34;
         double var48 = 0.0 + Math.cos(var26 + (Math.PI * 7.0 / 4.0)) * var34;
         double var50 = 0.0 + Math.sin(var26 + (Math.PI * 7.0 / 4.0)) * var34;
         double var52 = 0.0 + Math.cos(var26 + Math.PI) * var32;
         double var54 = 0.0 + Math.sin(var26 + Math.PI) * var32;
         double var56 = 0.0 + Math.cos(var26 + 0.0) * var32;
         double var58 = 0.0 + Math.sin(var26 + 0.0) * var32;
         double var60 = 0.0 + Math.cos(var26 + (Math.PI / 2)) * var32;
         double var62 = 0.0 + Math.sin(var26 + (Math.PI / 2)) * var32;
         double var64 = 0.0 + Math.cos(var26 + (Math.PI * 3.0 / 2.0)) * var32;
         double var66 = 0.0 + Math.sin(var26 + (Math.PI * 3.0 / 2.0)) * var32;
         double var70 = 0.0;
         double var72 = 0.4999;
         double var74 = -1.0F + var16;
         double var76 = var21 * (0.5 / var32) + var74;
         var13.b(var52, var21, var54).a(0.4999, var76).b(var29, var30, var31, 255).d();
         var13.b(var52, 0.0, var54).a(0.4999, var74).b(var29, var30, var31, 255).d();
         var13.b(var56, 0.0, var58).a(0.0, var74).b(var29, var30, var31, 255).d();
         var13.b(var56, var21, var58).a(0.0, var76).b(var29, var30, var31, 255).d();
         var13.b(var60, var21, var62).a(0.4999, var76).b(var29, var30, var31, 255).d();
         var13.b(var60, 0.0, var62).a(0.4999, var74).b(var29, var30, var31, 255).d();
         var13.b(var64, 0.0, var66).a(0.0, var74).b(var29, var30, var31, 255).d();
         var13.b(var64, var21, var66).a(0.0, var76).b(var29, var30, var31, 255).d();
         double var78 = 0.0;
         if (var1.W % 2 == 0) {
            var78 = 0.5;
         }

         var13.b(var36, var21, var38).a(0.5, var78 + 0.5).b(var29, var30, var31, 255).d();
         var13.b(var40, var21, var42).a(1.0, var78 + 0.5).b(var29, var30, var31, 255).d();
         var13.b(var48, var21, var50).a(1.0, var78).b(var29, var30, var31, 255).d();
         var13.b(var44, var21, var46).a(0.5, var78).b(var29, var30, var31, 255).d();
         var12.b();
         GlStateManager.F();
      }
   }

   protected void a(EntityGuardian var1, float var2) {
      if (var1.cn()) {
         GlStateManager.a(2.35F, 2.35F, 2.35F);
      }
   }

   protected ResourceLocation a(EntityGuardian var1) {
      return var1.cn() ? j : e;
   }
}
