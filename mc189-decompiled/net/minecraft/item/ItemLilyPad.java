package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemLilyPad extends ItemColored {
   public ItemLilyPad(Block var1) {
      super(var1, false);
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

            BlockPos var6 = var5.a();
            IBlockState var7 = var2.p(var5);
            if (var7.c().t() == Material.h && var7.b(BlockLiquid.b) == 0 && var2.d(var6)) {
               var2.a(var6, Blocks.bx.Q());
               if (!var3.bA.d) {
                  var1.b--;
               }

               var3.b(StatList.ad[Item.b(this)]);
            }
         }

         return var1;
      }
   }

   @Override
   public int a(ItemStack var1, int var2) {
      return Blocks.bx.h(Blocks.bx.a(var1.i()));
   }
}
