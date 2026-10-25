package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class GuiCustomizeSkin extends GuiScreen {
   private final GuiScreen a;
   private String f;

   public GuiCustomizeSkin(GuiScreen var1) {
      this.a = var1;
   }

   @Override
   public void b() {
      int var1 = 0;
      this.f = I18n.a("options.skinCustomisation.title");

      for (EnumPlayerModelParts var5 : EnumPlayerModelParts.values()) {
         this.n.add(new GuiCustomizeSkin.ButtonPart(var5.b(), this.l / 2 - 155 + var1 % 2 * 160, this.m / 6 + 24 * (var1 >> 1), 150, 20, var5));
         var1++;
      }

      if (var1 % 2 == 1) {
         var1++;
      }

      this.n.add(new GuiButton(200, this.l / 2 - 100, this.m / 6 + 24 * (var1 >> 1), I18n.a("gui.done")));
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 200) {
            this.j.t.b();
            this.j.a(this.a);
         } else if (var1 instanceof GuiCustomizeSkin.ButtonPart) {
            EnumPlayerModelParts var2 = ((GuiCustomizeSkin.ButtonPart)var1).p;
            this.j.t.a(var2);
            var1.j = this.a(var2);
         }
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, this.f, this.l / 2, 20, 16777215);
      super.a(var1, var2, var3);
   }

   private String a(EnumPlayerModelParts var1) {
      String var2;
      if (this.j.t.d().contains(var1)) {
         var2 = I18n.a("options.on");
      } else {
         var2 = I18n.a("options.off");
      }

      return var1.d().d() + ": " + var2;
   }

   class ButtonPart extends GuiButton {
      private final EnumPlayerModelParts p;

      private ButtonPart(int var2, int var3, int var4, int var5, int var6, EnumPlayerModelParts var7) {
         super(var2, var3, var4, var5, var6, GuiCustomizeSkin.this.a(var7));
         this.p = var7;
      }
   }
}
