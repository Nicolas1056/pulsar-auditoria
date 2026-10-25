package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.GeneratorBushFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecorator {
   protected World a;
   protected Random b;
   protected BlockPos c;
   protected ChunkProviderSettings d;
   protected WorldGenerator e = new WorldGenClay(4);
   protected WorldGenerator f = new WorldGenSand(Blocks.m, 7);
   protected WorldGenerator g = new WorldGenSand(Blocks.n, 6);
   protected WorldGenerator h;
   protected WorldGenerator i;
   protected WorldGenerator j;
   protected WorldGenerator k;
   protected WorldGenerator l;
   protected WorldGenerator m;
   protected WorldGenerator n;
   protected WorldGenerator o;
   protected WorldGenerator p;
   protected WorldGenerator q;
   protected WorldGenerator r;
   protected WorldGenFlowers s = new WorldGenFlowers(Blocks.N, BlockFlower.EnumFlowerType.a);
   protected WorldGenerator t = new GeneratorBushFeature(Blocks.P);
   protected WorldGenerator u = new GeneratorBushFeature(Blocks.Q);
   protected WorldGenerator v = new WorldGenBigMushroom();
   protected WorldGenerator w = new WorldGenReed();
   protected WorldGenerator x = new WorldGenCactus();
   protected WorldGenerator y = new WorldGenWaterlily();
   protected int z;
   protected int A;
   protected int B = 2;
   protected int C = 1;
   protected int D;
   protected int E;
   protected int F;
   protected int G;
   protected int H = 1;
   protected int I = 3;
   protected int J = 1;
   protected int K;
   public boolean L = true;

   public void a(World var1, Random var2, BiomeGenBase var3, BlockPos var4) {
      if (this.a != null) {
         throw new RuntimeException("Already decorating");
      } else {
         this.a = var1;
         String var5 = var1.P().B();
         if (var5 != null) {
            this.d = ChunkProviderSettings.Factory.a(var5).b();
         } else {
            this.d = ChunkProviderSettings.Factory.a("").b();
         }

         this.b = var2;
         this.c = var4;
         this.h = new WorldGenMinable(Blocks.d.Q(), this.d.I);
         this.i = new WorldGenMinable(Blocks.n.Q(), this.d.M);
         this.j = new WorldGenMinable(Blocks.b.Q().a(BlockStone.a, BlockStone.EnumType.b), this.d.Q);
         this.k = new WorldGenMinable(Blocks.b.Q().a(BlockStone.a, BlockStone.EnumType.d), this.d.U);
         this.l = new WorldGenMinable(Blocks.b.Q().a(BlockStone.a, BlockStone.EnumType.f), this.d.Y);
         this.m = new WorldGenMinable(Blocks.q.Q(), this.d.ac);
         this.n = new WorldGenMinable(Blocks.p.Q(), this.d.ag);
         this.o = new WorldGenMinable(Blocks.o.Q(), this.d.ak);
         this.p = new WorldGenMinable(Blocks.aC.Q(), this.d.ao);
         this.q = new WorldGenMinable(Blocks.ag.Q(), this.d.as);
         this.r = new WorldGenMinable(Blocks.x.Q(), this.d.aw);
         this.a(var3);
         this.a = null;
         this.b = null;
      }
   }

   protected void a(BiomeGenBase var1) {
      this.a();

      for (int var2 = 0; var2 < this.I; var2++) {
         int var3 = this.b.nextInt(16) + 8;
         int var4 = this.b.nextInt(16) + 8;
         this.f.b(this.a, this.b, this.a.r(this.c.a(var3, 0, var4)));
      }

      for (int var11 = 0; var11 < this.J; var11++) {
         int var14 = this.b.nextInt(16) + 8;
         int var31 = this.b.nextInt(16) + 8;
         this.e.b(this.a, this.b, this.a.r(this.c.a(var14, 0, var31)));
      }

      for (int var12 = 0; var12 < this.H; var12++) {
         int var15 = this.b.nextInt(16) + 8;
         int var32 = this.b.nextInt(16) + 8;
         this.g.b(this.a, this.b, this.a.r(this.c.a(var15, 0, var32)));
      }

      int var13 = this.A;
      if (this.b.nextInt(10) == 0) {
         var13++;
      }

      for (int var16 = 0; var16 < var13; var16++) {
         int var33 = this.b.nextInt(16) + 8;
         int var5 = this.b.nextInt(16) + 8;
         WorldGenAbstractTree var6 = var1.a(this.b);
         var6.e();
         BlockPos var7 = this.a.m(this.c.a(var33, 0, var5));
         if (var6.b(this.a, this.b, var7)) {
            var6.a(this.a, this.b, var7);
         }
      }

      for (int var17 = 0; var17 < this.K; var17++) {
         int var34 = this.b.nextInt(16) + 8;
         int var49 = this.b.nextInt(16) + 8;
         this.v.b(this.a, this.b, this.a.m(this.c.a(var34, 0, var49)));
      }

      for (int var18 = 0; var18 < this.B; var18++) {
         int var35 = this.b.nextInt(16) + 8;
         int var50 = this.b.nextInt(16) + 8;
         int var64 = this.a.m(this.c.a(var35, 0, var50)).o() + 32;
         if (var64 > 0) {
            int var78 = this.b.nextInt(var64);
            BlockPos var8 = this.c.a(var35, var78, var50);
            BlockFlower.EnumFlowerType var9 = var1.a(this.b, var8);
            BlockFlower var10 = var9.a().a();
            if (var10.t() != Material.a) {
               this.s.a(var10, var9);
               this.s.b(this.a, this.b, var8);
            }
         }
      }

      for (int var19 = 0; var19 < this.C; var19++) {
         int var36 = this.b.nextInt(16) + 8;
         int var51 = this.b.nextInt(16) + 8;
         int var65 = this.a.m(this.c.a(var36, 0, var51)).o() * 2;
         if (var65 > 0) {
            int var79 = this.b.nextInt(var65);
            var1.b(this.b).b(this.a, this.b, this.c.a(var36, var79, var51));
         }
      }

      for (int var20 = 0; var20 < this.D; var20++) {
         int var37 = this.b.nextInt(16) + 8;
         int var52 = this.b.nextInt(16) + 8;
         int var66 = this.a.m(this.c.a(var37, 0, var52)).o() * 2;
         if (var66 > 0) {
            int var80 = this.b.nextInt(var66);
            new WorldGenDeadBush().b(this.a, this.b, this.c.a(var37, var80, var52));
         }
      }

      for (int var21 = 0; var21 < this.z; var21++) {
         int var38 = this.b.nextInt(16) + 8;
         int var53 = this.b.nextInt(16) + 8;
         int var67 = this.a.m(this.c.a(var38, 0, var53)).o() * 2;
         if (var67 > 0) {
            int var81 = this.b.nextInt(var67);
            BlockPos var88 = this.c.a(var38, var81, var53);

            while (var88.o() > 0) {
               BlockPos var91 = var88.b();
               if (!this.a.d(var91)) {
                  break;
               }

               var88 = var91;
            }

            this.y.b(this.a, this.b, var88);
         }
      }

      for (int var22 = 0; var22 < this.E; var22++) {
         if (this.b.nextInt(4) == 0) {
            int var39 = this.b.nextInt(16) + 8;
            int var54 = this.b.nextInt(16) + 8;
            BlockPos var68 = this.a.m(this.c.a(var39, 0, var54));
            this.t.b(this.a, this.b, var68);
         }

         if (this.b.nextInt(8) == 0) {
            int var40 = this.b.nextInt(16) + 8;
            int var55 = this.b.nextInt(16) + 8;
            int var69 = this.a.m(this.c.a(var40, 0, var55)).o() * 2;
            if (var69 > 0) {
               int var82 = this.b.nextInt(var69);
               BlockPos var89 = this.c.a(var40, var82, var55);
               this.u.b(this.a, this.b, var89);
            }
         }
      }

      if (this.b.nextInt(4) == 0) {
         int var23 = this.b.nextInt(16) + 8;
         int var41 = this.b.nextInt(16) + 8;
         int var56 = this.a.m(this.c.a(var23, 0, var41)).o() * 2;
         if (var56 > 0) {
            int var70 = this.b.nextInt(var56);
            this.t.b(this.a, this.b, this.c.a(var23, var70, var41));
         }
      }

      if (this.b.nextInt(8) == 0) {
         int var24 = this.b.nextInt(16) + 8;
         int var42 = this.b.nextInt(16) + 8;
         int var57 = this.a.m(this.c.a(var24, 0, var42)).o() * 2;
         if (var57 > 0) {
            int var71 = this.b.nextInt(var57);
            this.u.b(this.a, this.b, this.c.a(var24, var71, var42));
         }
      }

      for (int var25 = 0; var25 < this.F; var25++) {
         int var43 = this.b.nextInt(16) + 8;
         int var58 = this.b.nextInt(16) + 8;
         int var72 = this.a.m(this.c.a(var43, 0, var58)).o() * 2;
         if (var72 > 0) {
            int var83 = this.b.nextInt(var72);
            this.w.b(this.a, this.b, this.c.a(var43, var83, var58));
         }
      }

      for (int var26 = 0; var26 < 10; var26++) {
         int var44 = this.b.nextInt(16) + 8;
         int var59 = this.b.nextInt(16) + 8;
         int var73 = this.a.m(this.c.a(var44, 0, var59)).o() * 2;
         if (var73 > 0) {
            int var84 = this.b.nextInt(var73);
            this.w.b(this.a, this.b, this.c.a(var44, var84, var59));
         }
      }

      if (this.b.nextInt(32) == 0) {
         int var27 = this.b.nextInt(16) + 8;
         int var45 = this.b.nextInt(16) + 8;
         int var60 = this.a.m(this.c.a(var27, 0, var45)).o() * 2;
         if (var60 > 0) {
            int var74 = this.b.nextInt(var60);
            new WorldGenPumpkin().b(this.a, this.b, this.c.a(var27, var74, var45));
         }
      }

      for (int var28 = 0; var28 < this.G; var28++) {
         int var46 = this.b.nextInt(16) + 8;
         int var61 = this.b.nextInt(16) + 8;
         int var75 = this.a.m(this.c.a(var46, 0, var61)).o() * 2;
         if (var75 > 0) {
            int var85 = this.b.nextInt(var75);
            this.x.b(this.a, this.b, this.c.a(var46, var85, var61));
         }
      }

      if (this.L) {
         for (int var29 = 0; var29 < 50; var29++) {
            int var47 = this.b.nextInt(16) + 8;
            int var62 = this.b.nextInt(16) + 8;
            int var76 = this.b.nextInt(248) + 8;
            if (var76 > 0) {
               int var86 = this.b.nextInt(var76);
               BlockPos var90 = this.c.a(var47, var86, var62);
               new WorldGenLiquids(Blocks.i).b(this.a, this.b, var90);
            }
         }

         for (int var30 = 0; var30 < 20; var30++) {
            int var48 = this.b.nextInt(16) + 8;
            int var63 = this.b.nextInt(16) + 8;
            int var77 = this.b.nextInt(this.b.nextInt(this.b.nextInt(240) + 8) + 8);
            BlockPos var87 = this.c.a(var48, var77, var63);
            new WorldGenLiquids(Blocks.k).b(this.a, this.b, var87);
         }
      }
   }

   protected void a(int var1, WorldGenerator var2, int var3, int var4) {
      if (var4 < var3) {
         int var5 = var3;
         var3 = var4;
         var4 = var5;
      } else if (var4 == var3) {
         if (var3 < 255) {
            var4++;
         } else {
            var3--;
         }
      }

      for (int var7 = 0; var7 < var1; var7++) {
         BlockPos var6 = this.c.a(this.b.nextInt(16), this.b.nextInt(var4 - var3) + var3, this.b.nextInt(16));
         var2.b(this.a, this.b, var6);
      }
   }

   protected void b(int var1, WorldGenerator var2, int var3, int var4) {
      for (int var5 = 0; var5 < var1; var5++) {
         BlockPos var6 = this.c.a(this.b.nextInt(16), this.b.nextInt(var4) + this.b.nextInt(var4) + var3 - var4, this.b.nextInt(16));
         var2.b(this.a, this.b, var6);
      }
   }

   protected void a() {
      this.a(this.d.J, this.h, this.d.K, this.d.L);
      this.a(this.d.N, this.i, this.d.O, this.d.P);
      this.a(this.d.V, this.k, this.d.W, this.d.X);
      this.a(this.d.R, this.j, this.d.S, this.d.T);
      this.a(this.d.Z, this.l, this.d.aa, this.d.ab);
      this.a(this.d.ad, this.m, this.d.ae, this.d.af);
      this.a(this.d.ah, this.n, this.d.ai, this.d.aj);
      this.a(this.d.al, this.o, this.d.am, this.d.an);
      this.a(this.d.ap, this.p, this.d.aq, this.d.ar);
      this.a(this.d.at, this.q, this.d.au, this.d.av);
      this.b(this.d.ax, this.r, this.d.ay, this.d.az);
   }
}
