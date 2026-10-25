package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeedFood extends ItemFood {
   private Block b;
   private Block c;

   public ItemSeedFood(int var1, float var2, Block var3, Block var4) {
      super(var1, var2, false);
      this.b = var3;
      this.c = var4;
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 != EnumFacing.b) {
         return false;
      } else if (!var2.a(var4.a(var5), var5, var1)) {
         return false;
      } else if (var3.p(var4).c() == this.c && var3.d(var4.a())) {
         var3.a(var4.a(), this.b.Q());
         var1.b--;
         return true;
      } else {
         return false;
      }
   }
}
