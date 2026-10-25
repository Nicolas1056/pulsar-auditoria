package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryCrafting implements IInventory {
   private final ItemStack[] a;
   private final int b;
   private final int c;
   private final Container d;

   public InventoryCrafting(Container var1, int var2, int var3) {
      int var4 = var2 * var3;
      this.a = new ItemStack[var4];
      this.d = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public int o_() {
      return this.a.length;
   }

   @Override
   public ItemStack a(int var1) {
      return var1 >= this.o_() ? null : this.a[var1];
   }

   public ItemStack c(int var1, int var2) {
      return var1 >= 0 && var1 < this.b && var2 >= 0 && var2 <= this.c ? this.a(var1 + var2 * this.b) : null;
   }

   @Override
   public String e_() {
      return "container.crafting";
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
   public ItemStack b(int var1) {
      if (this.a[var1] != null) {
         ItemStack var2 = this.a[var1];
         this.a[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (this.a[var1] != null) {
         if (this.a[var1].b <= var2) {
            ItemStack var4 = this.a[var1];
            this.a[var1] = null;
            this.d.a(this);
            return var4;
         } else {
            ItemStack var3 = this.a[var1].a(var2);
            if (this.a[var1].b == 0) {
               this.a[var1] = null;
            }

            this.d.a(this);
            return var3;
         }
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      this.a[var1] = var2;
      this.d.a(this);
   }

   @Override
   public int q_() {
      return 64;
   }

   @Override
   public void p_() {
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return true;
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
      for (int var1 = 0; var1 < this.a.length; var1++) {
         this.a[var1] = null;
      }
   }

   public int h() {
      return this.c;
   }

   public int i() {
      return this.b;
   }
}
