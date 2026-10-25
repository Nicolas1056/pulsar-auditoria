package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;

public class S3DPacketDisplayScoreboard implements Packet<INetHandlerPlayClient> {
   private int a;
   private String b;

   public S3DPacketDisplayScoreboard() {
   }

   public S3DPacketDisplayScoreboard(int var1, ScoreObjective var2) {
      this.a = var1;
      if (var2 == null) {
         this.b = "";
      } else {
         this.b = var2.b();
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readByte();
      this.b = var1.c(16);
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeByte(this.a);
      var1.a(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }
}
