package net.minecraft.client.gui.stream;

import com.google.common.collect.Lists;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import tv.twitch.ErrorCode;

public class GuiStreamUnavailable extends GuiScreen {
   private static final Logger a = LogManager.getLogger();
   private final IChatComponent f = new ChatComponentTranslation("stream.unavailable.title");
   private final GuiScreen g;
   private final GuiStreamUnavailable.Reason h;
   private final List<ChatComponentTranslation> i;
   private final List<String> r = Lists.newArrayList();

   public GuiStreamUnavailable(GuiScreen var1, GuiStreamUnavailable.Reason var2) {
      this(var1, var2, null);
   }

   public GuiStreamUnavailable(GuiScreen var1, GuiStreamUnavailable.Reason var2, List<ChatComponentTranslation> var3) {
      this.g = var1;
      this.h = var2;
      this.i = var3;
   }

   @Override
   public void b() {
      if (this.r.isEmpty()) {
         this.r.addAll(this.q.c(this.h.a().d(), (int)(this.l * 0.75F)));
         if (this.i != null) {
            this.r.add("");

            for (ChatComponentTranslation var2 : this.i) {
               this.r.add(var2.e());
            }
         }
      }

      if (this.h.b() != null) {
         this.n.add(new GuiButton(0, this.l / 2 - 155, this.m - 50, 150, 20, I18n.a("gui.cancel")));
         this.n.add(new GuiButton(1, this.l / 2 - 155 + 160, this.m - 50, 150, 20, I18n.a(this.h.b().d())));
      } else {
         this.n.add(new GuiButton(0, this.l / 2 - 75, this.m - 50, 150, 20, I18n.a("gui.cancel")));
      }
   }

   @Override
   public void m() {
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      int var4 = Math.max((int)(this.m * 0.85 / 2.0 - this.r.size() * this.q.a / 2.0F), 50);
      this.a(this.q, this.f.d(), this.l / 2, var4 - this.q.a * 2, 16777215);

      for (String var6 : this.r) {
         this.a(this.q, var6, this.l / 2, var4, 10526880);
         var4 += this.q.a;
      }

      super.a(var1, var2, var3);
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.l) {
         if (var1.k == 1) {
            switch (this.h) {
               case h:
               case i:
                  this.a("https://account.mojang.com/me/settings");
                  break;
               case g:
                  this.a("https://account.mojang.com/migrate");
                  break;
               case e:
                  this.a("http://www.apple.com/osx/");
                  break;
               case l:
               case c:
               case k:
                  this.a("http://bugs.mojang.com/browse/MC");
            }
         }

         this.j.a(this.g);
      }
   }

   private void a(String var1) {
      try {
         Class var2 = Class.forName("java.awt.Desktop");
         Object var3 = var2.getMethod("getDesktop").invoke(null);
         var2.getMethod("browse", URI.class).invoke(var3, new URI(var1));
      } catch (Throwable var4) {
         a.error("Couldn't open link", var4);
      }
   }

   public static void a(GuiScreen var0) {
      Minecraft var1 = Minecraft.A();
      IStream var2 = var1.Y();
      if (!OpenGlHelper.l) {
         ArrayList var3 = Lists.newArrayList();
         var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.version", GL11.glGetString(7938)));
         var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.blend", GLContext.getCapabilities().GL_EXT_blend_func_separate));
         var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.arb", GLContext.getCapabilities().GL_ARB_framebuffer_object));
         var3.add(new ChatComponentTranslation("stream.unavailable.no_fbo.ext", GLContext.getCapabilities().GL_EXT_framebuffer_object));
         var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.a, var3));
      } else if (var2 instanceof NullStream) {
         if (((NullStream)var2).a().getMessage().contains("Can't load AMD 64-bit .dll on a IA 32-bit platform")) {
            var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.b));
         } else {
            var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.c));
         }
      } else if (!var2.A() && var2.B() == ErrorCode.TTV_EC_OS_TOO_OLD) {
         switch (Util.a()) {
            case c:
               var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.d));
               break;
            case d:
               var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.e));
               break;
            default:
               var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.f));
         }
      } else if (!var1.M().containsKey("twitch_access_token")) {
         if (var1.L().f() == Session.Type.a) {
            var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.g));
         } else {
            var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.h));
         }
      } else if (!var2.C()) {
         switch (var2.E()) {
            case b:
               var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.i));
               break;
            case a:
            default:
               var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.j));
         }
      } else if (var2.B() != null) {
         List var4 = Arrays.asList(new ChatComponentTranslation("stream.unavailable.initialization_failure.extra", ErrorCode.getString(var2.B())));
         var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.k, var4));
      } else {
         var1.a(new GuiStreamUnavailable(var0, GuiStreamUnavailable.Reason.l));
      }
   }

   public static enum Reason {
      a(new ChatComponentTranslation("stream.unavailable.no_fbo")),
      b(new ChatComponentTranslation("stream.unavailable.library_arch_mismatch")),
      c(new ChatComponentTranslation("stream.unavailable.library_failure"), new ChatComponentTranslation("stream.unavailable.report_to_mojang")),
      d(new ChatComponentTranslation("stream.unavailable.not_supported.windows")),
      e(new ChatComponentTranslation("stream.unavailable.not_supported.mac"), new ChatComponentTranslation("stream.unavailable.not_supported.mac.okay")),
      f(new ChatComponentTranslation("stream.unavailable.not_supported.other")),
      g(new ChatComponentTranslation("stream.unavailable.account_not_migrated"), new ChatComponentTranslation("stream.unavailable.account_not_migrated.okay")),
      h(new ChatComponentTranslation("stream.unavailable.account_not_bound"), new ChatComponentTranslation("stream.unavailable.account_not_bound.okay")),
      i(new ChatComponentTranslation("stream.unavailable.failed_auth"), new ChatComponentTranslation("stream.unavailable.failed_auth.okay")),
      j(new ChatComponentTranslation("stream.unavailable.failed_auth_error")),
      k(new ChatComponentTranslation("stream.unavailable.initialization_failure"), new ChatComponentTranslation("stream.unavailable.report_to_mojang")),
      l(new ChatComponentTranslation("stream.unavailable.unknown"), new ChatComponentTranslation("stream.unavailable.report_to_mojang"));

      private final IChatComponent m;
      private final IChatComponent n;

      private Reason(IChatComponent var3) {
         this(var3, null);
      }

      private Reason(IChatComponent var3, IChatComponent var4) {
         this.m = var3;
         this.n = var4;
      }

      public IChatComponent a() {
         return this.m;
      }

      public IChatComponent b() {
         return this.n;
      }
   }
}
