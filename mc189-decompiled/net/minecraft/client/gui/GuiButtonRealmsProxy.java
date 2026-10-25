package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsButton;

public class GuiButtonRealmsProxy extends GuiButton {
   private RealmsButton o;

   public GuiButtonRealmsProxy(RealmsButton var1, int var2, int var3, int var4, String var5) {
      super(var2, var3, var4, var5);
      this.o = var1;
   }

   public GuiButtonRealmsProxy(RealmsButton var1, int var2, int var3, int var4, String var5, int var6, int var7) {
      super(var2, var3, var4, var6, var7, var5);
      this.o = var1;
   }

   public int c() {
      return super.k;
   }

   public boolean d() {
      return super.l;
   }

   public void b(boolean var1) {
      super.l = var1;
   }

   public void a(String var1) {
      super.j = var1;
   }

   @Override
   public int b() {
      return super.b();
   }

   public int e() {
      return super.i;
   }

   @Override
   public boolean c(Minecraft var1, int var2, int var3) {
      if (super.c(var1, var2, var3)) {
         this.o.clicked(var2, var3);
      }

      return super.c(var1, var2, var3);
   }

   @Override
   public void a(int var1, int var2) {
      this.o.released(var1, var2);
   }

   @Override
   public void b(Minecraft var1, int var2, int var3) {
      this.o.renderBg(var2, var3);
   }

   public RealmsButton f() {
      return this.o;
   }

   @Override
   public int a(boolean var1) {
      return this.o.getYImage(var1);
   }

   public int c(boolean var1) {
      return super.a(var1);
   }

   public int g() {
      return this.g;
   }
}
