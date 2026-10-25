package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

public class GuiOptionSlider extends GuiButton {
   private float p = 1.0F;
   public boolean o;
   private GameSettings.Options q;
   private final float r;
   private final float s;

   public GuiOptionSlider(int var1, int var2, int var3, GameSettings.Options var4) {
      this(var1, var2, var3, var4, 0.0F, 1.0F);
   }

   public GuiOptionSlider(int var1, int var2, int var3, GameSettings.Options var4, float var5, float var6) {
      super(var1, var2, var3, 150, 20, "");
      this.q = var4;
      this.r = var5;
      this.s = var6;
      Minecraft var7 = Minecraft.A();
      this.p = var4.c(var7.t.a(var4));
      this.j = var7.t.c(var4);
   }

   @Override
   protected int a(boolean var1) {
      return 0;
   }

   @Override
   protected void b(Minecraft var1, int var2, int var3) {
      if (this.m) {
         if (this.o) {
            this.p = (float)(var2 - (this.h + 4)) / (this.f - 8);
            this.p = MathHelper.a(this.p, 0.0F, 1.0F);
            float var4 = this.q.d(this.p);
            var1.t.a(this.q, var4);
            this.p = this.q.c(var4);
            this.j = var1.t.c(this.q);
         }

         var1.P().a(a);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.b(this.h + (int)(this.p * (this.f - 8)), this.i, 0, 66, 4, 20);
         this.b(this.h + (int)(this.p * (this.f - 8)) + 4, this.i, 196, 66, 4, 20);
      }
   }

   @Override
   public boolean c(Minecraft var1, int var2, int var3) {
      if (super.c(var1, var2, var3)) {
         this.p = (float)(var2 - (this.h + 4)) / (this.f - 8);
         this.p = MathHelper.a(this.p, 0.0F, 1.0F);
         var1.t.a(this.q, this.q.d(this.p));
         this.j = var1.t.c(this.q);
         this.o = true;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(int var1, int var2) {
      this.o = false;
   }
}
