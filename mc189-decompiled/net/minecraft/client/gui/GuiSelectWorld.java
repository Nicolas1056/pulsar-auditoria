package net.minecraft.client.gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback {
   private static final Logger g = LogManager.getLogger();
   private final DateFormat h = new SimpleDateFormat();
   protected GuiScreen a;
   protected String f = "Select world";
   private boolean i;
   private int r;
   private java.util.List<SaveFormatComparator> s;
   private GuiSelectWorld.List t;
   private String u;
   private String v;
   private String[] w = new String[4];
   private boolean x;
   private GuiButton y;
   private GuiButton z;
   private GuiButton A;
   private GuiButton B;

   public GuiSelectWorld(GuiScreen var1) {
      this.a = var1;
   }

   @Override
   public void b() {
      this.f = I18n.a("selectWorld.title");

      try {
         this.f();
      } catch (AnvilConverterException var2) {
         g.error("Couldn't load level list", var2);
         this.j.a(new GuiErrorScreen("Unable to load worlds", var2.getMessage()));
         return;
      }

      this.u = I18n.a("selectWorld.world");
      this.v = I18n.a("selectWorld.conversion");
      this.w[WorldSettings.GameType.b.a()] = I18n.a("gameMode.survival");
      this.w[WorldSettings.GameType.c.a()] = I18n.a("gameMode.creative");
      this.w[WorldSettings.GameType.d.a()] = I18n.a("gameMode.adventure");
      this.w[WorldSettings.GameType.e.a()] = I18n.a("gameMode.spectator");
      this.t = new GuiSelectWorld.List(this.j);
      this.t.d(4, 5);
      this.a();
   }

   @Override
   public void k() {
      super.k();
      this.t.p();
   }

   private void f() throws AnvilConverterException {
      ISaveFormat var1 = this.j.f();
      this.s = var1.b();
      Collections.sort(this.s);
      this.r = -1;
   }

   protected String b(int var1) {
      return this.s.get(var1).a();
   }

   protected String h(int var1) {
      String var2 = this.s.get(var1).b();
      if (StringUtils.isEmpty(var2)) {
         var2 = I18n.a("selectWorld.world") + " " + (var1 + 1);
      }

      return var2;
   }

   public void a() {
      this.n.add(this.z = new GuiButton(1, this.l / 2 - 154, this.m - 52, 150, 20, I18n.a("selectWorld.select")));
      this.n.add(new GuiButton(3, this.l / 2 + 4, this.m - 52, 150, 20, I18n.a("selectWorld.create")));
      this.n.add(this.A = new GuiButton(6, this.l / 2 - 154, this.m - 28, 72, 20, I18n.a("selectWorld.rename")));
      this.n.add(this.y = new GuiButton(2, this.l / 2 - 76, this.m - 28, 72, 20, I18n.a("selectWorld.delete")));
      this.n.add(this.B = new GuiButton(7, this.l / 2 + 4, this.m - 28, 72, 20, I18n.a("selectWorld.recreate")));
      this.n.add(new GuiButton(0, this.l / 2 + 82, this.m - 28, 72, 20, I18n.a("gui.cancel")));
      this.z.l = false;
      this.y.l = false;
      this.A.l = false;
      this.B.l = false;
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 2) {
            String var2 = this.h(this.r);
            if (var2 != null) {
               this.x = true;
               GuiYesNo var3 = a(this, var2, this.r);
               this.j.a(var3);
            }
         } else if (var1.k == 1) {
            this.i(this.r);
         } else if (var1.k == 3) {
            this.j.a(new GuiCreateWorld(this));
         } else if (var1.k == 6) {
            this.j.a(new GuiRenameWorld(this, this.b(this.r)));
         } else if (var1.k == 0) {
            this.j.a(this.a);
         } else if (var1.k == 7) {
            GuiCreateWorld var5 = new GuiCreateWorld(this);
            ISaveHandler var6 = this.j.f().a(this.b(this.r), false);
            WorldInfo var4 = var6.d();
            var6.a();
            var5.a(var4);
            this.j.a(var5);
         } else {
            this.t.a(var1);
         }
      }
   }

   public void i(int var1) {
      this.j.a(null);
      if (!this.i) {
         this.i = true;
         String var2 = this.b(var1);
         if (var2 == null) {
            var2 = "World" + var1;
         }

         String var3 = this.h(var1);
         if (var3 == null) {
            var3 = "World" + var1;
         }

         if (this.j.f().f(var2)) {
            this.j.a(var2, var3, null);
         }
      }
   }

   @Override
   public void a(boolean var1, int var2) {
      if (this.x) {
         this.x = false;
         if (var1) {
            ISaveFormat var3 = this.j.f();
            var3.d();
            var3.e(this.b(var2));

            try {
               this.f();
            } catch (AnvilConverterException var5) {
               g.error("Couldn't load level list", var5);
            }
         }

         this.j.a(this);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.t.a(var1, var2, var3);
      this.a(this.q, this.f, this.l / 2, 20, 16777215);
      super.a(var1, var2, var3);
   }

   public static GuiYesNo a(GuiYesNoCallback var0, String var1, int var2) {
      String var3 = I18n.a("selectWorld.deleteQuestion");
      String var4 = "'" + var1 + "' " + I18n.a("selectWorld.deleteWarning");
      String var5 = I18n.a("selectWorld.deleteButton");
      String var6 = I18n.a("gui.cancel");
      return new GuiYesNo(var0, var3, var4, var5, var6, var2);
   }

   class List extends GuiSlot {
      public List(Minecraft var2) {
         super(var2, GuiSelectWorld.this.l, GuiSelectWorld.this.m, 32, GuiSelectWorld.this.m - 64, 36);
      }

      @Override
      protected int b() {
         return GuiSelectWorld.this.s.size();
      }

      @Override
      protected void a(int var1, boolean var2, int var3, int var4) {
         GuiSelectWorld.this.r = var1;
         boolean var5 = GuiSelectWorld.this.r >= 0 && GuiSelectWorld.this.r < this.b();
         GuiSelectWorld.this.z.l = var5;
         GuiSelectWorld.this.y.l = var5;
         GuiSelectWorld.this.A.l = var5;
         GuiSelectWorld.this.B.l = var5;
         if (var2 && var5) {
            GuiSelectWorld.this.i(var1);
         }
      }

      @Override
      protected boolean a(int var1) {
         return var1 == GuiSelectWorld.this.r;
      }

      @Override
      protected int k() {
         return GuiSelectWorld.this.s.size() * 36;
      }

      @Override
      protected void a() {
         GuiSelectWorld.this.c();
      }

      @Override
      protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
         SaveFormatComparator var7 = GuiSelectWorld.this.s.get(var1);
         String var8 = var7.b();
         if (StringUtils.isEmpty(var8)) {
            var8 = GuiSelectWorld.this.u + " " + (var1 + 1);
         }

         String var9 = var7.a();
         var9 = var9 + " (" + GuiSelectWorld.this.h.format(new Date(var7.e()));
         var9 = var9 + ")";
         String var10 = "";
         if (var7.d()) {
            var10 = GuiSelectWorld.this.v + " " + var10;
         } else {
            var10 = GuiSelectWorld.this.w[var7.f().a()];
            if (var7.g()) {
               var10 = EnumChatFormatting.e + I18n.a("gameMode.hardcore") + EnumChatFormatting.v;
            }

            if (var7.h()) {
               var10 = var10 + ", " + I18n.a("selectWorld.cheats");
            }
         }

         GuiSelectWorld.this.c(GuiSelectWorld.this.q, var8, var2 + 2, var3 + 1, 16777215);
         GuiSelectWorld.this.c(GuiSelectWorld.this.q, var9, var2 + 2, var3 + 12, 8421504);
         GuiSelectWorld.this.c(GuiSelectWorld.this.q, var10, var2 + 2, var3 + 12 + 10, 8421504);
      }
   }
}
