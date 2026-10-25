package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeeds extends Item {
   private Block a;
   private Block b;

   public ItemSeeds(Block var1, Block var2) {
      this.a = var1;
      this.b = var2;
      this.a(CreativeTabs.l);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 != EnumFacing.b) {
         return false;
      } else if (!var2.a(var4.a(var5), var5, var1)) {
         return false;
      } else if (var3.p(var4).c() == this.b && var3.d(var4.a())) {
         var3.a(var4.a(), this.a.Q());
         var1.b--;
         return true;
      } else {
         return false;
      }
   }
}
