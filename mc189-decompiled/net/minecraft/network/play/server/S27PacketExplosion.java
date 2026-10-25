package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class S27PacketExplosion implements Packet<INetHandlerPlayClient> {
   private double a;
   private double b;
   private double c;
   private float d;
   private List<BlockPos> e;
   private float f;
   private float g;
   private float h;

   public S27PacketExplosion() {
   }

   public S27PacketExplosion(double var1, double var3, double var5, float var7, List<BlockPos> var8, Vec3 var9) {
      this.a = var1;
      this.b = var3;
      this.c = var5;
      this.d = var7;
      this.e = Lists.newArrayList(var8);
      if (var9 != null) {
         this.f = (float)var9.a;
         this.g = (float)var9.b;
         this.h = (float)var9.c;
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readFloat();
      this.b = var1.readFloat();
      this.c = var1.readFloat();
      this.d = var1.readFloat();
      int var2 = var1.readInt();
      this.e = Lists.newArrayListWithCapacity(var2);
      int var3 = (int)this.a;
      int var4 = (int)this.b;
      int var5 = (int)this.c;

      for (int var6 = 0; var6 < var2; var6++) {
         int var7 = var1.readByte() + var3;
         int var8 = var1.readByte() + var4;
         int var9 = var1.readByte() + var5;
         this.e.add(new BlockPos(var7, var8, var9));
      }

      this.f = var1.readFloat();
      this.g = var1.readFloat();
      this.h = var1.readFloat();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeFloat((float)this.a);
      var1.writeFloat((float)this.b);
      var1.writeFloat((float)this.c);
      var1.writeFloat(this.d);
      var1.writeInt(this.e.size());
      int var2 = (int)this.a;
      int var3 = (int)this.b;
      int var4 = (int)this.c;

      for (BlockPos var6 : this.e) {
         int var7 = var6.n() - var2;
         int var8 = var6.o() - var3;
         int var9 = var6.p() - var4;
         var1.writeByte(var7);
         var1.writeByte(var8);
         var1.writeByte(var9);
      }

      var1.writeFloat(this.f);
      var1.writeFloat(this.g);
      var1.writeFloat(this.h);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public float a() {
      return this.f;
   }

   public float b() {
      return this.g;
   }

   public float c() {
      return this.h;
   }

   public double d() {
      return this.a;
   }

   public double e() {
      return this.b;
   }

   public double f() {
      return this.c;
   }

   public float g() {
      return this.d;
   }

   public List<BlockPos> h() {
      return this.e;
   }
}
