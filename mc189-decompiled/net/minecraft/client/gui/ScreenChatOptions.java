package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class ScreenChatOptions extends GuiScreen {
   private static final GameSettings.Options[] a = new GameSettings.Options[]{
      GameSettings.Options.p,
      GameSettings.Options.q,
      GameSettings.Options.r,
      GameSettings.Options.s,
      GameSettings.Options.t,
      GameSettings.Options.z,
      GameSettings.Options.B,
      GameSettings.Options.C,
      GameSettings.Options.A,
      GameSettings.Options.Q
   };
   private final GuiScreen f;
   private final GameSettings g;
   private String h;

   public ScreenChatOptions(GuiScreen var1, GameSettings var2) {
      this.f = var1;
      this.g = var2;
   }

   @Override
   public void b() {
      int var1 = 0;
      this.h = I18n.a("options.chat.title");

      for (GameSettings.Options var5 : a) {
         if (var5.a()) {
            this.n.add(new GuiOptionSlider(var5.c(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 + 24 * (var1 >> 1), var5));
         } else {
            this.n.add(new GuiOptionButton(var5.c(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 + 24 * (var1 >> 1), var5, this.g.c(var5)));
         }

         var1++;
      }

      this.n.add(new GuiButton(200, this.l / 2 - 100, this.m / 6 + 120, I18n.a("gui.done")));
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k < 100 && var1 instanceof GuiOptionButton) {
            this.g.a(((GuiOptionButton)var1).c(), 1);
            var1.j = this.g.c(GameSettings.Options.a(var1.k));
         }

         if (var1.k == 200) {
            this.j.t.b();
            this.j.a(this.f);
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, this.h, this.l / 2, 20, 16777215);
      super.a(var1, var2, var3);
   }
}
