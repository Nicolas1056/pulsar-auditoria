package net.minecraft.block;

import com.google.common.base.Objects;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripWireHook extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);
   public static final PropertyBool b = PropertyBool.a("powered");
   public static final PropertyBool N = PropertyBool.a("attached");
   public static final PropertyBool O = PropertyBool.a("suspended");

   public BlockTripWireHook() {
      super(Material.q);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, false).a(N, false).a(O, false));
      this.a(CreativeTabs.d);
      this.a(true);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.a(O, !World.a(var2, var3.b()));
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
   public boolean b(World var1, BlockPos var2, EnumFacing var3) {
      return var3.k().c() && var1.p(var2.a(var3.d())).c().v();
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      for (EnumFacing var4 : EnumFacing.Plane.a) {
         if (var1.p(var2.a(var4)).c().v()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.Q().a(b, false).a(N, false).a(O, false);
      if (var3.k().c()) {
         var9 = var9.a(a, var3);
      }

      return var9;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      this.a(var1, var2, var3, false, false, -1, null);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (var4 != this) {
         if (this.e(var1, var2, var3)) {
            EnumFacing var5 = var3.b(a);
            if (!var1.p(var2.a(var5.d())).c().v()) {
               this.b(var1, var2, var3, 0);
               var1.g(var2);
            }
         }
      }
   }

   public void a(World var1, BlockPos var2, IBlockState var3, boolean var4, boolean var5, int var6, IBlockState var7) {
      EnumFacing var8 = var3.b(a);
      boolean var9 = var3.b(N);
      boolean var10 = var3.b(b);
      boolean var11 = !World.a(var1, var2.b());
      boolean var12 = !var4;
      boolean var13 = false;
      int var14 = 0;
      IBlockState[] var15 = new IBlockState[42];

      for (int var16 = 1; var16 < 42; var16++) {
         BlockPos var17 = var2.a(var8, var16);
         IBlockState var18 = var1.p(var17);
         if (var18.c() == Blocks.bR) {
            if (var18.b(a) == var8.d()) {
               var14 = var16;
            }
            break;
         }

         if (var18.c() != Blocks.bS && var16 != var6) {
            var15[var16] = null;
            var12 = false;
         } else {
            if (var16 == var6) {
               var18 = (IBlockState)Objects.firstNonNull(var7, var18);
            }

            boolean var19 = !var18.b(BlockTripWire.O);
            boolean var20 = var18.b(BlockTripWire.a);
            boolean var21 = var18.b(BlockTripWire.b);
            var12 &= var21 == var11;
            var13 |= var19 && var20;
            var15[var16] = var18;
            if (var16 == var6) {
               var1.a(var2, this, this.a(var1));
               var12 &= var19;
            }
         }
      }

      var12 &= var14 > 1;
      var13 &= var12;
      IBlockState var24 = this.Q().a(N, var12).a(b, var13);
      if (var14 > 0) {
         BlockPos var25 = var2.a(var8, var14);
         EnumFacing var27 = var8.d();
         var1.a(var25, var24.a(a, var27), 3);
         this.a(var1, var25, var27);
         this.a(var1, var25, var12, var13, var9, var10);
      }

      this.a(var1, var2, var12, var13, var9, var10);
      if (!var4) {
         var1.a(var2, var24.a(a, var8), 3);
         if (var5) {
            this.a(var1, var2, var8);
         }
      }

      if (var9 != var12) {
         for (int var26 = 1; var26 < var14; var26++) {
            BlockPos var28 = var2.a(var8, var26);
            IBlockState var29 = var15[var26];
            if (var29 != null && var1.p(var28).c() != Blocks.a) {
               var1.a(var28, var29.a(N, var12), 3);
            }
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.a(var1, var2, var3, false, true, -1, null);
   }

   private void a(World var1, BlockPos var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      if (var4 && !var6) {
         var1.a(var2.n() + 0.5, var2.o() + 0.1, var2.p() + 0.5, "random.click", 0.4F, 0.6F);
      } else if (!var4 && var6) {
         var1.a(var2.n() + 0.5, var2.o() + 0.1, var2.p() + 0.5, "random.click", 0.4F, 0.5F);
      } else if (var3 && !var5) {
         var1.a(var2.n() + 0.5, var2.o() + 0.1, var2.p() + 0.5, "random.click", 0.4F, 0.7F);
      } else if (!var3 && var5) {
         var1.a(var2.n() + 0.5, var2.o() + 0.1, var2.p() + 0.5, "random.bowhit", 0.4F, 1.2F / (var1.s.nextFloat() * 0.2F + 0.9F));
      }
   }

   private void a(World var1, BlockPos var2, EnumFacing var3) {
      var1.c(var2, this);
      var1.c(var2.a(var3.d()), this);
   }

   private boolean e(World var1, BlockPos var2, IBlockState var3) {
      if (!this.d(var1, var2)) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
         return false;
      } else {
         return true;
      }
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.1875F;
      switch ((EnumFacing)var1.p(var2).b(a)) {
         case f:
            this.a(0.0F, 0.2F, 0.5F - var3, var3 * 2.0F, 0.8F, 0.5F + var3);
            break;
         case e:
            this.a(1.0F - var3 * 2.0F, 0.2F, 0.5F - var3, 1.0F, 0.8F, 0.5F + var3);
            break;
         case d:
            this.a(0.5F - var3, 0.2F, 0.0F, 0.5F + var3, 0.8F, var3 * 2.0F);
            break;
         case c:
            this.a(0.5F - var3, 0.2F, 1.0F - var3 * 2.0F, 0.5F + var3, 0.8F, 1.0F);
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = var3.b(N);
      boolean var5 = var3.b(b);
      if (var4 || var5) {
         this.a(var1, var2, var3, true, false, -1, null);
      }

      if (var5) {
         var1.c(var2, this);
         var1.c(var2.a(var3.b(a).d()), this);
      }

      super.b(var1, var2, var3);
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var3.b(b) ? 15 : 0;
   }

   @Override
   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if (!var3.b(b)) {
         return 0;
      } else {
         return var3.b(a) == var4 ? 15 : 0;
      }
   }

   @Override
   public boolean i() {
      return true;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.b;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, EnumFacing.b(var1 & 3)).a(b, (var1 & 8) > 0).a(N, (var1 & 4) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).b();
      if (var1.b(b)) {
         var2 |= 8;
      }

      if (var1.b(N)) {
         var2 |= 4;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b, N, O);
   }
}
