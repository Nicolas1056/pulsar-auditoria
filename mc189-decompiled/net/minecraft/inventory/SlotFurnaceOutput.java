package net.minecraft.inventory;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotFurnaceOutput extends Slot {
   private EntityPlayer a;
   private int b;

   public SlotFurnaceOutput(EntityPlayer var1, IInventory var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.a = var1;
   }

   @Override
   public boolean a(ItemStack var1) {
      return false;
   }

   @Override
   public ItemStack a(int var1) {
      if (this.e()) {
         this.b = this.b + Math.min(var1, this.d().b);
      }

      return super.a(var1);
   }

   @Override
   public void a(EntityPlayer var1, ItemStack var2) {
      this.c(var2);
      super.a(var1, var2);
   }

   @Override
   protected void a(ItemStack var1, int var2) {
      this.b += var2;
      this.c(var1);
   }

   @Override
   protected void c(ItemStack var1) {
      var1.a(this.a.o, this.a, this.b);
      if (!this.a.o.D) {
         int var2 = this.b;
         float var3 = FurnaceRecipes.a().b(var1);
         if (var3 == 0.0F) {
            var2 = 0;
         } else if (var3 < 1.0F) {
            int var4 = MathHelper.d(var2 * var3);
            if (var4 < MathHelper.f(var2 * var3) && Math.random() < var2 * var3 - var4) {
               var4++;
            }

            var2 = var4;
         }

         while (var2 > 0) {
            int var5 = EntityXPOrb.a(var2);
            var2 -= var5;
            this.a.o.d(new EntityXPOrb(this.a.o, this.a.s, this.a.t + 0.5, this.a.u + 0.5, var5));
         }
      }

      this.b = 0;
      if (var1.b() == Items.j) {
         this.a.b(AchievementList.k);
      }

      if (var1.b() == Items.aV) {
         this.a.b(AchievementList.p);
      }
   }
}
