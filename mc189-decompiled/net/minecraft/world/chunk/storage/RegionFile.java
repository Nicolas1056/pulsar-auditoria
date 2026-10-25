package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.server.MinecraftServer;

public class RegionFile {
   private static final byte[] a = new byte[4096];
   private final File b;
   private RandomAccessFile c;
   private final int[] d = new int[1024];
   private final int[] e = new int[1024];
   private List<Boolean> f;
   private int g;
   private long h;

   public RegionFile(File var1) {
      this.b = var1;
      this.g = 0;

      try {
         if (var1.exists()) {
            this.h = var1.lastModified();
         }

         this.c = new RandomAccessFile(var1, "rw");
         if (this.c.length() < 4096L) {
            for (int var2 = 0; var2 < 1024; var2++) {
               this.c.writeInt(0);
            }

            for (int var7 = 0; var7 < 1024; var7++) {
               this.c.writeInt(0);
            }

            this.g += 8192;
         }

         if ((this.c.length() & 4095L) != 0L) {
            for (int var8 = 0; var8 < (this.c.length() & 4095L); var8++) {
               this.c.write(0);
            }
         }

         int var9 = (int)this.c.length() / 4096;
         this.f = Lists.newArrayListWithCapacity(var9);

         for (int var3 = 0; var3 < var9; var3++) {
            this.f.add(true);
         }

         this.f.set(0, false);
         this.f.set(1, false);
         this.c.seek(0L);

         for (int var10 = 0; var10 < 1024; var10++) {
            int var4 = this.c.readInt();
            this.d[var10] = var4;
            if (var4 != 0 && (var4 >> 8) + (var4 & 0xFF) <= this.f.size()) {
               for (int var5 = 0; var5 < (var4 & 0xFF); var5++) {
                  this.f.set((var4 >> 8) + var5, false);
               }
            }
         }

         for (int var11 = 0; var11 < 1024; var11++) {
            int var12 = this.c.readInt();
            this.e[var11] = var12;
         }
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }

   public synchronized DataInputStream a(int var1, int var2) {
      if (this.d(var1, var2)) {
         return null;
      } else {
         try {
            int var3 = this.e(var1, var2);
            if (var3 == 0) {
               return null;
            } else {
               int var4 = var3 >> 8;
               int var5 = var3 & 0xFF;
               if (var4 + var5 > this.f.size()) {
                  return null;
               } else {
                  this.c.seek(var4 * 4096);
                  int var6 = this.c.readInt();
                  if (var6 > 4096 * var5) {
                     return null;
                  } else if (var6 <= 0) {
                     return null;
                  } else {
                     byte var7 = this.c.readByte();
                     if (var7 == 1) {
                        byte[] var10 = new byte[var6 - 1];
                        this.c.read(var10);
                        return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(var10))));
                     } else if (var7 == 2) {
                        byte[] var8 = new byte[var6 - 1];
                        this.c.read(var8);
                        return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(var8))));
                     } else {
                        return null;
                     }
                  }
               }
            }
         } catch (IOException var9) {
            return null;
         }
      }
   }

   public DataOutputStream b(int var1, int var2) {
      return this.d(var1, var2) ? null : new DataOutputStream(new DeflaterOutputStream(new RegionFile.ChunkBuffer(var1, var2)));
   }

   protected synchronized void a(int var1, int var2, byte[] var3, int var4) {
      try {
         int var5 = this.e(var1, var2);
         int var6 = var5 >> 8;
         int var7 = var5 & 0xFF;
         int var8 = (var4 + 5) / 4096 + 1;
         if (var8 >= 256) {
            return;
         }

         if (var6 != 0 && var7 == var8) {
            this.a(var6, var3, var4);
         } else {
            for (int var9 = 0; var9 < var7; var9++) {
               this.f.set(var6 + var9, true);
            }

            int var15 = this.f.indexOf(true);
            int var10 = 0;
            if (var15 != -1) {
               for (int var11 = var15; var11 < this.f.size(); var11++) {
                  if (var10 != 0) {
                     if (this.f.get(var11)) {
                        var10++;
                     } else {
                        var10 = 0;
                     }
                  } else if (this.f.get(var11)) {
                     var15 = var11;
                     var10 = 1;
                  }

                  if (var10 >= var8) {
                     break;
                  }
               }
            }

            if (var10 >= var8) {
               var6 = var15;
               this.a(var1, var2, var15 << 8 | var8);

               for (int var17 = 0; var17 < var8; var17++) {
                  this.f.set(var6 + var17, false);
               }

               this.a(var6, var3, var4);
            } else {
               this.c.seek(this.c.length());
               var6 = this.f.size();

               for (int var16 = 0; var16 < var8; var16++) {
                  this.c.write(a);
                  this.f.add(false);
               }

               this.g += 4096 * var8;
               this.a(var6, var3, var4);
               this.a(var1, var2, var6 << 8 | var8);
            }
         }

         this.b(var1, var2, (int)(MinecraftServer.az() / 1000L));
      } catch (IOException var12) {
         var12.printStackTrace();
      }
   }

   private void a(int var1, byte[] var2, int var3) throws IOException {
      this.c.seek(var1 * 4096);
      this.c.writeInt(var3 + 1);
      this.c.writeByte(2);
      this.c.write(var2, 0, var3);
   }

   private boolean d(int var1, int var2) {
      return var1 < 0 || var1 >= 32 || var2 < 0 || var2 >= 32;
   }

   private int e(int var1, int var2) {
      return this.d[var1 + var2 * 32];
   }

   public boolean c(int var1, int var2) {
      return this.e(var1, var2) != 0;
   }

   private void a(int var1, int var2, int var3) throws IOException {
      this.d[var1 + var2 * 32] = var3;
      this.c.seek((var1 + var2 * 32) * 4);
      this.c.writeInt(var3);
   }

   private void b(int var1, int var2, int var3) throws IOException {
      this.e[var1 + var2 * 32] = var3;
      this.c.seek(4096 + (var1 + var2 * 32) * 4);
      this.c.writeInt(var3);
   }

   public void c() throws IOException {
      if (this.c != null) {
         this.c.close();
      }
   }

   class ChunkBuffer extends ByteArrayOutputStream {
      private int b;
      private int c;

      public ChunkBuffer(int var2, int var3) {
         super(8096);
         this.b = var2;
         this.c = var3;
      }

      @Override
      public void close() {
         RegionFile.this.a(this.b, this.c, this.buf, this.count);
      }
   }
}
