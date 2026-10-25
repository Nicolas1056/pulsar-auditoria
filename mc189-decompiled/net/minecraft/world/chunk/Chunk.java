package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderDebug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chunk {
   private static final Logger c = LogManager.getLogger();
   private final ExtendedBlockStorage[] d = new ExtendedBlockStorage[16];
   private final byte[] e = new byte[256];
   private final int[] f = new int[256];
   private final boolean[] g = new boolean[256];
   private boolean h;
   private final World i;
   private final int[] j;
   public final int a;
   public final int b;
   private boolean k;
   private final Map<BlockPos, TileEntity> l = Maps.newHashMap();
   private final ClassInheritanceMultiMap<Entity>[] m;
   private boolean n;
   private boolean o;
   private boolean p;
   private boolean q;
   private boolean r;
   private long s;
   private int t;
   private long u;
   private int v = 4096;
   private ConcurrentLinkedQueue<BlockPos> w = Queues.newConcurrentLinkedQueue();

   public Chunk(World var1, int var2, int var3) {
      this.m = new ClassInheritanceMultiMap[16];
      this.i = var1;
      this.a = var2;
      this.b = var3;
      this.j = new int[256];

      for (int var4 = 0; var4 < this.m.length; var4++) {
         this.m[var4] = new ClassInheritanceMultiMap<>(Entity.class);
      }

      Arrays.fill(this.f, -999);
      Arrays.fill(this.e, (byte)-1);
   }

   public Chunk(World var1, ChunkPrimer var2, int var3, int var4) {
      this(var1, var3, var4);
      short var5 = 256;
      boolean var6 = !var1.t.o();

      for (int var7 = 0; var7 < 16; var7++) {
         for (int var8 = 0; var8 < 16; var8++) {
            for (int var9 = 0; var9 < var5; var9++) {
               int var10 = var7 * var5 * 16 | var8 * var5 | var9;
               IBlockState var11 = var2.a(var10);
               if (var11.c().t() != Material.a) {
                  int var12 = var9 >> 4;
                  if (this.d[var12] == null) {
                     this.d[var12] = new ExtendedBlockStorage(var12 << 4, var6);
                  }

                  this.d[var12].a(var7, var9 & 15, var8, var11);
               }
            }
         }
      }
   }

   public boolean a(int var1, int var2) {
      return var1 == this.a && var2 == this.b;
   }

   public int f(BlockPos var1) {
      return this.b(var1.n() & 15, var1.p() & 15);
   }

   public int b(int var1, int var2) {
      return this.j[var2 << 4 | var1];
   }

   public int g() {
      for (int var1 = this.d.length - 1; var1 >= 0; var1--) {
         if (this.d[var1] != null) {
            return this.d[var1].d();
         }
      }

      return 0;
   }

   public ExtendedBlockStorage[] h() {
      return this.d;
   }

   protected void a() {
      int var1 = this.g();
      this.t = Integer.MAX_VALUE;

      for (int var2 = 0; var2 < 16; var2++) {
         for (int var3 = 0; var3 < 16; var3++) {
            this.f[var2 + (var3 << 4)] = -999;

            for (int var4 = var1 + 16; var4 > 0; var4--) {
               Block var5 = this.f(var2, var4 - 1, var3);
               if (var5.p() != 0) {
                  this.j[var3 << 4 | var2] = var4;
                  if (var4 < this.t) {
                     this.t = var4;
                  }
                  break;
               }
            }
         }
      }

      this.q = true;
   }

   public void b() {
      int var1 = this.g();
      this.t = Integer.MAX_VALUE;

      for (int var2 = 0; var2 < 16; var2++) {
         for (int var3 = 0; var3 < 16; var3++) {
            this.f[var2 + (var3 << 4)] = -999;

            for (int var4 = var1 + 16; var4 > 0; var4--) {
               if (this.e(var2, var4 - 1, var3) != 0) {
                  this.j[var3 << 4 | var2] = var4;
                  if (var4 < this.t) {
                     this.t = var4;
                  }
                  break;
               }
            }

            if (!this.i.t.o()) {
               int var8 = 15;
               int var5 = var1 + 16 - 1;

               while (true) {
                  int var6 = this.e(var2, var5, var3);
                  if (var6 == 0 && var8 != 15) {
                     var6 = 1;
                  }

                  var8 -= var6;
                  if (var8 > 0) {
                     ExtendedBlockStorage var7 = this.d[var5 >> 4];
                     if (var7 != null) {
                        var7.a(var2, var5 & 15, var3, var8);
                        this.i.n(new BlockPos((this.a << 4) + var2, var5, (this.b << 4) + var3));
                     }
                  }

                  if (--var5 <= 0 || var8 <= 0) {
                     break;
                  }
               }
            }
         }
      }

      this.q = true;
   }

   private void d(int var1, int var2) {
      this.g[var1 + var2 * 16] = true;
      this.k = true;
   }

   private void h(boolean var1) {
      this.i.B.a("recheckGaps");
      if (this.i.a(new BlockPos(this.a * 16 + 8, 0, this.b * 16 + 8), 16)) {
         for (int var2 = 0; var2 < 16; var2++) {
            for (int var3 = 0; var3 < 16; var3++) {
               if (this.g[var2 + var3 * 16]) {
                  this.g[var2 + var3 * 16] = false;
                  int var4 = this.b(var2, var3);
                  int var5 = this.a * 16 + var2;
                  int var6 = this.b * 16 + var3;
                  int var7 = Integer.MAX_VALUE;

                  for (EnumFacing var9 : EnumFacing.Plane.a) {
                     var7 = Math.min(var7, this.i.b(var5 + var9.g(), var6 + var9.i()));
                  }

                  this.c(var5, var6, var7);

                  for (EnumFacing var11 : EnumFacing.Plane.a) {
                     this.c(var5 + var11.g(), var6 + var11.i(), var4);
                  }

                  if (var1) {
                     this.i.B.b();
                     return;
                  }
               }
            }
         }

         this.k = false;
      }

      this.i.B.b();
   }

   private void c(int var1, int var2, int var3) {
      int var4 = this.i.m(new BlockPos(var1, 0, var2)).o();
      if (var4 > var3) {
         this.a(var1, var2, var3, var4 + 1);
      } else if (var4 < var3) {
         this.a(var1, var2, var4, var3 + 1);
      }
   }

   private void a(int var1, int var2, int var3, int var4) {
      if (var4 > var3 && this.i.a(new BlockPos(var1, 0, var2), 16)) {
         for (int var5 = var3; var5 < var4; var5++) {
            this.i.c(EnumSkyBlock.a, new BlockPos(var1, var5, var2));
         }

         this.q = true;
      }
   }

   private void d(int var1, int var2, int var3) {
      int var4 = this.j[var3 << 4 | var1] & 0xFF;
      int var5 = var4;
      if (var2 > var4) {
         var5 = var2;
      }

      while (var5 > 0 && this.e(var1, var5 - 1, var3) == 0) {
         var5--;
      }

      if (var5 != var4) {
         this.i.a(var1 + this.a * 16, var3 + this.b * 16, var5, var4);
         this.j[var3 << 4 | var1] = var5;
         int var6 = this.a * 16 + var1;
         int var7 = this.b * 16 + var3;
         if (!this.i.t.o()) {
            if (var5 < var4) {
               for (int var13 = var5; var13 < var4; var13++) {
                  ExtendedBlockStorage var16 = this.d[var13 >> 4];
                  if (var16 != null) {
                     var16.a(var1, var13 & 15, var3, 15);
                     this.i.n(new BlockPos((this.a << 4) + var1, var13, (this.b << 4) + var3));
                  }
               }
            } else {
               for (int var8 = var4; var8 < var5; var8++) {
                  ExtendedBlockStorage var9 = this.d[var8 >> 4];
                  if (var9 != null) {
                     var9.a(var1, var8 & 15, var3, 0);
                     this.i.n(new BlockPos((this.a << 4) + var1, var8, (this.b << 4) + var3));
                  }
               }
            }

            int var14 = 15;

            while (var5 > 0 && var14 > 0) {
               int var17 = this.e(var1, --var5, var3);
               if (var17 == 0) {
                  var17 = 1;
               }

               var14 -= var17;
               if (var14 < 0) {
                  var14 = 0;
               }

               ExtendedBlockStorage var10 = this.d[var5 >> 4];
               if (var10 != null) {
                  var10.a(var1, var5 & 15, var3, var14);
               }
            }
         }

         int var15 = this.j[var3 << 4 | var1];
         int var18 = var4;
         int var19 = var15;
         if (var15 < var4) {
            var18 = var15;
            var19 = var4;
         }

         if (var15 < this.t) {
            this.t = var15;
         }

         if (!this.i.t.o()) {
            for (EnumFacing var12 : EnumFacing.Plane.a) {
               this.a(var6 + var12.g(), var7 + var12.i(), var18, var19);
            }

            this.a(var6, var7, var18, var19);
         }

         this.q = true;
      }
   }

   public int b(BlockPos var1) {
      return this.a(var1).p();
   }

   private int e(int var1, int var2, int var3) {
      return this.f(var1, var2, var3).p();
   }

   private Block f(int var1, int var2, int var3) {
      Block var4 = Blocks.a;
      if (var2 >= 0 && var2 >> 4 < this.d.length) {
         ExtendedBlockStorage var5 = this.d[var2 >> 4];
         if (var5 != null) {
            try {
               var4 = var5.b(var1, var2 & 15, var3);
            } catch (Throwable var8) {
               CrashReport var7 = CrashReport.a(var8, "Getting block");
               throw new ReportedException(var7);
            }
         }
      }

      return var4;
   }

   public Block a(final int var1, final int var2, final int var3) {
      try {
         return this.f(var1 & 15, var2, var3 & 15);
      } catch (ReportedException var6) {
         CrashReportCategory var5 = var6.a().a("Block being got");
         var5.a("Location", new Callable<String>() {
            public String a() throws Exception {
               return CrashReportCategory.a(new BlockPos(Chunk.this.a * 16 + var1, var2, Chunk.this.b * 16 + var3));
            }
         });
         throw var6;
      }
   }

   public Block a(final BlockPos var1) {
      try {
         return this.f(var1.n() & 15, var1.o(), var1.p() & 15);
      } catch (ReportedException var4) {
         CrashReportCategory var3 = var4.a().a("Block being got");
         var3.a("Location", new Callable<String>() {
            public String a() throws Exception {
               return CrashReportCategory.a(var1);
            }
         });
         throw var4;
      }
   }

   public IBlockState g(final BlockPos var1) {
      if (this.i.G() == WorldType.g) {
         IBlockState var7 = null;
         if (var1.o() == 60) {
            var7 = Blocks.cv.Q();
         }

         if (var1.o() == 70) {
            var7 = ChunkProviderDebug.b(var1.n(), var1.p());
         }

         return var7 == null ? Blocks.a.Q() : var7;
      } else {
         try {
            if (var1.o() >= 0 && var1.o() >> 4 < this.d.length) {
               ExtendedBlockStorage var2 = this.d[var1.o() >> 4];
               if (var2 != null) {
                  int var8 = var1.n() & 15;
                  int var9 = var1.o() & 15;
                  int var5 = var1.p() & 15;
                  return var2.a(var8, var9, var5);
               }
            }

            return Blocks.a.Q();
         } catch (Throwable var6) {
            CrashReport var3 = CrashReport.a(var6, "Getting block state");
            CrashReportCategory var4 = var3.a("Block being got");
            var4.a("Location", new Callable<String>() {
               public String a() throws Exception {
                  return CrashReportCategory.a(var1);
               }
            });
            throw new ReportedException(var3);
         }
      }
   }

   private int g(int var1, int var2, int var3) {
      if (var2 >> 4 >= this.d.length) {
         return 0;
      } else {
         ExtendedBlockStorage var4 = this.d[var2 >> 4];
         return var4 != null ? var4.c(var1, var2 & 15, var3) : 0;
      }
   }

   public int c(BlockPos var1) {
      return this.g(var1.n() & 15, var1.o(), var1.p() & 15);
   }

   public IBlockState a(BlockPos var1, IBlockState var2) {
      int var3 = var1.n() & 15;
      int var4 = var1.o();
      int var5 = var1.p() & 15;
      int var6 = var5 << 4 | var3;
      if (var4 >= this.f[var6] - 1) {
         this.f[var6] = -999;
      }

      int var7 = this.j[var6];
      IBlockState var8 = this.g(var1);
      if (var8 == var2) {
         return null;
      } else {
         Block var9 = var2.c();
         Block var10 = var8.c();
         ExtendedBlockStorage var11 = this.d[var4 >> 4];
         boolean var12 = false;
         if (var11 == null) {
            if (var9 == Blocks.a) {
               return null;
            }

            var11 = this.d[var4 >> 4] = new ExtendedBlockStorage(var4 >> 4 << 4, !this.i.t.o());
            var12 = var4 >= var7;
         }

         var11.a(var3, var4 & 15, var5, var2);
         if (var10 != var9) {
            if (!this.i.D) {
               var10.b(this.i, var1, var8);
            } else if (var10 instanceof ITileEntityProvider) {
               this.i.t(var1);
            }
         }

         if (var11.b(var3, var4 & 15, var5) != var9) {
            return null;
         } else {
            if (var12) {
               this.b();
            } else {
               int var13 = var9.p();
               int var14 = var10.p();
               if (var13 > 0) {
                  if (var4 >= var7) {
                     this.d(var3, var4 + 1, var5);
                  }
               } else if (var4 == var7 - 1) {
                  this.d(var3, var4, var5);
               }

               if (var13 != var14 && (var13 < var14 || this.a(EnumSkyBlock.a, var1) > 0 || this.a(EnumSkyBlock.b, var1) > 0)) {
                  this.d(var3, var5);
               }
            }

            if (var10 instanceof ITileEntityProvider) {
               TileEntity var15 = this.a(var1, Chunk.EnumCreateEntityType.c);
               if (var15 != null) {
                  var15.E();
               }
            }

            if (!this.i.D && var10 != var9) {
               var9.c(this.i, var1, var2);
            }

            if (var9 instanceof ITileEntityProvider) {
               TileEntity var16 = this.a(var1, Chunk.EnumCreateEntityType.c);
               if (var16 == null) {
                  var16 = ((ITileEntityProvider)var9).a(this.i, var9.c(var2));
                  this.i.a(var1, var16);
               }

               if (var16 != null) {
                  var16.E();
               }
            }

            this.q = true;
            return var8;
         }
      }
   }

   public int a(EnumSkyBlock var1, BlockPos var2) {
      int var3 = var2.n() & 15;
      int var4 = var2.o();
      int var5 = var2.p() & 15;
      ExtendedBlockStorage var6 = this.d[var4 >> 4];
      if (var6 == null) {
         return this.d(var2) ? var1.c : 0;
      } else if (var1 == EnumSkyBlock.a) {
         return this.i.t.o() ? 0 : var6.d(var3, var4 & 15, var5);
      } else {
         return var1 == EnumSkyBlock.b ? var6.e(var3, var4 & 15, var5) : var1.c;
      }
   }

   public void a(EnumSkyBlock var1, BlockPos var2, int var3) {
      int var4 = var2.n() & 15;
      int var5 = var2.o();
      int var6 = var2.p() & 15;
      ExtendedBlockStorage var7 = this.d[var5 >> 4];
      if (var7 == null) {
         var7 = this.d[var5 >> 4] = new ExtendedBlockStorage(var5 >> 4 << 4, !this.i.t.o());
         this.b();
      }

      this.q = true;
      if (var1 == EnumSkyBlock.a) {
         if (!this.i.t.o()) {
            var7.a(var4, var5 & 15, var6, var3);
         }
      } else if (var1 == EnumSkyBlock.b) {
         var7.b(var4, var5 & 15, var6, var3);
      }
   }

   public int a(BlockPos var1, int var2) {
      int var3 = var1.n() & 15;
      int var4 = var1.o();
      int var5 = var1.p() & 15;
      ExtendedBlockStorage var6 = this.d[var4 >> 4];
      if (var6 == null) {
         return !this.i.t.o() && var2 < EnumSkyBlock.a.c ? EnumSkyBlock.a.c - var2 : 0;
      } else {
         int var7 = this.i.t.o() ? 0 : var6.d(var3, var4 & 15, var5);
         var7 -= var2;
         int var8 = var6.e(var3, var4 & 15, var5);
         if (var8 > var7) {
            var7 = var8;
         }

         return var7;
      }
   }

   public void a(Entity var1) {
      this.r = true;
      int var2 = MathHelper.c(var1.s / 16.0);
      int var3 = MathHelper.c(var1.u / 16.0);
      if (var2 != this.a || var3 != this.b) {
         c.warn("Wrong location! (" + var2 + ", " + var3 + ") should be (" + this.a + ", " + this.b + "), " + var1, new Object[]{var1});
         var1.J();
      }

      int var4 = MathHelper.c(var1.t / 16.0);
      if (var4 < 0) {
         var4 = 0;
      }

      if (var4 >= this.m.length) {
         var4 = this.m.length - 1;
      }

      var1.ad = true;
      var1.ae = this.a;
      var1.af = var4;
      var1.ag = this.b;
      this.m[var4].add(var1);
   }

   public void b(Entity var1) {
      this.a(var1, var1.af);
   }

   public void a(Entity var1, int var2) {
      if (var2 < 0) {
         var2 = 0;
      }

      if (var2 >= this.m.length) {
         var2 = this.m.length - 1;
      }

      this.m[var2].remove(var1);
   }

   public boolean d(BlockPos var1) {
      int var2 = var1.n() & 15;
      int var3 = var1.o();
      int var4 = var1.p() & 15;
      return var3 >= this.j[var4 << 4 | var2];
   }

   private TileEntity i(BlockPos var1) {
      Block var2 = this.a(var1);
      return !var2.z() ? null : ((ITileEntityProvider)var2).a(this.i, this.c(var1));
   }

   public TileEntity a(BlockPos var1, Chunk.EnumCreateEntityType var2) {
      TileEntity var3 = this.l.get(var1);
      if (var3 == null) {
         if (var2 == Chunk.EnumCreateEntityType.a) {
            var3 = this.i(var1);
            this.i.a(var1, var3);
         } else if (var2 == Chunk.EnumCreateEntityType.b) {
            this.w.add(var1);
         }
      } else if (var3.x()) {
         this.l.remove(var1);
         return null;
      }

      return var3;
   }

   public void a(TileEntity var1) {
      this.a(var1.v(), var1);
      if (this.h) {
         this.i.a(var1);
      }
   }

   public void a(BlockPos var1, TileEntity var2) {
      var2.a(this.i);
      var2.a(var1);
      if (this.a(var1) instanceof ITileEntityProvider) {
         if (this.l.containsKey(var1)) {
            this.l.get(var1).y();
         }

         var2.D();
         this.l.put(var1, var2);
      }
   }

   public void e(BlockPos var1) {
      if (this.h) {
         TileEntity var2 = this.l.remove(var1);
         if (var2 != null) {
            var2.y();
         }
      }
   }

   public void c() {
      this.h = true;
      this.i.a(this.l.values());

      for (int var1 = 0; var1 < this.m.length; var1++) {
         for (Entity var3 : this.m[var1]) {
            var3.ah();
         }

         this.i.b(this.m[var1]);
      }
   }

   public void d() {
      this.h = false;

      for (TileEntity var2 : this.l.values()) {
         this.i.b(var2);
      }

      for (int var3 = 0; var3 < this.m.length; var3++) {
         this.i.c(this.m[var3]);
      }
   }

   public void e() {
      this.q = true;
   }

   public void a(Entity var1, AxisAlignedBB var2, List<Entity> var3, Predicate<? super Entity> var4) {
      int var5 = MathHelper.c((var2.b - 2.0) / 16.0);
      int var6 = MathHelper.c((var2.e + 2.0) / 16.0);
      var5 = MathHelper.a(var5, 0, this.m.length - 1);
      var6 = MathHelper.a(var6, 0, this.m.length - 1);

      for (int var7 = var5; var7 <= var6; var7++) {
         if (!this.m[var7].isEmpty()) {
            for (Entity var9 : this.m[var7]) {
               if (var9.aR().b(var2) && var9 != var1) {
                  if (var4 == null || var4.apply(var9)) {
                     var3.add(var9);
                  }

                  Entity[] var10 = var9.aB();
                  if (var10 != null) {
                     for (int var11 = 0; var11 < var10.length; var11++) {
                        var9 = var10[var11];
                        if (var9 != var1 && var9.aR().b(var2) && (var4 == null || var4.apply(var9))) {
                           var3.add(var9);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public <T extends Entity> void a(Class<? extends T> var1, AxisAlignedBB var2, List<T> var3, Predicate<? super T> var4) {
      int var5 = MathHelper.c((var2.b - 2.0) / 16.0);
      int var6 = MathHelper.c((var2.e + 2.0) / 16.0);
      var5 = MathHelper.a(var5, 0, this.m.length - 1);
      var6 = MathHelper.a(var6, 0, this.m.length - 1);

      for (int var7 = var5; var7 <= var6; var7++) {
         for (Entity var9 : this.m[var7].c(var1)) {
            if (var9.aR().b(var2) && (var4 == null || var4.apply(var9))) {
               var3.add(var9);
            }
         }
      }
   }

   public boolean a(boolean var1) {
      if (var1) {
         if (this.r && this.i.K() != this.s || this.q) {
            return true;
         }
      } else if (this.r && this.i.K() >= this.s + 600L) {
         return true;
      }

      return this.q;
   }

   public Random a(long var1) {
      return new Random(this.i.J() + this.a * this.a * 4987142 + this.a * 5947611 + this.b * this.b * 4392871L + this.b * 389711 ^ var1);
   }

   public boolean f() {
      return false;
   }

   public void a(IChunkProvider var1, IChunkProvider var2, int var3, int var4) {
      boolean var5 = var1.a(var3, var4 - 1);
      boolean var6 = var1.a(var3 + 1, var4);
      boolean var7 = var1.a(var3, var4 + 1);
      boolean var8 = var1.a(var3 - 1, var4);
      boolean var9 = var1.a(var3 - 1, var4 - 1);
      boolean var10 = var1.a(var3 + 1, var4 + 1);
      boolean var11 = var1.a(var3 - 1, var4 + 1);
      boolean var12 = var1.a(var3 + 1, var4 - 1);
      if (var6 && var7 && var10) {
         if (!this.n) {
            var1.a(var2, var3, var4);
         } else {
            var1.a(var2, this, var3, var4);
         }
      }

      if (var8 && var7 && var11) {
         Chunk var13 = var1.d(var3 - 1, var4);
         if (!var13.n) {
            var1.a(var2, var3 - 1, var4);
         } else {
            var1.a(var2, var13, var3 - 1, var4);
         }
      }

      if (var5 && var6 && var12) {
         Chunk var14 = var1.d(var3, var4 - 1);
         if (!var14.n) {
            var1.a(var2, var3, var4 - 1);
         } else {
            var1.a(var2, var14, var3, var4 - 1);
         }
      }

      if (var9 && var5 && var8) {
         Chunk var15 = var1.d(var3 - 1, var4 - 1);
         if (!var15.n) {
            var1.a(var2, var3 - 1, var4 - 1);
         } else {
            var1.a(var2, var15, var3 - 1, var4 - 1);
         }
      }
   }

   public BlockPos h(BlockPos var1) {
      int var2 = var1.n() & 15;
      int var3 = var1.p() & 15;
      int var4 = var2 | var3 << 4;
      BlockPos var5 = new BlockPos(var1.n(), this.f[var4], var1.p());
      if (var5.o() == -999) {
         int var6 = this.g() + 15;
         var5 = new BlockPos(var1.n(), var6, var1.p());
         int var7 = -1;

         while (var5.o() > 0 && var7 == -1) {
            Block var8 = this.a(var5);
            Material var9 = var8.t();
            if (!var9.c() && !var9.d()) {
               var5 = var5.b();
            } else {
               var7 = var5.o() + 1;
            }
         }

         this.f[var4] = var7;
      }

      return new BlockPos(var1.n(), this.f[var4], var1.p());
   }

   public void b(boolean var1) {
      if (this.k && !this.i.t.o() && !var1) {
         this.h(this.i.D);
      }

      this.p = true;
      if (!this.o && this.n) {
         this.n();
      }

      while (!this.w.isEmpty()) {
         BlockPos var2 = this.w.poll();
         if (this.a(var2, Chunk.EnumCreateEntityType.c) == null && this.a(var2).z()) {
            TileEntity var3 = this.i(var2);
            this.i.a(var2, var3);
            this.i.b(var2, var2);
         }
      }
   }

   public boolean i() {
      return this.p && this.n && this.o;
   }

   public ChunkCoordIntPair j() {
      return new ChunkCoordIntPair(this.a, this.b);
   }

   public boolean c(int var1, int var2) {
      if (var1 < 0) {
         var1 = 0;
      }

      if (var2 >= 256) {
         var2 = 255;
      }

      for (int var3 = var1; var3 <= var2; var3 += 16) {
         ExtendedBlockStorage var4 = this.d[var3 >> 4];
         if (var4 != null && !var4.a()) {
            return false;
         }
      }

      return true;
   }

   public void a(ExtendedBlockStorage[] var1) {
      if (this.d.length != var1.length) {
         c.warn("Could not set level chunk sections, array length is " + var1.length + " instead of " + this.d.length);
      } else {
         for (int var2 = 0; var2 < this.d.length; var2++) {
            this.d[var2] = var1[var2];
         }
      }
   }

   public void a(byte[] var1, int var2, boolean var3) {
      int var4 = 0;
      boolean var5 = !this.i.t.o();

      for (int var6 = 0; var6 < this.d.length; var6++) {
         if ((var2 & 1 << var6) != 0) {
            if (this.d[var6] == null) {
               this.d[var6] = new ExtendedBlockStorage(var6 << 4, var5);
            }

            char[] var7 = this.d[var6].g();

            for (int var8 = 0; var8 < var7.length; var8++) {
               var7[var8] = (char)((var1[var4 + 1] & 255) << 8 | var1[var4] & 255);
               var4 += 2;
            }
         } else if (var3 && this.d[var6] != null) {
            this.d[var6] = null;
         }
      }

      for (int var10 = 0; var10 < this.d.length; var10++) {
         if ((var2 & 1 << var10) != 0 && this.d[var10] != null) {
            NibbleArray var14 = this.d[var10].h();
            System.arraycopy(var1, var4, var14.a(), 0, var14.a().length);
            var4 += var14.a().length;
         }
      }

      if (var5) {
         for (int var11 = 0; var11 < this.d.length; var11++) {
            if ((var2 & 1 << var11) != 0 && this.d[var11] != null) {
               NibbleArray var15 = this.d[var11].i();
               System.arraycopy(var1, var4, var15.a(), 0, var15.a().length);
               var4 += var15.a().length;
            }
         }
      }

      if (var3) {
         System.arraycopy(var1, var4, this.e, 0, this.e.length);
         var4 += this.e.length;
      }

      for (int var12 = 0; var12 < this.d.length; var12++) {
         if (this.d[var12] != null && (var2 & 1 << var12) != 0) {
            this.d[var12].e();
         }
      }

      this.o = true;
      this.n = true;
      this.a();

      for (TileEntity var16 : this.l.values()) {
         var16.E();
      }
   }

   public BiomeGenBase a(BlockPos var1, WorldChunkManager var2) {
      int var3 = var1.n() & 15;
      int var4 = var1.p() & 15;
      int var5 = this.e[var4 << 4 | var3] & 255;
      if (var5 == 255) {
         BiomeGenBase var6 = var2.a(var1, BiomeGenBase.q);
         var5 = var6.az;
         this.e[var4 << 4 | var3] = (byte)(var5 & 0xFF);
      }

      BiomeGenBase var7 = BiomeGenBase.e(var5);
      return var7 == null ? BiomeGenBase.q : var7;
   }

   public byte[] k() {
      return this.e;
   }

   public void a(byte[] var1) {
      if (this.e.length != var1.length) {
         c.warn("Could not set level chunk biomes, array length is " + var1.length + " instead of " + this.e.length);
      } else {
         for (int var2 = 0; var2 < this.e.length; var2++) {
            this.e[var2] = var1[var2];
         }
      }
   }

   public void l() {
      this.v = 0;
   }

   public void m() {
      BlockPos var1 = new BlockPos(this.a << 4, 0, this.b << 4);

      for (int var2 = 0; var2 < 8; var2++) {
         if (this.v >= 4096) {
            return;
         }

         int var3 = this.v % 16;
         int var4 = this.v / 16 % 16;
         int var5 = this.v / 256;
         this.v++;

         for (int var6 = 0; var6 < 16; var6++) {
            BlockPos var7 = var1.a(var4, (var3 << 4) + var6, var5);
            boolean var8 = var6 == 0 || var6 == 15 || var4 == 0 || var4 == 15 || var5 == 0 || var5 == 15;
            if (this.d[var3] == null && var8 || this.d[var3] != null && this.d[var3].b(var4, var6, var5).t() == Material.a) {
               for (EnumFacing var12 : EnumFacing.values()) {
                  BlockPos var13 = var7.a(var12);
                  if (this.i.p(var13).c().r() > 0) {
                     this.i.x(var13);
                  }
               }

               this.i.x(var7);
            }
         }
      }
   }

   public void n() {
      this.n = true;
      this.o = true;
      BlockPos var1 = new BlockPos(this.a << 4, 0, this.b << 4);
      if (!this.i.t.o()) {
         if (this.i.a(var1.a(-1, 0, -1), var1.a(16, this.i.F(), 16))) {
            label44:
            for (int var2 = 0; var2 < 16; var2++) {
               for (int var3 = 0; var3 < 16; var3++) {
                  if (!this.e(var2, var3)) {
                     this.o = false;
                     break label44;
                  }
               }
            }

            if (this.o) {
               for (EnumFacing var6 : EnumFacing.Plane.a) {
                  int var4 = var6.c() == EnumFacing.AxisDirection.a ? 16 : 1;
                  this.i.f(var1.a(var6, var4)).a(var6.d());
               }

               this.y();
            }
         } else {
            this.o = false;
         }
      }
   }

   private void y() {
      for (int var1 = 0; var1 < this.g.length; var1++) {
         this.g[var1] = true;
      }

      this.h(false);
   }

   private void a(EnumFacing var1) {
      if (this.n) {
         if (var1 == EnumFacing.f) {
            for (int var2 = 0; var2 < 16; var2++) {
               this.e(15, var2);
            }
         } else if (var1 == EnumFacing.e) {
            for (int var3 = 0; var3 < 16; var3++) {
               this.e(0, var3);
            }
         } else if (var1 == EnumFacing.d) {
            for (int var4 = 0; var4 < 16; var4++) {
               this.e(var4, 15);
            }
         } else if (var1 == EnumFacing.c) {
            for (int var5 = 0; var5 < 16; var5++) {
               this.e(var5, 0);
            }
         }
      }
   }

   private boolean e(int var1, int var2) {
      int var3 = this.g();
      boolean var4 = false;
      boolean var5 = false;
      BlockPos.MutableBlockPos var6 = new BlockPos.MutableBlockPos((this.a << 4) + var1, 0, (this.b << 4) + var2);

      for (int var7 = var3 + 16 - 1; var7 > this.i.F() || var7 > 0 && !var5; var7--) {
         var6.c(var6.n(), var7, var6.p());
         int var8 = this.b(var6);
         if (var8 == 255 && var6.o() < this.i.F()) {
            var5 = true;
         }

         if (!var4 && var8 > 0) {
            var4 = true;
         } else if (var4 && var8 == 0 && !this.i.x(var6)) {
            return false;
         }
      }

      for (int var9 = var6.o(); var9 > 0; var9--) {
         var6.c(var6.n(), var9, var6.p());
         if (this.a(var6).r() > 0) {
            this.i.x(var6);
         }
      }

      return true;
   }

   public boolean o() {
      return this.h;
   }

   public void c(boolean var1) {
      this.h = var1;
   }

   public World p() {
      return this.i;
   }

   public int[] q() {
      return this.j;
   }

   public void a(int[] var1) {
      if (this.j.length != var1.length) {
         c.warn("Could not set level chunk heightmap, array length is " + var1.length + " instead of " + this.j.length);
      } else {
         for (int var2 = 0; var2 < this.j.length; var2++) {
            this.j[var2] = var1[var2];
         }
      }
   }

   public Map<BlockPos, TileEntity> r() {
      return this.l;
   }

   public ClassInheritanceMultiMap<Entity>[] s() {
      return this.m;
   }

   public boolean t() {
      return this.n;
   }

   public void d(boolean var1) {
      this.n = var1;
   }

   public boolean u() {
      return this.o;
   }

   public void e(boolean var1) {
      this.o = var1;
   }

   public void f(boolean var1) {
      this.q = var1;
   }

   public void g(boolean var1) {
      this.r = var1;
   }

   public void b(long var1) {
      this.s = var1;
   }

   public int v() {
      return this.t;
   }

   public long w() {
      return this.u;
   }

   public void c(long var1) {
      this.u = var1;
   }

   public static enum EnumCreateEntityType {
      a,
      b,
      c;
   }
}
