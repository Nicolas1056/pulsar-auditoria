package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFenceGate extends BlockDirectional {
   public static final PropertyBool a = PropertyBool.a("open");
   public static final PropertyBool b = PropertyBool.a("powered");
   public static final PropertyBool N = PropertyBool.a("in_wall");

   public BlockFenceGate(BlockPlanks.EnumType var1) {
      super(Material.d, var1.c());
      this.j(this.M.b().a(a, false).a(b, false).a(N, false));
      this.a(CreativeTabs.d);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      EnumFacing.Axis var4 = var1.b(O).k();
      if (var4 == EnumFacing.Axis.c && (var2.p(var3.e()).c() == Blocks.bZ || var2.p(var3.f()).c() == Blocks.bZ)
         || var4 == EnumFacing.Axis.a && (var2.p(var3.c()).c() == Blocks.bZ || var2.p(var3.d()).c() == Blocks.bZ)) {
         var1 = var1.a(N, true);
      }

      return var1;
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return var1.p(var2.b()).c().t().a() ? super.d(var1, var2) : false;
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      if (var3.b(a)) {
         return null;
      } else {
         EnumFacing.Axis var4 = var3.b(O).k();
         return var4 == EnumFacing.Axis.c
            ? new AxisAlignedBB(var2.n(), var2.o(), var2.p() + 0.375F, var2.n() + 1, var2.o() + 1.5F, var2.p() + 0.625F)
            : new AxisAlignedBB(var2.n() + 0.375F, var2.o(), var2.p(), var2.n() + 0.625F, var2.o() + 1.5F, var2.p() + 1);
      }
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      EnumFacing.Axis var3 = var1.p(var2).b(O).k();
      if (var3 == EnumFacing.Axis.c) {
         this.a(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
      } else {
         this.a(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
      }
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
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return var1.p(var2).b(a);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(O, var8.aP()).a(a, false).a(b, false).a(N, false);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var3.b(a)) {
         var3 = var3.a(a, false);
         var1.a(var2, var3, 2);
      } else {
         EnumFacing var9 = EnumFacing.a((double)var4.y);
         if (var3.b(O) == var9.d()) {
            var3 = var3.a(O, var9);
         }

         var3 = var3.a(a, true);
         var1.a(var2, var3, 2);
      }

      var1.a(var4, var3.b(a) ? 1003 : 1006, var2, 0);
      return true;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.D) {
         boolean var5 = var1.z(var2);
         if (var5 || var4.i()) {
            if (var5 && !var3.b(a) && !var3.b(b)) {
               var1.a(var2, var3.a(a, true).a(b, true), 2);
               var1.a(null, 1003, var2, 0);
            } else if (!var5 && var3.b(a) && var3.b(b)) {
               var1.a(var2, var3.a(a, false).a(b, false), 2);
               var1.a(null, 1006, var2, 0);
            } else if (var5 != var3.b(b)) {
               var1.a(var2, var3.a(b, var5), 2);
            }
         }
      }
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(O, EnumFacing.b(var1)).a(a, (var1 & 4) != 0).a(b, (var1 & 8) != 0);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(O).b();
      if (var1.b(b)) {
         var2 |= 8;
      }

      if (var1.b(a)) {
         var2 |= 4;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, O, a, b, N);
   }
}
