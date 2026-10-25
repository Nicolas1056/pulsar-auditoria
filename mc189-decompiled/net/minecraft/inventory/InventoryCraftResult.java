package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryCraftResult implements IInventory {
   private ItemStack[] a = new ItemStack[1];

   @Override
   public int o_() {
      return 1;
   }

   @Override
   public ItemStack a(int var1) {
      return this.a[0];
   }

   @Override
   public String e_() {
      return "Result";
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
   public ItemStack a(int var1, int var2) {
      if (this.a[0] != null) {
         ItemStack var3 = this.a[0];
         this.a[0] = null;
         return var3;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack b(int var1) {
      if (this.a[0] != null) {
         ItemStack var2 = this.a[0];
         this.a[0] = null;
         return var2;
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      this.a[0] = var2;
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
}
