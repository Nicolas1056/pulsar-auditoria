package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

public class ChunkProviderGenerate implements IChunkProvider {
   private Random h;
   private NoiseGeneratorOctaves i;
   private NoiseGeneratorOctaves j;
   private NoiseGeneratorOctaves k;
   private NoiseGeneratorPerlin l;
   public NoiseGeneratorOctaves a;
   public NoiseGeneratorOctaves b;
   public NoiseGeneratorOctaves c;
   private World m;
   private final boolean n;
   private WorldType o;
   private final double[] p;
   private final float[] q;
   private ChunkProviderSettings r;
   private Block s = Blocks.j;
   private double[] t = new double[256];
   private MapGenBase u = new MapGenCaves();
   private MapGenStronghold v = new MapGenStronghold();
   private MapGenVillage w = new MapGenVillage();
   private MapGenMineshaft x = new MapGenMineshaft();
   private MapGenScatteredFeature y = new MapGenScatteredFeature();
   private MapGenBase z = new MapGenRavine();
   private StructureOceanMonument A = new StructureOceanMonument();
   private BiomeGenBase[] B;
   double[] d;
   double[] e;
   double[] f;
   double[] g;

   public ChunkProviderGenerate(World var1, long var2, boolean var4, String var5) {
      this.m = var1;
      this.n = var4;
      this.o = var1.P().u();
      this.h = new Random(var2);
      this.i = new NoiseGeneratorOctaves(this.h, 16);
      this.j = new NoiseGeneratorOctaves(this.h, 16);
      this.k = new NoiseGeneratorOctaves(this.h, 8);
      this.l = new NoiseGeneratorPerlin(this.h, 4);
      this.a = new NoiseGeneratorOctaves(this.h, 10);
      this.b = new NoiseGeneratorOctaves(this.h, 16);
      this.c = new NoiseGeneratorOctaves(this.h, 8);
      this.p = new double[825];
      this.q = new float[25];

      for (int var6 = -2; var6 <= 2; var6++) {
         for (int var7 = -2; var7 <= 2; var7++) {
            float var8 = 10.0F / MathHelper.c(var6 * var6 + var7 * var7 + 0.2F);
            this.q[var6 + 2 + (var7 + 2) * 5] = var8;
         }
      }

      if (var5 != null) {
         this.r = ChunkProviderSettings.Factory.a(var5).b();
         this.s = this.r.E ? Blocks.l : Blocks.j;
         var1.b(this.r.q);
      }
   }

   public void a(int var1, int var2, ChunkPrimer var3) {
      this.B = this.m.v().a(this.B, var1 * 4 - 2, var2 * 4 - 2, 10, 10);
      this.a(var1 * 4, 0, var2 * 4);

      for (int var4 = 0; var4 < 4; var4++) {
         int var5 = var4 * 5;
         int var6 = (var4 + 1) * 5;

         for (int var7 = 0; var7 < 4; var7++) {
            int var8 = (var5 + var7) * 33;
            int var9 = (var5 + var7 + 1) * 33;
            int var10 = (var6 + var7) * 33;
            int var11 = (var6 + var7 + 1) * 33;

            for (int var12 = 0; var12 < 32; var12++) {
               double var13 = 0.125;
               double var15 = this.p[var8 + var12];
               double var17 = this.p[var9 + var12];
               double var19 = this.p[var10 + var12];
               double var21 = this.p[var11 + var12];
               double var23 = (this.p[var8 + var12 + 1] - var15) * var13;
               double var25 = (this.p[var9 + var12 + 1] - var17) * var13;
               double var27 = (this.p[var10 + var12 + 1] - var19) * var13;
               double var29 = (this.p[var11 + var12 + 1] - var21) * var13;

               for (int var31 = 0; var31 < 8; var31++) {
                  double var32 = 0.25;
                  double var34 = var15;
                  double var36 = var17;
                  double var38 = (var19 - var15) * var32;
                  double var40 = (var21 - var17) * var32;

                  for (int var42 = 0; var42 < 4; var42++) {
                     double var43 = 0.25;
                     double var47 = (var36 - var34) * var43;
                     double var45 = var34 - var47;

                     for (int var49 = 0; var49 < 4; var49++) {
                        if ((var45 += var47) > 0.0) {
                           var3.a(var4 * 4 + var42, var12 * 8 + var31, var7 * 4 + var49, Blocks.b.Q());
                        } else if (var12 * 8 + var31 < this.r.q) {
                           var3.a(var4 * 4 + var42, var12 * 8 + var31, var7 * 4 + var49, this.s.Q());
                        }
                     }

                     var34 += var38;
                     var36 += var40;
                  }

                  var15 += var23;
                  var17 += var25;
                  var19 += var27;
                  var21 += var29;
               }
            }
         }
      }
   }

   public void a(int var1, int var2, ChunkPrimer var3, BiomeGenBase[] var4) {
      double var5 = 0.03125;
      this.t = this.l.a(this.t, var1 * 16, var2 * 16, 16, 16, var5 * 2.0, var5 * 2.0, 1.0);

      for (int var7 = 0; var7 < 16; var7++) {
         for (int var8 = 0; var8 < 16; var8++) {
            BiomeGenBase var9 = var4[var8 + var7 * 16];
            var9.a(this.m, this.h, var3, var1 * 16 + var7, var2 * 16 + var8, this.t[var8 + var7 * 16]);
         }
      }
   }

