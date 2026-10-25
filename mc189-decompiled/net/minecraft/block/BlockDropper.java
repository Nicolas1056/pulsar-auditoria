package net.minecraft.block;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockDropper extends BlockDispenser {
   private final IBehaviorDispenseItem P = new BehaviorDefaultDispenseItem();

   @Override
   protected IBehaviorDispenseItem a(ItemStack var1) {
      return this.P;
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityDropper();
   }

   @Override
   protected void f(World var1, BlockPos var2) {
      BlockSourceImpl var3 = new BlockSourceImpl(var1, var2);
      TileEntityDispenser var4 = var3.h();
      if (var4 != null) {
         int var5 = var4.m();
         if (var5 < 0) {
            var1.b(1001, var2, 0);
         } else {
            ItemStack var6 = var4.a(var5);
            if (var6 != null) {
               EnumFacing var7 = var1.p(var2).b(a);
               BlockPos var8 = var2.a(var7);
               IInventory var9 = TileEntityHopper.b(var1, var8.n(), var8.o(), var8.p());
               ItemStack var10;
               if (var9 == null) {
                  var10 = this.P.a(var3, var6);
                  if (var10 != null && var10.b <= 0) {
                     var10 = null;
                  }
               } else {
                  var10 = TileEntityHopper.a(var9, var6.k().a(1), var7.d());
                  if (var10 == null) {
                     var10 = var6.k();
                     if (--var10.b <= 0) {
                        var10 = null;
                     }
                  } else {
                     var10 = var6.k();
                  }
               }

               var4.a(var5, var10);
            }
         }
      }
   }
}
