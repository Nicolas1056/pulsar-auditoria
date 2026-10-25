package net.minecraft.server.management;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerManager {
   private static final Logger a = LogManager.getLogger();
   private final WorldServer b;
   private final List<EntityPlayerMP> c = Lists.newArrayList();
   private final LongHashMap<PlayerManager.PlayerInstance> d = new LongHashMap<>();
   private final List<PlayerManager.PlayerInstance> e = Lists.newArrayList();
   private final List<PlayerManager.PlayerInstance> f = Lists.newArrayList();
   private int g;
   private long h;
   private final int[][] i = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

   public PlayerManager(WorldServer var1) {
      this.b = var1;
      this.a(var1.r().ap().s());
   }

   public WorldServer a() {
      return this.b;
   }

   public void b() {
      long var1 = this.b.K();
      if (var1 - this.h > 8000L) {
         this.h = var1;

         for (int var3 = 0; var3 < this.f.size(); var3++) {
            PlayerManager.PlayerInstance var4 = this.f.get(var3);
            var4.b();
            var4.a();
         }
      } else {
         for (int var5 = 0; var5 < this.e.size(); var5++) {
            PlayerManager.PlayerInstance var7 = this.e.get(var5);
            var7.b();
         }
      }

      this.e.clear();
      if (this.c.isEmpty()) {
         WorldProvider var6 = this.b.t;
         if (!var6.e()) {
            this.b.b.b();
         }
      }
   }

   public boolean a(int var1, int var2) {
      long var3 = var1 + 2147483647L | var2 + 2147483647L << 32;
      return this.d.a(var3) != null;
   }

   private PlayerManager.PlayerInstance a(int var1, int var2, boolean var3) {
      long var4 = var1 + 2147483647L | var2 + 2147483647L << 32;
      PlayerManager.PlayerInstance var6 = this.d.a(var4);
      if (var6 == null && var3) {
         var6 = new PlayerManager.PlayerInstance(var1, var2);
         this.d.a(var4, var6);
         this.f.add(var6);
      }

      return var6;
   }

   public void a(BlockPos var1) {
      int var2 = var1.n() >> 4;
      int var3 = var1.p() >> 4;
      PlayerManager.PlayerInstance var4 = this.a(var2, var3, false);
      if (var4 != null) {
         var4.a(var1.n() & 15, var1.o(), var1.p() & 15);
      }
   }

   public void a(EntityPlayerMP var1) {
      int var2 = (int)var1.s >> 4;
      int var3 = (int)var1.u >> 4;
      var1.d = var1.s;
      var1.e = var1.u;

      for (int var4 = var2 - this.g; var4 <= var2 + this.g; var4++) {
         for (int var5 = var3 - this.g; var5 <= var3 + this.g; var5++) {
            this.a(var4, var5, true).a(var1);
         }
      }

      this.c.add(var1);
      this.b(var1);
   }

   public void b(EntityPlayerMP var1) {
      ArrayList var2 = Lists.newArrayList(var1.f);
      int var3 = 0;
      int var4 = this.g;
      int var5 = (int)var1.s >> 4;
      int var6 = (int)var1.u >> 4;
      int var7 = 0;
      int var8 = 0;
      ChunkCoordIntPair var9 = this.a(var5, var6, true).c;
      var1.f.clear();
      if (var2.contains(var9)) {
         var1.f.add(var9);
      }

      for (int var10 = 1; var10 <= var4 * 2; var10++) {
         for (int var11 = 0; var11 < 2; var11++) {
            int[] var12 = this.i[var3++ % 4];

            for (int var13 = 0; var13 < var10; var13++) {
               var7 += var12[0];
               var8 += var12[1];
               var9 = this.a(var5 + var7, var6 + var8, true).c;
               if (var2.contains(var9)) {
                  var1.f.add(var9);
               }
            }
         }
      }

      var3 %= 4;

      for (int var17 = 0; var17 < var4 * 2; var17++) {
         var7 += this.i[var3][0];
         var8 += this.i[var3][1];
         var9 = this.a(var5 + var7, var6 + var8, true).c;
         if (var2.contains(var9)) {
            var1.f.add(var9);
         }
      }
   }

   public void c(EntityPlayerMP var1) {
      int var2 = (int)var1.d >> 4;
      int var3 = (int)var1.e >> 4;

      for (int var4 = var2 - this.g; var4 <= var2 + this.g; var4++) {
         for (int var5 = var3 - this.g; var5 <= var3 + this.g; var5++) {
            PlayerManager.PlayerInstance var6 = this.a(var4, var5, false);
            if (var6 != null) {
               var6.b(var1);
            }
         }
      }

      this.c.remove(var1);
   }

   private boolean a(int var1, int var2, int var3, int var4, int var5) {
      int var6 = var1 - var3;
      int var7 = var2 - var4;
      return var6 < -var5 || var6 > var5 ? false : var7 >= -var5 && var7 <= var5;
   }

   public void d(EntityPlayerMP var1) {
      int var2 = (int)var1.s >> 4;
      int var3 = (int)var1.u >> 4;
      double var4 = var1.d - var1.s;
      double var6 = var1.e - var1.u;
      double var8 = var4 * var4 + var6 * var6;
      if (!(var8 < 64.0)) {
         int var10 = (int)var1.d >> 4;
         int var11 = (int)var1.e >> 4;
         int var12 = this.g;
         int var13 = var2 - var10;
         int var14 = var3 - var11;
         if (var13 != 0 || var14 != 0) {
            for (int var15 = var2 - var12; var15 <= var2 + var12; var15++) {
               for (int var16 = var3 - var12; var16 <= var3 + var12; var16++) {
                  if (!this.a(var15, var16, var10, var11, var12)) {
                     this.a(var15, var16, true).a(var1);
                  }

                  if (!this.a(var15 - var13, var16 - var14, var2, var3, var12)) {
                     PlayerManager.PlayerInstance var17 = this.a(var15 - var13, var16 - var14, false);
                     if (var17 != null) {
                        var17.b(var1);
                     }
                  }
               }
            }

            this.b(var1);
            var1.d = var1.s;
            var1.e = var1.u;
         }
      }
   }

   public boolean a(EntityPlayerMP var1, int var2, int var3) {
      PlayerManager.PlayerInstance var4 = this.a(var2, var3, false);
      return var4 != null && var4.b.contains(var1) && !var1.f.contains(var4.c);
   }

   public void a(int var1) {
      var1 = MathHelper.a(var1, 3, 32);
      if (var1 != this.g) {
         int var2 = var1 - this.g;

         for (EntityPlayerMP var5 : Lists.newArrayList(this.c)) {
            int var6 = (int)var5.s >> 4;
            int var7 = (int)var5.u >> 4;
            if (var2 > 0) {
               for (int var12 = var6 - var1; var12 <= var6 + var1; var12++) {
                  for (int var13 = var7 - var1; var13 <= var7 + var1; var13++) {
                     PlayerManager.PlayerInstance var10 = this.a(var12, var13, true);
                     if (!var10.b.contains(var5)) {
                        var10.a(var5);
                     }
                  }
               }
            } else {
               for (int var8 = var6 - this.g; var8 <= var6 + this.g; var8++) {
                  for (int var9 = var7 - this.g; var9 <= var7 + this.g; var9++) {
                     if (!this.a(var8, var9, var6, var7, var1)) {
                        this.a(var8, var9, true).b(var5);
                     }
                  }
               }
            }
         }

         this.g = var1;
      }
   }

   public static int b(int var0) {
      return var0 * 16 - 16;
   }

   class PlayerInstance {
      private final List<EntityPlayerMP> b = Lists.newArrayList();
      private final ChunkCoordIntPair c;
      private short[] d = new short[64];
      private int e;
      private int f;
      private long g;

      public PlayerInstance(int var2, int var3) {
         this.c = new ChunkCoordIntPair(var2, var3);
         PlayerManager.this.a().b.c(var2, var3);
      }

      public void a(EntityPlayerMP var1) {
         if (this.b.contains(var1)) {
            PlayerManager.a.debug("Failed to add player. {} already is in chunk {}, {}", new Object[]{var1, this.c.a, this.c.b});
         } else {
            if (this.b.isEmpty()) {
               this.g = PlayerManager.this.b.K();
            }

            this.b.add(var1);
            var1.f.add(this.c);
         }
      }

      public void b(EntityPlayerMP var1) {
         if (this.b.contains(var1)) {
            Chunk var2 = PlayerManager.this.b.a(this.c.a, this.c.b);
            if (var2.i()) {
               var1.a.a(new S21PacketChunkData(var2, true, 0));
            }

            this.b.remove(var1);
            var1.f.remove(this.c);
            if (this.b.isEmpty()) {
               long var3 = this.c.a + 2147483647L | this.c.b + 2147483647L << 32;
               this.a(var2);
               PlayerManager.this.d.d(var3);
               PlayerManager.this.f.remove(this);
               if (this.e > 0) {
                  PlayerManager.this.e.remove(this);
               }

               PlayerManager.this.a().b.b(this.c.a, this.c.b);
            }
         }
      }

      public void a() {
         this.a(PlayerManager.this.b.a(this.c.a, this.c.b));
      }

      private void a(Chunk var1) {
         var1.c(var1.w() + PlayerManager.this.b.K() - this.g);
         this.g = PlayerManager.this.b.K();
      }

      public void a(int var1, int var2, int var3) {
         if (this.e == 0) {
            PlayerManager.this.e.add(this);
         }

         this.f |= 1 << (var2 >> 4);
         if (this.e < 64) {
            short var4 = (short)(var1 << 12 | var3 << 8 | var2);

            for (int var5 = 0; var5 < this.e; var5++) {
               if (this.d[var5] == var4) {
                  return;
               }
            }

            this.d[this.e++] = var4;
         }
      }

      public void a(Packet var1) {
         for (int var2 = 0; var2 < this.b.size(); var2++) {
            EntityPlayerMP var3 = this.b.get(var2);
            if (!var3.f.contains(this.c)) {
               var3.a.a(var1);
            }
         }
      }

      public void b() {
         if (this.e != 0) {
            if (this.e == 1) {
               int var1 = (this.d[0] >> 12 & 15) + this.c.a * 16;
               int var2 = this.d[0] & 255;
               int var3 = (this.d[0] >> 8 & 15) + this.c.b * 16;
               BlockPos var4 = new BlockPos(var1, var2, var3);
               this.a(new S23PacketBlockChange(PlayerManager.this.b, var4));
               if (PlayerManager.this.b.p(var4).c().z()) {
                  this.a(PlayerManager.this.b.s(var4));
               }
            } else if (this.e == 64) {
               int var7 = this.c.a * 16;
               int var9 = this.c.b * 16;
               this.a(new S21PacketChunkData(PlayerManager.this.b.a(this.c.a, this.c.b), false, this.f));

               for (int var11 = 0; var11 < 16; var11++) {
                  if ((this.f & 1 << var11) != 0) {
                     int var13 = var11 << 4;
                     List var5 = PlayerManager.this.b.a(var7, var13, var9, var7 + 16, var13 + 16, var9 + 16);

                     for (int var6 = 0; var6 < var5.size(); var6++) {
                        this.a((TileEntity)var5.get(var6));
                     }
                  }
               }
            } else {
               this.a(new S22PacketMultiBlockChange(this.e, this.d, PlayerManager.this.b.a(this.c.a, this.c.b)));

               for (int var8 = 0; var8 < this.e; var8++) {
                  int var10 = (this.d[var8] >> 12 & 15) + this.c.a * 16;
                  int var12 = this.d[var8] & 255;
                  int var14 = (this.d[var8] >> 8 & 15) + this.c.b * 16;
                  BlockPos var15 = new BlockPos(var10, var12, var14);
                  if (PlayerManager.this.b.p(var15).c().z()) {
                     this.a(PlayerManager.this.b.s(var15));
                  }
               }
            }

            this.e = 0;
            this.f = 0;
         }
      }

      private void a(TileEntity var1) {
         if (var1 != null) {
            Packet var2 = var1.y_();
            if (var2 != null) {
               this.a(var2);
            }
         }
      }
   }
}
