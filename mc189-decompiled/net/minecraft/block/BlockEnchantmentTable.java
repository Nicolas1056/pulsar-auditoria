package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockEnchantmentTable extends BlockContainer {
   protected BlockEnchantmentTable() {
      super(Material.e, MapColor.D);
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
      this.e(0);
      this.a(CreativeTabs.c);
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.c(var1, var2, var3, var4);

      for (int var5 = -2; var5 <= 2; var5++) {
         for (int var6 = -2; var6 <= 2; var6++) {
            if (var5 > -2 && var5 < 2 && var6 == -1) {
               var6 = 2;
            }

            if (var4.nextInt(16) == 0) {
               for (int var7 = 0; var7 <= 1; var7++) {
                  BlockPos var8 = var2.a(var5, var7, var6);
                  if (var1.p(var8).c() == Blocks.X) {
                     if (!var1.d(var2.a(var5 / 2, 0, var6 / 2))) {
                        break;
                     }

                     var1.a(
                        EnumParticleTypes.z,
                        var2.n() + 0.5,
                        var2.o() + 2.0,
                        var2.p() + 0.5,
                        var5 + var4.nextFloat() - 0.5,
                        var7 - var4.nextFloat() - 1.0F,
                        var6 + var4.nextFloat() - 0.5
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityEnchantmentTable();
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         TileEntity var9 = var1.s(var2);
         if (var9 instanceof TileEntityEnchantmentTable) {
            var4.a((TileEntityEnchantmentTable)var9);
         }

         return true;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      super.a(var1, var2, var3, var4, var5);
      if (var5.s()) {
         TileEntity var6 = var1.s(var2);
         if (var6 instanceof TileEntityEnchantmentTable) {
            ((TileEntityEnchantmentTable)var6).a(var5.q());
         }
      }
   }
}
