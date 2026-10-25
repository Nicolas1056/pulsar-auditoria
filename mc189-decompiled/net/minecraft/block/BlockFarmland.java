package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFarmland extends Block {
   public static final PropertyInteger a = PropertyInteger.a("moisture", 0, 7);

   protected BlockFarmland() {
      super(Material.c);
      this.j(this.M.b().a(a, 0));
      this.a(true);
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
      this.e(255);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return new AxisAlignedBB(var2.n(), var2.o(), var2.p(), var2.n() + 1, var2.o() + 1, var2.p() + 1);
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
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = var3.b(a);
      if (!this.f(var1, var2) && !var1.C(var2.a())) {
         if (var5 > 0) {
            var1.a(var2, var3.a(a, var5 - 1), 2);
         } else if (!this.e(var1, var2)) {
            var1.a(var2, Blocks.d.Q());
         }
      } else if (var5 < 7) {
         var1.a(var2, var3.a(a, 7), 2);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, Entity var3, float var4) {
      if (var3 instanceof EntityLivingBase) {
         if (!var1.D && var1.s.nextFloat() < var4 - 0.5F) {
            if (!(var3 instanceof EntityPlayer) && !var1.Q().b("mobGriefing")) {
               return;
            }

            var1.a(var2, Blocks.d.Q());
         }

         super.a(var1, var2, var3, var4);
      }
   }

   private boolean e(World var1, BlockPos var2) {
      Block var3 = var1.p(var2.a()).c();
      return var3 instanceof BlockCrops || var3 instanceof BlockStem;
   }

   private boolean f(World var1, BlockPos var2) {
      for (BlockPos.MutableBlockPos var4 : BlockPos.b(var2.a(-4, 0, -4), var2.a(4, 1, 4))) {
         if (var1.p(var4).c().t() == Material.h) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      super.a(var1, var2, var3, var4);
      if (var1.p(var2.a()).c().t().a()) {
         var1.a(var2, Blocks.d.Q());
      }
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      switch (var3) {
         case b:
            return true;
         case c:
         case d:
         case e:
         case f:
            Block var4 = var1.p(var2).c();
            return !var4.c() && var4 != Blocks.ak;
         default:
            return super.a(var1, var2, var3);
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Blocks.d.a(Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.a), var2, var3);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Item.a(Blocks.d);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, var1 & 7);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
