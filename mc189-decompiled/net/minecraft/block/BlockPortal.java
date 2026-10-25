package net.minecraft.block;

import com.google.common.cache.LoadingCache;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPortal extends BlockBreakable {
   public static final PropertyEnum<EnumFacing.Axis> a = PropertyEnum.a("axis", EnumFacing.Axis.class, EnumFacing.Axis.a, EnumFacing.Axis.c);

   public BlockPortal() {
      super(Material.E, false);
      this.j(this.M.b().a(a, EnumFacing.Axis.a));
      this.a(true);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.b(var1, var2, var3, var4);
      if (var1.t.d() && var1.Q().b("doMobSpawning") && var4.nextInt(2000) < var1.aa().a()) {
         int var5 = var2.o();
         BlockPos var6 = var2;

         while (!World.a(var1, var6) && var6.o() > 0) {
            var6 = var6.b();
         }

         if (var5 > 0 && !var1.p(var6.a()).c().v()) {
            Entity var7 = ItemMonsterPlacer.a(var1, 57, var6.n() + 0.5, var6.o() + 1.1, var6.p() + 0.5);
            if (var7 != null) {
               var7.aj = var7.aq();
            }
         }
      }
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      EnumFacing.Axis var3 = var1.p(var2).b(a);
      float var4 = 0.125F;
      float var5 = 0.125F;
      if (var3 == EnumFacing.Axis.a) {
         var4 = 0.5F;
      }

      if (var3 == EnumFacing.Axis.c) {
         var5 = 0.5F;
      }

      this.a(0.5F - var4, 0.0F, 0.5F - var5, 0.5F + var4, 1.0F, 0.5F + var5);
   }

   public static int a(EnumFacing.Axis var0) {
      if (var0 == EnumFacing.Axis.a) {
         return 1;
      } else {
         return var0 == EnumFacing.Axis.c ? 2 : 0;
      }
   }

   @Override
   public boolean d() {
      return false;
   }

   public boolean e(World var1, BlockPos var2) {
      BlockPortal.Size var3 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.a);
      if (var3.d() && var3.e == 0) {
         var3.e();
         return true;
      } else {
         BlockPortal.Size var4 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.c);
         if (var4.d() && var4.e == 0) {
            var4.e();
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing.Axis var5 = var3.b(a);
      if (var5 == EnumFacing.Axis.a) {
         BlockPortal.Size var6 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.a);
         if (!var6.d() || var6.e < var6.h * var6.g) {
            var1.a(var2, Blocks.a.Q());
         }
      } else if (var5 == EnumFacing.Axis.c) {
         BlockPortal.Size var7 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.c);
         if (!var7.d() || var7.e < var7.h * var7.g) {
            var1.a(var2, Blocks.a.Q());
         }
      }
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      EnumFacing.Axis var4 = null;
      IBlockState var5 = var1.p(var2);
      if (var1.p(var2).c() == this) {
         var4 = var5.b(a);
         if (var4 == null) {
            return false;
         }

         if (var4 == EnumFacing.Axis.c && var3 != EnumFacing.f && var3 != EnumFacing.e) {
            return false;
         }

         if (var4 == EnumFacing.Axis.a && var3 != EnumFacing.d && var3 != EnumFacing.c) {
            return false;
         }
      }

      boolean var6 = var1.p(var2.e()).c() == this && var1.p(var2.f(2)).c() != this;
      boolean var7 = var1.p(var2.f()).c() == this && var1.p(var2.g(2)).c() != this;
      boolean var8 = var1.p(var2.c()).c() == this && var1.p(var2.d(2)).c() != this;
      boolean var9 = var1.p(var2.d()).c() == this && var1.p(var2.e(2)).c() != this;
      boolean var10 = var6 || var7 || var4 == EnumFacing.Axis.a;
      boolean var11 = var8 || var9 || var4 == EnumFacing.Axis.c;
      if (var10 && var3 == EnumFacing.e) {
         return true;
      } else if (var10 && var3 == EnumFacing.f) {
         return true;
      } else {
         return var11 && var3 == EnumFacing.c ? true : var11 && var3 == EnumFacing.d;
      }
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.d;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (var4.m == null && var4.l == null) {
         var4.d(var2);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var4.nextInt(100) == 0) {
         var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "portal.portal", 0.5F, var4.nextFloat() * 0.4F + 0.8F, false);
      }

      for (int var5 = 0; var5 < 4; var5++) {
         double var6 = var2.n() + var4.nextFloat();
         double var8 = var2.o() + var4.nextFloat();
         double var10 = var2.p() + var4.nextFloat();
         double var12 = (var4.nextFloat() - 0.5) * 0.5;
         double var14 = (var4.nextFloat() - 0.5) * 0.5;
         double var16 = (var4.nextFloat() - 0.5) * 0.5;
         int var18 = var4.nextInt(2) * 2 - 1;
         if (var1.p(var2.e()).c() != this && var1.p(var2.f()).c() != this) {
            var6 = var2.n() + 0.5 + 0.25 * var18;
            var12 = var4.nextFloat() * 2.0F * var18;
         } else {
            var10 = var2.p() + 0.5 + 0.25 * var18;
            var16 = var4.nextFloat() * 2.0F * var18;
         }

         var1.a(EnumParticleTypes.y, var6, var8, var10, var12, var14, var16);
      }
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return null;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, (var1 & 3) == 2 ? EnumFacing.Axis.c : EnumFacing.Axis.a);
   }

   @Override
   public int c(IBlockState var1) {
      return a(var1.b(a));
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }

   public BlockPattern.PatternHelper f(World var1, BlockPos var2) {
      EnumFacing.Axis var3 = EnumFacing.Axis.c;
      BlockPortal.Size var4 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.a);
      LoadingCache var5 = BlockPattern.a(var1, true);
      if (!var4.d()) {
         var3 = EnumFacing.Axis.a;
         var4 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.c);
      }

      if (!var4.d()) {
         return new BlockPattern.PatternHelper(var2, EnumFacing.c, EnumFacing.b, var5, 1, 1, 1);
      } else {
         int[] var6 = new int[EnumFacing.AxisDirection.values().length];
         EnumFacing var7 = var4.c.f();
         BlockPos var8 = var4.f.b(var4.a() - 1);

         for (EnumFacing.AxisDirection var12 : EnumFacing.AxisDirection.values()) {
            BlockPattern.PatternHelper var13 = new BlockPattern.PatternHelper(
               var7.c() == var12 ? var8 : var8.a(var4.c, var4.b() - 1), EnumFacing.a(var12, var3), EnumFacing.b, var5, var4.b(), var4.a(), 1
            );

            for (int var14 = 0; var14 < var4.b(); var14++) {
               for (int var15 = 0; var15 < var4.a(); var15++) {
                  BlockWorldState var16 = var13.a(var14, var15, 1);
                  if (var16.a() != null && var16.a().c().t() != Material.a) {
                     var6[var12.ordinal()]++;
                  }
               }
            }
         }

         EnumFacing.AxisDirection var17 = EnumFacing.AxisDirection.a;

         for (EnumFacing.AxisDirection var21 : EnumFacing.AxisDirection.values()) {
            if (var6[var21.ordinal()] < var6[var17.ordinal()]) {
               var17 = var21;
            }
         }

         return new BlockPattern.PatternHelper(
            var7.c() == var17 ? var8 : var8.a(var4.c, var4.b() - 1), EnumFacing.a(var17, var3), EnumFacing.b, var5, var4.b(), var4.a(), 1
         );
      }
   }

   public static class Size {
      private final World a;
      private final EnumFacing.Axis b;
      private final EnumFacing c;
      private final EnumFacing d;
      private int e = 0;
      private BlockPos f;
      private int g;
      private int h;

      public Size(World var1, BlockPos var2, EnumFacing.Axis var3) {
         this.a = var1;
         this.b = var3;
         if (var3 == EnumFacing.Axis.a) {
            this.d = EnumFacing.f;
            this.c = EnumFacing.e;
         } else {
            this.d = EnumFacing.c;
            this.c = EnumFacing.d;
         }

         BlockPos var4 = var2;

         while (var2.o() > var4.o() - 21 && var2.o() > 0 && this.a(var1.p(var2.b()).c())) {
            var2 = var2.b();
         }

         int var5 = this.a(var2, this.d) - 1;
         if (var5 >= 0) {
            this.f = var2.a(this.d, var5);
            this.h = this.a(this.f, this.c);
            if (this.h < 2 || this.h > 21) {
               this.f = null;
               this.h = 0;
            }
         }

         if (this.f != null) {
            this.g = this.c();
         }
      }

      protected int a(BlockPos var1, EnumFacing var2) {
         int var3;
         for (var3 = 0; var3 < 22; var3++) {
            BlockPos var4 = var1.a(var2, var3);
            if (!this.a(this.a.p(var4).c()) || this.a.p(var4.b()).c() != Blocks.Z) {
               break;
            }
         }

         Block var5 = this.a.p(var1.a(var2, var3)).c();
         return var5 == Blocks.Z ? var3 : 0;
      }

      public int a() {
         return this.g;
      }

      public int b() {
         return this.h;
      }

      protected int c() {
         label56:
         for (this.g = 0; this.g < 21; this.g++) {
            for (int var1 = 0; var1 < this.h; var1++) {
               BlockPos var2 = this.f.a(this.c, var1).b(this.g);
               Block var3 = this.a.p(var2).c();
               if (!this.a(var3)) {
                  break label56;
               }

               if (var3 == Blocks.aY) {
                  this.e++;
               }

               if (var1 == 0) {
                  var3 = this.a.p(var2.a(this.d)).c();
                  if (var3 != Blocks.Z) {
                     break label56;
                  }
               } else if (var1 == this.h - 1) {
                  var3 = this.a.p(var2.a(this.c)).c();
                  if (var3 != Blocks.Z) {
                     break label56;
                  }
               }
            }
         }

         for (int var4 = 0; var4 < this.h; var4++) {
            if (this.a.p(this.f.a(this.c, var4).b(this.g)).c() != Blocks.Z) {
               this.g = 0;
               break;
            }
         }

         if (this.g <= 21 && this.g >= 3) {
            return this.g;
         } else {
            this.f = null;
            this.h = 0;
            this.g = 0;
            return 0;
         }
      }

      protected boolean a(Block var1) {
         return var1.J == Material.a || var1 == Blocks.ab || var1 == Blocks.aY;
      }

      public boolean d() {
         return this.f != null && this.h >= 2 && this.h <= 21 && this.g >= 3 && this.g <= 21;
      }

      public void e() {
         for (int var1 = 0; var1 < this.h; var1++) {
            BlockPos var2 = this.f.a(this.c, var1);

            for (int var3 = 0; var3 < this.g; var3++) {
               this.a.a(var2.b(var3), Blocks.aY.Q().a(BlockPortal.a, this.b), 2);
            }
         }
      }
   }
}
