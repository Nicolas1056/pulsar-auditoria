package net.minecraft.entity.player;

import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;

public class InventoryPlayer implements IInventory {
   public ItemStack[] a = new ItemStack[36];
   public ItemStack[] b = new ItemStack[4];
   public int c;
   public EntityPlayer d;
   private ItemStack f;
   public boolean e;

   public InventoryPlayer(EntityPlayer var1) {
      this.d = var1;
   }

   public ItemStack h() {
      return this.c < 9 && this.c >= 0 ? this.a[this.c] : null;
   }

   public static int i() {
      return 9;
   }

   private int c(Item var1) {
      for (int var2 = 0; var2 < this.a.length; var2++) {
         if (this.a[var2] != null && this.a[var2].b() == var1) {
            return var2;
         }
      }

      return -1;
   }

   private int a(Item var1, int var2) {
      for (int var3 = 0; var3 < this.a.length; var3++) {
         if (this.a[var3] != null && this.a[var3].b() == var1 && this.a[var3].i() == var2) {
            return var3;
         }
      }

      return -1;
   }

   private int d(ItemStack var1) {
      for (int var2 = 0; var2 < this.a.length; var2++) {
         if (this.a[var2] != null
            && this.a[var2].b() == var1.b()
            && this.a[var2].d()
            && this.a[var2].b < this.a[var2].c()
            && this.a[var2].b < this.q_()
            && (!this.a[var2].f() || this.a[var2].i() == var1.i())
            && ItemStack.a(this.a[var2], var1)) {
            return var2;
         }
      }

      return -1;
   }

   public int j() {
      for (int var1 = 0; var1 < this.a.length; var1++) {
         if (this.a[var1] == null) {
            return var1;
         }
      }

      return -1;
   }

   public void a(Item var1, int var2, boolean var3, boolean var4) {
      ItemStack var5 = this.h();
      int var6 = var3 ? this.a(var1, var2) : this.c(var1);
      if (var6 >= 0 && var6 < 9) {
         this.c = var6;
      } else if (var4 && var1 != null) {
         int var7 = this.j();
         if (var7 >= 0 && var7 < 9) {
            this.c = var7;
         }

         if (var5 == null || !var5.v() || this.a(var5.b(), var5.h()) != this.c) {
            int var8 = this.a(var1, var2);
            int var9;
            if (var8 >= 0) {
               var9 = this.a[var8].b;
               this.a[var8] = this.a[this.c];
            } else {
               var9 = 1;
            }

            this.a[this.c] = new ItemStack(var1, var9, var2);
         }
      }
   }

   public void d(int var1) {
      if (var1 > 0) {
         var1 = 1;
      }

      if (var1 < 0) {
         var1 = -1;
      }

      this.c -= var1;

      while (this.c < 0) {
         this.c += 9;
      }

      while (this.c >= 9) {
         this.c -= 9;
      }
   }

   public int a(Item var1, int var2, int var3, NBTTagCompound var4) {
      int var5 = 0;

      for (int var6 = 0; var6 < this.a.length; var6++) {
         ItemStack var7 = this.a[var6];
         if (var7 != null && (var1 == null || var7.b() == var1) && (var2 <= -1 || var7.i() == var2) && (var4 == null || NBTUtil.a(var4, var7.o(), true))) {
            int var8 = var3 <= 0 ? var7.b : Math.min(var3 - var5, var7.b);
            var5 += var8;
            if (var3 != 0) {
               this.a[var6].b -= var8;
               if (this.a[var6].b == 0) {
                  this.a[var6] = null;
               }

               if (var3 > 0 && var5 >= var3) {
                  return var5;
               }
            }
         }
      }

      for (int var9 = 0; var9 < this.b.length; var9++) {
         ItemStack var11 = this.b[var9];
         if (var11 != null && (var1 == null || var11.b() == var1) && (var2 <= -1 || var11.i() == var2) && (var4 == null || NBTUtil.a(var4, var11.o(), false))) {
            int var12 = var3 <= 0 ? var11.b : Math.min(var3 - var5, var11.b);
            var5 += var12;
            if (var3 != 0) {
               this.b[var9].b -= var12;
               if (this.b[var9].b == 0) {
                  this.b[var9] = null;
               }

               if (var3 > 0 && var5 >= var3) {
                  return var5;
               }
            }
         }
      }

      if (this.f != null) {
         if (var1 != null && this.f.b() != var1) {
            return var5;
         }

         if (var2 > -1 && this.f.i() != var2) {
            return var5;
         }

         if (var4 != null && !NBTUtil.a(var4, this.f.o(), false)) {
            return var5;
         }

         int var10 = var3 <= 0 ? this.f.b : Math.min(var3 - var5, this.f.b);
         var5 += var10;
         if (var3 != 0) {
            this.f.b -= var10;
            if (this.f.b == 0) {
               this.f = null;
            }

            if (var3 > 0 && var5 >= var3) {
               return var5;
            }
         }
      }

      return var5;
   }

