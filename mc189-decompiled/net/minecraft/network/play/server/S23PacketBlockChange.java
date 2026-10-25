package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class S23PacketBlockChange implements Packet<INetHandlerPlayClient> {
   private BlockPos a;
   private IBlockState b;

   public S23PacketBlockChange() {
   }

   public S23PacketBlockChange(World var1, BlockPos var2) {
      this.a = var2;
      this.b = var1.p(var2);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c();
      this.b = Block.d.a(var1.e());
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.b(Block.d.b(this.b));
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public IBlockState a() {
      return this.b;
   }

   public BlockPos b() {
      return this.a;
   }
}
