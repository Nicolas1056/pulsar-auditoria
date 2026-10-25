package net.minecraft.network.login.server;

import java.io.IOException;
import java.security.PublicKey;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.CryptManager;

public class S01PacketEncryptionRequest implements Packet<INetHandlerLoginClient> {
   private String a;
   private PublicKey b;
   private byte[] c;

   public S01PacketEncryptionRequest() {
   }

   public S01PacketEncryptionRequest(String var1, PublicKey var2, byte[] var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(20);
      this.b = CryptManager.a(var1.a());
      this.c = var1.a();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.a(this.b.getEncoded());
      var1.a(this.c);
   }

   public void a(INetHandlerLoginClient var1) {
      var1.a(this);
   }

   public String a() {
      return this.a;
   }

   public PublicKey b() {
      return this.b;
   }

   public byte[] c() {
      return this.c;
   }
}
