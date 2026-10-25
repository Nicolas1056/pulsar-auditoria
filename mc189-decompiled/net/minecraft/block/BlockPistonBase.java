package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonBase extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing");
   public static final PropertyBool b = PropertyBool.a("extended");
   private final boolean N;

   public BlockPistonBase(boolean var1) {
      super(Material.H);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, false));
      this.N = var1;
      this.a(i);
      this.c(0.5F);
      this.a(CreativeTabs.d);
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.a(var2, var3.a(a, a(var1, var2, var4)), 2);
      if (!var1.D) {
         this.e(var1, var2, var3);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.D) {
         this.e(var1, var2, var3);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D && var1.s(var2) == null) {
         this.e(var1, var2, var3);
      }
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(a, a(var1, var2, var8)).a(b, false);
   }

   private void e(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = var3.b(a);
      boolean var5 = this.a(var1, var2, var4);
      if (var5 && !var3.b(b)) {
         if (new BlockPistonStructureHelper(var1, var2, var4, true).a()) {
            var1.c(var2, this, 0, var4.a());
         }
      } else if (!var5 && var3.b(b)) {
         var1.a(var2, var3.a(b, false), 2);
         var1.c(var2, this, 1, var4.a());
      }
   }

   private boolean a(World var1, BlockPos var2, EnumFacing var3) {
      for (EnumFacing var7 : EnumFacing.values()) {
         if (var7 != var3 && var1.b(var2.a(var7), var7)) {
            return true;
         }
      }

      if (var1.b(var2, EnumFacing.a)) {
         return true;
      } else {
         BlockPos var9 = var2.a();

         for (EnumFacing var8 : EnumFacing.values()) {
            if (var8 != EnumFacing.a && var1.b(var9.a(var8), var8)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      EnumFacing var6 = var3.b(a);
      if (!var1.D) {
         boolean var7 = this.a(var1, var2, var6);
         if (var7 && var4 == 1) {
            var1.a(var2, var3.a(b, true), 2);
            return false;
         }

         if (!var7 && var4 == 0) {
            return false;
         }
      }

      if (var4 == 0) {
         if (!this.a(var1, var2, var6, true)) {
            return false;
         }

         var1.a(var2, var3.a(b, true), 2);
         var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "tile.piston.out", 0.5F, var1.s.nextFloat() * 0.25F + 0.6F);
      } else if (var4 == 1) {
         TileEntity var13 = var1.s(var2.a(var6));
         if (var13 instanceof TileEntityPiston) {
            ((TileEntityPiston)var13).h();
         }

         var1.a(
            var2,
            Blocks.M
               .Q()
               .a(BlockPistonMoving.a, var6)
               .a(BlockPistonMoving.b, this.N ? BlockPistonExtension.EnumPistonType.b : BlockPistonExtension.EnumPistonType.a),
            3
         );
         var1.a(var2, BlockPistonMoving.a(this.a(var5), var6, false, true));
         if (this.N) {
            BlockPos var8 = var2.a(var6.g() * 2, var6.h() * 2, var6.i() * 2);
            Block var9 = var1.p(var8).c();
            boolean var10 = false;
            if (var9 == Blocks.M) {
               TileEntity var11 = var1.s(var8);
               if (var11 instanceof TileEntityPiston) {
                  TileEntityPiston var12 = (TileEntityPiston)var11;
                  if (var12.e() == var6 && var12.d()) {
                     var12.h();
                     var10 = true;
                  }
               }
            }

            if (!var10 && var9.t() != Material.a && a(var9, var1, var8, var6.d(), false) && (var9.k() == 0 || var9 == Blocks.J || var9 == Blocks.F)) {
               this.a(var1, var2, var6, false);
            }
         } else {
            var1.g(var2.a(var6));
         }

         var1.a(var2.n() + 0.5, var2.o() + 0.5, var2.p() + 0.5, "tile.piston.in", 0.5F, var1.s.nextFloat() * 0.15F + 0.6F);
      }

      return true;
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      if (var3.c() == this && var3.b(b)) {
         float var4 = 0.25F;
         EnumFacing var5 = var3.b(a);
         if (var5 != null) {
            switch (var5) {
               case a:
                  this.a(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                  break;
               case b:
                  this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                  break;
               case c:
                  this.a(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                  break;
               case d:
                  this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                  break;
               case e:
                  this.a(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                  break;
               case f:
                  this.a(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
            }
         }
      } else {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   @Override
   public void j() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      this.a(var1, var2);
      return super.a(var1, var2, var3);
   }

   @Override
   public boolean d() {
      return false;
   }

   public static EnumFacing b(int var0) {
      int var1 = var0 & 7;
      return var1 > 5 ? null : EnumFacing.a(var1);
   }

   public static EnumFacing a(World var0, BlockPos var1, EntityLivingBase var2) {
      if (MathHelper.e((float)var2.s - var1.n()) < 2.0F && MathHelper.e((float)var2.u - var1.p()) < 2.0F) {
         double var3 = var2.t + var2.aS();
         if (var3 - var1.o() > 2.0) {
            return EnumFacing.b;
         }

         if (var1.o() - var3 > 0.0) {
            return EnumFacing.a;
         }
      }

      return var2.aP().d();
   }

   public static boolean a(Block var0, World var1, BlockPos var2, EnumFacing var3, boolean var4) {
      if (var0 == Blocks.Z) {
         return false;
      } else if (!var1.af().a(var2)) {
         return false;
      } else if (var2.o() >= 0 && (var3 != EnumFacing.a || var2.o() != 0)) {
         if (var2.o() <= var1.U() - 1 && (var3 != EnumFacing.b || var2.o() != var1.U() - 1)) {
            if (var0 != Blocks.J && var0 != Blocks.F) {
               if (var0.g(var1, var2) == -1.0F) {
                  return false;
               }

               if (var0.k() == 2) {
                  return false;
               }

               if (var0.k() == 1) {
                  if (!var4) {
                     return false;
                  }

                  return true;
               }
            } else if (var1.p(var2).b(b)) {
               return false;
            }

            return !(var0 instanceof ITileEntityProvider);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean a(World var1, BlockPos var2, EnumFacing var3, boolean var4) {
      if (!var4) {
         var1.g(var2.a(var3));
      }

      BlockPistonStructureHelper var5 = new BlockPistonStructureHelper(var1, var2, var3, var4);
      List var6 = var5.c();
      List var7 = var5.d();
      if (!var5.a()) {
         return false;
      } else {
         int var8 = var6.size() + var7.size();
         Block[] var9 = new Block[var8];
         EnumFacing var10 = var4 ? var3 : var3.d();

         for (int var11 = var7.size() - 1; var11 >= 0; var11--) {
            BlockPos var12 = (BlockPos)var7.get(var11);
            Block var13 = var1.p(var12).c();
            var13.b(var1, var12, var1.p(var12), 0);
            var1.g(var12);
            var9[--var8] = var13;
         }

         for (int var16 = var6.size() - 1; var16 >= 0; var16--) {
            BlockPos var18 = (BlockPos)var6.get(var16);
            IBlockState var23 = var1.p(var18);
            Block var14 = var23.c();
            int var15 = var14.c(var23);
            var1.g(var18);
            var18 = var18.a(var10);
            var1.a(var18, Blocks.M.Q().a(a, var3), 4);
            var1.a(var18, BlockPistonMoving.a(var23, var3, var4, false));
            var9[--var8] = var14;
         }

         BlockPos var17 = var2.a(var3);
         if (var4) {
            BlockPistonExtension.EnumPistonType var20 = this.N ? BlockPistonExtension.EnumPistonType.b : BlockPistonExtension.EnumPistonType.a;
            IBlockState var24 = Blocks.K.Q().a(BlockPistonExtension.a, var3).a(BlockPistonExtension.b, var20);
            IBlockState var25 = Blocks.M
               .Q()
               .a(BlockPistonMoving.a, var3)
               .a(BlockPistonMoving.b, this.N ? BlockPistonExtension.EnumPistonType.b : BlockPistonExtension.EnumPistonType.a);
            var1.a(var17, var25, 4);
            var1.a(var17, BlockPistonMoving.a(var24, var3, true, false));
         }

         for (int var21 = var7.size() - 1; var21 >= 0; var21--) {
            var1.c((BlockPos)var7.get(var21), var9[var8++]);
         }

         for (int var22 = var6.size() - 1; var22 >= 0; var22--) {
            var1.c((BlockPos)var6.get(var22), var9[var8++]);
         }

         if (var4) {
            var1.c(var17, Blocks.K);
            var1.c(var2, this);
         }

         return true;
      }
   }

   @Override
   public IBlockState b(IBlockState var1) {
      return this.Q().a(a, EnumFacing.b);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, b(var1)).a(b, (var1 & 8) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).a();
      if (var1.b(b)) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }
}
