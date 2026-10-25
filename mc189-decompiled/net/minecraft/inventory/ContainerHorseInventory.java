package net.minecraft.inventory;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventory extends Container {
   private IInventory a;
   private EntityHorse f;

   public ContainerHorseInventory(IInventory var1, IInventory var2, final EntityHorse var3, EntityPlayer var4) {
      this.a = var2;
      this.f = var3;
      byte var5 = 3;
      var2.b(var4);
      int var6 = (var5 - 4) * 18;
      this.a(new Slot(var2, 0, 8, 18) {
         @Override
         public boolean a(ItemStack var1) {
            return super.a(var1) && var1.b() == Items.aA && !this.e();
         }
      });
      this.a(new Slot(var2, 1, 8, 36) {
         @Override
         public boolean a(ItemStack var1) {
            return super.a(var1) && var3.cO() && EntityHorse.a(var1.b());
         }

         @Override
         public boolean b() {
            return var3.cO();
         }
      });
      if (var3.cw()) {
         for (int var7 = 0; var7 < var5; var7++) {
            for (int var8 = 0; var8 < 5; var8++) {
               this.a(new Slot(var2, 2 + var8 + var7 * 5, 80 + var8 * 18, 18 + var7 * 18));
            }
         }
      }

      for (int var9 = 0; var9 < 3; var9++) {
         for (int var11 = 0; var11 < 9; var11++) {
            this.a(new Slot(var1, var11 + var9 * 9 + 9, 8 + var11 * 18, 102 + var9 * 18 + var6));
         }
      }

      for (int var10 = 0; var10 < 9; var10++) {
         this.a(new Slot(var1, var10, 8 + var10 * 18, 160 + var6));
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.a.a(var1) && this.f.ai() && this.f.g(var1) < 8.0F;
   }

   @Override
   public ItemStack b(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = this.c.get(var2);
      if (var4 != null && var4.e()) {
         ItemStack var5 = var4.d();
         var3 = var5.k();
         if (var2 < this.a.o_()) {
            if (!this.a(var5, this.a.o_(), this.c.size(), true)) {
               return null;
            }
         } else if (this.a(1).a(var5) && !this.a(1).e()) {
            if (!this.a(var5, 1, 2, false)) {
               return null;
            }
         } else if (this.a(0).a(var5)) {
            if (!this.a(var5, 0, 1, false)) {
               return null;
            }
         } else if (this.a.o_() <= 2 || !this.a(var5, 2, this.a.o_(), false)) {
            return null;
         }

         if (var5.b == 0) {
            var4.d(null);
         } else {
            var4.f();
         }
      }

      return var3;
   }

   @Override
   public void b(EntityPlayer var1) {
      super.b(var1);
      this.a.c(var1);
   }
}
