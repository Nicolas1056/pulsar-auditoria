package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class S01PacketJoinGame implements Packet<INetHandlerPlayClient> {
   private int a;
   private boolean b;
   private WorldSettings.GameType c;
   private int d;
   private EnumDifficulty e;
   private int f;
   private WorldType g;
   private boolean h;

   public S01PacketJoinGame() {
   }

   public S01PacketJoinGame(int var1, WorldSettings.GameType var2, boolean var3, int var4, EnumDifficulty var5, int var6, WorldType var7, boolean var8) {
      this.a = var1;
      this.d = var4;
      this.e = var5;
      this.c = var2;
      this.f = var6;
      this.b = var3;
      this.g = var7;
      this.h = var8;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readInt();
      int var2 = var1.readUnsignedByte();
      this.b = (var2 & 8) == 8;
      var2 &= -9;
      this.c = WorldSettings.GameType.a(var2);
      this.d = var1.readByte();
      this.e = EnumDifficulty.a(var1.readUnsignedByte());
      this.f = var1.readUnsignedByte();
      this.g = WorldType.a(var1.c(16));
      if (this.g == null) {
         this.g = WorldType.b;
      }

      this.h = var1.readBoolean();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeInt(this.a);
      int var2 = this.c.a();
      if (this.b) {
         var2 |= 8;
      }

      var1.writeByte(var2);
      var1.writeByte(this.d);
      var1.writeByte(this.e.a());
      var1.writeByte(this.f);
      var1.a(this.g.a());
      var1.writeBoolean(this.h);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }

   public boolean b() {
      return this.b;
   }

   public WorldSettings.GameType c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public EnumDifficulty e() {
      return this.e;
   }

   public int f() {
      return this.f;
   }

   public WorldType g() {
      return this.g;
   }

   public boolean h() {
      return this.h;
   }
}
