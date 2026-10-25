package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginClient implements INetHandlerLoginClient {
   private static final Logger a = LogManager.getLogger();
   private final Minecraft b;
   private final GuiScreen c;
   private final NetworkManager d;
   private GameProfile e;

   public NetHandlerLoginClient(NetworkManager var1, Minecraft var2, GuiScreen var3) {
      this.d = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public void a(S01PacketEncryptionRequest var1) {
      final SecretKey var2 = CryptManager.a();
      String var3 = var1.a();
      PublicKey var4 = var1.b();
      String var5 = new BigInteger(CryptManager.a(var3, var4, var2)).toString(16);
      if (this.b.D() != null && this.b.D().d()) {
         try {
            this.b().joinServer(this.b.L().e(), this.b.L().d(), var5);
         } catch (AuthenticationException var10) {
            a.warn("Couldn't connect to auth servers but will continue to join LAN");
         }
      } else {
         try {
            this.b().joinServer(this.b.L().e(), this.b.L().d(), var5);
         } catch (AuthenticationUnavailableException var7) {
            this.d.a(new ChatComponentTranslation("disconnect.loginFailedInfo", new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable")));
            return;
         } catch (InvalidCredentialsException var8) {
            this.d.a(new ChatComponentTranslation("disconnect.loginFailedInfo", new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession")));
            return;
         } catch (AuthenticationException var9) {
            this.d.a(new ChatComponentTranslation("disconnect.loginFailedInfo", var9.getMessage()));
            return;
         }
      }

      this.d.a(new C01PacketEncryptionResponse(var2, var4, var1.c()), new GenericFutureListener<Future<? super Void>>() {
         public void operationComplete(Future<? super Void> var1) throws Exception {
            NetHandlerLoginClient.this.d.a(var2);
         }
      });
   }

   private MinecraftSessionService b() {
      return this.b.aa();
   }

   @Override
   public void a(S02PacketLoginSuccess var1) {
      this.e = var1.a();
      this.d.a(EnumConnectionState.b);
      this.d.a(new NetHandlerPlayClient(this.b, this.c, this.d, this.e));
   }

   @Override
   public void a(IChatComponent var1) {
      this.b.a(new GuiDisconnected(this.c, "connect.failed", var1));
   }

   @Override
   public void a(S00PacketDisconnect var1) {
      this.d.a(var1.a());
   }

   @Override
   public void a(S03PacketEnableCompression var1) {
      if (!this.d.c()) {
         this.d.a(var1.a());
      }
   }
}
