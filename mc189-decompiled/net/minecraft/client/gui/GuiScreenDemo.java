package net.minecraft.client.gui;

import java.net.URI;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiScreenDemo extends GuiScreen {
   private static final Logger a = LogManager.getLogger();
   private static final ResourceLocation f = new ResourceLocation("textures/gui/demo_background.png");

   @Override
   public void b() {
      this.n.clear();
      byte var1 = -16;
      this.n.add(new GuiButton(1, this.l / 2 - 116, this.m / 2 + 62 + var1, 114, 20, I18n.a("demo.help.buy")));
      this.n.add(new GuiButton(2, this.l / 2 + 2, this.m / 2 + 62 + var1, 114, 20, I18n.a("demo.help.later")));
   }

   @Override
   protected void a(GuiButton var1) {
      switch (var1.k) {
         case 1:
            var1.l = false;

            try {
               Class var2 = Class.forName("java.awt.Desktop");
               Object var3 = var2.getMethod("getDesktop").invoke(null);
               var2.getMethod("browse", URI.class).invoke(var3, new URI("http://www.minecraft.net/store?source=demo"));
            } catch (Throwable var4) {
               a.error("Couldn't open link", var4);
            }
            break;
         case 2:
            this.j.a(null);
            this.j.n();
      }
   }

   @Override
   public void e() {
      super.e();
   }

   @Override
   public void c() {
      super.c();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.j.P().a(f);
      int var1 = (this.l - 248) / 2;
      int var2 = (this.m - 166) / 2;
      this.b(var1, var2, 0, 0, 248, 166);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      int var4 = (this.l - 248) / 2 + 10;
      int var5 = (this.m - 166) / 2 + 8;
      this.q.a(I18n.a("demo.help.title"), var4, var5, 2039583);
      var5 += 12;
      GameSettings var6 = this.j.t;
      this.q
         .a(
            I18n.a("demo.help.movementShort", GameSettings.c(var6.Y.i()), GameSettings.c(var6.Z.i()), GameSettings.c(var6.aa.i()), GameSettings.c(var6.ab.i())),
            var4,
            var5,
            5197647
         );
      this.q.a(I18n.a("demo.help.movementMouse"), var4, var5 + 12, 5197647);
      this.q.a(I18n.a("demo.help.jump", GameSettings.c(var6.ac.i())), var4, var5 + 24, 5197647);
      this.q.a(I18n.a("demo.help.inventory", GameSettings.c(var6.af.i())), var4, var5 + 36, 5197647);
      this.q.a(I18n.a("demo.help.fullWrapped"), var4, var5 + 68, 218, 2039583);
      super.a(var1, var2, var3);
   }
}
