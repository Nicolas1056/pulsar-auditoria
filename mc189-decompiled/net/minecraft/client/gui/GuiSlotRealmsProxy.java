package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsScrolledSelectionList;

public class GuiSlotRealmsProxy extends GuiSlot {
   private final RealmsScrolledSelectionList u;

   public GuiSlotRealmsProxy(RealmsScrolledSelectionList var1, int var2, int var3, int var4, int var5, int var6) {
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
   }
}
