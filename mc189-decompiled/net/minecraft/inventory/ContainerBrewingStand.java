package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

public class ContainerBrewingStand extends Container {
   private IInventory a;
   private final Slot f;
   private int g;

   public ContainerBrewingStand(InventoryPlayer var1, IInventory var2) {
      this.a = var2;
      this.a(new ContainerBrewingStand.Potion(var1.d, var2, 0, 56, 46));
      this.a(new ContainerBrewingStand.Potion(var1.d, var2, 1, 79, 53));
      this.a(new ContainerBrewingStand.Potion(var1.d, var2, 2, 102, 46));
      this.f = this.a(new ContainerBrewingStand.Ingredient(var2, 3, 79, 17));

      for (int var3 = 0; var3 < 3; var3++) {
         for (int var4 = 0; var4 < 9; var4++) {
            this.a(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
         }
      }

      for (int var5 = 0; var5 < 9; var5++) {
         this.a(new Slot(var1, var5, 8 + var5 * 18, 142));
      }
   }

   @Override
   public void a(ICrafting var1) {
      super.a(var1);
      var1.a(this, this.a);
   }

   @Override
   public void b() {
      super.b();

      for (int var1 = 0; var1 < this.e.size(); var1++) {
         ICrafting var2 = this.e.get(var1);
         if (this.g != this.a.a_(0)) {
            var2.a(this, 0, this.a.a_(0));
         }
      }

      this.g = this.a.a_(0);
   }

   @Override
   public void b(int var1, int var2) {
      this.a.b(var1, var2);
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.a.a(var1);
   }

   @Override
   public ItemStack b(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = this.c.get(var2);
      if (var4 != null && var4.e()) {
         ItemStack var5 = var4.d();
         var3 = var5.k();
         if ((var2 < 0 || var2 > 2) && var2 != 3) {
            if (!this.f.e() && this.f.a(var5)) {
               if (!this.a(var5, 3, 4, false)) {
                  return null;
               }
            } else if (ContainerBrewingStand.Potion.b_(var3)) {
               if (!this.a(var5, 0, 3, false)) {
                  return null;
               }
            } else if (var2 >= 4 && var2 < 31) {
               if (!this.a(var5, 31, 40, false)) {
                  return null;
               }
            } else if (var2 >= 31 && var2 < 40) {
               if (!this.a(var5, 4, 31, false)) {
                  return null;
               }
            } else if (!this.a(var5, 4, 40, false)) {
               return null;
            }
         } else {
            if (!this.a(var5, 4, 40, true)) {
               return null;
            }

            var4.a(var5, var3);
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

   class Ingredient extends Slot {
      public Ingredient(IInventory var2, int var3, int var4, int var5) {
         super(var2, var3, var4, var5);
      }

      @Override
      public boolean a(ItemStack var1) {
         return var1 != null ? var1.b().l(var1) : false;
      }

      @Override
      public int a() {
         return 64;
      }
   }

   static class Potion extends Slot {
      private EntityPlayer a;

      public Potion(EntityPlayer var1, IInventory var2, int var3, int var4, int var5) {
         super(var2, var3, var4, var5);
         this.a = var1;
      }

      @Override
      public boolean a(ItemStack var1) {
         return b_(var1);
      }

      @Override
      public int a() {
         return 1;
      }

      @Override
      public void a(EntityPlayer var1, ItemStack var2) {
         if (var2.b() == Items.bz && var2.i() > 0) {
            this.a.b(AchievementList.B);
         }

         super.a(var1, var2);
      }

      public static boolean b_(ItemStack var0) {
         return var0 != null && (var0.b() == Items.bz || var0.b() == Items.bA);
      }
   }
}
