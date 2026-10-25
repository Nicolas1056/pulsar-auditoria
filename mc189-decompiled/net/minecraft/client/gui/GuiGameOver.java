package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback {
   private int a;
   private boolean f = false;

   @Override
   public void b() {
      this.n.clear();
      if (this.j.f.P().t()) {
         if (this.j.E()) {
            this.n.add(new GuiButton(1, this.l / 2 - 100, this.m / 4 + 96, I18n.a("deathScreen.deleteWorld")));
         } else {
            this.n.add(new GuiButton(1, this.l / 2 - 100, this.m / 4 + 96, I18n.a("deathScreen.leaveServer")));
         }
      } else {
         this.n.add(new GuiButton(0, this.l / 2 - 100, this.m / 4 + 72, I18n.a("deathScreen.respawn")));
         this.n.add(new GuiButton(1, this.l / 2 - 100, this.m / 4 + 96, I18n.a("deathScreen.titleScreen")));
         if (this.j.L() == null) {
            this.n.get(1).l = false;
         }
      }

      for (GuiButton var2 : this.n) {
         var2.l = false;
      }
   }

   @Override
   protected void a(char var1, int var2) {
   }

   @Override
   protected void a(GuiButton var1) {
      switch (var1.k) {
         case 0:
            this.j.h.cb();
            this.j.a(null);
            break;
         case 1:
            if (this.j.f.P().t()) {
               this.j.a(new GuiMainMenu());
            } else {
               GuiYesNo var2 = new GuiYesNo(this, I18n.a("deathScreen.quit.confirm"), "", I18n.a("deathScreen.titleScreen"), I18n.a("deathScreen.respawn"), 0);
               this.j.a(var2);
               var2.b(20);
            }
      }
   }

   @Override
   public void a(boolean var1, int var2) {
      if (var1) {
         this.j.f.H();
         this.j.a(null);
         this.j.a(new GuiMainMenu());
      } else {
         this.j.h.cb();
         this.j.a(null);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.a(0, 0, this.l, this.m, 1615855616, -1602211792);
      GlStateManager.E();
      GlStateManager.a(2.0F, 2.0F, 2.0F);
      boolean var4 = this.j.f.P().t();
      String var5 = var4 ? I18n.a("deathScreen.title.hardcore") : I18n.a("deathScreen.title");
      this.a(this.q, var5, this.l / 2 / 2, 30, 16777215);
      GlStateManager.F();
      if (var4) {
         this.a(this.q, I18n.a("deathScreen.hardcoreInfo"), this.l / 2, 144, 16777215);
      }

      this.a(this.q, I18n.a("deathScreen.score") + ": " + EnumChatFormatting.o + this.j.h.bX(), this.l / 2, 100, 16777215);
      super.a(var1, var2, var3);
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public void e() {
      super.e();
      this.a++;
      if (this.a == 20) {
         for (GuiButton var2 : this.n) {
            var2.l = true;
         }
      }
   }
}
