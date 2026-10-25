package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityDispenser extends TileEntityLockable implements IInventory {
   private static final Random f = new Random();
   private ItemStack[] g = new ItemStack[9];
   protected String a;

   @Override
   public int o_() {
      return 9;
   }

   @Override
   public ItemStack a(int var1) {
      return this.g[var1];
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (this.g[var1] != null) {
         if (this.g[var1].b <= var2) {
            ItemStack var4 = this.g[var1];
            this.g[var1] = null;
            this.p_();
            return var4;
         } else {
            ItemStack var3 = this.g[var1].a(var2);
            if (this.g[var1].b == 0) {
               this.g[var1] = null;
            }

            this.p_();
            return var3;
         }
      } else {
         return null;
      }
   }

   @Override
   public ItemStack b(int var1) {
      if (this.g[var1] != null) {
         ItemStack var2 = this.g[var1];
         this.g[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public int m() {
      int var1 = -1;
      int var2 = 1;

      for (int var3 = 0; var3 < this.g.length; var3++) {
         if (this.g[var3] != null && f.nextInt(var2++) == 0) {
            var1 = var3;
         }
      }

      return var1;
   }

   @Override
   public void a(int var1, ItemStack var2) {
      this.g[var1] = var2;
      if (var2 != null && var2.b > this.q_()) {
         var2.b = this.q_();
      }

      this.p_();
   }

   public int a(ItemStack var1) {
      for (int var2 = 0; var2 < this.g.length; var2++) {
         if (this.g[var2] == null || this.g[var2].b() == null) {
            this.a(var2, var1);
            return var2;
         }
      }

      return -1;
   }

   @Override
   public String e_() {
      return this.l_() ? this.a : "container.dispenser";
   }

   public void a(String var1) {
      this.a = var1;
   }

   @Override
   public boolean l_() {
      return this.a != null;
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      NBTTagList var2 = var1.c("Items", 10);
      this.g = new ItemStack[this.o_()];

      for (int var3 = 0; var3 < var2.c(); var3++) {
         NBTTagCompound var4 = var2.b(var3);
         int var5 = var4.d("Slot") & 255;
         if (var5 >= 0 && var5 < this.g.length) {
            this.g[var5] = ItemStack.a(var4);
         }
      }

      if (var1.b("CustomName", 8)) {
         this.a = var1.j("CustomName");
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      NBTTagList var2 = new NBTTagList();

      for (int var3 = 0; var3 < this.g.length; var3++) {
         if (this.g[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.a("Slot", (byte)var3);
            this.g[var3].b(var4);
            var2.a(var4);
         }
      }

      var1.a("Items", var2);
      if (this.l_()) {
         var1.a("CustomName", this.a);
      }
   }

   @Override
   public int q_() {
      return 64;
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.b.s(this.c) != this ? false : !(var1.e(this.c.n() + 0.5, this.c.o() + 0.5, this.c.p() + 0.5) > 64.0);
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
   public String k() {
      return "minecraft:dispenser";
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerDispenser(var1, this);
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
      for (int var1 = 0; var1 < this.g.length; var1++) {
         this.g[var1] = null;
      }
   }
}
