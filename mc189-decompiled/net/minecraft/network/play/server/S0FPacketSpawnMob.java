package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0FPacketSpawnMob implements Packet<INetHandlerPlayClient> {
   private int a;
   private int b;
   private int c;
   private int d;
   private int e;
   private int f;
   private int g;
   private int h;
   private byte i;
   private byte j;
   private byte k;
   private DataWatcher l;
   private List<DataWatcher.WatchableObject> m;

   public S0FPacketSpawnMob() {
   }

   public S0FPacketSpawnMob(EntityLivingBase var1) {
      this.a = var1.F();
      this.b = (byte)EntityList.a(var1);
      this.c = MathHelper.c(var1.s * 32.0);
      this.d = MathHelper.c(var1.t * 32.0);
      this.e = MathHelper.c(var1.u * 32.0);
      this.i = (byte)(var1.y * 256.0F / 360.0F);
      this.j = (byte)(var1.z * 256.0F / 360.0F);
      this.k = (byte)(var1.aK * 256.0F / 360.0F);
      double var2 = 3.9;
      double var4 = var1.v;
      double var6 = var1.w;
      double var8 = var1.x;
      if (var4 < -var2) {
         var4 = -var2;
      }

      if (var6 < -var2) {
         var6 = -var2;
      }

      if (var8 < -var2) {
         var8 = -var2;
      }

      if (var4 > var2) {
         var4 = var2;
      }

      if (var6 > var2) {
         var6 = var2;
      }

      if (var8 > var2) {
         var8 = var2;
      }

      this.f = (int)(var4 * 8000.0);
      this.g = (int)(var6 * 8000.0);
      this.h = (int)(var8 * 8000.0);
      this.l = var1.H();
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.readByte() & 255;
      this.c = var1.readInt();
      this.d = var1.readInt();
      this.e = var1.readInt();
      this.i = var1.readByte();
      this.j = var1.readByte();
      this.k = var1.readByte();
      this.f = var1.readShort();
      this.g = var1.readShort();
      this.h = var1.readShort();
      this.m = DataWatcher.b(var1);
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeByte(this.b & 0xFF);
      var1.writeInt(this.c);
      var1.writeInt(this.d);
      var1.writeInt(this.e);
      var1.writeByte(this.i);
      var1.writeByte(this.j);
      var1.writeByte(this.k);
      var1.writeShort(this.f);
      var1.writeShort(this.g);
      var1.writeShort(this.h);
      this.l.a(var1);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public List<DataWatcher.WatchableObject> a() {
      if (this.m == null) {
         this.m = this.l.c();
      }

      return this.m;
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }

   public int f() {
      return this.e;
   }

   public int g() {
      return this.f;
   }

   public int h() {
      return this.g;
   }

   public int i() {
      return this.h;
   }

   public byte j() {
      return this.i;
   }

   public byte k() {
      return this.j;
   }

   public byte l() {
      return this.k;
   }
}
