package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.lwjgl.input.Keyboard;

public class GuiRenameWorld extends GuiScreen {
   private GuiScreen a;
   private GuiTextField f;
   private final String g;

   public GuiRenameWorld(GuiScreen var1, String var2) {
      this.a = var1;
      this.g = var2;
   }

   @Override
   public void e() {
      this.f.a();
   }

   @Override
   public void b() {
      Keyboard.enableRepeatEvents(true);
      this.n.clear();
      this.n.add(new GuiButton(0, this.l / 2 - 100, this.m / 4 + 96 + 12, I18n.a("selectWorld.renameButton")));
      this.n.add(new GuiButton(1, this.l / 2 - 100, this.m / 4 + 120 + 12, I18n.a("gui.cancel")));
      ISaveFormat var1 = this.j.f();
      WorldInfo var2 = var1.c(this.g);
      String var3 = var2.k();
      this.f = new GuiTextField(2, this.q, this.l / 2 - 100, 60, 200, 20);
      this.f.b(true);
      this.f.a(var3);
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 1) {
            this.j.a(this.a);
         } else if (var1.k == 0) {
            ISaveFormat var2 = this.j.f();
            var2.a(this.g, this.f.b().trim());
            this.j.a(this.a);
         }
      }
   }

   @Override
   protected void a(char var1, int var2) {
      this.f.a(var1, var2);
      this.n.get(0).l = this.f.b().trim().length() > 0;
      if (var2 == 28 || var2 == 156) {
         this.a(this.n.get(0));
      }
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      this.f.a(var1, var2, var3);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, I18n.a("selectWorld.renameTitle"), this.l / 2, 20, 16777215);
      this.c(this.q, I18n.a("selectWorld.enterName"), this.l / 2 - 100, 47, 10526880);
      this.f.g();
      super.a(var1, var2, var3);
   }
}
