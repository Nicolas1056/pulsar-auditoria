package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenNetherBridge;

public class ChunkProviderHell implements IChunkProvider {
   private final World h;
   private final boolean i;
   private final Random j;
   private double[] k = new double[256];
   private double[] l = new double[256];
   private double[] m = new double[256];
   private double[] n;
   private final NoiseGeneratorOctaves o;
   private final NoiseGeneratorOctaves p;
   private final NoiseGeneratorOctaves q;
   private final NoiseGeneratorOctaves r;
   private final NoiseGeneratorOctaves s;
   public final NoiseGeneratorOctaves a;
   public final NoiseGeneratorOctaves b;
   private final WorldGenFire t = new WorldGenFire();
   private final WorldGenGlowStone1 u = new WorldGenGlowStone1();
   private final WorldGenGlowStone2 v = new WorldGenGlowStone2();
   private final WorldGenerator w = new WorldGenMinable(Blocks.co.Q(), 14, BlockHelper.a(Blocks.aV));
   private final WorldGenHellLava x = new WorldGenHellLava(Blocks.k, true);
   private final WorldGenHellLava y = new WorldGenHellLava(Blocks.k, false);
   private final GeneratorBushFeature z = new GeneratorBushFeature(Blocks.P);
   private final GeneratorBushFeature A = new GeneratorBushFeature(Blocks.Q);
   private final MapGenNetherBridge B = new MapGenNetherBridge();
   private final MapGenBase C = new MapGenCavesHell();
   double[] c;
   double[] d;
   double[] e;
   double[] f;
   double[] g;

   public ChunkProviderHell(World var1, boolean var2, long var3) {
      this.h = var1;
      this.i = var2;
      this.j = new Random(var3);
      this.o = new NoiseGeneratorOctaves(this.j, 16);
      this.p = new NoiseGeneratorOctaves(this.j, 16);
      this.q = new NoiseGeneratorOctaves(this.j, 8);
      this.r = new NoiseGeneratorOctaves(this.j, 4);
      this.s = new NoiseGeneratorOctaves(this.j, 4);
      this.a = new NoiseGeneratorOctaves(this.j, 10);
      this.b = new NoiseGeneratorOctaves(this.j, 16);
      var1.b(63);
   }

