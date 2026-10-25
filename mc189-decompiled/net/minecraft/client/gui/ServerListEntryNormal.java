package net.minecraft.client.gui;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry {
   private static final Logger a = LogManager.getLogger();
   private static final ThreadPoolExecutor b = new ScheduledThreadPoolExecutor(
      5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build()
   );
   private static final ResourceLocation c = new ResourceLocation("textures/misc/unknown_server.png");
   private static final ResourceLocation d = new ResourceLocation("textures/gui/server_selection.png");
   private final GuiMultiplayer e;
   private final Minecraft f;
   private final ServerData g;
   private final ResourceLocation h;
   private String i;
   private DynamicTexture j;
   private long k;

   protected ServerListEntryNormal(GuiMultiplayer var1, ServerData var2) {
      this.e = var1;
      this.g = var2;
      this.f = Minecraft.A();
      this.h = new ResourceLocation("servers/" + var2.b + "/icon");
      this.j = (DynamicTexture)this.f.P().b(this.h);
   }

   @Override
   public void a(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      if (!this.g.h) {
         this.g.h = true;
         this.g.e = -2L;
         this.g.d = "";
         this.g.c = "";
         b.submit(new Runnable() {
            @Override
            public void run() {
               try {
                  ServerListEntryNormal.this.e.g().a(ServerListEntryNormal.this.g);
               } catch (UnknownHostException var2x) {
                  ServerListEntryNormal.this.g.e = -1L;
                  ServerListEntryNormal.this.g.d = EnumChatFormatting.e + "Can't resolve hostname";
               } catch (Exception var3x) {
                  ServerListEntryNormal.this.g.e = -1L;
                  ServerListEntryNormal.this.g.d = EnumChatFormatting.e + "Can't connect to server.";
               }
            }
         });
      }

      boolean var9 = this.g.f > 47;
      boolean var10 = this.g.f < 47;
      boolean var11 = var9 || var10;
      this.f.k.a(this.g.a, var2 + 32 + 3, var3 + 1, 16777215);
      List var12 = this.f.k.c(this.g.d, var4 - 32 - 2);

      for (int var13 = 0; var13 < Math.min(var12.size(), 2); var13++) {
         this.f.k.a((String)var12.get(var13), var2 + 32 + 3, var3 + 12 + this.f.k.a * var13, 8421504);
      }

      String var23 = var11 ? EnumChatFormatting.e + this.g.g : this.g.c;
      int var14 = this.f.k.a(var23);
      this.f.k.a(var23, var2 + var4 - var14 - 15 - 2, var3 + 1, 8421504);
      byte var15 = 0;
      String var17 = null;
      int var16;
      String var18;
      if (var11) {
         var16 = 5;
         var18 = var9 ? "Client out of date!" : "Server out of date!";
         var17 = this.g.i;
      } else if (this.g.h && this.g.e != -2L) {
         if (this.g.e < 0L) {
            var16 = 5;
         } else if (this.g.e < 150L) {
            var16 = 0;
         } else if (this.g.e < 300L) {
            var16 = 1;
         } else if (this.g.e < 600L) {
            var16 = 2;
         } else if (this.g.e < 1000L) {
            var16 = 3;
         } else {
            var16 = 4;
         }

         if (this.g.e < 0L) {
            var18 = "(no connection)";
         } else {
            var18 = this.g.e + "ms";
            var17 = this.g.i;
         }
      } else {
         var15 = 1;
         var16 = (int)(Minecraft.J() / 100L + var1 * 2 & 7L);
         if (var16 > 4) {
            var16 = 8 - var16;
         }

         var18 = "Pinging...";
      }

      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.f.P().a(Gui.d);
      Gui.a(var2 + var4 - 15, var3, var15 * 10, 176 + var16 * 8, 10, 8, 256.0F, 256.0F);
      if (this.g.c() != null && !this.g.c().equals(this.i)) {
         this.i = this.g.c();
         this.c();
         this.e.h().b();
      }

      if (this.j != null) {
         this.a(var2, var3, this.h);
      } else {
         this.a(var2, var3, c);
      }

      int var19 = var6 - var2;
      int var20 = var7 - var3;
      if (var19 >= var4 - 15 && var19 <= var4 - 5 && var20 >= 0 && var20 <= 8) {
         this.e.a(var18);
      } else if (var19 >= var4 - var14 - 15 - 2 && var19 <= var4 - 15 - 2 && var20 >= 0 && var20 <= 8) {
         this.e.a(var17);
      }

      if (this.f.t.A || var8) {
         this.f.P().a(d);
         Gui.a(var2, var3, var2 + 32, var3 + 32, -1601138544);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         int var21 = var6 - var2;
         int var22 = var7 - var3;
         if (this.b()) {
            if (var21 < 32 && var21 > 16) {
               Gui.a(var2, var3, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.a(var2, var3, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.e.a(this, var1)) {
            if (var21 < 16 && var22 < 16) {
               Gui.a(var2, var3, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.a(var2, var3, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.e.b(this, var1)) {
            if (var21 < 16 && var22 > 16) {
               Gui.a(var2, var3, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.a(var2, var3, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }
      }
   }

   protected void a(int var1, int var2, ResourceLocation var3) {
      this.f.P().a(var3);
      GlStateManager.l();
      Gui.a(var1, var2, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      GlStateManager.k();
   }

   private boolean b() {
      return true;
   }

   private void c() {
      if (this.g.c() == null) {
         this.f.P().c(this.h);
         this.j = null;
      } else {
         ByteBuf var2 = Unpooled.copiedBuffer(this.g.c(), Charsets.UTF_8);
         ByteBuf var3 = Base64.decode(var2);

         BufferedImage var1;
         label62: {
            try {
               var1 = TextureUtil.a(new ByteBufInputStream(var3));
               Validate.validState(var1.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(var1.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
               break label62;
            } catch (Throwable var8) {
               a.error("Invalid icon for server " + this.g.a + " (" + this.g.b + ")", var8);
               this.g.a(null);
            } finally {
               var2.release();
               var3.release();
            }

            return;
         }

         if (this.j == null) {
            this.j = new DynamicTexture(var1.getWidth(), var1.getHeight());
            this.f.P().a(this.h, this.j);
         }

         var1.getRGB(0, 0, var1.getWidth(), var1.getHeight(), this.j.e(), 0, var1.getWidth());
         this.j.d();
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var5 <= 32) {
         if (var5 < 32 && var5 > 16 && this.b()) {
            this.e.b(var1);
            this.e.f();
            return true;
         }

         if (var5 < 16 && var6 < 16 && this.e.a(this, var1)) {
            this.e.a(this, var1, GuiScreen.r());
            return true;
         }

         if (var5 < 16 && var6 > 16 && this.e.b(this, var1)) {
            this.e.b(this, var1, GuiScreen.r());
            return true;
         }
      }

      this.e.b(var1);
      if (Minecraft.J() - this.k < 250L) {
         this.e.f();
      }

      this.k = Minecraft.J();
      return false;
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }

   @Override
   public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public ServerData a() {
      return this.g;
   }
}
