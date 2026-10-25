package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonMoving extends BlockContainer {
   public static final PropertyDirection a = BlockPistonExtension.a;
   public static final PropertyEnum<BlockPistonExtension.EnumPistonType> b = BlockPistonExtension.b;

   public BlockPistonMoving() {
      super(Material.H);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, BlockPistonExtension.EnumPistonType.a));
      this.c(-1.0F);
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return null;
   }

   public static TileEntity a(IBlockState var0, EnumFacing var1, boolean var2, boolean var3) {
      return new TileEntityPiston(var0, var1, var2, var3);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.s(var2);
      if (var4 instanceof TileEntityPiston) {
         ((TileEntityPiston)var4).h();
      } else {
         super.b(var1, var2, var3);
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return false;
   }

   @Override
   public boolean b(World var1, BlockPos var2, EnumFacing var3) {
      return false;
   }

   @Override
   public void d(World var1, BlockPos var2, IBlockState var3) {
      BlockPos var4 = var2.a(var3.b(a).d());
      IBlockState var5 = var1.p(var4);
      if (var5.c() instanceof BlockPistonBase && var5.b(BlockPistonBase.b)) {
         var1.g(var4);
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
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var1.D && var1.s(var2) == null) {
         var1.g(var2);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return null;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.D) {
         TileEntityPiston var6 = this.e(var1, var2);
         if (var6 != null) {
            IBlockState var7 = var6.b();
            var7.c().b(var1, var2, var7, 0);
         }
      }
   }

   @Override
   public MovingObjectPosition a(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      return null;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.D) {
         var1.s(var2);
      }
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      TileEntityPiston var4 = this.e(var1, var2);
      if (var4 == null) {
         return null;
      } else {
         float var5 = var4.a(0.0F);
         if (var4.d()) {
            var5 = 1.0F - var5;
         }

         return this.a(var1, var2, var4.b(), var5, var4.e());
      }
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      TileEntityPiston var3 = this.e(var1, var2);
      if (var3 != null) {
         IBlockState var4 = var3.b();
         Block var5 = var4.c();
         if (var5 == this || var5.t() == Material.a) {
            return;
         }

         float var6 = var3.a(0.0F);
         if (var3.d()) {
            var6 = 1.0F - var6;
         }

         var5.a(var1, var2);
         if (var5 == Blocks.J || var5 == Blocks.F) {
            var6 = 0.0F;
         }

         EnumFacing var7 = var3.e();
         this.B = var5.B() - var7.g() * var6;
         this.C = var5.D() - var7.h() * var6;
         this.D = var5.F() - var7.i() * var6;
         this.E = var5.C() - var7.g() * var6;
         this.F = var5.E() - var7.h() * var6;
         this.G = var5.G() - var7.i() * var6;
      }
   }

   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3, float var4, EnumFacing var5) {
      if (var3.c() != this && var3.c().t() != Material.a) {
         AxisAlignedBB var6 = var3.c().a(var1, var2, var3);
         if (var6 == null) {
            return null;
         } else {
            double var7 = var6.a;
            double var9 = var6.b;
            double var11 = var6.c;
            double var13 = var6.d;
            double var15 = var6.e;
            double var17 = var6.f;
            if (var5.g() < 0) {
               var7 -= var5.g() * var4;
            } else {
               var13 -= var5.g() * var4;
            }

            if (var5.h() < 0) {
               var9 -= var5.h() * var4;
            } else {
               var15 -= var5.h() * var4;
            }

            if (var5.i() < 0) {
               var11 -= var5.i() * var4;
            } else {
               var17 -= var5.i() * var4;
            }

            return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
         }
      } else {
         return null;
      }
   }

   private TileEntityPiston e(IBlockAccess var1, BlockPos var2) {
      TileEntity var3 = var1.s(var2);
      return var3 instanceof TileEntityPiston ? (TileEntityPiston)var3 : null;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return null;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, BlockPistonExtension.b(var1)).a(b, (var1 & 8) > 0 ? BlockPistonExtension.EnumPistonType.b : BlockPistonExtension.EnumPistonType.a);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).a();
      if (var1.b(b) == BlockPistonExtension.EnumPistonType.b) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }
}
