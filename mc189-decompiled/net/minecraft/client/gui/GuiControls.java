package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class GuiControls extends GuiScreen {
   private static final GameSettings.Options[] h = new GameSettings.Options[]{GameSettings.Options.a, GameSettings.Options.b, GameSettings.Options.y};
   private GuiScreen i;
   protected String a = "Controls";
   private GameSettings r;
   public KeyBinding f = null;
   public long g;
   private GuiKeyBindingList s;
   private GuiButton t;

   public GuiControls(GuiScreen var1, GameSettings var2) {
      this.i = var1;
      this.r = var2;
   }

   @Override
   public void b() {
      this.s = new GuiKeyBindingList(this, this.j);
      this.n.add(new GuiButton(200, this.l / 2 - 155, this.m - 29, 150, 20, I18n.a("gui.done")));
      this.n.add(this.t = new GuiButton(201, this.l / 2 - 155 + 160, this.m - 29, 150, 20, I18n.a("controls.resetAll")));
      this.a = I18n.a("controls.title");
      int var1 = 0;

      for (GameSettings.Options var5 : h) {
         if (var5.a()) {
            this.n.add(new GuiOptionSlider(var5.c(), this.l / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5));
         } else {
            this.n.add(new GuiOptionButton(var5.c(), this.l / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5, this.r.c(var5)));
         }

         var1++;
      }
   }

   @Override
   public void k() {
      super.k();
      this.s.p();
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 200) {
         this.j.a(this.i);
      } else if (var1.k == 201) {
         for (KeyBinding var5 : this.j.t.ax) {
            var5.b(var5.h());
         }

         KeyBinding.b();
      } else if (var1.k < 100 && var1 instanceof GuiOptionButton) {
         this.r.a(((GuiOptionButton)var1).c(), 1);
         var1.j = this.r.c(GameSettings.Options.a(var1.k));
      }
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      if (this.f != null) {
         this.r.a(this.f, -100 + var3);
         this.f = null;
         KeyBinding.b();
      } else if (var3 != 0 || !this.s.b(var1, var2, var3)) {
         super.a(var1, var2, var3);
      }
   }

   @Override
   protected void b(int var1, int var2, int var3) {
      if (var3 != 0 || !this.s.c(var1, var2, var3)) {
         super.b(var1, var2, var3);
      }
   }

   @Override
   protected void a(char var1, int var2) {
      if (this.f != null) {
         if (var2 == 1) {
            this.r.a(this.f, 0);
         } else if (var2 != 0) {
            this.r.a(this.f, var2);
         } else if (var1 > 0) {
            this.r.a(this.f, var1 + 256);
         }

         this.f = null;
         this.g = Minecraft.J();
         KeyBinding.b();
      } else {
         super.a(var1, var2);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.s.a(var1, var2, var3);
      this.a(this.q, this.a, this.l / 2, 8, 16777215);
      boolean var4 = true;

      for (KeyBinding var8 : this.r.ax) {
         if (var8.i() != var8.h()) {
            var4 = false;
            break;
         }
      }

      this.t.l = !var4;
      super.a(var1, var2, var3);
   }
}
