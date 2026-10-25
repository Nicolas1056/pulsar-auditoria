package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFireball extends Item {
   public ItemFireball() {
      this.a(CreativeTabs.f);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var3.D) {
         return true;
      } else {
         var4 = var4.a(var5);
         if (!var2.a(var4, var5, var1)) {
            return false;
         } else {
            if (var3.p(var4).c().t() == Material.a) {
               var3.a(var4.n() + 0.5, var4.o() + 0.5, var4.p() + 0.5, "item.fireCharge.use", 1.0F, (g.nextFloat() - g.nextFloat()) * 0.2F + 1.0F);
               var3.a(var4, Blocks.ab.Q());
            }

            if (!var2.bA.d) {
               var1.b--;
            }

            return true;
         }
      }
   }
}