   @Override
   public Chunk d(int var1, int var2) {
      this.h.setSeed(var1 * 341873128712L + var2 * 132897987541L);
      ChunkPrimer var3 = new ChunkPrimer();
      this.a(var1, var2, var3);
      this.B = this.m.v().b(this.B, var1 * 16, var2 * 16, 16, 16);
      this.a(var1, var2, var3, this.B);
      if (this.r.r) {
         this.u.a(this, this.m, var1, var2, var3);
      }

      if (this.r.z) {
         this.z.a(this, this.m, var1, var2, var3);
      }

      if (this.r.w && this.n) {
         this.x.a(this, this.m, var1, var2, var3);
      }

      if (this.r.v && this.n) {
         this.w.a(this, this.m, var1, var2, var3);
      }

      if (this.r.u && this.n) {
         this.v.a(this, this.m, var1, var2, var3);
      }

      if (this.r.x && this.n) {
         this.y.a(this, this.m, var1, var2, var3);
      }

      if (this.r.y && this.n) {
         this.A.a(this, this.m, var1, var2, var3);
      }

      Chunk var4 = new Chunk(this.m, var3, var1, var2);
      byte[] var5 = var4.k();

      for (int var6 = 0; var6 < var5.length; var6++) {
         var5[var6] = (byte)this.B[var6].az;
      }

      var4.b();
      return var4;
   }

   private void a(int var1, int var2, int var3) {
      this.g = this.b.a(this.g, var1, var3, 5, 5, this.r.e, this.r.f, this.r.g);
      float var4 = this.r.a;
      float var5 = this.r.b;
      this.d = this.k.a(this.d, var1, var2, var3, 5, 33, 5, var4 / this.r.h, var5 / this.r.i, var4 / this.r.j);
      this.e = this.i.a(this.e, var1, var2, var3, 5, 33, 5, var4, var5, var4);
      this.f = this.j.a(this.f, var1, var2, var3, 5, 33, 5, var4, var5, var4);
      boolean var37 = false;
      boolean var36 = false;
      int var6 = 0;
      int var7 = 0;

      for (int var8 = 0; var8 < 5; var8++) {
         for (int var9 = 0; var9 < 5; var9++) {
            float var10 = 0.0F;
            float var11 = 0.0F;
            float var12 = 0.0F;
            byte var13 = 2;
            BiomeGenBase var14 = this.B[var8 + 2 + (var9 + 2) * 10];

            for (int var15 = -var13; var15 <= var13; var15++) {
               for (int var16 = -var13; var16 <= var13; var16++) {
                  BiomeGenBase var17 = this.B[var8 + var15 + 2 + (var9 + var16 + 2) * 10];
                  float var18 = this.r.n + var17.an * this.r.m;
                  float var19 = this.r.p + var17.ao * this.r.o;
                  if (this.o == WorldType.e && var18 > 0.0F) {
                     var18 = 1.0F + var18 * 2.0F;
                     var19 = 1.0F + var19 * 4.0F;
                  }

                  float var20 = this.q[var15 + 2 + (var16 + 2) * 5] / (var18 + 2.0F);
                  if (var17.an > var14.an) {
                     var20 /= 2.0F;
                  }

                  var10 += var19 * var20;
                  var11 += var18 * var20;
                  var12 += var20;
               }
            }

            var10 /= var12;
            var11 /= var12;
            var10 = var10 * 0.9F + 0.1F;
            var11 = (var11 * 4.0F - 1.0F) / 8.0F;
            double var42 = this.g[var7] / 8000.0;
            if (var42 < 0.0) {
               var42 = -var42 * 0.3;
            }

            var42 = var42 * 3.0 - 2.0;
            if (var42 < 0.0) {
               var42 /= 2.0;
               if (var42 < -1.0) {
                  var42 = -1.0;
               }

               var42 /= 1.4;
               var42 /= 2.0;
            } else {
               if (var42 > 1.0) {
                  var42 = 1.0;
               }

               var42 /= 8.0;
            }

            var7++;
            double var47 = var11;
            double var50 = var10;
            var47 += var42 * 0.2;
            var47 = var47 * this.r.k / 8.0;
            double var21 = this.r.k + var47 * 4.0;

            for (int var23 = 0; var23 < 33; var23++) {
               double var24 = (var23 - var21) * this.r.l * 128.0 / 256.0 / var50;
               if (var24 < 0.0) {
                  var24 *= 4.0;
               }

               double var26 = this.e[var6] / this.r.d;
               double var28 = this.f[var6] / this.r.c;
               double var30 = (this.d[var6] / 10.0 + 1.0) / 2.0;
               double var32 = MathHelper.b(var26, var28, var30) - var24;
               if (var23 > 29) {
                  double var34 = (var23 - 29) / 3.0F;
                  var32 = var32 * (1.0 - var34) + -10.0 * var34;
               }

               this.p[var6] = var32;
               var6++;
            }
         }
      }
   }

