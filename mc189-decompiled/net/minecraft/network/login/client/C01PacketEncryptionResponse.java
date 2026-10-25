package net.minecraft.network.login.client;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.util.CryptManager;

public class C01PacketEncryptionResponse implements Packet<INetHandlerLoginServer> {
   private byte[] a = new byte[0];
   private byte[] b = new byte[0];

   public C01PacketEncryptionResponse() {
   }

   public C01PacketEncryptionResponse(SecretKey var1, PublicKey var2, byte[] var3) {
      this.a = CryptManager.a(var2, var1.getEncoded());
      this.b = CryptManager.a(var2, var3);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.a();
      this.b = var1.a();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.a(this.b);
   }

   public void a(INetHandlerLoginServer var1) {
      var1.a(this);
   }

   public SecretKey a(PrivateKey var1) {
      return CryptManager.a(var1, this.a);
   }

   public byte[] b(PrivateKey var1) {
      return var1 == null ? this.b : CryptManager.b(var1, this.b);
   }
}
