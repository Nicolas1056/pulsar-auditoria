package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemHoe extends Item {
   protected Item.ToolMaterial a;

   public ItemHoe(Item.ToolMaterial var1) {
      this.a = var1;
      this.h = 1;
      this.d(var1.a());
      this.a(CreativeTabs.i);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var2.a(var4.a(var5), var5, var1)) {
         return false;
      } else {
         IBlockState var9 = var3.p(var4);
         Block var10 = var9.c();
         if (var5 != EnumFacing.a && var3.p(var4.a()).c().t() == Material.a) {
            if (var10 == Blocks.c) {
               return this.a(var1, var2, var3, var4, Blocks.ak.Q());
            }

            if (var10 == Blocks.d) {
               switch ((BlockDirt.DirtType)var9.b(BlockDirt.a)) {
                  case a:
                     return this.a(var1, var2, var3, var4, Blocks.ak.Q());
                  case b:
                     return this.a(var1, var2, var3, var4, Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.a));
               }
            }
         }

         return false;
      }
   }

   protected boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, IBlockState var5) {
      var3.a(var4.n() + 0.5F, var4.o() + 0.5F, var4.p() + 0.5F, var5.c().H.c(), (var5.c().H.d() + 1.0F) / 2.0F, var5.c().H.e() * 0.8F);
      if (var3.D) {
         return true;
      } else {
         var3.a(var4, var5);
         var1.a(1, var2);
         return true;
      }
   }

   @Override
   public boolean w_() {
      return true;
   }

   public String g() {
      return this.a.toString();
   }
}
