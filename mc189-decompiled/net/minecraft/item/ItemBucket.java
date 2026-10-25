package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBucket extends Item {
   private Block a;

   public ItemBucket(Block var1) {
      this.h = 1;
      this.a = var1;
      this.a(CreativeTabs.f);
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      boolean var4 = this.a == Blocks.a;
      MovingObjectPosition var5 = this.a(var2, var3, var4);
      if (var5 == null) {
         return var1;
      } else {
         if (var5.a == MovingObjectPosition.MovingObjectType.b) {
            BlockPos var6 = var5.a();
            if (!var2.a(var3, var6)) {
               return var1;
            }

            if (var4) {
               if (!var3.a(var6.a(var5.b), var5.b, var1)) {
                  return var1;
               }

               IBlockState var7 = var2.p(var6);
               Material var8 = var7.c().t();
               if (var8 == Material.h && var7.b(BlockLiquid.b) == 0) {
                  var2.g(var6);
                  var3.b(StatList.ad[Item.b(this)]);
                  return this.a(var1, var3, Items.ax);
               }

               if (var8 == Material.i && var7.b(BlockLiquid.b) == 0) {
                  var2.g(var6);
                  var3.b(StatList.ad[Item.b(this)]);
                  return this.a(var1, var3, Items.ay);
               }
            } else {
               if (this.a == Blocks.a) {
                  return new ItemStack(Items.aw);
               }

               BlockPos var9 = var6.a(var5.b);
               if (!var3.a(var9, var5.b, var1)) {
                  return var1;
               }

               if (this.a(var2, var9) && !var3.bA.d) {
                  var3.b(StatList.ad[Item.b(this)]);
                  return new ItemStack(Items.aw);
               }
            }
         }

         return var1;
      }
   }

   private ItemStack a(ItemStack var1, EntityPlayer var2, Item var3) {
      if (var2.bA.d) {
         return var1;
      } else if (--var1.b <= 0) {
         return new ItemStack(var3);
      } else {
         if (!var2.bi.a(new ItemStack(var3))) {
            var2.a(new ItemStack(var3, 1, 0), false);
         }

         return var1;
      }
   }

   public boolean a(World var1, BlockPos var2) {
      if (this.a == Blocks.a) {
         return false;
      } else {
         Material var3 = var1.p(var2).c().t();
         boolean var4 = !var3.a();
         if (!var1.d(var2) && !var4) {
            return false;
         } else {
            if (var1.t.n() && this.a == Blocks.i) {
               int var5 = var2.n();
               int var6 = var2.o();
               int var7 = var2.p();
               var1.a(var5 + 0.5F, var6 + 0.5F, var7 + 0.5F, "random.fizz", 0.5F, 2.6F + (var1.s.nextFloat() - var1.s.nextFloat()) * 0.8F);

               for (int var8 = 0; var8 < 8; var8++) {
                  var1.a(EnumParticleTypes.m, var5 + Math.random(), var6 + Math.random(), var7 + Math.random(), 0.0, 0.0, 0.0);
               }
            } else {
               if (!var1.D && var4 && !var3.d()) {
                  var1.b(var2, true);
               }

               var1.a(var2, this.a.Q(), 3);
            }

            return true;
         }
      }
   }
}
