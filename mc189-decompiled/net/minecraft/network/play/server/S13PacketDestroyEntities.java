package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S13PacketDestroyEntities implements Packet<INetHandlerPlayClient> {
   private int[] a;

   public S13PacketDestroyEntities() {
   }

   public S13PacketDestroyEntities(int... var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = new int[var1.e()];

      for (int var2 = 0; var2 < this.a.length; var2++) {
         this.a[var2] = var1.e();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a.length);

      for (int var2 = 0; var2 < this.a.length; var2++) {
         var1.b(this.a[var2]);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int[] a() {
      return this.a;
   }
}
