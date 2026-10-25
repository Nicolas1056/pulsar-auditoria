package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class BlockFire extends Block {
   public static final PropertyInteger a = PropertyInteger.a("age", 0, 15);
   public static final PropertyBool b = PropertyBool.a("flip");
   public static final PropertyBool N = PropertyBool.a("alt");
   public static final PropertyBool O = PropertyBool.a("north");
   public static final PropertyBool P = PropertyBool.a("east");
   public static final PropertyBool Q = PropertyBool.a("south");
   public static final PropertyBool R = PropertyBool.a("west");
   public static final PropertyInteger S = PropertyInteger.a("upper", 0, 2);
   private final Map<Block, Integer> T = Maps.newIdentityHashMap();
   private final Map<Block, Integer> U = Maps.newIdentityHashMap();

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      int var4 = var3.n();
      int var5 = var3.o();
      int var6 = var3.p();
      if (!World.a(var2, var3.b()) && !Blocks.ab.e(var2, var3.b())) {
         boolean var7 = (var4 + var5 + var6 & 1) == 1;
         boolean var8 = (var4 / 2 + var5 / 2 + var6 / 2 & 1) == 1;
         int var9 = 0;
         if (this.e(var2, var3.a())) {
            var9 = var7 ? 1 : 2;
         }

         return var1.a(O, this.e(var2, var3.c()))
            .a(P, this.e(var2, var3.f()))
            .a(Q, this.e(var2, var3.d()))
            .a(R, this.e(var2, var3.e()))
            .a(S, var9)
            .a(b, var8)
            .a(N, var7);
      } else {
         return this.Q();
      }
   }

   protected BlockFire() {
      super(Material.o);
      this.j(this.M.b().a(a, 0).a(b, false).a(N, false).a(O, false).a(P, false).a(Q, false).a(R, false).a(S, 0));
      this.a(true);
   }

   public static void l() {
      Blocks.ab.a(Blocks.f, 5, 20);
      Blocks.ab.a(Blocks.bL, 5, 20);
      Blocks.ab.a(Blocks.bM, 5, 20);
      Blocks.ab.a(Blocks.bo, 5, 20);
      Blocks.ab.a(Blocks.bp, 5, 20);
      Blocks.ab.a(Blocks.bq, 5, 20);
      Blocks.ab.a(Blocks.br, 5, 20);
      Blocks.ab.a(Blocks.bs, 5, 20);
      Blocks.ab.a(Blocks.bt, 5, 20);
      Blocks.ab.a(Blocks.aO, 5, 20);
      Blocks.ab.a(Blocks.aP, 5, 20);
      Blocks.ab.a(Blocks.aQ, 5, 20);
      Blocks.ab.a(Blocks.aR, 5, 20);
      Blocks.ab.a(Blocks.aS, 5, 20);
      Blocks.ab.a(Blocks.aT, 5, 20);
      Blocks.ab.a(Blocks.ad, 5, 20);
      Blocks.ab.a(Blocks.bV, 5, 20);
      Blocks.ab.a(Blocks.bU, 5, 20);
      Blocks.ab.a(Blocks.bW, 5, 20);
      Blocks.ab.a(Blocks.r, 5, 5);
      Blocks.ab.a(Blocks.s, 5, 5);
      Blocks.ab.a(Blocks.t, 30, 60);
      Blocks.ab.a(Blocks.u, 30, 60);
      Blocks.ab.a(Blocks.X, 30, 20);
      Blocks.ab.a(Blocks.W, 15, 100);
      Blocks.ab.a(Blocks.H, 60, 100);
      Blocks.ab.a(Blocks.cF, 60, 100);
      Blocks.ab.a(Blocks.N, 60, 100);
      Blocks.ab.a(Blocks.O, 60, 100);
      Blocks.ab.a(Blocks.I, 60, 100);
      Blocks.ab.a(Blocks.L, 30, 60);
      Blocks.ab.a(Blocks.bn, 15, 100);
      Blocks.ab.a(Blocks.cA, 5, 5);
      Blocks.ab.a(Blocks.cx, 60, 20);
      Blocks.ab.a(Blocks.cy, 60, 20);
   }

   public void a(Block var1, int var2, int var3) {
      this.T.put(var1, var2);
      this.U.put(var1, var3);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public int a(World var1) {
      return 30;
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var1.Q().b("doFireTick")) {
         if (!this.d(var1, var2)) {
            var1.g(var2);
         }

         Block var5 = var1.p(var2.b()).c();
         boolean var6 = var5 == Blocks.aV;
         if (var1.t instanceof WorldProviderEnd && var5 == Blocks.h) {
            var6 = true;
         }

         if (!var6 && var1.S() && this.e(var1, var2)) {
            var1.g(var2);
         } else {
            int var7 = var3.b(a);
            if (var7 < 15) {
               var3 = var3.a(a, var7 + var4.nextInt(3) / 2);
               var1.a(var2, var3, 4);
            }

            var1.a(var2, this, this.a(var1) + var4.nextInt(10));
            if (!var6) {
               if (!this.f(var1, var2)) {
                  if (!World.a(var1, var2.b()) || var7 > 3) {
                     var1.g(var2);
                  }

                  return;
               }

               if (!this.e((IBlockAccess)var1, var2.b()) && var7 == 15 && var4.nextInt(4) == 0) {
                  var1.g(var2);
                  return;
               }
            }

            boolean var8 = var1.D(var2);
            byte var9 = 0;
            if (var8) {
               var9 = -50;
            }

            this.a(var1, var2.f(), 300 + var9, var4, var7);
            this.a(var1, var2.e(), 300 + var9, var4, var7);
            this.a(var1, var2.b(), 250 + var9, var4, var7);
            this.a(var1, var2.a(), 250 + var9, var4, var7);
            this.a(var1, var2.c(), 300 + var9, var4, var7);
            this.a(var1, var2.d(), 300 + var9, var4, var7);

            for (int var10 = -1; var10 <= 1; var10++) {
               for (int var11 = -1; var11 <= 1; var11++) {
                  for (int var12 = -1; var12 <= 4; var12++) {
                     if (var10 != 0 || var12 != 0 || var11 != 0) {
                        int var13 = 100;
                        if (var12 > 1) {
                           var13 += (var12 - 1) * 100;
                        }

                        BlockPos var14 = var2.a(var10, var12, var11);
                        int var15 = this.m(var1, var14);
                        if (var15 > 0) {
                           int var16 = (var15 + 40 + var1.aa().a() * 7) / (var7 + 30);
                           if (var8) {
                              var16 /= 2;
                           }

                           if (var16 > 0 && var4.nextInt(var13) <= var16 && (!var1.S() || !this.e(var1, var14))) {
                              int var17 = var7 + var4.nextInt(5) / 4;
                              if (var17 > 15) {
                                 var17 = 15;
                              }

                              var1.a(var14, var3.a(a, var17), 3);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected boolean e(World var1, BlockPos var2) {
      return var1.C(var2) || var1.C(var2.e()) || var1.C(var2.f()) || var1.C(var2.c()) || var1.C(var2.d());
   }

   @Override
   public boolean N() {
      return false;
   }

   private int c(Block var1) {
      Integer var2 = this.U.get(var1);
      return var2 == null ? 0 : var2;
   }

   private int d(Block var1) {
      Integer var2 = this.T.get(var1);
      return var2 == null ? 0 : var2;
   }

   private void a(World var1, BlockPos var2, int var3, Random var4, int var5) {
      int var6 = this.c(var1.p(var2).c());
      if (var4.nextInt(var3) < var6) {
         IBlockState var7 = var1.p(var2);
         if (var4.nextInt(var5 + 10) < 5 && !var1.C(var2)) {
            int var8 = var5 + var4.nextInt(5) / 4;
            if (var8 > 15) {
               var8 = 15;
            }

            var1.a(var2, this.Q().a(a, var8), 3);
         } else {
            var1.g(var2);
         }

         if (var7.c() == Blocks.W) {
            Blocks.W.d(var1, var2, var7.a(BlockTNT.a, true));
         }
      }
   }

   private boolean f(World var1, BlockPos var2) {
      for (EnumFacing var6 : EnumFacing.values()) {
         if (this.e((IBlockAccess)var1, var2.a(var6))) {
            return true;
         }
      }

      return false;
   }

   private int m(World var1, BlockPos var2) {
      if (!var1.d(var2)) {
         return 0;
      } else {
         int var3 = 0;

         for (EnumFacing var7 : EnumFacing.values()) {
            var3 = Math.max(this.d(var1.p(var2.a(var7)).c()), var3);
         }

         return var3;
      }
   }

   @Override
   public boolean A() {
      return false;
   }

   public boolean e(IBlockAccess var1, BlockPos var2) {
      return this.d(var1.p(var2).c()) > 0;
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return World.a(var1, var2.b()) || this.f(var1, var2);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!World.a(var1, var2.b()) && !this.f(var1, var2)) {
         var1.g(var2);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      if (var1.t.q() > 0 || !Blocks.aY.e(var1, var2)) {
         if (!World.a(var1, var2.b()) && !this.f(var1, var2)) {
            var1.g(var2);
         } else {
            var1.a(var2, this, this.a(var1) + var1.s.nextInt(10));
         }
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var4.nextInt(24) == 0) {
         var1.a(var2.n() + 0.5F, var2.o() + 0.5F, var2.p() + 0.5F, "fire.fire", 1.0F + var4.nextFloat(), var4.nextFloat() * 0.7F + 0.3F, false);
      }

      if (!World.a(var1, var2.b()) && !Blocks.ab.e((IBlockAccess)var1, var2.b())) {
         if (Blocks.ab.e((IBlockAccess)var1, var2.e())) {
            for (int var12 = 0; var12 < 2; var12++) {
               double var17 = var2.n() + var4.nextDouble() * 0.1F;
               double var22 = var2.o() + var4.nextDouble();
               double var27 = var2.p() + var4.nextDouble();
               var1.a(EnumParticleTypes.m, var17, var22, var27, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.ab.e((IBlockAccess)var1, var2.f())) {
            for (int var13 = 0; var13 < 2; var13++) {
               double var18 = var2.n() + 1 - var4.nextDouble() * 0.1F;
               double var23 = var2.o() + var4.nextDouble();
               double var28 = var2.p() + var4.nextDouble();
               var1.a(EnumParticleTypes.m, var18, var23, var28, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.ab.e((IBlockAccess)var1, var2.c())) {
            for (int var14 = 0; var14 < 2; var14++) {
               double var19 = var2.n() + var4.nextDouble();
               double var24 = var2.o() + var4.nextDouble();
               double var29 = var2.p() + var4.nextDouble() * 0.1F;
               var1.a(EnumParticleTypes.m, var19, var24, var29, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.ab.e((IBlockAccess)var1, var2.d())) {
            for (int var15 = 0; var15 < 2; var15++) {
               double var20 = var2.n() + var4.nextDouble();
               double var25 = var2.o() + var4.nextDouble();
               double var30 = var2.p() + 1 - var4.nextDouble() * 0.1F;
               var1.a(EnumParticleTypes.m, var20, var25, var30, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.ab.e((IBlockAccess)var1, var2.a())) {
            for (int var16 = 0; var16 < 2; var16++) {
               double var21 = var2.n() + var4.nextDouble();
               double var26 = var2.o() + 1 - var4.nextDouble() * 0.1F;
               double var31 = var2.p() + var4.nextDouble();
               var1.a(EnumParticleTypes.m, var21, var26, var31, 0.0, 0.0, 0.0);
            }
         }
      } else {
         for (int var5 = 0; var5 < 3; var5++) {
            double var6 = var2.n() + var4.nextDouble();
            double var8 = var2.o() + var4.nextDouble() * 0.5 + 0.5;
            double var10 = var2.p() + var4.nextDouble();
            var1.a(EnumParticleTypes.m, var6, var8, var10, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   public MapColor g(IBlockState var1) {
      return MapColor.f;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, var1);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, O, P, Q, R, S, b, N);
   }
}
