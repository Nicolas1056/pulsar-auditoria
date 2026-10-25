package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C03PacketPlayer implements Packet<INetHandlerPlayServer> {
   protected double a;
   protected double b;
   protected double c;
   protected float d;
   protected float e;
   protected boolean f;
   protected boolean g;
   protected boolean h;

   public C03PacketPlayer() {
   }

   public C03PacketPlayer(boolean var1) {
      this.f = var1;
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.f = var1.readUnsignedByte() != 0;
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeByte(this.f ? 1 : 0);
   }

   public double a() {
      return this.a;
   }

   public double b() {
      return this.b;
   }

   public double c() {
      return this.c;
   }

   public float d() {
      return this.d;
   }

   public float e() {
      return this.e;
   }

   public boolean f() {
      return this.f;
   }

   public boolean g() {
      return this.g;
   }

   public boolean h() {
      return this.h;
   }

   public void a(boolean var1) {
      this.g = var1;
   }

   public static class C04PacketPlayerPosition extends C03PacketPlayer {
      public C04PacketPlayerPosition() {
         this.g = true;
      }

      public C04PacketPlayerPosition(double var1, double var3, double var5, boolean var7) {
         this.a = var1;
         this.b = var3;
         this.c = var5;
         this.f = var7;
         this.g = true;
      }

      @Override
      public void a(PacketBuffer var1) throws IOException {
         this.a = var1.readDouble();
         this.b = var1.readDouble();
         this.c = var1.readDouble();
         super.a(var1);
      }

      @Override
      public void b(PacketBuffer var1) throws IOException {
         var1.writeDouble(this.a);
         var1.writeDouble(this.b);
         var1.writeDouble(this.c);
         super.b(var1);
      }
   }

   public static class C05PacketPlayerLook extends C03PacketPlayer {
      public C05PacketPlayerLook() {
         this.h = true;
      }

      public C05PacketPlayerLook(float var1, float var2, boolean var3) {
         this.d = var1;
         this.e = var2;
         this.f = var3;
         this.h = true;
      }

      @Override
      public void a(PacketBuffer var1) throws IOException {
         this.d = var1.readFloat();
         this.e = var1.readFloat();
         super.a(var1);
      }

      @Override
      public void b(PacketBuffer var1) throws IOException {
         var1.writeFloat(this.d);
         var1.writeFloat(this.e);
         super.b(var1);
      }
   }

   public static class C06PacketPlayerPosLook extends C03PacketPlayer {
      public C06PacketPlayerPosLook() {
         this.g = true;
         this.h = true;
      }

      public C06PacketPlayerPosLook(double var1, double var3, double var5, float var7, float var8, boolean var9) {
         this.a = var1;
         this.b = var3;
         this.c = var5;
         this.d = var7;
         this.e = var8;
         this.f = var9;
         this.h = true;
         this.g = true;
      }

      @Override
      public void a(PacketBuffer var1) throws IOException {
         this.a = var1.readDouble();
         this.b = var1.readDouble();
         this.c = var1.readDouble();
         this.d = var1.readFloat();
         this.e = var1.readFloat();
         super.a(var1);
      }

      @Override
      public void b(PacketBuffer var1) throws IOException {
         var1.writeDouble(this.a);
         var1.writeDouble(this.b);
         var1.writeDouble(this.c);
         var1.writeFloat(this.d);
         var1.writeFloat(this.e);
         super.b(var1);
      }
   }
}