   private int e(ItemStack var1) {
      Item var2 = var1.b();
      int var3 = var1.b;
      int var4 = this.d(var1);
      if (var4 < 0) {
         var4 = this.j();
      }

      if (var4 < 0) {
         return var3;
      } else {
         if (this.a[var4] == null) {
            this.a[var4] = new ItemStack(var2, 0, var1.i());
            if (var1.n()) {
               this.a[var4].d((NBTTagCompound)var1.o().b());
            }
         }

         int var5 = var3;
         if (var3 > this.a[var4].c() - this.a[var4].b) {
            var5 = this.a[var4].c() - this.a[var4].b;
         }

         if (var5 > this.q_() - this.a[var4].b) {
            var5 = this.q_() - this.a[var4].b;
         }

         if (var5 == 0) {
            return var3;
         } else {
            var3 -= var5;
            this.a[var4].b += var5;
            this.a[var4].c = 5;
            return var3;
         }
      }
   }

   public void k() {
      for (int var1 = 0; var1 < this.a.length; var1++) {
         if (this.a[var1] != null) {
            this.a[var1].a(this.d.o, this.d, var1, this.c == var1);
         }
      }
   }

   public boolean a(Item var1) {
      int var2 = this.c(var1);
      if (var2 < 0) {
         return false;
      } else {
         if (--this.a[var2].b <= 0) {
            this.a[var2] = null;
         }

         return true;
      }
   }

   public boolean b(Item var1) {
      int var2 = this.c(var1);
      return var2 >= 0;
   }

   public boolean a(final ItemStack var1) {
      if (var1 != null && var1.b != 0 && var1.b() != null) {
         try {
            if (var1.g()) {
               int var6 = this.j();
               if (var6 >= 0) {
                  this.a[var6] = ItemStack.b(var1);
                  this.a[var6].c = 5;
                  var1.b = 0;
                  return true;
               } else if (this.d.bA.d) {
                  var1.b = 0;
                  return true;
               } else {
                  return false;
               }
            } else {
               int var2;
               do {
                  var2 = var1.b;
                  var1.b = this.e(var1);
               } while (var1.b > 0 && var1.b < var2);

               if (var1.b == var2 && this.d.bA.d) {
                  var1.b = 0;
                  return true;
               } else {
                  return var1.b < var2;
               }
            }
         } catch (Throwable var5) {
            CrashReport var3 = CrashReport.a(var5, "Adding item to inventory");
            CrashReportCategory var4 = var3.a("Item being added");
            var4.a("Item ID", Item.b(var1.b()));
            var4.a("Item data", var1.i());
            var4.a("Item name", new Callable<String>() {
               public String a() throws Exception {
                  return var1.q();
               }
            });
            throw new ReportedException(var3);
         }
      } else {
         return false;
      }
   }

   @Override
   public ItemStack a(int var1, int var2) {
      ItemStack[] var3 = this.a;
      if (var1 >= this.a.length) {
         var3 = this.b;
         var1 -= this.a.length;
      }

      if (var3[var1] != null) {
         if (var3[var1].b <= var2) {
            ItemStack var5 = var3[var1];
            var3[var1] = null;
            return var5;
         } else {
            ItemStack var4 = var3[var1].a(var2);
            if (var3[var1].b == 0) {
               var3[var1] = null;
            }

            return var4;
         }
      } else {
         return null;
      }
   }

   @Override
   public ItemStack b(int var1) {
      ItemStack[] var2 = this.a;
      if (var1 >= this.a.length) {
         var2 = this.b;
         var1 -= this.a.length;
      }

      if (var2[var1] != null) {
         ItemStack var3 = var2[var1];
         var2[var1] = null;
         return var3;
      } else {
         return null;
      }
   }

   @Override
   public void a(int var1, ItemStack var2) {
      ItemStack[] var3 = this.a;
      if (var1 >= var3.length) {
         var1 -= var3.length;
         var3 = this.b;
      }

      var3[var1] = var2;
   }

