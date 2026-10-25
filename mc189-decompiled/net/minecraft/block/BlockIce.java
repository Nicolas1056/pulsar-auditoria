package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class BlockIce extends BlockBreakable {
   public BlockIce() {
      super(Material.w, false);
      this.L = 0.98F;
      this.a(true);
      this.a(CreativeTabs.b);
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.d;
   }

   @Override
   public void a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      var2.b(StatList.ab[Block.a(this)]);
      var2.a(0.025F);
      if (this.I() && EnchantmentHelper.e(var2)) {
         ItemStack var8 = this.i(var4);
         if (var8 != null) {
            a(var1, var3, var8);
         }
      } else {
         if (var1.t.n()) {
            var1.g(var3);
            return;
         }

         int var6 = EnchantmentHelper.f(var2);
         this.b(var1, var3, var4, var6);
         Material var7 = var1.p(var3.b()).c().t();
         if (var7.c() || var7.d()) {
            var1.a(var3, Blocks.i.Q());
         }
      }
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var1.b(EnumSkyBlock.b, var2) > 11 - this.p()) {
         if (var1.t.n()) {
            var1.g(var2);
         } else {
            this.b(var1, var2, var1.p(var2), 0);
            var1.a(var2, Blocks.j.Q());
         }
      }
   }

   @Override
   public int k() {
      return 0;
   }
}
