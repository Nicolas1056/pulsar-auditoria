package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;

public class S3EPacketTeams implements Packet<INetHandlerPlayClient> {
   private String a = "";
   private String b = "";
   private String c = "";
   private String d = "";
   private String e;
   private int f;
   private Collection<String> g;
   private int h;
   private int i;

   public S3EPacketTeams() {
      this.e = Team.EnumVisible.a.e;
      this.f = -1;
      this.g = Lists.newArrayList();
   }

   public S3EPacketTeams(ScorePlayerTeam var1, int var2) {
      this.e = Team.EnumVisible.a.e;
      this.f = -1;
      this.g = Lists.newArrayList();
      this.a = var1.b();
      this.h = var2;
      if (var2 == 0 || var2 == 2) {
         this.b = var1.c();
         this.c = var1.e();
         this.d = var1.f();
         this.i = var1.k();
         this.e = var1.i().e;
         this.f = var1.l().b();
      }

      if (var2 == 0) {
         this.g.addAll(var1.d());
      }
   }

   public S3EPacketTeams(ScorePlayerTeam var1, Collection<String> var2, int var3) {
      this.e = Team.EnumVisible.a.e;
      this.f = -1;
      this.g = Lists.newArrayList();
      if (var3 != 3 && var3 != 4) {
         throw new IllegalArgumentException("Method must be join or leave for player constructor");
      } else if (var2 != null && !var2.isEmpty()) {
         this.h = var3;
         this.a = var1.b();
         this.g.addAll(var2);
      } else {
         throw new IllegalArgumentException("Players cannot be null/empty");
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(16);
      this.h = var1.readByte();
      if (this.h == 0 || this.h == 2) {
         this.b = var1.c(32);
         this.c = var1.c(16);
         this.d = var1.c(16);
         this.i = var1.readByte();
         this.e = var1.c(32);
         this.f = var1.readByte();
      }

      if (this.h == 0 || this.h == 3 || this.h == 4) {
         int var2 = var1.e();

         for (int var3 = 0; var3 < var2; var3++) {
            this.g.add(var1.c(40));
         }
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.writeByte(this.h);
      if (this.h == 0 || this.h == 2) {
         var1.a(this.b);
         var1.a(this.c);
         var1.a(this.d);
         var1.writeByte(this.i);
         var1.a(this.e);
         var1.writeByte(this.f);
      }

      if (this.h == 0 || this.h == 3 || this.h == 4) {
         var1.b(this.g.size());

         for (String var3 : this.g) {
            var1.a(var3);
         }
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

   public String c() {
      return this.c;
   }

   public String d() {
      return this.d;
   }

   public Collection<String> e() {
      return this.g;
   }

   public int f() {
      return this.h;
   }

   public int g() {
      return this.i;
   }

   public int h() {
      return this.f;
   }

   public String i() {
      return this.e;
   }
}
