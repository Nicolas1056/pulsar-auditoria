package net.minecraft.client.gui.stream;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;

public class GuiStreamOptions extends GuiScreen {
   private static final GameSettings.Options[] a = new GameSettings.Options[]{
      GameSettings.Options.F,
      GameSettings.Options.J,
      GameSettings.Options.I,
      GameSettings.Options.L,
      GameSettings.Options.G,
      GameSettings.Options.H,
      GameSettings.Options.O,
      GameSettings.Options.K
   };
   private static final GameSettings.Options[] f = new GameSettings.Options[]{GameSettings.Options.M, GameSettings.Options.N};
   private final GuiScreen g;
   private final GameSettings h;
   private String i;
   private String r;
   private int s;
   private boolean t = false;

   public GuiStreamOptions(GuiScreen var1, GameSettings var2) {
      this.g = var1;
      this.h = var2;
   }

   @Override
   public void b() {
      int var1 = 0;
      this.i = I18n.a("options.stream.title");
      this.r = I18n.a("options.stream.chat.title");

      for (GameSettings.Options var5 : a) {
         if (var5.a()) {
            this.n.add(new GuiOptionSlider(var5.c(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 + 24 * (var1 >> 1), var5));
         } else {
            this.n.add(new GuiOptionButton(var5.c(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 + 24 * (var1 >> 1), var5, this.h.c(var5)));
         }

         var1++;
      }

      if (var1 % 2 == 1) {
         var1++;
      }

      this.s = this.m / 6 + 24 * (var1 >> 1) + 6;
      var1 += 2;

      for (GameSettings.Options var11 : f) {
         if (var11.a()) {
            this.n.add(new GuiOptionSlider(var11.c(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 + 24 * (var1 >> 1), var11));
         } else {
            this.n.add(new GuiOptionButton(var11.c(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 + 24 * (var1 >> 1), var11, this.h.c(var11)));
         }

         var1++;
      }

      this.n.add(new GuiButton(200, this.l / 2 - 155, this.m / 6 + 168, 150, 20, I18n.a("gui.done")));
      GuiButton var8 = new GuiButton(201, this.l / 2 + 5, this.m / 6 + 168, 150, 20, I18n.a("options.stream.ingestSelection"));
      var8.l = this.j.Y().j() && this.j.Y().s().length > 0 || this.j.Y().w();
      this.n.add(var8);
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k < 100 && var1 instanceof GuiOptionButton) {
            GameSettings.Options var2 = ((GuiOptionButton)var1).c();
            this.h.a(var2, 1);
            var1.j = this.h.c(GameSettings.Options.a(var1.k));
            if (this.j.Y().k() && var2 != GameSettings.Options.M && var2 != GameSettings.Options.N) {
               this.t = true;
            }
         } else if (var1 instanceof GuiOptionSlider) {
            if (var1.k == GameSettings.Options.G.c()) {
               this.j.Y().p();
            } else if (var1.k == GameSettings.Options.H.c()) {
               this.j.Y().p();
            } else if (this.j.Y().k()) {
               this.t = true;
            }
         }

         if (var1.k == 200) {
            this.j.t.b();
            this.j.a(this.g);
         } else if (var1.k == 201) {
            this.j.t.b();
            this.j.a(new GuiIngestServers(this));
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, this.i, this.l / 2, 20, 16777215);
      this.a(this.q, this.r, this.l / 2, this.s, 16777215);
      if (this.t) {
         this.a(this.q, EnumChatFormatting.m + I18n.a("options.stream.changes"), this.l / 2, 20 + this.q.a, 16777215);
      }

      super.a(var1, var2, var3);
   }
}
