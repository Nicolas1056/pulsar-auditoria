package net.minecraft.network.login.server;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class S02PacketLoginSuccess implements Packet<INetHandlerLoginClient> {
   private GameProfile a;

   public S02PacketLoginSuccess() {
   }

   public S02PacketLoginSuccess(GameProfile var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      String var2 = var1.c(36);
      String var3 = var1.c(16);
      UUID var4 = UUID.fromString(var2);
      this.a = new GameProfile(var4, var3);
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      UUID var2 = this.a.getId();
      var1.a(var2 == null ? "" : var2.toString());
      var1.a(this.a.getName());
   }

   public void a(INetHandlerLoginClient var1) {
      var1.a(this);
   }

   public GameProfile a() {
      return this.a;
   }
}
