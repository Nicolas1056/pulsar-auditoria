package net.minecraft.client.gui;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiVideoSettings extends GuiScreen {
   private GuiScreen f;
   protected String a = "Video Settings";
   private GameSettings g;
   private GuiListExtended h;
   private static final GameSettings.Options[] i = new GameSettings.Options[]{
      GameSettings.Options.l,
      GameSettings.Options.f,
      GameSettings.Options.m,
      GameSettings.Options.i,
      GameSettings.Options.h,
      GameSettings.Options.g,
      GameSettings.Options.n,
      GameSettings.Options.d,
      GameSettings.Options.k,
      GameSettings.Options.o,
      GameSettings.Options.v,
      GameSettings.Options.w,
      GameSettings.Options.D,
      GameSettings.Options.P,
      GameSettings.Options.x,
      GameSettings.Options.R
   };

   public GuiVideoSettings(GuiScreen var1, GameSettings var2) {
      this.f = var1;
      this.g = var2;
   }

   @Override
   public void b() {
      this.a = I18n.a("options.videoTitle");
      this.n.clear();
      this.n.add(new GuiButton(200, this.l / 2 - 100, this.m - 27, I18n.a("gui.done")));
      if (!OpenGlHelper.P) {
         GameSettings.Options[] var1 = new GameSettings.Options[i.length - 1];
         int var2 = 0;

         for (GameSettings.Options var6 : i) {
            if (var6 == GameSettings.Options.x) {
               break;
            }

            var1[var2] = var6;
            var2++;
         }

         this.h = new GuiOptionsRowList(this.j, this.l, this.m, 32, this.m - 32, 25, var1);
      } else {
         this.h = new GuiOptionsRowList(this.j, this.l, this.m, 32, this.m - 32, 25, i);
      }
   }

   @Override
   public void k() {
      super.k();
      this.h.p();
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 200) {
            this.j.t.b();
            this.j.a(this.f);
         }
      }
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      int var4 = this.g.aL;
      super.a(var1, var2, var3);
      this.h.b(var1, var2, var3);
      if (this.g.aL != var4) {
         ScaledResolution var5 = new ScaledResolution(this.j);
         int var6 = var5.a();
         int var7 = var5.b();
         this.a(this.j, var6, var7);
      }
   }

   @Override
   protected void b(int var1, int var2, int var3) {
      int var4 = this.g.aL;
      super.b(var1, var2, var3);
      this.h.c(var1, var2, var3);
      if (this.g.aL != var4) {
         ScaledResolution var5 = new ScaledResolution(this.j);
         int var6 = var5.a();
         int var7 = var5.b();
         this.a(this.j, var6, var7);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.h.a(var1, var2, var3);
      this.a(this.q, this.a, this.l / 2, 5, 16777215);
      super.a(var1, var2, var3);
   }
}
