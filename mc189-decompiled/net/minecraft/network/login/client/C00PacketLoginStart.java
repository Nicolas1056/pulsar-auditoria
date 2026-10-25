package net.minecraft.network.login.client;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

public class C00PacketLoginStart implements Packet<INetHandlerLoginServer> {
   private GameProfile a;

   public C00PacketLoginStart() {
   }

   public C00PacketLoginStart(GameProfile var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = new GameProfile(null, var1.c(16));
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a.getName());
   }

   public void a(INetHandlerLoginServer var1) {
      var1.a(this);
   }

   public GameProfile a() {
      return this.a;
   }
}
