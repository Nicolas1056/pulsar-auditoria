package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsClickableScrolledSelectionList;
import net.minecraft.realms.Tezzelator;
import org.lwjgl.input.Mouse;

public class GuiClickableScrolledSelectionListProxy extends GuiSlot {
   private final RealmsClickableScrolledSelectionList u;

   public GuiClickableScrolledSelectionListProxy(RealmsClickableScrolledSelectionList var1, int var2, int var3, int var4, int var5, int var6) {
      super(Minecraft.A(), var2, var3, var4, var5, var6);
      this.u = var1;
   }

   @Override
   protected int b() {
      return this.u.getItemCount();
   }

   @Override
   protected void a(int var1, boolean var2, int var3, int var4) {
      this.u.selectItem(var1, var2, var3, var4);
   }

   @Override
   protected boolean a(int var1) {
      return this.u.isSelectedItem(var1);
   }

   @Override
   protected void a() {
      this.u.renderBackground();
   }

   @Override
   protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.u.renderItem(var1, var2, var3, var4, var5, var6);
   }

   public int e() {
      return super.b;
   }

   public int f() {
      return super.j;
   }

   public int g() {
      return super.i;
   }

   @Override
   protected int k() {
      return this.u.getMaxPosition();
   }

   @Override
   protected int d() {
      return this.u.getScrollbarPosition();
   }

   @Override
   public void p() {
      super.p();
      if (this.m > 0.0F && Mouse.getEventButtonState()) {
         this.u.customMouseEvent(this.d, this.e, this.t, this.n, this.h);
      }
   }

   public void a(int var1, int var2, int var3, Tezzelator var4) {
      this.u.renderSelected(var1, var2, var3, var4);
   }

   @Override
   protected void b(int var1, int var2, int var3, int var4) {
      int var5 = this.b();

      for (int var6 = 0; var6 < var5; var6++) {
         int var7 = var2 + var6 * this.h + this.t;
         int var8 = this.h - 4;
         if (var7 > this.e || var7 + var8 < this.d) {
            this.a(var6, var1, var7);
         }

         if (this.r && this.a(var6)) {
            this.a(this.b, var7, var8, Tezzelator.instance);
         }

         this.a(var6, var1, var7, var8, var3, var4);
      }
   }
}
