package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class GuiListButton extends GuiButton {
   private boolean o;
   private String p;
   private final GuiPageButtonList.GuiResponder q;

   public GuiListButton(GuiPageButtonList.GuiResponder var1, int var2, int var3, int var4, String var5, boolean var6) {
      super(var2, var3, var4, 150, 20, "");
      this.p = var5;
      this.o = var6;
      this.j = this.c();
      this.q = var1;
   }

   private String c() {
      return I18n.a(this.p) + ": " + (this.o ? I18n.a("gui.yes") : I18n.a("gui.no"));
   }

   public void b(boolean var1) {
      this.o = var1;
      this.j = this.c();
      this.q.a(this.k, var1);
   }

   @Override
   public boolean c(Minecraft var1, int var2, int var3) {
      if (super.c(var1, var2, var3)) {
         this.o = !this.o;
         this.j = this.c();
         this.q.a(this.k, this.o);
         return true;
      } else {
         return false;
      }
   }
}
