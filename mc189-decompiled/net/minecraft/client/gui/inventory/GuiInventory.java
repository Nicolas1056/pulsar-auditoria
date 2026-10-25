package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInventory extends InventoryEffectRenderer {
   private float u;
   private float v;

   public GuiInventory(EntityPlayer var1) {
      super(var1.bj);
      this.p = true;
   }

   @Override
   public void e() {
      if (this.j.c.h()) {
         this.j.a(new GuiContainerCreative(this.j.h));
      }

      this.a();
   }

   @Override
   public void b() {
      this.n.clear();
      if (this.j.c.h()) {
         this.j.a(new GuiContainerCreative(this.j.h));
      } else {
         super.b();
      }
   }

   @Override
   protected void b(int var1, int var2) {
      this.q.a(I18n.a("container.crafting"), 86, 16, 4210752);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      super.a(var1, var2, var3);
      this.u = var1;
      this.v = var2;
   }

   @Override
   protected void a(float var1, int var2, int var3) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(a);
      int var4 = this.i;
      int var5 = this.r;
      this.b(var4, var5, 0, 0, this.f, this.g);
      a(var4 + 51, var5 + 75, 30, var4 + 51 - this.u, var5 + 75 - 50 - this.v, this.j.h);
   }

   public static void a(int var0, int var1, int var2, float var3, float var4, EntityLivingBase var5) {
      GlStateManager.g();
      GlStateManager.E();
      GlStateManager.b((float)var0, (float)var1, 50.0F);
      GlStateManager.a((float)(-var2), (float)var2, (float)var2);
      GlStateManager.b(180.0F, 0.0F, 0.0F, 1.0F);
      float var6 = var5.aI;
      float var7 = var5.y;
      float var8 = var5.z;
      float var9 = var5.aL;
      float var10 = var5.aK;
      GlStateManager.b(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.b();
      GlStateManager.b(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(-((float)Math.atan(var4 / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
      var5.aI = (float)Math.atan(var3 / 40.0F) * 20.0F;
      var5.y = (float)Math.atan(var3 / 40.0F) * 40.0F;
      var5.z = -((float)Math.atan(var4 / 40.0F)) * 20.0F;
      var5.aK = var5.y;
      var5.aL = var5.y;
      GlStateManager.b(0.0F, 0.0F, 0.0F);
      RenderManager var11 = Minecraft.A().af();
      var11.a(180.0F);
      var11.a(false);
      var11.a(var5, 0.0, 0.0, 0.0, 0.0F, 1.0F);
      var11.a(true);
      var5.aI = var6;
      var5.y = var7;
      var5.z = var8;
      var5.aL = var9;
      var5.aK = var10;
      GlStateManager.F();
      RenderHelper.a();
      GlStateManager.C();
      GlStateManager.g(OpenGlHelper.r);
      GlStateManager.x();
      GlStateManager.g(OpenGlHelper.q);
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 0) {
         this.j.a(new GuiAchievements(this, this.j.h.x()));
      }

      if (var1.k == 1) {
         this.j.a(new GuiStats(this, this.j.h.x()));
      }
   }
}
