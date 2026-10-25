package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class S21PacketChunkData implements Packet<INetHandlerPlayClient> {
   private int a;
   private int b;
   private S21PacketChunkData.Extracted c;
   private boolean d;

   public S21PacketChunkData() {
   }

   public S21PacketChunkData(Chunk var1, boolean var2, int var3) {
      this.a = var1.a;
      this.b = var1.b;
      this.d = var2;
      this.c = a(var1, var2, !var1.p().t.o(), var3);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readInt();
      this.b = var1.readInt();
      this.d = var1.readBoolean();
      this.c = new S21PacketChunkData.Extracted();
      this.c.b = var1.readShort();
      this.c.a = var1.a();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeInt(this.a);
      var1.writeInt(this.b);
      var1.writeBoolean(this.d);
      var1.writeShort((short)(this.c.b & 65535));
      var1.a(this.c.a);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public byte[] a() {
      return this.c.a;
   }

   protected static int a(int var0, boolean var1, boolean var2) {
      int var3 = var0 * 2 * 16 * 16 * 16;
      int var4 = var0 * 16 * 16 * 16 / 2;
      int var5 = var1 ? var0 * 16 * 16 * 16 / 2 : 0;
      int var6 = var2 ? 256 : 0;
      return var3 + var4 + var5 + var6;
   }

   public static S21PacketChunkData.Extracted a(Chunk var0, boolean var1, boolean var2, int var3) {
      ExtendedBlockStorage[] var4 = var0.h();
      S21PacketChunkData.Extracted var5 = new S21PacketChunkData.Extracted();
      ArrayList var6 = Lists.newArrayList();

      for (int var7 = 0; var7 < var4.length; var7++) {
         ExtendedBlockStorage var8 = var4[var7];
         if (var8 != null && (!var1 || !var8.a()) && (var3 & 1 << var7) != 0) {
            var5.b |= 1 << var7;
            var6.add(var8);
         }
      }

      var5.a = new byte[a(Integer.bitCount(var5.b), var2, var1)];
      int var15 = 0;

      for (ExtendedBlockStorage var9 : var6) {
         char[] var10 = var9.g();

         for (char var14 : var10) {
            var5.a[var15++] = (byte)(var14 & 255);
            var5.a[var15++] = (byte)(var14 >> '\b' & 0xFF);
         }
      }

      for (ExtendedBlockStorage var20 : var6) {
         var15 = a(var20.h().a(), var5.a, var15);
      }

      if (var2) {
         for (ExtendedBlockStorage var21 : var6) {
            var15 = a(var21.i().a(), var5.a, var15);
         }
      }

      if (var1) {
         a(var0.k(), var5.a, var15);
      }

      return var5;
   }

   private static int a(byte[] var0, byte[] var1, int var2) {
      System.arraycopy(var0, 0, var1, var2, var0.length);
      return var2 + var0.length;
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c.b;
   }

   public boolean e() {
      return this.d;
   }

   public static class Extracted {
      public byte[] a;
      public int b;
   }
}
