package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSnow extends Block {
   public static final PropertyInteger a = PropertyInteger.a("layers", 1, 8);

   protected BlockSnow() {
      super(Material.y);
      this.j(this.M.b().a(a, 1));
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.a(true);
      this.a(CreativeTabs.c);
      this.j();
   }

   @Override
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return var1.p(var2).b(a) < 5;
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      int var4 = var3.b(a) - 1;
      float var5 = 0.125F;
      return new AxisAlignedBB(var2.n() + this.B, var2.o() + this.C, var2.p() + this.D, var2.n() + this.E, var2.o() + var4 * var5, var2.p() + this.G);
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
   public void j() {
      this.b(0);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      this.b(var3.b(a));
   }

   protected void b(int var1) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, var1 / 8.0F, 1.0F);
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2.b());
      Block var4 = var3.c();
      if (var4 != Blocks.aI && var4 != Blocks.cB) {
         if (var4.t() == Material.j) {
            return true;
         } else {
            return var4 == this && var3.b(a) >= 7 ? true : var4.c() && var4.J.c();
         }
      } else {
         return false;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.e(var1, var2, var3);
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
   public void a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      a(var1, var3, new ItemStack(Items.aD, var4.b(a) + 1, 0));
      var1.g(var3);
      var2.b(StatList.ab[Block.a(this)]);
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.aD;
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var1.b(EnumSkyBlock.b, var2) > 11) {
         this.b(var1, var2, var1.p(var2), 0);
         var1.g(var2);
      }
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.b ? true : super.a(var1, var2, var3);
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, (var1 & 7) + 1);
   }

   @Override
   public boolean a(World var1, BlockPos var2) {
      return var1.p(var2).b(a) == 1;
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a) - 1;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
