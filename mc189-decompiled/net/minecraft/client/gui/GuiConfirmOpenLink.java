package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

public class GuiConfirmOpenLink extends GuiYesNo {
   private final String r;
   private final String s;
   private final String t;
   private boolean u = true;

   public GuiConfirmOpenLink(GuiYesNoCallback var1, String var2, int var3, boolean var4) {
      super(var1, I18n.a(var4 ? "chat.link.confirmTrusted" : "chat.link.confirm"), var2, var3);
      this.g = I18n.a(var4 ? "chat.link.open" : "gui.yes");
      this.h = I18n.a(var4 ? "gui.cancel" : "gui.no");
      this.s = I18n.a("chat.copy");
      this.r = I18n.a("chat.link.warning");
      this.t = var2;
   }

   @Override
   public void b() {
      super.b();
      this.n.clear();
      this.n.add(new GuiButton(0, this.l / 2 - 50 - 105, this.m / 6 + 96, 100, 20, this.g));
      this.n.add(new GuiButton(2, this.l / 2 - 50, this.m / 6 + 96, 100, 20, this.s));
      this.n.add(new GuiButton(1, this.l / 2 - 50 + 105, this.m / 6 + 96, 100, 20, this.h));
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 2) {
         this.a();
      }

      this.a.a(var1.k == 0, this.i);
   }

   public void a() {
      e(this.t);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      super.a(var1, var2, var3);
      if (this.u) {
         this.a(this.q, this.r, this.l / 2, 110, 16764108);
      }
   }

   public void f() {
      this.u = false;
   }
}
