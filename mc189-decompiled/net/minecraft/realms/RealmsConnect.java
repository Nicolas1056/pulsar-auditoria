package net.minecraft.realms;

import java.net.InetAddress;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnect {
   private static final Logger LOGGER = LogManager.getLogger();
   private final RealmsScreen onlineScreen;
   private volatile boolean aborted = false;
   private NetworkManager connection;

   public RealmsConnect(RealmsScreen var1) {
      this.onlineScreen = var1;
   }

   public void connect(final String var1, final int var2) {
      Realms.setConnectedToRealms(true);
      (new Thread("Realms-connect-task") {
            @Override
            public void run() {
               InetAddress var1x = null;

               try {
                  var1x = InetAddress.getByName(var1);
                  if (RealmsConnect.this.aborted) {
                     return;
                  }

                  RealmsConnect.this.connection = NetworkManager.a(var1x, var2, Minecraft.A().t.f());
                  if (RealmsConnect.this.aborted) {
                     return;
                  }

                  RealmsConnect.this.connection
                     .a(new NetHandlerLoginClient(RealmsConnect.this.connection, Minecraft.A(), RealmsConnect.this.onlineScreen.getProxy()));
                  if (RealmsConnect.this.aborted) {
                     return;
                  }

                  RealmsConnect.this.connection.a(new C00Handshake(47, var1, var2, EnumConnectionState.d));
                  if (RealmsConnect.this.aborted) {
                     return;
                  }

                  RealmsConnect.this.connection.a(new C00PacketLoginStart(Minecraft.A().L().e()));
               } catch (UnknownHostException var5) {
                  Realms.clearResourcePack();
                  if (RealmsConnect.this.aborted) {
                     return;
                  }

                  RealmsConnect.LOGGER.error("Couldn't connect to world", var5);
                  Minecraft.A().R().f();
                  Realms.setScreen(
                     new DisconnectedRealmsScreen(
                        RealmsConnect.this.onlineScreen,
                        "connect.failed",
                        new ChatComponentTranslation("disconnect.genericReason", "Unknown host '" + var1 + "'")
                     )
                  );
               } catch (Exception var6) {
                  Realms.clearResourcePack();
                  if (RealmsConnect.this.aborted) {
                     return;
                  }

                  RealmsConnect.LOGGER.error("Couldn't connect to world", var6);
                  String var3 = var6.toString();
                  if (var1x != null) {
                     String var4 = var1x.toString() + ":" + var2;
                     var3 = var3.replaceAll(var4, "");
                  }

                  Realms.setScreen(
                     new DisconnectedRealmsScreen(
                        RealmsConnect.this.onlineScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", var3)
                     )
                  );
               }
            }
         })
         .start();
   }

   public void abort() {
      this.aborted = true;
   }

   public void tick() {
      if (this.connection != null) {
         if (this.connection.g()) {
            this.connection.a();
         } else {
            this.connection.l();
         }
      }
   }
}
