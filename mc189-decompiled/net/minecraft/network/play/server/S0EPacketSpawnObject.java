package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0EPacketSpawnObject implements Packet<INetHandlerPlayClient> {
   private int a;
   private int b;
   private int c;
   private int d;
   private int e;
   private int f;
   private int g;
   private int h;
   private int i;
   private int j;
   private int k;

   public S0EPacketSpawnObject() {
   }

   public S0EPacketSpawnObject(Entity var1, int var2) {
      this(var1, var2, 0);
   }

   public S0EPacketSpawnObject(Entity var1, int var2, int var3) {
      this.a = var1.F();
      this.b = MathHelper.c(var1.s * 32.0);
      this.c = MathHelper.c(var1.t * 32.0);
      this.d = MathHelper.c(var1.u * 32.0);
      this.h = MathHelper.d(var1.z * 256.0F / 360.0F);
      this.i = MathHelper.d(var1.y * 256.0F / 360.0F);
      this.j = var2;
      this.k = var3;
      if (var3 > 0) {
         double var4 = var1.v;
         double var6 = var1.w;
         double var8 = var1.x;
         double var10 = 3.9;
         if (var4 < -var10) {
            var4 = -var10;
         }

         if (var6 < -var10) {
            var6 = -var10;
         }

         if (var8 < -var10) {
            var8 = -var10;
         }

         if (var4 > var10) {
            var4 = var10;
         }

         if (var6 > var10) {
            var6 = var10;
         }

         if (var8 > var10) {
            var8 = var10;
         }

         this.e = (int)(var4 * 8000.0);
         this.f = (int)(var6 * 8000.0);
         this.g = (int)(var8 * 8000.0);
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.j = var1.readByte();
      this.b = var1.readInt();
      this.c = var1.readInt();
      this.d = var1.readInt();
      this.h = var1.readByte();
      this.i = var1.readByte();
      this.k = var1.readInt();
      if (this.k > 0) {
         this.e = var1.readShort();
         this.f = var1.readShort();
         this.g = var1.readShort();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeByte(this.j);
      var1.writeInt(this.b);
      var1.writeInt(this.c);
      var1.writeInt(this.d);
      var1.writeByte(this.h);
      var1.writeByte(this.i);
      var1.writeInt(this.k);
      if (this.k > 0) {
         var1.writeShort(this.e);
         var1.writeShort(this.f);
         var1.writeShort(this.g);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public int e() {
      return this.e;
   }

   public int f() {
      return this.f;
   }

   public int g() {
      return this.g;
   }

   public int h() {
      return this.h;
   }

   public int i() {
      return this.i;
   }

   public int j() {
      return this.j;
   }

   public int k() {
      return this.k;
   }

   public void a(int var1) {
      this.b = var1;
   }

   public void b(int var1) {
      this.c = var1;
   }

   public void c(int var1) {
      this.d = var1;
   }

   public void d(int var1) {
      this.e = var1;
   }

   public void e(int var1) {
      this.f = var1;
   }

   public void f(int var1) {
      this.g = var1;
   }

   public void g(int var1) {
      this.k = var1;
   }
}
