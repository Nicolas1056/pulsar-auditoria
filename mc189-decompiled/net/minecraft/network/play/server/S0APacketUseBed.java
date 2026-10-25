package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class S0APacketUseBed implements Packet<INetHandlerPlayClient> {
   private int a;
   private BlockPos b;

   public S0APacketUseBed() {
   }

   public S0APacketUseBed(EntityPlayer var1, BlockPos var2) {
      this.a = var1.F();
      this.b = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.c();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.a(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public EntityPlayer a(World var1) {
      return (EntityPlayer)var1.a(this.a);
   }

   public BlockPos a() {
      return this.b;
   }
}
