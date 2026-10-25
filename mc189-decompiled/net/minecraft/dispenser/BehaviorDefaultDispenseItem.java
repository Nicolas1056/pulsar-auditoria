package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem {
   @Override
   public final ItemStack a(IBlockSource var1, ItemStack var2) {
      ItemStack var3 = this.b(var1, var2);
      this.a(var1);
      this.a(var1, BlockDispenser.b(var1.f()));
      return var3;
   }

   protected ItemStack b(IBlockSource var1, ItemStack var2) {
      EnumFacing var3 = BlockDispenser.b(var1.f());
      IPosition var4 = BlockDispenser.a(var1);
      ItemStack var5 = var2.a(1);
      a(var1.i(), var5, 6, var3, var4);
      return var2;
   }

   public static void a(World var0, ItemStack var1, int var2, EnumFacing var3, IPosition var4) {
      double var5 = var4.a();
      double var7 = var4.b();
      double var9 = var4.c();
      if (var3.k() == EnumFacing.Axis.b) {
         var7 -= 0.125;
      } else {
         var7 -= 0.15625;
      }

      EntityItem var11 = new EntityItem(var0, var5, var7, var9, var1);
      double var12 = var0.s.nextDouble() * 0.1 + 0.2;
      var11.v = var3.g() * var12;
      var11.w = 0.2F;
      var11.x = var3.i() * var12;
      var11.v = var11.v + var0.s.nextGaussian() * 0.0075F * var2;
      var11.w = var11.w + var0.s.nextGaussian() * 0.0075F * var2;
      var11.x = var11.x + var0.s.nextGaussian() * 0.0075F * var2;
      var0.d(var11);
   }

   protected void a(IBlockSource var1) {
      var1.i().b(1000, var1.d(), 0);
   }

   protected void a(IBlockSource var1, EnumFacing var2) {
      var1.i().b(2000, var1.d(), this.a(var2));
   }

   private int a(EnumFacing var1) {
      return var1.g() + 1 + (var1.i() + 1) * 3;
   }
}
