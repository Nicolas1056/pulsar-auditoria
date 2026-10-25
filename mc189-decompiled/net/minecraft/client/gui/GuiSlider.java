package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSlider extends GuiButton {
   private float p = 1.0F;
   public boolean o;
   private String q;
   private final float r;
   private final float s;
   private final GuiPageButtonList.GuiResponder t;
   private GuiSlider.FormatHelper u;

   public GuiSlider(
      GuiPageButtonList.GuiResponder var1, int var2, int var3, int var4, String var5, float var6, float var7, float var8, GuiSlider.FormatHelper var9
   ) {
      super(var2, var3, var4, 150, 20, "");
      this.q = var5;
      this.r = var6;
      this.s = var7;
      this.p = (var8 - var6) / (var7 - var6);
      this.u = var9;
      this.t = var1;
      this.j = this.e();
   }

   public float c() {
      return this.r + (this.s - this.r) * this.p;
   }

   public void a(float var1, boolean var2) {
      this.p = (var1 - this.r) / (this.s - this.r);
      this.j = this.e();
      if (var2) {
         this.t.a(this.k, this.c());
      }
   }

   public float d() {
      return this.p;
   }

   private String e() {
      return this.u == null ? I18n.a(this.q) + ": " + this.c() : this.u.a(this.k, I18n.a(this.q), this.c());
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
            if (this.p < 0.0F) {
               this.p = 0.0F;
            }

            if (this.p > 1.0F) {
               this.p = 1.0F;
            }

            this.j = this.e();
            this.t.a(this.k, this.c());
         }

         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.b(this.h + (int)(this.p * (this.f - 8)), this.i, 0, 66, 4, 20);
         this.b(this.h + (int)(this.p * (this.f - 8)) + 4, this.i, 196, 66, 4, 20);
      }
   }

   public void a(float var1) {
      this.p = var1;
      this.j = this.e();
      this.t.a(this.k, this.c());
   }

   @Override
   public boolean c(Minecraft var1, int var2, int var3) {
      if (super.c(var1, var2, var3)) {
         this.p = (float)(var2 - (this.h + 4)) / (this.f - 8);
         if (this.p < 0.0F) {
            this.p = 0.0F;
         }

         if (this.p > 1.0F) {
            this.p = 1.0F;
         }

         this.j = this.e();
         this.t.a(this.k, this.c());
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

   public interface FormatHelper {
      String a(int var1, String var2, float var3);
   }
}
