package net.minecraft.entity.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {
   private ItemStack[] a = new ItemStack[36];
   private boolean b = true;

   public EntityMinecartContainer(World var1) {
      super(var1);
   }

   public EntityMinecartContainer(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      if (this.o.Q().b("doEntityDrops")) {
         InventoryHelper.a(this.o, this, this);
      }
   }

   @Override
   public ItemStack a(int var1) {
      return this.a[var1];
   }

   @Override
   public ItemStack a(int var1, int var2) {
      if (this.a[var1] != null) {
         if (this.a[var1].b <= var2) {
            ItemStack var4 = this.a[var1];
            this.a[var1] = null;
            return var4;
         } else {
            ItemStack var3 = this.a[var1].a(var2);
            if (this.a[var1].b == 0) {
               this.a[var1] = null;
            }

            return var3;
         }
      } else {
         return null;
      }
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
   public void a(int var1, ItemStack var2) {
      this.a[var1] = var2;
      if (var2 != null && var2.b > this.q_()) {
         var2.b = this.q_();
      }
   }

   @Override
   public void p_() {
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.I ? false : !(var1.h(this) > 64.0);
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
   public String e_() {
      return this.l_() ? this.aM() : "container.minecart";
   }

   @Override
   public int q_() {
      return 64;
   }

   @Override
   public void c(int var1) {
      this.b = false;
      super.c(var1);
   }

   @Override
   public void J() {
      if (this.b) {
         InventoryHelper.a(this.o, this, this);
      }

      super.J();
   }

   @Override
   protected void b(NBTTagCompound var1) {
      super.b(var1);
      NBTTagList var2 = new NBTTagList();

      for (int var3 = 0; var3 < this.a.length; var3++) {
         if (this.a[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.a("Slot", (byte)var3);
            this.a[var3].b(var4);
            var2.a(var4);
         }
      }

      var1.a("Items", var2);
   }

   @Override
   protected void a(NBTTagCompound var1) {
      super.a(var1);
      NBTTagList var2 = var1.c("Items", 10);
      this.a = new ItemStack[this.o_()];

      for (int var3 = 0; var3 < var2.c(); var3++) {
         NBTTagCompound var4 = var2.b(var3);
         int var5 = var4.d("Slot") & 255;
         if (var5 >= 0 && var5 < this.a.length) {
            this.a[var5] = ItemStack.a(var4);
         }
      }
   }

   @Override
   public boolean e(EntityPlayer var1) {
      if (!this.o.D) {
         var1.a((IInventory)this);
      }

      return true;
   }

   @Override
   protected void o() {
      int var1 = 15 - Container.b(this);
      float var2 = 0.98F + var1 * 0.001F;
      this.v *= var2;
      this.w *= 0.0;
      this.x *= var2;
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
   public boolean r_() {
      return false;
   }

   @Override
   public void a(LockCode var1) {
   }

   @Override
   public LockCode i() {
      return LockCode.a;
   }

   @Override
   public void l() {
      for (int var1 = 0; var1 < this.a.length; var1++) {
         this.a[var1] = null;
      }
   }
}
