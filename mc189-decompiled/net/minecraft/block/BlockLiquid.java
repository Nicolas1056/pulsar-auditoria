package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLiquid extends Block {
   public static final PropertyInteger b = PropertyInteger.a("level", 0, 15);

   protected BlockLiquid(Material var1) {
      super(var1);
      this.j(this.M.b().a(b, 0));
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.a(true);
   }

   @Override
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return this.J != Material.i;
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      return this.J == Material.h ? BiomeColorHelper.c(var1, var2) : 16777215;
   }

   public static float b(int var0) {
      if (var0 >= 8) {
         var0 = 0;
      }

      return (var0 + 1) / 9.0F;
   }

   protected int e(IBlockAccess var1, BlockPos var2) {
      return var1.p(var2).c().t() == this.J ? var1.p(var2).b(b) : -1;
   }

   protected int f(IBlockAccess var1, BlockPos var2) {
      int var3 = this.e(var1, var2);
      return var3 >= 8 ? 0 : var3;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean a(IBlockState var1, boolean var2) {
      return var2 && var1.b(b) == 0;
   }

   @Override
   public boolean b(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      Material var4 = var1.p(var2).c().t();
      if (var4 == this.J) {
         return false;
      } else if (var3 == EnumFacing.b) {
         return true;
      } else {
         return var4 == Material.w ? false : super.b(var1, var2, var3);
      }
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      if (var1.p(var2).c().t() == this.J) {
         return false;
      } else {
         return var3 == EnumFacing.b ? true : super.a(var1, var2, var3);
      }
   }

   public boolean g(IBlockAccess var1, BlockPos var2) {
      for (int var3 = -1; var3 <= 1; var3++) {
         for (int var4 = -1; var4 <= 1; var4++) {
            IBlockState var5 = var1.p(var2.a(var3, 0, var4));
            Block var6 = var5.c();
            Material var7 = var6.t();
            if (var7 != this.J && !var6.o()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public int b() {
      return 1;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return null;
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   protected Vec3 h(IBlockAccess var1, BlockPos var2) {
      Vec3 var3 = new Vec3(0.0, 0.0, 0.0);
      int var4 = this.f(var1, var2);

      for (EnumFacing var6 : EnumFacing.Plane.a) {
         BlockPos var7 = var2.a(var6);
         int var8 = this.f(var1, var7);
         if (var8 < 0) {
            if (!var1.p(var7).c().t().c()) {
               var8 = this.f(var1, var7.b());
               if (var8 >= 0) {
                  int var9 = var8 - (var4 - 8);
                  var3 = var3.b((var7.n() - var2.n()) * var9, (var7.o() - var2.o()) * var9, (var7.p() - var2.p()) * var9);
               }
            }
         } else if (var8 >= 0) {
            int var14 = var8 - var4;
            var3 = var3.b((var7.n() - var2.n()) * var14, (var7.o() - var2.o()) * var14, (var7.p() - var2.p()) * var14);
         }
      }

      if (var1.p(var2).b(b) >= 8) {
         for (EnumFacing var11 : EnumFacing.Plane.a) {
            BlockPos var12 = var2.a(var11);
            if (this.b(var1, var12, var11) || this.b(var1, var12.a(), var11)) {
               var3 = var3.a().b(0.0, -6.0, 0.0);
               break;
            }
         }
      }

      return var3.a();
   }

   @Override
   public Vec3 a(World var1, BlockPos var2, Entity var3, Vec3 var4) {
      return var4.e(this.h(var1, var2));
   }

   @Override
   public int a(World var1) {
      if (this.J == Material.h) {
         return 5;
      } else if (this.J == Material.i) {
         return var1.t.o() ? 10 : 30;
      } else {
         return 0;
      }
   }

   @Override
   public int c(IBlockAccess var1, BlockPos var2) {
      int var3 = var1.b(var2, 0);
      int var4 = var1.b(var2.a(), 0);
      int var5 = var3 & 0xFF;
      int var6 = var4 & 0xFF;
      int var7 = var3 >> 16 & 0xFF;
      int var8 = var4 >> 16 & 0xFF;
      return (var5 > var6 ? var5 : var6) | (var7 > var8 ? var7 : var8) << 16;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return this.J == Material.h ? EnumWorldBlockLayer.d : EnumWorldBlockLayer.a;
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      double var5 = var2.n();
      double var7 = var2.o();
      double var9 = var2.p();
      if (this.J == Material.h) {
         int var11 = var3.b(b);
         if (var11 > 0 && var11 < 8) {
            if (var4.nextInt(64) == 0) {
               var1.a(var5 + 0.5, var7 + 0.5, var9 + 0.5, "liquid.water", var4.nextFloat() * 0.25F + 0.75F, var4.nextFloat() * 1.0F + 0.5F, false);
            }
         } else if (var4.nextInt(10) == 0) {
            var1.a(EnumParticleTypes.h, var5 + var4.nextFloat(), var7 + var4.nextFloat(), var9 + var4.nextFloat(), 0.0, 0.0, 0.0);
         }
      }

      if (this.J == Material.i && var1.p(var2.a()).c().t() == Material.a && !var1.p(var2.a()).c().c()) {
         if (var4.nextInt(100) == 0) {
            double var18 = var5 + var4.nextFloat();
            double var13 = var7 + this.F;
            double var15 = var9 + var4.nextFloat();
            var1.a(EnumParticleTypes.B, var18, var13, var15, 0.0, 0.0, 0.0);
            var1.a(var18, var13, var15, "liquid.lavapop", 0.2F + var4.nextFloat() * 0.2F, 0.9F + var4.nextFloat() * 0.15F, false);
         }

         if (var4.nextInt(200) == 0) {
            var1.a(var5, var7, var9, "liquid.lava", 0.2F + var4.nextFloat() * 0.2F, 0.9F + var4.nextFloat() * 0.15F, false);
         }
      }

      if (var4.nextInt(10) == 0 && World.a(var1, var2.b())) {
         Material var19 = var1.p(var2.c(2)).c().t();
         if (!var19.c() && !var19.d()) {
            double var12 = var5 + var4.nextFloat();
            double var14 = var7 - 1.05;
            double var16 = var9 + var4.nextFloat();
            if (this.J == Material.h) {
               var1.a(EnumParticleTypes.s, var12, var14, var16, 0.0, 0.0, 0.0);
            } else {
               var1.a(EnumParticleTypes.t, var12, var14, var16, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   public static double a(IBlockAccess var0, BlockPos var1, Material var2) {
      Vec3 var3 = a(var2).h(var0, var1);
      return var3.a == 0.0 && var3.c == 0.0 ? -1000.0 : MathHelper.b(var3.c, var3.a) - (Math.PI / 2);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      this.e(var1, var2, var3);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.e(var1, var2, var3);
   }

   public boolean e(World var1, BlockPos var2, IBlockState var3) {
      if (this.J == Material.i) {
         boolean var4 = false;

         for (EnumFacing var8 : EnumFacing.values()) {
            if (var8 != EnumFacing.a && var1.p(var2.a(var8)).c().t() == Material.h) {
               var4 = true;
               break;
            }
         }

         if (var4) {
            Integer var9 = var3.b(b);
            if (var9 == 0) {
               var1.a(var2, Blocks.Z.Q());
               this.e(var1, var2);
               return true;
            }

            if (var9 <= 4) {
               var1.a(var2, Blocks.e.Q());
               this.e(var1, var2);
               return true;
            }
         }
      }

      return false;
   }

   protected void e(World var1, BlockPos var2) {
      double var3 = var2.n();
      double var5 = var2.o();
      double var7 = var2.p();
      var1.a(var3 + 0.5, var5 + 0.5, var7 + 0.5, "random.fizz", 0.5F, 2.6F + (var1.s.nextFloat() - var1.s.nextFloat()) * 0.8F);

      for (int var9 = 0; var9 < 8; var9++) {
         var1.a(EnumParticleTypes.m, var3 + Math.random(), var5 + 1.2, var7 + Math.random(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(b, var1);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(b);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, b);
   }

   public static BlockDynamicLiquid a(Material var0) {
      if (var0 == Material.h) {
         return Blocks.i;
      } else if (var0 == Material.i) {
         return Blocks.k;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }

   public static BlockStaticLiquid b(Material var0) {
      if (var0 == Material.h) {
         return Blocks.j;
      } else if (var0 == Material.i) {
         return Blocks.l;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }
}
