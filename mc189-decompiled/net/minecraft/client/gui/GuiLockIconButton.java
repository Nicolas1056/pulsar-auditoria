package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiLockIconButton extends GuiButton {
   private boolean o = false;

   public GuiLockIconButton(int var1, int var2, int var3) {
      super(var1, var2, var3, 20, 20, "");
   }

   public boolean c() {
      return this.o;
   }

   public void b(boolean var1) {
      this.o = var1;
   }

   @Override
   public void a(Minecraft var1, int var2, int var3) {
      if (this.m) {
         var1.P().a(GuiButton.a);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var4 = var2 >= this.h && var3 >= this.i && var2 < this.h + this.f && var3 < this.i + this.g;
         GuiLockIconButton.Icon var5;
         if (this.o) {
            if (!this.l) {
               var5 = GuiLockIconButton.Icon.c;
            } else if (var4) {
               var5 = GuiLockIconButton.Icon.b;
            } else {
               var5 = GuiLockIconButton.Icon.a;
            }
         } else if (!this.l) {
            var5 = GuiLockIconButton.Icon.f;
         } else if (var4) {
            var5 = GuiLockIconButton.Icon.e;
         } else {
            var5 = GuiLockIconButton.Icon.d;
         }

         this.b(this.h, this.i, var5.a(), var5.b(), this.f, this.g);
      }
   }

   static enum Icon {
      a(0, 146),
      b(0, 166),
      c(0, 186),
      d(20, 146),
      e(20, 166),
      f(20, 186);

      private final int g;
      private final int h;

      private Icon(int var3, int var4) {
         this.g = var3;
         this.h = var4;
      }

      public int a() {
         return this.g;
      }

      public int b() {
         return this.h;
      }
   }
}
