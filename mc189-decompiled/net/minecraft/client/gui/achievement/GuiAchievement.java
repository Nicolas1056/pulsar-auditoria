package net.minecraft.client.gui.achievement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

public class GuiAchievement extends Gui {
   private static final ResourceLocation a = new ResourceLocation("textures/gui/achievement/achievement_background.png");
   private Minecraft f;
   private int g;
   private int h;
   private String i;
   private String j;
   private Achievement k;
   private long l;
   private RenderItem m;
   private boolean n;

   public GuiAchievement(Minecraft var1) {
      this.f = var1;
      this.m = var1.ag();
   }

   public void a(Achievement var1) {
      this.i = I18n.a("achievement.get");
      this.j = var1.e().c();
      this.l = Minecraft.J();
      this.k = var1;
      this.n = false;
   }

   public void b(Achievement var1) {
      this.i = var1.e().c();
      this.j = var1.f();
      this.l = Minecraft.J() + 2500L;
      this.k = var1;
      this.n = true;
   }

   private void c() {
      GlStateManager.b(0, 0, this.f.d, this.f.e);
      GlStateManager.n(5889);
      GlStateManager.D();
      GlStateManager.n(5888);
      GlStateManager.D();
      this.g = this.f.d;
      this.h = this.f.e;
      ScaledResolution var1 = new ScaledResolution(this.f);
      this.g = var1.a();
      this.h = var1.b();
      GlStateManager.m(256);
      GlStateManager.n(5889);
      GlStateManager.D();
      GlStateManager.a(0.0, this.g, this.h, 0.0, 1000.0, 3000.0);
      GlStateManager.n(5888);
      GlStateManager.D();
      GlStateManager.b(0.0F, 0.0F, -2000.0F);
   }

   public void a() {
      if (this.k != null && this.l != 0L && Minecraft.A().h != null) {
         double var1 = (Minecraft.J() - this.l) / 3000.0;
         if (!this.n) {
            if (var1 < 0.0 || var1 > 1.0) {
               this.l = 0L;
               return;
            }
         } else if (var1 > 0.5) {
            var1 = 0.5;
         }

         this.c();
         GlStateManager.i();
         GlStateManager.a(false);
         double var3 = var1 * 2.0;
         if (var3 > 1.0) {
            var3 = 2.0 - var3;
         }

         var3 *= 4.0;
         var3 = 1.0 - var3;
         if (var3 < 0.0) {
            var3 = 0.0;
         }

         var3 *= var3;
         var3 *= var3;
         int var5 = this.g - 160;
         int var6 = 0 - (int)(var3 * 36.0);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.w();
         this.f.P().a(a);
         GlStateManager.f();
         this.b(var5, var6, 96, 202, 160, 32);
         if (this.n) {
            this.f.k.a(this.j, var5 + 30, var6 + 7, 120, -1);
         } else {
            this.f.k.a(this.i, var5 + 30, var6 + 7, -256);
            this.f.k.a(this.j, var5 + 30, var6 + 18, -1);
         }

         RenderHelper.c();
         GlStateManager.f();
         GlStateManager.B();
         GlStateManager.g();
         GlStateManager.e();
         this.m.b(this.k.d, var5 + 8, var6 + 8);
         GlStateManager.f();
         GlStateManager.a(true);
         GlStateManager.j();
      }
   }

   public void b() {
      this.k = null;
      this.l = 0L;
   }
}
