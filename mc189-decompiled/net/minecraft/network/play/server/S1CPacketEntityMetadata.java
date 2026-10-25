package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.DataWatcher;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1CPacketEntityMetadata implements Packet<INetHandlerPlayClient> {
   private int a;
   private List<DataWatcher.WatchableObject> b;

   public S1CPacketEntityMetadata() {
   }

   public S1CPacketEntityMetadata(int var1, DataWatcher var2, boolean var3) {
      this.a = var1;
      if (var3) {
         this.b = var2.c();
      } else {
         this.b = var2.b();
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = DataWatcher.b(var1);
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      DataWatcher.a(this.b, var1);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public List<DataWatcher.WatchableObject> a() {
      return this.b;
   }

   public int b() {
      return this.a;
   }
}
