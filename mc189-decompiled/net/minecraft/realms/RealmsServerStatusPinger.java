package net.minecraft.realms;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerStatusPinger {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<NetworkManager> connections = Collections.synchronizedList(Lists.newArrayList());

   public void pingServer(final String var1, final RealmsServerPing var2) throws UnknownHostException {
      if (var1 != null && !var1.startsWith("0.0.0.0") && !var1.isEmpty()) {
         RealmsServerAddress var3 = RealmsServerAddress.parseString(var1);
         final NetworkManager var4 = NetworkManager.a(InetAddress.getByName(var3.getHost()), var3.getPort(), false);
         this.connections.add(var4);
         var4.a(new INetHandlerStatusClient() {
            private boolean e = false;

            @Override
            public void a(S00PacketServerInfo var1x) {
               ServerStatusResponse var2x = var1x.a();
               if (var2x.b() != null) {
                  var2.nrOfPlayers = String.valueOf(var2x.b().b());
                  if (ArrayUtils.isNotEmpty(var2x.b().c())) {
                     StringBuilder var3x = new StringBuilder();

                     for (GameProfile var7 : var2x.b().c()) {
                        if (var3x.length() > 0) {
                           var3x.append("\n");
                        }

                        var3x.append(var7.getName());
                     }

                     if (var2x.b().c().length < var2x.b().b()) {
                        if (var3x.length() > 0) {
                           var3x.append("\n");
                        }

                        var3x.append("... and ").append(var2x.b().b() - var2x.b().c().length).append(" more ...");
                     }

                     var2.playerList = var3x.toString();
                  }
               } else {
                  var2.playerList = "";
               }

               var4.a(new C01PacketPing(Realms.currentTimeMillis()));
               this.e = true;
            }

            @Override
            public void a(S01PacketPong var1x) {
               var4.a(new ChatComponentText("Finished"));
            }

            @Override
            public void a(IChatComponent var1x) {
               if (!this.e) {
                  RealmsServerStatusPinger.LOGGER.error("Can't ping " + var1 + ": " + var1x.c());
               }
            }
         });

         try {
            var4.a(new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, var3.getHost(), var3.getPort(), EnumConnectionState.c));
            var4.a(new C00PacketServerQuery());
         } catch (Throwable var6) {
            LOGGER.error(var6);
         }
      }
   }

   public void tick() {
      synchronized (this.connections) {
         Iterator var2 = this.connections.iterator();

         while (var2.hasNext()) {
            NetworkManager var3 = (NetworkManager)var2.next();
            if (var3.g()) {
               var3.a();
            } else {
               var2.remove();
               var3.l();
            }
         }
      }
   }

   public void removeAll() {
      synchronized (this.connections) {
         Iterator var2 = this.connections.iterator();

         while (var2.hasNext()) {
            NetworkManager var3 = (NetworkManager)var2.next();
            if (var3.g()) {
               var2.remove();
               var3.a(new ChatComponentText("Cancelled"));
            }
         }
      }
   }
}
