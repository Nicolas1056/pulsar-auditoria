package net.minecraft.server.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginServer implements INetHandlerLoginServer, ITickable {
   private static final AtomicInteger b = new AtomicInteger(0);
   private static final Logger c = LogManager.getLogger();
   private static final Random d = new Random();
   private final byte[] e = new byte[4];
   private final MinecraftServer f;
   public final NetworkManager a;
   private NetHandlerLoginServer.LoginState g = NetHandlerLoginServer.LoginState.a;
   private int h;
   private GameProfile i;
   private String j = "";
   private SecretKey k;
   private EntityPlayerMP l;

   public NetHandlerLoginServer(MinecraftServer var1, NetworkManager var2) {
      this.f = var1;
      this.a = var2;
      d.nextBytes(this.e);
   }

   @Override
   public void c() {
      if (this.g == NetHandlerLoginServer.LoginState.d) {
         this.b();
      } else if (this.g == NetHandlerLoginServer.LoginState.e) {
         EntityPlayerMP var1 = this.f.ap().a(this.i.getId());
         if (var1 == null) {
            this.g = NetHandlerLoginServer.LoginState.d;
            this.f.ap().a(this.a, this.l);
            this.l = null;
         }
      }

      if (this.h++ == 600) {
         this.a("Took too long to log in");
      }
   }

   public void a(String var1) {
      try {
         c.info("Disconnecting " + this.d() + ": " + var1);
         ChatComponentText var2 = new ChatComponentText(var1);
         this.a.a(new S00PacketDisconnect(var2));
         this.a.a(var2);
      } catch (Exception var3) {
         c.error("Error whilst disconnecting player", var3);
      }
   }

   public void b() {
      if (!this.i.isComplete()) {
         this.i = this.a(this.i);
      }

      String var1 = this.f.ap().a(this.a.b(), this.i);
      if (var1 != null) {
         this.a(var1);
      } else {
         this.g = NetHandlerLoginServer.LoginState.f;
         if (this.f.aK() >= 0 && !this.a.c()) {
            this.a.a(new S03PacketEnableCompression(this.f.aK()), new ChannelFutureListener() {
               public void a(ChannelFuture var1) throws Exception {
                  NetHandlerLoginServer.this.a.a(NetHandlerLoginServer.this.f.aK());
               }
            });
         }

         this.a.a(new S02PacketLoginSuccess(this.i));
         EntityPlayerMP var2 = this.f.ap().a(this.i.getId());
         if (var2 != null) {
            this.g = NetHandlerLoginServer.LoginState.e;
            this.l = this.f.ap().g(this.i);
         } else {
            this.f.ap().a(this.a, this.f.ap().g(this.i));
         }
      }
   }

   @Override
   public void a(IChatComponent var1) {
      c.info(this.d() + " lost connection: " + var1.c());
   }

   public String d() {
      return this.i != null ? this.i.toString() + " (" + this.a.b().toString() + ")" : String.valueOf(this.a.b());
   }

   @Override
   public void a(C00PacketLoginStart var1) {
      Validate.validState(this.g == NetHandlerLoginServer.LoginState.a, "Unexpected hello packet", new Object[0]);
      this.i = var1.a();
      if (this.f.af() && !this.a.c()) {
         this.g = NetHandlerLoginServer.LoginState.b;
         this.a.a(new S01PacketEncryptionRequest(this.j, this.f.Q().getPublic(), this.e));
      } else {
         this.g = NetHandlerLoginServer.LoginState.d;
      }
   }

   @Override
   public void a(C01PacketEncryptionResponse var1) {
      Validate.validState(this.g == NetHandlerLoginServer.LoginState.b, "Unexpected key packet", new Object[0]);
      PrivateKey var2 = this.f.Q().getPrivate();
      if (!Arrays.equals(this.e, var1.b(var2))) {
         throw new IllegalStateException("Invalid nonce!");
      } else {
         this.k = var1.a(var2);
         this.g = NetHandlerLoginServer.LoginState.c;
         this.a.a(this.k);
         (new Thread("User Authenticator #" + b.incrementAndGet()) {
               @Override
               public void run() {
                  GameProfile var1x = NetHandlerLoginServer.this.i;

                  try {
                     String var2x = new BigInteger(
                           CryptManager.a(NetHandlerLoginServer.this.j, NetHandlerLoginServer.this.f.Q().getPublic(), NetHandlerLoginServer.this.k)
                        )
                        .toString(16);
                     NetHandlerLoginServer.this.i = NetHandlerLoginServer.this.f.aD().hasJoinedServer(new GameProfile(null, var1x.getName()), var2x);
                     if (NetHandlerLoginServer.this.i != null) {
                        NetHandlerLoginServer.c
                           .info("UUID of player " + NetHandlerLoginServer.this.i.getName() + " is " + NetHandlerLoginServer.this.i.getId());
                        NetHandlerLoginServer.this.g = NetHandlerLoginServer.LoginState.d;
                     } else if (NetHandlerLoginServer.this.f.T()) {
                        NetHandlerLoginServer.c.warn("Failed to verify username but will let them in anyway!");
                        NetHandlerLoginServer.this.i = NetHandlerLoginServer.this.a(var1x);
                        NetHandlerLoginServer.this.g = NetHandlerLoginServer.LoginState.d;
                     } else {
                        NetHandlerLoginServer.this.a("Failed to verify username!");
                        NetHandlerLoginServer.c.error("Username '" + NetHandlerLoginServer.this.i.getName() + "' tried to join with an invalid session");
                     }
                  } catch (AuthenticationUnavailableException var3) {
                     if (NetHandlerLoginServer.this.f.T()) {
                        NetHandlerLoginServer.c.warn("Authentication servers are down but will let them in anyway!");
                        NetHandlerLoginServer.this.i = NetHandlerLoginServer.this.a(var1x);
                        NetHandlerLoginServer.this.g = NetHandlerLoginServer.LoginState.d;
                     } else {
                        NetHandlerLoginServer.this.a("Authentication servers are down. Please try again later, sorry!");
                        NetHandlerLoginServer.c.error("Couldn't verify username because servers are unavailable");
                     }
                  }
               }
            })
            .start();
      }
   }

   protected GameProfile a(GameProfile var1) {
      UUID var2 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + var1.getName()).getBytes(Charsets.UTF_8));
      return new GameProfile(var2, var1.getName());
   }

   static enum LoginState {
      a,
      b,
      c,
      d,
      e,
      f;
   }
}
