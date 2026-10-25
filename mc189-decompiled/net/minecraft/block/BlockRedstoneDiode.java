package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRedstoneDiode extends BlockDirectional {
   protected final boolean N;

   protected BlockRedstoneDiode(boolean var1) {
      super(Material.q);
      this.N = var1;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return World.a(var1, var2.b()) ? super.d(var1, var2) : false;
   }

   public boolean e(World var1, BlockPos var2) {
      return World.a(var1, var2.b());
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!this.b(var1, var2, var3)) {
         boolean var5 = this.e(var1, var2, var3);
         if (this.N && !var5) {
            var1.a(var2, this.k(var3), 2);
         } else if (!this.N) {
            var1.a(var2, this.e(var3), 2);
            if (!var5) {
               var1.a(var2, this.e(var3).c(), this.m(var3), -1);
            }
         }
      }
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3.k() != EnumFacing.Axis.b;
   }

   protected boolean l(IBlockState var1) {
      return this.N;
   }

   @Override
   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return this.a(var1, var2, var3, var4);
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if (!this.l(var3)) {
         return 0;
      } else {
         return var3.b(O) == var4 ? this.a(var1, var2, var3) : 0;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (this.e(var1, var2)) {
         this.g(var1, var2, var3);
      } else {
         this.b(var1, var2, var3, 0);
         var1.g(var2);

         for (EnumFacing var8 : EnumFacing.values()) {
            var1.c(var2.a(var8), this);
         }
      }
   }

   protected void g(World var1, BlockPos var2, IBlockState var3) {
      if (!this.b(var1, var2, var3)) {
         boolean var4 = this.e(var1, var2, var3);
         if ((this.N && !var4 || !this.N && var4) && !var1.a(var2, this)) {
            byte var5 = -1;
            if (this.i(var1, var2, var3)) {
               var5 = -3;
            } else if (this.N) {
               var5 = -2;
            }

            var1.a(var2, this, this.d(var3), var5);
         }
      }
   }

   public boolean b(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      return false;
   }

   protected boolean e(World var1, BlockPos var2, IBlockState var3) {
      return this.f(var1, var2, var3) > 0;
   }

   protected int f(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = var3.b(O);
      BlockPos var5 = var2.a(var4);
      int var6 = var1.c(var5, var4);
      if (var6 >= 15) {
         return var6;
      } else {
         IBlockState var7 = var1.p(var5);
         return Math.max(var6, var7.c() == Blocks.af ? var7.b(BlockRedstoneWire.P) : 0);
      }
   }

   protected int c(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = var3.b(O);
      EnumFacing var5 = var4.e();
      EnumFacing var6 = var4.f();
      return Math.max(this.c(var1, var2.a(var5), var5), this.c(var1, var2.a(var6), var6));
   }

   protected int c(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      IBlockState var4 = var1.p(var2);
      Block var5 = var4.c();
      if (this.c(var5)) {
         return var5 == Blocks.af ? var4.b(BlockRedstoneWire.P) : var1.a(var2, var3);
      } else {
         return 0;
      }
   }

   @Override
   public boolean i() {
      return true;
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(O, var8.aP().d());
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (this.e(var1, var2, var3)) {
         var1.a(var2, this, 1);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      this.h(var1, var2, var3);
   }

   protected void h(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = var3.b(O);
      BlockPos var5 = var2.a(var4.d());
      var1.d(var5, this);
      var1.a(var5, this, var4);
   }

   @Override
   public void d(World var1, BlockPos var2, IBlockState var3) {
      if (this.N) {
         for (EnumFacing var7 : EnumFacing.values()) {
            var1.c(var2.a(var7), this);
         }
      }

      super.d(var1, var2, var3);
   }

   @Override
   public boolean c() {
      return false;
   }

   protected boolean c(Block var1) {
      return var1.i();
   }

   protected int a(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      return 15;
   }

   public static boolean d(Block var0) {
      return Blocks.bb.e(var0) || Blocks.cj.e(var0);
   }

   public boolean e(Block var1) {
      return var1 == this.e(this.Q()).c() || var1 == this.k(this.Q()).c();
   }

   public boolean i(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = var3.b(O).d();
      BlockPos var5 = var2.a(var4);
      return d(var1.p(var5).c()) ? var1.p(var5).b(O) != var4 : false;
   }

   protected int m(IBlockState var1) {
      return this.d(var1);
   }

   protected abstract int d(IBlockState var1);

   protected abstract IBlockState e(IBlockState var1);

   protected abstract IBlockState k(IBlockState var1);

   @Override
   public boolean b(Block var1) {
      return this.e(var1);
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }
}
