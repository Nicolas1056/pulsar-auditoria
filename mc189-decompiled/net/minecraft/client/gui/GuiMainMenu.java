package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
   private static final AtomicInteger f = new AtomicInteger(0);
   private static final Logger g = LogManager.getLogger();
   private static final Random h = new Random();
   private float i;
   private String r;
   private GuiButton s;
   private int t;
   private DynamicTexture u;
   private boolean v = true;
   private final Object w = new Object();
   private String x;
   private String y;
   private String z;
   private static final ResourceLocation A = new ResourceLocation("texts/splashes.txt");
   private static final ResourceLocation B = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation[] C = new ResourceLocation[]{
      new ResourceLocation("textures/gui/title/background/panorama_0.png"),
      new ResourceLocation("textures/gui/title/background/panorama_1.png"),
      new ResourceLocation("textures/gui/title/background/panorama_2.png"),
      new ResourceLocation("textures/gui/title/background/panorama_3.png"),
      new ResourceLocation("textures/gui/title/background/panorama_4.png"),
      new ResourceLocation("textures/gui/title/background/panorama_5.png")
   };
   public static final String a = "Please click " + EnumChatFormatting.t + "here" + EnumChatFormatting.v + " for more information.";
   private int D;
   private int E;
   private int F;
   private int G;
   private int H;
   private int I;
   private ResourceLocation J;
   private GuiButton K;
   private boolean L;
   private GuiScreen M;

   public GuiMainMenu() {
      this.y = a;
      this.L = false;
      this.r = "missingno";
      BufferedReader var1 = null;

      try {
         ArrayList var2 = Lists.newArrayList();
         var1 = new BufferedReader(new InputStreamReader(Minecraft.A().Q().a(A).b(), Charsets.UTF_8));

         String var3;
         while ((var3 = var1.readLine()) != null) {
            var3 = var3.trim();
            if (!var3.isEmpty()) {
               var2.add(var3);
            }
         }

         if (!var2.isEmpty()) {
            do {
               this.r = (String)var2.get(h.nextInt(var2.size()));
            } while (this.r.hashCode() == 125780783);
         }
      } catch (IOException var12) {
      } finally {
         if (var1 != null) {
            try {
               var1.close();
            } catch (IOException var11) {
            }
         }
      }

      this.i = h.nextFloat();
      this.x = "";
      if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.b()) {
         this.x = I18n.a("title.oldgl1");
         this.y = I18n.a("title.oldgl2");
         this.z = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
      }
   }

   private boolean a() {
      return Minecraft.A().t.b(GameSettings.Options.S) && this.M != null;
   }

   @Override
   public void e() {
      this.t++;
      if (this.a()) {
         this.M.e();
      }
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   protected void a(char var1, int var2) {
   }

   @Override
   public void b() {
      this.u = new DynamicTexture(256, 256);
      this.J = this.j.P().a("background", this.u);
      Calendar var1 = Calendar.getInstance();
      var1.setTime(new Date());
      if (var1.get(2) + 1 == 12 && var1.get(5) == 24) {
         this.r = "Merry X-mas!";
      } else if (var1.get(2) + 1 == 1 && var1.get(5) == 1) {
         this.r = "Happy new year!";
      } else if (var1.get(2) + 1 == 10 && var1.get(5) == 31) {
         this.r = "OOoooOOOoooo! Spooky!";
      }

      byte var2 = 24;
      int var3 = this.m / 4 + 48;
      if (this.j.t()) {
         this.c(var3, 24);
      } else {
         this.b(var3, 24);
      }

      this.n.add(new GuiButton(0, this.l / 2 - 100, var3 + 72 + 12, 98, 20, I18n.a("menu.options")));
      this.n.add(new GuiButton(4, this.l / 2 + 2, var3 + 72 + 12, 98, 20, I18n.a("menu.quit")));
      this.n.add(new GuiButtonLanguage(5, this.l / 2 - 124, var3 + 72 + 12));
      synchronized (this.w) {
         this.E = this.q.a(this.x);
         this.D = this.q.a(this.y);
         int var5 = Math.max(this.E, this.D);
         this.F = (this.l - var5) / 2;
         this.G = this.n.get(0).i - 24;
         this.H = this.F + var5;
         this.I = this.G + 24;
      }

      this.j.a(false);
      if (Minecraft.A().t.b(GameSettings.Options.S) && !this.L) {
         RealmsBridge var8 = new RealmsBridge();
         this.M = var8.getNotificationScreen(this);
         this.L = true;
      }

      if (this.a()) {
         this.M.a(this.l, this.m);
         this.M.b();
      }
   }

   private void b(int var1, int var2) {
      this.n.add(new GuiButton(1, this.l / 2 - 100, var1, I18n.a("menu.singleplayer")));
      this.n.add(new GuiButton(2, this.l / 2 - 100, var1 + var2 * 1, I18n.a("menu.multiplayer")));
      this.n.add(this.K = new GuiButton(14, this.l / 2 - 100, var1 + var2 * 2, I18n.a("menu.online")));
   }

   private void c(int var1, int var2) {
      this.n.add(new GuiButton(11, this.l / 2 - 100, var1, I18n.a("menu.playdemo")));
      this.n.add(this.s = new GuiButton(12, this.l / 2 - 100, var1 + var2 * 1, I18n.a("menu.resetdemo")));
      ISaveFormat var3 = this.j.f();
      WorldInfo var4 = var3.c("Demo_World");
      if (var4 == null) {
         this.s.l = false;
      }
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 0) {
         this.j.a(new GuiOptions(this, this.j.t));
      }

      if (var1.k == 5) {
         this.j.a(new GuiLanguage(this, this.j.t, this.j.S()));
      }

      if (var1.k == 1) {
         this.j.a(new GuiSelectWorld(this));
      }

      if (var1.k == 2) {
         this.j.a(new GuiMultiplayer(this));
      }

      if (var1.k == 14 && this.K.m) {
         this.f();
      }

      if (var1.k == 4) {
         this.j.m();
      }

      if (var1.k == 11) {
         this.j.a("Demo_World", "Demo_World", DemoWorldServer.a);
      }

      if (var1.k == 12) {
         ISaveFormat var2 = this.j.f();
         WorldInfo var3 = var2.c("Demo_World");
         if (var3 != null) {
            GuiYesNo var4 = GuiSelectWorld.a(this, var3.k(), 12);
            this.j.a(var4);
         }
      }
   }

   private void f() {
      RealmsBridge var1 = new RealmsBridge();
      var1.switchToRealms(this);
   }

   @Override
   public void a(boolean var1, int var2) {
      if (var1 && var2 == 12) {
         ISaveFormat var6 = this.j.f();
         var6.d();
         var6.e("Demo_World");
         this.j.a(this);
      } else if (var2 == 13) {
         if (var1) {
            try {
               Class var3 = Class.forName("java.awt.Desktop");
               Object var4 = var3.getMethod("getDesktop").invoke(null);
               var3.getMethod("browse", URI.class).invoke(var4, new URI(this.z));
            } catch (Throwable var5) {
               g.error("Couldn't open link", var5);
            }
         }

         this.j.a(this);
      }
   }

   private void b(int var1, int var2, float var3) {
      Tessellator var4 = Tessellator.a();
      WorldRenderer var5 = var4.c();
      GlStateManager.n(5889);
      GlStateManager.E();
      GlStateManager.D();
      Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
      GlStateManager.n(5888);
      GlStateManager.E();
      GlStateManager.D();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.b(180.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(90.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.l();
      GlStateManager.c();
      GlStateManager.p();
      GlStateManager.a(false);
      GlStateManager.a(770, 771, 1, 0);
      byte var6 = 8;

      for (int var7 = 0; var7 < var6 * var6; var7++) {
         GlStateManager.E();
         float var8 = ((float)(var7 % var6) / var6 - 0.5F) / 64.0F;
         float var9 = ((float)(var7 / var6) / var6 - 0.5F) / 64.0F;
         float var10 = 0.0F;
         GlStateManager.b(var8, var9, var10);
         GlStateManager.b(MathHelper.a((this.t + var3) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(-(this.t + var3) * 0.1F, 0.0F, 1.0F, 0.0F);

         for (int var11 = 0; var11 < 6; var11++) {
            GlStateManager.E();
            if (var11 == 1) {
               GlStateManager.b(90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var11 == 2) {
               GlStateManager.b(180.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var11 == 3) {
               GlStateManager.b(-90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var11 == 4) {
               GlStateManager.b(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var11 == 5) {
               GlStateManager.b(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            this.j.P().a(C[var11]);
            var5.a(7, DefaultVertexFormats.i);
            int var12 = 255 / (var7 + 1);
            float var13 = 0.0F;
            var5.b(-1.0, -1.0, 1.0).a(0.0, 0.0).b(255, 255, 255, var12).d();
            var5.b(1.0, -1.0, 1.0).a(1.0, 0.0).b(255, 255, 255, var12).d();
            var5.b(1.0, 1.0, 1.0).a(1.0, 1.0).b(255, 255, 255, var12).d();
            var5.b(-1.0, 1.0, 1.0).a(0.0, 1.0).b(255, 255, 255, var12).d();
            var4.b();
            GlStateManager.F();
         }

         GlStateManager.F();
         GlStateManager.a(true, true, true, false);
      }

      var5.c(0.0, 0.0, 0.0);
      GlStateManager.a(true, true, true, true);
      GlStateManager.n(5889);
      GlStateManager.F();
      GlStateManager.n(5888);
      GlStateManager.F();
      GlStateManager.a(true);
      GlStateManager.o();
      GlStateManager.j();
   }

   private void a(float var1) {
      this.j.P().a(this.J);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
      GlStateManager.l();
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.a(true, true, true, false);
      Tessellator var2 = Tessellator.a();
      WorldRenderer var3 = var2.c();
      var3.a(7, DefaultVertexFormats.i);
      GlStateManager.c();
      byte var4 = 3;

      for (int var5 = 0; var5 < var4; var5++) {
         float var6 = 1.0F / (var5 + 1);
         int var7 = this.l;
         int var8 = this.m;
         float var9 = (var5 - var4 / 2) / 256.0F;
         var3.b((double)var7, (double)var8, (double)this.e).a((double)(0.0F + var9), 1.0).a(1.0F, 1.0F, 1.0F, var6).d();
         var3.b((double)var7, 0.0, (double)this.e).a((double)(1.0F + var9), 1.0).a(1.0F, 1.0F, 1.0F, var6).d();
         var3.b(0.0, 0.0, (double)this.e).a((double)(1.0F + var9), 0.0).a(1.0F, 1.0F, 1.0F, var6).d();
         var3.b(0.0, (double)var8, (double)this.e).a((double)(0.0F + var9), 0.0).a(1.0F, 1.0F, 1.0F, var6).d();
      }

      var2.b();
      GlStateManager.d();
      GlStateManager.a(true, true, true, true);
   }

   private void c(int var1, int var2, float var3) {
      this.j.b().e();
      GlStateManager.b(0, 0, 256, 256);
      this.b(var1, var2, var3);
      this.a(var3);
      this.a(var3);
      this.a(var3);
      this.a(var3);
      this.a(var3);
      this.a(var3);
      this.a(var3);
      this.j.b().a(true);
      GlStateManager.b(0, 0, this.j.d, this.j.e);
      float var4 = this.l > this.m ? 120.0F / this.l : 120.0F / this.m;
      float var5 = this.m * var4 / 256.0F;
      float var6 = this.l * var4 / 256.0F;
      int var7 = this.l;
      int var8 = this.m;
      Tessellator var9 = Tessellator.a();
      WorldRenderer var10 = var9.c();
      var10.a(7, DefaultVertexFormats.i);
      var10.b(0.0, (double)var8, (double)this.e).a((double)(0.5F - var5), (double)(0.5F + var6)).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      var10.b((double)var7, (double)var8, (double)this.e).a((double)(0.5F - var5), (double)(0.5F - var6)).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      var10.b((double)var7, 0.0, (double)this.e).a((double)(0.5F + var5), (double)(0.5F - var6)).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      var10.b(0.0, 0.0, (double)this.e).a((double)(0.5F + var5), (double)(0.5F + var6)).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      var9.b();
   }

   @Override
   public void a(int var1, int var2, float var3) {
      GlStateManager.c();
      this.c(var1, var2, var3);
      GlStateManager.d();
      Tessellator var4 = Tessellator.a();
      WorldRenderer var5 = var4.c();
      short var6 = 274;
      int var7 = this.l / 2 - var6 / 2;
      byte var8 = 30;
      this.a(0, 0, this.l, this.m, -2130706433, 16777215);
      this.a(0, 0, this.l, this.m, 0, Integer.MIN_VALUE);
      this.j.P().a(B);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.i < 1.0E-4) {
         this.b(var7 + 0, var8 + 0, 0, 0, 99, 44);
         this.b(var7 + 99, var8 + 0, 129, 0, 27, 44);
         this.b(var7 + 99 + 26, var8 + 0, 126, 0, 3, 44);
         this.b(var7 + 99 + 26 + 3, var8 + 0, 99, 0, 26, 44);
         this.b(var7 + 155, var8 + 0, 0, 45, 155, 44);
      } else {
         this.b(var7 + 0, var8 + 0, 0, 0, 155, 44);
         this.b(var7 + 155, var8 + 0, 0, 45, 155, 44);
      }

      GlStateManager.E();
      GlStateManager.b((float)(this.l / 2 + 90), 70.0F, 0.0F);
      GlStateManager.b(-20.0F, 0.0F, 0.0F, 1.0F);
      float var9 = 1.8F - MathHelper.e(MathHelper.a((float)(Minecraft.J() % 1000L) / 1000.0F * (float) Math.PI * 2.0F) * 0.1F);
      var9 = var9 * 100.0F / (this.q.a(this.r) + 32);
      GlStateManager.a(var9, var9, var9);
      this.a(this.q, this.r, 0, -8, -256);
      GlStateManager.F();
      String var10 = "Minecraft 1.8.9";
      if (this.j.t()) {
         var10 = var10 + " Demo";
      }

      this.c(this.q, var10, 2, this.m - 10, -1);
      String var11 = "Copyright Mojang AB. Do not distribute!";
      this.c(this.q, var11, this.l - this.q.a(var11) - 2, this.m - 10, -1);
      if (this.x != null && this.x.length() > 0) {
         a(this.F - 2, this.G - 2, this.H + 2, this.I - 1, 1428160512);
         this.c(this.q, this.x, this.F, this.G, -1);
         this.c(this.q, this.y, (this.l - this.D) / 2, this.n.get(0).i - 12, -1);
      }

      super.a(var1, var2, var3);
      if (this.a()) {
         this.M.a(var1, var2, var3);
      }
   }

   @Override
   protected void a(int var1, int var2, int var3) {
      super.a(var1, var2, var3);
      synchronized (this.w) {
         if (this.x.length() > 0 && var1 >= this.F && var1 <= this.H && var2 >= this.G && var2 <= this.I) {
            GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, this.z, 13, true);
            var5.f();
            this.j.a(var5);
         }
      }

      if (this.a()) {
         this.M.a(var1, var2, var3);
      }
   }

   @Override
   public void m() {
      if (this.M != null) {
         this.M.m();
      }
   }
}