   public float a(Block var1) {
      float var2 = 1.0F;
      if (this.a[this.c] != null) {
         var2 *= this.a[this.c].a(var1);
      }

      return var2;
   }

   public NBTTagList a(NBTTagList var1) {
      for (int var2 = 0; var2 < this.a.length; var2++) {
         if (this.a[var2] != null) {
            NBTTagCompound var3 = new NBTTagCompound();
            var3.a("Slot", (byte)var2);
            this.a[var2].b(var3);
            var1.a(var3);
         }
      }

      for (int var4 = 0; var4 < this.b.length; var4++) {
         if (this.b[var4] != null) {
            NBTTagCompound var5 = new NBTTagCompound();
            var5.a("Slot", (byte)(var4 + 100));
            this.b[var4].b(var5);
            var1.a(var5);
         }
      }

      return var1;
   }

   public void b(NBTTagList var1) {
      this.a = new ItemStack[36];
      this.b = new ItemStack[4];

      for (int var2 = 0; var2 < var1.c(); var2++) {
         NBTTagCompound var3 = var1.b(var2);
         int var4 = var3.d("Slot") & 255;
         ItemStack var5 = ItemStack.a(var3);
         if (var5 != null) {
            if (var4 >= 0 && var4 < this.a.length) {
               this.a[var4] = var5;
            }

            if (var4 >= 100 && var4 < this.b.length + 100) {
               this.b[var4 - 100] = var5;
            }
         }
      }
   }

   @Override
   public int o_() {
      return this.a.length + 4;
   }

   @Override
   public ItemStack a(int var1) {
      ItemStack[] var2 = this.a;
      if (var1 >= var2.length) {
         var1 -= var2.length;
         var2 = this.b;
      }

      return var2[var1];
   }

   @Override
   public String e_() {
      return "container.inventory";
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

   public boolean b(Block var1) {
      if (var1.t().l()) {
         return true;
      } else {
         ItemStack var2 = this.a(this.c);
         return var2 != null ? var2.b(var1) : false;
      }
   }

   public ItemStack e(int var1) {
      return this.b[var1];
   }

   public int m() {
      int var1 = 0;

      for (int var2 = 0; var2 < this.b.length; var2++) {
         if (this.b[var2] != null && this.b[var2].b() instanceof ItemArmor) {
            int var3 = ((ItemArmor)this.b[var2].b()).c;
            var1 += var3;
         }
      }

      return var1;
   }

   public void a(float var1) {
      var1 /= 4.0F;
      if (var1 < 1.0F) {
         var1 = 1.0F;
      }

      for (int var2 = 0; var2 < this.b.length; var2++) {
         if (this.b[var2] != null && this.b[var2].b() instanceof ItemArmor) {
            this.b[var2].a((int)var1, this.d);
            if (this.b[var2].b == 0) {
               this.b[var2] = null;
            }
         }
      }
   }

   public void n() {
      for (int var1 = 0; var1 < this.a.length; var1++) {
         if (this.a[var1] != null) {
            this.d.a(this.a[var1], true, false);
            this.a[var1] = null;
         }
      }

      for (int var2 = 0; var2 < this.b.length; var2++) {
         if (this.b[var2] != null) {
            this.d.a(this.b[var2], true, false);
            this.b[var2] = null;
         }
      }
   }

   @Override
   public void p_() {
      this.e = true;
   }

   public void b(ItemStack var1) {
      this.f = var1;
   }

   public ItemStack p() {
      return this.f;
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.d.I ? false : !(var1.h(this.d) > 64.0);
   }

   public boolean c(ItemStack var1) {
      for (int var2 = 0; var2 < this.b.length; var2++) {
         if (this.b[var2] != null && this.b[var2].a(var1)) {
            return true;
         }
      }

      for (int var3 = 0; var3 < this.a.length; var3++) {
         if (this.a[var3] != null && this.a[var3].a(var1)) {
            return true;
         }
      }

      return false;
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

   public void b(InventoryPlayer var1) {
      for (int var2 = 0; var2 < this.a.length; var2++) {
         this.a[var2] = ItemStack.b(var1.a[var2]);
      }

      for (int var3 = 0; var3 < this.b.length; var3++) {
         this.b[var3] = ItemStack.b(var1.b[var3]);
      }

      this.c = var1.c;
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

      for (int var2 = 0; var2 < this.b.length; var2++) {
         this.b[var2] = null;
      }
   }
}
