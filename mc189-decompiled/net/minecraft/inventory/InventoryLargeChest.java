package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class InventoryLargeChest implements ILockableContainer {
   private String a;
   private ILockableContainer b;
   private ILockableContainer c;

   public InventoryLargeChest(String var1, ILockableContainer var2, ILockableContainer var3) {
      this.a = var1;
      if (var2 == null) {
         var2 = var3;
      }

      if (var3 == null) {
         var3 = var2;
      }

      this.b = var2;
      this.c = var3;
      if (var2.r_()) {
         var3.a(var2.i());
      } else if (var3.r_()) {
         var2.a(var3.i());
      }
   }

   @Override
   public int o_() {
      return this.b.o_() + this.c.o_();
   }

   public boolean a(IInventory var1) {
      return this.b == var1 || this.c == var1;
   }

   @Override
   public String e_() {
      if (this.b.l_()) {
         return this.b.e_();
      } else {
         return this.c.l_() ? this.c.e_() : this.a;
      }
   }

   @Override
   public boolean l_() {
      return this.b.l_() || this.c.l_();
   }

   @Override
   public IChatComponent f_() {
      return (IChatComponent)(this.l_() ? new ChatComponentText(this.e_()) : new ChatComponentTranslation(this.e_()));
   }

   @Override
   public ItemStack a(int var1) {
      return var1 >= this.b.o_() ? this.c.a(var1 - this.b.o_()) : this.b.a(var1);
   }

   @Override
   public ItemStack a(int var1, int var2) {
      return var1 >= this.b.o_() ? this.c.a(var1 - this.b.o_(), var2) : this.b.a(var1, var2);
   }

   @Override
   public ItemStack b(int var1) {
      return var1 >= this.b.o_() ? this.c.b(var1 - this.b.o_()) : this.b.b(var1);
   }

   @Override
   public void a(int var1, ItemStack var2) {
      if (var1 >= this.b.o_()) {
         this.c.a(var1 - this.b.o_(), var2);
      } else {
         this.b.a(var1, var2);
      }
   }

   @Override
   public int q_() {
      return this.b.q_();
   }

   @Override
   public void p_() {
      this.b.p_();
      this.c.p_();
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.b.a(var1) && this.c.a(var1);
   }

   @Override
   public void b(EntityPlayer var1) {
      this.b.b(var1);
      this.c.b(var1);
   }

   @Override
   public void c(EntityPlayer var1) {
      this.b.c(var1);
      this.c.c(var1);
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
   public boolean r_() {
      return this.b.r_() || this.c.r_();
   }

   @Override
   public void a(LockCode var1) {
      this.b.a(var1);
      this.c.a(var1);
   }

   @Override
   public LockCode i() {
      return this.b.i();
   }

   @Override
   public String k() {
      return this.b.k();
   }

   @Override
   public Container a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerChest(var1, this, var2);
   }

   @Override
   public void l() {
      this.b.l();
      this.c.l();
   }
}
