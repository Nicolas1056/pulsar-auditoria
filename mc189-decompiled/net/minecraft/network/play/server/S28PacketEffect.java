package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S28PacketEffect implements Packet<INetHandlerPlayClient> {
   private int a;
   private BlockPos b;
   private int c;
   private boolean d;

   public S28PacketEffect() {
   }

   public S28PacketEffect(int var1, BlockPos var2, int var3, boolean var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readInt();
      this.b = var1.c();
      this.c = var1.readInt();
      this.d = var1.readBoolean();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeInt(this.a);
      var1.a(this.b);
      var1.writeInt(this.c);
      var1.writeBoolean(this.d);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public boolean a() {
      return this.d;
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.c;
   }

   public BlockPos d() {
      return this.b;
   }
}
