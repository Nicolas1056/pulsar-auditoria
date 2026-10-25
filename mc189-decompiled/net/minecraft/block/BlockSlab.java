package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSlab extends Block {
   public static final PropertyEnum<BlockSlab.EnumBlockHalf> a = PropertyEnum.a("half", BlockSlab.EnumBlockHalf.class);

   public BlockSlab(Material var1) {
      super(var1);
      if (this.l()) {
         this.r = true;
      } else {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

      this.e(255);
   }

   @Override
   protected boolean I() {
      return false;
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      if (this.l()) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         IBlockState var3 = var1.p(var2);
         if (var3.c() == this) {
            if (var3.b(a) == BlockSlab.EnumBlockHalf.a) {
               this.a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
         }
      }
   }

   @Override
   public void j() {
      if (this.l()) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      this.a(var1, var2);
      super.a(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public boolean c() {
      return this.l();
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = super.a(var1, var2, var3, var4, var5, var6, var7, var8).a(a, BlockSlab.EnumBlockHalf.b);
      if (this.l()) {
         return var9;
      } else {
         return var3 != EnumFacing.a && (var3 == EnumFacing.b || !(var5 > 0.5)) ? var9 : var9.a(a, BlockSlab.EnumBlockHalf.a);
      }
   }

   @Override
   public int a(Random var1) {
      return this.l() ? 2 : 1;
   }

   @Override
   public boolean d() {
      return this.l();
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      if (this.l()) {
         return super.a(var1, var2, var3);
      } else if (var3 != EnumFacing.b && var3 != EnumFacing.a && !super.a(var1, var2, var3)) {
         return false;
      } else {
         BlockPos var4 = var2.a(var3.d());
         IBlockState var5 = var1.p(var2);
         IBlockState var6 = var1.p(var4);
         boolean var7 = c(var5.c()) && var5.b(a) == BlockSlab.EnumBlockHalf.a;
         boolean var8 = c(var6.c()) && var6.b(a) == BlockSlab.EnumBlockHalf.a;
         if (var8) {
            if (var3 == EnumFacing.a) {
               return true;
            } else {
               return var3 == EnumFacing.b && super.a(var1, var2, var3) ? true : !c(var5.c()) || !var7;
            }
         } else if (var3 == EnumFacing.b) {
            return true;
         } else {
            return var3 == EnumFacing.a && super.a(var1, var2, var3) ? true : !c(var5.c()) || var7;
         }
      }
   }

   protected static boolean c(Block var0) {
      return var0 == Blocks.U || var0 == Blocks.bM || var0 == Blocks.cP;
   }

   public abstract String b(int var1);

   @Override
   public int j(World var1, BlockPos var2) {
      return super.j(var1, var2) & 7;
   }

   public abstract boolean l();

   public abstract IProperty<?> n();

   public abstract Object a(ItemStack var1);

   public static enum EnumBlockHalf implements IStringSerializable {
      a("top"),
      b("bottom");

      private final String c;

      private EnumBlockHalf(String var3) {
         this.c = var3;
      }

      @Override
      public String toString() {
         return this.c;
      }

      @Override
      public String l() {
         return this.c;
      }
   }
}
