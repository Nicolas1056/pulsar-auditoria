package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStem extends BlockBush implements IGrowable {
   public static final PropertyInteger a = PropertyInteger.a("age", 0, 7);
   public static final PropertyDirection b = PropertyDirection.a("facing", new Predicate<EnumFacing>() {
      public boolean a(EnumFacing var1) {
         return var1 != EnumFacing.a;
      }
   });
   private final Block N;

   protected BlockStem(Block var1) {
      this.j(this.M.b().a(a, 0).a(b, EnumFacing.b));
      this.N = var1;
      this.a(true);
      float var2 = 0.125F;
      this.a(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
      this.a(null);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      var1 = var1.a(b, EnumFacing.b);

      for (EnumFacing var5 : EnumFacing.Plane.a) {
         if (var2.p(var3.a(var5)).c() == this.N) {
            var1 = var1.a(b, var5);
            break;
         }
      }

      return var1;
   }

   @Override
   protected boolean c(Block var1) {
      return var1 == Blocks.ak;
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.b(var1, var2, var3, var4);
      if (var1.l(var2.a()) >= 9) {
         float var5 = BlockCrops.a(this, var1, var2);
         if (var4.nextInt((int)(25.0F / var5) + 1) == 0) {
            int var6 = var3.b(a);
            if (var6 < 7) {
               var3 = var3.a(a, var6 + 1);
               var1.a(var2, var3, 2);
            } else {
               for (EnumFacing var8 : EnumFacing.Plane.a) {
                  if (var1.p(var2.a(var8)).c() == this.N) {
                     return;
                  }
               }

               var2 = var2.a(EnumFacing.Plane.a.a(var4));
               Block var11 = var1.p(var2.b()).c();
               if (var1.p(var2).c().J == Material.a && (var11 == Blocks.ak || var11 == Blocks.d || var11 == Blocks.c)) {
                  var1.a(var2, this.N.Q());
               }
            }
         }
      }
   }

   public void g(World var1, BlockPos var2, IBlockState var3) {
      int var4 = var3.b(a) + MathHelper.a(var1.s, 2, 5);
      var1.a(var2, var3.a(a, Math.min(7, var4)), 2);
   }

   @Override
   public int h(IBlockState var1) {
      if (var1.c() != this) {
         return super.h(var1);
      } else {
         int var2 = var1.b(a);
         int var3 = var2 * 32;
         int var4 = 255 - var2 * 8;
         int var5 = var2 * 4;
         return var3 << 16 | var4 << 8 | var5;
      }
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      return this.h(var1.p(var2));
   }

   @Override
   public void j() {
      float var1 = 0.125F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.F = (var1.p(var2).b(a) * 2 + 2) / 16.0F;
      float var3 = 0.125F;
      this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, (float)this.F, 0.5F + var3);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      super.a(var1, var2, var3, var4, var5);
      if (!var1.D) {
         Item var6 = this.l();
         if (var6 != null) {
            int var7 = var3.b(a);

            for (int var8 = 0; var8 < 3; var8++) {
               if (var1.s.nextInt(15) <= var7) {
                  a(var1, var2, new ItemStack(var6));
               }
            }
         }
      }
   }

   protected Item l() {
      if (this.N == Blocks.aU) {
         return Items.bg;
      } else {
         return this.N == Blocks.bk ? Items.bh : null;
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return null;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      Item var3 = this.l();
      return var3 != null ? var3 : null;
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return var3.b(a) != 7;
   }

   @Override
   public boolean a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.g(var1, var3, var4);
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
      return new BlockState(this, a, b);
   }
}
