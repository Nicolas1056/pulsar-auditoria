package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

public class S22PacketMultiBlockChange implements Packet<INetHandlerPlayClient> {
   private ChunkCoordIntPair a;
   private S22PacketMultiBlockChange.BlockUpdateData[] b;

   public S22PacketMultiBlockChange() {
   }

   public S22PacketMultiBlockChange(int var1, short[] var2, Chunk var3) {
      this.a = new ChunkCoordIntPair(var3.a, var3.b);
      this.b = new S22PacketMultiBlockChange.BlockUpdateData[var1];

      for (int var4 = 0; var4 < this.b.length; var4++) {
         this.b[var4] = new S22PacketMultiBlockChange.BlockUpdateData(var2[var4], var3);
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = new ChunkCoordIntPair(var1.readInt(), var1.readInt());
      this.b = new S22PacketMultiBlockChange.BlockUpdateData[var1.e()];

      for (int var2 = 0; var2 < this.b.length; var2++) {
         this.b[var2] = new S22PacketMultiBlockChange.BlockUpdateData(var1.readShort(), Block.d.a(var1.e()));
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeInt(this.a.a);
      var1.writeInt(this.a.b);
      var1.b(this.b.length);

      for (S22PacketMultiBlockChange.BlockUpdateData var5 : this.b) {
         var1.writeShort(var5.b());
         var1.b(Block.d.b(var5.c()));
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public S22PacketMultiBlockChange.BlockUpdateData[] a() {
      return this.b;
   }

   public class BlockUpdateData {
      private final short b;
      private final IBlockState c;

      public BlockUpdateData(short var2, IBlockState var3) {
         this.b = var2;
         this.c = var3;
      }

      public BlockUpdateData(short var2, Chunk var3) {
         this.b = var2;
         this.c = var3.g(this.a());
      }

      public BlockPos a() {
         return new BlockPos(S22PacketMultiBlockChange.this.a.a(this.b >> 12 & 15, this.b & 255, this.b >> 8 & 15));
      }

      public short b() {
         return this.b;
      }

      public IBlockState c() {
         return this.c;
      }
   }
}
