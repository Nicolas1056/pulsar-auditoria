package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;

public abstract class GuiListExtended extends GuiSlot {
   public GuiListExtended(Minecraft var1, int var2, int var3, int var4, int var5, int var6) {
      super(var1, var2, var3, var4, var5, var6);
   }

   @Override
   protected void a(int var1, boolean var2, int var3, int var4) {
   }

   @Override
   protected boolean a(int var1) {
      return false;
   }

   @Override
   protected void a() {
   }

   @Override
   protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.b(var1).a(var1, var2, var3, this.c(), var4, var5, var6, this.c(var5, var6) == var1);
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      this.b(var1).a(var1, var2, var3);
   }

   public boolean b(int var1, int var2, int var3) {
      if (this.g(var2)) {
         int var4 = this.c(var1, var2);
         if (var4 >= 0) {
            int var5 = this.g + this.b / 2 - this.c() / 2 + 2;
            int var6 = this.d + 4 - this.n() + var4 * this.h + this.t;
            int var7 = var1 - var5;
            int var8 = var2 - var6;
            if (this.b(var4).a(var4, var1, var2, var3, var7, var8)) {
               this.d(false);
               return true;
            }
         }
      }

      return false;
   }

   public boolean c(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.b(); var4++) {
         int var5 = this.g + this.b / 2 - this.c() / 2 + 2;
         int var6 = this.d + 4 - this.n() + var4 * this.h + this.t;
         int var7 = var1 - var5;
         int var8 = var2 - var6;
         this.b(var4).b(var4, var1, var2, var3, var7, var8);
      }

      this.d(true);
      return false;
   }

   public abstract GuiListExtended.IGuiListEntry b(int var1);

   public interface IGuiListEntry {
      void a(int var1, int var2, int var3);

      void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8);

      boolean a(int var1, int var2, int var3, int var4, int var5, int var6);

      void b(int var1, int var2, int var3, int var4, int var5, int var6);
   }
}
