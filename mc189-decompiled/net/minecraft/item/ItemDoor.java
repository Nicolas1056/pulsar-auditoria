package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemDoor extends Item {
   private Block a;

   public ItemDoor(Block var1) {
      this.a = var1;
      this.a(CreativeTabs.d);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 != EnumFacing.b) {
         return false;
      } else {
         IBlockState var9 = var3.p(var4);
         Block var10 = var9.c();
         if (!var10.a(var3, var4)) {
            var4 = var4.a(var5);
         }

         if (!var2.a(var4, var5, var1)) {
            return false;
         } else if (!this.a.d(var3, var4)) {
            return false;
         } else {
            a(var3, var4, EnumFacing.a((double)var2.y), this.a);
            var1.b--;
            return true;
         }
      }
   }

   public static void a(World var0, BlockPos var1, EnumFacing var2, Block var3) {
      BlockPos var4 = var1.a(var2.e());
      BlockPos var5 = var1.a(var2.f());
      int var6 = (var0.p(var5).c().v() ? 1 : 0) + (var0.p(var5.a()).c().v() ? 1 : 0);
      int var7 = (var0.p(var4).c().v() ? 1 : 0) + (var0.p(var4.a()).c().v() ? 1 : 0);
      boolean var8 = var0.p(var5).c() == var3 || var0.p(var5.a()).c() == var3;
      boolean var9 = var0.p(var4).c() == var3 || var0.p(var4.a()).c() == var3;
      boolean var10 = false;
      if (var8 && !var9 || var7 > var6) {
         var10 = true;
      }

      BlockPos var11 = var1.a();
      IBlockState var12 = var3.Q().a(BlockDoor.a, var2).a(BlockDoor.N, var10 ? BlockDoor.EnumHingePosition.b : BlockDoor.EnumHingePosition.a);
      var0.a(var1, var12.a(BlockDoor.P, BlockDoor.EnumDoorHalf.b), 2);
      var0.a(var11, var12.a(BlockDoor.P, BlockDoor.EnumDoorHalf.a), 2);
      var0.c(var1, var3);
      var0.c(var11, var3);
   }
}
