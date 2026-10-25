package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class S3CPacketUpdateScore implements Packet<INetHandlerPlayClient> {
   private String a = "";
   private String b = "";
   private int c;
   private S3CPacketUpdateScore.Action d;

   public S3CPacketUpdateScore() {
   }

   public S3CPacketUpdateScore(Score var1) {
      this.a = var1.e();
      this.b = var1.d().b();
      this.c = var1.c();
      this.d = S3CPacketUpdateScore.Action.a;
   }

   public S3CPacketUpdateScore(String var1) {
      this.a = var1;
      this.b = "";
      this.c = 0;
      this.d = S3CPacketUpdateScore.Action.b;
   }

   public S3CPacketUpdateScore(String var1, ScoreObjective var2) {
      this.a = var1;
      this.b = var2.b();
      this.c = 0;
      this.d = S3CPacketUpdateScore.Action.b;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(40);
      this.d = var1.a(S3CPacketUpdateScore.Action.class);
      this.b = var1.c(16);
      if (this.d != S3CPacketUpdateScore.Action.b) {
         this.c = var1.e();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.a(this.d);
      var1.a(this.b);
      if (this.d != S3CPacketUpdateScore.Action.b) {
         var1.b(this.c);
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
      return this.c;
   }

   public S3CPacketUpdateScore.Action d() {
      return this.d;
   }

   public static enum Action {
      a,
      b;
   }
}
