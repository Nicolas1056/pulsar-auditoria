package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;

public class S3BPacketScoreboardObjective implements Packet<INetHandlerPlayClient> {
   private String a;
   private String b;
   private IScoreObjectiveCriteria.EnumRenderType c;
   private int d;

   public S3BPacketScoreboardObjective() {
   }

   public S3BPacketScoreboardObjective(ScoreObjective var1, int var2) {
      this.a = var1.b();
      this.b = var1.d();
      this.c = var1.c().c();
      this.d = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(16);
      this.d = var1.readByte();
      if (this.d == 0 || this.d == 2) {
         this.b = var1.c(32);
         this.c = IScoreObjectiveCriteria.EnumRenderType.a(var1.c(16));
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.writeByte(this.d);
      if (this.d == 0 || this.d == 2) {
         var1.a(this.b);
         var1.a(this.c.a());
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public String a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   public int c() {
      return this.d;
   }

   public IScoreObjectiveCriteria.EnumRenderType d() {
      return this.c;
   }
}