   @Override
   public boolean a(int var1, int var2) {
      return true;
   }

   @Override
   public void a(IChunkProvider var1, int var2, int var3) {
      BlockFalling.N = true;
      int var4 = var2 * 16;
      int var5 = var3 * 16;
      BlockPos var6 = new BlockPos(var4, 0, var5);
      BiomeGenBase var7 = this.m.b(var6.a(16, 0, 16));
      this.h.setSeed(this.m.J());
      long var8 = this.h.nextLong() / 2L * 2L + 1L;
      long var10 = this.h.nextLong() / 2L * 2L + 1L;
      this.h.setSeed(var2 * var8 + var3 * var10 ^ this.m.J());
      boolean var12 = false;
      ChunkCoordIntPair var13 = new ChunkCoordIntPair(var2, var3);
      if (this.r.w && this.n) {
         this.x.a(this.m, this.h, var13);
      }

      if (this.r.v && this.n) {
         var12 = this.w.a(this.m, this.h, var13);
      }

      if (this.r.u && this.n) {
         this.v.a(this.m, this.h, var13);
      }

      if (this.r.x && this.n) {
         this.y.a(this.m, this.h, var13);
      }

      if (this.r.y && this.n) {
         this.A.a(this.m, this.h, var13);
      }

      if (var7 != BiomeGenBase.r && var7 != BiomeGenBase.G && this.r.A && !var12 && this.h.nextInt(this.r.B) == 0) {
         int var14 = this.h.nextInt(16) + 8;
         int var15 = this.h.nextInt(256);
         int var16 = this.h.nextInt(16) + 8;
         new WorldGenLakes(Blocks.j).b(this.m, this.h, var6.a(var14, var15, var16));
      }

      if (!var12 && this.h.nextInt(this.r.D / 10) == 0 && this.r.C) {
         int var19 = this.h.nextInt(16) + 8;
         int var22 = this.h.nextInt(this.h.nextInt(248) + 8);
         int var25 = this.h.nextInt(16) + 8;
         if (var22 < this.m.F() || this.h.nextInt(this.r.D / 8) == 0) {
            new WorldGenLakes(Blocks.l).b(this.m, this.h, var6.a(var19, var22, var25));
         }
      }

      if (this.r.s) {
         for (int var20 = 0; var20 < this.r.t; var20++) {
            int var23 = this.h.nextInt(16) + 8;
            int var26 = this.h.nextInt(256);
            int var17 = this.h.nextInt(16) + 8;
            new WorldGenDungeons().b(this.m, this.h, var6.a(var23, var26, var17));
         }
      }

      var7.a(this.m, this.h, new BlockPos(var4, 0, var5));
      SpawnerAnimals.a(this.m, var7, var4 + 8, var5 + 8, 16, 16, this.h);
      var6 = var6.a(8, 0, 8);

      for (int var21 = 0; var21 < 16; var21++) {
         for (int var24 = 0; var24 < 16; var24++) {
            BlockPos var27 = this.m.q(var6.a(var21, 0, var24));
            BlockPos var28 = var27.b();
            if (this.m.v(var28)) {
               this.m.a(var28, Blocks.aI.Q(), 2);
            }

            if (this.m.f(var27, true)) {
               this.m.a(var27, Blocks.aH.Q(), 2);
            }
         }
      }

      BlockFalling.N = false;
   }

   @Override
   public boolean a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      boolean var5 = false;
      if (this.r.y && this.n && var2.w() < 3600L) {
         var5 |= this.A.a(this.m, this.h, new ChunkCoordIntPair(var3, var4));
      }

      return var5;
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
      return "RandomLevelSource";
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> a(EnumCreatureType var1, BlockPos var2) {
      BiomeGenBase var3 = this.m.b(var2);
      if (this.n) {
         if (var1 == EnumCreatureType.a && this.y.a(var2)) {
            return this.y.b();
         }

         if (var1 == EnumCreatureType.a && this.r.y && this.A.a(this.m, var2)) {
            return this.A.b();
         }
      }

      return var3.a(var1);
   }

   @Override
   public BlockPos a(World var1, String var2, BlockPos var3) {
      return "Stronghold".equals(var2) && this.v != null ? this.v.b(var1, var3) : null;
   }

   @Override
   public int g() {
      return 0;
   }

   @Override
   public void a(Chunk var1, int var2, int var3) {
      if (this.r.w && this.n) {
         this.x.a(this, this.m, var2, var3, null);
      }

      if (this.r.v && this.n) {
         this.w.a(this, this.m, var2, var3, null);
      }

      if (this.r.u && this.n) {
         this.v.a(this, this.m, var2, var3, null);
      }

      if (this.r.x && this.n) {
         this.y.a(this, this.m, var2, var3, null);
      }

      if (this.r.y && this.n) {
         this.A.a(this, this.m, var2, var3, null);
      }
   }

   @Override
   public Chunk a(BlockPos var1) {
      return this.d(var1.n() >> 4, var1.p() >> 4);
   }
}
