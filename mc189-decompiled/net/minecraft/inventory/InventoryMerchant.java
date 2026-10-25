package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class InventoryMerchant implements IInventory {
   private final IMerchant a;
   private ItemStack[] b = new ItemStack[3];
   private final EntityPlayer c;
   private MerchantRecipe d;
   private int e;

   public InventoryMerchant(EntityPlayer var1, IMerchant var2) {
      this.c = var1;
      this.a = var2;
   }

   @Override
   public int o_() {
      return this.b.length;
   }

   @Override
   public ItemStack a(int var1) {
      return this.b[var1];
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (this.b[var1] != null) {
         if (var1 == 2) {
            ItemStack var5 = this.b[var1];
            this.b[var1] = null;
            return var5;
         } else if (this.b[var1].b <= var2) {
            ItemStack var4 = this.b[var1];
            this.b[var1] = null;
            if (this.e(var1)) {
               this.h();
            }

            return var4;
         } else {
            ItemStack var3 = this.b[var1].a(var2);
            if (this.b[var1].b == 0) {
               this.b[var1] = null;
            }

            if (this.e(var1)) {
               this.h();
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   private boolean e(int var1) {
      return var1 == 0 || var1 == 1;
   }

   @Override
   public ItemStack b(int var1) {
      if (this.b[var1] != null) {
         ItemStack var2 = this.b[var1];
         this.b[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      this.b[var1] = var2;
      if (var2 != null && var2.b > this.q_()) {
         var2.b = this.q_();
      }

      if (this.e(var1)) {
         this.h();
      }
   }

   @Override
   public String e_() {
      return "mob.villager";
   }

   @Override
   public boolean l_() {
      return false;
   }

   @Override
   public IChatComponent f_() {
      return (IChatComponent)(this.l_() ? new ChatComponentText(this.e_()) : new ChatComponentTranslation(this.e_()));
   }

   @Override
   public int q_() {
      return 64;
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.a.v_() == var1;
   }

   @Override
   public void b(EntityPlayer var1) {
   }

   @Override
   public void c(EntityPlayer var1) {
   }

   @Override
   public boolean b(int var1, ItemStack var2) {
      return true;
   }

   @Override
   public void p_() {
      this.h();
   }

   public void h() {
      this.d = null;
      ItemStack var1 = this.b[0];
      ItemStack var2 = this.b[1];
      if (var1 == null) {
         var1 = var2;
         var2 = null;
      }

      if (var1 == null) {
         this.a(2, null);
      } else {
         MerchantRecipeList var3 = this.a.b_(this.c);
         if (var3 != null) {
            MerchantRecipe var4 = var3.a(var1, var2, this.e);
            if (var4 != null && !var4.h()) {
               this.d = var4;
               this.a(2, var4.d().k());
            } else if (var2 != null) {
               var4 = var3.a(var2, var1, this.e);
               if (var4 != null && !var4.h()) {
                  this.d = var4;
                  this.a(2, var4.d().k());
               } else {
                  this.a(2, null);
               }
            } else {
               this.a(2, null);
            }
         }
      }

      this.a.a_(this.a(2));
   }

   public MerchantRecipe i() {
      return this.d;
   }

   public void d(int var1) {
      this.e = var1;
      this.h();
   }

   @Override
   public int a_(int var1) {
      return 0;
   }

   @Override
   public void b(int var1, int var2) {
   }

   @Override
   public int g() {
      return 0;
   }

   @Override
   public void l() {
      for (int var1 = 0; var1 < this.b.length; var1++) {
         this.b[var1] = null;
      }
   }
}
