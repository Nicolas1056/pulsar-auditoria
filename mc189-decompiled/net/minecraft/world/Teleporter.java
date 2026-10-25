package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;

public class Teleporter {
   private final WorldServer a;
   private final Random b;
   private final LongHashMap<Teleporter.PortalPosition> c = new LongHashMap<>();
   private final List<Long> d = Lists.newArrayList();

   public Teleporter(WorldServer var1) {
      this.a = var1;
      this.b = new Random(var1.J());
   }

   public void a(Entity var1, float var2) {
      if (this.a.t.q() != 1) {
         if (!this.b(var1, var2)) {
            this.a(var1);
            this.b(var1, var2);
         }
      } else {
         int var3 = MathHelper.c(var1.s);
         int var4 = MathHelper.c(var1.t) - 1;
         int var5 = MathHelper.c(var1.u);
         byte var6 = 1;
         byte var7 = 0;

         for (int var8 = -2; var8 <= 2; var8++) {
            for (int var9 = -2; var9 <= 2; var9++) {
               for (int var10 = -1; var10 < 3; var10++) {
                  int var11 = var3 + var9 * var6 + var8 * var7;
                  int var12 = var4 + var10;
                  int var13 = var5 + var9 * var7 - var8 * var6;
                  boolean var14 = var10 < 0;
                  this.a.a(new BlockPos(var11, var12, var13), var14 ? Blocks.Z.Q() : Blocks.a.Q());
               }
            }
         }

         var1.b(var3, var4, var5, var1.y, 0.0F);
         var1.v = var1.w = var1.x = 0.0;
      }
   }

   public boolean b(Entity var1, float var2) {
      short var3 = 128;
      double var4 = -1.0;
      int var6 = MathHelper.c(var1.s);
      int var7 = MathHelper.c(var1.u);
      boolean var8 = true;
      Object var9 = BlockPos.a;
      long var10 = ChunkCoordIntPair.a(var6, var7);
      if (this.c.b(var10)) {
         Teleporter.PortalPosition var12 = this.c.a(var10);
         var4 = 0.0;
         var9 = var12;
         var12.c = this.a.K();
         var8 = false;
      } else {
         BlockPos var30 = new BlockPos(var1);

         for (int var13 = -128; var13 <= 128; var13++) {
            for (int var14 = -128; var14 <= 128; var14++) {
               BlockPos var15 = var30.a(var13, this.a.V() - 1 - var30.o(), var14);

               while (var15.o() >= 0) {
                  BlockPos var16 = var15.b();
                  if (this.a.p(var15).c() == Blocks.aY) {
                     while (this.a.p(var16 = var15.b()).c() == Blocks.aY) {
                        var15 = var16;
                     }

                     double var17 = var15.i(var30);
                     if (var4 < 0.0 || var17 < var4) {
                        var4 = var17;
                        var9 = var15;
                     }
                  }

                  var15 = var16;
               }
            }
         }
      }

      if (var4 >= 0.0) {
         if (var8) {
            this.c.a(var10, new Teleporter.PortalPosition((BlockPos)var9, this.a.K()));
            this.d.add(var10);
         }

         double var31 = ((BlockPos)var9).n() + 0.5;
         double var32 = ((BlockPos)var9).o() + 0.5;
         double var34 = ((BlockPos)var9).p() + 0.5;
         BlockPattern.PatternHelper var18 = Blocks.aY.f(this.a, (BlockPos)var9);
         boolean var19 = var18.b().e().c() == EnumFacing.AxisDirection.b;
         double var20 = var18.b().k() == EnumFacing.Axis.a ? var18.a().p() : var18.a().n();
         var32 = var18.a().o() + 1 - var1.aG().b * var18.e();
         if (var19) {
            var20++;
         }

         if (var18.b().k() == EnumFacing.Axis.a) {
            var34 = var20 + (1.0 - var1.aG().a) * var18.d() * var18.b().e().c().a();
         } else {
            var31 = var20 + (1.0 - var1.aG().a) * var18.d() * var18.b().e().c().a();
         }

         float var22 = 0.0F;
         float var23 = 0.0F;
         float var24 = 0.0F;
         float var25 = 0.0F;
         if (var18.b().d() == var1.aH()) {
            var22 = 1.0F;
            var23 = 1.0F;
         } else if (var18.b().d() == var1.aH().d()) {
            var22 = -1.0F;
            var23 = -1.0F;
         } else if (var18.b().d() == var1.aH().e()) {
            var24 = 1.0F;
            var25 = -1.0F;
         } else {
            var24 = -1.0F;
            var25 = 1.0F;
         }

         double var26 = var1.v;
         double var28 = var1.x;
         var1.v = var26 * var22 + var28 * var25;
         var1.x = var26 * var24 + var28 * var23;
         var1.y = var2 - var1.aH().d().b() * 90 + var18.b().b() * 90;
         var1.b(var31, var32, var34, var1.y, var1.z);
         return true;
      } else {
         return false;
      }
   }

