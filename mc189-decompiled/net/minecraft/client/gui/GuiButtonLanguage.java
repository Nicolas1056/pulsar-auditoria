package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonLanguage extends GuiButton {
   public GuiButtonLanguage(int var1, int var2, int var3) {
      super(var1, var2, var3, 20, 20, "");
   }

   @Override
   public void a(Minecraft var1, int var2, int var3) {
      if (this.m) {
         var1.P().a(GuiButton.a);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var4 = var2 >= this.h && var3 >= this.i && var2 < this.h + this.f && var3 < this.i + this.g;
         int var5 = 106;
         if (var4) {
            var5 += this.g;
         }

         this.b(this.h, this.i, 0, var5, this.f, this.g);
      }
   }
}
