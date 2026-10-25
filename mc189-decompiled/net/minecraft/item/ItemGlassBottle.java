package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemGlassBottle extends Item {
   public ItemGlassBottle() {
      this.a(CreativeTabs.k);
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      MovingObjectPosition var4 = this.a(var2, var3, true);
      if (var4 == null) {
         return var1;
      } else {
         if (var4.a == MovingObjectPosition.MovingObjectType.b) {
            BlockPos var5 = var4.a();
            if (!var2.a(var3, var5)) {
               return var1;
            }

            if (!var3.a(var5.a(var4.b), var4.b, var1)) {
               return var1;
            }

            if (var2.p(var5).c().t() == Material.h) {
               var1.b--;
               var3.b(StatList.ad[Item.b(this)]);
               if (var1.b <= 0) {
                  return new ItemStack(Items.bz);
               }

               if (!var3.bi.a(new ItemStack(Items.bz))) {
                  var3.a(new ItemStack(Items.bz, 1, 0), false);
               }
            }
         }

         return var1;
      }
   }
}
