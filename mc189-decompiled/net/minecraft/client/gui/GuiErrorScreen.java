package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

public class GuiErrorScreen extends GuiScreen {
   private String a;
   private String f;

   public GuiErrorScreen(String var1, String var2) {
      this.a = var1;
      this.f = var2;
   }

   @Override
   public void b() {
      super.b();
      this.n.add(new GuiButton(0, this.l / 2 - 100, 140, I18n.a("gui.cancel")));
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.a(0, 0, this.l, this.m, -12574688, -11530224);
      this.a(this.q, this.a, this.l / 2, 90, 16777215);
      this.a(this.q, this.f, this.l / 2, 110, 16777215);
      super.a(var1, var2, var3);
   }

   @Override
   protected void a(char var1, int var2) {
   }

   @Override
   protected void a(GuiButton var1) {
      this.j.a(null);
   }
}
