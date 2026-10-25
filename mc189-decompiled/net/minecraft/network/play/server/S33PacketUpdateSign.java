package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class S33PacketUpdateSign implements Packet<INetHandlerPlayClient> {
   private World a;
   private BlockPos b;
   private IChatComponent[] c;

   public S33PacketUpdateSign() {
   }

   public S33PacketUpdateSign(World var1, BlockPos var2, IChatComponent[] var3) {
      this.a = var1;
      this.b = var2;
      this.c = new IChatComponent[]{var3[0], var3[1], var3[2], var3[3]};
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.b = var1.c();
      this.c = new IChatComponent[4];

      for (int var2 = 0; var2 < 4; var2++) {
         this.c[var2] = var1.d();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.b);

      for (int var2 = 0; var2 < 4; var2++) {
         var1.a(this.c[var2]);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public BlockPos a() {
      return this.b;
   }

   public IChatComponent[] b() {
      return this.c;
   }
}
