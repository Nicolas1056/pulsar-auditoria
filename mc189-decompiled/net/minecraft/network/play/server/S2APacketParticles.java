package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumParticleTypes;

public class S2APacketParticles implements Packet<INetHandlerPlayClient> {
   private EnumParticleTypes a;
   private float b;
   private float c;
   private float d;
   private float e;
   private float f;
   private float g;
   private float h;
   private int i;
   private boolean j;
   private int[] k;

   public S2APacketParticles() {
   }

   public S2APacketParticles(
      EnumParticleTypes var1, boolean var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int... var11
   ) {
      this.a = var1;
      this.j = var2;
      this.b = var3;
      this.c = var4;
      this.d = var5;
      this.e = var6;
      this.f = var7;
      this.g = var8;
      this.h = var9;
      this.i = var10;
      this.k = var11;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = EnumParticleTypes.a(var1.readInt());
      if (this.a == null) {
         this.a = EnumParticleTypes.J;
      }

      this.j = var1.readBoolean();
      this.b = var1.readFloat();
      this.c = var1.readFloat();
      this.d = var1.readFloat();
      this.e = var1.readFloat();
      this.f = var1.readFloat();
      this.g = var1.readFloat();
      this.h = var1.readFloat();
      this.i = var1.readInt();
      int var2 = this.a.d();
      this.k = new int[var2];

      for (int var3 = 0; var3 < var2; var3++) {
         this.k[var3] = var1.e();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeInt(this.a.c());
      var1.writeBoolean(this.j);
      var1.writeFloat(this.b);
      var1.writeFloat(this.c);
      var1.writeFloat(this.d);
      var1.writeFloat(this.e);
      var1.writeFloat(this.f);
      var1.writeFloat(this.g);
      var1.writeFloat(this.h);
      var1.writeInt(this.i);
      int var2 = this.a.d();

      for (int var3 = 0; var3 < var2; var3++) {
         var1.b(this.k[var3]);
      }
   }

   public EnumParticleTypes a() {
      return this.a;
   }

   public boolean b() {
      return this.j;
   }

   public double c() {
      return this.b;
   }

   public double d() {
      return this.c;
   }

   public double e() {
      return this.d;
   }

   public float f() {
      return this.e;
   }

   public float g() {
      return this.f;
   }

   public float h() {
      return this.g;
   }

   public float i() {
      return this.h;
   }

   public int j() {
      return this.i;
   }

   public int[] k() {
      return this.k;
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }
}
