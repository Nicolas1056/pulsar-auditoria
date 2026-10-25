package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class S10PacketSpawnPainting implements Packet<INetHandlerPlayClient> {
   private int a;
   private BlockPos b;
   private EnumFacing c;
   private String d;

   public S10PacketSpawnPainting() {
   }

   public S10PacketSpawnPainting(EntityPainting var1) {
      this.a = var1.F();
      this.b = var1.n();
      this.c = var1.b;
      this.d = var1.c.B;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.d = var1.c(EntityPainting.EnumArt.A);
      this.b = var1.c();
      this.c = EnumFacing.b(var1.readUnsignedByte());
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.a(this.d);
      var1.a(this.b);
      var1.writeByte(this.c.b());
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }

   public BlockPos b() {
      return this.b;
   }

   public EnumFacing c() {
      return this.c;
   }

   public String d() {
      return this.d;
   }
}
