package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import java.net.IDN;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class GuiScreenAddServer extends GuiScreen {
   private final GuiScreen a;
   private final ServerData f;
   private GuiTextField g;
   private GuiTextField h;
   private GuiButton i;
   private Predicate<String> r = new Predicate<String>() {
      public boolean a(String var1) {
         if (var1.length() == 0) {
            return true;
         } else {
            String[] var2 = var1.split(":");
            if (var2.length == 0) {
               return true;
            } else {
               try {
                  String var3 = IDN.toASCII(var2[0]);
                  return true;
               } catch (IllegalArgumentException var4) {
                  return false;
               }
            }
         }
      }
   };

   public GuiScreenAddServer(GuiScreen var1, ServerData var2) {
      this.a = var1;
      this.f = var2;
   }

   @Override
   public void e() {
      this.h.a();
      this.g.a();
   }

   @Override
   public void b() {
      Keyboard.enableRepeatEvents(true);
      this.n.clear();
      this.n.add(new GuiButton(0, this.l / 2 - 100, this.m / 4 + 96 + 18, I18n.a("addServer.add")));
      this.n.add(new GuiButton(1, this.l / 2 - 100, this.m / 4 + 120 + 18, I18n.a("gui.cancel")));
      this.n.add(this.i = new GuiButton(2, this.l / 2 - 100, this.m / 4 + 72, I18n.a("addServer.resourcePack") + ": " + this.f.b().a().d()));
      this.h = new GuiTextField(0, this.q, this.l / 2 - 100, 66, 200, 20);
      this.h.b(true);
      this.h.a(this.f.a);
      this.g = new GuiTextField(1, this.q, this.l / 2 - 100, 106, 200, 20);
      this.g.f(128);
      this.g.a(this.f.b);
      this.g.a(this.r);
      this.n.get(0).l = this.g.b().length() > 0 && this.g.b().split(":").length > 0 && this.h.b().length() > 0;
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 2) {
            this.f.a(ServerData.ServerResourceMode.values()[(this.f.b().ordinal() + 1) % ServerData.ServerResourceMode.values().length]);
            this.i.j = I18n.a("addServer.resourcePack") + ": " + this.f.b().a().d();
         } else if (var1.k == 1) {
            this.a.a(false, 0);
         } else if (var1.k == 0) {
            this.f.a = this.h.b();
            this.f.b = this.g.b();
            this.a.a(true, 0);
         }
      }
   }

   @Override
   protected void a(char var1, int var2) {
      this.h.a(var1, var2);
      this.g.a(var1, var2);
      if (var2 == 15) {
         this.h.b(!this.h.m());
         this.g.b(!this.g.m());
      }

      if (var2 == 28 || var2 == 156) {
         this.a(this.n.get(0));
      }

      this.n.get(0).l = this.g.b().length() > 0 && this.g.b().split(":").length > 0 && this.h.b().length() > 0;
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      this.g.a(var1, var2, var3);
      this.h.a(var1, var2, var3);
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, I18n.a("addServer.title"), this.l / 2, 17, 16777215);
      this.c(this.q, I18n.a("addServer.enterName"), this.l / 2 - 100, 53, 10526880);
      this.c(this.q, I18n.a("addServer.enterIp"), this.l / 2 - 100, 94, 10526880);
      this.h.g();
      this.g.g();
      super.a(var1, var2, var3);
   }
}
