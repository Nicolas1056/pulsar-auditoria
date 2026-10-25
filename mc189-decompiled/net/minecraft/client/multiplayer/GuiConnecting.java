package net.minecraft.client.multiplayer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting extends GuiScreen {
   private static final AtomicInteger a = new AtomicInteger(0);
   private static final Logger f = LogManager.getLogger();
   private NetworkManager g;
   private boolean h;
   private final GuiScreen i;

   public GuiConnecting(GuiScreen var1, Minecraft var2, ServerData var3) {
      this.j = var2;
      this.i = var1;
      ServerAddress var4 = ServerAddress.a(var3.b);
      var2.a(null);
      var2.a(var3);
      this.a(var4.a(), var4.b());
   }

   public GuiConnecting(GuiScreen var1, Minecraft var2, String var3, int var4) {
      this.j = var2;
      this.i = var1;
      var2.a(null);
      this.a(var3, var4);
   }

   private void a(final String var1, final int var2) {
      f.info("Connecting to " + var1 + ", " + var2);
      (new Thread("Server Connector #" + a.incrementAndGet()) {
            @Override
            public void run() {
               InetAddress var1x = null;

               try {
                  if (GuiConnecting.this.h) {
                     return;
                  }

                  var1x = InetAddress.getByName(var1);
                  GuiConnecting.this.g = NetworkManager.a(var1x, var2, GuiConnecting.this.j.t.f());
                  GuiConnecting.this.g.a(new NetHandlerLoginClient(GuiConnecting.this.g, GuiConnecting.this.j, GuiConnecting.this.i));
                  GuiConnecting.this.g.a(new C00Handshake(47, var1, var2, EnumConnectionState.d));
                  GuiConnecting.this.g.a(new C00PacketLoginStart(GuiConnecting.this.j.L().e()));
               } catch (UnknownHostException var5) {
                  if (GuiConnecting.this.h) {
                     return;
                  }

                  GuiConnecting.f.error("Couldn't connect to server", var5);
                  GuiConnecting.this.j
                     .a(new GuiDisconnected(GuiConnecting.this.i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", "Unknown host")));
               } catch (Exception var6) {
                  if (GuiConnecting.this.h) {
                     return;
                  }

                  GuiConnecting.f.error("Couldn't connect to server", var6);
                  String var3 = var6.toString();
                  if (var1x != null) {
                     String var4 = var1x.toString() + ":" + var2;
                     var3 = var3.replaceAll(var4, "");
                  }

                  GuiConnecting.this.j
                     .a(new GuiDisconnected(GuiConnecting.this.i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", var3)));
               }
            }
         })
         .start();
   }

   @Override
   public void e() {
      if (this.g != null) {
         if (this.g.g()) {
            this.g.a();
         } else {
            this.g.l();
         }
      }
   }

   @Override
   protected void a(char var1, int var2) {
   }

   @Override
   public void b() {
      this.n.clear();
      this.n.add(new GuiButton(0, this.l / 2 - 100, this.m / 4 + 120 + 12, I18n.a("gui.cancel")));
   }

   @Override
   protected void a(GuiButton var1) {
      if (var1.k == 0) {
         this.h = true;
         if (this.g != null) {
            this.g.a(new ChatComponentText("Aborted"));
         }

         this.j.a(this.i);
      }
   }

   @Override
   public void a(int var1, int var2, float var3) {
      this.c();
      if (this.g == null) {
         this.a(this.q, I18n.a("connect.connecting"), this.l / 2, this.m / 2 - 50, 16777215);
      } else {
         this.a(this.q, I18n.a("connect.authorizing"), this.l / 2, this.m / 2 - 50, 16777215);
      }

      super.a(var1, var2, var3);
   }
}
