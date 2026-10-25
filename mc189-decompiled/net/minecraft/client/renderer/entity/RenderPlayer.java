package net.minecraft.client.renderer.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;

public class RenderPlayer extends RendererLivingEntity<AbstractClientPlayer> {
   private boolean a;

   public RenderPlayer(RenderManager var1) {
      this(var1, false);
   }

   public RenderPlayer(RenderManager var1, boolean var2) {
      super(var1, new ModelPlayer(0.0F, var2), 0.5F);
      this.a = var2;
      this.a(new LayerBipedArmor(this));
      this.a(new LayerHeldItem(this));
      this.a(new LayerArrow(this));
      this.a(new LayerDeadmau5Head(this));
      this.a(new LayerCape(this));
      this.a(new LayerCustomHead(this.g().e));
   }

   public ModelPlayer g() {
      return (ModelPlayer)super.b();
   }

   public void a(AbstractClientPlayer var1, double var2, double var4, double var6, float var8, float var9) {
      if (!var1.cc() || this.b.c == var1) {
         double var10 = var4;
         if (var1.av() && !(var1 instanceof EntityPlayerSP)) {
            var10 = var4 - 0.125;
         }

         this.d(var1);
         super.a(var1, var2, var10, var6, var8, var9);
      }
   }

   private void d(AbstractClientPlayer var1) {
      ModelPlayer var2 = this.g();
      if (var1.v()) {
         var2.a(false);
         var2.e.j = true;
         var2.f.j = true;
      } else {
         ItemStack var3 = var1.bi.h();
         var2.a(true);
         var2.f.j = var1.a(EnumPlayerModelParts.g);
         var2.v.j = var1.a(EnumPlayerModelParts.b);
         var2.c.j = var1.a(EnumPlayerModelParts.e);
         var2.d.j = var1.a(EnumPlayerModelParts.f);
         var2.a.j = var1.a(EnumPlayerModelParts.c);
         var2.b.j = var1.a(EnumPlayerModelParts.d);
         var2.l = 0;
         var2.o = false;
         var2.n = var1.av();
         if (var3 == null) {
            var2.m = 0;
         } else {
            var2.m = 1;
            if (var1.bR() > 0) {
               EnumAction var4 = var3.m();
               if (var4 == EnumAction.d) {
                  var2.m = 3;
               } else if (var4 == EnumAction.e) {
                  var2.o = true;
               }
            }
         }
      }
   }

   protected ResourceLocation a(AbstractClientPlayer var1) {
      return var1.i();
   }

   @Override
   public void C_() {
      GlStateManager.b(0.0F, 0.1875F, 0.0F);
   }

   protected void a(AbstractClientPlayer var1, float var2) {
      float var3 = 0.9375F;
      GlStateManager.a(var3, var3, var3);
   }

   protected void a(AbstractClientPlayer var1, double var2, double var4, double var6, String var8, float var9, double var10) {
      if (var10 < 100.0) {
         Scoreboard var12 = var1.cp();
         ScoreObjective var13 = var12.a(2);
         if (var13 != null) {
            Score var14 = var12.c(var1.e_(), var13);
            this.a(var1, var14.c() + " " + var13.d(), var2, var4, var6, 64);
            var4 += this.c().a * 1.15F * var9;
         }
      }

      super.a(var1, var2, var4, var6, var8, var9, var10);
   }

   public void b(AbstractClientPlayer var1) {
      float var2 = 1.0F;
      GlStateManager.c(var2, var2, var2);
      ModelPlayer var3 = this.g();
      this.d(var1);
      var3.p = 0.0F;
      var3.n = false;
      var3.a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, var1);
      var3.a();
   }

   public void c(AbstractClientPlayer var1) {
      float var2 = 1.0F;
      GlStateManager.c(var2, var2, var2);
      ModelPlayer var3 = this.g();
      this.d(var1);
      var3.n = false;
      var3.p = 0.0F;
      var3.a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, var1);
      var3.b();
   }

   protected void a(AbstractClientPlayer var1, double var2, double var4, double var6) {
      if (var1.ai() && var1.bJ()) {
         super.a(var1, var2 + var1.by, var4 + var1.bZ, var6 + var1.bz);
      } else {
         super.a(var1, var2, var4, var6);
      }
   }

   protected void a(AbstractClientPlayer var1, float var2, float var3, float var4) {
      if (var1.ai() && var1.bJ()) {
         GlStateManager.b(var1.ce(), 0.0F, 1.0F, 0.0F);
         GlStateManager.b(this.b(var1), 0.0F, 0.0F, 1.0F);
         GlStateManager.b(270.0F, 0.0F, 1.0F, 0.0F);
      } else {
         super.a(var1, var2, var3, var4);
      }
   }
}
