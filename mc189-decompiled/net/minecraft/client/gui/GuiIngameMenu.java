package net.minecraft.client.gui;

import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;

public class GuiIngameMenu extends GuiScreen {
   private int a;
   private int f;

   @Override
   public void b() {
      this.a = 0;
      this.n.clear();
      byte var1 = -16;
      byte var2 = 98;
      this.n.add(new GuiButton(1, this.l / 2 - 100, this.m / 4 + 120 + var1, I18n.a("menu.returnToMenu")));
      if (!this.j.E()) {
         this.n.get(0).j = I18n.a("menu.disconnect");
      }

      this.n.add(new GuiButton(4, this.l / 2 - 100, this.m / 4 + 24 + var1, I18n.a("menu.returnToGame")));
      this.n.add(new GuiButton(0, this.l / 2 - 100, this.m / 4 + 96 + var1, 98, 20, I18n.a("menu.options")));
      GuiButton var3;
      this.n.add(var3 = new GuiButton(7, this.l / 2 + 2, this.m / 4 + 96 + var1, 98, 20, I18n.a("menu.shareToLan")));
      this.n.add(new GuiButton(5, this.l / 2 - 100, this.m / 4 + 48 + var1, 98, 20, I18n.a("gui.achievements")));
      this.n.add(new GuiButton(6, this.l / 2 + 2, this.m / 4 + 48 + var1, 98, 20, I18n.a("gui.stats")));
      var3.l = this.j.F() && !this.j.G().b();
   }

   @Override
   protected void a(GuiButton var1) {
      switch (var1.k) {
         case 0:
            this.j.a(new GuiOptions(this, this.j.t));
            break;
         case 1:
            boolean var2 = this.j.E();
            boolean var3 = this.j.al();
            var1.l = false;
            this.j.f.H();
            this.j.a(null);
            if (var2) {
               this.j.a(new GuiMainMenu());
            } else if (var3) {
               RealmsBridge var4 = new RealmsBridge();
               var4.switchToRealms(new GuiMainMenu());
            } else {
               this.j.a(new GuiMultiplayer(new GuiMainMenu()));
            }
         case 2:
         case 3:
         default:
            break;
         case 4:
            this.j.a(null);
            this.j.n();
            break;
         case 5:
            this.j.a(new GuiAchievements(this, this.j.h.x()));
            break;
         case 6:
            this.j.a(new GuiStats(this, this.j.h.x()));
            break;
         case 7:
            this.j.a(new GuiShareToLan(this));
      }
   }

   @Override
   public void e() {
      super.e();
      this.f++;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, I18n.a("menu.game"), this.l / 2, 40, 16777215);
      super.a(var1, var2, var3);
   }
}
