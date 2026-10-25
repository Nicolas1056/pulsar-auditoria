package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLadder extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);

   protected BlockLadder() {
      super(Material.q);
      this.j(this.M.b().a(a, EnumFacing.c));
      this.a(CreativeTabs.c);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      this.a(var1, var2);
      return super.a(var1, var2, var3);
   }

   @Override
   public AxisAlignedBB b(World var1, BlockPos var2) {
      this.a(var1, var2);
      return super.b(var1, var2);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      if (var3.c() == this) {
         float var4 = 0.125F;
         switch ((EnumFacing)var3.b(a)) {
            case c:
               this.a(0.0F, 0.0F, 1.0F - var4, 1.0F, 1.0F, 1.0F);
               break;
            case d:
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var4);
               break;
            case e:
               this.a(1.0F - var4, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
               break;
            case f:
            default:
               this.a(0.0F, 0.0F, 0.0F, var4, 1.0F, 1.0F);
         }
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
   public boolean d(World var1, BlockPos var2) {
      if (var1.p(var2.e()).c().v()) {
         return true;
      } else if (var1.p(var2.f()).c().v()) {
         return true;
      } else {
         return var1.p(var2.c()).c().v() ? true : var1.p(var2.d()).c().v();
      }
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if (var3.k().c() && this.a(var1, var2, var3)) {
         return this.Q().a(a, var3);
      } else {
         for (EnumFacing var10 : EnumFacing.Plane.a) {
            if (this.a(var1, var2, var10)) {
               return this.Q().a(a, var10);
            }
         }

         return this.Q();
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = var3.b(a);
      if (!this.a(var1, var2, var5)) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
      }

      super.a(var1, var2, var3, var4);
   }

   protected boolean a(World var1, BlockPos var2, EnumFacing var3) {
      return var1.p(var2.a(var3.d())).c().v();
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(int var1) {
      EnumFacing var2 = EnumFacing.a(var1);
      if (var2.k() == EnumFacing.Axis.b) {
         var2 = EnumFacing.c;
      }

      return this.Q().a(a, var2);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