   public boolean a(Entity var1) {
      byte var2 = 16;
      double var3 = -1.0;
      int var5 = MathHelper.c(var1.s);
      int var6 = MathHelper.c(var1.t);
      int var7 = MathHelper.c(var1.u);
      int var8 = var5;
      int var9 = var6;
      int var10 = var7;
      int var11 = 0;
      int var12 = this.b.nextInt(4);
      BlockPos.MutableBlockPos var13 = new BlockPos.MutableBlockPos();

      for (int var14 = var5 - var2; var14 <= var5 + var2; var14++) {
         double var15 = var14 + 0.5 - var1.s;

         for (int var17 = var7 - var2; var17 <= var7 + var2; var17++) {
            double var18 = var17 + 0.5 - var1.u;

            label296:
            for (int var20 = this.a.V() - 1; var20 >= 0; var20--) {
               if (this.a.d(var13.c(var14, var20, var17))) {
                  while (var20 > 0 && this.a.d(var13.c(var14, var20 - 1, var17))) {
                     var20--;
                  }

                  for (int var21 = var12; var21 < var12 + 4; var21++) {
                     int var22 = var21 % 2;
                     int var23 = 1 - var22;
                     if (var21 % 4 >= 2) {
                        var22 = -var22;
                        var23 = -var23;
                     }

                     for (int var24 = 0; var24 < 3; var24++) {
                        for (int var25 = 0; var25 < 4; var25++) {
                           for (int var26 = -1; var26 < 4; var26++) {
                              int var27 = var14 + (var25 - 1) * var22 + var24 * var23;
                              int var28 = var20 + var26;
                              int var29 = var17 + (var25 - 1) * var23 - var24 * var22;
                              var13.c(var27, var28, var29);
                              if (var26 < 0 && !this.a.p(var13).c().t().a() || var26 >= 0 && !this.a.d(var13)) {
                                 continue label296;
                              }
                           }
                        }
                     }

                     double var52 = var20 + 0.5 - var1.t;
                     double var62 = var15 * var15 + var52 * var52 + var18 * var18;
                     if (var3 < 0.0 || var62 < var3) {
                        var3 = var62;
                        var8 = var14;
                        var9 = var20;
                        var10 = var17;
                        var11 = var21 % 4;
                     }
                  }
               }
            }
         }
      }

      if (var3 < 0.0) {
         for (int var31 = var5 - var2; var31 <= var5 + var2; var31++) {
            double var32 = var31 + 0.5 - var1.s;

            for (int var34 = var7 - var2; var34 <= var7 + var2; var34++) {
               double var36 = var34 + 0.5 - var1.u;

               label233:
               for (int var38 = this.a.V() - 1; var38 >= 0; var38--) {
                  if (this.a.d(var13.c(var31, var38, var34))) {
                     while (var38 > 0 && this.a.d(var13.c(var31, var38 - 1, var34))) {
                        var38--;
                     }

                     for (int var41 = var12; var41 < var12 + 2; var41++) {
                        int var44 = var41 % 2;
                        int var48 = 1 - var44;

                        for (int var53 = 0; var53 < 4; var53++) {
                           for (int var58 = -1; var58 < 4; var58++) {
                              int var63 = var31 + (var53 - 1) * var44;
                              int var68 = var38 + var58;
                              int var71 = var34 + (var53 - 1) * var48;
                              var13.c(var63, var68, var71);
                              if (var58 < 0 && !this.a.p(var13).c().t().a() || var58 >= 0 && !this.a.d(var13)) {
                                 continue label233;
                              }
                           }
                        }

                        double var54 = var38 + 0.5 - var1.t;
                        double var64 = var32 * var32 + var54 * var54 + var36 * var36;
                        if (var3 < 0.0 || var64 < var3) {
                           var3 = var64;
                           var8 = var31;
                           var9 = var38;
                           var10 = var34;
                           var11 = var41 % 2;
                        }
                     }
                  }
               }
            }
         }
      }

      int var33 = var8;
      int var16 = var9;
      int var35 = var10;
      int var37 = var11 % 2;
      int var19 = 1 - var37;
      if (var11 % 4 >= 2) {
         var37 = -var37;
         var19 = -var19;
      }

      if (var3 < 0.0) {
         var9 = MathHelper.a(var9, 70, this.a.V() - 10);
         var16 = var9;

         for (int var39 = -1; var39 <= 1; var39++) {
            for (int var42 = 1; var42 < 3; var42++) {
               for (int var45 = -1; var45 < 3; var45++) {
                  int var49 = var33 + (var42 - 1) * var37 + var39 * var19;
                  int var55 = var16 + var45;
                  int var59 = var35 + (var42 - 1) * var19 - var39 * var37;
                  boolean var65 = var45 < 0;
                  this.a.a(new BlockPos(var49, var55, var59), var65 ? Blocks.Z.Q() : Blocks.a.Q());
               }
            }
         }
      }

      IBlockState var40 = Blocks.aY.Q().a(BlockPortal.a, var37 != 0 ? EnumFacing.Axis.a : EnumFacing.Axis.c);

      for (int var43 = 0; var43 < 4; var43++) {
         for (int var46 = 0; var46 < 4; var46++) {
            for (int var50 = -1; var50 < 4; var50++) {
               int var56 = var33 + (var46 - 1) * var37;
               int var60 = var16 + var50;
               int var66 = var35 + (var46 - 1) * var19;
               boolean var69 = var46 == 0 || var46 == 3 || var50 == -1 || var50 == 3;
               this.a.a(new BlockPos(var56, var60, var66), var69 ? Blocks.Z.Q() : var40, 2);
            }
         }

         for (int var47 = 0; var47 < 4; var47++) {
            for (int var51 = -1; var51 < 4; var51++) {
               int var57 = var33 + (var47 - 1) * var37;
               int var61 = var16 + var51;
               int var67 = var35 + (var47 - 1) * var19;
               BlockPos var70 = new BlockPos(var57, var61, var67);
               this.a.c(var70, this.a.p(var70).c());
            }
         }
      }

      return true;
   }

   public void a(long var1) {
      if (var1 % 100L == 0L) {
         Iterator var3 = this.d.iterator();
         long var4 = var1 - 300L;

         while (var3.hasNext()) {
            Long var6 = (Long)var3.next();
            Teleporter.PortalPosition var7 = this.c.a(var6);
            if (var7 == null || var7.c < var4) {
               var3.remove();
               this.c.d(var6);
            }
         }
      }
   }

   public class PortalPosition extends BlockPos {
      public long c;

      public PortalPosition(BlockPos var2, long var3) {
         super(var2.n(), var2.o(), var2.p());
         this.c = var3;
      }
   }
}
