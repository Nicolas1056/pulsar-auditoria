package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class C12PacketUpdateSign implements Packet<INetHandlerPlayServer> {
   private BlockPos a;
   private IChatComponent[] b;

   public C12PacketUpdateSign() {
   }

   public C12PacketUpdateSign(BlockPos var1, IChatComponent[] var2) {
      this.a = var1;
      this.b = new IChatComponent[]{var2[0], var2[1], var2[2], var2[3]};
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c();
      this.b = new IChatComponent[4];

      for (int var2 = 0; var2 < 4; var2++) {
         String var3 = var1.c(384);
         IChatComponent var4 = IChatComponent.Serializer.a(var3);
         this.b[var2] = var4;
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);

      for (int var2 = 0; var2 < 4; var2++) {
         IChatComponent var3 = this.b[var2];
         String var4 = IChatComponent.Serializer.a(var3);
         var1.a(var4);
      }
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public BlockPos a() {
      return this.a;
   }

   public IChatComponent[] b() {
      return this.b;
   }
}
