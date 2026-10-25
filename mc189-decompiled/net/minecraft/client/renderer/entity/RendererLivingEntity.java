package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public abstract class RendererLivingEntity<T extends EntityLivingBase> extends Render<T> {
   private static final Logger a = LogManager.getLogger();
   private static final DynamicTexture e = new DynamicTexture(16, 16);
   protected ModelBase f;
   protected FloatBuffer g = GLAllocation.h(4);
   protected List<LayerRenderer<T>> h = Lists.newArrayList();
   protected boolean i = false;

   public RendererLivingEntity(RenderManager var1, ModelBase var2, float var3) {
      super(var1);
      this.f = var2;
      this.c = var3;
   }

   protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean a(U var1) {
      return this.h.add(var1);
   }

   protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean b(U var1) {
      return this.h.remove(var1);
   }

   public ModelBase b() {
      return this.f;
   }

   protected float a(float var1, float var2, float var3) {
      float var4 = var2 - var1;

      while (var4 < -180.0F) {
         var4 += 360.0F;
      }

      while (var4 >= 180.0F) {
         var4 -= 360.0F;
      }

      return var1 + var3 * var4;
   }

   public void C_() {
   }

   public void a(T var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      GlStateManager.p();
      this.f.p = this.d((T)var1, var9);
      this.f.q = var1.au();
      this.f.r = var1.j_();

      try {
         float var10 = this.a(var1.aJ, var1.aI, var9);
         float var11 = this.a(var1.aL, var1.aK, var9);
         float var12 = var11 - var10;
         if (var1.au() && var1.m instanceof EntityLivingBase) {
            EntityLivingBase var13 = (EntityLivingBase)var1.m;
            var10 = this.a(var13.aJ, var13.aI, var9);
            var12 = var11 - var10;
            float var14 = MathHelper.g(var12);
            if (var14 < -85.0F) {
               var14 = -85.0F;
            }

            if (var14 >= 85.0F) {
               var14 = 85.0F;
            }

            var10 = var11 - var14;
            if (var14 * var14 > 2500.0F) {
               var10 += var14 * 0.2F;
            }
         }

         float var21 = var1.B + (var1.z - var1.B) * var9;
         this.a((T)var1, var2, var4, var6);
         float var22 = this.b((T)var1, var9);
         this.a((T)var1, var22, var10, var9);
         GlStateManager.B();
         GlStateManager.a(-1.0F, -1.0F, 1.0F);
         this.a((T)var1, var9);
         float var15 = 0.0625F;
         GlStateManager.b(0.0F, -1.5078125F, 0.0F);
         float var16 = var1.aA + (var1.aB - var1.aA) * var9;
         float var17 = var1.aC - var1.aB * (1.0F - var9);
         if (var1.j_()) {
            var17 *= 3.0F;
         }

         if (var16 > 1.0F) {
            var16 = 1.0F;
         }

         GlStateManager.d();
         this.f.a(var1, var17, var16, var9);
         this.f.a(var17, var16, var22, var12, var21, 0.0625F, var1);
         if (this.i) {
            boolean var23 = this.c((T)var1);
            this.a((T)var1, var17, var16, var22, var12, var21, 0.0625F);
            if (var23) {
               this.e();
            }
         } else {
            boolean var18 = this.c((T)var1, var9);
            this.a((T)var1, var17, var16, var22, var12, var21, 0.0625F);
            if (var18) {
               this.f();
            }

            GlStateManager.a(true);
            if (!(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).v()) {
               this.a((T)var1, var17, var16, var9, var22, var12, var21, 0.0625F);
            }
         }

         GlStateManager.C();
      } catch (Exception var19) {
         a.error("Couldn't render entity", var19);
      }

      GlStateManager.g(OpenGlHelper.r);
      GlStateManager.w();
      GlStateManager.g(OpenGlHelper.q);
      GlStateManager.o();
      GlStateManager.F();
      if (!this.i) {
         super.a((T)var1, var2, var4, var6, var8, var9);
      }
   }

   protected boolean c(T var1) {
      int var2 = 16777215;
      if (var1 instanceof EntityPlayer) {
         ScorePlayerTeam var3 = (ScorePlayerTeam)var1.bO();
         if (var3 != null) {
            String var4 = FontRenderer.b(var3.e());
            if (var4.length() >= 2) {
               var2 = this.c().b(var4.charAt(1));
            }
         }
      }

      float var6 = (var2 >> 16 & 0xFF) / 255.0F;
      float var7 = (var2 >> 8 & 0xFF) / 255.0F;
      float var5 = (var2 & 0xFF) / 255.0F;
      GlStateManager.f();
      GlStateManager.g(OpenGlHelper.q);
      GlStateManager.c(var6, var7, var5, 1.0F);
      GlStateManager.x();
      GlStateManager.g(OpenGlHelper.r);
      GlStateManager.x();
      GlStateManager.g(OpenGlHelper.q);
      return true;
   }

   protected void e() {
      GlStateManager.e();
      GlStateManager.g(OpenGlHelper.q);
      GlStateManager.w();
      GlStateManager.g(OpenGlHelper.r);
      GlStateManager.w();
      GlStateManager.g(OpenGlHelper.q);
   }

   protected void a(T var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      boolean var8 = !var1.ax();
      boolean var9 = !var8 && !var1.f(Minecraft.A().h);
      if (var8 || var9) {
         if (!this.c((T)var1)) {
            return;
         }

         if (var9) {
            GlStateManager.E();
            GlStateManager.c(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.a(false);
            GlStateManager.l();
            GlStateManager.b(770, 771);
            GlStateManager.a(516, 0.003921569F);
         }

         this.f.a(var1, var2, var3, var4, var5, var6, var7);
         if (var9) {
            GlStateManager.k();
            GlStateManager.a(516, 0.1F);
            GlStateManager.F();
            GlStateManager.a(true);
         }
      }
   }

   protected boolean c(T var1, float var2) {
      return this.a((T)var1, var2, true);
   }

   protected boolean a(T var1, float var2, boolean var3) {
      float var4 = var1.c(var2);
      int var5 = this.a((T)var1, var4, var2);
      boolean var6 = (var5 >> 24 & 0xFF) > 0;
      boolean var7 = var1.au > 0 || var1.ax > 0;
      if (!var6 && !var7) {
         return false;
      } else if (!var6 && !var3) {
         return false;
      } else {
         GlStateManager.g(OpenGlHelper.q);
         GlStateManager.w();
         GL11.glTexEnvi(8960, 8704, OpenGlHelper.t);
         GL11.glTexEnvi(8960, OpenGlHelper.y, 8448);
         GL11.glTexEnvi(8960, OpenGlHelper.z, OpenGlHelper.q);
         GL11.glTexEnvi(8960, OpenGlHelper.A, OpenGlHelper.v);
         GL11.glTexEnvi(8960, OpenGlHelper.C, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.D, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.F, 7681);
         GL11.glTexEnvi(8960, OpenGlHelper.G, OpenGlHelper.q);
         GL11.glTexEnvi(8960, OpenGlHelper.J, 770);
         GlStateManager.g(OpenGlHelper.r);
         GlStateManager.w();
         GL11.glTexEnvi(8960, 8704, OpenGlHelper.t);
         GL11.glTexEnvi(8960, OpenGlHelper.y, OpenGlHelper.u);
         GL11.glTexEnvi(8960, OpenGlHelper.z, OpenGlHelper.w);
         GL11.glTexEnvi(8960, OpenGlHelper.A, OpenGlHelper.x);
         GL11.glTexEnvi(8960, OpenGlHelper.B, OpenGlHelper.w);
         GL11.glTexEnvi(8960, OpenGlHelper.C, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.D, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.E, 770);
         GL11.glTexEnvi(8960, OpenGlHelper.F, 7681);
         GL11.glTexEnvi(8960, OpenGlHelper.G, OpenGlHelper.x);
         GL11.glTexEnvi(8960, OpenGlHelper.J, 770);
         ((Buffer)this.g).position(0);
         if (var7) {
            this.g.put(1.0F);
            this.g.put(0.0F);
            this.g.put(0.0F);
            this.g.put(0.3F);
         } else {
            float var8 = (var5 >> 24 & 0xFF) / 255.0F;
            float var9 = (var5 >> 16 & 0xFF) / 255.0F;
            float var10 = (var5 >> 8 & 0xFF) / 255.0F;
            float var11 = (var5 & 0xFF) / 255.0F;
            this.g.put(var9);
            this.g.put(var10);
            this.g.put(var11);
            this.g.put(1.0F - var8);
         }

         ((Buffer)this.g).flip();
         GL11.glTexEnv(8960, 8705, this.g);
         GlStateManager.g(OpenGlHelper.s);
         GlStateManager.w();
         GlStateManager.i(e.b());
         GL11.glTexEnvi(8960, 8704, OpenGlHelper.t);
         GL11.glTexEnvi(8960, OpenGlHelper.y, 8448);
         GL11.glTexEnvi(8960, OpenGlHelper.z, OpenGlHelper.x);
         GL11.glTexEnvi(8960, OpenGlHelper.A, OpenGlHelper.r);
         GL11.glTexEnvi(8960, OpenGlHelper.C, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.D, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.F, 7681);
         GL11.glTexEnvi(8960, OpenGlHelper.G, OpenGlHelper.x);
         GL11.glTexEnvi(8960, OpenGlHelper.J, 770);
         GlStateManager.g(OpenGlHelper.q);
         return true;
      }
   }

   protected void f() {
      GlStateManager.g(OpenGlHelper.q);
      GlStateManager.w();
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.t);
      GL11.glTexEnvi(8960, OpenGlHelper.y, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.z, OpenGlHelper.q);
      GL11.glTexEnvi(8960, OpenGlHelper.A, OpenGlHelper.v);
      GL11.glTexEnvi(8960, OpenGlHelper.C, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.D, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.F, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.G, OpenGlHelper.q);
      GL11.glTexEnvi(8960, OpenGlHelper.H, OpenGlHelper.v);
      GL11.glTexEnvi(8960, OpenGlHelper.J, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.K, 770);
      GlStateManager.g(OpenGlHelper.r);
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.t);
      GL11.glTexEnvi(8960, OpenGlHelper.y, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.C, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.D, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.z, 5890);
      GL11.glTexEnvi(8960, OpenGlHelper.A, OpenGlHelper.x);
      GL11.glTexEnvi(8960, OpenGlHelper.F, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.J, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.G, 5890);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.g(OpenGlHelper.s);
      GlStateManager.x();
      GlStateManager.i(0);
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.t);
      GL11.glTexEnvi(8960, OpenGlHelper.y, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.C, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.D, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.z, 5890);
      GL11.glTexEnvi(8960, OpenGlHelper.A, OpenGlHelper.x);
      GL11.glTexEnvi(8960, OpenGlHelper.F, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.J, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.G, 5890);
      GlStateManager.g(OpenGlHelper.q);
   }

   protected void a(T var1, double var2, double var4, double var6) {
      GlStateManager.b((float)var2, (float)var4, (float)var6);
   }

   protected void a(T var1, float var2, float var3, float var4) {
      GlStateManager.b(180.0F - var3, 0.0F, 1.0F, 0.0F);
      if (var1.ax > 0) {
         float var5 = (var1.ax + var4 - 1.0F) / 20.0F * 1.6F;
         var5 = MathHelper.c(var5);
         if (var5 > 1.0F) {
            var5 = 1.0F;
         }

         GlStateManager.b(var5 * this.b((T)var1), 0.0F, 0.0F, 1.0F);
      } else {
         String var7 = EnumChatFormatting.a(var1.e_());
         if (var7 != null
            && (var7.equals("Dinnerbone") || var7.equals("Grumm"))
            && (!(var1 instanceof EntityPlayer) || ((EntityPlayer)var1).a(EnumPlayerModelParts.a))) {
            GlStateManager.b(0.0F, var1.K + 0.1F, 0.0F);
            GlStateManager.b(180.0F, 0.0F, 0.0F, 1.0F);
         }
      }
   }

   protected float d(T var1, float var2) {
      return var1.l(var2);
   }

   protected float b(T var1, float var2) {
      return var1.W + var2;
   }

   protected void a(T var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      for (LayerRenderer var10 : this.h) {
         boolean var11 = this.a((T)var1, var4, var10.b());
         var10.a(var1, var2, var3, var4, var5, var6, var7, var8);
         if (var11) {
            this.f();
         }
      }
   }

   protected float b(T var1) {
      return 90.0F;
   }

   protected int a(T var1, float var2, float var3) {
      return 0;
   }

   protected void a(T var1, float var2) {
   }

   public void b(T var1, double var2, double var4, double var6) {
      if (this.a(var1)) {
         double var8 = var1.h(this.b.c);
         float var10 = var1.av() ? 32.0F : 64.0F;
         if (!(var8 >= var10 * var10)) {
            String var11 = var1.f_().d();
            float var12 = 0.02666667F;
            GlStateManager.a(516, 0.1F);
            if (var1.av()) {
               FontRenderer var13 = this.c();
               GlStateManager.E();
               GlStateManager.b((float)var2, (float)var4 + var1.K + 0.5F - (var1.j_() ? var1.K / 2.0F : 0.0F), (float)var6);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GlStateManager.b(-this.b.e, 0.0F, 1.0F, 0.0F);
               GlStateManager.b(this.b.f, 1.0F, 0.0F, 0.0F);
               GlStateManager.a(-0.02666667F, -0.02666667F, 0.02666667F);
               GlStateManager.b(0.0F, 9.374999F, 0.0F);
               GlStateManager.f();
               GlStateManager.a(false);
               GlStateManager.l();
               GlStateManager.x();
               GlStateManager.a(770, 771, 1, 0);
               int var14 = var13.a(var11) / 2;
               Tessellator var15 = Tessellator.a();
               WorldRenderer var16 = var15.c();
               var16.a(7, DefaultVertexFormats.f);
               var16.b((double)(-var14 - 1), -1.0, 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
               var16.b((double)(-var14 - 1), 8.0, 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
               var16.b((double)(var14 + 1), 8.0, 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
               var16.b((double)(var14 + 1), -1.0, 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
               var15.b();
               GlStateManager.w();
               GlStateManager.a(true);
               var13.a(var11, -var13.a(var11) / 2, 0, 553648127);
               GlStateManager.e();
               GlStateManager.k();
               GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.F();
            } else {
               this.a((T)var1, var2, var4 - (var1.j_() ? var1.K / 2.0F : 0.0), var6, var11, 0.02666667F, var8);
            }
         }
      }
   }

   protected boolean a(T var1) {
      EntityPlayerSP var2 = Minecraft.A().h;
      if (var1 instanceof EntityPlayer && var1 != var2) {
         Team var3 = var1.bO();
         Team var4 = var2.bO();
         if (var3 != null) {
            Team.EnumVisible var5 = var3.i();
            switch (var5) {
               case a:
                  return true;
               case b:
                  return false;
               case c:
                  return var4 == null || var3.a(var4);
               case d:
                  return var4 == null || !var3.a(var4);
               default:
                  return true;
            }
         }
      }

      return Minecraft.v() && var1 != this.b.c && !var1.f(var2) && var1.l == null;
   }

   public void a(boolean var1) {
      this.i = var1;
   }

   static {
      int[] var0 = e.e();

      for (int var1 = 0; var1 < 256; var1++) {
         var0[var1] = -1;
      }

      e.d();
   }
}
