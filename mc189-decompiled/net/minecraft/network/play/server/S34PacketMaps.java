package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

public class S34PacketMaps implements Packet<INetHandlerPlayClient> {
   private int a;
   private byte b;
   private Vec4b[] c;
   private int d;
   private int e;
   private int f;
   private int g;
   private byte[] h;

   public S34PacketMaps() {
   }

   public S34PacketMaps(int var1, byte var2, Collection<Vec4b> var3, byte[] var4, int var5, int var6, int var7, int var8) {
      this.a = var1;
      this.b = var2;
      this.c = var3.toArray(new Vec4b[var3.size()]);
      this.d = var5;
      this.e = var6;
      this.f = var7;
      this.g = var8;
      this.h = new byte[var7 * var8];

      for (int var9 = 0; var9 < var7; var9++) {
         for (int var10 = 0; var10 < var8; var10++) {
            this.h[var9 + var10 * var7] = var4[var5 + var9 + (var6 + var10) * 128];
         }
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.readByte();
      this.c = new Vec4b[var1.e()];

      for (int var2 = 0; var2 < this.c.length; var2++) {
         short var3 = var1.readByte();
         this.c[var2] = new Vec4b((byte)(var3 >> 4 & 15), var1.readByte(), var1.readByte(), (byte)(var3 & 15));
      }

      this.f = var1.readUnsignedByte();
      if (this.f > 0) {
         this.g = var1.readUnsignedByte();
         this.d = var1.readUnsignedByte();
         this.e = var1.readUnsignedByte();
         this.h = var1.a();
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeByte(this.b);
      var1.b(this.c.length);

      for (Vec4b var5 : this.c) {
         var1.writeByte((var5.a() & 15) << 4 | var5.d() & 15);
         var1.writeByte(var5.b());
         var1.writeByte(var5.c());
      }

      var1.writeByte(this.f);
      if (this.f > 0) {
         var1.writeByte(this.g);
         var1.writeByte(this.d);
         var1.writeByte(this.e);
         var1.a(this.h);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }

   public void a(MapData var1) {
      var1.e = this.b;
      var1.h.clear();

      for (int var2 = 0; var2 < this.c.length; var2++) {
         Vec4b var3 = this.c[var2];
         var1.h.put("icon-" + var2, var3);
      }

      for (int var4 = 0; var4 < this.f; var4++) {
         for (int var5 = 0; var5 < this.g; var5++) {
            var1.f[this.d + var4 + (this.e + var5) * 128] = this.h[var4 + var5 * this.f];
         }
      }
   }
}
