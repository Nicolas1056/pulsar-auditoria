package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class C07PacketPlayerDigging implements Packet<INetHandlerPlayServer> {
   private BlockPos a;
   private EnumFacing b;
   private C07PacketPlayerDigging.Action c;

   public C07PacketPlayerDigging() {
   }

   public C07PacketPlayerDigging(C07PacketPlayerDigging.Action var1, BlockPos var2, EnumFacing var3) {
      this.c = var1;
      this.a = var2;
      this.b = var3;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.c = var1.a(C07PacketPlayerDigging.Action.class);
      this.a = var1.c();
      this.b = EnumFacing.a(var1.readUnsignedByte());
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.c);
      var1.a(this.a);
      var1.writeByte(this.b.a());
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public BlockPos a() {
      return this.a;
   }

   public EnumFacing b() {
      return this.b;
   }

   public C07PacketPlayerDigging.Action c() {
      return this.c;
   }

   public static enum Action {
      a,
      b,
      c,
      d,
      e,
      f;
   }
}
