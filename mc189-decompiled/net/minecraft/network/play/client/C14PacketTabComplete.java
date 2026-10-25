package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import org.apache.commons.lang3.StringUtils;

public class C14PacketTabComplete implements Packet<INetHandlerPlayServer> {
   private String a;
   private BlockPos b;

   public C14PacketTabComplete() {
   }

   public C14PacketTabComplete(String var1) {
      this(var1, null);
   }

   public C14PacketTabComplete(String var1, BlockPos var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(32767);
      boolean var2 = var1.readBoolean();
      if (var2) {
         this.b = var1.c();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(StringUtils.substring(this.a, 0, 32767));
      boolean var2 = this.b != null;
      var1.writeBoolean(var2);
      if (var2) {
         var1.a(this.b);
      }
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public String a() {
      return this.a;
   }

   public BlockPos b() {
      return this.b;
   }
}
