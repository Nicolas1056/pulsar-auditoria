package net.minecraft.tileentity;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityBrewingStand extends TileEntityLockable implements ITickable, ISidedInventory {
   private static final int[] a = new int[]{3};
   private static final int[] f = new int[]{0, 1, 2};
   private ItemStack[] g = new ItemStack[4];
   private int h;
   private boolean[] i;
   private Item j;
   private String k;

   @Override
   public String e_() {
      return this.l_() ? this.k : "container.brewing";
   }

   @Override
   public boolean l_() {
      return this.k != null && this.k.length() > 0;
   }

   public void a(String var1) {
      this.k = var1;
   }

   @Override
   public int o_() {
      return this.g.length;
   }

   @Override
   public void c() {
      if (this.h > 0) {
         this.h--;
         if (this.h == 0) {
            this.o();
            this.p_();
         } else if (!this.n()) {
            this.h = 0;
            this.p_();
         } else if (this.j != this.g[3].b()) {
            this.h = 0;
            this.p_();
         }
      } else if (this.n()) {
         this.h = 400;
         this.j = this.g[3].b();
      }

      if (!this.b.D) {
         boolean[] var1 = this.m();
         if (!Arrays.equals(var1, this.i)) {
            this.i = var1;
            IBlockState var2 = this.b.p(this.v());
            if (!(var2.c() instanceof BlockBrewingStand)) {
               return;
            }

            for (int var3 = 0; var3 < BlockBrewingStand.a.length; var3++) {
               var2 = var2.a(BlockBrewingStand.a[var3], var1[var3]);
            }

            this.b.a(this.c, var2, 2);
         }
      }
   }

   private boolean n() {
      if (this.g[3] != null && this.g[3].b > 0) {
         ItemStack var1 = this.g[3];
         if (!var1.b().l(var1)) {
            return false;
         } else {
            boolean var2 = false;

            for (int var3 = 0; var3 < 3; var3++) {
               if (this.g[var3] != null && this.g[var3].b() == Items.bz) {
                  int var4 = this.g[var3].i();
                  int var5 = this.c(var4, var1);
                  if (!ItemPotion.f(var4) && ItemPotion.f(var5)) {
                     var2 = true;
                     break;
                  }

                  List var6 = Items.bz.e(var4);
                  List var7 = Items.bz.e(var5);
                  if ((var4 <= 0 || var6 != var7) && (var6 == null || !var6.equals(var7) && var7 != null) && var4 != var5) {
                     var2 = true;
                     break;
                  }
               }
            }

            return var2;
         }
      } else {
         return false;
      }
   }

   private void o() {
      if (this.n()) {
         ItemStack var1 = this.g[3];

         for (int var2 = 0; var2 < 3; var2++) {
            if (this.g[var2] != null && this.g[var2].b() == Items.bz) {
               int var3 = this.g[var2].i();
               int var4 = this.c(var3, var1);
               List var5 = Items.bz.e(var3);
               List var6 = Items.bz.e(var4);
               if (var3 > 0 && var5 == var6 || var5 != null && (var5.equals(var6) || var6 == null)) {
                  if (!ItemPotion.f(var3) && ItemPotion.f(var4)) {
                     this.g[var2].b(var4);
                  }
               } else if (var3 != var4) {
                  this.g[var2].b(var4);
               }
            }
         }

         if (var1.b().r()) {
            this.g[3] = new ItemStack(var1.b().q());
         } else {
            this.g[3].b--;
            if (this.g[3].b <= 0) {
               this.g[3] = null;
            }
         }
      }
   }

   private int c(int var1, ItemStack var2) {
      if (var2 == null) {
         return var1;
      } else {
         return var2.b().l(var2) ? PotionHelper.a(var1, var2.b().j(var2)) : var1;
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      NBTTagList var2 = var1.c("Items", 10);
      this.g = new ItemStack[this.o_()];

      for (int var3 = 0; var3 < var2.c(); var3++) {
         NBTTagCompound var4 = var2.b(var3);
         byte var5 = var4.d("Slot");
         if (var5 >= 0 && var5 < this.g.length) {
            this.g[var5] = ItemStack.a(var4);
         }
      }

      this.h = var1.e("BrewTime");
      if (var1.b("CustomName", 8)) {
         this.k = var1.j("CustomName");
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("BrewTime", (short)this.h);
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
         var1.a("CustomName", this.k);
      }
   }

   @Override
   public ItemStack a(int var1) {
      return var1 >= 0 && var1 < this.g.length ? this.g[var1] : null;
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (var1 >= 0 && var1 < this.g.length) {
         ItemStack var3 = this.g[var1];
         this.g[var1] = null;
         return var3;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack b(int var1) {
      if (var1 >= 0 && var1 < this.g.length) {
         ItemStack var2 = this.g[var1];
         this.g[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      if (var1 >= 0 && var1 < this.g.length) {
         this.g[var1] = var2;
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
      return var1 == 3 ? var2.b().l(var2) : var2.b() == Items.bz || var2.b() == Items.bA;
   }

   public boolean[] m() {
      boolean[] var1 = new boolean[3];

      for (int var2 = 0; var2 < 3; var2++) {
         if (this.g[var2] != null) {
            var1[var2] = true;
         }
      }

      return var1;
   }

   @Override
   public int[] a(EnumFacing var1) {
      return var1 == EnumFacing.b ? a : f;
   }

   @Override
   public boolean a(int var1, ItemStack var2, EnumFacing var3) {
      return this.b(var1, var2);
   }

   @Override
   public boolean b(int var1, ItemStack var2, EnumFacing var3) {
      return true;
   }

   @Override
   public String k() {
      return "minecraft:brewing_stand";
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerBrewingStand(var1, this);
   }

   @Override
   public int a_(int var1) {
      switch (var1) {
         case 0:
            return this.h;
         default:
            return 0;
      }
   }

   @Override
   public void b(int var1, int var2) {
      switch (var1) {
         case 0:
            this.h = var2;
      }
   }

   @Override
   public int g() {
      return 1;
   }

   @Override
   public void l() {
      for (int var1 = 0; var1 < this.g.length; var1++) {
         this.g[var1] = null;
      }
   }
}
