package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockSlime extends BlockBreakable {
   public BlockSlime() {
      super(Material.B, false, MapColor.c);
      this.a(CreativeTabs.c);
      this.L = 0.8F;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.d;
   }

   @Override
   public void a(World var1, BlockPos var2, Entity var3, float var4) {
      if (var3.av()) {
         super.a(var1, var2, var3, var4);
      } else {
         var3.e(var4, 0.0F);
      }
   }

   @Override
   public void a(World var1, Entity var2) {
      if (var2.av()) {
         super.a(var1, var2);
      } else if (var2.w < 0.0) {
         var2.w = -var2.w;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, Entity var3) {
      if (Math.abs(var3.w) < 0.1 && !var3.av()) {
         double var4 = 0.4 + Math.abs(var3.w) * 0.2;
         var3.v *= var4;
         var3.x *= var4;
      }

      super.a(var1, var2, var3);
   }
}
