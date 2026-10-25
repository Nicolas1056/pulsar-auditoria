package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S36PacketSignEditorOpen implements Packet<INetHandlerPlayClient> {
   private BlockPos a;

   public S36PacketSignEditorOpen() {
   }

   public S36PacketSignEditorOpen(BlockPos var1) {
      this.a = var1;
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
   }

   public BlockPos a() {
      return this.a;
   }
}
