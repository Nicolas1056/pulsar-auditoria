package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S08PacketPlayerPosLook implements Packet<INetHandlerPlayClient> {
   private double a;
   private double b;
   private double c;
   private float d;
   private float e;
   private Set<S08PacketPlayerPosLook.EnumFlags> f;

   public S08PacketPlayerPosLook() {
   }

   public S08PacketPlayerPosLook(double var1, double var3, double var5, float var7, float var8, Set<S08PacketPlayerPosLook.EnumFlags> var9) {
      this.a = var1;
      this.b = var3;
      this.c = var5;
      this.d = var7;
      this.e = var8;
      this.f = var9;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readDouble();
      this.b = var1.readDouble();
      this.c = var1.readDouble();
      this.d = var1.readFloat();
      this.e = var1.readFloat();
      this.f = S08PacketPlayerPosLook.EnumFlags.a(var1.readUnsignedByte());
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeDouble(this.a);
      var1.writeDouble(this.b);
      var1.writeDouble(this.c);
      var1.writeFloat(this.d);
      var1.writeFloat(this.e);
      var1.writeByte(S08PacketPlayerPosLook.EnumFlags.a(this.f));
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
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

   public Set<S08PacketPlayerPosLook.EnumFlags> f() {
      return this.f;
   }

   public static enum EnumFlags {
      a(0),
      b(1),
      c(2),
      d(3),
      e(4);

      private int f;

      private EnumFlags(int var3) {
         this.f = var3;
      }

      private int a() {
         return 1 << this.f;
      }

      private boolean b(int var1) {
         return (var1 & this.a()) == this.a();
      }

      public static Set<S08PacketPlayerPosLook.EnumFlags> a(int var0) {
         EnumSet var1 = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);

         for (S08PacketPlayerPosLook.EnumFlags var5 : values()) {
            if (var5.b(var0)) {
               var1.add(var5);
            }
         }

         return var1;
      }

      public static int a(Set<S08PacketPlayerPosLook.EnumFlags> var0) {
         int var1 = 0;

         for (S08PacketPlayerPosLook.EnumFlags var3 : var0) {
            var1 |= var3.a();
         }

         return var1;
      }
   }
}
