package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerWorkbench extends Container {
   public InventoryCrafting a = new InventoryCrafting(this, 3, 3);
   public IInventory f = new InventoryCraftResult();
   private World g;
   private BlockPos h;

   public ContainerWorkbench(InventoryPlayer var1, World var2, BlockPos var3) {
      this.g = var2;
      this.h = var3;
      this.a(new SlotCrafting(var1.d, this.a, this.f, 0, 124, 35));

      for (int var4 = 0; var4 < 3; var4++) {
         for (int var5 = 0; var5 < 3; var5++) {
            this.a(new Slot(this.a, var5 + var4 * 3, 30 + var5 * 18, 17 + var4 * 18));
         }
      }

      for (int var6 = 0; var6 < 3; var6++) {
         for (int var8 = 0; var8 < 9; var8++) {
            this.a(new Slot(var1, var8 + var6 * 9 + 9, 8 + var8 * 18, 84 + var6 * 18));
         }
      }

      for (int var7 = 0; var7 < 9; var7++) {
         this.a(new Slot(var1, var7, 8 + var7 * 18, 142));
      }

      this.a(this.a);
   }

   @Override
   public void a(IInventory var1) {
      this.f.a(0, CraftingManager.a().a(this.a, this.g));
   }

   @Override
   public void b(EntityPlayer var1) {
      super.b(var1);
      if (!this.g.D) {
         for (int var2 = 0; var2 < 9; var2++) {
            ItemStack var3 = this.a.b(var2);
            if (var3 != null) {
               var1.a(var3, false);
            }
         }
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.g.p(this.h).c() != Blocks.ai ? false : !(var1.e(this.h.n() + 0.5, this.h.o() + 0.5, this.h.p() + 0.5) > 64.0);
   }

   @Override
   public ItemStack b(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = this.c.get(var2);
      if (var4 != null && var4.e()) {
         ItemStack var5 = var4.d();
         var3 = var5.k();
         if (var2 == 0) {
            if (!this.a(var5, 10, 46, true)) {
               return null;
            }

            var4.a(var5, var3);
         } else if (var2 >= 10 && var2 < 37) {
            if (!this.a(var5, 37, 46, false)) {
               return null;
            }
         } else if (var2 >= 37 && var2 < 46) {
            if (!this.a(var5, 10, 37, false)) {
               return null;
            }
         } else if (!this.a(var5, 10, 46, false)) {
            return null;
         }

         if (var5.b == 0) {
            var4.d(null);
         } else {
            var4.f();
         }

         if (var5.b == var3.b) {
            return null;
         }

         var4.a(var1, var5);
      }

      return var3;
   }

   @Override
   public boolean a(ItemStack var1, Slot var2) {
      return var2.d != this.f && super.a(var1, var2);
   }
}
