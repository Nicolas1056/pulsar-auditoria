package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem {
   @Override
   public ItemStack b(IBlockSource var1, ItemStack var2) {
      World var3 = var1.i();
      IPosition var4 = BlockDispenser.a(var1);
      EnumFacing var5 = BlockDispenser.b(var1.f());
      IProjectile var6 = this.a(var3, var4);
      var6.c(var5.g(), var5.h() + 0.1F, var5.i(), this.b(), this.a());
      var3.d((Entity)var6);
      var2.a(1);
      return var2;
   }

   @Override
   protected void a(IBlockSource var1) {
      var1.i().b(1002, var1.d(), 0);
   }

   protected abstract IProjectile a(World var1, IPosition var2);

   protected float a() {
      return 6.0F;
   }

   protected float b() {
      return 1.1F;
   }
}