   public void a(int var1, int var2, ChunkPrimer var3) {
      byte var4 = 4;
      int var5 = this.h.F() / 2 + 1;
      int var6 = var4 + 1;
      byte var7 = 17;
      int var8 = var4 + 1;
      this.n = this.a(this.n, var1 * var4, 0, var2 * var4, var6, var7, var8);

      for (int var9 = 0; var9 < var4; var9++) {
         for (int var10 = 0; var10 < var4; var10++) {
            for (int var11 = 0; var11 < 16; var11++) {
               double var12 = 0.125;
               double var14 = this.n[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
               double var16 = this.n[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
               double var18 = this.n[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
               double var20 = this.n[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
               double var22 = (this.n[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var14) * var12;
               double var24 = (this.n[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
               double var26 = (this.n[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var18) * var12;
               double var28 = (this.n[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var20) * var12;

               for (int var30 = 0; var30 < 8; var30++) {
                  double var31 = 0.25;
                  double var33 = var14;
                  double var35 = var16;
                  double var37 = (var18 - var14) * var31;
                  double var39 = (var20 - var16) * var31;

                  for (int var41 = 0; var41 < 4; var41++) {
                     double var42 = 0.25;
                     double var44 = var33;
                     double var46 = (var35 - var33) * var42;

                     for (int var48 = 0; var48 < 4; var48++) {
                        IBlockState var49 = null;
                        if (var11 * 8 + var30 < var5) {
                           var49 = Blocks.l.Q();
                        }

                        if (var44 > 0.0) {
                           var49 = Blocks.aV.Q();
                        }

                        int var50 = var41 + var9 * 4;
                        int var51 = var30 + var11 * 8;
                        int var52 = var48 + var10 * 4;
                        var3.a(var50, var51, var52, var49);
                        var44 += var46;
                     }

                     var33 += var37;
                     var35 += var39;
                  }

                  var14 += var22;
                  var16 += var24;
                  var18 += var26;
                  var20 += var28;
               }
            }
         }
      }
   }

   public void b(int var1, int var2, ChunkPrimer var3) {
      int var4 = this.h.F() + 1;
      double var5 = 0.03125;
      this.k = this.r.a(this.k, var1 * 16, var2 * 16, 0, 16, 16, 1, var5, var5, 1.0);
      this.l = this.r.a(this.l, var1 * 16, 109, var2 * 16, 16, 1, 16, var5, 1.0, var5);
      this.m = this.s.a(this.m, var1 * 16, var2 * 16, 0, 16, 16, 1, var5 * 2.0, var5 * 2.0, var5 * 2.0);

      for (int var7 = 0; var7 < 16; var7++) {
         for (int var8 = 0; var8 < 16; var8++) {
            boolean var9 = this.k[var7 + var8 * 16] + this.j.nextDouble() * 0.2 > 0.0;
            boolean var10 = this.l[var7 + var8 * 16] + this.j.nextDouble() * 0.2 > 0.0;
            int var11 = (int)(this.m[var7 + var8 * 16] / 3.0 + 3.0 + this.j.nextDouble() * 0.25);
            int var12 = -1;
            IBlockState var13 = Blocks.aV.Q();
            IBlockState var14 = Blocks.aV.Q();

            for (int var15 = 127; var15 >= 0; var15--) {
               if (var15 < 127 - this.j.nextInt(5) && var15 > this.j.nextInt(5)) {
                  IBlockState var16 = var3.a(var8, var15, var7);
                  if (var16.c() == null || var16.c().t() == Material.a) {
                     var12 = -1;
                  } else if (var16.c() == Blocks.aV) {
                     if (var12 == -1) {
                        if (var11 <= 0) {
                           var13 = null;
                           var14 = Blocks.aV.Q();
                        } else if (var15 >= var4 - 4 && var15 <= var4 + 1) {
                           var13 = Blocks.aV.Q();
                           var14 = Blocks.aV.Q();
                           if (var10) {
                              var13 = Blocks.n.Q();
                              var14 = Blocks.aV.Q();
                           }

                           if (var9) {
                              var13 = Blocks.aW.Q();
                              var14 = Blocks.aW.Q();
                           }
                        }

                        if (var15 < var4 && (var13 == null || var13.c().t() == Material.a)) {
                           var13 = Blocks.l.Q();
                        }

                        var12 = var11;
                        if (var15 >= var4 - 1) {
                           var3.a(var8, var15, var7, var13);
                        } else {
                           var3.a(var8, var15, var7, var14);
                        }
                     } else if (var12 > 0) {
                        var12--;
                        var3.a(var8, var15, var7, var14);
                     }
                  }
               } else {
                  var3.a(var8, var15, var7, Blocks.h.Q());
               }
            }
         }
      }
   }

   @Override
   public Chunk d(int var1, int var2) {
      this.j.setSeed(var1 * 341873128712L + var2 * 132897987541L);
      ChunkPrimer var3 = new ChunkPrimer();
      this.a(var1, var2, var3);
      this.b(var1, var2, var3);
      this.C.a(this, this.h, var1, var2, var3);
      if (this.i) {
         this.B.a(this, this.h, var1, var2, var3);
      }

      Chunk var4 = new Chunk(this.h, var3, var1, var2);
      BiomeGenBase[] var5 = this.h.v().b(null, var1 * 16, var2 * 16, 16, 16);
      byte[] var6 = var4.k();

      for (int var7 = 0; var7 < var6.length; var7++) {
         var6[var7] = (byte)var5[var7].az;
      }

      var4.l();
      return var4;
   }

   private double[] a(double[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var1 == null) {
         var1 = new double[var5 * var6 * var7];
      }

      double var8 = 684.412;
      double var10 = 2053.236;
      this.f = this.a.a(this.f, var2, var3, var4, var5, 1, var7, 1.0, 0.0, 1.0);
      this.g = this.b.a(this.g, var2, var3, var4, var5, 1, var7, 100.0, 0.0, 100.0);
      this.c = this.q.a(this.c, var2, var3, var4, var5, var6, var7, var8 / 80.0, var10 / 60.0, var8 / 80.0);
      this.d = this.o.a(this.d, var2, var3, var4, var5, var6, var7, var8, var10, var8);
      this.e = this.p.a(this.e, var2, var3, var4, var5, var6, var7, var8, var10, var8);
      int var12 = 0;
      double[] var13 = new double[var6];

      for (int var14 = 0; var14 < var6; var14++) {
         var13[var14] = Math.cos(var14 * Math.PI * 6.0 / var6) * 2.0;
         double var15 = var14;
         if (var14 > var6 / 2) {
            var15 = var6 - 1 - var14;
         }

         if (var15 < 4.0) {
            var15 = 4.0 - var15;
            var13[var14] -= var15 * var15 * var15 * 10.0;
         }
      }

      for (int var31 = 0; var31 < var5; var31++) {
         for (int var33 = 0; var33 < var7; var33++) {
            double var16 = 0.0;

            for (int var18 = 0; var18 < var6; var18++) {
               double var19 = 0.0;
               double var21 = var13[var18];
               double var23 = this.d[var12] / 512.0;
               double var25 = this.e[var12] / 512.0;
               double var27 = (this.c[var12] / 10.0 + 1.0) / 2.0;
               if (var27 < 0.0) {
                  var19 = var23;
               } else if (var27 > 1.0) {
                  var19 = var25;
               } else {
                  var19 = var23 + (var25 - var23) * var27;
               }

               var19 -= var21;
               if (var18 > var6 - 4) {
                  double var29 = (var18 - (var6 - 4)) / 3.0F;
                  var19 = var19 * (1.0 - var29) + -10.0 * var29;
               }

               if (var18 < var16) {
                  double var36 = (var16 - var18) / 4.0;
                  var36 = MathHelper.a(var36, 0.0, 1.0);
                  var19 = var19 * (1.0 - var36) + -10.0 * var36;
               }

               var1[var12] = var19;
               var12++;
            }
         }
      }

      return var1;
   }

   @Override
   public boolean a(int var1, int var2) {
      return true;
   }

   @Override
   public void a(IChunkProvider var1, int var2, int var3) {
      BlockFalling.N = true;
      BlockPos var4 = new BlockPos(var2 * 16, 0, var3 * 16);
      ChunkCoordIntPair var5 = new ChunkCoordIntPair(var2, var3);
      this.B.a(this.h, this.j, var5);

      for (int var6 = 0; var6 < 8; var6++) {
         this.y.b(this.h, this.j, var4.a(this.j.nextInt(16) + 8, this.j.nextInt(120) + 4, this.j.nextInt(16) + 8));
      }

      for (int var7 = 0; var7 < this.j.nextInt(this.j.nextInt(10) + 1) + 1; var7++) {
         this.t.b(this.h, this.j, var4.a(this.j.nextInt(16) + 8, this.j.nextInt(120) + 4, this.j.nextInt(16) + 8));
      }

      for (int var8 = 0; var8 < this.j.nextInt(this.j.nextInt(10) + 1); var8++) {
         this.u.b(this.h, this.j, var4.a(this.j.nextInt(16) + 8, this.j.nextInt(120) + 4, this.j.nextInt(16) + 8));
      }

      for (int var9 = 0; var9 < 10; var9++) {
         this.v.b(this.h, this.j, var4.a(this.j.nextInt(16) + 8, this.j.nextInt(128), this.j.nextInt(16) + 8));
      }

      if (this.j.nextBoolean()) {
         this.z.b(this.h, this.j, var4.a(this.j.nextInt(16) + 8, this.j.nextInt(128), this.j.nextInt(16) + 8));
      }

      if (this.j.nextBoolean()) {
         this.A.b(this.h, this.j, var4.a(this.j.nextInt(16) + 8, this.j.nextInt(128), this.j.nextInt(16) + 8));
      }

      for (int var10 = 0; var10 < 16; var10++) {
         this.w.b(this.h, this.j, var4.a(this.j.nextInt(16), this.j.nextInt(108) + 10, this.j.nextInt(16)));
      }

      for (int var11 = 0; var11 < 16; var11++) {
         this.x.b(this.h, this.j, var4.a(this.j.nextInt(16), this.j.nextInt(108) + 10, this.j.nextInt(16)));
      }

      BlockFalling.N = false;
   }

   @Override
   public boolean a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      return false;
   }

   @Override
   public boolean a(boolean var1, IProgressUpdate var2) {
      return true;
   }

   @Override
   public void c() {
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean e() {
      return true;
   }

   @Override
   public String f() {
      return "HellRandomLevelSource";
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> a(EnumCreatureType var1, BlockPos var2) {
      if (var1 == EnumCreatureType.a) {
         if (this.B.b(var2)) {
            return this.B.b();
         }

         if (this.B.a(this.h, var2) && this.h.p(var2.b()).c() == Blocks.by) {
            return this.B.b();
         }
      }

      BiomeGenBase var3 = this.h.b(var2);
      return var3.a(var1);
   }

   @Override
   public BlockPos a(World var1, String var2, BlockPos var3) {
      return null;
   }

   @Override
   public int g() {
      return 0;
   }

   @Override
   public void a(Chunk var1, int var2, int var3) {
      this.B.a(this, this.h, var2, var3, null);
   }

   @Override
   public Chunk a(BlockPos var1) {
      return this.d(var1.n() >> 4, var1.p() >> 4);
   }
}
