package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {
   protected static final ResourceLocation a = new ResourceLocation("textures/gui/widgets.png");
   protected int f = 200;
   protected int g = 20;
   public int h;
   public int i;
   public String j;
   public int k;
   public boolean l = true;
   public boolean m = true;
   protected boolean n;

   public GuiButton(int var1, int var2, int var3, String var4) {
      this(var1, var2, var3, 200, 20, var4);
   }

   public GuiButton(int var1, int var2, int var3, int var4, int var5, String var6) {
      this.k = var1;
      this.h = var2;
      this.i = var3;
      this.f = var4;
      this.g = var5;
      this.j = var6;
   }

   protected int a(boolean var1) {
      byte var2 = 1;
      if (!this.l) {
         var2 = 0;
      } else if (var1) {
         var2 = 2;
      }

      return var2;
   }

   public void a(Minecraft var1, int var2, int var3) {
      if (this.m) {
         FontRenderer var4 = var1.k;
         var1.P().a(a);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.n = var2 >= this.h && var3 >= this.i && var2 < this.h + this.f && var3 < this.i + this.g;
         int var5 = this.a(this.n);
         GlStateManager.l();
         GlStateManager.a(770, 771, 1, 0);
         GlStateManager.b(770, 771);
         this.b(this.h, this.i, 0, 46 + var5 * 20, this.f / 2, this.g);
         this.b(this.h + this.f / 2, this.i, 200 - this.f / 2, 46 + var5 * 20, this.f / 2, this.g);
         this.b(var1, var2, var3);
         int var6 = 14737632;
         if (!this.l) {
            var6 = 10526880;
         } else if (this.n) {
            var6 = 16777120;
         }

         this.a(var4, this.j, this.h + this.f / 2, this.i + (this.g - 8) / 2, var6);
      }
   }

   protected void b(Minecraft var1, int var2, int var3) {
   }

   public void a(int var1, int var2) {
   }

   public boolean c(Minecraft var1, int var2, int var3) {
      return this.l && this.m && var2 >= this.h && var3 >= this.i && var2 < this.h + this.f && var3 < this.i + this.g;
   }

   public boolean a() {
      return this.n;
   }

   public void b(int var1, int var2) {
   }

   public void a(SoundHandler var1) {
      var1.a(PositionedSoundRecord.a(new ResourceLocation("gui.button.press"), 1.0F));
   }

   public int b() {
      return this.f;
   }

   public void a(int var1) {
      this.f = var1;
   }
}
