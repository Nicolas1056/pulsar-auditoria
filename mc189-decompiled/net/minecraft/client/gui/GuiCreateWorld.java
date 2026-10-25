package net.minecraft.client.gui;

import java.util.Random;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public class GuiCreateWorld extends GuiScreen {
   private GuiScreen f;
   private GuiTextField g;
   private GuiTextField h;
   private String i;
   private String r = "survival";
   private String s;
   private boolean t = true;
   private boolean u;
   private boolean v;
   private boolean w;
   private boolean x;
   private boolean y;
   private boolean z;
   private GuiButton A;
   private GuiButton B;
   private GuiButton C;
   private GuiButton D;
   private GuiButton E;
   private GuiButton F;
   private GuiButton G;
   private String H;
   private String I;
   private String J;
   private String K;
   private int L;
   public String a = "";
   private static final String[] M = new String[]{
      "CON",
      "COM",
      "PRN",
      "AUX",
      "CLOCK$",
      "NUL",
      "COM1",
      "COM2",
      "COM3",
      "COM4",
      "COM5",
      "COM6",
      "COM7",
      "COM8",
      "COM9",
      "LPT1",
      "LPT2",
      "LPT3",
      "LPT4",
      "LPT5",
      "LPT6",
      "LPT7",
      "LPT8",
      "LPT9"
   };

   public GuiCreateWorld(GuiScreen var1) {
      this.f = var1;
      this.J = "";
      this.K = I18n.a("selectWorld.newWorld");
   }

   @Override
   public void e() {
      this.g.a();
      this.h.a();
   }

   @Override
   public void b() {
      Keyboard.enableRepeatEvents(true);
      this.n.clear();
      this.n.add(new GuiButton(0, this.l / 2 - 155, this.m - 28, 150, 20, I18n.a("selectWorld.create")));
      this.n.add(new GuiButton(1, this.l / 2 + 5, this.m - 28, 150, 20, I18n.a("gui.cancel")));
      this.n.add(this.A = new GuiButton(2, this.l / 2 - 75, 115, 150, 20, I18n.a("selectWorld.gameMode")));
      this.n.add(this.B = new GuiButton(3, this.l / 2 - 75, 187, 150, 20, I18n.a("selectWorld.moreWorldOptions")));
      this.n.add(this.C = new GuiButton(4, this.l / 2 - 155, 100, 150, 20, I18n.a("selectWorld.mapFeatures")));
      this.C.m = false;
      this.n.add(this.D = new GuiButton(7, this.l / 2 + 5, 151, 150, 20, I18n.a("selectWorld.bonusItems")));
      this.D.m = false;
      this.n.add(this.E = new GuiButton(5, this.l / 2 + 5, 100, 150, 20, I18n.a("selectWorld.mapType")));
      this.E.m = false;
      this.n.add(this.F = new GuiButton(6, this.l / 2 - 155, 151, 150, 20, I18n.a("selectWorld.allowCommands")));
      this.F.m = false;
      this.n.add(this.G = new GuiButton(8, this.l / 2 + 5, 120, 150, 20, I18n.a("selectWorld.customizeType")));
      this.G.m = false;
      this.g = new GuiTextField(9, this.q, this.l / 2 - 100, 60, 200, 20);
      this.g.b(true);
      this.g.a(this.K);
      this.h = new GuiTextField(10, this.q, this.l / 2 - 100, 60, 200, 20);
      this.h.a(this.J);
      this.a(this.z);
      this.a();
      this.f();
   }

   private void a() {
      this.i = this.g.b().trim();

      for (char var4 : ChatAllowedCharacters.a) {
         this.i = this.i.replace(var4, '_');
      }

      if (StringUtils.isEmpty(this.i)) {
         this.i = "World";
      }

      this.i = a(this.j.f(), this.i);
   }

   private void f() {
      this.A.j = I18n.a("selectWorld.gameMode") + ": " + I18n.a("selectWorld.gameMode." + this.r);
      this.H = I18n.a("selectWorld.gameMode." + this.r + ".line1");
      this.I = I18n.a("selectWorld.gameMode." + this.r + ".line2");
      this.C.j = I18n.a("selectWorld.mapFeatures") + " ";
      if (this.t) {
         this.C.j = this.C.j + I18n.a("options.on");
      } else {
         this.C.j = this.C.j + I18n.a("options.off");
      }

      this.D.j = I18n.a("selectWorld.bonusItems") + " ";
      if (this.w && !this.x) {
         this.D.j = this.D.j + I18n.a("options.on");
      } else {
         this.D.j = this.D.j + I18n.a("options.off");
      }

      this.E.j = I18n.a("selectWorld.mapType") + " " + I18n.a(WorldType.a[this.L].b());
      this.F.j = I18n.a("selectWorld.allowCommands") + " ";
      if (this.u && !this.x) {
         this.F.j = this.F.j + I18n.a("options.on");
      } else {
         this.F.j = this.F.j + I18n.a("options.off");
      }
   }

   public static String a(ISaveFormat var0, String var1) {
      var1 = var1.replaceAll("[\\./\"]", "_");

      for (String var5 : M) {
         if (var1.equalsIgnoreCase(var5)) {
            var1 = "_" + var1 + "_";
         }
      }

      while (var0.c(var1) != null) {
         var1 = var1 + "-";
      }

      return var1;
   }

   @Override
   public void m() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 1) {
            this.j.a(this.f);
         } else if (var1.k == 0) {
            this.j.a(null);
            if (this.y) {
               return;
            }

            this.y = true;
            long var2 = new Random().nextLong();
            String var4 = this.h.b();
            if (!StringUtils.isEmpty(var4)) {
               try {
                  long var5 = Long.parseLong(var4);
                  if (var5 != 0L) {
                     var2 = var5;
                  }
               } catch (NumberFormatException var7) {
                  var2 = var4.hashCode();
               }
            }

            WorldSettings.GameType var8 = WorldSettings.GameType.a(this.r);
            WorldSettings var6 = new WorldSettings(var2, var8, this.t, this.x, WorldType.a[this.L]);
            var6.a(this.a);
            if (this.w && !this.x) {
               var6.a();
            }

            if (this.u && !this.x) {
               var6.b();
            }

            this.j.a(this.i, this.g.b().trim(), var6);
         } else if (var1.k == 3) {
            this.h();
         } else if (var1.k == 2) {
            if (this.r.equals("survival")) {
               if (!this.v) {
                  this.u = false;
               }

               this.x = false;
               this.r = "hardcore";
               this.x = true;
               this.F.l = false;
               this.D.l = false;
               this.f();
            } else if (this.r.equals("hardcore")) {
               if (!this.v) {
                  this.u = true;
               }

               this.x = false;
               this.r = "creative";
               this.f();
               this.x = false;
               this.F.l = true;
               this.D.l = true;
            } else {
               if (!this.v) {
                  this.u = false;
               }

               this.r = "survival";
               this.f();
               this.F.l = true;
               this.D.l = true;
               this.x = false;
            }

            this.f();
         } else if (var1.k == 4) {
            this.t = !this.t;
            this.f();
         } else if (var1.k == 7) {
            this.w = !this.w;
            this.f();
         } else if (var1.k == 5) {
            this.L++;
            if (this.L >= WorldType.a.length) {
               this.L = 0;
            }

            while (!this.g()) {
               this.L++;
               if (this.L >= WorldType.a.length) {
                  this.L = 0;
               }
            }

            this.a = "";
            this.f();
            this.a(this.z);
         } else if (var1.k == 6) {
            this.v = true;
            this.u = !this.u;
            this.f();
         } else if (var1.k == 8) {
            if (WorldType.a[this.L] == WorldType.c) {
               this.j.a(new GuiCreateFlatWorld(this, this.a));
            } else {
               this.j.a(new GuiCustomizeWorldScreen(this, this.a));
            }
         }
      }
   }

   private boolean g() {
      WorldType var1 = WorldType.a[this.L];
      if (var1 == null || !var1.e()) {
         return false;
      } else {
         return var1 == WorldType.g ? r() : true;
      }
   }

   private void h() {
      this.a(!this.z);
   }

   private void a(boolean var1) {
      this.z = var1;
      if (WorldType.a[this.L] == WorldType.g) {
         this.A.m = !this.z;
         this.A.l = false;
         if (this.s == null) {
            this.s = this.r;
         }

         this.r = "spectator";
         this.C.m = false;
         this.D.m = false;
         this.E.m = this.z;
         this.F.m = false;
         this.G.m = false;
      } else {
         this.A.m = !this.z;
         this.A.l = true;
         if (this.s != null) {
            this.r = this.s;
            this.s = null;
         }

         this.C.m = this.z && WorldType.a[this.L] != WorldType.f;
         this.D.m = this.z;
         this.E.m = this.z;
         this.F.m = this.z;
         this.G.m = this.z && (WorldType.a[this.L] == WorldType.c || WorldType.a[this.L] == WorldType.f);
      }

      this.f();
      if (this.z) {
         this.B.j = I18n.a("gui.done");
      } else {
         this.B.j = I18n.a("selectWorld.moreWorldOptions");
      }
   }

   @Override
   protected void a(char var1, int var2) {
      if (this.g.m() && !this.z) {
         this.g.a(var1, var2);
         this.K = this.g.b();
      } else if (this.h.m() && this.z) {
         this.h.a(var1, var2);
         this.J = this.h.b();
      }

      if (var2 == 28 || var2 == 156) {
         this.a(this.n.get(0));
      }

      this.n.get(0).l = this.g.b().length() > 0;
      this.a();
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      if (this.z) {
         this.h.a(var1, var2, var3);
      } else {
         this.g.a(var1, var2, var3);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      this.a(this.q, I18n.a("selectWorld.create"), this.l / 2, 20, -1);
      if (this.z) {
         this.c(this.q, I18n.a("selectWorld.enterSeed"), this.l / 2 - 100, 47, -6250336);
         this.c(this.q, I18n.a("selectWorld.seedInfo"), this.l / 2 - 100, 85, -6250336);
         if (this.C.m) {
            this.c(this.q, I18n.a("selectWorld.mapFeatures.info"), this.l / 2 - 150, 122, -6250336);
         }

         if (this.F.m) {
            this.c(this.q, I18n.a("selectWorld.allowCommands.info"), this.l / 2 - 150, 172, -6250336);
         }

         this.h.g();
         if (WorldType.a[this.L].h()) {
            this.q.a(I18n.a(WorldType.a[this.L].c()), this.E.h + 2, this.E.i + 22, this.E.b(), 10526880);
         }
      } else {
         this.c(this.q, I18n.a("selectWorld.enterName"), this.l / 2 - 100, 47, -6250336);
         this.c(this.q, I18n.a("selectWorld.resultFolder") + " " + this.i, this.l / 2 - 100, 85, -6250336);
         this.g.g();
         this.c(this.q, this.H, this.l / 2 - 100, 137, -6250336);
         this.c(this.q, this.I, this.l / 2 - 100, 149, -6250336);
      }

      super.a(var1, var2, var3);
   }

   public void a(WorldInfo var1) {
      this.K = I18n.a("selectWorld.newWorld.copyOf", var1.k());
      this.J = var1.b() + "";
      this.L = var1.u().g();
      this.a = var1.B();
      this.t = var1.s();
      this.u = var1.v();
      if (var1.t()) {
         this.r = "hardcore";
      } else if (var1.r().e()) {
         this.r = "survival";
      } else if (var1.r().d()) {
         this.r = "creative";
      }
   }
}
